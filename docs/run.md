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

```Json5
{
  "AutoReconnect":true,
  "HighwayUpload":10,
  "HttpPort":23335,
  "MaxList":100000,
  "Pack":true,
  "QQs": [
    {
      "Info": "device.json",
      "LoginType": "ANDROID_PHONE",
      "Password": "请填写你的密码",
      "QQ": 0
    }
  ],
  "ReadEncoding":"UTF-8",
  "SendEncoding":"UTF-8",
  "SocketPort":23333,
  "WebSocketPort":23334,
  "authKey":"123456789",
  "authTime":1800,
  "cacheSize":100,
  "escapeSelf":true,
  "noInput": false
}
```

- `MaxList`：最大消息列表
- `Pack`：是否发送心跳包
- `SocketPort`：Socket启动的端口
- `HttpPort`：mirai-http-api启动端口
- `WebSocketPort`：websocket启动的端口
- `QQs` : QQ账号列表 (可以添加多个)
  - `Info`：登录设备信息
  - `QQ`：登录的QQ号
  - `Password`：QQ号密码
  - `LoginType`：登录的方式
  - 目前支持两种协议:
    - 安卓 `ANDROID_PHONE`
    - 手表 `ANDROID_WATCH`
    - 平板 `ANDROID_PAD`
    - iPad `IPAD`
    - MacOS `MACOS`
- `escapeSelf`：是否跳过自己机器人的信息
- `ReadEncoding`：读数据包编码
- `SendEncoding`：发送数据包编码
- `AutoReconnect`：自动重连
- `HighwayUpload`：上传通道数
- `authKey`：mriai-http-api密钥
- `authTime`：密钥刷新时间
- `cacheSize`：缓存大小
- `noInput`：没有输入

设置完成后再次启动ColorMirai，出现
```
[INFO] Socket已启动: 23333
``` 
说明机器人已成功启动

## Linux下的挂后台启动
首先修改配置文件的`noInput`为true  
然后用下面指令启动
```
nohup java -jar ColorMirai-xxx-all.jar &
```
