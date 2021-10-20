using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace ColoryrSDK
{
    public partial class Robot
    {
        private record QQGroup
        {
            public long QQ { get; set; }
            public long Group { get; set; }
        }
        private record QQMember
        {
            public long QQ { get; set; }
            public long Group { get; set; }
            public long Member { get; set; }
        }
        private record QQFriend
        {
            public long QQ { get; set; }
            public long Friend { get; set; }
        }
        private readonly Dictionary<long, Action<ListFriendPack>> GetFriendsMap = new();
        private readonly Dictionary<QQGroup, Action<ListMemberPack>> GetMembersMap = new();
        private readonly Dictionary<QQGroup, Action<GroupSettingPack>> GetGroupSettingMap = new();
        private readonly Dictionary<long, Action<ListGroupPack>> GetGroupsMap = new();
        private readonly Dictionary<string, Action<string>> GetImageUrlMap = new();
        private readonly Dictionary<QQMember, Action<MemberInfoPack>> GetMemberInfoMap = new();
        private readonly Dictionary<QQFriend, Action<FriendInfoPack>> GetFriendInfoMap = new();
        private partial void CallTop(byte index, string data)
        {
            if (index == 55)
            {
                var pack = JsonConvert.DeserializeObject<ListGroupPack>(data);
                if (GetGroupsMap.TryGetValue(pack.qq, out var action))
                {
                    GetGroupsMap.Remove(pack.qq);
                    action.Invoke(pack);
                }
            }
            else if (index == 56)
            {
                var pack = JsonConvert.DeserializeObject<ListFriendPack>(data);
                if (GetFriendsMap.TryGetValue(pack.qq, out var action))
                {
                    GetFriendsMap.Remove(pack.qq);
                    action.Invoke(pack);
                }
            }
            else if (index == 57)
            {
                var pack = JsonConvert.DeserializeObject<ListMemberPack>(data);
                var key = new QQGroup() { QQ = pack.qq, Group = pack.id };
                if (GetMembersMap.TryGetValue(key, out var action))
                {
                    GetMembersMap.Remove(key);
                    action.Invoke(pack);
                }
            }
            else if (index == 90)
            {
                var pack = JsonConvert.DeserializeObject<ReImagePack>(data);
                if (GetImageUrlMap.TryGetValue(pack.uuid, out var action))
                {
                    GetImageUrlMap.Remove(pack.uuid);
                    action.Invoke(pack.url);
                }
            }
            else if (index == 91)
            {
                var pack = JsonConvert.DeserializeObject<MemberInfoPack>(data);
                var key = new QQMember() { QQ = pack.qq, Group = pack.id, Member = pack.fid };
                if (GetMemberInfoMap.TryGetValue(key, out var action))
                {
                    GetMemberInfoMap.Remove(key);
                    action.Invoke(pack);
                }
            }
            else if (index == 92)
            {

            }
        }
        /// <summary>
        /// 55 [插件]获取群列表
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="res">获取成功后回调</param>
        public void GetGroups(long qq, Action<ListGroupPack> res)
        {
            GetGroupsMap.Add(qq, res);
            var data = BuildPack.Build(new GetPack()
            {
                qq = qq
            }, 55);
            AddTask(data);
        }
        /// <summary>
        /// 56 [插件]获取好友列表
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="res">获取成功后回调</param>
        public void GetFriends(long qq, Action<ListFriendPack> res)
        {
            GetFriendsMap.Add(qq, res);
            var data = BuildPack.Build(new GetPack()
            {
                qq = qq
            }, 56);
            AddTask(data);
        }
        /// <summary>
        /// 57 [插件]获取群成员
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        /// <param name="res">获取成功后回调</param>
        public void GetMembers(long qq, long group, Action<ListMemberPack> res)
        {
            var key = new QQGroup() { QQ = qq, Group = group };
            GetMembersMap.Add(key, res);
            var data = BuildPack.Build(new GetGroupMemberInfoPack()
            {
                qq = qq,
                id = group
            }, 57);
            AddTask(data);
        }
        /// <summary>
        /// 58 [插件]获取群设置
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        /// <param name="res">获取成功后回调</param>
        public void GetGroupSetting(long qq, long group, Action<GroupSettingPack> res)
        {
            var key = new QQGroup() { QQ = qq, Group = group };
            GetGroupSettingMap.Add(key, res);
            var data = BuildPack.Build(new GetGroupSettingPack()
            {
                qq = qq,
                id = group
            }, 58);
            AddTask(data);
        }
        /// <summary>
        /// 52 [插件]发送群消息
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        /// <param name="message">消息</param>
        public void SendGroupMessage(long qq, long group, List<string> message)
        {
            var data = BuildPack.Build(new SendGroupMessagePack()
            {
                qq = qq,
                id = group,
                message = message
            }, 52);
            AddTask(data);
        }

        /// <summary>
        /// 53 [插件]发送私聊消息
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        /// <param name="member">群员</param>
        /// <param name="message">消息</param>
        public void SendGroupTempMessage(long qq, long group, long member, List<string> message)
        {
            var data = BuildPack.Build(new SendGroupPrivateMessagePack()
            {
                qq = qq,
                id = group,
                fid = member,
                message = message
            }, 53);
            AddTask(data);
        }

        /// <summary>
        /// 59 [插件]回应事件
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="eventid">事件ID</param>
        /// <param name="dofun">操作方式</param>
        /// <param name="arg">附加参数</param>
        public void EventCall(long qq, long eventid, int dofun, List<object> arg)
        {
            var data = BuildPack.Build(new EventCallPack()
            {
                qq = qq,
                eventid = eventid,
                dofun = dofun,
                arg = arg
            }, 59);
            AddTask(data);
        }

        public enum GroupCallType
        {
            /// <summary>
            /// 同意
            /// </summary>
            accept = 0,
            /// <summary>
            /// 拒绝
            /// </summary>
            reject,
            /// <summary>
            /// 不理睬
            /// </summary>
            ignore
        }

        public enum FriendCallType
        {
            /// <summary>
            /// 同意
            /// </summary>
            accept = 0,
            /// <summary>
            /// 拒绝
            /// </summary>
            reject
        }

        /// <summary>
        /// 同意邀请加群
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="eventid">事件ID</param>
        /// <param name="accept">是否同意</param>
        public void BotInvitedJoinGroupRequestCall(long qq, long eventid, bool accept)
        {
            EventCall(qq, eventid, accept ? 0 : 1, null);
        }

        /// <summary>
        /// 同意成员入群
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="eventid">事件ID</param>
        /// <param name="type">操作类型</param>
        /// <param name="blackList">是否加入黑名单</param>
        /// <param name="message">拒绝理由</param>
        public void MemberJoinRequestCall(long qq, long eventid, GroupCallType type, bool blackList = false, string message = "")
        {
            EventCall(qq, eventid, (int)type, new() { blackList, message });
        }

        /// <summary>
        /// 同意新好友申请
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="eventid">事件ID</param>
        /// <param name="type">操作类型</param>
        /// <param name="blackList">是否加入黑名单</param>
        public void NewFriendRequestCall(long qq, long eventid, FriendCallType type, bool blackList = false)
        {
            EventCall(qq, eventid, (int)type, new() { blackList });
        }

        /// <summary>
        /// 64 [插件]删除群员
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        /// <param name="member">群员</param>
        public void GroupDeleteMember(long qq, long group, long member)
        {
            var data = BuildPack.Build(new GroupDeleteMemberPack()
            {
                qq = qq,
                id = group,
                fid = member
            }, 64);
            AddTask(data);
        }

        /// <summary>
        /// 65 [插件]禁言群员
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        /// <param name="member">群员</param>
        public void GroupMemberMute(long qq, long group, long member, int time)
        {
            var data = BuildPack.Build(new GroupMemberMutePack()
            {
                qq = qq,
                id = group,
                fid = member,
                time = time
            }, 65);
            AddTask(data);
        }

        /// <summary>
        /// 66 [插件]解除禁言
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        /// <param name="member">群员</param>
        public void GroupMemberUnmute(long qq, long group, long member)
        {
            var data = BuildPack.Build(new GroupMemberUnmutePack()
            {
                qq = qq,
                id = group,
                fid = member
            }, 66);
            AddTask(data);
        }

        /// <summary>
        /// 67 [插件]开启全员禁言
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        public void GroupMuteAll(long qq, long group)
        {
            var data = BuildPack.Build(new GroupMuteAllPack()
            {
                qq = qq,
                id = group
            }, 67);
            AddTask(data);
        }

        /// <summary>
        /// 68 [插件]关闭全员禁言
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        public void GroupUnmuteAll(long qq, long group)
        {
            var data = BuildPack.Build(new GroupUnmuteAllPack()
            {
                qq = qq,
                id = group
            }, 68);
            AddTask(data);
        }

        /// <summary>
        /// 69 [插件]设置群名片
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        /// <param name="member">群员</param>
        /// <param name="card">群名片</param>
        public void GroupSetMember(long qq, long group, long member, string card)
        {
            var data = BuildPack.Build(new GroupSetMemberCard()
            {
                qq = qq,
                id = group,
                fid = member,
                card = card
            }, 69);
            AddTask(data);
        }

        /// <summary>
        /// 70 [插件]设置群名
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        /// <param name="name">群名字</param>
        public void GroupSetName(long qq, long group, string name)
        {
            var data = BuildPack.Build(new GroupSetNamePack()
            {
                qq = qq,
                id = group,
                name = name
            }, 70);
            AddTask(data);
        }

        /// <summary>
        /// 71 [插件]撤回消息
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="id">消息ID</param>
        public void ReCallMessage(long qq, int id)
        {
            var data = BuildPack.Build(new ReCallMessagePack()
            {
                qq = qq,
                id = id
            }, 71);
            AddTask(data);
        }

        /// <summary>
        /// 75 [插件]从本地文件加载图片发送到群
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        /// <param name="file">文件位置</param>
        public void SendGroupImageFile(long qq, long group, string file)
        {
            var data = BuildPack.Build(new SendGroupImageFilePack()
            {
                qq = qq,
                id = group,
                file = file
            }, 75);
            AddTask(data);
        }

        /// <summary>
        /// 76 [插件]从本地文件加载图片发送到群私聊
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        /// <param name="member">群员</param>
        /// <param name="file">文件位置</param>
        public void SendGroupPrivateImageFile(long qq, long group, long member, string file)
        {
            var data = BuildPack.Build(new SendGroupPrivateImageFilePack()
            {
                qq = qq,
                id = group,
                fid = member,
                file = file
            }, 76);
            AddTask(data);
        }

        /// <summary>
        /// 77 [插件]从本地文件加载图片发送到朋友
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="friend">好友QQ号</param>
        /// <param name="file">文件位置</param>
        public void SendFriendImageFile(long qq, long friend, string file)
        {
            var data = BuildPack.Build(new SendFriendImageFilePack()
            {
                qq = qq,
                id = friend,
                file = file
            }, 77);
            AddTask(data);
        }

        /// <summary>
        /// 78 [插件]从本地文件加载语音发送到群
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        /// <param name="file">文件位置</param>
        public void SendGroupSoundFile(long qq, long group, string file)
        {
            var data = BuildPack.Build(new SendFriendImageFilePack()
            {
                qq = qq,
                id = group,
                file = file
            }, 78);
            AddTask(data);
        }

        /// <summary>
        /// 83 [插件]发送朋友戳一戳
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="friend">好友QQ号</param>
        public void SendFriendNudge(long qq, long friend)
        {
            var data = BuildPack.Build(new SendFriendNudgePack()
            {
                qq = qq,
                id = friend
            }, 83);
            AddTask(data);
        }

        /// <summary>
        /// 84 [插件]发送群成员戳一戳
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        /// <param name="member">群员</param>
        public void SendGroupMemberNudge(long qq, long group, long member)
        {
            var data = BuildPack.Build(new SendGroupMemberNudgePack()
            {
                qq = qq,
                id = group,
                fid = member
            }, 84);
            AddTask(data);
        }

        /// <summary>
        /// 90 [插件]获取图片Url
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="uuid">图片UUID</param>
        /// <param name="res">返回回调</param>
        public void GetImageUrls(long qq, string uuid, Action<string> res)
        {
            GetImageUrlMap.Add(uuid, res);
            var data = BuildPack.Build(new GetImageUrlPack()
            {
                qq = qq,
                uuid = uuid
            }, 90);
            AddTask(data);
        }
        /// <summary>
        /// 91 [插件]获取群成员信息
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        /// <param name="member">群员</param>
        public void GetMemberInfo(long qq, long group, long member, Action<MemberInfoPack> res)
        {
            var key = new QQMember() { QQ = qq, Group = group, Member = member };
            GetMemberInfoMap.Add(key, res);
            var data = BuildPack.Build(new GetMemberInfo()
            {
                qq = qq,
                id = group,
                fid = member
            }, 91);
            AddTask(data);
        }
        /// <summary>
        /// 92 [插件]获取朋友信息
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="friend">群号</param>
        /// <param name="res">返回回调</param>
        public void GetFriendInfo(long qq, long friend, Action<FriendInfoPack> res)
        {
            var key = new QQFriend() { QQ = qq, Friend = friend };
            GetFriendInfoMap.Add(key, res);
            var data = BuildPack.Build(new GetFriendInfoPack()
            {
                qq = qq,
                id = friend
            }, 92);
            AddTask(data);
        }

        public enum MusicKind
        {
            NeteaseCloudMusic = 1,
            QQMusic, MiguMusic, KugouMusic, KuwoMusic
        }

        public enum SendToType
        {
            Friend, Group, Member
        }

        /// <summary>
        /// 93 [插件]发送音乐分享
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="id">发送目标</param>
        /// <param name="fid">发送目标</param>
        /// <param name="kind">音乐类型</param>
        /// <param name="type">目标类型</param>
        /// <param name="title">标题</param>
        /// <param name="summary">概要</param>
        /// <param name="jumpUrl">跳转Url</param>
        /// <param name="pictureUrl">图片Url</param>
        /// <param name="musicUrl">音乐Url</param>
        public void SendMusicShare(long qq, long id, long fid, MusicKind kind,
            SendToType type, string title, string summary, string jumpUrl,
            string pictureUrl, string musicUrl)
        {
            var data = BuildPack.Build(new SendMusicSharePack()
            {
                qq = qq,
                id = id,
                fid = fid,
                type = (int)kind,
                type1 = (int)type,
                title = title,
                summary = summary,
                jumpUrl = jumpUrl,
                pictureUrl = pictureUrl,
                musicUrl = musicUrl
            }, 93);
            AddTask(data);
        }
    }
}
