package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

/*
2 [机器人]头像被修改（通过其他客户端修改了头像）（事件）
name:机器人nick
 */
public class BotAvatarChangedPack extends PackBase {

    public BotAvatarChangedPack(long qq) {
        this.qq = qq;
    }
}
