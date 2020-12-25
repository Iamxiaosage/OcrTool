package com.juguo.ocr.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

//import com.juguo.ocr.R;
import com.juguo.ocr.R;
import com.juguo.ocr.base.BaseActivity;
import com.juguo.ocr.utils.TitleBarUtils;


/**
 *  未登录页面
 */
public class NotLoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_login);

        ImageView img_w = findViewById(R.id.img_w);
        TextView tv_context = findViewById(R.id.tv_context);

        TitleBarUtils titleBarUtils = new TitleBarUtils(this);
        titleBarUtils.setLeftImageRes(R.mipmap.ic_black_black);
        titleBarUtils.setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        tv_context.setText("未登录请先登录");

        tv_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotLoginActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
