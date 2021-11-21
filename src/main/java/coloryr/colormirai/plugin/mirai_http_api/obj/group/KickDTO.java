package coloryr.colormirai.plugin.mirai_http_api.obj.group;

import coloryr.colormirai.plugin.mirai_http_api.obj.VerifyDTO;

public class KickDTO extends VerifyDTO {
    public long target;
    public long memberId;
    public String msg = "";
}
