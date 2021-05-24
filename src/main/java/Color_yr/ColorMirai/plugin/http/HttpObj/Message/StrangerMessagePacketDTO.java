package Color_yr.ColorMirai.plugin.http.HttpObj.Message;

import Color_yr.ColorMirai.plugin.http.HttpObj.Contact.QQDTO;

public class StrangerMessagePacketDTO {
    public QQDTO sender;
    public String type;

    public StrangerMessagePacketDTO(QQDTO sender)
    {
        this.sender = sender;
        this.type = "StrangerMessage";
    }
}
