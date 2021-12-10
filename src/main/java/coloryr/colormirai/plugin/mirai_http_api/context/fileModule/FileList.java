package coloryr.colormirai.plugin.mirai_http_api.context.fileModule;

import coloryr.colormirai.plugin.mirai_http_api.Authed;
import coloryr.colormirai.plugin.mirai_http_api.context.GetBaseMessage;
import coloryr.colormirai.plugin.mirai_http_api.obj.StateCode;
import coloryr.colormirai.plugin.mirai_http_api.obj.file.RemoteFileDTO;
import coloryr.colormirai.plugin.mirai_http_api.obj.file.RemoteFileList;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.file.AbsoluteFile;
import net.mamoe.mirai.contact.file.AbsoluteFileFolder;
import net.mamoe.mirai.contact.file.RemoteFiles;
import net.mamoe.mirai.utils.RemoteFile;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class FileList extends GetBaseMessage {

    @Override
    public Object toDo(Authed authed, Map<String, String> parameters) {
        if (!parameters.containsKey("id")) {
            return StateCode.Null;
        }
        try {
            String id = parameters.get("id");
            String target = null;
            if (parameters.containsKey("target")) {
                target = parameters.get("target");
            } else if (parameters.containsKey("group")) {
                target = parameters.get("group");
            } else if (parameters.containsKey("qq")) {
                target = parameters.get("qq");
            }
            if (target == null) {
                return StateCode.NoElement;
            }
            String temp = parameters.get("withDownloadInfo");
            int offset = 0;
            int size = 0;
            boolean withDownloadInfo = false;
            long group = Long.parseLong(id);
            withDownloadInfo = Boolean.parseBoolean(temp);
            temp = parameters.get("offset");
            if (temp != null) {
                offset = Integer.parseInt(temp);
            }
            temp = parameters.get("size");
            if (temp != null) {
                size = Integer.parseInt(temp);
            }
            String path = parameters.get("path");
            Group group1 = authed.bot.getGroup(group);
            if (group1 == null) {
                return StateCode.NoElement;
            }
            RemoteFiles files = group1.getFiles();
            RemoteFileList list = new RemoteFileList();
            AbsoluteFile file;
            if (path != null) {
                List<AbsoluteFileFolder> files1 = files.getRoot().childrenStream().skip(offset).limit(size).collect(Collectors.toList());
                for (AbsoluteFileFolder item : files1) {
                    RemoteFileDTO dto = FileUtils.get(item);
                    if (dto == null)
                        return StateCode.Error;
                    list.data.add(dto);
                }
            } else if (!id.isEmpty()) {
                file = files.getRoot().resolveFileById(id);
                RemoteFileDTO dto = FileUtils.get(file, withDownloadInfo);
                if (dto == null)
                    return StateCode.Error;
                list.data.add(dto);
            }
            return list;
        } catch (NumberFormatException| NoSuchElementException e) {
            return StateCode.NoElement;
        }
    }
}
