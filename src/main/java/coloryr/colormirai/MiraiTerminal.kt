package coloryr.colormirai

import kotlinx.coroutines.CoroutineScope
import net.mamoe.mirai.console.MiraiConsoleFrontEndDescription
import net.mamoe.mirai.console.data.MultiFilePluginDataStorage
import net.mamoe.mirai.console.data.PluginDataStorage
import net.mamoe.mirai.console.terminal.MiraiConsoleImplementationTerminal
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.console.util.SemVersion
import net.mamoe.mirai.utils.MiraiLogger

@OptIn(ConsoleExperimentalApi::class)
class MiraiTerminal : MiraiConsoleImplementationTerminal(), CoroutineScope {
    @ConsoleExperimentalApi
    override val configStorageForBuiltIns: PluginDataStorage
        get() = MultiFilePluginDataStorage(rootPath.resolve("plugin_config"))
    @ConsoleExperimentalApi
    override val configStorageForJvmPluginLoader: PluginDataStorage
        get() = MultiFilePluginDataStorage(rootPath.resolve("plugin_config_storage"))
    @OptIn(ConsoleExperimentalApi::class)
    override val dataStorageForBuiltIns: PluginDataStorage
        get() = MultiFilePluginDataStorage(rootPath.resolve("plugin_data"))
    @ConsoleExperimentalApi
    override val dataStorageForJvmPluginLoader: PluginDataStorage
        get() = MultiFilePluginDataStorage(rootPath.resolve("plugin_data_storage"))
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

    override fun createLogger(identity: String?): MiraiLogger {
        return object : MiraiLogger {
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
    }
}