package com.smth.androidware.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smth.androidware.R;
import com.smth.androidware.util.AnimationType;
import com.smth.androidware.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimationsActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.explodeJava)
    Button btnExplodeAnimation;
    @BindView(R.id.slideJava)
    Button btnSlideAnimation;
    @BindView(R.id.fadeJava)
    Button btnFadeAnimation;

    @BindView(R.id.shared_element)
    LinearLayout layoutSharedElementAnimation;

    @BindView(R.id.imgStarSharedElement)
    ImageView imgStar;

    @BindView(R.id.txvRippleWithBorder)
    TextView txvRippleWithBorder;

    @BindView(R.id.txvRippleWithoutBorder)
    TextView txvRippleWithoutBorder;

    @BindView(R.id.txvCustomRippleWithBorder)
    TextView txvCustomRippleWithBorder;

    @BindView(R.id.txvCustomRippleWithoutBorder)
    TextView txvCustomRippleWithoutBorder;

    @BindView(R.id.txvSharedElement)
    TextView txvShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animations);
        ButterKnife.bind(this);

        btnExplodeAnimation.setOnClickListener(this);
        btnSlideAnimation.setOnClickListener(this);
        btnFadeAnimation.setOnClickListener(this);
        layoutSharedElementAnimation.setOnClickListener(this);

        txvRippleWithBorder.setOnClickListener(this);
        txvRippleWithoutBorder.setOnClickListener(this);
        txvCustomRippleWithBorder.setOnClickListener(this);
        txvCustomRippleWithoutBorder.setOnClickListener(this);

        setupWindowAnimations();
        setupToolbar();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.explodeJava: {

                //noinspection unchecked
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
                Intent i = new Intent(AnimationsActivity.this, TransitionActivity.class);
                i.putExtra(Constants.KEY_TYPE, AnimationType.EXPLODE);
                i.putExtra(Constants.KEY_TITLE, "Explode Animation");
                i.putExtra(Constants.KEY_NAME, "Java Explode");
                startActivity(i, options.toBundle());

                break;
            }

            case R.id.slideJava : {

                //noinspection unchecked
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
                Intent i = new Intent(AnimationsActivity.this, TransitionActivity.class);
                i.putExtra(Constants.KEY_TYPE, AnimationType.SLIDE);
                i.putExtra(Constants.KEY_TITLE, "Slide Animation");
                i.putExtra(Constants.KEY_NAME, "Slide Java");
                startActivity(i, options.toBundle());

                break;
            }

            case R.id.fadeJava : {

                //noinspection unchecked
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
                Intent i = new Intent(AnimationsActivity.this, TransitionActivity.class);
                i.putExtra(Constants.KEY_TYPE, AnimationType.FADE);
                i.putExtra(Constants.KEY_TITLE, "Fade Animation");
                i.putExtra(Constants.KEY_NAME, "Fade Java");
                startActivity(i, options.toBundle());

                break;
            }

            case R.id.shared_element : {

                Pair[] pair = new Pair[2];

                pair[0] = new Pair<View, String>(imgStar, "star");
                pair[1] = new Pair<View, String>(txvShared, "text_shared");

                //noinspection unchecked
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, pair);
                Intent i = new Intent(AnimationsActivity.this, SharedElementActivity.class);
                startActivity(i, options.toBundle());

                break;
            }
        }
    }

    private void setupWindowAnimations() {
        // Re-enter transition is executed when returning back to this activity
        Slide slideTransition = new Slide();
        slideTransition.setSlideEdge(Gravity.LEFT); // Use START if using right - to - left locale
        slideTransition.setDuration(1000);

        getWindow().setReenterTransition(slideTransition);  // When MainActivity Re-enter the Screen
        getWindow().setExitTransition(slideTransition);     // When MainActivity Exits the Screen

        // For overlap of Re Entering Activity - MainActivity.java and Exiting TransitionActivity.java
        getWindow().setAllowReturnTransitionOverlap(false);
    }

    protected void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


}
