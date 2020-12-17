package com.juguo.ocr.ui.activity.contract;


import com.juguo.ocr.base.BaseMvpCallback;
import com.juguo.ocr.base.BaseResponse;
import com.juguo.ocr.bean.FeedBackBean;

public interface HelpFeedbackContract {

    interface View extends BaseMvpCallback {
        void httpCallback(BaseResponse response);
        void httpError(String e);
    }

    interface Presenter {

        void feedBack(FeedBackBean feedBackBean);
    }
}
