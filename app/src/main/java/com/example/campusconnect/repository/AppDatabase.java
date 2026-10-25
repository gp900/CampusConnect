package com.example.campusconnect.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.campusconnect.model.Announcement;
import com.example.campusconnect.model.Assignment;
import com.example.campusconnect.model.AttendanceItem;
import com.example.campusconnect.model.Club;
import com.example.campusconnect.model.Complaint;
import com.example.campusconnect.model.Event;
import com.example.campusconnect.model.LostFoundItem;
import com.example.campusconnect.model.Note;
import com.example.campusconnect.model.NotificationItem;
import com.example.campusconnect.model.TimetableItem;

@Database(entities = {
        Assignment.class,
        Note.class,
        TimetableItem.class,
        AttendanceItem.class,
        Event.class,
        LostFoundItem.class,
        Club.class,
        Announcement.class,
        Complaint.class,
        NotificationItem.class
}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;

    public abstract AcademicDao academicDao();
    public abstract CampusDao campusDao();
    public abstract NotificationDao notificationDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "campus_connect_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
