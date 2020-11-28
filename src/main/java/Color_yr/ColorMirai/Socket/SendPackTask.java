package Color_yr.ColorMirai.Socket;

public class SendPackTask {
    public byte index;
    public String data;
    public long group;
    public long qq;
    public long runqq;

    public SendPackTask(int index, String data, long qq, long group, long runqq) {
        this.index = (byte) index;
        this.data = data;
        this.qq = qq;
        this.group = group;
        this.runqq = runqq;
    }
}
