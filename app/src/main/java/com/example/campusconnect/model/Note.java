package com.example.campusconnect.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Data model representing a shared study note.
 */
@Entity(tableName = "notes")
public class Note {
    @PrimaryKey
    @NonNull
    private String id;
    private String title;
    private String subjectName;
    private String uploaderName;
    private String fileType; // PDF, IMG
    private int downloadsCount;
    private String timestamp;

    public Note(String id, String title, String subjectName, String uploaderName, String fileType, int downloadsCount, String timestamp) {
        this.id = id;
        this.title = title;
        this.subjectName = subjectName;
        this.uploaderName = uploaderName;
        this.fileType = fileType;
        this.downloadsCount = downloadsCount;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getUploaderName() { return uploaderName; }
    public void setUploaderName(String uploaderName) { this.uploaderName = uploaderName; }

    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }

    public int getDownloadsCount() { return downloadsCount; }
    public void setDownloadsCount(int downloadsCount) { this.downloadsCount = downloadsCount; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
