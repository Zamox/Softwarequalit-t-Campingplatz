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

    private JRadioButton stellplatzRadio;
    private JRadioButton shopRadio;
    private JRadioButton sanitaereAnlagenRadio;
    private JRadioButton sonstigeRadio;
    private ButtonGroup platzartGroup;

    private JRadioButton wohnmobilRadio;
    private JRadioButton wohnwagenRadio;
    private JRadioButton zeltRadio;
    private ButtonGroup wohnoptionGroup;

    private StellplaetzeInfo platz;
    private boolean isEditable;

    public PlatzBearbeitenGUI(StellplaetzeInfo platz, boolean isEditable, String selectedPlatzart) {
        this.platz = platz;
        this.isEditable = isEditable;

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

        JPanel platzartPanel = new JPanel();
        platzartPanel.setLayout(new GridLayout(0, 1));
        platzartPanel.add(new JLabel("Platzart:"));
        stellplatzRadio = new JRadioButton("Stellplatz");
        shopRadio = new JRadioButton("Shop");
        sanitaereAnlagenRadio = new JRadioButton("Sanitäre Anlagen");
        sonstigeRadio = new JRadioButton("Sonstige");

        platzartGroup = new ButtonGroup();
        platzartGroup.add(stellplatzRadio);
        platzartGroup.add(shopRadio);
        platzartGroup.add(sanitaereAnlagenRadio);
        platzartGroup.add(sonstigeRadio);

        platzartPanel.add(stellplatzRadio);
        platzartPanel.add(shopRadio);
        platzartPanel.add(sanitaereAnlagenRadio);
        platzartPanel.add(sonstigeRadio);
        styleLeftPanel.add(platzartPanel);

        if (!selectedPlatzart.isEmpty()) {
            if (selectedPlatzart.equals("Stellplatz")) {
                stellplatzRadio.setSelected(true);
            } else if (selectedPlatzart.equals("Shop")) {
                shopRadio.setSelected(true);
            } else if (selectedPlatzart.equals("Sanitäre Anlagen")) {
                sanitaereAnlagenRadio.setSelected(true);
            } else if (selectedPlatzart.equals("Sonstige")) {
                sonstigeRadio.setSelected(true);
            }
        }

        JButton speichernButton = new JButton("Speichern");
        speichernButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String neuePlatznummer = platz.getPlatznummer();
                String neuePlatzart = getSelectedPlatzart();

                if (!neuePlatzart.isEmpty()) {
                    if (updatePlatzInCSV(platz.getPlatznummer(), neuePlatzart)) {
                        JOptionPane.showMessageDialog(frame, "Daten erfolgreich gespeichert.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Fehler beim Speichern der Daten.", "Fehler", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Bitte wählen Sie eine Platzart aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
                }

                frame.dispose();
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

    private String getSelectedPlatzart() {
        if (stellplatzRadio.isSelected()) {
            return "Stellplatz";
        } else if (shopRadio.isSelected()) {
            return "Shop";
        } else if (sanitaereAnlagenRadio.isSelected()) {
            return "Sanitäre Anlagen";
        } else if (sonstigeRadio.isSelected()) {
            return "Sonstige";
        }
        return "";
    }

    private boolean updatePlatzInCSV(String platznummer, String neuePlatzart) {
        String csvFilePath = "./Platzdaten.csv";
        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String currentPlatznummer = parts[0].trim();
                    String currentStatus = parts[1].trim();
                    String currentPlatzregion = parts[2].trim();
                    String currentPlatzart = parts[3].trim(); // Hier aktualisieren wir die Platzart

                    if (currentPlatznummer.equals(platznummer)) {
                        // Hier überschreiben wir die Platzart
                        currentPlatzart = neuePlatzart;
                        line = platznummer + "," + currentStatus + "," + currentPlatzregion + "," + currentPlatzart;
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
