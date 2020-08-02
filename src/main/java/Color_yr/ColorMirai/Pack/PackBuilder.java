package Color_yr.ColorMirai.Pack;

public class PackBuilder {

    public static PackBase buildError(String error) {
        PackBase pack = new PackBase();
        pack.setState((byte) 0);
        pack.setError(error);
        return pack;
    }

    public static PackBase buildReMessage(long formQQ, long group, String message) {
        PackBase pack = new PackBase();
        pack.setState((byte) 2);
        pack.setFormQQ(formQQ);
        pack.setGroup(group);
        pack.setMessage(message);
        return pack;
    }

    public static PackBase buildGetInfo(String info) {
        PackBase pack = new PackBase();
        pack.setState((byte) 1);
        pack.setInfoData(info);
        return pack;
    }

}
