package Color_yr.ColorMirai.plugin.mirai_http_api.obj;

import net.mamoe.mirai.data.UserProfile;

public class ProfileDTO implements DTO {
    public String nickname;
    public String email;
    public int age;
    public int level;
    public String sign;
    public UserProfile.Sex sex;
}
