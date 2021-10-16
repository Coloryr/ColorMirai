using System;
using System.Collections.Generic;
using Newtonsoft.Json;

namespace ColoryrSDK
{
    public partial class Robot
    {
        private record QQGroup
        {
            public long qq { get; set; }
            public long group { get; set; }
        }
        private Dictionary<long, Action<ListFriendPack>> GetFriendsMap = new();
        private Dictionary<QQGroup, Action<ListMemberPack>> GetMembersMap = new();
        private Dictionary<QQGroup, Action<GroupSettingPack>> GetGroupSettingMap = new();
        private Dictionary<long, Action<ListGroupPack>> GetGroupsMap = new();
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
                var key = new QQGroup() { qq = pack.qq, group = pack.id };
                if (GetMembersMap.TryGetValue(key, out var action))
                {
                    GetMembersMap.Remove(key);
                    action.Invoke(pack);
                }
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
        public void Getembers(long qq, long group, Action<ListMemberPack> res)
        {
            var key = new QQGroup() { qq = qq, group = group };
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
            var key = new QQGroup() { qq = qq, group = group };
            GetGroupSettingMap.Add(key, res);
            var data = BuildPack.Build(new GetGroupSettingPack()
            {
                qq = qq,
                id = group
            }, 58);
            AddTask(data);
        }
    }
}
