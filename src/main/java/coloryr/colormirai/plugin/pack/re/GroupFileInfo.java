package coloryr.colormirai.plugin.pack.re;

/**
 * 群文件信息
 */
public class GroupFileInfo {
    /**
     * 名字
     */
    public String name;
    /**
     * 文件ID
     */
    public String id;
    /**
     * 路径
     */
    public String absolutePath;
    /**
     * 是否为文件
     */
    public boolean isFile;
    /**
     * 是否是路径
     */
    public boolean isFolder;
    /**
     * 文件大小
     */
    public long size;
    /**
     * 上传者ID
     */
    public long uploaderId;
    /**
     * 上传时间
     */
    public long uploadTime;
    /**
     * 最后更新时间
     */
    public long lastModifyTime;
    /**
     * 文件到期时间
     */
    public long expiryTime;
    /**
     * SHA1
     */
    public String sha1;
    /**
     * MD5
     */
    public String md5;
}
