package coloryr.colormirai.plugin.websocket;

import coloryr.colormirai.ColorMiraiMain;
import coloryr.colormirai.plugin.IPluginSocket;
import coloryr.colormirai.plugin.PluginUtils;
import coloryr.colormirai.plugin.ThePlugin;
import coloryr.colormirai.plugin.obj.PluginPack;
import coloryr.colormirai.plugin.pack.from.*;
import coloryr.colormirai.plugin.socket.PackDo;
import coloryr.colormirai.robot.BotStart;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.java_websocket.WebSocket;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;

public class WebSocketThread implements IPluginSocket {
    private ThePlugin plugin;
    private final WebSocket socket;
    private final Thread socketThread;

    public WebSocketThread(WebSocket socket) {
        this.socket = socket;
        socketThread = new Thread(this::start, "WebSocketThread");
    }

    public void setPlugin(ThePlugin plugin) {
        this.plugin = plugin;
        socketThread.start();
    }

    private WebSocketPackObj read() {
        String temp = PluginWebSocketServer.read(this.socket);
        JSONObject object = JSONObject.parseObject(temp);
        if (!object.containsKey("index")) {
            return null;
        }
        WebSocketPackObj obj = new WebSocketPackObj();
        obj.index = object.getInteger("index");
        obj.data = object.get("pack");

        return obj;
    }

    private void send(String data) {
        PluginWebSocketServer.send(data, socket);
    }

