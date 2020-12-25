package com.juguo.ocr.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.juguo.ocr.R;
import com.juguo.ocr.base.BaseActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContentActivity extends BaseActivity {
    @BindView(R.id.et_content)
    EditText et_Content;
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @BindView(R.id.iv_save)
    ImageView ivSave;
//    @BindView(R.id.iv_close)
//    ImageView iv_Close;
//    @BindView(R.id.fl_interaction_ad)
//    FrameLayout ll_interation_ad;

    //    @BindView(R.id.fl_interaction_ad)
//    FrameLayout mFl_InteractionAd;
//    private View convertView;
    public String srt_result;
    private Context mContext;
    private Activity mActivity;


    @Override
    public void dialogShow_Progress() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ContentActivity.this);
        final Dialog dialog;
        builder.setTitle("标题");
        builder.setCancelable(false);//点击屏幕和返回键对话框不消失
        LinearLayout relativeLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_save, null);
        builder.setView(relativeLayout);
        builder.setCancelable(false);
        Button bt_cancel = (Button) relativeLayout.findViewById(R.id.bt_cancle);
        Button bt_sure = (Button) relativeLayout.findViewById(R.id.bt_sure);
        EditText et_filename = (EditText) relativeLayout.findViewById(R.id.et_filename);
        dialog = builder.show();
        bt_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_filename = et_filename.getText().toString();
                String str_result_from_editext = et_Content.getText().toString();
                if (str_filename.isEmpty()) {
                    show_Toast("文件名不能为空！");
                } else {
                    File file = new File(str_filename);
                    WriteSysFile(ContentActivity.this, str_filename, str_result_from_editext);

                    dialog.dismiss();

                }
            }
        });
        bt_cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();

    }


    private void loadInteractionAd(String codeId) {
        TTAdNative mTTAdNative = TTAdSdk.getAdManager().createAdNative(mActivity);

        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId) //广告位id
                .setSupportDeepLink(true)
                .setAdCount(1) //请求广告数量为1到3条
                //width,height
                .setExpressViewAcceptedSize(320,330) //期望模板广告view的size,单位dp
                .build();

        mTTAdNative.loadInteractionExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            //请求广告失败
            @Override
            public void onError(int code, String message) {

                Log.v("ocr",code+"返回信息"+message);
                Toast.makeText(mActivity,"错误码。。"+code,Toast.LENGTH_LONG).show();
            }
            //请求广告成功
            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                Log.v("ocr",ads.toString());
                Toast.makeText(mActivity,"错误码。。"+ads.toString(),Toast.LENGTH_LONG).show();

                TTNativeExpressAd ttNativeExpressAd = ads.get(0);

                View expressAdView = ttNativeExpressAd.getExpressAdView();

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mActivity);
                builder.setCancelable(false);
