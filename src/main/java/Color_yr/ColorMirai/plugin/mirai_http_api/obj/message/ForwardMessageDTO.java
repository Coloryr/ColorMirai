package Color_yr.ColorMirai.plugin.mirai_http_api.obj.message;

import com.alibaba.fastjson.annotation.JSONType;
import net.mamoe.mirai.message.data.ForwardMessage;

import java.util.ArrayList;
import java.util.List;

@JSONType(typeName = "Forward")
public class ForwardMessageDTO extends MessageDTO {
    public List<String> preview;
    public String title;
    public String brief;
    public String source;
    public String summary;
    public List<NodeDTO> nodeList;

    public ForwardMessageDTO(List<String> preview, String title, String brief, String source, String summary, List<NodeDTO> nodeList) {
        this.brief = brief;
        this.nodeList = nodeList;
        this.title = title;
        this.preview = preview;
        this.source = source;
        this.summary = summary;
    }

    public ForwardMessageDTO(ForwardMessage message) {
        List<NodeDTO> list = new ArrayList<>();
        for (ForwardMessage.Node node : message.getNodeList()) {
            list.add(
                    new NodeDTO(
                            node.getSenderId(),
                            node.getTime(),
                            node.getSenderName(),
                            MessageDTO.toMessageChainDTO(
                                    node.getMessageChain())));
        }
        message.getNodeList();
        this.brief = message.getBrief();
        this.nodeList = list;
        this.title = message.getTitle();
        this.preview = message.getPreview();
        this.source = message.getSource();
        this.summary = message.getSummary();
    }
}