package com.enginizer.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.enginizer.R;
import com.enginizer.activities.ReScheduleActivity;
import com.enginizer.model.CallDetails;
import com.enginizer.util.CallConstants;

import static com.enginizer.util.CallConstants.INTENT_CALL_STARTED_TIME;
import static com.enginizer.util.CallConstants.INTENT_CALL_STOP_TIME;
import static com.enginizer.util.CallConstants.INTENT_PHONE_NUMBER;

/**
 * Created by drago on 3/24/2017.
 */

public class ScheduleService {
    private static final String TAG = "ScheduleService:";
    public static final int NOTIFICATION_ID = 1;

    public ScheduleService() {
    }


    public void sendNotificationAfterCall(Context ctx, CallDetails callDetails) {
        Intent rescheduleActivityIntent = new Intent();
        rescheduleActivityIntent.setClass(ctx, ReScheduleActivity.class);
        rescheduleActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        rescheduleActivityIntent.putExtra(INTENT_PHONE_NUMBER, callDetails.getPhoneNumber());
        rescheduleActivityIntent.putExtra(INTENT_CALL_STARTED_TIME, callDetails.getCallStartedTime());
        rescheduleActivityIntent.putExtra(INTENT_CALL_STOP_TIME, callDetails.getCallStopTime());
        rescheduleActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        Intent snoozeIntent = new Intent(ctx, NotificationActionService.class).setAction("snooze");
        snoozeIntent.putExtra(CallConstants.CALL_DETAILS_EXTRA,callDetails);
        snoozeIntent.putExtra(CallConstants.NOTIFICATION_ID,NOTIFICATION_ID);

        PendingIntent reschedulerPendingIntent = PendingIntent.getActivity(ctx, 0, rescheduleActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent cancelPendingIntent = PendingIntent.getService(ctx, 0, snoozeIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(ctx);

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.callerq_icon)
                .setTicker("Hearty365")
                .setContentTitle("Add a reminder")
                .setContentText("For your callDetails to: " + callDetails.getPhoneNumber())
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND | Notification.FLAG_AUTO_CANCEL | Notification.FLAG_ONGOING_EVENT)
                .setContentIntent(reschedulerPendingIntent)
                .setContentInfo("Info");

        notificationBuilder.addAction(new NotificationCompat.Action(0,"Snooze",cancelPendingIntent));
        notificationBuilder.addAction(0, "Schedule", reschedulerPendingIntent);

        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }
}
