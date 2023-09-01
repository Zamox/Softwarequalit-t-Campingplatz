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

import static java.awt.Color.red;

public class MainGui {
    private JFrame frame;
    private DefaultTableModel infoTableModel;
    private DefaultTableModel tableModel2;
    private List<JButton> panelButtonList;
    private List<JButton> buchungsButtonList;
    private JButton loginBtn;
    private static JTable buchungsTable;
    private int selectedRowIndex = -1;

    public MainGui() {
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
                    new upperLeftPlaetze();

                    break;
                case "Neuer Platz":
                    new PlatzAnlegenGui();
                    break;
                case "Login":
                    new Login(MainGui.this);
                    break;
            }
        }
    };

    private JPanel createBottomTablePanel() {
        JPanel buchungsTablePanel = new JPanel(new GridBagLayout());
        buchungsTablePanel.setPreferredSize(new Dimension(300, 150));
        tableModel2 = new DefaultTableModel() {
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
            BufferedImage img = ImageIO.read(new File("./Campingplatz.jpg"));
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
                new upperLeftPlaetze();
            }
        });
        upperRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new upperRightPlaetze();
            }
        });
        lowerLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Button wurde geklickt!");
            }
        });
        lowerRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Button wurde geklickt!");
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
    }

    private JButton createIdentifiedButton(String label) {
        JButton button = new JButton(label);
        button.setPreferredSize(new Dimension(140, 40)); // Button-Größe anpassen
        button.setActionCommand(label); // Verwende das Label als Identifier
        button.addActionListener(buttonListener); // Füge den ActionListener hinzu
        return button;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));

        String[] buttonLabels = {
                "Freie Plätze", "Neuer Platz",
                "Platz bearbeiten", "Platz löschen", "Platz Buchen",
                "Export/Import"
        };

        panelButtonList = new ArrayList<JButton>();

        for (String label : buttonLabels) {
            JButton button = createIdentifiedButton(label); // Button mit Identifier erstellen
            button.setEnabled(false);
            panelButtonList.add(button);
            buttonPanel.add(button);
        }

        loginBtn = createIdentifiedButton("Login"); // Button mit Identifier erstellen
        buttonPanel.add(loginBtn);

        return buttonPanel;
    }

    private ActionListener buttonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String identifier = e.getActionCommand(); // Hole den Identifier des geklickten Buttons

            // Hier kannst du basierend auf dem Identifier die entsprechende Aktion ausführen
            switch (identifier) {
                case "Neue Buchung":
                    new BuchungsGui(MainGui.this, null);
                    break;
                case "Buchung bearbeiten":
                    int selectedRowIndex = buchungsTable.getSelectedRow();
                    if (selectedRowIndex != -1) {
                        datenAuslesen(selectedRowIndex + 1); // +1, da der Index 0-basiert ist, während die Zeilennummer in der CSV 1-basiert ist
                    } else {
                        JOptionPane.showMessageDialog(frame, "Bitte wählen Sie zuerst eine Buchung aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case "Buchung löschen":
                        getAusgewählteZeile();
                        break;
                case "Login":
                    new Login(MainGui.this);
                    break;
                case "Neuer Platz": // Hinzugefügt: Aktion für "Neuer Platz" Button
                    new PlatzAnlegenGui();
                    break;
                case "Export/Import":
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Buchungsdatei speichern");

                    int userSelection = fileChooser.showSaveDialog(frame);

                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        String filePath = selectedFile.getAbsolutePath();

                        try {
                            File sourceFile = new File("./BuchungsCSV.csv");
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

    private void datenAuslesen(int rowIndex) {
        String csvFile = "./BuchungsCSV.csv"; // Pfad zur CSV-Datei

        try {
            // CSV-Datei einlesen
            BufferedReader reader = new BufferedReader(new FileReader(csvFile));
            List<String> zeilen = new ArrayList<>();
            String zeile;

            while ((zeile = reader.readLine()) != null) {
                zeilen.add(zeile);
            }
            reader.close();

            // Überprüfen, ob der ausgewählte Zeilenindex gültig ist
            if (rowIndex >= 0 && rowIndex < zeilen.size()) {
                String ausgewaehlteZeile = zeilen.get(rowIndex);

                // Hier kannst du den ausgewählten Datensatz weiter verarbeiten, z.B., in die BuchungsGui übergeben
                // AusgewaehlteZeile enthält jetzt den ausgewählten Datensatz aus der CSV-Datei
                // ...

                // Erstelle eine Instanz von BuchungsGui und übergebe die ausgewählten Daten
                new BuchungsGui(MainGui.this, ausgewaehlteZeile.split(","));
            } else {
                System.out.println("Ungültiger Zeilenindex.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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

        String csvFile = "./BuchungsCSV.csv"; // Pfad zur CSV-Datei

        try {
            // CSV-Datei einlesen
            BufferedReader reader = new BufferedReader(new FileReader(csvFile));
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
        String csvFilePath = "./BuchungsCSV.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGui());
    }
}
