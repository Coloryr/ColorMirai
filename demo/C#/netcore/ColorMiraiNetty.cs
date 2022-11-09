using DotNetty.Buffers;
using DotNetty.Codecs;
using DotNetty.Transport.Bootstrapping;
using DotNetty.Transport.Channels;
using DotNetty.Transport.Channels.Sockets;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Text;
using System.Threading;

namespace ColoryrSDK;

internal static class PackDecode
{
    public const int NettyVersion = 102;
    public static string ReadString(this IByteBuffer buff)
    {
        return buff.ReadString(buff.ReadInt(), Encoding.UTF8);
    }
    public static List<long> ReadLongList(this IByteBuffer buff)
    {
        var list = new List<long>();
        int a = buff.ReadInt();
        for (int i = 0; i < a; i++)
        {
            list.Add(buff.ReadLong());
        }
        return list;
    }
    public static List<string> ReadStringList(this IByteBuffer buff)
    {
        var list = new List<string>();
        int a = buff.ReadInt();
        for (int i = 0; i < a; i++)
        {
            list.Add(buff.ReadString());
        }
        return list;
    }
    public static int[] ReadIntList(this IByteBuffer buff)
    {
        var list = new int[buff.ReadInt()];
        for (int a = 0; a < list.Length; a++)
        {
            list[a] = buff.ReadInt();
        }
        return list;
    }

    private static List<GroupHonorType> ReadGroupHonorTypeList(this IByteBuffer buff)
    {
        List<GroupHonorType> list = new();
        int size = buff.ReadInt();
        for (int a = 0; a < size; a++)
        {
            list.Add((GroupHonorType)buff.ReadInt());
        }

        return list;
    }

    private static List<MemberMedalType> ReadMemberMedalTypeList(this IByteBuffer buff)
    {
        List<MemberMedalType> list = new();
        int size = buff.ReadInt();
        for (int a = 0; a < size; a++)
        {
            list.Add((MemberMedalType)buff.ReadInt());
        }

        return list;
    }

