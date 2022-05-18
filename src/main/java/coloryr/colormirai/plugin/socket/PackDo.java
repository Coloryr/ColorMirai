package coloryr.colormirai.plugin.socket;

import coloryr.colormirai.ColorMiraiMain;
import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class PackDo {
    public static byte[] buildPack(Object data, int index) {
        String str = JSON.toJSONString(data) + " ";
        byte[] temp = str.getBytes(ColorMiraiMain.sendCharset);
        temp[temp.length - 1] = (byte) index;
        return temp;
    }

    public static Map<String, String> parseDataFromPack(String data) {
        Map<String, String> map = new HashMap<>();
        String[] temp = data.split("&");
        for (String item : temp) {
            String[] temp1 = item.split("=");
            if (temp1.length != 2)
                continue;
            map.put(temp1[0], temp1[1]);
        }
        return map;
    }
}
