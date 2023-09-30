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

    private JRadioButton stellplatzRadio;
    private JRadioButton shopRadio;
    private JRadioButton sanitaereAnlagenRadio;
    private JRadioButton sonstigeRadio;
    private ButtonGroup platzartGroup;

    private JRadioButton wohnmobilRadio;
    private JRadioButton wohnwagenRadio;
    private JRadioButton zeltRadio;
    private ButtonGroup wohnoptionGroup;
    private JTextField personenzahlField;
    private JTextField tagessatzField;
    private JComboBox platzregionBox;

    private JButton buttonToUpdate;
    private upperRightPlaetze upperRightPlaetze;

    public void setButton(JButton button) {
        this.buttonToUpdate = button;
    }


    public PlatzAnlegenGui(){



        platznummerField = new JTextField();
        frame = new JFrame("Platz anlegen");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(450, 300));

        mainPanel = new JPanel(new BorderLayout());
        styleLeftPanel = new JPanel(new GridLayout(0, 1, 10, 20)); // Erhöhter Abstand zwischen den Komponenten
        styleLeftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        styleRightPanel = new JPanel(); // Rechtes Panel für zusätzliche Inhalte
        styleRightPanel.setLayout(new BorderLayout());

        platzregionBox = new JComboBox("Nord-Ost, Süd-Ost".split(","));
        platzregionBox.setSelectedIndex(0);
        styleLeftPanel.add(platzregionBox);
        // Platzart
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

        // Wohnoption
        JPanel wohnoptionPanel = new JPanel();
        wohnoptionPanel.setLayout(new GridLayout(0, 1));
        wohnoptionPanel.add(new JLabel("Wohnoption:"));
        wohnmobilRadio = new JRadioButton("Wohnmobil");
        wohnwagenRadio = new JRadioButton("Wohnwagen");
        zeltRadio = new JRadioButton("Zelt");

        wohnoptionGroup = new ButtonGroup();
        wohnoptionGroup.add(wohnmobilRadio);
        wohnoptionGroup.add(wohnwagenRadio);
        wohnoptionGroup.add(zeltRadio);

        wohnoptionPanel.add(wohnmobilRadio);
        wohnoptionPanel.add(wohnwagenRadio);
        wohnoptionPanel.add(zeltRadio);
        styleLeftPanel.add(wohnoptionPanel);


        // Speichern-Button
        JButton speichernButton = new JButton("Speichern");
        speichernButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Platznummer
                String platznummer = platznummerField.getText().trim();

                // Platzart
                String platzart = "";
                if (stellplatzRadio.isSelected()) {
                    platzart = "Stellplatz";
                } else if (shopRadio.isSelected()) {
                    platzart = "Shop";
                } else if (sanitaereAnlagenRadio.isSelected()) {
                    platzart = "Sanitäre Anlagen";
                } else if (sonstigeRadio.isSelected()) {
                    platzart = "Sonstige";
                }

                // Wohnoption
                String wohnoption = "";
                if (wohnmobilRadio.isSelected()) {
                    wohnoption = "Wohnmobil";
                } else if (wohnwagenRadio.isSelected()) {
                    wohnoption = "Wohnwagen";
                } else if (zeltRadio.isSelected()) {
                    wohnoption = "Zelt";
                }





                // Überprüfung, ob alle Felder ausgefüllt sind
                if (platzart.isEmpty() || wohnoption.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Bitte füllen Sie alle Felder aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
                    return; // Beende die Methode, da nicht alle Felder ausgefüllt sind
                }



                upperRightPlaetze urp= new  upperRightPlaetze();
                urp.addPlatz();


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



    //32, 33, 52, 53
}
