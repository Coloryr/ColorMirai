package coloryr.colormirai.plugin.obj;

public class SendPackObj {
    public byte index;
    public Object data;
    public long group;
    public long qq;
    public long runqq;

    public SendPackObj(int index, Object data, long qq, long group, long runqq) {
        this.index = (byte) index;
        this.data = data;
        this.qq = qq;
        this.group = group;
        this.runqq = runqq;
    }
}
