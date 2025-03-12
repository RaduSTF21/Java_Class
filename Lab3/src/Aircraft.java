import java.io.Serializable;

public abstract class Aircraft implements Comparable<Aircraft>{
    private String model;
    private String name;
    private double wingSpan;

    public Aircraft(String model, String name, double wingSpan){
        this.model = model;
        this.name = name;
        this.wingSpan = wingSpan;
    }
    public String getModel(){
        return this.model;
    }
    public String getName(){
        return this.name;
    }
    public double getWingSpan(){
        return this.wingSpan;
    }
    public String toString(){
        return "Model: " + this.model + "  Name: " + this.name + "  Wingspan: " + this.wingSpan;
    }

    @Override
    public int compareTo(Aircraft aircraft){
        return this.name.compareTo(aircraft.name);
    }

}