    public static ReMemberInfoPack ReadMemberInfoPack(this IByteBuffer buff)
    {
        return new()
        {
            id = buff.ReadLong(),
            fid = buff.ReadLong(),
            nick = buff.ReadString(),
            img = buff.ReadString(),
            per = (MemberPermission)buff.ReadInt(),
            nameCard = buff.ReadString(),
            specialTitle = buff.ReadString(),
            avatarUrl = buff.ReadString(),
            muteTimeRemaining = buff.ReadInt(),
            joinTimestamp = buff.ReadInt(),
            lastSpeakTimestamp = buff.ReadInt(),
            rankTitle = buff.ReadString(),
            active = new()
            {
                rank = buff.ReadInt(),
                point = buff.ReadInt(),
                honors = buff.ReadGroupHonorTypeList(),
                temperature = buff.ReadInt(),
                title = buff.ReadString(),
                color = buff.ReadString(),
                wearing = (MemberMedalType)buff.ReadInt(),
                medals = buff.ReadMemberMedalTypeList(),
            },
            uuid = buff.ReadString()
        };
    }
    public static ReFriendInfoPack ReadFriendInfoPack(this IByteBuffer buff)
    {
        return new()
        {
            id = buff.ReadLong(),
            img = buff.ReadString(),
            remark = buff.ReadString(),
            userProfile = new()
            {
                nickname = buff.ReadString(),
                email = buff.ReadString(),
                age = buff.ReadInt(),
                qLevel = buff.ReadInt(),
                sex = (Sex)buff.ReadInt(),
                sign = buff.ReadString()
            },
            groupId = buff.ReadInt(),
            uuid = buff.ReadString()
        };
    }
    public static GroupFileInfo ReadGroupFileInfo(this IByteBuffer buff)
    {
        return new()
        {
            name = buff.ReadString(),
            id = buff.ReadString(),
            absolutePath = buff.ReadString(),
            isFile = buff.ReadBoolean(),
            isFolder = buff.ReadBoolean(),
            size = buff.ReadLong(),
            uploaderId = buff.ReadLong(),
            uploadTime = buff.ReadLong(),
            lastModifyTime = buff.ReadLong(),
            expiryTime = buff.ReadLong(),
            sha1 = buff.ReadString(),
            md5 = buff.ReadString()
        };
    }
    public static GroupAnnouncement ReadGroupAnnouncement(this IByteBuffer buff)
    {
        return new()
        {
            senderId = buff.ReadLong(),
            fid = buff.ReadString(),
            allConfirmed = buff.ReadBoolean(),
            confirmedMembersCount = buff.ReadInt(),
            publicationTime = buff.ReadLong(),
            content = buff.ReadString(),
            image = buff.ReadString(),
            sendToNewMember = buff.ReadBoolean(),
            isPinned = buff.ReadBoolean(),
            showEditCard = buff.ReadBoolean(),
            showPopup = buff.ReadBoolean(),
            requireConfirmation = buff.ReadBoolean()
        };
    }
    public static GroupInfo ReadGroupInfo(this IByteBuffer buff)
    {
        return new()
        {
            id = buff.ReadLong(),
            name = buff.ReadString(),
            img = buff.ReadString(),
            oid = buff.ReadLong(),
            per = (MemberPermission)buff.ReadInt()
        };
    }
    public static FriendGroupInfo ReadFriendGroupInfo(this IByteBuffer buff)
    {
        return new()
        {
            id = buff.ReadInt(),
            name = buff.ReadString(),
            count = buff.ReadInt(),
            friends = buff.ReadLongList()
        };
    }
    public static List<long> StartPack(this IByteBuffer buff)
    {
        int a = buff.ReadInt();
        List<long> list = new();
        for (int i = 0; i < a; i++)
        {
            list.Add(buff.ReadLong());
        }
        return list;
    }
    public static ReListGroupPack ListGroupPack(this IByteBuffer buff)
    {
        ReListGroupPack pack = new()
        {
            qq = buff.ReadLong(),
            groups = new()
        };
        int a = buff.ReadInt();
        for (int i = 0; i < a; i++)
        {
            pack.groups.Add(buff.ReadGroupInfo());
        }
        pack.uuid = buff.ReadString();
        return pack;
    }
    public static ReListFriendPack ListFriendPack(this IByteBuffer buff)
    {
        ReListFriendPack pack = new()
        {
            qq = buff.ReadLong(),
            friends = new()
        };
        int a = buff.ReadInt();
        for (int i = 0; i < a; i++)
        {
            pack.friends.Add(buff.ReadFriendInfoPack());
        }
        pack.uuid = buff.ReadString();
        return pack;
    }
    public static ReListMemberPack ListMemberPack(this IByteBuffer buff)
    {
        ReListMemberPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            members = new()
        };
        int a = buff.ReadInt();
        for (int i = 0; i < a; i++)
        {
            pack.members.Add(buff.ReadMemberInfoPack());
        }
        pack.uuid = buff.ReadString();
        return pack;
    }
    public static ReGroupSettingPack GroupSettingPack(this IByteBuffer buff)
    {
        ReGroupSettingPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            setting = new()
            {
                isMuteAll = buff.ReadBoolean(),
                isAllowMemberInvite = buff.ReadBoolean(),
                isAutoApproveEnabled = buff.ReadBoolean(),
                isAnonymousChatEnabled = buff.ReadBoolean()
            },
            uuid = buff.ReadString()
        };
        return pack;
    }
    public static GetImageUrlPack GetImageUrlPack(this IByteBuffer buff)
    {
        GetImageUrlPack pack = new()
        {
            qq = buff.ReadLong(),
            uuid = buff.ReadString()
        };
        return pack;
    }
    public static ReMemberInfoPack MemberInfoPack(this IByteBuffer buff)
    {
        long qq = buff.ReadLong();
        var pack = buff.ReadMemberInfoPack();
        pack.qq = qq;
        return pack;
    }
    public static ReFriendInfoPack FriendInfoPack(this IByteBuffer buff)
    {
        long qq = buff.ReadLong();
        var pack = buff.ReadFriendInfoPack();
        pack.qq = qq;
        pack.uuid = buff.ReadString();
        return pack;
    }
    public static ReGroupFilesPack GroupFilesPack(this IByteBuffer buff)
    {
        ReGroupFilesPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            files = new()
        };
        int a = buff.ReadInt();
        for (int i = 0; i < a; i++)
        {
            pack.files.Add(buff.ReadGroupFileInfo());
        }
        pack.uuid = buff.ReadString();
        return pack;
    }
    public static ReGroupAnnouncementsPack GroupAnnouncementsPack(this IByteBuffer buff)
    {
        ReGroupAnnouncementsPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            list = new()
        };
        int a = buff.ReadInt();
        for (int i = 0; i < a; i++)
        {
            pack.list.Add(buff.ReadGroupAnnouncement());
        }
        pack.uuid = buff.ReadString();
        return pack;
    }
    public static BeforeImageUploadPack BeforeImageUploadPack(this IByteBuffer buff)
    {
        BeforeImageUploadPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            name = buff.ReadString()
        };
        return pack;
    }
    public static BotAvatarChangedPack BotAvatarChangedPack(this IByteBuffer buff)
    {
        BotAvatarChangedPack pack = new()
        {
            qq = buff.ReadLong()
        };
        return pack;
    }
    public static BotGroupPermissionChangePack BotGroupPermissionChangePack(this IByteBuffer buff)
    {
        BotGroupPermissionChangePack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            old = (MemberPermission)buff.ReadInt(),
            now = (MemberPermission)buff.ReadInt()
        };
        return pack;
    }
    public static BotInvitedJoinGroupRequestEventPack BotInvitedJoinGroupRequestEventPack(this IByteBuffer buff)
    {
        BotInvitedJoinGroupRequestEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            fid = buff.ReadLong(),
            eventid = buff.ReadLong()
        };
        return pack;
    }
    public static BotJoinGroupEventAPack BotJoinGroupEventAPack(this IByteBuffer buff)
    {
        BotJoinGroupEventAPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong()
        };
        return pack;
    }
    public static BotJoinGroupEventBPack BotJoinGroupEventBPack(this IByteBuffer buff)
    {
        BotJoinGroupEventBPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            fid = buff.ReadLong()
        };
        return pack;
    }
    public static BotLeaveEventAPack BotLeaveEventAPack(this IByteBuffer buff)
    {
        BotLeaveEventAPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong()
        };
        return pack;
    }
    public static BotLeaveEventBPack BotLeaveEventBPack(this IByteBuffer buff)
    {
        BotLeaveEventBPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            fid = buff.ReadLong()
        };
        return pack;
    }
    public static BotMuteEventPack BotMuteEventPack(this IByteBuffer buff)
    {
        BotMuteEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            fid = buff.ReadLong(),
            time = buff.ReadInt()
        };
        return pack;
    }
    public static BotOfflineEventAPack BotOfflineEventAPack(this IByteBuffer buff)
    {
        BotOfflineEventAPack pack = new()
        {
            qq = buff.ReadLong(),
            message = buff.ReadString()
        };
        return pack;
    }
    public static BotOfflineEventBPack BotOfflineEventBPack(this IByteBuffer buff)
    {
        BotOfflineEventBPack pack = new()
        {
            qq = buff.ReadLong(),
            message = buff.ReadString(),
            title = buff.ReadString()
        };
        return pack;
    }
    public static BotOfflineEventCPack BotOfflineEventCPack(this IByteBuffer buff)
    {
        BotOfflineEventCPack pack = new()
        {
            qq = buff.ReadLong()
        };
        return pack;
    }
    public static BotOnlineEventPack BotOnlineEventPack(this IByteBuffer buff)
    {
        BotOnlineEventPack pack = new()
        {
            qq = buff.ReadLong()
        };
        return pack;
    }
    public static BotReloginEventPack BotReloginEventPack(this IByteBuffer buff)
    {
        BotReloginEventPack pack = new()
        {
            qq = buff.ReadLong(),
            message = buff.ReadString()
        };
        return pack;
    }
    public static BotUnmuteEventPack BotUnmuteEventPack(this IByteBuffer buff)
    {
        BotUnmuteEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            fid = buff.ReadLong()
        };
        return pack;
    }
    public static FriendAddEventPack FriendAddEventPack(this IByteBuffer buff)
    {
        FriendAddEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            nick = buff.ReadString()
        };
        return pack;
    }
    public static FriendAvatarChangedEventPack FriendAvatarChangedEventPack(this IByteBuffer buff)
    {
        FriendAvatarChangedEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            url = buff.ReadString()
        };
        return pack;
    }
    public static FriendDeleteEventPack FriendDeleteEventPack(this IByteBuffer buff)
    {
        FriendDeleteEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong()
        };
        return pack;
    }
    public static FriendMessagePostSendEventPack FriendMessagePostSendEventPack(this IByteBuffer buff)
    {
        FriendMessagePostSendEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            res = buff.ReadBoolean(),
            ids1 = buff.ReadIntList(),
            ids2 = buff.ReadIntList(),
            message = buff.ReadStringList(),
            error = buff.ReadString()
        };
        return pack;
    }
    public static FriendMessagePreSendEventPack FriendMessagePreSendEventPack(this IByteBuffer buff)
    {
        FriendMessagePreSendEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            message = buff.ReadStringList()
        };
        return pack;
    }
    public static FriendRemarkChangeEventPack FriendRemarkChangeEventPack(this IByteBuffer buff)
    {
        FriendRemarkChangeEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            old = buff.ReadString(),
            now = buff.ReadString()
        };
        return pack;
    }
    public static GroupAllowAnonymousChatEventPack GroupAllowAnonymousChatEventPack(this IByteBuffer buff)
    {
        GroupAllowAnonymousChatEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            old = buff.ReadBoolean(),
            now = buff.ReadBoolean()
        };
        return pack;
    }
    public static GroupAllowConfessTalkEventPack GroupAllowConfessTalkEventPack(this IByteBuffer buff)
    {
        GroupAllowConfessTalkEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            old = buff.ReadBoolean(),
            now = buff.ReadBoolean()
        };
        return pack;
    }
    public static GroupAllowMemberInviteEventPack GroupAllowMemberInviteEventPack(this IByteBuffer buff)
    {
        GroupAllowMemberInviteEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            old = buff.ReadBoolean(),
            now = buff.ReadBoolean()
        };
        return pack;
    }
    //public static GroupEntranceAnnouncementChangeEventPack GroupEntranceAnnouncementChangeEventPack(this IByteBuffer buff)
    //{
    //    GroupEntranceAnnouncementChangeEventPack pack = new()
    //    {
    //        qq = buff.ReadLong(),
    //        id = buff.ReadLong(),
    //        fid = buff.ReadLong(),
    //        old = buff.ReadBoolean(),
    //        now = buff.ReadBoolean()
    //    };
    //    return pack;
    //}
    public static GroupMessagePostSendEventPack GroupMessagePostSendEventPack(this IByteBuffer buff)
    {
        GroupMessagePostSendEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            res = buff.ReadBoolean(),
            ids1 = buff.ReadIntList(),
            ids2 = buff.ReadIntList(),
            message = buff.ReadStringList(),
            error = buff.ReadString()
        };
        return pack;
    }
    public static GroupMessagePreSendEventPack GroupMessagePreSendEventPack(this IByteBuffer buff)
    {
        GroupMessagePreSendEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            message = buff.ReadStringList()
        };
        return pack;
    }
    public static GroupMuteAllEventPack GroupMuteAllEventPack(this IByteBuffer buff)
    {
        GroupMuteAllEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            fid = buff.ReadLong(),
            old = buff.ReadBoolean(),
            now = buff.ReadBoolean()
        };
        return pack;
    }
    public static GroupNameChangeEventPack GroupNameChangeEventPack(this IByteBuffer buff)
    {
        GroupNameChangeEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            fid = buff.ReadLong(),
            old = buff.ReadString(),
            now = buff.ReadString()
        };
        return pack;
    }
    public static ImageUploadEventAPack ImageUploadEventAPack(this IByteBuffer buff)
    {
        ImageUploadEventAPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            fid = buff.ReadLong(),
            uuid = buff.ReadString()
        };
        return pack;
    }
    public static ImageUploadEventBPack ImageUploadEventBPack(this IByteBuffer buff)
    {
        ImageUploadEventBPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            index = buff.ReadInt(),
            name = buff.ReadString(),
            error = buff.ReadString()
        };
        return pack;
    }
    public static MemberCardChangeEventPack MemberCardChangeEventPack(this IByteBuffer buff)
    {
        MemberCardChangeEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            old = buff.ReadString(),
            now = buff.ReadString()
        };
        return pack;
    }
    public static InviteMemberJoinEventPack InviteMemberJoinEventPack(this IByteBuffer buff)
    {
        InviteMemberJoinEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            fid = buff.ReadLong(),
            name = buff.ReadString(),
            ifid = buff.ReadLong(),
            iname = buff.ReadString()
        };
        return pack;
    }
    public static MemberJoinEventPack MemberJoinEventPack(this IByteBuffer buff)
    {
        MemberJoinEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            fid = buff.ReadLong(),
            name = buff.ReadString()
        };
        return pack;
    }
    public static MemberJoinRequestEventPack MemberJoinRequestEventPack(this IByteBuffer buff)
    {
        MemberJoinRequestEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            fid = buff.ReadLong(),
            qif = buff.ReadLong(),
            message = buff.ReadString(),
            eventid = buff.ReadLong()
        };
        return pack;
    }
    public static MemberLeaveEventAPack MemberLeaveEventAPack(this IByteBuffer buff)
    {
        MemberLeaveEventAPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            fid = buff.ReadLong(),
            eid = buff.ReadLong()
        };
        return pack;
    }
    public static MemberLeaveEventBPack MemberLeaveEventBPack(this IByteBuffer buff)
    {
        MemberLeaveEventBPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            fid = buff.ReadLong()
        };
        return pack;
    }
    public static MemberMuteEventPack MemberMuteEventPack(this IByteBuffer buff)
    {
        MemberMuteEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            fid = buff.ReadLong(),
            eid = buff.ReadLong(),
            time = buff.ReadInt()
        };
        return pack;
    }
    public static MemberPermissionChangeEventPack MemberPermissionChangeEventPack(this IByteBuffer buff)
    {
        MemberPermissionChangeEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            fid = buff.ReadLong(),
            old = (MemberPermission)buff.ReadInt(),
            now = (MemberPermission)buff.ReadInt()
        };
        return pack;
    }
    public static MemberSpecialTitleChangeEventPack MemberSpecialTitleChangeEventPack(this IByteBuffer buff)
    {
        MemberSpecialTitleChangeEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            fid = buff.ReadLong(),
            old = buff.ReadString(),
            now = buff.ReadString()
        };
        return pack;
    }
    public static MemberUnmuteEventPack MemberUnmuteEventPack(this IByteBuffer buff)
    {
        MemberUnmuteEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            fid = buff.ReadLong(),
            eid = buff.ReadLong()
        };
        return pack;
    }
    public static MessageRecallEventAPack MessageRecallEventAPack(this IByteBuffer buff)
    {
        MessageRecallEventAPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            time = buff.ReadInt()
        };
        int a = buff.ReadInt();
        pack.mid = new int[a];
        for (int i = 0; i < a; i++)
        {
            pack.mid[i] = buff.ReadInt();
        }
        return pack;
    }
    public static MessageRecallEventBPack MessageRecallEventBPack(this IByteBuffer buff)
    {
        MessageRecallEventBPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            time = buff.ReadInt(),
            fid = buff.ReadLong(),
            oid = buff.ReadLong()
        };
        int a = buff.ReadInt();
        pack.mid = new int[a];
        for (int i = 0; i < a; i++)
        {
            pack.mid[i] = buff.ReadInt();
        }
        return pack;
    }
    public static NewFriendRequestEventPack NewFriendRequestEventPack(this IByteBuffer buff)
    {
        NewFriendRequestEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            message = buff.ReadString(),
            eventid = buff.ReadLong()
        };
        return pack;
    }
    public static TempMessagePostSendEventPack TempMessagePostSendEventPack(this IByteBuffer buff)
    {
        TempMessagePostSendEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            res = buff.ReadBoolean(),
            ids1 = buff.ReadIntList(),
            ids2 = buff.ReadIntList(),
            message = buff.ReadStringList(),
            error = buff.ReadString()
        };
        return pack;
    }
    public static TempMessagePreSendEventPack TempMessagePreSendEventPack(this IByteBuffer buff)
    {
        return new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            message = buff.ReadStringList()
        };
    }
    public static GroupMessageEventPack GroupMessageEventPack(this IByteBuffer buff)
    {
        GroupMessageEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            fid = buff.ReadLong(),
            name = buff.ReadString(),
            time = buff.ReadInt(),
            ids1 = buff.ReadIntList(),
            ids2 = buff.ReadIntList(),
            permission = (MemberPermission)buff.ReadInt(),
            message = buff.ReadStringList()
        };
        return pack;
    }
    public static TempMessageEventPack TempMessageEventPack(this IByteBuffer buff)
    {
        TempMessageEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            fid = buff.ReadLong(),
            name = buff.ReadString(),
            ids1 = buff.ReadIntList(),
            ids2 = buff.ReadIntList(),
            permission = (MemberPermission)buff.ReadInt(),
            message = buff.ReadStringList(),
            time = buff.ReadInt()
        };
        return pack;
    }
    public static FriendMessageEventPack FriendMessageEventPack(this IByteBuffer buff)
    {
        FriendMessageEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            name = buff.ReadString(),
            ids1 = buff.ReadIntList(),
            ids2 = buff.ReadIntList(),
            message = buff.ReadStringList(),
            time = buff.ReadInt()
        };
        return pack;
    }
    public static FriendInputStatusChangedEventPack FriendInputStatusChangedEventPack(this IByteBuffer buff)
    {
        FriendInputStatusChangedEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            input = buff.ReadBoolean()
        };
        return pack;
    }
    public static FriendNickChangedEventPack FriendNickChangedEventPack(this IByteBuffer buff)
    {
        FriendNickChangedEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            old = buff.ReadString(),
            now = buff.ReadString()
        };
        return pack;
    }
    public static MemberJoinRetrieveEventPack MemberJoinRetrieveEventPack(this IByteBuffer buff)
    {
        MemberJoinRetrieveEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            fid = buff.ReadLong()
        };
        return pack;
    }
    public static BotJoinGroupEventRetrieveEventPack BotJoinGroupEventRetrieveEventPack(this IByteBuffer buff)
    {
        BotJoinGroupEventRetrieveEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong()
        };
        return pack;
    }
    public static NudgedEventPack NudgedEventPack(this IByteBuffer buff)
    {
        NudgedEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            fid = buff.ReadLong(),
            aid = buff.ReadLong(),
            action = buff.ReadString(),
            suffix = buff.ReadString()
        };
        return pack;
    }
    public static GroupTalkativeChangePack GroupTalkativeChangePack(this IByteBuffer buff)
    {
        return new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            old = buff.ReadLong(),
            now = buff.ReadLong()
        };
    }
    public static OtherClientOnlineEventPack OtherClientOnlineEventPack(this IByteBuffer buff)
    {
        OtherClientOnlineEventPack pack = new()
        {
            qq = buff.ReadLong(),
            appId = buff.ReadInt(),
            kind = buff.ReadString(),
            platform = buff.ReadString(),
            deviceName = buff.ReadString(),
            deviceKind = buff.ReadString()
        };
        return pack;
    }
    public static OtherClientOfflineEventPack OtherClientOfflineEventPack(this IByteBuffer buff)
    {
        OtherClientOfflineEventPack pack = new()
        {
            qq = buff.ReadLong(),
            appId = buff.ReadInt(),
            platform = buff.ReadString(),
            deviceName = buff.ReadString(),
            deviceKind = buff.ReadString()
        };
        return pack;
    }
    public static OtherClientMessageEventPack OtherClientMessageEventPack(this IByteBuffer buff)
    {
        OtherClientMessageEventPack pack = new()
        {
            qq = buff.ReadLong(),
            appId = buff.ReadInt(),
            platform = buff.ReadString(),
            deviceName = buff.ReadString(),
            deviceKind = buff.ReadString(),
            message = buff.ReadStringList()
        };
        return pack;
    }
    public static GroupMessageSyncEventPack GroupMessageSyncEventPack(this IByteBuffer buff)
    {
        GroupMessageSyncEventPack pack = new()
        {
            qq = buff.ReadLong(),
            time = buff.ReadInt(),
            message = buff.ReadStringList()
        };
        return pack;
    }
    public static GroupDisbandPack GroupDisbandPack(this IByteBuffer buff)
    {
        GroupDisbandPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong()
        };
        return pack;
    }
    public static StrangerMessageEventPack StrangerMessageEventPack(this IByteBuffer buff)
    {
        StrangerMessageEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            name = buff.ReadString(),
            ids1 = buff.ReadIntList(),
            ids2 = buff.ReadIntList(),
            message = buff.ReadStringList(),
            time = buff.ReadInt()
        };
        return pack;
    }
    public static StrangerMessagePreSendEventPack StrangerMessagePreSendEventPack(this IByteBuffer buff)
    {
        StrangerMessagePreSendEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            message = buff.ReadStringList()
        };
        return pack;
    }
    public static StrangerMessagePostSendEventPack StrangerMessagePostSendEventPack(this IByteBuffer buff)
    {
        StrangerMessagePostSendEventPack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            res = buff.ReadBoolean(),
            message = buff.ReadStringList(),
            error = buff.ReadString()
        };
        return pack;
    }
    public static StrangerRelationChangePack StrangerRelationChangePack(this IByteBuffer buff)
    {
        StrangerRelationChangePack pack = new()
        {
            qq = buff.ReadLong(),
            id = buff.ReadLong(),
            type = buff.ReadInt()
        };
        return pack;
    }
    public static ReFriendGroupPack ReFriendGroupPack(this IByteBuffer buff)
    {
        ReFriendGroupPack pack = new()
        {
            qq = buff.ReadLong(),
            info = buff.ReadFriendGroupInfo(),
            uuid = buff.ReadString()
        };
        return pack;
    }
    public static ReListFriendGroupPack ReListFriendGroupPack(this IByteBuffer buff)
    {
        ReListFriendGroupPack pack = new()
        {
            qq = buff.ReadLong(),
            infos = new()
        };
        int size = buff.ReadInt();
        for (int a = 0; a < size; a++)
        {
            pack.infos.Add(buff.ReadFriendGroupInfo());
        }
        pack.uuid = buff.ReadString();
        return pack;
    }
    public static ReMessagePack ReMessagePack(this IByteBuffer buff)
    {
        return new ReMessagePack()
        {
            qq = buff.ReadLong(),
            uuid = buff.ReadString(),
            msg = buff.ReadString()
        };
    }
}

