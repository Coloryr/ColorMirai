# ColorMirai

[返回](../README.md)

## 启动

1. 启动ColorMirai，必须使用openjdk8及以上，推荐使用openjdk17启动  
   请务必用openjdk启动  
   请务必用openjdk启动  
   请务必用openjdk启动

如果没有openjdk，群文件有openjdk17，下载安装之后新建一个`.cmd`，使用JAVA启动，例如在里面输入
```
"C:\Program Files\AdoptOpenJDK\jdk-17\bin\java.exe" -Xmx1024M -jar ColorMirai-4.x.x-SNAPSHOT-all.jar
pause
```
- `jdk版本`要根据你安装的版本修改  
  `ColorMirai-4.x.x-SNAPSHOT-all`也根据你运行的版本修改

如果你的系统环境变量默认是openjdk，则cmd内容是下面
```
java -Xmx1024M -jar ColorMirai-4.X.X-SNAPSHOT-all.jar
pause
```

首次启动后, 会生成四个日志文件夹和`device.json`、`config.json`两个配置文件

- `device.json`是设备名称，不需要特别设置
- `config.json`是配置文件

2. 默认配置

`config.json`

```Json5
{
  "autoReconnect":true,
  "highwayUpload":10,
  "maxList":100000,
  "pack":true,
  "qqList": [
    {
      "info": "device.json",
      "loginType": "ANDROID_PHONE",
      "password": "请填写你的密码",
      "qq": 0
    }
  ],
  "sendEncoding":"UTF-8",
  "readEncoding":"UTF-8",
  "socketPort":23333,
  "webSocketPort":23334,
  "nettyPort": 23335,
  "cacheSize":100,
  "escapeSelf":true,
  "noInput": false,
  "debug": false,
  "maxNettyPackSize": 500
}
```

- `autoReconnect`：自动重连
- `highwayUpload`：上传通道数
- `maxList`：最大消息列表
- `pack`：是否发送心跳包
- `socketPort`：Socket启动的端口
- `webSocketPort`：websocket启动的端口
- `nettyPort`：netty启动端口
- `qqList` : QQ账号列表 (可以添加多个)
  - `info`：登录设备信息
  - `qq`：登录的QQ号
  - `password`：QQ号密码
  - `loginType`：登录的方式
  - 目前支持协议:
    - 安卓 `ANDROID_PHONE`
    - 手表 `ANDROID_WATCH`
    - 平板 `ANDROID_PAD`
    - iPad `IPAD`
    - MacOS `MACOS`
- `escapeSelf`：是否跳过自己机器人的信息
- `readEncoding`：读数据包编码
- `sendEncoding`：发送数据包编码
- `cacheSize`：缓存大小
- `noInput`：没有输入
- `debug`: 调试
- `maxNettyPackSize`: netty数据包最大长度

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
