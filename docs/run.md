# ColorMirai

[返回](../README.md)

## 启动

1. 启动ColorMirai，必须使用openjdk8及以上，推荐使用openjdk15启动  
   请务必用openjdk启动  
   请务必用openjdk启动  
   请务必用openjdk启动

如果没有openjdk，群文件有openjdk15，下载安装之后新建一个`.cmd`，在里面输入
```Cmd
"C:\Program Files\AdoptOpenJDK\jdk-15.0.2.7-hotspot\bin\java.exe" -Xmx1024M -jar ColorMirai-3.x.x-SNAPSHOT-all.jar
pause
```
- `jdk-15.0.2.7-hotspot`要根据你安装的版本修改  
  `ColorMirai-3.x.x-SNAPSHOT-all`也根据你运行的版本修改

如果你的系统环境变量默认是openjdk，则cmd内容是下面
```
java -Xmx1024M -jar ColorMirai-3.X-SNAPSHOT-all.jar
pause
```

首次启动后, 会生成四个日志文件夹和`info.json`、`MainConfig.json`两个配置文件

- `info.json`是设备名称，不需要特别设置
- `MainConfig.json`是配置文件

2. 默认配置

`MainConfig.json`

```Json
{
  "AutoReconnect": true,
  "HighwayUpload": 10,
  "MaxList": 100000,
  "Pack": true,
  "Port": 23333,
  "QQs": [
    {
      "Info": "info.json",
      "LoginType": "ANDROID_PHONE",
      "Password": "请填写你的密码",
      "QQ": 0
    }
  ],
  "ReadEncoding": "UTF-8",
  "SendEncoding": "UTF-8",
  "SocketType": 0,
  "escapeSelf": true
}
```

- `MaxList`：最大消息列表
- `Pack`：是否发送心跳包
- `Port`：启动的端口
- `QQs` : QQ账号列表 (可以添加多个)
  - `Info`：登录设备信息
  - `QQ`：登录的QQ号
  - `Password`：QQ号密码
  - `LoginType`：登录的方式
  - 目前支持两种协议:
    - 安卓 `ANDROID_PHONE`
    - 手表 `ANDROID_WATCH`
    - 平板 `ANDROID_PAD`
- `escapeSelf`：是否跳过自己机器人的信息
- `ReadEncoding`：读数据包编码
- `SendEncoding`：发送数据包编码
- `SocketType`：插件连接方式
    - Socket [0]
    - WebSocket [1]
- `AutoReconnect`：自动重连
- `HighwayUpload`：上传通道数

设置完成后再次启动ColorMirai，出现
```
[INFO] Socket已启动: 23333
``` 
说明机器人已成功启动

### 无法登录临时解决

1. 将协议切换成 手表 `ANDROID_WATCH`
2. 登录成功后, 下次登录可以选择 安卓 `ANDROID_PHONE`
3. 安装Chrome浏览器再次尝试

还是无解可以尝试[这个](https://github.com/project-mirai/mirai-login-solver-selenium/#%E6%89%8B%E5%8A%A8%E5%AE%8C%E6%88%90%E6%BB%91%E5%8A%A8%E9%AA%8C%E8%AF%81)