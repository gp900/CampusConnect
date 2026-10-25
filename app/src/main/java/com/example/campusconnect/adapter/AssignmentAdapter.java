package com.example.campusconnect.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusconnect.R;
import com.example.campusconnect.model.Assignment;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {

    private final List<Assignment> assignments;
    private final OnAssignmentUpdateListener listener;

    public interface OnAssignmentUpdateListener {
        void onAssignmentToggled(Assignment assignment, boolean isCompleted);
        void onDeleteClicked(Assignment assignment);
    }

    public AssignmentAdapter(List<Assignment> assignments, OnAssignmentUpdateListener listener) {
        this.assignments = assignments;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_assignment, parent, false);
        return new AssignmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {
        Assignment item = assignments.get(position);
        holder.bind(item, listener);
        
        if (holder.ivDelete != null) {
            holder.ivDelete.setOnClickListener(v -> {
                if (listener != null) listener.onDeleteClicked(item);
            });
        }
    }

    @Override
    public int getItemCount() {
        return assignments != null ? assignments.size() : 0;
    }

    public static class AssignmentViewHolder extends RecyclerView.ViewHolder {

        private final MaterialCheckBox cbCompleted;
        private final TextView tvTitle;
        private final TextView tvSubject;
        private final TextView tvDueDate;
        private final TextView tvPriorityBadge;
        final View ivDelete;

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            cbCompleted = itemView.findViewById(R.id.cbCompleted);
            tvTitle = itemView.findViewById(R.id.tvAssignmentTitle);
            tvSubject = itemView.findViewById(R.id.tvAssignmentSubject);
            tvDueDate = itemView.findViewById(R.id.tvDueDate);
            tvPriorityBadge = itemView.findViewById(R.id.tvPriorityBadge);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }

        public void bind(Assignment item, OnAssignmentUpdateListener listener) {
            tvTitle.setText(item.getTitle());
            tvSubject.setText(item.getSubjectName());
            tvDueDate.setText(itemView.getContext().getString(R.string.due_date_format, item.getDueDate()));
            
            // Priority logic
            tvPriorityBadge.setText(item.getPriority());
            if ("HIGH".equalsIgnoreCase(item.getPriority())) {
                tvPriorityBadge.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.error));
                tvPriorityBadge.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.error_light));
            } else if ("MEDIUM".equalsIgnoreCase(item.getPriority())) {
                tvPriorityBadge.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.warning));
                tvPriorityBadge.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.warning_light));
            } else {
                tvPriorityBadge.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.primaryBlue));
                tvPriorityBadge.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.primaryBlueLight));
            }

            // Temporarily remove listener to avoid triggering it when setting state manually
            cbCompleted.setOnCheckedChangeListener(null);
            cbCompleted.setChecked(item.isCompleted());
            cbCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (listener != null) {
                    listener.onAssignmentToggled(item, isChecked);
                }
            });

            // visual strike-through if completed
            if (item.isCompleted()) {
                tvTitle.setPaintFlags(tvTitle.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
                tvTitle.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.text_secondary));
            } else {
                tvTitle.setPaintFlags(tvTitle.getPaintFlags() & (~android.graphics.Paint.STRIKE_THRU_TEXT_FLAG));
                tvTitle.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.text_primary));
            }
        }
    }
}