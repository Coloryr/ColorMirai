package Color_yr.ColorMirai.Config;

public class ConfigObj {
    private long QQ;
    private String Password;
    private int Port;

    public ConfigObj() {
        QQ = 0;
        Password = "请填写你的密码";
        Port = 23333;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setPort(int port) {
        Port = port;
    }

    public void setQQ(long QQ) {
        this.QQ = QQ;
    }

    public long getQQ() {
        return QQ;
    }

    public String getPassword() {
        return Password;
    }

    public int getPort() {
        return Port;
    }
}
