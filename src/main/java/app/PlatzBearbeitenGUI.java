package app;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class PlatzBearbeitenGUI {
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel styleLeftPanel;
    private JPanel styleRightPanel;

    private JTextField platznummerField;
    private JTextField statusField;
    private JTextField platzregionField;

    private JRadioButton stellplatzRadio;
    private JRadioButton shopRadio;
    private JRadioButton sanitaereAnlagenRadio;
    private JRadioButton sonstigeRadio;
    private ButtonGroup platzartGroup;

    private JRadioButton wohnmobilRadio;
    private JRadioButton wohnwagenRadio;
    private JRadioButton zeltRadio;
    private ButtonGroup wohnoptionGroup;

    private JTextField breiteField;
    private JTextField längeField;
    private JTextField personenzahlField;
    private JTextField tagessatzField;

    private boolean isEditable;

    public PlatzBearbeitenGUI(StellplaetzeInfo platz, boolean isEditable) {
        frame = new JFrame("Platz bearbeiten");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(600, 600));
        this.isEditable = isEditable;

        mainPanel = new JPanel(new BorderLayout());
        styleLeftPanel = new JPanel(new GridLayout(0, 2, 10, 20));
        styleLeftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        styleRightPanel = new JPanel();
        styleRightPanel.setLayout(new BorderLayout());

        // Platznummer
        styleLeftPanel.add(new JLabel("Platznummer:"));
        platznummerField = new JTextField();
        platznummerField.setEditable(false); // Das Textfeld ist nicht editierbar
        styleLeftPanel.add(platznummerField);

        // Status
        styleLeftPanel.add(new JLabel("Status:"));
        statusField = new JTextField();
        statusField.setEditable(false); // Das Textfeld ist nicht editierbar
        styleLeftPanel.add(statusField);

        // Platzregion
        styleLeftPanel.add(new JLabel("Platzregion:"));
        platzregionField = new JTextField();
        platzregionField.setEditable(false); // Das Textfeld ist nicht editierbar
        styleLeftPanel.add(platzregionField);

        // Weitere GUI-Komponenten hier hinzufügen (z.B. Platzart, Wohnoption, etc.)

        // Speichern-Button (nur wenn isEditable true ist)
        if (isEditable) {
            JButton speichernButton = new JButton("Speichern");
            speichernButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Hier können Sie den Code für das Speichern der Änderungen einfügen
                }
            });
            styleRightPanel.add(speichernButton, BorderLayout.SOUTH);
        }

        // Beenden-Button
        JButton beendenButton = new JButton("Beenden");
        beendenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Schließen Sie das Fenster
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(beendenButton);
        styleRightPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(styleLeftPanel, BorderLayout.WEST);
        mainPanel.add(styleRightPanel, BorderLayout.EAST);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        // Platzdaten setzen
        platznummerField.setText(platz.getPlatznummer());
        statusField.setText(platz.getStatus());
        platzregionField.setText(platz.getPlatzregion());
    }

    // Methode, um die Platznummer in das Textfeld zu setzen
    public void setPlatznummer(String platznummer) {
        platznummerField.setText(platznummer);
    }

    // Weitere Setter-Methoden für andere Felder (Status, Platzregion, etc.) können hier hinzugefügt werden
}
