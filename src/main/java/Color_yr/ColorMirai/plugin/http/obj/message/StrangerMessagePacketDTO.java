package Color_yr.ColorMirai.plugin.http.obj.message;

import Color_yr.ColorMirai.plugin.http.obj.contact.QQDTO;

public class StrangerMessagePacketDTO extends MessagePacketDTO{
    public QQDTO sender;
    public String type;

    public StrangerMessagePacketDTO(QQDTO sender)
    {
        this.sender = sender;
        this.type = "StrangerMessage";
    }
}
