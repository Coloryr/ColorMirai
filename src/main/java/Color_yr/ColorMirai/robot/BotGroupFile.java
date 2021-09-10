package Color_yr.ColorMirai.robot;

import Color_yr.ColorMirai.ColorMiraiMain;
import Color_yr.ColorMirai.plugin.download.DownloadUtils;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.FileMessage;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.utils.ExternalResource;
import net.mamoe.mirai.utils.RemoteFile;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BotGroupFile {
    public static void addFile(long qq, long id, String file, String name) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + id);
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
            tempfile.close();
        } catch (Exception e) {
            ColorMiraiMain.logger.error("上传群文件失败", e);
        }
    }

    public static void deleteFile(long qq, long id, String name) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            RemoteFile remoteFile = group.getFilesRoot().resolve(name);
            if (!remoteFile.exists()) {
                ColorMiraiMain.logger.warn("群：" + id + "文件：" + name + " 不存在");
                return;
            }
            remoteFile.delete();
        } catch (Exception e) {
            ColorMiraiMain.logger.error("删除群文件失败", e);
        }
    }

    public static List<String> getFiles(long qq, long id) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return null;
            }
            Bot bot = BotStart.getBots().get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + id);
                return null;
            }
            List<String> fileList = new ArrayList<>();
            RemoteFile remoteFile = group.getFilesRoot();
            Iterator<RemoteFile> list = remoteFile.listFilesIterator(false);
            while (list.hasNext()) {
                RemoteFile temp = list.next();
                if (!temp.isFile()) {
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
            ColorMiraiMain.logger.error("获取群文件列表失败", e);
        }
        return null;
    }

    public static void moveFile(long qq, long id, String old, String dir) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            RemoteFile remoteFile = group.getFilesRoot().resolve(old);
            if (!remoteFile.exists()) {
                ColorMiraiMain.logger.warn("群：" + id + "文件：" + old + " 不存在");
                return;
            }
            remoteFile.moveTo(dir);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("群文件移动失败", e);
        }
    }

    public static void renameFile(long qq, long id, String old, String now) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            RemoteFile remoteFile = group.getFilesRoot().resolve(old);
            if (!remoteFile.exists()) {
                ColorMiraiMain.logger.warn("群：" + id + "文件：" + old + " 不存在");
                return;
            }
            remoteFile.renameTo(now);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("群文件重命名失败", e);
        }
    }

    public static void addGroupDir(long qq, long id, String dir) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            RemoteFile remoteFile = group.getFilesRoot().resolve(dir);

            if (remoteFile.exists()) {
                ColorMiraiMain.logger.warn("群：" + id + "文件夹：" + dir + " 已存在");
                return;
            }
            if (remoteFile.isFile()) {
                ColorMiraiMain.logger.warn("不能对文件:" + dir + " 进行创造群文件夹操作");
                return;
            }
            remoteFile.mkdir();
        } catch (Exception e) {
            ColorMiraiMain.logger.error("群文件夹创建失败", e);
        }
    }

    public static void removeGroupDir(long qq, long id, String dir) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            RemoteFile remoteFile = group.getFilesRoot().resolve(dir);

            if (!remoteFile.exists()) {
                ColorMiraiMain.logger.warn("群：" + id + "文件夹：" + dir + " 不存在");
                return;
            }
            if (remoteFile.isFile()) {
                ColorMiraiMain.logger.warn("不能对文件:" + dir + " 进行创造群文件夹操作");
                return;
            }
            remoteFile.delete();
        } catch (Exception e) {
            ColorMiraiMain.logger.error("群文件夹创建失败", e);
        }
    }

    public static void renameGroupDir(long qq, long id, String old, String now) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            RemoteFile remoteFile = group.getFilesRoot().resolve(old);

            if (!remoteFile.exists()) {
                ColorMiraiMain.logger.warn("群：" + id + "文件夹：" + old + " 不存在");
                return;
            }
            if (remoteFile.isFile()) {
                ColorMiraiMain.logger.warn("不能对文件:" + old + " 进行创造群文件夹操作");
                return;
            }
            remoteFile.renameTo(now);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("群文件夹创建失败", e);
        }
    }

    public static void downloadGroupFile(long qq, long id, String name, String dir) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            RemoteFile remoteFile = group.getFilesRoot().resolve(name);

            if (!remoteFile.exists()) {
                ColorMiraiMain.logger.warn("群：" + id + "文件：" + name + " 不存在");
                return;
            }
            if (!remoteFile.isFile()) {
                ColorMiraiMain.logger.warn(name + " 不是文件");
                return;
            }
            RemoteFile.DownloadInfo info = remoteFile.getDownloadInfo();
            if (info == null) {
                ColorMiraiMain.logger.warn("群：" + id + "文件：" + name + " 信息获取失败");
                return;
            }
            String url = info.getUrl();
            DownloadUtils.addTask(url, info.getFilename(), dir);
            ColorMiraiMain.logger.info("添加下载群：" + id + " 文件：" + name + " 任务");
        } catch (Exception e) {
            ColorMiraiMain.logger.error("群文件夹创建失败", e);
        }
    }
}
