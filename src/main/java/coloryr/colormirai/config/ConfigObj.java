package coloryr.colormirai.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigObj {
    public List<QQsObj> qqList;
    public boolean pack;
    public int socketPort;

    public int nettyPort;
    public int webSocketPort;
    public boolean escapeSelf;
    public long maxList;
    public String sendEncoding;
    public String readEncoding;
    public boolean autoReconnect;
    public int highwayUpload;
    public String miraiHttpAuthKey;
    public long authTime;
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
        socketPort = 23333;
        webSocketPort = 23334;
        nettyPort = 23335;
        maxList = 100000;
        sendEncoding = "UTF-8";
        readEncoding = "UTF-8";
        autoReconnect = true;
        highwayUpload = 10;
        miraiHttpAuthKey = "123456789";
        authTime = 1800;
        cacheSize = 100;
        noInput = false;
        debug = false;
        maxNettyPackSize = 500;
    }
}
