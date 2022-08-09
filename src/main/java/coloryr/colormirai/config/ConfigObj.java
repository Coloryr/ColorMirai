package coloryr.colormirai.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigObj {
    public List<QQsObj> qqList;
    public boolean pack;
    public int nettyPort;
    public int webSocketPort;
    public boolean escapeSelf;
    public long maxList;
    public String sendEncoding;
    public String readEncoding;
    public boolean autoReconnect;
    public int highwayUpload;
    public int cacheSize;
    public boolean noInput;
    public boolean debug;
    public int maxNettyPackSize;

    public ConfigObj() {
        qqList = new ArrayList<QQsObj>() {{
            add(new QQsObj());
        }};
        pack = true;
        escapeSelf = true;
        webSocketPort = 23334;
        nettyPort = 23335;
        maxList = 100000;
        sendEncoding = "UTF-8";
        readEncoding = "UTF-8";
        autoReconnect = true;
        highwayUpload = 10;
        cacheSize = 100;
        noInput = false;
        debug = false;
        maxNettyPackSize = 500;
    }
}
