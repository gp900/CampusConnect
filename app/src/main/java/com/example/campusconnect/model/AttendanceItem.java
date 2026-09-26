package com.example.campusconnect.model;

/**
 * Model class representing a single subject's attendance record.
 */
public class AttendanceItem {

    private String id;
    private String subjectCode;
    private String subjectName;
    private int classesAttended;
    private int totalClasses;

    public AttendanceItem(String id, String subjectCode, String subjectName, int classesAttended, int totalClasses) {
        this.id = id;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.classesAttended = classesAttended;
        this.totalClasses = totalClasses;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public int getClassesAttended() { return classesAttended; }
    public void setClassesAttended(int classesAttended) { this.classesAttended = classesAttended; }

    public int getTotalClasses() { return totalClasses; }
    public void setTotalClasses(int totalClasses) { this.totalClasses = totalClasses; }

    /**
     * Calculates the attendance percentage.
     */
    public int getPercentage() {
        if (totalClasses == 0) return 0;
        return (int) (((float) classesAttended / totalClasses) * 100);
    }

    /**
     * Determines if the student is in the safe zone (>= 75%).
     */
    public boolean isSafe() {
        return getPercentage() >= 75;
    }
}
