package model;

import javax.swing.*;

public class Stellplatz {
    public Stellplatz() {
    }

    public static void main(String[] args) {
        try {
            buildStellplatz();
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    private static void buildStellplatz() {
        JFrame mainFrame = new JFrame("Stellplatz");
        mainFrame.setSize(200, 400);
        mainFrame.setDefaultCloseOperation(3);
        mainFrame.setVisible(true);
    }
}
