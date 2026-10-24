package com.example.campusconnect.repository;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.campusconnect.model.Club;
import com.example.campusconnect.model.Event;
import com.example.campusconnect.model.LostFoundItem;
import com.example.campusconnect.utils.Resource;

import java.util.ArrayList;
import java.util.List;

public class CampusRepository {

    private static CampusRepository instance;

    private CampusRepository() {}

    public static synchronized CampusRepository getInstance() {
        if (instance == null) {
            instance = new CampusRepository();
        }
        return instance;
    }

    private List<Event> mockFeaturedEvents;
    private List<Event> mockUpcomingEvents;

    public LiveData<Resource<List<Event>>> getFeaturedEvents() {
        MutableLiveData<Resource<List<Event>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (mockFeaturedEvents == null) {
                mockFeaturedEvents = new ArrayList<>();
                mockFeaturedEvents.add(new Event("fe1", "Annual Tech Symposium", "Campus Connect Team", "Sep 15", "10:00 AM", "Main Auditorium", "The biggest tech event of the year.", 450, false, true));
                mockFeaturedEvents.add(new Event("fe2", "Campus Hackathon 2026", "Coding Club", "Oct 12", "08:00 AM", "Computer Science Lab", "48-hour intense coding competition.", 120, false, true));
            }
            result.setValue(Resource.success(new ArrayList<>(mockFeaturedEvents)));
        }, 300);

        return result;
    }

    public LiveData<Resource<List<Event>>> getUpcomingEvents() {
        MutableLiveData<Resource<List<Event>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (mockUpcomingEvents == null) {
                mockUpcomingEvents = new ArrayList<>();
                mockUpcomingEvents.add(new Event("ue1", "Guest Lecture: AI in Healthcare", "CS Department", "Sep 24", "02:00 PM - 04:00 PM", "Auditorium", "Explore the future of medical AI.", 65, false, false));
                mockUpcomingEvents.add(new Event("ue2", "Cultural Night", "Arts Club", "Sep 30", "07:00 PM - 10:00 PM", "Open Ground", "Dance, music, and food fest.", 800, false, false));
                mockUpcomingEvents.add(new Event("ue3", "Photography Workshop", "Photography Club", "Oct 05", "11:00 AM - 01:00 PM", "Room 405", "Master DSLR basics.", 45, false, false));
            }
            result.setValue(Resource.success(new ArrayList<>(mockUpcomingEvents)));
        }, 300);

        return result;
    }

    public void toggleEventRsvp(String eventId, boolean isFeatured) {
        List<Event> targetList = isFeatured ? mockFeaturedEvents : mockUpcomingEvents;
        if (targetList == null) return;

        for (Event event : targetList) {
            if (java.util.Objects.equals(event.getId(), eventId)) {
                boolean wasRsvped = event.isRsvped();
                event.setRsvped(!wasRsvped);
                event.setRsvpCount(wasRsvped ? event.getRsvpCount() - 1 : event.getRsvpCount() + 1);
                break;
            }
        }
    }

    // ==========================================
    // LOST & FOUND MODULE LOGIC
    // ==========================================
    private List<LostFoundItem> mockLostFoundList;

    public LiveData<Resource<List<LostFoundItem>>> getLostFoundItems() {
        MutableLiveData<Resource<List<LostFoundItem>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (mockLostFoundList == null) {
                mockLostFoundList = new ArrayList<>();
                mockLostFoundList.add(new LostFoundItem("lf1", "Blue Water Bottle", "Milton 1L bottle, slightly scratched", "LOST", "Library 2nd Floor", "9876543210", "Sep 20", false));
                mockLostFoundList.add(new LostFoundItem("lf2", "Calculator Casio fx-991EX", "Found on a desk in Lab 4", "FOUND", "CS Lab 4", "Library Desk", "Sep 21", false));
                mockLostFoundList.add(new LostFoundItem("lf3", "Black Leather Wallet", "Contains some cash and ID card", "LOST", "Cafeteria", "9123456789", "Sep 22", false));
            }
            result.setValue(Resource.success(new ArrayList<>(mockLostFoundList)));
        }, 300);

        return result;
    }

    public void addLostFoundItem(LostFoundItem item) {
        if (mockLostFoundList == null) {
            mockLostFoundList = new ArrayList<>();
        }
        mockLostFoundList.add(0, item);
    }

    // ==========================================
    // CLUBS MODULE LOGIC
    // ==========================================
    private List<Club> mockClubsList;

    public LiveData<Resource<List<Club>>> getClubs() {
        MutableLiveData<Resource<List<Club>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (mockClubsList == null) {
                mockClubsList = new ArrayList<>();
                mockClubsList.add(new Club("c1", "Coding Club", "TECHNICAL", "A community for developers and tech enthusiasts.", "", "Sarah Connor", "9876543210", 120, false));
                mockClubsList.add(new Club("c2", "Robotics Society", "TECHNICAL", "Building the future of automation.", "", "John McCarthy", "9123456789", 45, true));
                mockClubsList.add(new Club("c3", "Arts & Drama Club", "CULTURAL", "Express yourself through theater.", "", "Alice Walker", "9871234560", 85, false));
                mockClubsList.add(new Club("c4", "Music Band", "CULTURAL", "Campus official music society.", "", "Bob Dylan", "9871112222", 30, false));
                mockClubsList.add(new Club("c5", "Football Team", "SPORTS", "The official campus football squad.", "", "Lionel Messi", "9873334444", 25, true));
            }
            result.setValue(Resource.success(new ArrayList<>(mockClubsList)));
        }, 300);

        return result;
    }

    public void toggleClubMembership(String clubId) {
        if (mockClubsList == null) return;
        for (Club club : mockClubsList) {
            if (java.util.Objects.equals(club.getId(), clubId)) {
                boolean wasMember = club.isMember();
                club.setMember(!wasMember);
                club.setMemberCount(wasMember ? club.getMemberCount() - 1 : club.getMemberCount() + 1);
                break;
            }
        }
    }

    // ==========================================
    // ANNOUNCEMENTS MODULE LOGIC
    // ==========================================
    private List<com.example.campusconnect.model.Announcement> mockAnnouncementsList;

    public LiveData<Resource<List<com.example.campusconnect.model.Announcement>>> getAnnouncements() {
        MutableLiveData<Resource<List<com.example.campusconnect.model.Announcement>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (mockAnnouncementsList == null) {
                mockAnnouncementsList = new ArrayList<>();
                mockAnnouncementsList.add(new com.example.campusconnect.model.Announcement("a1", "Mid-Semester Exam Schedule", "The examination schedule for all B.Tech branches has been published on the portal.", "EXAM", "Exam Dept", "Sep 24"));
                mockAnnouncementsList.add(new com.example.campusconnect.model.Announcement("a2", "Library Maintenance Closure", "The central library will remain closed tomorrow for server maintenance.", "NOTICE", "Library Services", "Sep 23"));
                mockAnnouncementsList.add(new com.example.campusconnect.model.Announcement("a3", "Heavy Rainfall Holiday", "Due to severe weather warnings, all physical classes are suspended tomorrow.", "HOLIDAY", "Dean Office", "Sep 22"));
                mockAnnouncementsList.add(new com.example.campusconnect.model.Announcement("a4", "Fee Submission Deadline", "Last date to clear semester dues is this Friday to avoid late fees.", "URGENT", "Accounts Dept", "Sep 20"));
            }
            result.setValue(Resource.success(new ArrayList<>(mockAnnouncementsList)));
        }, 300);

        return result;
    }

    // ==========================================
    // COMPLAINTS MODULE LOGIC
    // ==========================================
    private List<com.example.campusconnect.model.Complaint> mockComplaintsList;

    public LiveData<Resource<List<com.example.campusconnect.model.Complaint>>> getComplaints() {
        MutableLiveData<Resource<List<com.example.campusconnect.model.Complaint>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (mockComplaintsList == null) {
                mockComplaintsList = new ArrayList<>();
                mockComplaintsList.add(new com.example.campusconnect.model.Complaint("CMP1042", "Wi-Fi Not Working in Hostel Block B", "WI-FI", "The internet connection has been completely dead since yesterday evening. Restarting routers didn't help.", "IN_REVIEW", "Sep 20"));
                mockComplaintsList.add(new com.example.campusconnect.model.Complaint("CMP1043", "Broken projector in Room 302", "INFRASTRUCTURE", "The projector HDMI port is broken, making it difficult for presentations.", "SUBMITTED", "Sep 22"));
                mockComplaintsList.add(new com.example.campusconnect.model.Complaint("CMP1040", "Missing Grades in Portal", "ACADEMICS", "My Mid-Term grades for OS are still not reflecting on the student portal.", "RESOLVED", "Sep 15"));
            }
            result.setValue(Resource.success(new ArrayList<>(mockComplaintsList)));
        }, 300);

        return result;
    }

    public void addComplaint(com.example.campusconnect.model.Complaint complaint) {
        if (mockComplaintsList == null) {
            mockComplaintsList = new ArrayList<>();
        }
        mockComplaintsList.add(0, complaint);
    }
}
