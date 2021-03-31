package Color_yr.ColorMirai;

import Color_yr.ColorMirai.Config.ConfigObj;
import Color_yr.ColorMirai.Config.ConfigRead;
import Color_yr.ColorMirai.Plugin.Download.DownloadUtils;
import Color_yr.ColorMirai.Plugin.PluginSocket.IPluginSocket;
import Color_yr.ColorMirai.Plugin.PluginSocket.MySocketServer;
import Color_yr.ColorMirai.Plugin.PluginSocket.MyWebSocket;
import Color_yr.ColorMirai.Plugin.PluginUtils;
import Color_yr.ColorMirai.Plugin.ThePlugin;
import Color_yr.ColorMirai.Robot.BotStart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Scanner;

public class ColorMiraiMain {
    public static final Logger logger = LogManager.getLogger("ColorMirai");
    public static final Base64.Decoder decoder = Base64.getDecoder();
    public static String RunDir;
    public static ConfigObj Config;
    public static Charset SendCharset;
    public static Charset ReadCharset;
    public static IPluginSocket PluginSocket;

    public static void main(String[] args) {
        RunDir = System.getProperty("user.dir") + "/";
        logger.info("正在启动");
        if (ConfigRead.ReadStart(RunDir)) {
            logger.info("请修改配置文件后重新启动");
            return;
        }
        PluginUtils.init();
        logger.info("初始化完成");

        if (!BotStart.Start()) {
            logger.error("机器人启动失败");
            return;
        }

        if (Config.SocketType == 1) {
            PluginSocket = new MyWebSocket();
        } else {
            PluginSocket = new MySocketServer();
        }

        if (!PluginSocket.pluginServerStart()) {
            logger.error("socket启动失败");
            return;
        }

        DownloadUtils.start();

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
        PluginSocket.pluginServerStop();
        BotStart.stop();
        System.exit(0);
    }
}
