package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class upperRightPlaetze {

    private JFrame frame;

    private JFrame parentframe;
    private BuchungBearbeitenGui BuchungBearbeitenGui;
    private BuchungErstellenGui BuchungErstellenGui;
    private String fall;

    public upperRightPlaetze(String fall) {
        this.frame = new JFrame("Platzregion Nord-Ost");
        this.frame.setSize(1000, 1000);
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setLayout(new GridLayout(1, 7));
        this.fall = fall;
        renderFrame();
    }

    public upperRightPlaetze(BuchungBearbeitenGui BuchungBearbeitenGui, JFrame parentframe, String fall) {
        this.frame = new JFrame("Platzregion Nord-Ost");
        this.frame.setSize(1000, 1000);
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setLayout(new GridLayout(1, 7));
        this.BuchungBearbeitenGui = BuchungBearbeitenGui;
        this.parentframe = parentframe;
        this.fall = fall;
        renderFrame();
    }

    public upperRightPlaetze(BuchungErstellenGui BuchungErstellenGui, JFrame parentframe, String fall) {
        this.frame = new JFrame("Platzregion Nord-Ost");
        this.frame.setSize(1000, 1000);
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setLayout(new GridLayout(1, 7));
        this.BuchungErstellenGui = BuchungErstellenGui;
        this.parentframe = parentframe;
        this.fall = fall;
        renderFrame();
    }

    private void renderFrame() {
        JPanel mainPanel = new JPanel(new GridLayout(1, 7)); // 7 Spalten insgesamt

        // Leerzeile für Spalte 2
        mainPanel.add(new JPanel());

        JPanel column1Panel = createButtonPanel(5, 56);
        mainPanel.add(column1Panel);

        // Leerzeile für Spalte 5
        mainPanel.add(new JPanel());


        // Leerzeile für Spalte 5
        mainPanel.add(new JPanel());

        this.frame.setContentPane(mainPanel);
        this.frame.pack();
        this.frame.setVisible(true);

        // Add click listener to buttons
        addClickListenerToButtons(mainPanel);
    }

    private JPanel createButtonPanel(int buttonCount, int startNumber) {
        JPanel panel = new JPanel(new GridLayout(buttonCount, 1));

        for (int i = 0; i < buttonCount; i++) {
            JButton button = new JButton("Platz " + (startNumber + i));
            panel.add(button);
        }

        return panel;
    }

    private void addClickListenerToButtons(Container container) {
        if (container instanceof JButton) {
            JButton button = (JButton) container;
            button.addActionListener(e -> {
                // Handle button click event if needed
            });

        } else if (container instanceof Container) {
            Container subContainer = (Container) container;
            Component[] components = subContainer.getComponents();
            for (Component component : components) {
                addClickListenerToButtons((Container) component);
            }
        }
    }
}