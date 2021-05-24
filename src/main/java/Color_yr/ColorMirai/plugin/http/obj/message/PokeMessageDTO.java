package Color_yr.ColorMirai.plugin.http.obj.message;

public class PokeMessageDTO extends MessageDTO {
    public String name;

    public PokeMessageDTO(String name) {
        this.name = name;
        this.type = "Poke";
    }
}
