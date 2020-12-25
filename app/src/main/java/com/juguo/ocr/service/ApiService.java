package com.juguo.ocr.service;



import com.juguo.ocr.bean.AddPayOrderBean;
import com.juguo.ocr.base.BaseResponse;
import com.juguo.ocr.bean.FeedBackBean;
import com.juguo.ocr.bean.VersionUpdataBean;
import com.juguo.ocr.dragger.bean.User;
import com.juguo.ocr.response.AccountInformationResponse;
import com.juguo.ocr.response.AddPayOrderResponse;
import com.juguo.ocr.response.LoginResponse;
import com.juguo.ocr.response.MemberLevelResponse;
import com.juguo.ocr.response.QueryOrderResponse;
import com.juguo.ocr.response.VersionUpdataResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ApiService {


    // 查询订单
    @GET("order/{id}")
    Observable<QueryOrderResponse> queryOrder(@Path("id") String id);
    // 获取会员价格列表
    @GET("member-level/list")
    Observable<MemberLevelResponse> memberLevel();
    // 版本更新
    @POST("app-v/check")
    Observable<VersionUpdataResponse> versionUpdata(@Body VersionUpdataBean versionUpdataBean);

    // 登录获取token
    @POST("user/login")
    Observable<LoginResponse> login(@Body User user);

    // 获取用户账号信息
    @GET("user/me/")
    Observable<AccountInformationResponse> accountInformation();

    // 退出登录
    @GET("user/logout")
    Observable<BaseResponse> logOut();

    // 帮助反馈
    @POST("feedback/")
    Observable<BaseResponse> feedBack(@Body FeedBackBean feedBackBean);

    // 添加订单
    @POST("order/")
    Observable<AddPayOrderResponse> addPayOrder(@Body AddPayOrderBean addPayOrderBean);
}
