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

public class BuchungErstellenGui {

    JFrame frame = new JFrame("Neue Buchung");
    private JTextField anreiseField;
    private JTextField abreiseField;
    private JTextField platznummerField; // Hinzugefügt: Platznummer-Feld
    private JComboBox<String> unterkunftComboBox;

    private JPanel leftPanel;
    private JPanel rightPanel;

    private MainGui mainGui;
    private String[] selectedBookingData;

    // Deklaration der Felder für Name, Vorname, PLZ, Hausnummer, usw.
    private JTextField nameField;
    private JTextField vornameField;
    private JTextField strasseField;
    private JTextField plzField;
    private JTextField hausnummerField;
    private JTextField rechnungsadresseField;
    private JTextField telefonField;
    private JTextField emailField;
    private JTextField kreditkartendatenField;

    private boolean isEditable;

    public BuchungErstellenGui(MainGui mainGui, String[] selectedBookingData, boolean isEditable) {
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(1000, 400);
        this.mainGui = mainGui;
        this.selectedBookingData = selectedBookingData;
        this.isEditable = isEditable;

        // Erstellen Sie die Felder für Name, Vorname, PLZ, Hausnummer, usw.
        nameField = new JTextField();
        vornameField = new JTextField();
        strasseField = new JTextField();
        plzField = new JTextField();
        hausnummerField = new JTextField();
        rechnungsadresseField = new JTextField();
        telefonField = new JTextField();
        emailField = new JTextField();
        kreditkartendatenField = new JTextField();

        JPanel leftPanel = createLeftPanel();
        JPanel rightPanel = createRightPanel();
        JPanel mainPanel = createMainPanel(leftPanel, rightPanel);
        disableFields(isEditable); // Deaktivieren Sie die Felder, wenn die Info-Funktionalität aktiviert ist
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
        unterkunftComboBox = new JComboBox<>(unterkunftstypen);
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

        JButton platzButton = new JButton("Platz wählen");

        platzButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlatzGUIBuchungen platzGUIBuchungen = new PlatzGUIBuchungen(BuchungErstellenGui.this);
            }
        });
        leftPanel.add(platzButton);

        return leftPanel;
    }

    private JPanel createRightPanel() {
        rightPanel = new JPanel(new GridLayout(10, 2)); // Erhöht auf 10 Reihen für Platznummer

        rightPanel.add(new JLabel("Name:"));
        rightPanel.add(nameField);

        rightPanel.add(new JLabel("Vorname:"));
        rightPanel.add(vornameField);

        rightPanel.add(new JLabel("Straße:"));
        rightPanel.add(strasseField);

        rightPanel.add(new JLabel("PLZ:"));
        rightPanel.add(plzField);

        rightPanel.add(new JLabel("Hausnummer:"));
        rightPanel.add(hausnummerField);

        rightPanel.add(new JLabel("Rechnungsadresse:"));
        rightPanel.add(rechnungsadresseField);

        rightPanel.add(new JLabel("Telefon:"));
        rightPanel.add(telefonField);

        rightPanel.add(new JLabel("Email:"));
        rightPanel.add(emailField);

        rightPanel.add(new JLabel("Kreditkartendaten:"));
        rightPanel.add(kreditkartendatenField);

        return rightPanel;
    }

    private void disableFields(boolean disable) {

        for (Component component : rightPanel.getComponents()) {
            if (component instanceof JTextField) {
                ((JTextField) component).setEditable(disable);
            }
        }

        anreiseField.setEditable(false);
        abreiseField.setEditable(false);
        platznummerField.setEditable(disable);
    }

    private JPanel createMainPanel(JPanel leftPanel, JPanel rightPanel) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        JButton buchungsButton = new JButton("Buchung erstellen");
        buchungsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    bestaetigeBuchung();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                MainGui.updateTable();
            }
        });
        mainPanel.add(buchungsButton, BorderLayout.SOUTH);

        return mainPanel;
    }

    private void bestaetigeBuchung() throws IOException {
        // Überprüfe, ob alle erforderlichen Felder ausgefüllt sind
        if (anreiseField.getText().isEmpty() || abreiseField.getText().isEmpty() ||
                platznummerField.getText().isEmpty() ||
                nameField.getText().isEmpty() || // Name
                vornameField.getText().isEmpty() || // Vorname
                plzField.getText().isEmpty() || // PLZ
                hausnummerField.getText().isEmpty() || // Hausnummer
                emailField.getText().isEmpty() || // Email
                telefonField.getText().isEmpty()) { // Telefon
            JOptionPane.showMessageDialog(frame, "Bitte füllen Sie alle erforderlichen Felder aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
        } else {
            // Alle erforderlichen Felder sind ausgefüllt, speichere die Daten in der CSV-Datei
            speichereBuchungsdaten();
            this.frame.dispose();
        }
    }

    public void updatePlatzNummer() {
        PlatzTransfer dataSingleton = PlatzTransfer.getInstance();
        String sharedData = dataSingleton.getSharedData();
        platznummerField.setText(sharedData.replace("Platz ",""));
    }

    private void speichereBuchungsdaten() throws IOException {
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
        String buchungsnummer = ((JTextField) leftPanel.getComponent(1)).getText();
        String personen = ((JTextField) leftPanel.getComponent(3)).getText();
        String unterkunft = unterkunftComboBox.getSelectedItem().toString();
        String kosten = ((JTextField) leftPanel.getComponent(11)).getText();

        String dateiPfad = "./BuchungsCSV.csv";

        String tz = ",";
        String csv_eintrag = name +tz+ vorname +tz+ anreiseDatum +tz+ abreiseDatum +tz+ platznummer +tz+
                email +tz+ telefon +tz+ strasse +tz+ plz +tz+ hausnummer +tz+ rechnungsadresse +tz+
                kreditkartendaten +tz+ personen +tz+ unterkunft +tz+ kosten +tz+ buchungsnummer+"\n";

        try{
            FileWriter csvWriter = new FileWriter(dateiPfad, true);
            csvWriter.append(csv_eintrag);
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