package coloryr.colormirai.plugin.mirai_http_api.obj.message;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(typeName = "Source")
public class MessageSourceDTO extends MessageDTO {
    public int id;
    public int time;

    public MessageSourceDTO(int id, int time) {
        this.id = id;
        this.time = time;
    }
}
