package app;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class main {
    public static void main(String[] args) {
        try {
            //IOUtilities.openInJFrame(new JButton("Hello Welt"), 200, 400, "Button", true);
            // use Calender Component App
            MainGui mainGUI = new MainGui();
            mainGUI.loginBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new Login(mainGUI);
                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
