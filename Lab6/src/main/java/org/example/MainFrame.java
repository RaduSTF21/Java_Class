package org.example;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    ConfigPanel configPanel;
    ControlPanel controlPanel;
    DrawingPanel canvas;

    public MainFrame() {
        super("Drawing app");
        init();
    }
    private void init() {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(1920, 1920 );
            setLayout(new BorderLayout()); // Three rows, one column

            controlPanel = new ControlPanel(this);
            configPanel = new ConfigPanel(this);
            canvas = new DrawingPanel(this,configPanel);
            add(configPanel, BorderLayout.NORTH);
            add(canvas, BorderLayout.CENTER);
            add(controlPanel, BorderLayout.SOUTH);
            pack();
        }
    public void newGame() {
        // Retrieve the desired number of dots from the config panel
        int numDots = configPanel.getVal();
        // Regenerate the dots on the drawing panel
        canvas.generateRandDots(numDots);
        // (Optional) Reset any additional game state such as player turns or scores here.
    }



}

