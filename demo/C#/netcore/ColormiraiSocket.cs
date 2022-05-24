using Newtonsoft.Json;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace ColoryrSDK;

public class ColorMiraiSocket : IColorMiraiPipe
{
    private ConcurrentBag<byte[]> queue;
    private Socket socket;
    private RobotSDK robot;
    private Thread thread;

    public ColorMiraiSocket(RobotSDK robot)
    {
        this.robot = robot;
        queue = new();
        thread = new(Read);
    }

    private void Read()
    {
        int time = 0;
        while (robot.IsRun)
        {
            try
            {
                if (!robot.IsConnect)
                {
                    ReConnect();
                    robot.IsFirst = false;
                    robot.Times = 0;
                    robot.RobotStateEvent.Invoke(StateType.Connect);
                }
                else if (socket.Available > 0)
                {
                    var data = new byte[socket.Available];
                    socket.Receive(data);
                    byte index = data[^1];
                    data[^1] = 0;
                    var data1 = Encoding.UTF8.GetString(data);
                    if (RobotSDK.PackType.TryGetValue(index, out var type))
                    {
                        robot.AddRead(JsonConvert.DeserializeObject(data1, type), index);
                    }
                }
                else if (robot.Config.Check && time >= 20)
                {
                    time = 0;
                    AddSend(null, 60);
                }
                else if (queue.TryTake(out byte[] Send))
                {
                    socket.Send(Send);
                }
                time++;
                Thread.Sleep(50);
            }
            catch (Exception e)
            {
                robot.IsConnect = false;
                robot.RobotStateEvent.Invoke(StateType.Disconnect);
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

    public void AddSend(PackBase pack, byte index)
    {
        byte[] temp;
        if (index == 60)
        {
            temp = BuildPack.Build(new object(), 60);
        }
        else if (index == 61)
        {
            var pack1 = pack as SendGroupImagePack;
            temp = BuildPack.BuildImage(pack1.qq, pack1.id, 0, Convert.ToBase64String(pack1.data), 61, pack1.ids);
        }
        else if (index == 62)
        {
            var pack1 = pack as SendGroupPrivateImagePack;
            temp = BuildPack.BuildImage(pack1.qq, pack1.id, pack1.fid, Convert.ToBase64String(pack1.data), 62, null);
        }
        else if (index == 63)
        {
            var pack1 = pack as SendFriendImagePack;
            temp = BuildPack.BuildImage(pack1.qq, pack1.id, 0, Convert.ToBase64String(pack1.data), 63, pack1.ids);
        }
        else if (index == 74)
        {
            var pack1 = pack as SendFriendImagePack;
            temp = BuildPack.BuildSound(pack1.qq, pack1.id, Convert.ToBase64String(pack1.data), 74, pack1.ids);
        }
        else if (index == 95)
        {
            var pack1 = pack as MessageBuffPack;
            pack1.imgData = null;
            temp = BuildPack.Build(pack1, 95);
        }
        else if (index == 126)
        {
            var pack1 = pack as SendFriendSoundPack;
            temp = BuildPack.BuildSound(pack1.qq, pack1.id, Convert.ToBase64String(pack1.data), 126, pack1.ids);
        }
        else
        {
            temp = BuildPack.Build(pack, index);
        }

        queue.Add(temp);
    }

    public void StartRead()
    {
        thread.Start();
    }
    public void ReConnect()
    {
        if (socket != null)
            socket.Close();

        robot.RobotStateEvent.Invoke(StateType.Connecting);

        socket = new(SocketType.Stream, ProtocolType.Tcp);
        socket.Connect(robot.Config.IP, robot.Config.Port);

        var data = Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(robot.PackStart) + " ");
        data[^1] = 0;

        socket.Send(data);

        while (socket.Available == 0)
        {
            Thread.Sleep(10);
        }

        data = new byte[socket.Available];
        socket.Receive(data);
        string temp = Encoding.UTF8.GetString(data, 0, data.Length - 1);
        robot.QQs = JsonConvert.DeserializeObject<List<long>>(temp);

        queue.Clear();
        robot.LogOut("机器人已连接");
        robot.IsConnect = true;
    }

    public void SendStop()
    {
        if (!robot.IsConnect)
            return;
        var data = BuildPack.Build(new object(), 127);
        socket.Send(data);
    }

    public void Stop()
    {
        SendStop();
        if (socket != null)
            socket.Close();
    }
}
