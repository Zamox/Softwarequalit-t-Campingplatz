package app;

import javax.swing.*;
import java.awt.*;

class Login {
    private JFrame frame;

    public Login() {
        this.frame = new JFrame("Login");
        this.frame.setSize(500, 100);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        renderFrame();
    }

    private void renderFrame() {
        JPanel mainPanel = new JPanel(new GridLayout(4, 1));
        JPanel buttons = new JPanel(new BorderLayout());
        //add a Benutzer textfield
        //add a label which says Anmeldung
        JLabel anmeldung = new JLabel("Anmeldung");
        JTextField benutzer = new JTextField("Benutzer");
        //add a passwort textfield
        //add a submit and cancel button
        JButton submit = new JButton("Submit");
        JButton cancel = new JButton("Cancel");
        JTextField passwort = new JTextField("Passwort");
        buttons.add(submit, BorderLayout.WEST);
        buttons.add(cancel, BorderLayout.EAST);
        mainPanel.add(anmeldung);
        mainPanel.add(benutzer);
        mainPanel.add(passwort);
        mainPanel.add(buttons);

        this.frame.setContentPane(mainPanel);
        this.frame.pack();
        this.frame.setVisible(true);
    }
}