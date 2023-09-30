package app;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FreiePlaetzeGui extends JFrame {

    public FreiePlaetzeGui() {
        setTitle("Freie Plätze");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        List<String> platzdaten = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("./Platzdaten.csv"))) {
            String line;
            boolean skipFirstLine = true; // Um die erste Zeile zu überspringen
            while ((line = reader.readLine()) != null) {
                if (skipFirstLine) {
                    skipFirstLine = false;
                    continue; // Überspringe die erste Zeile
                }

                // Überprüfe, ob die Zeile das erwartete Format hat
                String[] splitInfo = line.split(",");
                if (splitInfo.length >= 3) { // Stellen Sie sicher, dass mindestens 3 Elemente vorhanden sind
                    String platznummer = splitInfo[0];
                    String status = splitInfo[1];
                    String platzregion = splitInfo[2];

                    if (status.equals("frei")) {
                        String labelText = "Platznummer: " + platznummer + ", Region: " + platzregion;
                        platzdaten.add(labelText);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (String label : platzdaten) {
            JLabel labelComponent = new JLabel(label);
            panel.add(labelComponent);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        getContentPane().add(scrollPane);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FreiePlaetzeGui();
        });
    }
}
