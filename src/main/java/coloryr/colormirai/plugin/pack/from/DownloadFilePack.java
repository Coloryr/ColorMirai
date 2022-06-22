package coloryr.colormirai.plugin.pack.from;

import coloryr.colormirai.plugin.pack.PackBase;

/**
 * 27 [插件]下载文件
 */
public class DownloadFilePack extends PackBase {
    /**
     * 请求UUID
     */
    public String uuid;
    /**
     * 服务器需要的某种 ID
     */
    public String id;
    /**
     * 服务器需要的某种 ID
     */
    public int internalId;
    /**
     * 文件名
     */
    public String name;
    /**
     * 文件大小 bytes
     */
    public long size;
    /**
     * 下载的位置
     */
    public String local;
    /**
     * 用户qq号
     */
    public long fid;
    /**
     * 群号
     */
    public long qid;
}
