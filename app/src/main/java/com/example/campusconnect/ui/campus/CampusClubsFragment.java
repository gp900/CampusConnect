package com.example.campusconnect.ui.campus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusconnect.R;
import com.example.campusconnect.adapter.ClubAdapter;
import com.example.campusconnect.model.Club;
import com.example.campusconnect.viewmodel.CampusViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class CampusClubsFragment extends Fragment implements ClubAdapter.OnClubClickListener {

    private TabLayout tabLayoutFilter;
    private RecyclerView rvClubs;
    private ProgressBar progressBar;
    private LinearLayout layoutEmptyState;

    private CampusViewModel campusViewModel;
    private ClubAdapter clubAdapter;
    private List<Club> allClubs = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_campus_lost_found, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        campusViewModel = new ViewModelProvider(this).get(CampusViewModel.class);
        rvClubs.setLayoutManager(new LinearLayoutManager(getContext()));
        
        setupListeners();
        observeClubs();
    }

    private void initViews(View view) {
        tabLayoutFilter = view.findViewById(R.id.tabLayoutFilter);
        rvClubs = view.findViewById(R.id.rvLostFound); // Reusing recycler id from layout
        progressBar = view.findViewById(R.id.progressBar);
        layoutEmptyState = view.findViewById(R.id.layoutEmptyState);
        
        // Hide fab as we don't post clubs
        view.findViewById(R.id.fabPostItem).setVisibility(View.GONE);
        
        tabLayoutFilter.removeAllTabs();
        tabLayoutFilter.addTab(tabLayoutFilter.newTab().setText(R.string.tab_technical));
        tabLayoutFilter.addTab(tabLayoutFilter.newTab().setText(R.string.tab_cultural));
        tabLayoutFilter.addTab(tabLayoutFilter.newTab().setText(R.string.tab_sports));
    }

    private void setupListeners() {
        tabLayoutFilter.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String category;
                switch (tab.getPosition()) {
                    case 1:
                        category = "CULTURAL";
                        break;
                    case 2:
                        category = "SPORTS";
                        break;
                    default:
                        category = "TECHNICAL";
                        break;
                }
                filterClubs(category);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void observeClubs() {
        campusViewModel.getClubs().observe(getViewLifecycleOwner(), resource -> {
            if (resource == null) return;
            switch (resource.getStatus()) {
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    rvClubs.setVisibility(View.GONE);
                    layoutEmptyState.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    if (resource.getData() != null) {
                        allClubs = resource.getData();
                        String currentCategory;
                        if (tabLayoutFilter.getSelectedTabPosition() == 1) {
                            currentCategory = "CULTURAL";
                        } else if (tabLayoutFilter.getSelectedTabPosition() == 2) {
                            currentCategory = "SPORTS";
                        } else {
                            currentCategory = "TECHNICAL";
                        }
                        filterClubs(currentCategory);
                    }
                    break;
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), resource.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    private void filterClubs(String category) {
        List<Club> filteredList = new ArrayList<>();
        for (Club club : allClubs) {
            if (club.getCategory().equalsIgnoreCase(category)) {
                filteredList.add(club);
            }
        }

        if (filteredList.isEmpty()) {
            rvClubs.setVisibility(View.GONE);
            layoutEmptyState.setVisibility(View.VISIBLE);
        } else {
            layoutEmptyState.setVisibility(View.GONE);
            rvClubs.setVisibility(View.VISIBLE);
            clubAdapter = new ClubAdapter(filteredList, this);
            rvClubs.setAdapter(clubAdapter);
        }
    }

    @Override
    public void onJoinToggled(Club club) {
        campusViewModel.toggleClubMembership(club.getId());
    }
}
