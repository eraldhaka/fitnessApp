package org.fitnessapp.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    // The default result for the user logged out equal 1
    public static final int USER_LOGGED_OUT = -1;
    public static final String USER_ID = "user_id";
    public static final String USER_WALK = "user_walk";

    private static SharedPreferences sharedPreferences;

    public static void initSharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences("auth_app_fitness", Context.MODE_PRIVATE);
    }

    private static SharedPreferences getAuthCredentials() {
        return sharedPreferences;
    }

    private static SharedPreferences.Editor editSharedPrefs() {
        return getAuthCredentials().edit();
    }

    public static int getID(String key) {
        return sharedPreferences.getInt(key, USER_LOGGED_OUT);
    }

    public static boolean getUserWalk(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public static void setID(String key, int value) {
        editSharedPrefs().putInt(key, value).commit();
    }

    public static void setUserWalk(String key, boolean value) {
        editSharedPrefs().putBoolean(key, value).commit();
    }

    public static boolean isAuthorized() {
        return getID(USER_ID) != USER_LOGGED_OUT;
    }

    public static boolean isUserWalking() {
        return getUserWalk(USER_WALK);
    }
}
