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

    public String getName() {
        return name;
    }
}
