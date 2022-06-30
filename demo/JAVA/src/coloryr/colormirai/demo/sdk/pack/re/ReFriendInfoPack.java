package coloryr.colormirai.demo.sdk.pack.re;

import coloryr.colormirai.demo.sdk.objs.UserProfile;
import coloryr.colormirai.demo.sdk.pack.PackBase;

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
     * 请求UUID
     */
    public String uuid;
}
