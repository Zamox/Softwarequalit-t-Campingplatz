package app;

import javax.swing.*;
import java.awt.*;

public class Help {
        private JFrame frame;


        public Help() {
            this.frame = new JFrame("HELP");
            this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.frame.setLayout(new FlowLayout(FlowLayout.CENTER));
            renderFrame();
        }
        private void renderFrame() {
            JPanel mainPanel = new JPanel(new GridLayout(3, 1));
            JLabel Stellplatz_f = new JLabel("Freier Stellplatz");
            Stellplatz_f.setForeground(Color.GREEN);
            JLabel Stellplatz_b = new JLabel("Gebuchter Stellplatz");
            Stellplatz_b.setForeground(Color.RED);
            JLabel Stellplatz_s = new JLabel("Logistische Einrichtung");


            mainPanel.add(Stellplatz_f);
            mainPanel.add(Stellplatz_b);
            mainPanel.add(Stellplatz_s);


            this.frame.setContentPane(mainPanel);
            this.frame.pack();
            this.frame.setSize(500, 150);
            this.frame.setVisible(true);

        }


}
