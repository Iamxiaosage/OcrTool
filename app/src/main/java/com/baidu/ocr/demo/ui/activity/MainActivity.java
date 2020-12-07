/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.ocr.demo.ui.activity;

import com.baidu.ocr.demo.utils.FileUtil;
import com.baidu.ocr.demo.R;
import com.baidu.ocr.demo.bean.VatInvoce;
import com.baidu.ocr.demo.bean.Words;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.BankCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

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


    private boolean hasGotToken = false;

    private AlertDialog.Builder alertDialog;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            infoPopText(msg.obj.toString());

        }
    };
    public static final String Tag="ocr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alertDialog = new AlertDialog.Builder(this);



        // 通用文字识别
        findViewById(R.id.general_basic_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_GENERAL_BASIC);
            }
        });

        // 通用文字识别(高精度版)
        findViewById(R.id.accurate_basic_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_ACCURATE_BASIC);
            }
        });

        // 通用文字识别（含位置信息版）
        findViewById(R.id.general_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_GENERAL);
            }
        });

        // 通用文字识别（含位置信息高精度版）
        findViewById(R.id.accurate_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_ACCURATE);
            }
        });

        // 通用文字识别（含生僻字版）
        findViewById(R.id.general_enhance_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_GENERAL_ENHANCED);
            }
        });

        // 网络图片识别
        findViewById(R.id.general_webimage_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_GENERAL_WEBIMAGE);
            }
        });

        // 身份证识别
        findViewById(R.id.idcard_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(MainActivity.this, IDCardActivity.class);
                startActivity(intent);
            }
        });

        // 银行卡识别
        findViewById(R.id.bankcard_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_BANK_CARD);
                startActivityForResult(intent, REQUEST_CODE_BANKCARD);
            }
        });

        // 行驶证识别
        findViewById(R.id.vehicle_license_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_VEHICLE_LICENSE);
            }
        });

        // 驾驶证识别
        findViewById(R.id.driving_license_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_DRIVING_LICENSE);
            }
        });

        // 车牌识别
        findViewById(R.id.license_plate_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_LICENSE_PLATE);
            }
        });

        // 营业执照识别
        findViewById(R.id.business_license_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_BUSINESS_LICENSE);
            }
        });

        // 通用票据识别
        findViewById(R.id.receipt_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_RECEIPT);
            }
        });

        // 护照识别
        findViewById(R.id.passport_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_PASSPORT);
                startActivityForResult(intent, REQUEST_CODE_PASSPORT);
            }
        });

        // 二维码识别
        findViewById(R.id.qrcode_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_QRCODE);
            }
        });

        // 数字识别
        findViewById(R.id.numbers_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_NUMBERS);
            }
        });

        // 名片识别
        findViewById(R.id.business_card_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_BUSINESSCARD);
            }
        });

        // 增值税发票识别
        findViewById(R.id.vat_invoice_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_VATINVOICE);
            }
        });

        // 彩票识别
        findViewById(R.id.lottery_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_LOTTERY);
            }
        });

        // 手写识别
        findViewById(R.id.handwritting_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_HANDWRITING);
            }
        });

        // 自定义模板
        findViewById(R.id.custom_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_CUSTOM);
            }
        });


        // 请选择您的初始化方式
        // initAccessToken();
        initAccessTokenWithAkSk();
    }





    private String analyseWord(String str) {
//        String str="{\"words_result\":[{\"words\":\"vold onResult(string result. st\"},{\"words\":\"old onResult(string result- str)\"},{\"words\":\"e vold recGeneral(Context ctx, string\"},{\"words\":\"arms param. new GeneralParams ();\"},{\"words\":\"tDetectDirection(true);\"},{\"words\":\"tvertexesLocation(true):\"},{\"words\":\"tRecognizeGranularity( GeneralParams GRANU\"},{\"words\":\"etImageFile(new File(filePath));\"},{\"words\":\"nstance(ctx). recognizeGeneral(param, new\"},{\"words\":\"ride\"},{\"words\":\"de void onResult(GeneralResult result)(\"},{\"words\":\"stringBullder sb- new StringBuilder():\"},{\"words\":\"for(Wordsimple wordsimple result gete\"},{\"words\":\"Wordword .(Word) wordsimple:\"},{\"words\":\"String words. word. getwords ();\"},{\"words\":\"List<word. Char? characterResults\"},{\"words\":\"g,e(g\\\"ocr”,mg:“文字:\\\"+wond\"},{\"words\":\"b append (word metHords o));\"}],\"log_id\":1334456250083049472,\"words_result_num\":18,\"direction\":0}";
        Gson gson = new Gson();
        Words words = gson.fromJson(str, Words.class);
        List<Words.WordsResultBean> words_result = words.getWords_result();
        Iterator<Words.WordsResultBean> iterator = words_result.iterator();

        StringBuilder sb_Words=new StringBuilder();

        while (iterator.hasNext()){
            Words.WordsResultBean next = iterator.next();
            String words1 = next.getWords();
            Log.e("ocr","文本by wringt："+words1);
            sb_Words.append(words1+"\n");
        }

        return sb_Words.toString();

    }


    private String analyseVatInvoce(String str_VatInvoce) {
//        String str="{\"words_result\":{\"AmountInWords\":\"叁佰圆整\",\"CommodityPrice\":[{\"row\":\"1\",\"word\":\"240.0110674\"}],\"NoteDrawer\":\"杨云\",\"SellerAddress\":\"京市高城区发无行西路1创有丽(\",\"CommodityNum\":[{\"row\":\"1\",\"word\":\"1\"}],\"SellerRegisterNum\":\"91116838957\",\"MachineCode\":\"\",\"Remarks\":\"\",\"SellerBank\":\"无W55招路银行莲外大街支行1\",\"CommodityTaxRate\":[{\"row\":\"1\",\"word\":\"A\"}],\"TotalTax\":\"16.97\",\"CheckCode\":\"\",\"InvoiceCode\":\"1100181130\",\"InvoiceDate\":\"2018年05月01日\",\"PurchaserRegisterNum\":\"911000581\",\"InvoiceTypeOrg\":\"北京增值税专用发票\",\"Password\":\"3-*21/09451>7-168691-701387318<1+</*3*9018047-33-253987*52>6*5*>4+3125-938797470383+345938/<3*/51/09-25-70\",\"Agent\":\"否\",\"AmountInFiguers\":\"300.00\",\"PurchaserBank\":\"招商银行股份有限公司北京望京支行1106058\",\"Checker\":\"黄昌晶\",\"City\":\"\",\"TotalAmount\":\"283.03\",\"CommodityAmount\":[{\"row\":\"1\",\"word\":\"295.02\"}],\"PurchaserName\":\"北京快义专知国产程化理有限公司\",\"CommodityType\":[{\"row\":\"1\",\"word\":\"W3T1600001\"}],\"Province\":\"\",\"InvoiceType\":\"专用发票\",\"SheetNum\":\"第三联\",\"PurchaserAddress\":\"\",\"CommodityTax\":[{\"row\":\"1\",\"word\":\"16.98\"}],\"CommodityUnit\":[{\"row\":\"1\",\"word\":\"李\"}],\"Payee\":\"于南玉\",\"CommodityName\":[{\"row\":\"1\",\"word\":\"*信息技术服务*技术服务费\"}],\"SellerName\":\"北京微梦创科网络技术有限公司\",\"InvoiceNum\":\"13784454\"},\"log_id\":1334686424993103872,\"words_result_num\":36}";
        VatInvoce vatInvoce = new Gson().fromJson(str_VatInvoce, VatInvoce.class);
        String myString = vatInvoce.toMyString();

        Log.e(Tag,"增值税："+vatInvoce.toMyString());
        return vatInvoce.toMyString();
    }


    private HashMap<String, String> analyseDriverLicense(String string) {
        HashMap<String, String> map_businessLicense = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(string);

            JSONObject data = jsonObject.getJSONObject("words_result");


            String key_name = getStr(R.string.name);
            String key_effectStartDate = getStr(R.string.effectStartDate);
            String key_birthday = getStr(R.string.birthday);
            String key_certificateNumber = getStr(R.string.certificateNumber);
            String key_legalPerson = getStr((R.string.address_DrivierLicense));
            String key_registerCapital = getStr(R.string.registerCapital);
            String key_licenseNum = getStr(R.string.licenseNum);
            String key_address = getStr(R.string.address);
            String key_companyName = getStr(R.string.companyName);
            String key_validityDate = getStr(R.string.validityDate);
            String key_type = getStr(R.string.type);

            //11
            JSONObject socialCreditCode = data.getJSONObject(key_name);
            JSONObject composeForm = data.getJSONObject(key_effectStartDate);
            JSONObject manageScope = data.getJSONObject(key_birthday);
            JSONObject establishDate = data.getJSONObject(key_certificateNumber);
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
            map_businessLicense.put(key_name,str_socalCreditCode);
            map_businessLicense.put(key_type,str_type);
            map_businessLicense.put(key_validityDate,str_validityDate);
            map_businessLicense.put(key_companyName,str_companyName);
            map_businessLicense.put(key_legalPerson,str_legalPerson);
            map_businessLicense.put(key_registerCapital,str_registerCapital);
            map_businessLicense.put(key_licenseNum,str_licenseNum);
            map_businessLicense.put(key_address,str_address);
            map_businessLicense.put(key_certificateNumber,str_establishDate);
            map_businessLicense.put(key_birthday,str_manageScope);
            map_businessLicense.put(key_effectStartDate,str_composeForm);

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
//                    try {
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
            map_businessLicense.put(key_socal_credit_code,str_socalCreditCode);
            map_businessLicense.put(key_type,str_type);
            map_businessLicense.put(key_validityDate,str_validityDate);
            map_businessLicense.put(key_companyName,str_companyName);
            map_businessLicense.put(key_legalPerson,str_legalPerson);
            map_businessLicense.put(key_registerCapital,str_registerCapital);
            map_businessLicense.put(key_licenseNum,str_licenseNum);
            map_businessLicense.put(key_address,str_address);
            map_businessLicense.put(key_establishDate,str_establishDate);
            map_businessLicense.put(key_manageScope,str_manageScope);
            map_businessLicense.put(key_composeForm,str_composeForm);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //更新UI 切换到主线程（可切换可不切换，毕竟就是在主线程）
        return map_businessLicense;
    }


    private boolean checkTokenStatus() {
        if (!hasGotToken) {
            Toast.makeText(getApplicationContext(), "token还未成功获取", Toast.LENGTH_LONG).show();
        }
        return hasGotToken;
    }

    /**
     * 以license文件方式初始化
     */
    private void initAccessToken() {
        OCR.getInstance(this).initAccessToken(new OnResultListener<AccessToken>() {
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
        }, getApplicationContext());
    }

    /**
     * 用明文ak，sk初始化
     */
    private void initAccessTokenWithAkSk() {
        OCR.getInstance(this).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
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

    /**
     * 自定义license的文件路径和文件名称，以license文件方式初始化
     */
    private void initAccessTokenLicenseFile() {
        OCR.getInstance(this).initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                alertText("自定义文件路径licence方式获取token失败", error.getMessage());
            }
        }, "aip.license", getApplicationContext());
    }


    private void alertText(final String title, final String message) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EditText editText = new EditText(MainActivity.this);


                editText.setText(message);
                alertDialog.setTitle(title)
