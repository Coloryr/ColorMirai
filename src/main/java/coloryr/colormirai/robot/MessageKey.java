package coloryr.colormirai.robot;

import java.lang.reflect.Array;

public class MessageKey {
    private final int[] ids1;
    private final int[] ids2;

    public MessageKey(int[] ids1, int[] ids2) {
        this.ids1 = ids1;
        this.ids2 = ids2;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MessageKey))
            return false;
        MessageKey key = (MessageKey) obj;
        if (key.ids1.length != this.ids1.length
                || key.ids2.length != this.ids2.length)
            return false;
        for (int a = 0; a < key.ids1.length; a++) {
            if (key.ids1[a] != this.ids1[a])
                return false;
        }
        for (int a = 0; a < key.ids2.length; a++) {
            if (key.ids2[a] != this.ids2[a])
                return false;
        }

        return true;
    }
}
