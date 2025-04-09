package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Board {
    private final List<String> words = new ArrayList<>();
    public synchronized void addWord(String playerName, String word) {
        words.add(word);
        System.out.println(playerName + " submitted: " + word);
    }
    public String toString() {
        return words.toString();
    }
}