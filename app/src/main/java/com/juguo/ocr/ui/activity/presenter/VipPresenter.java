package com.juguo.ocr.ui.activity.presenter;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;

//import com.juguo.ocr.base.BaseMvpPresenter;
//import com.juguo.ocr.bean.AddPayOrderBean;
//import com.juguo.ocr.http.DefaultObserver;
//import com.juguo.officefamily.http.RetrofitUtils;
//import com.juguo.officefamily.http.RxSchedulers;
//import com.juguo.officefamily.response.AccountInformationResponse;
//import com.juguo.officefamily.response.AddPayOrderResponse;
//import com.juguo.officefamily.response.MemberLevelResponse;
//import com.juguo.officefamily.response.QueryOrderResponse;
//import com.juguo.officefamily.service.ApiService;
//import com.juguo.officefamily.ui.activity.contract.VipContract;
import com.juguo.ocr.bean.AddPayOrderBean;
import com.juguo.ocr.base.BaseMvpPresenter;
import com.juguo.ocr.http.DefaultObserver;
import com.juguo.ocr.http.RetrofitUtils;
import com.juguo.ocr.http.RxSchedulers;
import com.juguo.ocr.response.AccountInformationResponse;
import com.juguo.ocr.response.AddPayOrderResponse;
import com.juguo.ocr.response.MemberLevelResponse;
import com.juguo.ocr.response.QueryOrderResponse;
import com.juguo.ocr.service.ApiService;
import com.juguo.ocr.ui.activity.contract.VipContract;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import javax.inject.Inject;

public class VipPresenter extends BaseMvpPresenter<VipContract.View> implements VipContract.Presenter {

    @Inject
    public VipPresenter() {

    }

    @Override
    public void addPayOrder(AddPayOrderBean addPayOrderBean) {
        RetrofitUtils.getInstance().create(ApiService.class)
                .addPayOrder(addPayOrderBean)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(new DefaultObserver<AddPayOrderResponse>((Context) mView) {
                    @Override
                    public void onSuccess(AddPayOrderResponse result) {
                        mView.httpCallback(result);
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        mView.httpError(e.toString());
                    }
                });
    }

    @Override
    public void queryOrder(String orderId) {
        RetrofitUtils.getInstance().create(ApiService.class)
                .queryOrder(orderId)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(new DefaultObserver<QueryOrderResponse>((Context) mView) {
                    @Override
                    public void onSuccess(QueryOrderResponse result) {
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

    @Override
    public void getMemberLevel() {
        RetrofitUtils.getInstance().create(ApiService.class)
                .memberLevel()
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(new DefaultObserver<MemberLevelResponse>((Context) mView) {
                    @Override
                    public void onSuccess(MemberLevelResponse result) {
                        mView.httpCallback(result);
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        mView.httpError(e.toString());
                    }
                });
    }
}
