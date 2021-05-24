package Color_yr.ColorMirai.plugin.http.HttpObj.Contact;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.MemberPermission;

public class GroupDTO extends ContactDTO{
    public String name;
    public MemberPermission memberPermission;

    public GroupDTO(Group group)
    {
        this.id = group.getId();
        this.name = group.getName();
        this.memberPermission = group.getBotPermission();
    }
}
