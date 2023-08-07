package app;

import javax.swing.*;

public class Stellplaetze {
    public static void main(String[] args) {
        try {
            buildStellplatz();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void buildStellplatz(){
        //create JFrame
        JFrame mainFrame = new JFrame("Stellplatz");
        mainFrame.setSize(200, 400);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
}
