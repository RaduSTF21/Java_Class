package org.example;

public class Robot {
    Location startLocation;
    String name;

    public Robot(Location startLocation, String name) {
        this.startLocation = startLocation;
        this.name = name;
    }
    public Location getStartLocation() {
        return startLocation;
    }
    public String getName() {
        return name;
    }
}
