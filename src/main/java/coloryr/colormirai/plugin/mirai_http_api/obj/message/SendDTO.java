package coloryr.colormirai.plugin.mirai_http_api.obj.message;

import coloryr.colormirai.plugin.mirai_http_api.obj.VerifyDTO;

import java.util.List;

public class SendDTO extends VerifyDTO {
    public int quote;
    public long target;
    public long qq;
    public long group;
    public List<MessageDTO> messageChain;
}
