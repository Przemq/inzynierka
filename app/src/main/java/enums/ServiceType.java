package enums;


public enum ServiceType {
    GET_CONFIGURATION,
    DOWNLOAD_FILE;

    public static final String SERVER_PATH = "http://www.przem94.ayz.pl/dijkstra/";

    public static String getURL(ServiceType serviceType){
        switch(serviceType){

            case GET_CONFIGURATION:
                return SERVER_PATH + "configuration.txt";
            case DOWNLOAD_FILE:
                return SERVER_PATH + "uploads/";
        }
        return "Service path is invalid";
    }
}
