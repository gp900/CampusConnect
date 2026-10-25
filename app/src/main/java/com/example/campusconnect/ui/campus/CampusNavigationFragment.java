package com.example.campusconnect.ui.campus;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.campusconnect.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

public class CampusNavigationFragment extends Fragment {

    private TabLayout tabLayoutFloors;
    private PhotoView photoViewMap;
    private ProgressBar progressBar;
    private TextInputEditText etSearchRoom;

    // Floor prefixes (LG, G, 1-8) matching the files in assets
    private final String[] floorIds = {"lg", "g", "1", "2", "3", "4", "5", "6", "7", "8"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_campus_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setupListeners();
        
        // Load default floor (Ground Floor / Index 1)
        loadFloorPlan(1);
    }

    private void initViews(View view) {
        tabLayoutFloors = view.findViewById(R.id.tabLayoutFloors);
        photoViewMap = view.findViewById(R.id.photoViewMap);
        progressBar = view.findViewById(R.id.progressBar);
        etSearchRoom = view.findViewById(R.id.etSearchRoom);
        
        // Default select Ground Floor tab
        TabLayout.Tab tab = tabLayoutFloors.getTabAt(1);
        if (tab != null) tab.select();
    }

    private void setupListeners() {
        tabLayoutFloors.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                loadFloorPlan(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        etSearchRoom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 3) {
                    searchRoom(s.toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadFloorPlan(int tabIndex) {
        progressBar.setVisibility(View.VISIBLE);
        
        String floorPrefix = floorIds[tabIndex];
        boolean isDarkTheme = (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
        String themeSuffix = isDarkTheme ? "_dark" : "_light";
        
        // Example: floor_1_light
        String drawableName = "floor_" + floorPrefix + themeSuffix;
        int resourceId = getResources().getIdentifier(drawableName, "drawable", requireContext().getPackageName());

        if (resourceId != 0) {
            Drawable vectorMap = ContextCompat.getDrawable(requireContext(), resourceId);
            photoViewMap.setImageDrawable(vectorMap);
            // Reset zoom when changing floors
            photoViewMap.setScale(1.0f);
        } else {
            Toast.makeText(getContext(), "Map not found for this floor", Toast.LENGTH_SHORT).show();
            photoViewMap.setImageDrawable(null);
        }
        
        progressBar.setVisibility(View.GONE);
    }

    private void searchRoom(String query) {
        // Simple heuristic: If it starts with '1', it's 1st floor (index 2). If '2', 2nd floor (index 3), etc.
        // Assuming room formats like "102", "305", "G14"
        
        int targetTabIndex = -1;
        String firstChar = query.substring(0, 1).toUpperCase();
        
        if (firstChar.equals("G")) targetTabIndex = 1;
        else if (query.toUpperCase().startsWith("LG")) targetTabIndex = 0;
        else {
            try {
                int floorNum = Integer.parseInt(firstChar);
                if (floorNum >= 1 && floorNum <= 8) {
                    targetTabIndex = floorNum + 1; // offset because LG is 0, G is 1
                }
            } catch (NumberFormatException e) {
                // Not a valid room format
            }
        }
        
        if (targetTabIndex != -1 && targetTabIndex != tabLayoutFloors.getSelectedTabPosition()) {
            TabLayout.Tab tab = tabLayoutFloors.getTabAt(targetTabIndex);
            if (tab != null) {
                tab.select(); // This triggers loadFloorPlan automatically
                Toast.makeText(getContext(), "Switched to floor containing room " + query, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
