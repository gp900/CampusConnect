package com.example.campusconnect.ui.campus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.SnapHelper;

import com.example.campusconnect.R;
import com.example.campusconnect.adapter.EventAdapter;
import com.example.campusconnect.model.Event;
import com.example.campusconnect.viewmodel.CampusViewModel;

public class CampusEventsFragment extends Fragment implements EventAdapter.OnEventClickListener {

    private RecyclerView rvFeaturedEvents;
    private RecyclerView rvUpcomingEvents;
    private ProgressBar progressBar;

    private CampusViewModel campusViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_campus_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        campusViewModel = new ViewModelProvider(this).get(CampusViewModel.class);

        setupRecyclerViews();
        observeEvents();
    }

    private void initViews(View view) {
        rvFeaturedEvents = view.findViewById(R.id.rvFeaturedEvents);
        rvUpcomingEvents = view.findViewById(R.id.rvUpcomingEvents);
        progressBar = view.findViewById(R.id.progressBar);
    }

    private void setupRecyclerViews() {
        rvFeaturedEvents.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvFeaturedEvents);

        rvUpcomingEvents.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void observeEvents() {
        campusViewModel.getFeaturedEvents().observe(getViewLifecycleOwner(), resource -> {
            if (resource == null) return;
            switch (resource.getStatus()) {
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    if (resource.getData() != null) {
                        EventAdapter featuredAdapter = new EventAdapter(resource.getData(), true, this);
                        rvFeaturedEvents.setAdapter(featuredAdapter);
                    }
                    break;
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), resource.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        campusViewModel.getUpcomingEvents().observe(getViewLifecycleOwner(), resource -> {
            if (resource == null) return;
            switch (resource.getStatus()) {
                case SUCCESS:
                    if (resource.getData() != null) {
                        EventAdapter upcomingAdapter = new EventAdapter(resource.getData(), false, this);
                        rvUpcomingEvents.setAdapter(upcomingAdapter);
                    }
                    break;
                case ERROR:
                    Toast.makeText(getContext(), resource.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public void onRsvpClicked(Event event, int position) {
        campusViewModel.toggleRsvp(event);
        String action = event.isRsvped() ? "Cancelled RSVP for " : "RSVP'd to ";
        Toast.makeText(getContext(), action + event.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
