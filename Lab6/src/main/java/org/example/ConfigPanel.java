package org.example;

import javax.swing.*;

public class ConfigPanel extends JPanel {
    final MainFrame frame;
    JLabel label;
    JSpinner spinner;
    JButton newbutton;
    public ConfigPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }
    private void init() {
        label = new JLabel("Number of dots:");
        spinner = new JSpinner(new SpinnerNumberModel(10, 2, 100, 1));
        newbutton = new JButton("New Game");
        newbutton.addActionListener(e -> {frame.newGame();});

        add(label);
        add(spinner);
        add(newbutton);
        frame.pack();
    }
    public int getVal(){
        return Integer.parseInt(spinner.getValue().toString());
    }
}
