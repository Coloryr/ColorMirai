package Color_yr.ColorMirai.plugin.mirai_http_api.obj.message;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(typeName = "FlashImage")
public class FlashImageDTO extends MessageDTO {
    public String imageId;
    public String url;
    public String path;

    public FlashImageDTO(String imageId, String url, String path) {
        this.imageId = imageId;
        this.path = path;
        this.url = url;
    }
}
