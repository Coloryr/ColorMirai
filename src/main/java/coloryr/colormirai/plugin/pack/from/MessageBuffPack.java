package coloryr.colormirai.plugin.pack.from;

import coloryr.colormirai.plugin.pack.PackBase;

import java.util.List;

/*
 * 95 [插件]消息队列
 */
public class MessageBuffPack extends PackBase {
    /*
     * 发送目标
     */
    public long id;
    /*
     * 发送目标
     */
    public long fid;
    /*
     * 是否发送
     */
    public boolean send;
    /*
     * 消息内容
     */
    public List<String> text;
    /*
     * 图片路径
     */
    public String imgurl;
    /*
     * 图片数据
     */
    public byte[] imgData;
    /*
     * 发送对象类型
     */
    public int type;
}
