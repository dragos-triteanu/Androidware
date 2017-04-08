package com.enginizer.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;
import android.util.Log;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
/**
 * Due to an android bug: http://stackoverflow.com/questions/17911883/cannot-get-the-notificationlistenerservice-class-to-work
 * This class' name should be changed before each debug use, because if not, onReceive will never get called.
 */
public class CalendarNotificationListenerService extends NotificationListenerService {

    private String TAG = this.getClass().getSimpleName();
    private CalendarNotificationBroadcastReceiver receiver;

    @Override
    public void onCreate() {
        Log.i(TAG,"********** Service is created");
        super.onCreate();
        receiver = new CalendarNotificationBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter("android.intent.action.EVENT_REMINDER");
        intentFilter.addDataScheme("content");
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG,"**********  Service is destroyed");
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public IBinder onBind(Intent mIntent) {
        IBinder mIBinder = super.onBind(mIntent);
        Log.i(TAG, "**********  onBind");
        return mIBinder;
    }

    @Override
    public boolean onUnbind(Intent mIntent) {
        boolean mOnUnbind = super.onUnbind(mIntent);
        Log.i(TAG, "**********  onUnbind");
        return mOnUnbind;
    }


    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.i(TAG,"**********  onNotificationPosted");
        Log.i(TAG,"ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());
        Intent i = new  Intent("com.enginizer.NOTIFICATION_LISTENER_EXAMPLE");
        i.putExtra("notification_event","onNotificationPosted :" + sbn.getPackageName() + "\n");
        sendBroadcast(i);

        CalendarNotificationListenerService.this.cancelAllNotifications();
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i(TAG,"********** onNOtificationRemoved");
        Log.i(TAG,"ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText +"\t" + sbn.getPackageName());
        Intent i = new  Intent("com.enginizer.NOTIFICATION_LISTENER_EXAMPLE");
        i.putExtra("notification_event","onNotificationRemoved :" + sbn.getPackageName() + "\n");

        sendBroadcast(i);
    }

//

    class CalendarNotificationBroadcastReceiver extends BroadcastReceiver{

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "********** Notification received");
            String intentAction = intent.getAction();
            Log.i(TAG, "********** " + intentAction);

            CalendarNotificationListenerService.this.cancelAllNotifications();
        }
    }

    @Override
    public void onListenerConnected() {
        Log.i(TAG,"********** Listener connected");
        super.onListenerConnected();
    }
}
