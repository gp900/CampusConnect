package com.example.campusconnect.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.campusconnect.model.NotificationItem;
import com.example.campusconnect.utils.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotificationRepository {

    private static NotificationRepository instance;
    private final NotificationDao notificationDao;
    private final ExecutorService executor;

    private NotificationRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.notificationDao = db.notificationDao();
        this.executor = Executors.newSingleThreadExecutor();
        seedInitialData();
    }

    public static synchronized NotificationRepository getInstance(Context context) {
        if (instance == null) {
            instance = new NotificationRepository(context);
        }
        return instance;
    }

    private void seedInitialData() {
        executor.execute(() -> {
            if (notificationDao.getNotifications().getValue() == null || notificationDao.getNotifications().getValue().isEmpty()) {
                List<NotificationItem> defaultNotifications = new ArrayList<>();
                defaultNotifications.add(new NotificationItem("n1", "Class Starting Soon", "Data Structures & Algorithms starts in 15 minutes in Room 302.", "CLASS", "Just now", false));
                defaultNotifications.add(new NotificationItem("n2", "Assignment Due Tomorrow", "Chapter 4 Exercise Questions due tomorrow at 11:59 PM.", "ASSIGNMENT", "2h ago", false));
                defaultNotifications.add(new NotificationItem("n3", "Complaint Resolved", "Ticket #CMP1040 regarding Missing Grades has been resolved.", "COMPLAINT", "Yesterday", true));
                defaultNotifications.add(new NotificationItem("n4", "New Campus Event", "Registration for Annual Tech Symposium is now open!", "ANNOUNCEMENT", "Oct 12", true));
                notificationDao.insertNotifications(defaultNotifications);
            }
        });
    }

    public LiveData<Resource<List<NotificationItem>>> getNotifications() {
        MutableLiveData<Resource<List<NotificationItem>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());
        notificationDao.getNotifications().observeForever(items -> {
            if (items != null) result.setValue(Resource.success(items));
        });
        return result;
    }

    public void markAsRead(String id) {
        executor.execute(() -> notificationDao.markAsRead(id));
    }
}
