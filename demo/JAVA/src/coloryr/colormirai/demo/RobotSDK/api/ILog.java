package coloryr.colormirai.demo.RobotSDK.api;

import coloryr.colormirai.demo.RobotSDK.enums.LogType;

public interface ILog {
    void LogAction(LogType type, String data);
}
