package coloryr.colormirai.plugin.socket.pack.from;

import coloryr.colormirai.plugin.socket.pack.PackBase;

/**
 * 110 [插件]设置群公告
 */
public class GroupAddAnnouncementPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 图片路径
     */
    public String imageFile;
    /**
     * 发送给新群员
     */
    public boolean sendToNewMember;
    /**
     * 顶置
     */
    public boolean isPinned;
    /**
     * 显示能够引导群成员修改昵称的窗口
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
    /**
     * 公告内容
     */
    public String text;
}
