package Color_yr.ColorMirai;

import Color_yr.ColorMirai.config.ConfigObj;
import Color_yr.ColorMirai.config.ConfigRead;
import Color_yr.ColorMirai.config.SessionObj;
import Color_yr.ColorMirai.plugin.download.DownloadUtils;
import Color_yr.ColorMirai.plugin.ISocket;
import Color_yr.ColorMirai.plugin.http.MyHttpServer;
import Color_yr.ColorMirai.plugin.http.SessionManager;
import Color_yr.ColorMirai.plugin.socket.MySocketServer;
import Color_yr.ColorMirai.plugin.socket.MyWebSocket;
import Color_yr.ColorMirai.plugin.PluginUtils;
import Color_yr.ColorMirai.plugin.ThePlugin;
import Color_yr.ColorMirai.robot.BotStart;
import io.github.mzdluo123.silk4j.AudioUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;

public class ColorMiraiMain {
    public static final Logger logger = LogManager.getLogger("ColorMirai");
    public static final Base64.Decoder decoder = Base64.getDecoder();
    public static final Random random = new Random();
    public static String RunDir;
    public static ConfigObj Config;
    public static SessionObj Sessions;
    public static Charset SendCharset;
    public static Charset ReadCharset;
    private static ISocket WebSocket;
    private static ISocket Socket;
    private static ISocket Http;

    public static void main(String[] args) {
        RunDir = System.getProperty("user.dir") + "/";
        logger.info("正在启动");
        if (ConfigRead.ReadStart(RunDir)) {
            logger.info("请修改配置文件后重新启动");
            return;
        }

        PluginUtils.init();
        DownloadUtils.start();
        try {
            AudioUtils.init();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!BotStart.Start()) {
            logger.error("机器人启动失败");
            return;
        }

        SessionManager.start();

        logger.info("初始化完成");

        WebSocket = new MyWebSocket();
        Socket = new MySocketServer();
        Http = new MyHttpServer();

        if (!Socket.pluginServerStart()) {
            logger.error("socket启动失败");
            return;
        }

        if (!WebSocket.pluginServerStart()) {
            logger.error("websocket启动失败");
            return;
        }
        if (!Http.pluginServerStart()) {
            logger.error("mirai-http-api启动失败");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                String data = scanner.nextLine();
                String[] arg = data.split(" ");
                switch (arg[0]) {
                    case "help":
                        logger.info("插件帮助");
                        logger.info("stop 关闭机器人");
                        logger.info("list 获取连接的插件列表");
                        logger.info("close [插件] 断开插件连接");
                        break;
                    case "list":
                        logger.info("插件列表");
                        for (ThePlugin item : PluginUtils.getAll()) {
                            logger.info(item.getName() + " 注册的包：" + item.getReg());
                        }
                        break;
                    case "close":
                        if (arg.length < 2) {
                            logger.warn("错误的指令");
                            break;
                        }
                        if (!PluginUtils.havePlugin(arg[1])) {
                            logger.warn("没有插件：" + arg[1]);
                            break;
                        }
                        PluginUtils.removePlugin(arg[1]);
                        logger.info("正在断开插件" + arg[1]);
                        break;
                    case "stop":
                        logger.info("正在停止");
                        stop();
                        return;
                }
            }
        } catch (Exception e) {
            logger.error("控制台发生错误", e);
        }
    }

    public static void stop() {
        DownloadUtils.stop();
        SessionManager.stop();
        Socket.pluginServerStop();
        WebSocket.pluginServerStop();
        Http.pluginServerStop();
        BotStart.stop();
        System.exit(0);
    }
}
