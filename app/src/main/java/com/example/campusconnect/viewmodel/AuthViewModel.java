package com.example.campusconnect.viewmodel;

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

    public LiveData<Resource<User>> login(String email, String password) {
        return authRepository.loginUser(email, password);
    }

    public LiveData<Resource<User>> register(User user, String password) {
        return authRepository.registerUser(user, password);
    }
}
