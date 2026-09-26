package com.example.campusconnect.ui.academics;

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

import com.example.campusconnect.R;
import com.example.campusconnect.adapter.AttendanceAdapter;
import com.example.campusconnect.model.AttendanceItem;
import com.example.campusconnect.viewmodel.AttendanceViewModel;

import java.util.List;

public class AttendanceFragment extends Fragment implements AttendanceAdapter.OnAttendanceUpdateListener {

    private RecyclerView rvAttendance;
    private ProgressBar progressBar;
    private AttendanceViewModel attendanceViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_attendance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvAttendance = view.findViewById(R.id.rvAttendance);
        progressBar = view.findViewById(R.id.progressBar);

        rvAttendance.setLayoutManager(new LinearLayoutManager(getContext()));
        
        attendanceViewModel = new ViewModelProvider(this).get(AttendanceViewModel.class);

        observeAttendanceData();
    }

    private void observeAttendanceData() {
        attendanceViewModel.getAttendanceList().observe(getViewLifecycleOwner(), resource -> {
            if (resource == null) return;

            switch (resource.getStatus()) {
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    rvAttendance.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    List<AttendanceItem> items = resource.getData();
                    if (items != null) {
                        rvAttendance.setVisibility(View.VISIBLE);
                        rvAttendance.setAdapter(new AttendanceAdapter(items, this));
                    }
                    break;
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), resource.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    @Override
    public void onPresentClicked(AttendanceItem item, int position) {
        attendanceViewModel.markPresent(item.getId());
        Toast.makeText(getContext(), "Marked Present: " + item.getSubjectCode(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAbsentClicked(AttendanceItem item, int position) {
        attendanceViewModel.markAbsent(item.getId());
        Toast.makeText(getContext(), "Marked Absent: " + item.getSubjectCode(), Toast.LENGTH_SHORT).show();
    }
}
