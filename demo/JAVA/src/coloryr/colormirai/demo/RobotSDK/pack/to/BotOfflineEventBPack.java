package coloryr.colormirai.demo.RobotSDK.pack.to;

/*
11 [机器人]被挤下线（事件）
title:标题
message:离线原因
 */
public class BotOfflineEventBPack extends BotOfflineEventAPack {
    public String title;

    public BotOfflineEventBPack(long qq, String message, String title) {
        super(qq, message);
        this.title = title;
    }
}
