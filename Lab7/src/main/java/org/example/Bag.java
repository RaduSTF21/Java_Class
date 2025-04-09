package org.example;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Bag {
    private final Queue<Tile> tiles = new LinkedList<>();
    public Bag() {
        Random rand = new Random();
        for (char c = 'a'; c <= 'z'; c++) {
            for (int i = 0; i < 10; i++) {
                int point = rand.nextInt(10) + 1;
                tiles.add(new Tile(c, point));
            }
        }
        List<Tile> temp = new ArrayList<>(tiles);
        Collections.shuffle(temp);
        tiles.clear();
        tiles.addAll(temp);
    }
    public synchronized List<Tile> extractTiles(int count) {
        List<Tile> extracted = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if (tiles.isEmpty()) break;
            extracted.add(tiles.poll());
        }
        return extracted;
    }
    public synchronized boolean isEmpty() {
        return tiles.isEmpty();
    }
}