internal static class PackEncode
{
    public static IByteBuffer WriteString(this IByteBuffer buff, string data)
    {
        var temp = Encoding.UTF8.GetBytes(data);
        buff.WriteBytes1(temp);
        return buff;
    }
    public static IByteBuffer WriteStringList(this IByteBuffer buff, List<string> list)
    {
        buff.WriteInt(list.Count);
        foreach (var item in list)
        {
            buff.WriteString(item);
        }
        return buff;
    }
    public static IByteBuffer WriteLongList(this IByteBuffer buff, HashSet<long> list)
    {
        buff.WriteInt(list.Count);
        foreach (var item in list)
        {
            buff.WriteLong(item);
        }
        return buff;
    }
    public static IByteBuffer WriteLongList(this IByteBuffer buff, List<long> list)
    {
        buff.WriteInt(list.Count);
        foreach (var item in list)
        {
            buff.WriteLong(item);
        }
        return buff;
    }
    public static IByteBuffer WriteBytes1(this IByteBuffer buff, byte[] data)
    {
        buff.WriteInt(data.Length);
        buff.WriteBytes(data, 0, data.Length);
        return buff;
    }
    public static IByteBuffer WriteIntList(this IByteBuffer buff, int[] data)
    {
        buff.WriteInt(data.Length);
        foreach (var item in data)
        {
            buff.WriteInt(item);
        }
        return buff;
    }

