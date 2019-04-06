package com.example.ydshoa;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.DesignAllInfos;
import com.example.bean.JfMxBean;
import com.example.bean.JsonRootBean;
import com.example.bean.Jsonjf;
import com.example.bean.NumberToCN;
import com.example.bean.PriceNumID;
import com.example.bean.ReceiptSkdForm;
import com.example.bean.URLS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class DesignerJlActivity extends Activity implements View.OnClickListener {
    private Context context;
    private SharedPreferences sp;
    private String session, db_zt, user_Id, user_yh, user_Name, jf_id_hd;
    private TextView head, jl_sszt, jl_sjsdh, jl_jfdh, jl_jflx, jl_jfrq, jl_jabz, jl_zdr, jl_shr, jl_lydh,jl_sfzh;
    private Button jl_add, jl_del, jl_reset, jl_query, jl_save, jl_sh;
    private EditText  jl_szmc, jl_jfbd, jl_jfms;
    private ImageButton jl_sszt_ib, jl_sjsdh_ib, jl_jabz_ib, jl_jalx_ib;
    private String str_name, date_dd, vip_no_cust, sjsdh_del;
    int do_design_jl;
    String zt_comit_jl, jfdh_comit_jl, sjsdh_comit_jl, sfzh_comit_jl, jflx_comit_jl, szmc_comit_jl, lydh_comit_jl, jfrq_comit_jl, jfbd_comit_jl, jfms_comit_jl, jabz_comit_jl, zdr_comit_jl, shr_comit_jl, sjsdh_comit_jl_id;
    //判断新增数据是否存在
    ArrayList<String> card_list;
    LinearLayout touch;
    //接口
    String jfjl_get = URLS.design_jfdjquery_mx;
    String jfjl_add = URLS.design_jfdj_add;
    String jfjl_del = URLS.design_jfdj_del;
    String jfjl_set = URLS.design_jfdj_set;
    String url_dh_price = URLS.price_num_ls;
    //dialog数据
    private Button query_ok_cd, start_quick, stop_quick, jf_get;
    private ImageButton query_fh_cd;
    private ListView lv_query_jf;
    String str_time, stopTime;
    private AlertDialog alertDialog;
    String s_zh, clientRows, comit_zt, comit_time, comit_time_zb, comit_dh, comit_sjsdh, comit_sfzh, comit_jflx, comit_szmc, comit_jabz, comit_jabz_quy,
            comit_zdr, comit_jfbd, comit_lydh, comit_jfms,jf_fh_jflx,jf_fh_sjsdh;
    List<JfMxBean.Data> mf_monList;
    private JfsQtyAdapter qtyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_designer_jl);
        context = DesignerJlActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        user_Id = sp.getString("USER_ID", "");
        user_yh = sp.getString("MR_YH", "");
        user_Name = sp.getString("USER_NAME", "");
        session = sp.getString("SESSION", "");
        getNowTime();
        initView();//初始化
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
        head.setText("会员积分单");
        jl_sszt = (TextView) findViewById(R.id.et_designjl_zt);
        jl_sszt.setText(db_zt);
        touch = (LinearLayout) findViewById(R.id.ll_touch_vip);
        touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getTicket();//获取临时单号
            }
        });
        jl_sjsdh = (TextView) findViewById(R.id.et_designjl_sjsdh);
        jl_jfdh = (TextView) findViewById(R.id.tv_jfcard);
        jl_jflx = (TextView) findViewById(R.id.et_designjl_jflx);
        jl_jfrq = (TextView) findViewById(R.id.et_designjl_jfrq);
        jl_jfrq.setText(date_dd);
        jl_jfrq.setOnClickListener(new View.OnClickListener() {
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
                                jl_jfrq.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        jl_jabz = (TextView) findViewById(R.id.et_designjl_jabz);
        jl_zdr = (TextView) findViewById(R.id.et_designjl_zdr);
        jl_zdr.setText(user_Name);
        jl_shr = (TextView) findViewById(R.id.et_designjl_shr);
        jl_lydh = (TextView) findViewById(R.id.et_designjl_lydh);

        jl_sfzh = (TextView) findViewById(R.id.et_designjl_usercard);
        jl_szmc = (EditText) findViewById(R.id.et_designjl_szmc);
        jl_jfbd = (EditText) findViewById(R.id.et_designjl_jfbd);
        jl_jfms = (EditText) findViewById(R.id.et_designjl_jfms);

        jl_sszt_ib = (ImageButton) findViewById(R.id.ib_designjl_account);
        jl_sjsdh_ib = (ImageButton) findViewById(R.id.ib_designjl_sjsdh);
        jl_jabz_ib = (ImageButton) findViewById(R.id.ib_designjl_jabz);
        jl_jalx_ib = (ImageButton) findViewById(R.id.ib_designjl_jflx);

        jl_add = (Button) findViewById(R.id.btn_designjl_add);
        jl_del = (Button) findViewById(R.id.btn_designjl_del);
        jl_reset = (Button) findViewById(R.id.btn_designjl_reset);
        jl_query = (Button) findViewById(R.id.btn_designjl_query);
        jl_save = (Button) findViewById(R.id.btn_designjl_save);
        jl_sh = (Button) findViewById(R.id.btn_designjl_sh);
        jl_add.setOnClickListener(this);
        jl_del.setOnClickListener(this);
        jl_reset.setOnClickListener(this);
        jl_query.setOnClickListener(this);
        jl_save.setOnClickListener(this);
        jl_sh.setOnClickListener(this);
        jl_sszt_ib.setOnClickListener(this);
        jl_sjsdh_ib.setOnClickListener(this);
        jl_jabz_ib.setOnClickListener(this);
        jl_jalx_ib.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_designjl_account:
                Intent intent = new Intent(context, CondicionInfoActivity.class);
                intent.putExtra("flag", "1");
                startActivityForResult(intent, 1);
                break;
            case R.id.ib_designjl_sjsdh:
//                设计师
                Intent intent_vip = new Intent(context, QueryDesigActivity.class);
                intent_vip.putExtra("ZT_VIP", jl_sszt.getText().toString());
                intent_vip.putExtra("CUST_DO", "2");
                startActivityForResult(intent_vip, 2);
                break;
            case R.id.ib_designjl_jflx:
                if (jl_sszt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {
                    ischeck_vip = true;
                    Intent intent_jflx = new Intent(context, JfClassActivity.class);
                    intent_jflx.putExtra("ZT_VIP", jl_sszt.getText().toString());
                    startActivityForResult(intent_jflx, 3);
                }
                break;
            case R.id.ib_designjl_jabz:
                showPopupMenu(jl_jabz_ib);
                break;
            case R.id.btn_designjl_add:
                clearInfo();
                jl_sjsdh_ib.setFocusable(false);
                do_design_jl = 1;
                jl_zdr.setEnabled(false);
                jl_shr.setEnabled(false);
                jl_jabz.setText("否");
                jl_jabz_ib.setEnabled(false);

                break;
            case R.id.btn_designjl_del:
                new AlertDialog.Builder(context)
                        .setTitle("是否删除")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                delInfo();
                            }
                        })
                        .setNegativeButton("否", null)
                        .show();
                break;
            case R.id.btn_designjl_reset:
                do_design_jl = 2;
                jl_jabz_ib.setEnabled(true);
                jl_jalx_ib.setEnabled(true);
                break;
            case R.id.btn_designjl_query:
                if (jl_sszt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {

                    get_aleartDialog();
                }
                break;
            case R.id.btn_designjl_save:
                if (do_design_jl == 1) {
                    commitInfos();
                    save_add();
                    //获取所有数据（主要是判断身份证）
//                    getInfos_all();

                }
                if (do_design_jl == 2) {
                    commitInfos();//主表信息
                    getinfos_queryToset();//修改
                }
                break;
            case R.id.btn_designjl_sh:
                break;
            default:
                break;
        }

    }
    boolean ischeck_vip = false;//用于判断type_id是否点击
    private void getinfos_queryToset() {
        if (ischeck_vip == false) {
            jflx_comit_jl = jf_fh_jflx;
        } else {
            jflx_comit_jl = jf_id_hd;
        }
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("db_Id", zt_comit_jl)
//                .add("vip_NO", sjsdh_comit_jl_id)
                    .add("vip_NO", jf_fh_sjsdh)
                .add("card_Num", sfzh_comit_jl)
                .add("vp_No", jfdh_comit_jl)
                .add("points_Type", jflx_comit_jl)
                .add("item_Name", "厂家自登")
                .add("source_No", lydh_comit_jl)
                .add("points_Date", jfrq_comit_jl)
                .add("points", jfbd_comit_jl)
                .add("rem", jfms_comit_jl)
                .add("cls_Id", jabz_comit_jl)
                .add("usr_No", user_yh)
                .add("chk_No", user_yh)
                .build();
        Log.e("LiNing", "添加结果====" + zt_comit_jl + "---" + sjsdh_comit_jl_id + "---" + sfzh_comit_jl + "---" + jfdh_comit_jl + "---" + jflx_comit_jl + "---"
                + szmc_comit_jl + "---" + lydh_comit_jl + "---" + jfrq_comit_jl + "---" + jfbd_comit_jl + "---" + jfms_comit_jl + "---" + jabz_comit_jl + "---" + user_yh);
        client.newCall(
                new Request.Builder().addHeader("cookie", session).url(jfjl_set)
                        .post(body).build()).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                String str = response.body().string();
                Log.e("LiNing", "修改结果====" + str);
                final Jsonjf localJsonRootBean = (Jsonjf) new Gson()
                        .fromJson(str, Jsonjf.class);
                if (localJsonRootBean != null) {
                    DesignerJlActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            boolean rlo = localJsonRootBean.isAddResult();
                            if (rlo == true) {
                                Toast.makeText(DesignerJlActivity.this,
                                        "修改成功", Toast.LENGTH_SHORT).show();
                                clearInfo();
                            } else if (rlo == false) {
                                Toast.makeText(DesignerJlActivity.this,
                                        "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call arg0, IOException arg1) {

            }
        });
    }

    private void get_aleartDialog() {
        View view = getLayoutInflater()
                .inflate(R.layout.sjsjl_query, null);
        lv_query_jf = (ListView) view.findViewById(R.id.lv_jf_query_header_query);
        query_ok_cd = (Button) view.findViewById(R.id.btn_jf_query_dialogOk);
        query_fh_cd = (ImageButton) view.findViewById(R.id.imageButton1);
        start_quick = (Button) view.findViewById(R.id.btn_start_time);
        stop_quick = (Button) view.findViewById(R.id.btn_stop_time);
        jf_get = (Button) view.findViewById(R.id.search_jf_query);
        start_quick.setText(date_dd);
        stop_quick.setText(date_dd);

        start_quick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // 获取当前年份
                int mMonth = c.get(Calendar.MONTH);// 获取当前月份
                int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期cal.set(Calendar.DAY_OF_MONTH, 1);
                Log.e("LiNing", "y" + mYear + "/y" + mMonth + "/r" + mDay);
                new DatePickerDialog(context, DatePickerDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                str_time = String.format("%d-%d-%d", year,
                                        monthOfYear + 1, dayOfMonth);
                                start_quick.setText(str_time);

                            }
                        }, mYear, mMonth, mDay).show();//获取当前时间


            }
        });
        stop_quick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // 获取当前年份
                int mMonth = c.get(Calendar.MONTH);// 获取当前月份
                int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期cal.set(Calendar.DAY_OF_MONTH, 1);
                Log.e("LiNing", "y" + mYear + "/y" + mMonth + "/r" + mDay);
                new DatePickerDialog(context, DatePickerDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                stopTime = String.format("%d-%d-%d", year,
                                        monthOfYear + 1, dayOfMonth);
                                stop_quick.setText(stopTime);

                            }
                        }, mYear, mMonth, mDay).show();//获取当前时间

            }
        });

        jf_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_info_comint();//提交查询条件
                get_alljfs();//请求数据
                Log.e("LiNing", "mf_monList提交数据===" + mf_monList);

            }
        });
