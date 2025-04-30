package org.example;

import java.io.*;
import java.util.*;

public class Dictionary {
    private Set<String> words = new HashSet<>();

    public Dictionary(String filename) {
        if (filename == null || filename.isEmpty()) {
            generateSimpleDictionary();
            return;
        }

        try {
            loadDictionary(filename);
        } catch (IOException e) {
            System.err.println("Failed to load dictionary: " + e.getMessage());
            generateSimpleDictionary();
        }
    }

    private void loadDictionary(String filename) throws IOException {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(filename);
             BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line.trim().toLowerCase());
            }
        }
        System.out.println("Dictionary loaded with " + words.size() + " words.");
    }

    private void generateSimpleDictionary() {
        System.out.println("Using a simple test dictionary");
        String[] commonWords = {
                "the", "be", "to", "of", "and", "a", "in", "that", "have", "it",
                "for", "not", "on", "with", "he", "as", "you", "do", "at", "this",
                "but", "his", "by", "from", "they", "we", "say", "her", "she", "or",
                "an", "will", "my", "one", "all", "would", "there", "their", "what",
                "so", "up", "out", "if", "about", "who", "get", "which", "go", "me",
                "cat", "dog", "run", "jump", "play", "bird", "fish", "tree", "sun", "moon",
                "star", "book", "page", "read", "write", "pen", "ink", "paper", "cup", "plate",
                "fork", "spoon", "knife", "table", "chair", "room", "house", "door", "window", "floor"
        };
        words.addAll(Arrays.asList(commonWords));
    }

    public boolean contains(String word) {
        return words.contains(word.toLowerCase());
    }
}
