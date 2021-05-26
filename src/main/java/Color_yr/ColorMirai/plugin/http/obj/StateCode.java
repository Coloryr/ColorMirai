package Color_yr.ColorMirai.plugin.http.obj;

public class StateCode {
    public int code;
    public String msg;

    public StateCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static StateCode Success
            = new StateCode(0, "success");
    public static StateCode AuthKeyFail
            = new StateCode(1, "Auth Key错误");
    public static StateCode NoBot
            = new StateCode(2, "指定Bot不存在");
    public static StateCode IllegalSession
            = new StateCode(3, "Session失效或不存在");
    public static StateCode NotVerifySession
            = new StateCode(4, "Session未认证");
    public static StateCode NoElement
            = new StateCode(5, "指定对象不存在");
    public static StateCode NoOperateSupport
            = new StateCode(6, "指定操作不支持");
    public static StateCode PermissionDenied
            = new StateCode(10, "无操作权限");
    public static StateCode BotMuted
            = new StateCode(20, "Bot被禁言");
    public static StateCode MessageTooLarge
            = new StateCode(30, "消息过长");
    public static StateCode Unknown
            = new StateCode(-1, "未支持操作");
    public static StateCode MessageNull
            = new StateCode(40, "消息为空");
    public static StateCode Error
            = new StateCode(50, "发生错误");
}
