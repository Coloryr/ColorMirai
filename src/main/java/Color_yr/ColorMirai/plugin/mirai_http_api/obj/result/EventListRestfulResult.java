package Color_yr.ColorMirai.plugin.mirai_http_api.obj.result;

import Color_yr.ColorMirai.plugin.mirai_http_api.obj.DTO;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.EventDTO;

import java.util.List;

public class EventListRestfulResult implements DTO {
    public int code;
    public String errorMessage;
    public List<EventDTO> data;
}
