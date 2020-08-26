package Color_yr.ColorMirai.Socket;

public class SendPackTask {
    public byte index;
    public String data;

    public SendPackTask(int index, String data) {
        this.index = (byte) index;
        this.data = data;
    }
}
