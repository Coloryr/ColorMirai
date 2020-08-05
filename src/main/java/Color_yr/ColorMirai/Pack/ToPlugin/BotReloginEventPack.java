package Color_yr.ColorMirai.Pack.ToPlugin;

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
