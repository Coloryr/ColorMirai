package coloryr.colormirai.plugin.mirai_http_api.obj.contact;

import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Stranger;

public class QQDTO extends ContactDTO {
    public String nickname;
    public String remark;

    public QQDTO(Friend qq) {
        this.id = qq.getId();
        this.nickname = qq.getNick();
        this.remark = qq.getRemark();
    }

    public QQDTO(Stranger qq) {
        this.id = qq.getId();
        this.nickname = qq.getNick();
        this.remark = qq.getRemark();
    }
}
