package com.example.ydshoa;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.JsonRootBean;
import com.example.bean.URLS;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BillAmtActivity extends Activity implements View.OnClickListener {
    //根数据
    private Context context;
    private SharedPreferences sp;
    private String session,fh_sjs,fh_sfpr,fh_zt;

    //基本数据
    private String db_zt,date_dd,skdpb_id,skdkpyh_id;
    private TextView head,pb_one,pb_two,pb_three,pb_four,pb_five,yh_one,yh_two,yh_three,yh_four,yh_five,time_one,time_two,time_three,time_four,time_five,dxzh_one,
            dxzh_two,dxzh_three,dxzh_four,dxzh_five;
    private int object_chk,checked,checked_yh;
    private EditText pjhm_one,pjhm_two,pjhm_three,pjhm_four,pjhm_five,je_one,je_two,je_three
            ,je_four,je_five;
    private Button pjok_hd;
    private ArrayList<String> PJ_hm,PJ_je,PJ_yh,PJ_spr,PJ_ydr,PJ_dqr,PJ_bdr,PJ_zl;
    private String url_isph= URLS.design_jfspquery_ph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bill_amt);
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        context=BillAmtActivity.this;
        db_zt = sp.getString("DB_MR", "");
         fh_sjs = getIntent().getStringExtra("fh_sjs");
         fh_sfpr = getIntent().getStringExtra("cd_djrq");
         fh_zt = getIntent().getStringExtra("cd_zt");
        getNowTime();
        intView();
