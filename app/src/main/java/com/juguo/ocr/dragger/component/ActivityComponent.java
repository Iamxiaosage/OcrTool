package com.juguo.ocr.dragger.component;

import android.app.Activity;


import com.juguo.ocr.dragger.ActivityScope;
import com.juguo.ocr.dragger.module.ActivityModule;
import com.juguo.ocr.ui.activity.HelpFeedbackActivity;
import com.juguo.ocr.ui.activity.LoginActivity;
import com.juguo.ocr.ui.activity.SettingActivity;
import com.juguo.ocr.ui.activity.SplashActivity;
import com.juguo.ocr.ui.activity.WebUrlActivity;

import dagger.Component;

/**
 * @author Administrator
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(SplashActivity splashActivity);

    void inject(LoginActivity loginActivity);

    void inject(WebUrlActivity webUrlActivity);

    void inject(SettingActivity settingActivity);

    void inject(HelpFeedbackActivity helpFeedbackActivity);

}
