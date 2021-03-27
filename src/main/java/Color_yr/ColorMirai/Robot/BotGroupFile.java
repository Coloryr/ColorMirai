package Color_yr.ColorMirai.Robot;

import Color_yr.ColorMirai.Start;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.FileSupported;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.message.data.FileMessage;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.utils.ExternalResource;
import net.mamoe.mirai.utils.RemoteFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BotGroupFile {
    public static void addFile(long qq, long id, String file, String name) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                Start.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            RemoteFile remoteFile = group.getFilesRoot().resolve(name);
            FileInputStream stream = new FileInputStream(file.substring(1));
            ExternalResource tempfile = ExternalResource.create(stream);
            FileMessage message = remoteFile.upload(tempfile);
            MessageSource source = group.sendMessage(message).getSource();
            stream.close();
            int[] temp = source.getIds();
            if (temp.length != 0 && temp[0] != -1) {
                MessageSaveObj call = new MessageSaveObj();
                call.source = source;
                call.time = 120;
                call.id = temp[0];
                BotStart.addMessage(qq, call.id, call);
            }
        } catch (Exception e) {
            Start.logger.error("上传群文件失败", e);
        }
    }

    public static void deleteFile(long qq, long id, String name) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                Start.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            RemoteFile remoteFile = group.getFilesRoot().resolve(name);
            if (!remoteFile.exists()) {
                Start.logger.warn("群：" + id + "文件：" + name + " 不存在");
                return;
            }
            remoteFile.delete();
        } catch (Exception e) {
            Start.logger.error("删除群文件失败", e);
        }
    }

    public static List<String> getFiles(long qq, long id) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return null;
            }
            Bot bot = BotStart.getBots().get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                Start.logger.warn("机器人:" + qq + "不存在群:" + id);
                return null;
            }
            List<String> fileList = new ArrayList<>();
            RemoteFile remoteFile = group.getFilesRoot();
            Iterator<RemoteFile> list = remoteFile.listFilesIterator(false);
            while (list.hasNext()) {
                RemoteFile temp = list.next();
                if (!temp.isFile()) {
                    //todo: mirai-core的bug
                    Iterator<RemoteFile> list1 = temp.listFilesIterator(false);
                    while (list1.hasNext()) {
                        RemoteFile temp1 = list1.next();
                        fileList.add(temp1.getPath());
                    }
                } else {
                    fileList.add(temp.getPath());
                }
            }
            return fileList;
        } catch (Exception e) {
            Start.logger.error("获取群文件列表失败", e);
        }
        return null;
    }
}
