package Color_yr.ColorMirai.plugin.http.obj.message;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(typeName = "Voice")
public class VoiceDTO extends MessageDTO {
    public String voiceId;
    public String url;
    public String path;

    public VoiceDTO(String voiceId, String url, String path) {
        this.path = path;
        this.url = url;
        this.voiceId = voiceId;
    }
}
