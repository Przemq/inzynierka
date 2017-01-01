package enums;


public enum ServiceType {
    GET_CONFIGURATION,
    CHECK_VERSION,
    DOWNLOAD_FILE;

    public static final String SERVER_PATH = "http://www.przem94.ayz.pl/dijkstra/";

    public static String getURL(ServiceType serviceType){
        switch(serviceType){

            case GET_CONFIGURATION:
                return SERVER_PATH + "configuration.txt";
            case CHECK_VERSION:
                return SERVER_PATH + "checkVersion.php";
            case DOWNLOAD_FILE:
                return SERVER_PATH + "uploads/";
        }
        return "Service path is invalid";
    }
}
