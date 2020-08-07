package Color_yr.ColorMirai.Pack.ToPlugin;

/*
33 [机器人]图片上传失败（事件）
error：错误消息
index：错误码
 */
public class ImageUploadEventBPack extends ImageUploadEventAPack {
    private String error;
    private int index;

    public ImageUploadEventBPack(long id, String name, String error, int index) {
        super(id, name);
        this.error = error;
        this.index = index;
    }

    public ImageUploadEventBPack() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
