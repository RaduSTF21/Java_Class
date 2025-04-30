package org.example;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameController {
    private final List<Player> players;
    private final Bag bag;
    private final Board board;
    private final int timeLimit;
    private final AtomicBoolean gameRunning = new AtomicBoolean(true);
    private final CountDownLatch gameFinished = new CountDownLatch(1);

    public GameController(List<Player> players, Bag bag, Board board, int timeLimit) {
        this.players = players;
        this.bag = bag;
        this.board = board;
        this.timeLimit = timeLimit;
    }

    public void startGame() {
        System.out.println("Game starting with " + players.size() + " players!");
        System.out.println("Bag contains " + bag.getSize() + " tiles");


        Thread timekeeper = new Thread(new Timekeeper(timeLimit, this));
        timekeeper.setDaemon(true);
        timekeeper.start();


        List<Thread> playerThreads = new ArrayList<>();
        for (Player player : players) {
            Thread thread = new Thread(player);
            playerThreads.add(thread);
            thread.start();
        }


        try {
            gameFinished.await();
            System.out.println("Game has ended!");


            for (Player player : players) {
                player.stopPlaying();
            }


            for (Thread thread : playerThreads) {
                thread.join();
            }


            determineWinner();

        } catch (InterruptedException e) {
            System.out.println("Game was interrupted");
        }
    }

    public void stopGame() {
        if (gameRunning.compareAndSet(true, false)) {
            gameFinished.countDown();
        }
    }

    public boolean isGameRunning() {
        return gameRunning.get();
    }

    private void determineWinner() {
        Player winner = players.get(0);
        for (Player player : players) {
            System.out.println(player.getName() + " scored " + player.getScore() + " points");
            if (player.getScore() > winner.getScore()) {
                winner = player;
            }
        }
        System.out.println("\nThe winner is " + winner.getName() + " with " + winner.getScore() + " points!");
    }
}