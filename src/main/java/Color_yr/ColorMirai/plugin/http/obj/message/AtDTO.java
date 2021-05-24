package Color_yr.ColorMirai.plugin.http.obj.message;

public class AtDTO extends MessageDTO{
    public long target;
    public String display;

    public AtDTO(long target, String display)
    {
        this.target = target;
        this.display = display;
        this.type = "At";
    }
}
