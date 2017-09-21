package api.vdp.visa.com.instagramapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by svuddara on 9/17/17.
 */

public class SharedPreferencesUtils {

    public static String getAccessTokenFromSharedPreferences(Context context,String key){
        SharedPreferences instagramPreferences = context.getSharedPreferences(OAuthConstants.PREFS_NAME, Context.MODE_PRIVATE);
        return instagramPreferences.getString(key,"");
    }

    public static void insertIntoSharedPreferences(Context context,String key,String value){
        SharedPreferences instagramPreferences = context.getSharedPreferences(OAuthConstants.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = instagramPreferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static void clearPreference(Context context){
        SharedPreferences instagramPreferences = context.getSharedPreferences(OAuthConstants.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = instagramPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
