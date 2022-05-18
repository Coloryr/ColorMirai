package coloryr.colormirai.plugin.netty;

import io.netty.buffer.ByteBuf;

public class RePackObj {
    public byte index;
    public ByteBuf data;

    public RePackObj(byte index, ByteBuf data) {
        this.index = index;
        this.data = data;
    }
}
