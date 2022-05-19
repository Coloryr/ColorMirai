package coloryr.colormirai.plugin.pack.from;

import java.util.List;

/**
 * 0 [插件]插件开始连接
 */
public class StartPack {
    /**
     * 插件名字
     */
    public String name;
    /**
     * 注册的事件
     */
    public List<Integer> reg;
    /**
     * 监听QQ群列表
     */
    public List<Long> groups;
    /**
     * 监听QQ号列表
     */
    public List<Long> qqList;
    /**
     * 插件运行的QQ号
     */
    public long runQQ;
}
