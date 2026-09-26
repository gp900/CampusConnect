package com.example.campusconnect.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.campusconnect.model.User;

/**
 * Utility class to manage SharedPreferences for Campus Connect.
 * Stores lightweight key-value flags (onboarding status) and persistent user session data.
 */
public class SharedPrefManager {

    private static final String PREF_NAME = "campus_connect_prefs";
    private static final String KEY_IS_FIRST_RUN = "is_first_run";

    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_COURSE = "user_course";
    private static final String KEY_USER_DEPT = "user_dept";
    private static final String KEY_USER_SEM = "user_sem";
    private static final String KEY_USER_ROLL = "user_roll";

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

    public boolean isFirstRun() {
        return sharedPreferences.getBoolean(KEY_IS_FIRST_RUN, true);
    }

    public void setFirstRun(boolean isFirstRun) {
        sharedPreferences.edit().putBoolean(KEY_IS_FIRST_RUN, isFirstRun).apply();
    }

    /**
     * Saves the logged-in or registered user details into persistent session storage.
     */
    public void saveUserSession(User user) {
        if (user == null) return;
        sharedPreferences.edit()
                .putString(KEY_USER_ID, user.getUserId())
                .putString(KEY_USER_NAME, user.getFullName())
                .putString(KEY_USER_EMAIL, user.getEmail())
                .putString(KEY_USER_COURSE, user.getCourse())
                .putString(KEY_USER_DEPT, user.getDepartment())
                .putInt(KEY_USER_SEM, user.getSemester())
                .putString(KEY_USER_ROLL, user.getRollNumber())
                .apply();
    }

    /**
     * Retrieves the currently saved user session from persistent storage.
     * @return User instance or null if no user is logged in.
     */
    public User getUserSession() {
        String userId = sharedPreferences.getString(KEY_USER_ID, null);
        if (userId == null) return null;

        String name = sharedPreferences.getString(KEY_USER_NAME, "Student");
        String email = sharedPreferences.getString(KEY_USER_EMAIL, "");
        String course = sharedPreferences.getString(KEY_USER_COURSE, "B.Tech");
        String dept = sharedPreferences.getString(KEY_USER_DEPT, "Computer Science");
        int sem = sharedPreferences.getInt(KEY_USER_SEM, 1);
        String roll = sharedPreferences.getString(KEY_USER_ROLL, "");

        return new User(userId, name, email, course, dept, sem, roll);
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getString(KEY_USER_ID, null) != null;
    }

    public void clearUserSession() {
        sharedPreferences.edit()
                .remove(KEY_USER_ID)
                .remove(KEY_USER_NAME)
                .remove(KEY_USER_EMAIL)
                .remove(KEY_USER_COURSE)
                .remove(KEY_USER_DEPT)
                .remove(KEY_USER_SEM)
                .remove(KEY_USER_ROLL)
                .apply();
    }
}
