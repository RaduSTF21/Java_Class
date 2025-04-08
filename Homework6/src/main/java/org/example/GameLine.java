package org.example;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;

public class GameLine implements Serializable {
    private static final long serialVersionUID = 1L;
    private Point p1;
    private Point p2;
    private Color color;

    public GameLine(Point p1, Point p2, Color color) {
        this.p1 = p1;
        this.p2 = p2;
        this.color = color;
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public Color getColor() {
        return color;
    }
}
