package coloryr.colormirai.config;

import net.mamoe.mirai.utils.BotConfiguration;

public class QQsObj {
    public long qq;
    public String password;
    public BotConfiguration.MiraiProtocol loginType;
    public String info;

    public QQsObj() {
        qq = 0;
        password = "请填写你的密码";
        loginType = BotConfiguration.MiraiProtocol.ANDROID_PHONE;
        info = "device.json";
    }
}
