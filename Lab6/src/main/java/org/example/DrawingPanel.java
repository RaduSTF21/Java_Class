package org.example;

import javax.sound.sampled.Line;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class DrawingPanel extends JPanel {
    private final MainFrame frame;
    private int canvasWidth = 800;
    private int canvasHeight = 600;
    
    private int dotSize = 20;
    private List<Point> dots;
    private List<Line2D> lines;
    
    private BufferedImage image;
    private Graphics2D offscreen;


    public DrawingPanel(MainFrame frame, ConfigPanel configPanel) {
        this.frame = frame;
        dots = new ArrayList<>();
        lines = new ArrayList<>();
        createOffscreenImage();
        generateRandDots(configPanel.getVal());
        setPreferredSize(new Dimension(canvasWidth, canvasHeight));
    }

    private void createOffscreenImage() {
        image = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        offscreen = image.createGraphics();
        offscreen.setColor(Color.WHITE);
        offscreen.fillRect(0, 0, canvasWidth, canvasHeight);
    }

    public void generateRandDots(int numDots) {
        dots.clear();
        for(int i = 0; i < numDots; i++) {
            int x = (int) (Math.random() * (canvasWidth-dotSize) + dotSize / 2) ;
            int y = (int) (Math.random() * (canvasHeight-dotSize)+dotSize / 2) ;
            dots.add(new Point(x,y));
        }
        lines.clear();
        updateOffscreen();
        repaint();
    }
    public void addLine(Point p1, Point p2) {
        lines.add(new Line2D.Double(p1, p2));
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
        g.drawImage(image,0,0,this);
    }

    private void paintDots() {
        offscreen.setColor(Color.black);
        for(Point p : dots) {
            int x = p.x - dotSize / 2;
            int y = p.y - dotSize / 2;
            offscreen.fillOval(x,y,dotSize,dotSize);

        }
    }
    private void paintLines() {
        offscreen.setColor(Color.blue);
        for(Line2D line : lines) {
            offscreen.draw(line);
        }
    }
    @Override
    public void update(Graphics g) {

    }

}
