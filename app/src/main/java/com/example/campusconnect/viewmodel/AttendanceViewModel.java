package com.example.campusconnect.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.campusconnect.model.AttendanceItem;
import com.example.campusconnect.repository.AcademicRepository;
import com.example.campusconnect.utils.Resource;

import java.util.List;

public class AttendanceViewModel extends ViewModel {

    private final AcademicRepository academicRepository;
    private final MutableLiveData<Boolean> forceRefreshTrigger = new MutableLiveData<>(true);

    public AttendanceViewModel() {
        this.academicRepository = AcademicRepository.getInstance();
    }

    // Using Transformations.switchMap so we can refresh the list manually
    public LiveData<Resource<List<AttendanceItem>>> getAttendanceList() {
        return Transformations.switchMap(forceRefreshTrigger, trigger -> 
            academicRepository.getAttendanceList()
        );
    }

    public void markPresent(String itemId) {
        academicRepository.markAttendancePresent(itemId);
        // Trigger a refresh after updating data
        forceRefreshTrigger.setValue(true);
    }

    public void markAbsent(String itemId) {
        academicRepository.markAttendanceAbsent(itemId);
        // Trigger a refresh after updating data
        forceRefreshTrigger.setValue(true);
    }
}
