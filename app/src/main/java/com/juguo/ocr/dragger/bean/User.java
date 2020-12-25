package com.juguo.ocr.dragger.bean;
//package com.juguo.ocr.dragger.bean;

//import com.juguo.ocr.bean.UserInfos;

//import com.juguo.ocr.bean.UserInfos;

import com.juguo.ocr.bean.UserInfos;

import java.io.Serializable;

public class User implements Serializable {

    private UserInfos param;

    public UserInfos getParam() {
        return param;
    }

    public void setParam(UserInfos param) {
        this.param = param;
    }
}
