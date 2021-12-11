package coloryr.colormirai;

import coloryr.colormirai.config.ConfigObj;
import coloryr.colormirai.config.ConfigRead;
import coloryr.colormirai.plugin.socket.PluginUtils;
import coloryr.colormirai.plugin.socket.ThePlugin;
import coloryr.colormirai.download.DownloadUtils;
import coloryr.colormirai.plugin.socket.PluginSocketServer;
import coloryr.colormirai.plugin.socket.PluginWebSocketServer;
import coloryr.colormirai.robot.BotStart;
import net.mamoe.mirai.console.MiraiConsoleImplementation;
import net.mamoe.mirai.console.terminal.MiraiConsoleImplementationTerminal;
import net.mamoe.mirai.console.terminal.MiraiConsoleTerminalLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;

public class ColorMiraiMain {
    public static final String version = "3.8.0";
    public static final Logger logger = LogManager.getLogger("ColorMirai");
    public static final Base64.Decoder decoder = Base64.getDecoder();
    public static final Random random = new Random();
    public static String runDir;
    public static String tempDir;
    public static ConfigObj config;
    public static Charset sendCharset;
    public static Charset readCharset;
    private static PluginWebSocketServer webSocket;
    private static PluginSocketServer socket;

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

        if (!socket.pluginServerStart()) {
            logger.error("socket启动失败");
            return;
        }

        if (!webSocket.pluginServerStart()) {
            logger.error("websocket启动失败");
            return;
        }
        MiraiConsoleImplementationTerminal terminal = new MiraiTerminal();
        MiraiConsoleTerminalLoader.INSTANCE.startAsDaemon(terminal);

        if (config.noInput) {
            return;
        }
    }

    public static String command(String data) {
        try {
            String[] arg = data.split(" ");
            StringBuilder builder = new StringBuilder();
            if (arg.length < 2)
                builder.append("错误的指令");
            else
                switch (arg[1]) {
                    case "help":
                        builder.append("插件帮助");
                        builder.append("stop 关闭机器人");
                        builder.append("list 获取连接的插件列表");
                        builder.append("close [插件] 断开插件连接");
                        break;
                    case "list":
                        builder.append("插件列表");
                        for (ThePlugin item : PluginUtils.getAll()) {
                            builder.append(item.getName()).append(" 注册的包：").append(item.getReg());
                        }
                        break;
                    case "close":
                        if (arg.length < 3) {
                            builder.append("错误的指令");
                            break;
                        }
                        if (!PluginUtils.havePlugin(arg[1])) {
                            builder.append("没有插件：").append(arg[1]);
                            break;
                        }
                        PluginUtils.removePlugin(arg[1]);
                        builder.append("正在断开插件").append(arg[1]);
                        break;
                }
            return builder.toString();
        } catch (Exception e) {
            logger.error("控制台发生错误", e);
        }
        return "";
    }

    public static void stop() {
        DownloadUtils.stop();
        socket.pluginServerStop();
        webSocket.pluginServerStop();
        BotStart.stop();
        System.exit(0);
    }
}
