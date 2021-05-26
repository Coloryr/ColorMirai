package Color_yr.ColorMirai.plugin.http.obj.group;

import Color_yr.ColorMirai.plugin.http.obj.DTO;
import net.mamoe.mirai.contact.Group;

public class GroupDetailDTO implements DTO {
    public String name;
    public String announcement;
    public boolean confessTalk;
    public boolean allowMemberInvite;
    public boolean autoApprove;
    public boolean anonymousChat;

    public GroupDetailDTO(Group group) {
        this.name = group.getName();
        this.announcement = group.getSettings().getEntranceAnnouncement();
        this.confessTalk = false;
        this.allowMemberInvite = group.getSettings().isAllowMemberInvite();
        this.autoApprove = group.getSettings().isAutoApproveEnabled();
        this.anonymousChat = group.getSettings().isAnonymousChatEnabled();
    }
}
