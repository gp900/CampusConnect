package com.example.campusconnect.utils;

import android.util.Patterns;

/**
 * Utility methods for form input validation across Authentication screens.
 */
public class ValidationUtils {

    /**
     * Validates if the email string is non-empty and matches standard email format.
     */
    public static boolean isValidEmail(CharSequence email) {
        return email != null && email.length() > 0 && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Validates if password meets minimum length requirement (at least 6 characters).
     */
    public static boolean isValidPassword(String password) {
        return password != null && password.trim().length() >= 6;
    }

    /**
     * Validates if text string is non-empty.
     */
    public static boolean isNonEmpty(String text) {
        return text != null && !text.trim().isEmpty();
    }
}
