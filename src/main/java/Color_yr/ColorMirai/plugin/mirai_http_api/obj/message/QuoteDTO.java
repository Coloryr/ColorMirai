package Color_yr.ColorMirai.plugin.mirai_http_api.obj.message;

import com.alibaba.fastjson.annotation.JSONType;

import java.util.List;

@JSONType(typeName = "Quote")
public class QuoteDTO extends MessageDTO {
    public int id;
    public long senderId;
    public long targetId;
    public long groupId;
    public List<MessageDTO> origin;

    public QuoteDTO(int id, long senderId, long targetId, long groupId, List<MessageDTO> origin) {
        this.groupId = groupId;
        this.id = id;
        this.senderId = senderId;
        this.targetId = targetId;
        this.origin = origin;
    }
}