//        lv_query_jf.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
//                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
//                        loadData();
//                    }
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                lastItem = firstVisibleItem + visibleItemCount - 1 ;
//                if(lastItem==totalItemCount){
//                    Toast.makeText(context,"已经是最后一条数据",Toast.LENGTH_LONG).show();
//                }
//            }
//        });
        lv_query_jf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JfMxBean.Data item_jfqty = (JfMxBean.Data) parent.getAdapter().getItem(position);
                String jf_fh_rq = item_jfqty.getPoints_Date().toString();
                String jf_fh_dh = item_jfqty.getVp_No().toString();
                String jf_fh_lydh = item_jfqty.getSource_No().toString();
                jf_fh_jflx = item_jfqty.getPoints_Type().toString();
                String jf_fh_szmc = item_jfqty.getItem_Name().toString();
                String jf_fh_jfms = item_jfqty.getRem().toString();
                String jf_fh_jfbd = item_jfqty.getPoints().toString();
                String jf_fh_jabz = item_jfqty.getCls_Id().toString();
                 jf_fh_sjsdh = item_jfqty.getDepco_Vip().getVip_NO().toString();
                String jf_fh_sjsdhmc = item_jfqty.getDepco_Vip().getVip_Name().toString();
                String jf_fh_sfzh = item_jfqty.getDepco_Vip().getCard_Num().toString();
                String jf_fh_sszt = item_jfqty.getDepco_Vip().getDb_Id().toString();

                //日期转换
                SimpleDateFormat sf1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date  parse = sf1.parse(jf_fh_rq);
                    String format = new SimpleDateFormat("yyyy-MM-dd").format(parse);
                    Log.e("LiNing","时间====xin====="+format);
                    jl_jfrq.setText(format);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                jl_sszt.setText(jf_fh_sszt);
                jl_jfdh.setText(jf_fh_dh);
                jl_sjsdh.setText(jf_fh_sjsdh);
                jl_sfzh.setText(jf_fh_sfzh);
                jl_jflx.setText(jf_fh_jflx);
                jl_szmc.setText(jf_fh_szmc);
                jl_lydh.setText(jf_fh_lydh);
                jl_jfbd.setText(jf_fh_jfbd);
                jl_jfms.setText(jf_fh_jfms);
                jl_jabz.setText(jf_fh_jabz);


            }
        });
        query_ok_cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //传递数据
                alertDialog.dismiss();
                jl_sszt_ib.setEnabled(false);
                jl_sjsdh_ib.setEnabled(false);
                touch.setEnabled(false);
                jl_jabz_ib.setEnabled(false);
                jl_jalx_ib.setEnabled(false);
            }
        });
        query_fh_cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //传递数据
                alertDialog.dismiss();
            }
        });
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setCancelable(true);
        alertDialog.setView(view);
        alertDialog.show();
    }

    private void get_alljfs() {
        Log.e("LiNing", "提交数据===" + comit_zt + comit_time + clientRows);
        if (lv_query_jf.getCount() <= 0) {
            clientRows = "0";
        }
        Log.e("LiNing", "提交数据===" + clientRows);
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("db_Id", comit_zt)
                .add("showRow", "50")
                .add("clientRows", clientRows)
//                .add("clientRows","0")
                .add("vip_NO", comit_sjsdh)
                .add("card_Num", comit_sfzh)
                .add("vp_No", comit_dh)
                .add("points_Type", comit_jflx)
                .add("item_Name", comit_szmc)
                .add("source_No", comit_lydh)
                .add("points_Date", comit_time)
//                .add("dep",comit_dep)//有默认值
                .add("points", comit_jfbd)
                .add("cls_Id", comit_jabz_quy)
                .add("rem", comit_jfms)
                .add("usr_No", user_yh)
                .add("chk_No", user_yh)
                .build();
        Log.e("LiNing", "提交数据=1==" + comit_zt + comit_time + clientRows);
        client.newCall(new Request.Builder().addHeader("cookie", session).post(body)
                .url(jfjl_get).build()).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e("LiNing", "所有收款单===new" + str);
                // 解析包含date的数据必须添加此代码(InputStream型)
                Gson gson = new GsonBuilder().setDateFormat(
                        "yyyy-MM-dd HH:mm:ss").create();
                final JfMxBean jf_all = gson.fromJson(str,
                        JfMxBean.class);
                int sumShowRow = jf_all.getSumShowRow();
                s_zh = String.valueOf(sumShowRow);
                clientRows = s_zh;
                if (jf_all != null) {
                    DesignerJlActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            mf_monList = jf_all.getData();
                            if (mf_monList != null && mf_monList.size() > 0) {

                                qtyAdapter = new JfsQtyAdapter(R.layout.sjsjl_head, mf_monList, context);
                                lv_query_jf.setAdapter(qtyAdapter);
                                qtyAdapter.notifyDataSetChanged();
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

    private void get_info_comint() {
        Log.e("LiNing", "初始化值====" + clientRows);
        if (lv_query_jf.getCount() < 0) {

            clientRows = "0";
        }

        comit_zt = jl_sszt.getText().toString();
        if (comit_zt.equals("")) {
            Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
        }
        comit_time_zb = jl_jfrq.getText().toString();
        if (comit_time_zb.equals("")) {
            comit_time_zb = "null";
        }

        if (!start_quick.getText().toString().equals("") && !stop_quick.getText().toString().equals("")) {
            comit_time = start_quick.getText().toString() + ";" + stop_quick.getText().toString();
        } else {
            comit_time = "null";

        }
        comit_dh = jl_jfdh.getText().toString();
        if (comit_dh.equals("") || comit_dh == null) {
            comit_dh = "null";
        }
        comit_sjsdh = jl_sjsdh.getText().toString();
        if (comit_sjsdh.equals("")) {
            comit_sjsdh = "null";
        }
        comit_sfzh = jl_sfzh.getText().toString();
        if (comit_sfzh.equals("")) {
            comit_sfzh = "null";
        }
        comit_jflx = jl_jflx.getText().toString();
        if (comit_jflx.equals("")) {
            comit_jflx = "null";
        }
        comit_szmc = jl_szmc.getText().toString();
        if (comit_szmc.equals("")) {
            comit_szmc = "null";
        }
        comit_jabz = jl_jabz.getText().toString();
        if (comit_jabz.equals("")) {
            comit_jabz_quy = "null";
        } else {
            if (comit_jabz.equals("是")) {
                comit_jabz_quy = "T";
            }
            if (comit_jabz.equals("否")) {
                comit_jabz_quy = "F";
            }
        }
        comit_jfbd = jl_jfbd.getText().toString();
        if (comit_jfbd.equals("")) {
            comit_jfbd = "null";
        }
        comit_jfms = jl_jfms.getText().toString();
        if (comit_jfms.equals("")) {
            comit_jfms = "null";

        }
        comit_lydh = jl_lydh.getText().toString();
        if (comit_lydh.equals("") || comit_lydh == null) {
            comit_lydh = "null";
        }
        comit_zdr = jl_zdr.getText().toString();
        if (comit_zdr.equals("")) {
            comit_zdr = "null";
        }

        Log.e("LiNing", "获取数据===" + comit_dh + comit_lydh);
    }

    private void delInfo() {
        //获取删除的数据字段
        //oa.ydshce.com:8080/InfManagePlatform/Deco_Vip_Of_PointsdelDeco_Vip_Points.action?db_Id=DB_BJ18
        // &&vip_NO=VP1904020001&&usr_No=ADMIN&&vp_No=VO1904030001&&points_Date=2019-4-3&&card_Num=130728199103286019
        String jfzt_hd = jl_sszt.getText().toString();
        String sjsid_hd = jl_sjsdh.getText().toString();
        if (sjsid_hd.equals("")) {
            Toast.makeText(context, "设计师代号不能为空", Toast.LENGTH_LONG).show();
        } else {
            sjsdh_del = vip_no_cust;
        }
        String jfdh_hd = jl_jfdh.getText().toString();
        String userno_hd = jl_zdr.getText().toString();
        String jfrq_hd = jl_jfrq.getText().toString();
        String sfzh_hd = jl_sfzh.getText().toString();
        if (jfzt_hd.equals("") || sjsid_hd.equals("") ||
                jfdh_hd.equals("") || userno_hd.equals("") ||
                jfrq_hd.equals("") || sfzh_hd.equals("")) {
            Toast.makeText(context, "信息不能为空", Toast.LENGTH_LONG).show();
        } else {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("db_Id", jfzt_hd)
                    .add("vip_NO", sjsdh_del)
                    .add("vp_No", jfdh_hd)
                    .add("user_No", user_Id)
                    .add("card_Num", sfzh_hd)
                    .add("points_Date", jfrq_hd)
                    .build();
            Log.e("LiNing", "删除结果====" + jfzt_hd + "---" + sjsdh_del + "---" + jfdh_hd + "---" + user_Id
                    + "---" + sfzh_hd + "---" + jfrq_hd);
            client.newCall(new Request.Builder().addHeader("cookie", session).url(jfjl_del)
                    .post(body).build()).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String str = response.body().string();
                    Log.e("LiNing", "删除结果====" + str);
                    final JsonjfDel localJsonRootBean = (JsonjfDel) new Gson()
                            .fromJson(str, JsonjfDel.class);
                    if (localJsonRootBean != null) {
                        DesignerJlActivity.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                boolean rlo = localJsonRootBean.isDelResult();
                                if (rlo == true) {
                                    Toast.makeText(DesignerJlActivity.this,
                                            "删除成功", Toast.LENGTH_SHORT).show();
                                    clearInfo();
                                } else if (rlo == false) {
                                    Toast.makeText(DesignerJlActivity.this,
                                            "删除失败", Toast.LENGTH_SHORT).show();
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
    }

    private void getInfos_all() {
        if (!jl_sszt.getText().toString().equals("")) {
            OkHttpClient client_all = new OkHttpClient();
            FormBody body_all = new FormBody.Builder()
                    .add("db_Id", jl_sszt.getText().toString())
                    .build();
            Request request_all = new Request.Builder()
                    .addHeader("cookie", session).url(jfjl_get)
                    .post(body_all)
                    .build();
            client_all.newCall(request_all).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String info_all = response.body().string();
                    Log.e("LiNing", "id_type结果====" + info_all);
                    Gson gson = new GsonBuilder().setDateFormat(
                            "yyyy-MM-dd HH:mm:ss").create();
                    DesignAllInfos designAllInfos = gson.fromJson(info_all, DesignAllInfos.class);
                    Log.e("LiNing", "id_type结果====" + designAllInfos);
                    if (designAllInfos != null) {
                        List<DesignAllInfos.VipList> vipList = designAllInfos.getVipList();
                        card_list = new ArrayList<String>();
                        for (int i = 0; i < vipList.size(); i++) {
                            String card_num = vipList.get(i).getCard_Num();
                            //去除字符串后边的空格
                            String w_null = card_num.replaceAll("\\s+$", "");
                            Log.e("LiNing", "id_type结果====" + card_num);
                            Log.e("LiNing", "id_type结果====" + w_null);
                            card_list.add(w_null);
                        }
                        Log.e("LiNing", "id_type结果====" + card_list);
                        if (response.code() == 200) {

                            save_add();
                        }
                    }
                }

                @Override
                public void onFailure(Call call, IOException e) {

                }
            });
        }
    }

    private void save_add() {
        Log.e("LiNing", "id_type结果====" + card_list);
//        if (card_list.contains(sfzh_comit_jl)) {
//            DesignerJlActivity.this.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(context, "该设计师已存在", Toast.LENGTH_LONG).show();
//                }
//            });
//
//        }  else
        if (sfzh_comit_jl.length() < 18 || sfzh_comit_jl.length() > 20) {
            DesignerJlActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "请添加正确身份证号", Toast.LENGTH_LONG).show();
                }
            });
            //删除数据测试
            //oa.ydshce.com:8080/InfManagePlatform/Deco_Vip_Of_PointsdelDeco_Vip_Points.action?db_Id=DB_BJ18&&vip_NO=VP1904020001&&usr_No=ADMIN&&vp_No=VO1904030001&&points_Date=2019-4-3&&card_Num=130728199103286019&&points_Type=ln&&item_Name=ln&&source_No=123&&points=111&&rem=111&&chk_No=ADMIN
        } else {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("db_Id", zt_comit_jl)
                    .add("vip_NO", sjsdh_comit_jl_id)
//                    .add("vip_NO", "VP1904020002")
                    .add("card_Num", sfzh_comit_jl)
                    .add("vp_No", jfdh_comit_jl)
                    .add("points_Type", jflx_comit_jl)
//                    .add("points_Type", "ln")
                    .add("item_Name", "厂家自登")
                    .add("source_No", lydh_comit_jl)
                    .add("points_Date", jfrq_comit_jl)
                    .add("points", jfbd_comit_jl)
                    .add("rem", jfms_comit_jl)
                    .add("cls_Id", jabz_comit_jl)
                    .add("usr_No", user_yh)
                    .add("chk_No", user_yh)
                    .build();
            Log.e("LiNing", "添加结果====" + zt_comit_jl + "---" + sjsdh_comit_jl_id + "---" + sfzh_comit_jl + "---" + jfdh_comit_jl + "---" + jflx_comit_jl + "---"
                    + szmc_comit_jl + "---" + lydh_comit_jl + "---" + jfrq_comit_jl + "---" + jfbd_comit_jl + "---" + jfms_comit_jl + "---" + jabz_comit_jl + "---" + user_yh);
            client.newCall(
                    new Request.Builder().addHeader("cookie", session).url(jfjl_add)
                            .post(body).build()).enqueue(new Callback() {

                @Override
                public void onResponse(Call call, Response response)
                        throws IOException {
                    String str = response.body().string();
                    Log.e("LiNing", "添加结果====" + str);
                    final Jsonjf localJsonRootBean = (Jsonjf) new Gson()
                            .fromJson(str, Jsonjf.class);
                    if (localJsonRootBean != null) {
                        DesignerJlActivity.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                boolean rlo = localJsonRootBean.isAddResult();
                                if (rlo == true) {
                                    Toast.makeText(DesignerJlActivity.this,
                                            "新增成功", Toast.LENGTH_SHORT).show();
                                    clearInfo();
                                } else if (rlo == false) {
                                    Toast.makeText(DesignerJlActivity.this,
                                            "新增失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call arg0, IOException arg1) {

                }
            });
        }
    }

    private void commitInfos() {
        zt_comit_jl = jl_sszt.getText().toString();
        if (zt_comit_jl.equals("")) {
            Toast.makeText(context, "请添加账套", Toast.LENGTH_LONG).show();
        }
        jfdh_comit_jl = jl_jfdh.getText().toString();
        Log.e("LiNing", "-----" + jfdh_comit_jl);
        if (jfdh_comit_jl.equals("")) {
//            jfdh_comit_jl="VO20190123456";
            Toast.makeText(context, "请添加积分单号", Toast.LENGTH_LONG).show();
        }
        sjsdh_comit_jl = jl_sjsdh.getText().toString();
        Log.e("LiNing", "长度=====" + sjsdh_comit_jl.length());
        if (sjsdh_comit_jl.equals("")) {
            Toast.makeText(context, "请添加设计师代号", Toast.LENGTH_LONG).show();
        } else {
            if(do_design_jl==1){

                sjsdh_comit_jl_id = vip_no_cust;
            }else if(do_design_jl==2){
                sjsdh_comit_jl = jf_fh_sjsdh;
            }
        }
        sfzh_comit_jl = jl_sfzh.getText().toString();
        Log.e("LiNing", "长度=====" + sfzh_comit_jl.length());
        if (sfzh_comit_jl.equals("")) {
            Toast.makeText(context, "请添加正确的身份证号", Toast.LENGTH_LONG).show();
        }

        jflx_comit_jl = jl_jflx.getText().toString();
        if (jflx_comit_jl.equals("")) {
            Toast.makeText(context, "请添加积分类型", Toast.LENGTH_LONG).show();

        } else {
            jflx_comit_jl = jf_id_hd;
        }
        szmc_comit_jl = jl_szmc.getText().toString();
        if (szmc_comit_jl.equals("")) {
            Toast.makeText(context, "请添加收支名称", Toast.LENGTH_LONG).show();
        }
        lydh_comit_jl = jl_lydh.getText().toString();
        if (lydh_comit_jl.equals("")) {
            Toast.makeText(context, "请添加来源单号", Toast.LENGTH_LONG).show();
        }
        jfrq_comit_jl = jl_jfrq.getText().toString();
        if (jfrq_comit_jl.equals("")) {
            Toast.makeText(context, "请添加积分日期", Toast.LENGTH_LONG).show();
        }
        jfms_comit_jl = jl_jfms.getText().toString();
        if (jfms_comit_jl.equals("")) {
            Toast.makeText(context, "请添加积分描述", Toast.LENGTH_LONG).show();
        }
        jabz_comit_jl = jl_jabz.getText().toString();
        if (jabz_comit_jl.equals("")) {
            jabz_comit_jl = "F";
        }else{
            jabz_comit_jl = jl_jabz.getText().toString();
        }
        jfbd_comit_jl = jl_jfbd.getText().toString();
        if (jfbd_comit_jl.equals("")) {
            Toast.makeText(context, "请添加积分变动", Toast.LENGTH_LONG).show();
        }

        zdr_comit_jl = jl_zdr.getText().toString();
        if (do_design_jl != 1) {

            if (zdr_comit_jl.equals("")) {
                Toast.makeText(context, "请添加制单人", Toast.LENGTH_LONG).show();
            }
        }
        shr_comit_jl = jl_shr.getText().toString();
        if (do_design_jl != 1&&do_design_jl != 2) {

            if (shr_comit_jl.equals("")) {
                Toast.makeText(context, "审核用户为空", Toast.LENGTH_LONG).show();
            }
        }
        String sfty_sj = jl_jabz.getText().toString();
        if (sfty_sj.equals("是")) {
            jabz_comit_jl = "T";
        }
        if (sfty_sj.equals("否")) {
            jabz_comit_jl = "F";
        }
    }

    private void clearInfo() {
        jl_sjsdh.setText("");
        jl_jfdh.setText("");
        jl_jflx.setText("");
        jl_jabz.setText("");
        jl_shr.setText("");
        jl_lydh.setText("");
        jl_sfzh.setText("");
        jl_szmc.setText("");
        jl_jfbd.setText("");
        jl_jfms.setText("");

    }

    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.sfhz, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.check1
                        || menuItem.getItemId() == R.id.check2
                        ) {
                    menuItem.setChecked(!menuItem.isChecked());
                    jl_jabz.setText(menuItem.getTitle());
                    Log.e("LiNing", "----" + jl_jabz);

                }
                return true;
            }
        });
        popupMenu.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    String str_id = data.getStringExtra("condition_db");
                    str_name = data.getStringExtra("condition_name");
                    jl_sszt.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + str_name);
                }
                break;
            case 2:
                if (resultCode == 1) {
                    DesignAllInfos.VipList vipList_hd = (DesignAllInfos.VipList) data.getSerializableExtra("VIP_INFOS_ALL");
                    Log.e("LiNing", "提交的id====" + data.getSerializableExtra("VIP_INFOS_ALL"));
                    if (vipList_hd.getVip_Name() != null) {
                        jl_sjsdh.setText(vipList_hd.getVip_Name());
                    } else {
                        jl_sjsdh.setText("null");
                    }
                    if (vipList_hd.getDb_Id() != null) {
                        Log.e("LiNing", "提交的id====" + vipList_hd.getDb_Id().toString());
                        jl_sszt.setText(vipList_hd.getDb_Id().toString());
                    } else {
                        jl_sjsdh.setText(db_zt);
                    }
                    if (vipList_hd.getCard_Num() != null) {
                        jl_sfzh.setText(vipList_hd.getCard_Num().toString());
                    } else {
                        jl_sfzh.setText("");
                    }

                    if (vipList_hd.getVip_NO() != null) {
                        vip_no_cust = vipList_hd.getVip_NO().toString();
                        Log.e("LiNing", "提交的vip_no_cust====" + vip_no_cust);
                    }
                }
                break;
            case 3:
                if (resultCode == 1) {
                    jf_id_hd = data.getStringExtra("jf_ID");
                    String vip_name_hd = data.getStringExtra("jf_NAME");
                    jl_jflx.setText(vip_name_hd);
                    jflx_comit_jl = jf_id_hd;
                    sp.edit().putString("JF_ID_SJS", jf_id_hd).commit();
                    Log.e("LiNing", "提交的id====" + jf_id_hd + vip_name_hd);
                }
                break;
            default:
                break;
        }
    }

    public class JfsQtyAdapter extends BaseAdapter {
        int id_row_layout;
        LayoutInflater mInflater;
        List<JfMxBean.Data> skdqty_infos;
        //item高亮显示
        private int selectItem = -1;

        public void setSelectItem(int selectItem) {
            this.selectItem = selectItem;
        }

        public JfsQtyAdapter(int sjsjl_head, List<JfMxBean.Data> mf_monList, Context context) {
            this.id_row_layout = sjsjl_head;
            this.skdqty_infos = mf_monList;
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return skdqty_infos.size();
        }

        @Override
        public Object getItem(int position) {
            return skdqty_infos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(id_row_layout, null);
                holder = new ViewHolder();
                holder.jf_qty_jfdh = (TextView) convertView.findViewById(R.id.jfquy_jfdh);
                holder.jf_qty_jflx = (TextView) convertView.findViewById(R.id.jfquy_jflx);
                holder.jf_qty_jfrq = (TextView) convertView.findViewById(R.id.jfquy_rq);
                holder.jf_qty_zdr = (TextView) convertView.findViewById(R.id.jfquy_zdr);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            JfMxBean.Data mf_monList_all = skdqty_infos.get(position);
            holder.jf_qty_jfdh.setText(mf_monList_all.getVp_No().toString());
            Log.e("LiNing", "时间====xin=====" + mf_monList_all.getPoints_Date().toString());

            SimpleDateFormat sf1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date  parse = sf1.parse(mf_monList_all.getPoints_Date().toString());
                String format = new SimpleDateFormat("yyyy-MM-dd").format(parse);
                Log.e("LiNing","时间====xin====="+format);
                holder.jf_qty_jfrq.setText(format);
            } catch (ParseException e) {
                e.printStackTrace();
            }

//            if (mf_monList_all.getPoints_Date() != null) {
//
//                Date date = new Date(mf_monList_all.getPoints_Date().toString());
//                SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd");
//                String a1 = dateformat1.format(date);
//                holder.jf_qty_jfrq.setText(a1);
//            }


            holder.jf_qty_jflx.setText(mf_monList_all.getPoints_Type().toString());
            holder.jf_qty_zdr.setText(mf_monList_all.getUsr_No().toString());

            //操作修改，删除等
            if (position == selectItem) {
                convertView.setBackgroundColor(Color.RED);
//                Log.e("LiNing", "id_type结果====" + id_itm);
            } else {
                convertView.setBackgroundColor(Color.TRANSPARENT);
            }
            return convertView;
        }

        class ViewHolder {

            public TextView jf_qty_jfdh;
            public TextView jf_qty_jflx;
            public TextView jf_qty_jfrq;
            public TextView jf_qty_zdr;
        }
    }

    //临时单号
    private void getTicket() {
        String DB_LS = jl_sszt.getText().toString();
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // 获取当前年份
        int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前日
        date_dd = mYear + "-" + mMonth + "-" + mDay;
        if (DB_LS.equals("")) {
            Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
        } else if (date_dd.equals("")) {
            Toast.makeText(context, "请获取时间", Toast.LENGTH_LONG).show();
        } else {

            url_dh_price = URLS.price_num_ls;
            Log.e("LiNing", "临时vip卡号===" + session + "---" + URLS.price_num_ls + "---" + DB_LS + "----" + date_dd);
            OkHttpClient client = new OkHttpClient();
            FormBody localFormBody = new FormBody.Builder()
                    .add("bn_Type", "VO")
                    .add("db_Id", DB_LS)
                    .add("bn_Date", date_dd)
                    .build();
            Request localRequest = new Request.Builder()
                    .addHeader("cookie", session).url(url_dh_price)
                    .post(localFormBody)
                    .build();
            client.newCall(localRequest).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.code() == 200) {

                        String str = response.body().string();
                        Log.e("LiNing", "临时VIP===" + str);
                        final PriceNumID bean_id = (PriceNumID) new Gson()
                                .fromJson(str, PriceNumID.class);
                        if (bean_id != null) {
                            DesignerJlActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    String id_ls = bean_id.getBil_no();
                                    jl_jfdh.setText(id_ls);
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
