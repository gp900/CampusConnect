package com.example.campusconnect.ui.campus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.campusconnect.R;

public class CampusEventsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_campus_placeholder, container, false);
        TextView title = view.findViewById(R.id.tvPlaceholderTitle);
        TextView icon = view.findViewById(R.id.tvPlaceholderIcon);
        title.setText("Campus Events");
        icon.setText("🎉");
        return view;
    }
}