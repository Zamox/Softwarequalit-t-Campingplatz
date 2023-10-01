package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class downRightPlaetze {

    private JFrame frame;
    private JFrame parentframe;
    private BuchungBearbeitenGui BuchungBearbeitenGui;
    private BuchungErstellenGui BuchungErstellenGui;
    private String[] csv_inhalt;
    private String fall;

    public downRightPlaetze(String fall) {
        this.frame = new JFrame("Platzregion Süd-Ost");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.fall = fall;
        renderFrame();
    }

    public downRightPlaetze(BuchungBearbeitenGui BuchungBearbeitenGui, JFrame parentframe, String fall) {
        this.frame = new JFrame("Platzregion Süd-Ost");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.BuchungBearbeitenGui = BuchungBearbeitenGui;
        this.parentframe = parentframe;
        this.fall = fall;
        renderFrame();
    }

    public downRightPlaetze(BuchungErstellenGui BuchungErstellenGui, JFrame parentframe, String fall) {
        this.frame = new JFrame("Platzregion Süd-Ost");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.BuchungErstellenGui = BuchungErstellenGui;
        this.parentframe = parentframe;
        this.fall = fall;
        renderFrame();
    }

    private void renderFrame() {
        JPanel mainPanel = new JPanel(); // 7 Spalten insgesamt

        // Leerzeile für Spalte 2
        mainPanel.add(new JPanel());

        JPanel column1Panel = createButtonPanel(61, 63, 91);
        mainPanel.add(column1Panel);
        this.frame.setContentPane(mainPanel);
        this.frame.pack();
        this.frame.setSize(700, 250);
        this.frame.setVisible(true);

        // Add click listener to buttons
        addClickListenerToButtons(mainPanel);
        checkCSVAndColorButtons();
    }

    private JPanel createButtonPanel(int startNumber_a, int endNumber_a, int startNumber_b) {
        String dateiPfad = "./Platzdaten.csv";
        String temp = "";
        try(BufferedReader br = new BufferedReader(new FileReader(dateiPfad))) {
            String line;
            while ((line = br.readLine()) != null) {
                temp = temp + line + ",";
            }

            csv_inhalt = temp.split(",");

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JPanel panel = new JPanel(new GridLayout(5, 3));

        for (int j = 4; j < csv_inhalt.length ; j+=4) {
            if ((startNumber_a <= Integer.parseInt(csv_inhalt[j]) && Integer.parseInt(csv_inhalt[j]) <= endNumber_a) || (startNumber_b <= Integer.parseInt(csv_inhalt[j]))) {
                JButton button = new JButton("Platz " + csv_inhalt[j]);
                panel.add(button);
            }
        }

        return panel;
    }

    private void checkCSVAndColorButtons() {
        String csvFilePath = "./BuchungsCSV.csv";

        Container mainPanel = this.frame.getContentPane();
        Set<String> foundNumbers = new HashSet<>();

        findButtonsAndCheckCSV(mainPanel, foundNumbers, csvFilePath);
        colorButtons(mainPanel, foundNumbers);
    }

    private void findButtonsAndCheckCSV(Component component, Set<String> foundNumbers, String csvFilePath) {
        if (component instanceof JButton) {
            JButton button = (JButton) component;
            String buttonText = button.getText().replace("Platz ", "");

            if (checkCSVForNumber_Buchungen(buttonText, csvFilePath) || checkCSVForNumber_Plaetze(buttonText)) {
                foundNumbers.add(buttonText);
            }
        } else if (component instanceof Container) {
            Container container = (Container) component;
            Component[] components = container.getComponents();
            for (Component subComponent : components) {
                findButtonsAndCheckCSV(subComponent, foundNumbers, csvFilePath);
            }
        }
    }

    private boolean checkCSVForNumber_Buchungen(String number, String csvFilePath) {
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

    private boolean checkCSVForNumber_Plaetze(String number) {
        try (BufferedReader br = new BufferedReader(new FileReader("Platzdaten.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // Annahme: CSV ist kommagetrennt

                if (parts.length > 2) { // Annahme: Die 10. Spalte enthält die Zahlen
                    String csvNumber = parts[0]; // Ändern Sie den Index entsprechend
                    if (csvNumber.equals(number) && parts[1].equals("belegt")) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void colorButtons(Component component, Set<String> belegtePlaetze) {
        if (component instanceof JButton) {
            JButton button = (JButton) component;
            String buttonText = button.getText().replace("Platz ", "");

            if (belegtePlaetze.contains(buttonText)) {

                int index = -1;
                for (int i = 4; i < csv_inhalt.length; i+=4) {
                    if (csv_inhalt[i].equals(buttonText)) {
                        index = i;
                        break;
                    }
                }
                if(index != -1 && csv_inhalt[index+3].equals("keine")){
                    button.setBackground(Color.BLUE);
                }
                else {

                    button.setBackground(Color.RED);
                }

            } else {
                button.setBackground(Color.GREEN);
            }
        } else if (component instanceof Container) {
            Container container = (Container) component;
            Component[] components = container.getComponents();
            for (Component subComponent : components) {
                colorButtons(subComponent, belegtePlaetze);
            }
        }
    }

    private void addClickListenerToButtons(Container container) {
        if (container instanceof JButton) {
            JButton button = (JButton) container;
            String buttonText = button.getText();
            button.addActionListener(e -> {
                if (button.getBackground().equals(Color.RED) || button.getBackground().equals(Color.BLUE)) {
                    String csvFilePath = "./Platzdaten.csv";
                    String placeNumber = buttonText.replace("Platz ", "");
                    String[] selectedBookingData = readBookingDataFromCSV(csvFilePath, placeNumber);

                } else if (button.getBackground().equals(Color.GREEN)) {
                    PlatzTransfer dataSingleton = PlatzTransfer.getInstance();
                    switch (fall){
                        case "erstellen":
                            dataSingleton = PlatzTransfer.getInstance();
                            dataSingleton.setSharedData(button.getText());
                            BuchungErstellenGui.updatePlatzNummer();
                            parentframe.dispose();
                            this.frame.dispose();
                            break;

                        case "bearbeiten":
                            dataSingleton = PlatzTransfer.getInstance();
                            dataSingleton.setSharedData(button.getText());
                            BuchungBearbeitenGui.updatePlatzNummer();
                            parentframe.dispose();
                            this.frame.dispose();
                            break;
                    }
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