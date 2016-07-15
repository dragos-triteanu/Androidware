package com.smth.androidware.util;

import android.app.Activity;
import android.content.Context;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.animation.AnticipateOvershootInterpolator;

/**
 * Created by Dragos on 7/15/2016.
 */
public class ActivityAnimator {


    public static void animateActivity(Context ctx, AnimationType animationType, int duration, int gravity){
        switch (animationType) {

            case EXPLODE: { // For Explode By Code

                Explode enterTransition = new Explode();
                enterTransition.setDuration(ctx.getResources().getInteger(duration));
                ((Activity)ctx).getWindow().setEnterTransition(enterTransition);
                break;
            }

            case SLIDE: { // For Slide By Code

                Slide enterTransition = new Slide();
                enterTransition.setSlideEdge(gravity);
                enterTransition.setDuration(ctx.getResources().getInteger(duration));
                enterTransition.setInterpolator(new AnticipateOvershootInterpolator());
                ((Activity)ctx).getWindow().setEnterTransition(enterTransition);
                break;
            }

            case FADE: { // For Fade By Code

                Fade enterTransition = new Fade();
                enterTransition.setDuration(ctx.getResources().getInteger(duration));
                ((Activity)ctx).getWindow().setEnterTransition(enterTransition);
                break;
            }
        }
    }

}
