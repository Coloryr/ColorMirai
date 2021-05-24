package Color_yr.ColorMirai.plugin.http.HttpObj.Message;

import Color_yr.ColorMirai.plugin.http.HttpObj.Contact.MemberDTO;

public class TempMessagePacketDTO extends MessagePacketDTO{
    public MemberDTO sender;
    public String type;

    public TempMessagePacketDTO(MemberDTO sender)
    {
        this.sender = sender;
        this.type = "TempMessage";
    }
}
