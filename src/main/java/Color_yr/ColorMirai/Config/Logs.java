package Color_yr.ColorMirai.Config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class Logs {
    private final Date date = new Date();
    public File file;
    private FileWriter fw;
    private PrintWriter pw;

    public Logs(String local) {
        file = new File(local + "logs.log");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                System.out.println("日志文件创建失败");
                e.printStackTrace();
            }
        }
    }

    public void logWrite(String text) {
        try {
            if (fw == null)
                fw = new FileWriter(file, true);
            String year = String.format("%tF", date);
            String time = String.format("%tT", date);
            String write = "[" + year + "]" + "[" + time + "]" + text;
            if (pw == null)
                pw = new PrintWriter(fw);
            pw.println(write);
            pw.flush();
            fw.flush();

        } catch (IOException e) {
            System.out.println("日志文件写入失败");
            e.printStackTrace();
        }
    }

    public void stop() {
        if (fw != null)
            pw.close();
        if (fw != null) {
            try {
                fw.close();
            } catch (IOException e) {
                System.out.println("日志文件写入失败");
                e.printStackTrace();
            }
        }
    }
}
