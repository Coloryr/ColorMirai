package coloryr.colormirai.plugin.mirai_http_api.obj.message;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(typeName = "Json")
public class JsonDTO extends MessageDTO {
    public String json;

    public JsonDTO(String json) {
        this.json = json;
    }
}
