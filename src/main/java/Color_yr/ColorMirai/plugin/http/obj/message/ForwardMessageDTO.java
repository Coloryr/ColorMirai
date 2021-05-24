package Color_yr.ColorMirai.plugin.http.obj.message;

import java.util.List;

public class ForwardMessageDTO extends MessageDTO {
    public List<String> preview;
    public String title;
    public String brief;
    public String source;
    public String summary;
    public List<NodeDTO> nodeList;

    public ForwardMessageDTO(List<String> preview, String title, String brief, String source, String summary, List<NodeDTO> nodeList) {
        this.type = "Forward";
        this.brief = brief;
        this.nodeList = nodeList;
        this.title = title;
        this.preview = preview;
        this.source = source;
        this.summary = summary;
    }
}