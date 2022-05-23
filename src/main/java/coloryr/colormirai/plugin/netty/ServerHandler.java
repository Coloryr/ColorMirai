package coloryr.colormirai.plugin.netty;

import coloryr.colormirai.plugin.PluginUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    public static final Map<ChannelHandlerContext, NettyThread> contexts = new ConcurrentHashMap<>();

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        contexts.put(ctx, PluginUtils.addPlugin(ctx));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;
        if (contexts.containsKey(ctx)) {
            NettyThread thread = contexts.get(ctx);
            thread.add(byteBuf);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (contexts.containsKey(ctx)) {
            NettyThread thread = contexts.remove(ctx);
            thread.close();
            PluginUtils.removePlugin(thread.getPlugin().getName());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (contexts.containsKey(ctx)) {
            NettyThread thread = contexts.remove(ctx);
            thread.close();
            PluginUtils.removePlugin(thread.getPlugin().getName());
        }
    }
}
