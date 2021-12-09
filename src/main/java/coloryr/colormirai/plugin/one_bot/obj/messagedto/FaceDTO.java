package coloryr.colormirai.plugin.one_bot.obj.messagedto;

class FaceIDTO{
    public String id;
}
public class FaceDTO {
    public final String type = "face";
    public FaceIDTO data;

    public void set(int face) {
        data = new FaceIDTO() {{
            id = String.valueOf(face);
        }};
    }

    public void set(String face) {
        data = new FaceIDTO() {{
            id = face;
        }};
    }
}
