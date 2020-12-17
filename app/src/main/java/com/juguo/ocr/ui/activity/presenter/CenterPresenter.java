package com.juguo.ocr.ui.activity.presenter;

import com.juguo.ocr.base.BaseMvpPresenter;
import com.juguo.ocr.ui.activity.contract.CenterContract;

import javax.inject.Inject;

public class CenterPresenter extends BaseMvpPresenter<CenterContract.View> implements CenterContract.Presenter {

    @Inject
    public CenterPresenter() {

    }

}
