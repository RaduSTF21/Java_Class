package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ControlPanel extends JPanel {
    final MainFrame frame;
    JButton exitButton = new JButton("Exit");
    JButton loadButton = new JButton("Load Game");
    JButton saveButton = new JButton("Save Game");

    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }
    private void init() {
        setLayout(new GridLayout(1,3));
        saveButton.setSize(200,100);
        exitButton.setSize(200,100);
        loadButton.setSize(200,100);
        add(exitButton);
        add(loadButton);
        add(saveButton);
        exitButton.addActionListener(this::exitGame);
    }
    private void exitGame(ActionEvent e) {
        frame.dispose();
    }

}