//                        .setMessage(message)
                        .setView(editText)
                        .setPositiveButton("确定", null)
                        .show();
            }
        });
    }

    private void infoPopText(final String result) {
        alertText("", result);
//        result.
        System.out.println("返回内容：" + result);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 识别成功回调，通用文字识别（含位置信息）
        if (requestCode == REQUEST_CODE_GENERAL && resultCode == Activity.RESULT_OK) {
            RecognizeService.recGeneral(this, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
                            infoPopText(result_str);
                        }
                    });
        }

        // 识别成功回调，通用文字识别（含位置信息高精度版）
        if (requestCode == REQUEST_CODE_ACCURATE && resultCode == Activity.RESULT_OK) {
            RecognizeService.recAccurate(this, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {

                            infoPopText(result_str);
                        }
                    });
        }

        // 识别成功回调，通用文字识别
        if (requestCode == REQUEST_CODE_GENERAL_BASIC && resultCode == Activity.RESULT_OK) {
            RecognizeService.recGeneralBasic(this, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {

//                            infoPopText(result_str);
                            String s = analyseWord(result_str);
                            Message message = new Message();
                            message.what=1;
                            message.obj=s;

                            handler.sendMessage(message);

                        }
                    });
        }




        // 识别成功回调，通用文字识别（高精度版）
        if (requestCode == REQUEST_CODE_ACCURATE_BASIC && resultCode == Activity.RESULT_OK) {
            RecognizeService.recAccurateBasic(this, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
                            infoPopText(result_str);
                        }
                    });
        }

        // 识别成功回调，通用文字识别（含生僻字版）
        if (requestCode == REQUEST_CODE_GENERAL_ENHANCED && resultCode == Activity.RESULT_OK) {
            RecognizeService.recGeneralEnhanced(this, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
                            infoPopText(result_str);
                        }
                    });
        }

        // 识别成功回调，网络图片文字识别
        if (requestCode == REQUEST_CODE_GENERAL_WEBIMAGE && resultCode == Activity.RESULT_OK) {
            RecognizeService.recWebimage(this, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
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


            RecognizeService.recBankCard(this, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
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
            RecognizeService.recVehicleLicense(this, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
//                            new Gson().fromJson(result_str,)
                            infoPopText(result_str);
                        }
                    });
        }

        // 识别成功回调，驾驶证识别
        if (requestCode == REQUEST_CODE_DRIVING_LICENSE && resultCode == Activity.RESULT_OK) {
            RecognizeService.recDrivingLicense(this, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
                            infoPopText(result_str);
                        }
                    });
        }

        // 识别成功回调，车牌识别
        if (requestCode == REQUEST_CODE_LICENSE_PLATE && resultCode == Activity.RESULT_OK) {
            RecognizeService.recLicensePlate(this, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
                            infoPopText(result_str);
                        }
                    });
        }

        // 识别成功回调，营业执照识别
        if (requestCode == REQUEST_CODE_BUSINESS_LICENSE && resultCode == Activity.RESULT_OK) {
            RecognizeService.recBusinessLicense(this, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
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
                            while (iterator.hasNext()){
                                Map.Entry<String, String> next = iterator.next();
                                sb_BussinessLicense.append( next.getKey() + ":" + next.getValue() + "\n");
                            }

                            Log.e("ocr","总数据："+sb_BussinessLicense);



                            infoPopText(sb_BussinessLicense.toString());
                        }
                    });
        }

        // 识别成功回调，通用票据识别
        if (requestCode == REQUEST_CODE_RECEIPT && resultCode == Activity.RESULT_OK) {
            RecognizeService.recReceipt(this, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
                            infoPopText(result_str);
                        }
                    });
        }

        // 识别成功回调，护照
        if (requestCode == REQUEST_CODE_PASSPORT && resultCode == Activity.RESULT_OK) {
            RecognizeService.recPassport(this, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
                            infoPopText(result_str);
                        }
                    });
        }

        // 识别成功回调，二维码
        if (requestCode == REQUEST_CODE_QRCODE && resultCode == Activity.RESULT_OK) {
            RecognizeService.recQrcode(this, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
                            infoPopText(result_str);
                        }
                    });
        }

        // 识别成功回调，彩票
        if (requestCode == REQUEST_CODE_LOTTERY && resultCode == Activity.RESULT_OK) {
            RecognizeService.recLottery(this, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
                            infoPopText(result_str);
                        }
                    });
        }

        // 识别成功回调，增值税发票
        if (requestCode == REQUEST_CODE_VATINVOICE && resultCode == Activity.RESULT_OK) {
            RecognizeService.recVatInvoice(this, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
                            String str_vatInvoce = analyseVatInvoce(result_str);
                            infoPopText(str_vatInvoce);
                        }
                    });
        }

        // 识别成功回调，数字
        if (requestCode == REQUEST_CODE_NUMBERS && resultCode == Activity.RESULT_OK) {
            RecognizeService.recNumbers(this, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
                            infoPopText(result_str);
                        }
                    });
        }

        // 识别成功回调，手写
        if (requestCode == REQUEST_CODE_HANDWRITING && resultCode == Activity.RESULT_OK) {
            RecognizeService.recHandwriting(this, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
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
            RecognizeService.recBusinessCard(this, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
                            infoPopText(result_str);
                        }
                    });
        }

        // 识别成功回调，自定义模板
        if (requestCode == REQUEST_CODE_CUSTOM && resultCode == Activity.RESULT_OK) {
            RecognizeService.recCustom(this, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result_str) {
                            infoPopText(result_str);
                        }
                    });
        }
    }

    String getStr(int resId) {
        return getResources().getString(resId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放内存资源
        OCR.getInstance(this).release();
    }
}
