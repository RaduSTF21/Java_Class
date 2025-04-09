package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
public class Game {
    private final Bag bag = new Bag();
    private final Board board = new Board();
    private final Dictionary dictionary = new Dictionary();
    private final List<Player> players = new ArrayList<>();
    public Bag getBag() { return bag; }
    public Board getBoard() { return board; }
    public Dictionary getDictionary() { return dictionary; }
    public void addPlayer(Player p) { players.add(p); }
    public void play() {
        List<Thread> threads = new ArrayList<>();
        for (Player p : players) {
            Thread t = new Thread(p);
            threads.add(t);
            t.start();
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Final board state: " + board);
    }
    public static void main(String[] args) {
        Game game = new Game();
        game.addPlayer(new Player("Alice", game));
        game.addPlayer(new Player("Bob", game));
        game.addPlayer(new Player("Charlie", game));
        game.play();
    }
}