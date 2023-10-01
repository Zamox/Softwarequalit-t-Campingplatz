package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlatzBearbeitenGUI {
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel styleLeftPanel;
    private JPanel styleRightPanel;

    private JLabel platznummerLabel;
    private JLabel statusLabel;
    private JLabel platzregionLabel;

    private JComboBox<String> platzartComboBox;
    private JComboBox<String> wohnoptionComboBox;

    private StellplaetzeInfo platz;
    private boolean isEditable;
    private String selectedPlatzart;
    private String selectedWohnoption;

    public PlatzBearbeitenGUI(StellplaetzeInfo platz, boolean isEditable, String selectedPlatzart, String selectedWohnoption) {
        this.platz = platz;
        this.isEditable = isEditable;
        this.selectedPlatzart = selectedPlatzart;
        this.selectedWohnoption = selectedWohnoption;

        frame = new JFrame("Platz bearbeiten");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(600, 600));

        mainPanel = new JPanel(new BorderLayout());
        styleLeftPanel = new JPanel(new GridLayout(0, 2, 10, 20));
        styleLeftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        styleRightPanel = new JPanel();
        styleRightPanel.setLayout(new BorderLayout());

        // Platznummer
        platznummerLabel = new JLabel("Platznummer:");
        styleLeftPanel.add(platznummerLabel);
        styleLeftPanel.add(new JLabel(platz.getPlatznummer()));

        // Status
        statusLabel = new JLabel("Status:");
        styleLeftPanel.add(statusLabel);
        styleLeftPanel.add(new JLabel(platz.getStatus()));

        // Platzregion
        platzregionLabel = new JLabel("Platzregion:");
        styleLeftPanel.add(platzregionLabel);
        styleLeftPanel.add(new JLabel(platz.getPlatzregion()));

        // Platzart
        JPanel platzartPanel = new JPanel();
        platzartPanel.setLayout(new GridLayout(0, 1));
        platzartPanel.add(new JLabel("Platzart:"));
        String[] platzarten = {"", "Stellplatz", "Shop", "Sanitäre Anlagen", "Sonstige"};
        platzartComboBox = new JComboBox<>(platzarten);
        platzartComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Wenn "Stellplatz" ausgewählt ist, aktiviere die Wohnoption-ComboBox, ansonsten deaktiviere sie.
                if (platzartComboBox.getSelectedItem().equals("Stellplatz")) {
                    wohnoptionComboBox.setEnabled(true);
                } else {
                    wohnoptionComboBox.setEnabled(false);
                    wohnoptionComboBox.setSelectedItem(""); // Wohnoption zurücksetzen, wenn nicht Stellplatz ausgewählt ist.
                }
            }
        });
        platzartPanel.add(platzartComboBox);
        styleLeftPanel.add(platzartPanel);

        // Wohnoption
        JPanel wohnoptionPanel = new JPanel();
        wohnoptionPanel.setLayout(new GridLayout(0, 1));
        wohnoptionPanel.add(new JLabel("Wohnoption:"));
        String[] wohnoptionen = {"", "Wohnmobil", "Wohnwagen", "Zelt"};
        wohnoptionComboBox = new JComboBox<>(wohnoptionen);
        wohnoptionPanel.add(wohnoptionComboBox);
        styleLeftPanel.add(wohnoptionPanel);

        // Setze die ausgewählten Optionen
        if (selectedPlatzart != null && !selectedPlatzart.isEmpty()) {
            platzartComboBox.setSelectedItem(selectedPlatzart);
            if (selectedPlatzart.equals("Stellplatz")) {
                wohnoptionComboBox.setEnabled(true);
            }
        }

        if (selectedWohnoption != null && !selectedWohnoption.isEmpty()) {
            wohnoptionComboBox.setSelectedItem(selectedWohnoption);
        }

        JButton speichernButton = new JButton("Speichern");
        speichernButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String neuePlatznummer = platz.getPlatznummer();
                String neuePlatzart = platzartComboBox.getSelectedItem().toString();

                // Prüfe, ob die ausgewählte Platzart "Stellplatz" ist, bevor die Wohnoption übernommen wird.
                String neueWohnoption = "";
                if (neuePlatzart.equals("Stellplatz")) {
                    neueWohnoption = wohnoptionComboBox.getSelectedItem().toString();
                }

                if (!neuePlatzart.isEmpty()) {
                    if (updatePlatzInCSV(platz.getPlatznummer(), neuePlatzart, neueWohnoption)) {
                        JOptionPane.showMessageDialog(frame, "Daten erfolgreich gespeichert.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Fehler beim Speichern der Daten.", "Fehler", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Bitte wählen Sie eine Platzart aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(speichernButton);
        styleRightPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(styleLeftPanel, BorderLayout.WEST);
        mainPanel.add(styleRightPanel, BorderLayout.EAST);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    private boolean updatePlatzInCSV(String platznummer, String neuePlatzart, String neueWohnoption) {
        String csvFilePath = "./Platzdaten.csv";
        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            boolean firstLine = true; // Um die erste Zeile (Überschriften) zu ignorieren
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    updatedLines.add(line);
                    firstLine = false;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String currentPlatznummer = parts[0].trim();
                    String currentStatus = parts[1].trim();
                    String currentPlatzregion = parts[2].trim();
                    String currentPlatzart = parts[3].trim();
                    String currentWohnoption = parts[4].trim();

                    if (currentPlatznummer.equals(platznummer)) {
                        line = platznummer + "," + currentStatus + "," + currentPlatzregion + "," + neuePlatzart + "," + neueWohnoption;
                    }

                    updatedLines.add(line);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }
}
