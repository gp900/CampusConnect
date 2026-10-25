package com.example.campusconnect.viewmodel;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.campusconnect.model.Note;
import com.example.campusconnect.repository.AcademicRepository;
import com.example.campusconnect.utils.Resource;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    private final AcademicRepository academicRepository;
    private final MutableLiveData<Boolean> forceRefreshTrigger = new MutableLiveData<>(true);

    public NotesViewModel(android.app.Application application) {
        super(application);
        this.academicRepository = AcademicRepository.getInstance(application);
    }

    public LiveData<Resource<List<Note>>> getNotes() {
        return Transformations.switchMap(forceRefreshTrigger, trigger ->
                academicRepository.getNotes()
        );
    }

    public void addNote(Note note) {
        academicRepository.addNote(note);
        forceRefreshTrigger.setValue(true);
    }
}
