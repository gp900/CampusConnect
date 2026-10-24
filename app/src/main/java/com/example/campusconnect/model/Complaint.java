package com.example.campusconnect.model;

/**
 * Data model representing a Student Complaint or Grievance.
 */
public class Complaint {

    private String id;
    private String subject;
    private String category; // HOSTEL, ACADEMICS, INFRASTRUCTURE, WI-FI
    private String description;
    private String status; // SUBMITTED, IN_REVIEW, RESOLVED
    private String dateSubmitted;

    public Complaint(String id, String subject, String category, String description, String status, String dateSubmitted) {
        this.id = id;
        this.subject = subject;
        this.category = category;
        this.description = description;
        this.status = status;
        this.dateSubmitted = dateSubmitted;
    }

    public String getId() { return id; }
    public String getSubject() { return subject; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public String getDateSubmitted() { return dateSubmitted; }
}
