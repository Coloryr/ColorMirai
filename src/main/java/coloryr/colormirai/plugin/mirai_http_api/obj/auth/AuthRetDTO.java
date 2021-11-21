package coloryr.colormirai.plugin.mirai_http_api.obj.auth;

public class AuthRetDTO {
    public int code;
    public String session;

    public AuthRetDTO(int code, String session) {
        this.code = code;
        this.session = session;
    }
}
