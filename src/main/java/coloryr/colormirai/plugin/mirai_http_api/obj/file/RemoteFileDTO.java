package coloryr.colormirai.plugin.mirai_http_api.obj.file;

import coloryr.colormirai.plugin.mirai_http_api.obj.DTO;
import coloryr.colormirai.plugin.mirai_http_api.obj.contact.GroupDTO;

public class RemoteFileDTO implements DTO {
    public String name;
    public String id;
    public String path;
    public RemoteFileDTO parent;
    public GroupDTO contact;
    public boolean isFile;
    public boolean isDictionary;
    public boolean isDirectory;
    public long size;
    public DownloadInfoDTO downloadInfo;
}
