package coloryr.colormirai.robot;

import coloryr.colormirai.ColorMiraiMain;
import coloryr.colormirai.Msg;
import coloryr.colormirai.Utils;
import coloryr.colormirai.download.DownloadUtils;
import coloryr.colormirai.plugin.ThePlugin;
import coloryr.colormirai.plugin.pack.re.GroupFileInfo;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.PermissionDeniedException;
import net.mamoe.mirai.contact.file.AbsoluteFile;
import net.mamoe.mirai.contact.file.AbsoluteFolder;
import net.mamoe.mirai.contact.file.RemoteFiles;

import java.util.ArrayList;
import java.util.List;

public class BotGroupFile {
    public static void addFile(ThePlugin plugin, long qq, long id, String file, String name) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            RemoteFiles remoteFile = group.getFiles();
            remoteFile.uploadNewFile(name, BotUpload.up(file.substring(1)));
        } catch (PermissionDeniedException e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.file(name) + Msg.update + Msg.non_permission;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.file(name) + Msg.update + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void deleteFile(ThePlugin plugin, long qq, long id, String fid) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            group.getFiles().getRoot().refreshed();
            AbsoluteFile file = BotCheck.file(plugin, bot, group, fid);
            if (file == null) return;
            boolean res = file.delete();
            if (!res) {
                String temp = Msg.qq(qq) + Msg.group(id) + Msg.file(fid) + Msg.delete + Msg.fail;
                ColorMiraiMain.logger.warn(temp);
                plugin.sendPluginMessage(qq, "", temp);
            }
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.file(fid) + Msg.delete + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static List<GroupFileInfo> getFiles(ThePlugin plugin, long qq, long id) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return null;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return null;
            List<GroupFileInfo> fileList = new ArrayList<>();
            RemoteFiles remoteFile = group.getFiles();
            remoteFile.getRoot().filesStream().forEach(str -> {
                GroupFileInfo info = get(str);
                fileList.add(info);
            });
            remoteFile.getRoot().foldersStream().forEach(item ->
                    item.filesStream().forEach(item1 -> {
                        GroupFileInfo info = get(item1);
                        fileList.add(info);
                    }));
            return fileList;
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.files + Msg.get + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
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

    public static void moveFile(ThePlugin plugin, long qq, long id, String fid, String dir) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            group.getFiles().getRoot().refreshed();
            AbsoluteFile remoteFile = BotCheck.file(plugin, bot, group, fid);
            if (remoteFile == null) return;
            AbsoluteFolder dir1 = group.getFiles().getRoot().resolveFolder(dir);
            if (dir1 == null) {
                dir1 = group.getFiles().getRoot().createFolder(dir);
            }
            boolean res = remoteFile.moveTo(dir1);
            if (!res) {
                String temp = Msg.qq(qq) + Msg.group(id) + Msg.file(fid) + Msg.move + Msg.fail;
                ColorMiraiMain.logger.warn(temp);
                plugin.sendPluginMessage(qq, "", temp);
            }
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.file(fid) + Msg.move + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void renameFile(ThePlugin plugin, long qq, long id, String fid, String now) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            group.getFiles().getRoot().refreshed();
            AbsoluteFile remoteFile = BotCheck.file(plugin, bot, group, fid);
            if (remoteFile == null) return;
            boolean res = remoteFile.renameTo(now);
            if (!res) {
                String temp = Msg.qq(qq) + Msg.group(id) + Msg.file(fid) + Msg.rename + Msg.fail;
                ColorMiraiMain.logger.warn(temp);
                plugin.sendPluginMessage(qq, "", temp);
            }
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.file(fid) + Msg.rename + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void addGroupDir(ThePlugin plugin, long qq, long id, String dir) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            group.getFiles().getRoot().refreshed();
            group.getFiles().getRoot().createFolder(dir);
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.dir(dir) + Msg.create + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void removeGroupDir(ThePlugin plugin, long qq, long id, String dir) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            group.getFiles().getRoot().refreshed();
            AbsoluteFolder remoteFile = BotCheck.folder(plugin, bot, group, dir);
            if (remoteFile == null) return;
            if (remoteFile.isFile()) {
                String temp = Msg.qq(qq) + Msg.group(id) + Msg.dir(dir) + Msg.type_error;
                ColorMiraiMain.logger.warn(temp);
                plugin.sendPluginMessage(qq, "", temp);
                return;
            }
            boolean res = remoteFile.delete();
            if (!res) {
                String temp = Msg.qq(qq) + Msg.group(id) + Msg.dir(dir) + Msg.delete + Msg.fail;
                ColorMiraiMain.logger.warn(temp);
                plugin.sendPluginMessage(qq, "", temp);
            }
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.dir(dir) + Msg.delete + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void renameGroupDir(ThePlugin plugin, long qq, long id, String old, String now) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            group.getFiles().getRoot().refreshed();
            AbsoluteFolder remoteFile = BotCheck.folder(plugin, bot, group, old);
            if (remoteFile == null) return;
            if (remoteFile.isFile()) {
                String temp = Msg.qq(qq) + Msg.group(id) + Msg.dir(old) + Msg.type_error;
                ColorMiraiMain.logger.warn(temp);
                plugin.sendPluginMessage(qq, "", temp);
                return;
            }
            boolean res = remoteFile.renameTo(now);
            if (!res) {
                String temp = Msg.qq(qq) + Msg.group(id) + Msg.dir(old) + Msg.rename + Msg.fail;
                ColorMiraiMain.logger.warn(temp);
                plugin.sendPluginMessage(qq, "", temp);
            }
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.dir(old) + Msg.rename + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void downloadGroupFile(ThePlugin plugin, long qq, long id, String fid, String dir) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            group.getFiles().getRoot().refreshed();
            AbsoluteFile remoteFile = BotCheck.file(plugin, bot, group, fid);
            if (remoteFile == null) return;
            if (!remoteFile.isFile()) {
                String temp = Msg.qq(qq) + Msg.group(id) + Msg.file(fid) + Msg.type_error;
                ColorMiraiMain.logger.warn(temp);
                plugin.sendPluginMessage(qq, "", temp);
                return;
            }
            String url = remoteFile.getUrl();
            if (url == null) {
                String temp = Msg.qq(qq) + Msg.group(id) + Msg.file(fid) + Msg.url + Msg.get + Msg.file;
                ColorMiraiMain.logger.warn(temp);
                plugin.sendPluginMessage(qq, "", temp);
                return;
            }
            DownloadUtils.addTask(plugin, qq, id, url, remoteFile.getName(), dir);
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.file(fid) + Msg.download + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }
}
