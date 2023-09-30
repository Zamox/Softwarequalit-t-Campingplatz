package app;



    public class StellplaetzeInfo {
        private String platznummer;
        private String status;

        public StellplaetzeInfo(String platznummer, String status) {
            this.platznummer = platznummer;
            this.status = status;
        }

        public String getPlatznummer() {
            return platznummer;
        }

        public String getStatus() {
            return status;
        }
    }

