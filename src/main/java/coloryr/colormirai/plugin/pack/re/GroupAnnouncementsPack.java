package coloryr.colormirai.plugin.pack.re;

import coloryr.colormirai.plugin.pack.PackBase;

import java.util.List;

/*
 * 群公告返回包
 */
public class GroupAnnouncementsPack extends PackBase {
    /*
     * 群号
     */
    public long id;
    /*
     * 群公告
     */
    public List<GroupAnnouncement> list;
}
