package Color_yr.ColorMirai.plugin.http.HttpObj.Message;

import Color_yr.ColorMirai.plugin.http.HttpObj.DTO;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.FriendMessageEvent;

public class MessageDTO implements DTO {
    public static MessageDTO toDTO(BotEvent event)
    {
        if(event instanceof FriendMessageEvent)
        {

        }
    }
}
