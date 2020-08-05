package Color_yr.ColorMirai.Pack.ToPlugin;

public class FriendAddEventPack {
    private String name;
    private long id;

    public FriendAddEventPack(String name, long id) {
        this.id = id;
        this.name = name;
    }

    public FriendAddEventPack() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
