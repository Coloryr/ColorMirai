package Color_yr.ColorMirai.Pack;

public class MessageRecallEventAPack {
    private long id;
    private int mid;
    private int time;

    public MessageRecallEventAPack(long id, int mid, int time) {
        this.id = id;
        this.mid = mid;
        this.time = time;
    }

    public MessageRecallEventAPack() {
    }

    public long getId() {
        return id;
    }

    public int getTime() {
        return time;
    }

    public int getMid() {
        return mid;
    }
}
