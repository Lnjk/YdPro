package com.example.ydshoa;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BillAmtActivity extends Activity implements View.OnClickListener {
    //根数据
    private Context context;
    private SharedPreferences sp;
    private String session,fh_sjs;

    //基本数据
    private String db_zt,date_dd,skdpb_id,skdkpyh_id;
    private TextView head,pb_one,pb_two,pb_three,pb_four,pb_five,yh_one,yh_two,yh_three,yh_four,yh_five,time_one,time_two,time_three,time_four,time_five;
    private int object_chk,checked,checked_yh;
    private EditText pjhm_one,pjhm_two,pjhm_three,pjhm_four,pjhm_five,je_one,je_two,je_three
            ,je_four,je_five,dxzh_one,dxzh_two,dxzh_three,dxzh_four,dxzh_five;
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
        getNowTime();
        intView();
        infos_chage();
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
        pjhm_two= (EditText) findViewById(R.id.et_pjje_pjhm_two);
        pjhm_three= (EditText) findViewById(R.id.et_pjje_pjhm_three);
        pjhm_four= (EditText) findViewById(R.id.et_pjje_pjhm_four);
        pjhm_five= (EditText) findViewById(R.id.et_pjje_pjhm_five);
        je_one= (EditText) findViewById(R.id.et_pjje_je_one);
        je_two= (EditText) findViewById(R.id.et_pjje_je_two);
        je_three= (EditText) findViewById(R.id.et_pjje_je_three);
        je_four= (EditText) findViewById(R.id.et_pjje_je_four);
        je_five= (EditText) findViewById(R.id.et_pjje_je_five);
        dxzh_one= (EditText) findViewById(R.id.et_pjje_dxzh_one);
        dxzh_two= (EditText) findViewById(R.id.et_pjje_dxzh_two);
        dxzh_three= (EditText) findViewById(R.id.et_pjje_dxzh_three);
        dxzh_four= (EditText) findViewById(R.id.et_pjje_dxzh_four);
        dxzh_five= (EditText) findViewById(R.id.et_pjje_dxzh_five);


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
        time_one.setText(date_dd);
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
        time_two.setText(date_dd);
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
        time_three.setText(date_dd);
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
        time_four.setText(date_dd);
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
        time_five.setText(date_dd);
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
            String pjje_dxzh = jsonObject.get("usr").toString();//兑现帐户
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

                default:
                    break;
        }
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
