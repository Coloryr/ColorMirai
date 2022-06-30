package coloryr.colormirai.demo.sdk.pack.re;

import coloryr.colormirai.demo.sdk.pack.PackBase;

import java.util.List;

/**
 * 109 群公告返回包
 */
public class ReGroupAnnouncementsPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 群公告
     */
    public List<GroupAnnouncement> list;
    /**
     * 请求UUID
     */
    public String uuid;
}
