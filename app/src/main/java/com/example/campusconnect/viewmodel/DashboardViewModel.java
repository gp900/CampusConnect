package com.example.campusconnect.viewmodel;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.campusconnect.model.DashboardData;
import com.example.campusconnect.model.User;
import com.example.campusconnect.utils.Resource;
import com.example.campusconnect.utils.SharedPrefManager;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<Resource<DashboardData>> dashboardDataLiveData = new MutableLiveData<>();

    public LiveData<Resource<DashboardData>> getDashboardData(Context context) {
        fetchDashboardData(context);
        return dashboardDataLiveData;
    }

    private void fetchDashboardData(Context context) {
        dashboardDataLiveData.setValue(Resource.loading());

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            User activeUser = SharedPrefManager.getInstance(context).getUserSession();
            String name = activeUser != null && activeUser.getFullName() != null ? activeUser.getFullName() : "Student";
            String course = activeUser != null && activeUser.getCourse() != null ? activeUser.getCourse() : "B.Tech";
            String dept = activeUser != null && activeUser.getDepartment() != null ? activeUser.getDepartment() : "Computer Science";
            int sem = activeUser != null ? activeUser.getSemester() : 1;

            String subtitle = course + " • " + dept + " • Semester " + sem;

            DashboardData data = new DashboardData(
                    name,
                    subtitle,
                    "Data Structures & Algorithms",
                    "10:00 AM - 11:00 AM • Room 302",
                    "Dr. Robert Vance",
                    "Starts in 15 mins",
                    82,
                    "Safe Zone",
                    3,
                    1,
                    "Annual Tech Symposium 2026",
                    "Sep 15 • Main Auditorium"
            );
            dashboardDataLiveData.setValue(Resource.success(data));
        }, 300);
    }
}
