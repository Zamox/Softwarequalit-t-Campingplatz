package app;

public class PlatzTransfer {
        private static PlatzTransfer instance;
        private String sharedPlatzNummer;
        private int ButtonCounter;

        private PlatzTransfer() {
            // Private constructor to prevent instantiation from outside
            sharedPlatzNummer = "";
        }

        public static PlatzTransfer getInstance() {
            if (instance == null) {
                instance = new PlatzTransfer();
            }
            return instance;
        }

        public String getSharedData() {
            return sharedPlatzNummer;
        }

        public void setSharedData(String data) {
            sharedPlatzNummer = data.substring(6);
        }
    }

