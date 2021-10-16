package Color_yr.ColorMirai.plugin.mirai_http_api.obj.message;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(typeName = "At")
public class AtDTO extends MessageDTO {
    public long target;
    public String display;

    public AtDTO(long target, String display) {
        this.target = target;
        this.display = display;
    }
}
