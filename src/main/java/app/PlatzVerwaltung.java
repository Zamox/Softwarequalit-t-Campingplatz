package app;

import app.Platz;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PlatzVerwaltung {

    private List<Platz> plaetze;
    private static final String CSV_FILE_PATH = "./Platzdaten.csv";
    private String dateiPfad = "./BuchungsCSV.csv";

    public PlatzVerwaltung() {

    }

    private boolean platzIstGebucht(int platzNummer, String dateiPfad) {
        try {
            for (String line : Files.readAllLines(Paths.get(dateiPfad))) {
                String[] parts = line.split(",");
                String csvNumber = parts[4].trim(); // Annahme: Die Platznummer steht in der 1. Spalte

                if (csvNumber.equals(String.valueOf(platzNummer))) {
                    return true; // Platz ist gebucht
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Platz ist nicht gebucht
    }

    public void aktualisierePlatzDaten(int platzNummer) {
        try {
            // Lese die bestehenden Platzdaten ein
            List<String> existingData = Files.readAllLines(Paths.get(CSV_FILE_PATH));

            // Überprüfe, ob der Platz in der Buchungsliste steht
            boolean platzGebucht = platzIstGebucht(platzNummer, dateiPfad);
            String status = platzGebucht ? "belegt" : "frei";

            // Aktualisiere den Platzstatus in den vorhandenen Daten

            aktualisierePlatzStatus(existingData, platzNummer, status);
            // Schreibe alle Platzdaten zurück in die CSV-Datei

            writePlatzDaten(existingData);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void aktualisierePlatzStatus(List<String> existingData, int platzNummer, String status) {
        // Suche nach dem Platz in den vorhandenen Daten
        boolean platzGefunden = false;
        for (int i = 1; i < existingData.size(); i++) { // Beginne bei 1, um die Header-Zeile zu überspringen
            String[] parts = existingData.get(i).split(",");
            int existingPlatzNummer = Integer.parseInt(parts[0].trim());

            // Wenn der Platz gefunden wird, aktualisiere den Status
            if (existingPlatzNummer == platzNummer) {
                parts[1] = status; // Index 1 entspricht dem Status in der CSV-Datei
                platzGefunden = true;
                break;
            }
        }

        // Wenn der Platz nicht gefunden wurde, füge einen neuen Eintrag hinzu
        if (!platzGefunden) {
            String neueDaten = platzNummer + "," + status + "," + "Nord-West";
            existingData.add(neueDaten);
        }
    }



    private void writePlatzDaten(List<String> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
