package org.example;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    ConfigPanel configPanel;
    ControlPanel controlPanel;
    DrawingPanel drawingPanel;

    public MainFrame() {
        super("Drawing App");
        init();
    }

    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        configPanel = new ConfigPanel(this);
        drawingPanel = new DrawingPanel(this, configPanel);
        controlPanel = new ControlPanel(this);

        add(configPanel, BorderLayout.NORTH);
        add(drawingPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    // When starting a new game, regenerate dots and reset scores.
    public void newGame() {
        int numDots = configPanel.getVal();
        drawingPanel.generateRandDots(numDots);
        drawingPanel.resetGameScores();
    }

    public DrawingPanel getDrawingPanel() {
        return drawingPanel;
    }
}
