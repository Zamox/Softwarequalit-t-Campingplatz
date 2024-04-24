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

public class InfoGui {

    JFrame frame = new JFrame("Buchungsinfo");
    private JTextField anreiseField;
    private JTextField abreiseField;
    private JTextField platznummerField; // Hinzugefügt: Platznummer-Feld

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
    private JTextField buchungsnummerField;
    private JTextField personenField;
    private JComboBox unterkunftComboBox;
    private JTextField kostenField;

    public InfoGui(MainGui mainGui, String[] selectedBookingData, boolean isEditable) {
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
        buchungsnummerField = new JTextField();
        personenField = new JTextField();
        unterkunftComboBox = new JComboBox();
        kostenField = new JTextField();

        JPanel leftPanel = createLeftPanel();
        JPanel rightPanel = createRightPanel();
        JPanel mainPanel = createMainPanel(leftPanel, rightPanel);
        fillFieldsWithSelectedData(selectedBookingData);
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
        leftPanel.add(personenField);

        leftPanel.add(new JLabel("Unterkunftstyp:"));
        String[] unterkunftstypen = {"Wohnwagen", "Wohnmobil", "Zelt"};
        unterkunftComboBox = new JComboBox<>(unterkunftstypen);
        leftPanel.add(unterkunftComboBox);
        unterkunftComboBox.setEnabled(isEditable);

        leftPanel.add(new JLabel("Platzauswahl:"));
        platznummerField = new JTextField();
        leftPanel.add(platznummerField);

        leftPanel.add(new JLabel("Kosten:"));
        leftPanel.add(kostenField);

        JButton zeitraumButton = new JButton("Zeitraum wählen");
        zeitraumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectZeitraum();
            }
        });
        leftPanel.add(zeitraumButton);
        zeitraumButton.setEnabled(isEditable);

        JButton platzButton = new JButton("Platz wählen");
        platzButton.setEnabled(isEditable);

        platzButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlatzGUIBuchungen platzGUIBuchungen = new PlatzGUIBuchungen();
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
        fillFieldsWithSelectedData(selectedBookingData);
        platznummerField.setEditable(false);

        for (Component component : rightPanel.getComponents()) {
            if (component instanceof JTextField) {
                ((JTextField) component).setEditable(disable);
            }
        }

        anreiseField.setEditable(false);
        abreiseField.setEditable(false);
        platznummerField.setEditable(disable);
        nameField.setEditable(disable);
        vornameField.setEditable(disable);
        strasseField.setEditable(disable);
        plzField.setEditable(disable);

    }

    private void fillFieldsWithSelectedData(String[] selectedBookingData) {
        if (this.selectedBookingData != null && this.selectedBookingData.length == 16) {
            // Fülle die Felder mit den ausgewählten Daten
            anreiseField.setText(this.selectedBookingData[2]);
            abreiseField.setText(this.selectedBookingData[3]);
            platznummerField.setText(this.selectedBookingData[4]);
            nameField.setText(this.selectedBookingData[0]); // Name
            vornameField.setText(this.selectedBookingData[1]); // Vorname
            strasseField.setText(this.selectedBookingData[7]); //Straße
            plzField.setText(this.selectedBookingData[8]); // PLZ
            hausnummerField.setText(this.selectedBookingData[9]); // Nummer
            rechnungsadresseField.setText(this.selectedBookingData[10]); // Rechnungsadresse
            telefonField.setText(this.selectedBookingData[6]);// Telefon
            emailField.setText(this.selectedBookingData[5]); // Email
            kreditkartendatenField.setText(this.selectedBookingData[11]); // Kreditkartendaten
            buchungsnummerField.setText(this.selectedBookingData[15]);
            personenField.setText(this.selectedBookingData[12]);
            unterkunftComboBox.setSelectedItem(this.selectedBookingData[13]);
            kostenField.setText(this.selectedBookingData[14]);
        } else {
            new JOptionPane("Bitte wählen Sie einen Stellplatz aus.");
        }
    }

    private JPanel createMainPanel(JPanel leftPanel, JPanel rightPanel) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        return mainPanel;
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
