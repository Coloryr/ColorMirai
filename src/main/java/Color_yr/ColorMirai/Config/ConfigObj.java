package Color_yr.ColorMirai.Config;

import java.util.ArrayList;
import java.util.List;

public class ConfigObj {
    public List<QQsObj> QQs;
    public boolean Pack;
    public int Port;
    public boolean escapeSelf;
    public long MaxList;
    public String SendEncoding;
    public String ReadEncoding;
    public int SocketType;
    public boolean AutoReconnect;
    public int HighwayUpload;

    public ConfigObj() {
        QQs = new ArrayList<QQsObj>() {{
            add(new QQsObj());
        }};
        Pack = true;
        escapeSelf = true;
        Port = 23333;
        MaxList = 100000;
        SendEncoding = "UTF-8";
        ReadEncoding = "UTF-8";
        SocketType = 0;
        AutoReconnect = true;
        HighwayUpload = 10;
    }
}
