package coloryr.colormirai;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.UUID;

public class Utils {
    public static byte[] getUrlBytes(String url) {
        try {
            URL url1 = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(1500);
            connection.setReadTimeout(6000);
            connection.connect();
            byte[] buffer = null;
            if (connection.getResponseCode() == 200) {
                int length = connection.getContentLength();
                buffer = new byte[length];
                InputStream is = connection.getInputStream();
                int i = 0;
                while (length > 0) {
                    i += is.read(buffer, i, length);
                    if (i == -1)
                        break;
                    length -= i;
                }
                is.close();
            }
            connection.disconnect();// 关闭远程连接
            return buffer;
        } catch (Exception e) {
            ColorMiraiMain.logger.error("获取图片发生错误", e);
        }
        return null;
    }

    public static String toUHexString(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length);
        String sTemp;
        for (byte datum : data) {
            sTemp = Integer.toHexString(0xFF & datum);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    public static byte[] hexToByteArray(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            //奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            //偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    public static File saveFile(byte[] datas) {
        try {
            UUID uuid = UUID.randomUUID();
            File file = new File(ColorMiraiMain.tempDir, uuid + ".tmp");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(datas);
            fileOutputStream.close();
            return file;
        } catch (Exception e) {
            ColorMiraiMain.logger.error("保存发生错误", e);
        }
        return null;
    }

    public static byte[] getFileBytes(String url) {
        try {
            File file = new File(url);
            if (!file.exists())
                return null;
            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            return buffer;
        } catch (Exception e) {
            ColorMiraiMain.logger.error("获取文件发生错误", e);
        }
        return null;
    }

    public static byte[] base64D(String data) {
        try {
            return Base64.getDecoder().decode(data);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("编码转换发生错误", e);
        }
        return null;
    }
}
