package coloryr.colormirai.demo.RobotSDK;

import coloryr.colormirai.demo.RobotSDK.api.ICall;
import coloryr.colormirai.demo.RobotSDK.api.ILog;
import coloryr.colormirai.demo.RobotSDK.api.IState;

import java.util.List;

public class RobotConfig {
    /**
     * 机器人IP
     */
    public String IP;
    /**
     * 机器人端口
     */
    public int Port;
    /**
     * 监听的包
     */
    public List<Integer> Pack;
    /**
     * 插件名字
     */
    public String Name;
    /**
     * 监听的群，可以为null
     */
    public List<Long> Groups;
    /**
     * 监听的qq号，可以为null
     */
    public List<Long> QQs;
    /**
     * 运行的qq，可以不设置
     */
    public long RunQQ;
    /**
     * 重连时间
     */
    public int Time;
    /**
     * 检测是否断开
     */
    public boolean Check;
    /**
     * 机器人事件回调函数
     */
    public ICall CallAction;
    /**
     * 机器人日志回调函数
     */
    public ILog LogAction;
    /**
     * 机器人状态回调函数
     */
    public IState StateAction;
}
