package Color_yr.ColorMirai.Config;

import Color_yr.ColorMirai.Start;
import com.alibaba.fastjson.JSON;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ConfigRead {
    private static File ConfigFile;

    public static boolean ReadStart(String local) {
        try {
            ConfigFile = new File(local + "MainConfig.json");
            if (!ConfigFile.exists()) {
                ConfigFile.createNewFile();
                Start.Config = new ConfigObj();
                Save();
                return true;
            } else {
                var reader = new InputStreamReader(
                        new FileInputStream(ConfigFile), StandardCharsets.UTF_8);
                var bf = new BufferedReader(reader);
                var buf = new char[1024];
                int length;
                var data = new StringBuilder();
                while ((length = bf.read(buf)) != -1) {
                    data.append(new String(buf, 0, length));
                }
                Start.Config = JSON.parseObject(data.toString(), ConfigObj.class);
                if (Start.Config.QQs == null) {
                    Start.Config = new ConfigObj();
                    Save();
                }
                bf.close();
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void Save() {
        try {
            var out = new FileOutputStream(ConfigFile);
            var write = new OutputStreamWriter(
                    out, StandardCharsets.UTF_8);
            write.write(JSON.toJSONString(Start.Config));
            write.close();
            out.close();
        } catch (Exception e) {
            Start.logger.error("配置文件保存失败", e);
            e.printStackTrace();
        }
    }
}