//        infos_chage();


    }



    private void getNowTime() {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // 获取当前年份
        int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前日
        date_dd = mYear + "-" + mMonth + "-" + mDay;
    }
    private void intView() {
        head = (TextView) findViewById(R.id.all_head);
        head.setText("票据明细");
        //填写数据
        pjhm_one= (EditText) findViewById(R.id.et_pjje_pjhm_one);
        pjhm_one.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                je_one.setEnabled(true);
            }
        });
        pjhm_two= (EditText) findViewById(R.id.et_pjje_pjhm_two);
        pjhm_two.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                je_two.setEnabled(true);
            }
        });
        pjhm_three= (EditText) findViewById(R.id.et_pjje_pjhm_three);
        pjhm_three.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                je_three.setEnabled(true);
            }
        });
        pjhm_four= (EditText) findViewById(R.id.et_pjje_pjhm_four);
        pjhm_four.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                je_four.setEnabled(true);
            }
        });
        pjhm_five= (EditText) findViewById(R.id.et_pjje_pjhm_five);
        pjhm_five.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                je_five.setEnabled(true);
            }
        });
        je_one= (EditText) findViewById(R.id.et_pjje_je_one);
        je_one.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isHaveIph(je_one,pjhm_one.getText().toString());
            }
        });
        je_two= (EditText) findViewById(R.id.et_pjje_je_two);
        je_two.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isHaveIph(je_two, pjhm_two.getText().toString());
            }
        });
        je_three= (EditText) findViewById(R.id.et_pjje_je_three);
        je_three.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isHaveIph(je_three, pjhm_three.getText().toString());
            }
        });
        je_four= (EditText) findViewById(R.id.et_pjje_je_four);
        je_four.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isHaveIph(je_four, pjhm_four.getText().toString());
            }
        });
        je_five= (EditText) findViewById(R.id.et_pjje_je_five);
        je_five.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isHaveIph(je_five, pjhm_five.getText().toString());
            }
        });
        dxzh_one= (TextView) findViewById(R.id.et_pjje_dxzh_one);
        dxzh_one.setHint(date_dd);
        dxzh_two= (TextView) findViewById(R.id.et_pjje_dxzh_two);
        dxzh_two.setHint(date_dd);
        dxzh_three= (TextView) findViewById(R.id.et_pjje_dxzh_three);
        dxzh_three.setHint(date_dd);
        dxzh_four= (TextView) findViewById(R.id.et_pjje_dxzh_four);
        dxzh_four.setHint(date_dd);
        dxzh_five= (TextView) findViewById(R.id.et_pjje_dxzh_five);
        dxzh_five.setHint(date_dd);


        pb_one= (TextView) findViewById(R.id.tv_pjje_pb_one);
        pb_one.setOnClickListener(this);
        pb_two= (TextView) findViewById(R.id.tv_pjje_pb_two);
        pb_two.setOnClickListener(this);
        pb_three= (TextView) findViewById(R.id.tv_pjje_pb_three);
        pb_three.setOnClickListener(this);
        pb_four= (TextView) findViewById(R.id.tv_pjje_pb_four);
        pb_four.setOnClickListener(this);
        pb_five= (TextView) findViewById(R.id.tv_pjje_pb_five);
        pb_five.setOnClickListener(this);
        yh_one= (TextView) findViewById(R.id.et_pjje_kpyh_one);
        yh_one.setOnClickListener(this);
        yh_two= (TextView) findViewById(R.id.et_pjje_kpyh_two);
        yh_two.setOnClickListener(this);
        yh_three= (TextView) findViewById(R.id.et_pjje_kpyh_three);
        yh_three.setOnClickListener(this);
        yh_four= (TextView) findViewById(R.id.et_pjje_kpyh_four);
        yh_four.setOnClickListener(this);
        yh_five= (TextView) findViewById(R.id.et_pjje_kpyh_five);
        yh_five.setOnClickListener(this);
        time_one= (TextView) findViewById(R.id.tv_pjje_time_one);
        time_one.setHint(date_dd);
        time_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c_time = Calendar.getInstance();
                int mYear_time = c_time.get(Calendar.YEAR); // 获取当前年份
                int mMonth_time = c_time.get(Calendar.MONTH);// 获取当前月份
                int mDay_time = c_time.get(Calendar.DAY_OF_MONTH);// 获取当日期cal.set(Calendar.DAY_OF_MONTH, 1);
                Log.e("LiNing", "y" + mYear_time + "/y" + mMonth_time + "/r" + mDay_time);
                new DatePickerDialog(context, DatePickerDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String startTime = String.format("%d-%d-%d", year,
                                        monthOfYear + 1, dayOfMonth);
                                time_one.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        time_two= (TextView) findViewById(R.id.tv_pjje_time_two);
        time_two.setHint(date_dd);
        time_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c_time = Calendar.getInstance();
                int mYear_time = c_time.get(Calendar.YEAR); // 获取当前年份
                int mMonth_time = c_time.get(Calendar.MONTH);// 获取当前月份
                int mDay_time = c_time.get(Calendar.DAY_OF_MONTH);// 获取当日期cal.set(Calendar.DAY_OF_MONTH, 1);
                Log.e("LiNing", "y" + mYear_time + "/y" + mMonth_time + "/r" + mDay_time);
                new DatePickerDialog(context, DatePickerDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String startTime = String.format("%d-%d-%d", year,
                                        monthOfYear + 1, dayOfMonth);
                                time_two.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        time_three= (TextView) findViewById(R.id.tv_pjje_time_three);
        time_three.setHint(date_dd);
        time_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c_time = Calendar.getInstance();
                int mYear_time = c_time.get(Calendar.YEAR); // 获取当前年份
                int mMonth_time = c_time.get(Calendar.MONTH);// 获取当前月份
                int mDay_time = c_time.get(Calendar.DAY_OF_MONTH);// 获取当日期cal.set(Calendar.DAY_OF_MONTH, 1);
                Log.e("LiNing", "y" + mYear_time + "/y" + mMonth_time + "/r" + mDay_time);
                new DatePickerDialog(context, DatePickerDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String startTime = String.format("%d-%d-%d", year,
                                        monthOfYear + 1, dayOfMonth);
                                time_three.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        time_four= (TextView) findViewById(R.id.tv_pjje_time_four);
        time_four.setHint(date_dd);
        time_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c_time = Calendar.getInstance();
                int mYear_time = c_time.get(Calendar.YEAR); // 获取当前年份
                int mMonth_time = c_time.get(Calendar.MONTH);// 获取当前月份
                int mDay_time = c_time.get(Calendar.DAY_OF_MONTH);// 获取当日期cal.set(Calendar.DAY_OF_MONTH, 1);
                Log.e("LiNing", "y" + mYear_time + "/y" + mMonth_time + "/r" + mDay_time);
                new DatePickerDialog(context, DatePickerDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String startTime = String.format("%d-%d-%d", year,
                                        monthOfYear + 1, dayOfMonth);
                                time_four.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        time_five= (TextView) findViewById(R.id.tv_pjje_time_five);
        time_five.setHint(date_dd);
        time_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c_time = Calendar.getInstance();
                int mYear_time = c_time.get(Calendar.YEAR); // 获取当前年份
                int mMonth_time = c_time.get(Calendar.MONTH);// 获取当前月份
                int mDay_time = c_time.get(Calendar.DAY_OF_MONTH);// 获取当日期cal.set(Calendar.DAY_OF_MONTH, 1);
                Log.e("LiNing", "y" + mYear_time + "/y" + mMonth_time + "/r" + mDay_time);
                new DatePickerDialog(context, DatePickerDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String startTime = String.format("%d-%d-%d", year,
                                        monthOfYear + 1, dayOfMonth);
                                time_five.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        dxzh_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c_time = Calendar.getInstance();
                int mYear_time = c_time.get(Calendar.YEAR); // 获取当前年份
                int mMonth_time = c_time.get(Calendar.MONTH);// 获取当前月份
                int mDay_time = c_time.get(Calendar.DAY_OF_MONTH);// 获取当日期cal.set(Calendar.DAY_OF_MONTH, 1);
                Log.e("LiNing", "y" + mYear_time + "/y" + mMonth_time + "/r" + mDay_time);
                new DatePickerDialog(context, DatePickerDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String startTime = String.format("%d-%d-%d", year,
                                        monthOfYear + 1, dayOfMonth);
                                dxzh_one.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        dxzh_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c_time = Calendar.getInstance();
                int mYear_time = c_time.get(Calendar.YEAR); // 获取当前年份
                int mMonth_time = c_time.get(Calendar.MONTH);// 获取当前月份
                int mDay_time = c_time.get(Calendar.DAY_OF_MONTH);// 获取当日期cal.set(Calendar.DAY_OF_MONTH, 1);
                Log.e("LiNing", "y" + mYear_time + "/y" + mMonth_time + "/r" + mDay_time);
                new DatePickerDialog(context, DatePickerDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String startTime = String.format("%d-%d-%d", year,
                                        monthOfYear + 1, dayOfMonth);
                                dxzh_two.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        dxzh_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c_time = Calendar.getInstance();
                int mYear_time = c_time.get(Calendar.YEAR); // 获取当前年份
                int mMonth_time = c_time.get(Calendar.MONTH);// 获取当前月份
                int mDay_time = c_time.get(Calendar.DAY_OF_MONTH);// 获取当日期cal.set(Calendar.DAY_OF_MONTH, 1);
                Log.e("LiNing", "y" + mYear_time + "/y" + mMonth_time + "/r" + mDay_time);
                new DatePickerDialog(context, DatePickerDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String startTime = String.format("%d-%d-%d", year,
                                        monthOfYear + 1, dayOfMonth);
                                dxzh_three.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        dxzh_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c_time = Calendar.getInstance();
                int mYear_time = c_time.get(Calendar.YEAR); // 获取当前年份
                int mMonth_time = c_time.get(Calendar.MONTH);// 获取当前月份
                int mDay_time = c_time.get(Calendar.DAY_OF_MONTH);// 获取当日期cal.set(Calendar.DAY_OF_MONTH, 1);
                Log.e("LiNing", "y" + mYear_time + "/y" + mMonth_time + "/r" + mDay_time);
                new DatePickerDialog(context, DatePickerDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String startTime = String.format("%d-%d-%d", year,
                                        monthOfYear + 1, dayOfMonth);
                                dxzh_four.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        dxzh_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c_time = Calendar.getInstance();
                int mYear_time = c_time.get(Calendar.YEAR); // 获取当前年份
                int mMonth_time = c_time.get(Calendar.MONTH);// 获取当前月份
                int mDay_time = c_time.get(Calendar.DAY_OF_MONTH);// 获取当日期cal.set(Calendar.DAY_OF_MONTH, 1);
                Log.e("LiNing", "y" + mYear_time + "/y" + mMonth_time + "/r" + mDay_time);
                new DatePickerDialog(context, DatePickerDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String startTime = String.format("%d-%d-%d", year,
                                        monthOfYear + 1, dayOfMonth);
                                dxzh_five.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        pjok_hd= (Button) findViewById(R.id.pjok);
        pjok_hd.setOnClickListener(this);
    }

    private void isHaveIph(final EditText edt, String str) {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("db_Id", fh_zt)
                .add("chk_NO", str)
                .build();
        Request request = new Request.Builder().addHeader("cookie", session)
                .url(url_isph).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String strph = response.body().string();
                Log.e("LiNing", "票号结果===" + strph);
                final JsonRootBean localJsonRootBean = (JsonRootBean) new Gson()
                        .fromJson(strph, JsonRootBean.class);
                if (localJsonRootBean != null) {
                    BillAmtActivity.this.runOnUiThread(new Runnable() {
                        boolean rlo = localJsonRootBean.getRLO();
                        @Override
                        public void run() {
                            if(rlo==true){
                                Toast.makeText(BillAmtActivity.this,
                                        "该票号已存在,请重新填写", Toast.LENGTH_SHORT).show();
                                edt.setEnabled(false);
                                edt.setText("");
                            }else{
                                edt.setEnabled(true);
                            }
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call call, IOException e) {

            }


        });
    }

    private void infos_chage() {
        try {
            JSONArray arr = new JSONArray(fh_sjs);
            int size = arr.length();
            JSONObject jsonObject = arr.getJSONObject(0);
//            String pjje_dqr,pjje_pb,pjje_pjhm,pjje_je,pjje_kpyh,pjje_dxzh;
            String pjje_pb = jsonObject.get("chk_KND").toString();//票别
            String pjje_pjhm = jsonObject.get("chk_NO").toString();//票据号码
            String pjje_je = jsonObject.get("amtn").toString();//金额
            String pjje_kpyh = jsonObject.get("bank_NO").toString();//开票银行
//            String pjje_dxzh = jsonObject.get("usr").toString();//兑现帐户
            String pjje_dxzh = jsonObject.get("cah_DD").toString();//预兑日
            String pjje_dqr = jsonObject.get("end_DD").toString();//到期日
            pb_one.setText(pjje_pb);
            pjhm_one.setText(pjje_pjhm);
            je_one.setText(pjje_je);
            yh_one.setText(pjje_kpyh);
            dxzh_one.setText(pjje_dxzh);

            //日期转换
            Date date = new Date(pjje_dqr);
            SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd");
            String a1 = dateformat1.format(date);
            time_one.setText(a1);
//            for (int i = 0; i < size; i++) {
//                //多数据遍历
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void allback(View v) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_pjje_pb_one:
                //pop弹框
                object_chk=1;
                checked = 1;
                showPopupMenu(pb_one);
                break;
            case R.id.tv_pjje_pb_two:
                object_chk=1;
                checked = 2;
                showPopupMenu(pb_two);
                break;
            case R.id.tv_pjje_pb_three:
                object_chk=1;
                checked = 3;
                showPopupMenu(pb_three);
                break;
            case R.id.tv_pjje_pb_four:
                object_chk=1;
                checked = 4;
                showPopupMenu(pb_four);
                break;
            case R.id.tv_pjje_pb_five:
                object_chk=1;
                checked = 5;
                showPopupMenu(pb_five);
                break;
            case R.id.et_pjje_kpyh_one:
                object_chk=2;
                checked_yh=1;
                showPopupMenu(yh_one);
                break;
            case R.id.et_pjje_kpyh_two:
                object_chk=2;
                checked_yh=2;
                showPopupMenu(yh_two);
                break;
            case R.id.et_pjje_kpyh_three:
                object_chk=2;
                checked_yh=3;
                showPopupMenu(yh_three);
                break;
            case R.id.et_pjje_kpyh_four:
                object_chk=2;
                checked_yh=4;
                showPopupMenu(yh_four);
                break;
            case R.id.et_pjje_kpyh_five:
                object_chk=2;
                checked_yh=5;
                showPopupMenu(yh_five);
                break;
            case R.id.pjok:
                getComitInfos();
                break;

                default:
                    break;
        }
    }

    private void getComitInfos() {
        PJ_hm=new ArrayList<String>();
        PJ_je=new ArrayList<String>();
        PJ_yh=new ArrayList<String>();
        PJ_spr=new ArrayList<String>();
        PJ_ydr=new ArrayList<String>();
        PJ_dqr=new ArrayList<String>();
        PJ_bdr=new ArrayList<String>();
        PJ_zl=new ArrayList<String>();
        Double sum_pjje=0.0;
        //票号
        if(!pjhm_one.getText().toString().equals("")){
            PJ_hm.add(pjhm_one.getText().toString());
            PJ_spr.add(fh_sfpr);
            PJ_bdr.add(date_dd);
            //票别
            if(!pb_one.getText().toString().equals("")){
                PJ_zl.add(pb_one.getText().toString().substring(0,1));
                Log.e("LiNing","======"+pb_one.getText().toString().substring(0,1));
            }else{
                PJ_zl.add("");
            }
            //金额
            if(!je_one.getText().toString().equals("")){
                PJ_je.add(je_one.getText().toString());
            }else{
                PJ_je.add("0.0");
            }
            //开票银行
            if(!yh_one.getText().toString().equals("")){
                PJ_yh.add(yh_one.getText().toString().substring(0,1));
            }else {
                PJ_yh.add("");
            }
            //预兑日
            if(!dxzh_one.getText().toString().equals("")){
                PJ_ydr.add(dxzh_one.getText().toString());
            }else {
                PJ_ydr.add(date_dd);
            }
            //到期日
            if(!time_one.getText().toString().equals("")){
                PJ_dqr.add(time_one.getText().toString());
            }else {
                PJ_dqr.add(date_dd);
            }

        }
        if(!pjhm_two.getText().toString().equals("")){
            PJ_hm.add(pjhm_two.getText().toString());
            PJ_spr.add(fh_sfpr);
            PJ_bdr.add(date_dd);
            //票别
            if(!pb_two.getText().toString().equals("")){
                PJ_zl.add(pb_two.getText().toString().substring(0,1));
            }else{
                PJ_zl.add("");
            }
            //金额
            if(!je_two.getText().toString().equals("")){
                PJ_je.add(je_two.getText().toString());
            }else{
                PJ_je.add("0.0");
            }
            //开票银行
            if(!yh_two.getText().toString().equals("")){
                PJ_yh.add(yh_two.getText().toString().substring(0,1));
            }else {
                PJ_yh.add("");
            }
            //预兑日

            if(!dxzh_two.getText().toString().equals("")){
                PJ_ydr.add(dxzh_two.getText().toString());
            }else {
                PJ_ydr.add(date_dd);
            }
            //到期日

            if(!time_two.getText().toString().equals("")){
                PJ_dqr.add(time_two.getText().toString());
            }else {
                PJ_dqr.add(date_dd);
            }

        }
        if(!pjhm_three.getText().toString().equals("")){
            PJ_hm.add(pjhm_three.getText().toString());
            PJ_spr.add(fh_sfpr);
            PJ_bdr.add(date_dd);
            //票别
            if(!pb_three.getText().toString().equals("")){
                PJ_zl.add(pb_three.getText().toString().substring(0,1));
            }else{
                PJ_zl.add("");
            }
            //金额
            if(!je_three.getText().toString().equals("")){
                PJ_je.add(je_three.getText().toString());
            }else{
                PJ_je.add("0.0");
            }
            //开票银行
            if(!yh_three.getText().toString().equals("")){
                PJ_yh.add(yh_three.getText().toString().substring(0,1));
            }else {
                PJ_yh.add("");
            }
            //预兑日

            if(!dxzh_three.getText().toString().equals("")){
                PJ_ydr.add(dxzh_three.getText().toString());
            }else {
                PJ_ydr.add(date_dd);
            }
            //到期日

            if(!time_three.getText().toString().equals("")){
                PJ_dqr.add(time_three.getText().toString());
            }else {
                PJ_dqr.add(date_dd);
            }

        }
        if(!pjhm_four.getText().toString().equals("")){
            PJ_hm.add(pjhm_four.getText().toString());
            PJ_spr.add(fh_sfpr);
            PJ_bdr.add(date_dd);
            //票别
            if(!pb_four.getText().toString().equals("")){
                PJ_zl.add(pb_four.getText().toString().substring(0,1));
            }else{
                PJ_zl.add("");
            }
            //金额
            if(!je_four.getText().toString().equals("")){
                PJ_je.add(je_four.getText().toString());
            }else{
                PJ_je.add("0.0");
            }
            //开票银行
            if(!yh_four.getText().toString().equals("")){
                PJ_yh.add(yh_four.getText().toString().substring(0,1));
            }else {
                PJ_yh.add("");
            }
            //预兑日

            if(!dxzh_four.getText().toString().equals("")){
                PJ_ydr.add(dxzh_four.getText().toString());
            }else {
                PJ_ydr.add(date_dd);
            }
            //到期日

            if(!time_four.getText().toString().equals("")){
                PJ_dqr.add(time_four.getText().toString());
            }else {
                PJ_dqr.add(date_dd);
            }

        }
        if(!pjhm_five.getText().toString().equals("")){
            PJ_hm.add(pjhm_five.getText().toString());
            PJ_spr.add(fh_sfpr);
            PJ_bdr.add(date_dd);
            //票别
            if(!pb_five.getText().toString().equals("")){
                PJ_zl.add(pb_five.getText().toString().substring(0,1));
            }else{
                PJ_zl.add("");
            }
            //金额
            if(!je_five.getText().toString().equals("")){
                PJ_je.add(je_five.getText().toString());
            }else{
                PJ_je.add("0.0");
            }
            //开票银行
            if(!yh_five.getText().toString().equals("")){
                PJ_yh.add(yh_five.getText().toString().substring(0,1));
            }else {
                PJ_yh.add("");
            }
            //预兑日

            if(!dxzh_five.getText().toString().equals("")){
                PJ_ydr.add(dxzh_five.getText().toString());
            }else {
                PJ_ydr.add(date_dd);
            }
            //到期日

            if(!time_five.getText().toString().equals("")){
                PJ_dqr.add(time_five.getText().toString());
            }else {
                PJ_dqr.add(date_dd);
            }
        }



        String hm_str = "";
        for (String zt : PJ_hm) {
            hm_str += zt + ",";
        }
        String sub_pjhm = hm_str.substring(0, hm_str.length() - 1);
        String je_str = "";
        for (String zt : PJ_je) {
            je_str += zt + ",";
            sum_pjje+=Double.parseDouble(zt);
        }
        String sub_pjje = je_str.substring(0, je_str.length() - 1);
        String yh_str = "";
        for (String zt : PJ_yh) {
            yh_str += zt + ",";
        }
        String sub_pjyh = yh_str.substring(0, yh_str.length() - 1);
        String spr_str = "";
        for (String zt : PJ_spr) {
            spr_str += zt + ",";
        }
        String sub_pjspr = spr_str.substring(0, spr_str.length() - 1);
        String ydr_str = "";
        for (String zt : PJ_ydr) {
            ydr_str += zt + ",";
        }
        String sub_pjydr = ydr_str.substring(0, ydr_str.length() - 1);
        String dqr_str = "";
        for (String zt : PJ_dqr) {
            dqr_str += zt + ",";
        }
        String sub_pjdqr = dqr_str.substring(0, dqr_str.length() - 1);
        String bdr_str = "";
        for (String zt : PJ_bdr) {
            bdr_str += zt + ",";
        }
        String sub_pjbdr = bdr_str.substring(0, bdr_str.length() - 1);
        String zl_str = "";
        for (String zt : PJ_zl) {
            zl_str += zt + ",";
        }
        String sub_pjzl = zl_str.substring(0, zl_str.length() - 1);
        Log.e("LiNing", "票据数据======" + sub_pjhm + "/y" + sub_pjje + "/r" + sub_pjyh
                + "/r" + sub_pjspr
                + "/r" + sub_pjydr
                + "/r" + sub_pjdqr
                + "/r" + sub_pjbdr
                + "/r" + sub_pjzl
                + "/r" + sum_pjje);
        Intent localIntent = getIntent();
        localIntent.putExtra("pj_hm", sub_pjhm);
        localIntent.putExtra("pj_je", sub_pjje);
        localIntent.putExtra("pj_yh", sub_pjyh);
        localIntent.putExtra("pj_spr", sub_pjspr);
        localIntent.putExtra("pj_ydr", sub_pjydr);
        localIntent.putExtra("pj_dqr", sub_pjdqr);
        localIntent.putExtra("pj_bdr", sub_pjbdr);
        localIntent.putExtra("pj_zl", sub_pjzl);
        localIntent.putExtra("pj_jehj", ""+sum_pjje);
        setResult(1, localIntent);
        finish();
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        if(object_chk==1){

            popupMenu.getMenuInflater().inflate(R.menu.pb_skd, popupMenu.getMenu());
        }else if(object_chk==2){
            popupMenu.getMenuInflater().inflate(R.menu.back, popupMenu.getMenu());
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.check1_skd
                        || menuItem.getItemId() == R.id.check2_skd
                        || menuItem.getItemId() == R.id.check3_skd
                        || menuItem.getItemId() == R.id.check4_skd
                        || menuItem.getItemId() == R.id.check5_skd
                        || menuItem.getItemId() == R.id.check6_skd
                        ) {
                    menuItem.setChecked(!menuItem.isChecked());
                    skdpb_id = menuItem.getTitle().toString().substring(0, 1);
                    Log.e("LiNing", "------新增的数据id" + skdpb_id);

                    if(checked==1){
                        pb_one.setText(menuItem.getTitle());
                    }
                    if(checked==2){
                        pb_two.setText(menuItem.getTitle());
                    }
                    if(checked==3){
                        pb_three.setText(menuItem.getTitle());
                    }
                    if(checked==4){
                        pb_four.setText(menuItem.getTitle());
                    }
                    if(checked==5){
                        pb_five.setText(menuItem.getTitle());
                    }

                    return true;
                }else if(menuItem.getItemId() == R.id.check1_kpyh
                        || menuItem.getItemId() == R.id.check2_kpyh
                        || menuItem.getItemId() == R.id.check3_kpyh
                        || menuItem.getItemId() == R.id.check4_kpyh
                        || menuItem.getItemId() == R.id.check5_kpyh
                        ){
                    menuItem.setChecked(!menuItem.isChecked());
                    skdkpyh_id = menuItem.getTitle().toString().substring(0, 1);
                    Log.e("LiNing", "------新增的数据id" + skdkpyh_id);
                    if(checked_yh==1){
                        yh_one.setText(menuItem.getTitle());
                    }
                    if(checked_yh==2){
                        yh_two.setText(menuItem.getTitle());
                    }
                    if(checked_yh==3){
                        yh_three.setText(menuItem.getTitle());
                    }
                    if(checked_yh==4){
                        yh_four.setText(menuItem.getTitle());
                    }
                    if(checked_yh==5){
                        yh_five.setText(menuItem.getTitle());
                    }

                    return true;
                }
                return true;
            }
        });
        popupMenu.show();
    }
}
