package com.example.campusconnect.repository;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.campusconnect.model.User;
import com.example.campusconnect.utils.Resource;

public class AuthRepository {

    private static AuthRepository instance;

    private AuthRepository() {
    }

    public static synchronized AuthRepository getInstance() {
        if (instance == null) {
            instance = new AuthRepository();
        }
        return instance;
    }

    public LiveData<Resource<User>> loginUser(String email, String password) {
        MutableLiveData<Resource<User>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        // Simulate network delay for authentication
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (email.contains("@") && password.length() >= 6) {
                User user = new User("user_101", "Alex Student", email, "B.Tech", "Computer Science", 5, "CS202301");
                result.setValue(Resource.success(user));
            } else {
                result.setValue(Resource.error("Invalid credentials provided."));
            }
        }, 1200);

        return result;
    }

    public LiveData<Resource<User>> registerUser(User user, String password) {
        MutableLiveData<Resource<User>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            user.setUserId("user_" + System.currentTimeMillis());
            result.setValue(Resource.success(user));
        }, 1200);

        return result;
    }
}
