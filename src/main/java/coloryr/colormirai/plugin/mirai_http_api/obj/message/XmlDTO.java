package coloryr.colormirai.plugin.mirai_http_api.obj.message;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(typeName = "Xml")
public class XmlDTO extends MessageDTO {
    public String xml;

    public XmlDTO(String xml) {
        this.xml = xml;
    }
}
