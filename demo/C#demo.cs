using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace netcore
{
    class PackBase
    {
        public long qq { get; set; }
    }
    class PackStart: PackBase
    {
        public string Name { get; set; }
        public List<byte> Reg { get; set; }
    }
    class GroupMessageEventPack : PackBase
    {
        public long id { get; set; }
        public long fid { get; set; }
        public string name { get; set; }
        public List<string> message { get; set; }
    }

    class SendGroupMessagePack : PackBase
    {
        public long id { get; set; }
        public List<string> message { get; set; }
    }
    class SendFriendMessagePack : PackBase
    {
        public long id { get; set; }
        public List<string> message { get; set; }
    }
    class SendGroupPrivateMessagePack : PackBase
    {
        public long id { get; set; }
        public long fid { get; set; }
        public List<string> message { get; set; }
    }
    class TempMessageEventPack : PackBase
    {
        public long id { get; set; }
        public long fid { get; set; }
        public string name { get; set; }
        public List<string> message { get; set; }
        public int time { get; set; }
    }
    class FriendMessageEventPack : PackBase
    {
        public long id { get; set; }
        public string name { get; set; }
        public List<string> message { get; set; }
        public int time { get; set; }
    }
    class SendGroupImagePack : PackBase
    {
        public long id { get; set; }
        public string img { get; set; }
    }
    class SendGroupPrivateImagePack : PackBase
    {
        public long id { get; set; }
        public long fid { get; set; }
        public string img { get; set; }
    }
    class SendFriendImagePack : PackBase
    {
        public long id { get; set; }
        public string img { get; set; }
    }
    class GroupMuteAll : PackBase
    {
        public long id { get; set; }
    }
    class GroupUnmuteAll : PackBase
    {
        public long id { get; set; }
    }
    class SetGroupMemberCard : PackBase
    {
        public long id { get; set; }
        public long fid { get; set; }
        public string card { get; set; }
    }
    class SetGroupName : PackBase
    {
        public long id { get; set; }
        public string name { get; set; }
    }
    class NewFriendRequestEventPack : PackBase
    {
        public long id { get; set; }
        public long fid { get; set; }
        public string name { get; set; }
        public string message { get; set; }
        public long eventid { get; set; }
    }
    class EventCallPack : PackBase
    {
        public long eventid { get; set; }
        public int dofun { get; set; }
        public List<object> arg { get; set; }
    }
    class ReCallMessage : PackBase
    {
        public long id { get; set; }
    }
    class LoadFileSendToGroupImagePack : PackBase
    {
        public long id { get; set; }
        public string file { get; set; }
    }
    class GroupMessagePostSendEventPack : PackBase
    {
        public long id { get; set; }
        public bool res { get; set; }
        public List<string> message { get; set; }
        public string error { get; set; }
    }
    class BuildPack
    {
        public static byte[] Build(object obj, byte index)
        {
            byte[] data = Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(obj) + " ");
            data[data.Length - 1] = index;
            return data;
        }

        public static byte[] BuildImage(long qq, long id, long fid, string img, byte index)
        {
            string temp = "";
            if (id != 0)
            {
                temp += "id=" + id + "&";
            }
            if (fid != 0)
            {
                temp += "fid=" + fid + "&";
            }
            temp += "qq=" + qq + "&";
            temp += "img=" + img;
            byte[] data = Encoding.UTF8.GetBytes(temp + " ");
            data[data.Length - 1] = index;
            return data;
        }

        public static byte[] BuildSound(long qq, long id, string sound, byte index)
        {
            string temp = "id=" + id + "&qq=" + qq + "&sound=" + sound;
            byte[] data = Encoding.UTF8.GetBytes(temp + " ");
            data[data.Length - 1] = index;
            return data;
        }
    }
    class RobotTask
    {
        public byte index { get; set; }
        public string data { get; set; }
    }
    class ServerMain
    {
        public static void LogError(Exception e)
        {
            string a = "[错误]" + e.ToString();
            Console.WriteLine(a);
        }
        public static void LogError(string a)
        {
            a = "[错误]" + a;
            Console.WriteLine(a);
        }
        public static void LogOut(string a)
        {
            a = "[信息]" + a;
            Console.WriteLine(a);
        }
    }
    class RobotSocket
    {
        private static Socket Socket;
        private static Thread ReadThread;
        private static Thread DoThread;
        private static bool IsRun;
        private static bool IsConnect;
        private static List<long> QQs;
        private static ConcurrentBag<RobotTask> QueueRead;
        private static ConcurrentBag<byte[]> QueueSend;
        private static PackStart PackStart = new PackStart
        {
            Name = "ColoryrSDK",
            Reg = new List<byte>()
            { 28, 46, 49, 50, 51 }
        };
        public static void Start()
        {
            QueueRead = new ConcurrentBag<RobotTask>();
            QueueSend = new ConcurrentBag<byte[]>();
            DoThread = new Thread(() =>
            {
                RobotTask task;
                while (IsRun)
                {
                    try
                    {
                        if (QueueRead.TryTake(out task))
                        {
                            switch (task.index)
                            {
                                case 28:
                                    var pack5 = JsonConvert.DeserializeObject<GroupMessagePostSendEventPack>(task.data);
                                    if (pack5.res && pack5.message[pack5.message.Count - 1] == "3秒后撤回")
                                    {
                                        Task.Run(() =>
                                        {
                                            Thread.Sleep(2900);
                                            string id = Utils.GetString(pack5.message[0], "source:", ",");
                                            var data = BuildPack.Build(new ReCallMessage { id = long.Parse(id) }, 71);
                                            QueueSend.Add(data);
                                        });
                                    }
                                    break;
                                case 46:
                                    var pack3 = JsonConvert.DeserializeObject<NewFriendRequestEventPack>(task.data);
                                    Console.WriteLine("qq = " + pack3.qq);
                                    Console.WriteLine("id = " + pack3.id);
                                    Console.WriteLine("fid = " + pack3.fid);
                                    Console.WriteLine("name = " + pack3.name);
                                    Console.WriteLine("message = " + pack3.message);
                                    Console.WriteLine("eventid = " + pack3.eventid);
                                    Console.WriteLine();
                                    CallEvent(pack3.eventid, 0, null);
                                    break;
                                case 49:
                                    var pack = JsonConvert.DeserializeObject<GroupMessageEventPack>(task.data);
                                    Console.WriteLine("qq = " + pack.qq);
                                    Console.WriteLine("id = " + pack.id);
                                    Console.WriteLine("fid = " + pack.fid);
                                    Console.WriteLine("name = " + pack.name);
                                    Console.WriteLine("message = ");
                                    foreach (var item in pack.message)
                                    {
                                        Console.WriteLine(item);
                                    }
                                    Console.WriteLine();
                                    if (pack.message[pack.message.Count - 1] == "撤回")
                                    {
                                        string id = Utils.GetString(pack.message[0], "source:", ",");
                                        var data = BuildPack.Build(new ReCallMessage { qq = pack.qq, id = long.Parse(id) }, 71);
                                        QueueSend.Add(data);
                                    }
                                    else if (pack.message[pack.message.Count - 1] == "回复")
                                    {
                                        string id = Utils.GetString(pack.message[0], "source:", ",");
                                        var list2 = new List<string>() { "quote:" + id };
                                        list2.Add("回复消息");
                                        SendGroupMessage(pack.qq, pack.id, list2);
                                    }
                                    else if (pack.message[pack.message.Count - 1] == "撤回自己")
                                    {
                                        var list2 = new List<string>() { "3秒后撤回" };
                                        SendGroupMessage(pack.qq, pack.id, list2);
                                    }
                                    break;
                                case 50:
                                    var pack1 = JsonConvert.DeserializeObject<TempMessageEventPack>(task.data);
                                    Console.WriteLine("qq = " + pack1.qq);
                                    Console.WriteLine("id = " + pack1.id);
                                    Console.WriteLine("fid = " + pack1.fid);
                                    Console.WriteLine("name = " + pack1.name);
                                    Console.WriteLine("message = ");
                                    foreach (var item in pack1.message)
                                    {
                                        Console.WriteLine(item);
                                    }
                                    Console.WriteLine();
                                    var list = new List<string>() { pack1.name };
                                    list.AddRange(pack1.message);
                                    SendGroupPrivateMessage(pack1.qq, pack1.id, pack1.fid, list);
                                    break;
                                case 51:
                                    var pack2 = JsonConvert.DeserializeObject<FriendMessageEventPack>(task.data);
                                    Console.WriteLine("id = " + pack2.id);
                                    Console.WriteLine("time = " + pack2.time);
                                    Console.WriteLine("name = " + pack2.name);
                                    Console.WriteLine("message = ");
                                    foreach (var item in pack2.message)
                                    {
                                        Console.WriteLine(item);
                                    }
                                    Console.WriteLine();
                                    var list1 = new List<string>() { pack2.name };
                                    list1.AddRange(pack2.message);
                                    SendFriendMessage(pack2.qq, pack2.id, list1);
                                    break;
                            }
                        }
                        Thread.Sleep(10);
                    }
                    catch (Exception e)
                    {
                        ServerMain.LogError(e);
                    }
                }
            });

            ReadThread = new Thread(() =>
            {
                while (!IsRun)
                {
                    Thread.Sleep(100);
                }
                DoThread.Start();
                byte[] Send;
                int time = 0;
                while (IsRun)
                {
                    try
                    {
                        if (!IsConnect)
                        {
                            ReConnect();
                        }
                        else if (Socket.Available > 0)
                        {
                            var data = new byte[Socket.Available];
                            Socket.Receive(data);
                            var type = data[data.Length - 1];
                            data[data.Length - 1] = 0;
                            QueueRead.Add(new RobotTask
                            {
                                index = type,
                                data = Encoding.UTF8.GetString(data)
                            });
                        }
                        else if (time >= 20)
                        {
                            time = 0;
                            if (Socket.Poll(1000, SelectMode.SelectRead))
                            {
                                ServerMain.LogOut("机器人连接中断");
                                IsConnect = false;
                            }
                        }
                        else if (QueueSend.TryTake(out Send))
                        {
                            Socket.Send(Send);
                        }
                        time++;
                        Thread.Sleep(50);
                    }
                    catch (Exception e)
                    {
                        ServerMain.LogError("机器人连接失败");
                        ServerMain.LogError(e);
                        IsConnect = false;
                        ServerMain.LogError("机器人20秒后重连");
                        Thread.Sleep(20000);
                        ServerMain.LogError("机器人重连中");
                    }
                }
            });
            ReadThread.Start();
            IsRun = true;
            ReadTest();
        }

        private static void ReadTest()
        {
            List<string> list = new List<string>();
            while (true)
            {
                while (!IsConnect)
                {
                    Thread.Sleep(500);
                }
                list.Clear();

                SendGroupImageFile(1092415357, 571239090, @"G:\横的90.jpg");
                Console.WriteLine("输入4行数据后发送");
                list.Add(Console.ReadLine());
                list.Add(Console.ReadLine());
                list.Add(Console.ReadLine());
                list.Add(Console.ReadLine());
                SendGroupMessage(1092415357, 571239090, list);
            }
        }

        private static void ReConnect()
        {
            if (Socket != null)
                Socket.Close();
            try
            {
                Socket = new Socket(SocketType.Stream, ProtocolType.Tcp);
                Socket.Connect(IPAddress.Parse("127.0.0.1"), 23333);

                var data = Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(PackStart) + " ");
                data[data.Length - 1] = 0;

                Socket.Send(data);

                while (Socket.Available == 0)
                {
                    Thread.Sleep(10);
                }

                data = new byte[Socket.Available];
                Socket.Receive(data);
                QQs = JArray.Parse(Encoding.UTF8.GetString(data)).ToObject<List<long>>();

                QueueRead.Clear();
                QueueSend.Clear();
                ServerMain.LogOut("机器人已连接");
                IsConnect = true;
            }
            catch (Exception e)
            {
                ServerMain.LogError("机器人连接失败");
                ServerMain.LogError(e);
            }
        }
        public static void CallEvent(long eventid, int dofun, List<object> arg)
        {
            var data = BuildPack.Build(new EventCallPack { eventid = eventid, dofun = dofun, arg = arg }, 59);
            QueueSend.Add(data);
        }
        public static void SendGroupMessage(long qq, long id, List<string> message)
        {
            var data = BuildPack.Build(new SendGroupMessagePack {qq=qq, id = id, message = message }, 52);
            QueueSend.Add(data);
        }
        public static void SendGroupPrivateMessage(long qq, long id, long fid, List<string> message)
        {
            var data = BuildPack.Build(new SendGroupPrivateMessagePack { qq=qq,id = id, fid = fid, message = message }, 53);
            QueueSend.Add(data);
        }
        public static void SendFriendMessage(long qq, long id, List<string> message)
        {
            var data = BuildPack.Build(new SendFriendMessagePack { qq=qq,id = id, message = message }, 54);
            QueueSend.Add(data);
        }
        public static void SendGroupImage(long qq, long id, string img)
        {
            var data = BuildPack.BuildImage(qq, id, 0, img, 61);
            QueueSend.Add(data);
        }
        public static void SendGroupPrivateImage(long qq, long id, long fid, string img)
        {
            var data = BuildPack.BuildImage(qq, id, fid, img, 62);
            QueueSend.Add(data);
        }
        public static void SendFriendImage(long qq, long id, string img)
        {
            var data = BuildPack.BuildImage(qq, id, 0, img, 63);
            QueueSend.Add(data);
        }

        public static void SendGroupSound(long qq, long id, string sound)
        {
            var data = BuildPack.BuildSound(qq, id, sound, 74);
            QueueSend.Add(data);
        }
        public static void SendGroupImageFile(long qq, long id, string file)
        {
            var data = BuildPack.Build(new LoadFileSendToGroupImagePack { qq = qq, id = id, file = file, }, 75);
            QueueSend.Add(data);
        }
        public static void Stop()
        {
            ServerMain.LogOut("机器人正在断开");
            IsRun = false;
            if (Socket != null)
                Socket.Close();
            ServerMain.LogOut("机器人已断开");
        }
    }
}
