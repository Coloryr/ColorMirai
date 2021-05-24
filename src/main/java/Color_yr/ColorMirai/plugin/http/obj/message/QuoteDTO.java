package Color_yr.ColorMirai.plugin.http.obj.message;

import net.mamoe.mirai.contact.Friend;

import java.util.List;

public class QuoteDTO extends MessageDTO {
    public int id;
    public long senderId;
    public long targetId;
    public long groupId;
    public List<MessageDTO> origin;

    public QuoteDTO(int id, long senderId, long targetId, long groupId, List<MessageDTO> origin) {
        this.type = "Quote";
        this.groupId = groupId;
        this.id = id;
        this.senderId = senderId;
        this.targetId = targetId;
        this.origin = origin;
    }
}
