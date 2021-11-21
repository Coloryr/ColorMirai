package coloryr.colormirai.plugin.mirai_http_api.obj.message;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(typeName = "Image")
public class ImageDTO extends MessageDTO {
    public String imageId;
    public String url;
    public String path;

    public ImageDTO(String imageId, String url, String path) {
        this.imageId = imageId;
        this.url = url;
        this.path = path;
    }
}
