package coloryr.colormirai.plugin.mirai_http_api.context.infoModule;

import coloryr.colormirai.plugin.mirai_http_api.Authed;
import coloryr.colormirai.plugin.mirai_http_api.context.GetBaseMessage;
import coloryr.colormirai.plugin.mirai_http_api.obj.ProfileDTO;
import coloryr.colormirai.plugin.mirai_http_api.obj.StateCode;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.data.UserProfile;

import java.util.Map;

public class FriendProfile extends GetBaseMessage {

    @Override
    public Object toDo(Authed authed, Map<String, String> parameters) {
        if (!parameters.containsKey("target") || parameters.containsKey("memberId")) {
            return StateCode.Null;
        }
        try {
            long group = Long.parseLong(parameters.get("target"));
            long member = Long.parseLong(parameters.get("memberId"));
            Group group1 = authed.bot.getGroup(group);
            if (group1 == null) {
                return StateCode.NoElement;
            }
            Member member1 = group1.get(member);
            if (member1 == null) {
                return StateCode.NoElement;
            }
            UserProfile profile = member1.queryProfile();
            return new ProfileDTO() {{
                nickname = profile.getNickname();
                email = profile.getEmail();
                age = profile.getAge();
                level = profile.getQLevel();
                sign = profile.getSign();
                sex = profile.getSex();
            }};
        } catch (NumberFormatException e) {
            return StateCode.Unknown;
        }
    }
}
