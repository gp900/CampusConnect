package com.example.campusconnect.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.campusconnect.R;
import com.example.campusconnect.ui.auth.LoginActivity;
import com.example.campusconnect.viewmodel.AuthViewModel;

public class ProfileFragment extends Fragment {

    private AuthViewModel authViewModel;

    private TextView tvProfileName;
    private TextView tvProfileEmail;
    private TextView tvProfileRoll;
    private TextView tvAvatarLarge;
    
    private LinearLayout btnLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        initViews(view);
        setupListeners();
        observeUserData();
    }

    private void initViews(View view) {
        tvProfileName = view.findViewById(R.id.tvProfileName);
        tvProfileEmail = view.findViewById(R.id.tvProfileEmail);
        tvProfileRoll = view.findViewById(R.id.tvProfileRoll);
        tvAvatarLarge = view.findViewById(R.id.tvAvatarLarge);
        
        btnLogout = view.findViewById(R.id.btnLogout);
        
        // Setup mock clicks for non-functional settings
        view.findViewById(R.id.btnEditProfile).setOnClickListener(v -> showToast("Edit Profile clicked"));
        view.findViewById(R.id.btnMyUploads).setOnClickListener(v -> showToast("My Uploads clicked"));
        view.findViewById(R.id.btnMyComplaints).setOnClickListener(v -> showToast("My Complaints clicked"));
    }

    private void setupListeners() {
        btnLogout.setOnClickListener(v -> {
            authViewModel.logout(requireContext());
            
            // Navigate back to Login
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            // Clear backstack so user can't press back to re-enter
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            if (getActivity() != null) {
                getActivity().finish();
            }
        });
    }

    private void observeUserData() {
        authViewModel.getCurrentUser(requireContext()).observe(getViewLifecycleOwner(), resource -> {
            if (resource != null && resource.getData() != null) {
                com.example.campusconnect.model.User user = resource.getData();
                tvProfileName.setText(user.getFullName());
                tvProfileEmail.setText(user.getEmail());
                tvProfileRoll.setText(user.getRollNumber() != null ? user.getRollNumber() : "N/A");
                
                // Set avatar initials (First letter of name)
                if (user.getFullName() != null && !user.getFullName().isEmpty()) {
                    tvAvatarLarge.setText(String.valueOf(user.getFullName().charAt(0)).toUpperCase());
                } else {
                    tvAvatarLarge.setText(R.string.icon);
                }
            } else {
                // Handle case where user is null (e.g. right after logout triggers a lifecycle update)
                tvProfileName.setText("Loading...");
                tvProfileEmail.setText("");
                tvProfileRoll.setText("");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
