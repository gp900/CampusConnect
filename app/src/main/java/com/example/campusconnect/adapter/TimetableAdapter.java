package com.example.campusconnect.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusconnect.R;
import com.example.campusconnect.model.TimetableItem;

import java.util.List;

public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.TimetableViewHolder> {

    private final List<TimetableItem> timetableItems;

    public TimetableAdapter(List<TimetableItem> timetableItems) {
        this.timetableItems = timetableItems;
    }

    @NonNull
    @Override
    public TimetableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_timetable, parent, false);
        return new TimetableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimetableViewHolder holder, int position) {
        holder.bind(timetableItems.get(position));
    }

    @Override
    public int getItemCount() {
        return timetableItems != null ? timetableItems.size() : 0;
    }

    public static class TimetableViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvSubjectCode;
        private final TextView tvStatusBadge;
        private final TextView tvSubjectName;
        private final TextView tvTimeSlot;
        private final TextView tvRoomAndProf;

        public TimetableViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubjectCode = itemView.findViewById(R.id.tvSubjectCode);
            tvStatusBadge = itemView.findViewById(R.id.tvStatusBadge);
            tvSubjectName = itemView.findViewById(R.id.tvSubjectName);
            tvTimeSlot = itemView.findViewById(R.id.tvTimeSlot);
            tvRoomAndProf = itemView.findViewById(R.id.tvRoomAndProf);
        }

        public void bind(TimetableItem item) {
            tvSubjectCode.setText(item.getSubjectCode());
            tvSubjectName.setText(item.getSubjectName());
            String timeText = "🕒 " + item.getStartTime() + " - " + item.getEndTime();
            tvTimeSlot.setText(timeText);
            String roomProfText = "📍 " + item.getRoomNumber() + " • " + item.getProfessorName();
            tvRoomAndProf.setText(roomProfText);

            String status = item.getStatus();
            if ("ONGOING".equalsIgnoreCase(status)) {
                tvStatusBadge.setText(R.string.status_ongoing);
                tvStatusBadge.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.success));
                tvStatusBadge.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.success_light));
            } else if ("COMPLETED".equalsIgnoreCase(status)) {
                tvStatusBadge.setText(R.string.status_completed);
                tvStatusBadge.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.text_secondary));
                tvStatusBadge.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.border));
            } else {
                tvStatusBadge.setText(R.string.status_upcoming);
                tvStatusBadge.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.primaryBlue));
                tvStatusBadge.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.primaryBlueLight));
            }
        }
    }
}
