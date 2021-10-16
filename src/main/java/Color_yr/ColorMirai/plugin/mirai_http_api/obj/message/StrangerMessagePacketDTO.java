package Color_yr.ColorMirai.plugin.mirai_http_api.obj.message;

import Color_yr.ColorMirai.plugin.mirai_http_api.obj.contact.QQDTO;
import com.alibaba.fastjson.annotation.JSONType;

@JSONType(typeName = "StrangerMessage")
public class StrangerMessagePacketDTO extends MessagePacketDTO {
    public QQDTO sender;

    public StrangerMessagePacketDTO(QQDTO sender) {
        this.sender = sender;
    }
}
