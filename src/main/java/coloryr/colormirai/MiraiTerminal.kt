package coloryr.colormirai

import kotlinx.coroutines.CoroutineScope
import net.mamoe.mirai.console.ConsoleFrontEndImplementation
import net.mamoe.mirai.console.MiraiConsoleFrontEndDescription
import net.mamoe.mirai.console.MiraiConsoleImplementation
import net.mamoe.mirai.console.data.MultiFilePluginDataStorage
import net.mamoe.mirai.console.data.PluginDataStorage
import net.mamoe.mirai.console.terminal.MiraiConsoleImplementationTerminal
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.console.util.SemVersion
import net.mamoe.mirai.utils.MiraiLogger
import kotlin.reflect.KClass

object ColorMiraiLogger : MiraiLogger {
    override val identity: String
        get() = "ColorMirai"
    override val isEnabled: Boolean
        get() = true

    override fun verbose(message: String?) {
        ColorMiraiMain.logger.info(message)
    }

    override fun verbose(message: String?, e: Throwable?) {
        ColorMiraiMain.logger.info(message, e)
    }

    override fun debug(message: String?) {
        ColorMiraiMain.logger.debug(message)
    }

    override fun debug(message: String?, e: Throwable?) {
        ColorMiraiMain.logger.debug(message, e)
    }

    override fun info(message: String?) {
        ColorMiraiMain.logger.info(message)
    }

    override fun info(message: String?, e: Throwable?) {
        ColorMiraiMain.logger.info(message, e)
    }

    override fun warning(message: String?) {
        ColorMiraiMain.logger.warn(message)
    }

    override fun warning(message: String?, e: Throwable?) {
        ColorMiraiMain.logger.warn(message, e)
    }

    override fun error(message: String?) {
        ColorMiraiMain.logger.error(message)
    }

    override fun error(message: String?, e: Throwable?) {
        ColorMiraiMain.logger.error(message, e)
    }
}

object ColorMiraiFactory : MiraiLogger.Factory {
    /**
     * 创建 [MiraiLogger] 实例.
     *
     * @param requester 请求创建 [MiraiLogger] 的对象的 class
     * @param identity 对象标记 (备注)
     */
    override fun create(requester: KClass<*>, identity: String?): MiraiLogger = ColorMiraiLogger

    /**
     * 创建 [MiraiLogger] 实例.
     *
     * @param requester 请求创建 [MiraiLogger] 的对象的 class
     * @param identity 对象标记 (备注)
     */
    override fun create(requester: Class<*>, identity: String?): MiraiLogger = ColorMiraiLogger

    /**
     * 创建 [MiraiLogger] 实例.
     *
     * @param requester 请求创建 [MiraiLogger] 的对象
     */
    override fun create(requester: KClass<*>): MiraiLogger = ColorMiraiLogger

    /**
     * 创建 [MiraiLogger] 实例.
     *
     * @param requester 请求创建 [MiraiLogger] 的对象
     */
    override fun create(requester: Class<*>): MiraiLogger = ColorMiraiLogger
}

@OptIn(ConsoleExperimentalApi::class)
class MiraiTerminal : MiraiConsoleImplementationTerminal(), CoroutineScope {
    @ConsoleExperimentalApi
    override val configStorageForBuiltIns: PluginDataStorage
        get() = MultiFilePluginDataStorage(rootPath.resolve("plugin_config"))

    @ConsoleExperimentalApi
    override val configStorageForJvmPluginLoader: PluginDataStorage
        get() = MultiFilePluginDataStorage(rootPath.resolve("plugin_config"))

    @OptIn(ConsoleExperimentalApi::class)
    override val dataStorageForBuiltIns: PluginDataStorage
        get() = MultiFilePluginDataStorage(rootPath.resolve("plugin_data"))

    @ConsoleExperimentalApi
    override val dataStorageForJvmPluginLoader: PluginDataStorage
        get() = MultiFilePluginDataStorage(rootPath.resolve("plugin_data"))

    @ConsoleExperimentalApi
    override val frontEndDescription: MiraiConsoleFrontEndDescription
        get() = object : MiraiConsoleFrontEndDescription {
            override val version: SemVersion
                get() = SemVersion(ColorMiraiMain.version)
            override val vendor: String
                get() = "Coloryr"
            override val name: String
                get() = "ColorMirai"
        }

    @Deprecated(
        "Deprecated for removal. Implement the other overload, or use MiraiConsole.createLogger instead.",
        replaceWith = ReplaceWith(
            "MiraiLogger.Factory.create(javaClass, identity)",
            "net.mamoe.mirai.utils.MiraiLogger"
        ),
        level = DeprecationLevel.ERROR
    )

    fun createLogger(identity: String?): MiraiLogger {
        return ColorMiraiLogger
    }

    @ConsoleFrontEndImplementation
    override fun createLoggerFactory(context: MiraiConsoleImplementation.FrontendLoggingInitContext):
            MiraiLogger.Factory {
        return ColorMiraiFactory
    }
}