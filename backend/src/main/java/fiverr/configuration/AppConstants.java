package fiverr.configuration;

public interface AppConstants {

    /**
     * API prefix
     */
    String API = "api";

    /**
     * API version 1
     */
    String V1 = "v1";

    /**
     * Register end point path
     */
    String REGISTER = API + "/" + V1 + "/register";

    /**
     * Profile end point path
     */
    String PROFILE = API + "/" + V1 + "/profile";
}
