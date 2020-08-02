package Color_yr.ColorMirai.Socket;

import Color_yr.ColorMirai.Pack.PackBase;
import Color_yr.ColorMirai.Start;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Plugins {
    private Socket Socket;
    private String name;
    private Thread read;
    private boolean isRun;
    private Gson Gson;


    public Plugins(Socket Socket) {
        this.Socket = Socket;
        Gson = new Gson();
        try {
            byte[] buf = new byte[8192];
            int len = Socket.getInputStream().read(buf);
            if (len > 0) {
                String temp = new String(buf, StandardCharsets.UTF_8);
                PackBase pack = new Gson().fromJson(temp, PackBase.class);
                if (pack.getState() == 0) {
                    name = pack.getName();
                    SocketServer.addPlugin(name, this);
                }
            } else
                return;
            isRun = true;
            read = new Thread(() -> {
                while (isRun) {
                    try {
                        int len1 = Socket.getInputStream().read(buf);
                        if (len1 > 0) {
                            String a = new String(buf, StandardCharsets.UTF_8);
                            if (!a.isEmpty()) {
                                PackBase pack = Gson.fromJson(a, PackBase.class);
                                switch (pack.getState()) {
                                    case 1:
                                        break;
                                }
                            }
                        } else if (len1 == -1) {
                            Start.logger.error("连接发生异常");
                            close();
                            break;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            isRun = false;
            Socket.close();
            if (read != null && read.isAlive())
                read.join();
            SocketServer.removePlugin(name);
        } catch (Exception e) {
            Start.logger.error("插件断开失败", e);
        }
    }
}
