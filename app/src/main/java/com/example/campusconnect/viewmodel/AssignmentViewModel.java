package com.example.campusconnect.viewmodel;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.campusconnect.model.Assignment;
import com.example.campusconnect.repository.AcademicRepository;
import com.example.campusconnect.utils.Resource;

import java.util.List;

public class AssignmentViewModel extends AndroidViewModel {

    private final AcademicRepository academicRepository;
    private final MutableLiveData<Boolean> forceRefreshTrigger = new MutableLiveData<>(true);

    public AssignmentViewModel(android.app.Application application) {
        super(application);
        this.academicRepository = AcademicRepository.getInstance(application);
    }

    public LiveData<Resource<List<Assignment>>> getAssignments() {
        return Transformations.switchMap(forceRefreshTrigger, trigger ->
                academicRepository.getAssignments()
        );
    }

    public void toggleAssignmentCompletion(Assignment assignment) {
        assignment.setCompleted(!assignment.isCompleted());
        academicRepository.updateAssignmentCompletion(assignment);
        forceRefreshTrigger.setValue(true);
    }

    public void addAssignment(Assignment assignment) {
        academicRepository.addAssignment(assignment);
        forceRefreshTrigger.setValue(true);
    }
    
    public void deleteAssignment(String id) {
        academicRepository.deleteAssignment(id);
        forceRefreshTrigger.setValue(true);
    }
}
