package coloryr.colormirai.robot;

import net.mamoe.mirai.message.data.MessageSourceKind;

public class ReCallObj extends MessageKey{
    public long bot;
    public MessageSourceKind kind;

    public ReCallObj(int[] ids1, int[] ids2) {
        super(ids1, ids2);
    }
}
