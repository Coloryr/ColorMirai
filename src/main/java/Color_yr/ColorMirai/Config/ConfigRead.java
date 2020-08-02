package Color_yr.ColorMirai.Config;

import Color_yr.ColorMirai.Start;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ConfigRead {
    private static File ConfigFile;

    public static void ReadStart(String local) {
        try {
            ConfigFile = new File(local + "MainConfig.json");
            if (!ConfigFile.exists()) {
                ConfigFile.createNewFile();
                Save();
            } else {
                InputStreamReader reader = new InputStreamReader(
                        new FileInputStream(ConfigFile), StandardCharsets.UTF_8);
                BufferedReader bf = new BufferedReader(reader);
                Start.Config = new Gson().fromJson(bf, ConfigObj.class);
                if (Start.Config.getQQ() == null) {
                    Start.Config = new ConfigObj();
                    Save();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Save() {
        try {
            FileOutputStream out = new FileOutputStream(ConfigFile);
            OutputStreamWriter write = new OutputStreamWriter(
                    out, StandardCharsets.UTF_8);
            write.write(new Gson().toJson(Start.Config));
            write.close();
            out.close();
        } catch (Exception e) {
            System.out.println("配置文件保存失败");
            e.printStackTrace();
        }
    }
}
