package coloryr.colormirai.plugin.one_bot.obj.messagedto;

import net.mamoe.mirai.message.data.Audio;
import net.mamoe.mirai.message.data.OnlineAudio;

class RecordIDTO{
    public String file;
    public String magic;
    public String url;
}

public class RecordDTO {
    public final String type = "record";
    public RecordIDTO data;

    public void set(Audio record) {
        data = new RecordIDTO() {{
            file = record.getFilename();
        }};
    }

    public void set(OnlineAudio record) {
        data = new RecordIDTO() {{
            file = record.getFilename();
            url = record.getUrlForDownload();
        }};
    }
}
