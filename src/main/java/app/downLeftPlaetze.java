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

public class downLeftPlaetze {

    private JFrame frame;
    private JFrame parentframe;
    private BuchungBearbeitenGui BuchungBearbeitenGui;
    private BuchungErstellenGui BuchungErstellenGui;
    private String fall;

    public  downLeftPlaetze(String fall) {
        this.frame = new JFrame("Platzregion Süd-West");
        this.frame.setSize(1000, 1000);
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.fall = fall;
        renderFrame();
    }

    public  downLeftPlaetze(BuchungBearbeitenGui BuchungBearbeitenGui, JFrame parentframe, String fall) {
        this.frame = new JFrame("Platzregion Süd-West");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.BuchungBearbeitenGui = BuchungBearbeitenGui;
        this.parentframe = parentframe;
        this.fall = fall;
        renderFrame();
    }

    public  downLeftPlaetze(BuchungErstellenGui BuchungErstellenGui, JFrame parentframe, String fall) {
        this.frame = new JFrame("Platzregion Süd-West");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.BuchungErstellenGui = BuchungErstellenGui;
        this.parentframe = parentframe;
        this.fall = fall;
        renderFrame();
    }

    private void renderFrame() {
        JPanel mainPanel = new JPanel(); // 7 Spalten insgesamt
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Ändern Sie das Layout auf BoxLayout für die vertikale Anordnung


        // Leerzeile für Spalte 2
        mainPanel.add(new JPanel());

        JPanel column1Panel = createButtonPanel(14, 31, 64, 70);
        addButtonsToPanels(column1Panel, mainPanel);
        mainPanel.add(column1Panel);
        this.frame.setContentPane(mainPanel);
        this.frame.pack();
        this.frame.setSize(700, 250);
        this.frame.setVisible(true);

        // Add click listener to buttons
        addClickListenerToButtons(mainPanel);
        checkCSVAndColorButtons();
    }

    private JPanel createButtonPanel(int startNumber_a, int endNumber_a, int startNumber_b, int endNumber_b) {
        String dateiPfad = "./Platzdaten.csv";
        String temp = "";
        String[] csv_inhalt;
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
        JPanel panel = new JPanel(new GridLayout(5, 5));

        for (int j = 5; j < csv_inhalt.length ; j+=5) {
            if ((startNumber_a <= Integer.parseInt(csv_inhalt[j]) && Integer.parseInt(csv_inhalt[j]) <= endNumber_a) || (startNumber_b <= Integer.parseInt(csv_inhalt[j]) && Integer.parseInt(csv_inhalt[j]) <= endNumber_b)) {
                JButton button = new JButton("Platz " + csv_inhalt[j]);
                panel.add(button);
            }
        }

        return panel;
    }

    private void addButtonsToPanels(JPanel sourcePanel, JPanel mainPanel) {
        // Aufteilen der Buttons in Panels mit jeweils 5 Buttons
        JPanel currentPanel = new JPanel();
        currentPanel.setLayout(new BoxLayout(currentPanel, BoxLayout.Y_AXIS));

        Component[] components = sourcePanel.getComponents();
        for (int i = 0; i < components.length; i++) {
            currentPanel.add(components[i]);
            if ((i + 1) % 5 == 0 || i == components.length - 1) {
                mainPanel.add(currentPanel);
                currentPanel = new JPanel();
                currentPanel.setLayout(new BoxLayout(currentPanel, BoxLayout.Y_AXIS));

                // Fügen Sie einen leeren Border hinzu, um den Abstand zwischen den Panels zu erhöhen
                if ((i + 1) % 10 == 0) {
                    // Größerer Abstand nach jedem ersten Panel
                    currentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 40));
                } else {
                    // Standardabstand
                    currentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
                }
            }
        }

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS)); // Ändern Sie die Achse auf X_AXIS
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
                    String csvFilePath = "./Platzdaten.csv";
                    String placeNumber = buttonText.replace("Platz ", "");
                    String[] selectedBookingData = readBookingDataFromCSV(csvFilePath, placeNumber);

                    // Hier könnte die Logik für die Anzeige der InfoGui stehen
                    // new InfoGui(selectedBookingData);

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
