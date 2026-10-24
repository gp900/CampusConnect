package com.example.campusconnect.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusconnect.R;
import com.example.campusconnect.model.Club;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.ClubViewHolder> {

    private final List<Club> clubs;
    private final OnClubClickListener listener;

    public interface OnClubClickListener {
        void onJoinToggled(Club club);
    }

    public ClubAdapter(List<Club> clubs, OnClubClickListener listener) {
        this.clubs = clubs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_club, parent, false);
        return new ClubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClubViewHolder holder, int position) {
        holder.bind(clubs.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return clubs != null ? clubs.size() : 0;
    }

    public static class ClubViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvClubName;
        private final TextView tvMembersCount;
        private final TextView tvDescription;
        private final TextView tvLeadInfo;
        private final MaterialButton btnJoinClub;

        public ClubViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClubName = itemView.findViewById(R.id.tvClubName);
            tvMembersCount = itemView.findViewById(R.id.tvMembersCount);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvLeadInfo = itemView.findViewById(R.id.tvLeadInfo);
            btnJoinClub = itemView.findViewById(R.id.btnJoinClub);
        }

        public void bind(Club club, OnClubClickListener listener) {
            tvClubName.setText(club.getName());
            tvDescription.setText(club.getDescription());
            
            tvMembersCount.setText(itemView.getContext().getString(R.string.members_format, club.getMemberCount()));
            tvLeadInfo.setText(itemView.getContext().getString(R.string.lead_format, club.getLeadName()));

            if (club.isMember()) {
                btnJoinClub.setText(R.string.btn_leave_club);
                btnJoinClub.setBackgroundTintList(ContextCompat.getColorStateList(btnJoinClub.getContext(), R.color.border));
                btnJoinClub.setTextColor(ContextCompat.getColor(btnJoinClub.getContext(), R.color.text_secondary));
            } else {
                btnJoinClub.setText(R.string.btn_join_club);
                btnJoinClub.setBackgroundTintList(ContextCompat.getColorStateList(btnJoinClub.getContext(), R.color.primaryBlue));
                btnJoinClub.setTextColor(ContextCompat.getColor(btnJoinClub.getContext(), R.color.white));
            }

            btnJoinClub.setOnClickListener(v -> {
                if (listener != null) listener.onJoinToggled(club);
            });
        }
    }
}
