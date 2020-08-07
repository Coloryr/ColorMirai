using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
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
        public string message { get; set; }
    }

    class SendGroupMessagePack
    { 
        public long id { get; set; }
        public string message { get; set; }
    }

    class MySocket
    {
        public static void Start()
        {
            try
            {
                var socket = new Socket(SocketType.Stream, ProtocolType.Tcp);
                socket.Connect(IPAddress.Parse("127.0.0.1"), 23333);

                var PackStart = new PackStart { Name = "test", Reg = new List<byte>() { 49, 50, 51 } };
                var data = Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(PackStart) + " ");
                data[data.Length - 1] = 0;

                socket.Send(data);

                new Thread(() =>
                {
                    while (true)
                    {
                        if (socket.Available > 0)
                        {
                            data = new byte[socket.Available];
                            socket.Receive(data);
                            var type = data[data.Length - 1];
                            data[data.Length - 1] = 0;
                            switch (type)
                            {
                                case 49:
                                    var pack = JsonConvert.DeserializeObject<GroupMessageEventPack>(Encoding.UTF8.GetString(data));
                                    Console.WriteLine("id=" + pack.id);
                                    Console.WriteLine("fid=" + pack.fid);
                                    Console.WriteLine("name=" + pack.name);
                                    Console.WriteLine("message=" + pack.message);
                                    break;
                            }
                        }
                        Thread.Sleep(10);
                    }
                }).Start();
                var pack1 = new SendGroupMessagePack { id = 571239090 };
                byte[] data1;
                while (true)
                {
                    string read = Console.ReadLine();
                    pack1.message = read;
                    data1 = Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(pack1) + " ");
                    data1[data1.Length - 1] = 52;
                    socket.Send(data1);
                }
                
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
            
        }
    }
}
