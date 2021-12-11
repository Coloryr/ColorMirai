package coloryr.colormirai;

import coloryr.colormirai.config.ConfigObj;
import coloryr.colormirai.config.ConfigRead;
import coloryr.colormirai.download.DownloadUtils;
import coloryr.colormirai.plugin.socket.PluginSocketServer;
import coloryr.colormirai.plugin.socket.PluginUtils;
import coloryr.colormirai.plugin.socket.PluginWebSocketServer;
import coloryr.colormirai.robot.BotStart;
import net.mamoe.mirai.console.MiraiConsoleImplementation;
import net.mamoe.mirai.console.command.CommandManager;
import net.mamoe.mirai.console.terminal.ConsoleTerminalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Random;

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
        MiraiConsoleImplementation terminal = new MiraiTerminal();
        MiraiConsoleImplementation.start(terminal);

        if (ConsoleTerminalSettings.noConsole) {
            return;
        }

        CommandManager.INSTANCE.registerCommand(ColorMiraiCommand.INSTANCE, false);
        MyMiraiConsoleKt.startupConsoleThread();
    }

    public static void stop() {
        logger.info("正在关闭");
        DownloadUtils.stop();
        socket.pluginServerStop();
        webSocket.pluginServerStop();
        BotStart.stop();
        System.exit(0);
    }
}
