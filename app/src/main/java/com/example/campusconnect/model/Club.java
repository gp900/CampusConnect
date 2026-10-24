package com.example.campusconnect.model;

/**
 * Data model representing a Campus Club.
 */
public class Club {

    private String id;
    private String name;
    private String category; // TECHNICAL, CULTURAL, SPORTS
    private String description;
    private String logoUrl;
    private String leadName;
    private String leadContact;
    private int memberCount;
    private boolean isMember;

    public Club(String id, String name, String category, String description, String logoUrl, String leadName, String leadContact, int memberCount, boolean isMember) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.logoUrl = logoUrl;
        this.leadName = leadName;
        this.leadContact = leadContact;
        this.memberCount = memberCount;
        this.isMember = isMember;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public String getLogoUrl() { return logoUrl; }
    public String getLeadName() { return leadName; }
    public String getLeadContact() { return leadContact; }
    public int getMemberCount() { return memberCount; }
    public boolean isMember() { return isMember; }
    public void setMember(boolean member) { isMember = member; }
    public void setMemberCount(int count) { memberCount = count; }
}
