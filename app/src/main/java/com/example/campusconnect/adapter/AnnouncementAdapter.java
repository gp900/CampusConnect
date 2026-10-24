package com.example.campusconnect.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusconnect.R;
import com.example.campusconnect.model.Announcement;

import java.util.List;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.AnnouncementViewHolder> {

    private final List<Announcement> announcements;

    public AnnouncementAdapter(List<Announcement> announcements) {
        this.announcements = announcements;
    }

    @NonNull
    @Override
    public AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_announcement, parent, false);
        return new AnnouncementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementViewHolder holder, int position) {
        holder.bind(announcements.get(position));
    }

    @Override
    public int getItemCount() {
        return announcements != null ? announcements.size() : 0;
    }

    public static class AnnouncementViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvBadgeNotice;
        private final TextView tvNoticeTitle;
        private final TextView tvNoticeContent;
        private final TextView tvNoticeDate;
        private final TextView tvNoticeAuthor;

        public AnnouncementViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBadgeNotice = itemView.findViewById(R.id.tvBadgeNotice);
            tvNoticeTitle = itemView.findViewById(R.id.tvNoticeTitle);
            tvNoticeContent = itemView.findViewById(R.id.tvNoticeContent);
            tvNoticeDate = itemView.findViewById(R.id.tvNoticeDate);
            tvNoticeAuthor = itemView.findViewById(R.id.tvNoticeAuthor);
        }

        public void bind(Announcement item) {
            tvNoticeTitle.setText(item.getTitle());
            tvNoticeContent.setText(item.getContent());
            tvNoticeDate.setText(item.getDatePosted());
            tvNoticeAuthor.setText(itemView.getContext().getString(R.string.posted_by_format, item.getAuthor()));

            tvBadgeNotice.setText(item.getCategory());
            String cat = item.getCategory();

            if ("URGENT".equalsIgnoreCase(cat) || "EXAM".equalsIgnoreCase(cat)) {
                tvBadgeNotice.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.error));
                tvBadgeNotice.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.error_light));
            } else if ("HOLIDAY".equalsIgnoreCase(cat)) {
                tvBadgeNotice.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.success));
                tvBadgeNotice.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.success_light));
            } else {
                tvBadgeNotice.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.primaryBlue));
                tvBadgeNotice.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.primaryBlueLight));
            }
        }
    }
}
