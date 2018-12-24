package com.example.ydshoa;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.LeftOrRight.MyHScrollView;
import com.example.bean.Quotation;
import com.example.bean.URLS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QueryBjdActivity extends Activity implements View.OnClickListener {
    private Context context;
    private SharedPreferences sp;
    private String session,query_db,date_dd;
    String bjd_get = URLS.cust_bjxx_qyery;
    private TextView head,ks_time,js_time,bj_time,fwgj,jhfs,djlb,xszd,xsbm,xsgw,ztsj,styw,qdyw,yshk,shrq,qdrq;
    private EditText khxm,khdh,lplx,zxgs,xsdd,shr,shdh,xxbz, bjd_tv_bjdh, khly,tjmj,htdh,gwdh,xxdz,
            gjdh,sjdh,dyzt,zdr,shenhr;
    BjdZbQtyAdapter bjdzbQtyAdapter;
    RelativeLayout mHead;
    private ListView lv_offer_qry_zb;
    String cx_kstime,cx_jstime,cx_khxm,cx_khdh,cx_lplx,cx_zxgs,cx_jhfs,cx_xsdd,cx_shr,cx_shdh,cx_xxbz,cx_bjd_tv_bjrq,cx_bjd_tv_bjdh,cx_khly,cx_tjmj,cx_djlb,cx_htdh,cx_xszd,cx_xsbm,
            cx_xsgw,cx_gwdh,cx_fwgj,cx_gjdh,cx_ztsj,cx_sjdh,cx_styw,cx_qdyw,cx_yshk,cx_dyzt,cx_xxdz,cx_shrq,cx_qdrq,cx_zdr,cx_shenhr;
    private ImageButton ib_zt_xl, ib_djlb_xl, ib_ztsj_xl, ib_xszd_xl, ib_xsbm_xl, ib_xsgw_xl, ib_fwgj_xl, ib_styw_xl, ib_qdyw_xl,
            ib_yshk_xl, ib_kh_xl, ib_jhfs_xl, ib_shr_xl;
    private String djlb_id_sub, zd_id_sub, bm_id_sub, gw_id_sub, fwgj_id_sub, ztsj_id_sub, styw_id_sub, qdyw_id_sub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_query_bjd);
        context = QueryBjdActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        query_db = getIntent().getStringExtra("ZT_VIP");
        getNowTime();
        initView();
    }
    private void getNowTime() {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // 获取当前年份
        int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前日
        date_dd = mYear + "-" + mMonth + "-" + mDay;
    }
    private void initView() {
        head = (TextView) findViewById(R.id.all_head);
        head.setText("报价单列表");
        this.mHead = ((RelativeLayout) findViewById(R.id.bjzb_head));
        this.mHead.setFocusable(true);
        this.mHead.setClickable(true);
        this.mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        lv_offer_qry_zb= (ListView) findViewById(R.id.lv_bjzb_header);
        lv_offer_qry_zb.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        lv_offer_qry_zb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bjdzbQtyAdapter.setSelectItem(position);//刷新
                bjdzbQtyAdapter.notifyDataSetInvalidated();
                Quotation.QuotationList bjdList_callback = (Quotation.QuotationList) parent.getAdapter().getItem(position);
                Intent localIntent = getIntent();
                localIntent.putExtra("BJD_INFOS_ALL", bjdList_callback);
                setResult(1, localIntent);
                finish();
            }
        });
        ks_time= (TextView) findViewById(R.id.tv_time_ks);
        ks_time.setOnClickListener(new View.OnClickListener() {
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
                                ks_time.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        js_time= (TextView) findViewById(R.id.tv_time_js);
        js_time.setOnClickListener(new View.OnClickListener() {
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
                                js_time.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        bj_time= (TextView) findViewById(R.id.tv_time_offer);
//        bj_time.setText(date_dd);
        bj_time.setOnClickListener(new View.OnClickListener() {
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
                                bj_time.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        bjd_tv_bjdh= (EditText) findViewById(R.id.et_offer_all_dabh);
        khxm= (EditText) findViewById(R.id.et_offer_all_khxm);
        khdh= (EditText) findViewById(R.id.et_offer_all_khdh);
        lplx= (EditText) findViewById(R.id.et_offer_all_lplx);
        zxgs= (EditText) findViewById(R.id.et_offer_all_zxgs);
        jhfs= (TextView) findViewById(R.id.et_offer_all_jhfs);
        xsdd= (EditText) findViewById(R.id.et_offer_all_xsdd);
        shr= (EditText) findViewById(R.id.et_offer_all_shr);
        shdh= (EditText) findViewById(R.id.et_offer_all_shdh);
        xxdz= (EditText) findViewById(R.id.et_offer_all_xxdz);
        xxbz= (EditText) findViewById(R.id.et_offer_all_bzxx);
        khly= (EditText) findViewById(R.id.et_offer_all_khly);
        tjmj= (EditText) findViewById(R.id.et_offer_all_tjmj);
        djlb= (TextView) findViewById(R.id.et_offer_all_djlb);
        htdh= (EditText) findViewById(R.id.et_offer_all_htdh);
        xszd= (TextView) findViewById(R.id.et_offer_all_xszd);
        xsbm= (TextView) findViewById(R.id.et_offer_all_xsbm);
        xsgw= (TextView) findViewById(R.id.et_offer_all_xsgw);
        gwdh= (EditText) findViewById(R.id.et_offer_all_gwdh);
        fwgj= (TextView) findViewById(R.id.et_offer_all_fwgj);
        gjdh= (EditText) findViewById(R.id.et_offer_all_gjdh);
        ztsj= (TextView) findViewById(R.id.et_offer_all_ztsj);
        sjdh= (EditText) findViewById(R.id.et_offer_all_sjdh);
        styw= (TextView) findViewById(R.id.et_offer_all_styw);
        qdyw= (TextView) findViewById(R.id.tv_offer_all_qdyw);
        yshk= (TextView) findViewById(R.id.et_offer_all_yshk);
        dyzt= (EditText) findViewById(R.id.et_offer_all_dyzt);
        shrq= (TextView) findViewById(R.id.tv_offer_all_shrq);
//        shrq.setText(date_dd);
        shrq.setOnClickListener(new View.OnClickListener() {
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
                                shrq.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        qdrq= (TextView) findViewById(R.id.tv_offer_all_qdrq);
//        qdrq.setText(date_dd);
        qdrq.setOnClickListener(new View.OnClickListener() {
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
                                qdrq.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        zdr= (EditText) findViewById(R.id.tv_offer_all_zdr);
        shenhr= (EditText) findViewById(R.id.tv_offer_all_shenhr);

        //单据类别
        ib_djlb_xl = (ImageButton) findViewById(R.id.ib_offer_all_djlb);
        ib_djlb_xl.setOnClickListener(this);
        //终端（调接口）
        ib_xszd_xl = (ImageButton) findViewById(R.id.ib_offer_all_xszd);
        ib_xszd_xl.setOnClickListener(this);
        //部门（调接口）
        ib_xsbm_xl = (ImageButton) findViewById(R.id.ib_offer_all_xsbm);
        ib_xsbm_xl.setOnClickListener(this);
        //顾问（调接口）
        ib_xsgw_xl = (ImageButton) findViewById(R.id.ib_offer_all_xsgw);
        ib_xsgw_xl.setOnClickListener(this);
        //服务管家（调接口业务）
        ib_fwgj_xl = (ImageButton) findViewById(R.id.ib_offer_all_fwgj);
        ib_fwgj_xl.setOnClickListener(this);
        //制图设计（调接口业务）
        ib_ztsj_xl = (ImageButton) findViewById(R.id.ib_offer_all_ztsj);
        ib_ztsj_xl.setOnClickListener(this);
        //审图业务（调接口业务）
        ib_styw_xl = (ImageButton) findViewById(R.id.ib_offer_all_styw);
        ib_styw_xl.setOnClickListener(this);
        //确单业务（调接口业务）
        ib_qdyw_xl = (ImageButton) findViewById(R.id.ib_offer_all_qdyw);
        ib_qdyw_xl.setOnClickListener(this);
        //预收货款（调收款单）
        ib_yshk_xl = (ImageButton) findViewById(R.id.ib_offer_all_yshk);
        ib_yshk_xl.setOnClickListener(this);
        //交货方式（调pop）
        ib_jhfs_xl = (ImageButton) findViewById(R.id.ib_offer_all_jhfs);
        ib_jhfs_xl.setOnClickListener(this);
    }

    public void bjdQuery(View v){
        getinfos();//获取查询信息
        OkHttpClient client_all = new OkHttpClient();
        FormBody body_all = new FormBody.Builder()
                .add("DB_ID", query_db)
                .add("QT_DD", cx_kstime)
                .add("QT_DD2", cx_jstime)
                .add("Cust_Name", cx_khxm)
                .add("Cust_Con", cx_khdh)
                .add("Hous_Type", cx_lplx)
                .add("Deco_Com", cx_zxgs)
                .add("SEND_MTH", cx_jhfs)
                .add("Con_Per", cx_shr)
                .add("Con_Tel", cx_shdh)
                .add("Con_Add", cx_xxdz)
                .add("REM", cx_xxbz)
                .add("QT_NO", cx_bjd_tv_bjdh)
                .add("Cust_Src", cx_khly)
                .add("Remd", cx_tjmj)
                .add("BIL_TYPE", cx_djlb)
                .add("CUS_OS_NO", cx_htdh)
                .add("CUS_NO", cx_xszd)
                .add("CUS_NAME", cx_xszd)//
                .add("DEP_NO", cx_xsbm)
                .add(" DEP_NAME", cx_xsbm)//
                .add("SAL_NO", cx_xsgw)
                .add("SAL_NAME", cx_xsgw)//
                .add("SAL_Tel", cx_gwdh)
                .add("SECE", cx_fwgj)
                .add("SECE_TEL", cx_gjdh)
                .add("STYL", cx_ztsj)
                .add("STYL_TEL", cx_sjdh)
                .add("PLAW", cx_styw)
                .add("AFFI", cx_qdyw)
                .add("DEP_RED", cx_yshk)
                .add("PRT_SW", cx_dyzt)
                .add("chk_dd", cx_shrq)
                .add("AFFI_DD",cx_qdrq )
                .add("usr", cx_zdr)
                .add("usr_chk",cx_shenhr )
                .build();
        Log.e("LiNing", "查询数据----" + query_db+"---"+cx_shrq+"---"+cx_qdrq+"---"+cx_zdr+"---"+cx_shenhr);
        Request request_all = new Request.Builder()
                .addHeader("cookie", session).url(bjd_get)
                .post(body_all)
                .build();
        client_all.newCall(request_all).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String info_all = response.body().string();
                Log.e("LiNing", "id_type结果====" + info_all);
                Gson gson = new GsonBuilder().setDateFormat(
                        "yyyy-MM-dd HH:mm:ss").create();
                final Quotation bjdAllInfos = gson.fromJson(info_all, Quotation.class);
                Log.e("LiNing", "id_type结果====" + bjdAllInfos);
                QueryBjdActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bjdAllInfos != null) {
                            List<Quotation.QuotationList> quotationList = bjdAllInfos.getQuotationList();
                            if(quotationList!=null&&quotationList.size()>0){
                                bjdzbQtyAdapter = new BjdZbQtyAdapter(R.layout.bjzbobj_head, quotationList, context);
                                lv_offer_qry_zb.setAdapter(bjdzbQtyAdapter);
                                bjdzbQtyAdapter.notifyDataSetChanged();
                            }else{
                                Toast.makeText(context,"此账套无数据", Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                });

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    private void getinfos() {

         cx_khxm = khxm.getText().toString();
         cx_khdh = khdh.getText().toString();
         cx_lplx = lplx.getText().toString();
         cx_zxgs = zxgs.getText().toString();
         cx_jhfs = jhfs.getText().toString();
         cx_xsdd = xsdd.getText().toString();
         cx_shr = shr.getText().toString();
         cx_shdh = shdh.getText().toString();
         cx_xxbz = xxbz.getText().toString();
         cx_xxdz = xxdz.getText().toString();
         cx_bjd_tv_bjdh= bjd_tv_bjdh.getText().toString();
         cx_bjd_tv_bjrq= bj_time.getText().toString();
        cx_kstime= ks_time.getText().toString();
        cx_jstime= js_time.getText().toString();
         cx_khly= khly.getText().toString();
         cx_tjmj= tjmj.getText().toString();
         cx_djlb= djlb.getText().toString();
         cx_htdh = htdh.getText().toString();
         cx_xszd = xszd.getText().toString();
         cx_xsbm = xsbm.getText().toString();
         cx_xsgw = xsgw.getText().toString();
         cx_gwdh = gwdh.getText().toString();
         cx_fwgj = fwgj.getText().toString();
         cx_gjdh = gjdh.getText().toString();
         cx_ztsj = ztsj.getText().toString();
         cx_sjdh= sjdh.getText().toString();
         cx_styw= styw.getText().toString();
         cx_qdyw= qdyw.getText().toString();
         cx_yshk= yshk.getText().toString();
         cx_dyzt= dyzt.getText().toString();
         cx_shrq= shrq.getText().toString();
         cx_qdrq= qdrq.getText().toString();
         cx_zdr= zdr.getText().toString();
         cx_shenhr= shenhr.getText().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //单据类别-9
            case R.id.ib_offer_all_djlb:
                if (query_db.equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {

                    Intent intent_djlb = new Intent(context,
                            CondicionInfoActivity.class);
                    intent_djlb.putExtra("flag", "22");
                    intent_djlb.putExtra("queryID", query_db);
                    startActivityForResult(intent_djlb, 9);
//                    ischeck_bbqd = true;//用于判断bbqd_id是否点击
                }
                break;
            //销售终端-10
            case R.id.ib_offer_all_xszd:
                if (query_db.equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {

                    Intent intent_zd = new Intent(context,
                            CondicionInfoActivity.class);
                    intent_zd.putExtra("flag", "13");
                    intent_zd.putExtra("queryID", query_db);
                    startActivityForResult(intent_zd, 10);
//                    ischeck_bbqd = true;//用于判断bbqd_id是否点击
                }
                break;
            //销售部门-11
            case R.id.ib_offer_all_xsbm:
                if (query_db.equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {

                    Intent intent_bm = new Intent(context,
                            CondicionInfoActivity.class);
                    intent_bm.putExtra("flag", "11");
                    intent_bm.putExtra("queryID", query_db);
                    startActivityForResult(intent_bm, 11);
//                    ischeck_bbqd = true;//用于判断bbqd_id是否点击
                }
                break;
            //销售顾问-12
            case R.id.ib_offer_all_xsgw:
                if (query_db.equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {

                    Intent intent_gw = new Intent(context,
                            CondicionInfoActivity.class);
                    intent_gw.putExtra("flag", "17");
                    intent_gw.putExtra("queryID", query_db);
                    startActivityForResult(intent_gw, 12);
//                    ischeck_bbqd = true;//用于判断bbqd_id是否点击
                }
                break;
            //服务管家-13
            case R.id.ib_offer_all_fwgj:
                if (query_db.equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {

                    Intent intent_gj = new Intent(context,
                            CondicionInfoActivity.class);
                    intent_gj.putExtra("flag", "17");
                    intent_gj.putExtra("queryID", query_db);
                    startActivityForResult(intent_gj, 13);
//                    ischeck_bbqd = true;//用于判断bbqd_id是否点击
                }
                break;
            //制图设计-14
            case R.id.ib_offer_all_ztsj:
                if (query_db.equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {

                    Intent intent_ztsj = new Intent(context,
                            CondicionInfoActivity.class);
                    intent_ztsj.putExtra("flag", "17");
                    intent_ztsj.putExtra("queryID", query_db);
                    startActivityForResult(intent_ztsj, 14);
//                    ischeck_bbqd = true;//用于判断bbqd_id是否点击
                }
                break;
            //审图业务-15
            case R.id.ib_offer_all_styw:
                if (query_db.equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {

                    Intent intent_styw = new Intent(context,
                            CondicionInfoActivity.class);
                    intent_styw.putExtra("flag", "17");
                    intent_styw.putExtra("queryID", query_db);
                    startActivityForResult(intent_styw, 15);
//                    ischeck_bbqd = true;//用于判断bbqd_id是否点击
                }
                break;
            //确单业务-16
            case R.id.ib_offer_all_qdyw:
                if (query_db.equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {

                    Intent intent_qdyw = new Intent(context,
                            CondicionInfoActivity.class);
                    intent_qdyw.putExtra("flag", "17");
                    intent_qdyw.putExtra("queryID", query_db);
                    startActivityForResult(intent_qdyw, 16);
//                    ischeck_bbqd = true;//用于判断bbqd_id是否点击
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 9:
                if (resultCode == 1) {
                    String str_id = data.getStringExtra("data_return");
                    djlb_id_sub = data.getStringExtra("data_return_ids");
                    djlb.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + djlb_id_sub);
                }
                break;
            case 10:
                if (resultCode == 1) {
                    String str_id = data.getStringExtra("data_return");
                    zd_id_sub = data.getStringExtra("data_return_ids");
                    xszd.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + zd_id_sub);
                }
                break;
            case 11:
                if (resultCode == 1) {
                    String str_id = data.getStringExtra("data_return");
                    bm_id_sub = data.getStringExtra("data_return_ids");
                    xsbm.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + bm_id_sub);
                }
                break;
            case 12:
                if (resultCode == 1) {
                    String str_id = data.getStringExtra("data_return");
                    gw_id_sub = data.getStringExtra("data_return_ids");
                    xsgw.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + gw_id_sub);
                }
                break;
            case 13:
                if (resultCode == 1) {
                    String str_id = data.getStringExtra("data_return");
                    fwgj_id_sub = data.getStringExtra("data_return_ids");
                    fwgj.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + fwgj_id_sub);
                }
                break;
            case 14:
                if (resultCode == 1) {
                    String str_id = data.getStringExtra("data_return");
                    ztsj_id_sub = data.getStringExtra("data_return_ids");
                    ztsj.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + ztsj_id_sub);
                }
                break;
            case 15:
                if (resultCode == 1) {
                    String str_id = data.getStringExtra("data_return");
                    styw_id_sub = data.getStringExtra("data_return_ids");
                    styw.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + styw_id_sub);
                }
                break;
            case 16:
                if (resultCode == 1) {
                    String str_id = data.getStringExtra("data_return");
                    qdyw_id_sub = data.getStringExtra("data_return_ids");
                    qdyw.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + qdyw_id_sub);
                }
                break;
        }
    }

    public class BjdZbQtyAdapter extends BaseAdapter {
        int id_row_layout;
        LayoutInflater mInflater;
        List<Quotation.QuotationList> data_adp;
        //item高亮显示
        private int selectItem = -1;

        public void setSelectItem(int selectItem) {
            this.selectItem = selectItem;
        }

        public BjdZbQtyAdapter(int bjzbobj_head, List<Quotation.QuotationList> quotationList, Context context) {
            this.id_row_layout = bjzbobj_head;
            this.data_adp = quotationList;
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return data_adp.size();
        }

        @Override
        public Object getItem(int position) {
            return data_adp.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder=null;
            if (convertView == null) {
                synchronized (QueryBjdActivity.this) {

                    convertView = mInflater.inflate(id_row_layout, null);
                    holder=new ViewHolder();
                    MyHScrollView scrollView1 = (MyHScrollView) convertView
                            .findViewById(R.id.horizontalScrollView1);
                    holder.scrollView = scrollView1;
                    holder.bjzb_xh= (TextView) convertView.findViewById(R.id.textView1_id_bjzbxh);
                    holder.bjzb_dh= (TextView) convertView.findViewById(R.id.textView2_bjzbbjdh);
                    holder.bjzb_rq= (TextView) convertView.findViewById(R.id.textView3_bjzbbjrq);
                    holder.bjzb_khly= (TextView) convertView.findViewById(R.id.textView4_bjzbkhly);
                    holder.bjzb_khxm= (TextView) convertView.findViewById(R.id.textView5_bjzbkhxm);
                    holder.bjzb_khdh= (TextView) convertView.findViewById(R.id.tv_bjzbkhdh);

                    MyHScrollView headSrcrollView = (MyHScrollView) mHead
                            .findViewById(R.id.horizontalScrollView1);
                    headSrcrollView
                            .AddOnScrollChangedListener(new OnScrollChangedListenerImp(
                                    scrollView1));
                    convertView.setTag(holder);
                }
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            Quotation.QuotationList quotationList_info = data_adp.get(position);
            if(quotationList_info!=null){
                holder.bjzb_xh.setText(""+position);
                if(quotationList_info.getQT_NO()!=null){
                    holder.bjzb_dh.setText(quotationList_info.getQT_NO().toString());
                }else{
                    holder.bjzb_dh.setText("");
                }
                if(quotationList_info.getQT_DD()!=null){
                    SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                    try {
                        Date parse = sf1.parse(quotationList_info.getQT_DD().toString());
                        String format = new SimpleDateFormat("yyyy-MM-dd").format(parse);
                        Log.e("LiNing", "时间====xin=====" + format);
                        holder.bjzb_rq.setText(format);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else{
                    holder.bjzb_rq.setText("");
                }
                if(quotationList_info.getCust_Src()!=null){
                    holder.bjzb_khly.setText(quotationList_info.getCust_Src().toString());
                }else{
                    holder.bjzb_khly.setText("");
                }
                if(quotationList_info.getCust_Name()!=null){
                    holder.bjzb_khxm.setText(quotationList_info.getCust_Name().toString());
                }else{
                    holder.bjzb_khxm.setText("");
                }
                if(quotationList_info.getCust_Con()!=null){
                    holder.bjzb_khdh.setText(quotationList_info.getCust_Con().toString());
                }else{
                    holder.bjzb_khdh.setText("");
                }
            }
            //操作选择等
            if (position == selectItem) {
                convertView.setBackgroundColor(Color.RED);
            } else {
                convertView.setBackgroundColor(Color.TRANSPARENT);
            }
            return convertView;
        }

        class ViewHolder {
            HorizontalScrollView scrollView;
            public TextView bjzb_xh;
            public TextView bjzb_dh;
            public TextView bjzb_rq;
            public TextView bjzb_khly;
            public TextView bjzb_khxm;
            public TextView bjzb_khdh;
        }
    }
    class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {
        ListViewAndHeadViewTouchLinstener() {
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ((HorizontalScrollView) QueryBjdActivity.this.mHead
                    .findViewById(R.id.horizontalScrollView1))
                    .onTouchEvent(event);
            return false;
        }

    }
    class OnScrollChangedListenerImp implements
            MyHScrollView.OnScrollChangedListener {
        MyHScrollView mScrollViewArg;

        public OnScrollChangedListenerImp(MyHScrollView mScrollViewArg) {
            super();
            this.mScrollViewArg = mScrollViewArg;
        }

        public void onScrollChanged(int paramInt1, int paramInt2,
                                    int paramInt3, int paramInt4) {
            this.mScrollViewArg.smoothScrollTo(paramInt1, paramInt2);
        }
    }
    public void allback(View v) {
        finish();
    }
}
