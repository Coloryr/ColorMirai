package Color_yr.ColorMirai;

import Color_yr.ColorMirai.Config.ConfigObj;
import Color_yr.ColorMirai.Config.ConfigRead;
import Color_yr.ColorMirai.Robot.BotStart;
import Color_yr.ColorMirai.Socket.Plugins;
import Color_yr.ColorMirai.Socket.SocketServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.Charset;
import java.util.Scanner;

public class Start {
    public static final Logger logger = LogManager.getLogger("ColorMirai");
    public static String RunDir;
    public static ConfigObj Config;
    public static Charset SendCharset;
    public static Charset ReadCharset;

    public static void main(String[] args) {
        RunDir = System.getProperty("user.dir") + "/";
        logger.info("正在启动");
        if (ConfigRead.ReadStart(RunDir)) {
            logger.info("请修改配置文件后重新启动");
            return;
        }
        logger.info("初始化完成");

        if (!BotStart.Start()) {
            logger.error("机器人启动失败");
            return;
        }
        if (!SocketServer.start()) {
            logger.error("socket启动失败");
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
                        logger.info("输入 stop 关闭机器人");
                        logger.info("输入 list 获取连接的插件列表");
                        logger.info("输入 close 插件 断开插件连接");
                        break;
                    case "list":
                        logger.info("插件列表");
                        for (Plugins item : SocketServer.PluginList.values()) {
                            logger.info(item.getName() + " 注册的包：" + item.getReg());
                        }
                        break;
                    case "close":
                        if (arg.length < 2) {
                            logger.warn("错误的指令");
                            break;
                        }
                        if (!SocketServer.havePlugin(arg[1])) {
                            logger.warn("没有插件：" + arg[1]);
                            break;
                        }
                        SocketServer.removePlugin(arg[1]);
                        logger.info("正在断开插件" + arg[1]);
                        break;
                    case "stop":
                        logger.info("正在停止");
                        SocketServer.stop();
                        BotStart.stop();
                        Thread.sleep(500);
                        System.exit(0);
                        return;
                }
            }
        } catch (Exception e) {
            logger.error("控制台发生错误", e);
        }
    }
}
