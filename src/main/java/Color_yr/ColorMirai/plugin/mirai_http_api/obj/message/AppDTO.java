package Color_yr.ColorMirai.plugin.mirai_http_api.obj.message;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(typeName = "App")
public class AppDTO extends MessageDTO {
    public String content;

    public AppDTO(String content) {
        this.content = content;
    }
}
