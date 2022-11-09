package coloryr.colormirai;

public class Msg {
    public static String qq(long id) {
        return qq + "[" + id + "]";
    }

    public static String friend_group(long id) {
        return friend_group + "[" + id + "]";
    }

    public static String friend_group(String id) {
        return friend_group + "[" + id + "]";
    }

    public static String friend(long id) {
        return friend + "[" + id + "]";
    }

    public static String group(long id) {
        return group + "[" + id + "]";
    }

    public static String member(long id) {
        return member + "[" + id + "]";
    }

    public static String file(String id) {
        return file + "[" + id + "]";
    }

    public static String dir(String id) {
        return dir + "[" + id + "]";
    }

    public static String dice(long id) {
        return dice + "[" + id + "]";
    }

    public static String stranger(long id) {
        return stranger + "[" + id + "]";
    }

    public static String image(String id) {
        return image + "[" + id + "]";
    }

    public static String announcement(String id) {
        return announcement + "[" + id + "]";
    }

    public static String essence_message(int[] ids1, int[] ids2) {
        StringBuilder temp = new StringBuilder(essence_message + "[[");
        for (int a = 0; a < ids1.length - 1; a++) {
            temp.append(ids1[a]).append(",");
        }
        temp.append(ids1[ids1.length - 1]).append("],[");
        for (int a = 0; a < ids2.length - 1; a++) {
            temp.append(ids2[a]).append(",");
        }
        temp.append(ids2[ids2.length - 1]).append("]]");
        return temp.toString();
    }

    //////////////////////////////////////////////////////
    /// 操作
    //////////////////////////////////////////////////////
    public static final String create = "创建";
    public static final String rename = "重命名";
    public static final String move = "移动";
    public static final String delete = "删除";
    public static final String get = "获取";
    public static final String kick = "踢出";
    public static final String mute = "禁言";
    public static final String unmute = "解除禁言";
    public static final String set = "设置";
    public static final String update = "上传";
    public static final String download = "下载";
    public static final String send = "发送";
    //////////////////////////////////////////////////////
    /// 失败原因
    //////////////////////////////////////////////////////
    public static final String non_permission = "无权限";
    public static final String non_existent = "不存在";
    public static final String existent = "已存在";
    public static final String fail = "失败";
    public static final String type_error = "类型错误";
    //////////////////////////////////////////////////////
    /// 定义
    //////////////////////////////////////////////////////
    public static final String data = "数据";
    public static final String group = "群";
    public static final String friend = "好友";
    public static final String member = "成员";
    public static final String stranger = "陌生人";
    public static final String friend_group = "好友分组";
    public static final String all = "全体";
    public static final String name_card = "群名片";
    public static final String name = "名字";
    public static final String essence_message = "精华消息";
    public static final String qq = "QQ号";
    public static final String admin = "管理员";
    public static final String announcement = "公告";
    public static final String image = "图片";
    public static final String anonymous_chat = "匿名聊天";
    public static final String allow_member_invite = "允许群员邀请好友入群";
    public static final String special_title = "群特殊头衔";
    public static final String file = "文件";
    public static final String files = "文件列表";
    public static final String dir = "文件夹";
    public static final String url = "下载地址";
    public static final String dice = "骰子";
    public static final String message = "消息";
    public static final String sound = "语音";
    public static final String music = "音乐分享";
    public static final String nudge = "戳一戳";
    //////////////////////////////////////////////////////
}
