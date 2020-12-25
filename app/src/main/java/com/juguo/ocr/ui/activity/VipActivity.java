package com.juguo.ocr.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
//import com.alipay.sdk.app.PayTask;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.juguo.ocr.R;
import com.juguo.ocr.base.BaseMvpActivity;
import com.juguo.ocr.bean.AddPayOrderBean;
import com.juguo.ocr.bean.EventBusMessage;
//import com.juguo.ocr.HyqxItemBean;
//import com.juguo.ocr.bean.SignOrderBean;
//import com.juguo.ocr.bean.WxPayMessageBean;
import com.juguo.ocr.bean.HyqxItemBean;
import com.juguo.ocr.bean.SignOrderBean;
import com.juguo.ocr.bean.WxPayMessageBean;
import com.juguo.ocr.response.AccountInformationResponse;
import com.juguo.ocr.response.AddPayOrderResponse;
import com.juguo.ocr.response.MemberLevelResponse;
import com.juguo.ocr.response.QueryOrderResponse;
import com.juguo.ocr.ui.activity.contract.VipContract;
import com.juguo.ocr.ui.activity.presenter.VipPresenter;
import com.juguo.ocr.utils.Constants;
import com.juguo.ocr.utils.DimenUtil;
import com.juguo.ocr.utils.MySharedPreferences;
import com.juguo.ocr.utils.PayResult;
import com.juguo.ocr.utils.ToastUtils;
import com.juguo.ocr.utils.Util;
import com.juguo.ocr.utils.WeChatField;
//import com.tencent.mm.opensdk.modelpay.PayReq;
//import com.tencent.mm.opensdk.openapi.IWXAPI;
//import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

public class VipActivity extends BaseMvpActivity<VipPresenter> implements VipContract.View {

    private Context mContext;

    // 永久会员
//    @BindView(R.id.fl_yjhy)
//    public FrameLayout fl_yjhy;
//    @BindView(R.id.tv_yjhy_xsqg)
//    public TextView tv_yjhy_xsqg;// 限时抢购
//    @BindView(R.id.tv_yjhy_xj)
//    public TextView tv_yjhy_xj;
//    @BindView(R.id.tv_yjhy_price)
//    public TextView tv_yjhy_price;
//    @BindView(R.id.tv_yjhy_yj)
//    public TextView tv_yjhy_yj;
//    @BindView(R.id.tv_yjhy_color)
//    public TextView tv_yjhy_color;

    // 年度会员
//    @BindView(R.id.fl_ndhy)
//    public FrameLayout fl_ndhy;
//    @BindView(R.id.tv_ndhy_xsqg)
//    public TextView tv_ndhy_xsqg;
//    @BindView(R.id.tv_ndhy_xj)
//    public TextView tv_ndhy_xj;
//    @BindView(R.id.tv_ndhy_price)
//    public TextView tv_ndhy_price;
//    @BindView(R.id.tv_ndhy_yj)
//    public TextView tv_ndhy_yj;
//
//    // 月度会员
//    @BindView(R.id.fl_ydhy)
//    public FrameLayout fl_ydhy;
//    @BindView(R.id.tv_ydhy_xsqg)
//    public TextView tv_ydhy_xsqg;
//    @BindView(R.id.tv_ydhy_xj)
//    public TextView tv_ydhy_xj;
//    @BindView(R.id.tv_ydhy_price)
//    public TextView tv_ydhy_price;
//    @BindView(R.id.tv_ydhy_yj)
//    public TextView tv_ydhy_yj;
//    @BindView(R.id.scrollView)
//    public ScrollView scrollView;

    @BindView(R.id.tv_price_older)
    public TextView tv_price_older;

    @BindView(R.id.img_back)
    public ImageView img_back;

    @BindView(R.id.rl_zfb)
    public RelativeLayout rl_zfb;


    @BindView(R.id.img_wx_zf)
    public ImageView img_wechat_pay;


    @BindView(R.id.img_zfb_zf)
    public ImageView img_zfb_zf;

    @BindView(R.id.tv_price_pay)
    public TextView tv_price_pay;

//    @BindView(R.id.img_wx_zf)
//    public ImageView tv_prices;

    @BindView(R.id.tv_gm)
    public TextView tv_purchase;

