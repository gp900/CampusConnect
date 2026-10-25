package com.example.campusconnect.viewmodel;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.campusconnect.model.AttendanceItem;
import com.example.campusconnect.repository.AcademicRepository;
import com.example.campusconnect.utils.Resource;

import java.util.List;

public class AttendanceViewModel extends AndroidViewModel {

    private final AcademicRepository academicRepository;
    private final MutableLiveData<Boolean> forceRefreshTrigger = new MutableLiveData<>(true);

    public AttendanceViewModel(android.app.Application application) {
        super(application);
        this.academicRepository = AcademicRepository.getInstance(application);
    }

    // Using Transformations.switchMap so we can refresh the list manually
    public LiveData<Resource<List<AttendanceItem>>> getAttendanceList() {
        return Transformations.switchMap(forceRefreshTrigger, trigger -> 
            academicRepository.getAttendance()
        );
    }

    public void markPresent(AttendanceItem item) {
        academicRepository.markAttendanceObj(item, true);
        forceRefreshTrigger.setValue(true);
    }

    public void markAbsent(AttendanceItem item) {
        academicRepository.markAttendanceObj(item, false);
        forceRefreshTrigger.setValue(true);
    }
}
