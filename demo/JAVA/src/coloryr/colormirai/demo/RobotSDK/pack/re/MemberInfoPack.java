package coloryr.colormirai.demo.RobotSDK.pack.re;

import coloryr.colormirai.demo.RobotSDK.pack.PackBase;
import net.mamoe.mirai.contact.MemberPermission;

/*
91 [插件]获取群成员信息
id:群号
fid:群成员QQ号
nick:昵称
img:头像图片
per:群权限
nameCard:群名片
specialTitle:群头衔
avatarUrl:头像下载链接
muteTimeRemaining:被禁言剩余时长
joinTimestamp:入群时间
lastSpeakTimestamp:最后发言时间
 */
public class MemberInfoPack extends PackBase {
    public long id;
    public long fid;
    public String nick;
    public String img;
    public MemberPermission per;
    public String nameCard;
    public String specialTitle;
    public String avatarUrl;
    public int muteTimeRemaining;
    public int joinTimestamp;
    public int lastSpeakTimestamp;
}
