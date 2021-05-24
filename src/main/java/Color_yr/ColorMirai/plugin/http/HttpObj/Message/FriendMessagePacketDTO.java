package Color_yr.ColorMirai.plugin.http.HttpObj.Message;

import Color_yr.ColorMirai.plugin.http.HttpObj.Contact.QQDTO;

public class FriendMessagePacketDTO extends MessagePacketDTO {
    public String type;
    public QQDTO sender;

    public FriendMessagePacketDTO(QQDTO sender) {
        this.sender = sender;
        this.type = "FriendMessage";
    }
}
