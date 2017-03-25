package com.enginizer.config.injection.component;


import com.enginizer.activities.MainActivity;
import com.enginizer.config.injection.module.ApplicationModule;
import com.enginizer.config.injection.module.ServiceModule;
import com.enginizer.listener.CallStateBroadcastReciever;
import com.enginizer.service.NotificationActionService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, ServiceModule.class})
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);
    void inject(CallStateBroadcastReciever broadcastReceiver);
    void inject(NotificationActionService notificationActionService);
}
