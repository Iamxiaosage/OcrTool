package com.juguo.ocr.dragger.module;


import com.juguo.ocr.MyApplication;
import com.juguo.ocr.dragger.ContextLife;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Administrator
 */
@Module
public class AppModule {
    private final MyApplication application;

    public AppModule(MyApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @ContextLife("Application")
    MyApplication provideApplicationContext() {
        return application;
    }

}
