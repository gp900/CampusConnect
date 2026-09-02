package com.example.campusconnect.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusconnect.R;
import com.example.campusconnect.model.OnboardingItem;

import java.util.List;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder> {

    private final List<OnboardingItem> onboardingItems;

    public OnboardingAdapter(List<OnboardingItem> onboardingItems) {
        this.onboardingItems = onboardingItems;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_onboarding, parent, false);
        return new OnboardingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
        holder.bind(onboardingItems.get(position));
    }

    @Override
    public int getItemCount() {
        return onboardingItems != null ? onboardingItems.size() : 0;
    }

    public static class OnboardingViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvIcon;
        private final TextView tvTitle;
        private final TextView tvDesc;

        public OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIcon = itemView.findViewById(R.id.tvOnboardingIcon);
            tvTitle = itemView.findViewById(R.id.tvOnboardingTitle);
            tvDesc = itemView.findViewById(R.id.tvOnboardingDesc);
        }

        public void bind(OnboardingItem item) {
            tvIcon.setText(item.getIconEmoji());
            tvTitle.setText(item.getTitle());
            tvDesc.setText(item.getDescription());
        }
    }
}