    public static IByteBuffer ToPack(this StartPack pack)
    {
        pack.qqList ??= new();
        pack.groups ??= new();
        pack.reg ??= new();
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(0)
            .WriteString(pack.name)
            .WriteInt(pack.reg.Count);
        foreach (var item in pack.reg)
        {
            buff.WriteInt(item);
        }
        buff.WriteLongList(pack.groups)
            .WriteLongList(pack.qqList)
            .WriteLong(pack.qq)
            .WriteInt(PackDecode.NettyVersion);
        return buff;
    }
    public static IByteBuffer ToPack(this SendGroupMessagePack pack)
    {
        pack.ids ??= new();
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(52)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteStringList(pack.message)
            .WriteLongList(pack.ids);

        return buff;
    }
    public static IByteBuffer ToPack(this SendGroupPrivateMessagePack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(53)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteLong(pack.fid)
            .WriteStringList(pack.message)
            .WriteLongList(pack.ids);

        return buff;
    }
    public static IByteBuffer ToPack(this SendFriendMessagePack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(54)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteStringList(pack.message)
            .WriteLongList(pack.ids);

        return buff;
    }
    public static IByteBuffer ToPack(this GetPack pack, byte index)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(index).WriteLong(pack.qq).WriteString(pack.uuid);

        return buff;
    }
    public static IByteBuffer ToPack(this GroupGetMemberInfoPack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(57)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteBoolean(pack.fast)
            .WriteString(pack.uuid);

        return buff;
    }
    public static IByteBuffer ToPack(this GroupGetSettingPack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(58)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteString(pack.uuid);

        return buff;
    }
    public static IByteBuffer ToPack(this EventCallPack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(59)
            .WriteLong(pack.qq)
            .WriteLong(pack.eventid)
            .WriteInt(pack.dofun)
            .WriteStringList(pack.arg);

        return buff;
    }
    public static IByteBuffer ToPack(this SendGroupImagePack pack)
    {
        pack.data ??= Array.Empty<byte>();
        pack.ids ??= new();
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(61)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteBytes1(pack.data)
            .WriteLongList(pack.ids);

        return buff;
    }
    public static IByteBuffer ToPack(this SendGroupPrivateImagePack pack)
    {
        pack.data ??= Array.Empty<byte>();
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(62)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteLong(pack.fid)
            .WriteBytes1(pack.data)
            .WriteLongList(pack.ids);

        return buff;
    }
    public static IByteBuffer ToPack(this SendFriendImagePack pack)
    {
        pack.data ??= Array.Empty<byte>();
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(63)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteBytes1(pack.data)
            .WriteLongList(pack.ids);

        return buff;
    }
    public static IByteBuffer ToPack(this GroupKickMemberPack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(64)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteLong(pack.fid)
            .WriteBoolean(pack.black);

        return buff;
    }
    public static IByteBuffer ToPack(this GroupMuteMemberPack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(65)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteLong(pack.fid)
            .WriteInt(pack.time);

        return buff;
    }
    public static IByteBuffer ToPack(this GroupUnmuteMemberPack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(66)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteLong(pack.fid);

        return buff;
    }
    public static IByteBuffer ToPack(this GroupMuteAllPack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(67)
            .WriteLong(pack.qq)
            .WriteLong(pack.id);

        return buff;
    }
    public static IByteBuffer ToPack(this GroupUnmuteAllPack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(68)
            .WriteLong(pack.qq)
            .WriteLong(pack.id);

        return buff;
    }
    public static IByteBuffer ToPack(this GroupSetMemberCardPack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(69)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteLong(pack.fid)
            .WriteString(pack.card);

        return buff;
    }
    public static IByteBuffer ToPack(this GroupSetNamePack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(70)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteString(pack.name);

        return buff;
    }
    public static IByteBuffer ToPack(this ReCallMessagePack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(71)
            .WriteLong(pack.qq)
            .WriteIntList(pack.ids1)
            .WriteIntList(pack.ids2)
            .WriteInt((int)pack.kind);

        return buff;
    }
    public static IByteBuffer ToPack(this SendGroupSoundPack pack)
    {
        pack.data ??= Array.Empty<byte>();
        pack.ids ??= new();
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(74)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteBytes1(pack.data)
            .WriteLongList(pack.ids);

        return buff;
    }
    public static IByteBuffer ToPack(this SendGroupImageFilePack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(75)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteString(pack.file)
            .WriteLongList(pack.ids);

        return buff;
    }
    public static IByteBuffer ToPack(this SendGroupPrivateImageFilePack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(76)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteLong(pack.fid)
            .WriteString(pack.file)
            .WriteLongList(pack.ids);

        return buff;
    }
    public static IByteBuffer ToPack(this SendFriendImageFilePack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(77)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteLongList(pack.ids);

        return buff;
    }
    public static IByteBuffer ToPack(this SendGroupSoundFilePack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(78)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteString(pack.file)
            .WriteLongList(pack.ids);

        return buff;
    }
    public static IByteBuffer ToPack(this SendFriendNudgePack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(83)
            .WriteLong(pack.qq)
            .WriteLong(pack.id);

        return buff;
    }
    public static IByteBuffer ToPack(this SendGroupMemberNudgePack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(84)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteLong(pack.fid);

        return buff;
    }
    public static IByteBuffer ToPack(this GetImageUrlPack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(90)
            .WriteLong(pack.qq)
            .WriteString(pack.uuid);

        return buff;
    }
    public static IByteBuffer ToPack(this GetMemberInfoPack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(91)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteLong(pack.fid)
            .WriteBoolean(pack.fast)
            .WriteString(pack.uuid);

        return buff;
    }
    public static IByteBuffer ToPack(this GetFriendInfoPack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(92)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteString(pack.uuid);

        return buff;
    }
    public static IByteBuffer ToPack(this SendMusicSharePack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(93)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteLong(pack.fid)
            .WriteInt(pack.type)
            .WriteInt(pack.type1)
            .WriteString(pack.title)
            .WriteString(pack.summary)
            .WriteString(pack.jumpUrl)
            .WriteString(pack.pictureUrl)
            .WriteString(pack.musicUrl)
            .WriteLongList(pack.ids);

        return buff;
    }
    public static IByteBuffer ToPack(this GroupSetEssenceMessagePack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(94)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteIntList(pack.ids1)
            .WriteIntList(pack.ids2);

        return buff;
    }
    public static IByteBuffer ToPack(this MessageBuffPack pack)
    {
        pack.imgData ??= Array.Empty<byte>();
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(95)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteLong(pack.fid)
            .WriteInt(pack.type)
            .WriteBoolean(pack.send)
            .WriteStringList(pack.text)
            .WriteString(pack.imgurl)
            .WriteBytes1(pack.imgData);

        return buff;
    }
    public static IByteBuffer ToPack(this SendFriendDicePack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteInt(pack.dice);

        return buff;
    }
    public static IByteBuffer ToPack(this SendGroupDicePack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(97)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteInt(pack.dice);

        return buff;
    }
    public static IByteBuffer ToPack(this SendGroupPrivateDicePack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(98)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteLong(pack.fid)
            .WriteInt(pack.dice);

        return buff;
    }
    public static IByteBuffer ToPack(this GroupAddFilePack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(99)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteString(pack.file)
            .WriteString(pack.name);

        return buff;
    }
    public static IByteBuffer ToPack(this GroupDeleteFilePack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(100)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteString(pack.fid);

        return buff;
    }
    public static IByteBuffer ToPack(this GroupGetFilesPack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(101)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteString(pack.uuid);

        return buff;
    }
    public static IByteBuffer ToPack(this GroupMoveFilePack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(102)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteString(pack.fid)
            .WriteString(pack.dir);

        return buff;
    }
    public static IByteBuffer ToPack(this GroupRenameFilePack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(103)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteString(pack.fid)
            .WriteString(pack.now);

        return buff;
    }
    public static IByteBuffer ToPack(this GroupAddDirPack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(104)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteString(pack.dir);

        return buff;
    }
    public static IByteBuffer ToPack(this GroupDeleteDirPack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(105)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteString(pack.dir);

        return buff;
    }
    public static IByteBuffer ToPack(this GroupRenameDirPack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(106)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteString(pack.old)
            .WriteString(pack.now);

        return buff;
    }
    public static IByteBuffer ToPack(this GroupDownloadFilePack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(107)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteString(pack.fid)
            .WriteString(pack.dir);

        return buff;
    }
    public static IByteBuffer ToPack(this GroupSetAdminPack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(108)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteLong(pack.fid)
            .WriteBoolean(pack.type);

        return buff;
    }
    public static IByteBuffer ToPack(this GroupGetAnnouncementsPack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(109)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteString(pack.uuid);

        return buff;
    }
    public static IByteBuffer ToPack(this GroupAddAnnouncementPack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(110)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteString(pack.imageFile)
            .WriteBoolean(pack.sendToNewMember)
            .WriteBoolean(pack.isPinned)
            .WriteBoolean(pack.showEditCard)
            .WriteBoolean(pack.showPopup)
            .WriteBoolean(pack.requireConfirmation)
            .WriteString(pack.text);

        return buff;
    }
    public static IByteBuffer ToPack(this GroupDeleteAnnouncementPack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(111)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteString(pack.fid);

        return buff;
    }
    public static IByteBuffer ToPack(this SendFriendSoundFilePack pack)
    {
        pack.ids ??= new();
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(112)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteString(pack.file)
            .WriteLongList(pack.ids);

        return buff;
    }
    public static IByteBuffer ToPack(this GroupSetAllowMemberInvitePack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(114)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteBoolean(pack.enable);

        return buff;
    }
    public static IByteBuffer ToPack(this GroupSetAnonymousChatEnabledPack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(115)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteBoolean(pack.enable);

        return buff;
    }
    public static IByteBuffer ToPack(this SendStrangerMessagePack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(117)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteStringList(pack.message);

        return buff;
    }
    public static IByteBuffer ToPack(this SendStrangerImageFilePack pack)
    {
        pack.ids ??= new();
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(118)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteString(pack.file)
            .WriteLongList(pack.ids);

        return buff;
    }
    public static IByteBuffer ToPack(this SendStrangerDicePack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(119)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteInt(pack.dice);

        return buff;
    }
    public static IByteBuffer ToPack(this SendStrangerNudgePack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(120)
            .WriteLong(pack.qq)
            .WriteLong(pack.id);

        return buff;
    }
    public static IByteBuffer ToPack(this SendStrangerSoundFilePack pack)
    {
        pack.ids ??= new();
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(121)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteString(pack.file)
            .WriteLongList(pack.ids);

        return buff;
    }
    public static IByteBuffer ToPack(this SendFriendSoundPack pack)
    {
        pack.data ??= Array.Empty<byte>();
        pack.ids ??= new();
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(126)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteBytes1(pack.data)
            .WriteLongList(pack.ids);

        return buff;
    }
    public static IByteBuffer ToPack(this GetFriendGroupPack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(128)
            .WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteString(pack.uuid);

        return buff;
    }
    public static IByteBuffer ToPack(this FriendGroupCreatePack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteLong(pack.qq).
            WriteString(pack.name);

        return buff;
    }
    public static IByteBuffer ToPack(this FriendGroupRenamePack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteLong(pack.qq)
            .WriteInt(pack.id)
            .WriteString(pack.name);

        return buff;
    }
    public static IByteBuffer ToPack(this FriendGroupMovePack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteLong(pack.qq)
            .WriteInt(pack.id)
            .WriteLong(pack.fid);

        return buff;
    }
    public static IByteBuffer ToPack(this FriendGroupDeletePack pack)
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteLong(pack.qq)
            .WriteInt(pack.id);

        return buff;
    }
    public static IByteBuffer ToPack(this GroupMemberEditSpecialTitlePack pack) 
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteLong(pack.qq)
            .WriteLong(pack.id)
            .WriteLong(pack.fid)
            .WriteString(pack.name);

        return buff;
    }

    public static IByteBuffer TestPack()
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(60);
        return buff;
    }
}

