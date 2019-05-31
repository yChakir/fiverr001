package fiverr.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConstants {

    /**
     * API prefix
     */
    public static final String API = "api";

    /**
     * API version 1
     */
    public static final String V1 = "v1";

    /**
     * Register end point path
     */
    public static final String REGISTER = API + "/" + V1 + "/register";

    /**
     * Profile end point path
     */
    public static final String PROFILE = API + "/" + V1 + "/profile";

    @Value("${app.front-end.path}")
    public static String FE_PATH;

    @Value("${app.front-end.routes}")
    public static String FE_ROUTES;
}
