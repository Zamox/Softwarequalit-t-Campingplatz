package app;

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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

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
                        // Öffne die PlatzBearbeitenGUI
                        new PlatzBearbeitenGUI(platz);
                    }
                }
            });

            platzPanel.add(platzButton);
        }

        frame.add(platzPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    private List<StellplaetzeInfo> ladePlatzInformationenAusCSV(String csvFilePath) {
        List<StellplaetzeInfo> plaetze = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String platznummer = parts[0].trim();
                    String status = parts[1].trim();
                    // Hier können Sie weitere Informationen wie Platzart, Wohnoption, etc. auslesen, falls benötigt
                    plaetze.add(new StellplaetzeInfo(platznummer, status));
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Hier sollte eine ordnungsgemäße Fehlerbehandlung implementiert werden
        }

        return plaetze;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PlatzUebersichtGui();
            }
        });
    }
}

// Annahme: StellplaetzeInfo Klasse mit Platznummer und Status
class StellplaetzeInfo {
    private String platznummer;
    private String status;

    public StellplaetzeInfo(String platznummer, String status) {
        this.platznummer = platznummer;
        this.status = status;
    }

    public String getPlatznummer() {
        return platznummer;
    }

    public String getStatus() {
        return status;
    }
}

// Annahme: PlatzBearbeitenGUI Klasse für die Bearbeitung eines Platzes
class PlatzBearbeitenGUI {
    public PlatzBearbeitenGUI(StellplaetzeInfo platz) {
        // Hier implementieren Sie die PlatzBearbeitenGUI
        System.out.println("Platz bearbeiten: " + platz.getPlatznummer());
    }
}

