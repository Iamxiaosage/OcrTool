package com.juguo.ocr.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.juguo.ocr.R;
import com.juguo.ocr.base.BaseActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//import com.juguo.ocr.R;

public class ContentActivity extends BaseActivity {

    @BindView(R.id.et_content)
    EditText et_Content;
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @BindView(R.id.iv_save)
    ImageView ivSave;
    private View convertView;
    public String srt_result;


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
                if (str_filename.isEmpty()){
                    show_Toast("文件名不能为空！");
                }else {
                    File file = new File(str_filename);
                    WriteSysFile(ContentActivity.this,str_filename,str_result_from_editext);
//                    new BufferedReader(new InputStreamReader(""));

                    dialog.dismiss();

                }
            }
        });
        bt_cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();

    }


    public void WriteSysFile(Context context, String filename, String str_result_from_editext) {
        try {
//            File file = new File(Environment.getExternalStorageDirectory() + filename + ".txt");
            FileOutputStream fos = context.openFileOutput(filename+".txt", Context.MODE_PRIVATE);//openFileOutput函数会自动创建文件
//            FileOutputStream fos = new FileOutputStream(file);//openFileOutput函数会自动创建文件
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


        Intent intent = getIntent();
        if (intent != null) {
            srt_result = intent.getStringExtra("result");
            et_Content.setText(srt_result);

        }

    }



}