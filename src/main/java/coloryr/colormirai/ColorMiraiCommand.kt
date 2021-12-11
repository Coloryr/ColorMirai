package coloryr.colormirai

import coloryr.colormirai.plugin.socket.PluginUtils
import net.mamoe.mirai.console.command.ConsoleCommandOwner
import net.mamoe.mirai.console.command.ConsoleCommandSender.sendMessage
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.util.ConsoleExperimentalApi

@OptIn(ConsoleExperimentalApi::class)
object ColorMiraiCommand : SimpleCommand(
    ConsoleCommandOwner, "color",
    description = "ColorMirai控制台"
) {

    @Handler
    suspend fun help(@Name("命令") message: String) {
        when (message) {
            "help" -> {
                sendMessage("插件帮助")
                sendMessage("stop 关闭机器人")
                sendMessage("list 获取连接的插件列表")
                sendMessage("close [插件] 断开插件连接")
            }
            "list" -> {
                sendMessage("插件列表")
                for (item in PluginUtils.getAll()) {
                    sendMessage(item.name + " 注册的包：" + item.reg)
                }
            }
        }
    }

    @Handler
    fun permit(
        @Name("close") message: String,
        @Name("插件名") message1: String
    ) {
        when (message) {
            "close" -> {
                if (!PluginUtils.havePlugin(message1)) {
                    ColorMiraiMain.logger.info("没有插件：$message1")
                }
                PluginUtils.removePlugin(message1)
                ColorMiraiMain.logger.info("正在断开插件$message1")
            }
        }
    }
}