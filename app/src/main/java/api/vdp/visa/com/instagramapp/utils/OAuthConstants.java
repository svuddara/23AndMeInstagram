package api.vdp.visa.com.instagramapp.utils;

/**
 * Created by svuddara on 9/16/17.
 */

public interface OAuthConstants {
    public static final String ACCESS_TOKEN = "access_token";
    public static final String PREFS_NAME = "InstagramPreferences";
    public static final String REDIRECT_URL = "https://www.23andme.com";
    /**
     * This should be in Android manifest as a property. putting this here because of time
     */
    public static final String CLIENT_ID = "0637825256de4d9e9c969ec594b032c8";
    public static final String AUTH_URL = "https://api.instagram.com/oauth/authorize/?client_id="+CLIENT_ID+"&redirect_uri="+REDIRECT_URL+"&response_type=token";
    public static final String AUTH_SUCCESS_URL = "https://www.23andme.com/#access_token=";
}
