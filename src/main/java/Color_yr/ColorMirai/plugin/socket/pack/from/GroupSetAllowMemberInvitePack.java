package Color_yr.ColorMirai.plugin.socket.pack.from;

import Color_yr.ColorMirai.plugin.socket.pack.PackBase;

/**
 * 114 [插件]设置允许群员邀请好友入群的状态
 */
public class GroupSetAllowMemberInvitePack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 是否允许
     */
    public boolean enable;
}
