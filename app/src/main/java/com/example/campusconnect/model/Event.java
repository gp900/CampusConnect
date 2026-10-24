package com.example.campusconnect.model;

/**
 * Data model representing a Campus Event.
 */
public class Event {

    private String id;
    private String title;
    private String organizer;
    private String date; // e.g. "Sep 15"
    private String time; // e.g. "10:00 AM - 04:00 PM"
    private String venue;
    private String description;
    private int rsvpCount;
    private boolean isRsvped;
    private boolean isFeatured;

    public Event(String id, String title, String organizer, String date, String time, String venue, String description, int rsvpCount, boolean isRsvped, boolean isFeatured) {
        this.id = id;
        this.title = title;
        this.organizer = organizer;
        this.date = date;
        this.time = time;
        this.venue = venue;
        this.description = description;
        this.rsvpCount = rsvpCount;
        this.isRsvped = isRsvped;
        this.isFeatured = isFeatured;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getOrganizer() { return organizer; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getVenue() { return venue; }
    public String getDescription() { return description; }
    public int getRsvpCount() { return rsvpCount; }
    public boolean isRsvped() { return isRsvped; }
    public boolean isFeatured() { return isFeatured; }

    public void setRsvped(boolean rsvped) { isRsvped = rsvped; }
    public void setRsvpCount(int rsvpCount) { this.rsvpCount = rsvpCount; }
}
