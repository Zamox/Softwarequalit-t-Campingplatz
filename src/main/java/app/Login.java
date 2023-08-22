package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JFrame frame;


    public Login(MainGui mainGUI) {
        this.frame = new JFrame("Login");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        renderFrame(mainGUI);
    }

    private void renderFrame(MainGui mainGUI) {
        JPanel mainPanel = new JPanel(new GridLayout(4, 1));
        JPanel buttons = new JPanel(new BorderLayout());
        //add a Benutzer textfield
        //add a label which says Anmeldung
        JLabel anmeldung = new JLabel("Anmeldung");
        JTextField benutzer = new JTextField("Benutzer");
        //add a passwort textfield
        //add a submit and cancel button
        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGUI.enable();
                frame.dispose();
            }
        });
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        JTextField passwort = new JTextField("Passwort");
        buttons.add(submit, BorderLayout.WEST);
        buttons.add(cancel, BorderLayout.EAST);
        mainPanel.add(anmeldung);
        mainPanel.add(benutzer);
        mainPanel.add(passwort);
        mainPanel.add(buttons);
        this.frame.setContentPane(mainPanel);
        this.frame.pack();
        this.frame.setSize(500, 150);
        this.frame.setVisible(true);
    }



}
