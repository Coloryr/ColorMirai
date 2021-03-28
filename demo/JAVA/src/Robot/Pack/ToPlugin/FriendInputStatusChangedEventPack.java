package Robot.Pack.ToPlugin;

import Robot.Pack.PackBase;

/*
72 [机器人]友输入状态改变（事件）
id:好友QQ号
input:输入状态
 */
public class FriendInputStatusChangedEventPack extends PackBase {
    public long id;
    public boolean input;

    public FriendInputStatusChangedEventPack(long qq, long id, boolean input) {
        this.id = id;
        this.qq = qq;
        this.input = input;
    }
}
