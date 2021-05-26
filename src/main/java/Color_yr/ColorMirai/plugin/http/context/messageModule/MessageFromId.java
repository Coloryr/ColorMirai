package Color_yr.ColorMirai.plugin.http.context.messageModule;

import Color_yr.ColorMirai.plugin.http.Authed;
import Color_yr.ColorMirai.plugin.http.obj.contact.MemberDTO;
import Color_yr.ColorMirai.plugin.http.obj.contact.QQDTO;
import Color_yr.ColorMirai.plugin.http.obj.message.*;
import Color_yr.ColorMirai.plugin.http.obj.result.EventRestfulResult;
import net.mamoe.mirai.message.data.MessageUtils;
import net.mamoe.mirai.message.data.OnlineMessageSource;

import java.util.Map;

public class MessageFromId extends GetBaseMessage {
    @Override
    public Object toDo(Authed authed, Map<String, String> parameters) {
        int id = Integer.parseInt(parameters.get("id"));
        OnlineMessageSource item = authed.cacheQueue.get(id);
        MessagePacketDTO dto = new MessagePacketDTO();
        if (item instanceof OnlineMessageSource.Outgoing.ToGroup) {
            OnlineMessageSource.Outgoing.ToGroup item1 = (OnlineMessageSource.Outgoing.ToGroup) item;
            dto = new GroupMessagePacketDTO(new MemberDTO(item1.getTarget().getBotAsMember()));
        } else if (item instanceof OnlineMessageSource.Outgoing.ToFriend) {
            OnlineMessageSource.Outgoing.ToFriend item1 = (OnlineMessageSource.Outgoing.ToFriend) item;
            dto = new FriendMessagePacketDTO(new QQDTO(item1.getSender().getAsFriend()));
        } else if (item instanceof OnlineMessageSource.Outgoing.ToTemp) {
            OnlineMessageSource.Outgoing.ToTemp item1 = (OnlineMessageSource.Outgoing.ToTemp) item;
            dto = new TempMessagePacketDTO(new MemberDTO(item1.getTarget()));
        } else if (item instanceof OnlineMessageSource.Outgoing.ToStranger) {
            OnlineMessageSource.Outgoing.ToStranger item1 = (OnlineMessageSource.Outgoing.ToStranger) item;
            dto = new StrangerMessagePacketDTO(new QQDTO(item1.getTarget()));
        } else if (item instanceof OnlineMessageSource.Incoming.FromGroup) {
            OnlineMessageSource.Incoming.FromGroup item1 = (OnlineMessageSource.Incoming.FromGroup) item;
            dto = new GroupMessagePacketDTO(new MemberDTO(item1.getSender()));
        } else if (item instanceof OnlineMessageSource.Incoming.FromFriend) {
            OnlineMessageSource.Incoming.FromFriend item1 = (OnlineMessageSource.Incoming.FromFriend) item;
            dto = new FriendMessagePacketDTO(new QQDTO(item1.getSender()));
        } else if (item instanceof OnlineMessageSource.Incoming.FromTemp) {
            OnlineMessageSource.Incoming.FromTemp item1 = (OnlineMessageSource.Incoming.FromTemp) item;
            dto = new TempMessagePacketDTO(new MemberDTO(item1.getSender()));
        } else if (item instanceof OnlineMessageSource.Incoming.FromStranger) {
            OnlineMessageSource.Incoming.FromStranger item1 = (OnlineMessageSource.Incoming.FromStranger) item;
            dto = new StrangerMessagePacketDTO(new QQDTO(item1.getSender()));
        }
        dto.messageChain = MessageDTO.toMessageChainDTO(MessageUtils.newChain(item, item.getOriginalMessage()));

        MessagePacketDTO finalDto = dto;
        return new EventRestfulResult() {{
            data = finalDto;
        }};
    }
}