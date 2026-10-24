package com.example.campusconnect.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.campusconnect.model.Event;
import com.example.campusconnect.repository.CampusRepository;
import com.example.campusconnect.utils.Resource;

import java.util.List;

public class CampusViewModel extends ViewModel {

    private final CampusRepository campusRepository;
    private final MutableLiveData<Boolean> featuredRefreshTrigger = new MutableLiveData<>(true);
    private final MutableLiveData<Boolean> upcomingRefreshTrigger = new MutableLiveData<>(true);

    public CampusViewModel() {
        this.campusRepository = CampusRepository.getInstance();
    }

    public LiveData<Resource<List<Event>>> getFeaturedEvents() {
        return Transformations.switchMap(featuredRefreshTrigger, trigger ->
                campusRepository.getFeaturedEvents()
        );
    }

    public LiveData<Resource<List<Event>>> getUpcomingEvents() {
        return Transformations.switchMap(upcomingRefreshTrigger, trigger ->
                campusRepository.getUpcomingEvents()
        );
    }

    public void toggleRsvp(Event event) {
        campusRepository.toggleEventRsvp(event.getId(), event.isFeatured());
        if (event.isFeatured()) {
            featuredRefreshTrigger.setValue(true);
        } else {
            upcomingRefreshTrigger.setValue(true);
        }
    }

    // ==========================================
    // LOST & FOUND LOGIC
    // ==========================================
    private final MutableLiveData<Boolean> lostFoundRefreshTrigger = new MutableLiveData<>(true);

    public LiveData<Resource<List<com.example.campusconnect.model.LostFoundItem>>> getLostFoundItems() {
        return Transformations.switchMap(lostFoundRefreshTrigger, trigger ->
                campusRepository.getLostFoundItems()
        );
    }

    public void addLostFoundItem(com.example.campusconnect.model.LostFoundItem item) {
        campusRepository.addLostFoundItem(item);
        lostFoundRefreshTrigger.setValue(true);
    }

    // ==========================================
    // CLUBS LOGIC
    // ==========================================
    private final MutableLiveData<Boolean> clubsRefreshTrigger = new MutableLiveData<>(true);

    public LiveData<Resource<List<com.example.campusconnect.model.Club>>> getClubs() {
        return Transformations.switchMap(clubsRefreshTrigger, trigger ->
                campusRepository.getClubs()
        );
    }

    public void toggleClubMembership(String clubId) {
        campusRepository.toggleClubMembership(clubId);
        clubsRefreshTrigger.setValue(true);
    }

    // ==========================================
    // ANNOUNCEMENTS LOGIC
    // ==========================================
    private final MutableLiveData<Boolean> announcementsRefreshTrigger = new MutableLiveData<>(true);

    public LiveData<Resource<List<com.example.campusconnect.model.Announcement>>> getAnnouncements() {
        return Transformations.switchMap(announcementsRefreshTrigger, trigger ->
                campusRepository.getAnnouncements()
        );
    }
}