internal class ColorMiraiNetty : IColorMiraiPipe
{
    public RobotSDK Robot;

    private record PackTask
    {
        public PackBase pack;
        public int index;
    }
    private ConcurrentBag<PackTask> queue1;
    private ConcurrentBag<IByteBuffer> queue2;

    private Thread thread;
    private MultithreadEventLoopGroup group;
    private IChannel client;
    private Bootstrap bootstrap;

    public ColorMiraiNetty(RobotSDK robot)
    {
        Robot = robot;
        queue1 = new();
        queue2 = new();
        thread = new(Read);

        group = new MultithreadEventLoopGroup();
    }

    private void Read()
    {
        int time = 0;
        while (Robot.IsRun)
        {
            try
            {
                Thread.Sleep(50);
                if (!Robot.IsConnect)
                {
                    ReConnect();
                    Robot.IsFirst = false;
                    Robot.Times = 0;
                    Robot.Config.StateAction(StateType.Connect);
                }
                else if (queue2.TryTake(out var item))
                {
                    if (item == null)
                    {
                        continue;
                    }
                    int index = item.ReadInt();
                    if (index == 60)
                        continue;
                    PackBase pack = index switch
                    {
                        0 => item.ReMessagePack(),
                        1 => item.BeforeImageUploadPack(),
                        2 => item.BotAvatarChangedPack(),
                        3 => item.BotGroupPermissionChangePack(),
                        4 => item.BotInvitedJoinGroupRequestEventPack(),
                        5 => item.BotJoinGroupEventAPack(),
                        6 => item.BotJoinGroupEventBPack(),
                        7 => item.BotLeaveEventAPack(),
                        8 => item.BotLeaveEventBPack(),
                        9 => item.BotMuteEventPack(),
                        10 => item.BotOfflineEventAPack(),
                        11 => item.BotOfflineEventBPack(),
                        12 => item.BotOfflineEventAPack(),
                        13 => item.BotOfflineEventAPack(),
                        14 => item.BotOfflineEventCPack(),
                        15 => item.BotOnlineEventPack(),
                        16 => item.BotReloginEventPack(),
                        17 => item.BotUnmuteEventPack(),
                        18 => item.FriendAddEventPack(),
                        19 => item.FriendAvatarChangedEventPack(),
                        20 => item.FriendDeleteEventPack(),
                        21 => item.FriendMessagePostSendEventPack(),
                        22 => item.FriendMessagePreSendEventPack(),
                        23 => item.FriendRemarkChangeEventPack(),
                        24 => item.GroupAllowAnonymousChatEventPack(),
                        25 => item.GroupAllowConfessTalkEventPack(),
                        26 => item.GroupAllowMemberInviteEventPack(),
                        //27 => item.GroupEntranceAnnouncementChangeEventPack(),
                        28 => item.GroupMessagePostSendEventPack(),
                        29 => item.GroupMessagePreSendEventPack(),
                        30 => item.GroupMuteAllEventPack(),
                        31 => item.GroupNameChangeEventPack(),
                        32 => item.ImageUploadEventAPack(),
                        33 => item.ImageUploadEventBPack(),
                        34 => item.MemberCardChangeEventPack(),
                        35 => item.InviteMemberJoinEventPack(),
                        36 => item.MemberJoinEventPack(),
                        37 => item.MemberJoinRequestEventPack(),
                        38 => item.MemberLeaveEventAPack(),
                        39 => item.MemberLeaveEventBPack(),
                        40 => item.MemberMuteEventPack(),
                        41 => item.MemberPermissionChangeEventPack(),
                        42 => item.MemberSpecialTitleChangeEventPack(),
                        43 => item.MemberUnmuteEventPack(),
                        44 => item.MessageRecallEventAPack(),
                        45 => item.MessageRecallEventBPack(),
                        46 => item.NewFriendRequestEventPack(),
                        47 => item.TempMessagePostSendEventPack(),
                        48 => item.TempMessagePreSendEventPack(),
                        49 => item.GroupMessageEventPack(),
                        50 => item.TempMessageEventPack(),
                        51 => item.FriendMessageEventPack(),
                        55 => item.ListGroupPack(),
                        56 => item.ListFriendPack(),
                        57 => item.ListMemberPack(),
                        58 => item.GroupSettingPack(),
                        72 => item.FriendInputStatusChangedEventPack(),
                        73 => item.FriendNickChangedEventPack(),
                        79 => item.MemberJoinRetrieveEventPack(),
                        80 => item.BotJoinGroupEventRetrieveEventPack(),
                        81 => item.NudgedEventPack(),
                        82 => item.NudgedEventPack(),
                        85 => item.GroupTalkativeChangePack(),
                        86 => item.OtherClientOnlineEventPack(),
                        87 => item.OtherClientOfflineEventPack(),
                        88 => item.OtherClientMessageEventPack(),
                        89 => item.GroupMessageSyncEventPack(),
                        90 => item.GetImageUrlPack(),
                        91 => item.MemberInfoPack(),
                        92 => item.FriendInfoPack(),
                        101 => item.GroupFilesPack(),
                        109 => item.GroupAnnouncementsPack(),
                        113 => item.GroupDisbandPack(),
                        116 => item.StrangerMessageEventPack(),
                        122 => item.StrangerMessagePreSendEventPack(),
                        123 => item.StrangerMessagePostSendEventPack(),
                        124 => item.StrangerRelationChangePack(),
                        125 => item.StrangerRelationChangePack(),
                        128 => item.ReFriendGroupPack(),
                        129 => item.ReListFriendGroupPack(),
                        _ => null
                    };

                    Robot.AddRead(pack, index);
                }
                else if (Robot.Config.Check && time >= 20)
                {
                    time = 0;
                    AddSend(null, 60);
                }
                else if (queue1.TryTake(out PackTask item1))
                {
                    IByteBuffer pack = item1.index switch
                    {
                        52 => (item1.pack as SendGroupMessagePack).ToPack(),
                        53 => (item1.pack as SendGroupPrivateMessagePack).ToPack(),
                        54 => (item1.pack as SendFriendMessagePack).ToPack(),
                        55 => (item1.pack as GetPack).ToPack(55),
                        56 => (item1.pack as GetPack).ToPack(56),
                        57 => (item1.pack as GroupGetMemberInfoPack).ToPack(),
                        58 => (item1.pack as GroupGetSettingPack).ToPack(),
                        59 => (item1.pack as EventCallPack).ToPack(),
                        60 => PackEncode.TestPack(),
                        61 => (item1.pack as SendGroupImagePack).ToPack(),
                        62 => (item1.pack as SendGroupPrivateImagePack).ToPack(),
                        63 => (item1.pack as SendFriendImagePack).ToPack(),
                        64 => (item1.pack as GroupKickMemberPack).ToPack(),
                        65 => (item1.pack as GroupMuteMemberPack).ToPack(),
                        66 => (item1.pack as GroupUnmuteMemberPack).ToPack(),
                        67 => (item1.pack as GroupMuteAllPack).ToPack(),
                        68 => (item1.pack as GroupUnmuteAllPack).ToPack(),
                        69 => (item1.pack as GroupSetMemberCardPack).ToPack(),
                        70 => (item1.pack as GroupSetNamePack).ToPack(),
                        71 => (item1.pack as ReCallMessagePack).ToPack(),
                        74 => (item1.pack as SendGroupSoundPack).ToPack(),
                        75 => (item1.pack as SendGroupImageFilePack).ToPack(),
                        76 => (item1.pack as SendGroupPrivateImageFilePack).ToPack(),
                        77 => (item1.pack as SendFriendImageFilePack).ToPack(),
                        78 => (item1.pack as SendGroupSoundFilePack).ToPack(),
                        83 => (item1.pack as SendFriendNudgePack).ToPack(),
                        84 => (item1.pack as SendGroupMemberNudgePack).ToPack(),
                        90 => (item1.pack as GetImageUrlPack).ToPack(),
                        91 => (item1.pack as GetMemberInfoPack).ToPack(),
                        92 => (item1.pack as GetFriendInfoPack).ToPack(),
                        93 => (item1.pack as SendMusicSharePack).ToPack(),
                        94 => (item1.pack as GroupSetEssenceMessagePack).ToPack(),
                        95 => (item1.pack as MessageBuffPack).ToPack(),
                        96 => (item1.pack as SendFriendDicePack).ToPack(),
                        97 => (item1.pack as SendGroupDicePack).ToPack(),
                        98 => (item1.pack as SendGroupPrivateDicePack).ToPack(),
                        99 => (item1.pack as GroupAddFilePack).ToPack(),
                        100 => (item1.pack as GroupDeleteFilePack).ToPack(),
                        101 => (item1.pack as GroupGetFilesPack).ToPack(),
                        102 => (item1.pack as GroupMoveFilePack).ToPack(),
                        103 => (item1.pack as GroupRenameFilePack).ToPack(),
                        104 => (item1.pack as GroupAddDirPack).ToPack(),
                        105 => (item1.pack as GroupDeleteDirPack).ToPack(),
                        106 => (item1.pack as GroupRenameDirPack).ToPack(),
                        107 => (item1.pack as GroupDownloadFilePack).ToPack(),
                        108 => (item1.pack as GroupSetAdminPack).ToPack(),
                        109 => (item1.pack as GroupGetAnnouncementsPack).ToPack(),
                        110 => (item1.pack as GroupAddAnnouncementPack).ToPack(),
                        111 => (item1.pack as GroupDeleteAnnouncementPack).ToPack(),
                        112 => (item1.pack as SendFriendSoundFilePack).ToPack(),
                        114 => (item1.pack as GroupSetAllowMemberInvitePack).ToPack(),
                        115 => (item1.pack as GroupSetAnonymousChatEnabledPack).ToPack(),
                        117 => (item1.pack as SendStrangerMessagePack).ToPack(),
                        118 => (item1.pack as SendStrangerImageFilePack).ToPack(),
                        119 => (item1.pack as SendStrangerDicePack).ToPack(),
                        120 => (item1.pack as SendStrangerNudgePack).ToPack(),
                        121 => (item1.pack as SendStrangerSoundFilePack).ToPack(),
                        126 => (item1.pack as SendFriendSoundPack).ToPack(),
                        128 => (item1.pack as GetFriendGroupPack).ToPack(),
                        129 => (item1.pack as GetPack).ToPack(129),
                        130 => (item1.pack as FriendGroupCreatePack).ToPack(),
                        131 => (item1.pack as FriendGroupRenamePack).ToPack(),
                        132 => (item1.pack as FriendGroupMovePack).ToPack(),
                        133 => (item1.pack as FriendGroupDeletePack).ToPack(),
                        134 => (item1.pack as GroupMemberEditSpecialTitlePack).ToPack(),
                        _ => null
                    };
                    if (pack != null)
                    {
                        client.WriteAndFlushAsync(pack);
                    }
                }
                time++;
            }
            catch (Exception e)
            {
                Robot.IsConnect = false;
                Robot.Config.StateAction(StateType.Disconnect);
                if (Robot.IsFirst)
                {
                    Robot.IsRun = false;
                    Robot.LogError("机器人连接失败");
                }
                else
                {
                    Robot.Times++;
                    if (Robot.Times == 10)
                    {
                        Robot.IsRun = false;
                        Robot.LogError("重连失败次数过多");
                    }
                    Robot.LogError("机器人连接失败");
                    Robot.LogError(e);
                    Robot.IsConnect = false;
                    Robot.LogError($"机器人{Robot.Config.Time}毫秒后重连");
                    Thread.Sleep(Robot.Config.Time);
                    Robot.LogError("机器人重连中");
                }
            }
        }
    }

