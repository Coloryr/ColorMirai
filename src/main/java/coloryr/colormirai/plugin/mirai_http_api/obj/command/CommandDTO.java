package coloryr.colormirai.plugin.mirai_http_api.obj.command;

import coloryr.colormirai.plugin.mirai_http_api.obj.DTO;

import java.util.List;

public class CommandDTO implements DTO {
    public String name;
    public long friend;
    public long group;
    public List<String> args;
}
