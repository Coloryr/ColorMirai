package Color_yr.ColorMirai.plugin.http.obj.message;

public class AtAllDTO extends MessageDTO{
    public long target;

    public AtAllDTO(long target)
    {
        this.target = target;
        this.type = "AtAll";
    }
}
