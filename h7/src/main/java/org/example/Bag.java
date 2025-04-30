package org.example;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Bag {
    private final List<Tile> tiles = new ArrayList<>();
    private final Random random = new Random();
    private final AtomicBoolean empty = new AtomicBoolean(false);

    public Bag() {
        initializeBag();
    }

    private void initializeBag() {
        for (char c = 'a'; c <= 'z'; c++) {
            int value = random.nextInt(10) + 1;
            for (int i = 0; i < 10; i++) {
                tiles.add(new Tile(c, value));
            }
        }

        Collections.shuffle(tiles);
        System.out.println("Bag initialized with " + tiles.size() + " tiles");
    }

    public synchronized List<Tile> extractTiles(int count) {
        List<Tile> extracted = new ArrayList<>();
        int available = Math.min(count, tiles.size());

        for (int i = 0; i < available; i++) {
            extracted.add(tiles.remove(0));
        }

        if (tiles.isEmpty()) {
            empty.set(true);
        }

        return extracted;
    }

    public int getSize() {
        return tiles.size();
    }

    public boolean isEmpty() {
        return empty.get();
    }
}