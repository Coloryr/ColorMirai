package Color_yr.ColorMirai.plugin.socket.pack.from;

import Color_yr.ColorMirai.plugin.socket.pack.PackBase;

/*
110 [插件]设置群公告
id:群号
imageFile:图片路径
sendToNewMember:发送给新群员
isPinned:顶置
showEditCard:显示能够引导群成员修改昵称的窗口
showPopup:使用弹窗
requireConfirmation:需要群成员确认
text:公告内容
 */
public class GroupSetAnnouncementsPack extends PackBase {
    public long id;
    public String imageFile;
    public boolean sendToNewMember;
    public boolean isPinned;
    public boolean showEditCard;
    public boolean showPopup;
    public boolean requireConfirmation;
    public String text;
}
