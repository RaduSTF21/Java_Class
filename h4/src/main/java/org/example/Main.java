package org.example;



import com.github.javafaker.Faker;

import java.util.*;

import org.graph4j.GraphBuilder;
import org.graph4j.Graph;
import org.graph4j.shortestpath.DijkstraShortestPathBase;
import org.graph4j.shortestpath.DijkstraShortestPathDefault;


import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        Faker faker = new Faker();
        int numLocations = 5;
        Location[] locations = new Location[numLocations];
        Random random = new Random();
        Type[] types = Type.values();

        for (int i = 0; i < numLocations; i++) {
            String fakeName = faker.address().city();

             Type type = types[random.nextInt(types.length)];
            int typeIndex = random.nextInt(3);
            if (typeIndex == 0) {
                type = Type.FRIENDLY;
            } else if (typeIndex == 1) {
                type = Type.NEUTRAL;
            } else {
                type = Type.ENEMY;
            }
            locations[i] = new Location(fakeName, type);
        }




        Graph mOLocation = GraphBuilder.empty().buildGraph();
        Arrays.stream(locations).forEach(loc -> mOLocation.addLabeledVertex(loc));
        mOLocation.addEdge(locations[0], locations[1]);
        mOLocation.setEdgeWeight(mOLocation.findVertex(locations[0]),mOLocation.findVertex(locations[1]),2);
        mOLocation.addEdge(locations[0], locations[3]);
        mOLocation.setEdgeWeight(mOLocation.findVertex(locations[0]),mOLocation.findVertex(locations[3]),5);
        mOLocation.addEdge(locations[1], locations[2]);
        mOLocation.setEdgeWeight(mOLocation.findVertex(locations[1]),mOLocation.findVertex(locations[2]),1);
        mOLocation.addEdge(locations[3], locations[4]);
        mOLocation.setEdgeWeight(mOLocation.findVertex(locations[3]),mOLocation.findVertex(locations[4]),3);
        mOLocation.addEdge(locations[2], locations[4]);
        mOLocation.setEdgeWeight(mOLocation.findVertex(locations[2]),mOLocation.findVertex(locations[4]),1);

        Robot r = new Robot(locations[0],faker.name().username());

        DijkstraShortestPathBase dijkstraShortestPathBase = new DijkstraShortestPathDefault(mOLocation,mOLocation.findVertex(r.startLocation));

        Arrays.stream(locations).forEach(loc -> {
            var path = Arrays.stream(dijkstraShortestPathBase.findPath(mOLocation.findVertex(loc)).vertices()).mapToObj(mOLocation::getVertexLabel).collect(Collectors.toList());
            double distance = dijkstraShortestPathBase.getPathWeight(mOLocation.findVertex(loc));
            System.out.println("Path: "+ path + " Distance: " + distance);
        });

        System.out.println();

        Map<Type, List<Location>> locsList = new HashMap<>();

        locsList = Arrays.stream(locations).collect(Collectors.groupingBy(Location::getType));
        List<Type> order = Arrays.asList(Type.FRIENDLY, Type.NEUTRAL, Type.ENEMY);

        locsList.entrySet().stream().sorted(Comparator.comparingInt(item -> order.indexOf(item.getKey()))).forEach(item ->{
            System.out.println(item.getKey().toString() + " Locations: " );
            item.getValue().forEach(loc ->{
                double distance = dijkstraShortestPathBase.getPathWeight(mOLocation.findVertex(loc));
                System.out.println(loc.getName() + " Distance: " + distance);
            });
        });
    }
}