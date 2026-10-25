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
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class AttendanceFragment extends Fragment implements AttendanceAdapter.OnAttendanceUpdateListener {

    private RecyclerView rvAttendance;
    private ProgressBar progressBar;
    private ExtendedFloatingActionButton fabAddSubject;
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
        fabAddSubject = view.findViewById(R.id.fabAddSubject);

        rvAttendance.setLayoutManager(new LinearLayoutManager(getContext()));
        
        attendanceViewModel = new ViewModelProvider(this).get(AttendanceViewModel.class);

        fabAddSubject.setOnClickListener(v -> showAddSubjectDialog());

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
        attendanceViewModel.markPresent(item);
        Toast.makeText(getContext(), "Marked Present: " + item.getSubjectCode(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAbsentClicked(AttendanceItem item, int position) {
        attendanceViewModel.markAbsent(item);
        Toast.makeText(getContext(), "Marked Absent: " + item.getSubjectCode(), Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onDeleteClicked(AttendanceItem item) {
        attendanceViewModel.deleteAttendance(item.getId());
        Toast.makeText(getContext(), "Subject Deleted", Toast.LENGTH_SHORT).show();
    }

    private void showAddSubjectDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_add_attendance, null);
        dialog.setContentView(view);

        TextInputEditText etSubjectCode = view.findViewById(R.id.etSubjectCode);
        TextInputEditText etSubjectName = view.findViewById(R.id.etSubjectName);
        TextInputEditText etAttended = view.findViewById(R.id.etAttended);
        TextInputEditText etTotal = view.findViewById(R.id.etTotal);
        MaterialButton btnSaveSubject = view.findViewById(R.id.btnSaveSubject);

        btnSaveSubject.setOnClickListener(v -> {
            String code = etSubjectCode.getText() != null ? etSubjectCode.getText().toString().trim() : "";
            String name = etSubjectName.getText() != null ? etSubjectName.getText().toString().trim() : "";
            String attendedStr = etAttended.getText() != null ? etAttended.getText().toString().trim() : "";
            String totalStr = etTotal.getText() != null ? etTotal.getText().toString().trim() : "";

            if (code.isEmpty() || name.isEmpty() || attendedStr.isEmpty() || totalStr.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int attended = Integer.parseInt(attendedStr);
            int total = Integer.parseInt(totalStr);

            if (attended > total) {
                Toast.makeText(getContext(), "Attended cannot be greater than total", Toast.LENGTH_SHORT).show();
                return;
            }

            AttendanceItem newItem = new AttendanceItem(
                    "at_" + System.currentTimeMillis(),
                    code, name, attended, total
            );
            
            attendanceViewModel.addAttendanceSubject(newItem);
            dialog.dismiss();
            Toast.makeText(getContext(), "Subject Added Successfully!", Toast.LENGTH_SHORT).show();
        });

        dialog.show();
    }
}
