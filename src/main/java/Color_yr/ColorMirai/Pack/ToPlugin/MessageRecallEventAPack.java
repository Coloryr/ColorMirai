package Color_yr.ColorMirai.Pack.ToPlugin;

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

    public void setId(long id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }
}
