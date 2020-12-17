package com.juguo.ocr.ui.fragment;

//import com.juguo.ocr.R;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juguo.ocr.R;
import com.juguo.ocr.base.BaseActivity;
import com.juguo.ocr.base.BaseMvpFragment;
import com.juguo.ocr.ui.activity.contract.CenterContract;
import com.juguo.ocr.ui.activity.presenter.CenterPresenter;
import com.juguo.ocr.view.NormalAdapter;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ScanFragment extends BaseMvpFragment<CenterPresenter> implements CenterContract.View {


    @BindView(R.id.rv)
    RecyclerView rv;
    Unbinder unbinder;
    private BaseActivity mActivity;
    private NormalAdapter normalAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_scan;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.e("ocr","是否被隐藏了？"+hidden);
        if (!hidden){
            Log.e("ocr","刷新数据吧"+hidden);
            updateScanData();


        }

        super.onHiddenChanged(hidden);
    }

    @Override
    public void onResume() {
        Log.e("ocr","看得见了");

        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("ocr","看得见了");

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    public static Vector<String> getFileName(String fileAbsolutePath) {
        Vector<String> vecFile = new Vector<String>();
        File file = new File(fileAbsolutePath);
        File[] subFile = file.listFiles();

        for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
            // 判断是否为文件夹
            File cFile = subFile[iFileLength];
            String cFileName = cFile.getName();
            if (!cFile.isDirectory()&&cFileName.endsWith(".txt")) {
                String filename = subFile[iFileLength].getName();
                Log.e("eee","文件名 ： " + filename);
                vecFile.add(filename);
            }
        }
        return vecFile;
    }

    @Override
    protected void initViewAndData() {
        mActivity = getBindingActivity();



    }

    private void updateScanData() {
        String path_filesDir = mActivity.getFilesDir().getAbsolutePath();
        Vector<String> list_fileName = getFileName(path_filesDir);
        Iterator<String> iterator = list_fileName.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            Log.e("ocr","文件名"+next);
            Log.e("ocr","长度："+list_fileName.size());

        }

        if (normalAdapter==null){

            Log.e("ocr","路径"+path_filesDir);

            LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity );
//设置布局管理器
            rv.setLayoutManager(layoutManager);
//设置为垂直布局，这也是默认的
            layoutManager.setOrientation(OrientationHelper. VERTICAL);
//设置Adapter
            normalAdapter = new NormalAdapter(list_fileName,getActivity());
            rv.setAdapter(normalAdapter);
//        //设置分隔线
////        rv.addItemDecoration( new DividerGridItemDecoration(this ));
////设置增加或删除条目的动画
//        rv.setItemAnimator( new DefaultItemAnimator());
        }else {
            normalAdapter = new NormalAdapter(list_fileName,getActivity());
            rv.setAdapter(normalAdapter);
            normalAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void httpCallback(Object o) {

    }

    @Override
    public void httpError(String e) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
