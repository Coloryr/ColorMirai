package Color_yr.ColorMirai;

import Color_yr.ColorMirai.Config.ConfigObj;
import Color_yr.ColorMirai.Config.ConfigRead;
import Color_yr.ColorMirai.Socket.SocketServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class Start {
    public static final Logger logger = LogManager.getLogger("ColorMirai");
    public static String RunDir;
    public static ConfigObj Config;

    public static String getString(String a, String b, String c) {
        int x = a.indexOf(b) + b.length();
        int y = a.indexOf(c);
        char[] data = a.toCharArray();
        if (data[y - 1] == '"')
            y = a.indexOf(c, y + 1);
        return a.substring(x, y);
    }

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

        var scanner = new Scanner(System.in);
        while (true) {
            String data = scanner.nextLine();
            switch (data) {
                case "stop":
                    logger.info("正在停止");
                    SocketServer.stop();
                    break;
            }
        }
    }
}
