package com.example.ydshoa;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.CustAllObjectInfos;
import com.example.bean.NumberToCN;
import com.example.bean.PriceNumID;
import com.example.bean.URLS;
import com.google.gson.Gson;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ReceiptFormActivity extends Activity implements View.OnClickListener {
    //根数据
    private Context context;
    private SharedPreferences sp;
    private String session;
    //表头操作（功能）
    private Button query, add, reset, del, print, save, info, out;
    //基本数据
    private String db_zt, date_dd, date_dd_dh, db_bm, db_kh;
    RelativeLayout mHead_yus, mHead_yings;
    private ListView lv_skd_qry_yus, lv_skd_qry_yings;
    private TextView head, skd_tv_zt, skd_time, skd_tv_dh, skd_bm, skd_kh, skd_ywy, skd_zdwd, skd_djlb, skd_pjje, skd_yhzh, skd_qtzk, skd_xjzh, skd_yscz,
            skd_time_sx, skd_time_zs, skd_xxje, skd_hjje;
    private EditText et;
    private ImageButton skd_ib_zt, skd_ib_bm, skd_ib_kh, skd_ib_ywy, skd_ib_zdwd, skd_ib_djlb, skd_ib_pjje, skd_ib_yhzh, skd_ib_qtzk, skd_ib_xjzh, skd_ib_yscz;
    LinearLayout touch;
    String url_dh_skd = URLS.price_num_ls;
    AlertDialog alg_qtzk;
    CustAllObjectInfos.CustList cust_callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_receipt_form);
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        context = ReceiptFormActivity.this;
        db_zt = sp.getString("DB_MR", "");
        db_bm = sp.getString("USER_DEPBM", "");
        db_kh = sp.getString("USER_CUSTKH", "");
        getNowTime();
        intView();
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
        head.setText("收款单");
//        mHead_yus = ((RelativeLayout) findViewById(R.id.skdmx_head_yus));
//        mHead_yus.setFocusable(true);
//        mHead_yus.setClickable(true);
//        mHead_yus.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        mHead_yings = ((RelativeLayout) findViewById(R.id.skdmx_head_yings));
        mHead_yings.setFocusable(true);
        mHead_yings.setClickable(true);
        mHead_yings.setOnTouchListener(new ListViewAndHeadViewTouchLinstener_yings());
