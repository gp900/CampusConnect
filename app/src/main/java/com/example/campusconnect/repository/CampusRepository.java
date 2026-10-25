package com.example.campusconnect.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.campusconnect.model.Announcement;
import com.example.campusconnect.model.Club;
import com.example.campusconnect.model.Complaint;
import com.example.campusconnect.model.Event;
import com.example.campusconnect.model.LostFoundItem;
import com.example.campusconnect.utils.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CampusRepository {

    private static CampusRepository instance;
    private final CampusDao campusDao;
    private final ExecutorService executor;

    private CampusRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.campusDao = db.campusDao();
        this.executor = Executors.newSingleThreadExecutor();
        seedInitialData();
    }

    public static synchronized CampusRepository getInstance(Context context) {
        if (instance == null) {
            instance = new CampusRepository(context);
        }
        return instance;
    }

    private void seedInitialData() {
        executor.execute(() -> {
            // Seed Events
            if (campusDao.getFeaturedEvents().getValue() == null || campusDao.getFeaturedEvents().getValue().isEmpty()) {
                List<Event> defaultEvents = new ArrayList<>();
                defaultEvents.add(new Event("fe1", "Annual Tech Symposium", "Campus Connect Team", "Sep 15", "10:00 AM", "Main Auditorium", "The biggest tech event of the year.", 450, false, true));
                defaultEvents.add(new Event("fe2", "Campus Hackathon 2026", "Coding Club", "Oct 12", "08:00 AM", "Computer Science Lab", "48-hour intense coding competition.", 120, false, true));
                defaultEvents.add(new Event("ue1", "Guest Lecture: AI in Healthcare", "CS Department", "Sep 24", "02:00 PM - 04:00 PM", "Auditorium", "Explore the future of medical AI.", 65, false, false));
                defaultEvents.add(new Event("ue2", "Cultural Night", "Arts Club", "Sep 30", "07:00 PM - 10:00 PM", "Open Ground", "Dance, music, and food fest.", 800, false, false));
                defaultEvents.add(new Event("ue3", "Photography Workshop", "Photography Club", "Oct 05", "11:00 AM - 01:00 PM", "Room 405", "Master DSLR basics.", 45, false, false));
                campusDao.insertEvents(defaultEvents);
            }

            // Seed Lost & Found
            if (campusDao.getLostFoundItems().getValue() == null || campusDao.getLostFoundItems().getValue().isEmpty()) {
                List<LostFoundItem> defaultLostFound = new ArrayList<>();
                defaultLostFound.add(new LostFoundItem("lf1", "Blue Water Bottle", "Milton 1L bottle, slightly scratched", "LOST", "Library 2nd Floor", "9876543210", "Sep 20", false));
                defaultLostFound.add(new LostFoundItem("lf2", "Calculator Casio fx-991EX", "Found on a desk in Lab 4", "FOUND", "CS Lab 4", "Library Desk", "Sep 21", false));
                defaultLostFound.add(new LostFoundItem("lf3", "Black Leather Wallet", "Contains some cash and ID card", "LOST", "Cafeteria", "9123456789", "Sep 22", false));
                campusDao.insertLostFoundItems(defaultLostFound);
            }

            // Seed Clubs
            if (campusDao.getClubs().getValue() == null || campusDao.getClubs().getValue().isEmpty()) {
                List<Club> defaultClubs = new ArrayList<>();
                defaultClubs.add(new Club("c1", "Coding Club", "TECHNICAL", "A community for developers and tech enthusiasts.", "", "Sarah Connor", "9876543210", 120, false));
                defaultClubs.add(new Club("c2", "Robotics Society", "TECHNICAL", "Building the future of automation.", "", "John McCarthy", "9123456789", 45, true));
                defaultClubs.add(new Club("c3", "Arts & Drama Club", "CULTURAL", "Express yourself through theater.", "", "Alice Walker", "9871234560", 85, false));
                defaultClubs.add(new Club("c4", "Music Band", "CULTURAL", "Campus official music society.", "", "Bob Dylan", "9871112222", 30, false));
                defaultClubs.add(new Club("c5", "Football Team", "SPORTS", "The official campus football squad.", "", "Lionel Messi", "9873334444", 25, true));
                campusDao.insertClubs(defaultClubs);
            }

            // Seed Announcements
            if (campusDao.getAnnouncements().getValue() == null || campusDao.getAnnouncements().getValue().isEmpty()) {
                List<Announcement> defaultAnnouncements = new ArrayList<>();
                defaultAnnouncements.add(new Announcement("a1", "Mid-Semester Exam Schedule", "The examination schedule for all B.Tech branches has been published on the portal.", "EXAM", "Exam Dept", "Sep 24"));
                defaultAnnouncements.add(new Announcement("a2", "Library Maintenance Closure", "The central library will remain closed tomorrow for server maintenance.", "NOTICE", "Library Services", "Sep 23"));
                defaultAnnouncements.add(new Announcement("a3", "Heavy Rainfall Holiday", "Due to severe weather warnings, all physical classes are suspended tomorrow.", "HOLIDAY", "Dean Office", "Sep 22"));
                defaultAnnouncements.add(new Announcement("a4", "Fee Submission Deadline", "Last date to clear semester dues is this Friday to avoid late fees.", "URGENT", "Accounts Dept", "Sep 20"));
                campusDao.insertAnnouncements(defaultAnnouncements);
            }

            // Seed Complaints
            if (campusDao.getComplaints().getValue() == null || campusDao.getComplaints().getValue().isEmpty()) {
                List<Complaint> defaultComplaints = new ArrayList<>();
                defaultComplaints.add(new Complaint("CMP1042", "Wi-Fi Not Working in Hostel Block B", "WI-FI", "The internet connection has been completely dead since yesterday evening. Restarting routers didn't help.", "IN_REVIEW", "Sep 20"));
                defaultComplaints.add(new Complaint("CMP1043", "Broken projector in Room 302", "INFRASTRUCTURE", "The projector HDMI port is broken, making it difficult for presentations.", "SUBMITTED", "Sep 22"));
                defaultComplaints.add(new Complaint("CMP1040", "Missing Grades in Portal", "ACADEMICS", "My Mid-Term grades for OS are still not reflecting on the student portal.", "RESOLVED", "Sep 15"));
                campusDao.insertComplaints(defaultComplaints);
            }
        });
    }

    // ==========================================
    // EVENTS MODULE LOGIC
    // ==========================================

    public LiveData<Resource<List<Event>>> getFeaturedEvents() {
        MutableLiveData<Resource<List<Event>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());
        campusDao.getFeaturedEvents().observeForever(items -> {
            if (items != null) result.setValue(Resource.success(items));
        });
        return result;
    }

    public LiveData<Resource<List<Event>>> getUpcomingEvents() {
        MutableLiveData<Resource<List<Event>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());
        campusDao.getUpcomingEvents().observeForever(items -> {
            if (items != null) result.setValue(Resource.success(items));
        });
        return result;
    }

    public void toggleEventRsvp(String eventId, boolean isFeatured) {
        executor.execute(() -> {
            // Usually we'd fetch the single event, toggle, and update.
            // For now, assume a toggle is fired. We need the current values.
            // This is complex without a single query.
            // A small workaround: pass the new values from ViewModel.
            // We'll leave this empty for now, but in reality we'd do a DAO update.
        });
    }
    
    public void toggleEventRsvpObj(Event event) {
        executor.execute(() -> {
            boolean wasRsvped = event.isRsvped();
            campusDao.updateEventRsvp(event.getId(), !wasRsvped, wasRsvped ? event.getRsvpCount() - 1 : event.getRsvpCount() + 1);
        });
    }

    // ==========================================
    // LOST & FOUND MODULE LOGIC
    // ==========================================

    public LiveData<Resource<List<LostFoundItem>>> getLostFoundItems() {
        MutableLiveData<Resource<List<LostFoundItem>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());
        campusDao.getLostFoundItems().observeForever(items -> {
            if (items != null) result.setValue(Resource.success(items));
        });
        return result;
    }

    public void addLostFoundItem(LostFoundItem item) {
        executor.execute(() -> campusDao.insertLostFoundItem(item));
    }

    // ==========================================
    // CLUBS MODULE LOGIC
    // ==========================================

    public LiveData<Resource<List<Club>>> getClubs() {
        MutableLiveData<Resource<List<Club>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());
        campusDao.getClubs().observeForever(items -> {
            if (items != null) result.setValue(Resource.success(items));
        });
        return result;
    }

    public void toggleClubMembership(String clubId) {
        // Needs a fetch and update. 
    }
    
    public void toggleClubMembershipObj(Club club) {
        executor.execute(() -> {
            boolean wasMember = club.isMember();
            campusDao.updateClubMembership(club.getId(), !wasMember, wasMember ? club.getMemberCount() - 1 : club.getMemberCount() + 1);
        });
    }

    // ==========================================
    // ANNOUNCEMENTS MODULE LOGIC
    // ==========================================

    public LiveData<Resource<List<Announcement>>> getAnnouncements() {
        MutableLiveData<Resource<List<Announcement>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());
        campusDao.getAnnouncements().observeForever(items -> {
            if (items != null) result.setValue(Resource.success(items));
        });
        return result;
    }

    // ==========================================
    // COMPLAINTS MODULE LOGIC
    // ==========================================

    public LiveData<Resource<List<Complaint>>> getComplaints() {
        MutableLiveData<Resource<List<Complaint>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());
        campusDao.getComplaints().observeForever(items -> {
            if (items != null) result.setValue(Resource.success(items));
        });
        return result;
    }

    public void addComplaint(Complaint complaint) {
        executor.execute(() -> campusDao.insertComplaint(complaint));
    }
}
