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
        private readonly Dictionary<QQGroup, Action<GroupFilesPack>> GetGroupFilesMap = new();
        private readonly Dictionary<QQGroup, Action<GroupAnnouncementsPack>> GetGroupAnnouncementsMap = new();
        private partial bool CallTop(byte index, string data)
        {
            switch (index)
            {
                case 55:
                    {
                        var pack = JsonConvert.DeserializeObject<ListGroupPack>(data);
                        if (GetGroupsMap.TryGetValue(pack.qq, out var action))
                        {
                            GetGroupsMap.Remove(pack.qq);
                            action.Invoke(pack);
                        }
                        return true;
                    }
                case 56:
                    {
                        var pack = JsonConvert.DeserializeObject<ListFriendPack>(data);
                        if (GetFriendsMap.TryGetValue(pack.qq, out var action))
                        {
                            GetFriendsMap.Remove(pack.qq);
                            action.Invoke(pack);
                        }
                        return true;
                    }
                case 57:
                    {
                        var pack = JsonConvert.DeserializeObject<ListMemberPack>(data);
                        var key = new QQGroup() { QQ = pack.qq, Group = pack.id };
                        if (GetMembersMap.TryGetValue(key, out var action))
                        {
                            GetMembersMap.Remove(key);
                            action.Invoke(pack);
                        }
                        return true;
                    }
                case 58:
                    {
                        var pack = JsonConvert.DeserializeObject<GroupSettingPack>(data);
                        var key = new QQGroup() { QQ = pack.qq, Group = pack.id };
                        if (GetGroupSettingMap.TryGetValue(key, out var action))
                        {
                            GetGroupSettingMap.Remove(key);
                            action.Invoke(pack);
                        }
                        return true;
                    }
                case 90:
                    {
                        var pack = JsonConvert.DeserializeObject<ReImagePack>(data);
                        if (GetImageUrlMap.TryGetValue(pack.uuid, out var action))
                        {
                            GetImageUrlMap.Remove(pack.uuid);
                            action.Invoke(pack.url);
                        }
                        return true;
                    }
                case 91:
                    {
                        var pack = JsonConvert.DeserializeObject<MemberInfoPack>(data);
                        var key = new QQMember() { QQ = pack.qq, Group = pack.id, Member = pack.fid };
                        if (GetMemberInfoMap.TryGetValue(key, out var action))
                        {
                            GetMemberInfoMap.Remove(key);
                            action.Invoke(pack);
                        }
                        return true;
                    }
                case 92:
                    {
                        var pack = JsonConvert.DeserializeObject<FriendInfoPack>(data);
                        var key = new QQFriend() { QQ = pack.qq, Friend = pack.id };
                        if (GetFriendInfoMap.TryGetValue(key, out var action))
                        {
                            GetFriendInfoMap.Remove(key);
                            action.Invoke(pack);
                        }
                        return true;
                    }
                case 101:
                    {
                        var pack = JsonConvert.DeserializeObject<GroupFilesPack>(data);
                        var key = new QQGroup() { QQ = pack.qq, Group = pack.id };
                        if (GetGroupFilesMap.TryGetValue(key, out var action))
                        {
                            GetGroupFilesMap.Remove(key);
                            action.Invoke(pack);
                        }
                        return true;
                    }
                case 109:
                    {
                        var pack = JsonConvert.DeserializeObject<GroupAnnouncementsPack>(data);
                        var key = new QQGroup() { QQ = pack.qq, Group = pack.id };
                        if (GetGroupAnnouncementsMap.TryGetValue(key, out var action))
                        {
                            GetGroupAnnouncementsMap.Remove(key);
                            action.Invoke(pack);
                        }
                        return true;
                    }
                default:
                    return false;
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
            var data = BuildPack.Build(new GroupGetMemberInfoPack()
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
            var data = BuildPack.Build(new GroupGetSettingPack()
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
            var data = BuildPack.Build(new GroupKickMemberPack()
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
            var data = BuildPack.Build(new GroupMuteMemberPack()
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
            var data = BuildPack.Build(new GroupUnmuteMemberPack()
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

        /// <summary>
        /// 94 [插件]设置群精华消息
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        /// <param name="mid">消息ID</param>
        public void GroupSetEssenceMessage(long qq, long group, long mid)
        {
            var data = BuildPack.Build(new GroupSetEssenceMessagePack()
            {
                qq = qq,
                id = group,
                mid = mid
            }, 94);
            AddTask(data);
        }

        /// <summary>
        /// 95 [插件]消息队列
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="id">发送目标</param>
        /// <param name="fid">发送目标</param>
        /// <param name="file">图片文件位置</param>
        /// <param name="message">消息内容</param>
        /// <param name="send">是否发送</param>
        public void MessageBuff(long qq, long id, long fid, SendToType type,
            string file, List<string> message, bool send)
        {
            var data = BuildPack.Build(new MessageBuffPack()
            {
                qq = qq,
                id = id,
                fid = fid,
                type = (int)type,
                imgurl = file,
                text = message,
                send = send
            }, 95);
            AddTask(data);
        }

        /// <summary>
        /// 96 [插件]发送朋友骰子
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="friend">好友QQ号</param>
        /// <param name="dice">点数</param>
        public void SendFriendDice(long qq, long friend, int dice)
        {
            var data = BuildPack.Build(new SendFriendDicePack()
            {
                qq = qq,
                id = friend,
                dice = dice
            }, 96);
            AddTask(data);
        }

        /// <summary>
        /// 97 [插件]发送群骰子
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        /// <param name="dice">点数</param>
        public void SendGroupDice(long qq, long group, int dice)
        {
            var data = BuildPack.Build(new SendGroupDicePack()
            {
                qq = qq,
                id = group,
                dice = dice
            }, 97);
            AddTask(data);
        }

        /// <summary>
        /// 98 [插件]发送群私聊骰子
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        /// <param name="member">群成员</param>
        /// <param name="dice">点数</param>
        public void SendGroupPrivateDice(long qq, long group, long member, int dice)
        {
            var data = BuildPack.Build(new SendGroupPrivateDicePack()
            {
                qq = qq,
                id = group,
                fid = member,
                dice = dice
            }, 98);
            AddTask(data);
        }

        /// <summary>
        /// 99 [插件]上传群文件
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        /// <param name="file">文件路径</param>
        /// <param name="name">群文件名称</param>
        public void GroupAddFilePack(long qq, long group, string file, string name)
        {
            var data = BuildPack.Build(new GroupAddFilePack()
            {
                qq = qq,
                id = group,
                file = file,
                name = name
            }, 99);
            AddTask(data);
        }

        /// <summary>
        /// 100 [插件]删除群文件
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        /// <param name="name">群文件ID</param>
        public void GroupDeleteFile(long qq, long group, string fid)
        {
            var data = BuildPack.Build(new GroupDeleteFilePack()
            {
                qq = qq,
                id = group,
                fid = fid
            }, 100);
            AddTask(data);
        }

        /// <summary>
        /// 101 [插件]获取群文件
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        public void GroupGetFiles(long qq, long group, Action<GroupFilesPack> res)
        {
            var key = new QQGroup() { QQ = qq, Group = group };
            GetGroupFilesMap.Add(key, res);
            var data = BuildPack.Build(new GroupGetFilesPack()
            {
                qq = qq,
                id = group
            }, 101);
            AddTask(data);
        }

        /// <summary>
        /// 102 [插件]移动群文件
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        /// <param name="fid">文件ID</param>
        /// <param name="dir">新的路径</param>
        public void GroupMoveFile(long qq, long group, string fid, string dir)
        {
            var data = BuildPack.Build(new GroupMoveFilePack()
            {
                qq = qq,
                id = group,
                fid = fid,
                dir = dir
            }, 102);
            AddTask(data);
        }

        /// <summary>
        /// 103 [插件]重命名群文件
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        /// <param name="fid">文件ID</param>
        /// <param name="name">新文件名</param>
        public void GroupRemoveFile(long qq, long group, string fid, string name)
        {
            var data = BuildPack.Build(new GroupRenameFilePack()
            {
                qq = qq,
                id = group,
                fid = fid,
                now = name
            }, 103);
            AddTask(data);
        }

        /// <summary>
        /// 104 [插件]创新群文件文件夹
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        /// <param name="dir">文件夹名字</param>
        public void GroupAddDir(long qq, long group, string dir)
        {
            var data = BuildPack.Build(new GroupAddDirPack()
            {
                qq = qq,
                id = group,
                dir = dir
            }, 104);
            AddTask(data);
        }

        /// <summary>
        /// 105 [插件]删除群文件文件夹
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        /// <param name="dir">文件夹名字</param>
        public void GroupRemoveDir(long qq, long group, string dir)
        {
            var data = BuildPack.Build(new GroupDeleteDirPack()
            {
                qq = qq,
                id = group,
                dir = dir
            }, 105);
            AddTask(data);
        }
        /// <summary>
        /// 106 [插件]重命名群文件文件夹
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        /// <param name="old">旧的名字</param>
        /// <param name="now">新的名字</param>
        public void GroupRenameDir(long qq, long group, string old, string now)
        {
            var data = BuildPack.Build(new GroupRenameDirPack()
            {
                qq = qq,
                id = group,
                old = old,
                now = now
            }, 106);
            AddTask(data);
        }

        /// <summary>
        /// 107 [插件]下载群文件到指定位置
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        /// <param name="fid">文件ID</param>
        /// <param name="file">下载到的位置</param>
        public void GroupDownloadFile(long qq, long group, string fid, string file)
        {
            var data = BuildPack.Build(new GroupDownloadFilePack()
            {
                qq = qq,
                id = group,
                fid = fid,
                dir = file
            }, 107);
            AddTask(data);
        }

        /// <summary>
        /// 108 [插件]设置取消管理员
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        /// <param name="member">群成员QQ号</param>
        /// <param name="set">是否设置</param>
        public void GroupSetAdmin(long qq, long group, long member, bool set)
        {
            var data = BuildPack.Build(new GroupSetAdminPack()
            {
                qq = qq,
                id = group,
                fid = member,
                type = set
            }, 108);
            AddTask(data);
        }

        /// <summary>
        /// 109 [插件]获取群公告
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        public void GroupGetAnnouncements(long qq, long group, Action<GroupAnnouncementsPack> res)
        {
            var key = new QQGroup() { QQ = qq, Group = group };
            GetGroupAnnouncementsMap.Add(key, res);
            var data = BuildPack.Build(new GroupGetAnnouncementsPack()
            {
                qq = qq,
                id = group
            }, 109);
            AddTask(data);
        }

        /// <summary>
        /// 110 [插件]设置群公告
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        public void GroupSetAnnouncement(long qq, long group, string imageFile,
            bool sendToNewMember, bool isPinned, bool showEditCard,
            bool showPopup, bool requireConfirmation, string text)
        {
            var data = BuildPack.Build(new GroupAddAnnouncementPack()
            {
                qq = qq,
                id = group,
                imageFile = imageFile,
                sendToNewMember = sendToNewMember,
                isPinned = isPinned,
                showEditCard = showEditCard,
                showPopup = showPopup,
                requireConfirmation = requireConfirmation,
                text = text
            }, 110);
            AddTask(data);
        }

        /// <summary>
        /// 111 [插件]删除群公告
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="group">群号</param>
        /// <param name="fid">公告ID</param>
        public void GroupRemoveAnnouncement(long qq, long  group, string fid)
        {
            var data = BuildPack.Build(new GroupDeleteAnnouncementPack()
            {
                qq = qq,
                id = group,
                fid = fid
            }, 111);
            AddTask(data);
        }

        /// <summary>
        /// 112 [插件]发送好友语言文件
        /// </summary>
        /// <param name="qq">qq号</param>
        /// <param name="friend">好友QQ号</param>
        /// <param name="file">文件路径</param>
        public void SendFriendSoundFile(long qq, long friend, string file)
        {
            var data = BuildPack.Build(new SendFriendSoundFilePack()
            {
                qq = qq,
                id = friend,
                file = file
            }, 112);
            AddTask(data);
        }
    }
}
