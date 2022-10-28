package coloryr.colormirai.plugin.obj;

import coloryr.colormirai.plugin.pack.PackBase;

public class SendPackObj {
    public int index;
    public PackBase data;
    public long group;
    public long qq;
    public long runqq;

    public SendPackObj(int index, PackBase data, long qq, long group, long runqq) {
        this.index = index;
        this.data = data;
        this.qq = qq;
        this.group = group;
        this.runqq = runqq;
    }
}
