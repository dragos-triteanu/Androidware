package com.enginizer.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by drago on 3/24/2017.
 */

public class NotificationActionService extends IntentService {

    public NotificationActionService() {
        super(NotificationActionService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        if("snooze".equals(action)){

        }
    }
}
