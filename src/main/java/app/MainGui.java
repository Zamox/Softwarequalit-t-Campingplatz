package app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainGui {
    private JFrame frame;
    private DefaultTableModel infoTableModel;
    private List<JButton> panelButtonList;
    private List<JButton> buchungsButtonList;
    public JButton loginBtn;






    public MainGui() {
        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());
        String t = "test";
        renderFrame();
        this.frame.setSize(1400, 600);
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());

        infoTableModel = new DefaultTableModel(); // Erstelle ein DefaultTableModel

        JTable table = new JTable(infoTableModel); // Erstelle die Tabelle mit dem Modell

        // Füge die Spaltennamen hinzu
        infoTableModel.addColumn("Buchung");
        infoTableModel.addColumn("Name");
        infoTableModel.addColumn("Buchungszeitraum");
        infoTableModel.addColumn("Status");

        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createBottomTablePanel() {
        JPanel buchungsTablePanel = new JPanel(new GridBagLayout());

        DefaultTableModel tableModel2 = new DefaultTableModel();
        // Erstelle ein DefaultTableModel für die zweite Tabelle

        JTable buchungsTable = new JTable(tableModel2); // Erstelle die zweite Tabelle mit dem Modell

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
        tableConstraints.gridx = 0;
        tableConstraints.gridy = 0;
        tableConstraints.gridwidth = 1;
        tableConstraints.gridheight = 1;
        tableConstraints.fill = GridBagConstraints.BOTH; // Füllen Sie den verfügbaren Platz
        tableConstraints.weightx = 1.0;
        tableConstraints.weighty = 0.2; // Hier den Wert anpassen, um den vertikalen Platzbedarf zu steuern

        buchungsTablePanel.add(scrollPane2, tableConstraints);

        return buchungsTablePanel;
    }



    private void renderFrame() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout());
        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("./src/main/java/app/Platzplan.png");
        Image scaledImage = imageIcon.getImage().getScaledInstance(150, -1, Image.SCALE_SMOOTH);
        imageLabel.setIcon(imageIcon);

        contentPanel.add(imageLabel, BorderLayout.WEST);

        JPanel buchungsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 40)); // Erhöhter vertikaler Abstand hier
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

        mainPanel.add(contentPanel, BorderLayout.WEST); // Hier wird das Bild links von der Tabelle platziert
        contentPanel.add(createTablePanel(), BorderLayout.EAST); // Hier wird die erste Tabelle rechts vom Bild platziert
        mainPanel.add(createBottomTablePanel(), BorderLayout.SOUTH); // Hier wird die zweite Tabelle unterhalb der unteren Buttonreihe platziert





        // Das Panel für die untere Tabelle im South-Bereich des mainPanel hinzufügen

        /*this.frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Aktualisiere das Bild, wenn sich die GUI-Größe ändert
                Image newScaledImage = imageIcon.getImage().getScaledInstance(mainPanel.getWidth(), -1, Image.SCALE_SMOOTH);
                ImageIcon newScaledIcon = new ImageIcon(newScaledImage);
                imageLabel.setIcon(newScaledIcon);
            }
        });*/
        this.frame.add(mainPanel);
        this.frame.setSize(900, 500); // Größe auf 900x500 festlegen
        this.frame.setVisible(true);
    }

    private JButton createIdentifiedButton(String label) {
        JButton button = new JButton(label);
        button.setPreferredSize(new Dimension(140, 40)); // Button-Größe anpassen
        button.setActionCommand(label); // Verwende das Label als Identifier
        button.addActionListener(buttonListener); // Füge den ActionListener hinzu
        return button;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        String[] buttonLabels = {
                "Buchungen", "Freie Plätze", "Neuer Platz",
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
                    new BuchungsGui(MainGui.this);
                    break;
                case "Buchung bearbeiten":
                    // Funktion für "Buchung bearbeiten" ausführen
                    break;
                case "Login":
                    new Login(MainGui.this);

                    break;
            }
        }
    };

    public void enable() {
        for (JButton button : panelButtonList){
            button.setEnabled(true);
        }

        for (JButton button : buchungsButtonList){
            button.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGui());
    }
}