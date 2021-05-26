package Color_yr.ColorMirai.plugin.http.obj.message;

import Color_yr.ColorMirai.plugin.http.obj.contact.MemberDTO;

public class TempMessagePacketDTO extends MessagePacketDTO {
    public MemberDTO sender;
    public String type;

    public TempMessagePacketDTO(MemberDTO sender) {
        this.sender = sender;
        this.type = "TempMessage";
    }
}
