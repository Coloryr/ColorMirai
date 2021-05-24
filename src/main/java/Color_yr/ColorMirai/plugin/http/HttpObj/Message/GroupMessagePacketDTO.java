package Color_yr.ColorMirai.plugin.http.HttpObj.Message;

import Color_yr.ColorMirai.plugin.http.HttpObj.Contact.MemberDTO;

public class GroupMessagePacketDTO extends MessagePacketDTO{
    public MemberDTO sender;
    public String type;

    public GroupMessagePacketDTO(MemberDTO sender)
    {
        this.sender = sender;
        this.type = "GroupMessage";
    }
}
