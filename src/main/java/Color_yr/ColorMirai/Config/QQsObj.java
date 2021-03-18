package Color_yr.ColorMirai.Config;

import net.mamoe.mirai.utils.BotConfiguration;

public class QQsObj {
    public long QQ;
    public String Password;
    public BotConfiguration.MiraiProtocol LoginType;
    public String Info;

    public QQsObj() {
        QQ = 0;
        Password = "请填写你的密码";
        LoginType = BotConfiguration.MiraiProtocol.ANDROID_PHONE;
        Info = "info.json";
    }
}
