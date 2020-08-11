# ColorMirai
一个基于[Mirai](https://github.com/mamoe/mirai) 的机器人框架  
不提供二进制文件，请自行构建  
需要JAVA11环境  
运行环境看你的构建环境  
官方QQ群：[571239090](https://qm.qq.com/cgi-bin/qm/qr?k=85m_MZMJ7BbyZ2vZW4wHVZGGvGnIL2As&jump_from=webapi)  
构建不了可以去群里找人要
## 构建说明
首先安装JDK11并且设置好环境变量  
安装git  
右键打开git bash  
输入下面的指令（期间需要梯子等工具，如果慢的话）
```
git clone https://github.com/Coloryr/ColorMirai.git
./gradlew shadowJar
```
你会得到一个jar
`build/libs/ColorMirai-1.0-SNAPSHOT-all.jar`  
## 启动
> 1. 使用cmd启动
> ```
> java -jar ColorMirai-1.0-SNAPSHOT-all.jar
> ```
> 启动后会生成`info.json`和`MainConfig.json`  
> `info.json`是设备名称，不需要特别设置  
> `MainConfig.json`是配置文件  

> 2.第一次配置  
> `MainConfig.json`里面有下面内容
> ```Json
> {
>   "Password":"密码", 
>   "Port":23333,
>   "QQ":0,
>   "Type":0
> }
> ```
> - `Password`：QQ号密码
> - `Port`：启动的端口
> - `QQ`：登录的QQ号
> - `Type`：登录的方式 0：安卓 1：手表

设置完后再次启动，出现`[INFO]Socket已启动:23333`说明已启动

### 无法登录临时解决
将Type改成1即可登录，下次登录改回0就行了

## Socket数据包
```
以字符串的方式看
{...}[ID]
以byte[]的方式看
123,...,125,[ID]
```
`[ID]`对应数据包的ID  
目前重要的ID：
```
0 插件开始连接
49 [机器人]收到群消息（事件）
50 [机器人]收到群临时会话消息（事件）
51 [机器人]收到朋友消息（事件）

52 [插件]发送群消息
53 [插件]发送私聊消息
54 [插件]发送好友消息

60 心跳包

61 [插件]发送图片到群
62 [插件]发送图片到私聊
63 [插件]发送图片到朋友
```
只有正确的发包，才能处理  
更多包请看[PackDo.java](src/main/java/Color_yr/ColorMirai/Pack/PackDo.java)  
每个包需要发送的数据都在`/Pack`路径下的java  
标注为`[机器人]`是框架发给插件的  
标注为`[插件]`是插件发送给框架的
## 插件连接
插件第一次连接需要发送这个json
```json
{
  "Name": "test",
  "Reg": [49,50,51]
}
```
- `Name`：插件名字
- `Reg`：监听的包

注意：所有包必须带ID，否则无法识别

## 目前的示例代码
[C#](demo/C%23demo.cs)  
[JAVA](demo/JAVAdemo.java)  
示例代码不包含Main函数，可以作为库用

懒得写了（jiushi）