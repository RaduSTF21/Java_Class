package org.example;

import java.io.File;
import java.nio.file.Path;
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
    public Path getPath(Image image){
        return image.path();
    }

    public Image getImage(String name){
        return images.stream().filter(image -> image.name().equals(name)).findFirst().get();
    }
    public void print(){
        System.out.println(this.name);
        this.images.forEach(image -> System.out.println(image.name()));
    }
    public void removeImage(String name) throws ImageNotFoundException {
        boolean removed = images.removeIf(img -> img.name().equals(name));
        if(!removed){
            throw new ImageNotFoundException("Image not found: " + name);
        }
    }
    public void setImages(List<Image> images){
        this.images = images;
    }
    public void updateImage(String name, Image updatedImage) throws ImageNotFoundException {
        for(int i = 0; i<images.size(); i++){
            if(images.get(i).name().equalsIgnoreCase(name)){
                images.set(i, updatedImage);
                return;
            }
        }
        throw new ImageNotFoundException("Image not found: " + name);
    }


}
