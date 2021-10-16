package Color_yr.ColorMirai.plugin.mirai_http_api.context.infoModule;

import Color_yr.ColorMirai.plugin.mirai_http_api.Authed;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.StateCode;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.file.RemoteFileDTO;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.utils.RemoteFile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GroupFileList extends BaseInfo {
    @Override
    public Object toDo(Authed authed, Map<String, String> parameters) {
        String dir = parameters.get("dir");
        if (dir == null) {
            return StateCode.NoElement;
        }
        String target = parameters.get("target");
        if (target == null) {
            return StateCode.NoElement;
        }
        try {
            Group group = authed.bot.getGroup(Long.parseLong(target));
            if (group == null) {
                return StateCode.NoElement;
            }
            RemoteFile remoteFile = group.getFilesRoot();
            List<RemoteFileDTO> list = new ArrayList<>();
            if (dir.isEmpty()) {
                Iterator<RemoteFile> list1 = remoteFile.listFilesIterator(false);
                while (list1.hasNext()) {
                    RemoteFile temp = list1.next();
                    RemoteFileDTO info = new RemoteFileDTO();
                    info.name = temp.getName();
                    info.id = temp.getId();
                    info.path = temp.getPath();
                    info.isFile = temp.isFile();
                    list.add(info);
                }
            } else {
                RemoteFile file = remoteFile.resolve("/" + dir);
                Iterator<RemoteFile> list1 = file.listFilesIterator(false);
                while (list1.hasNext()) {
                    RemoteFile temp = list1.next();
                    RemoteFileDTO info = new RemoteFileDTO();
                    info.name = temp.getName();
                    info.id = temp.getId();
                    info.path = temp.getPath();
                    info.isFile = temp.isFile();
                    list.add(info);
                }
            }
            return list;
        } catch (NumberFormatException e) {
            return StateCode.NoOperateSupport;
        }
    }
}