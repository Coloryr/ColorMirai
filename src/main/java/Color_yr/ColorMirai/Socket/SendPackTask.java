package Color_yr.ColorMirai.Socket;

public class SendPackTask {
    public int index;
    public byte[] data;

    public SendPackTask(int index, byte[] data) {
        this.index = index;
        this.data = data;
    }
}
