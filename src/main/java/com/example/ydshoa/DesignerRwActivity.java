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
import com.example.bean.PriceNumID;
import com.example.bean.RwMxBean;
import com.example.bean.URLS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DesignerRwActivity extends Activity implements View.OnClickListener {
    private Context context;
    private SharedPreferences sp;
    private String session, db_zt,user_Id,user_yh,user_Name,vip_no_cust,sjsdh_del,sp_query, sp_add, sp_del, sp_set;
    private TextView head,rw_sszt,rw_rwrq,rw_sxrq,rw_dqrq,rw_sjsdh,rw_rwdh,rw_zdr,rw_shr,rw_dqzky,rw_sfzh;
    private Button rw_add,rw_del,rw_reset,rw_query,rw_save,rw_sh;
    private EditText rw_mc,rw_rwjf;
    private ImageButton rw_sszt_ib,rw_sjsbh_ib,rw_dqzky_ib;
    private String str_name,date_dd;
    int do_design_rw;
    String zt_comit_rw,jfdh_comit_rw,rwdh_comit_rw,sfzh_comit_rw,sjsbh_comit_rw,rwrq_comit_rw,rwmc_comit_rw,rwjf_comit_rw,sxrq_comit_rw,dqrq_comit_rw,dqzky_comit_rw
            ,zdr_comit_rw,shr_comit_rw;
    //判断新增数据是否存在
    ArrayList<String> card_list;
    LinearLayout touch;
    //接口
    String jfrw_get = URLS.design_jfrwquery_mx;
    String jfrw_add = URLS.design_jfrw_add;
    String jfrw_del = URLS.design_jfrw_del;
    String jfrw_set = URLS.design_jfrw_set;
    String url_dh_price = URLS.price_num_ls;
    //dialog数据
    private Button query_ok_cd, start_quick, stop_quick, rw_get;
    private ImageButton query_fh_cd;
    private ListView lv_query_rw;
    String str_time, stopTime;
    private AlertDialog alertDialog;
    String s_zh, clientRows, comit_zt, comit_time, comit_time_zb, comit_dh, comit_sjsdh, comit_sfzh, comit_jflx, comit_szmc, comit_jabz, comit_jabz_quy,
            comit_zdr, comit_jfbd, comit_lydh, comit_jfms,jf_fh_jflx,jf_fh_sjsdh;
    List<RwMxBean.Data> mf_monList;
    private RwsQtyAdapter qtyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_designer_rw);
        context = DesignerRwActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        user_Id = sp.getString("USER_ID", "");
        user_yh = sp.getString("MR_YH", "");
        user_Name = sp.getString("USER_NAME", "");
        session = sp.getString("SESSION", "");
        //增删改查权限
        sp_query = sp.getString("USER_QUERY","");
        sp_add = sp.getString("USER_ADD","");
        sp_del = sp.getString("USER_DEL", "");
        sp_set = sp.getString("USER_UP", "");
        Log.e("LiNing", "sp数据----" + sp_add + sp_query + sp_del + sp_set);
        getNowTime();
        session = sp.getString("SESSION", "");
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
        head.setText("会员任务单");
        rw_sszt = (TextView) findViewById(R.id.et_designrw_zt);
        rw_sszt.setText(db_zt);
        touch = (LinearLayout) findViewById(R.id.ll_touch_vip);
        touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getTicket();//获取临时单号
            }
        });
        rw_sjsdh = (TextView) findViewById(R.id.et_designrw_sjsbh);
        rw_rwdh = (TextView) findViewById(R.id.tv_rwdh);
        rw_rwrq = (TextView) findViewById(R.id.et_designrw_rwrq);
        rw_sxrq = (TextView) findViewById(R.id.et_designrw_sxrq);
        rw_dqrq = (TextView) findViewById(R.id.et_designrw_dqrq);
        rw_rwrq.setText(date_dd);
        rw_sxrq.setText(date_dd);
        rw_dqrq.setText(date_dd);
        rw_rwrq.setOnClickListener(new View.OnClickListener() {
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
                                rw_rwrq.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        rw_sxrq.setOnClickListener(new View.OnClickListener() {
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
                                rw_sxrq.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        rw_dqrq.setOnClickListener(new View.OnClickListener() {
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
                                rw_dqrq.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        rw_dqzky = (TextView) findViewById(R.id.et_designrw_dqzky);
        rw_zdr = (TextView) findViewById(R.id.et_designrw_zdr);
        rw_zdr.setText(user_Name);
        rw_shr = (TextView) findViewById(R.id.et_designrw_shr);

        rw_sfzh = (TextView) findViewById(R.id.et_designrw_usercard);
        rw_mc = (EditText) findViewById(R.id.et_designrw_rwmc);
        rw_rwjf = (EditText) findViewById(R.id.et_designrw_rwjf);

        rw_sszt_ib= (ImageButton) findViewById(R.id.ib_designrw_account);
        rw_sjsbh_ib= (ImageButton) findViewById(R.id.ib_designrw_sjsbh);
        rw_dqzky_ib= (ImageButton) findViewById(R.id.ib_designrw_dqzky);

        rw_add= (Button) findViewById(R.id.btn_designrw_add);
        rw_del= (Button) findViewById(R.id.btn_designrw_del);
        rw_reset= (Button) findViewById(R.id.btn_designrw_reset);
        rw_query= (Button) findViewById(R.id.btn_designrw_query);
        rw_save= (Button) findViewById(R.id.btn_designrw_save);
        rw_sh= (Button) findViewById(R.id.btn_designrw_sh);
        rw_add.setOnClickListener(this);
        rw_del.setOnClickListener(this);
        rw_reset.setOnClickListener(this);
        rw_query.setOnClickListener(this);
        rw_save.setOnClickListener(this);
        rw_sh.setOnClickListener(this);
        rw_sszt_ib.setOnClickListener(this);
        rw_sjsbh_ib.setOnClickListener(this);
        rw_dqzky_ib.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_designrw_account:
                Intent intent = new Intent(context, CondicionInfoActivity.class);
                intent.putExtra("flag", "1");
                startActivityForResult(intent, 1);
                break;
            case R.id.ib_designrw_sjsbh:
//                设计师
                Intent intent_vip = new Intent(context, QueryDesigActivity.class);
                intent_vip.putExtra("ZT_VIP", rw_sszt.getText().toString());
                intent_vip.putExtra("CUST_DO", "2");
                startActivityForResult(intent_vip, 2);
                break;
            case R.id.ib_designrw_dqzky:
                showPopupMenu(rw_dqzky_ib);
                break;
            case R.id.btn_designrw_add:
                if(sp_add.equals("true")){
                    clearInfo();

                    do_design_rw = 1;
                    rw_zdr.setEnabled(false);
                    rw_shr.setEnabled(false);
                    rw_dqzky.setText("否");
                    rw_dqzky_ib.setEnabled(false);
                }else {
                    Toast.makeText(context, "请等待", Toast.LENGTH_LONG).show();
                }
                clearInfo();

                do_design_rw = 1;
                rw_zdr.setEnabled(false);
                rw_shr.setEnabled(false);
                rw_dqzky.setText("否");
                rw_dqzky_ib.setEnabled(false);

                break;
            case R.id.btn_designrw_del:
                if(sp_del.equals("true")){
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
                }else{
                    Toast.makeText(context, "请等待", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.btn_designrw_reset:
                if(sp_set.equals("true")){
                    do_design_rw = 2;
                    rw_dqzky_ib.setEnabled(true);
                    touch.setEnabled(true);
                    rw_rwdh.setEnabled(false);
                    rw_sfzh.setEnabled(false);
                    rw_rwrq.setEnabled(true);
                    rw_mc.setEnabled(true);
                    rw_rwjf.setEnabled(true);
                    rw_sxrq.setEnabled(true);
                    rw_dqrq.setEnabled(true);
                }else{
                    Toast.makeText(context, "请等待", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.btn_designrw_query:
                if(sp_query.equals("true")){
                    if (rw_sszt.getText().toString().equals("")) {
                        Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                    } else {

                        get_aleartDialog();
                    }
                }else{
                    Toast.makeText(context, "请等待", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.btn_designrw_save:
                if (do_design_rw == 1) {
                    commitInfos();
                    //获取所有数据（主要是判断身份证）
//                    getInfos_all();
                    save_add();

                }
                if (do_design_rw == 2) {
                    commitInfos();//主表信息
                    getinfos_queryToset();//修改
                }
                break;
            case R.id.btn_designrw_sh:
                break;
            default:
                break;
        }

    }

    private void get_aleartDialog() {
        View view = getLayoutInflater()
                .inflate(R.layout.sjsjl_query, null);
        lv_query_rw = (ListView) view.findViewById(R.id.lv_jf_query_header_query);
        query_ok_cd = (Button) view.findViewById(R.id.btn_jf_query_dialogOk);
        query_fh_cd = (ImageButton) view.findViewById(R.id.imageButton1);
        start_quick = (Button) view.findViewById(R.id.btn_start_time);
        stop_quick = (Button) view.findViewById(R.id.btn_stop_time);
        rw_get = (Button) view.findViewById(R.id.search_jf_query);
        view.findViewById(R.id.jfquy_jflx).setVisibility(View.GONE);
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

        rw_get.setOnClickListener(new View.OnClickListener() {
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
        lv_query_rw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RwMxBean.Data item_rwqty = (RwMxBean.Data) parent.getAdapter().getItem(position);
                String jf_fh_rq = item_rwqty.getTask_Date().toString();
                String jf_fh_rqsx = item_rwqty.getBeg_Date().toString();
                String jf_fh_rqdq = item_rwqty.getEnd_Date().toString();
                String jf_fh_dh = item_rwqty.getVt_No().toString();
                String jf_fh_szmc = item_rwqty.getTask_Name().toString();
                String jf_fh_jfbd = item_rwqty.getTask_Points().toString();
                String jf_fh_jabz = item_rwqty.getIsTran().toString();
                jf_fh_sjsdh = item_rwqty.getDepco_Vip().getVip_NO().toString();
                String jf_fh_sjsdhmc = item_rwqty.getDepco_Vip().getVip_Name().toString();
                String jf_fh_sfzh = item_rwqty.getDepco_Vip().getCard_Num().toString();
                String jf_fh_sszt = item_rwqty.getDepco_Vip().getDb_Id().toString();

                //日期转换
                SimpleDateFormat sf1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date  parse = sf1.parse(jf_fh_rq);
                    String format = new SimpleDateFormat("yyyy-MM-dd").format(parse);
                    Log.e("LiNing","时间====xin====="+format);
                    rw_rwrq.setText(format);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //日期转换
                SimpleDateFormat sf1_sx= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date  parse = sf1_sx.parse(jf_fh_rqsx);
                    String format = new SimpleDateFormat("yyyy-MM-dd").format(parse);
                    Log.e("LiNing","时间====xin====="+format);
                    rw_sxrq.setText(format);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //日期转换
                SimpleDateFormat sf1_dq= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date  parse = sf1_dq.parse(jf_fh_rqdq);
                    String format = new SimpleDateFormat("yyyy-MM-dd").format(parse);
                    Log.e("LiNing","时间====xin====="+format);
                    rw_dqrq.setText(format);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                rw_sszt.setText(jf_fh_sszt);
                rw_rwdh.setText(jf_fh_dh);
                rw_sjsdh.setText(jf_fh_sjsdh);
                rw_sfzh.setText(jf_fh_sfzh);
                rw_mc.setText(jf_fh_szmc);
                rw_rwjf.setText(jf_fh_jfbd);
                rw_dqzky.setText(jf_fh_jabz);


            }
        });
        query_ok_cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //传递数据
                alertDialog.dismiss();
                rw_sszt_ib.setEnabled(false);
                rw_sjsbh_ib.setEnabled(false);
                rw_rwdh.setEnabled(false);
                rw_sfzh.setEnabled(false);
                rw_rwrq.setEnabled(false);
                rw_mc.setEnabled(false);
                rw_rwjf.setEnabled(false);
                rw_sxrq.setEnabled(false);
                rw_dqrq.setEnabled(false);
                rw_dqzky_ib.setEnabled(false);
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
        if (lv_query_rw.getCount() <= 0) {
            clientRows = "0";
        }
        Log.e("LiNing", "提交数据===" + clientRows);
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("db_Id", comit_zt)
                .add("showRow", "50")
                .add("clientRows", clientRows)
                .add("task_Date", comit_time)
                .add("vip_NO", comit_sjsdh)
                .add("card_Num", comit_sfzh)
                .add("vt_No", comit_dh)
                .add("task_Name", comit_szmc)
                .add("isTran", comit_jabz_quy)
                .add("usr_No", comit_zdr)
                .add("chk_No", user_yh)
                .build();
        Log.e("LiNing", "提交数据=1==" + comit_zt + comit_time +comit_sjsdh+comit_sfzh+comit_szmc+comit_jabz_quy+ clientRows+comit_zdr+user_yh+user_Id);
        client.newCall(new Request.Builder().addHeader("cookie", session).post(body)
                .url(jfrw_get).build()).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e("LiNing", "所有收款单===new" + str);
                if (str != null && !str.equals("") && !str.equals("null")) {
                // 解析包含date的数据必须添加此代码(InputStream型)
                Gson gson = new GsonBuilder().setDateFormat(
                        "yyyy-MM-dd HH:mm:ss").create();
                final RwMxBean jf_all = gson.fromJson(str,
                        RwMxBean.class);
                int sumShowRow = jf_all.getSumShowRow();
                s_zh = String.valueOf(sumShowRow);
                clientRows = s_zh;
                if (jf_all != null) {
                    DesignerRwActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            mf_monList = jf_all.getData();
                            if (mf_monList != null && mf_monList.size() > 0) {

                                qtyAdapter = new RwsQtyAdapter(R.layout.sjsjl_head, mf_monList, context);
                                lv_query_rw.setAdapter(qtyAdapter);
                                qtyAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }else{
                    DesignerRwActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "无数据", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }


        });
    }
    public class RwsQtyAdapter extends BaseAdapter {
        int id_row_layout;
        LayoutInflater mInflater;
        List<RwMxBean.Data> skdqty_infos;
        //item高亮显示
        private int selectItem = -1;


        public void setSelectItem(int selectItem) {
            this.selectItem = selectItem;
        }

        public RwsQtyAdapter(int sjsjl_head, List<RwMxBean.Data> mf_monList, Context context) {
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
                holder.jf_qty_jflx.setVisibility(View.GONE);
                holder.jf_qty_jfrq = (TextView) convertView.findViewById(R.id.jfquy_rq);
                holder.jf_qty_zdr = (TextView) convertView.findViewById(R.id.jfquy_zdr);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            RwMxBean.Data mf_monList_all = skdqty_infos.get(position);
            holder.jf_qty_jfdh.setText(mf_monList_all.getVt_No().toString());
            Log.e("LiNing", "时间====xin=====" + mf_monList_all.getTask_Date().toString());

            SimpleDateFormat sf1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date  parse = sf1.parse(mf_monList_all.getTask_Date().toString());
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


            holder.jf_qty_jflx.setText("");
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
    private void get_info_comint() {
        Log.e("LiNing", "初始化值====" + clientRows);
        if (lv_query_rw.getCount() < 0) {

            clientRows = "0";
        }

        comit_zt = rw_sszt.getText().toString();
        if (comit_zt.equals("")) {
            Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
        }
        comit_time_zb = rw_rwrq.getText().toString();
        if (comit_time_zb.equals("")) {
            comit_time_zb = "null";
        }

        if (!start_quick.getText().toString().equals("") && !stop_quick.getText().toString().equals("")) {
            comit_time = start_quick.getText().toString() + ";" + stop_quick.getText().toString();
        } else {
            comit_time = "null";

        }
        comit_dh = rw_rwdh.getText().toString();
        if (comit_dh.equals("") || comit_dh == null) {
            comit_dh = "null";
        }
        comit_sjsdh = rw_sjsdh.getText().toString();
        if (comit_sjsdh.equals("")) {
            comit_sjsdh = "null";
        }
        comit_sfzh = rw_sfzh.getText().toString();
        if (comit_sfzh.equals("")) {
            comit_sfzh = "null";
        }
        comit_szmc = rw_mc.getText().toString();
        if (comit_szmc.equals("")) {
            comit_szmc = "null";
        }
        comit_jabz = rw_dqzky.getText().toString();
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
        comit_zdr = rw_zdr.getText().toString();
        if (comit_zdr.equals("")) {
            comit_zdr = "null";
        }else{
            comit_zdr=user_yh;
        }

        Log.e("LiNing", "获取数据===" + comit_dh + comit_lydh);
    }
    private void getInfos_all() {
        if (!rw_sszt.getText().toString().equals("")) {
            OkHttpClient client_all = new OkHttpClient();
            FormBody body_all = new FormBody.Builder()
                    .add("db_Id", rw_sszt.getText().toString())
                    .add("showRow", "50")
                    .add("clientRows", clientRows)
                    .build();
            Request request_all = new Request.Builder()
                    .addHeader("cookie", session).url(jfrw_get)
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
    private void delInfo() {
        //获取删除的数据字段
        //oa.ydshce.com:8080/InfManagePlatform/Deco_Vip_Of_PointsdelDeco_Vip_Points.action?db_Id=DB_BJ18
        // &&vip_NO=VP1904020001&&usr_No=ADMIN&&vp_No=VO1904030001&&points_Date=2019-4-3&&card_Num=130728199103286019
        String jfzt_hd = rw_sszt.getText().toString();
        String sjsid_hd = rw_sjsdh.getText().toString();
        if (sjsid_hd.equals("")) {
            Toast.makeText(context, "设计师代号不能为空", Toast.LENGTH_LONG).show();
        } else {
            sjsdh_del = jf_fh_sjsdh;
        }
        String jfdh_hd = rw_rwdh.getText().toString();
        String userno_hd = rw_zdr.getText().toString();
        String jfrq_hd = rw_rwrq.getText().toString();
        String sfzh_hd = rw_sfzh.getText().toString();
        if (jfzt_hd.equals("") || sjsid_hd.equals("") ||
                jfdh_hd.equals("") || userno_hd.equals("") ||
                jfrq_hd.equals("") || sfzh_hd.equals("")) {
            Toast.makeText(context, "信息不能为空", Toast.LENGTH_LONG).show();
        } else {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("db_Id", jfzt_hd)
                    .add("vip_NO", sjsdh_del)
                    .add("vt_No", jfdh_hd)
                    .add("card_Num", sfzh_hd)
                    .add("task_Date", jfrq_hd)
                    .build();
            Log.e("LiNing", "删除结果====" + jfzt_hd + "---" + sjsdh_del + "---" + jfdh_hd + "---" + user_Id
                    + "---" + sfzh_hd + "---" + jfrq_hd);
            client.newCall(new Request.Builder().addHeader("cookie", session).url(jfrw_del)
                    .post(body).build()).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String str = response.body().string();
                    Log.e("LiNing", "删除结果====" + str);
                    final Jsonjf localJsonRootBean = (Jsonjf) new Gson()
                            .fromJson(str, Jsonjf.class);
                    if (localJsonRootBean != null) {
                        DesignerRwActivity.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                boolean rlo = localJsonRootBean.isAddResult();
                                if (rlo == true) {
                                    Toast.makeText(DesignerRwActivity.this,
                                            "删除成功", Toast.LENGTH_SHORT).show();
                                    clearInfo();
                                    qtyAdapter.notifyDataSetChanged();
                                } else if (rlo == false) {
                                    Toast.makeText(DesignerRwActivity.this,
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
    private void save_add() {
        Log.e("LiNing", "id_type结果====" + card_list);
//        if (card_list.contains(sfzh_comit_rw)) {
//            DesignerRwActivity.this.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(context, "该设计师已存在", Toast.LENGTH_LONG).show();
//                }
//            });
//
//        }  else if (sfzh_comit_rw.length() < 18 || sfzh_comit_rw.length() > 20) {
//            DesignerRwActivity.this.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(context, "请添加正确身份证号", Toast.LENGTH_LONG).show();
//                }
//            });
            if (sfzh_comit_rw.length() < 18 || sfzh_comit_rw.length() > 20) {
                DesignerRwActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "请添加正确身份证号", Toast.LENGTH_LONG).show();
                    }
                });

        } else {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("db_Id", zt_comit_rw)
                    .add("vip_NO", sjsbh_comit_rw)
                    .add("card_Num", sfzh_comit_rw)
                    .add("vt_No", rwdh_comit_rw)
                    .add("task_Name", rwmc_comit_rw)
                    .add("task_Points", rwjf_comit_rw)
                    .add("task_Date", rwrq_comit_rw)
                    .add("beg_Date", sxrq_comit_rw)
                    .add("end_Date", dqrq_comit_rw)
                    .add("isTran", dqzky_comit_rw)
                    .add("usr_No", user_yh)
                    .add("chk_No", user_yh)
                    .build();
            Log.e("LiNing", "添加结果====" + zt_comit_rw + "---" + sjsbh_comit_rw + "---" + sfzh_comit_rw + "---" + rwdh_comit_rw + "---" + rwmc_comit_rw + "---"
                    + rwjf_comit_rw + "---" + rwrq_comit_rw + "---" + sxrq_comit_rw + "---" + dqrq_comit_rw + "---" + dqzky_comit_rw + "---" + user_yh  );
            client.newCall(
                    new Request.Builder().addHeader("cookie", session).url(jfrw_add)
                            .post(body).build()).enqueue(new Callback() {

                @Override
                public void onResponse(Call call, Response response)
                        throws IOException {
                    String str = response.body().string();
                    Log.e("LiNing", "添加结果====" + str);
                    final Jsonjf localJsonRootBean = (Jsonjf) new Gson()
                            .fromJson(str, Jsonjf.class);
                    if (localJsonRootBean != null) {
                        DesignerRwActivity.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                boolean rlo = localJsonRootBean.isAddResult();
                                if (rlo == true) {
                                    Toast.makeText(DesignerRwActivity.this,
                                            "新增成功", Toast.LENGTH_SHORT).show();
                                    clearInfo();
                                } else if (rlo == false) {
                                    Toast.makeText(DesignerRwActivity.this,
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
    private void getinfos_queryToset() {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("db_Id", zt_comit_rw)
                .add("vip_NO", jf_fh_sjsdh)
                .add("card_Num", sfzh_comit_rw)
                .add("vt_No", rwdh_comit_rw)
                .add("task_Name", rwmc_comit_rw)
                .add("task_Points", rwjf_comit_rw)
                .add("task_Date", rwrq_comit_rw)
                .add("beg_Date", sxrq_comit_rw)
                .add("end_Date", dqrq_comit_rw)
                .add("isTran", dqzky_comit_rw)
                .add("usr_No", user_yh)
                .add("chk_No", user_yh)
                .build();
        Log.e("LiNing", "添加结果====" + zt_comit_rw + "---" + sjsbh_comit_rw + "---" + sfzh_comit_rw + "---" + rwdh_comit_rw + "---" + rwmc_comit_rw + "---"
                + rwjf_comit_rw + "---" + rwrq_comit_rw + "---" + sxrq_comit_rw + "---" + dqrq_comit_rw + "---" + dqzky_comit_rw + "---" + user_yh  );
        client.newCall(
                new Request.Builder().addHeader("cookie", session).url(jfrw_set)
                        .post(body).build()).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                String str = response.body().string();
                Log.e("LiNing", "修改结果====" + str);
                final Jsonjf localJsonRootBean = (Jsonjf) new Gson()
                        .fromJson(str, Jsonjf.class);
                if (localJsonRootBean != null) {
                    DesignerRwActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            String rlo = localJsonRootBean.getSetResult().toString();
                            if (!rlo .equals("")) {
                                Toast.makeText(DesignerRwActivity.this,
                                        "修改成功", Toast.LENGTH_SHORT).show();
                                clearInfo();
                            } else {
                                Toast.makeText(DesignerRwActivity.this,
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
    private void commitInfos() {
        zt_comit_rw = rw_sszt.getText().toString();
        if (zt_comit_rw.equals("")) {
            Toast.makeText(context, "请添加账套", Toast.LENGTH_LONG).show();
        }
        rwdh_comit_rw = rw_rwdh.getText().toString();
        Log.e("LiNing", "-----" + jfdh_comit_rw);
        if (rwdh_comit_rw.equals("")) {
            Toast.makeText(context, "请添加任务单号", Toast.LENGTH_LONG).show();
        }
        sjsbh_comit_rw = rw_sjsdh.getText().toString();
        Log.e("LiNing", "长度=====" + sjsbh_comit_rw.length());
        if (sjsbh_comit_rw.equals("")) {
            Toast.makeText(context, "请添加设计师代号", Toast.LENGTH_LONG).show();
        } else {
            if(do_design_rw==1){

                sjsbh_comit_rw = vip_no_cust;
            }else if(do_design_rw==2){
                sjsbh_comit_rw = jf_fh_sjsdh;
            }
        }
        sfzh_comit_rw = rw_sfzh.getText().toString();
        Log.e("LiNing", "长度=====" + sfzh_comit_rw.length());
        if (sfzh_comit_rw.equals("")) {
            Toast.makeText(context, "请添加正确的身份证号", Toast.LENGTH_LONG).show();
        }
        rwrq_comit_rw = rw_rwrq.getText().toString();
        if (rwrq_comit_rw.equals("")) {
            Toast.makeText(context, "请添加任务日期", Toast.LENGTH_LONG).show();
        }
        rwmc_comit_rw = rw_mc.getText().toString();
        if (rwmc_comit_rw.equals("")) {
            Toast.makeText(context, "请添加任务名称", Toast.LENGTH_LONG).show();
        }
        rwjf_comit_rw = rw_rwjf.getText().toString();
        if (rwjf_comit_rw.equals("")) {
            Toast.makeText(context, "请添加任务积分", Toast.LENGTH_LONG).show();
        }
        sxrq_comit_rw = rw_sxrq.getText().toString();
        if (sxrq_comit_rw.equals("")) {
            Toast.makeText(context, "请添加生效日期", Toast.LENGTH_LONG).show();
        }

        dqrq_comit_rw = rw_dqrq.getText().toString();
        if (dqrq_comit_rw.equals("")) {
            Toast.makeText(context, "请添加到期日期", Toast.LENGTH_LONG).show();
        }


        zdr_comit_rw = rw_zdr.getText().toString();
        if (do_design_rw != 1) {

            if (zdr_comit_rw.equals("")) {
                Toast.makeText(context, "请添加制单人", Toast.LENGTH_LONG).show();
            }
        }
        shr_comit_rw = rw_shr.getText().toString();
//        if (do_design_rw != 1) {
//
//            if (shr_comit_rw.equals("")) {
//                Toast.makeText(context, "审核用户为空", Toast.LENGTH_LONG).show();
//            }
//        }
        dqzky_comit_rw = rw_dqzky.getText().toString();
        if (dqzky_comit_rw.equals("")) {
            dqzky_comit_rw = "F";
        }else{
            dqzky_comit_rw = rw_dqzky.getText().toString();
        }
        String sfty_sj = rw_dqzky.getText().toString();
        if (sfty_sj.equals("是")) {
            dqzky_comit_rw = "T";
        }
        if (sfty_sj.equals("否")) {
            dqzky_comit_rw = "F";
        }
    }
    private void clearInfo() {
        rw_rwdh.setText("");
        rw_sjsdh.setText("");
        rw_sfzh.setText("");
        rw_mc.setText("");
        rw_rwjf.setText("");
        rw_shr.setText("");

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
                    rw_dqzky.setText(menuItem.getTitle());
                    Log.e("LiNing", "----" + rw_dqzky);

                }
                return true;
            }
        });
        popupMenu.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode==1){
                    String str_id = data.getStringExtra("condition_db");
                    str_name = data.getStringExtra("condition_name");
                    rw_sszt.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + str_name);
                }
                break;
            case 2:
                if (resultCode == 1) {
                    DesignAllInfos.VipList vipList_hd = (DesignAllInfos.VipList) data.getSerializableExtra("VIP_INFOS_ALL");
                    Log.e("LiNing", "提交的id====" + data.getSerializableExtra("VIP_INFOS_ALL"));
                    if (vipList_hd.getVip_Name() != null) {
                        rw_sjsdh.setText(vipList_hd.getVip_Name());
                    } else {
                        rw_sjsdh.setText("null");
                    }
                    if (vipList_hd.getDb_Id() != null) {
                        Log.e("LiNing", "提交的id====" + vipList_hd.getDb_Id().toString());
                        rw_sszt.setText(vipList_hd.getDb_Id().toString());
                    } else {
                        rw_sjsdh.setText(db_zt);
                    }
                    if (vipList_hd.getCard_Num() != null) {
                        rw_sfzh.setText(vipList_hd.getCard_Num().toString());
                    } else {
                        rw_sfzh.setText("");
                    }

                    if (vipList_hd.getVip_NO() != null) {
                        vip_no_cust = vipList_hd.getVip_NO().toString();
                        Log.e("LiNing", "提交的vip_no_cust====" + vip_no_cust);
                    }
                }
                break;
                default:
                    break;
        }
    }
    //临时单号
    private void getTicket() {
        String DB_LS = rw_sszt.getText().toString();
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
                            DesignerRwActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    String id_ls = bean_id.getBil_no();
                                    rw_rwdh.setText(id_ls);
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
