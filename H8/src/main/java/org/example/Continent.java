package org.example;



public class Continent extends Entity {

    public Continent() {
        super();
    }

    public Continent(Integer id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return "Continent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}