// File: src/main/java/com/example/robot/Location.java
package org.example;

import java.util.Objects;

public class Location implements Comparable<Location> {

    private String name;
    private Type type;

    public Location(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    @Override
    public int compareTo(Location other) {
        // Natural ordering by name
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return "Location{" + "name='" + name + '\'' + ", type=" + type + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return Objects.equals(name, location.name) && type == location.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}
