package app;

import de.dhbwka.swe.utils.event.GUIEvent;
import de.dhbwka.swe.utils.event.IGUIEventListener;
import de.dhbwka.swe.utils.gui.CalendarComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BuchungsGui {

    JFrame frame = new JFrame("Campingplatz Buchung");
    private JTextField anreiseField;
    private JTextField abreiseField;


    public BuchungsGui(MainGui mainGui) {
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(1000, 400);

        JPanel leftPanel = createLeftPanel();
        JPanel rightPanel = createRightPanel();
        JPanel mainPanel = createMainPanel(leftPanel, rightPanel);

        frame.add(mainPanel);
        frame.setVisible(true);
    }



    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel(new GridLayout(9, 2));


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
        leftPanel.add(new JTextField());

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

    private static JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new GridLayout(9, 2));

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

    private static JPanel createMainPanel(JPanel leftPanel, JPanel rightPanel) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        // Schaltfläche zur Bestätigung der Buchung
        JButton buchungsButton = new JButton("Buchung bestätigen");
        mainPanel.add(buchungsButton, BorderLayout.SOUTH);

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
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                        String formattedDate = selectedDate.format(formatter);
                        abreiseField.setText(formattedDate);
                        calendarFrame.dispose();
                    }
                }
            }
        });

        calendarFrame.add(calendarComponent);
        calendarFrame.pack();
        calendarFrame.setVisible(true);
    }

}
