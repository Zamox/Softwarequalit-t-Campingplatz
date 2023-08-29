package app;

import javax.swing.*;
import java.awt.*;

public class upperLeftPlaetze {
    private JFrame frame;

    public upperLeftPlaetze() {
        this.frame = new JFrame("Platzregion Nord-West");
        this.frame.setSize(1000, 1000);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        renderFrame();
    }

    private void renderFrame() {
        JPanel mainPanel = new JPanel(new GridLayout(1, 7)); // 7 Spalten insgesamt

        // Leerzeile für Spalte 2
        mainPanel.add(new JPanel());

        // Erste Spalte mit 4 Buttons (individuelle Nummern von 34 bis 37)
        JPanel column1Panel = createButtonPanel(4, 34);
        mainPanel.add(column1Panel);

        // Leerzeile für Spalte 5
        mainPanel.add(new JPanel());

        // Spalte 3 mit 4 Buttons (individuelle Nummern von 38 bis 41)
        JPanel column3Panel = createButtonPanel(4, 38);
        mainPanel.add(column3Panel);

        // Spalte 4 mit 4 Buttons (individuelle Nummern von 42 bis 45)
        JPanel column4Panel = createButtonPanel(4, 42);
        mainPanel.add(column4Panel);

        // Leerzeile für Spalte 5
        mainPanel.add(new JPanel());

        // Spalte 6 mit 4 Buttons (individuelle Nummern von 46 bis 49)
        JPanel column6Panel = createButtonPanel(4, 46);
        mainPanel.add(column6Panel);

        // Spalte 7 mit 4 Buttons (individuelle Nummern von 50 bis 53)
        JPanel column7Panel = createButtonPanel(4, 50);
        mainPanel.add(column7Panel);

        this.frame.setContentPane(mainPanel);
        this.frame.pack();
        this.frame.setVisible(true);
    }

    private JPanel createButtonPanel(int buttonCount, int startNumber) {
        JPanel panel = new JPanel(new GridLayout(buttonCount, 1));

        for (int i = 0; i < buttonCount; i++) {
            JButton button = new JButton("Platz " + (startNumber + i));
            panel.add(button);
        }

        return panel;
    }


}
