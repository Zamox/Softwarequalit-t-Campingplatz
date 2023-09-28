package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class downLeftPlaetze {

    private JFrame frame;

    public  downLeftPlaetze() {
        this.frame = new JFrame("Platzregion Süd-West");
        this.frame.setSize(1000, 1000);
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        renderFrame();
        checkCSVAndColorButtons();

    }
    private void renderFrame() {
        JPanel mainPanel = new JPanel(new GridLayout(1, 7));

        mainPanel.add(new JPanel());

        JPanel column1Panel = createButtonPanel(6, 14);
        mainPanel.add(column1Panel);

        mainPanel.add(new JPanel());

        JPanel column2Panel = createButtonPanel(5, 20);
        mainPanel.add(column2Panel);

        mainPanel.add(new JPanel());

        JPanel column3Panel = createButtonPanel(5, 25);
        mainPanel.add(column3Panel);

        mainPanel.add(new JPanel());

        JPanel column4Panel = createButtonPanel(2, 30);
        mainPanel.add(column4Panel);

        mainPanel.add(new JPanel());

        JPanel column5Panel = createButtonPanel(6, 71);
        mainPanel.add(column5Panel);

        mainPanel.add(new JPanel());

        JPanel column6Panel = createButtonPanel(4, 64);
        mainPanel.add(column6Panel);

        mainPanel.add(new JPanel());

        JPanel column7Panel = createButtonPanel(3, 68);
        mainPanel.add(column7Panel);

        mainPanel.add(new JPanel());




        mainPanel.add(new JPanel());

        this.frame.setContentPane(mainPanel);
        this.frame.pack();
        this.frame.setVisible(true);
    }
    private JPanel createButtonPanel(int buttonCount, int startNumber) {
        JPanel panel = new JPanel(new GridLayout(buttonCount, 1));

        for (int i = 0; i < buttonCount; i++) {
            JButton button = new JButton("Platz " + (startNumber + i));
            panel.add(button);
        }

        return panel;
    }
    private void checkCSVAndColorButtons() {
        // Definieren Sie den Dateipfad Ihrer CSV-Datei
        String csvFilePath = "./BuchungsCSV.csv"; // Ändern Sie dies entsprechend

        // Holen Sie den Hauptpanel
        Container mainPanel = this.frame.getContentPane();

        // Erstellen Sie eine Menge (Set) für die in der CSV-Datei gefundenen Nummern
        Set<String> foundNumbers = new HashSet<>();

        // Durchlaufen Sie alle Komponenten im Hauptpanel (einschließlich verschachtelter Panels)
        findButtonsAndCheckCSV(mainPanel, foundNumbers, csvFilePath);

        // Durchlaufen Sie erneut alle Komponenten und färben Sie die Buttons entsprechend
        colorButtons(mainPanel, foundNumbers);
    }
    private void findButtonsAndCheckCSV(Component component, Set<String> foundNumbers, String csvFilePath) {
        if (component instanceof JButton) {
            JButton button = (JButton) component;
            String buttonText = button.getText().replace("Platz ", "");

            if (checkCSVForNumber(buttonText, csvFilePath)) {
                foundNumbers.add(buttonText);
            }
        } else if (component instanceof Container) {
            // Wenn es sich um ein Container-Objekt handelt, durchlaufen Sie seine Komponenten
            Container container = (Container) component;
            Component[] components = container.getComponents();
            for (Component subComponent : components) {
                // Rekursiv in verschachtelten Containern nach Buttons suchen
                findButtonsAndCheckCSV(subComponent, foundNumbers, csvFilePath);
            }
        }
    }

    private boolean checkCSVForNumber(String number, String csvFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // Annahme: CSV ist kommagetrennt

                if (parts.length > 9) { // Annahme: Die 10. Spalte enthält die Zahlen
                    String csvNumber = parts[4].trim(); // Ändern Sie den Index entsprechend
                    if (csvNumber.equals(number)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void colorButtons(Component component, Set<String> foundNumbers) {
        if (component instanceof JButton) {
            JButton button = (JButton) component;
            String buttonText = button.getText().replace("Platz ", "");

            if (foundNumbers.contains(buttonText)) {
                // Die Nummer wurde in der CSV-Datei gefunden, färben Sie den Button rot
                button.setBackground(Color.RED);
            } else {
                // Die Nummer wurde nicht in der CSV-Datei gefunden, färben Sie den Button grün
                button.setBackground(Color.GREEN);
            }
        } else if (component instanceof Container) {
            // Wenn es sich um ein Container-Objekt handelt, durchlaufen Sie seine Komponenten
            Container container = (Container) component;
            Component[] components = container.getComponents();
            for (Component subComponent : components) {
                // Rekursiv in verschachtelten Containern Buttons einfärben
                colorButtons(subComponent, foundNumbers);
            }
        }
    }

    private void addClickListenerToButtons(Container container) {
        if (container instanceof JButton) {
            JButton button = (JButton) container;
            String buttonText = button.getText();
            button.addActionListener(e -> {
                if (button.getBackground().equals(Color.RED)) {
                    // Wenn der Button rot ist, bedeutet das, dass der Platz belegt ist
                    // Hier können Sie die Zeile in der CSV-Datei auslesen
                    String csvFilePath = "./BuchungsCSV.csv"; // Ändern Sie dies entsprechend
                    String placeNumber = buttonText.replace("Platz ", "");
                    String[] selectedBookingData = readBookingDataFromCSV(csvFilePath, placeNumber);

                    // Öffnen Sie die BuchungsGUI und zeigen Sie die ausgewählten Buchungsdaten an
                    new BuchungsGui(null, selectedBookingData, false); // Der letzte Parameter ist false, da Sie die Daten anzeigen möchten und nicht bearbeiten
                }
            });
        } else if (container instanceof Container) {
            Container subContainer = (Container) container;
            Component[] components = subContainer.getComponents();
            for (Component component : components) {
                addClickListenerToButtons((Container) component);
            }
        }
    }

    private String[] readBookingDataFromCSV(String csvFilePath, String placeNumber) {
        // Lesen Sie die Zeile in der CSV-Datei, die zur ausgewählten Platznummer gehört
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // Annahme: CSV ist kommagetrennt

                if (parts.length > 9) { // Annahme: Die 10. Spalte enthält die Zahlen
                    String csvNumber = parts[4].trim(); // Ändern Sie den Index entsprechend
                    if (csvNumber.equals(placeNumber)) {
                        // Gefundene Zeile zurückgeben
                        return parts;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Platznummer wurde nicht in der CSV-Datei gefunden
    }
}
