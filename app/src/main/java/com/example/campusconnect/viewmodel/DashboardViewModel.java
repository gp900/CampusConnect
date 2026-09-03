package com.example.campusconnect.viewmodel;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.campusconnect.model.DashboardData;
import com.example.campusconnect.utils.Resource;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<Resource<DashboardData>> dashboardDataLiveData = new MutableLiveData<>();

    public LiveData<Resource<DashboardData>> getDashboardData() {
        fetchDashboardData();
        return dashboardDataLiveData;
    }

    private void fetchDashboardData() {
        dashboardDataLiveData.setValue(Resource.loading());

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            DashboardData data = new DashboardData(
                    "Alex Student",
                    "B.Tech • Computer Science • Semester 5",
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
        }, 500);
    }
}
