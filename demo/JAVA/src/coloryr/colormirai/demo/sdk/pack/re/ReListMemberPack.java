package coloryr.colormirai.demo.sdk.pack.re;

import coloryr.colormirai.demo.sdk.pack.PackBase;

import java.util.List;

/**
 * 57 [插件]获取群成员
 */
public class ReListMemberPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 成员列表
     */
    public List<ReMemberInfoPack> members;
    /**
     * 请求UUID
     */
    public String uuid;
}
