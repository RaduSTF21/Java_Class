package org.example;

import java.awt.Point;
import java.util.List;
import java.io.Serializable;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
    public List<Point> dots;
    public List<GameLine> lines;
    public boolean isPlayerOneTurn;
    public double playerOneScore;
    public double playerTwoScore;
}
