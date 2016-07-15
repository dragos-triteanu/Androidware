package com.smth.androidware.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Window;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.TextView;

import com.smth.androidware.R;
import com.smth.androidware.util.ActivityAnimator;
import com.smth.androidware.util.AnimationType;
import com.smth.androidware.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransitionActivity extends AppCompatActivity {

    AnimationType type;

    @BindView(R.id.txtAnimationName)
    TextView txtAnimationName;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // inside your activity (if you did not enable transitions in your theme)
        // For AppCompat getWindow must be called before calling super.OnCreate
        // Must be called before setContentView
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        ButterKnife.bind(this);

        toolbar.setTitle(getIntent().getExtras().getString(Constants.KEY_TITLE));
        txtAnimationName.setText(getIntent().getExtras().getString(Constants.KEY_NAME));
        type = (AnimationType) getIntent().getSerializableExtra(Constants.KEY_TYPE);

        setupToolbar();
        ActivityAnimator.animateActivity(this,type,R.integer.anim_duration_fast,Gravity.RIGHT);

        // For overlap between Exiting  MainActivity.java and Entering TransitionActivity.java
        getWindow().setAllowEnterTransitionOverlap(false);
    }

    protected void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finishAfterTransition();
        return true;
    }
}
