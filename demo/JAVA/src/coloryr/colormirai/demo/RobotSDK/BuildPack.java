package coloryr.colormirai.demo.RobotSDK;

import com.alibaba.fastjson.JSON;

import java.nio.charset.StandardCharsets;

public class BuildPack {
    /**
     * 构建一个包
     *
     * @param data  对象
     * @param index 包ID
     * @return 构建好的包
     */
    public static byte[] Build(Object data, int index) {
        String str = JSON.toJSONString(data) + " ";
        byte[] temp = str.getBytes(StandardCharsets.UTF_8);
        temp[temp.length - 1] = (byte) index;
        return temp;
    }

    /**
     * 构建一个图片发送包
     *
     * @param qq    运行qq
     * @param id    发给群的号码
     * @param fid   发给朋友的qq号
     * @param img   图片BASE64流
     * @param index 包ID
     * @return 构建好的包
     */
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

    /**
     * 构建一个发送语音的包
     *
     * @param qq    运行的qq号
     * @param id    发送给的id
     * @param sound 音频BASE64
     * @param index 包ID
     * @return 构建好的包
     */
    public static byte[] BuildSound(long qq, long id, String sound, byte index) {
        String temp = "id=" + id + "&qq=" + qq + "&sound=" + sound + " ";
        byte[] data = temp.getBytes(StandardCharsets.UTF_8);
        data[data.length - 1] = index;
        return data;
    }
}
