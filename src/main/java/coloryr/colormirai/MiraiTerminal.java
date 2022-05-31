package coloryr.colormirai;

import kotlinx.coroutines.CoroutineScope;
import net.mamoe.mirai.console.MiraiConsoleFrontEndDescription;
import net.mamoe.mirai.console.data.MultiFilePluginDataStorage;
import net.mamoe.mirai.console.data.PluginDataStorage;
import net.mamoe.mirai.console.terminal.MiraiConsoleImplementationTerminal;
import net.mamoe.mirai.console.util.SemVersion;
import net.mamoe.mirai.utils.MiraiLogger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MiraiTerminal extends MiraiConsoleImplementationTerminal implements CoroutineScope {

    @NotNull
    @Override
    public PluginDataStorage getConfigStorageForBuiltIns() {
        return MultiFilePluginDataStorage.create(getRootPath().resolve("plugin_config"));
    }

    @NotNull
    @Override
    public PluginDataStorage getConfigStorageForJvmPluginLoader() {
        return MultiFilePluginDataStorage.create(getRootPath().resolve("plugin_config_storage"));
    }

    @NotNull
    @Override
    public PluginDataStorage getDataStorageForBuiltIns() {
        return MultiFilePluginDataStorage.create(getRootPath().resolve("plugin_data"));
    }

    @NotNull
    @Override
    public PluginDataStorage getDataStorageForJvmPluginLoader() {
        return MultiFilePluginDataStorage.create(getRootPath().resolve("plugin_data_storage"));
    }

    @NotNull
    @Override
    public MiraiConsoleFrontEndDescription getFrontEndDescription() {
        return new MiraiConsoleFrontEndDescription() {
            @NotNull
            @Override
            public SemVersion getVersion() {
                return SemVersion.parse(ColorMiraiMain.version);
            }

            @NotNull
            @Override
            public String getVendor() {
                return "Color_yr";
            }

            @NotNull
            @Override
            public String getName() {
                return "ColorMirai";
            }
        };
    }

    @NotNull
    @Override
    public MiraiLogger createLogger(@Nullable String s) {
        return new MiraiLogger() {
            @NotNull
            @Override
            public String getIdentity() {
                return "ColorMirai";
            }

            @Override
            public boolean isEnabled() {
                return true;
            }

            @Override
            public void verbose(@Nullable String s) {
                ColorMiraiMain.logger.info(s);
            }

            @Override
            public void verbose(@Nullable String s, @Nullable Throwable throwable) {
                ColorMiraiMain.logger.info(s, throwable);
            }

            @Override
            public void debug(@Nullable String s) {
                ColorMiraiMain.logger.debug(s);
            }

            @Override
            public void debug(@Nullable String s, @Nullable Throwable throwable) {
                ColorMiraiMain.logger.debug(s, throwable);
            }

            @Override
            public void info(@Nullable String s) {
                ColorMiraiMain.logger.info(s);
            }

            @Override
            public void info(@Nullable String s, @Nullable Throwable throwable) {
                ColorMiraiMain.logger.info(s, throwable);
            }

            @Override
            public void warning(@Nullable String s) {
                ColorMiraiMain.logger.warn(s);
            }

            @Override
            public void warning(@Nullable String s, @Nullable Throwable throwable) {
                ColorMiraiMain.logger.warn(s, throwable);
            }

            @Override
            public void error(@Nullable String s) {
                ColorMiraiMain.logger.error(s);
            }

            @Override
            public void error(@Nullable String s, @Nullable Throwable throwable) {
                ColorMiraiMain.logger.error(s, throwable);
            }
        };
    }
}
