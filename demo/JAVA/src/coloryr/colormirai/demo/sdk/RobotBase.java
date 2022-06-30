package coloryr.colormirai.demo.sdk;

import coloryr.colormirai.demo.sdk.api.ICall;
import coloryr.colormirai.demo.sdk.api.IColorMiraiPipe;
import coloryr.colormirai.demo.sdk.api.ILog;
import coloryr.colormirai.demo.sdk.api.IState;
import coloryr.colormirai.demo.sdk.enums.LogType;
import coloryr.colormirai.demo.sdk.pack.PackBase;
import coloryr.colormirai.demo.sdk.pack.from.StartPack;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

//需要使用JAVA8环境
//需要安装fastjson库

public class RobotBase {
    /**
     * 机器人登录的QQ号列表
     */
    public List<Long> qqs;
    /**
     * 是否正在运行
     */
    public boolean isRun;
    /**
     * 是否连接
     */
    public boolean isConnect;
    /**
     * 第一次连接检查
     */
    public boolean isFirst = true;

    public ICall robotCallEvent;
    public ILog robotLogEvent;
    public IState robotStateEvent;
    public StartPack packStart;
    public RobotConfig config;
    public int times = 0;

    protected boolean callTop(byte index, Object data) {
        return false;
    }

    private Thread doThread;
    private Queue<RobotTask> queueRead;
    private IColorMiraiPipe pipe;

    /**
     * 设置链接方式
     *
     * @param pipe 管线
     */
    public void setPipe(IColorMiraiPipe pipe) {
        this.pipe = pipe;
    }

    /**
     * 设置配置
     *
     * @param config 机器人配置
     */
    public void set(RobotConfig config) {
        this.config = config;

        robotCallEvent = config.callAction;
        robotLogEvent = config.logAction;
        robotStateEvent = config.stateAction;

        packStart = new StartPack() {{
            name = config.name;
            reg = config.pack;
            groups = config.groups;
            qqs = config.qqs;
            qq = config.runQQ;
        }};
    }

    /**
     * 启动机器人
     */
    public void start() {
        if (doThread != null && doThread.isAlive())
            return;
        queueRead = new ConcurrentLinkedDeque<>();
        doThread = new Thread(this::read);
        isRun = true;
        doThread.start();
        pipe.startRead();
    }

    private void read() {
        RobotTask task;
        while (isRun) {
            try {
                task = queueRead.poll();
                if (task != null) {
                    if (task.index == 60)
                        continue;
                    if (callTop(task.index, task.data))
                        continue;
                    if (RobotPack.packType.containsKey(task.index)) {
                        Class<? extends PackBase> class1 = RobotPack.packType.get(task.index);
                        robotCallEvent.CallAction(task.index, class1.cast(task.data));
                    } else {
                        logError("不认识的数据包" + task.index);
                    }
                }
                Thread.sleep(10);
            } catch (Exception e) {
                logError(e);
            }
        }
    }

    /**
     * 添加读取的包
     *
     * @param pack  解析后的信息
     * @param index 包ID
     */
    public void addRead(Object pack, byte index) {
        queueRead.add(new RobotTask(index, pack));
    }

    /**
     * 添加数据包
     *
     * @param pack  数据包
     * @param index 包ID
     */
    public void addSend(PackBase pack, byte index) {
        pipe.addSend(pack, index);
    }

    /**
     * 停止机器人
     */
    public void stop() {
        logOut("机器人正在断开");
        isRun = false;
        pipe.stop();
        logOut("机器人已断开");
    }

    private void logError(Exception e) {
        robotLogEvent.LogAction(LogType.ERROR, "机器人错误\n" + e.toString());
    }

    private void logError(String a) {
        robotLogEvent.LogAction(LogType.ERROR, "机器人错误:" + a);
    }

    private void logOut(String a) {
        robotLogEvent.LogAction(LogType.LOG, a);
    }
}
