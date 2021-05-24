package Color_yr.ColorMirai.plugin.http.obj.message;

public class JsonDTO extends MessageDTO {
    public String json;

    public JsonDTO(String json) {
        this.type = "Json";
        this.json = json;
    }
}
