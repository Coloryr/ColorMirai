package coloryr.colormirai.plugin;

public interface IPluginSocket {
    boolean send(Object data, int index);

    void setPlugin(ThePlugin plugin);

    void close();
}
