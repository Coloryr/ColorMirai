# ColorMirai

[返回](../README.md)

## [基础底包](../src/main/java/Color_yr/ColorMirai/Pack/PackBase.java)

如果你插件没有设置运行QQ，则需要填写`qq`号

## 机器人发送给插件

### [1 [机器人]图片上传前. 可以阻止上传（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/BeforeImageUploadPack.java)

```
public long id { get; set; }
public string name { get; set; }
```

- `id`：发送给的号码
- `name`：图片UUID

### [2 [机器人]头像被修改（通过其他客户端修改了头像）（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/BotAvatarChangedPack.java)

```
什么都没有
```

### [3 [机器人]在群里的权限被改变. 操作人一定是群主（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/BotGroupPermissionChangePack.java)

```
public MemberPermission now { get; set; }
public MemberPermission old { get; set; }
public long id { get; set; }
```

- `now`：当前权限
- `old`：旧的权限
- `id`：群号

### [4 [机器人]被邀请加入一个群（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/BotInvitedJoinGroupRequestEventPack.java)

```
public long eventid { get; set; }
public long id { get; set; }
public long fid { get; set; }
```

- `eventid`：事件ID
- `id`：群号
- `fid`：邀请人QQ号

### [5 [机器人]成功加入了一个新群（不确定. 可能是主动加入）（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/BotJoinGroupEventAPack.java)

```
public long id { get; set; }
```

- `id`：群号

### [6 [机器人]成功加入了一个新群（机器人被一个群内的成员直接邀请加入了群）（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/BotJoinGroupEventBPack.java)

```
public long id { get; set; }
public long fid { get; set; }
```

- `id`：群号
- `fid`：邀请人QQ

### [7 [机器人]主动退出一个群（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/BotLeaveEventAPack.java)

```
public long id { get; set; }
```

- `id`：群号

### [8 [机器人]被管理员或群主踢出群（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/BotLeaveEventBPack.java)

```
public long id { get; set; }
public long fid { get; set; }
```

- `id`：群号
- `fid`：执行人QQ

### [9 [机器人]被禁言（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/BotMuteEventPack.java)

```
public long id { get; set; }
public long fid { get; set; }
public int time { get; set; }
```

- `id`：群号
- `fid`：执行人QQ
- `time`：禁言时间

### [10 [机器人]主动离线（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/BotOfflineEventAPack.java)
### [12 [机器人]被服务器断开（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/BotOfflineEventAPack.java)
### [13 [机器人]因网络问题而掉线（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/BotOfflineEventAPack.java)

```
public string message { get; set; }
```

- `message`：离线原因

### [11 [机器人]被挤下线（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/BotOfflineEventBPack.java)

```
public string message { get; set; }
public string title { get; set; }
```

- `message`：离线原因
- `title`：标题

### [14 [机器人]服务器主动要求更换另一个服务器（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/BotOfflineEventCPack.java)

```
什么都没有
```

### [15 [机器人]登录完成, 好友列表, 群组列表初始化完成（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/BotOnlineEventPack.java)

```
什么都没有
```

### [16 [机器人]主动或被动重新登录（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/BotReloginEventPack.java)

```
public string message { get; set; }
```

- `message`：原因消息

### [17 [机器人]被取消禁言（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/BotUnmuteEventPack.java)

```
public long id { get; set; }
public long fid { get; set; }
```

- `id`：群号
- `fid`：执行人QQ

### [18 [机器人]成功添加了一个新好友（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/FriendAddEventPack.java)

```
public long id { get; set; }
public string nick { get; set; }
```

- `id`：好友QQ号
- `nick`：昵称

### [19 [机器人]好友头像修改（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/FriendAvatarChangedEventPack.java)

```
public string url { get; set; }
public long id { get; set; }
```

- `url`：图片url
- `id`：好友QQ号

### [20 [机器人]好友已被删除（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/FriendDeleteEventPack.java)

```
public long id { get; set; }
```

- `id`：好友QQ号

### [21 [机器人]在好友消息发送后广播（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/FriendMessagePostSendEventPack.java)

```
public List<string> message { get; set; }
public long id { get; set; }
public bool res { get; set; }
public string error { get; set; }
```

