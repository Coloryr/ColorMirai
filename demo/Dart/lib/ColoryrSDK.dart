import 'dart:convert';
import 'dart:async';
import 'dart:isolate';
import 'dart:io';
import 'dart:collection';
import 'dart:typed_data';

abstract class PackBase {
  int qq;
}

//55 [插件]获取群列表
class ListGroupPack extends PackBase {
  List<GroupInfo> groups;
}

//56 [插件]获取好友列表
class ListFriendPack extends PackBase {
  List<FriendInfoPack> friends;
}

//57 [插件]获取群成员
class ListMemberPack extends PackBase {
  int id;
  List<MemberInfoPack> members;
}

//58 [插件]获取群设置
class GroupSettingPack extends PackBase {
  int id;
  List<GroupSettings> members;
}

//91 [插件]获取群成员信息
class MemberInfoPack extends PackBase {
  int id;
  String nick;
  String img;
  MemberPermission per;
  String nameCard;
  String specialTitle;
  String avatarUrl;
  int muteTimeRemaining;
  int joinTimestamp;
  int lastSpeakTimestamp;
}

//92 [插件]获取朋友信息
class FriendInfoPack extends PackBase {
  int id;
  String img;
  String remark;
  UserProfile userProfile;
}

//101 [插件]获取群文件
class GroupFilesPack extends PackBase {
  int id;
  List<String> files;
}

//性别
enum Sex { MALE, FEMALE, UNKNOWN }
//成员权限
enum MemberPermission { MEMBER, ADMINISTRATOR, OWNER }

//用户信息
class UserProfile {
  String nickname;
  String email;
  int age;
  int qLevel;
  Sex sex;
}

//群信息
class GroupInfo {
  int id;
  String name;
  String img;
  int oid;
  MemberPermission per;
}

//群设定
class GroupSettings {
  String entranceAnnouncement;
  bool isMuteAll;
  bool isAllowMemberInvite;
  bool isAutoApproveEnabled;
  bool isAnonymousChatEnabled;
}

//0 [插件]插件开始连接
class StartPack {
  String Name;
  List<int> Reg;
  List<int> Groups;
  List<int> QQs;
  int RunQQ;
}

class BuildPack {
  static List<int> Build(Object obj, int index) {
    var data = utf8.encode(jsonEncode(obj) + ' ');
    data[data.length - 1] = index;
    return data;
  }

  static List<int> BuildImage(int qq, int id, int fid, String img, int index) {
    String temp;
    if (id != 0) {
      temp += 'id=$id&';
    }
    if (fid != 0) {
      temp += 'fid=$fid&';
    }
    temp += 'qq=$qq&img=$img';
    var data = utf8.encode(temp + ' ');
    data[data.length - 1] = index;
    return data;
  }

  static List<int> BuildSound(int qq, int id, String sound, int index) {
    String temp;
    temp = 'id=$id&qq=$qq&sound=$sound';
    var data = utf8.encode(temp + ' ');
    data[data.length - 1] = index;
    return data;
  }

  static List<int> BuildBuffImage(
      int qq, int id, int fid, int type, String img, bool send) {
    String temp;
    if (id != 0) {
      temp += 'id=$id&';
    }
    if (fid != 0) {
      temp += 'fid=$fid&';
    }
    temp += 'qq=$qq&img=$img&type=$type&send=$send';
    var data = utf8.encode(temp + ' ');
    data[data.length - 1] = 97;
    return data;
  }
}

class RobotTask {
  int index;
  String data;
}

enum LogType { Log, Error }
enum StateType { Disconnect, Connecting, Connect }

typedef callAction = void Function(int index, String data);
typedef logAction = void Function(LogType type, String data);
typedef stateAction = void Function(StateType type);

class RobotConfig {
  String IP;
  int Port;
  List<int> Pack;
  String Name;
  List<int> Groups;
  List<int> QQs;
  int RunQQ;
  int Time;
  bool Check;
  callAction CallAction;
  logAction LogAction;
  stateAction StateAction;
}

class IntConverter extends Converter<List<int>, List<int>> {
  const IntConverter();

  @override
  List<int> convert(List<int> data) {
    if (data is Uint8List) {
      return data.buffer.asInt64List();
    } else {
      return Uint64List.fromList(data).buffer.asUint8List();
    }
  }
}

