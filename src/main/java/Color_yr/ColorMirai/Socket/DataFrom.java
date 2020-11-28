package Color_yr.ColorMirai.Socket;

import java.util.HashMap;
import java.util.Map;

public class DataFrom {
    public static Map<String, String> parse(String data) {
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
