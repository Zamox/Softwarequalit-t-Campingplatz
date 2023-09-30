package app;
import app.StellplaetzeInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlatzUebersichtGui {
    private JFrame frame;
    private JPanel platzPanel;

    public PlatzUebersichtGui() {
        frame = new JFrame("Platzübersicht");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Panel für die Platzübersicht
        platzPanel = new JPanel(new GridLayout(0, 5, 10, 10)); // Anzahl der Spalten kann angepasst werden
        platzPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Lade Platzinformationen aus CSV und erstelle die Buttons
        List<StellplaetzeInfo> plaetze = ladePlatzInformationenAusCSV("./Platzdaten.csv");
        for (StellplaetzeInfo platz : plaetze) {
            JButton platzButton = new JButton(platz.getPlatznummer());
            if (platz.getStatus().equals("frei")) {
                platzButton.setBackground(Color.GREEN);
            } else if (platz.getStatus().equals("belegt")) {
                platzButton.setBackground(Color.RED);
            }

            platzButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (platz.getStatus().equals("belegt")) {
                        JOptionPane.showMessageDialog(frame, "Dieser Platz ist momentan gebucht und kann nicht bearbeitet werden.", "Fehler", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Öffne die PlatzBearbeitenGUI und übergebe die Platzdaten
                        PlatzBearbeitenGUI PlatzBearbeitenGUI = new PlatzBearbeitenGUI(platz, false, platz.getPlatzregion(), platz.getPlatzart());
                    }
                }
            });

            platzPanel.add(platzButton);
        }

        // Panel für die Anzeige der Regionen
        JPanel regionenPanel = new JPanel();
        regionenPanel.setLayout(new BoxLayout(regionenPanel, BoxLayout.Y_AXIS));
        regionenPanel.add(new JLabel("Für die genaue Lage des Platzes bitte die Regionen in der Karte anklicken und Platz ausfindig machen:"));
        regionenPanel.add(new JLabel("34-41: Platzregion Nord-West"));
        regionenPanel.add(new JLabel("56 bis max. 64: Platzregion Nord-Ost"));
        regionenPanel.add(new JLabel("61-93 und weitere: Platzregion Süd-Ost"));
        regionenPanel.add(new JLabel("14-31 & 64-76: Platzregion Süd-West"));

        // Hauptpanel für die Anordnung der Platz- und Regionenpanele
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(platzPanel, BorderLayout.CENTER);
        mainPanel.add(regionenPanel, BorderLayout.SOUTH);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    private List<StellplaetzeInfo> ladePlatzInformationenAusCSV(String csvFilePath) {
        List<StellplaetzeInfo> plaetze = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            boolean ersteZeileUeberspringen = true;
            while ((line = reader.readLine()) != null) {
                if (ersteZeileUeberspringen) {
                    ersteZeileUeberspringen = false;
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String platznummer = parts[0].trim();
                    String status = parts[1].trim();
                    String platzregion = parts[2].trim();
                    String platzart = parts[3].trim();
                    // Hier können Sie weitere Informationen wie Platzart, Wohnoption, etc. auslesen, falls benötigt
                    plaetze.add(new StellplaetzeInfo(platznummer, status, platzregion, platzart));
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Hier sollte eine ordnungsgemäße Fehlerbehandlung implementiert werden
        }

        return plaetze;
    }
}