package com.example.campusconnect.repository;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.campusconnect.model.AttendanceItem;
import com.example.campusconnect.model.TimetableItem;
import com.example.campusconnect.utils.Resource;

import java.util.ArrayList;
import java.util.List;

public class AcademicRepository {

    private static AcademicRepository instance;

    private AcademicRepository() {
    }

    public static synchronized AcademicRepository getInstance() {
        if (instance == null) {
            instance = new AcademicRepository();
        }
        return instance;
    }

    public LiveData<Resource<List<TimetableItem>>> getTimetableForDay(String dayOfWeek) {
        MutableLiveData<Resource<List<TimetableItem>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            List<TimetableItem> list = new ArrayList<>();

            if ("SUNDAY".equalsIgnoreCase(dayOfWeek)) {
                result.setValue(Resource.success(list));
                return;
            }

            if ("SATURDAY".equalsIgnoreCase(dayOfWeek)) {
                list.add(new TimetableItem("t101", "SATURDAY", "CS-305", "Lab - Data Structures", "Prof. Alan Turing", "Lab 2", "10:00 AM", "12:00 PM", "UPCOMING"));
                result.setValue(Resource.success(list));
                return;
            }

            list.add(new TimetableItem("t1", dayOfWeek, "CS-301", "Data Structures & Algorithms", "Dr. Robert Vance", "Room 302", "09:00 AM", "10:00 AM", "COMPLETED"));
            list.add(new TimetableItem("t2", dayOfWeek, "CS-302", "Database Management Systems", "Prof. Sarah Connor", "Room 304", "10:15 AM", "11:15 AM", "ONGOING"));
            list.add(new TimetableItem("t3", dayOfWeek, "AI-303", "Machine Learning Foundations", "Dr. John McCarthy", "Room 105", "11:30 AM", "12:30 PM", "UPCOMING"));
            list.add(new TimetableItem("t4", dayOfWeek, "CS-304", "Operating Systems", "Dr. Linus Torvalds", "Room 201", "02:00 PM", "03:00 PM", "UPCOMING"));

            result.setValue(Resource.success(list));
        }, 400);

        return result;
    }

    // ==========================================
    // ATTENDANCE MODULE LOGIC
    // ==========================================

    // Use an in-memory list for demo/local persistence purposes
    private List<AttendanceItem> mockAttendanceList;

    public LiveData<Resource<List<AttendanceItem>>> getAttendanceList() {
        MutableLiveData<Resource<List<AttendanceItem>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (mockAttendanceList == null) {
                mockAttendanceList = new ArrayList<>();
                mockAttendanceList.add(new AttendanceItem("a1", "CS-301", "Data Structures & Algorithms", 33, 40));
                mockAttendanceList.add(new AttendanceItem("a2", "CS-302", "Database Management Systems", 28, 30));
                mockAttendanceList.add(new AttendanceItem("a3", "AI-303", "Machine Learning Foundations", 18, 25));
                mockAttendanceList.add(new AttendanceItem("a4", "CS-304", "Operating Systems", 15, 22));
                mockAttendanceList.add(new AttendanceItem("a5", "MA-201", "Applied Mathematics", 12, 20)); // 60% Danger
            }
            result.setValue(Resource.success(mockAttendanceList));
        }, 300);

        return result;
    }

    public void markAttendancePresent(String itemId) {
        if (mockAttendanceList == null) return;
        for (AttendanceItem item : mockAttendanceList) {
            if (item.getId().equals(itemId)) {
                item.setClassesAttended(item.getClassesAttended() + 1);
                item.setTotalClasses(item.getTotalClasses() + 1);
                break;
            }
        }
    }

    public void markAttendanceAbsent(String itemId) {
        if (mockAttendanceList == null) return;
        for (AttendanceItem item : mockAttendanceList) {
            if (item.getId().equals(itemId)) {
                item.setTotalClasses(item.getTotalClasses() + 1);
                break;
            }
        }
    }
}

