package com.example.campusconnect.model;

/**
 * Data model representing a Student User in Campus Connect.
 */
public class User {

    private String userId;
    private String fullName;
    private String email;
    private String department;
    private int semester;
    private String rollNumber;
    private String profileImageUrl;

    public User() {
        // Required empty constructor for Firestore deserialization
    }

    public User(String userId, String fullName, String email, String department, int semester, String rollNumber) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.department = department;
        this.semester = semester;
        this.rollNumber = rollNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
