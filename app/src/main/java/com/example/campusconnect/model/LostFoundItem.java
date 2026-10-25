package com.example.campusconnect.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Data model representing a Lost or Found item.
 */
@Entity(tableName = "lost_found")
public class LostFoundItem {

    @PrimaryKey
    @NonNull
    private String id;
    private String title;
    private String description;
    private String category; // "LOST" or "FOUND"
    private String location;
    private String contactInfo;
    private String datePosted;
    private boolean isResolved;

    public LostFoundItem(String id, String title, String description, String category, String location, String contactInfo, String datePosted, boolean isResolved) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.location = location;
        this.contactInfo = contactInfo;
        this.datePosted = datePosted;
        this.isResolved = isResolved;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public String getLocation() { return location; }
    public String getContactInfo() { return contactInfo; }
    public String getDatePosted() { return datePosted; }
    public boolean isResolved() { return isResolved; }

    public void setResolved(boolean resolved) { isResolved = resolved; }
}
