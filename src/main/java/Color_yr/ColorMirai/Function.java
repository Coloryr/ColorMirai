package Color_yr.ColorMirai;

public class Function {
    public static String getString(String a, String b, String c) {
        int x = a.indexOf(b) + b.length();
        int y = a.indexOf(c);
        if (y == -1)
            return a;
        char[] data = a.toCharArray();
        if (data[y - 1] == '"')
            y = a.indexOf(c, y + 1);
        return a.substring(x, y);
    }
}
