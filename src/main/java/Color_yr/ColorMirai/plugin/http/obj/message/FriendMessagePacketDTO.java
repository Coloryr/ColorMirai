package Color_yr.ColorMirai.plugin.http.obj.message;

import Color_yr.ColorMirai.plugin.http.obj.contact.QQDTO;

public class FriendMessagePacketDTO extends MessagePacketDTO {
    public String type;
    public QQDTO sender;

    public FriendMessagePacketDTO(QQDTO sender) {
        this.sender = sender;
        this.type = "FriendMessage";
    }
}
