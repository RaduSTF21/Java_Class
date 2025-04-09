package org.example;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Player implements Runnable {
    private final String name;
    private final Game game;
    private List<Tile> hand = new ArrayList<>();
    public Player(String name, Game game) {
        this.name = name;
        this.game = game;
    }
    public String getName() { return name; }
    private String createWord(List<Tile> tiles) {
        StringBuilder sb = new StringBuilder();
        for (Tile t : tiles) sb.append(t.getLetter());
        return sb.toString();
    }
    public void run() {
        while (!game.getBag().isEmpty()) {
            if (hand.size() < 7) {
                int needed = 7 - hand.size();
                List<Tile> newTiles = game.getBag().extractTiles(needed);
                if (newTiles.isEmpty()) break;
                hand.addAll(newTiles);
            }
            if (hand.isEmpty()) break;
            String word = createWord(hand);
            if (game.getDictionary().isWord(word)) {
                game.getBoard().addWord(name, word);
                hand.clear();
                hand.addAll(game.getBag().extractTiles(word.length()));
            } else {
                System.out.println(name + " submitted an invalid word: " + word);
                hand.clear();
                hand.addAll(game.getBag().extractTiles(7));
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}