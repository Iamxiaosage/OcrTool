package com.juguo.ocr.ui.activity.contract;

import com.juguo.ocr.base.BaseMvpCallback;
import com.juguo.ocr.dragger.bean.User;
import com.juguo.ocr.response.AccountInformationResponse;
import com.juguo.ocr.response.LoginResponse;

public interface HomeContract {

    interface View extends BaseMvpCallback {
        void httpCallback(LoginResponse loginResponse);

        void httpCallback(AccountInformationResponse response);

        void httpError(String e);
    }

    interface Presenter {

        void login(User user);

        void getAccountInformation();
    }
}
