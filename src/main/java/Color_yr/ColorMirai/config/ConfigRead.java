package Color_yr.ColorMirai.config;

import Color_yr.ColorMirai.ColorMiraiMain;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ConfigRead {
    private static File ConfigFile;
    private static File SessionFile;

    public static boolean ReadStart(String local) {
        try {
            ConfigFile = new File(local + "MainConfig.json");
            SessionFile = new File(local + "Session.json");
            if (!ConfigFile.exists()) {
                ConfigFile.createNewFile();
                ColorMiraiMain.Config = new ConfigObj();
                Save();
                return true;
            } else {
                InputStreamReader reader = new InputStreamReader(
                        new FileInputStream(ConfigFile), StandardCharsets.UTF_8);
                BufferedReader bf = new BufferedReader(reader);
                char[] buf = new char[4096];
                int length;
                StringBuilder data = new StringBuilder();
                while ((length = bf.read(buf)) != -1) {
                    data.append(new String(buf, 0, length));
                }
                ColorMiraiMain.Config = JSON.parseObject(data.toString(), ConfigObj.class);
                if (ColorMiraiMain.Config.QQs == null) {
                    ColorMiraiMain.Config = new ConfigObj();
                    Save();
                }
                bf.close();
                reader.close();
            }

            if (!SessionFile.exists()) {
                SessionFile.createNewFile();
                ColorMiraiMain.Sessions = new SessionObj();
                SaveSession();
                return true;
            } else {
                InputStreamReader reader = new InputStreamReader(
                        new FileInputStream(SessionFile), StandardCharsets.UTF_8);
                BufferedReader bf = new BufferedReader(reader);
                char[] buf = new char[4096];
                int length;
                StringBuilder data = new StringBuilder();
                while ((length = bf.read(buf)) != -1) {
                    data.append(new String(buf, 0, length));
                }
                ColorMiraiMain.Sessions = JSON.parseObject(data.toString(), SessionObj.class);
                if (ColorMiraiMain.Sessions == null || ColorMiraiMain.Sessions.allSession == null) {
                    ColorMiraiMain.Sessions = new SessionObj();
                    SaveSession();
                }
                bf.close();
                reader.close();
            }

            if (ColorMiraiMain.Config.ReadEncoding == null || ColorMiraiMain.Config.ReadEncoding.isEmpty()) {
                ColorMiraiMain.ReadCharset = StandardCharsets.UTF_8;
            } else {
                try {
                    ColorMiraiMain.ReadCharset = Charset.forName(ColorMiraiMain.Config.ReadEncoding);
                } catch (Exception e) {
                    ColorMiraiMain.ReadCharset = StandardCharsets.UTF_8;
                }
            }

            if (ColorMiraiMain.Config.SendEncoding == null || ColorMiraiMain.Config.SendEncoding.isEmpty()) {
                ColorMiraiMain.SendCharset = StandardCharsets.UTF_8;
            } else {
                try {
                    ColorMiraiMain.SendCharset = Charset.forName(ColorMiraiMain.Config.SendEncoding);
                } catch (Exception e) {
                    ColorMiraiMain.SendCharset = StandardCharsets.UTF_8;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void Save() {
        try {
            FileOutputStream out = new FileOutputStream(ConfigFile);
            OutputStreamWriter write = new OutputStreamWriter(
                    out, StandardCharsets.UTF_8);
            write.write(JSON.toJSONString(ColorMiraiMain.Config, SerializerFeature.PrettyFormat));
            write.close();
            out.close();
        } catch (Exception e) {
            ColorMiraiMain.logger.error("配置文件保存失败", e);
            e.printStackTrace();
        }
    }

    public static void SaveSession() {
        try {
            FileOutputStream out = new FileOutputStream(SessionFile);
            OutputStreamWriter write = new OutputStreamWriter(
                    out, StandardCharsets.UTF_8);
            write.write(JSON.toJSONString(ColorMiraiMain.Sessions, SerializerFeature.PrettyFormat));
            write.close();
            out.close();
        } catch (Exception e) {
            ColorMiraiMain.logger.error("配置文件保存失败", e);
            e.printStackTrace();
        }
    }
}
