using ColoryrSDK;
using System;

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
                break;
            }
        case 50:

            break;
        case 51:

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
    IP = "ws://127.0.0.1:23334",
    Port = 23333,
    Name = "test",
    Pack = new() { 46, 49, 50, 51, 116 },
    RunQQ = 0,
    Time = 10000,
    CallAction = Message,
    LogAction = Log,
    StateAction = State
};

robot.Set(config);
robot.SetPipe(new ColorMiraiWebSocket(robot));
robot.Start();

while (!robot.IsConnect) ;

while (true)
{
    string temp = Console.ReadLine();
    string[] arg = temp.Split(' ');
    switch (arg[0])
    {
        case "friends":
            if (arg.Length != 2)
            {
                Console.WriteLine("错误的参数");
                break;
            }
            if (!long.TryParse(arg[1], out long qq))
            {
                Console.WriteLine("错误的参数");
                break;
            }
            robot.GetFriends(qq, (res) =>
            {
                Console.WriteLine($"{res.qq}的好友：");
                foreach (var item in res.friends)
                {
                    Console.WriteLine($"{item.id} {item.remark}");
                }
            });
            break;
        case "groups":
            break;
        case "members":
            break;
        case "stop":
            robot.Stop();
            return;
    }
}