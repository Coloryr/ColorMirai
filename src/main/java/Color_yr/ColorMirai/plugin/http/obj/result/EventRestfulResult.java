package Color_yr.ColorMirai.plugin.http.obj.result;

import Color_yr.ColorMirai.plugin.http.obj.DTO;
import Color_yr.ColorMirai.plugin.http.obj.EventDTO;

import java.util.List;

public class EventRestfulResult implements DTO {
    public int code;
    public String errorMessage;
    public EventDTO data;
}
