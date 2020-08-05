package Color_yr.ColorMirai.Pack.ToPlugin;

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

    public void setUrl(String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
