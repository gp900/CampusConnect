package com.example.campusconnect.repository;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
}
