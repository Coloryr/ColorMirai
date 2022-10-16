package coloryr.colormirai.plugin.netty;

import io.netty.buffer.ByteBuf;

public class RePackObj {
    public int index;
    public ByteBuf data;

    public RePackObj(int index, ByteBuf data) {
        this.index = index;
        this.data = data;
    }
}
