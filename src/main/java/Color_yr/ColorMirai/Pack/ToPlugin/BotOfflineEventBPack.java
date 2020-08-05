package Color_yr.ColorMirai.Pack.ToPlugin;

public class BotOfflineEventBPack extends BotOfflineEventAPack {
    private String title;

    public BotOfflineEventBPack(String message, String title) {
        super(message);
        this.title = title;
    }

    public BotOfflineEventBPack() {
    }

    public String getTitle() {
        return title;
    }
}
