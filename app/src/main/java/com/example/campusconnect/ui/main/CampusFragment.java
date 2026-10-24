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
import com.example.campusconnect.adapter.CampusPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class CampusFragment extends Fragment {

    private TabLayout tabLayoutCampus;
    private ViewPager2 vpCampus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_campus, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayoutCampus = view.findViewById(R.id.tabLayoutCampus);
        vpCampus = view.findViewById(R.id.vpCampus);

        CampusPagerAdapter pagerAdapter = new CampusPagerAdapter(getChildFragmentManager(), getLifecycle());
        vpCampus.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayoutCampus, vpCampus, (tab, position) -> {
            switch (position) {
                case 0: tab.setText("Maps"); break;
                case 1: tab.setText("Events"); break;
                case 2: tab.setText("Lost/Found"); break;
                case 3: tab.setText("Clubs"); break;
                case 4: tab.setText("Notices"); break;
                case 5: tab.setText("Complaints"); break;
            }
        }).attach();
    }
}
