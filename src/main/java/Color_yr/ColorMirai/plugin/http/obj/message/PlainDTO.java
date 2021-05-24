package Color_yr.ColorMirai.plugin.http.obj.message;

public class PlainDTO extends MessageDTO{
    public String text;

    public PlainDTO(String text)
    {
        this.text = text;
        this.type = "Plain";
    }
}
