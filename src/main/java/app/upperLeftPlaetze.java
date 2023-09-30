package app;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class upperLeftPlaetze {
    private JFrame frame;

    private JFrame parentframe;
    private BuchungBearbeitenGui BuchungBearbeitenGui;
    private BuchungErstellenGui BuchungErstellenGui;
    private String fall;
    private PlatzVerwaltung platzVerwaltung = new PlatzVerwaltung();

    public upperLeftPlaetze(String fall) {
        this.frame = new JFrame("Platzregion Nord-West");
        this.frame.setSize(1000, 1000);
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setLayout(new GridLayout(1, 7));
        this.fall = fall;
        renderFrame();
    }

    public upperLeftPlaetze(BuchungBearbeitenGui BuchungBearbeitenGui, JFrame parentframe, String fall) {
        this.frame = new JFrame("Platzregion Nord-West");
        this.frame.setSize(1000, 1000);
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setLayout(new GridLayout(1, 7));
        this.BuchungBearbeitenGui = BuchungBearbeitenGui;
        this.parentframe = parentframe;
        this.fall = fall;
        renderFrame();
    }

    public upperLeftPlaetze(BuchungErstellenGui BuchungErstellenGui, JFrame parentframe, String fall) {
        this.frame = new JFrame("Platzregion Nord-West");
        this.frame.setSize(1000, 1000);
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setLayout(new GridLayout(1, 7));
        this.BuchungErstellenGui = BuchungErstellenGui;
        this.parentframe = parentframe;
        this.fall = fall;
        renderFrame();
    }

    private void renderFrame() {
        JPanel mainPanel = new JPanel(new GridLayout(1, 7));

        // Leerzeile für Spalte 2
        mainPanel.add(new JPanel());

        // Erste Spalte mit 4 Buttons (individuelle Nummern von 34 bis 37)
        JPanel column1Panel = createButtonPanel(4, 34);
        mainPanel.add(column1Panel);

        // Leerzeile für Spalte 5
        mainPanel.add(new JPanel());

        // Spalte 3 mit 4 Buttons (individuelle Nummern von 38 bis 41)
        JPanel column3Panel = createButtonPanel(4, 38);
        mainPanel.add(column3Panel);

        // Spalte 4 mit 4 Buttons (individuelle Nummern von 42 bis 45)
        JPanel column4Panel = createButtonPanel(4, 42);
        mainPanel.add(column4Panel);

        // Leerzeile für Spalte 5
        mainPanel.add(new JPanel());

        // Spalte 6 mit 4 Buttons (individuelle Nummern von 46 bis 49)
        JPanel column6Panel = createButtonPanel(4, 46);
        mainPanel.add(column6Panel);

        // Spalte 7 mit 4 Buttons (individuelle Nummern von 50 bis 53)
        JPanel column7Panel = createButtonPanel(2, 50);
        mainPanel.add(column7Panel);

        this.frame.setContentPane(mainPanel);
        this.frame.pack();
        this.frame.setVisible(true);

        // Color buttons based on CSV data
        colorButtonsBasedOnCSVStatus();
        addClickListenerToButtons(mainPanel);
    }

    private JPanel createButtonPanel(int buttonCount, int startNumber) {
        JPanel panel = new JPanel(new GridLayout(buttonCount, 1));

        for (int i = 0; i < buttonCount; i++) {
            JButton button = new JButton("Platz " + (startNumber + i));
            panel.add(button);
        }

        return panel;
    }

    private void colorButtonsBasedOnCSVStatus() {
        String csvFilePath = "./Platzdaten.csv"; // Update with your CSV file path

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            Set<String> belegtePlaetze = new HashSet<>();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String platznummerStr = parts[0].trim();
                    String status = parts[1].trim();

                    if (status.equalsIgnoreCase("belegt")) {
                        belegtePlaetze.add(platznummerStr);
                    }
                }
            }

            Container mainPanel = this.frame.getContentPane();
            colorButtons(mainPanel, belegtePlaetze);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void colorButtons(Component component, Set<String> belegtePlaetze) {
        if (component instanceof JButton) {
            JButton button = (JButton) component;
            String buttonText = button.getText().replace("Platz ", "");

            if (belegtePlaetze.contains(buttonText)) {
                button.setBackground(Color.RED);
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
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String csvNumber = parts[0].trim();
                    if (csvNumber.equals(placeNumber)) {
                        return parts;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}