package coloryr.colormirai.demo.sdk.pack.re;

import coloryr.colormirai.demo.sdk.objs.UserProfile;
import coloryr.colormirai.demo.sdk.pack.PackBase;

/*
92 [插件]获取朋友信息
id:QQ号
img:头像图片
remark:好友备注
userProfile:用户详细资料
 */
public class FriendInfoPack extends PackBase {
    public long id;
    public String img;
    public String remark;
    public UserProfile userProfile;
}