//        lv_skd_qry_yus = (ListView) findViewById(R.id.lv_skd_header_yus);
//        lv_skd_qry_yus.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        lv_skd_qry_yings = (ListView) findViewById(R.id.lv_skd_header_yings);
        lv_skd_qry_yings.setOnTouchListener(new ListViewAndHeadViewTouchLinstener_yings());
        skd_tv_zt = (TextView) findViewById(R.id.et_skd_all_zt);
        skd_tv_zt.setText(db_zt);
        skd_ib_zt = (ImageButton) findViewById(R.id.ib_skd_all_account);
        skd_ib_zt.setOnClickListener(this);
        skd_time = (TextView) findViewById(R.id.tv_time_skd);
        skd_time.setText(date_dd);
        skd_time.setOnClickListener(new View.OnClickListener() {
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
                                skd_time.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        skd_time_sx = (TextView) findViewById(R.id.tv_hj_sxrq);
        skd_time_sx.setText(date_dd);
        skd_time_sx.setOnClickListener(new View.OnClickListener() {
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
                                skd_time_sx.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        skd_time_zs = (TextView) findViewById(R.id.tv_hj_zsrq);
        skd_time_zs.setText(date_dd);
        skd_time_zs.setOnClickListener(new View.OnClickListener() {
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
                                skd_time_zs.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        skd_tv_dh = (TextView) findViewById(R.id.et_skd_all_skdh);
        skd_tv_dh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTicket();
            }
        });
        touch = (LinearLayout) findViewById(R.id.ll_touch_skd_all);
        touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getTicket();//获取临时档案编号
            }
        });
        //部门(id转name)
        skd_bm = (TextView) findViewById(R.id.tv_skd_jybm);
        skd_bm.setText(db_bm);
        skd_ib_bm = (ImageButton) findViewById(R.id.ib_skd_jybm);
        skd_ib_bm.setOnClickListener(this);
        //默认客户（添加模糊查询）
        skd_kh = (TextView) findViewById(R.id.tv_skd_zdkh);
        skd_ib_kh = (ImageButton) findViewById(R.id.ib_skd_zdkh);
        skd_ib_kh.setOnClickListener(this);
        //业务员
        skd_ywy = (TextView) findViewById(R.id.tv_skd_zryw);
        skd_ib_ywy = (ImageButton) findViewById(R.id.ib_skd_zryw);
        skd_ib_ywy.setOnClickListener(this);
        //终端网点客户(id转name)
        skd_zdwd = (TextView) findViewById(R.id.tv_skd_zdwd);
        skd_zdwd.setText(db_kh);
        skd_ib_zdwd = (ImageButton) findViewById(R.id.ib_skd_zdwd);
        skd_ib_zdwd.setOnClickListener(this);
        //单据类别
        skd_djlb = (TextView) findViewById(R.id.tv_skd_djlb);
        skd_ib_djlb = (ImageButton) findViewById(R.id.ib_skd_djlb);
        skd_ib_djlb.setOnClickListener(this);
        //票据金额
        skd_pjje = (TextView) findViewById(R.id.et_skd_pj);
        skd_ib_pjje = (ImageButton) findViewById(R.id.ib_skd_pj);
        skd_ib_pjje.setOnClickListener(this);
        //其他账款
        skd_qtzk = (TextView) findViewById(R.id.et_skd_qtje);
        skd_ib_qtzk = (ImageButton) findViewById(R.id.ib_skd_qtje);
        skd_ib_qtzk.setOnClickListener(this);
        //现金账户
        skd_xjzh = (TextView) findViewById(R.id.tv_skd_xj);
        skd_ib_xjzh = (ImageButton) findViewById(R.id.ib_skd_xjzh);
        skd_ib_xjzh.setOnClickListener(this);
        //银行账户
        skd_yhzh = (TextView) findViewById(R.id.tv_skd_yh);
        skd_ib_yhzh = (ImageButton) findViewById(R.id.ib_skd_yh);
        skd_ib_yhzh.setOnClickListener(this);
        //数字金额大小写切换
        skd_xxje = (TextView) findViewById(R.id.et_skd_jexx);
        skd_hjje = (TextView) findViewById(R.id.et_skd_skhj);
        BigDecimal numberOfMoney = new BigDecimal(Double.parseDouble(skd_xxje.getText().toString()));
        String s = NumberToCN.number2CNMontrayUnit(numberOfMoney);
        skd_hjje.setText(s);
        //表头操作（功能）
        query = (Button) findViewById(R.id.btn_skd_all_quickquery);
        add = (Button) findViewById(R.id.btn_skd_all_add);
        reset = (Button) findViewById(R.id.btn_skd_all_reset);
        del = (Button) findViewById(R.id.btn_skd_all_quickquery);
        print = (Button) findViewById(R.id.btn_skd_all_prite);
        save = (Button) findViewById(R.id.btn_skd_all_save);
        info = (Button) findViewById(R.id.btn_skd_all_okd);
        out = (Button) findViewById(R.id.btn_skd_all_out);
        query.setOnClickListener(this);
        add.setOnClickListener(this);
        reset.setOnClickListener(this);
        del.setOnClickListener(this);
        print.setOnClickListener(this);
        save.setOnClickListener(this);
        info.setOnClickListener(this);
        out.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_skd_all_quickquery:
                //    http://oa.ydshce.com:8080/InfManagePlatform/PaymentsqueryPayment.action?db_Id=DB_BJ18&&showRow=300&&clientRows=0&&rp_ID=1&&rp_NO=RT201805230051
