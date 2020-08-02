package Color_yr.ColorMirai;

import Color_yr.ColorMirai.Config.ConfigObj;
import Color_yr.ColorMirai.Config.ConfigRead;
import Color_yr.ColorMirai.Config.Logs;

import java.io.File;

public class Start {
    public static String RunDir;
    public static Logs log;
    public static ConfigObj Config;

    private static final String[] Libs = new String[]
            {
                    "gson-2.8.6.jar",
                    "mirai-core-1.1.3.jar",
                    "mirai-core-qqandroid-1.1.3.jar"
            };

    public static void main(String[] args) {
        System.out.println("正在检查库文件");
        RunDir = System.getProperty("user.dir") + "\\";
        for (String item : Libs) {
            if (!new File(RunDir + item).exists()) {
                System.out.println("库文件不齐");
                return;
            }
        }
        log = new Logs(RunDir);
        ConfigRead.ReadStart(RunDir);
    }
}
