package com.juguo.ocr.dragger.component;



import com.juguo.ocr.MyApplication;
import com.juguo.ocr.dragger.ContextLife;
import com.juguo.ocr.dragger.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Administrator
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    /**
     * 提供App的Context
     * @return
     */
    @ContextLife("Application")
    MyApplication getContext();

}
