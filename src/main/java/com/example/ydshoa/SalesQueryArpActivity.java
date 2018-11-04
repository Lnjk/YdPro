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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.LeftOrRight.MyHScrollView;
import com.example.bean.ArpInfos;
import com.example.bean.SaleMakeInfo;
import com.example.bean.TestQx;
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
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SalesQueryArpActivity extends Activity implements View.OnClickListener {
    private Context context;
    private SharedPreferences sp;
    public String session, zc_xy, dy_xy;
    String url = URLS.sale_url;
    private String reportBus, reportnos, reportname,ps_id,str_time,startTime, stopTime,sub_querys_db,str_all,head_all;
    private TextView head;
    private ListView lv_get_arp;
    private Button start, stop, make, condicion, out, printe, back;
    private ArrayList<String> querryDBs, querryDBs_all, bilNos, custNos,
            custNames, custPhones, custAdress, inputNos, cust_os, rems,
            prdMarks, query_deps, query_sups, query_custs, query_users,
            query_deps_all, query_sups_all, query_custs_all, query_users_all,
            chk_users, areas, query_hs, query_hs_all, employees, prdNos,
            prdIndexs, prdWhs, zts, zcs, dys, cbs, mls, mlls, jes, cbs_dj,
            mls_dj, mlls_dj, jes_dj;
    List<String> arp_head_json = new ArrayList<String>();
    TextView zt,time,money,zt_1,jg_1,time_1,money_1,zt_2,jg_2,jg_2_2,time_2,money_2,zt_3,jg_3,jg_3_3,jg_3_3_3,time_3,money_3;
    ArpQueryInfosAdapter adapter_arp;
    int count,flag_index;
    FrameLayout mHead;
    private PopupWindow popupWindow;
    public static List<ArpInfos.Data> out_infos_arp = new ArrayList<ArpInfos.Data>();//转出数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_query_arp);
        context = SalesQueryArpActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        ps_id = sp.getString("PASS", "");
        zc_xy = sp.getString("ZC", "");
        dy_xy = sp.getString("DY", "");
        reportBus = getIntent().getStringExtra("reportB");// 获取业务类型
        reportnos = getIntent().getStringExtra("reportNo");// 获取种类类型id
        reportname = getIntent().getStringExtra("reportName");// 获取种类类型名称
        Log.e("LiNing", reportBus + reportnos);
        getNowTime();
        initView();
    }


    private void initView() {
        mHead = (FrameLayout) findViewById(R.id.sales_scrowHead_arp);
        mHead.setFocusable(true);
        mHead.setClickable(true);
        mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        head = (TextView) findViewById(R.id.all_head);
        head.setTextSize(15);
        String jc_bb = sp.getString("JC", "");
        String tj_bb = sp.getString("TJ", "");
        String zbHead = jc_bb + "/" + tj_bb + "/" + reportname;
        head.setText(zbHead);
        lv_get_arp = (ListView) findViewById(R.id.lv_saleGet_header_arp);
        lv_get_arp.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        start = (Button) findViewById(R.id.btn_start_time);
        stop = (Button) findViewById(R.id.btn_stop_time);
        stop.setText(str_time);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_MONTH, 1); //当前月1号和最后日期
        start.setText(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
        cal.roll(Calendar.DAY_OF_MONTH, -1);
        make = (Button) findViewById(R.id.btn_making_arp);
        condicion = (Button) findViewById(R.id.btn_condition_arp);
        out = (Button) findViewById(R.id.btn_out_arp);
        printe = (Button) findViewById(R.id.btn_print_arp);
        back = (Button) findViewById(R.id.btn_back_zb_arp);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        make.setOnClickListener(this);
        condicion.setOnClickListener(this);
        out.setOnClickListener(this);
        printe.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private void getNowTime() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
        Date curDate = new Date(System.currentTimeMillis());
        str_time = formatter.format(curDate);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start_time:
                Calendar c_star = Calendar.getInstance();
                int mYear_s = c_star.get(Calendar.YEAR); // 获取当前年份
                new DatePickerDialog(context, DatePickerDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                startTime = String.format("%d-%d-%d", year,
                                        monthOfYear + 1, dayOfMonth);
                                start.setText(startTime);

                            }
//					}, 2015, 0, 1).show();
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
                                stopTime = String.format("%d-%d-%d", year,
                                        monthOfYear + 1, dayOfMonth);
                                stop.setText(stopTime);

                            }
                        }, mYear, mMonth, mDay).show();//获取当前时间
                break;
            case R.id.btn_making_arp:
                if (reportnos.equals("")) {
                    Toast.makeText(context, "请选择报表种类", Toast.LENGTH_LONG).show();
                } else if (start.getText().toString().equals("")
                        || stop.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择时间", Toast.LENGTH_LONG).show();
                }else{
                    getAllQuery();// 获取查询数据（all）

                    if (flag_index == 1) {
//                        getRootTorF();
                        getpopWindow();
                        postAllInfo();
                    } else {
                        getpopWindow();
                        getAllQuery();// 获取查询数据（all）
                    }
                }
                break;
            case R.id.btn_condition_arp:
                lv_get_arp.setAdapter(null);
                // 判断
                sp.edit().putString("sale_tjInfo", "").commit();
                flag_index = 1;
                // 获取查询条件
                Intent intent = new Intent(context, ConditionActivity.class);
                // intent.putExtra("page", page_get);
                startActivity(intent);
                break;
            case R.id.btn_out_arp:
                if (zc_xy.equals("true")) {
                    Intent intent2 = new Intent(context, ArpOutActivity.class);
                    intent2.putExtra("Stime", start.getText().toString());
                    intent2.putExtra("Etime", stop.getText().toString());
                    intent2.putExtra("RTNAME", reportname);
                    intent2.putExtra("RT", reportnos);
                    intent2.putExtra("INFO", head_all);
                    Log.e("LiNing", "传递数据====" + reportnos + "===" + str_all);
                    startActivity(intent2);
                } else {
                    Toast.makeText(context, "无此权限", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_print_arp:

                if (dy_xy.equals("false")) {

                    Toast.makeText(context, "无此权限", Toast.LENGTH_LONG).show();
                } else {

                }
                break;
            case R.id.btn_back_zb_arp:
                getSpToNull();
                finish();
                break;
        }
    }
    // 提交信息，开始制表
    private void postAllInfo() {
        String all_info = sp.getString("sale_tjInfo", "");
        Log.e("LiNing", "--更新数据--" + all_info);
        if (all_info != null && !all_info.equals("")) {

            querryDBs = new ArrayList<String>();
            bilNos = new ArrayList<String>();
            custNos = new ArrayList<String>();
            custNames = new ArrayList<String>();
            custPhones = new ArrayList<String>();
            custAdress = new ArrayList<String>();
            inputNos = new ArrayList<String>();
            cust_os = new ArrayList<String>();
            rems = new ArrayList<String>();
            prdMarks = new ArrayList<String>();
            query_hs = new ArrayList<String>();
            query_deps = new ArrayList<String>();
            query_sups = new ArrayList<String>();
            query_custs = new ArrayList<String>();
            query_users = new ArrayList<String>();
            chk_users = new ArrayList<String>();
            areas = new ArrayList<String>();
            employees = new ArrayList<String>();
            prdNos = new ArrayList<String>();
            prdIndexs = new ArrayList<String>();
            prdWhs = new ArrayList<String>();
            try {
                JSONArray arr = new JSONArray(all_info);
                int size = arr.length();
                for (int i = 0; i < size; i++) {
                    JSONObject jsonObject = arr.getJSONObject(i);
                    String db_ids = jsonObject.get("账套").toString();
                    if (db_ids.equals("") || db_ids.equals("null")
                            || db_ids.equals("All")) {
                        querryDBs.add("All");
                    } else {
                        querryDBs.add(db_ids);
                    }

                    String bilNo = jsonObject.get("单号").toString();
                    bilNos.add(bilNo);
                    String custNo = jsonObject.get("客户编号").toString();
                    custNos.add(custNo);
                    String custName = jsonObject.get("客户名称").toString();
                    custNames.add(custName);
                    String custPhone = jsonObject.get("客户电话").toString();
                    custPhones.add(custPhone);
                    String custAdr = jsonObject.get("客户地址").toString();
                    custAdress.add(custAdr);
                    String inputNo = jsonObject.get("转入单号").toString();
                    inputNos.add(inputNo);
                    String custOSNO = jsonObject.get("客户订单").toString();
                    cust_os.add(custOSNO);
                    String rem = jsonObject.get("备注信息").toString();
                    rems.add(rem);
                    String prdMark = jsonObject.get("色号").toString();
                    prdMarks.add(prdMark);
                    String queryHs = jsonObject.get("ERP核算单位").toString();
                    query_hs.add(queryHs);
                    String queryDep = jsonObject.get("ERP部门").toString();
                    query_deps.add(queryDep);
                    String querySup = jsonObject.get("ERP厂商").toString();
                    query_sups.add(querySup);
                    String queryCust = jsonObject.get("ERP客户").toString();
                    query_custs.add(queryCust);
                    String queryUser = jsonObject.get("ERP用户").toString();
                    query_users.add(queryUser);
                    String chkUser = jsonObject.get("ERP审核人").toString();
                    chk_users.add(chkUser);
                    String area = jsonObject.get("销售渠道").toString();
                    areas.add(area);
                    String employee = jsonObject.get("销售人员").toString();
                    employees.add(employee);
                    String prdNo = jsonObject.get("品号").toString();
                    prdNos.add(prdNo);
                    String prdIndex = jsonObject.get("货品中类").toString();
                    prdIndexs.add(prdIndex);
                    String prdWh = jsonObject.get("货品库区").toString();
                    prdWhs.add(prdWh);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            beforPost();
        } else {
            Toast.makeText(context, "数据不能为空", Toast.LENGTH_LONG).show();
            // 空数据就是全部。。。
            getAllQuery();
        }
    }
    private void beforPost() {
        // getFlagreportNo();// 获取编号标识
        // reportNo_get = saleNum.getText().toString();

        String querys_db_str = "";// 此处解决数据重复出现（不能放出去）
        for (String querys_db : querryDBs) {
            querys_db_str += querys_db + ",";
        }
        sub_querys_db = querys_db_str.substring(0, querys_db_str.length() - 1);
        Log.e("LiNing", "======" + sub_querys_db);
        // 多层判断
        // pdsj();
        // getXSqx();
        // getForT();
        String bilNos_str = "";
        for (String bil : bilNos) {
            bilNos_str += bil + ";";
        }
        String sub_bils = bilNos_str.substring(0, bilNos_str.length() - 1);
        Log.e("LiNing", "======" + sub_bils);
        String custNos_str = "";
        for (String cstno : custNos) {
            custNos_str += cstno + ";";
        }
        String sub_custNos = custNos_str.substring(0, custNos_str.length() - 1);
        String custNames_str = "";
        for (String cstname : custNames) {
            custNames_str += cstname + ";";
        }
        String sub_custNames = custNames_str.substring(0,
                custNames_str.length() - 1);
        String custPhones_str = "";
        for (String cstphone : custPhones) {
            custPhones_str += cstphone + ";";
        }
        String sub_custPhones = custPhones_str.substring(0,
                custPhones_str.length() - 1);
        String custAdress_str = "";
        for (String cstadr : custAdress) {
            custAdress_str += cstadr + ";";
        }
        String sub_custAdress = custAdress_str.substring(0,
                custAdress_str.length() - 1);
        String inputNos_str = "";
        for (String inputs : inputNos) {
            inputNos_str += inputs + ";";
        }
        String sub_inputNos = inputNos_str.substring(0,
                inputNos_str.length() - 1);
        String cust_os_str = "";
        for (String custos : cust_os) {
            cust_os_str += custos + ";";
        }
        String sub_cust_os = cust_os_str.substring(0, cust_os_str.length() - 1);
        String rems_str = "";
        for (String rem_get : rems) {
            rems_str += rem_get + ";";
        }
        String sub_rems = rems_str.substring(0, rems_str.length() - 1);
        String prdMarks_str = "";
        for (String prdMarks_get : prdMarks) {
            prdMarks_str += prdMarks_get + ";";
        }
        String sub_prdMarks = prdMarks_str.substring(0,
                prdMarks_str.length() - 1);
        String query_hss_str = "";
        for (String query_hss_get : query_hs) {
            query_hss_str += query_hss_get + ";";
        }
        String sub_query_hss = query_hss_str.substring(0,
                query_hss_str.length() - 1);
        String query_deps_str = "";
        for (String query_deps_get : query_deps) {
            query_deps_str += query_deps_get + ";";
        }
        String sub_query_deps = query_deps_str.substring(0,
                query_deps_str.length() - 1);
        String query_sups_str = "";
        for (String query_sups_get : query_sups) {
            query_sups_str += query_sups_get + ";";
        }
        String sub_query_sups = query_sups_str.substring(0,
                query_sups_str.length() - 1);
        String query_custs_str = "";
        for (String query_custs_get : query_custs) {
            query_custs_str += query_custs_get + ";";
        }
        String sub_query_custs = query_custs_str.substring(0,
                query_custs_str.length() - 1);
        String query_users_str = "";
        for (String query_users_get : query_users) {
            query_users_str += query_users_get + ";";
        }
        String sub_query_users = query_users_str.substring(0,
                query_users_str.length() - 1);
        String chk_users_str = "";
        for (String chk_users_get : chk_users) {
            chk_users_str += chk_users_get + ";";
        }
        String sub_chk_users = chk_users_str.substring(0,
                chk_users_str.length() - 1);
        String areas_str = "";
        for (String areas_get : areas) {
            areas_str += areas_get + ";";
        }
        String sub_areas = areas_str.substring(0, areas_str.length() - 1);
        String employees_str = "";
        for (String employees_get : employees) {
            employees_str += employees_get + ";";
        }
        String sub_employees = employees_str.substring(0,
                employees_str.length() - 1);
        String prdNos_str = "";
        for (String prdNos_get : prdNos) {
            prdNos_str += prdNos_get + ";";
        }
        String sub_prdNos = prdNos_str.substring(0, prdNos_str.length() - 1);
        String prdIndexs_str = "";
        for (String prdIndexs_get : prdIndexs) {
            prdIndexs_str += prdIndexs_get + ";";
        }
        String sub_prdIndexs = prdIndexs_str.substring(0,
                prdIndexs_str.length() - 1);
        String prdWhs_str = "";
        for (String prdWhs_get : prdWhs) {
            prdWhs_str += prdWhs_get + ";";
        }
        String sub_prdWhs = prdWhs_str.substring(0, prdWhs_str.length() - 1);
        Log.e("LiNing", "-----" + start.getText().toString() + "-----"
                + sub_querys_db + "-----" + sub_query_deps);

        OkHttpClient client = new OkHttpClient();
        // if (flag_index == 1) {

        FormBody body = new FormBody.Builder()
                .add("reprotNo", reportnos)
                .add("beginDate", start.getText().toString())
                .add("endDate", stop.getText().toString())
                .add("reporyBusiness", reportBus)
                .add("query_DB", sub_querys_db).add("bilNo", sub_bils)
                .add("custNo", sub_custNos).add("custName", sub_custNames)
                .add("custPhone", sub_custPhones)
                .add("custAddress", sub_custAdress)
                .add("inputNo", sub_inputNos).add("cust_OS_NO", sub_cust_os)
                .add("rem", sub_rems).add("prdMark", sub_prdMarks)
                .add("query_Dep", sub_query_deps)
                .add("query_CompDep", sub_query_hss)
                .add("query_Sup", sub_query_sups)
                .add("query_Cust", sub_query_custs)
                .add("query_User", sub_query_users)
                .add("chk_User", sub_chk_users).add("area", sub_areas)
                .add("employee", sub_employees).add("prdNO", sub_prdNos)
                .add("prdIndx", sub_prdIndexs).add("prdWh", sub_prdWhs).build();
        Request request = new Request.Builder().addHeader("cookie", session)
                .url(url).post(body).build();
        Log.e("LiNing", "-----" + body.toString());
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {

                str_all = response.body().string();
                Log.e("LiNing", "-----" + str_all + "-----" + response.code());
                Log.e("LiNing", "-----" + response.code());
                if (response.code() == 200) {
                    sp.edit().putString("sale_tjInfo", "").commit();
                }
                if (str_all != null) {
                    SalesQueryArpActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            popupWindow.dismiss();
                        }
                    });
                } else {
                    Toast.makeText(SalesQueryArpActivity.this, "无数据。。。", Toast.LENGTH_LONG)
                            .show();
                }
                final ArpInfos aDb1 = new Gson().fromJson(str_all,ArpInfos.class);
                if(aDb1!=null){
                    SalesQueryArpActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            List<ArpInfos.Data> data_arp = aDb1.getData();
                            String head_arp = aDb1.getHead();
                            Log.e("LiNing", "---数据--" + data_arp );
                            Log.e("LiNing", "---标题--" + head_arp );
                            if (head_arp != null) {
                                String[] head_spls = head_arp.split(",");
                                Log.e("LiNing", "=====" + head_arp.split(","));
                                for (int i = 0; i < head_spls.length; i++) {
                                    String A = head_spls[i];
                                    Log.e("LiNing", "===A==" + A);
                                    arp_head_json.add(A);
                                }
                                Log.e("LiNing", "==ln===" + arp_head_json);
                            }
                            findViewById(R.id.one_zt).setVisibility(View.VISIBLE);
                            getFlagreportNo();
                            if(data_arp!=null){

                                adapter_arp=new ArpQueryInfosAdapter(count,R.layout.arp_dthead,data_arp,context);
                                lv_get_arp.setAdapter(adapter_arp);
                                adapter_arp.notifyDataSetChanged();
                            }
//                            else {
//                                Toast.makeText(SalesQueryArpActivity.this, "无数据。。。", Toast.LENGTH_LONG)
//                                        .show();
//                            }
                        }
                    });
                }
                }


            @Override
            public void onFailure(Call arg0, IOException arg1) {

            }
        });
    }
    // 提交信息，开始制表(all)
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
            getInfos_arp();

        }
    }
    private void getInfos_arp() {
        String querys_db_str = "";// 此处解决数据重复出现（不能放出去）
        for (String querys_db : querryDBs_all) {
            querys_db_str += querys_db + ",";
        }
        String sub_querys_db_all = querys_db_str.substring(0,
                querys_db_str.length() - 1);
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
        OkHttpClient client = new OkHttpClient();
        FormBody body= new FormBody.Builder()
                .add("reprotNo", reportnos)
                .add("beginDate", start.getText().toString())
                .add("endDate", stop.getText().toString())
                .add("reporyBusiness", reportBus)
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
                .add("prdIndx", "ALL").add("prdWh", "ALL")
                .build();
        Request request = new Request.Builder().addHeader("cookie", session)
                .url(url).post(body).build();
        Log.e("LiNing", "-----" + sub_querys_db_all + sub_query_deps_all);
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str_arp = response.body().string();
                if (str_arp != null) {
                    SalesQueryArpActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            popupWindow.dismiss();
                        }
                    });
                } else {
                    Toast.makeText(SalesQueryArpActivity.this, "无数据。。。", Toast.LENGTH_LONG)
                            .show();
                }
                final ArpInfos aDb1 = new Gson().fromJson(str_arp,ArpInfos.class);
                if(aDb1!=null){
                    SalesQueryArpActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            List<ArpInfos.Data> data_arp = aDb1.getData();
                            head_all = aDb1.getHead();
                            Log.e("LiNing", "---数据--" + data_arp );
                            Log.e("LiNing", "---标题--" + head_all );
                            if (head_all != null) {
                                String[] head_spls = head_all.split(",");

                                Log.e("LiNing", "=====" + head_all.split(","));
                                for (int i = 0; i < head_spls.length; i++) {
                                    String A = head_spls[i];
                                    Log.e("LiNing", "===A==" + A);
                                    arp_head_json.add(A);
                                }
                                Log.e("LiNing", "==ln===" + arp_head_json);
                            }
                            findViewById(R.id.one_zt).setVisibility(View.VISIBLE);
                            getFlagreportNo();
                            if(data_arp!=null){

                                adapter_arp=new ArpQueryInfosAdapter(count,R.layout.arp_dthead,data_arp,context);
                                lv_get_arp.setAdapter(adapter_arp);
                                adapter_arp.notifyDataSetChanged();
                            }
//                            else {
//                                Toast.makeText(SalesQueryArpActivity.this, "无数据。。。", Toast.LENGTH_LONG)
//                                        .show();
//                            }
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }
    public class ArpQueryInfosAdapter extends BaseAdapter{
        MyHScrollView scrollView1,headSrcrollView;
        int id_layout,num;
        List<ArpInfos.Data> infos_arp;
        LayoutInflater inflater;
        //item高亮显示
        private int selectItem = -1;
        public void setSelectItem(int selectItem) {
            this.selectItem = selectItem;
        }

        public ArpQueryInfosAdapter(int count, int arp_dthead, List<ArpInfos.Data> data_arp, Context context) {
            this.num=count;
            this.id_layout=arp_dthead;
            this.infos_arp=data_arp;
            this.inflater=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return infos_arp.size();
        }

        @Override
        public Object getItem(int position) {
            return infos_arp.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if(convertView==null){
                synchronized (SalesQueryArpActivity.this) {
                    convertView = inflater.inflate(id_layout, null);
                    holder = new ViewHolder();
                    scrow(convertView);
                    if (num == 0) {
                        holder.ll_one = (LinearLayout) convertView.findViewById(R.id.ll_one);
                        holder.ll_one.setVisibility(View.VISIBLE);
                        holder.db_sigle = (TextView) convertView.findViewById(R.id.one_zt);
                        holder.db_sigle.setVisibility(View.VISIBLE);
                        holder.time_sigle = (TextView) convertView.findViewById(R.id.one_time);
                        holder.money_sigle = (TextView) convertView.findViewById(R.id.one_money);
                    } else if (num == 1) {
                        holder.ll_two = (LinearLayout) convertView.findViewById(R.id.ll_two);
                        holder.ll_two.setVisibility(View.VISIBLE);
                        holder.db_sigle = (TextView) convertView.findViewById(R.id.one_zt);
                        holder.db_sigle.setVisibility(View.VISIBLE);
                        holder.jg_one = (TextView) convertView.findViewById(R.id.two_jg);
                        holder.time_sigle = (TextView) convertView.findViewById(R.id.two_time);
                        holder.money_sigle = (TextView) convertView.findViewById(R.id.two_money);
                    } else if (num == 2) {
                        holder.ll_three = (LinearLayout) convertView.findViewById(R.id.ll_three);
                        holder.ll_three.setVisibility(View.VISIBLE);
                        holder.db_sigle = (TextView) convertView.findViewById(R.id.one_zt);
                        holder.db_sigle.setVisibility(View.VISIBLE);
                        holder.jg_one = (TextView) convertView.findViewById(R.id.three_jg);
                        holder.jg_two = (TextView) convertView.findViewById(R.id.three_jg2);
                        holder.time_sigle = (TextView) convertView.findViewById(R.id.three_time);
                        holder.money_sigle = (TextView) convertView.findViewById(R.id.three_money);
                    } else if (num == 3) {
                        holder.ll_four = (LinearLayout) convertView.findViewById(R.id.ll_four);
                        holder.ll_four.setVisibility(View.VISIBLE);
                        holder.db_sigle = (TextView) convertView.findViewById(R.id.one_zt);
                        holder.db_sigle.setVisibility(View.VISIBLE);
                        holder.jg_one = (TextView) convertView.findViewById(R.id.four_jg);
                        holder.jg_two = (TextView) convertView.findViewById(R.id.four_jg2);
                        holder.jg_three = (TextView) convertView.findViewById(R.id.four_jg3);
                        holder.time_sigle = (TextView) convertView.findViewById(R.id.four_time);
                        holder.money_sigle = (TextView) convertView.findViewById(R.id.four_money);
                    }
                    convertView.setTag(holder);
                }
            }else {
                holder= (ViewHolder) convertView.getTag();
            }
            ArpInfos.Data data = infos_arp.get(position);
            Log.e("LiNing","数据是。。。"+num+"-----"+data.getAffiliateNo());
            if(num==0){
                Log.e("LiNing","数据是。。。"+num+"-----"+data.getAffiliateNo());
                holder.db_sigle.setText(data.getAffiliateNo());
                holder.time_sigle.setText(data.getBiln_DD());
                holder.money_sigle.setText(data.getSAmtn_ARPJ());
            }else if(num==1){
                holder.db_sigle.setText(data.getAffiliateNo());
                holder.time_sigle.setText(data.getBiln_DD());
                holder.money_sigle.setText(data.getSAmtn_ARPJ());
                if(reportnos.equals("ArpTGP")){
                    holder.jg_one.setText(data.getCompDepName());
                }
                if(reportnos.equals("ArpTGGC")){
                    holder.jg_one.setText(data.getSalesTerminalName());
                }
                if(reportnos.equals("ArpTGC")){
                    holder.jg_one.setText(data.getSalesChannelName());
                }
                if(reportnos.equals("ArpTGS")){
                    holder.jg_one.setText(data.getSellingOperationName());
                }
                if(reportnos.equals("ArpTGD")){
                    holder.jg_one.setText(data.getSalesDepartmentName());
                }
            }else if(num==2){
                holder.db_sigle.setText(data.getAffiliateNo());
                holder.time_sigle.setText(data.getBiln_DD());
                holder.money_sigle.setText(data.getSAmtn_ARPJ());
                holder.jg_one.setText(data.getCompDepName());
                if(reportnos.equals("ArpTGPC")){
                    holder.jg_two.setText(data.getSalesChannelName());
                }
                if(reportnos.equals("ArpTGPGC")){
                    holder.jg_two.setText(data.getSalesTerminalName());
                }
                if(reportnos.equals("ArpTGPS")){
                    holder.jg_two.setText(data.getSellingOperationName());
                }
                if(reportnos.equals("ArpTGPD")){
                    holder.jg_two.setText(data.getSalesDepartmentName());
                }
            }else if(num==3){
                holder.db_sigle.setText(data.getAffiliateNo());
                holder.time_sigle.setText(data.getBiln_DD());
                holder.money_sigle.setText(data.getSAmtn_ARPJ());
                holder.jg_one.setText(data.getCompDepName());
                holder.jg_three.setText(data.getSalesTerminalName());
                if(reportnos.equals("ArpTGPCGC")){
                    holder.jg_two.setText(data.getSalesChannelName());
                }
                if(reportnos.equals("ArpTGPDGC")){
                    holder.jg_two.setText(data.getSalesDepartmentName());
                }
                if(reportnos.equals("ArpTGPSGC")){
                    holder.jg_two.setText(data.getSellingOperationName());
                }
            }
            if (position == selectItem) {
                convertView.setBackgroundColor(Color.RED);
            } else {
                convertView.setBackgroundColor(Color.TRANSPARENT);
            }
            out_infos_arp=infos_arp;
            return convertView;
        }

        private void scrow(View convertView) {

            if(num==0){

                scrollView1 = (MyHScrollView) convertView
                        .findViewById(R.id.horizontalScrollView1);
                headSrcrollView = (MyHScrollView) mHead
                        .findViewById(R.id.horizontalScrollView1);
            }else  if(num==1){

                scrollView1 = (MyHScrollView) convertView
                        .findViewById(R.id.horizontalScrollView_two);
                headSrcrollView = (MyHScrollView) mHead
                        .findViewById(R.id.horizontalScrollView_two);
            }else  if(num==2){

                scrollView1 = (MyHScrollView) convertView
                        .findViewById(R.id.horizontalScrollView_three);
                headSrcrollView = (MyHScrollView) mHead
                        .findViewById(R.id.horizontalScrollView_three);
            }else  if(num==3){

                scrollView1 = (MyHScrollView) convertView
                        .findViewById(R.id.horizontalScrollView_four);
                headSrcrollView = (MyHScrollView) mHead
                        .findViewById(R.id.horizontalScrollView_four);
            }
            headSrcrollView
                    .AddOnScrollChangedListener(new OnScrollChangedListenerImp(
                            scrollView1));
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
        class ViewHolder{
            LinearLayout ll_one,ll_two,ll_three,ll_four;
            TextView db_sigle;
            TextView time_sigle;
            TextView money_sigle;
            TextView jg_one;
            TextView jg_two;
            TextView jg_three;
        }
    }
    private void getFlagreportNo() {
        zt= (TextView) findViewById(R.id.one_zt);
        zt.setText(arp_head_json.get(0));
        if(reportnos.equals("ArpTGGC")||reportnos.equals("ArpTGP")||reportnos.equals("ArpTGC")||
                reportnos.equals("ArpTGS")||reportnos.equals("ArpTGD")){
            findViewById(R.id.ll_two).setVisibility(View.VISIBLE);
            jg_1= (TextView) findViewById(R.id.two_jg);
            time_1= (TextView) findViewById(R.id.two_time);
            money_1= (TextView) findViewById(R.id.two_money);
            jg_1.setText(arp_head_json.get(1));
            time_1.setText(arp_head_json.get(2));
            money_1.setText(arp_head_json.get(3));
            count=1;
        }else  if(reportnos.equals("ArpTGPGC")||reportnos.equals("ArpTGPC")||
                reportnos.equals("ArpTGPS")||reportnos.equals("ArpTGPD")){
            findViewById(R.id.ll_three).setVisibility(View.VISIBLE);
            jg_2= (TextView) findViewById(R.id.three_jg);
            jg_2_2= (TextView) findViewById(R.id.three_jg2);
            time_2= (TextView) findViewById(R.id.three_time);
            money_2= (TextView) findViewById(R.id.three_money);
            jg_2.setText(arp_head_json.get(1));
            jg_2_2.setText(arp_head_json.get(2));
            time_2.setText(arp_head_json.get(3));
            money_2.setText(arp_head_json.get(4));
            count=2;
        }else  if(reportnos.equals("ArpTGPCGC")||reportnos.equals("ArpTGPDGC")||
                reportnos.equals("ArpTGPSGC")){
            findViewById(R.id.ll_four).setVisibility(View.VISIBLE);
            jg_3= (TextView) findViewById(R.id.four_jg);
            jg_3_3= (TextView) findViewById(R.id.four_jg2);
            jg_3_3_3= (TextView) findViewById(R.id.four_jg3);
            time_3= (TextView) findViewById(R.id.four_time);
            money_3= (TextView) findViewById(R.id.four_money);
            jg_3.setText(arp_head_json.get(1));
            jg_3_3.setText(arp_head_json.get(2));
            jg_3_3_3.setText(arp_head_json.get(3));
            time_3.setText(arp_head_json.get(4));
            money_3.setText(arp_head_json.get(5));
            count=3;
        }else  if(reportnos.equals("ArpTG")){
            findViewById(R.id.ll_one).setVisibility(View.VISIBLE);
            time= (TextView) findViewById(R.id.four_time);
            money= (TextView) findViewById(R.id.four_money);
            time.setText(arp_head_json.get(1));
            money.setText(arp_head_json.get(2));
            count=0;
        }
    }
    public void allback(View v) {
        finish();
    }
    // 表头滑动事件
    class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {
        ListViewAndHeadViewTouchLinstener() {
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //判断不同类型标题
            if(count==0){
                ((HorizontalScrollView) SalesQueryArpActivity.this.mHead
                        .findViewById(R.id.horizontalScrollView1))
                        .onTouchEvent(event);
            }else if(count==1){
                ((HorizontalScrollView) SalesQueryArpActivity.this.mHead
                        .findViewById(R.id.horizontalScrollView_two))
                        .onTouchEvent(event);
            }else if(count==2){
                ((HorizontalScrollView) SalesQueryArpActivity.this.mHead
                        .findViewById(R.id.horizontalScrollView_three))
                        .onTouchEvent(event);
            }else if(count==3){
                ((HorizontalScrollView) SalesQueryArpActivity.this.mHead
                        .findViewById(R.id.horizontalScrollView_four))
                        .onTouchEvent(event);
            }

            return false;
        }

    }
    private void getpopWindow() {
        popupWindow = new PopupWindow();
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        View view = LayoutInflater.from(this).inflate(R.layout.loding, null);
        popupWindow.setContentView(view);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER,
                0, 0);
    }
    private void getSpToNull() {
        sp.edit().putString("CSTOSNO_CDTA", "").commit();
        sp.edit().putString("REM_CDTA", "").commit();
        sp.edit().putString("REM_CDTA", "").commit();
        sp.edit().putString("HS_CDTA", "").commit();
        sp.edit().putString("DEP_CDTA", "").commit();
        sp.edit().putString("SUP_CDTA", "").commit();
        sp.edit().putString("CUST_CDTA", "").commit();
        sp.edit().putString("USER_CDTA", "").commit();
        sp.edit().putString("CHKUSER_CDTA", "").commit();
        sp.edit().putString("AREA_CDTA", "").commit();
        sp.edit().putString("EMPL_CDTA", "").commit();
        sp.edit().putString("PRDNO_CDTA", "").commit();
        sp.edit().putString("PRDINDEX_CDTA", "").commit();
        sp.edit().putString("PRDWH_CDTA", "").commit();
        sp.edit().putString("DB_CDTA", "").commit();
        sp.edit().putString("BILNO_CDTA", "").commit();
        sp.edit().putString("CUSTNO_CDTA", "").commit();
        sp.edit().putString("CSTNAME_CDTA", "").commit();
        sp.edit().putString("CSTP_CDTA", "").commit();
        sp.edit().putString("CSTARD_CDTA", "").commit();
        sp.edit().putString("INPUTNO_CDTA", "").commit();

    }
}
