package Color_yr.ColorMirai.Pack.ToPlugin;

public class BotGroupPermissionChangePack {
    private String name;
    private long id;

    public BotGroupPermissionChangePack(String name, long id) {
        this.id = id;
        this.name = name;
    }

    public BotGroupPermissionChangePack() {
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }
}
