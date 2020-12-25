package com.juguo.ocr.ui.activity.contract;

import com.juguo.ocr.bean.AddPayOrderBean;
import com.juguo.ocr.base.BaseMvpCallback;
//import com.juguo.ocr.bean.AddPayOrderBean;
import com.juguo.ocr.response.AccountInformationResponse;
import com.juguo.ocr.response.AddPayOrderResponse;
import com.juguo.ocr.response.MemberLevelResponse;
import com.juguo.ocr.response.QueryOrderResponse;
//import com.juguo.ocr.response.AddPayOrderResponse;
//import com.juguo.ocr.response.MemberLevelResponse;
//import com.juguo.ocr.response.QueryOrderResponse;

public interface VipContract {

    interface View extends BaseMvpCallback {
        void httpCallback(AddPayOrderResponse response);
        void httpCallback(QueryOrderResponse response);
        void httpCallback(AccountInformationResponse response);
        void httpCallback(MemberLevelResponse response);
        void httpError(String e);
    }

    interface Presenter {

        void addPayOrder(AddPayOrderBean addPayOrderBean);

        void queryOrder(String orderId);

        void getAccountInformation();

        void getMemberLevel();
    }
}
