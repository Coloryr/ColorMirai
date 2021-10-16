package Color_yr.ColorMirai.plugin.mirai_http_api.obj.message;

import Color_yr.ColorMirai.plugin.mirai_http_api.obj.EventDTO;

import java.util.List;

public class MessagePacketDTO extends EventDTO {
    public List<MessageDTO> messageChain;
}