    public void AddSend(PackBase pack, int index)
    {
        queue1.Add(new PackTask
        {
            pack = pack,
            index = index
        });
    }

    internal void AddReadPack(IByteBuffer pack)
    {
        queue2.Add(pack);
    }

    public void ReConnect()
    {
        if (client != null)
        {
            client.CloseAsync().Wait();
            client.DeregisterAsync().Wait();
        }

        queue2.Clear();

        Robot.Config.StateAction(StateType.Connecting);

        client = bootstrap.ConnectAsync(Robot.Config.IP, Robot.Config.Port).Result;

        var pack = Robot.PackStart.ToPack();

        client.WriteAndFlushAsync(pack).Wait();

        while (!queue2.TryTake(out pack))
        {
            Thread.Sleep(10);
        }

        Robot.QQs = pack.StartPack();

        queue1.Clear();
        Robot.LogOut("机器人已连接");
        Robot.IsConnect = true;
    }

    public void SendStop()
    {
        IByteBuffer buff = Unpooled.Buffer();
        buff.WriteInt(127);
        client.WriteAndFlushAsync(buff).Wait();
    }

    public void StartRead()
    {
        bootstrap = new Bootstrap();
        bootstrap
            .Group(group)
            .Channel<TcpSocketChannel>()
            .Handler(new ActionChannelInitializer<ISocketChannel>(channel =>
            {
                IChannelPipeline pipeline = channel.Pipeline;
                pipeline.AddLast(new LengthFieldPrepender(4))
                        .AddLast(new LengthFieldBasedFrameDecoder(1024 * 500, 0, 4, 0, 4))
                        .AddLast(new ClientHandler(this));
            }));

        thread.Start();
    }

    public void Stop()
    {
        Robot.IsConnect = false;
        Robot.Config.StateAction(StateType.Disconnect);
        client?.CloseAsync().Wait();
    }
}

internal class ClientHandler : ChannelHandlerAdapter
{
    private readonly ColorMiraiNetty netty;
    internal ClientHandler(ColorMiraiNetty netty)
    {
        this.netty = netty;
    }

    public override void ChannelRead(IChannelHandlerContext context, object message)
    {
        var byteBuffer = message as IByteBuffer;
        netty.AddReadPack(byteBuffer);
    }

    public override void ChannelInactive(IChannelHandlerContext context)
    {
        netty.Robot.IsConnect = false;
        netty.Robot.Config.StateAction(StateType.Disconnect);
    }

    public override void ChannelReadComplete(IChannelHandlerContext context) => context.Flush();

    public override void ExceptionCaught(IChannelHandlerContext context, Exception exception)
    {
        netty.Robot.IsConnect = false;
        netty.Robot.Config.StateAction(StateType.Disconnect);
        context.CloseAsync();
    }
}
