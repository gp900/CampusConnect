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
import com.example.campusconnect.adapter.LostFoundAdapter;
import com.example.campusconnect.model.LostFoundItem;
import com.example.campusconnect.viewmodel.CampusViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class CampusLostFoundFragment extends Fragment implements LostFoundAdapter.OnContactClickListener {

    private TabLayout tabLayoutFilter;
    private RecyclerView rvLostFound;
    private ProgressBar progressBar;
    private LinearLayout layoutEmptyState;
    private ExtendedFloatingActionButton fabPostItem;

    private CampusViewModel campusViewModel;
    private LostFoundAdapter lostFoundAdapter;
    private List<LostFoundItem> allItems = new ArrayList<>();

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

        rvLostFound.setLayoutManager(new LinearLayoutManager(getContext()));
        
        setupListeners();
        observeLostFoundItems();
    }

    private void initViews(View view) {
        tabLayoutFilter = view.findViewById(R.id.tabLayoutFilter);
        rvLostFound = view.findViewById(R.id.rvLostFound);
        progressBar = view.findViewById(R.id.progressBar);
        layoutEmptyState = view.findViewById(R.id.layoutEmptyState);
        fabPostItem = view.findViewById(R.id.fabPostItem);
    }

    private void setupListeners() {
        tabLayoutFilter.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                filterItems(tab.getPosition() == 0 ? "LOST" : "FOUND");
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        fabPostItem.setOnClickListener(v -> showPostItemDialog());
    }

    private void observeLostFoundItems() {
        campusViewModel.getLostFoundItems().observe(getViewLifecycleOwner(), resource -> {
            if (resource == null) return;
            switch (resource.getStatus()) {
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    rvLostFound.setVisibility(View.GONE);
                    layoutEmptyState.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    if (resource.getData() != null) {
                        allItems = resource.getData();
                        String currentCategory = tabLayoutFilter.getSelectedTabPosition() == 0 ? "LOST" : "FOUND";
                        filterItems(currentCategory);
                    }
                    break;
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), resource.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    private void filterItems(String category) {
        List<LostFoundItem> filteredList = new ArrayList<>();
        for (LostFoundItem item : allItems) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()) {
            rvLostFound.setVisibility(View.GONE);
            layoutEmptyState.setVisibility(View.VISIBLE);
        } else {
            layoutEmptyState.setVisibility(View.GONE);
            rvLostFound.setVisibility(View.VISIBLE);
            lostFoundAdapter = new LostFoundAdapter(filteredList, this);
            rvLostFound.setAdapter(lostFoundAdapter);
        }
    }

    @Override
    public void onContactClicked(LostFoundItem item) {
        Toast.makeText(getContext(), "Calling: " + item.getContactInfo(), Toast.LENGTH_SHORT).show();
    }

    private void showPostItemDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_post_item, null);
        dialog.setContentView(view);

        TextInputEditText etTitle = view.findViewById(R.id.etTitle);
        TextInputEditText etDesc = view.findViewById(R.id.etDesc);
        AutoCompleteTextView actvCategory = view.findViewById(R.id.actvCategory);
        TextInputEditText etLocation = view.findViewById(R.id.etLocation);
        TextInputEditText etContact = view.findViewById(R.id.etContact);
        MaterialButton btnSubmitPost = view.findViewById(R.id.btnSubmitPost);

        String[] categories = {"LOST", "FOUND"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, categories);
        actvCategory.setAdapter(adapter);

        btnSubmitPost.setOnClickListener(v -> {
            String title = etTitle.getText() != null ? etTitle.getText().toString().trim() : "";
            String desc = etDesc.getText() != null ? etDesc.getText().toString().trim() : "";
            String location = etLocation.getText() != null ? etLocation.getText().toString().trim() : "";
            String contact = etContact.getText() != null ? etContact.getText().toString().trim() : "";
            String category = actvCategory.getText() != null ? actvCategory.getText().toString().trim() : "LOST";

            if (title.isEmpty() || desc.isEmpty() || location.isEmpty() || contact.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            LostFoundItem newItem = new LostFoundItem(
                    "lf_" + System.currentTimeMillis(),
                    title, desc, category, location, contact, "Just now", false
            );
            campusViewModel.addLostFoundItem(newItem);
            dialog.dismiss();
            Toast.makeText(getContext(), "Item Posted Successfully!", Toast.LENGTH_SHORT).show();
        });

        dialog.show();
    }
}
