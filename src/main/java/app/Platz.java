package app;


public class Platz {
    private int platzNummer;
    private String status;
    private String platzRegion;

    public Platz(int platzNummer, String status, String platzRegion) {
        this.platzNummer = platzNummer;
        this.status = status;
        this.platzRegion = platzRegion;
    }

    public int getPlatzNummer() {
        return platzNummer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlatzRegion() {
        return platzRegion;
    }

    // Weitere Methoden, falls benötigt
}

