package com.example.campusconnect.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusconnect.R;
import com.example.campusconnect.model.LostFoundItem;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class LostFoundAdapter extends RecyclerView.Adapter<LostFoundAdapter.LostFoundViewHolder> {

    private final List<LostFoundItem> items;
    private final OnContactClickListener listener;

    public interface OnContactClickListener {
        void onContactClicked(LostFoundItem item);
    }

    public LostFoundAdapter(List<LostFoundItem> items, OnContactClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LostFoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lost_found, parent, false);
        return new LostFoundViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LostFoundViewHolder holder, int position) {
        holder.bind(items.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public static class LostFoundViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvBadgeCategory;
        private final TextView tvItemTitle;
        private final TextView tvLocation;
        private final TextView tvDate;
        private final MaterialButton btnContact;

        public LostFoundViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBadgeCategory = itemView.findViewById(R.id.tvBadgeCategory);
            tvItemTitle = itemView.findViewById(R.id.tvItemTitle);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvDate = itemView.findViewById(R.id.tvDate);
            btnContact = itemView.findViewById(R.id.btnContact);
        }

        public void bind(LostFoundItem item, OnContactClickListener listener) {
            tvItemTitle.setText(item.getTitle());
            tvLocation.setText(itemView.getContext().getString(R.string.location_format, item.getLocation()));
            tvDate.setText(itemView.getContext().getString(R.string.date_format, item.getDatePosted()));
            btnContact.setText(itemView.getContext().getString(R.string.contact_format, item.getContactInfo()));

            tvBadgeCategory.setText(item.getCategory());
            if ("LOST".equalsIgnoreCase(item.getCategory())) {
                tvBadgeCategory.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.error));
                tvBadgeCategory.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.error_light));
            } else {
                tvBadgeCategory.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.success));
                tvBadgeCategory.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.success_light));
            }

            btnContact.setOnClickListener(v -> {
                if (listener != null) listener.onContactClicked(item);
            });
        }
    }
}