//    &&rp_DD=2018-05-23&&usr_NO=L022&&dep=2023&&rem=孙黎明&&bil_NO=SO201805230097&&irp_ID=T&&CLS_ID=T&&cus_NO=KH031&&bil_TYPE=06&&usr=Z002&&chk_MAN=Z002
//    &&cus_NO_OS=123
                //速查（是否限制条件，账套必须传）
                //http://oa.ydshce.com:8080/InfManagePlatform/PaymentsqueryPayment.action?db_Id=DB_BJ18&&showRow=20&&clientRows=0
//                Intent intent_query=new Intent(context,ReceiptQueryActivity.class);
//                startActivity(intent_query);
                break;
            case R.id.ib_skd_pj:
                //回调票据/金额信息
                Intent intent_pjje = new Intent(context, BillAmtActivity.class);
//                intent_pjje.putExtra("flag", "1");
//                startActivityForResult(intent_pjje, 1);
                startActivity(intent_pjje);
                break;

            case R.id.ib_skd_yhzk:
                //其他账款
                View view_qtzk = getLayoutInflater()
                        .inflate(R.layout.skd_qtzk, null);
                TextView head_qtzk = (TextView) view_qtzk.findViewById(R.id.all_head);
                head_qtzk.setText("其他账款");
                ListView lv_qtzk = (ListView) view_qtzk.findViewById(R.id.lv_skd_qtzk_head);

                view_qtzk.findViewById(R.id.imageButton1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alg_qtzk.dismiss();
                    }
                });

                alg_qtzk = new AlertDialog.Builder(context).create();
                alg_qtzk.setCancelable(false);
                alg_qtzk.setView(view_qtzk);
                alg_qtzk.show();
                break;


            case R.id.ib_skd_all_account:
                Intent intent = new Intent(context, CondicionInfoActivity.class);
                intent.putExtra("flag", "1");
                startActivityForResult(intent, 1);
                break;
            case R.id.ib_skd_jybm:
                if (skd_tv_zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {
                    Intent bm_intent = new Intent(context, ErpDepInfoActivity.class);
                    bm_intent.putExtra("DB_ID", skd_tv_zt.getText().toString());
                    bm_intent.putExtra("flag", "2");
                    startActivityForResult(bm_intent, 2);
                }
                break;
            case R.id.ib_skd_zdkh:
                if (skd_tv_zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {
                    //调取查询客户的界面
                    Intent intent_cust = new Intent(context, QueryCustersActivity.class);
                    intent_cust.putExtra("ZT_VIP", skd_tv_zt.getText().toString());
                    startActivityForResult(intent_cust, 6);

                }
                break;
            case R.id.ib_skd_zryw:
                if (skd_tv_zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {
                    Intent yw_intent = new Intent(context, ErpDepInfoActivity.class);
                    yw_intent.putExtra("DB_ID", skd_tv_zt.getText().toString());
                    yw_intent.putExtra("flag", "5");
                    startActivityForResult(yw_intent, 5);
                }
                break;
            case R.id.ib_skd_zdwd:
                if (skd_tv_zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {
                    Intent zl_intent = new Intent(context, ErpDepInfoActivity.class);
                    zl_intent.putExtra("DB_ID", skd_tv_zt.getText().toString());
                    zl_intent.putExtra("flag", "3");
                    startActivityForResult(zl_intent, 3);
                }
                break;
            case R.id.ib_skd_djlb:
                if (skd_tv_zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {
                    Intent djlb_intent = new Intent(context, ErpDepInfoActivity.class);
                    djlb_intent.putExtra("DB_ID", skd_tv_zt.getText().toString());
                    djlb_intent.putExtra("flag", "22");
                    startActivityForResult(djlb_intent, 4);
                }
                break;

            default:
                break;
        }
    }

    //http://oa.ydshce.com:8080/InfManagePlatform/PaymentsqueryPayment.action?db_Id=DB_BJ18&&rp_DD=2019-1-1,2019-3-31
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    String str_id = data.getStringExtra("condition_db");
                    String str_name = data.getStringExtra("condition_name");
                    skd_tv_zt.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + str_name);
                }
                break;
            case 2:
                if (resultCode == 1) {
                    String str_name = data.getStringExtra("data_dep");
                    String str_id = data.getStringExtra("data_dep_id");
                    skd_bm.setText(str_name);
                    Log.e("LiNing", "提交的id====" + str_id + str_name);
                }
                break;
            case 3:
                if (resultCode == 1) {
                    String str_name = data.getStringExtra("data_dep");
                    String str_id = data.getStringExtra("data_dep_id");
                    skd_zdwd.setText(str_name);
                    Log.e("LiNing", "提交的id====" + str_id + str_name);
                }
                break;
            case 4:
                if (resultCode == 1) {
                    String str_name = data.getStringExtra("data_dep");
                    String str_id = data.getStringExtra("data_dep_id");
                    skd_djlb.setText(str_name);
                    Log.e("LiNing", "提交的id====" + str_id + str_name);
                }
                break;
            case 5:
                if (resultCode == 1) {
                    String str_name = data.getStringExtra("data_dep");
                    String str_id = data.getStringExtra("data_dep_id");
                    skd_ywy.setText(str_name);
                    Log.e("LiNing", "提交的id====" + str_id + str_name);
                }
                break;
            case 6:
                if (resultCode == 1) {
                    cust_callback = (CustAllObjectInfos.CustList) data.getSerializableExtra("CUST_QTY");
                    if (cust_callback.getCust_Name() != null) {
                        skd_kh.setText(cust_callback.getCust_Name());
                    } else {
                        skd_kh.setText("");
                    }

                }
                break;
        }
    }

    //    class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {
