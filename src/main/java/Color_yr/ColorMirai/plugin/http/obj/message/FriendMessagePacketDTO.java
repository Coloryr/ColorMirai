package Color_yr.ColorMirai.plugin.http.obj.message;

import Color_yr.ColorMirai.plugin.http.obj.contact.QQDTO;
import com.alibaba.fastjson.annotation.JSONType;

@JSONType(typeName = "FriendMessage")
public class FriendMessagePacketDTO extends MessagePacketDTO {
    public QQDTO sender;

    public FriendMessagePacketDTO(QQDTO sender) {
        this.sender = sender;
    }
}
