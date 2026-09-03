package com.example.campusconnect.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.campusconnect.R;
import com.example.campusconnect.model.DashboardData;
import com.example.campusconnect.viewmodel.DashboardViewModel;
import com.google.android.material.button.MaterialButton;

public class HomeFragment extends Fragment {

    private TextView tvGreeting;
    private TextView tvAcademicSubtitle;
    private TextView tvNextSubject;
    private TextView tvStartsIn;
    private TextView tvNextTimeRoom;
    private TextView tvNextProfessor;
    private TextView tvAttendancePercent;
    private TextView tvAttendanceStatus;
    private TextView tvPendingCount;
    private TextView tvHighPriorityCount;
    private TextView tvEventTitle;
    private TextView tvEventDetails;

    private MaterialButton btnQuickAttendance;
    private MaterialButton btnQuickNotes;
    private MaterialButton btnQuickLostFound;
    private MaterialButton btnQuickComplaint;
    private MaterialButton btnRsvp;

    private DashboardViewModel dashboardViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        observeDashboardData();
        setupQuickActions();
    }

    private void initViews(View view) {
        tvGreeting = view.findViewById(R.id.tvGreeting);
        tvAcademicSubtitle = view.findViewById(R.id.tvAcademicSubtitle);
        tvNextSubject = view.findViewById(R.id.tvNextSubject);
        tvStartsIn = view.findViewById(R.id.tvStartsIn);
        tvNextTimeRoom = view.findViewById(R.id.tvNextTimeRoom);
        tvNextProfessor = view.findViewById(R.id.tvNextProfessor);
        tvAttendancePercent = view.findViewById(R.id.tvAttendancePercent);
        tvAttendanceStatus = view.findViewById(R.id.tvAttendanceStatus);
        tvPendingCount = view.findViewById(R.id.tvPendingCount);
        tvHighPriorityCount = view.findViewById(R.id.tvHighPriorityCount);
        tvEventTitle = view.findViewById(R.id.tvEventTitle);
        tvEventDetails = view.findViewById(R.id.tvEventDetails);

        btnQuickAttendance = view.findViewById(R.id.btnQuickAttendance);
        btnQuickNotes = view.findViewById(R.id.btnQuickNotes);
        btnQuickLostFound = view.findViewById(R.id.btnQuickLostFound);
        btnQuickComplaint = view.findViewById(R.id.btnQuickComplaint);
        btnRsvp = view.findViewById(R.id.btnRsvp);
    }

    private void observeDashboardData() {
        dashboardViewModel.getDashboardData().observe(getViewLifecycleOwner(), resource -> {
            if (resource == null || resource.getData() == null) return;

            DashboardData data = resource.getData();
            String greetingText = "Hello, " + data.getStudentName() + "! 👋";
            tvGreeting.setText(greetingText);
            tvAcademicSubtitle.setText(data.getDegreeAndDept());
            tvNextSubject.setText(data.getNextSubject());
            tvStartsIn.setText(data.getStartsIn());
            tvNextTimeRoom.setText(data.getNextTimeAndRoom());
            tvNextProfessor.setText(data.getNextProfessor());

            String attendanceText = data.getAttendancePercentage() + "%";
            tvAttendancePercent.setText(attendanceText);
            tvAttendanceStatus.setText(data.getAttendanceStatus());

            String pendingText = data.getPendingAssignments() + " Due";
            tvPendingCount.setText(pendingText);
            String priorityText = data.getHighPriorityAssignments() + " High Priority";
            tvHighPriorityCount.setText(priorityText);

            tvEventTitle.setText(data.getFeaturedEventTitle());
            tvEventDetails.setText(data.getFeaturedEventDetails());
        });
    }

    private void setupQuickActions() {
        btnQuickAttendance.setOnClickListener(v ->
                Toast.makeText(getContext(), "Opening Attendance Tracker...", Toast.LENGTH_SHORT).show());

        btnQuickNotes.setOnClickListener(v ->
                Toast.makeText(getContext(), "Opening Notes Repository...", Toast.LENGTH_SHORT).show());

        btnQuickLostFound.setOnClickListener(v ->
                Toast.makeText(getContext(), "Opening Lost & Found Bulletin...", Toast.LENGTH_SHORT).show());

        btnQuickComplaint.setOnClickListener(v ->
                Toast.makeText(getContext(), "Opening Complaint System...", Toast.LENGTH_SHORT).show());

        btnRsvp.setOnClickListener(v ->
                Toast.makeText(getContext(), "RSVP Registered Successfully!", Toast.LENGTH_SHORT).show());
    }
}
