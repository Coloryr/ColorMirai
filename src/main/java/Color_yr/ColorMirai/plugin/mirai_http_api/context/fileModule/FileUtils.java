package Color_yr.ColorMirai.plugin.mirai_http_api.context.fileModule;

import Color_yr.ColorMirai.plugin.mirai_http_api.Utils;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.contact.GroupDTO;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.file.DownloadInfoDTO;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.file.RemoteFileDTO;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.utils.RemoteFile;

public class FileUtils {
    public static RemoteFileDTO get(RemoteFile item, boolean withDownloadInfo) {
        RemoteFileDTO dto = new RemoteFileDTO();
        if (withDownloadInfo) {
            RemoteFile.FileInfo file = item.getInfo();
            RemoteFile.DownloadInfo info = item.getDownloadInfo();
            if (file != null && info != null) {
                dto.downloadInfo = new DownloadInfoDTO();
                dto.downloadInfo.url = info.getUrl();
                dto.downloadInfo.downloadTimes = file.getDownloadTimes();
                dto.downloadInfo.lastModifyTime = file.getLastModifyTime();
                dto.downloadInfo.sha1 = Utils.toUHexString(file.getSha1());
                dto.downloadInfo.md5 = Utils.toUHexString(file.getMd5());
                dto.downloadInfo.uploaderId = file.getUploaderId();
                dto.downloadInfo.uploadTime = file.getUploadTime();
            }
        }
        dto.name = item.getName();
        dto.id = item.getId();
        dto.path = item.getPath();
        RemoteFile top = item.getParent();
        if (top != null) {
            dto.parent = get(top, withDownloadInfo);
        }
        if (!(item.getContact() instanceof Group)) {
            return null;
        }
        dto.contact = new GroupDTO((Group) item.getContact());
        dto.isFile = item.isFile();
        dto.isDictionary = !item.isFile();
        dto.isDirectory = !item.isFile();
        dto.size = item.length();
        return dto;
    }
}
