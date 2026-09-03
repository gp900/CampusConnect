package com.example.campusconnect.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.campusconnect.model.TimetableItem;
import com.example.campusconnect.repository.AcademicRepository;
import com.example.campusconnect.utils.Resource;

import java.util.List;

public class TimetableViewModel extends ViewModel {

    private final AcademicRepository academicRepository;

    public TimetableViewModel() {
        this.academicRepository = AcademicRepository.getInstance();
    }

    public LiveData<Resource<List<TimetableItem>>> getTimetableForDay(String dayOfWeek) {
        return academicRepository.getTimetableForDay(dayOfWeek);
    }
}
