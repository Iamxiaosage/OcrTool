package com.juguo.ocr.utils;

import android.content.Context;
import android.os.Environment;

public class Constants {

    //  573c67e79ee69e07ae4087ede27d0465

    // 测试环境
    public static String BASE_URL = "http://app.91juguo.com/testJ/";
    // 正式环境
//    public static String BASE_URL = "http://app.91juguo.com/api/";
//    public static String BASE_URL = "http://app.91juguo.com/bgtest/";

    // 微信appId 要发送给服务端
//    public static final String WX_APP_ID = "wx499160198874eb51";
    public static final String WX_APP_ID = "wx499160198874eb51";
    // 微信支付申请支付功能的商户号
    public static final String WX_MCH_ID = "iui";
    // 微信支付 商品平台API密钥
    //public static final String WX_PRIVATE_KEY = "1d63792986ea3383659af41d62956ca6";
//    public static final String WX_PRIVATE_KEY = "f37b4cdc3c9ac07015dd2c340539ee98";
    public static final String WX_PRIVATE_KEY = "wstl2016wstl2016wstl2016wstl2016";

    //2639cf2a362b0e9696e4212889394574
    // 穿山甲
    public static final String CSJ_APP_ID = "5126521";// 应用id
    public static final String CSJ_CODE_ID = "887412015";// 代码位id

    // 本地环境
//    public static String BASE_URL = "http://172.16.0.41:8080/";


    //缓存数据保存根目录
    public static String JUGUO_CACHE_DIR;
    public static String CACHE_FILE = "/JuguoOfficeFamilyCache/";
    public static String NET_ERROR = "请连接您的网络";

    public static void getCachePath(Context mContext) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            JUGUO_CACHE_DIR = mContext.getExternalCacheDir().getAbsolutePath();
        } else {
            JUGUO_CACHE_DIR = mContext.getCacheDir().getPath();
        }
    }
}
