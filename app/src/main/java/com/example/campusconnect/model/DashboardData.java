package com.example.campusconnect.model;

/**
 * Data model encapsulating student dashboard statistics and upcoming activities.
 */
public class DashboardData {

    private final String studentName;
    private final String degreeAndDept;
    private final String nextSubject;
    private final String nextTimeAndRoom;
    private final String nextProfessor;
    private final String startsIn;
    private final int attendancePercentage;
    private final String attendanceStatus;
    private final int pendingAssignments;
    private final int highPriorityAssignments;
    private final String featuredEventTitle;
    private final String featuredEventDetails;

    public DashboardData(String studentName, String degreeAndDept, String nextSubject,
                         String nextTimeAndRoom, String nextProfessor, String startsIn,
                         int attendancePercentage, String attendanceStatus,
                         int pendingAssignments, int highPriorityAssignments,
                         String featuredEventTitle, String featuredEventDetails) {
        this.studentName = studentName;
        this.degreeAndDept = degreeAndDept;
        this.nextSubject = nextSubject;
        this.nextTimeAndRoom = nextTimeAndRoom;
        this.nextProfessor = nextProfessor;
        this.startsIn = startsIn;
        this.attendancePercentage = attendancePercentage;
        this.attendanceStatus = attendanceStatus;
        this.pendingAssignments = pendingAssignments;
        this.highPriorityAssignments = highPriorityAssignments;
        this.featuredEventTitle = featuredEventTitle;
        this.featuredEventDetails = featuredEventDetails;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getDegreeAndDept() {
        return degreeAndDept;
    }

    public String getNextSubject() {
        return nextSubject;
    }

    public String getNextTimeAndRoom() {
        return nextTimeAndRoom;
    }

    public String getNextProfessor() {
        return nextProfessor;
    }

    public String getStartsIn() {
        return startsIn;
    }

    public int getAttendancePercentage() {
        return attendancePercentage;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public int getPendingAssignments() {
        return pendingAssignments;
    }

    public int getHighPriorityAssignments() {
        return highPriorityAssignments;
    }

    public String getFeaturedEventTitle() {
        return featuredEventTitle;
    }

    public String getFeaturedEventDetails() {
        return featuredEventDetails;
    }
}
