package coloryr.colormirai.plugin.mirai_http_api.obj.message;

import coloryr.colormirai.plugin.mirai_http_api.obj.EventDTO;

import java.util.List;

public class MessagePacketDTO extends EventDTO {
    public List<MessageDTO> messageChain;
}
