package coloryr.colormirai.plugin;

public interface IPluginSocket {
    boolean send(Object data, int index);

    void close();
}
