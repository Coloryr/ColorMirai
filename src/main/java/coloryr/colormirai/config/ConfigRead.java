package coloryr.colormirai.config;

import coloryr.colormirai.ColorMiraiMain;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class ConfigRead {
    private static File configFile;

    public static boolean readStart(String local) {
        try {
            configFile = new File(local + "config.json");
            if (!configFile.exists()) {
                configFile.createNewFile();
                ColorMiraiMain.config = new ConfigObj();
                save();
                return true;
            } else {
                InputStreamReader reader = new InputStreamReader(
                        Files.newInputStream(configFile.toPath()), StandardCharsets.UTF_8);
                BufferedReader bf = new BufferedReader(reader);
                char[] buf = new char[4096];
                int length;
                StringBuilder data = new StringBuilder();
                while ((length = bf.read(buf)) != -1) {
                    data.append(new String(buf, 0, length));
                }
                ColorMiraiMain.config = JSON.parseObject(data.toString(), ConfigObj.class);
                if (ColorMiraiMain.config.qqList == null) {
                    ColorMiraiMain.config = new ConfigObj();
                    save();
                }
                bf.close();
                reader.close();
            }

            if (ColorMiraiMain.config.readEncoding == null || ColorMiraiMain.config.readEncoding.isEmpty()) {
                ColorMiraiMain.readCharset = StandardCharsets.UTF_8;
            } else {
                try {
                    ColorMiraiMain.readCharset = Charset.forName(ColorMiraiMain.config.readEncoding);
                } catch (Exception e) {
                    ColorMiraiMain.readCharset = StandardCharsets.UTF_8;
                }
            }

            if (ColorMiraiMain.config.sendEncoding == null || ColorMiraiMain.config.sendEncoding.isEmpty()) {
                ColorMiraiMain.sendCharset = StandardCharsets.UTF_8;
            } else {
                try {
                    ColorMiraiMain.sendCharset = Charset.forName(ColorMiraiMain.config.sendEncoding);
                } catch (Exception e) {
                    ColorMiraiMain.sendCharset = StandardCharsets.UTF_8;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void save() {
        try {
            FileOutputStream out = new FileOutputStream(configFile);
            OutputStreamWriter write = new OutputStreamWriter(
                    out, StandardCharsets.UTF_8);
            write.write(JSON.toJSONString(ColorMiraiMain.config, SerializerFeature.PrettyFormat));
            write.close();
            out.close();
        } catch (Exception e) {
            ColorMiraiMain.logger.error("配置文件保存失败", e);
            e.printStackTrace();
        }
    }
}
