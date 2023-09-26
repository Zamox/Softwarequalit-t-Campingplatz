package app;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PlatzGUIBuchungen {


        private JFrame frame;
        private DefaultTableModel infoTableModel;
        private JButton upperLeftButton;
        private JButton upperRightButton;
        private JButton lowerLeftButton;
        private JButton lowerRightButton;
        private int selectedRowIndex = -1;

        public PlatzGUIBuchungen() {
            this.frame = new JFrame();
            this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.frame.setLayout(new BorderLayout());
            renderFrame();
        }

        private void renderFrame() {
            // Hier können Sie den vorhandenen Code für die UI-Komponenten einfügen

            JPanel contentPanel = new JPanel(new BorderLayout());
            JLabel imageLabel = new JLabel();

            try {
                // Lade das Bild
                BufferedImage img = ImageIO.read(new File("./Campingplatz.jpg"));
                // mach das Bild transparent
                imageLabel.setOpaque(false);

                // Skaliere das Bild auf die gewünschte Größe
                int imageWidth = 600; // Ändere dies auf die gewünschte Breite
                int imageHeight = 450; // Ändere dies auf die gewünschte Höhe
                Image scaledImage = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);

                // Setze das skalierte Bild im JLabel
                imageLabel.setIcon(new ImageIcon(scaledImage));
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Fügen Sie einen Seitenabstand für das Bild hinzu (10 Pixel) - Ändern Sie dies nach Bedarf
            imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

            // Buttons hinzufügen und ActionListener anhängen
            upperLeftButton = new JButton();
            // Weitere Button-Initialisierungen hier

            // ActionListener für die Buttons hinzufügen
            upperLeftButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Aktion für den oberen linken Button hier
                }
            });

            contentPanel.add(imageLabel, BorderLayout.WEST);

            this.frame.add(contentPanel);
            this.frame.setSize(600,450);
            this.frame.setVisible(true);
        }


        /*public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> new PlatzGUIBuchungen());
        }*/
    }

