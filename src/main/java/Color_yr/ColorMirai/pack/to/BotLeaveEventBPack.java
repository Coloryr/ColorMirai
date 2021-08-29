package Color_yr.ColorMirai.pack.to;

/*
8 [机器人]被管理员或群主踢出群（事件）
id:群号
fid:执行人QQ
 */
public class BotLeaveEventBPack extends BotLeaveEventAPack {
    public long fid;

    public BotLeaveEventBPack(long qq, long id, long fid) {
        super(qq, id);
        this.fid = fid;
    }
}
