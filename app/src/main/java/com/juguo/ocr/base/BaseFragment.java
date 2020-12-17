package com.juguo.ocr.base;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.content.Context;
import android.os.Bundle;

import me.yokeyword.fragmentation.SupportFragment;


public class BaseFragment<A extends BaseActivity> extends SupportFragment implements LifecycleOwner {

    public A mContext;
    private Context mAppContext;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected boolean isNeedToAddBackStack() {
        return true;
    }

    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (A) requireActivity();
        mAppContext = mContext.getApplicationContext();

    }

    /**
     * 获取绑定的Activity，防止出现 getActivity() 为空
     */
    public A getBindingActivity() {
        return mContext;
    }

    protected String getStr(int resId) {
        return getResources().getString(resId);
    }

    public Context getApplicationContext(){
        return mAppContext;
    }
}