- `message`：消息
- `id`：好友QQ号
- `res`：是否成功发送
- `error`：错误消息
- 
### [22 [机器人]在发送好友消息前广播（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/FriendMessagePreSendEventPack.java)

```
public List<string> message { get; set; }
public long id { get; set; }
```

- `message`：消息
- `id`：好友QQ号

### [23 [机器人]好友昵称改变（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/FriendRemarkChangeEventPack.java)

```
public long id { get; set; }
public string old { get; set; }
public string now { get; set; }
```

- `id`：好友QQ号
- `old`：旧的昵称
- `now`：新的昵称

### [24 [机器人]群 "匿名聊天" 功能状态改变（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/GroupAllowAnonymousChatEventPack.java)

```
public long id { get; set; }
public long fid { get; set; }
public bool old { get; set; }
public bool now { get; set; }
```

- `id`：群号
- `fid`：好友QQ号
- `old`：旧的状态
- `now`：新的状态

### [25 [机器人]群 "坦白说" 功能状态改变（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/GroupAllowConfessTalkEventPack.java)

```
public long id { get; set; }
public bool old { get; set; }
public bool now { get; set; }
```

- `id`：群号
- `old`：旧的状态
- `now`：新的状态

### [26 [机器人]群 "允许群员邀请好友加群" 功能状态改变（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/GroupAllowMemberInviteEventPack.java)

```
public long id { get; set; }
public long fid { get; set; }
public bool old { get; set; }
public bool now { get; set; }
```

- `id`：群号
- `fid`：执行人QQ号
- `old`：旧的状态
- `now`：新的状态

### [27 [机器人]入群公告改变（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/GroupEntranceAnnouncementChangeEventPack.java)

```
public long id { get; set; }
public long fid { get; set; }
public string old { get; set; }
public string now { get; set; }
```

- `id`：群号
- `fid`：执行人QQ号
- `old`：旧的状态
- `now`：新的状态

### [28 [机器人]在群消息发送后广播（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/GroupMessagePostSendEventPack.java)

```
public long id { get; set; }
public bool res { get; set; }
public List<string> message { get; set; }
public string error { get; set; }
```

- `id`：群号
- `res`：是否发送成功
- `message`：发送的消息
- `error`：错误消息

### [28 [机器人]在群消息发送后广播（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/GroupMessagePostSendEventPack.java)

```
public long id { get; set; }
public bool res { get; set; }
public List<string> message { get; set; }
public string error { get; set; }
```

- `id`：群号
- `res`：是否发送成功
- `message`：发送的消息
- `error`：错误消息

### [29 [机器人]在发送群消息前广播（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/GroupMessagePreSendEventPack.java)

```
public long id { get; set; }
public List<string> message { get; set; }
```

- `id`：群号
- `message`：消息

### [30 [机器人]群 "全员禁言" 功能状态改变（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/GroupMuteAllEventPack.java)

```
public long id { get; set; }
public long fid { get; set; }
public bool old { get; set; }
public bool now { get; set; }
```

- `id`：群号
- `fid`：执行人QQ号
- `old`：旧的状态
- `now`：新的状态

### [31 [机器人]群名改变（事件）](../src/main/java/Color_yr/ColorMirai/Pack/ToPlugin/GroupNameChangeEventPack.java)

```
public long id { get; set; }
public long fid { get; set; }
public string old { get; set; }
public string now { get; set; }
```

- `id`：群号
- `fid`：执行人QQ号
- `old`：旧的名字
- `now`：新的名字

待补充.....

## 插件发送给机器人

### [0 [插件]插件开始连接](../src/main/java/Color_yr/ColorMirai/Pack/FromPlugin/StartPack.java)


插件第一次连接需要发送一个数据包来注册所监听事件的包

```
public string Name { get; set; }
public List<byte> Reg { get; set; }
public List<long> Groups { get; set; }
public List<long> QQs { get; set; }
public long RunQQ { get; set; }
```

- `Name`：插件名字
- `Reg`：监听的包，只有标注`（事件）`的包才会被监听
- `Groups`：只监听的群号，可以为null
- `QQs`：只监听的QQ号，可以为null
- `RunQQ`：插件运行的机器人QQ号，可以为0