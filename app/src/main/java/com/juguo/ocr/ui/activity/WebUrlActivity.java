package com.juguo.ocr.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.juguo.ocr.R;
import com.juguo.ocr.base.BaseMvpActivity;
import com.juguo.ocr.response.AccountInformationResponse;
import com.juguo.ocr.response.LoginResponse;
import com.juguo.ocr.ui.MainActivity;
import com.juguo.ocr.ui.activity.contract.LoginContract;
import com.juguo.ocr.ui.activity.presenter.LoginPresenter;
import com.juguo.ocr.utils.TitleBarUtils;

//import com.juguo.ocr.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2019/10/30.
 */

public class WebUrlActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.bt_agree)
    Button btAgree;
    private String title;
    private WebView urlWebView;
    private String resId;
    private TitleBarUtils titleBarUtils;


    private Context mContext;

    // 该推文是否已经收藏  1为收藏  2为未收藏
    private int isEnshrine = 2;
    private boolean isScJr;
    private String url;
    private String mIsAtention;

    @Override
    protected int getLayout() {
        return R.layout.activity_web_url;
    }


    /**
     * H5 交互 class
     */


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewAndData() {
        mContext = this;


        url = getIntent().getStringExtra("url");

        initView();

    }

    /**
     * H5 交互 class
     */
    public class JsInteration {
        @JavascriptInterface
        public void back(String value) {
//            value就是 H5 界面传递过来的参数值
            String[] arrayList = value.split("#");
            mIsAtention = arrayList[0];
        }
    }

    public void initView() {
        urlWebView = (WebView) findViewById(R.id.webView);
        urlWebView.getSettings().setJavaScriptEnabled(true);
        urlWebView.setWebViewClient(new WebViewClient() {// tel://400-666-0360
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                //页面加载完毕
                //从 Android 传递参数到 H5 界面
                String par_url = "javascript:getFromAndroid(\"" + getString(R.string.company_name) + "," + getString(R.string.app_name) + "\")";
                view.loadUrl(par_url);
                Log.e("cartoon","这是啥参数啊："+par_url);

            }


            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if ("tel://400-666-0360".equals(url)){
//                    String[] split = url.split("//");
//                    Intent in = new Intent(Intent.ACTION_DIAL);
//                    in.setData(Uri.parse("tel:" + split[1]));
//                    startActivity(in);
//                    return true;
//                }else {
                return false;
//                }
            }
        });

        urlWebView.addJavascriptInterface(new  JsInteration(), "android");

//        urlWebView.loadUrl("file:///android_asset/PrivacyProtocol2.html");
        urlWebView.loadUrl(url);
    }

    @Override
    public void httpCallback(LoginResponse user) {

    }

    @Override
    public void httpCallback(AccountInformationResponse response) {

    }

    @Override
    public void httpError(String e) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_agree)
    public void onViewClicked() {

        Intent intent = new Intent(WebUrlActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
