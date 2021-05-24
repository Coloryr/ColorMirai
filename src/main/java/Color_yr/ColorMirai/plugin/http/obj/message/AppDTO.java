package Color_yr.ColorMirai.plugin.http.obj.message;

public class AppDTO extends MessageDTO {
    public String content;

    public AppDTO(String content) {
        this.type = "App";
        this.content = content;
    }
}
