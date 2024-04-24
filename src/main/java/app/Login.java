package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login{
    private JFrame frame;


    public Login(MainGui mainGUI) {
        this.frame = new JFrame("Login");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        renderFrame(mainGUI);
    }

    private void renderFrame(MainGui mainGUI) {
        JPanel mainPanel = new JPanel(new GridLayout(4, 1));
        JPanel buttons = new JPanel(new BorderLayout());
        //add a Benutzer textfield
        //add a label which says Anmeldung
        JLabel anmeldung = new JLabel("Anmeldung");

        JTextField benutzer = new JTextField(20);
        JTextField passwort = new JTextField(20);
        String benutzer_platzhalter = "Benutzer";
        String passwort_platzhalter = "Passwort";
        // Platzhaltertext für das erste JTextField hinzufügen
        benutzer.setText(benutzer_platzhalter);
        benutzer.setForeground(Color.GRAY);

        // Platzhaltertext für das zweite JTextField hinzufügen
        passwort.setText(passwort_platzhalter);
        passwort.setForeground(Color.GRAY);

        // Event Listener, um den Platzhaltertext zu entfernen, wenn der Benutzer das Textfeld bearbeitet
        benutzer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (benutzer.getText().equals(benutzer_platzhalter)) {
                    benutzer.setText("");
                    benutzer.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (benutzer.getText().isEmpty()) {
                    benutzer.setText(benutzer_platzhalter);
                    benutzer.setForeground(Color.GRAY);
                }
            }
        });

        passwort.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (passwort.getText().equals(passwort_platzhalter)) {
                    passwort.setText("");
                    passwort.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (passwort.getText().isEmpty()) {
                    passwort.setText(passwort_platzhalter);
                    passwort.setForeground(Color.GRAY);
                }
            }
        });


        //add a submit- and cancel button
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

        cancel.setFocusable(true);

        buttons.add(submit, BorderLayout.WEST);
        buttons.add(cancel, BorderLayout.EAST);
        mainPanel.add(anmeldung);
        mainPanel.add(benutzer);
        mainPanel.add(passwort);
        mainPanel.add(buttons);
        this.frame.setContentPane(mainPanel);
        this.frame.pack();
        cancel.requestFocusInWindow();
        this.frame.setSize(500, 150);
        this.frame.setVisible(true);

    }
}
