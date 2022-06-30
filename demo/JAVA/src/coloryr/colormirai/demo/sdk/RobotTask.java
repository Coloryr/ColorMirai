package coloryr.colormirai.demo.sdk;

public class RobotTask {
    public byte index;
    public Object data;

    public RobotTask(byte type, Object s) {
        index = type;
        data = s;
    }
}