package com.example.campusconnect.viewmodel;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.campusconnect.model.TimetableItem;
import com.example.campusconnect.repository.AcademicRepository;
import com.example.campusconnect.utils.Resource;

import java.util.List;

public class TimetableViewModel extends AndroidViewModel {

    private final AcademicRepository academicRepository;

    public TimetableViewModel(android.app.Application application) {
        super(application);
        this.academicRepository = AcademicRepository.getInstance(application);
    }

    public LiveData<Resource<List<TimetableItem>>> getTimetableForDay(String dayOfWeek) {
        return academicRepository.getTimetableForDay(dayOfWeek);
    }
}
