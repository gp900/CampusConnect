package com.example.campusconnect.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Utility class to manage SharedPreferences for Campus Connect.
 * Stores lightweight key-value flags such as first-run / onboarding status.
 */
public class SharedPrefManager {

    private static final String PREF_NAME = "campus_connect_prefs";
    private static final String KEY_IS_FIRST_RUN = "is_first_run";
    private static SharedPrefManager instance;
    private final SharedPreferences sharedPreferences;

    private SharedPrefManager(Context context) {
        sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    /**
     * Checks if this is the user's first launch of the app.
     * @return true if first run (default), false otherwise.
     */
    public boolean isFirstRun() {
        return sharedPreferences.getBoolean(KEY_IS_FIRST_RUN, true);
    }

    /**
     * Updates the first run status. Set to false once onboarding is completed/skipped.
     * @param isFirstRun false when onboarding is complete.
     */
    public void setFirstRun(boolean isFirstRun) {
        sharedPreferences.edit().putBoolean(KEY_IS_FIRST_RUN, isFirstRun).apply();
    }
}
