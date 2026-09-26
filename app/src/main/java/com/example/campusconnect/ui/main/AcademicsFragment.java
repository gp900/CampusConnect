package com.example.campusconnect.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.viewpager2.widget.ViewPager2;

import com.example.campusconnect.R;
import com.example.campusconnect.adapter.AcademicsPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class AcademicsFragment extends Fragment {

    private TabLayout tabLayoutAcademics;
    private ViewPager2 vpAcademics;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_academics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayoutAcademics = view.findViewById(R.id.tabLayoutAcademics);
        vpAcademics = view.findViewById(R.id.vpAcademics);

        AcademicsPagerAdapter pagerAdapter = new AcademicsPagerAdapter(getChildFragmentManager(), getLifecycle());
        vpAcademics.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayoutAcademics, vpAcademics, (tab, position) -> {
            switch (position) {
                case 0: tab.setText("Timetable"); break;
                case 1: tab.setText("Attendance"); break;
                // case 2: tab.setText("Assignments"); break;
                // case 3: tab.setText("Notes"); break;
            }
        }).attach();
    }
}
