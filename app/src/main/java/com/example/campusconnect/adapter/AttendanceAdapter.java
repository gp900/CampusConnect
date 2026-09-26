package com.example.campusconnect.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusconnect.R;
import com.example.campusconnect.model.AttendanceItem;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder> {

    private final List<AttendanceItem> attendanceItems;
    private final OnAttendanceUpdateListener listener;

    public interface OnAttendanceUpdateListener {
        void onPresentClicked(AttendanceItem item, int position);
        void onAbsentClicked(AttendanceItem item, int position);
    }

    public AttendanceAdapter(List<AttendanceItem> attendanceItems, OnAttendanceUpdateListener listener) {
        this.attendanceItems = attendanceItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attendance, parent, false);
        return new AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewHolder holder, int position) {
        AttendanceItem item = attendanceItems.get(position);
        holder.bind(item);

        holder.btnPresent.setOnClickListener(v -> {
            if (listener != null) listener.onPresentClicked(item, position);
        });

        holder.btnAbsent.setOnClickListener(v -> {
            if (listener != null) listener.onAbsentClicked(item, position);
        });
    }

    @Override
    public int getItemCount() {
        return attendanceItems != null ? attendanceItems.size() : 0;
    }

    public static class AttendanceViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvSubjectCode;
        private final TextView tvSubjectName;
        private final TextView tvStatusBadge;
        private final TextView tvPercentage;
        private final TextView tvClassesCount;
        private final LinearProgressIndicator progressAttendance;
        final View btnPresent;
        final View btnAbsent;

        public AttendanceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubjectCode = itemView.findViewById(R.id.tvSubjectCode);
            tvSubjectName = itemView.findViewById(R.id.tvSubjectName);
            tvStatusBadge = itemView.findViewById(R.id.tvStatusBadge);
            tvPercentage = itemView.findViewById(R.id.tvPercentage);
            tvClassesCount = itemView.findViewById(R.id.tvClassesCount);
            progressAttendance = itemView.findViewById(R.id.progressAttendance);
            btnPresent = itemView.findViewById(R.id.btnPresent);
            btnAbsent = itemView.findViewById(R.id.btnAbsent);
        }

        public void bind(AttendanceItem item) {
            tvSubjectCode.setText(item.getSubjectCode());
            tvSubjectName.setText(item.getSubjectName());

            int percentage = item.getPercentage();
            tvPercentage.setText(percentage + "%");
            tvClassesCount.setText(item.getClassesAttended() + " / " + item.getTotalClasses() + " Attended");
            progressAttendance.setProgressCompat(percentage, true);

            if (item.isSafe()) {
                tvStatusBadge.setText("Safe");
                tvStatusBadge.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.success));
                tvStatusBadge.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.success_light));
                progressAttendance.setIndicatorColor(ContextCompat.getColor(itemView.getContext(), R.color.success));
            } else {
                tvStatusBadge.setText("Danger");
                tvStatusBadge.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.error));
                tvStatusBadge.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.error_light));
                progressAttendance.setIndicatorColor(ContextCompat.getColor(itemView.getContext(), R.color.error));
            }
        }
    }
}
