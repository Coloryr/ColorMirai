using Newtonsoft.Json;
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
    class PackStart
    {
        public string Name { get; set; }
        public List<byte> Reg { get; set; }
    }
    class GroupMessageEventPack
    {
        public long id { get; set; }
        public long fid { get; set; }
        public string name { get; set; }
        public List<string> message { get; set; }
    }

    class SendGroupMessagePack
    {
        public long id { get; set; }
        public List<string> message { get; set; }
    }
    class SendFriendMessagePack
    {
        public long id { get; set; }
        public List<string> message { get; set; }
    }
    class SendGroupPrivateMessagePack
    {
        public long id { get; set; }
        public long fid { get; set; }
        public List<string> message { get; set; }
    }
    class TempMessageEventPack
    {
        public long id { get; set; }
        public long fid { get; set; }
        public string name { get; set; }
        public List<string> message { get; set; }
        public int time { get; set; }
    }
    class FriendMessageEventPack
    {
        public long id { get; set; }
        public string name { get; set; }
        public List<string> message { get; set; }
        public int time { get; set; }
    }
    class SendGroupImagePack
    {
        public long id { get; set; }
        public string img { get; set; }
    }
    class SendGroupPrivateImagePack
    {
        public long id { get; set; }
        public long fid { get; set; }
        public string img { get; set; }
    }
    class SendFriendImagePack
    {
        public long id { get; set; }
        public string img { get; set; }
    }
    class GroupMuteAll
    {
        public long id { get; set; }
    }
    class GroupUnmuteAll
    {
        public long id { get; set; }
    }
    class SetGroupMemberCard
    {
        public long id { get; set; }
        public long fid { get; set; }
        public string card { get; set; }
    }
    class SetGroupName
    {
        public long id { get; set; }
        public string name { get; set; }
    }
    class NewFriendRequestEventPack
    {
        public long id { get; set; }
        public long fid { get; set; }
        public string name { get; set; }
        public string message { get; set; }
        public long eventid { get; set; }
    }
    class EventCallPack
    {
        public long eventid { get; set; }
        public int dofun { get; set; }
        public List<object> arg { get; set; }
    }
    class ReCallMessage
    {
        public long id { get; set; }
    }
    class BuildPack
    {
        public static byte[] Build(object obj, byte index)
        {
            byte[] data = Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(obj) + " ");
            data[data.Length - 1] = index;
            return data;
        }

        public static byte[] BuildImage(long id, long fid, string img, byte index)
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
            temp += "img=" + img;
            byte[] data = Encoding.UTF8.GetBytes(temp + " ");
            data[data.Length - 1] = index;
            return data;
        }

        public static byte[] BuildSound(long id, string sound, byte index)
        {
            string temp = "id=" + id + "&sound=" + sound;
            byte[] data = Encoding.UTF8.GetBytes(temp + " ");
            data[data.Length - 1] = index;
            return data;
        }
    }
    class GroupMessagePostSendEventPack
    {
        public long id { get; set; }
        public bool res { get; set; }
        public List<string> message { get; set; }
        public string error { get; set; }
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
                                        var data = BuildPack.Build(new ReCallMessage { id = long.Parse(id) }, 71);
                                        QueueSend.Add(data);
                                    }
                                    else if (pack.message[pack.message.Count - 1] == "回复")
                                    {
                                        string id = Utils.GetString(pack.message[0], "source:", ",");
                                        var list2 = new List<string>() { "quote:" + id };
                                        list2.Add("回复消息");
                                        SendGroupMessage(pack.id, list2);
                                    }
                                    else if (pack.message[pack.message.Count - 1] == "撤回自己")
                                    {
                                        var list2 = new List<string>() { "3秒后撤回" };
                                        SendGroupMessage(pack.id, list2);
                                    }
                                    break;
                                case 50:
                                    var pack1 = JsonConvert.DeserializeObject<TempMessageEventPack>(task.data);
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
                                    SendGroupPrivateMessage(pack1.id, pack1.fid, list);
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
                                    SendFriendMessage(pack2.id, list1);
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
                HttpClient http = new HttpClient();
                //var mp3 = http.GetByteArrayAsync(@"https://tts.baidu.com/text2audio?tex=%E8%BF%99%E6%98%AF%E4%B8%80%E6%AE%B5%E8%AF%AD%E9%9F%B3&cuid=baike&lan=ZH&ctp=1&pdt=301&vol=9&rate=32&per=0").Result;
                var res = http.GetAsync("https://music.163.com/song/media/outer/url?id=1365679378");
                var data1 = res.Result.Headers.Location;
                var mp3 = http.GetByteArrayAsync(data1).Result;
                //var mp3 = http.GetByteArrayAsync(@"https://pic1.afdiancdn.com/user/a976a704a3b011e88adc52540025c377/common/a43ff57e99670c82733831a8344e8df2_w512_h512_s84.jpg").Result;
                Guid guid = Guid.NewGuid();
                string uuid = guid.ToString().Replace("-", "");
                string inputfile = uuid + ".mp3";
                string output = uuid + ".amr";
                File.WriteAllBytes(inputfile, mp3);

                Process process = new Process();
                process.StartInfo.FileName = @"D:\ffmpeg\bin\ffmpeg.exe";  //这里改成你FFMPEG的路径
                process.StartInfo.Arguments = " -i \"" + inputfile + "\" -ar 8000 -ab 12.2k -ac 1 \""+ output + '\"';
                process.StartInfo.UseShellExecute = false;
                process.StartInfo.CreateNoWindow = true;
                process.StartInfo.RedirectStandardOutput = true;
                process.StartInfo.RedirectStandardInput = true;
                process.StartInfo.RedirectStandardError = true;

                process.Start();
                process.BeginErrorReadLine();   // 开始异步读取

                Console.WriteLine("开始音频转码...");

                process.WaitForExit();    // 等待转码完成
                //FileStream file = new FileStream(AppDomain.CurrentDomain.BaseDirectory + @"target.amr", FileMode.Open, FileAccess.Read);
                FileStream file = new FileStream(AppDomain.CurrentDomain.BaseDirectory + uuid + @".amr", FileMode.Open, FileAccess.Read);
                Thread.Sleep(200);
                var data = new byte[file.Length];
                file.Read(data, 0, data.Length);
                file.Close();
                SendGroupSound(571239090, Convert.ToBase64String(data));
                //SendGroupImage(571239090, Convert.ToBase64String());

                File.Delete(inputfile);
                File.Delete(output);

                Console.WriteLine("输入4行数据后发送");
                list.Add(Console.ReadLine());
                list.Add(Console.ReadLine());
                list.Add(Console.ReadLine());
                list.Add(Console.ReadLine());
                SendGroupMessage(571239090, list);
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
        public static void SendGroupMessage(long id, List<string> message)
        {
            var data = BuildPack.Build(new SendGroupMessagePack { id = id, message = message }, 52);
            QueueSend.Add(data);
        }
        public static void SendGroupPrivateMessage(long id, long fid, List<string> message)
        {
            var data = BuildPack.Build(new SendGroupPrivateMessagePack { id = id, fid = fid, message = message }, 53);
            QueueSend.Add(data);
        }
        public static void SendFriendMessage(long id, List<string> message)
        {
            var data = BuildPack.Build(new SendFriendMessagePack { id = id, message = message }, 54);
            QueueSend.Add(data);
        }
        public static void SendGroupImage(long id, string img)
        {
            var data = BuildPack.BuildImage(id, 0, img, 61);
            QueueSend.Add(data);
        }
        public static void SendGroupPrivateImage(long id, long fid, string img)
        {
            var data = BuildPack.BuildImage(id, fid, img, 62);
            QueueSend.Add(data);
        }
        public static void SendFriendImage(long id, string img)
        {
            var data = BuildPack.BuildImage(id, 0, img, 63);
            QueueSend.Add(data);
        }

        public static void SendGroupSound(long id, string sound)
        {
            var data = BuildPack.BuildSound(id, sound, 74);
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
