package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlatzAnlegenGui {
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel styleLeftPanel;
    private JPanel styleRightPanel;

    private JTextField platznummerField;

    private JRadioButton stellplatzRadio;
    private JRadioButton shopRadio;
    private JRadioButton sanitäreAnlagenRadio;
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

    public PlatzAnlegenGui() {
        frame = new JFrame("Platz anlegen");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(600, 600));

        mainPanel = new JPanel(new BorderLayout());
        styleLeftPanel = new JPanel(new GridLayout(0, 2, 10, 20)); // Erhöhter Abstand zwischen den Komponenten
        styleLeftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        styleRightPanel = new JPanel(); // Rechtes Panel für zusätzliche Inhalte
        styleRightPanel.setLayout(new BorderLayout());

        // Platznummer
        styleLeftPanel.add(new JLabel("Platznummer:"));
        platznummerField = new JTextField();
        styleLeftPanel.add(platznummerField);

        // Platzart
        JPanel platzartPanel = new JPanel();
        platzartPanel.setLayout(new GridLayout(0, 1));
        platzartPanel.add(new JLabel("Platzart:"));
        stellplatzRadio = new JRadioButton("Stellplatz");
        shopRadio = new JRadioButton("Shop");
        sanitäreAnlagenRadio = new JRadioButton("Sanitäre Anlagen");
        sonstigeRadio = new JRadioButton("Sonstige");

        platzartGroup = new ButtonGroup();
        platzartGroup.add(stellplatzRadio);
        platzartGroup.add(shopRadio);
        platzartGroup.add(sanitäreAnlagenRadio);
        platzartGroup.add(sonstigeRadio);

        platzartPanel.add(stellplatzRadio);
        platzartPanel.add(shopRadio);
        platzartPanel.add(sanitäreAnlagenRadio);
        platzartPanel.add(sonstigeRadio);
        styleLeftPanel.add(platzartPanel);

        // Wohnoption
        JPanel wohnoptionPanel = new JPanel();
        wohnoptionPanel.setLayout(new GridLayout(0, 1));
        wohnoptionPanel.add(new JLabel("Wohnoption:"));
        wohnmobilRadio = new JRadioButton("Wohnmobil");
        wohnwagenRadio = new JRadioButton("Wohnwagen");
        zeltRadio = new JRadioButton("Zelt");

        wohnoptionGroup = new ButtonGroup();
        wohnoptionGroup.add(wohnmobilRadio);
        wohnoptionGroup.add(wohnwagenRadio);
        wohnoptionGroup.add(zeltRadio);

        wohnoptionPanel.add(wohnmobilRadio);
        wohnoptionPanel.add(wohnwagenRadio);
        wohnoptionPanel.add(zeltRadio);
        styleLeftPanel.add(wohnoptionPanel);

        // Breite
        styleLeftPanel.add(new JLabel("Breite:"));
        breiteField = new JTextField(10); // Schmaleres Textfeld
        styleLeftPanel.add(breiteField);

        // Länge
        styleLeftPanel.add(new JLabel("Länge:"));
        längeField = new JTextField(10); // Schmaleres Textfeld
        styleLeftPanel.add(längeField);

        // Personenzahl
        styleLeftPanel.add(new JLabel("Personenzahl:"));
        personenzahlField = new JTextField(10); // Schmaleres Textfeld
        styleLeftPanel.add(personenzahlField);

        // Tagessatz
        styleLeftPanel.add(new JLabel("Tagessatz:"));
        tagessatzField = new JTextField(10); // Schmaleres Textfeld
        styleLeftPanel.add(tagessatzField);

        // Speichern-Button
        JButton speichernButton = new JButton("Speichern");
        speichernButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hier können Sie den Code zum Speichern der Daten implementieren
                // Sie können auf die Eingaben in den Textfeldern zugreifen
            }
        });

        // Beenden-Button
        JButton beendenButton = new JButton("Beenden");
        beendenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Schließen Sie das Fenster
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(speichernButton);
        buttonPanel.add(beendenButton);

        styleRightPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(styleLeftPanel, BorderLayout.WEST);
        mainPanel.add(styleRightPanel, BorderLayout.EAST);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PlatzAnlegenGui();
            }
        });
    }
}
