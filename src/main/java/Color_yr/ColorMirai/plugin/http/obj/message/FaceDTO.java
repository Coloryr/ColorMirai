package Color_yr.ColorMirai.plugin.http.obj.message;

public class FaceDTO extends MessageDTO{
    public int faceId;
    public String name;

    public FaceDTO(int faceId, String name)
    {
        this.faceId = faceId;
        this.name = name;
        this.type = "Face";
    }
}
