package com.example.campusconnect.ui.academics;

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
import com.example.campusconnect.adapter.AssignmentAdapter;
import com.example.campusconnect.model.Assignment;
import com.example.campusconnect.viewmodel.AssignmentViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class AssignmentsFragment extends Fragment implements AssignmentAdapter.OnAssignmentUpdateListener {

    private TabLayout tabLayoutFilter;
    private RecyclerView rvAssignments;
    private ProgressBar progressBar;
    private LinearLayout layoutEmptyState;
    private ExtendedFloatingActionButton fabAddAssignment;

    private AssignmentViewModel assignmentViewModel;
    private AssignmentAdapter assignmentAdapter;
    private List<Assignment> allAssignments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_assignments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        initViews(view);
        assignmentViewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);

        rvAssignments.setLayoutManager(new LinearLayoutManager(getContext()));
        
        setupListeners();
        observeAssignments();
    }

    private void initViews(View view) {
        tabLayoutFilter = view.findViewById(R.id.tabLayoutFilter);
        rvAssignments = view.findViewById(R.id.rvAssignments);
        progressBar = view.findViewById(R.id.progressBar);
        layoutEmptyState = view.findViewById(R.id.layoutEmptyState);
        fabAddAssignment = view.findViewById(R.id.fabAddAssignment);
    }

    private void setupListeners() {
        tabLayoutFilter.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                filterAssignments(tab.getPosition() == 1);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        fabAddAssignment.setOnClickListener(v -> showAddAssignmentDialog());
    }

    private void observeAssignments() {
        assignmentViewModel.getAssignments().observe(getViewLifecycleOwner(), resource -> {
            if (resource == null) return;
            switch (resource.getStatus()) {
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    rvAssignments.setVisibility(View.GONE);
                    layoutEmptyState.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    if (resource.getData() != null) {
                        allAssignments = resource.getData();
                        filterAssignments(tabLayoutFilter.getSelectedTabPosition() == 1);
                    }
                    break;
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), resource.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    private void filterAssignments(boolean showCompleted) {
        List<Assignment> filteredList = new ArrayList<>();
        for (Assignment assignment : allAssignments) {
            if (assignment.isCompleted() == showCompleted) {
                filteredList.add(assignment);
            }
        }

        if (filteredList.isEmpty()) {
            rvAssignments.setVisibility(View.GONE);
            layoutEmptyState.setVisibility(View.VISIBLE);
        } else {
            layoutEmptyState.setVisibility(View.GONE);
            rvAssignments.setVisibility(View.VISIBLE);
            assignmentAdapter = new AssignmentAdapter(filteredList, this);
            rvAssignments.setAdapter(assignmentAdapter);
        }
    }

    @Override
    public void onAssignmentToggled(Assignment assignment, boolean isCompleted) {
        assignmentViewModel.toggleAssignmentCompletion(assignment);
    }

    private void showAddAssignmentDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_add_assignment, null);
        dialog.setContentView(view);

        TextInputLayout tilTitle = view.findViewById(R.id.tilTitle);
        TextInputEditText etTitle = view.findViewById(R.id.etTitle);
        TextInputLayout tilSubject = view.findViewById(R.id.tilSubject);
        TextInputEditText etSubject = view.findViewById(R.id.etSubject);
        TextInputLayout tilDueDate = view.findViewById(R.id.tilDueDate);
        TextInputEditText etDueDate = view.findViewById(R.id.etDueDate);
        TextInputLayout tilPriority = view.findViewById(R.id.tilPriority);
        AutoCompleteTextView actvPriority = view.findViewById(R.id.actvPriority);
        MaterialButton btnSave = view.findViewById(R.id.btnSave);

        String[] priorities = {"HIGH", "MEDIUM", "LOW"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, priorities);
        actvPriority.setAdapter(adapter);

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText() != null ? etTitle.getText().toString().trim() : "";
            String subject = etSubject.getText() != null ? etSubject.getText().toString().trim() : "";
            String date = etDueDate.getText() != null ? etDueDate.getText().toString().trim() : "";
            String priority = actvPriority.getText() != null ? actvPriority.getText().toString().trim() : "LOW";

            if (title.isEmpty() || subject.isEmpty() || date.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Assignment newAssignment = new Assignment(
                    "as_" + System.currentTimeMillis(),
                    title, subject, date, priority, false
            );
            assignmentViewModel.addAssignment(newAssignment);
            dialog.dismiss();
            Toast.makeText(getContext(), "Assignment Added!", Toast.LENGTH_SHORT).show();
        });

        dialog.show();
    }
}