    @BindView(R.id.tv_gmxz)
    public TextView tv_gmxz;





//    @BindView(R.id.fl_view)
//    public FrameLayout fl_view;

    // 支付方式
    private int zfType = 1;
    // 会员类型
    private String hyType = "永久会员";
    private MySharedPreferences mySharedPreferences;
    private String memberUser;
    private String orderId;

    private String level;

    private int querySum = 3;
    private final int SDK_PAY_FLAG = 1;
    private List<HyqxItemBean> mList = null;

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    switch (payResult.getResultStatus()) {
                        case "9000":
                            MobclickAgent.onEvent(mContext, "payment_success", "付款成功");
                            mPresenter.queryOrder(orderId);
                            break;
                        case "6004":
                        case "8000":
                            ToastUtils.shortShowStr(mContext, "正在处理中");
                            break;
                        case "4000":
                            MobclickAgent.onEvent(mContext, "payment_fali", "付款失败");
                            ToastUtils.shortShowStr(mContext, "订单支付失败");
                            break;
                        case "5000":
                            ToastUtils.shortShowStr(mContext, "重复请求");
                            break;
                        case "6001":
                            ToastUtils.shortShowStr(mContext, "已取消支付");
                            break;
                        case "6002":
                            ToastUtils.shortShowStr(mContext, "网络连接出错");
                            break;
                        default:
                            MobclickAgent.onEvent(mContext, "payment_fali", "付款失败");
                            ToastUtils.shortShowStr(mContext, "支付失败");
                            break;
                    }
                }
            }
            return false;
        }
    });
    private String prodCode;
    private String yjId;
    private String ndId;
    private String ydId;
    private String aliPay;
    private String wxPay;
    private boolean spxq;

    @Override
    protected int getLayout() {
        return R.layout.vip_activity;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(VipActivity.this);
    }

    @Override
    protected void initViewAndData() {
        mContext = this;


//        RelativeLayout rl_wx = findViewById(R.id.rl_wx);


//        tv_price_pay.setText(tv_price_pay.getText().toString());
        tv_gmxz.setVisibility(View.GONE);

//        rl_wx.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                zfType = 1;
//                img_wx_zf.setVisibility(View.VISIBLE);
//                img_zfb_zf.setVisibility(View.GONE);
//            }
//        });

        rl_zfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zfType = 2;
                img_wechat_pay.setVisibility(View.GONE);
                img_zfb_zf.setVisibility(View.VISIBLE);
            }
        });

        tv_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialog.dismiss();
                // 确认支付
                String price = tv_price_pay.getText().toString().replace("￥", "");
//                String price = "￥78".replace("￥", "");
                if (Float.parseFloat(price) > 0f) {
//                if (0.01f > 0f) {
                    pay(price);
                } else {
                    ToastUtils.shortShowStr(mContext, "请选择需要购买的课程");
                }
            }
        });

//        TitleBarUtils titleBarUtils = new TitleBarUtils(this);
//        titleBarUtils.setMiddleTitleText("会员");
//        titleBarUtils.setLeftImageRes(R.mipmap.btn_return);
//        titleBarUtils.setRightText("购买须知");
//        titleBarUtils.setLeftImageListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        titleBarUtils.setRightTextListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 购买须知
//                Intent intent = new Intent(mContext, WebUrlActivity.class);
//                intent.putExtra("title", "购买须知");
//                intent.putExtra("url", "file:///android_asset/PurchaseNotes.html");
//                startActivity(intent);
//            }
//        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        mySharedPreferences = new MySharedPreferences(mContext, "Shared");
//        memberUser = (String) mySharedPreferences.getValue("MemberUser", "");
        aliPay = (String) mySharedPreferences.getValue("ali", "");
        wxPay = (String) mySharedPreferences.getValue("wx", "");

        level = getIntent().getStringExtra("level");
        spxq = getIntent().getBooleanExtra("spxq", false);

        // 获取会员价格信息
        mPresenter.getMemberLevel();

        if (mList == null) {
            mList = new ArrayList<>();
//            addHyqyData();
        }

        // 设置textview中划线
