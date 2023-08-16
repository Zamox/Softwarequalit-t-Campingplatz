package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainGui {
    private JFrame frame;
    private List<JButton> panelButtonList;
    private List<JButton> buchungsButtonList;
    public JButton loginBtn;

    public MainGui() {
        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());
        String t = "test";
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

        buchungsButtonList = new ArrayList<JButton>();

        for (String label : buchungsButtonLabels) {
            JButton button = createIdentifiedButton(label); // Button mit Identifier erstellen
            button.setEnabled(false);
            buchungsButtonList.add(button);
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
                "Export/Import"
        };

        panelButtonList = new ArrayList<JButton>();

        for (String label : buttonLabels) {
            JButton button = createIdentifiedButton(label); // Button mit Identifier erstellen
            button.setEnabled(false);
            panelButtonList.add(button);
            buttonPanel.add(button);
        }

        loginBtn = createIdentifiedButton("Login"); // Button mit Identifier erstellen
        buttonPanel.add(loginBtn);


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
                case "Login":


                    break;
            }
        }
    };

    public void enable() {
        for (JButton button : panelButtonList){
            button.setEnabled(true);
        }

        for (JButton button : buchungsButtonList){
            button.setEnabled(true);
        }
    }

    public static void main(String[] args) {
       SwingUtilities.invokeLater(() -> new MainGui());
    }
}
