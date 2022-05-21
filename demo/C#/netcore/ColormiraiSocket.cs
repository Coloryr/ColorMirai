using Newtonsoft.Json;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace ColoryrSDK;

public class ColormiraiSocket : IColormiraiPipe
{
    private ConcurrentBag<byte[]> QueueSend;
    private Socket Socket;
    private RobotSDK Robot;
    private Thread ReadThread;

    public ColormiraiSocket(RobotSDK robot) 
    {
        Robot = robot;
        QueueSend = new();
        ReadThread = new(Read);
    }

    private void Read() 
    {
        int time = 0;
        while (Robot.IsRun)
        {
            try
            {
                if (!Robot.IsConnect)
                {
                    ReConnect();
                    Robot.IsFirst = false;
                    Robot.Times = 0;
                    Robot.RobotStateEvent.Invoke(StateType.Connect);
                }
                else if (Socket.Available > 0)
                {
                    var data = new byte[Socket.Available];
                    Socket.Receive(data);
                    data[^1] = 0;
                    var data1 = Encoding.UTF8.GetString(data);
                    byte index = data[^1];
                    if (RobotSDK.PackType.TryGetValue(index, out var type))
                    {
                        Robot.AddRead(JsonConvert.DeserializeObject(data1, type), index);
                    }
                }
                else if (Robot.Config.Check && time >= 20)
                {
                    time = 0;
                    AddPack(null, 60);
                }
                else if (QueueSend.TryTake(out byte[] Send))
                {
                    Socket.Send(Send);
                }
                time++;
                Thread.Sleep(50);
            }
            catch (Exception e)
            {
                Robot.IsConnect = false;
                Robot.RobotStateEvent.Invoke(StateType.Disconnect);
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

    public void AddPack(PackBase pack, byte index)
    {
        byte[] temp = Array.Empty<byte>();
        if (index == 60)
        {
            temp = BuildPack.Build(new object(), 60);
        }
        else if (index == 61) 
        {
            var pack1 = pack as SendGroupImagePack;
            temp = BuildPack.BuildImage(pack1.qq, pack1.id, 0, Convert.ToBase64String(pack1.data), 61, pack1.ids);
        }

        QueueSend.Add(temp);
    }

    public void StartRead() 
    {
        
    }
    public void ReConnect()
    {
        if (Socket != null)
            Socket.Close();

        Robot.RobotStateEvent.Invoke(StateType.Connecting);

        Socket = new(SocketType.Stream, ProtocolType.Tcp);
        Socket.Connect(Robot.Config.IP, Robot.Config.Port);

        var data = Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(Robot.PackStart) + " ");
        data[^1] = 0;

        Socket.Send(data);

        while (Socket.Available == 0)
        {
            Thread.Sleep(10);
        }

        data = new byte[Socket.Available];
        Socket.Receive(data);
        string temp = Encoding.UTF8.GetString(data, 0, data.Length - 1);
        Robot.QQs = JsonConvert.DeserializeObject<List<long>>(temp);

        QueueSend.Clear();
        Robot.LogOut("机器人已连接");
        Robot.IsConnect = true;
    }

    public void SendStop()
    {
        if (!Robot.IsConnect)
            return;
        var data = BuildPack.Build(new object(), 127);
        Socket.Send(data);
    }

    public void Stop() 
    {
        SendStop();
        if (Socket != null)
            Socket.Close();
    }
}
