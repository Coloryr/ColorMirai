package coloryr.colormirai.plugin.mirai_http_api.obj.file;

import coloryr.colormirai.plugin.mirai_http_api.obj.DTO;

public class FileInfoDTO implements DTO {
    public String name;
    public String id;
    public String path;
    public long length;
    public int downloadTimes;
    public long uploaderId;
    public long uploadTime;
    public long lastModifyTime;
    public String downloadUrl;
    public String sha1;
    public String md5;
}
