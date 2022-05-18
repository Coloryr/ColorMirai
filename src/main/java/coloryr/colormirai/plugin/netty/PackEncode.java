package coloryr.colormirai.plugin.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.List;

public class PackEncode {

    public static ByteBuf startPack(List<Long> botsKey) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(botsKey.size());
        for (long item : botsKey) {
            buf.writeLong(item);
        }
        return buf;
    }
}
