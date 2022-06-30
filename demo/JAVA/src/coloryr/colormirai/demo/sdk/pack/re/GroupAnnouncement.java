package coloryr.colormirai.demo.sdk.pack.re;

/**
 * 群公告
 */
public class GroupAnnouncement {
    /**
     * 发布者
     */
    public long senderId;
    /**
     * 公告ID
     */
    public String fid;
    /**
     * 有人都已确认
     */
    public boolean allConfirmed;
    /**
     * 为已经确认的成员数量
     */
    public int confirmedMembersCount;
    /**
     * 公告发出的时间
     */
    public long publicationTime;
    /**
     * 内容
     */
    public String content;
    /**
     * 图片
     */
    public String image;
    /**
     * 是否发送给新成员
     */
    public boolean sendToNewMember;
    /**
     * 置顶
     */
    public boolean isPinned;
    /**
     * 修改昵称
     */
    public boolean showEditCard;
    /**
     * 使用弹窗
     */
    public boolean showPopup;
    /**
     * 需要群成员确认
     */
    public boolean requireConfirmation;
}
