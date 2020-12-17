package com.juguo.ocr.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.BankCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
//import com.juguo.ocr.R;
import com.juguo.ocr.R;
import com.juguo.ocr.base.BaseMvpFragment;
import com.juguo.ocr.dragger.bean.User;
import com.juguo.ocr.dragger.bean.UserInfo;
import com.juguo.ocr.ocr.bean.VatInvoce;
import com.juguo.ocr.ocr.bean.Words;
import com.juguo.ocr.ocr.ui.activity.IDCardActivity;
import com.juguo.ocr.ocr.ui.activity.RecognizeService;
import com.juguo.ocr.ocr.utils.FileUtil;
import com.juguo.ocr.response.AccountInformationResponse;
import com.juguo.ocr.response.LoginResponse;
import com.juguo.ocr.ui.activity.ContentActivity;
import com.juguo.ocr.ui.activity.contract.HomeContract;
import com.juguo.ocr.ui.activity.presenter.HomePresenter;
import com.juguo.ocr.utils.CommUtils;
import com.juguo.ocr.utils.Constants;
import com.juguo.ocr.utils.MySharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

import static com.mob.tools.utils.DeviceHelper.getApplication;

public class HomeFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View {


    private static final int REQUEST_CODE_GENERAL = 105;
    private static final int REQUEST_CODE_GENERAL_BASIC = 106;
    private static final int REQUEST_CODE_ACCURATE_BASIC = 107;
    private static final int REQUEST_CODE_ACCURATE = 108;
    private static final int REQUEST_CODE_GENERAL_ENHANCED = 109;
    private static final int REQUEST_CODE_GENERAL_WEBIMAGE = 110;
    private static final int REQUEST_CODE_BANKCARD = 111;
    private static final int REQUEST_CODE_VEHICLE_LICENSE = 120;
    private static final int REQUEST_CODE_DRIVING_LICENSE = 121;
    private static final int REQUEST_CODE_LICENSE_PLATE = 122;
    private static final int REQUEST_CODE_BUSINESS_LICENSE = 123;
    private static final int REQUEST_CODE_RECEIPT = 124;

    private static final int REQUEST_CODE_PASSPORT = 125;
    private static final int REQUEST_CODE_NUMBERS = 126;
    private static final int REQUEST_CODE_QRCODE = 127;
    private static final int REQUEST_CODE_BUSINESSCARD = 128;
    private static final int REQUEST_CODE_HANDWRITING = 129;
    private static final int REQUEST_CODE_LOTTERY = 130;
    private static final int REQUEST_CODE_VATINVOICE = 131;
    private static final int REQUEST_CODE_CUSTOM = 132;

    private static final int REQUEST_CODE_ID_CARD = 132;


