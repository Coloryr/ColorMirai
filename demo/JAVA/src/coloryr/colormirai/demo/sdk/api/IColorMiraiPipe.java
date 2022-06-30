package coloryr.colormirai.demo.sdk.api;

import coloryr.colormirai.demo.sdk.pack.PackBase;

public interface IColorMiraiPipe {
    void addSend(PackBase pack, byte index);

    void reConnect() throws Exception;

    void sendStop();

    void stop();

    void startRead();
}
