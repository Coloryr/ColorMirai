package coloryr.colormirai.plugin.mirai_http_api.obj.command;

import coloryr.colormirai.plugin.mirai_http_api.obj.DTO;

import java.util.ArrayList;
import java.util.List;

public class PostCommandDTO implements DTO {
    public String authKey;
    public String name;
    public List<String> alias = new ArrayList<>();
    public String description = "";
    public String usage = "";
    public List<String> args = new ArrayList<>();
}
