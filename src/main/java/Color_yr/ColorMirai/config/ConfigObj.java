package Color_yr.ColorMirai.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigObj {
    public List<QQsObj> QQs;
    public boolean Pack;
    public int SocketPort;
    public int WebSocketPort;
    public int HttpPort;
    public boolean escapeSelf;
    public long MaxList;
    public String SendEncoding;
    public String ReadEncoding;
    public boolean AutoReconnect;
    public int HighwayUpload;
    public String authKey;
    public long authTime;
    public int cacheSize;

    public ConfigObj() {
        QQs = new ArrayList<QQsObj>() {{
            add(new QQsObj());
        }};
        Pack = true;
        escapeSelf = true;
        SocketPort = 23333;
        WebSocketPort = 23334;
        HttpPort = 23335;
        MaxList = 100000;
        SendEncoding = "UTF-8";
        ReadEncoding = "UTF-8";
        AutoReconnect = true;
        HighwayUpload = 10;
        authKey = "123456789";
        authTime = 1800;
        cacheSize = 100;
    }
}
