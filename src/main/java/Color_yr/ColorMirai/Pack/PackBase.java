package Color_yr.ColorMirai.Pack;

/*
state
0:初始发包
1:群消息
2:私聊消息
3:发送群消息
4:发送私聊消息
5:取信息

*/

public class PackBase {
    private byte state;
    private String error;
    private String infoData;
    private long formQQ;
    private long group;
    private String message;
    private long toQQ;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getToQQ() {
        return toQQ;
    }

    public void setToQQ(long toQQ) {
        this.toQQ = toQQ;
    }

    public long getFormQQ() {
        return formQQ;
    }

    public void setFormQQ(long formQQ) {
        this.formQQ = formQQ;
    }

    public long getGroup() {
        return group;
    }

    public void setGroup(long group) {
        this.group = group;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInfoData() {
        return infoData;
    }

    public void setInfoData(String infoData) {
        this.infoData = infoData;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }
}
