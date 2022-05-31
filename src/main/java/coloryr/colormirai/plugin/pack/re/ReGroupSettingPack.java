package coloryr.colormirai.plugin.pack.re;

import coloryr.colormirai.plugin.pack.PackBase;
import net.mamoe.mirai.contact.GroupSettings;

/**
 * 58 [插件]获取群设置
 */
public class ReGroupSettingPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 设定
     */
    public GroupSettings setting;
    /**
     * 请求UUID
     */
    public String uuid;
}