//        tv_yjhy_yj.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
//        tv_ndhy_yj.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
//        tv_ydhy_yj.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

        //创建线性布局
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
//        //垂直方向
//        mLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
//        //给RecyclerView设置布局管理器
//        recyclerView.setLayoutManager(mLayoutManager);
//        //创建适配器，并且设置
//        MyAdapter myAdapter = new MyAdapter();
//        recyclerView.setAdapter(myAdapter);

        // 永久会员
        if ("9".equals(level)) {
            showDialog();
        }

//        fl_view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
////                if (getLocalVisibleRect(img_purchase_now, 0)) {
////                    ll_bottom.setVisibility(View.GONE);
////                } else {
////                    ll_bottom.setVisibility(View.VISIBLE);
////                }
//            }
//        });

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
////                    if (getLocalVisibleRect(img_purchase_now, 0)) {
//////                        ll_bottom.setVisibility(View.GONE);
////                    } else {
//////                        ll_bottom.setVisibility(View.VISIBLE);
////                    }
//                }
//            });
//        }

//        scrollView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (getLocalVisibleRect(img_ljgm, 0)) {
//                    ll_bottom.setVisibility(View.GONE);
//                } else {
//                    ll_bottom.setVisibility(View.VISIBLE);
//                }
//                return false;
//            }
//        });
    }

    /**
     * 判断当前view是否在屏幕可见
     */
    public boolean getLocalVisibleRect(View view, int offsetY) {
        Point p = new Point();
        getWindowManager().getDefaultDisplay().getSize(p);
        int screenWidth = p.x;
        int screenHeight = p.y;
        Rect rect = new Rect(0, 0, screenWidth, screenHeight);
        int[] location = new int[2];
        location[1] = location[1] + DimenUtil.dp2px(offsetY);
        view.getLocationInWindow(location);
        view.setTag(location[1]);//存储y方向的位置
        if (view.getLocalVisibleRect(rect)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 初始化会员权益数据
     */
//    private void addHyqyData() {
//        mList.add(new HyqxItemBean(R.mipmap.vip_hyqx01));
//        mList.add(new HyqxItemBean(R.mipmap.vip_hyqx02));
//        mList.add(new HyqxItemBean(R.mipmap.vip_hyqx03));
//        mList.add(new HyqxItemBean(R.mipmap.vip_hyqx04));
//        mList.add(new HyqxItemBean(R.mipmap.vip_hyqx05));
//        mList.add(new HyqxItemBean(R.mipmap.vip_hyqx06));
//        mList.add(new HyqxItemBean(R.mipmap.vip_hyqx07));
//        mList.add(new HyqxItemBean(R.mipmap.vip_hyqx08));
//        mList.add(new HyqxItemBean(R.mipmap.vip_hyqx09));
//        mList.add(new HyqxItemBean(R.mipmap.vip_hyqx10));
//        mList.add(new HyqxItemBean(R.mipmap.vip_hyqx11));
//    }
    @OnClick({R.id.tv_gmxz})
    public void btn_Login_Click(View v) {
        switch (v.getId()) {

            case R.id.tv_gmxz:
                // 购买须知
                Intent intent = new Intent(mContext, WebUrlActivity.class);
                intent.putExtra("title", "购买须知");
                intent.putExtra("url", "file:///android_asset/PurchaseNotes.html");
                startActivity(intent);
                break;
        }
    }

    /**
     * 立即购买
     */
//    private void dialogZf() {
////        Dialog dialog = new Dialog(this);//可以在style中设定dialog的样式
////        dialog.setContentView(R.layout.dialog_vip_purchased);
////        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
////        lp.gravity = Gravity.BOTTOM;
////        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
////        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
////        dialog.getWindow().setAttributes(lp);
//        //设置该属性，dialog可以铺满屏幕
////        dialog.getWindow().setBackgroundDrawable(null);
////        dialog.show();
////      dialog.getWindow().setWindowAnimations();
//        RelativeLayout rl_wx = dialog.findViewById(R.id.rl_wx);
//        RelativeLayout rl_zfb = dialog.findViewById(R.id.rl_zfb);
//        ImageView img_wx_zf = dialog.findViewById(R.id.img_wx_zf);
//        ImageView img_zfb_zf = dialog.findViewById(R.id.img_zfb_zf);
//        TextView tv_prices = dialog.findViewById(R.id.tv_price);
//        TextView tv_gm = dialog.findViewById(R.id.tv_gm);
//        TextView tv_gmxz = dialog.findViewById(R.id.tv_gmxz);
//
//        tv_prices.setText(tv_price.getText().toString());
//        tv_gmxz.setVisibility(View.GONE);
//
//        rl_wx.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                zfType = 1;
//                img_wx_zf.setVisibility(View.VISIBLE);
//                img_zfb_zf.setVisibility(View.GONE);
//            }
//        });
//
//        rl_zfb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                zfType = 2;
//                img_wx_zf.setVisibility(View.GONE);
//                img_zfb_zf.setVisibility(View.VISIBLE);
//            }
//        });
//
//        tv_gm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                // 确认支付
//                String price = tv_prices.getText().toString().replace("¥ ", "");
//                if (Float.parseFloat(price) > 0f) {
//                    pay(price);
//                } else {
//                    ToastUtils.shortShowStr(mContext, "请选择需要购买的课程");
//                }
//            }
//        });
//    }

    /**
     * 支付
     *
     * @param price
     */
    private void pay(String price) {
        String priceF = Util.changeY2F(Double.parseDouble(price)).split("\\.")[0];
        AddPayOrderBean addPayOrderBean = new AddPayOrderBean();
        AddPayOrderBean.AddPayOrderInfo addPayOrderInfo = new AddPayOrderBean.AddPayOrderInfo();
        addPayOrderInfo.setSubject(getString(R.string.app_name) + "-" + hyType);
        addPayOrderInfo.setBody(getString(R.string.app_name) + "-" + hyType);
        addPayOrderInfo.setChannel(Util.getChannel(mContext));
        List<AddPayOrderBean.GoodsListInfo> list = new ArrayList();
        list.add(new AddPayOrderBean.GoodsListInfo(prodCode, Integer.parseInt(priceF)));
        addPayOrderInfo.setGoodsList(list);
        addPayOrderInfo.setCurrencyType("CNY");
//        addPayOrderInfo.setUserId(memberUser);
        //setRecAccount  4: 微信支付 3: 支付宝
        if (zfType == 1) {
            // 微信支付
            if (Util.isWeixinAvilible(mContext)) {
                addPayOrderInfo.setRecAccount(wxPay);
            } else {
                ToastUtils.shortShowStr(mContext, "请先安装微信客户端");
                return;
            }
        } else {
            // 支付宝支付
            addPayOrderInfo.setRecAccount(aliPay);
        }
        addPayOrderInfo.setAmount(Integer.parseInt(priceF));
        addPayOrderBean.setParam(addPayOrderInfo);
        mPresenter.addPayOrder(addPayOrderBean);
    }

    // 会员切换
//    private void hySelect(int pos) {
//        switch (pos) {
//            case 1:
//                fl_yjhy.setBackgroundResource(R.mipmap.hy_yjhy);
//                tv_yjhy_color.setTextColor(getResources().getColor(R.color.text_E7C6));
////                tv_yjhy_xsqg.setVisibility(View.VISIBLE);
//                tv_yjhy_xj.setTextColor(getResources().getColor(R.color.white));
//                tv_yjhy_price.setTextColor(getResources().getColor(R.color.white));
//                tv_yjhy_yj.setTextColor(getResources().getColor(R.color.white));
//
//                fl_ndhy.setBackgroundResource(R.mipmap.hy_select_on);
////                tv_ndhy_xsqg.setVisibility(View.GONE);
//                tv_ndhy_xj.setTextColor(getResources().getColor(R.color.text_6E));
//                tv_ndhy_price.setTextColor(getResources().getColor(R.color.text_2B));
//                tv_ndhy_yj.setTextColor(getResources().getColor(R.color.text_6E));
//
//                fl_ydhy.setBackgroundResource(R.mipmap.hy_select_on);
////                tv_ydhy_xsqg.setVisibility(View.GONE);
//                tv_ydhy_xj.setTextColor(getResources().getColor(R.color.text_6E));
//                tv_ydhy_price.setTextColor(getResources().getColor(R.color.text_2B));
//                tv_ydhy_yj.setTextColor(getResources().getColor(R.color.text_6E));
//
//                tv_price.setText(tv_yjhy_price.getText().toString());
//                break;
//            case 2:
//                fl_yjhy.setBackgroundResource(R.mipmap.hy_select_on);
////                tv_yjhy_xsqg.setVisibility(View.GONE);
//                tv_yjhy_color.setTextColor(getResources().getColor(R.color.text_2B));
//                tv_yjhy_xj.setTextColor(getResources().getColor(R.color.text_6E));
//                tv_yjhy_price.setTextColor(getResources().getColor(R.color.text_2B));
//                tv_yjhy_yj.setTextColor(getResources().getColor(R.color.text_6E));
//
//                fl_ndhy.setBackgroundResource(R.mipmap.hy_ndhy);
////                tv_ndhy_xsqg.setVisibility(View.VISIBLE);
//                tv_ndhy_xj.setTextColor(getResources().getColor(R.color.text_6E));
//                tv_ndhy_price.setTextColor(getResources().getColor(R.color.text_2B));
//                tv_ndhy_yj.setTextColor(getResources().getColor(R.color.text_6E));
//
//                fl_ydhy.setBackgroundResource(R.mipmap.hy_select_on);
////                tv_ydhy_xsqg.setVisibility(View.GONE);
//                tv_ydhy_xj.setTextColor(getResources().getColor(R.color.text_6E));
//                tv_ydhy_price.setTextColor(getResources().getColor(R.color.text_2B));
//                tv_ydhy_yj.setTextColor(getResources().getColor(R.color.text_6E));
//
//                tv_price.setText(tv_ndhy_price.getText().toString());
//                break;
//            case 3:
//                fl_yjhy.setBackgroundResource(R.mipmap.hy_select_on);
////                tv_yjhy_xsqg.setVisibility(View.GONE);
//                tv_yjhy_color.setTextColor(getResources().getColor(R.color.text_2B));
//                tv_yjhy_xj.setTextColor(getResources().getColor(R.color.text_6E));
//                tv_yjhy_price.setTextColor(getResources().getColor(R.color.text_2B));
//                tv_yjhy_yj.setTextColor(getResources().getColor(R.color.text_6E));
//
//                fl_ndhy.setBackgroundResource(R.mipmap.hy_select_on);
////                tv_ndhy_xsqg.setVisibility(View.GONE);
//                tv_ndhy_xj.setTextColor(getResources().getColor(R.color.text_6E));
//                tv_ndhy_price.setTextColor(getResources().getColor(R.color.text_2B));
//                tv_ndhy_yj.setTextColor(getResources().getColor(R.color.text_6E));
//
//                fl_ydhy.setBackgroundResource(R.mipmap.hygm_yes);
////                tv_ydhy_xsqg.setVisibility(View.VISIBLE);
//                tv_ydhy_xj.setTextColor(getResources().getColor(R.color.text_6E));
//                tv_ydhy_price.setTextColor(getResources().getColor(R.color.text_2B));
//                tv_ydhy_yj.setTextColor(getResources().getColor(R.color.text_6E));
//
//                tv_price.setText(tv_ydhy_price.getText().toString());
//                break;
//        }
//    }

    /**
     * 支付宝支付业务示例
     */
    public void payV2(String orderInfo) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(VipActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 微信支付
     */
    private void wxPay(String return_code, String return_msg, String prepay_id, String nonce_str) {
        //1、 先向微信注册APPID
        IWXAPI wxapi = WXAPIFactory.createWXAPI(mContext, null);
        wxapi.registerApp(Constants.WX_APP_ID);

        if ("SUCCESS".equalsIgnoreCase(return_code) && "OK".equalsIgnoreCase(return_msg)) {
            PayReq req = new PayReq();
            req.appId = Constants.WX_APP_ID;//APPID
            req.partnerId = Constants.WX_MCH_ID;//商户号
            req.prepayId = prepay_id;
            req.nonceStr = nonce_str;
            String time = System.currentTimeMillis() / 1000 + "";
            req.timeStamp = time;//时间戳，这次是截取long类型时间的前10位
            req.packageValue = "Sign=WXPay";//参数是固定的，写死的
            SortedMap<String, String> sortedMap = new TreeMap<String, String>();//一定要注意键名，去掉下划线，字母全是小写
            sortedMap.put("appid", Constants.WX_APP_ID);
            sortedMap.put("partnerid", Constants.WX_MCH_ID);
            sortedMap.put("prepayid", prepay_id);
            sortedMap.put("noncestr", nonce_str);
            sortedMap.put("timestamp", time);
            sortedMap.put("package", "Sign=WXPay");
            req.sign = WeChatField.getSign(sortedMap);//重新存除了sign字段之外，再次签名，要不然唤起微信支付会返回-1，特别坑爹的是，键名一定要去掉下划线，不然返回-1
            //进行支付
            wxapi.sendReq(req);
        } else {
            ToastUtils.shortShowStr(mContext, "生成订单失败,请稍后重试!");
        }
    }

    /**
     * 永久会员提示弹窗
     */
    private void showDialog() {
        View diaView = View.inflate(mContext, R.layout.dialog_vip_have_purchased, null);
        ImageView img_qx = diaView.findViewById(R.id.img_qx);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(diaView);
        AlertDialog dialog = builder.create();
//        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        Window dialogWindow = dialog.getWindow();
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // 设置宽度
        p.width = (int) (d.getWidth() * 0.95); // 宽度设置为屏幕的0.95
        p.gravity = Gravity.CENTER;//设置位置
        //p.alpha = 0.8f;//设置透明度
        dialogWindow.setAttributes(p);

        img_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void httpCallback(AddPayOrderResponse response) {
        if (response.isSuccess()) {
            AddPayOrderResponse.AddPayOrderInfo result = response.getResult();
            if (result != null) {
                String payerType = result.getPayerType();
                orderId = result.getOrderId();
                if ("ALI".equals(payerType)) {
                    payV2(result.getSignOrder());
                } else if ("WX".equals(payerType)) {
                    String signOrder = result.getSignOrder();
                    SignOrderBean signOrderBean = JSON.parseObject(signOrder, SignOrderBean.class);
                    wxPay(signOrderBean.getReturn_code(), signOrderBean.getReturn_msg(), signOrderBean.getPrepay_id(), signOrder);
                }
            }
        } else {
            ToastUtils.shortShowStr(mContext, response.getMsg());
        }
    }

    @Override
    public void httpCallback(QueryOrderResponse response) {
        if (response.isSuccess()) {
            QueryOrderResponse.QueryOrderInfo result = response.getResult();
            if (result != null) {
                String orderStatus = result.getOrderStatus();
                if ("2".equals(orderStatus) || "3".equals(orderStatus)) {
                    querySum = 3;
                    // 交易成功
                    ToastUtils.shortShowStr(mContext, "支付成功");
                    mySharedPreferences.putValue("isOpenMember", "1");
                    mPresenter.getAccountInformation();
                } else {
                    // 重新查询
                    if (querySum > 0) {
                        querySum--;
                        mPresenter.queryOrder(orderId);
                    } else {
                        querySum = 3;
                        ToastUtils.shortShowStr(mContext, "支付成功");
                        ToastUtils.shortShowStr(mContext, "订单查询失败，请先联系客服。");
                    }
                }
            }
        }
    }

    @Override
    public void httpCallback(AccountInformationResponse response) {
        if (response.isSuccess()) {
            AccountInformationResponse.AccountInformationInfo result = response.getResult();
            if (result != null) {
                mySharedPreferences.putValue("MemberUser", result.getId());
                mySharedPreferences.putValue("level", result.getLevel());
                mySharedPreferences.putValue("dueTime", result.getDueTime());
                String level = result.getLevel();
                if (!TextUtils.isEmpty(level)) {
                    // 已开通会员
                    EventBusMessage eventBusMessage = new EventBusMessage();
                    eventBusMessage.setLevel(level);
                    eventBusMessage.setDueTime(result.getDueTime());
                    EventBus.getDefault().post(eventBusMessage);
                }
                if (spxq) {
                    setResult(10);
                }
                showGmcgDialog();

                Intent intent = new Intent();
                intent.setAction("refresh_data");
                sendBroadcast(intent);
            }
        }
    }

    @Override
    public void httpCallback(MemberLevelResponse response) {
        if (response.isSuccess()) {
            List<MemberLevelResponse.MemberLevelInfo> list = response.getList();
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    MemberLevelResponse.MemberLevelInfo memberLevelInfo = list.get(i);
                    if (memberLevelInfo != null) {
                        String code = memberLevelInfo.getCode();
                        int originalPrice = memberLevelInfo.getOriginalPrice();
                        int price = memberLevelInfo.getPrice();
                        ydId = memberLevelInfo.getGoodId();

                        Log.v("","各种参数"+originalPrice);
                        Log.v("","各种参数"+price);
                        Log.v("","各种参数"+ydId);

                        if ("2".equals(code)) {
                            prodCode = ydId;

                            tv_price_pay.setText(String.format("￥%s", Util.changeF2Y(mContext, price)));
                            tv_price_older.setText(Util.changeF2Y(mContext, originalPrice));
                            // 月度会员

//                            tv.setText(String.format("原价 ¥%s", Util.changeF2Y(mContext, originalPrice)));
//                            tv_ydhy_price.setText(String.format("¥ %s", Util.changeF2Y(mContext, price)));
                        }


//                        if ("9".equals(code)) {
//                            // 永久会员
//                            yjId = memberLevelInfo.getGoodId();
//                            prodCode = yjId;
//                            tv_yjhy_yj.setText(String.format("原价 ¥%s", Util.changeF2Y(mContext, originalPrice)));
//                            tv_yjhy_price.setText(String.format("¥ %s", Util.changeF2Y(mContext, price)));
//                            tv_price.setText(String.format("¥ %s", Util.changeF2Y(mContext, price)));
//                        } else if ("4".equals(code)) {
//                            // 年度会员
//                            ndId = memberLevelInfo.getGoodId();
//                            tv_ndhy_yj.setText(String.format("原价 ¥%s", Util.changeF2Y(mContext, originalPrice)));
//                            tv_ndhy_price.setText(String.format("¥ %s", Util.changeF2Y(mContext, price)));
//                        } else if ("2".equals(code)) {
//                            // 月度会员
//                            ydId = memberLevelInfo.getGoodId();
//                            tv_ydhy_yj.setText(String.format("原价 ¥%s", Util.changeF2Y(mContext, originalPrice)));
//                            tv_ydhy_price.setText(String.format("¥ %s", Util.changeF2Y(mContext, price)));
//                        }
                        }
                    }
                }
            } else {
                ToastUtils.shortShowStr(mContext, response.getMsg());
            }
        }

        @Override
        public void httpError (String e){
            ToastUtils.shortShowStr(mContext, e);
        }

        @Subscribe(threadMode = ThreadMode.MAIN)
        public void onEvent (WxPayMessageBean event){
            if (event.isWxPay()) {
                String requestGet = Util.requestGet(orderId, true);
                QueryOrderResponse queryOrderResponse = JSON.parseObject(requestGet, QueryOrderResponse.class);
                if (queryOrderResponse != null && queryOrderResponse.isSuccess()) {
                    QueryOrderResponse.QueryOrderInfo queryOrderInfo = queryOrderResponse.getResult();
                    if (queryOrderInfo != null) {
                        String orderStatus = queryOrderInfo.getOrderStatus();
                        if ("2".equals(orderStatus) || "3".equals(orderStatus)) {
                            querySum = 3;
                            // 交易成功
                            ToastUtils.shortShowStr(mContext, "支付成功");
                            // 获取用户信息
                            String requestStr = Util.requestGet("", false);
                            AccountInformationResponse accountInformationResponse = JSON.parseObject(requestStr, AccountInformationResponse.class);
                            if (accountInformationResponse != null && accountInformationResponse.isSuccess()) {
                                AccountInformationResponse.AccountInformationInfo informationInfo = accountInformationResponse.getResult();
                                mySharedPreferences.putValue("MemberUser", informationInfo.getId());
                                mySharedPreferences.putValue("level", informationInfo.getLevel());
                                mySharedPreferences.putValue("dueTime", informationInfo.getDueTime());
                                String level = informationInfo.getLevel();
                                if (!TextUtils.isEmpty(level)) {
                                    // 已开通会员
                                    EventBusMessage eventBusMessage = new EventBusMessage();
                                    eventBusMessage.setLevel(level);
                                    eventBusMessage.setDueTime(informationInfo.getDueTime());
                                    EventBus.getDefault().post(eventBusMessage);
                                }
                                if (spxq) {
                                    setResult(10);
                                }
                                showGmcgDialog();

                                Intent intent = new Intent();
                                intent.setAction("refresh_data");
                                sendBroadcast(intent);
                            }
                        } else {
                            // 重新查询
                            if (querySum > 2) {
                                querySum--;
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                String req = Util.requestGet(orderId, true);
                                QueryOrderResponse queryOrderResponse1 = JSON.parseObject(req, QueryOrderResponse.class);
                                if (queryOrderResponse1 != null && queryOrderResponse1.isSuccess()) {
                                    QueryOrderResponse.QueryOrderInfo queryOrderInfo1 = queryOrderResponse1.getResult();
                                    if (queryOrderInfo1 != null) {
                                        String orderStatus1 = queryOrderInfo1.getOrderStatus();
                                        if ("2".equals(orderStatus1) || "3".equals(orderStatus1)) {
                                            querySum = 3;
                                            // 交易成功
                                            ToastUtils.shortShowStr(mContext, "支付成功");
                                            // 获取用户信息
                                            String requestStr = Util.requestGet("", false);
                                            AccountInformationResponse accountInformationResponse = JSON.parseObject(requestStr, AccountInformationResponse.class);
                                            if (accountInformationResponse != null && accountInformationResponse.isSuccess()) {
                                                AccountInformationResponse.AccountInformationInfo informationInfo = accountInformationResponse.getResult();
                                                mySharedPreferences.putValue("MemberUser", informationInfo.getId());
                                                mySharedPreferences.putValue("level", informationInfo.getLevel());
                                                mySharedPreferences.putValue("dueTime", informationInfo.getDueTime());
                                                String level = informationInfo.getLevel();
                                                if (!TextUtils.isEmpty(level)) {
                                                    // 已开通会员
                                                    EventBusMessage eventBusMessage = new EventBusMessage();
                                                    eventBusMessage.setLevel(level);
                                                    eventBusMessage.setDueTime(informationInfo.getDueTime());
                                                    EventBus.getDefault().post(eventBusMessage);
                                                }
                                                if (spxq) {
                                                    setResult(10);
                                                }
                                                showGmcgDialog();

                                                Intent intent = new Intent();
                                                intent.setAction("refresh_data");
                                                sendBroadcast(intent);
                                            }
                                        } else {
                                            querySum = 3;
                                            ToastUtils.longShowStr(mContext, "订单查询失败，请联系客服。");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        /**
         * 购买成功提示弹窗
         */
        private void showGmcgDialog () {
            View diaView = View.inflate(mContext, R.layout.dialog_vip_have_purchased, null);
            ImageView img_qx = diaView.findViewById(R.id.img_qx);
            ImageView img_tp = diaView.findViewById(R.id.img_tp);

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setView(diaView);
            AlertDialog dialog = builder.create();
//        dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();

            Window dialogWindow = dialog.getWindow();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
            WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
            // 设置宽度
            p.width = (int) (d.getWidth() * 0.95); // 宽度设置为屏幕的0.95
            p.gravity = Gravity.CENTER;//设置位置
            //p.alpha = 0.8f;//设置透明度
            dialogWindow.setAttributes(p);

            img_qx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            img_tp.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    img_tp.setDrawingCacheEnabled(true);
                    Util.saveImageToGallery(mContext, img_tp.getDrawingCache());
                    return true;
                }
            });
        }

//    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
//
//        @NonNull
//        @Override
//        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//            View view = LayoutInflater.from(mContext).inflate(R.layout.hyqx_item_layout, viewGroup, false);
//            ViewHolder viewHolder = new ViewHolder(view);
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
//            viewHolder.img_icon.setImageResource(mList.get(i).getIcon());
//        }
//
//        @Override
//        public int getItemCount() {
//            return mList.size();
//        }
//
//        //自定义的ViewHolder，持有每个Item的的所有界面元素
//        public class ViewHolder extends RecyclerView.ViewHolder {
//            public ImageView img_icon;
//
//            public ViewHolder(View view) {
//                super(view);
//                img_icon = (ImageView) view.findViewById(R.id.img_icon);
//            }
//        }
//    }
    }
