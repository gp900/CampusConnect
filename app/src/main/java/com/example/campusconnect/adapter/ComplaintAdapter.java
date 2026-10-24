package com.example.campusconnect.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusconnect.R;
import com.example.campusconnect.model.Complaint;

import java.util.List;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder> {

    private final List<Complaint> complaints;

    public ComplaintAdapter(List<Complaint> complaints) {
        this.complaints = complaints;
    }

    @NonNull
    @Override
    public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_complaint, parent, false);
        return new ComplaintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintViewHolder holder, int position) {
        holder.bind(complaints.get(position));
    }

    @Override
    public int getItemCount() {
        return complaints != null ? complaints.size() : 0;
    }

    public static class ComplaintViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTicketId;
        private final TextView tvStatusBadge;
        private final TextView tvSubject;
        private final TextView tvDescription;
        private final TextView tvCategory;
        private final TextView tvDate;

        public ComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTicketId = itemView.findViewById(R.id.tvTicketId);
            tvStatusBadge = itemView.findViewById(R.id.tvStatusBadge);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDate = itemView.findViewById(R.id.tvDate);
        }

        public void bind(Complaint item) {
            tvTicketId.setText(itemView.getContext().getString(R.string.complaint_id_format, item.getId()));
            tvSubject.setText(item.getSubject());
            tvDescription.setText(item.getDescription());
            tvCategory.setText(item.getCategory());
            tvDate.setText(item.getDateSubmitted());

            String status = item.getStatus();
            if ("RESOLVED".equalsIgnoreCase(status)) {
                tvStatusBadge.setText(R.string.status_resolved);
                tvStatusBadge.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.success));
                tvStatusBadge.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.success_light));
            } else if ("IN_REVIEW".equalsIgnoreCase(status)) {
                tvStatusBadge.setText(R.string.status_in_review);
                tvStatusBadge.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.warning));
                tvStatusBadge.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.warning_light));
            } else {
                tvStatusBadge.setText(R.string.status_submitted);
                tvStatusBadge.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.primaryBlue));
                tvStatusBadge.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.primaryBlueLight));
            }
        }
    }
}
