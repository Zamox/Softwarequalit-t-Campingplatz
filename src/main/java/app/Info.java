package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Info {
        private JFrame frame;


        public Info() {
            this.frame = new JFrame("Info");
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
            Stellplatz_s.setForeground(Color.BLUE);

            this.frame.setContentPane(mainPanel);
            this.frame.pack();
            this.frame.setSize(500, 150);
            this.frame.setVisible(true);

        }


}
