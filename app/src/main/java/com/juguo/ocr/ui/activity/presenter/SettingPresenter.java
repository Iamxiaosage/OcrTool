package com.juguo.ocr.ui.activity.presenter;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;

import com.juguo.ocr.base.BaseMvpPresenter;
import com.juguo.ocr.base.BaseResponse;
import com.juguo.ocr.http.DefaultObserver;
import com.juguo.ocr.http.RetrofitUtils;
import com.juguo.ocr.http.RxSchedulers;
import com.juguo.ocr.service.ApiService;
import com.juguo.ocr.ui.activity.contract.SettingContract;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import javax.inject.Inject;

public class SettingPresenter extends BaseMvpPresenter<SettingContract.View> implements SettingContract.Presenter {

    @Inject
    public SettingPresenter() {

    }

    @Override
    public void logOut() {
        RetrofitUtils.getInstance().create(ApiService.class)
                .logOut()
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(new DefaultObserver<BaseResponse>((Context) mView) {
                    @Override
                    public void onSuccess(BaseResponse result) {
                        mView.httpCallback(result);
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        mView.httpError(e.toString());
                    }
                });
    }
}
