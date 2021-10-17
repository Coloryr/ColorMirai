package Color_yr.ColorMirai.plugin.mirai_http_api.obj.file;

import Color_yr.ColorMirai.plugin.mirai_http_api.obj.StateCode;

import java.util.ArrayList;
import java.util.List;

public class RemoteFileList extends StateCode {
    public List<RemoteFileDTO> data;
    public RemoteFileList() {
        super(0, "");
        data = new ArrayList<>();
    }
}
