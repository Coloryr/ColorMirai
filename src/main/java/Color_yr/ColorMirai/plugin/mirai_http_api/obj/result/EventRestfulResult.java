package Color_yr.ColorMirai.plugin.mirai_http_api.obj.result;

import Color_yr.ColorMirai.plugin.mirai_http_api.obj.DTO;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.EventDTO;

public class EventRestfulResult implements DTO {
    public int code;
    public String msg;
    public EventDTO data;

    public EventRestfulResult() {
        code = 0;
        msg = "";
    }
}
