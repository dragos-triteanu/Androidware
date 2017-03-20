package com.enginizer.listener;

import java.util.Calendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.enginizer.R;
import com.enginizer.activities.ReScheduleActivity;
import com.enginizer.util.PreferencesHelper;

public class CallStateBroadcastReciever extends BroadcastReceiver {

    private static final String TAG = "CallStateBroadcastReciever:";
    private static final String OUTGOING_CALL_ACTION = "android.intent.action.NEW_OUTGOING_CALL";
    private static final String PHONE_STATE_ACTION = "android.intent.action.PHONE_STATE";

    public static final String INTENT_PHONE_NUMBER = "android.intent.extra.PHONE_NUMBER";
    public static final String INTENT_CALL_STARTED_TIME = "CALL_STARTED_TIME";
    public static final String INTENT_CALL_STOP_TIME = "CALL_STOP_TIME";
    public static final int NOTIFICATION_ID = 1;

    private static String previousCallState = new String();
    private static String phoneNumber;
    private static Calendar callStartedTime;
    private static Calendar callStopTime;

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();

        if (intentAction.equals(OUTGOING_CALL_ACTION)) {
            // store the phone number in memory
            phoneNumber = intent.getExtras().getString(INTENT_PHONE_NUMBER);

            Log.i(TAG, "new outgoing call to number:" + phoneNumber);
            Log.i(TAG, intent.getExtras().toString());
        } else if (intentAction.equals(PHONE_STATE_ACTION)) {
            // get the state
            String phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

            Log.i(TAG, "calling state changed to :" + phoneState);
            Log.i(TAG, intent.getExtras().toString());

            // phone state changed to idle (after a call)
            if (phoneState.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                if (previousCallState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                    callStopTime = Calendar.getInstance();

                    // check if the phone number is not on the ignore list
                    if (!PreferencesHelper.isPhoneNumberIgnored(context, phoneNumber)) {
                        // launch the re-schedule activity

                        Intent rescheduleActivityIntent = new Intent();
                        rescheduleActivityIntent.setClass(context, ReScheduleActivity.class);
                        rescheduleActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        rescheduleActivityIntent.putExtra(INTENT_PHONE_NUMBER, phoneNumber);
                        rescheduleActivityIntent.putExtra(INTENT_CALL_STARTED_TIME, callStartedTime);
                        rescheduleActivityIntent.putExtra(INTENT_CALL_STOP_TIME, callStopTime);
                        rescheduleActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        PendingIntent reschedulerPendingIntent = PendingIntent.getActivity(context, 0, rescheduleActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        PendingIntent cancelIntent = PendingIntent.getActivity(context, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);

                        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);

                        notificationBuilder.setAutoCancel(true)
                                .setDefaults(Notification.DEFAULT_ALL)
                                .setWhen(System.currentTimeMillis())
                                .setSmallIcon(R.drawable.callerq_icon)
                                .setTicker("Hearty365")
                                .setContentTitle("Add a reminder")
                                .setContentText("For your call to: " + phoneNumber)
                                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND | Notification.FLAG_AUTO_CANCEL )
                                .setContentIntent(reschedulerPendingIntent)
                                .setContentInfo("Info");

                        notificationBuilder.addAction(R.drawable.common_google_signin_btn_icon_dark, "Snooze", cancelIntent);
                        notificationBuilder.addAction(1, "Schedule", reschedulerPendingIntent);


                        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
                    }
                }
            }
            // phone state changed to ringing (when receiving a call)
            else if (phoneState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                phoneNumber = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            } else if (phoneState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                callStartedTime = Calendar.getInstance();
            }

            previousCallState = phoneState;
        }

    }

}
