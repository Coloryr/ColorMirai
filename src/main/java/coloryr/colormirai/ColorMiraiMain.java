package coloryr.colormirai;

import coloryr.colormirai.config.ConfigObj;
import coloryr.colormirai.config.ConfigRead;
import coloryr.colormirai.download.DownloadUtils;
import coloryr.colormirai.plugin.PluginUtils;
import coloryr.colormirai.plugin.netty.PluginNettyServer;
import coloryr.colormirai.plugin.socket.PluginSocketServer;
import coloryr.colormirai.plugin.websocket.PluginWebSocketServer;
import coloryr.colormirai.robot.BotStart;
import net.mamoe.mirai.console.MiraiConsoleImplementation;
import net.mamoe.mirai.console.command.CommandManager;
import net.mamoe.mirai.console.terminal.ConsoleTerminalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.charset.Charset;

public class ColorMiraiMain {
    public static final String version = "4.0.3";
    public static final Logger logger = LogManager.getLogger("ColorMirai");
    public static String runDir;
    public static String tempDir;
    public static ConfigObj config;
    public static Charset sendCharset;
    public static Charset readCharset;

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

        PluginSocketServer.start();
        PluginWebSocketServer.start();
        PluginNettyServer.start();

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
        PluginUtils.stop();
        DownloadUtils.stop();
        PluginSocketServer.stop();
        PluginWebSocketServer.stop();
        PluginNettyServer.stop();
        BotStart.stop();
        System.exit(0);
    }
}
