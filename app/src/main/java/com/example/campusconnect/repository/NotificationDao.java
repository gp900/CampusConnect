package com.example.campusconnect.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.campusconnect.model.NotificationItem;

import java.util.List;

@Dao
public interface NotificationDao {

    @Query("SELECT * FROM notifications ORDER BY timestamp DESC")
    LiveData<List<NotificationItem>> getNotifications();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNotifications(List<NotificationItem> notifications);

    @Query("UPDATE notifications SET isRead = 1 WHERE id = :id")
    void markAsRead(String id);

    @Query("DELETE FROM notifications")
    void clearNotifications();
}
