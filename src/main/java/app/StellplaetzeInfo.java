package app;



    public class StellplaetzeInfo {
        private String platznummer;
        private String status;

        private String platzregion;

        public StellplaetzeInfo(String platznummer, String status, String platzregion) {
            this.platznummer = platznummer;
            this.status = status;
            this.platzregion = platzregion;
        }

        public String getPlatznummer() {
            return platznummer;
        }

        public String getStatus() {
            return status;
        }

        public String getPlatzregion() {
            return platzregion;
        }
    }

