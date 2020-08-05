package Color_yr.ColorMirai.Pack.ToPlugin;

public class ImageUploadEventAPack {
    private long id;
    private String name;

    public ImageUploadEventAPack(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ImageUploadEventAPack() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
