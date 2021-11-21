package coloryr.colormirai.plugin.socket.pack.from;

import coloryr.colormirai.plugin.socket.pack.PackBase;

import java.util.List;

/*
52 [插件]发送群消息
id:群号
message:消息
 */
public class SendGroupMessagePack extends PackBase {
    public long id;
    public List<String> message;
}
