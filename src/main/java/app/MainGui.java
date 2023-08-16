package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGui {
    private JFrame frame;

    public MainGui() {
        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());

        renderFrame();
    }

    private void renderFrame() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout());

        ImageIcon imageIcon = new ImageIcon("./src/main/java/app/Platzplan.png");
        JLabel imageLabel = new JLabel(imageIcon);
        contentPanel.add(imageLabel, BorderLayout.WEST);

        JPanel buchungsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        String[] buchungsButtonLabels = {
                "Neue Buchung", "Buchung bearbeiten",
                "Buchung löschen", "Info"
        };

        for (String label : buchungsButtonLabels) {
            JButton button = createIdentifiedButton(label); // Button mit Identifier erstellen
            button.setPreferredSize(new Dimension(140, 40)); // Button-Größe anpassen
            buchungsPanel.add(button);
        }
        contentPanel.add(buchungsPanel, BorderLayout.SOUTH);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        this.frame.add(mainPanel);
        this.frame.setSize(1800, 1000);
        this.frame.setVisible(true);
    }

    private JButton createIdentifiedButton(String label) {
        JButton button = new JButton(label);
        button.setPreferredSize(new Dimension(140, 40)); // Button-Größe anpassen
        button.setActionCommand(label); // Verwende das Label als Identifier
        button.addActionListener(buttonListener); // Füge den ActionListener hinzu
        return button;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        String[] buttonLabels = {
                "Buchungen", "Freie Plätze", "Neuer Platz",
                "Platz bearbeiten", "Platz löschen", "Platz Buchen",
                "Export/Import", "Login"
        };

        for (String label : buttonLabels) {
            JButton button = createIdentifiedButton(label); // Button mit Identifier erstellen
            button.setPreferredSize(new Dimension(120, 40)); // Button-Größe anpassen
            buttonPanel.add(button);
        }

        return buttonPanel;
    }

    private ActionListener buttonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String identifier = e.getActionCommand(); // Hole den Identifier des geklickten Buttons

            // Hier kannst du basierend auf dem Identifier die entsprechende Aktion ausführen
            switch (identifier) {
                case "Neue Buchung":
                    // Funktion für "Neue Buchung" ausführen
                    break;
                case "Buchung bearbeiten":
                    // Funktion für "Buchung bearbeiten" ausführen
                    break;
                // Füge hier weitere Cases hinzu für die anderen Buttons
            }
        }
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGui());
    }
}
