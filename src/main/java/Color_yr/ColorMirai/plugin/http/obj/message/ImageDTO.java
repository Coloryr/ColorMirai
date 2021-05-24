package Color_yr.ColorMirai.plugin.http.obj.message;

public class ImageDTO extends MessageDTO {
    public String imageId;
    public String url;
    public String path;

    public ImageDTO(String imageId, String url, String path) {
        this.imageId = imageId;
        this.url = url;
        this.path = path;
        this.type = "Image";
    }
}