//        ListViewAndHeadViewTouchLinstener() {
//        }
//
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//            ((HorizontalScrollView) ReceiptFormActivity.this.mHead_yus
//                    .findViewById(R.id.horizontalScrollView1))
//                    .onTouchEvent(event);
//            return false;
//        }
//
//    }
    class ListViewAndHeadViewTouchLinstener_yings implements View.OnTouchListener {
        ListViewAndHeadViewTouchLinstener_yings() {
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ((HorizontalScrollView) ReceiptFormActivity.this.mHead_yings
                    .findViewById(R.id.horizontalScrollView1))
                    .onTouchEvent(event);
            return false;
        }

    }

    private void getTicket() {
        String DB_LS = skd_tv_zt.getText().toString();
        date_dd_dh = skd_time.getText().toString();
        if (DB_LS.equals("")) {
            Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
        } else if (date_dd.equals("")) {
            Toast.makeText(context, "请获取时间", Toast.LENGTH_LONG).show();
        } else {
            OkHttpClient client = new OkHttpClient();
            FormBody localFormBody = new FormBody.Builder()
                    .add("bn_Type", "RT")
                    .add("db_Id", DB_LS)
                    .add("bn_Date", date_dd)
                    .build();
            Request localRequest = new Request.Builder()
                    .addHeader("cookie", session).url(url_dh_skd)
                    .post(localFormBody)
                    .build();
            Log.e("LiNing", "临时单号===" + session + "---" + URLS.price_num_ls + "---" + DB_LS + "----" + date_dd);
            client.newCall(localRequest).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.code() == 200) {

                        String str = response.body().string();
                        Log.e("LiNing", "临时档案编号===" + str);
                        final PriceNumID bean_id = (PriceNumID) new Gson()
                                .fromJson(str, PriceNumID.class);
                        if (bean_id != null) {
                            ReceiptFormActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    String id_ls = bean_id.getBil_no();
                                    skd_tv_dh.setText(id_ls);
                                }
                            });

                        }
                    }

                }

                @Override
                public void onFailure(Call call, IOException e) {

                }
            });
        }
    }

    public void allback(View v) {
        finish();
    }
}
