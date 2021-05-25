package Color_yr.ColorMirai.plugin.http.obj.message;

import Color_yr.ColorMirai.plugin.http.obj.VerifyDTO;

import java.util.List;

public class SendImageDTO extends VerifyDTO {
    public long target;
    public long qq;
    public long group;
    public List<String> urls;
}
