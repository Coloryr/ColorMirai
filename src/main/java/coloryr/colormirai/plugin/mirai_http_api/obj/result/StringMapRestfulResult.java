package coloryr.colormirai.plugin.mirai_http_api.obj.result;

import coloryr.colormirai.plugin.mirai_http_api.obj.DTO;

import java.util.Map;

public class StringMapRestfulResult implements DTO {
    public int code;
    public String errorMessage;
    public Map<String, String> data;
}
