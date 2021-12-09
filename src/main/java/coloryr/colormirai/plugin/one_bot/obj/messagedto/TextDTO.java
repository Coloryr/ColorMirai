package coloryr.colormirai.plugin.one_bot.obj.messagedto;

import java.util.Map;

class TextIDTO{
    public String text;
}

public class TextDTO {
    public final String type = "text";
    public TextIDTO data;

    public void set(String text_) {
        data = new TextIDTO() {{
            text = text_;
        }};
    }
}
