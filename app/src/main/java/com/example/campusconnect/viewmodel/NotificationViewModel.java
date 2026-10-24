package com.example.campusconnect.viewmodel;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.campusconnect.model.NotificationItem;
import com.example.campusconnect.utils.Resource;

import java.util.ArrayList;
import java.util.List;

public class NotificationViewModel extends ViewModel {

    private final MutableLiveData<Resource<List<NotificationItem>>> notificationsLiveData = new MutableLiveData<>();
    private List<NotificationItem> mockNotifications;

    public LiveData<Resource<List<NotificationItem>>> getNotifications() {
        fetchNotifications();
        return notificationsLiveData;
    }

    private void fetchNotifications() {
        notificationsLiveData.setValue(Resource.loading());

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (mockNotifications == null) {
                mockNotifications = new ArrayList<>();
                mockNotifications.add(new NotificationItem("n1", "Class Starting Soon", "Data Structures & Algorithms starts in 15 minutes in Room 302.", "CLASS", "Just now", false));
                mockNotifications.add(new NotificationItem("n2", "Assignment Due Tomorrow", "Chapter 4 Exercise Questions due tomorrow at 11:59 PM.", "ASSIGNMENT", "2h ago", false));
                mockNotifications.add(new NotificationItem("n3", "Complaint Resolved", "Ticket #CMP1040 regarding Missing Grades has been resolved.", "COMPLAINT", "Yesterday", true));
                mockNotifications.add(new NotificationItem("n4", "New Campus Event", "Registration for Annual Tech Symposium is now open!", "ANNOUNCEMENT", "Oct 12", true));
            }
            notificationsLiveData.setValue(Resource.success(new ArrayList<>(mockNotifications)));
        }, 300);
    }
}
