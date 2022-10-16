package coloryr.colormirai.plugin.pack.re;

import java.util.List;

/**
 * 好友分组数据
 */
public class FriendGroupInfo {
    /**
     * 好友分组 ID
     */
    public int id;
    /**
     * 好友分组名
     */
    public String name;
    /**
     * 好友分组内好友数量
     */
    public int count;
    /**
     * 属于本分组的好友集合
     */
    public List<Long> friends;
}
