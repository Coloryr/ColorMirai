package coloryr.colormirai.plugin.netty;

import coloryr.colormirai.plugin.PluginUtils;
import coloryr.colormirai.plugin.ThePlugin;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    public static final Map<ChannelHandlerContext, NettyThread> contexts = new ConcurrentHashMap<>();
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        contexts.put(ctx,  PluginUtils.addPlugin(ctx));
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
    public void channelUnregistered(ChannelHandlerContext ctx){

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        //发生异常，关闭通道
        ctx.close();
    }
}
