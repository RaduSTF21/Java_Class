package org.example;


import java.util.ArrayList;
import java.util.List;

public class WordGame {
    public static void main(String[] args) {
        int numberOfPlayers = 3;
        int timeLimit = 10;

        Dictionary dictionary = new Dictionary("dictionary.txt");
        Bag bag = new Bag();
        Board board = new Board();

        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= numberOfPlayers; i++) {
            players.add(new Player("Player " + i, bag, board, dictionary));
        }

        GameController gameController = new GameController(players, bag, board, timeLimit);

        gameController.startGame();
    }
}