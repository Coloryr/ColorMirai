package Color_yr.ColorMirai.plugin.http.obj.file;

import Color_yr.ColorMirai.plugin.http.obj.DTO;

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
