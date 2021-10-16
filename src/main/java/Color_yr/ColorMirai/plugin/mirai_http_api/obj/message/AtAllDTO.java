package Color_yr.ColorMirai.plugin.mirai_http_api.obj.message;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(typeName = "AtAll")
public class AtAllDTO extends MessageDTO {
    public long target;

    public AtAllDTO(long target) {
        this.target = target;
    }
}
