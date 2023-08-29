package app;

import de.dhbwka.swe.utils.event.GUIEvent;
import de.dhbwka.swe.utils.event.IGUIEventListener;
import de.dhbwka.swe.utils.gui.CalendarComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BuchungsGui {

    JFrame frame = new JFrame("Campingplatz Buchung");
    private JTextField anreiseField;
    private JTextField abreiseField;
    private JTextField platznummerField; // Hinzugefügt: Platznummer-Feld

    private JPanel leftPanel;
    private JPanel rightPanel;

    private MainGui mainGui;
    private String[] selectedBookingData;

    public BuchungsGui(MainGui mainGui, String[] selectedBookingData) {
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(1000, 400);
        this.mainGui = mainGui;
        this.selectedBookingData = selectedBookingData;

        JPanel leftPanel = createLeftPanel();
        JPanel rightPanel = createRightPanel();
        JPanel mainPanel = createMainPanel(leftPanel, rightPanel);
        fillFieldsWithSelectedData();
        frame.add(mainPanel);
        frame.setVisible(true);
    }



    private JPanel createLeftPanel() {
        leftPanel = new JPanel(new GridLayout(10, 2)); // Erhöht auf 10 Reihen für Platznummer

        leftPanel.add(new JLabel("Anreise:"));
        anreiseField = new JTextField();
        anreiseField.setEditable(false);
        leftPanel.add(anreiseField);

        leftPanel.add(new JLabel("Abreise:"));
        abreiseField = new JTextField();
        abreiseField.setEditable(false);
        leftPanel.add(abreiseField);

        leftPanel.add(new JLabel("Anzahl der Personen:"));
        leftPanel.add(new JTextField());

        leftPanel.add(new JLabel("Unterkunftstyp:"));
        String[] unterkunftstypen = {"Wohnwagen", "Wohnmobil", "Zelt"};
        JComboBox<String> unterkunftComboBox = new JComboBox<>(unterkunftstypen);
        leftPanel.add(unterkunftComboBox);

        leftPanel.add(new JLabel("Platzauswahl:"));
        platznummerField = new JTextField();
        leftPanel.add(platznummerField);

        leftPanel.add(new JLabel("Kosten:"));
        leftPanel.add(new JTextField());

        JButton zeitraumButton = new JButton("Zeitraum wählen");
        zeitraumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectZeitraum();
            }
        });
        leftPanel.add(zeitraumButton);

        return leftPanel;
    }

    private JPanel createRightPanel() {
        rightPanel = new JPanel(new GridLayout(10, 2)); // Erhöht auf 10 Reihen für Platznummer

        rightPanel.add(new JLabel("Name:"));
        rightPanel.add(new JTextField());

        rightPanel.add(new JLabel("Vorname:"));
        rightPanel.add(new JTextField());

        rightPanel.add(new JLabel("Straße:"));
        rightPanel.add(new JTextField());

        rightPanel.add(new JLabel("PLZ:"));
        rightPanel.add(new JTextField());

        rightPanel.add(new JLabel("Hausnummer:"));
        rightPanel.add(new JTextField());

        rightPanel.add(new JLabel("Rechnungsadresse:"));
        rightPanel.add(new JTextField());

        rightPanel.add(new JLabel("Telefon:"));
        rightPanel.add(new JTextField());

        rightPanel.add(new JLabel("Email:"));
        rightPanel.add(new JTextField());

        rightPanel.add(new JLabel("Kreditkartendaten:"));
        rightPanel.add(new JTextField());

        return rightPanel;
    }

    private void fillFieldsWithSelectedData() {
        if (selectedBookingData != null && selectedBookingData.length == 7) {
            // Fülle die Felder mit den ausgewählten Daten
            anreiseField.setText(selectedBookingData[2]);
            abreiseField.setText(selectedBookingData[3]);
            platznummerField.setText(selectedBookingData[4]);
            ((JTextField) rightPanel.getComponent(1)).setText(selectedBookingData[0]); // Name
            ((JTextField) rightPanel.getComponent(3)).setText(selectedBookingData[1]); // Vorname
            ((JTextField) rightPanel.getComponent(7)).setText(selectedBookingData[5]); // Email
            ((JTextField) rightPanel.getComponent(9)).setText(selectedBookingData[6]); // Telefon
        }
    }

    private JPanel createMainPanel(JPanel leftPanel, JPanel rightPanel) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        // Schaltfläche zur Bestätigung der Buchung
        JButton buchungsButton = new JButton("Buchung bestätigen");
        buchungsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bestaetigeBuchung();
                MainGui.updateTable();
            }
        });
        mainPanel.add(buchungsButton, BorderLayout.SOUTH);

        return mainPanel;
    }

    private void bestaetigeBuchung() {
        // Überprüfe, ob alle erforderlichen Felder ausgefüllt sind
        if (anreiseField.getText().isEmpty() || abreiseField.getText().isEmpty() ||
                platznummerField.getText().isEmpty() ||
                ((JTextField) rightPanel.getComponent(1)).getText().isEmpty() || // Name
                ((JTextField) rightPanel.getComponent(3)).getText().isEmpty() || // Vorname
                ((JTextField) rightPanel.getComponent(7)).getText().isEmpty() || // Email
                ((JTextField) rightPanel.getComponent(9)).getText().isEmpty()) { // Telefon
            JOptionPane.showMessageDialog(frame, "Bitte füllen Sie alle erforderlichen Felder aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
        } else {
            // Alle erforderlichen Felder sind ausgefüllt, speichere die Daten in der CSV-Datei
            speichereBuchungsdaten();
        }
    }

    // Methode zum Speichern der Buchungsdaten in einer CSV-Datei
    private void speichereBuchungsdaten() {
        String anreiseDatum = anreiseField.getText();
        String abreiseDatum = abreiseField.getText();
        String platznummer = platznummerField.getText();

        String name = ((JTextField) rightPanel.getComponent(1)).getText();
        String vorname = ((JTextField) rightPanel.getComponent(3)).getText();
        String strasse = ((JTextField) rightPanel.getComponent(5)).getText();
        String plz = ((JTextField) rightPanel.getComponent(7)).getText();
        String hausnummer = ((JTextField) rightPanel.getComponent(9)).getText();
        String rechnungsadresse = ((JTextField) rightPanel.getComponent(11)).getText();
        String telefon = ((JTextField) rightPanel.getComponent(13)).getText();
        String email = ((JTextField) rightPanel.getComponent(15)).getText();
        String kreditkartendaten = ((JTextField) rightPanel.getComponent(17)).getText();

        String dateiPfad = "./BuchungsCSV.csv";

        try {
            FileWriter csvWriter = new FileWriter(dateiPfad, true);
            csvWriter.append(name);
            csvWriter.append(",");
            csvWriter.append(vorname);
            csvWriter.append(",");
            csvWriter.append(anreiseDatum);
            csvWriter.append(",");
            csvWriter.append(abreiseDatum);
            csvWriter.append(",");
            csvWriter.append(platznummer);
            csvWriter.append(",");
            csvWriter.append(email);
            csvWriter.append(",");
            csvWriter.append(telefon);
            csvWriter.append(",");
            // Fügen Sie hier die restlichen Felder in der gewünschten Reihenfolge hinzu
            csvWriter.append(strasse);
            csvWriter.append(",");
            csvWriter.append(plz);
            csvWriter.append(",");
            csvWriter.append(hausnummer);
            csvWriter.append(",");
            csvWriter.append(rechnungsadresse);
            csvWriter.append(",");
            csvWriter.append(kreditkartendaten);
            csvWriter.append(",");
            csvWriter.append("AnzahlDerPersonen"); // Beispiel: Platzhalter für Anzahl der Personen
            csvWriter.append(",");
            csvWriter.append("Unterkunftstyp"); // Beispiel: Platzhalter für Unterkunftstyp
            csvWriter.append(",");
            csvWriter.append("Kosten"); // Beispiel: Platzhalter für Kosten
            csvWriter.append("\n");
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Erfolgreiche Buchungsbestätigung
        JOptionPane.showMessageDialog(frame, "Buchung erfolgreich bestätigt. Die Daten wurden in einer CSV-Datei gespeichert.");
    }

    // Methode zur Auswahl des Anreise- und Abreisezeitraums
    private void selectZeitraum() {
        JFrame calendarFrame = new JFrame("Zeitraum wählen");

        // Erstelle die CalendarComponent
        CalendarComponent calendarComponent = CalendarComponent.builder("Zeitraum")
                .date(LocalDate.now())
                .startYear(2023)
                .endYear(2025)
                .build();

        // Verfolge die ausgewählten Daten
        final LocalDate[] anreiseDatum = {null};
        final LocalDate[] abreiseDatum = {null};

        calendarComponent.addObserver(new IGUIEventListener() {
            @Override
            public void processGUIEvent(GUIEvent ge) {
                if (ge.getCmd().equals(CalendarComponent.Commands.DATE_SELECTED)) {
                    LocalDate selectedDate = (LocalDate) ge.getData();

                    // Wenn noch kein Anreisedatum ausgewählt wurde, setze das ausgewählte Datum als Anreisedatum
                    if (anreiseDatum[0] == null) {
                        anreiseDatum[0] = selectedDate;
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                        String formattedDate = selectedDate.format(formatter);
                        anreiseField.setText(formattedDate);
                    } else {
                        // Andernfalls setze das ausgewählte Datum als Abreisedatum
                        abreiseDatum[0] = selectedDate;

                        // Überprüfe, ob das Abreisedatum vor dem Anreisedatum liegt
                        if (abreiseDatum[0].isBefore(anreiseDatum[0])) {
                            JOptionPane.showMessageDialog(calendarFrame, "Abreisedatum kann nicht vor dem Anreisedatum liegen.", "Fehler", JOptionPane.ERROR_MESSAGE);
                            // Setze die Textfelder zurück, damit Benutzer beide Daten erneut eingeben können
                            anreiseField.setText("");
                            abreiseField.setText("");
                            anreiseDatum[0] = null; // Setze Anreisedatum zurück
                            abreiseDatum[0] = null; // Setze Abreisedatum zurück
                        } else {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                            String formattedDate = selectedDate.format(formatter);
                            abreiseField.setText(formattedDate);
                            calendarFrame.dispose();
                        }
                    }
                }
            }
        });
        calendarFrame.setPreferredSize(new Dimension(500, 500));
        calendarFrame.add(calendarComponent);
        calendarFrame.pack();
        calendarFrame.setVisible(true);
    }
}
