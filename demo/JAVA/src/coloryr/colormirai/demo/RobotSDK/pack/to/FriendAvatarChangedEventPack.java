package coloryr.colormirai.demo.RobotSDK.pack.to;

import coloryr.colormirai.demo.RobotSDK.pack.PackBase;

/*
19 [机器人]好友头像修改（事件）
id:好友QQ号
url:图片url
 */
public class FriendAvatarChangedEventPack extends PackBase {
    public String url;
    public long id;

    public FriendAvatarChangedEventPack(long qq, long id, String url) {
        this.id = id;
        this.qq = qq;
        this.url = url;
    }
}
