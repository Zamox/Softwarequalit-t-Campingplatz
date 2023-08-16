package app;

import de.dhbwka.swe.utils.app.SimpleTableComponentApp;
import de.dhbwka.swe.utils.gui.SimpleTableComponent;
import de.dhbwka.swe.utils.util.IPropertyManager;

import javax.swing.*;
import java.awt.*;

public class MainGui {
    private JFrame frame;

    public MainGui() {
        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());

        renderFrame();
    }

    private void renderFrame() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        JPanel middlePanel = createMiddlePanel();
        mainPanel.add(middlePanel, BorderLayout.CENTER);

        this.frame.add(mainPanel);
         this.frame.setSize(1800, 1000);
        this.frame.setVisible(true);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        String[] buttonLabels = {
                "Buchungen", "Freie Plätze", "Neuer Platz",
                "Platz bearbeiten", "Platz löschen", "Platz Buchen",
                "Export/Import", "Login"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setPreferredSize(new Dimension(120, 40)); // Button-Größe anpassen
            buttonPanel.add(button);
        }

        return buttonPanel;
    }

    private JPanel createMiddlePanel() {
        JPanel middlePanel = new JPanel(new BorderLayout());

        ImageIcon imageIcon = new ImageIcon("./src/main/java/app/Platzplan.png");
        JLabel imageLabel = new JLabel(imageIcon);
        middlePanel.add(imageLabel, BorderLayout.WEST);

        IPropertyManager propManager = null; // Falls du einen PropertyManager benötigst, kannst du ihn hier erstellen
        SimpleTableComponentApp stcApp = new SimpleTableComponentApp();
        SimpleTableComponent simpleTableComponent = stcApp.createSimpleTableComponent("TableComponent", propManager);
        middlePanel.add(simpleTableComponent, BorderLayout.CENTER);

        JPanel buchungsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        String[] buchungsButtonLabels = {
                "Neue Buchung", "Buchung bearbeiten",
                "Buchung löschen", "Info"
        };

        for (String label : buchungsButtonLabels) {
            JButton button = new JButton(label);
            button.setPreferredSize(new Dimension(140, 40)); // Button-Größe anpassen
            buchungsPanel.add(button);
        }
        middlePanel.add(buchungsPanel, BorderLayout.SOUTH);

        return middlePanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGui());
    }
}
