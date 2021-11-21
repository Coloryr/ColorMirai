package coloryr.colormirai.plugin.mirai_http_api.obj.contact;

import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.contact.MemberPermission;

public class MemberDTO extends ContactDTO {
    public String memberName;
    public MemberPermission permission;
    public GroupDTO group;

    public MemberDTO(Member member) {
        this.id = member.getId();
        this.memberName = member.getNameCard().isEmpty() ? member.getNick() : member.getNameCard();
        this.permission = member.getPermission();
        this.group = new GroupDTO(member.getGroup());
    }
}
