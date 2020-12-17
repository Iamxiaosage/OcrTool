package com.juguo.ocr.ui.activity.presenter;

import android.arch.lifecycle.LifecycleOwner;
import android.support.v4.app.Fragment;

import com.juguo.ocr.base.BaseMvpPresenter;
import com.juguo.ocr.bean.VersionUpdataBean;
import com.juguo.ocr.http.DefaultObserver;
import com.juguo.ocr.http.RetrofitUtils;
import com.juguo.ocr.http.RxSchedulers;
import com.juguo.ocr.response.VersionUpdataResponse;
import com.juguo.ocr.service.ApiService;
import com.juguo.ocr.ui.activity.contract.MineContract;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import javax.inject.Inject;

public class MinePresenter extends BaseMvpPresenter<MineContract.View> implements MineContract.Presenter {

    @Inject
    public MinePresenter() {

    }

    @Override
    public void settingVersion(VersionUpdataBean versionUpdataBean) {
        RetrofitUtils.getInstance().create(ApiService.class)
                .versionUpdata(versionUpdataBean)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(new DefaultObserver<VersionUpdataResponse>((Fragment) mView) {
                    @Override
                    public void onSuccess(VersionUpdataResponse result) {
                        mView.httpCallback(result);
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        mView.httpError(e.toString());
                    }
                });
    }
}
