package org.example;

import java.util.*;

public class Player implements Runnable {
    private final String name;
    private final Bag bag;
    private final Board board;
    private final Dictionary dictionary;
    private final List<Tile> hand = new ArrayList<>();
    private int score = 0;
    private final int playerIndex;
    private static int playerCount = 0;
    private volatile boolean playing = true;

    public Player(String name, Bag bag, Board board, Dictionary dictionary) {
        this.name = name;
        this.bag = bag;
        this.board = board;
        this.dictionary = dictionary;
        this.playerIndex = playerCount++;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void stopPlaying() {
        playing = false;
    }

    private void drawInitialHand() {
        List<Tile> newTiles = bag.extractTiles(7);
        hand.addAll(newTiles);
        System.out.println(name + " drew initial hand: " + tilesAsString());
    }

    private String tilesAsString() {
        StringBuilder sb = new StringBuilder();
        for (Tile tile : hand) {
            sb.append(tile.getLetter());
        }
        return sb.toString();
    }

    private String findValidWord() {

        List<String> possibleWords = new ArrayList<>();


        for (int len = 1; len <= hand.size(); len++) {
            findCombinations(hand, len, 0, new ArrayList<>(), possibleWords);
        }


        String bestWord = null;
        int bestScore = 0;

        for (String word : possibleWords) {
            if (dictionary.contains(word)) {
                int wordScore = calculateWordScore(word);
                if (wordScore > bestScore) {
                    bestScore = wordScore;
                    bestWord = word;
                }
            }
        }

        return bestWord;
    }

    private void findCombinations(List<Tile> tiles, int len, int startPos, List<Tile> current, List<String> result) {
        if (current.size() == len) {
            StringBuilder sb = new StringBuilder();
            for (Tile t : current) {
                sb.append(t.getLetter());
            }
            result.add(sb.toString());
            return;
        }

        for (int i = startPos; i < tiles.size(); i++) {
            current.add(tiles.get(i));
            findCombinations(tiles, len, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }

    private int calculateWordScore(String word) {
        int wordScore = 0;
        for (char c : word.toCharArray()) {
            for (Tile tile : hand) {
                if (tile.getLetter() == c) {
                    wordScore += tile.getPoints();
                    break;
                }
            }
        }
        return wordScore;
    }

    private void removeTilesFromHand(String word) {
        for (char c : word.toCharArray()) {
            Iterator<Tile> iterator = hand.iterator();
            while (iterator.hasNext()) {
                Tile tile = iterator.next();
                if (tile.getLetter() == c) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    @Override
    public void run() {
        drawInitialHand();

        while (playing && !bag.isEmpty()) {
            try {

                board.waitForTurn(playerIndex);

                if (!playing) {
                    break;
                }

                System.out.println(name + "'s turn with hand: " + tilesAsString());

                String word = findValidWord();

                if (word != null && !word.isEmpty()) {
                    int wordScore = calculateWordScore(word);

                    board.submitWord(this, word, wordScore);

                    score += wordScore;

                    removeTilesFromHand(word);

                    List<Tile> newTiles = bag.extractTiles(word.length());
                    hand.addAll(newTiles);

                    System.out.println(name + " drew " + newTiles.size() + " new tiles, hand now: " + tilesAsString());
                } else {
                    System.out.println(name + " couldn't form a word, discarding tiles and drawing new ones");
                    hand.clear();
                    List<Tile> newTiles = bag.extractTiles(7);
                    hand.addAll(newTiles);
                    System.out.println(name + " new hand: " + tilesAsString());
                }

                if (playerIndex == playerCount - 1) {
                    board.resetTurn();
                } else {
                    board.nextTurn();
                }

                Thread.sleep(100);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println(name + " has finished playing with a score of " + score);
    }
}