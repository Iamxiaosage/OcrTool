package com.juguo.ocr.ui.activity.contract;


import com.juguo.ocr.base.BaseMvpCallback;
import com.juguo.ocr.bean.VersionUpdataBean;
import com.juguo.ocr.response.VersionUpdataResponse;

public interface SplashContract {

    interface View extends BaseMvpCallback {
        void httpCallback(VersionUpdataResponse response);
        void httpError(String e);
    }

    interface Presenter {

        void selectSplash(VersionUpdataBean versionUpdataBean);
    }
}
