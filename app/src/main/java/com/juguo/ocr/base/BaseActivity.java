package com.juguo.ocr.base;

import android.Manifest;
import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.juguo.ocr.R;
import com.juguo.ocr.MyApplication;
//import com.juguo.ocr.R;
import com.juguo.ocr.ui.activity.WebUrlActivity;
import com.juguo.ocr.utils.CommUtils;
import com.juguo.ocr.utils.KeyBoradHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Administrator on 2018/1/29.
 */

public class BaseActivity extends SupportActivity implements LifecycleOwner, ViewTreeObserver.OnGlobalLayoutListener {
    private MyApplication application;
    private BaseActivity mContext;
    public AlertDialog alertDialog;
    private ImmersionBar mImmersionBar;//状态栏沉浸


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (application == null) {
            // 得到Application对象
            application = MyApplication.getApp();
        }
        mContext = this;// 把当前的上下文对象赋值给BaseActivity
        addActivity();// 调用添加方法
        EventBus.getDefault().register(this);

        statusBarConfig().init();


//        checkPermission();


    }


    public void checkPermission() {
        int targetSdkVersion = 0;
        String[] PermissionString = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};
        try {
            final PackageInfo info = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            targetSdkVersion = info.applicationInfo.targetSdkVersion;//获取应用的Target版本
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
//            Log.e("err", "检查权限_err0");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Build.VERSION.SDK_INT是获取当前手机版本 Build.VERSION_CODES.M为6.0系统
            //如果系统>=6.0
            if (targetSdkVersion >= Build.VERSION_CODES.M) {
                //第 1 步: 检查是否有相应的权限
                boolean isAllGranted = checkPermissionAllGranted(PermissionString);
                if (isAllGranted) {
                    //Log.e("err","所有权限已经授权！");
                    return;
                }
                // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
                ActivityCompat.requestPermissions(this,
                        PermissionString, 1);
            }
        }
    }

    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                //Log.e("err","权限"+permission+"没有授权");
                return false;
            }
        }
        return true;
    }

    //申请权限结果返回处理
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            boolean isAllGranted = true;
            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }
            if (isAllGranted) {
                // 所有的权限都授予了
                Log.e("err", "权限都授权了");
            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                //容易判断错
                //MyDialog("提示", "某些权限未开启,请手动开启", 1) ;
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object myEvent) {

    }

    /**
     * 初始化沉浸式状态栏
     */
    protected ImmersionBar statusBarConfig() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this)
                .statusBarDarkFont(statusBarDarkFont())    //默认状态栏字体颜色为黑色
                .keyboardEnable(false, WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
        //必须设置View树布局变化监听，否则软键盘无法顶上去，还有模式必须是SOFT_INPUT_ADJUST_PAN
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(this);
        return mImmersionBar;
    }

    /**
     * 获取状态栏字体颜色
     */
    public boolean statusBarDarkFont() {
        //返回false表示白色字体
        return true;
    }

    // 添加Activity方法
    public void addActivity() {
        application.addActivity_(mContext);// 调用BaseApplication的添加Activity方法
    }

    //销毁当个Activity方法
    public void removeActivity() {
        application.removeActivity_(mContext);// 调用BaseApplication的销毁单个Activity方法
    }

    //销毁所有Activity方法
    public void removeALLActivity() {
        application.removeALLActivity_();// 调用BaseApplication的销毁所有Activity方法
    }

    /* 把Toast定义成一个方法  可以重复使用，使用时只需要传入需要提示的内容即可*/
    public void show_Toast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    public boolean isEmpty(String str) {
        if (!TextUtils.isEmpty(str) && !str.equals("null")) {
            return false;
        } else {
            return true;
        }
    }

    //等待上传的加载框
    public void dialogShow_Progress() {
        LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = mInflater.inflate(R.layout.dialog_common, null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext, R.style.loading_dialog_style).setView(convertView);
        alertDialog = dialog.create();
        alertDialog.show();
    }

    //等待上传的加载框
    public void dialogShow_Protocol() {
//        SharedPreferences sharedPreferences;
//        SharedPreferences.Editor editor;
//
//        //私有数据
//        sharedPreferences = getSharedPreferences("isagree", Context.MODE_PRIVATE);
//
//
//        //获取编辑器
//        editor = sharedPreferences.edit();
//
//        boolean isagree = sharedPreferences.getBoolean("isagree", false);
//
//        if (!isagree) {
//
//        }
        showPrivacyDialog();


    }

    private void showPrivacyDialog() {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        sharedPreferences = getSharedPreferences("isagree", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        boolean isagree = sharedPreferences.getBoolean("isagree", false);

        if (!isagree) {

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setCancelable(false);//点击屏幕和返回键对话框不消失
            final Dialog dialog;
            LinearLayout relativeLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_agree, null);
            builder.setView(relativeLayout);
            builder.setCancelable(false);
            Button bt_cancel = (Button) relativeLayout.findViewById(R.id.bt_cancle);
            Button bt_sure = (Button) relativeLayout.findViewById(R.id.bt_sure);
            TextView tv_message = (TextView) relativeLayout.findViewById(R.id.tv_message);

            String userProtocol = "《用户协议》";
            String privacyProtocol = "《隐私协议》";

            String message = "感谢您信任并使用" + getString(R.string.app_name) + "的产品和服务。在您使用JPG转换App前，请认真阅读并了解我们的";
            String messageAll = "感谢您信任并使用" + getString(R.string.app_name) + "的产品和服务。在您使用JPG转换App前，请认真阅读并了解我们的" + userProtocol + "和" + privacyProtocol;

            int start_User = message.length();
            int end_User = start_User + userProtocol.length() - 1;

            int start_Privacy = end_User + 3;
            int end_Privacy = start_Privacy + privacyProtocol.length() - 1;

            final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();

            spannableStringBuilder.append(messageAll);
            ClickableSpan userLicenseClickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {

                    Intent userProtocolIntent = new Intent(mContext, WebUrlActivity.class);

                    userProtocolIntent.putExtra("url", "file:///android_asset/UserProtocol.html");
                    userProtocolIntent.putExtra("title", "用户使用协议");
                    startActivity(userProtocolIntent);
                }
            };

            ClickableSpan privacyClickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    Intent privacyIntent = new Intent(mContext, WebUrlActivity.class);

                    privacyIntent.putExtra("url", "file:///android_asset/PrivacyProtocol2.html");
                    privacyIntent.putExtra("title", "用户隐私协议");
                    startActivity(privacyIntent);
                }
            };

            spannableStringBuilder.setSpan(userLicenseClickableSpan, start_User, end_User, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableStringBuilder.setSpan(privacyClickableSpan, start_Privacy, end_Privacy, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            tv_message.setMovementMethod(LinkMovementMethod.getInstance());
            tv_message.setText(spannableStringBuilder);


            /********************************************************/

            dialog = builder.show();

            bt_cancel.setOnClickListener(v -> {
                show_Toast("拜拜啦~");
                dialog.dismiss();

                mContext.finish();
            });

            bt_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.putBoolean("isagree", true);
                    editor.commit();//提交修改
                    dialog.dismiss();
                }
            });
        }
    }


    public void dialogDismiss() {
        if (alertDialog != null) alertDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity();
        EventBus.getDefault().unregister(this);
        if (mImmersionBar != null) mImmersionBar.destroy();
    }

    @Override
    public void onGlobalLayout() {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 防止重复快速点击跳转多个界面
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (CommUtils.checkDoubleClick()) {
                return true;
            }

            View v = getCurrentFocus();      //得到当前页面的焦点,ps:有输入框的页面焦点一般会被输入框占据
            if (KeyBoradHelper.isShouldHideKeyboard(v, ev)) { //判断用户点击的是否是输入框以外的区域
                KeyBoradHelper.hideKeyboard(mContext, v.getWindowToken());   //收起键盘
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}