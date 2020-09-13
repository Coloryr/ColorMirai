package Color_yr.ColorMirai.Config;

import java.util.ArrayList;
import java.util.List;

public class ConfigObj {
    public List<QQsObj> QQs;
    public int Port;
    public int Type;
    public boolean escapeSelf;
    public long MaxList;

    public ConfigObj() {
        QQs = new ArrayList<>() {{
            add(new QQsObj());
        }};
        escapeSelf = true;
        Port = 23333;
        Type = 0;
        MaxList = 100000;
    }
}
