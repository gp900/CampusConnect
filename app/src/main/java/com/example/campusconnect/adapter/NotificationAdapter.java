package com.example.campusconnect.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusconnect.R;
import com.example.campusconnect.model.NotificationItem;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private final List<NotificationItem> notifications;

    public NotificationAdapter(List<NotificationItem> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.bind(notifications.get(position));
    }

    @Override
    public int getItemCount() {
        return notifications != null ? notifications.size() : 0;
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivNotifIcon;
        private final TextView tvNotifTitle;
        private final TextView tvNotifMessage;
        private final TextView tvNotifTime;
        private final View vUnreadDot;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            ivNotifIcon = itemView.findViewById(R.id.ivNotifIcon);
            tvNotifTitle = itemView.findViewById(R.id.tvNotifTitle);
            tvNotifMessage = itemView.findViewById(R.id.tvNotifMessage);
            tvNotifTime = itemView.findViewById(R.id.tvNotifTime);
            vUnreadDot = itemView.findViewById(R.id.vUnreadDot);
        }

        public void bind(NotificationItem item) {
            tvNotifTitle.setText(item.getTitle());
            tvNotifMessage.setText(item.getMessage());
            tvNotifTime.setText(item.getTimestamp());
            
            vUnreadDot.setVisibility(item.isRead() ? View.GONE : View.VISIBLE);

            String type = item.getType();
            if ("CLASS".equalsIgnoreCase(type)) {
                ivNotifIcon.setImageResource(R.drawable.ic_notification_class);
                ivNotifIcon.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.bg_circle_blue));
            } else if ("ASSIGNMENT".equalsIgnoreCase(type)) {
                ivNotifIcon.setImageResource(R.drawable.ic_notification_assignment);
                ivNotifIcon.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.bg_circle_red));
            } else if ("COMPLAINT".equalsIgnoreCase(type)) {
                ivNotifIcon.setImageResource(R.drawable.ic_campus);
                ivNotifIcon.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.bg_circle_yellow));
            } else {
                ivNotifIcon.setImageResource(R.drawable.ic_academics);
                ivNotifIcon.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.bg_circle_green));
            }

            itemView.setOnClickListener(v -> {
                item.setRead(true);
                vUnreadDot.setVisibility(View.GONE);
            });
        }
    }
}
