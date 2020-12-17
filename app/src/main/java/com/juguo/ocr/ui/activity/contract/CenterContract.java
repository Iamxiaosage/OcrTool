package com.juguo.ocr.ui.activity.contract;

import com.juguo.ocr.base.BaseMvpCallback;

public interface CenterContract {

    interface View extends BaseMvpCallback {
        void httpCallback(Object o);
        void httpError(String e);
    }

    interface Presenter {


    }
}
