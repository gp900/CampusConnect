package com.example.campusconnect.viewmodel;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.campusconnect.model.NotificationItem;
import com.example.campusconnect.repository.NotificationRepository;
import com.example.campusconnect.utils.Resource;

import java.util.List;

public class NotificationViewModel extends AndroidViewModel {

    private final NotificationRepository repository;
    private final MutableLiveData<Boolean> refreshTrigger = new MutableLiveData<>(true);

    public NotificationViewModel(android.app.Application application) {
        super(application);
        repository = NotificationRepository.getInstance(application);
    }

    public LiveData<Resource<List<NotificationItem>>> getNotifications() {
        return androidx.lifecycle.Transformations.switchMap(refreshTrigger, trigger -> 
                repository.getNotifications()
        );
    }

    public void markAsRead(String id) {
        repository.markAsRead(id);
    }
}
