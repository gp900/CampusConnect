package com.example.campusconnect.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Data model representing a student's assignment/homework.
 */
@Entity(tableName = "assignments")
public class Assignment {

    @PrimaryKey
    @NonNull
    private String id;
    private String title;
    private String subjectName;
    private String dueDate;
    private String priority; // HIGH, MEDIUM, LOW
    private boolean isCompleted;

    public Assignment(String id, String title, String subjectName, String dueDate, String priority, boolean isCompleted) {
        this.id = id;
        this.title = title;
        this.subjectName = subjectName;
        this.dueDate = dueDate;
        this.priority = priority;
        this.isCompleted = isCompleted;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
}
