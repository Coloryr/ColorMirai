package coloryr.colormirai.plugin.pack.from;

import coloryr.colormirai.plugin.pack.PackBase;

import java.util.List;

/*
95 [插件]消息队列
send:是否发送
text:消息内容
imgurl:图片路径
type:发送对象类型
id:发送目标
fid:发送目标
 */
public class MessageBuffPack extends PackBase {
    public boolean send;
    public List<String> text;
    public String imgurl;
    public byte[] imgData;
    public int type;
    public long id;
    public long fid;
}
