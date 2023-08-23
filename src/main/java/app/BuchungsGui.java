package app;

import javax.swing.*;
import java.awt.*;

public class BuchungsGui {
    public BuchungsGui(MainGui mainGui) {
        createAndShowGUI();
    }

    JFrame frame = new JFrame("Campingplatz Buchung");
    private void createAndShowGUI() {

        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(1000, 400);

        JPanel leftPanel = createLeftPanel();
        JPanel rightPanel = createRightPanel();
        JPanel mainPanel = createMainPanel(leftPanel, rightPanel);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel(new GridLayout(7, 2));

        leftPanel.add(new JLabel("Buchungszeitraum:"));
        leftPanel.add(new JTextField());

        leftPanel.add(new JLabel("Anzahl der Personen:"));
        leftPanel.add(new JTextField());

        leftPanel.add(new JLabel("Unterkunftstyp:"));
        String[] unterkunftstypen = {"Wohnwagen", "Wohnmobil", "Zelt"};
        JComboBox<String> unterkunftComboBox = new JComboBox<>(unterkunftstypen);
        leftPanel.add(unterkunftComboBox);

        leftPanel.add(new JLabel("Platzauswahl:"));
        leftPanel.add(new JTextField());

        leftPanel.add(new JLabel("Kosten:"));
        leftPanel.add(new JTextField());

        return leftPanel;
    }

    private static JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new GridLayout(9, 2));

        rightPanel.add(new JLabel("Name:"));
        rightPanel.add(new JTextField());

        rightPanel.add(new JLabel("Vorname:"));
        rightPanel.add(new JTextField());

        rightPanel.add(new JLabel("Straße:"));
        rightPanel.add(new JTextField());

        rightPanel.add(new JLabel("PLZ:"));
        rightPanel.add(new JTextField());

        rightPanel.add(new JLabel("Hausnummer:"));
        rightPanel.add(new JTextField());

        rightPanel.add(new JLabel("Rechnungsadresse:"));
        rightPanel.add(new JTextField());

        rightPanel.add(new JLabel("Telefon:"));
        rightPanel.add(new JTextField());

        rightPanel.add(new JLabel("Email:"));
        rightPanel.add(new JTextField());

        rightPanel.add(new JLabel("Kreditkartendaten:"));
        rightPanel.add(new JTextField());

        return rightPanel;
    }

    private static JPanel createMainPanel(JPanel leftPanel, JPanel rightPanel) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        // Schaltfläche zur Bestätigung der Buchung
        JButton buchungsButton = new JButton("Buchung bestätigen");
        mainPanel.add(buchungsButton, BorderLayout.SOUTH);

        return mainPanel;
    }
}
