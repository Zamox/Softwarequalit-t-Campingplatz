package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class upperRightPlaetze {


    private JFrame frame;

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


        JPanel column1Panel = createButtonPanel(4, 56);
        mainPanel.add(column1Panel);

        // Leerzeile für Spalte 5
        mainPanel.add(new JPanel());

        // Spalte 3 mit 4 Buttons (individuelle Nummern von 38 bis 41)
        JPanel column3Panel = new JPanel(new GridLayout(4, 1));
        JButton emptyButton1 = new JButton("");
        column3Panel.add(emptyButton1);
        JButton emptyButton2 = new JButton("");
        JButton emptyButton3 = new JButton("");
        JButton emptyButton4 = new JButton("");
        column3Panel.add(emptyButton2);
        column3Panel.add(emptyButton3);
        column3Panel.add(emptyButton4);
        emptyButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Dieser Platz muss zunächst angelegt werden und kann noch nicht gebucht werden.");
            }
        });

        emptyButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Dieser Platz muss zunächst angelegt werden und kann noch nicht gebucht werden.");
            }
        });

        emptyButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Dieser Platz muss zunächst angelegt werden und kann noch nicht gebucht werden.");
            }
        });

        emptyButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Dieser Platz muss zunächst angelegt werden und kann noch nicht gebucht werden.");
            }
        });

        mainPanel.add(column3Panel);




        // Spalte 4 mit 4 Buttons (individuelle Nummern von 42 bis 45)


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

}