    private void start() {
        WebSocketPackObj task;
        JSONObject object;
        String object1 = "";
        try {
            while ((task = read()) == null) {
                Thread.sleep(100);
            }
            if (task.index != 0) {
                ColorMiraiMain.logger.warn("插件连接初始化失败，首个数据包不是初始化包");
                socket.close();
                return;
            }
            object = (JSONObject) task.data;
            StartPack StartPack = object.toJavaObject(StartPack.class);
            if (StartPack.name != null && StartPack.reg != null) {
                socketThread.setName("Plugin[" + StartPack.name + "]WebSocketThread");
                plugin.setName(StartPack.name);
                plugin.setEvents(StartPack.reg);
                if (StartPack.groups != null) {
                    plugin.addGroup(StartPack.groups);
                }
                if (StartPack.qqList != null) {
                    plugin.addQQs(StartPack.qqList);
                }
                if (StartPack.runQQ != 0 && !BotStart.getBotsKey().contains(StartPack.runQQ)) {
                    ColorMiraiMain.logger.warn("插件连接失败，没有运行的QQ：" + StartPack.runQQ);
                    socket.close();
                    return;
                }
                plugin.setRunQQ(StartPack.runQQ);
                PluginUtils.addPlugin(plugin.getName(), plugin);
                String data = JSON.toJSONString(BotStart.getBotsKey());
                send(data);
            } else {
                ColorMiraiMain.logger.warn("插件连接初始化失败");
                socket.close();
                return;
            }
        } catch (Exception e) {
            ColorMiraiMain.logger.error("插件连接初始化失败", e);
            return;
        }
        plugin.setRun(true);
        plugin.startRead();
        while (plugin.isRun()) {
            try {
                task = read();
                if (task != null) {
                    if (task.data instanceof JSONObject) {
                        object = (JSONObject) task.data;
                    } else {
                        object1 = task.data.toString();
                    }
                    if (ColorMiraiMain.config.debug) {
                        ColorMiraiMain.logger.info("收到数据包：[" + task.index + "]" + task.data + "");
                    }
                    switch (task.index) {
                        //52 [插件]发送群消息
                        case 52: {
                            plugin.addPack(new PluginPack(object.toJavaObject(SendGroupMessagePack.class), task.index));
                            break;
                        }
                        //53 [插件]发送私聊消息
                        case 53: {
                            plugin.addPack(new PluginPack(object.toJavaObject(SendGroupPrivateMessagePack.class), task.index));
                            break;
                        }
                        //54 [插件]发送好友消息
                        case 54: {
                            plugin.addPack(new PluginPack(object.toJavaObject(SendFriendMessagePack.class), task.index));
                            break;
                        }
                        //55 [插件]获取群列表
                        //56 [插件]获取好友列表
                        case 55:
                        case 56: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GetPack.class), task.index));
                            break;
                        }
                        //57 [插件]获取群成员
                        case 57: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GroupGetMemberInfoPack.class), task.index));
                            break;
                        }
                        //58 [插件]获取群设置
                        case 58: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GroupGetSettingPack.class), task.index));
                            break;
                        }
                        //59 [插件]回应事件
                        case 59: {
                            plugin.addPack(new PluginPack(object.toJavaObject(EventCallPack.class), task.index));
                            break;
                        }
                        //61 [插件]发送图片到群
                        case 61: {
                            Map<String, String> formdata = PackDo.parseDataFromPack(object1);
                            if (formdata.containsKey("id") && formdata.containsKey("img") && formdata.containsKey("qq")) {
                                try {
                                    long id = Long.parseLong(formdata.get("id"));
                                    long qq = Long.parseLong(formdata.get("qq"));
                                    String ids = formdata.get("ids");
                                    SendGroupImagePack pack = new SendGroupImagePack();
                                    if (ids != null) {
                                        pack.ids = new ArrayList<>();
                                        for (String item : ids.split(",")) {
                                            pack.ids.add(Long.parseLong(item));
                                        }
                                    }
                                    pack.qq = qq;
                                    pack.id = id;
                                    pack.data = Base64.getDecoder().decode(formdata.get("img"));
                                    plugin.addPack(new PluginPack(pack, task.index));
                                } catch (Exception e) {
                                    ColorMiraiMain.logger.error("解析发生错误", e);
                                }
                            }
                            break;
                        }
                        //62 [插件]发送图片到私聊
                        case 62: {
                            Map<String, String> formdata = PackDo.parseDataFromPack(object1);
                            if (formdata.containsKey("id") && formdata.containsKey("fid") && formdata.containsKey("img") && formdata.containsKey("qq")) {
                                try {
                                    long id = Long.parseLong(formdata.get("id"));
                                    long fid = Long.parseLong(formdata.get("fid"));
                                    long qq = Long.parseLong(formdata.get("qq"));
                                    SendGroupPrivateImagePack pack = new SendGroupPrivateImagePack();
                                    pack.qq = qq;
                                    pack.id = id;
                                    pack.fid = fid;
                                    pack.data = Base64.getDecoder().decode(formdata.get("img"));
                                    plugin.addPack(new PluginPack(pack, task.index));
                                } catch (Exception e) {
                                    ColorMiraiMain.logger.error("解析发生错误", e);
                                }
                            }
                            break;
                        }
                        //63 [插件]发送图片到朋友
                        case 63: {
                            Map<String, String> formdata = PackDo.parseDataFromPack(object1);
                            if (formdata.containsKey("id") && formdata.containsKey("img")) {
                                try {
                                    long id = Long.parseLong(formdata.get("id"));
                                    long qq = Long.parseLong(formdata.get("qq"));
                                    SendFriendImagePack pack = new SendFriendImagePack();
                                    String ids = formdata.get("ids");
                                    if (ids != null) {
                                        pack.ids = new ArrayList<>();
                                        for (String item : ids.split(",")) {
                                            pack.ids.add(Long.parseLong(item));
                                        }
                                    }
                                    pack.qq = qq;
                                    pack.id = id;
                                    pack.data = Base64.getDecoder().decode(formdata.get("img"));
                                    plugin.addPack(new PluginPack(pack, task.index));
                                } catch (Exception e) {
                                    ColorMiraiMain.logger.error("解析发生错误", e);
                                }
                            }
                            break;
                        }
                        //64 [插件]删除群员
                        case 64: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GroupKickMemberPack.class), task.index));
                            break;
                        }
                        //65 [插件]禁言群员
                        case 65: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GroupMuteMemberPack.class), task.index));
                            break;
                        }
                        //66 [插件]解除禁言
                        case 66: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GroupUnmuteMemberPack.class), task.index));
                            break;
                        }
                        //67 [插件]开启全员禁言
                        case 67: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GroupMuteAllPack.class), task.index));
                            break;
                        }
                        //68 [插件]关闭全员禁言
                        case 68: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GroupUnmuteAllPack.class), task.index));
                            break;
                        }
                        //69 [插件]设置群名片
                        case 69: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GroupSetMemberCardPack.class), task.index));
                            break;
                        }
                        //70 [插件]设置群名
                        case 70: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GroupSetNamePack.class), task.index));
                            break;
                        }
                        //71 [插件]撤回消息
                        case 71: {
                            plugin.addPack(new PluginPack(object.toJavaObject(ReCallMessagePack.class), task.index));
                            break;
                        }
                        //74 [插件]发送语音到群
                        case 74: {
                            Map<String, String> formdata = PackDo.parseDataFromPack(object1);
                            if (formdata.containsKey("id") && formdata.containsKey("sound") && formdata.containsKey("qq")) {
                                try {
                                    long id = Long.parseLong(formdata.get("id"));
                                    long qq = Long.parseLong(formdata.get("qq"));
                                    SendGroupSoundPack pack = new SendGroupSoundPack();
                                    String ids = formdata.get("ids");
                                    if (ids != null) {
                                        pack.ids = new ArrayList<>();
                                        for (String item : ids.split(",")) {
                                            pack.ids.add(Long.parseLong(item));
                                        }
                                    }
                                    pack.qq = qq;
                                    pack.id = id;
                                    pack.data = Base64.getDecoder().decode(formdata.get("sound"));
                                    plugin.addPack(new PluginPack(pack, task.index));
                                } catch (Exception e) {
                                    ColorMiraiMain.logger.error("解析发生错误", e);
                                }
                            }
                            break;
                        }
                        //75 [插件]从文件加载图片发送到群
                        case 75: {
                            plugin.addPack(new PluginPack(object.toJavaObject(SendGroupImageFilePack.class), task.index));
                            break;
                        }
                        //76 [插件]从文件加载图片发送到群私聊
                        case 76: {
                            plugin.addPack(new PluginPack(object.toJavaObject(SendGroupPrivateImageFilePack.class), task.index));
                            break;
                        }
                        //77 [插件]从文件加载图片发送到朋友
                        case 77: {
                            plugin.addPack(new PluginPack(object.toJavaObject(SendFriendImageFilePack.class), task.index));
                            break;
                        }
                        //78 [插件]从文件加载语音发送到群
                        case 78: {
                            plugin.addPack(new PluginPack(object.toJavaObject(SendGroupSoundFilePack.class), task.index));
                            break;
                        }
                        //83 [插件]发送私聊戳一戳
                        case 83: {
                            plugin.addPack(new PluginPack(object.toJavaObject(SendFriendNudgePack.class), task.index));
                            break;
                        }
                        //84 [插件]发送群戳一戳
                        case 84: {
                            plugin.addPack(new PluginPack(object.toJavaObject(SendGroupMemberNudgePack.class), task.index));
                            break;
                        }
                        //90 [插件]获取图片Url
                        case 90: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GetImageUrlPack.class), task.index));
                            break;
                        }
                        //91 [插件]获取群成员信息
                        case 91: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GetMemberInfoPack.class), task.index));
                            break;
                        }
                        //92 [插件]获取朋友信息
                        case 92: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GetFriendInfoPack.class), task.index));
                            break;
                        }
                        //93 [插件]发送音乐分享
                        case 93: {
                            plugin.addPack(new PluginPack(object.toJavaObject(SendMusicSharePack.class), task.index));
                            break;
                        }
                        //94 [插件]设置群精华消息
                        case 94: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GroupSetEssenceMessagePack.class), task.index));
                            break;
                        }
                        //95 [插件]消息队列
                        case 95: {
                            plugin.addPack(new PluginPack(object.toJavaObject(MessageBuffPack.class), task.index));
                            break;
                        }
                        //96 [插件]发送朋友骰子
                        case 96: {
                            plugin.addPack(new PluginPack(object.toJavaObject(SendFriendDicePack.class), task.index));
                        }
                        //97 [插件]发送群骰子
                        case 97: {
                            plugin.addPack(new PluginPack(object.toJavaObject(SendGroupDicePack.class), task.index));
                        }
                        //98 [插件]发送群私聊骰子
                        case 98: {
                            plugin.addPack(new PluginPack(object.toJavaObject(SendGroupPrivateDicePack.class), task.index));
                        }
                        //99 [插件]上传群文件
                        case 99: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GroupAddFilePack.class), task.index));
                            break;
                        }
                        //100 [插件]删除群文件
                        case 100: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GroupDeleteFilePack.class), task.index));
                            break;
                        }
                        //101 [插件]获取群文件
                        case 101: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GroupGetFilesPack.class), task.index));
                            break;
                        }
                        //102 [插件]移动群文件
                        case 102: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GroupMoveFilePack.class), task.index));
                            break;
                        }
                        //103 [插件]重命名群文件
                        case 103: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GroupRenameFilePack.class), task.index));
                            break;
                        }
                        //104 [插件]创新群文件文件夹
                        case 104: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GroupAddDirPack.class), task.index));
                            break;
                        }
                        //105 [插件]删除群文件文件夹
                        case 105: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GroupDeleteDirPack.class), task.index));
                            break;
                        }
                        //106 [插件]重命名群文件文件夹
                        case 106: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GroupRenameDirPack.class), task.index));
                            break;
                        }
                        //107 [插件]下载群文件到指定位置
                        case 107: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GroupDownloadFilePack.class), task.index));
                            break;
                        }
                        //108 [插件]设置取消管理员
                        case 108: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GroupSetAdminPack.class), task.index));
                            break;
                        }
                        //109 [插件]获取群公告
                        case 109: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GroupGetAnnouncementsPack.class), task.index));
                            break;
                        }
                        //110 [插件]设置群公告
                        case 110: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GroupAddAnnouncementPack.class), task.index));
                            break;
                        }
                        //111 [插件]删除群公告
                        case 111: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GroupDeleteAnnouncementPack.class), task.index));
                            break;
                        }
                        //112 [插件]发送好友语言文件
                        case 112: {
                            plugin.addPack(new PluginPack(object.toJavaObject(SendFriendSoundFilePack.class), task.index));
                            break;
                        }
                        //114 [插件]设置允许群员邀请好友入群的状态
                        case 114: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GroupSetAllowMemberInvitePack.class), task.index));
                            break;
                        }
                        //115 [插件]设置允许匿名聊天
                        case 115: {
                            plugin.addPack(new PluginPack(object.toJavaObject(GroupSetAnonymousChatEnabledPack.class), task.index));
                            break;
                        }
                        //117 [插件]发送陌生人消息
                        case 117: {
                            plugin.addPack(new PluginPack(object.toJavaObject(SendStrangerMessagePack.class), task.index));
                            break;
                        }
                        //118 [插件]从文件加载图片发送到陌生人
                        case 118: {
                            plugin.addPack(new PluginPack(object.toJavaObject(SendStrangerImageFilePack.class), task.index));
                            break;
                        }
                        //119 [插件]发送陌生人骰子
                        case 119: {
                            plugin.addPack(new PluginPack(object.toJavaObject(SendStrangerDicePack.class), task.index));
                            break;
                        }
                        //120 [插件]发送陌生人戳一戳
                        case 120: {
                            plugin.addPack(new PluginPack(object.toJavaObject(SendStrangerNudgePack.class), task.index));
                            break;
                        }
                        //121 [插件]从文件加载语音发送到陌生人
                        case 121: {
                            plugin.addPack(new PluginPack(object.toJavaObject(SendStrangerSoundFilePack.class), task.index));
                            break;
                        }
                        //126 [插件]发送好友语音
                        case 126: {
                            Map<String, String> formdata = PackDo.parseDataFromPack(object1);
                            if (formdata.containsKey("id") && formdata.containsKey("sound") && formdata.containsKey("qq")) {
                                try {
                                    long id = Long.parseLong(formdata.get("id"));
                                    long qq = Long.parseLong(formdata.get("qq"));
                                    SendFriendSoundPack pack = new SendFriendSoundPack();
                                    String ids = formdata.get("ids");
                                    if (ids != null) {
                                        pack.ids = new ArrayList<>();
                                        for (String item : ids.split(",")) {
                                            pack.ids.add(Long.parseLong(item));
                                        }
                                    }
                                    pack.qq = qq;
                                    pack.id = id;
                                    pack.data = Base64.getDecoder().decode(formdata.get("sound"));
                                    plugin.addPack(new PluginPack(pack, task.index));
                                } catch (Exception e) {
                                    ColorMiraiMain.logger.error("解析发生错误", e);
                                }
                            }
                            break;
                        }
                        //127 [插件]断开连接
                        case 127: {
                            plugin.close();
                            break;
                        }
                        //60 心跳包
                        case 60: {
                            break;
                        }
                        default: {
                            ColorMiraiMain.logger.error("不知道的包" + task.index);
                            break;
                        }
                    }
                    Thread.sleep(10);
                }
            } catch (Exception e) {
                if (!plugin.isRun())
                    break;
                ColorMiraiMain.logger.error("连接发生异常", e);
                close();
            }
        }
    }

    @Override
    public boolean send(Object data, int index) {
        WebSocketPackObj pack = new WebSocketPackObj();
        pack.index = index;
        pack.data = data;
        return PluginWebSocketServer.send(JSON.toJSONString(pack), socket);
    }

    @Override
    public void close() {
        socket.close();
    }
}
