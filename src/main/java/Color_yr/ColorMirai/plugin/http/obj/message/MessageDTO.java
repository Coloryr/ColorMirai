package Color_yr.ColorMirai.plugin.http.obj.message;

import Color_yr.ColorMirai.plugin.http.obj.DTO;
import Color_yr.ColorMirai.plugin.http.obj.EventDTO;
import Color_yr.ColorMirai.plugin.http.obj.IgnoreEventDTO;
import Color_yr.ColorMirai.plugin.http.obj.contact.MemberDTO;
import Color_yr.ColorMirai.plugin.http.obj.contact.QQDTO;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.GroupTempMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.*;

import java.util.ArrayList;
import java.util.List;

public class MessageDTO implements DTO {
    public String type;

    public static EventDTO toDTO(MessageEvent event) {
        EventDTO pack;
        if (event instanceof FriendMessageEvent) {
            FriendMessageEvent event1 = (FriendMessageEvent) event;
            QQDTO dto = new QQDTO(event1.getSender());
            pack = new FriendMessagePacketDTO(dto);
        } else if (event instanceof GroupMessageEvent) {
            GroupMessageEvent event1 = (GroupMessageEvent) event;
            MemberDTO dto = new MemberDTO(event1.getSender());
            pack = new GroupMessagePacketDTO(dto);
        } else if (event instanceof GroupTempMessageEvent) {
            GroupTempMessageEvent event1 = (GroupTempMessageEvent) event;
            MemberDTO dto = new MemberDTO(event1.getSender());
            pack = new TempMessagePacketDTO(dto);
        } else {
            pack = new IgnoreEventDTO();
        }
        if (pack instanceof MessagePacketDTO) {
            ((MessagePacketDTO) pack).messageChain = toMessageChainDTO(event)
        }
        return pack;
    }

    private static List<MessageDTO> toMessageChainDTO(MessageEvent event) {
        List<MessageDTO> list = new ArrayList<>();
        for (SingleMessage item : event.getMessage()) {
            list.add(toDTO(item));
        }
        return list;
    }

    private static MessageDTO toDTO(SingleMessage item) {
        if (item instanceof MessageSource) {
            MessageSource item1 = (MessageSource) item;
            return new MessageSourceDTO(item1.getIds().length == 0 ? 0 : item1.getIds()[0], item1.getTime());
        } else if (item instanceof At) {
            At item1 = (At)item;
            return new AtDTO(item1.getTarget(), "");
        } else if (item instanceof AtAll) {
            return new AtAllDTO(0);
        }else if (item instanceof Face) {
            Face item1 = (Face)item;
            return new FaceDTO(item1.getId(), "");
        } else {
            return new UnknownMessageDTO();
        }
    }
}
