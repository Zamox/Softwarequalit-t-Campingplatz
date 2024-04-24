package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVHelper {



        public static boolean isPlatzNummerInCSV(String platzNummer, String csvFilePath) {
            try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(","); // Annahme: CSV ist kommagetrennt

                    if (parts.length > 4) { // Überprüfen Sie, ob die Zeile genügend Spalten hat
                        String csvPlatzNummer = parts[5].trim(); // Ändern Sie den Index entsprechend

                        // Überprüfen Sie, ob die Platznummer übereinstimmt
                        if (csvPlatzNummer.equals(platzNummer)) {
                            return true;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }


}
