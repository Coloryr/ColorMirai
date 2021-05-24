package Color_yr.ColorMirai.plugin.http.obj.message;

public class FlashImageDTO extends MessageDTO {
    public String imageId;
    public String url;
    public String path;

    public FlashImageDTO(String imageId, String url, String path) {
        this.type = "FlashImage";
        this.imageId = imageId;
        this.path = path;
        this.url = url;
    }
}
