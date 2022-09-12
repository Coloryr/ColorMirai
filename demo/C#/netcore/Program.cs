using ColoryrSDK;
using System;

const int type = 1;
const long qq = 2315986522;

RobotSDK robot = new();

void Message(byte type, object data)
{
    switch (type)
    {
        case 46:
            {
                var pack = data as NewFriendRequestEventPack;
                robot.NewFriendRequestCall(pack.qq, pack.eventid, RobotSDK.FriendCallType.accept);
                break;
            }
        case 49:
            {
                var pack = data as GroupMessageEventPack;
                if (pack.id != 571239090)
                    break;
                Console.WriteLine($"id = {pack.id}");
                Console.WriteLine($"fid = {pack.fid}");
                Console.WriteLine($"message = ");
                foreach (var item in pack.message)
                {
                    Console.WriteLine(item);
                }
                Console.WriteLine();
                robot.SendGroupMessage(pack.qq, pack.id, new() { $"{pack.fid} 你发送了消息 {pack.message[^1]}" });

                if (pack.permission == MemberPermission.MEMBER)
                {
                    if (pack.message[^1] == "撤回")
                    {
                        robot.SendGroupMessage(pack.qq, pack.id, new() { $"撤回消息" });
                        robot.ReCallMessage(pack.qq, pack.ids1, pack.ids2, MessageSourceKind.GROUP);
                    }
                }
                else if (pack.message[^1] == "精华")
                {
                    robot.SendGroupMessage(pack.qq, pack.id, new() { $"设置精华消息" });
                    robot.GroupSetEssenceMessage(pack.qq, pack.id, pack.ids1, pack.ids2);
                }
                else if (pack.message[^1] == "回复")
                {
                    robot.SendGroupMessage(pack.qq, pack.id, new()
                    {
                        RobotSDK.BuildQuoteReply(pack),
                        "回复消息"
                    });
                }

                break;
            }
        case 50:
            {
                var pack = data as TempMessageEventPack;

            }
            break;
        case 51:
            {
                var pack = data as FriendMessageEventPack;

            }
            break;
        case 116:

            break;
    }
}

void Log(LogType type, string data)
{
    Console.WriteLine($"日志:{type} {data}");
}

void State(StateType type)
{
    Console.WriteLine($"日志:{type}");
}

RobotConfig config = new()
{
    IP = "127.0.0.1",
    Name = "test",
    Pack = new() { 46, 49, 50, 51, 116 },
    RunQQ = 0,
    Time = 10000,
    CallAction = Message,
    LogAction = Log,
    StateAction = State
};

robot.Set(config);
if (type == 1)
{
    config.Port = 23335;
    robot.SetPipe(new ColorMiraiNetty(robot));
}
else if (type == 2)
{
    config.IP = "ws://127.0.0.1:23334";
    robot.SetPipe(new ColorMiraiWebSocket(robot));
}


robot.Start();

while (!robot.IsConnect) ;

while (true)
{
    string temp = Console.ReadLine();
    string[] arg = temp.Split(' ');
    switch (arg[0])
    {
        case "tests":
            robot.GetFriends(qq, (res) =>
            {
                Console.WriteLine($"{res.qq}的好友：");
                foreach (var item in res.friends)
                {
                    Console.WriteLine($"{item.id} {item.remark}");
                }
            });
            robot.GetGroups(qq, (res) =>
            {
                Console.WriteLine($"{res.qq}的群：");
                foreach (var item in res.groups)
                {
                    Console.WriteLine($"{item.id} {item.name}");
                }
                long group = res.groups[0].id;
                robot.GetMembers(qq, group, (res) =>
                {
                    Console.WriteLine($"{res.qq}的群{group}的群员：");
                    foreach (var item in res.members)
                    {
                        Console.WriteLine($"{item.id} {item.nick}");
                    }
                });
            });
            break;
        case "stop":
            robot.Stop();
            return;
    }
}