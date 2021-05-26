package Color_yr.ColorMirai.plugin.http.obj.message;

import Color_yr.ColorMirai.plugin.http.obj.contact.MemberDTO;
import com.alibaba.fastjson.annotation.JSONType;

@JSONType(typeName = "TempMessage")
public class TempMessagePacketDTO extends MessagePacketDTO {
    public MemberDTO sender;

    public TempMessagePacketDTO(MemberDTO sender) {
        this.sender = sender;
    }
}
