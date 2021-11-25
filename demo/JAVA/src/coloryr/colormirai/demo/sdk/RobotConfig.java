package coloryr.colormirai.demo.sdk;

import coloryr.colormirai.demo.sdk.api.ICall;
import coloryr.colormirai.demo.sdk.api.ILog;
import coloryr.colormirai.demo.sdk.api.IState;

import java.util.List;

public class RobotConfig {
    /**
     * 机器人IP
     */
    public String ip;
    /**
     * 机器人端口
     */
    public int port;
    /**
     * 监听的包
     */
    public List<Integer> pack;
    /**
     * 插件名字
     */
    public String name;
    /**
     * 监听的群，可以为null
     */
    public List<Long> groups;
    /**
     * 监听的qq号，可以为null
     */
    public List<Long> qqs;
    /**
     * 运行的qq，可以不设置
     */
    public long runQQ;
    /**
     * 重连时间
     */
    public int time;
    /**
     * 检测是否断开
     */
    public boolean check;
    /**
     * 机器人事件回调函数
     */
    public ICall callAction;
    /**
     * 机器人日志回调函数
     */
    public ILog logAction;
    /**
     * 机器人状态回调函数
     */
    public IState stateAction;
}
