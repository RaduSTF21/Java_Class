package org.example;

import java.util.*;

public class Board {
    private final List<String> submittedWords = Collections.synchronizedList(new ArrayList<>());
    private int currentPlayerIndex = 0;
    private final Object turnLock = new Object();

    public void submitWord(Player player, String word, int score) {
        synchronized (submittedWords) {
            submittedWords.add(word);
            System.out.println(player.getName() + " submitted word: " + word + " for " + score + " points");
        }
    }

    public synchronized int getPlayerTurn() {
        return currentPlayerIndex;
    }

    public synchronized void nextTurn() {
        currentPlayerIndex++;
        synchronized (turnLock) {
            turnLock.notifyAll();
        }
    }

    public synchronized void resetTurn() {
        currentPlayerIndex = 0;
    }

    public void waitForTurn(int playerIndex) throws InterruptedException {
        synchronized (turnLock) {
            while (currentPlayerIndex != playerIndex) {
                turnLock.wait();
            }
        }
    }
}
