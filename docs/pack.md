# ColorMirai

[返回](../README.md)

### [0 [插件]插件开始连接](../src/main/java/Color_yr/ColorMirai/Pack/FromPlugin/StartPack.java)


插件第一次连接需要发送一个数据包来注册所监听事件的包

```Json
{
  "Name": "test",
  "Reg": [49,50,51],
  "Groups" : [],
  "QQs" : [],
  "RunQQ": 0
}
```

- `Name`：插件名字
- `Reg`：监听的包，只有标注`（事件）`的包才会被监听
- `Groups`：只监听的群号，可以为null
- `QQs`：只监听的QQ号，可以为null
- `RunQQ`：插件运行的机器人QQ号，可以为0