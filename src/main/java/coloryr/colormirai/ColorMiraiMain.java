package coloryr.colormirai;

import coloryr.colormirai.config.ConfigObj;
import coloryr.colormirai.config.ConfigRead;
import coloryr.colormirai.config.SessionObj;
import coloryr.colormirai.plugin.one_bot.OneBotServer;
import coloryr.colormirai.plugin.socket.PluginUtils;
import coloryr.colormirai.plugin.socket.ThePlugin;
import coloryr.colormirai.download.DownloadUtils;
import coloryr.colormirai.plugin.mirai_http_api.MiraiHttpApiServer;
import coloryr.colormirai.plugin.socket.PluginSocketServer;
import coloryr.colormirai.plugin.socket.PluginWebSocketServer;
import coloryr.colormirai.robot.BotStart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;

public class ColorMiraiMain {
    public static final Logger logger = LogManager.getLogger("ColorMirai");
    public static final Base64.Decoder decoder = Base64.getDecoder();
    public static final Random random = new Random();
    public static String runDir;
    public static String tempDir;
    public static ConfigObj config;
    public static SessionObj sessions;
    public static Charset sendCharset;
    public static Charset readCharset;
    private static PluginWebSocketServer webSocket;
    private static PluginSocketServer socket;
    private static MiraiHttpApiServer httpApiServer;
    private static OneBotServer oneBotServer;

    public static void main(String[] args) {
        runDir = System.getProperty("user.dir") + "/";
        tempDir = runDir + "temp/";
        File dir = new File(tempDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        logger.info("正在启动");
        if (ConfigRead.readStart(runDir)) {
            logger.info("请修改配置文件后重新启动");
            return;
        }

        PluginUtils.init();
        DownloadUtils.start();

        if (!BotStart.start()) {
            logger.error("机器人启动失败");
            return;
        }

        logger.info("初始化完成");

        webSocket = new PluginWebSocketServer();
        socket = new PluginSocketServer();
        httpApiServer = new MiraiHttpApiServer();
        oneBotServer = new OneBotServer();

        if (!socket.pluginServerStart()) {
            logger.error("socket启动失败");
            return;
        }

        if (!webSocket.pluginServerStart()) {
            logger.error("websocket启动失败");
            return;
        }
        if (!httpApiServer.pluginServerStart()) {
            logger.error("mirai-http-api启动失败");
            return;
        }
        if (!oneBotServer.pluginServerStart()) {
            logger.error("mirai-http-api启动失败");
            return;
        }

        if (config.noInput) {
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
        socket.pluginServerStop();
        webSocket.pluginServerStop();
        httpApiServer.pluginServerStop();
        oneBotServer.pluginServerStop();
        BotStart.stop();
        System.exit(0);
    }
}
