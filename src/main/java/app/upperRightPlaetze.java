package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class upperRightPlaetze {

    private JFrame frame;
    private JPanel column3Panel;

    private int nordOstPlatzCounter = 0;
    private final int MAX_NORD_OST_PLAETZE = 4;

    public upperRightPlaetze() {
        this.frame = new JFrame("Platzregion Nord-Ost");
        this.frame.setSize(1000, 1000);
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setLayout(new FlowLayout(FlowLayout.CENTER));
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

        // Spalte 3 mit 4 Buttons (individuelle Nummern von 38 bis 41)
        column3Panel = new JPanel(new GridLayout(4, 1));
        mainPanel.add(column3Panel);

        // Leerzeile für Spalte 5
        mainPanel.add(new JPanel());

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new upperRightPlaetze());
    }

    public void addPlatz() {
        if (nordOstPlatzCounter < MAX_NORD_OST_PLAETZE) {
            JButton button = new JButton("Platz " + (61 + nordOstPlatzCounter));
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle button click event if needed
                }
            });
            column3Panel.add(button);
            nordOstPlatzCounter++;
            frame.revalidate();
            frame.repaint();
        } else {
            JOptionPane.showMessageDialog(frame, "Es können nicht mehr Plätze hinzugefügt werden.", "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }
}