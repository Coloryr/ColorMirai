package Color_yr.ColorMirai.plugin.http.obj.message;

public class MusicShareDTO extends MessageDTO {
    public String kind;
    public String title;
    public String summary;
    public String jumpUrl;
    public String pictureUrl;
    public String musicUrl;
    public String brief;

    public MusicShareDTO(String kind, String title, String summary, String jumpUrl, String pictureUrl, String musicUrl, String brief) {
        this.type = "MusicShare";
        this.brief = brief;
        this.kind = kind;
        this.pictureUrl = pictureUrl;
        this.musicUrl = musicUrl;
        this.jumpUrl = jumpUrl;
        this.summary = summary;
        this.title = title;
    }
}
