package Color_yr.ColorMirai.plugin.http.obj.message;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(typeName = "File")
public class FileMessageDTO extends MessageDTO {
    public String id;
    public int internalId;
    public String name;
    public long size;

    public FileMessageDTO(String id, int internalId, String name, long size) {
        this.id = id;
        this.internalId = internalId;
        this.name = name;
        this.size = size;
    }
}
