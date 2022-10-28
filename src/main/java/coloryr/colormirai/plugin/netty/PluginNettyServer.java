package coloryr.colormirai.plugin.netty;

import coloryr.colormirai.ColorMiraiMain;
import coloryr.colormirai.plugin.PluginUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PluginNettyServer {
    private static ChannelFuture channelFuture;
    //创建两个线程组 boosGroup、workerGroup
    private static final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private static final EventLoopGroup workerGroup = new NioEventLoopGroup();

    public static void start() {
        try {
            //创建服务端的启动对象，设置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            //设置两个线程组boosGroup和workerGroup
            bootstrap.group(bossGroup, workerGroup)
                    //设置服务端通道实现类型
                    .channel(NioServerSocketChannel.class)
                    //设置线程队列得到连接个数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //设置保持活动连接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //使用匿名内部类的形式初始化通道对象
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(@NotNull SocketChannel socketChannel) {
                            //给pipeline管道设置处理器
                            socketChannel.pipeline()
                                    .addLast(new LengthFieldPrepender(4))
                                    .addLast(new LengthFieldBasedFrameDecoder(1024 * ColorMiraiMain.config.maxNettyPackSize, 0, 4, 0, 4))
                                    .addLast(new ServerHandler());
                        }
                    });//给workerGroup的EventLoop对应的管道设置处理器
            //绑定端口号，启动服务端
            channelFuture = bootstrap.bind(ColorMiraiMain.config.nettyPort);
            ColorMiraiMain.logger.info("netty服务器已启动" + ColorMiraiMain.config.nettyPort);
            //对关闭通道进行监听
        } catch (Exception e) {
            ColorMiraiMain.logger.error("netty服务器启动失败", e);
        }
    }

    public static void stop() {
        try {
            channelFuture.channel().close().sync();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        } catch (Exception e) {
            ColorMiraiMain.logger.error("netty服务器关闭失败", e);
        }
    }

    public static class ServerHandler extends ChannelInboundHandlerAdapter {
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
}
