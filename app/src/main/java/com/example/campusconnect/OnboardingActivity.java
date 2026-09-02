package com.example.campusconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.campusconnect.adapter.OnboardingAdapter;
import com.example.campusconnect.model.OnboardingItem;
import com.example.campusconnect.utils.SharedPrefManager;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager2 vpOnboarding;
    private LinearLayout layoutIndicators;
    private MaterialButton btnSkip;
    private MaterialButton btnNext;
    private OnboardingAdapter onboardingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_onboarding);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupOnboardingItems();
        setupIndicators(onboardingAdapter.getItemCount());
        setCurrentIndicator(0);
        setupListeners();
    }

    private void initViews() {
        vpOnboarding = findViewById(R.id.vpOnboarding);
        layoutIndicators = findViewById(R.id.layoutIndicators);
        btnSkip = findViewById(R.id.btnSkip);
        btnNext = findViewById(R.id.btnNext);
    }

    private void setupOnboardingItems() {
        List<OnboardingItem> items = new ArrayList<>();
        items.add(new OnboardingItem(
                "🎓",
                getString(R.string.onboarding_title_1),
                getString(R.string.onboarding_desc_1)
        ));
        items.add(new OnboardingItem(
                "📅",
                getString(R.string.onboarding_title_2),
                getString(R.string.onboarding_desc_2)
        ));
        items.add(new OnboardingItem(
                "💬",
                getString(R.string.onboarding_title_3),
                getString(R.string.onboarding_desc_3)
        ));

        onboardingAdapter = new OnboardingAdapter(items);
        vpOnboarding.setAdapter(onboardingAdapter);
    }

    private void setupIndicators(int count) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        int margin = getResources().getDimensionPixelSize(R.dimen.spacing_small);
        layoutParams.setMargins(margin, 0, margin, 0);

        layoutIndicators.removeAllViews();
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.indicator_inactive
            ));
            imageView.setLayoutParams(layoutParams);
            layoutIndicators.addView(imageView);
        }
    }

    private void setCurrentIndicator(int index) {
        int childCount = layoutIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutIndicators.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(),
                        R.drawable.indicator_active
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(),
                        R.drawable.indicator_inactive
                ));
            }
        }
    }

    private void setupListeners() {
        vpOnboarding.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
                if (position == onboardingAdapter.getItemCount() - 1) {
                    btnNext.setText(R.string.get_started);
                    btnSkip.setVisibility(View.GONE);
                } else {
                    btnNext.setText(R.string.next);
                    btnSkip.setVisibility(View.VISIBLE);
                }
            }
        });

        btnSkip.setOnClickListener(v -> completeOnboarding());

        btnNext.setOnClickListener(v -> {
            if (vpOnboarding.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                vpOnboarding.setCurrentItem(vpOnboarding.getCurrentItem() + 1);
            } else {
                completeOnboarding();
            }
        });
    }

    private void completeOnboarding() {
        SharedPrefManager.getInstance(this).setFirstRun(false);
        Intent intent = new Intent(OnboardingActivity.this, com.example.campusconnect.ui.auth.LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
