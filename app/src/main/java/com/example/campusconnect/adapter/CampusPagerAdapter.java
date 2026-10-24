package com.example.campusconnect.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.campusconnect.ui.campus.CampusAnnouncementsFragment;
import com.example.campusconnect.ui.campus.CampusClubsFragment;
import com.example.campusconnect.ui.campus.CampusComplaintsFragment;
import com.example.campusconnect.ui.campus.CampusEventsFragment;
import com.example.campusconnect.ui.campus.CampusLostFoundFragment;
import com.example.campusconnect.ui.campus.CampusNavigationFragment;

public class CampusPagerAdapter extends FragmentStateAdapter {

    public CampusPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new CampusNavigationFragment();
            case 1: return new CampusEventsFragment();
            case 2: return new CampusLostFoundFragment();
            case 3: return new CampusClubsFragment();
            case 4: return new CampusAnnouncementsFragment();
            case 5: return new CampusComplaintsFragment();
            default: return new CampusNavigationFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 6; // Navigation, Events, Lost & Found, Clubs, Announcements, Complaints
    }
}
