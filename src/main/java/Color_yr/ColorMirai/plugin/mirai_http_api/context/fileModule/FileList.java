package Color_yr.ColorMirai.plugin.mirai_http_api.context.fileModule;

import Color_yr.ColorMirai.plugin.mirai_http_api.Authed;
import Color_yr.ColorMirai.plugin.mirai_http_api.Utils;
import Color_yr.ColorMirai.plugin.mirai_http_api.context.messageModule.GetBaseMessage;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.StateCode;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.contact.GroupDTO;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.file.DownloadInfoDTO;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.file.RemoteFileDTO;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.file.RemoteFileList;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.utils.RemoteFile;

import java.util.List;
import java.util.Map;
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
            RemoteFile file = group1.getFilesRoot();
            RemoteFileList list = new RemoteFileList();
            if (path != null) {
                file = file.resolve(path);
                List<RemoteFile> files = file.listFilesCollection().stream().skip(offset).limit(size).collect(Collectors.toList());
                for (RemoteFile item : files) {
                    RemoteFileDTO dto = FileUtils.get(item, withDownloadInfo);
                    if (dto == null)
                        return StateCode.Error;
                    list.data.add(dto);
                }
            } else if (!id.isEmpty()) {
                file = file.resolveById(id);
                RemoteFileDTO dto = FileUtils.get(file, withDownloadInfo);
                if (dto == null)
                    return StateCode.Error;
                list.data.add(dto);
            }
            return list;
        } catch (NumberFormatException e) {
            return StateCode.NoElement;
        }
    }
}
