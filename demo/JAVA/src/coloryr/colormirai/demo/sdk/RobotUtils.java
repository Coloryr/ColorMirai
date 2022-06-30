package coloryr.colormirai.demo.sdk;

import java.util.Base64;

public class RobotUtils {
    public static String base64E(byte[] data) {
        try {
            return Base64.getEncoder().encodeToString(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
