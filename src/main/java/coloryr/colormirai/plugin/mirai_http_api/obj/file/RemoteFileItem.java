package coloryr.colormirai.plugin.mirai_http_api.obj.file;

import coloryr.colormirai.plugin.mirai_http_api.obj.StateCode;

public class RemoteFileItem extends StateCode {
    public RemoteFileDTO data;

    public RemoteFileItem() {
        super(0, "");
    }
}
