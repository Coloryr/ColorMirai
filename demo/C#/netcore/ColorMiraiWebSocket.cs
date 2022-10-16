using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Text;
using System.Threading;
using WebSocketSharp;

namespace ColoryrSDK;

internal class ColorMiraiWebSocket : IColorMiraiPipe
{
    private record PackTask
    {
        public PackBase pack;
        public int index;
    }
    private ConcurrentBag<PackTask> queue1;
    private ConcurrentBag<string> queue2;
    private RobotSDK robot;
    private Thread thread;
    private WebSocket client;

    public ColorMiraiWebSocket(RobotSDK robot)
    {
        this.robot = robot;
        queue1 = new();
        queue2 = new();
        thread = new(Read);
    }

    private void Read()
    {
        int time = 0;
        while (robot.IsRun)
        {
            try
            {
                Thread.Sleep(50);
                if (!robot.IsConnect)
                {
                    ReConnect();
                    robot.IsFirst = false;
                    robot.Times = 0;
                    robot.Config.StateAction(StateType.Connect);
                }
                else if (queue2.TryTake(out var item))
                {
                    if (item == null)
                    {
                        continue;
                    }
                    JObject obj = JObject.Parse(item);
                    byte index = (byte)obj["index"];
                    if (index == 60)
                        continue;
                    var obj1 = obj["pack"];
                    if (RobotSDK.PackType.TryGetValue(index, out var type))
                    {
                        robot.AddRead(obj1.ToObject(type), index);
                    }
                }
                else if (robot.Config.Check && time >= 20)
                {
                    time = 0;
                    AddSend(null, 60);
                }
                else if (queue1.TryTake(out PackTask item1))
                {
                    client.Send(JsonConvert.SerializeObject(item1));
                }
                time++;
            }
            catch (Exception e)
            {
                robot.IsConnect = false;
                robot.Config.StateAction(StateType.Disconnect);
                if (robot.IsFirst)
                {
                    robot.IsRun = false;
                    robot.LogError("机器人连接失败");
                }
                else
                {
                    robot.Times++;
                    if (robot.Times == 10)
                    {
                        robot.IsRun = false;
                        robot.LogError("重连失败次数过多");
                    }
                    robot.LogError("机器人连接失败");
                    robot.LogError(e);
                    robot.IsConnect = false;
                    robot.LogError($"机器人{robot.Config.Time}毫秒后重连");
                    Thread.Sleep(robot.Config.Time);
                    robot.LogError("机器人重连中");
                }
            }
        }
    }

    private void OnData(object sender, MessageEventArgs message)
    {
        string temp = null;
        if (message.IsText)
        {
            temp = message.Data;
        }
        else if (message.IsBinary)
        {
            temp = Encoding.UTF8.GetString(message.RawData);
        }

        queue2.Add(temp);
    }

    public void AddSend(PackBase pack, int index)
    {
        queue1.Add(new PackTask
        {
            pack = pack,
            index = index
        });
    }

    public void ReConnect()
    {
        if (client != null)
            client.Close();

        queue2.Clear();

        robot.Config.StateAction(StateType.Connecting);

        string temp = JsonConvert.SerializeObject(new PackTask
        {
            pack = robot.PackStart,
            index = 0
        });

        client = new WebSocket(robot.Config.IP);
        client.Connect();
        client.OnClose += Client_OnClose;
        client.OnMessage += OnData;

        client.Send(temp);

        while (!queue2.TryTake(out temp))
        {
            Thread.Sleep(10);
        }

        robot.QQs = JsonConvert.DeserializeObject<List<long>>(temp);

        queue1.Clear();
        robot.LogOut("机器人已连接");
        robot.IsConnect = true;
    }

    private void Client_OnClose(object sender, CloseEventArgs e)
    {
        robot.IsConnect = false;
        robot.Config.StateAction(StateType.Disconnect);
    }

    public void SendStop()
    {
        client.Send(JsonConvert.SerializeObject(new PackTask
        {
            index = 127
        }));
    }

    public void StartRead()
    {
        thread.Start();
    }

    public void Stop()
    {
        client.Close();
    }
}
