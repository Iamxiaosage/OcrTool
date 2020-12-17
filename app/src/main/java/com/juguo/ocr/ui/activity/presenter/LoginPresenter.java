package com.juguo.ocr.ui.activity.presenter;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;

import com.juguo.ocr.base.BaseMvpPresenter;
import com.juguo.ocr.dragger.bean.User;
import com.juguo.ocr.http.DefaultObserver;
import com.juguo.ocr.http.RetrofitUtils;
import com.juguo.ocr.http.RxSchedulers;
import com.juguo.ocr.response.AccountInformationResponse;
import com.juguo.ocr.response.LoginResponse;
import com.juguo.ocr.service.ApiService;
import com.juguo.ocr.ui.activity.contract.LoginContract;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import javax.inject.Inject;

/**
 * @Author: Administrator
 * @Time: 2019/7/29 16:27
 * @Company：ch
 * @Description: 功能描述
 */
public class LoginPresenter extends BaseMvpPresenter<LoginContract.View> implements LoginContract.Presenter {

    @Inject
    public LoginPresenter() {

    }

    @Override
    public void login(User user) {
        RetrofitUtils.getInstance().create(ApiService.class)
                .login(user)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(new DefaultObserver<LoginResponse>((Context) mView) {
                    @Override
                    public void onSuccess(LoginResponse result) {
                        mView.httpCallback(result);
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        mView.httpError(e.toString());
                    }
                });
    }

    @Override
    public void getAccountInformation() {
        RetrofitUtils.getInstance().create(ApiService.class)
                .accountInformation()
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(new DefaultObserver<AccountInformationResponse>((Context) mView) {
                    @Override
                    public void onSuccess(AccountInformationResponse result) {
                        mView.httpCallback(result);
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        mView.httpError(e.toString());
                    }
                });
    }
}
