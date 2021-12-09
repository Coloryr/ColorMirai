package coloryr.colormirai.plugin.one_bot.obj.messagedto;

import net.mamoe.mirai.message.data.FlashImage;
import net.mamoe.mirai.message.data.Image;

class ImageIDTO{
    public String file;
    public String type;
    public String url;
}
public class ImageDTO {
    public final String type = "image";
    public ImageIDTO data;

    public void set(Image image) {
        data = new ImageIDTO() {{
            file = image.getImageId();
            url = Image.queryUrl(image);
        }};
    }

    public void set(FlashImage image) {
        data = new ImageIDTO() {{
            file = image.getImage().getImageId();
            type = "flash";
            url = Image.queryUrl(image.getImage());
        }};
    }
}
