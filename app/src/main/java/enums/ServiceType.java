package enums;


public enum ServiceType {
    GET_DATABASE,
    CHECK_VERSION,
    ADD_FICHE;

    public static final String SERVER_PATH = "http://www.datastore.waw.pl/Fiche2016/";

    public static String getURL(ServiceType serviceType){
        switch(serviceType){

            case GET_DATABASE:
                return SERVER_PATH + "getDatabase.php";
            case CHECK_VERSION:
                return SERVER_PATH + "checkVersion.php";
            case ADD_FICHE:
                return SERVER_PATH + "addFiche.php";
        }
        return "Service path is invalid";
    }
}
