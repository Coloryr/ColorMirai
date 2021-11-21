package coloryr.colormirai.plugin.mirai_http_api.obj.message;

import coloryr.colormirai.plugin.mirai_http_api.obj.contact.QQDTO;
import com.alibaba.fastjson.annotation.JSONType;

@JSONType(typeName = "FriendMessage")
public class FriendMessagePacketDTO extends MessagePacketDTO {
    public QQDTO sender;

    public FriendMessagePacketDTO(QQDTO sender) {
        this.sender = sender;
    }
}
