package coloryr.colormirai.plugin.mirai_http_api.obj.message;

import java.util.List;

public class NodeDTO {
    public long senderId;
    public int time;
    public String senderName;
    public List<MessageDTO> messageChain;

    public NodeDTO(long senderId, int time, String senderName, List<MessageDTO> messageChain) {
        this.messageChain = messageChain;
        this.senderId = senderId;
        this.time = time;
        this.senderName = senderName;
    }
}
