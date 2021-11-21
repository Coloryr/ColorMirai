package coloryr.colormirai.plugin.socket.pack.re;

import coloryr.colormirai.plugin.socket.pack.PackBase;
import net.mamoe.mirai.data.UserProfile;

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
