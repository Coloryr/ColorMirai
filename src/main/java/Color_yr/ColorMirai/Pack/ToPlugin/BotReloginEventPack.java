package Color_yr.ColorMirai.Pack.ToPlugin;

/*
16 [机器人]主动或被动重新登录（事件）
message：原因消息
 */
public class BotReloginEventPack {
    private String message;

    public BotReloginEventPack(String message) {
        this.message = message;
    }

    public BotReloginEventPack() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
