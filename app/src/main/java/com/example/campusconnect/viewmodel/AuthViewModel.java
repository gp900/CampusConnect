package com.example.campusconnect.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.campusconnect.model.User;
import com.example.campusconnect.repository.AuthRepository;
import com.example.campusconnect.utils.Resource;

public class AuthViewModel extends ViewModel {

    private final AuthRepository authRepository;

    public AuthViewModel() {
        this.authRepository = AuthRepository.getInstance();
    }

    public LiveData<Resource<User>> login(Context context, String email, String password) {
        return authRepository.loginUser(context, email, password);
    }

    public LiveData<Resource<User>> register(Context context, User user, String password) {
        return authRepository.registerUser(context, user, password);
    }
}
