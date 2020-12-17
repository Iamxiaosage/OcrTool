package com.juguo.ocr.dragger.component;

import android.app.Activity;


import com.juguo.ocr.dragger.FragmentScope;
import com.juguo.ocr.dragger.module.FragmentModule;
import com.juguo.ocr.ui.fragment.ScanFragment;
import com.juguo.ocr.ui.fragment.HomeFragment;
import com.juguo.ocr.ui.fragment.MineFragment;

import dagger.Component;

/**
 * @author Administrator
 */
@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(HomeFragment homeFragment);

    void inject(ScanFragment centerFragment);

    void inject(MineFragment centerFragment);

}
