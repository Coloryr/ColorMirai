package coloryr.colormirai.plugin.mirai_http_api.obj.file;

import coloryr.colormirai.Utils;
import coloryr.colormirai.plugin.mirai_http_api.Authed;
import coloryr.colormirai.plugin.mirai_http_api.context.infoModule.BaseInfo;
import coloryr.colormirai.plugin.mirai_http_api.obj.StateCode;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.utils.RemoteFile;

import java.util.Map;

public class GroupFileInfo extends BaseInfo {
    @Override
    public Object toDo(Authed authed, Map<String, String> parameters) {
        String id = parameters.get("id");
        if (id == null) {
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
            RemoteFile remoteFile = group.getFilesRoot().resolveById(id);
            if (remoteFile == null) {
                return StateCode.NoElement;
            } else {
                FileInfoDTO info = new FileInfoDTO();
                RemoteFile.FileInfo info1 = remoteFile.getInfo();
                RemoteFile.DownloadInfo info2 = remoteFile.getDownloadInfo();
                if (info1 == null || info2 == null) {
                    return StateCode.NoOperateSupport;
                }
                info.name = info1.getName();
                info.id = info1.getId();
                info.path = info1.getPath();
                info.length = info1.getLength();
                info.downloadTimes = info1.getDownloadTimes();
                info.uploaderId = info1.getUploaderId();
                info.uploadTime = info1.getUploadTime();
                info.lastModifyTime = info1.getLastModifyTime();
                info.downloadUrl = info2.getUrl();
                info.sha1 = Utils.toUHexString(info1.getSha1());
                info.md5 = Utils.toUHexString(info1.getMd5());
                return info;
            }
        } catch (NumberFormatException e) {
            return StateCode.NoOperateSupport;
        }
    }
}