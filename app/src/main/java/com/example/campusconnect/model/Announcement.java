package com.example.campusconnect.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Data model representing an Official Campus Announcement.
 */
@Entity(tableName = "announcements")
public class Announcement {

    @PrimaryKey
    @NonNull
    private String id;
    private String title;
    private String content;
    private String category; // EXAM, GENERAL, HOLIDAY, URGENT
    private String author; // e.g. Dean Office, Exam Dept
    private String datePosted;

    public Announcement(String id, String title, String content, String category, String author, String datePosted) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.author = author;
        this.datePosted = datePosted;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getCategory() { return category; }
    public String getAuthor() { return author; }
    public String getDatePosted() { return datePosted; }
}
