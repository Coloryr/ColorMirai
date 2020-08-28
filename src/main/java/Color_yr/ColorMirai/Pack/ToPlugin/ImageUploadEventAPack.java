package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;

/*
id：目标id
name：图片ID
 */
public class ImageUploadEventAPack extends PackBase {
    public long id;
    public String name;

    public ImageUploadEventAPack(long qq, long id, String name) {
        this.qq = qq;
        this.id = id;
        this.name = name;
    }
}
