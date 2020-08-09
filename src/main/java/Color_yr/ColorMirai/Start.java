package Color_yr.ColorMirai;

import Color_yr.ColorMirai.Config.ConfigObj;
import Color_yr.ColorMirai.Config.ConfigRead;
import Color_yr.ColorMirai.Socket.SocketServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.Scanner;

public class Start {
    public static final Logger logger = LogManager.getLogger("ColorMirai");
    private static final Date date = new Date();
    public static String RunDir;
    public static ConfigObj Config;

    public static void main(String[] args) {
        RunDir = System.getProperty("user.dir") + "\\";
        logger.info("正在启动");
        if (ConfigRead.ReadStart(RunDir)) {
            return;
        }
        logger.info("初始化完成");
        if (!BotStart.Start())
            return;
        if (!SocketServer.start())
            return;

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
