package Color_yr.ColorMirai.plugin.mirai_http_api.obj.message;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(typeName = "Poke")
public class PokeMessageDTO extends MessageDTO {
    public String name;

    public PokeMessageDTO(String name) {
        this.name = name;
    }
}
