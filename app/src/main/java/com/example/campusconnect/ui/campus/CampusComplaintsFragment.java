package com.example.campusconnect.ui.campus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.example.campusconnect.adapter.ComplaintAdapter;
import com.example.campusconnect.model.Complaint;
import com.example.campusconnect.viewmodel.CampusViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class CampusComplaintsFragment extends Fragment {

    private TabLayout tabLayoutFilter;
    private RecyclerView rvComplaints;
    private ProgressBar progressBar;
    private LinearLayout layoutEmptyState;
    private ExtendedFloatingActionButton fabLodgeComplaint;

    private CampusViewModel campusViewModel;
    private ComplaintAdapter complaintAdapter;
    private List<Complaint> allComplaints = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_campus_complaints, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        campusViewModel = new ViewModelProvider(this).get(CampusViewModel.class);

        rvComplaints.setLayoutManager(new LinearLayoutManager(getContext()));
        
        setupListeners();
        observeComplaints();
    }

    private void initViews(View view) {
        tabLayoutFilter = view.findViewById(R.id.tabLayoutFilter);
        rvComplaints = view.findViewById(R.id.rvComplaints);
        progressBar = view.findViewById(R.id.progressBar);
        layoutEmptyState = view.findViewById(R.id.layoutEmptyState);
        fabLodgeComplaint = view.findViewById(R.id.fabLodgeComplaint);
    }

    private void setupListeners() {
        tabLayoutFilter.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                filterComplaints(tab.getPosition() == 1);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        fabLodgeComplaint.setOnClickListener(v -> showLodgeComplaintDialog());
    }

    private void observeComplaints() {
        campusViewModel.getComplaints().observe(getViewLifecycleOwner(), resource -> {
            if (resource == null) return;
            switch (resource.getStatus()) {
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    rvComplaints.setVisibility(View.GONE);
                    layoutEmptyState.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    if (resource.getData() != null) {
                        allComplaints = resource.getData();
                        filterComplaints(tabLayoutFilter.getSelectedTabPosition() == 1);
                    }
                    break;
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), resource.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    private void filterComplaints(boolean showResolved) {
        List<Complaint> filteredList = new ArrayList<>();
        for (Complaint complaint : allComplaints) {
            boolean isResolved = "RESOLVED".equalsIgnoreCase(complaint.getStatus());
            if (isResolved == showResolved) {
                filteredList.add(complaint);
            }
        }

        if (filteredList.isEmpty()) {
            rvComplaints.setVisibility(View.GONE);
            layoutEmptyState.setVisibility(View.VISIBLE);
        } else {
            layoutEmptyState.setVisibility(View.GONE);
            rvComplaints.setVisibility(View.VISIBLE);
            complaintAdapter = new ComplaintAdapter(filteredList);
            rvComplaints.setAdapter(complaintAdapter);
        }
    }

    private void showLodgeComplaintDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_add_complaint, null);
        dialog.setContentView(view);

        TextInputEditText etSubject = view.findViewById(R.id.etSubject);
        TextInputEditText etDesc = view.findViewById(R.id.etDesc);
        AutoCompleteTextView actvCategory = view.findViewById(R.id.actvCategory);
        MaterialButton btnSubmitComplaint = view.findViewById(R.id.btnSubmitComplaint);

        String[] categories = {"HOSTEL", "ACADEMICS", "INFRASTRUCTURE", "WI-FI"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, categories);
        actvCategory.setAdapter(adapter);

        btnSubmitComplaint.setOnClickListener(v -> {
            String subject = etSubject.getText() != null ? etSubject.getText().toString().trim() : "";
            String desc = etDesc.getText() != null ? etDesc.getText().toString().trim() : "";
            String category = actvCategory.getText() != null ? actvCategory.getText().toString().trim() : "HOSTEL";

            if (subject.isEmpty() || desc.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Complaint newComplaint = new Complaint(
                    "CMP" + System.currentTimeMillis(),
                    subject, category, desc, "SUBMITTED", "Just now"
            );
            campusViewModel.addComplaint(newComplaint);
            dialog.dismiss();
            Toast.makeText(getContext(), "Complaint Submitted Successfully!", Toast.LENGTH_SHORT).show();
        });

        dialog.show();
    }
}
