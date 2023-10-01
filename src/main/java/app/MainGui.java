package app;

//import sun.text.resources.ext.JavaTimeSupplementary_ar_LB;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainGui {
    private JFrame frame;
    private static DefaultTableModel infoTableModel;
    private List<JButton> panelButtonList;
    private List<JButton> buchungsButtonList;
    private static JTable buchungsTable;
    private List<String> zeilen = new ArrayList<>();

    public MainGui() throws IOException {

        File file = new File("BuchungsCSV.csv");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Fehler beim Erstellen der Buchungsdatei.", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write("Name,Vorname,Anreisedatum,Abreisedatum,Platznummer,E-mail,Telefon,Straße,Plz,Hausnummer,Rechnungsadresse,CC-Nr.,AnzahlDerPersonen,Unterkunftstyp,Kosten, Buchungsnummer\n");

        String initial = " 4,vorname,20.08.2023,25.08.2023,35,email,tel,Straße,plz,haus,rech,cc,AnzahlDerPersonen,Unterkunftstyp,Kosten,4\n" +
                "Nachname,Vorname,30.09.2023,03.10.2024,44,fickmeinleben@gmail.com,0720247238123,Straße,postleit,44,Adresse,cc,AnzahlDerPersonen,Unterkunftstyp,Kosten,23467\n" +
                "NameTest,VorTest,27.09.2023,30.09.2023,56,mail,tel,Straße,plz,hausnummer,rradresse,cc,AnzahlDerPersonen,Unterkunftstyp,Kosten,26248\n" +
                "name,vorname,06.10.2023,08.10.2023,46,email,telefon,straße,plz,hausnummer,rechnungsadresse,cc,2,Zelt,40€,16437";
        bw.write(initial);

        bw.close();

        file = new File("Platzdaten.csv");
        bw = new BufferedWriter(new FileWriter(file));
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Fehler beim Erstellen der Platzdatei.", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }
        bw.write("Platznummer,Status,Platzart,Wohnoption\n");

        String initialConditions = "14,belegt,Shop,keine\n" +
                "15,frei,Stellplatz,Wohnmobil\n" +
                "16,frei,Stellplatz,Wohnmobil\n" +
                "17,frei,Stellplatz,Wohnmobil\n" +
                "18,frei,Stellplatz,Wohnmobil\n" +
                "19,frei,Stellplatz,Wohnmobil\n" +
                "20,frei,Stellplatz,Wohnmobil\n" +
                "21,frei,Stellplatz,Wohnmobil\n" +
                "22,frei,Stellplatz,Wohnmobil\n" +
                "23,frei,Stellplatz,Wohnmobil\n" +
                "24,frei,Stellplatz,Wohnmobil\n" +
                "25,frei,Stellplatz,Wohnmobil\n" +
                "26,belegt,Sonstige,keine\n" +
                "27,frei,Stellplatz,Wohnmobil\n" +
                "28,frei,Stellplatz,Wohnmobil\n" +
                "29,frei,Stellplatz,Wohnmobil\n" +
                "30,frei,Stellplatz,Wohnmobil\n" +
                "31,frei,Stellplatz,Wohnmobil\n" +
                "34,frei,Stellplatz,Zelt\n" +
                "35,belegt,Stellplatz,Zelt\n" +
                "36,frei,Stellplatz,Zelt\n" +
                "37,frei,Stellplatz,Zelt\n" +
                "38,frei,Stellplatz,Zelt\n" +
                "39,frei,Stellplatz,Zelt\n" +
                "40,frei,Stellplatz,Zelt\n" +
                "41,frei,Stellplatz,Zelt\n" +
                "42,frei,Stellplatz,Zelt\n" +
                "43,frei,Stellplatz,Zelt\n" +
                "44,belegt,Stellplatz,Zelt\n" +
                "45,frei,Stellplatz,Zelt\n" +
                "46,belegt,Stellplatz,Zelt\n" +
                "47,frei,Stellplatz,Zelt\n" +
                "48,frei,Stellplatz,Zelt\n" +
                "49,frei,Stellplatz,Zelt\n" +
                "50,frei,Stellplatz,Zelt\n" +
                "51,belegt,Shop,keine\n" +
                "56,belegt,Stellplatz,Zelt\n" +
                "57,frei,Stellplatz,Zelt\n" +
                "58,belegt,Sonstige,keine\n" +
                "59,frei,Stellplatz,Zelt\n" +
                "60,frei,Stellplatz,Zelt\n" +
                "61,frei,Stellplatz,Wohnwagen\n" +
                "62,frei,Stellplatz,Wohnwagen\n" +
                "63,belegt,Shop,keine\n" +
                "64,frei,Stellplatz,Wohnmobil\n" +
                "65,belegt,Sanitäre Anlagen,keine\n" +
                "66,frei,Stellplatz,Wohnmobil\n" +
                "67,frei,Stellplatz,Wohnmobil\n" +
                "68,frei,Stellplatz,Wohnmobil\n" +
                "69,frei,Stellplatz,Wohnmobil\n" +
                "70,frei,Stellplatz,Wohnmobil\n" +
                "71,frei,Stellplatz,Wohnmobil\n" +
                "72,frei,Stellplatz,Wohnmobil\n" +
                "73,frei,Stellplatz,Wohnmobil\n" +
                "74,frei,Stellplatz,Wohnmobil\n" +
                "75,belegt,Sanitäre Anlagen,keine\n" +
                "76,frei,Stellplatz,Wohnmobil\n" +
                "91,frei,Stellplatz,Wohnmobil\n" +
                "92,frei,Stellplatz,Wohnmobil\n" +
                "99,belegt,Sanitäre Anlagen,keine\n"
                ;
        bw.write(initialConditions);
        bw.close();


        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());
        renderFrame();

    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());

        infoTableModel = new DefaultTableModel() {
            // Override the isCellEditable method to make the cells non-editable
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(infoTableModel);

        // Füge die Spaltennamen hinzu
        infoTableModel.addColumn("Buchung");
        infoTableModel.addColumn("Name");
        infoTableModel.addColumn("Buchungszeitraum");
        infoTableModel.addColumn("Status");

        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }
    private ActionListener belegungsButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String identifier = e.getActionCommand();

            switch (identifier) {
                case "Freie Plätze":
                    new FreiePlaetzeGui();

                    break;
                case "Neuer Platz":
                    new PlatzAnlegenGui();
                    break;
                case "Login":
                    new Login(MainGui.this);
                    break;
                case "Platz bearbeiten":
                    new PlatzUebersichtGui();
                    break;

                case "Export/Import":
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Buchungsdatei speichern");

                    int userSelection = fileChooser.showSaveDialog(frame);

                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        String filePath = selectedFile.getAbsolutePath();

                        try {
                            File sourceFile = new File("BuchungsCSV.csv");
                            Path sourcePath = sourceFile.toPath();
                            Path targetPath = Paths.get(filePath);

                            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

                            JOptionPane.showMessageDialog(frame, "Buchungsdatei erfolgreich gespeichert.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(frame, "Fehler beim Speichern der Buchungsdatei.", "Fehler", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;

            }
        }
    };

    private JPanel createBottomTablePanel() {
        JPanel buchungsTablePanel = new JPanel(new GridBagLayout());
        buchungsTablePanel.setPreferredSize(new Dimension(300, 150));
        // Override the isCellEditable method to make the cells non-editable
        DefaultTableModel tableModel2 = new DefaultTableModel() {
            // Override the isCellEditable method to make the cells non-editable
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        buchungsTable = new JTable(tableModel2);

        // Füge die Spaltennamen hinzu
        tableModel2.addColumn("Name");
        tableModel2.addColumn("Vorname");
        tableModel2.addColumn("Anreise");
        tableModel2.addColumn("Abreise");
        tableModel2.addColumn("Platznummer");
        tableModel2.addColumn("E-Mail");
        tableModel2.addColumn("Telefon");

        JScrollPane scrollPane2 = new JScrollPane(buchungsTable);

        // Konfigurieren Sie die GridBagConstraints für die Tabelle
        GridBagConstraints tableConstraints = new GridBagConstraints();
        tableConstraints.gridx = 2;
        tableConstraints.gridy = 0;
        tableConstraints.gridwidth = 1;
        tableConstraints.gridheight = 1;
        tableConstraints.fill = GridBagConstraints.BOTH; // Füllen Sie den verfügbaren Platz
        tableConstraints.weightx = 2.0;
        tableConstraints.weighty = 0.2;

        buchungsTablePanel.add(scrollPane2, tableConstraints);

        // Fügen Sie einen Seitenabstand hinzu (10 Pixel) - Ändern Sie dies nach Bedarf
        buchungsTablePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        return buchungsTablePanel;
    }

    private void renderFrame() {
        try {
            // Set Nimbus Look and Feel
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout());
        JLabel imageLabel = new JLabel();
        try {
            // Lade das Bild
            BufferedImage img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Campingplatz.jpg")));
            // mach das Bild transparent
            imageLabel.setOpaque(false);




            // Skaliere das Bild auf die gewünschte Größe
            int imageWidth = 550; // Ändere dies auf die gewünschte Breite
            int imageHeight = 400; // Ändere dies auf die gewünschte Höhe
            Image scaledImage = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);

            // Setze das skalierte Bild im JLabel
            imageLabel.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Fügen Sie einen Seitenabstand für das Bild hinzu (10 Pixel) - Ändern Sie dies nach Bedarf
        imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        JButton upperLeftButton = new JButton();
        upperLeftButton.setBounds(0,0, 275, 200);
        JButton upperRightButton = new JButton();
        upperRightButton.setBounds(275, 0, 275, 200);
        JButton lowerLeftButton = new JButton();
        lowerLeftButton.setBounds(0, 200, 275, 200);
        JButton lowerRightButton = new JButton();
        lowerRightButton.setBounds(275, 200, 275, 200);
        upperLeftButton.setOpaque(true);
        //upperLeftButton.setBackground(red);
        upperLeftButton.setContentAreaFilled(false);
        upperLeftButton.setBorderPainted(true);

        upperRightButton.setOpaque(true);
        upperRightButton.setContentAreaFilled(false);

        lowerLeftButton.setOpaque(true);
        lowerLeftButton.setContentAreaFilled(false);

        lowerRightButton.setOpaque(true);
        lowerRightButton.setContentAreaFilled(false);

        imageLabel.add(upperLeftButton);
        imageLabel.add(upperRightButton);
        imageLabel.add(lowerLeftButton);
        imageLabel.add(lowerRightButton);
        upperLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new upperLeftPlaetze("info");
            }
        });
        upperRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new upperRightPlaetze("info");
            }
        });
        lowerLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new downLeftPlaetze("info");
            }
        });
        lowerRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new downRightPlaetze("info");
            }
        });
        contentPanel.add(imageLabel, BorderLayout.WEST);

        JPanel buchungsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Erhöhter vertikaler Abstand hier
        String[] buchungsButtonLabels = {
                "Neue Buchung", "Buchung bearbeiten",
                "Buchung löschen", "Info"
        };

        buchungsButtonList = new ArrayList<JButton>();

        for (String label : buchungsButtonLabels) {
            JButton button = createIdentifiedButton(label); // Button mit Identifier erstellen

            button.setPreferredSize(new Dimension(150, 40)); // Button-Größe anpassen
            button.setEnabled(false);
            buchungsButtonList.add(button);
            buchungsPanel.add(button);
        }
        contentPanel.add(buchungsPanel, BorderLayout.SOUTH);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 130, 0, 10));

        mainPanel.add(contentPanel, BorderLayout.WEST); // Hier wird das Bild links von der Tabelle platziert
        contentPanel.add(createTablePanel(), BorderLayout.EAST); // Hier wird die erste Tabelle rechts vom Bild platziert
        mainPanel.add(createBottomTablePanel(), BorderLayout.SOUTH); // Hier wird die zweite Tabelle unterhalb der unteren Buttonreihe platziert

        this.frame.add(mainPanel);
        this.frame.setSize(1300, 700); // Größe auf 900x500 festlegen
        //this.frame.setResizable(false);
        this.frame.setVisible(true);

        // Daten aus CSV-Datei in Tabelle zwei laden
        loadCSVDataToTable((DefaultTableModel) buchungsTable.getModel());
        loadBuchungTableData((DefaultTableModel) infoTableModel);
        ;
    }

    private JButton createIdentifiedButton(String label) {
        JButton button = new JButton(label);
        button.setPreferredSize(new Dimension(140, 40)); // Button-Größe anpassen
        button.setActionCommand(label); // Verwende das Label als Identifier
        button.addActionListener(buttonListener); // Füge den ActionListener hinzu
        button.addActionListener(belegungsButtonListener);
        return button;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));

        String[] buttonLabels = {
                "Freie Plätze", "Neuer Platz",
                "Platz bearbeiten",
                "Export/Import"
        };

        panelButtonList = new ArrayList<JButton>();

        for (String label : buttonLabels) {
            JButton button = createIdentifiedButton(label); // Button mit Identifier erstellen
            button.setEnabled(false);
            panelButtonList.add(button);
            buttonPanel.add(button);
        }

        JButton loginBtn = createIdentifiedButton("Login"); // Button mit Identifier erstellen
        buttonPanel.add(loginBtn);

        return buttonPanel;
    }

    private ActionListener buttonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String identifier = e.getActionCommand(); // Hole den Identifier des geklickten Buttons
            int selectedRowIndex = buchungsTable.getSelectedRow() + 1;
            // Hier kannst du basierend auf dem Identifier die entsprechende Aktion ausführen
            switch (identifier) {
                case "Neue Buchung":
                    new BuchungErstellenGui(MainGui.this, null, true);
                    break;
                case "Buchung bearbeiten":
                    if (selectedRowIndex != 0) {
                        datenAuslesen(selectedRowIndex); // +1, da der Index 0-basiert ist, während die Zeilennummer in der CSV 1-basiert ist
                    } else {
                        JOptionPane.showMessageDialog(frame, "Bitte wählen Sie zuerst eine Buchung aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
                    }
                    // Überprüfen, ob der ausgewählte Zeilenindex gültig ist
                    if (selectedRowIndex >= 0 && selectedRowIndex < zeilen.size()) {
                        String ausgewaehlteZeile = zeilen.get(selectedRowIndex);
                        new BuchungBearbeitenGui(MainGui.this, ausgewaehlteZeile.split(","), selectedRowIndex,true);
                    } else {
                        System.out.println("Ungültiger Zeilenindex.");
                    }
                    break;
                case "Buchung löschen":
                    getAusgewählteZeile();
                    break;

                case "Info":

                    if (selectedRowIndex != 0) {
                        datenAuslesen(selectedRowIndex); // +1, da der Index 0-basiert ist, während die Zeilennummer in der CSV 1-basiert ist

                    } else {
                        JOptionPane.showMessageDialog(frame, "Bitte wählen Sie zuerst eine Buchung aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
                    }

                    // Überprüfen, ob der ausgewählte Zeilenindex gültig ist

                    if (selectedRowIndex >= 0 && selectedRowIndex < zeilen.size()) {
                        String ausgewaehlteZeile = zeilen.get(selectedRowIndex);
                        new InfoGui(MainGui.this, ausgewaehlteZeile.split(","), false);


                    } else {
                        System.out.println("Ungültiger Zeilenindex.");
                    }
                    break;
            }
        }
    };

    private List<String> datenAuslesen(int selectedRowIndex) {
        String csvFile = "BuchungsCSV.csv"; // Pfad zur CSV-Datei
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(csvFile)));
            String zeile;
            while ((zeile = reader.readLine()) != null) {
                zeilen.add(zeile);
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return zeilen;
    }

    private void getAusgewählteZeile() {
        int selectedRow = buchungsTable.getSelectedRow();
        if (selectedRow != -1) {
            buchungLoeschen(selectedRow + 2); // +1, da der Index 0-basiert ist, während die Zeilennummer in der CSV 1-basiert ist
        } else {
            JOptionPane.showMessageDialog(frame, "Bitte wählen Sie zuerst eine Buchung aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buchungLoeschen(int zeileZumLoeschen) {

        String csvFile = "BuchungsCSV.csv"; // Pfad zur CSV-Datei

        try {
            // CSV-Datei einlesen
            BufferedReader reader = new BufferedReader(new FileReader(new File(csvFile)));
            List<String> zeilen = new ArrayList<>();
            String zeile;

            while ((zeile = reader.readLine()) != null) {
                zeilen.add(zeile);
            }
            reader.close();

            // Prüfen, ob die Zeile existiert
            if (zeileZumLoeschen >= 1 && zeileZumLoeschen <= zeilen.size()) {
                // Zeile löschen
                zeilen.remove(zeileZumLoeschen - 1);

                // Aktualisierte CSV-Datei schreiben
                FileWriter writer = new FileWriter(csvFile);
                for (String updatedZeile : zeilen) {
                    writer.write(updatedZeile + "\n");
                }
                writer.close();

                System.out.println("Zeile " + zeileZumLoeschen + " wurde erfolgreich gelöscht.");

                // Aktualisiere die Tabelle nach dem Löschen
                updateTable();
            } else {
                System.out.println("Die angegebene Zeile existiert nicht.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    // Methode, um alle Buttons zu aktivieren
    private void enableAllButtons() {
        for (JButton button : buchungsButtonList) {
            button.setEnabled(true);
        }
    }

    private void disableButtonsExcept(int indexToExclude) {
        for (int i = 0; i < buchungsButtonList.size(); i++) {
            if (i != indexToExclude) {
                buchungsButtonList.get(i).setEnabled(false);
            }
        }
    }

    public void enable() {
        for (JButton button : panelButtonList) {
            button.setEnabled(true);
        }

        for (JButton button : buchungsButtonList) {
            button.setEnabled(true);
        }
    }

    private void saveEditedData() {
        DefaultTableModel model = (DefaultTableModel) buchungsTable.getModel();
        int selectedRowIndex = 1;
        if (selectedRowIndex >= 0 && selectedRowIndex < model.getRowCount()) {
            // Nehmen Sie die Änderungen an den Daten vor
            String name = (String) model.getValueAt(selectedRowIndex, 0);
            String vorname = (String) model.getValueAt(selectedRowIndex, 1);
            String anreise = (String) model.getValueAt(selectedRowIndex, 2);
            String abreise = (String) model.getValueAt(selectedRowIndex, 3);
            String platznummer = (String) model.getValueAt(selectedRowIndex, 4);
            String email = (String) model.getValueAt(selectedRowIndex, 5);
            String telefon = (String) model.getValueAt(selectedRowIndex, 6);

            // Hier können Sie den Code zum Speichern der Daten in Ihrer CSV-Datei hinzufügen
            // Verwenden Sie die oben erfassten Daten
            // ...

            // Nach dem Speichern können Sie eine Erfolgsmeldung anzeigen
            JOptionPane.showMessageDialog(frame, "Änderungen erfolgreich gespeichert.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void loadCSVDataToTable(DefaultTableModel tableModel) {
        // Pfad zur CSV-Datei ändern, falls erforderlich
        String csvFilePath = "BuchungsCSV.csv";

        try {
            try (BufferedReader br = new BufferedReader(new FileReader(new File(csvFilePath)))) {
                String line;
                boolean firstLine = true;
                while ((line = br.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false;
                        continue; // Überspringe die erste Zeile
                    }
                    String[] data = line.split(",");

                    // Annahme: Die CSV-Datei hat die gewünschte Reihenfolge der Spalten
                    if (data.length >= 7) { // Überprüfen Sie, ob genügend Spalten vorhanden sind
                        String[] rowData = new String[7]; // Erstellen Sie ein Array für die gewünschten Spalten
                        rowData[0] = data[0]; // Name
                        rowData[1] = data[1]; // Vorname
                        rowData[2] = data[2]; // Anreise
                        rowData[3] = data[3]; // Abreise
                        rowData[4] = data[4]; // Platznummer
                        rowData[5] = data[5]; // E-Mail
                        rowData[6] = data[6]; // Telefon

                        tableModel.addRow(rowData); // Fügen Sie die Daten in die Tabelle ein
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadBuchungTableData(DefaultTableModel tableModel) {
        // Pfad zur CSV-Datei ändern, falls erforderlich
        String csvFilePath = "BuchungsCSV.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(new File(csvFilePath)))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Überspringe die erste Zeile
                }
                String[] data = line.split(",");

                // Annahme: Die CSV-Datei hat die gewünschte Reihenfolge der Spalten
                if (data.length >= 16) { // Überprüfen Sie, ob genügend Spalten vorhanden sind
                    String buchungsnummer = data[15]; // Buchungsnummer
                    String name = data[1]; // Name
                    String anreise = data[2]; // Anreise
                    String abreise = data[3]; // Abreise

                    String buchungszeitraum = anreise + " - " + abreise;

                    // Überprüfen, ob die Buchung einen Platz hat (Buchungsnummer ist nicht leer)
                    if (!buchungsnummer.isEmpty()) {
                        String[] rowData = new String[4]; // Erstellen Sie ein Array für die gewünschten Spalten
                        rowData[0] = buchungsnummer; // Buchungsnummer
                        rowData[1] = name; // Name
                        rowData[2] = buchungszeitraum; // Buchungszeitraum
                        rowData[3] = "Status hier eintragen"; // Status (hier musst du den tatsächlichen Status festlegen)

                        tableModel.addRow(rowData); // Fügen Sie die Daten in die Tabelle ein
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void updateTable() {
        // Löschen Sie alle Zeilen aus der Tabelle
        DefaultTableModel model = (DefaultTableModel) buchungsTable.getModel();
        model.setRowCount(0);

        // Laden Sie die Daten erneut aus Ihrer CSV-Datei
        loadCSVDataToTable(model);
        DefaultTableModel infoTableModel = (DefaultTableModel) MainGui.infoTableModel;
        infoTableModel.setRowCount(0);

        // Laden Sie die Daten erneut aus Ihrer CSV-Datei in die infoTableModel
        loadBuchungTableData(infoTableModel);


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new MainGui();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}