    private boolean hasGotToken = false;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            infoPopText(msg.obj.toString());

        }
    };


    @BindView(R.id.general_basic_button)
    ImageView general_basic_button;
    @BindView(R.id.handwritting_button)
    ImageView accurate_basic_button;
    @BindView(R.id.idcard_button)
    ImageView idcard_button;
    @BindView(R.id.general_webimage_button)
    ImageView general_webimage_button;
    @BindView(R.id.bankcard_button)
    ImageView bankcard_button;
    @BindView(R.id.business_license_button)
    ImageView businessLicenseButton;
    @BindView(R.id.accurate_basic_button)
    ImageView accurateBasicButton;
    @BindView(R.id.general_button)
    ImageView general_button;
    @BindView(R.id.accurate_button)
    ImageView accurate_button;
    @BindView(R.id.vat_invoice_button)
    ImageView vatInvoiceButton;
    @BindView(R.id.general_enhance_button)
    ImageView general_enhance_button;
    @BindView(R.id.driving_license_button)
    ImageView driving_license_button;
    @BindView(R.id.vehicle_license_button)
    ImageView vehicle_license_button;
    @BindView(R.id.license_plate_button)
    ImageView license_plate_button;
    @BindView(R.id.receipt_button)
    ImageView receiptButton;
    @BindView(R.id.custom_button)
    ImageView customButton;
    @BindView(R.id.passport_button)
    ImageView passportButton;
    @BindView(R.id.numbers_button)
    ImageView numbersButton;
    @BindView(R.id.qrcode_button)
    ImageView qrcodeButton;
    @BindView(R.id.business_card_button)
    ImageView businessCardButton;
    @BindView(R.id.lottery_button)
    ImageView lotteryButton;
    @BindView(R.id.activity_main)
    LinearLayout ll_Main;
    Unbinder unbinder;
    private Context mContext;
    private Context mAppContext;
    private AlertDialog.Builder alertDialog;
    public static String Tag = "ocr";
    private Intent intent;

    private MySharedPreferences mMySharedPreferences;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewAndData() {

        mContext = getBindingActivity();
        mAppContext = getApplicationContext();
        alertDialog = new AlertDialog.Builder(mContext);




        initAccessTokenWithAkSk();


        mMySharedPreferences = new MySharedPreferences(getActivity(), "Shared");
        String uuid = (String) mMySharedPreferences.getValue("uuid", "");
        if (TextUtils.isEmpty(uuid)) {
            mMySharedPreferences.putValue("uuid", CommUtils.getUniqueID(getActivity()));
        }
        mPresenter.login(loginType());
    }

    private void setIsEnabled(boolean isEnable) {
        general_basic_button.setEnabled(isEnable);
        accurate_basic_button.setEnabled(isEnable);
        idcard_button.setEnabled(isEnable);
        general_webimage_button.setEnabled(isEnable);
        bankcard_button.setEnabled(isEnable);
        businessLicenseButton.setEnabled(isEnable);
        accurateBasicButton.setEnabled(isEnable);
        general_button.setEnabled(isEnable);
        accurate_button.setEnabled(isEnable);
        vatInvoiceButton.setEnabled(isEnable);
        general_enhance_button.setEnabled(isEnable);
        driving_license_button.setEnabled(isEnable);
        vehicle_license_button.setEnabled(isEnable);
        license_plate_button.setEnabled(isEnable);
        receiptButton.setEnabled(isEnable);
        customButton.setEnabled(isEnable);
        passportButton.setEnabled(isEnable);
        numbersButton.setEnabled(isEnable);
        qrcodeButton.setEnabled(isEnable);
        businessCardButton.setEnabled(isEnable);
        lotteryButton.setEnabled(isEnable);
    }


    private void initAccessTokenWithAkSk() {
        OCR.getInstance(mContext).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                String token = result.getAccessToken();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                alertText("AK，SK方式获取token失败", error.getMessage());
            }
        }, getApplicationContext(), "t1IDcYXB0GtdTTt54QkbSGw3", "oxGEVMDOYbG3THiIfltfZgyFKSZXghyz");
    }


    @Override
    public void httpCallback(LoginResponse loginResponse) {

        if (loginResponse.isSuccess()) {
            String result = loginResponse.getResult();
            if (!TextUtils.isEmpty(result)) {
                mMySharedPreferences.putValue("token", result);

                // 已经获取到token调用其他接口
                mPresenter.getAccountInformation();
            }
        }
    }

    @Override
    public void httpCallback(AccountInformationResponse response) {
        if (response.isSuccess()) {
            AccountInformationResponse.AccountInformationInfo result = response.getResult();
            if (result != null) {
                mMySharedPreferences.putValue("MemberUser", result.getId());
                mMySharedPreferences.putValue("level", result.getLevel());
                mMySharedPreferences.putValue("dueTime", result.getDueTime());
            }
        }
    }

    @Override
    public void httpError(String e) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(getActivity(), rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initAccessToken() {
        OCR.getInstance(getActivity()).initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                alertText("licence方式获取token失败", error.getMessage());
            }
        }, mAppContext);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initAccessToken();
        } else {
            Toast.makeText(getApplicationContext(), "需要android.permission.READ_PHONE_STATE", Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkTokenStatus() {
        if (!hasGotToken) {
            Toast.makeText(getActivity().getApplicationContext(), "token还未成功获取", Toast.LENGTH_LONG).show();
        }
        return hasGotToken;
    }

    private void alertText(final String title, final String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(mContext, ContentActivity.class);
                intent.putExtra("result", message);
                startActivity(intent);


//                EditText editText = new EditText(mContext);
//
//
//                editText.setText(message);
//                alertDialog.setTitle(title)
////                        .setMessage(message)
//                        .setView(editText)
//                        .setPositiveButton("确定", null)
//                        .show();
            }
        });
    }

    private void infoPopText(final String result) {
        alertText("", result);
        System.out.println("返回内容：" + result);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        setIsEnabled(true);
        super.onActivityResult(requestCode, resultCode, data);
        // 识别成功回调，通用文字识别（含位置信息）
        if (requestCode == REQUEST_CODE_GENERAL && resultCode == Activity.RESULT_OK) {
            RecognizeService.recGeneral(mContext, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
                            infoPopText(result_str);
                        }
                    });
        }

        // 识别成功回调，通用文字识别（含位置信息高精度版）
        if (requestCode == REQUEST_CODE_ACCURATE && resultCode == Activity.RESULT_OK) {
            RecognizeService.recAccurate(mContext, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {

                            infoPopText(result_str);
                        }
                    });
        }

        // 识别成功回调，通用文字识别
        if (requestCode == REQUEST_CODE_GENERAL_BASIC && resultCode == Activity.RESULT_OK) {
            RecognizeService.recGeneralBasic(mContext, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {

//                            infoPopText(result_str);
                            String s = analyseWord(result_str);
                            Message message = new Message();
                            message.what = 1;
                            message.obj = s;

                            handler.sendMessage(message);

                        }
                    });
        }


        // 识别成功回调，通用文字识别（高精度版）
        if (requestCode == REQUEST_CODE_ACCURATE_BASIC && resultCode == Activity.RESULT_OK) {
            RecognizeService.recAccurateBasic(mContext, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
                            infoPopText(result_str);
                        }
                    });
        }

        // 识别成功回调，通用文字识别（含生僻字版）
        if (requestCode == REQUEST_CODE_GENERAL_ENHANCED && resultCode == Activity.RESULT_OK) {
            RecognizeService.recGeneralEnhanced(mContext, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
                            infoPopText(result_str);
                        }
                    });
        }

        // 识别成功回调，网络图片文字识别
        if (requestCode == REQUEST_CODE_GENERAL_WEBIMAGE && resultCode == Activity.RESULT_OK) {
            RecognizeService.recWebimage(mContext, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {

                            String str_webcharacter = analyseWord(result_str);
                            infoPopText(str_webcharacter);
//                            infoPopText(result_str);
                        }
                    });
        }

        // 识别成功回调，银行卡识别
        if (requestCode == REQUEST_CODE_BANKCARD && resultCode == Activity.RESULT_OK) {
            final String absolutePath = FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();

            Log.e("ocr", "ocr地址：" + absolutePath);


            RecognizeService.recBankCard(mContext, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
                            Log.e("ocr", "ocr地址：" + absolutePath);


                            Log.e("ocr", "字符串：" + result_str);

                            BankCardResult bankCardResult = new Gson().fromJson(result_str, BankCardResult.class);
                            Log.e("ocr", "对象：" + bankCardResult.toString());

                            result_str = getStr(R.string.bank_card_num) + bankCardResult.getBankCardNumber() + "\n"
                                    + getStr(R.string.bank_name) + bankCardResult.getBankName() + "\n"
                                    + getStr(R.string.bank_card_type) + bankCardResult.getBankCardType() + "\n"
                                    + getStr(R.string.log_id) + bankCardResult.getLogId() + "\n";

                            infoPopText(result_str);
                        }
                    });
        }

        // 识别成功回调，行驶证识别
        if (requestCode == REQUEST_CODE_VEHICLE_LICENSE && resultCode == Activity.RESULT_OK) {
            RecognizeService.recVehicleLicense(mContext, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
//                            new Gson().fromJson(result_str,)
                            HashMap<String, String> result_vehicle = analyseVehicleLicense(result_str);


//                            String key_socal_credit_code = getStr(R.string.);
//                            String key_composeForm = getStr(R.string.);
//                            String key_manageScope = getStr(R.string.);
//                            String key_establishDate = getStr(R.string.);
//                            String key_legalPerson = getStr((R.string.));
//                            String key_registerCapital = getStr(R.string.);
//                            String key_licenseNum = getStr(R.string.);
//                            String key_address = getStr(R.string.);
//                            String key_companyName = getStr(R.string.);

                            Set<Map.Entry<String, String>> entrySet = result_vehicle.entrySet();
                            Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
                            StringBuffer sb_BussinessLicense = new StringBuffer();
                            while (iterator.hasNext()) {
                                Map.Entry<String, String> next = iterator.next();
                                sb_BussinessLicense.append(next.getKey() + ":" + next.getValue() + "\n");
                            }


//                            result_str = getStr(R.string.vehicle_recognize_code) + result_vehicle.get() + "\n"
//                                    + getStr(R.string.Date_Of_Issue) + bankCardResult.getBankName() + "\n"
//                                    + getStr(R.string.brand_model) + bankCardResult.getBankCardType() + "\n"
//                                    + getStr(R.string.Vehicle_type) + bankCardResult.getBankCardType() + "\n"
//                                    + getStr(R.string.owner) + bankCardResult.getBankCardType() + "\n"
//                                    + getStr(R.string.Nature_of_use) + bankCardResult.getBankCardType() + "\n"
//                                    + getStr(R.string.bank_card_type) + bankCardResult.getBankCardType() + "\n"
//                                    + getStr(R.string.Engine_number) + bankCardResult.getBankCardType() + "\n"
//                                    + getStr(R.string.bank_card_type) + bankCardResult.getBankCardType() + "\n"
//                                    + getStr(R.string.Plate_number) + bankCardResult.getBankCardType() + "\n"
//                                    + getStr(R.string.Date_of_registration) + bankCardResult.getLogId() + "\n";


                            infoPopText(sb_BussinessLicense.toString());
                        }
                    });
        }

        // 识别成功回调，驾驶证识别
        if (requestCode == REQUEST_CODE_DRIVING_LICENSE && resultCode == Activity.RESULT_OK) {
            RecognizeService.recDrivingLicense(mContext, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
                            infoPopText(result_str);
                        }
                    });
        }

        // 识别成功回调，车牌识别
        if (requestCode == REQUEST_CODE_LICENSE_PLATE && resultCode == Activity.RESULT_OK) {
            RecognizeService.recLicensePlate(mContext, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
                            infoPopText(result_str);
                        }
                    });
        }

        // 识别成功回调，营业执照识别
        if (requestCode == REQUEST_CODE_BUSINESS_LICENSE && resultCode == Activity.RESULT_OK) {
            RecognizeService.recBusinessLicense(mContext, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
//                            BusinessLicense businessLicense = new Gson().fromJson(result_str, BusinessLicense.class);
//                        businessLicense.get成立日期();
//                            String s = new Gson().toJson(businessLicense);
//                            Log.e("ocr", "营业执照：" + s);


                            HashMap<String, String> map_businessLicense = analyseBusinessLicense(result_str);
                            Set<Map.Entry<String, String>> entrySet = map_businessLicense.entrySet();
                            Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
                            StringBuffer sb_BussinessLicense = new StringBuffer();
                            while (iterator.hasNext()) {
                                Map.Entry<String, String> next = iterator.next();
                                sb_BussinessLicense.append(next.getKey() + ":" + next.getValue() + "\n");
                            }

                            Log.e("ocr", "总数据：" + sb_BussinessLicense);


                            infoPopText(sb_BussinessLicense.toString());
                        }
                    });
        }

        // 识别成功回调，通用票据识别
        if (requestCode == REQUEST_CODE_RECEIPT && resultCode == Activity.RESULT_OK) {
            RecognizeService.recReceipt(mContext, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
                            infoPopText(result_str);
                        }
                    });
        }

        // 识别成功回调，护照
        if (requestCode == REQUEST_CODE_PASSPORT && resultCode == Activity.RESULT_OK) {
            RecognizeService.recPassport(mContext, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
                            infoPopText(result_str);
                        }
                    });
        }

        // 识别成功回调，二维码
        if (requestCode == REQUEST_CODE_QRCODE && resultCode == Activity.RESULT_OK) {
            RecognizeService.recQrcode(mContext, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
                            infoPopText(result_str);
                        }
                    });
        }

        // 识别成功回调，彩票
        if (requestCode == REQUEST_CODE_LOTTERY && resultCode == Activity.RESULT_OK) {
            RecognizeService.recLottery(mContext, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
                            infoPopText(result_str);
                        }
                    });
        }

        // 识别成功回调，增值税发票
        if (requestCode == REQUEST_CODE_VATINVOICE && resultCode == Activity.RESULT_OK) {
            RecognizeService.recVatInvoice(mContext, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
                            try {
                                Log.e(Tag, "发票原始值：" + result_str);
//                            Log.e(getTag(),"发票原始值："+result_str);
                                String str_vatInvoce = analyseVatInvoce(result_str);
                                infoPopText(str_vatInvoce);
                            } catch (JsonSyntaxException e) {
                                Log.e(Tag, "你扫的啥呀：" + result_str);

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext, "你扫的啥呀？", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    });
        }

        // 识别成功回调，数字
        if (requestCode == REQUEST_CODE_NUMBERS && resultCode == Activity.RESULT_OK) {
            RecognizeService.recNumbers(mContext, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
                            infoPopText(result_str);
                        }
                    });
        }

        // 识别成功回调，手写
        if (requestCode == REQUEST_CODE_HANDWRITING && resultCode == Activity.RESULT_OK) {
            RecognizeService.recHandwriting(mContext, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
                            String str_WordsByWriting = analyseWord(result_str);
                            infoPopText(str_WordsByWriting);
                        }
                    });
        }

        // 识别成功回调，名片
        if (requestCode == REQUEST_CODE_BUSINESSCARD && resultCode == Activity.RESULT_OK) {
            RecognizeService.recBusinessCard(mContext, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
                            infoPopText(result_str);
                        }
                    });
        }

        // 识别成功回调，自定义模板
        if (requestCode == REQUEST_CODE_CUSTOM && resultCode == Activity.RESULT_OK) {
            RecognizeService.recCustom(mContext, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
                            infoPopText(result_str);
                        }
                    });
        }
    }

    @OnClick({R.id.general_basic_button, R.id.handwritting_button, R.id.idcard_button, R.id.general_webimage_button, R.id.bankcard_button, R.id.business_license_button, R.id.accurate_basic_button, R.id.general_button, R.id.accurate_button, R.id.vat_invoice_button, R.id.general_enhance_button, R.id.driving_license_button, R.id.vehicle_license_button, R.id.license_plate_button, R.id.receipt_button, R.id.custom_button, R.id.passport_button, R.id.numbers_button, R.id.qrcode_button, R.id.business_card_button, R.id.lottery_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.general_basic_button:
                if (!checkTokenStatus()) {
                    return;
                }
                intent = new Intent(mContext, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_GENERAL_BASIC);
                break;
            case R.id.handwritting_button:
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(mContext, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_HANDWRITING);

                break;
            case R.id.idcard_button:
                if (!checkTokenStatus()) {
                    return;
                }
                intent = new Intent(mContext, IDCardActivity.class);
//                startActivity(intent);
                startActivityForResult(intent,REQUEST_CODE_ID_CARD);
                break;
            case R.id.general_webimage_button:
                if (!checkTokenStatus()) {
                    return;
                }
                intent = new Intent(mContext, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_GENERAL_WEBIMAGE);
                break;
            case R.id.bankcard_button:
                if (!checkTokenStatus()) {
                    return;
                }
                intent = new Intent(mContext, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_BANK_CARD);
                startActivityForResult(intent, REQUEST_CODE_BANKCARD);
                break;
            case R.id.business_license_button:
                if (!checkTokenStatus()) {
                    return;
                }
                intent = new Intent(mContext, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_BUSINESS_LICENSE);

                break;
            case R.id.accurate_basic_button:
                if (!checkTokenStatus()) {
                    return;
                }
                intent = new Intent(mContext, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_ACCURATE_BASIC);
                break;
            case R.id.general_button:
                if (!checkTokenStatus()) {
                    return;
                }
                intent = new Intent(mContext, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_GENERAL);
                break;
            case R.id.accurate_button:
                if (!checkTokenStatus()) {
                    return;
                }
                intent = new Intent(mContext, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_ACCURATE);
                break;
            case R.id.vat_invoice_button:
                if (!checkTokenStatus()) {
                    return;
                }
                intent = new Intent(mContext, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_VATINVOICE);
                break;
            case R.id.general_enhance_button:
                if (!checkTokenStatus()) {
                    return;
                }
                intent = new Intent(mContext, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_GENERAL_ENHANCED);
                break;
            case R.id.driving_license_button:
                if (!checkTokenStatus()) {
                    return;
                }
                intent = new Intent(mContext, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_DRIVING_LICENSE);

                break;
            case R.id.vehicle_license_button:
                if (!checkTokenStatus()) {
                    return;
                }
                intent = new Intent(mContext, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_VEHICLE_LICENSE);
                break;
            case R.id.license_plate_button:
                if (!checkTokenStatus()) {
                    return;
                }
                intent = new Intent(mContext, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_LICENSE_PLATE);
                break;
            case R.id.receipt_button:
                if (!checkTokenStatus()) {
                    return;
                }
                intent = new Intent(mContext, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_RECEIPT);
                break;
            case R.id.custom_button:
                if (!checkTokenStatus()) {
                    return;
                }
                intent = new Intent(mContext, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_CUSTOM);
                break;
            case R.id.passport_button:

                break;
            case R.id.numbers_button:
                if (!checkTokenStatus()) {
                    return;
                }
                intent = new Intent(mContext, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_NUMBERS);
                break;
            case R.id.qrcode_button:
                if (!checkTokenStatus()) {
                    return;
                }
                intent = new Intent(mContext, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_QRCODE);
                break;
            case R.id.business_card_button:
                if (!checkTokenStatus()) {
                    return;
                }
                intent = new Intent(mContext, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_BUSINESSCARD);

                break;
            case R.id.lottery_button:
                if (!checkTokenStatus()) {
                    return;
                }
                intent = new Intent(mContext, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_LOTTERY);
                break;
        }



        setIsEnabled(false);

    }

    private HashMap<String, String> analyseVehicleLicense(String string) {
        HashMap<String, String> map_businessLicense = new HashMap<>();
        try {
            JSONObject jsonObject = null;
            jsonObject = new JSONObject(string);

            JSONObject data = jsonObject.getJSONObject("words_result");


            String key_socal_credit_code = getStr(R.string.vehicle_recognize_code);
            String key_composeForm = getStr(R.string.Date_Of_Issue);
            String key_manageScope = getStr(R.string.brand_model);
            String key_establishDate = getStr(R.string.Vehicle_type);
            String key_legalPerson = getStr((R.string.owner));
            String key_registerCapital = getStr(R.string.Nature_of_use);
            String key_licenseNum = getStr(R.string.Engine_number);
            String key_address = getStr(R.string.Plate_number);
            String key_companyName = getStr(R.string.Date_of_registration);



            //11
            JSONObject socialCreditCode = data.getJSONObject(key_socal_credit_code);
            JSONObject composeForm = data.getJSONObject(key_composeForm);
            JSONObject manageScope = data.getJSONObject(key_manageScope);
            JSONObject establishDate = data.getJSONObject(key_establishDate);
            JSONObject legalPerson = data.getJSONObject(key_legalPerson);
            JSONObject registerCapital = data.getJSONObject(key_registerCapital);
            JSONObject licenseNum = data.getJSONObject(key_licenseNum);
            JSONObject address = data.getJSONObject(key_address);
            JSONObject companyName = data.getJSONObject(key_companyName);
//            JSONObject validityDate = data.getJSONObject(key_validityDate);
//            JSONObject type = data.getJSONObject(key_type);

            String str_socalCreditCode = socialCreditCode.getString("words");
//            String str_type = type.getString("words");
//            String str_validityDate = validityDate.getString("words");
            String str_companyName = companyName.getString("words");
            String str_address = address.getString("words");
            String str_licenseNum = licenseNum.getString("words");
            String str_registerCapital = registerCapital.getString("words");
            String str_legalPerson = legalPerson.getString("words");
            String str_establishDate = establishDate.getString("words");
            String str_manageScope = manageScope.getString("words");
            String str_composeForm = composeForm.getString("words");

            //11
            map_businessLicense.put(key_socal_credit_code, str_socalCreditCode);
//            map_businessLicense.put(key_type, str_type);
//            map_businessLicense.put(key_validityDate, str_validityDate);
            map_businessLicense.put(key_companyName, str_companyName);
            map_businessLicense.put(key_legalPerson, str_legalPerson);
            map_businessLicense.put(key_registerCapital, str_registerCapital);
            map_businessLicense.put(key_licenseNum, str_licenseNum);
            map_businessLicense.put(key_address, str_address);
            map_businessLicense.put(key_establishDate, str_establishDate);
            map_businessLicense.put(key_manageScope, str_manageScope);
            map_businessLicense.put(key_composeForm, str_composeForm);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //更新UI 切换到主线程（可切换可不切换，毕竟就是在主线程）
        return map_businessLicense;
    }


    private HashMap<String, String> analyseBusinessLicense(String string) {
        HashMap<String, String> map_businessLicense = new HashMap<>();
        try {
            JSONObject jsonObject = null;

            jsonObject = new JSONObject(string);
            //最外层是对象，创建对象
            JSONObject data = jsonObject.getJSONObject("words_result");
            //data对象中还是对象（carArchive），再次创建对象

            String key_socal_credit_code = getStr(R.string.socal_credit_code);
            String key_composeForm = getStr(R.string.composeForm);
            String key_manageScope = getStr(R.string.manageScope);
            String key_establishDate = getStr(R.string.establishDate);
            String key_legalPerson = getStr((R.string.legalPerson));
            String key_registerCapital = getStr(R.string.registerCapital);
            String key_licenseNum = getStr(R.string.licenseNum);
            String key_address = getStr(R.string.address);
            String key_companyName = getStr(R.string.companyName);
            String key_validityDate = getStr(R.string.validityDate);
            String key_type = getStr(R.string.type);

            //11
            JSONObject socialCreditCode = data.getJSONObject(key_socal_credit_code);
            JSONObject composeForm = data.getJSONObject(key_composeForm);
            JSONObject manageScope = data.getJSONObject(key_manageScope);
            JSONObject establishDate = data.getJSONObject(key_establishDate);
            JSONObject legalPerson = data.getJSONObject(key_legalPerson);
            JSONObject registerCapital = data.getJSONObject(key_registerCapital);
            JSONObject licenseNum = data.getJSONObject(key_licenseNum);
            JSONObject address = data.getJSONObject(key_address);
            JSONObject companyName = data.getJSONObject(key_companyName);
            JSONObject validityDate = data.getJSONObject(key_validityDate);
            JSONObject type = data.getJSONObject(key_type);

            String str_socalCreditCode = socialCreditCode.getString("words");
            String str_type = type.getString("words");
            String str_validityDate = validityDate.getString("words");
            String str_companyName = companyName.getString("words");
            String str_address = address.getString("words");
            String str_licenseNum = licenseNum.getString("words");
            String str_registerCapital = registerCapital.getString("words");
            String str_legalPerson = legalPerson.getString("words");
            String str_establishDate = establishDate.getString("words");
            String str_manageScope = manageScope.getString("words");
            String str_composeForm = composeForm.getString("words");

            //11
            map_businessLicense.put(key_socal_credit_code, str_socalCreditCode);
            map_businessLicense.put(key_type, str_type);
            map_businessLicense.put(key_validityDate, str_validityDate);
            map_businessLicense.put(key_companyName, str_companyName);
            map_businessLicense.put(key_legalPerson, str_legalPerson);
            map_businessLicense.put(key_registerCapital, str_registerCapital);
            map_businessLicense.put(key_licenseNum, str_licenseNum);
            map_businessLicense.put(key_address, str_address);
            map_businessLicense.put(key_establishDate, str_establishDate);
            map_businessLicense.put(key_manageScope, str_manageScope);
            map_businessLicense.put(key_composeForm, str_composeForm);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //更新UI 切换到主线程（可切换可不切换，毕竟就是在主线程）
        return map_businessLicense;
    }


    private String analyseWord(String str) {
//        String str="{\"words_result\":[{\"words\":\"vold onResult(string result. st\"},{\"words\":\"old onResult(string result- str)\"},{\"words\":\"e vold recGeneral(Context ctx, string\"},{\"words\":\"arms param. new GeneralParams ();\"},{\"words\":\"tDetectDirection(true);\"},{\"words\":\"tvertexesLocation(true):\"},{\"words\":\"tRecognizeGranularity( GeneralParams GRANU\"},{\"words\":\"etImageFile(new File(filePath));\"},{\"words\":\"nstance(ctx). recognizeGeneral(param, new\"},{\"words\":\"ride\"},{\"words\":\"de void onResult(GeneralResult result)(\"},{\"words\":\"stringBullder sb- new StringBuilder():\"},{\"words\":\"for(Wordsimple wordsimple result gete\"},{\"words\":\"Wordword .(Word) wordsimple:\"},{\"words\":\"String words. word. getwords ();\"},{\"words\":\"List<word. Char? characterResults\"},{\"words\":\"g,e(g\\\"ocr”,mg:“文字:\\\"+wond\"},{\"words\":\"b append (word metHords o));\"}],\"log_id\":1334456250083049472,\"words_result_num\":18,\"direction\":0}";
        Gson gson = new Gson();
        Words words = gson.fromJson(str, Words.class);
        List<Words.WordsResultBean> words_result = words.getWords_result();
        Iterator<Words.WordsResultBean> iterator = words_result.iterator();

        StringBuilder sb_Words = new StringBuilder();

        while (iterator.hasNext()) {
            Words.WordsResultBean next = iterator.next();
            String words1 = next.getWords();
            Log.e("ocr", "文本by wringt：" + words1);
            sb_Words.append(words1 + "\n");
        }

        return sb_Words.toString();

    }


    private String analyseVatInvoce(String str_VatInvoce) {
//        String str="{\"words_result\":{\"AmountInWords\":\"叁佰圆整\",\"CommodityPrice\":[{\"row\":\"1\",\"word\":\"240.0110674\"}],\"NoteDrawer\":\"杨云\",\"SellerAddress\":\"京市高城区发无行西路1创有丽(\",\"CommodityNum\":[{\"row\":\"1\",\"word\":\"1\"}],\"SellerRegisterNum\":\"91116838957\",\"MachineCode\":\"\",\"Remarks\":\"\",\"SellerBank\":\"无W55招路银行莲外大街支行1\",\"CommodityTaxRate\":[{\"row\":\"1\",\"word\":\"A\"}],\"TotalTax\":\"16.97\",\"CheckCode\":\"\",\"InvoiceCode\":\"1100181130\",\"InvoiceDate\":\"2018年05月01日\",\"PurchaserRegisterNum\":\"911000581\",\"InvoiceTypeOrg\":\"北京增值税专用发票\",\"Password\":\"3-*21/09451>7-168691-701387318<1+</*3*9018047-33-253987*52>6*5*>4+3125-938797470383+345938/<3*/51/09-25-70\",\"Agent\":\"否\",\"AmountInFiguers\":\"300.00\",\"PurchaserBank\":\"招商银行股份有限公司北京望京支行1106058\",\"Checker\":\"黄昌晶\",\"City\":\"\",\"TotalAmount\":\"283.03\",\"CommodityAmount\":[{\"row\":\"1\",\"word\":\"295.02\"}],\"PurchaserName\":\"北京快义专知国产程化理有限公司\",\"CommodityType\":[{\"row\":\"1\",\"word\":\"W3T1600001\"}],\"Province\":\"\",\"InvoiceType\":\"专用发票\",\"SheetNum\":\"第三联\",\"PurchaserAddress\":\"\",\"CommodityTax\":[{\"row\":\"1\",\"word\":\"16.98\"}],\"CommodityUnit\":[{\"row\":\"1\",\"word\":\"李\"}],\"Payee\":\"于南玉\",\"CommodityName\":[{\"row\":\"1\",\"word\":\"*信息技术服务*技术服务费\"}],\"SellerName\":\"北京微梦创科网络技术有限公司\",\"InvoiceNum\":\"13784454\"},\"log_id\":1334686424993103872,\"words_result_num\":36}";
        VatInvoce vatInvoce = new Gson().fromJson(str_VatInvoce, VatInvoce.class);
        String myString = vatInvoce.toMyString();

        Log.e(Tag, "增值税：" + vatInvoce.toMyString());
        return vatInvoce.toMyString();
    }

    /**
     * 封装登录请求参数
     *
     * @return
     */
    private User loginType() {
        User user = new User();
        UserInfo userInfo = new UserInfo();
        if (CommUtils.isLogin(getActivity())) {
            String loginType = (String) mMySharedPreferences.getValue("loginType", "");
            String userId = (String) mMySharedPreferences.getValue("userId", "");
            if (TextUtils.isEmpty(userId)) {
                String uuid = (String) mMySharedPreferences.getValue("uuid", "");
                if (TextUtils.isEmpty(uuid)) {
                    uuid = CommUtils.getUniqueID(getActivity());
                }
                userInfo.setType(2);
                userInfo.setUnionInfo(uuid);
                mMySharedPreferences.clear();
            } else {
                if (Wechat.NAME.equals(loginType)) {
                    userInfo.setType(3);
                    userInfo.setUnionInfo(userId);
                } else if (QQ.NAME.equals(loginType)) {
                    userInfo.setType(4);
                    userInfo.setUnionInfo(userId);
                }
            }
        } else {
            String uuid = (String) mMySharedPreferences.getValue("uuid", "");
            userInfo.setType(2);
            userInfo.setUnionInfo(uuid);
        }
        userInfo.setAppId(Constants.WX_APP_ID);
        user.setParam(userInfo);
        return user;
    }
}
