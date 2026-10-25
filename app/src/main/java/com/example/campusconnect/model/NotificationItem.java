package com.example.campusconnect.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Data model representing a user notification.
 */
@Entity(tableName = "notifications")
public class NotificationItem {

    @PrimaryKey
    @NonNull
    private String id;
    private String title;
    private String message;
    private String type; // CLASS, ASSIGNMENT, COMPLAINT, ANNOUNCEMENT
    private String timestamp;
    private boolean isRead;

    public NotificationItem(String id, String title, String message, String type, String timestamp, boolean isRead) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.type = type;
        this.timestamp = timestamp;
        this.isRead = isRead;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public String getType() { return type; }
    public String getTimestamp() { return timestamp; }
    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }
}
