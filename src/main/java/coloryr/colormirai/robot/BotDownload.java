//好友文件还未支持
//package coloryr.colormirai.robot;
//
//import coloryr.colormirai.ColorMiraiMain;
//import coloryr.colormirai.plugin.pack.re.GroupInfo;
//import coloryr.colormirai.plugin.pack.re.ReDownloadFilePack;
//import net.mamoe.mirai.Bot;
//import net.mamoe.mirai.contact.FileSupported;
//import net.mamoe.mirai.contact.Group;
//import net.mamoe.mirai.contact.Member;
//import net.mamoe.mirai.contact.NormalMember;
//import net.mamoe.mirai.message.data.FileMessage;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class BotDownload {
//    public static ReDownloadFilePack download(long qq, String id, int internalId, String name, long size, String local, long fid, long qid, String uuid){
//        try {
//            ReDownloadFilePack pack = new ReDownloadFilePack();
//            pack.qq = qq;
//            pack.uuid = uuid;
//            pack.done = false;
//            if (!BotStart.getBots().containsKey(qq)) {
//                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
//                pack.message = "不存在QQ号:" + qq;
//                return pack;
//            }
//            Bot bot = BotStart.getBots().get(qq);
//            FileMessage file = FileMessage.create(id, internalId, name, size);
//            FileSupported supported;
//            if(qid!=0)
//            {
//                supported = bot.getGroup(qid);
//                if(supported==null){
//                    ColorMiraiMain.logger.warn("QQ号:" + qq + " 不存在QQ群:" + qid);
//                    pack.message = "QQ号:" + qq + " 不存在QQ群:" + qid;
//                    return pack;
//                }
//
//            }
//            else if(fid!=0)
//            {
//                supported = bot.getFriend(fid);
//                if(member==null){
//                    ColorMiraiMain.logger.warn("QQ号:" + qq + " QQ群:" + qid + " 不存在群员:" + fid);
//                    pack.message = "QQ号:" + qq + " QQ群:" + qid + " 不存在群员:" + fid;
//                    return pack;
//                }
//                supported = (FileSupported) member;
//            }
//            else
//            {
//                pack.message = "不存在用户";
//                return pack;
//            }
//            file.toAbsoluteFile(supported);
//            return pack;
//        } catch (Exception e) {
//            ColorMiraiMain.logger.error("获取群数据失败", e);
//            return null;
//        }
//    }
//}
