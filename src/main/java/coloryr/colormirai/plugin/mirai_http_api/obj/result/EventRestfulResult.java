package coloryr.colormirai.plugin.mirai_http_api.obj.result;

import coloryr.colormirai.plugin.mirai_http_api.obj.DTO;
import coloryr.colormirai.plugin.mirai_http_api.obj.EventDTO;

public class EventRestfulResult implements DTO {
    public int code;
    public String msg;
    public EventDTO data;

    public EventRestfulResult() {
        code = 0;
        msg = "";
    }
}
