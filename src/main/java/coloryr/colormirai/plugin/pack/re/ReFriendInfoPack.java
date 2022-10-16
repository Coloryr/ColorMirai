package coloryr.colormirai.plugin.pack.re;

import coloryr.colormirai.plugin.pack.PackBase;
import net.mamoe.mirai.data.UserProfile;

/**
 * 92 [插件]获取朋友信息
 */
public class ReFriendInfoPack extends PackBase {
    /**
     * QQ号
     */
    public long id;
    /**
     * 头像图片
     */
    public String img;
    /**
     * 好友备注
     */
    public String remark;
    /**
     * 用户详细资料
     */
    public UserProfile userProfile;
    /**
     * 用户分组ID
     */
    public int groupId;
    /**
     * 请求UUID
     */
    public String uuid;
}
