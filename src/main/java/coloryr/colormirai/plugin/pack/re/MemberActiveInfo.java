package coloryr.colormirai.plugin.pack.re;

import net.mamoe.mirai.contact.active.MemberMedalType;

import java.util.Set;

/**
 * 群活跃度相关属性
 */
public class MemberActiveInfo {
    /**
     * 群活跃等级. 取值为 1~6 (包含)
     */
    public int rank;
    /**
     * 群活跃积分
     */
    public int point;
    /**
     * 群荣誉标识
     */
    public Set<Integer> honors;
    /**
     * 群荣誉等级. 取值为 1~100 (包含)
     */
    public int temperature;
    /**
     * 当前佩戴的头衔
     */
    public String title;
    /**
     * 当前佩戴的头衔的颜色
     */
    public String color;
    /**
     * 当前佩戴的头衔类型
     */
    public MemberMedalType wearing;
    /**
     * 拥有的所有头衔
     */
    public Set<MemberMedalType> medals;
}
