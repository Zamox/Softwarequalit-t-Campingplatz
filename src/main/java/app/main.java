package app;

public class main {
    public static void main(String[] args) {
        try {
            //IOUtilities.openInJFrame(new JButton("Hello Welt"), 200, 400, "Button", true);
            // use Calender Component App
            new MainGui();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
