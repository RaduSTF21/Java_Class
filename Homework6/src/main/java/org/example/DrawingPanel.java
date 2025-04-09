package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DrawingPanel extends JPanel {
    private final MainFrame frame;
    private int canvasWidth = 800;
    private int canvasHeight = 600;
    private int dotSize = 20;

    private List<Point> dots;
    private List<GameLine> lines;

    private BufferedImage image;
    private Graphics2D offscreen;

    private boolean isPlayerOneTurn = true;
    private double playerOneScore = 0.0;
    private double playerTwoScore = 0.0;

    public DrawingPanel(MainFrame frame, ConfigPanel configPanel) {
        this.frame = frame;
        dots = new ArrayList<>();
        lines = new ArrayList<>();
        createOffscreenImage();
        generateRandDots(configPanel.getVal());
        setPreferredSize(new Dimension(canvasWidth, canvasHeight));

        addMouseListener(new MouseAdapter() {
            private Point selectedDot = null;

            @Override
            public void mousePressed(MouseEvent e) {
                for (Point p : dots) {
                    if (Math.abs(e.getX() - p.x) < dotSize / 2 &&
                            Math.abs(e.getY() - p.y) < dotSize / 2) {
                        selectedDot = p;
                        break;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (selectedDot != null) {
                    for (Point p : dots) {
                        if (p != selectedDot &&
                                Math.abs(e.getX() - p.x) < dotSize / 2 &&
                                Math.abs(e.getY() - p.y) < dotSize / 2) {
                            addLine(selectedDot, p);
                            break;
                        }
                    }
                    selectedDot = null;
                }
            }
        });
    }

    private void createOffscreenImage() {
        image = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        offscreen = image.createGraphics();
        offscreen.setColor(Color.WHITE);
        offscreen.fillRect(0, 0, canvasWidth, canvasHeight);
    }

    public void generateRandDots(int numDots) {
        dots.clear();
        for (int i = 0; i < numDots; i++) {
            int x = (int) (Math.random() * (canvasWidth - dotSize) + dotSize / 2);
            int y = (int) (Math.random() * (canvasHeight - dotSize) + dotSize / 2);
            dots.add(new Point(x, y));
        }
        lines.clear();
        updateOffscreen();
        repaint();
    }

    public void addLine(Point p1, Point p2) {
        GameLine newLine = new GameLine(p1, p2, isPlayerOneTurn ? Color.BLUE : Color.RED);
        lines.add(newLine);
        double length = p1.distance(p2);
        if (isPlayerOneTurn) {
            playerOneScore += length;
        } else {
            playerTwoScore += length;
        }
        isPlayerOneTurn = !isPlayerOneTurn;
        updateOffscreen();
        repaint();
    }

    private void updateOffscreen() {
        offscreen.setColor(Color.WHITE);
        offscreen.fillRect(0, 0, canvasWidth, canvasHeight);
        paintDots();
        paintLines();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

    private void paintDots() {
        offscreen.setColor(Color.BLACK);
        for (Point p : dots) {
            int x = p.x - dotSize / 2;
            int y = p.y - dotSize / 2;
            offscreen.fillOval(x, y, dotSize, dotSize);
        }
    }

    private void paintLines() {
        for (GameLine gl : lines) {
            offscreen.setColor(gl.getColor());
            offscreen.draw(new Line2D.Double(gl.getP1(), gl.getP2()));
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public void saveGame(String filename) {
        GameState state = new GameState();
        state.dots = dots;
        state.lines = lines;
        state.isPlayerOneTurn = isPlayerOneTurn;
        state.playerOneScore = playerOneScore;
        state.playerTwoScore = playerTwoScore;
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(state);
            System.out.println("Game saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGame(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            GameState state = (GameState) in.readObject();
            dots = state.dots;
            lines = state.lines;
            isPlayerOneTurn = state.isPlayerOneTurn;
            playerOneScore = state.playerOneScore;
            playerTwoScore = state.playerTwoScore;
            updateOffscreen();
            repaint();
            System.out.println("Game loaded from " + filename);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public double computeMSTCost() {
        int n = dots.size();
        if (n == 0) return 0.0;
        boolean[] visited = new boolean[n];
        double[] minDist = new double[n];
        for (int i = 0; i < n; i++) {
            minDist[i] = Double.MAX_VALUE;
        }
        minDist[0] = 0.0;
        double totalCost = 0.0;
        for (int i = 0; i < n; i++) {
            int u = -1;
            for (int j = 0; j < n; j++) {
                if (!visited[j] && (u == -1 || minDist[j] < minDist[u])) {
                    u = j;
                }
            }
            visited[u] = true;
            totalCost += minDist[u];
            for (int v = 0; v < n; v++) {
                if (!visited[v]) {
                    double dx = dots.get(u).x - dots.get(v).x;
                    double dy = dots.get(u).y - dots.get(v).y;
                    double dist = Math.sqrt(dx * dx + dy * dy);
                    if (dist < minDist[v]) {
                        minDist[v] = dist;
                    }
                }
            }
        }

        return totalCost;
    }

    public void resetGameScores() {
        isPlayerOneTurn = true;
        playerOneScore = 0.0;
        playerTwoScore = 0.0;
    }

    public double getPlayerOneScore() {
        return playerOneScore;
    }

    public double getPlayerTwoScore() {
        return playerTwoScore;
    }
}
