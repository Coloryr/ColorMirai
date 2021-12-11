package coloryr.colormirai

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.console.util.NamedSupervisorJob

class MyCoroutineContext {
    val test: CoroutineScope = CoroutineScope(
        NamedSupervisorJob("MiraiConsoleImplementationTerminal") +
                CoroutineExceptionHandler { coroutineContext, throwable ->
                    if (throwable is CancellationException) {
                        return@CoroutineExceptionHandler
                    }
                    val coroutineName = coroutineContext[CoroutineName]?.name ?: "<unnamed>"
                    MiraiConsole.mainLogger.error("Exception in coroutine $coroutineName", throwable)
                });

}
