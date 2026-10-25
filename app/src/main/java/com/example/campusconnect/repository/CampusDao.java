package com.example.campusconnect.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.campusconnect.model.Announcement;
import com.example.campusconnect.model.Club;
import com.example.campusconnect.model.Complaint;
import com.example.campusconnect.model.Event;
import com.example.campusconnect.model.LostFoundItem;

import java.util.List;

@Dao
public interface CampusDao {

    // Events
    @Query("SELECT * FROM events WHERE isFeatured = 1")
    LiveData<List<Event>> getFeaturedEvents();

    @Query("SELECT * FROM events WHERE isFeatured = 0")
    LiveData<List<Event>> getUpcomingEvents();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEvents(List<Event> events);

    @Query("UPDATE events SET isRsvped = :isRsvped, rsvpCount = :rsvpCount WHERE id = :id")
    void updateEventRsvp(String id, boolean isRsvped, int rsvpCount);

    @Query("DELETE FROM events")
    void clearEvents();

    // Lost & Found
    @Query("SELECT * FROM lost_found")
    LiveData<List<LostFoundItem>> getLostFoundItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLostFoundItem(LostFoundItem item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLostFoundItems(List<LostFoundItem> items);

    @Query("DELETE FROM lost_found")
    void clearLostFoundItems();

    // Clubs
    @Query("SELECT * FROM clubs")
    LiveData<List<Club>> getClubs();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertClubs(List<Club> clubs);

    @Query("UPDATE clubs SET isMember = :isMember, memberCount = :memberCount WHERE id = :id")
    void updateClubMembership(String id, boolean isMember, int memberCount);

    @Query("DELETE FROM clubs")
    void clearClubs();

    // Announcements
    @Query("SELECT * FROM announcements")
    LiveData<List<Announcement>> getAnnouncements();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAnnouncements(List<Announcement> announcements);

    @Query("DELETE FROM announcements")
    void clearAnnouncements();

    // Complaints
    @Query("SELECT * FROM complaints")
    LiveData<List<Complaint>> getComplaints();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertComplaint(Complaint complaint);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertComplaints(List<Complaint> complaints);

    @Query("DELETE FROM complaints")
    void clearComplaints();
}
