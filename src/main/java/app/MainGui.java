package app;

import de.dhbwka.swe.utils.app.SimpleTableComponentApp;
import de.dhbwka.swe.utils.gui.SimpleTableComponent;
import de.dhbwka.swe.utils.util.IPropertyManager;

import javax.swing.*;
import java.awt.*;

class MainGui {
    private JFrame frame;


    public MainGui() {
        this.frame = new JFrame();
        this.frame.setSize(1000, 1000);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new FlowLayout(FlowLayout.CENTER));

        renderFrame();
    }

    private void renderFrame() {
        JPanel mainPanel = new JPanel(new GridLayout(3,1));
        JPanel buttonPanel = new JPanel(new GridLayout(1,8));
        JPanel middlePanel = new JPanel(new BorderLayout());
        JPanel buchungsPanel = new JPanel(new GridLayout(1,1));
        JPanel tablepanel = new JPanel();

            buttonPanel.add(new JButton("Buchungen"));
            buttonPanel.add(new JButton("Freie Plätze"));
            buttonPanel.add(new JButton("Neuer Platz"));
            buttonPanel.add(new JButton("Platz bearbeiten"));
            buttonPanel.add(new JButton("Platz löschen"));
            buttonPanel.add(new JButton("Platz Buchen"));
            buttonPanel.add(new JButton("Export/Import"));
            buttonPanel.add(new JButton("Login"));


            ImageIcon imageIcon = new ImageIcon("./src/main/java/app/Platzplan.png");
            JLabel imageLabel = new JLabel(imageIcon);
            middlePanel.add(imageLabel, BorderLayout.WEST);
        IPropertyManager propManager = null; // Falls du einen PropertyManager benötigst, kannst du ihn hier erstellen
         // If you have a PropertyManager, you can pass it here
        SimpleTableComponentApp stcApp = new SimpleTableComponentApp();
        SimpleTableComponent simpleTableComponent = stcApp.createSimpleTableComponent("TableComponent", propManager);
        middlePanel.add(simpleTableComponent, BorderLayout.CENTER);

        buchungsPanel.add(new JButton("Neue Buchung"));
        buchungsPanel.add(new JButton("Buchung bearbeiten"));
        buchungsPanel.add(new JButton("Buchung löschen"));
        buchungsPanel.add(new JButton("Info"));

        SimpleTableComponentApp stcApp2 = new SimpleTableComponentApp();
        SimpleTableComponent simpleTableComponent2 = stcApp2.createSimpleTableComponent("TableComponent", propManager);
        tablepanel.add(simpleTableComponent2);

        middlePanel.add(buchungsPanel, BorderLayout.SOUTH);

        mainPanel.add(buttonPanel);
        mainPanel.add(middlePanel);
        mainPanel.add(tablepanel);
        this.frame.setContentPane(mainPanel);
        this.frame.pack();
        this.frame.setVisible(true);

    }



}