package coloryr.colormirai

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.launch
import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.command.*
import net.mamoe.mirai.console.command.CommandExecuteResult.*
import net.mamoe.mirai.console.command.descriptor.AbstractCommandValueParameter.StringConstant
import net.mamoe.mirai.console.command.descriptor.CommandReceiverParameter
import net.mamoe.mirai.console.command.descriptor.CommandValueParameter
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.command.parse.CommandCall
import net.mamoe.mirai.console.command.parse.CommandValueArgument
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.console.util.cast
import net.mamoe.mirai.console.util.requestInput
import net.mamoe.mirai.console.util.safeCast
import org.jline.reader.EndOfFileException
import org.jline.reader.UserInterruptException
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

@OptIn(ExperimentalCommandDescriptors::class, ConsoleExperimentalApi::class)
fun startupConsoleThread() {
    MiraiConsole.launch(CoroutineName("Console Command")) {
        while (true) {
            val next = try {
                MiraiConsole.requestInput("").let {
                    when {
                        it.isBlank() -> it
                        it.startsWith(CommandManager.commandPrefix) -> it
                        it == "?" -> CommandManager.commandPrefix + BuiltInCommands.HelpCommand.primaryName
                        else -> CommandManager.commandPrefix + it
                    }
                }
            } catch (e: InterruptedException) {
                return@launch
            } catch (e: CancellationException) {
                return@launch
            } catch (e: UserInterruptException) {
                BuiltInCommands.StopCommand.run { ConsoleCommandSender.handle() }
                return@launch
            } catch (eof: EndOfFileException) {
                ColorMiraiMain.logger.warn("Closing input service...")
                return@launch
            } catch (e: Throwable) {
                ColorMiraiMain.logger.error("Error in reading next command", e)
                ColorMiraiMain.logger.warn("Closing input service...")
                return@launch
            }
            if (next.isBlank()) {
                continue
            }
            try {
                if (next.toLowerCase().startsWith("/login") || next.toLowerCase().startsWith("/autologin")) {
                    ColorMiraiMain.logger.warn("不能使用这个指令")
                    continue
                }
                if (next.equals("/stop")) {
                    ColorMiraiMain.stop()
                }
                // consoleLogger.debug("INPUT> $next")
                when (val result = ConsoleCommandSender.executeCommand(next)) {
                    is Success -> {
                    }
                    is IllegalArgument -> { // user wouldn't want stacktrace for a parser error unless it is in debugging mode (to do).
                        val message = result.exception.message
                        if (message != null) {
                            ColorMiraiMain.logger.warn(message)
                        } else ColorMiraiMain.logger.warn(result.exception)
                    }
                    is ExecutionFailed -> {
                        ColorMiraiMain.logger.error(result.exception)
                    }
                    is UnresolvedCommand -> {
                        ColorMiraiMain.logger.warn("未知指令: ${next}, 输入 ? 获取帮助")
                    }
                    is PermissionDenied -> {
                        ColorMiraiMain.logger.warn("权限不足.")
                    }
                    is UnmatchedSignature -> {
                        ColorMiraiMain.logger.warn(
                            "参数不匹配, 你是否想执行: \n" + result.failureReasons.render(
                                result.command,
                                result.call
                            )
                        )
                    }
                    is Failure -> {
                        ColorMiraiMain.logger.warn(result.toString())
                    }
                }
            } catch (e: InterruptedException) {
                return@launch
            } catch (e: CancellationException) {
                return@launch
            } catch (e: Throwable) {
                ColorMiraiMain.logger.error("Unhandled exception", e)
            }
        }
    }
}

@OptIn(ExperimentalCommandDescriptors::class, ConsoleExperimentalApi::class)
private fun List<UnmatchedCommandSignature>.render(command: Command, call: CommandCall): String {
    val list =
        this.filter lambda@{ signature ->
            if (signature.failureReason.safeCast<FailureReason.InapplicableValueArgument>()?.parameter is StringConstant) return@lambda false
            if (signature.signature.valueParameters.anyStringConstantUnmatched(call.valueArguments)) return@lambda false
            true
        }
    if (list.isEmpty()) {
        return command.usage
    }
    return list.joinToString("\n") { it.render(command) }
}

@OptIn(ExperimentalCommandDescriptors::class, ConsoleExperimentalApi::class)
private fun List<CommandValueParameter<*>>.anyStringConstantUnmatched(arguments: List<CommandValueArgument>): Boolean {
    return this.zip(arguments).any { (parameter, argument) ->
        parameter is StringConstant && !parameter.accepts(argument, null)
    }
}

@OptIn(ExperimentalCommandDescriptors::class, ConsoleExperimentalApi::class)
internal fun UnmatchedCommandSignature.render(command: Command): String {
    @Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")
    val usage =
        net.mamoe.mirai.console.internal.command.CommandReflector.generateUsage(command, null, listOf(this.signature))
    return usage.trim() + "    (${failureReason.render()})"
}

@OptIn(ExperimentalCommandDescriptors::class, ConsoleExperimentalApi::class)
internal fun FailureReason.render(): String {
    return when (this) {
        is FailureReason.InapplicableReceiverArgument -> "需要由 ${this.parameter.renderAsName()} 执行"
        is FailureReason.InapplicableArgument -> "参数类型错误"
        is FailureReason.TooManyArguments -> "参数过多"
        is FailureReason.NotEnoughArguments -> "参数不足"
        is FailureReason.ResolutionAmbiguity -> "调用歧义"
        is FailureReason.ArgumentLengthMismatch -> {
            // should not happen, render it anyway.
            "参数长度不匹配"
        }
    }
}

@OptIn(ExperimentalCommandDescriptors::class, ConsoleExperimentalApi::class)
internal fun CommandReceiverParameter<*>.renderAsName(): String {
    val classifier = this.type.classifier.cast<KClass<out CommandSender>>()
    return when {
        classifier.isSubclassOf(ConsoleCommandSender::class) -> "控制台"
        classifier.isSubclassOf(FriendCommandSenderOnMessage::class) -> "好友私聊"
        classifier.isSubclassOf(FriendCommandSender::class) -> "好友"
        classifier.isSubclassOf(MemberCommandSenderOnMessage::class) -> "群内发言"
        classifier.isSubclassOf(MemberCommandSender::class) -> "群成员"
        classifier.isSubclassOf(GroupTempCommandSenderOnMessage::class) -> "群临时会话"
        classifier.isSubclassOf(GroupTempCommandSender::class) -> "群临时好友"
        classifier.isSubclassOf(UserCommandSender::class) -> "用户"
        else -> classifier.simpleName ?: classifier.toString()
    }
}