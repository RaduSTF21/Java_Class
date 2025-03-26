import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Repository{
    private List<Image> images;
    private String name;

    public Repository(String name){
        this.name = name;
        this.images = new ArrayList<>();
    }

    public void addImage(Image image){
        this.images.add(image);
    }

    public List<Image> getImages(){
        return this.images;
    }
    public String getName(){
        return this.name;
    }
    public File getPath(Image image){
        return image.path();
    }

    public Image getImage(String name){
        return images.stream().filter(image -> image.name().equals(name)).findFirst().get();
    }
    public void print(){
        System.out.println(this.name);
        this.images.forEach(image -> System.out.println(image.name()));
    }

}
