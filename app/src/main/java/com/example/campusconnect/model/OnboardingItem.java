package com.example.campusconnect.model;

/**
 * Model class representing a single slide item in the Onboarding ViewPager2.
 */
public class OnboardingItem {

    private final String iconEmoji;
    private final String title;
    private final String description;

    public OnboardingItem(String iconEmoji, String title, String description) {
        this.iconEmoji = iconEmoji;
        this.title = title;
        this.description = description;
    }

    public String getIconEmoji() {
        return iconEmoji;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
