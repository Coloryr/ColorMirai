package Color_yr.ColorMirai.plugin.http.obj.message;

public class MessageSourceDTO extends MessageDTO{
    public int id;
    public int time;

    public MessageSourceDTO(int id, int time)
    {
        this.id = id;
        this.time = time;
        this.type = "Source";
    }
}
