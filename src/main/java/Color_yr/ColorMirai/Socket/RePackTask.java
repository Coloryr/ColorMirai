package Color_yr.ColorMirai.Socket;

public class RePackTask {
    private byte index;
    private String data;

    public RePackTask(byte index, String data) {
        this.index = index;
        this.data = data;
    }

    public byte getIndex() {
        return index;
    }

    public void setIndex(byte index) {
        this.index = index;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
