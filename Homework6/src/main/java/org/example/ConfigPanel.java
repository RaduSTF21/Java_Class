package org.example;

import javax.swing.*;

public class ConfigPanel extends JPanel {
    final MainFrame frame;
    JLabel label;
    JSpinner spinner;
    JButton newButton;

    public ConfigPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        label = new JLabel("Number of dots:");
        spinner = new JSpinner(new SpinnerNumberModel(10, 2, 100, 1));
        newButton = new JButton("New Game");
        newButton.addActionListener(e -> frame.newGame());

        add(label);
        add(spinner);
        add(newButton);
    }

    public int getVal() {
        return Integer.parseInt(spinner.getValue().toString());
    }
}
