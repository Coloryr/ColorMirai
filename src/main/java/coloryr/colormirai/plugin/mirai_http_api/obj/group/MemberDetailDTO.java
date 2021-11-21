package coloryr.colormirai.plugin.mirai_http_api.obj.group;

import coloryr.colormirai.plugin.mirai_http_api.obj.DTO;
import net.mamoe.mirai.contact.Member;

public class MemberDetailDTO implements DTO {
    public String name;
    public String nick;
    public String specialTitle;

    public MemberDetailDTO(Member member) {
        this.name = member.getNameCard();
        this.nick = member.getNick();
        this.specialTitle = member.getSpecialTitle();
    }
}
