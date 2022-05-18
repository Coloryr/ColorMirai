package coloryr.colormirai

import coloryr.colormirai.plugin.PluginUtils
import net.mamoe.mirai.console.command.Command
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.console.command.ConsoleCommandOwner
import net.mamoe.mirai.console.command.ConsoleCommandSender.sendMessage
import net.mamoe.mirai.console.util.ConsoleExperimentalApi

@OptIn(ConsoleExperimentalApi::class)
object ColorMiraiCommand : CompositeCommand(
    ConsoleCommandOwner, "color",
    description = "ColorMirai控制台"
), Command {

    @Description("底层指令")
    @SubCommand
    suspend fun help() {
        sendMessage("插件帮助")
        sendMessage("list 获取连接的插件列表")
        sendMessage("close [插件] 断开插件连接")
    }

    @Description("获取插件列表")
    @SubCommand("list")
    suspend fun list() {
        sendMessage("插件列表")
        for (item in PluginUtils.getAll()) {
            sendMessage(item.name + " 注册的包：" + item.reg)
        }
    }

    @Description("断开一个插件")
    @SubCommand("close")
    suspend fun close(
        @Name("插件名") message: String
    ) {
        if (!PluginUtils.havePlugin(message)) {
            sendMessage("没有插件：$message")
            return
        }
        PluginUtils.removePlugin(message)
        sendMessage("正在断开插件$message")
    }
}