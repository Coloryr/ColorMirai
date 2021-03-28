package Robot;

import com.alibaba.fastjson.JSON;

import java.nio.charset.StandardCharsets;

public class BuildPack {
    public static byte[] Build(Object data, int index) {
        String str = JSON.toJSONString(data) + " ";
        byte[] temp = str.getBytes(StandardCharsets.UTF_8);
        temp[temp.length - 1] = (byte) index;
        return temp;
    }

    public static byte[] BuildImage(long qq, long id, long fid, String img, int index) {
        String temp = "";
        if (id != 0) {
            temp += "id=" + id + "&";
        }
        if (fid != 0) {
            temp += "fid=" + fid + "&";
        }
        temp += "qq=" + qq + "&" + "img=" + img + " ";
        byte[] temp1 = temp.getBytes(StandardCharsets.UTF_8);
        temp1[temp1.length - 1] = (byte) index;
        return temp1;
    }

    public static byte[] BuildSound(long qq, long id, String sound, byte index) {
        String temp = "id=" + id + "&qq=" + qq + "&sound=" + sound + " ";
        byte[] data = temp.getBytes(StandardCharsets.UTF_8);
        data[data.length - 1] = index;
        return data;
    }

    public static byte[] BuildBuffImage(long qq, long id, long fid, int type, String img, boolean send) {
        String temp = "";
        if (id != 0) {
            temp += "id=" + id + "&";
        }
        if (fid != 0) {
            temp += "fid=" + fid + "&";
        }
        temp += "qq=" + qq + "&img=" + img + "&type=" + type + "&send=" + send + " ";
        byte[] data = temp.getBytes(StandardCharsets.UTF_8);
        data[data.length - 1] = 97;
        return data;
    }
}
