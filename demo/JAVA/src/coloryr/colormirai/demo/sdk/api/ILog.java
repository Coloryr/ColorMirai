package coloryr.colormirai.demo.sdk.api;

import coloryr.colormirai.demo.sdk.enums.LogType;

public interface ILog {
    void LogAction(LogType type, String data);
}