class Robot {
  List<int> QQs;
  bool IsRun;
  bool IsConnect;
  Socket socket;
  Isolate ReadThread;
  Isolate DoThread;
  Queue QueueRead;
  Queue QueueSend;
  StartPack PackStart;
  RobotConfig Config;

  bool IsFirst = true;
  int Times = 0;

  void Set(RobotConfig Config) {
    this.Config = Config;

    PackStart = StartPack();
    PackStart.Name = Config.Name;
    PackStart.Reg = Config.Pack;
    PackStart.Groups = Config.Groups;
    PackStart.QQs = Config.QQs;
    PackStart.RunQQ = Config.RunQQ;
  }

  void Start() async {
    if (ReadThread != null) {
      ReadThread.kill(priority: Isolate.immediate);
    }
    if (DoThread != null) {
      DoThread.kill(priority: Isolate.immediate);
    }
    QueueRead = Queue<RobotTask>();
    QueueSend = Queue<List<int>>();
    DoThread = await Isolate.spawn((message) {
      while (IsRun) {
        try {
          if (QueueRead.isNotEmpty) {
            RobotTask task = QueueRead.removeFirst();
            Config.CallAction(task.index, task.data);
          }
          sleep(const Duration(microseconds: 10));
        } catch (e) {
          LogError(e);
        }
      }
    }, '');

    ReadThread = await Isolate.spawn((message) async {
      while (!IsRun) {
        sleep(const Duration(microseconds: 100));
      }
      var time = 0;
      while (IsRun) {
        try {
          if (!IsConnect) {
            ReConnect();
            IsFirst = false;
            Times = 0;
            Config.StateAction(StateType.Connect);
          } else if (await socket.isEmpty == false) {
            // var data = socket.
            // Socket.Receive(data);
            // var type = data[^1];
            // data[^1] = 0;
            // QueueRead.Add(new RobotTask
            // {
            //     index = type,
            //     data = Encoding.UTF8.GetString(data)
            // });
          } else if (Config.Check && time >= 20) {
            // time = 0;
            // if (socket.) {
            //   LogOut('机器人连接中断');
            //   IsConnect = false;
            //   Config.StateAction(StateType.Disconnect);
            // }
          } else if (QueueSend.isNotEmpty) {
            List<int> Send = QueueSend.removeFirst();
            socket.add(Send);
          }
          time++;
          sleep(const Duration(microseconds: 50));
        } catch (e) {
          if (IsFirst) {
            IsRun = false;
            LogError1('机器人连接失败');
          } else {
            Times++;
            if (Times == 10) {
              IsRun = false;
              LogError1('重连失败次数过多');
            }
            LogError1('机器人连接失败');
            LogError(e);
            IsConnect = false;
            LogError1('机器人${Config.Time}毫秒后重连');
            sleep(Duration(microseconds: Config.Time));
            LogError1('机器人重连中');
          }
        }
      }
    }, '');
    IsRun = true;
  }

  void ReConnect() async {
    if (socket != null) {
      socket.destroy();
    }

    Config.StateAction(StateType.Connecting);
    var data = utf8.encode(jsonEncode(PackStart) + ' ');
    data[data.length - 1] = 0;

    socket.add(data);

    socket = await Socket.connect(Config.IP, Config.Port);

    while (await socket.isEmpty) {
      sleep(const Duration(microseconds: 10));
    }

    var data1 = await socket.take(1).first;
    QQs = jsonDecode(utf8.decode(data1));

    QueueRead.clear();
    QueueSend.clear();
    LogOut('机器人已连接');
    IsConnect = true;
  }

  void AddTask(List<int> data) {
    QueueSend.add(data);
  }

  void SendStop() {
    var data = BuildPack.Build(Object(), 127);
    socket.add(data);
  }

  void Stop() {
    LogOut('机器人正在断开');
    IsRun = false;
    SendStop();
    if (socket != null) {
      socket.destroy();
    }
    LogOut('机器人已断开');
  }

  void LogError(Exception e) {
    Config.LogAction(LogType.Error, '机器人错误\n' + e.toString());
  }

  void LogError1(String a) {
    Config.LogAction(LogType.Error, '机器人错误:' + a);
  }

  void LogOut(String a) {
    Config.LogAction(LogType.Log, a);
  }
}
