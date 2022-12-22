# ColorMirai

[返回](../README.md)

## 构建说明

需要openjdk8环境  
运行环境看你的构建环境  
构建不了可以去群里找人要，也可以群里下载

请根据自己的系统先安装openjdk8并且设置好环境变量

1. 安装git
2. 右键打开git bash
3. 输入下面的指令（如果慢的话, 可能需要梯子等工具辅助下载）

修改文件`gradle.properties`下的JAVA路径为jdk8的路径

```bash
git clone https://github.com/Coloryr/ColorMirai.git
cd ColorMirai
./gradlew shadowJar
```

在`BUILD SUCCESSFUL`之后, 你会在以下路径找到一个jar文件

`build/libs/ColorMirai-4.x.x-SNAPSHOT-all.jar`