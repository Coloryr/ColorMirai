using System;
using ColoryrSDK;
using Newtonsoft.Json;

Robot robot = new();

void Message(byte type, string data)
{
    switch (type)
    {
        case 46:
            {
                var pack = JsonConvert.DeserializeObject<NewFriendRequestEventPack>(data);
                var temp = BuildPack.Build(new EventCallPack
                {
                    eventid = pack.eventid,
                    dofun = 0,
                }, 59);
                robot.AddTask(temp);
                break;
            }
        case 49:
            {
                var pack = JsonConvert.DeserializeObject<GroupMessageEventPack>(data);
                var temp = BuildPack.Build(new SendGroupMessagePack
                {
                    qq = robot.QQs[0],
                    id = pack.id,
                    message = new()
                    {
                        $"{pack.fid} 你发送了消息 {pack.message[^1]}"
                    }
                }, 52);
                robot.AddTask(temp);
                break;
            }
        case 50:

            break;
        case 51:

            break;
        case 101:
            Console.WriteLine(data);
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
    Port = 23333,
    Name = "test",
    Pack = new() { 46, 49, 50, 51 },
    RunQQ = 0,
    Time = 10000,
    CallAction = Message,
    LogAction = Log,
    StateAction = State
};

robot.Set(config);
robot.Start();

while (!robot.IsConnect) ;

while (true)
{
    string temp = Console.ReadLine();
    switch (temp)
    {

    }
}