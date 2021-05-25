package Color_yr.ColorMirai.plugin.http.obj.result;

import Color_yr.ColorMirai.plugin.http.obj.DTO;
import Color_yr.ColorMirai.plugin.http.obj.EventDTO;

import java.util.Map;

public class StringMapRestfulResult implements DTO {
    public int code;
    public String errorMessage;
    public Map<String, String> data;
}
