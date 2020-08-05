package Color_yr.ColorMirai.Pack;

public class FriendRemarkChangeEventPack {
    private long id;
    private String name;

    public FriendRemarkChangeEventPack(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public FriendRemarkChangeEventPack() {
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }
}
