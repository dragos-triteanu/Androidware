package com.enginizer.receivers;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;

/**
 * Created by drago on 3/26/2017.
 */

public class CalendarNotificationBroadcastReceiver extends BroadcastReceiver {
    public static final String[] FIELDS = {
            CalendarContract.Events.TITLE,
            CalendarContract.Events.CALENDAR_ID,
            CalendarContract.Events.EVENT_TIMEZONE,
            CalendarContract.Events._SYNC_ID,
            CalendarContract.Events.CUSTOM_APP_URI

    };

    public static final String[] EVENT_PROJECTION = new String[] {
            CalendarContract.Calendars._ID,                           // 0
            CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
            CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
    };

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();

        switch (intentAction){
            case CalendarContract.ACTION_EVENT_REMINDER:
                Uri uri = intent.getData();

                // Cancel current intent
                // Cancel current intent
                // Cancel current intent
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);

                pendingIntent.cancel();

                AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
                am.cancel(pendingIntent);


                String selection = uri.getLastPathSegment();
                Cursor cursor = context.getContentResolver().query(Uri.parse("content://com.android.calendar/events"), FIELDS, null, null, null);
                while (cursor.moveToNext()){
                    String title = cursor.getString(0);
                }

                break;
        }


    }
}
