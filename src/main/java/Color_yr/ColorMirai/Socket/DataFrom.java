package Color_yr.ColorMirai.Socket;

import java.util.HashMap;
import java.util.Map;

public class DataFrom {
    public static Map<String, String> parse(String data) {
        var map = new HashMap<String, String>();
        var temp = data.split("&");
        for (var item : temp) {
            var temp1 = item.split("=");
            if (temp1.length != 2)
                continue;
            map.put(temp1[0], temp1[1]);
        }
        return map;
    }
}
