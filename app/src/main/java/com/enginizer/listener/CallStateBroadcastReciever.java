package com.enginizer.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.enginizer.EnginizerApplication;
import com.enginizer.model.CallDetails;
import com.enginizer.service.ScheduleService;
import com.enginizer.util.PreferencesHelper;

import java.util.Calendar;

import javax.inject.Inject;

import static com.enginizer.util.CallConstants.INTENT_PHONE_NUMBER;
import static com.enginizer.util.CallConstants.OUTGOING_CALL_ACTION;
import static com.enginizer.util.CallConstants.PHONE_STATE_ACTION;

public class CallStateBroadcastReciever extends BroadcastReceiver {
    private static final String TAG = "CallStateBroadcastReciever:";

    private static String previousCallState = new String();
    private static String phoneNumber;
    private static Calendar callStartedTime;
    private static Calendar callStopTime;

    @Inject
    ScheduleService schedulingService;

    @Override
    public void onReceive(Context context, Intent intent) {
        ((EnginizerApplication) context.getApplicationContext()).getApplicationComponent().inject(this);

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
                        schedulingService.sendNotificationAfterCall(context, new CallDetails(phoneNumber, callStartedTime, callStopTime));
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
