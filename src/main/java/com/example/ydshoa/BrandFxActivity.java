package com.example.ydshoa;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.BrandSame;
import com.example.bean.SaleMakeInfo;
import com.example.bean.TbSales;
import com.example.bean.URLS;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BrandFxActivity extends Activity implements View.OnClickListener {
    Context context;
    public String session;
    private SharedPreferences sp;
    private TextView head_fx, fx_time_name_dq, fx_time_name_db,head_name1,head_name2;
    private Button fx_jz, fx_zc, fx_dq_kstime, fx_db_kstime, fx_dq_jstime, fx_db_jstime;
    private ArrayList<String> querryDBs, querryDBs_all, bilNos, custNos,
            custNames, custPhones, custAdress, inputNos, cust_os, rems,
            prdMarks, query_deps, query_sups, query_custs, query_users,
            query_deps_all, query_sups_all, query_custs_all, query_users_all,
            chk_users, areas, query_hs, query_hs_all, employees, prdNos,
            prdIndexs, prdWhs, zts, zcs, dys, cbs, mls, mlls, jes, cbs_dj,
            mls_dj, mlls_dj, jes_dj;
    private String sub_querys_db, hq_kssj_dq, hq_jssj_dq, hq_kssj_db, hq_jssj_db;
    private FormBody body;
    private List<SaleMakeInfo.Data> data, data_db;
    String url = URLS.sale_url;
    SalesInfoDbAdapter sfadapter_db;
    private ListView lv_get_all;
    public static List<TbSales> info_out = new ArrayList<TbSales>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_brand_fx);
        context = BrandFxActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        initView();
    }

    private void initView() {
        head_fx = (TextView) findViewById(R.id.all_head);
        head_name1 = (TextView) findViewById(R.id.tb_zl_tv_name);
        head_name2 = (TextView) findViewById(R.id.tb_zl_tv_name_db);
//        head_name1.setText("品牌");
        head_name2.setText("品牌");
        head_fx.setText("经营品牌销售分析表");
        fx_jz = (Button) findViewById(R.id.btn_fx_jz);//加载
        fx_zc = (Button) findViewById(R.id.btn_fx_zc);//转出
        fx_time_name_dq = (TextView) findViewById(R.id.tv_fx_dqsj);//当前时间
        fx_time_name_db = (TextView) findViewById(R.id.tv_fx_dbsj);//对比时间
        fx_dq_kstime = (Button) findViewById(R.id.btn_start_time);//开始时间1
        fx_dq_jstime = (Button) findViewById(R.id.btn_stop_time);//结束时间1
        fx_db_kstime = (Button) findViewById(R.id.btn_start_time_tb);//开始时间1
        fx_db_jstime = (Button) findViewById(R.id.btn_stop_time_tb);//结束时间2
        lv_get_all = (ListView) findViewById(R.id.lv_fx_pp);//结束时间2

        fx_jz.setOnClickListener(this);
        fx_zc.setOnClickListener(this);
        fx_dq_kstime.setOnClickListener(this);
        fx_dq_jstime.setOnClickListener(this);
        fx_db_kstime.setOnClickListener(this);
        fx_db_jstime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //转出
            case R.id.btn_fx_zc:
                if(info_out!=null&&info_out.size()>0){

                    Intent intent2 = new Intent(context, BrandOutInfoActivity.class);
//                intent2.putExtra("Stime", start.getText().toString());
                    Log.e("LiNing", "传递数据====" + info_out.size() + "===" + info_out);
                    startActivity(intent2);
                }else{
                    Toast.makeText(context, "请加载转出数据", Toast.LENGTH_LONG).show();
                }
                break;
            //加载
            case R.id.btn_fx_jz:
                getAllQuery();// 获取查询数据（all）
                break;
            case R.id.btn_start_time:
                Calendar c_star = Calendar.getInstance();
                int mYear_s = c_star.get(Calendar.YEAR); // 获取当前年份
                new DatePickerDialog(context, DatePickerDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                hq_kssj_dq = String.format("%d-%d-%d", year,
                                        monthOfYear + 1, dayOfMonth);
                                fx_dq_kstime.setText(hq_kssj_dq);

                            }
                        }, mYear_s, 0, 1).show();
                break;
            case R.id.btn_stop_time:
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
                                hq_jssj_dq = String.format("%d-%d-%d", year,
                                        monthOfYear + 1, dayOfMonth);
                                fx_dq_jstime.setText(hq_jssj_dq);

                            }
                        }, mYear, mMonth, mDay).show();//获取当前时间
                break;
            case R.id.btn_start_time_tb:
                Calendar c_star_db = Calendar.getInstance();
                int mYear_s_db = c_star_db.get(Calendar.YEAR); // 获取当前年份
                new DatePickerDialog(context, DatePickerDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                hq_kssj_db = String.format("%d-%d-%d", year,
                                        monthOfYear + 1, dayOfMonth);
                                fx_db_kstime.setText(hq_kssj_db);

                            }
                        }, mYear_s_db, 0, 1).show();
                break;
            case R.id.btn_stop_time_tb:
                Calendar c_db = Calendar.getInstance();
                int mYear_db = c_db.get(Calendar.YEAR); // 获取当前年份
                int mMonth_db = c_db.get(Calendar.MONTH);// 获取当前月份
                int mDay_db = c_db.get(Calendar.DAY_OF_MONTH);// 获取当日期cal.set(Calendar.DAY_OF_MONTH, 1);
                Log.e("LiNing", "y" + mYear_db + "/y" + mMonth_db + "/r" + mDay_db);
                new DatePickerDialog(context, DatePickerDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                hq_jssj_db = String.format("%d-%d-%d", year,
                                        monthOfYear + 1, dayOfMonth);
                                fx_db_jstime.setText(hq_jssj_db);

                            }
                        }, mYear_db, mMonth_db, mDay_db).show();//获取当前时间
                break;
            default:
                break;
        }
    }

    private void getAllQuery() {
        String all_info = sp.getString("all_query", "");
        Log.e("LiNing", "--更新数据--" + all_info);
        if (all_info != null && !all_info.equals("")) {
            querryDBs_all = new ArrayList<String>();
            query_hs_all = new ArrayList<String>();
            query_deps_all = new ArrayList<String>();
            query_sups_all = new ArrayList<String>();
            query_custs_all = new ArrayList<String>();
            query_users_all = new ArrayList<String>();

            try {
                JSONArray arr = new JSONArray(all_info);
                int size = arr.length();
                for (int i = 0; i < size; i++) {
                    JSONObject jsonObject = arr.getJSONObject(i);
                    String db_ids = jsonObject.get("query_DB").toString();
                    if (db_ids.equals("") || db_ids.equals("null")
                            || db_ids.equals("All")) {
                        querryDBs_all.add("All");
                    } else {
                        querryDBs_all.add(db_ids);
                    }
                    String queryHs = jsonObject.get("query_CompDep").toString();
                    if (queryHs.equals("") || queryHs.equals("null")
                            || queryHs.equals("All")) {
                        query_hs_all.add("All");
                    } else {
                        query_hs_all.add(queryHs);
                    }
                    String queryDep = jsonObject.get("query_Dep").toString();
                    if (queryDep.equals("") || queryDep.equals("null")
                            || queryDep.equals("All")) {
                        query_deps_all.add("All");
                    } else {
                        query_deps_all.add(queryDep);
                    }
                    String querySup = jsonObject.get("query_Sup").toString();
                    if (querySup.equals("") || querySup.equals("null")
                            || querySup.equals("All")) {
                        query_sups_all.add("All");
                    } else {
                        query_sups_all.add(querySup);
                    }
                    String queryCust = jsonObject.get("query_Cust").toString();
                    if (queryCust.equals("") || queryCust.equals("null")
                            || queryCust.equals("All")) {
                        query_custs_all.add("All");
                    } else {
                        query_custs_all.add(queryCust);
                    }
                    String queryUser = jsonObject.get("query_User").toString();
                    if (queryUser.equals("") || queryUser.equals("null")
                            || queryUser.equals("All")) {
                        query_users_all.add("All");
                    } else {
                        query_users_all.add(queryUser);
                    }
                }
            } catch (JSONException e) {

                e.printStackTrace();
            }
            postNoInfo();

        }
    }

    private void postNoInfo() {
        String querys_db_str = "";// 此处解决数据重复出现（不能放出去）
        for (String querys_db : querryDBs_all) {
            querys_db_str += querys_db + ",";
        }
        String sub_querys_db_all = querys_db_str.substring(0,
                querys_db_str.length() - 1);
        Log.e("LiNing", "======" + sub_querys_db);
        String query_hss_str = "";
        for (String query_hss_get : query_hs_all) {
            query_hss_str += query_hss_get + ";";
        }
        String sub_query_hss_all = query_hss_str.substring(0,
                query_hss_str.length() - 1);
        String query_deps_str = "";
        for (String query_deps_get : query_deps_all) {
            query_deps_str += query_deps_get + ";";
        }
        String sub_query_deps_all = query_deps_str.substring(0,
                query_deps_str.length() - 1);
        String query_sups_str = "";
        for (String query_sups_get : query_sups_all) {
            query_sups_str += query_sups_get + ";";
        }
        String sub_query_sups_all = query_sups_str.substring(0,
                query_sups_str.length() - 1);
        String query_custs_str = "";
        for (String query_custs_get : query_custs_all) {
            query_custs_str += query_custs_get + ";";
        }
        String sub_query_custs_all = query_custs_str.substring(0,
                query_custs_str.length() - 1);
        String query_users_str = "";
        for (String query_users_get : query_users_all) {
            query_users_str += query_users_get + ";";
        }
        String sub_query_users_all = query_users_str.substring(0,
                query_users_str.length() - 1);
        // 此处的数据为全部（同步查询）
        // http://oa.ydshce.com:8080/InfManagePlatform/Report.action%EF%BC%9FreprotNo=SATGS&reporyBusiness=SA&query_DB=DB_BJ18
        OkHttpClient client = new OkHttpClient();
        body = new FormBody.Builder()
                .add("reprotNo", "SATGP")
                .add("beginDate", fx_dq_kstime.getText().toString())
                .add("endDate", fx_dq_jstime.getText().toString())
                .add("reporyBusiness", "SA")
                .add("isGroupSum", "T")
                .add("query_DB", sub_querys_db_all).add("bilNo", "")
                .add("custNo", "").add("custName", "").add("custPhone", "")
                .add("custAddress", "").add("inputNo", "")
                .add("cust_OS_NO", "").add("rem", "").add("prdMark", "")
                .add("query_CompDep", sub_query_hss_all)
                .add("query_Dep", sub_query_deps_all)
                .add("query_Sup", sub_query_sups_all)
                .add("query_Cust", sub_query_custs_all)
                .add("query_User", sub_query_users_all).add("chk_User", "ALL")
                .add("area", "ALL").add("employee", "ALL").add("prdNO", "ALL")
                .add("prdIndx", "ALL").add("prdWh", "ALL").build();
        Request request = new Request.Builder().addHeader("cookie", session)
                .url(url).post(body).build();
//        Log.e("LiNing", "-----" + reportnos+"---"+start.getText().toString()
//                +"---"+stop.getText().toString()+"---"+reportBus+"---"+arp_sum
//                +"---"+sub_querys_db_all+"---"+sub_query_hss_all+"---"+sub_query_deps_all+"---"+sub_query_sups_all
//                +"---"+sub_query_custs_all+"---"+sub_query_users_all
//        );
        Log.e("LiNing", "-----" + sub_querys_db_all + sub_query_deps_all);
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {

                String str_all = response.body().string();

                Log.e("LiNing", "-----" + str_all);
                Log.e("LiNing", "-----" + response.code());
                if (str_all != null) {
                    BrandFxActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                        }
                    });
                } else {
                    Toast.makeText(BrandFxActivity.this, "无数据。。。", Toast.LENGTH_LONG)
                            .show();
                }
                final SaleMakeInfo aDb1 = new Gson().fromJson(str_all,
                        SaleMakeInfo.class);
                if (aDb1 != null) {
                    BrandFxActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            data = aDb1.getData();
                            Log.e("LiNing", "========" + data.size());
                            two_loadInfos();
                        }

                    });
                }

            }

            @Override
            public void onFailure(Call arg0, IOException arg1) {

            }
        });
    }

    private void two_loadInfos() {
        String all_info = sp.getString("all_query", "");
        Log.e("LiNing", "--更新数据--" + all_info);
        if (all_info != null && !all_info.equals("")) {
            querryDBs_all = new ArrayList<String>();
            query_hs_all = new ArrayList<String>();
            query_deps_all = new ArrayList<String>();
            query_sups_all = new ArrayList<String>();
            query_custs_all = new ArrayList<String>();
            query_users_all = new ArrayList<String>();

            try {
                JSONArray arr = new JSONArray(all_info);
                int size = arr.length();
                for (int i = 0; i < size; i++) {
                    JSONObject jsonObject = arr.getJSONObject(i);
                    String db_ids = jsonObject.get("query_DB").toString();
                    if (db_ids.equals("") || db_ids.equals("null")
                            || db_ids.equals("All")) {
                        querryDBs_all.add("All");
                    } else {
                        querryDBs_all.add(db_ids);
                    }
                    String queryHs = jsonObject.get("query_CompDep").toString();
                    if (queryHs.equals("") || queryHs.equals("null")
                            || queryHs.equals("All")) {
                        query_hs_all.add("All");
                    } else {
                        query_hs_all.add(queryHs);
                    }
                    String queryDep = jsonObject.get("query_Dep").toString();
                    if (queryDep.equals("") || queryDep.equals("null")
                            || queryDep.equals("All")) {
                        query_deps_all.add("All");
                    } else {
                        query_deps_all.add(queryDep);
                    }
                    String querySup = jsonObject.get("query_Sup").toString();
                    if (querySup.equals("") || querySup.equals("null")
                            || querySup.equals("All")) {
                        query_sups_all.add("All");
                    } else {
                        query_sups_all.add(querySup);
                    }
                    String queryCust = jsonObject.get("query_Cust").toString();
                    if (queryCust.equals("") || queryCust.equals("null")
                            || queryCust.equals("All")) {
                        query_custs_all.add("All");
                    } else {
                        query_custs_all.add(queryCust);
                    }
                    String queryUser = jsonObject.get("query_User").toString();
                    if (queryUser.equals("") || queryUser.equals("null")
                            || queryUser.equals("All")) {
                        query_users_all.add("All");
                    } else {
                        query_users_all.add(queryUser);
                    }
                }
            } catch (JSONException e) {

                e.printStackTrace();
            }
            postNoInfo_db();
        }
    }

    private void postNoInfo_db() {
        String querys_db_str = "";// 此处解决数据重复出现（不能放出去）
        for (String querys_db : querryDBs_all) {
            querys_db_str += querys_db + ",";
        }
        String sub_querys_db_all = querys_db_str.substring(0,
                querys_db_str.length() - 1);
        Log.e("LiNing", "======" + sub_querys_db);
        String query_hss_str = "";
        for (String query_hss_get : query_hs_all) {
            query_hss_str += query_hss_get + ";";
        }
        String sub_query_hss_all = query_hss_str.substring(0,
                query_hss_str.length() - 1);
        String query_deps_str = "";
        for (String query_deps_get : query_deps_all) {
            query_deps_str += query_deps_get + ";";
        }
        String sub_query_deps_all = query_deps_str.substring(0,
                query_deps_str.length() - 1);
        String query_sups_str = "";
        for (String query_sups_get : query_sups_all) {
            query_sups_str += query_sups_get + ";";
        }
        String sub_query_sups_all = query_sups_str.substring(0,
                query_sups_str.length() - 1);
        String query_custs_str = "";
        for (String query_custs_get : query_custs_all) {
            query_custs_str += query_custs_get + ";";
        }
        String sub_query_custs_all = query_custs_str.substring(0,
                query_custs_str.length() - 1);
        String query_users_str = "";
        for (String query_users_get : query_users_all) {
            query_users_str += query_users_get + ";";
        }
        String sub_query_users_all = query_users_str.substring(0,
                query_users_str.length() - 1);
        // 此处的数据为全部（同步查询）
        // http://oa.ydshce.com:8080/InfManagePlatform/Report.action%EF%BC%9FreprotNo=SATGS&reporyBusiness=SA&query_DB=DB_BJ18
        OkHttpClient client = new OkHttpClient();
        body = new FormBody.Builder()
                .add("reprotNo", "SATGP")
                .add("beginDate", fx_db_kstime.getText().toString())
                .add("endDate", fx_db_jstime.getText().toString())
                .add("reporyBusiness", "SA")
                .add("isGroupSum", "T")
                .add("query_DB", sub_querys_db_all).add("bilNo", "")
                .add("custNo", "").add("custName", "").add("custPhone", "")
                .add("custAddress", "").add("inputNo", "")
                .add("cust_OS_NO", "").add("rem", "").add("prdMark", "")
                .add("query_CompDep", sub_query_hss_all)
                .add("query_Dep", sub_query_deps_all)
                .add("query_Sup", sub_query_sups_all)
                .add("query_Cust", sub_query_custs_all)
                .add("query_User", sub_query_users_all).add("chk_User", "ALL")
                .add("area", "ALL").add("employee", "ALL").add("prdNO", "ALL")
                .add("prdIndx", "ALL").add("prdWh", "ALL").build();
        Request request = new Request.Builder().addHeader("cookie", session)
                .url(url).post(body).build();
//        Log.e("LiNing", "-----" + reportnos+"---"+start.getText().toString()
//                +"---"+stop.getText().toString()+"---"+reportBus+"---"+arp_sum
//                +"---"+sub_querys_db_all+"---"+sub_query_hss_all+"---"+sub_query_deps_all+"---"+sub_query_sups_all
//                +"---"+sub_query_custs_all+"---"+sub_query_users_all
//        );
        Log.e("LiNing", "-----" + sub_querys_db_all + sub_query_deps_all);
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {

                String str_all = response.body().string();

                Log.e("LiNing", "-----" + str_all);
                Log.e("LiNing", "-----" + response.code());
                if (str_all != null) {
                    BrandFxActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                        }
                    });
                } else {
                    Toast.makeText(BrandFxActivity.this, "无数据。。。", Toast.LENGTH_LONG)
                            .show();
                }
                final SaleMakeInfo aDb1_db = new Gson().fromJson(str_all,
                        SaleMakeInfo.class);
                if (aDb1_db != null) {
                    BrandFxActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            data_db = aDb1_db.getData();
                            Log.e("LiNing", "========" + data_db.size());
                            fx_time_name_dq.setText(hq_kssj_dq + "/" + hq_jssj_dq);
                            fx_time_name_db.setText(hq_kssj_db + "/" + hq_jssj_db);
                            compareInfos();
                        }

                    });
                }

            }

            @Override
            public void onFailure(Call arg0, IOException arg1) {

            }
        });
    }

    private ArrayList<String> names;
    private ArrayList<String> names_2;
    String s_pp_db, s_jxse, s_ml, s_mll;
    private List<List<TbSales>> tb_datas_new;
    private List<TbSales> tb_data1;
    private List<TbSales> tb_data2;
    private List<TbSales> tb_data3;
    private List<TbSales> tb_data_zj;
    private List<BrandSame> all_data_db, same_data_db, same_data_db_all, dif_data_db_all,same_data_db_zj,dif_data_db;
    private List<BrandSame> all_data, same_data, same_data_all, dif_data_all,same_data_zj,dif_data;
    String zj_pp_m,zj_jxse_m,zj_ml_m,zj_mll_m;

    private void compareInfos() {
        names = new ArrayList<String>();
        names_2 = new ArrayList<String>();
        tb_data1 = new ArrayList<TbSales>();
        tb_data2 = new ArrayList<TbSales>();
        tb_data3 = new ArrayList<TbSales>();
        tb_data_zj = new ArrayList<TbSales>();
        tb_datas_new = new ArrayList<>();
        same_data_db = new ArrayList<BrandSame>();
        same_data_db_all = new ArrayList<BrandSame>();
        dif_data_db_all = new ArrayList<BrandSame>();
        dif_data_db = new ArrayList<BrandSame>();
        all_data_db = new ArrayList<BrandSame>();
        same_data = new ArrayList<BrandSame>();
        dif_data = new ArrayList<BrandSame>();
        same_data_all = new ArrayList<BrandSame>();
        dif_data_all = new ArrayList<BrandSame>();
        all_data = new ArrayList<BrandSame>();
        same_data_db_zj = new ArrayList<BrandSame>();
        same_data_zj = new ArrayList<BrandSame>();
        if (data != null && data.size() > 0) {
            Log.e("LiNing", "data数据---1-----" + data.size() + "-----" + data);
            //数据整合
            bl_sales_names();
        }
        if (data_db != null && data_db.size() > 0) {
            Log.e("LiNing", "data_db数据---2-----" + data_db.size() + "-----" + data_db);
            //数据整合
            bl_sales_names_db();

        }
        if(same_data_db_all!=null&&same_data_all!=null){
            for (int i=0;i<same_data_db_all.size();i++){
                if (same_data_db_all.get(i).getName() != null && !same_data_db_all.get(i).getName().equals("")) {

                    s_pp_db = same_data_db_all.get(i).getName().toString();
                    names_2.add(s_pp_db);
                }
            }
            for (int i=0;i<same_data_all.size();i++){
                if (same_data_all.get(i).getName() != null && !same_data_all.get(i).getName().equals("")) {

                    String s_pp = same_data_all.get(i).getName().toString();
                    names.add(s_pp);
                }
            }
            //集合所有信息
            bl_sales_info_add();
        }

    }
    private void bl_sales_info_add() {
        Log.e("LiNing", "数据大小--------" + same_data_all.size() + "-----" + same_data_db_all.size());
        Log.e("LiNing", "names数据大小--------" + names.size() + "-----" + names);
        for (int m = 0; m < same_data_db_all.size(); m++) {
            zj_q(m);
            if (same_data_db_all.get(m).getName() != null && !same_data_db_all.get(m).getName().equals("")) {
                String db_s_pp_m = same_data_db_all.get(m).getName().toString();
                String db_s_jxse_m = same_data_db_all.get(m).getJxsr().toString();
                String db_s_ml_m = same_data_db_all.get(m).getMl().toString();
                String db_s_mll_m = same_data_db_all.get(m).getMll().toString();

                if (!names.contains(db_s_pp_m)) {
                        TbSales tbSales_bt_m = new TbSales("", "", "", "", db_s_pp_m, db_s_jxse_m, db_s_ml_m, db_s_mll_m);
//                    TbSales tbSales_bt_m = new TbSales(db_s_pp_m, db_s_jxse_m, db_s_ml_m, db_s_mll_m,"", "", "", "");
                    tb_data3.add(tbSales_bt_m);
                }
            }
        }
        for (int j = 0; j < same_data_all.size(); j++) {
            zj_h(j);
            if (same_data_all.get(j).getName() != null && !same_data_all.get(j).getName().equals("")) {
                String db_s_pp = same_data_all.get(j).getName().toString();
                String db_s_jxse = same_data_all.get(j).getJxsr().toString();
                String db_s_ml = same_data_all.get(j).getMl().toString();
                String db_s_mll = same_data_all.get(j).getMll().toString();

                if (names_2.contains(db_s_pp)) {
                    for (int i = 0; i < same_data_db_all.size(); i++) {
                        if (same_data_db_all.get(i).getName() != null && !same_data_db_all.get(i).getName().equals("")) {

                            String s_pp_q = same_data_db_all.get(i).getName().toString();
                            if (s_pp_q.equals(db_s_pp)&&!s_pp_q.equals("总计：")) {

                                String s_jxse_q = same_data_db_all.get(i).getJxsr().toString();
                                String s_ml_q = same_data_db_all.get(i).getMl().toString();
                                String s_mll_q = same_data_db_all.get(i).getMll().toString();

//                                    TbSales tbSales = new TbSales(s_pp_q, s_jxse_q, s_ml_q, s_mll_q, db_s_pp, db_s_jxse, db_s_ml, db_s_mll);
                                TbSales tbSales = new TbSales( db_s_pp, db_s_jxse, db_s_ml, db_s_mll,s_pp_q, s_jxse_q, s_ml_q, s_mll_q);
                                tb_data1.add(tbSales);
                            }
                        }
                    }
//                        Log.e("LiNing", "tb_data1.size--------" + tb_data1.size() + "-----" + tb_data1);
                } else {
                        TbSales tbSales_bt = new TbSales(db_s_pp, db_s_jxse, db_s_ml, db_s_mll, "", "", "", "");
//                    TbSales tbSales_bt = new TbSales("", "", "", "",db_s_pp, db_s_jxse, db_s_ml, db_s_mll);
                    tb_data2.add(tbSales_bt);
                }
            }

        }
//            Log.e("LiNing", "names_2--------" + names_2.size() + names_2);
        tb_data1.addAll(tb_data2);
        tb_data1.addAll(tb_data3);
        tb_data1.addAll(tb_data_zj);
            Log.e("LiNing", "tb_data1.size--------" + tb_data1.size() + "-----" + tb_data1);
        showlist_db();//显示数据
    }

    private void showlist_db() {
        sfadapter_db = new SalesInfoDbAdapter(R.layout.tb_one_listview_team, context, tb_data1);
        lv_get_all.setAdapter(sfadapter_db);
        sfadapter_db.notifyDataSetChanged();
    }
    public class SalesInfoDbAdapter extends BaseAdapter {
        SaleMakeInfo.Data data_dq_infos;
        int id_row_layout;
        Context context;
        List<TbSales> tb_data_db;
        LayoutInflater mInflater;
        String index;


        public SalesInfoDbAdapter(int tb_one_listview_team, Context context, List<TbSales> tb_data1) {
        this.id_row_layout = tb_one_listview_team;
        this.context = context;
        this.tb_data_db = tb_data1;
        this.mInflater = LayoutInflater.from(context);
    }


        @Override
        public int getCount() {
        return tb_data_db.size();
    }

        @Override
        public Object getItem(int position) {
        return tb_data_db.get(position);
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
            holder.tb_dq_data_zl = (TextView) convertView.findViewById(R.id.tb_zl_tv_name);
            holder.tb_dq_sn_amtn = (TextView) convertView.findViewById(R.id.tb_dqzt_tv_jxse);
            holder.tb_dq_gp = (TextView) convertView.findViewById(R.id.tb_dqzt_tv_xsml);
            holder.tb_dq_gpm = (TextView) convertView.findViewById(R.id.tb_dqzt_tv_mll);
            holder.tb_db_data_zl = (TextView) convertView.findViewById(R.id.tb_zl_tv_name_db);
            holder.tb_db_sn_amtn = (TextView) convertView.findViewById(R.id.tb_dbzt_tv_jxse);
            holder.tb_db_gp = (TextView) convertView.findViewById(R.id.tb_dbzt_tv_xsml);
            holder.tb_db_gpm = (TextView) convertView.findViewById(R.id.tb_dbzt_tv_mll);
            holder.tb_db_xs_zzl = (TextView) convertView.findViewById(R.id.tb_zzl_tv_xszzl);
            holder.tb_db_gp_zzl = (TextView) convertView.findViewById(R.id.tb_zzl_tv_mlzzl);
            holder.tb_db_gpm_c = (TextView) convertView.findViewById(R.id.tb_zzl_tv_mllc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TbSales tbSales = tb_data_db.get(position);
            Log.e("LiNing", "----最终数据------" + tbSales.getJxsr());
//            if (index.equals("SATGP")) {
        if (tbSales != null) {
            holder.tb_dq_data_zl.setText(tbSales.getName());
            holder.tb_db_data_zl.setText(tbSales.getName_db());
            holder.tb_dq_sn_amtn.setText(tbSales.getJxsr());
            holder.tb_dq_gp.setText(tbSales.getMl());
            holder.tb_dq_gpm.setText(tbSales.getMll()+"%");
            holder.tb_db_sn_amtn.setText(tbSales.getJxsr_db());
            holder.tb_db_gp.setText(tbSales.getMl_db());
            holder.tb_db_gpm.setText(tbSales.getMll_db()+"%");
            if (tbSales.getName().toString().equals(tbSales.getName_db())) {
                if(tbSales.getJxsr_db().toString().equals("0.00")||tbSales.getMl_db().equals("0.00")||tbSales.getMll_db().equals("0.00")){
                    holder.tb_db_xs_zzl.setText("-100");
                    holder.tb_db_gp_zzl.setText("-100");
                    holder.tb_db_gpm_c.setText("-1");
                }else {

                    //销售增长率
                    BigDecimal d_jxse_q = new BigDecimal(tbSales.getJxsr());
                    BigDecimal d_jxse_h = new BigDecimal(tbSales.getJxsr_db());
//                        BigDecimal c_jxse = d_jxse_h.subtract(d_jxse_q);
                    BigDecimal c_jxse = d_jxse_q.subtract(d_jxse_h);//(前-后)
                    Log.e("LiNing", "净销售额率--cc--" + c_jxse);
                    double v = c_jxse.divide(d_jxse_h,4, BigDecimal.ROUND_HALF_UP).doubleValue();
                    Log.e("LiNing", "净销售额率----" + v);
                    holder.tb_db_xs_zzl.setText(String.valueOf(v*100)+"%");
                    //毛利增长率
                    BigDecimal d_ml_q = new BigDecimal(tbSales.getMl());
                    BigDecimal d_ml_h = new BigDecimal(tbSales.getMl_db());
//                        BigDecimal c_ml = d_ml_h.subtract(d_ml_q);
                    BigDecimal c_ml = d_ml_q.subtract(d_ml_h);
                    Log.e("LiNing", "毛利增长率--cc--" + c_ml);
                    double v_ml = c_ml.divide(d_ml_h,4, BigDecimal.ROUND_HALF_UP).doubleValue();
                    Log.e("LiNing", "毛利增长率----" + v_ml);
                    holder.tb_db_gp_zzl.setText(String.valueOf(v_ml*100)+"%");
                    //毛利率差
                    String gpm_rp = tbSales.getMll().replace("%", "");
                    double v1 = Double.parseDouble(gpm_rp);
//                        double v2 = v1 * 0.01;
                    BigDecimal b1 = new BigDecimal(Double.toString(v1));
                    double v3 = Double.parseDouble(tbSales.getMll_db().toString().replace("%", ""));
//                        double v4 = v3 * 0.01;
                    BigDecimal b2 = new BigDecimal(Double.toString(v3));
//                        double v5 = b2.subtract(b1).doubleValue();
                    double v5 = b1.subtract(b2).doubleValue();
                    Log.e("LiNing", "毛利率差----" + v5);
                    holder.tb_db_gpm_c.setText(String.valueOf(v5) + "%");
                }
            } else {
                if (tbSales.getName().toString().equals("")) {
                    holder.tb_db_xs_zzl.setText("100");
                    holder.tb_db_gp_zzl.setText("100");
                    holder.tb_db_gpm_c.setText("1");
                }
                if (tbSales.getName_db().toString().equals("")) {
                    holder.tb_db_xs_zzl.setText("-100");
                    holder.tb_db_gp_zzl.setText("-100");
                    holder.tb_db_gpm_c.setText("-1");
                }
            }
        }
//            }
        info_out=tb_data_db;
        return convertView;
    }


        public class ViewHolder {
            TextView tb_dq_data_zl;// 分支机构1
            TextView tb_dq_sn_amtn;// 净销售额1
            TextView tb_dq_gp;// 销售毛利1
            TextView tb_dq_gpm;// 毛利率1
            TextView tb_db_data_zl;// 分支机构
            TextView tb_db_sn_amtn;// 净销售额
            TextView tb_db_gp;// 销售毛利
            TextView tb_db_gpm;// 毛利率
            TextView tb_db_xs_zzl;// 销售增长率
            TextView tb_db_gp_zzl;// 毛利增长率
            TextView tb_db_gpm_c;// 毛利率差
//            TextView brand;// 品牌
//            TextView sales_channels;// 渠道
//            TextView sales_dep;// 部门
//            TextView sales_oper;// 业务
//            TextView sales_ter;// 终端

        }
    }
    private void zj_h(int j) {
        if(same_data_all.get(j).getName().equals("总计：")){
            String zj_pp_m_db = same_data_all.get(j).getName().toString();
            String zj_jxse_m_db = same_data_all.get(j).getJxsr().toString();
            String zj_ml_m_db = same_data_all.get(j).getMl().toString();
            String zj_mll_m_db = same_data_all.get(j).getMll().toString();
//            TbSales tbSales_zj = new TbSales(zj_pp_m, zj_jxse_m, zj_ml_m, zj_mll_m, zj_pp_m_db, zj_jxse_m_db, zj_ml_m_db, zj_mll_m_db);
            TbSales tbSales_zj = new TbSales(zj_pp_m_db, zj_jxse_m_db, zj_ml_m_db, zj_mll_m_db,zj_pp_m, zj_jxse_m, zj_ml_m, zj_mll_m);
            tb_data_zj.add(tbSales_zj);
            Log.e("LiNing", "tb_data_zj----总计:----" + tb_data_zj.size() + "-----" + tbSales_zj);
        }
    }

    private void zj_q(int m) {
        if(same_data_db_all.get(m).getName().equals("总计：")){
            zj_pp_m = same_data_db_all.get(m).getName().toString();
            zj_jxse_m = same_data_db_all.get(m).getJxsr().toString();
            zj_ml_m = same_data_db_all.get(m).getMl().toString();
            zj_mll_m = same_data_db_all.get(m).getMll().toString();
            Log.e("LiNing", "总计数据-------" + zj_pp_m + zj_jxse_m);
        }
    }

    private void bl_sales_names() {
        List<String> result1 = new ArrayList<String>();
        List<String> result_name1 = new ArrayList<String>();
        for (int i = 0; i < data.size(); i++) {
            if(data.get(i).getAffiliateName().equals("总计：")){
                String all_zj_pp_m = data.get(i).getAffiliateName().toString();
                String all_zj_jxse_m = data.get(i).getSnAmtn().toString();
                String all_zj_ml_m = data.get(i).getgP().toString();
                String all_zj_mll_m = data.get(i).getgPM().toString();
                Log.e("LiNing", "总计数据-------" + all_zj_pp_m + all_zj_jxse_m);
                BrandSame same_zj = new BrandSame(all_zj_pp_m, all_zj_jxse_m, all_zj_ml_m, all_zj_mll_m);
                same_data_zj.add(same_zj);
            }
//            Log.e("LiNing", "品牌名称====" + data.get(i).getBrandName() + "....." + data.get(i).getSnAmtn());
            if (data.get(i).getBrandName() != null && !data.get(i).getBrandName().equals("")) {

                String id_pp = data.get(i).getBrandName().toString();
                String xsje_pp = data.get(i).getSnAmtn().toString();
                String ml_pp = data.get(i).getgP().toString();
                String mll_pp = data.get(i).getgPM().toString();
                if (result1.contains(id_pp)) {

                    //此处添加类（new和同比一样）
                    String jxse_xt = data.get(i).getSnAmtn().toString();
                    double v_x1 = Double.parseDouble(jxse_xt);
                    String ml_xt = data.get(i).getgP().toString();
                    double v_x2 = Double.parseDouble(ml_xt);
                    String mll_xt = data.get(i).getgPM().toString();
                    Log.e("LiNing", "xt品牌名称====" + id_pp + "....." + jxse_xt);
//                    Log.e("LiNing", "相同数据==id_pp1==" + id_pp + "/" + jxse_xt + "/" + ml_xt + "/" + mll_xt);
                    result_name1.add(id_pp);
                    BrandSame same1 = new BrandSame(id_pp, jxse_xt, ml_xt, mll_xt);
                    same_data.add(same1);

                } else {
                    result1.add(id_pp);
                    BrandSame data_dif = new BrandSame(id_pp, xsje_pp, ml_pp, mll_pp);
                    dif_data.add(data_dif);
                    Log.e("LiNing", "不同数据====" + dif_data.size()+dif_data);

                }
            }
        }
        List<BrandSame> same_data_two = new ArrayList<BrandSame>();
        for(int k=0;k<same_data.size();k++){
            String name_two = same_data.get(k).getName();
            String jxsr_two = same_data.get(k).getJxsr();
            String ml_two = same_data.get(k).getMl();
            String mll_two = same_data.get(k).getMll();
            int flag = 0;// 0为新增数据，1为增加count
            for(int n=0;n<same_data_two.size();n++){
                String name_two_db = same_data_two.get(n).getName();
                String jxsr_two_db = same_data_two.get(n).getJxsr();
                String ml_two_db = same_data_two.get(n).getMl();
                String mll_two_db = same_data_two.get(n).getMll();
                if(name_two.equals(name_two_db)){
                    double v_jxse_hj = Double.parseDouble(jxsr_two) + Double.parseDouble(jxsr_two_db);
                    double v_ml_hj = Double.parseDouble(ml_two) + Double.parseDouble(ml_two_db);
//                    double v_mll_hj = Double.parseDouble(mll_two) + Double.parseDouble(mll_two_db);
                    same_data_two.get(n).setJxsr(""+v_jxse_hj);
                    same_data_two.get(n).setMl(""+v_ml_hj);
//                    same_data_two.get(n).setMll(""+v_mll_hj);
                    flag = 1;
                    continue;
                }
            }
            if (flag == 0) {
                same_data_two.add(same_data.get(k));
            }
        }
        for (int i = 0; i < dif_data.size(); i++) {

            if (dif_data.get(i).getName()!= null && !dif_data.get(i).getName().equals("")) {
                String id_pp = dif_data.get(i).getName().toString();
                //此处添加类（new和同比一样）
                String jxse_xt = dif_data.get(i).getJxsr().toString();
                String ml_xt = dif_data.get(i).getMl().toString();
                String mll_xt = dif_data.get(i).getMll().replace("%", "").toString();
                Log.e("LiNing", "相同数据==name=名字=" + result_name1);
                if(result_name1!=null&&result_name1.size()>0){
                    if (result_name1.contains(id_pp)) {
//                        Log.e("LiNing", "相同数据==name==" + same_data.size()+same_data);
                        Log.e("LiNing", "相同数据==name==" + same_data_two.size()+same_data_two);
//                        相同数据==name==2[BrandSame{name='冠珠瓷砖', jxsr='1802188.84', ml='871827.09', mll='48.38%'},
//                        BrandSame{name='瓷砖炒货', jxsr='1925.60', ml='638.60', mll='33.16%'}]
                        for (int j = 0; j < same_data_two.size(); j++) {
                            if (same_data_two.get(j).getName() != null && !same_data_two.get(j).getName().equals("")) {
                                String s_name = same_data_two.get(j).getName().toString();
                                if (s_name.equals(id_pp)) {
                                    String s_jxse = same_data_two.get(j).getJxsr().toString();
                                    String s_ml = same_data_two.get(j).getMl().toString();
//                                    String s_mll = same_data_two.get(j).getMll().replace("%", "").toString();
                                    double v_jxse = Double.parseDouble(s_jxse);
                                    double v_jxse1 = Double.parseDouble(jxse_xt);
                                    double v_jxse_xt = v_jxse + v_jxse1;
                                    double v_ml = Double.parseDouble(s_ml);
                                    double v_ml1 = Double.parseDouble(ml_xt);
                                    double v_ml_xt = v_ml + v_ml1;
//                                    double v_mll_xt = v_ml_xt / v_jxse_xt;
//                                    double v_mll_xt = (double) Math.round(v_ml_xt / v_jxse_xt * 100) / 100;
                                    double v_mll_xt = (double) Math.round(v_ml_xt / v_jxse_xt * 100);
//                                    double v_mll = Double.parseDouble(s_mll);
//                                    double v_mll1 = Double.parseDouble(mll_xt);
//                                    double v_mll_xt = v_mll + v_mll1;
//                                    Log.e("LiNing", "---相同的数据---f---" + v_jxse + "===" + v_jxse1 + "===" + v_jxse_xt);
                                    BrandSame same_all1 = new BrandSame(s_name, String.valueOf(v_jxse_xt), String.valueOf(v_ml_xt), String.valueOf(v_mll_xt));
//                                    BrandSame same_all1 = new BrandSame(s_name, String.valueOf(v_jxse), String.valueOf(v_ml), String.valueOf(v_mll));
                                    same_data_all.add(same_all1);
                                    Log.e("LiNing", "数据==重复===" + same_data_all.size() + same_data_all);
                                }
                            }
                        }

                    }else{
                        BrandSame dif_all1 = new BrandSame(id_pp, jxse_xt, ml_xt, mll_xt);
                        dif_data_all.add(dif_all1);
                        Log.e("LiNing", "---不同的数据" + dif_data_all.size() );
                    }
                }else{
                        BrandSame dif_all1 = new BrandSame(id_pp, jxse_xt, ml_xt, mll_xt);
                        dif_data_all.add(dif_all1);
                        Log.e("LiNing", "--0-不同的数据" + dif_data_all.size() );
                }

            }
        }

        //此处重复添加了
//        all_data.addAll(same_data_all);
//        all_data.addAll(dif_data_all);
//        all_data.addAll(same_data_zj);
        same_data_all.addAll(dif_data_all);
        same_data_all.addAll(same_data_zj);
        Log.e("LiNing", "数据==重复===" + same_data_all.size() + same_data_all);

    }



    private void bl_sales_names_db() {


        List<String> result = new ArrayList<String>();
        List<String> result_name_db = new ArrayList<String>();
        for (int i = 0; i < data_db.size(); i++) {
            if(data_db.get(i).getAffiliateName().equals("总计：")){
                String all_zj_pp_m = data_db.get(i).getAffiliateName().toString();
                String all_zj_jxse_m = data_db.get(i).getSnAmtn().toString();
                String all_zj_ml_m = data_db.get(i).getgP().toString();
                String all_zj_mll_m = data_db.get(i).getgPM().toString();
                Log.e("LiNing", "总计数据-------" + zj_pp_m + zj_jxse_m);
                BrandSame same_zj = new BrandSame(all_zj_pp_m, all_zj_jxse_m, all_zj_ml_m, all_zj_mll_m);
                same_data_db_zj.add(same_zj);
            }
//            Log.e("LiNing", "品牌名称====" + data_db.get(i).getBrandName() + "....." + data_db.get(i).getSnAmtn());
            if (data_db.get(i).getBrandName() != null && !data_db.get(i).getBrandName().equals("")) {

                String id_pp = data_db.get(i).getBrandName().toString();
                String xsje_pp = data_db.get(i).getSnAmtn().toString();
                String ml_pp = data_db.get(i).getgP().toString();
                String mll_pp = data_db.get(i).getgPM().toString();
                if (result.contains(id_pp)) {
                    //此处添加类（new和同比一样）
                    String jxse_xt = data_db.get(i).getSnAmtn().toString();
                    String ml_xt = data_db.get(i).getgP().toString();
                    String mll_xt = data_db.get(i).getgPM().toString();
//                    Log.e("LiNing", "相同数据==id_pp==" + id_pp + "/" + jxse_xt + "/" + ml_xt + "/" + mll_xt);
                    BrandSame same = new BrandSame(id_pp, jxse_xt, ml_xt, mll_xt);
                    same_data_db.add(same);
                    result_name_db.add(id_pp);

                } else {
                    result.add(id_pp);
                    BrandSame same_dif = new BrandSame(id_pp, xsje_pp, ml_pp, mll_pp);
                    dif_data_db.add(same_dif);
                    Log.e("LiNing", "不同数据====" + data.size());

                }
            }
        }
        List<BrandSame> same_data_db_two = new ArrayList<BrandSame>();
        for(int k=0;k<same_data_db.size();k++){
            String name_two = same_data_db.get(k).getName();
            String jxsr_two = same_data_db.get(k).getJxsr();
            String ml_two = same_data_db.get(k).getMl();
            String mll_two = same_data_db.get(k).getMll();
            int flag = 0;// 0为新增数据，1为增加count
            for(int n=0;n<same_data_db_two.size();n++){
                String name_two_db = same_data_db_two.get(n).getName();
                String jxsr_two_db = same_data_db_two.get(n).getJxsr();
                String ml_two_db = same_data_db_two.get(n).getMl();
                String mll_two_db = same_data_db_two.get(n).getMll();
                if(name_two.equals(name_two_db)){
                    double v_jxse_hj = Double.parseDouble(jxsr_two) + Double.parseDouble(jxsr_two_db);
                    double v_ml_hj = Double.parseDouble(ml_two) + Double.parseDouble(ml_two_db);
//                    double v_mll_hj = Double.parseDouble(mll_two) + Double.parseDouble(mll_two_db);
                    same_data_db_two.get(n).setJxsr(""+v_jxse_hj);
                    same_data_db_two.get(n).setMl(""+v_ml_hj);
//                    same_data_two.get(n).setMll(""+v_mll_hj);
                    flag = 1;
                    continue;
                }
            }
            if (flag == 0) {
                same_data_db_two.add(same_data_db.get(k));
            }
        }
        for (int i = 0; i < dif_data_db.size(); i++) {
            if (dif_data_db.get(i).getName() != null && !dif_data_db.get(i).getName().equals("")) {
                String id_pp = dif_data_db.get(i).getName().toString();
                //此处添加类（new和同比一样）
                String jxse_xt = dif_data_db.get(i).getJxsr().toString();
                String ml_xt = dif_data_db.get(i).getMl().toString();
                String mll_xt = dif_data_db.get(i).getMll().replace("%", "").toString();
                if(result_name_db!=null&&result_name_db.size()>0){
                    if (result_name_db.contains(id_pp)) {
                        for (int j = 0; j < same_data_db_two.size(); j++) {
                            if (same_data_db_two.get(j).getName() != null && !same_data_db_two.get(j).getName().equals("")) {
                                String s_name = same_data_db_two.get(j).getName().toString();
                                if (s_name.equals(id_pp)) {
                                    String s_jxse = same_data_db_two.get(j).getJxsr().toString();
                                    String s_ml = same_data_db_two.get(j).getMl().toString();
                                    String s_mll = same_data_db_two.get(j).getMll().replace("%", "").toString();
                                    double v_jxse = Double.parseDouble(s_jxse);
                                    double v_jxse1 = Double.parseDouble(jxse_xt);
                                    double v_jxse_xt = v_jxse + v_jxse1;
                                    double v_ml = Double.parseDouble(s_ml);
                                    double v_ml1 = Double.parseDouble(ml_xt);
                                    double v_ml_xt = v_ml + v_ml1;
//                                    double v_mll_xt = v_ml_xt / v_jxse_xt;
//                                    double v_mll_xt = (double) Math.round(v_ml_xt / v_jxse_xt * 100) / 100;
                                    double v_mll_xt = (double) Math.round(v_ml_xt / v_jxse_xt * 100) ;
//                                    double v_mll = Double.parseDouble(s_mll);
//                                    double v_mll1 = Double.parseDouble(mll_xt);
//                                    double v_mll_xt = v_mll + v_mll1;
//                                    Log.e("LiNing", "---相同的数据" + v_jxse_xt + "===" + v_ml_xt + "===" + v_mll_xt);
                                    BrandSame same_all = new BrandSame(s_name, String.valueOf(v_jxse_xt), String.valueOf(v_ml_xt), String.valueOf(v_mll_xt));
                                    same_data_db_all.add(same_all);
                                    Log.e("LiNing", "-b--相同的数据" + same_data_db_all.size());
                                }
                            }
                        }

                    }else{
                        BrandSame dif_all = new BrandSame(id_pp, jxse_xt, ml_xt, mll_xt);
                        dif_data_db_all.add(dif_all);
                        Log.e("LiNing", "---b同的数据" + same_data_db_all.size());
                    }
                }else{
                        BrandSame dif_all = new BrandSame(id_pp, jxse_xt, ml_xt, mll_xt);
                        dif_data_db_all.add(dif_all);
                }

            }
        }
//        all_data_db.addAll(same_data_db_all);
//        all_data_db.addAll(dif_data_db_all);
//        all_data_db.addAll(same_data_db_zj);
        same_data_db_all.addAll(dif_data_db_all);
        same_data_db_all.addAll(same_data_db_zj);
        Log.e("LiNing", "数据===1===" + all_data_db.size() + all_data_db);
        Log.e("LiNing", "数据===1=b==" + same_data_db_all.size() + same_data_db_all);

    }

    public void allback(View v) {
        finish();
    }
}
