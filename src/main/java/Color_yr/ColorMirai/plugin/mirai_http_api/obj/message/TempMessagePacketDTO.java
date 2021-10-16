package Color_yr.ColorMirai.plugin.mirai_http_api.obj.message;

import Color_yr.ColorMirai.plugin.mirai_http_api.obj.contact.MemberDTO;
import com.alibaba.fastjson.annotation.JSONType;

@JSONType(typeName = "TempMessage")
public class TempMessagePacketDTO extends MessagePacketDTO {
    public MemberDTO sender;

    public TempMessagePacketDTO(MemberDTO sender) {
        this.sender = sender;
    }
}
