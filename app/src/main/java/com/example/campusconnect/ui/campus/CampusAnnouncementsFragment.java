package com.example.campusconnect.ui.campus;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.campusconnect.adapter.AnnouncementAdapter;
import com.example.campusconnect.model.Announcement;
import com.example.campusconnect.viewmodel.CampusViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class CampusAnnouncementsFragment extends Fragment {

    private TextInputEditText etSearch;
    private RecyclerView rvAnnouncements;
    private ProgressBar progressBar;
    private LinearLayout layoutEmptyState;

    private CampusViewModel campusViewModel;
    private AnnouncementAdapter announcementAdapter;
    private List<Announcement> allAnnouncements = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_campus_announcements, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        campusViewModel = new ViewModelProvider(this).get(CampusViewModel.class);
        rvAnnouncements.setLayoutManager(new LinearLayoutManager(getContext()));
        
        setupListeners();
        observeAnnouncements();
    }

    private void initViews(View view) {
        etSearch = view.findViewById(R.id.etSearch);
        rvAnnouncements = view.findViewById(R.id.rvAnnouncements);
        progressBar = view.findViewById(R.id.progressBar);
        layoutEmptyState = view.findViewById(R.id.layoutEmptyState);
    }

    private void setupListeners() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterAnnouncements(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void observeAnnouncements() {
        campusViewModel.getAnnouncements().observe(getViewLifecycleOwner(), resource -> {
            if (resource == null) return;
            switch (resource.getStatus()) {
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    rvAnnouncements.setVisibility(View.GONE);
                    layoutEmptyState.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    if (resource.getData() != null) {
                        allAnnouncements = resource.getData();
                        String currentQuery = etSearch.getText() != null ? etSearch.getText().toString() : "";
                        filterAnnouncements(currentQuery);
                    }
                    break;
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), resource.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    private void filterAnnouncements(String query) {
        List<Announcement> filteredList = new ArrayList<>();
        if (query.trim().isEmpty()) {
            filteredList.addAll(allAnnouncements);
        } else {
            String lowerCaseQuery = query.toLowerCase().trim();
            for (Announcement announcement : allAnnouncements) {
                if (announcement.getTitle().toLowerCase().contains(lowerCaseQuery) ||
                        announcement.getContent().toLowerCase().contains(lowerCaseQuery)) {
                    filteredList.add(announcement);
                }
            }
        }

        if (filteredList.isEmpty()) {
            rvAnnouncements.setVisibility(View.GONE);
            layoutEmptyState.setVisibility(View.VISIBLE);
        } else {
            layoutEmptyState.setVisibility(View.GONE);
            rvAnnouncements.setVisibility(View.VISIBLE);
            announcementAdapter = new AnnouncementAdapter(filteredList);
            rvAnnouncements.setAdapter(announcementAdapter);
        }
    }
}