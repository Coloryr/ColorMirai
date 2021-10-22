package coloryr.colormirai.demo.RobotSDK;

import coloryr.colormirai.demo.RobotSDK.api.*;
import coloryr.colormirai.demo.RobotSDK.pack.re.*;
import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

class QQGroup {
    public long qq;
    public long group;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof QQGroup) {
            QQGroup group = (QQGroup) obj;
            return group.group == this.group &&
                    group.qq == this.qq;
        }
        return false;
    }
}

class QQMember {
    public long qq;
    public long group;
    public long member;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof QQMember) {
            QQMember member = (QQMember) obj;
            return member.group == this.group &&
                    member.member == this.member &&
                    member.qq == this.qq;
        }
        return false;
    }
}

class QQFriend {
    public long qq;
    public long friend;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof QQFriend) {
            QQFriend friend = (QQFriend) obj;
            return friend.friend == this.friend &&
                    friend.qq == this.qq;
        }
        return false;
    }
}

public class TopRobot extends BaseRobot {
    private final Map<Long, IListFriend> GetFriendsMap = new HashMap<>();
    private final Map<QQGroup, IListMember> GetMembersMap = new HashMap<>();
    private final Map<QQGroup, IGroupSetting> GetGroupSettingMap = new HashMap<>();
    private final Map<Long, IListGroup> GetGroupsMap = new HashMap<>();
    private final Map<String, IImageUrl> GetImageUrlMap = new HashMap<>();
    private final Map<QQMember, IMemberInfo> GetMemberInfoMap = new HashMap<>();
    private final Map<QQFriend, IFriendInfo> GetFriendInfoMap = new HashMap<>();
    private final Map<QQGroup, IGroupFiles> GetGroupFilesMap = new HashMap<>();
    private final Map<QQGroup, IGroupAnnouncements> GetGroupAnnouncementsMap = new HashMap<>();

    @Override
    protected boolean CallTop(byte index, String data) {
        switch (index) {
            case 55: {
                ListGroupPack pack = JSON.parseObject(data, ListGroupPack.class);
                if (GetGroupsMap.containsKey(pack.qq)) {
                    IListGroup action = GetGroupsMap.remove(pack.qq);
                    action.res(pack);
                }
                return true;
            }
            case 56: {
                ListFriendPack pack = JSON.parseObject(data, ListFriendPack.class);
                if (GetFriendsMap.containsKey(pack.qq)) {
                    IListFriend action = GetFriendsMap.remove(pack.qq);
                    action.res(pack);
                }
                return true;
            }
            case 57: {
                ListMemberPack pack = JSON.parseObject(data, ListMemberPack.class);
                QQGroup key = new QQGroup() {{
                    qq = pack.qq;
                    group = pack.id;
                }};
                if (GetMembersMap.containsKey(key)) {
                    IListMember action = GetMembersMap.remove(key);
                    action.res(pack);
                }
                return true;
            }
            case 58: {
                GroupSettingPack pack = JSON.parseObject(data, GroupSettingPack.class);
                QQGroup key = new QQGroup() {{
                    qq = pack.qq;
                    group = pack.id;
                }};
                if (GetGroupSettingMap.containsKey(key)) {
                    IGroupSetting action = GetGroupSettingMap.remove(key);
                    action.res(pack);
                }
                return true;
            }
            case 90: {
                ReImagePack pack = JSON.parseObject(data, ReImagePack.class);
                if (GetImageUrlMap.containsKey(pack.uuid)) {
                    IImageUrl action = GetImageUrlMap.remove(pack.uuid);
                    action.res(pack.url);
                }
                return true;
            }
            case 91: {
                MemberInfoPack pack = JSON.parseObject(data, MemberInfoPack.class);
                QQMember key = new QQMember() {{
                    qq = pack.qq;
                    group = pack.id;
                    member = pack.fid;
                }};
                if (GetMemberInfoMap.containsKey(key)) {
                    IMemberInfo action = GetMemberInfoMap.remove(key);
                    action.res(pack);
                }
                return true;
            }
            case 92: {
                FriendInfoPack pack = JSON.parseObject(data, FriendInfoPack.class);
                QQFriend key = new QQFriend() {{
                    qq = pack.qq;
                    friend = pack.id;
                }};
                if (GetFriendInfoMap.containsKey(key)) {
                    IFriendInfo action = GetFriendInfoMap.remove(key);
                    action.res(pack);
                }
                return true;
            }
            case 101: {
                GroupFilesPack pack = JSON.parseObject(data, GroupFilesPack.class);
                QQGroup key = new QQGroup() {{
                    qq = pack.qq;
                    group = pack.id;
                }};
                if (GetGroupFilesMap.containsKey(key)) {
                    IGroupFiles action = GetGroupFilesMap.remove(key);
                    action.res(pack);
                }
                return true;
            }
            case 109: {
                GroupAnnouncementsPack pack = JSON.parseObject(data, GroupAnnouncementsPack.class);
                QQGroup key = new QQGroup() {{
                    qq = pack.qq;
                    group = pack.id;
                }};
                if (GetGroupAnnouncementsMap.containsKey(key)) {
                    IGroupAnnouncements action = GetGroupAnnouncementsMap.remove(key);
                    action.res(pack);
                }
                return true;
            }
            default:
                return false;
        }
    }
}
