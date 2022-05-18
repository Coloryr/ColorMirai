package coloryr.colormirai.plugin.pack.re;

import coloryr.colormirai.plugin.pack.PackBase;

import java.util.List;

/*
 * 57 [插件]获取群成员
 */
public class ListMemberPack extends PackBase {
    /*
     * 群号
     */
    public long id;
    /*
     * 成员列表
     */
    public List<MemberInfoPack> members;
}
