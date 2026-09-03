package com.example.campusconnect.model;

/**
 * Model class representing a single lecture entry in the student timetable.
 */
public class TimetableItem {

    private String timetableId;
    private String dayOfWeek; // e.g. MONDAY, TUESDAY
    private String subjectCode; // e.g. CS-301
    private String subjectName; // e.g. Data Structures & Algorithms
    private String professorName; // e.g. Dr. Robert Vance
    private String roomNumber; // e.g. Room 302
    private String startTime; // e.g. 09:00 AM
    private String endTime; // e.g. 10:00 AM
    private String status; // ONGOING, UPCOMING, COMPLETED

    public TimetableItem() {
    }

    public TimetableItem(String timetableId, String dayOfWeek, String subjectCode, String subjectName,
                         String professorName, String roomNumber, String startTime, String endTime, String status) {
        this.timetableId = timetableId;
        this.dayOfWeek = dayOfWeek;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.professorName = professorName;
        this.roomNumber = roomNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public String getTimetableId() {
        return timetableId;
    }

    public void setTimetableId(String timetableId) {
        this.timetableId = timetableId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
