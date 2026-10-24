package com.example.campusconnect.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.campusconnect.model.Assignment;
import com.example.campusconnect.repository.AcademicRepository;
import com.example.campusconnect.utils.Resource;

import java.util.List;

public class AssignmentViewModel extends ViewModel {

    private final AcademicRepository academicRepository;
    private final MutableLiveData<Boolean> forceRefreshTrigger = new MutableLiveData<>(true);

    public AssignmentViewModel() {
        this.academicRepository = AcademicRepository.getInstance();
    }

    public LiveData<Resource<List<Assignment>>> getAssignments() {
        return Transformations.switchMap(forceRefreshTrigger, trigger ->
                academicRepository.getAssignments()
        );
    }

    public void toggleAssignmentCompletion(String assignmentId, boolean isCompleted) {
        academicRepository.toggleAssignmentCompletion(assignmentId, isCompleted);
        forceRefreshTrigger.setValue(true);
    }

    public void addAssignment(Assignment assignment) {
        academicRepository.addAssignment(assignment);
        forceRefreshTrigger.setValue(true);
    }
}
