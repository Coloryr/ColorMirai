package Color_yr.ColorMirai.robot;

import Color_yr.ColorMirai.ColorMiraiMain;
import Color_yr.ColorMirai.plugin.download.DownloadUtils;
import Color_yr.ColorMirai.plugin.mirai_http_api.Utils;
import Color_yr.ColorMirai.plugin.socket.pack.re.GroupFileInfo;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.PermissionDeniedException;
import net.mamoe.mirai.contact.file.AbsoluteFile;
import net.mamoe.mirai.contact.file.AbsoluteFileFolder;
import net.mamoe.mirai.contact.file.AbsoluteFolder;
import net.mamoe.mirai.contact.file.RemoteFiles;
import net.mamoe.mirai.utils.ExternalResource;
import net.mamoe.mirai.utils.RemoteFile;

import java.io.FileInputStream;
import java.util.ArrayList;
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
            RemoteFiles remoteFile = group.getFiles();
            FileInputStream stream = new FileInputStream(file.substring(1));
            ExternalResource tempfile = ExternalResource.create(stream).toAutoCloseable();
            remoteFile.uploadNewFile(name, tempfile);
            stream.close();
        } catch (PermissionDeniedException e) {
            ColorMiraiMain.logger.error("上传群文件失败，只允许管理员上传");
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
            group.getFiles().getRoot().refreshed();
            AbsoluteFile file = group.getFiles().getRoot().resolveFileById(name);
            if (file == null) {
                ColorMiraiMain.logger.warn("群：" + id + "文件：" + name + " 不存在");
                return;
            }
            file.delete();
        } catch (Exception e) {
            ColorMiraiMain.logger.error("删除群文件失败", e);
        }
    }

    public static List<GroupFileInfo> getFiles(long qq, long id) {
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
            group.getFiles().getRoot().refreshed();
            List<GroupFileInfo> fileList = new ArrayList<>();
            RemoteFiles remoteFile = group.getFiles();
            remoteFile.getRoot().filesStream().forEach(str -> {
                GroupFileInfo info = get(str);
                fileList.add(info);
            });
            remoteFile.getRoot().foldersStream().forEach(item -> item.filesStream().forEach(item1 -> {
                GroupFileInfo info = get(item1);
                fileList.add(info);
            }));
            return fileList;
        } catch (Exception e) {
            ColorMiraiMain.logger.error("获取群文件列表失败", e);
        }
        return null;
    }

    private static GroupFileInfo get(AbsoluteFile file) {
        if (file == null)
            return null;
        GroupFileInfo info = new GroupFileInfo();
        info.expiryTime = file.getExpiryTime();
        info.size = file.getSize();
        info.sha1 = Utils.toUHexString(file.getSha1());
        info.md5 = Utils.toUHexString(file.getMd5());
        info.id = file.getId();
        info.name = file.getName();
        info.absolutePath = file.getAbsolutePath();
        info.isFile = file.isFile();
        info.isFolder = file.isFolder();
        info.uploadTime = file.getUploadTime();
        info.lastModifyTime = file.getLastModifiedTime();
        info.uploaderId = file.getUploaderId();
        return info;
    }

    public static void moveFile(long qq, long id, String fid, String dir) {
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
            group.getFiles().getRoot().refreshed();
            AbsoluteFile remoteFile = group.getFiles().getRoot().resolveFileById(fid);
            if (remoteFile == null) {
                ColorMiraiMain.logger.warn("群：" + id + "文件：" + fid + " 不存在");
                return;
            }
            AbsoluteFolder dir1 = group.getFiles().getRoot().resolveFolder(dir);
            if (dir1 == null) {
                dir1 = group.getFiles().getRoot().createFolder(dir);
            }
            remoteFile.moveTo(dir1);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("群文件移动失败", e);
        }
    }

    public static void renameFile(long qq, long id, String fid, String now) {
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
            group.getFiles().getRoot().refreshed();
            AbsoluteFile remoteFile = group.getFiles().getRoot().resolveFileById(fid);
            if (remoteFile == null) {
                ColorMiraiMain.logger.warn("群：" + id + "文件：" + fid + " 不存在");
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
            group.getFiles().getRoot().refreshed();
            group.getFiles().getRoot().createFolder(dir);
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
            group.getFiles().getRoot().refreshed();
            AbsoluteFolder remoteFile = group.getFiles().getRoot().resolveFolder(dir);
            if (remoteFile == null) {
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
            group.getFiles().getRoot().refreshed();
            AbsoluteFolder remoteFile = group.getFiles().getRoot().resolveFolder(old);

            if (remoteFile == null) {
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
            group.getFiles().getRoot().refreshed();
            AbsoluteFile remoteFile = group.getFiles().getRoot().resolveFileById(name);

            if (remoteFile == null) {
                ColorMiraiMain.logger.warn("群：" + id + "文件：" + name + " 不存在");
                return;
            }
            if (!remoteFile.isFile()) {
                ColorMiraiMain.logger.warn(name + " 不是文件");
                return;
            }
            String url = remoteFile.getUrl();
            if (url == null) {
                ColorMiraiMain.logger.warn("群：" + id + "文件：" + name + " 信息获取失败");
                return;
            }
            DownloadUtils.addTask(url, remoteFile.getName(), dir);
            ColorMiraiMain.logger.info("添加下载群：" + id + " 文件：" + name + " 任务");
        } catch (Exception e) {
            ColorMiraiMain.logger.error("群文件夹创建失败", e);
        }
    }
}
