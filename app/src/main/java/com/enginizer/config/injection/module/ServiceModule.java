package com.enginizer.config.injection.module;

import com.enginizer.service.ScheduleService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by drago on 3/24/2017.
 */
@Module
public class ServiceModule {

    @Provides
    @Singleton
    ScheduleService provideScheduleService(){
        return new ScheduleService();
    }
}
