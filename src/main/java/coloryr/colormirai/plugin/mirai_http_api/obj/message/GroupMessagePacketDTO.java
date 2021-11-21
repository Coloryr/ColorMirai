package coloryr.colormirai.plugin.mirai_http_api.obj.message;

import coloryr.colormirai.plugin.mirai_http_api.obj.contact.MemberDTO;

public class GroupMessagePacketDTO extends MessagePacketDTO {
    public MemberDTO sender;
    public String type;

    public GroupMessagePacketDTO(MemberDTO sender) {
        this.sender = sender;
        this.type = "GroupMessage";
    }
}
