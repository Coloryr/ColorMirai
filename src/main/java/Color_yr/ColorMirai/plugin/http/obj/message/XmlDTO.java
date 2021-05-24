package Color_yr.ColorMirai.plugin.http.obj.message;

public class XmlDTO extends MessageDTO {
    public String xml;

    public XmlDTO(String xml) {
        this.type = "Xml";
        this.xml = xml;
    }
}
