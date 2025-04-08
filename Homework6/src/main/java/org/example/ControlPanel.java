package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ControlPanel extends JPanel {
    final MainFrame frame;
    JButton exitButton = new JButton("Exit");
    JButton loadButton = new JButton("Load Game");
    JButton saveButton = new JButton("Save Game");
    JButton exportButton = new JButton("Export");
    JButton compareButton = new JButton("Compare Score");

    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        // Adjust layout to accommodate 5 buttons.
        setLayout(new GridLayout(1, 5));
        exitButton.setPreferredSize(new Dimension(120, 50));
        loadButton.setPreferredSize(new Dimension(120, 50));
        saveButton.setPreferredSize(new Dimension(120, 50));
        exportButton.setPreferredSize(new Dimension(120, 50));
        compareButton.setPreferredSize(new Dimension(120, 50));

        add(exitButton);
        add(loadButton);
        add(saveButton);
        add(exportButton);
        add(compareButton);

        exitButton.addActionListener(this::exitGame);

        loadButton.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            if(fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                frame.getDrawingPanel().loadGame(filePath);
            }
        });

        saveButton.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            if(fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                frame.getDrawingPanel().saveGame(filePath);
            }
        });

        exportButton.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            if(fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                BufferedImage image = frame.getDrawingPanel().getImage();
                try {
                    ImageIO.write(image, "png", new File(filePath));
                    JOptionPane.showMessageDialog(frame, "Export successful: " + filePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        compareButton.addActionListener((ActionEvent e) -> {
            double mstCost = frame.getDrawingPanel().computeMSTCost();
            double playerOneScore = frame.getDrawingPanel().getPlayerOneScore();
            double playerTwoScore = frame.getDrawingPanel().getPlayerTwoScore();
            String message = "Minimum Spanning Tree (Best) Score: " + mstCost + "\n" +
                    "Player 1 (Blue) Score: " + playerOneScore + "\n" +
                    "Player 2 (Red) Score: " + playerTwoScore;
            JOptionPane.showMessageDialog(frame, message);
        });
    }

    private void exitGame(ActionEvent e) {
        frame.dispose();
    }
}
