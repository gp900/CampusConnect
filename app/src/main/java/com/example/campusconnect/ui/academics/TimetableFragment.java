package com.example.campusconnect.ui.academics;

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
import com.example.campusconnect.adapter.TimetableAdapter;
import com.example.campusconnect.model.TimetableItem;
import com.example.campusconnect.viewmodel.TimetableViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.Calendar;
import java.util.List;

public class TimetableFragment extends Fragment {

    private ChipGroup chipGroupDays;
    private Chip chipMon, chipTue, chipWed, chipThu, chipFri, chipSat;
    private RecyclerView rvTimetable;
    private ProgressBar progressBar;
    private LinearLayout layoutEmptyState;

    private TimetableViewModel timetableViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timetable, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        timetableViewModel = new ViewModelProvider(this).get(TimetableViewModel.class);

        rvTimetable.setLayoutManager(new LinearLayoutManager(getContext()));

        setupDayChipListeners();
        selectTodayChip();
    }

    private void initViews(View view) {
        chipGroupDays = view.findViewById(R.id.chipGroupDays);
        chipMon = view.findViewById(R.id.chipMon);
        chipTue = view.findViewById(R.id.chipTue);
        chipWed = view.findViewById(R.id.chipWed);
        chipThu = view.findViewById(R.id.chipThu);
        chipFri = view.findViewById(R.id.chipFri);
        chipSat = view.findViewById(R.id.chipSat);

        rvTimetable = view.findViewById(R.id.rvTimetable);
        progressBar = view.findViewById(R.id.progressBar);
        layoutEmptyState = view.findViewById(R.id.layoutEmptyState);
    }

    private void selectTodayChip() {
        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                chipMon.setChecked(true);
                loadTimetableForDay("MONDAY");
                break;
            case Calendar.TUESDAY:
                chipTue.setChecked(true);
                loadTimetableForDay("TUESDAY");
                break;
            case Calendar.WEDNESDAY:
                chipWed.setChecked(true);
                loadTimetableForDay("WEDNESDAY");
                break;
            case Calendar.THURSDAY:
                chipThu.setChecked(true);
                loadTimetableForDay("THURSDAY");
                break;
            case Calendar.FRIDAY:
                chipFri.setChecked(true);
                loadTimetableForDay("FRIDAY");
                break;
            case Calendar.SATURDAY:
                chipSat.setChecked(true);
                loadTimetableForDay("SATURDAY");
                break;
            default:
                chipMon.setChecked(true);
                loadTimetableForDay("SUNDAY");
                break;
        }
    }

    private void setupDayChipListeners() {
        chipGroupDays.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) return;
            int checkedId = checkedIds.get(0);

            if (checkedId == R.id.chipMon) {
                loadTimetableForDay("MONDAY");
            } else if (checkedId == R.id.chipTue) {
                loadTimetableForDay("TUESDAY");
            } else if (checkedId == R.id.chipWed) {
                loadTimetableForDay("WEDNESDAY");
            } else if (checkedId == R.id.chipThu) {
                loadTimetableForDay("THURSDAY");
            } else if (checkedId == R.id.chipFri) {
                loadTimetableForDay("FRIDAY");
            } else if (checkedId == R.id.chipSat) {
                loadTimetableForDay("SATURDAY");
            }
        });
    }

    private void loadTimetableForDay(String dayOfWeek) {
        timetableViewModel.getTimetableForDay(dayOfWeek).observe(getViewLifecycleOwner(), resource -> {
            if (resource == null) return;

            switch (resource.getStatus()) {
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    rvTimetable.setVisibility(View.GONE);
                    layoutEmptyState.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    List<TimetableItem> items = resource.getData();
                    if (items == null || items.isEmpty()) {
                        rvTimetable.setVisibility(View.GONE);
                        layoutEmptyState.setVisibility(View.VISIBLE);
                    } else {
                        layoutEmptyState.setVisibility(View.GONE);
                        rvTimetable.setVisibility(View.VISIBLE);
                        rvTimetable.setAdapter(new TimetableAdapter(items));
                    }
                    break;
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), resource.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }
}
