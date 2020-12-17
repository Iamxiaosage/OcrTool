package com.juguo.ocr.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bytedance.sdk.openadsdk.preload.geckox.model.UpdatePackage;
import com.juguo.ocr.R;
import com.juguo.ocr.ui.activity.ContentActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

// ① 创建Adapter
public class NormalAdapter extends RecyclerView.Adapter<NormalAdapter.VH>{
    private final Context mContext;

    //② 创建ViewHolder
    public static class VH extends RecyclerView.ViewHolder{
        public final TextView title;
        public VH(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.tv_filename);
        }
    }

    private List<String> mDatas;
    public NormalAdapter(List<String> data,Context context) {
        this.mDatas = data;
        this.mContext=context;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.title.setText(mDatas.get(position));
//        holder.position
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int adapterPosition = holder.getAdapterPosition();
                String fileName = mDatas.get(adapterPosition);

                String str_result = loadText(mContext, fileName);
                Log.e("ocr","点击读取的内容："+str_result);
                Intent intent = new Intent(mContext, ContentActivity.class);
                intent.putExtra("result",str_result);
                mContext.startActivity(intent);
                //item 点击事件



            }
        });
    }


    //读取文本
    public String loadText(Context context,String fileName){
        FileInputStream fileInputStream=null;
        BufferedReader bufferedReader=null;
        StringBuilder content=new StringBuilder();
        try {
            fileInputStream=context.openFileInput(fileName);
            bufferedReader=new BufferedReader(new InputStreamReader(fileInputStream));
            String line="";
            while ((line=bufferedReader.readLine())!=null){
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fileInputStream!=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }


    @Override
    public int getItemCount() {
//        return 5;
        return mDatas.size();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false);
        return new VH(v);
    }
}