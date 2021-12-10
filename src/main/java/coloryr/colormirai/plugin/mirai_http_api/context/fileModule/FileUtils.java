package coloryr.colormirai.plugin.mirai_http_api.context.fileModule;

import coloryr.colormirai.Utils;
import coloryr.colormirai.plugin.mirai_http_api.obj.contact.GroupDTO;
import coloryr.colormirai.plugin.mirai_http_api.obj.file.DownloadInfoDTO;
import coloryr.colormirai.plugin.mirai_http_api.obj.file.RemoteFileDTO;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.file.AbsoluteFile;
import net.mamoe.mirai.contact.file.AbsoluteFileFolder;
import net.mamoe.mirai.contact.file.AbsoluteFolder;
import net.mamoe.mirai.utils.RemoteFile;

public class FileUtils {
    public static RemoteFileDTO get(AbsoluteFolder item) {
        if(item == null)
            return null;
        RemoteFileDTO dto = new RemoteFileDTO();
        dto.name = item.getName();
        dto.id = item.getId();
        dto.path = item.getAbsolutePath();
        AbsoluteFolder top = item.getParent();
        if (top != null) {
            dto.parent = get(top);
        }
        if (!(item.getContact() instanceof Group)) {
            return null;
        }
        dto.contact = new GroupDTO((Group) item.getContact());
        dto.isFile = item.isFile();
        dto.isDictionary = !item.isFile();
        dto.isDirectory = !item.isFile();
        return dto;
    }

    public static RemoteFileDTO get(AbsoluteFileFolder item) {
        if (item == null)
            return null;
        RemoteFileDTO dto = new RemoteFileDTO();
        dto.name = item.getName();
        dto.id = item.getId();
        dto.path = item.getAbsolutePath();
        if (!(item.getContact() instanceof Group)) {
            return null;
        }
        dto.contact = new GroupDTO((Group) item.getContact());
        dto.isFile = item.isFile();
        dto.isDictionary = !item.isFile();
        dto.isDirectory = !item.isFile();
        return dto;
    }

    public static RemoteFileDTO get(AbsoluteFile item, boolean withDownloadInfo) {
        RemoteFileDTO dto = new RemoteFileDTO();
        if (withDownloadInfo) {
                dto.downloadInfo = new DownloadInfoDTO();
                dto.downloadInfo.url = item.getUrl();
                dto.downloadInfo.lastModifyTime = item.getLastModifiedTime();
                dto.downloadInfo.sha1 = Utils.toUHexString(item.getSha1());
                dto.downloadInfo.md5 = Utils.toUHexString(item.getMd5());
                dto.downloadInfo.uploaderId = item.getUploaderId();
                dto.downloadInfo.uploadTime = item.getUploadTime();
        }
        dto.name = item.getName();
        dto.id = item.getId();
        dto.path = item.getAbsolutePath();
        AbsoluteFolder top = item.getParent();
        if (top != null) {
            dto.parent = get(top);
        }
        if (!(item.getContact() instanceof Group)) {
            return null;
        }
        dto.contact = new GroupDTO((Group) item.getContact());
        dto.isFile = item.isFile();
        dto.isDictionary = !item.isFile();
        dto.isDirectory = !item.isFile();
        dto.size = 0;
        return dto;
    }
}
