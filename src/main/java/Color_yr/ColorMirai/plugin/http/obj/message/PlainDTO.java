package Color_yr.ColorMirai.plugin.http.obj.message;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(typeName = "Plain")
public class PlainDTO extends MessageDTO {
    public String text;

    public PlainDTO(String text) {
        this.text = text;
    }
}
