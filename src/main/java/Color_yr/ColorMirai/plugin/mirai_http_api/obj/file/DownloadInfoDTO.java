package Color_yr.ColorMirai.plugin.mirai_http_api.obj.file;

import Color_yr.ColorMirai.plugin.mirai_http_api.obj.DTO;

public class DownloadInfoDTO implements DTO {
    public String sha1;
    public String md5;
    public int downloadTimes;
    public long uploaderId;
    public long uploadTime;
    public long lastModifyTime;
    public String url;
}