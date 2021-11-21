package coloryr.colormirai.plugin.mirai_http_api.obj.message;

import coloryr.colormirai.plugin.mirai_http_api.obj.DTO;

public class SendRetDTO implements DTO {
    public int code;
    public String msg = "success";
    public int messageId;
}
