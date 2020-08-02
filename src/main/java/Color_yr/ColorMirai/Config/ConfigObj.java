package Color_yr.ColorMirai.Config;

public class ConfigObj {
    private String QQ;
    private String Password;
    private String IP;
    private int Port;

    public ConfigObj()
    {
        QQ = "请填写你的QQ号";
        Password = "请填写你的密码";
        IP = "0.0.0.0";
        Port = 23333;
    }

    public String getQQ() {
        return QQ;
    }

    public String getPassword() {
        return Password;
    }

    public String getIP() {
        return IP;
    }

    public int getPort() {
        return Port;
    }
}
