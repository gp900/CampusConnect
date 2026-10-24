package com.example.campusconnect.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusconnect.R;
import com.example.campusconnect.model.Event;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Event> events;
    private final boolean isFeaturedLayout;
    private final OnEventClickListener listener;

    private static final int VIEW_TYPE_FEATURED = 1;
    private static final int VIEW_TYPE_NORMAL = 2;

    public interface OnEventClickListener {
        void onRsvpClicked(Event event, int position);
    }

    public EventAdapter(List<Event> events, boolean isFeaturedLayout, OnEventClickListener listener) {
        this.events = events;
        this.isFeaturedLayout = isFeaturedLayout;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return isFeaturedLayout ? VIEW_TYPE_FEATURED : VIEW_TYPE_NORMAL;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_FEATURED) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_event_featured, parent, false);
            return new FeaturedEventViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_event_normal, parent, false);
            return new NormalEventViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Event event = events.get(position);
        if (holder instanceof FeaturedEventViewHolder) {
            ((FeaturedEventViewHolder) holder).bind(event, listener);
        } else if (holder instanceof NormalEventViewHolder) {
            ((NormalEventViewHolder) holder).bind(event, listener);
        }
    }

    @Override
    public int getItemCount() {
        return events != null ? events.size() : 0;
    }

    // ==========================================
    // VIEW HOLDERS
    // ==========================================

    static class FeaturedEventViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvEventTitle;
        private final TextView tvEventDate;
        private final MaterialButton btnRsvp;

        public FeaturedEventViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEventTitle = itemView.findViewById(R.id.tvEventTitle);
            tvEventDate = itemView.findViewById(R.id.tvEventDate);
            btnRsvp = itemView.findViewById(R.id.btnRsvp);
        }

        public void bind(Event event, OnEventClickListener listener) {
            tvEventTitle.setText(event.getTitle());
            String dateTime = event.getDate() + ", " + event.getTime();
            tvEventDate.setText(dateTime);

            setupRsvpButton(btnRsvp, event, listener, getBindingAdapterPosition());
        }
    }

    static class NormalEventViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvMonth;
        private final TextView tvDay;
        private final TextView tvEventTitle;
        private final TextView tvEventOrganizer;
        private final TextView tvEventTime;
        private final TextView tvEventVenue;
        private final MaterialButton btnRsvp;

        public NormalEventViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMonth = itemView.findViewById(R.id.tvMonth);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvEventTitle = itemView.findViewById(R.id.tvEventTitle);
            tvEventOrganizer = itemView.findViewById(R.id.tvEventOrganizer);
            tvEventTime = itemView.findViewById(R.id.tvEventTime);
            tvEventVenue = itemView.findViewById(R.id.tvEventVenue);
            btnRsvp = itemView.findViewById(R.id.btnRsvp);
        }

        public void bind(Event event, OnEventClickListener listener) {
            tvEventTitle.setText(event.getTitle());
            tvEventOrganizer.setText(itemView.getContext().getString(R.string.organizer_format, event.getOrganizer()));
            tvEventTime.setText(event.getTime());
            tvEventVenue.setText(event.getVenue());

            // Extract month and day from "Sep 15" format
            String[] dateParts = event.getDate().split(" ");
            if (dateParts.length >= 2) {
                tvMonth.setText(dateParts[0].toUpperCase());
                tvDay.setText(dateParts[1]);
            } else {
                tvMonth.setText("");
                tvDay.setText(event.getDate());
            }

            setupRsvpButton(btnRsvp, event, listener, getBindingAdapterPosition());
        }
    }

    private static void setupRsvpButton(MaterialButton btnRsvp, Event event, OnEventClickListener listener, int position) {
        if (event.isRsvped()) {
            btnRsvp.setText(R.string.rsvpd_button);
            btnRsvp.setBackgroundTintList(ContextCompat.getColorStateList(btnRsvp.getContext(), R.color.border));
            btnRsvp.setTextColor(ContextCompat.getColor(btnRsvp.getContext(), R.color.text_secondary));
        } else {
            btnRsvp.setText(R.string.rsvp_button);
            btnRsvp.setBackgroundTintList(ContextCompat.getColorStateList(btnRsvp.getContext(), R.color.primaryBlue));
            btnRsvp.setTextColor(ContextCompat.getColor(btnRsvp.getContext(), R.color.white));
        }

        btnRsvp.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRsvpClicked(event, position);
            }
        });
    }
}
