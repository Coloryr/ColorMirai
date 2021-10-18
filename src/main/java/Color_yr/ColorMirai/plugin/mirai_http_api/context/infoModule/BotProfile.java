package Color_yr.ColorMirai.plugin.mirai_http_api.context.infoModule;

import Color_yr.ColorMirai.plugin.mirai_http_api.Authed;
import Color_yr.ColorMirai.plugin.mirai_http_api.context.messageModule.GetBaseMessage;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.ProfileDTO;
import net.mamoe.mirai.data.UserProfile;

import java.util.Map;

public class BotProfile extends GetBaseMessage {
    @Override
    public Object toDo(Authed authed, Map<String, String> parameters) {
        UserProfile profile = authed.bot.getAsFriend().queryProfile();
        return new ProfileDTO() {{
            nickname = profile.getNickname();
            email = profile.getEmail();
            age = profile.getAge();
            level = profile.getQLevel();
            sign = profile.getSign();
            sex = profile.getSex();
        }};
    }
}