package com.example.campusconnect.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.campusconnect.ui.academics.AttendanceFragment;
import com.example.campusconnect.ui.academics.TimetableFragment;

public class AcademicsPagerAdapter extends FragmentStateAdapter {

    public AcademicsPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new TimetableFragment();
            case 1: return new AttendanceFragment();
            // TODO: Case 2: AssignmentsFragment
            // TODO: Case 3: NotesFragment
            default: return new TimetableFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2; // Timetable, Attendance (Will be 4 eventually)
    }
}
