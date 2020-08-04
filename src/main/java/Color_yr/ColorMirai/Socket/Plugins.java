package Color_yr.ColorMirai.Socket;

import Color_yr.ColorMirai.Pack.PackStart;
import Color_yr.ColorMirai.Start;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Plugins {
    private final Socket Socket;
    private final List<byte[]> Tasks = new CopyOnWriteArrayList<>();
    private final Gson Gson;
    private String name;
    private Thread read;
    private Thread doRead;
    private List<Byte> Events = null;
    private boolean isRun;

    public Plugins(Socket Socket) {
        this.Socket = Socket;
        Gson = new Gson();
        try {
            byte[] buf = new byte[8192];
            int len = Socket.getInputStream().read(buf);
            if (len > 0) {
                String temp = new String(buf, StandardCharsets.UTF_8);
                PackStart pack = new Gson().fromJson(temp, PackStart.class);
                name = pack.getName();
                Events = pack.getReg();
                SocketServer.addPlugin(name, this);
            } else {
                Start.logger.warn("插件连接初始化失败");
                return;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        isRun = true;
        read = new Thread(() -> {
            while (isRun) {
                try {
                    if (Socket.getInputStream().available() > 0) {
                        byte[] buf = new byte[Socket.getInputStream().available()];
                        int len1 = Socket.getInputStream().read(buf);
                        if (len1 > 0) {
                            Tasks.add(buf);
                        } else if (len1 == -1) {
                            Start.logger.error("连接发生异常");
                            close();
                            break;
                        }
                    }
                    Thread.sleep(50);
                } catch (Exception e) {
                    if (!isRun)
                        break;
                    Start.logger.error("连接发生异常", e);
                    close();
                }
            }
        });
        read.start();
        doRead = new Thread(() -> {
            while (isRun) {
                try {
                    if (!Tasks.isEmpty()) {
                        byte[] buf = Tasks.remove(0);
                        byte type = buf[buf.length - 1];
                        buf[buf.length - 1] = 0;
                        String a = new String(buf, StandardCharsets.UTF_8);
                        if (!a.isEmpty()) {
                            switch (type) {
                                case 1:

                                    break;
                            }
                        }
                    }
                    Thread.sleep(50);
                } catch (Exception e) {
                    if (!isRun)
                        break;
                    Start.logger.error("数据处理发生异常", e);
                    close();
                }
            }
        });
        doRead.start();
    }

    public void callEvent(byte index, byte[] data) {
        if (Events.contains(index)) {
            SocketServer.sendPack(data, Socket);
        }
    }

    public void close() {
        try {
            isRun = false;
            Socket.close();
            if (read != null && read.isAlive())
                read.join();
            if (doRead != null && doRead.isAlive())
                doRead.join();
            SocketServer.removePlugin(name);
        } catch (Exception e) {
            Start.logger.error("插件断开失败", e);
        }
    }
}
