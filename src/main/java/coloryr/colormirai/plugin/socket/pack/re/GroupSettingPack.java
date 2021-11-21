package coloryr.colormirai.plugin.socket.pack.re;

import coloryr.colormirai.plugin.socket.pack.PackBase;
import net.mamoe.mirai.contact.GroupSettings;

/*
58 [插件]获取群设置
id:群号
setting:设定
 */
public class GroupSettingPack extends PackBase {
    public long id;
    public GroupSettings setting;
}