//                builder.setIcon(R.drawable.bd_ocr_close);
                builder.setPositiveButton("关闭广告", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                builder.setTitle("文字识别完成，看会广告休息一下吧");
                android.app.AlertDialog dialog = builder.create();
//                dialog.setIcon(R.drawable.ic);
                dialog.setView(expressAdView);

                dialog.show();

                ttNativeExpressAd.render();

            }
        });
    }


    private void loadInteractionAd0(String codeId) {
        TTAdNative mTTAdNative = TTAdSdk.getAdManager().createAdNative(this);

        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId) //广告位id
                .setSupportDeepLink(true)
                .setAdCount(1) //请求广告数量为1到3条
                .setExpressViewAcceptedSize(1080, 1920) //期望模板广告view的size,单位dp
                .build();

        mTTAdNative.loadInteractionExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            //请求广告失败
            @Override
            public void onError(int code, String message) {

                Log.v("ocr", code + "返回信息" + message);
                Toast.makeText(ContentActivity.this, "错误码。。" + code, Toast.LENGTH_LONG).show();
            }

            //请求广告成功
            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {

//                mFl_InteractionAd.setVisibility(View.VISIBLE);

                Log.v("ocr", ads.toString());
                showToast(ads.toString());

//                TTNativeExpressAd ttNativeExpressAd = ads.get(0);
//                View expressAdView = ttNativeExpressAd.getExpressAdView();


                if (ads == null) {
                    return;
                }
                //获取SplashView
                TTNativeExpressAd ttNativeExpressAd = ads.get(0);
                View adView = ttNativeExpressAd.getExpressAdView();

//                mFl_InteractionAd.addView(view);
//
//
//                if (mFl_InteractionAd != null && !ContentActivity.this.isFinishing()) {
//                    mFl_InteractionAd.removeAllViews();
//                    //把SplashView 添加到ViewGroup中,注意开屏广告view：width >=70%屏幕宽；height >=50%屏幕高
//                    mFl_InteractionAd.addView(view);
//                    //设置不开启开屏广告倒计时功能以及不显示跳过按钮,如果这么设置，您需要自定义倒计时逻辑
//                    //ad.setNotAllowSdkCountdown();
//                } else {
////                    goToMainActivity();
//                }

//                ttNativeExpressAd.set(3000);
//                ttNativeExpressAd.dis


                ttNativeExpressAd.setExpressInteractionListener(new TTNativeExpressAd.AdInteractionListener() {
                    //广告关闭回调
                    @Override
                    public void onAdDismiss() {

                        showToast("开屏广告跳过onAdDismiss");

                    }

                    //广告点击回调
                    @Override
                    public void onAdClicked(View view, int type) {
                        showToast("开屏广告跳过onAdClicked");


                    }

                    //广告展示回调
                    @Override
                    public void onAdShow(View view, int type) {
                        showToast("开屏广告跳过onAdShow");


                    }

                    //广告渲染失败回调
                    @Override
                    public void onRenderFail(View view, String msg, int code) {
                        showToast("开屏广告跳过onRenderFail");


                    }

                    //广告渲染成功回调
                    @Override
                    public void onRenderSuccess(View view, float width, float height) {
                        //在渲染成功回调时展示广告，提升体验
                        showToast("开屏广告跳过");

//                        ttNativeExpressAd.showInteractionExpressAd(ContentActivity.this);
                    }
                });


//                LinearLayout convertView = (LinearLayout)View.inflate(ContentActivity.this, R.layout.dialog_interaction_ad, null);
//                LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////                View convertView = mInflater.inflate(R.layout.dialog_interaction_ad, null);
//                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext, R.style.loading_dialog_style).setView(convertView);
//                alertDialog = dialog.create();
//                alertDialog.show();


                AlertDialog.Builder builder = new AlertDialog.Builder(ContentActivity.this);
                final Dialog dialog;
                builder.setTitle("标题");
                builder.setCancelable(false);//点击屏幕和返回键对话框不消失
//                LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_interaction_ad, null);
//                builder.setView(linearLayout);
                builder.setView(adView);
                builder.setCancelable(false);

//                ImageView iv_close = (ImageView) linearLayout.findViewById(R.id.iv_close);
//                FrameLayout fl_ad = (FrameLayout) linearLayout.findViewById(R.id.fl_ad);
//
//                fl_ad.addView(adView);



                dialog = builder.create();




//                iv_close.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        dialog.dismiss();
//
//                    }
//                });
//                bt_cancel.setOnClickListener(v -> {
//                    dialog.dismiss();
//                });
                dialog.show();


//                LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View convertView = mInflater.inflate(R.layout.dialog_interaction_ad, null);
//                ImageView iv_close = (ImageView)convertView.findViewById(R.id.iv_close);
//                FrameLayout fl_ad = (FrameLayout)convertView.findViewById(R.id.fl_ad);
//                fl_ad.addView(adView);
//
//                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ContentActivity.this);
//                builder.setView(convertView);
//
//                android.app.AlertDialog dialog = builder.create();
////                dialog.setView(convertView);
//
//                iv_close.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        fl_ad.removeView(adView);
//                        dialog.dismiss();
//                    }
//                });
//
//
//
//                dialog.show();

//
//                ttNativeExpressAd.render();

            }
        });
    }

    private void showToast(String ads) {
        Toast.makeText(ContentActivity.this, "请求到广告了。。" + ads.toString(), Toast.LENGTH_LONG).show();
    }


    public void WriteSysFile(Context context, String filename, String str_result_from_editext) {
        try {
            FileOutputStream fos = context.openFileOutput(filename + ".txt", Context.MODE_PRIVATE);//openFileOutput函数会自动创建文件
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            osw.write(str_result_from_editext);
            osw.flush();
            fos.flush();  //输出缓冲区中所有的内容
            osw.close();
            fos.close();
            show_Toast("保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.iv_back, R.id.iv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.iv_save:
                dialogShow_Progress();

                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ButterKnife.bind(this);

        mContext = getBaseContext();

        Intent intent = getIntent();
        if (intent != null) {
            srt_result = intent.getStringExtra("result");
            et_Content.setText(srt_result);
        }


        ivBack.post(new Runnable() {
            @Override
            public void run() {

                loadInteractionAd("945699954");
            }
        });

        mActivity=ContentActivity.this;

//        iv_Close.setVisibility(View.VISIBLE);
//        iv_Close.setOnClickListener(new CloseListener(ContentActivity.this));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        loadInteractionAd("945699954");
    }

    private class CloseListener implements View.OnClickListener {
        public CloseListener(ContentActivity contentActivity) {

        }

        @Override
        public void onClick(View v) {
//            mFl_InteractionAd.removeAllViews();
//            v.setVisibility(View.GONE);
        }
    }
}