package coloryr.colormirai.plugin.mirai_http_api.context.fileModule;

import coloryr.colormirai.plugin.mirai_http_api.Authed;
import coloryr.colormirai.plugin.mirai_http_api.context.messageModule.GetBaseMessage;
import coloryr.colormirai.plugin.mirai_http_api.obj.StateCode;
import coloryr.colormirai.plugin.mirai_http_api.obj.file.RemoteFileDTO;
import coloryr.colormirai.plugin.mirai_http_api.obj.file.RemoteFileItem;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.utils.RemoteFile;

import java.util.Map;

public class FileInfo extends GetBaseMessage {
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
            boolean withDownloadInfo = false;
            long group = Long.parseLong(id);
            withDownloadInfo = Boolean.parseBoolean(temp);
            String path = parameters.get("path");
            Group group1 = authed.bot.getGroup(group);
            if (group1 == null) {
                return StateCode.NoElement;
            }
            RemoteFile file = group1.getFilesRoot();
            RemoteFileItem list = new RemoteFileItem();
            if (path != null) {
                file = file.resolve(path);
            } else if (!id.isEmpty()) {
                file = file.resolveById(id);
            }
            RemoteFileDTO dto = FileUtils.get(file, withDownloadInfo);
            if (dto == null)
                return StateCode.Error;
            list.data = dto;
            return list;
        } catch (NumberFormatException e) {
            return StateCode.NoElement;
        }
    }
}
