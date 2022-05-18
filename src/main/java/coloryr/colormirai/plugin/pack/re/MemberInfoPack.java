package coloryr.colormirai.plugin.pack.re;

import coloryr.colormirai.plugin.pack.PackBase;
import net.mamoe.mirai.contact.MemberPermission;

/*
 * 91 [插件]获取群成员信息
 */
public class MemberInfoPack extends PackBase {
    /*
     * 群号
     */
    public long id;
    /*
     * 群成员QQ号
     */
    public long fid;
    /*
     * 昵称
     */
    public String nick;
    /*
     * 头像图片
     */
    public String img;
    /*
     * 群权限
     */
    public MemberPermission per;
    /*
     * 群名片
     */
    public String nameCard;
    /*
     * 群头衔
     */
    public String specialTitle;
    /*
     * 头像下载链接
     */
    public String avatarUrl;
    /*
     * 被禁言剩余时长
     */
    public int muteTimeRemaining;
    /*
     * 入群时间
     */
    public int joinTimestamp;
    /*
     * 最后发言时间
     */
    public int lastSpeakTimestamp;
}
