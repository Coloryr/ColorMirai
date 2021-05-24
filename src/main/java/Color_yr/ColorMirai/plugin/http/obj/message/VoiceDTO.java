package Color_yr.ColorMirai.plugin.http.obj.message;

public class VoiceDTO extends MessageDTO {
    public String voiceId;
    public String url;
    public String path;

    public VoiceDTO(String voiceId, String url, String path) {
        this.type = "Voice";
        this.path = path;
        this.url = url;
        this.voiceId = voiceId;
    }
}
