package com.example.campusconnect.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.campusconnect.model.User;
import com.example.campusconnect.utils.Resource;
import com.example.campusconnect.utils.SharedPrefManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthRepository {

    private static AuthRepository instance;
    private final FirebaseAuth mAuth;
    private final FirebaseFirestore db;

    private AuthRepository() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public static synchronized AuthRepository getInstance() {
        if (instance == null) {
            instance = new AuthRepository();
        }
        return instance;
    }

    public LiveData<Resource<User>> loginUser(Context context, String email, String password) {
        MutableLiveData<Resource<User>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser firebaseUser = authResult.getUser();
                    if (firebaseUser != null) {
                        fetchUserFromFirestore(context, firebaseUser.getUid(), result);
                    } else {
                        result.setValue(Resource.error("Login failed: User not found."));
                    }
                })
                .addOnFailureListener(e -> result.setValue(Resource.error(e.getLocalizedMessage())));

        return result;
    }

    public LiveData<Resource<User>> registerUser(Context context, User user, String password) {
        MutableLiveData<Resource<User>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        mAuth.createUserWithEmailAndPassword(user.getEmail(), password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser firebaseUser = authResult.getUser();
                    if (firebaseUser != null) {
                        user.setUserId(firebaseUser.getUid());
                        saveUserToFirestore(context, user, result);
                    } else {
                        result.setValue(Resource.error("Registration failed."));
                    }
                })
                .addOnFailureListener(e -> result.setValue(Resource.error(e.getLocalizedMessage())));

        return result;
    }

    private void fetchUserFromFirestore(Context context, String uid, MutableLiveData<Resource<User>> result) {
        db.collection("users").document(uid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    User user = documentSnapshot.toObject(User.class);
                    if (user != null) {
                        SharedPrefManager.getInstance(context).saveUserSession(user);
                        result.setValue(Resource.success(user));
                    } else {
                        result.setValue(Resource.error("User profile data not found in database."));
                    }
                })
                .addOnFailureListener(e -> result.setValue(Resource.error(e.getLocalizedMessage())));
    }

    private void saveUserToFirestore(Context context, User user, MutableLiveData<Resource<User>> result) {
        db.collection("users").document(user.getUserId()).set(user)
                .addOnSuccessListener(aVoid -> {
                    SharedPrefManager.getInstance(context).saveUserSession(user);
                    result.setValue(Resource.success(user));
                })
                .addOnFailureListener(e -> result.setValue(Resource.error("Failed to create profile: " + e.getLocalizedMessage())));
    }
}
