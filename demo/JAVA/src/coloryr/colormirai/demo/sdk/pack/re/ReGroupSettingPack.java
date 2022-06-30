package coloryr.colormirai.demo.sdk.pack.re;

import coloryr.colormirai.demo.sdk.objs.GroupSettings;
import coloryr.colormirai.demo.sdk.pack.PackBase;

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
