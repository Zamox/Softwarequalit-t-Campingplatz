package app;

public class StellplaetzeInfo {
    private String platznummer;
    private String status;
    private String platzregion; // Platzregion hinzugefügt
    private String platzart;

    public StellplaetzeInfo(String platznummer, String status, String platzregion, String platzart) {
        this.platznummer = platznummer;
        this.status = status;
        this.platzregion = platzregion;
        this.platzart = platzart;
    }

    public String getPlatznummer() {
        return platznummer;
    }

    public void setPlatznummer(String platznummer) {
        this.platznummer = platznummer;
    }

    public String getStatus() {
        return status;
    }

    public String getPlatzregion() {
        return platzregion;
    }

    public void setPlatzregion(String platzregion) {
        this.platzregion = platzregion;
    }

    public String getPlatzart() {
        return platzart;
    }

    public void setPlatzart(String platzart) {
        this.platzart = platzart;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
