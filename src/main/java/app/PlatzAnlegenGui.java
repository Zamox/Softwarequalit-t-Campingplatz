package app;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class PlatzAnlegenGui {
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel styleLeftPanel;
    private JPanel styleRightPanel;

    private JTextField platznummerField;
    private String selectedPlatzart;
    private String selectedWohnoption;
    private JComboBox<String> platzartComboBox;
    private String status;
    private JComboBox<String> wohnoptionComboBox;

    public PlatzAnlegenGui() {
        frame = new JFrame("Platz anlegen");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(600, 600));

        mainPanel = new JPanel(new BorderLayout());
        styleLeftPanel = new JPanel(new GridLayout(0, 2, 10, 20)); // Erhöhter Abstand zwischen den Komponenten
        styleLeftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        styleRightPanel = new JPanel(); // Rechtes Panel für zusätzliche Inhalte
        styleRightPanel.setLayout(new BorderLayout());

        // Platznummer
        styleLeftPanel.add(new JLabel("Platznummer:"));
        platznummerField = new JTextField();
        styleLeftPanel.add(platznummerField);



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






        // Speichern-Button
        JButton speichernButton = new JButton("Speichern");
        speichernButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Platznummer
                String platznummer = platznummerField.getText().trim();
                String platzart = platzartComboBox.getSelectedItem().toString();


                // Prüfe, ob die ausgewählte Platzart "Stellplatz" ist, bevor die Wohnoption übernommen wird.
                String wohnoption = "";
                if (platzart.equals("Stellplatz")) {
                    wohnoption = wohnoptionComboBox.getSelectedItem().toString();
                }else {
                    wohnoption = "keine";
                }



                // Überprüfung, ob alle Felder ausgefüllt sind
                if (platznummer.isEmpty() || platzart.isEmpty() || wohnoption.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Bitte füllen Sie alle Felder aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
                    return; // Beende die Methode, da nicht alle Felder ausgefüllt sind
                }

                // Überprüfung, ob die Platznummer bereits existiert
                if (platznummerExistiertBereits(platznummer, "./Platzdaten.csv")) {

                    JOptionPane.showMessageDialog(frame, "Die Platznummer existiert bereits. Es können nur neue Plätze ab Nummer 94 vergeben werden", "Fehler", JOptionPane.ERROR_MESSAGE);
                    return; // Beende die Methode, da die Platznummer bereits existiert

                }
                if (Integer.parseInt(platznummer) <= 14 && Integer.parseInt(platznummer) >= 1) {
                    JOptionPane.showMessageDialog(frame, "Die Platznummer existiert bereits oder ist zu klein. Es können nur neue Plätze ab Nummer 94 vergeben werden", "Fehler", JOptionPane.ERROR_MESSAGE);
                    return; // Beende die Methode, da die Platznummer bereits existiert
                }

                if (!platzart.equals("Stellplatz")){
                    status = "belegt";
                    wohnoption = "keine";
                }else {
                    status = "frei";
                }

                String csvData = "\n"+platznummer + ","+status+"," + platzart + "," + wohnoption;
                String csvFilePath = "./Platzdaten.csv";

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath, true))) {
                    // true gibt an, dass die Datei im Append-Modus geöffnet wird, um Daten am Ende anzufügen
                    writer.write(csvData);
                    writer.newLine();
                } catch (IOException ex) {
                    ex.printStackTrace(); // Hier sollte eine ordnungsgemäße Fehlerbehandlung implementiert werden
                }

                // Erfolgsmeldung
                JOptionPane.showMessageDialog(frame, "Daten erfolgreich gespeichert.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose(); // Schließen Sie das Fenster
            }
        });
        // Beenden-Button
        JButton beendenButton = new JButton("Beenden");
        beendenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Schließen Sie das Fenster
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(speichernButton);
        buttonPanel.add(beendenButton);

        styleRightPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(styleLeftPanel, BorderLayout.WEST);
        mainPanel.add(styleRightPanel, BorderLayout.EAST);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    private boolean platznummerExistiertBereits(String platznummer, String csvFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Annahme: Platznummer ist in der ersten Spalte der CSV-Datei
                String[] parts = line.split(",");
                if (parts.length > 0 && parts[0].equals(platznummer)) {
                    return true; // Platznummer wurde gefunden
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace(); // Hier sollte eine ordnungsgemäße Fehlerbehandlung implementiert werden
        }
        return false; // Platznummer wurde nicht gefunden
    }




}