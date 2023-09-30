package app;

public class PlatzTransfer {
        private static PlatzTransfer instance;
        private String sharedData;

        private PlatzTransfer() {
            // Private constructor to prevent instantiation from outside
            sharedData = "";
        }

        public static PlatzTransfer getInstance() {
            if (instance == null) {
                instance = new PlatzTransfer();
            }
            return instance;
        }

        public String getSharedData() {
            return sharedData;
        }

        public void setSharedData(String data) {
            sharedData = data;
        }
    }

