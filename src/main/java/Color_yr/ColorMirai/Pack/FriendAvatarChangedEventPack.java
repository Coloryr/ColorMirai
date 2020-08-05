package Color_yr.ColorMirai.Pack;

public class FriendAvatarChangedEventPack {
    private String name;
    private String url;
    private long id;

    public FriendAvatarChangedEventPack(String name, long id, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public FriendAvatarChangedEventPack() {
    }

    public String getUrl() {
        return url;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
