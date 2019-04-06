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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.SaleMakeInfo;
import com.example.bean.URLS;
import com.example.bean.UserInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SalesPkActivity extends Activity implements View.OnClickListener {

    Context context;
    public String session;
    private SharedPreferences sp;
    private Button tb_all_zl, tb_all_zc, tb_all_dy, tb_all_jz, tb_sx_one, tb_sx_two, tb_dq_kstime, tb_db_kstime, tb_dq_jstime, tb_db_jstime;
    private ImageButton tb_zt_dq_xl, tb_zt_db_xl;
    private TextView head_tb, tb_db_dq, tb_db_db, tb_zl_name_dq, tb_zt_name_dq, tb_zl_name_db, tb_zt_name_db;
    int checked;
    String reportnos, str_id, hq_kssj_dq, hq_jssj_dq, hq_kssj_db, hq_jssj_db;
    String all_info_dq, all_info_db, db_ids, str_all;
    //reportBus + reportnos+reportname---SASATGP账套+品牌
    private ArrayList<String> querryDBs, querryDBs_all, bilNos, custNos,
            custNames, custPhones, custAdress, inputNos, cust_os, rems,
            prdMarks, query_deps, query_sups, query_custs, query_users,
            query_deps_all, query_sups_all, query_custs_all, query_users_all,
            chk_users, areas, query_hs, query_hs_all, employees, prdNos,
            prdIndexs, prdWhs, zts, zcs, dys, cbs, mls, mlls, jes, cbs_dj,
            mls_dj, mlls_dj, jes_dj;
    private FormBody body;
    String url = URLS.sale_url;
    private List<SaleMakeInfo.Data> data,data_db;
    SalesInfoAdapter sfadapter_dq;
    SalesInfoDbAdapter sfadapter_db;
    private ListView lv_get_dq, lv_get_db;
    String url_query = URLS.userInfo_url;
    ArrayList<String> modIds_get = new ArrayList<String>();
    private ArrayList<HashMap<String, Object>> dList;
    private HashMap<String, Object> item;
    private String ps_id,db_ID,mod_ID;
    private List<com.example.bean.UserInfo.User_Mod> user_Mod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sales_pk);
        context = SalesPkActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        initView();
        getRoot();
    }

    private void initView() {
        head_tb = (TextView) findViewById(R.id.all_head);
        tb_all_zl = (Button) findViewById(R.id.tb_btn_condition_zl);//种类
        tb_all_zc = (Button) findViewById(R.id.tb_btn_out);//转出
        tb_all_dy = (Button) findViewById(R.id.tb_btn_print);//打印
        tb_all_jz = (Button) findViewById(R.id.tb_jz);//加载
        tb_sx_one = (Button) findViewById(R.id.btn_gjsx_dq);//筛选1
        tb_sx_two = (Button) findViewById(R.id.btn_gjsx_bj);//筛选2
        tb_zt_dq_xl = (ImageButton) findViewById(R.id.ib_queryDb_dqzt);//下拉账套1
        tb_zt_db_xl = (ImageButton) findViewById(R.id.ib_queryDb_bjzt);//下拉账套2
        tb_dq_kstime = (Button) findViewById(R.id.btn_start_time);//开始时间1
        tb_dq_jstime = (Button) findViewById(R.id.btn_stop_time);//结束时间1
        tb_db_kstime = (Button) findViewById(R.id.btn_start_time_tb);//开始时间1
        tb_db_jstime = (Button) findViewById(R.id.btn_stop_time_tb);//结束时间2
        tb_db_dq = (TextView) findViewById(R.id.et_condition_dqDb);//当前账套
        tb_db_db = (TextView) findViewById(R.id.et_condition_bjDb);//对比账套
        tb_zl_name_dq = (TextView) findViewById(R.id.tb_zl_tv_name);//当前种类名称
        tb_zt_name_dq = (TextView) findViewById(R.id.tv_tb_db_dq);//当前账套名称
        tb_zl_name_db = (TextView) findViewById(R.id.tb_zl_tv_name_db);//对比种类名称
        tb_zt_name_db = (TextView) findViewById(R.id.tv_tb_db_db);//对比账套名称
        tb_zt_dq_xl.setOnClickListener(this);
        tb_zt_db_xl.setOnClickListener(this);
        tb_all_zl.setOnClickListener(this);
        tb_all_zc.setOnClickListener(this);
        tb_all_dy.setOnClickListener(this);
        tb_all_jz.setOnClickListener(this);
        tb_sx_one.setOnClickListener(this);
        tb_sx_two.setOnClickListener(this);
        tb_dq_kstime.setOnClickListener(this);
        tb_dq_jstime.setOnClickListener(this);
        tb_db_kstime.setOnClickListener(this);
        tb_db_jstime.setOnClickListener(this);
        lv_get_dq = (ListView) findViewById(R.id.tb_dqzt_lv);
        lv_get_db = (ListView) findViewById(R.id.tb_dbzt_lv);
        dList = new ArrayList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tb_btn_condition_zl:
                checked = 1;
                showPopupMenu(tb_all_zl);
                break;
            case R.id.tb_jz:
//                all_info_dq = sp.getString("sale_tjInfo", "");
//                Log.e("LiNing", "--更新数据---------2-" + all_info_dq);
                //同时加载对比数据
//                if (all_info_db != null && !all_info_db.equals("")) {
//                    getDbInfos();//成功加载当前数据后加载对比数据
//                } else {
//                    Toast.makeText(context, "对比数据为空", Toast.LENGTH_LONG).show();
//                }
                String name_bt = head_tb.getText().toString();
                if (name_bt.equals("帐套销售同比表")) {
                    reportnos = "SATGV";
                }
                if (name_bt.equals("帐套+品牌销售同比表")) {
                    reportnos = "SATGVP";
                }
                if (name_bt.equals("帐套+渠道销售同比表")) {
                    reportnos = "SATGVC";
                }
                if (name_bt.equals("帐套+部门销售同比表")) {
                    reportnos = "SATGVD";
                }
                if (name_bt.equals("帐套+网点销售同比表")) {
                    reportnos = "SATGVGC";
                }
                checked = 4;
                //判断是否有权限
//                Log.e("LiNing", "权限-" + reportnos.toString());
//                if (modIds_get.contains(reportnos.toString())) {
//                    Log.e("LiNing", "--modIds_get----值-" + modIds_get);
                    first_load();
//                }else{
//                    Toast.makeText(context, "此种类无权限", Toast.LENGTH_LONG).show();
//                }
//                getDbInfos();//成功加载当前数据后加载对比数据
                break;
            case R.id.ib_queryDb_dqzt:
                checked = 2;
                Intent intent = new Intent(context, CondicionInfoActivity.class);
                intent.putExtra("flag", "1");
                startActivityForResult(intent, 1);
                break;
            case R.id.ib_queryDb_bjzt:
                if (tb_db_dq.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择当前帐套", Toast.LENGTH_LONG).show();
                } else {
                    String name_bt_h = head_tb.getText().toString();
                    if (name_bt_h.equals("帐套销售同比表")) {
                        reportnos = "SATGV";
                    }
                    if (name_bt_h.equals("帐套+品牌销售同比表")) {
                        reportnos = "SATGVP";
                    }
                    if (name_bt_h.equals("帐套+渠道销售同比表")) {
                        reportnos = "SATGVC";
                    }
                    if (name_bt_h.equals("帐套+部门销售同比表")) {
                        reportnos = "SATGVD";
                    }
                    if (name_bt_h.equals("帐套+网点销售同比表")) {
                        reportnos = "SATGGVC";
                    }
                    checked = 3;
//                    first_load();
                    //判断是否有权限
                    Log.e("LiNing", "权限-" + reportnos.toString());
//                    if (modIds_get.contains(reportnos.toString())) {
//                        Log.e("LiNing", "--modIds_get----值-" + modIds_get);
//                        first_load();
//                    }else{
//                        Toast.makeText(context, "此种类无权限", Toast.LENGTH_LONG).show();
//                    }
//                    beforPost();
//                    all_info_dq = sp.getString("sale_tjInfo", "");
//                    Log.e("LiNing", "--更新数据--" + all_info_dq);


                    Intent intent_db = new Intent(context, CondicionInfoActivity.class);
                    intent_db.putExtra("flag", "1");
                    startActivityForResult(intent_db, 1);

                }
                break;
            case R.id.btn_gjsx_dq:
                if (tb_db_dq.getText().toString().equals("")) {
                    Toast.makeText(context, "账套不能为空", Toast.LENGTH_LONG).show();
                } else {
                    // 获取查询条件
                    Intent intent_tj_dq = new Intent(context, ConditionActivity.class);
                    intent_tj_dq.putExtra("page", "1");
                    intent_tj_dq.putExtra("dqzt_tb", tb_db_dq.getText().toString());
                    startActivity(intent_tj_dq);
                }
                break;
            case R.id.btn_gjsx_bj:
                if (tb_db_dq.getText().toString().equals("")) {
                    Toast.makeText(context, "账套不能为空", Toast.LENGTH_LONG).show();
                } else {
                    sp.edit().putString("sale_tjInfo", "").commit();
                    // 获取查询条件
                    Intent intent_tj_db = new Intent(context, ConditionActivity.class);
                    intent_tj_db.putExtra("page", "1");
                    intent_tj_db.putExtra("dqzt_tb", tb_db_db.getText().toString());
                    startActivity(intent_tj_db);
                }
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
                                tb_dq_kstime.setText(hq_kssj_dq);

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
                                tb_dq_jstime.setText(hq_jssj_dq);

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
                                tb_db_kstime.setText(hq_kssj_db);

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
                                tb_db_jstime.setText(hq_jssj_db);

                            }
                        }, mYear_db, mMonth_db, mDay_db).show();//获取当前时间
                break;

            default:
                break;
        }

    }

    private void first_load() {
        all_info_dq = sp.getString("sale_tjInfo", "");
        Log.e("LiNing", "--更新数据-----" + all_info_dq);
        if (all_info_dq != null && !all_info_dq.equals("")) {
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
                JSONArray arr = new JSONArray(all_info_dq);
                int size = arr.length();
                for (int i = 0; i < size; i++) {
                    JSONObject jsonObject = arr.getJSONObject(i);
                    db_ids = jsonObject.get("账套").toString();
                    if (db_ids.equals("") || db_ids.equals("null")
                            || db_ids.equals("All")) {
                        Toast.makeText(context, "请选择单一账套", Toast.LENGTH_LONG).show();
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
            if(checked==4){
                getDbInfos();
            }else{
                beforPost();
            }

        } else {
            Toast.makeText(context, "数据不能为空", Toast.LENGTH_LONG).show();
            // 空数据就是全部。。。
        }
    }

    private void beforPost() {
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
//        Log.e("LiNing", "-----" + start.getText().toString() + "-----"
//                + sub_querys_db + "-----" + sub_query_deps);

        OkHttpClient client = new OkHttpClient();
        // if (flag_index == 1) {

        body = new FormBody.Builder()
                // .add("reprotNo", reportNo_get)
                .add("reprotNo", reportnos)
                .add("beginDate", tb_dq_kstime.getText().toString())
                .add("endDate", tb_dq_jstime.getText().toString())
                .add("reporyBusiness", "SA")
                .add("isGroupSum", "T")
                .add("query_DB", db_ids).add("bilNo", sub_bils)
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
        Log.e("LiNing", "-当前数据----" + body.toString());
//        Log.e("LiNing", "-----" + reportnos+"---888"+start.getText().toString()+"---888"+reportBus+"---888"+arp_sum+"---888"+sub_querys_db
//                +"---888"+sub_bils+"---888"+sub_custNos+"---888"+sub_custNames+"---888"+sub_custPhones+"---888"+sub_custAdress+"---888"+sub_inputNos
//                +"---888"+sub_cust_os+"---888"+sub_rems+"---888"+sub_prdMarks+"---888"+sub_query_deps+"---888"+sub_query_hss+"---888"+sub_query_sups
//                +"---888"+sub_query_custs+"---888"+sub_query_users+"---888"+sub_chk_users+"---888"+sub_areas+"---888"+sub_employees
//                +"---888"+sub_prdNos+"---888"+sub_prdIndexs+"---888"+sub_prdWhs);
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {

                str_all = response.body().string();
                Log.e("LiNing", "-----" + str_all + "-----" + response.code());
                Log.e("LiNing", "-----" + response.code());
//                if (response.code() == 200) {
//                    sp.edit().putString("sale_tjInfo", "").commit();
//                }
                if (str_all != null) {
                    SalesPkActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                        }
                    });
                } else {
                    Toast.makeText(SalesPkActivity.this, "无数据。。。", Toast.LENGTH_LONG)
                            .show();
                }
                final SaleMakeInfo aDb1 = new Gson().fromJson(str_all,
                        SaleMakeInfo.class);
                if (aDb1 != null) {
                    SalesPkActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            data = aDb1.getData();
//                            head_all = aDb1.getHead();
                            Log.e("LiNing", "========" + data.size());
                            if (data != null && data.size() > 0) {
                                showlist();
//                                //同时加载对比数据
//                                if (all_info_db != null && !all_info_db.equals("")) {
//                                    getDbInfos();//成功加载当前数据后加载对比数据
//                                }else{
//                                    Toast.makeText(context,"对比数据为空",Toast.LENGTH_LONG).show();
//                                }
                            } else {
                                Toast.makeText(context, "无数据", Toast.LENGTH_LONG).show();
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

    private void getDbInfos() {
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
        OkHttpClient client = new OkHttpClient();
        // if (flag_index == 1) {

        body = new FormBody.Builder()
                // .add("reprotNo", reportNo_get)
                .add("reprotNo", reportnos)
                .add("beginDate", tb_db_kstime.getText().toString())
                .add("endDate", tb_db_jstime.getText().toString())
                .add("reporyBusiness", "SA")
                .add("isGroupSum", "T")
                .add("query_DB", db_ids).add("bilNo", sub_bils)
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
//        Log.e("LiNing", "-----" + reportnos+"---888"+start.getText().toString()+"---888"+reportBus+"---888"+arp_sum+"---888"+sub_querys_db
//                +"---888"+sub_bils+"---888"+sub_custNos+"---888"+sub_custNames+"---888"+sub_custPhones+"---888"+sub_custAdress+"---888"+sub_inputNos
//                +"---888"+sub_cust_os+"---888"+sub_rems+"---888"+sub_prdMarks+"---888"+sub_query_deps+"---888"+sub_query_hss+"---888"+sub_query_sups
//                +"---888"+sub_query_custs+"---888"+sub_query_users+"---888"+sub_chk_users+"---888"+sub_areas+"---888"+sub_employees
//                +"---888"+sub_prdNos+"---888"+sub_prdIndexs+"---888"+sub_prdWhs);
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {

                str_all = response.body().string();
                Log.e("LiNing", "-----" + str_all + "-----" + response.code());
                Log.e("LiNing", "-----" + response.code());
//                if (response.code() == 200) {
//                    sp.edit().putString("sale_tjInfo", "").commit();
//                }
                if (str_all != null) {
                    SalesPkActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                        }
                    });
                } else {
                    Toast.makeText(SalesPkActivity.this, "无数据。。。", Toast.LENGTH_LONG)
                            .show();
                }
                final SaleMakeInfo aDb1 = new Gson().fromJson(str_all,
                        SaleMakeInfo.class);
                if (aDb1 != null) {
                    SalesPkActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            data_db = aDb1.getData();
//                            head_all = aDb1.getHead();
//                            Log.e("LiNing", "========" + data.size());
                            if (data_db != null && data_db.size() > 0) {
                                showlist_db();
//                                //同时加载对比数据
//                                if (all_info_db != null && !all_info_db.equals("")) {
//                                    getDbInfos();//成功加载当前数据后加载对比数据
//                                }else{
//                                    Toast.makeText(context,"对比数据为空",Toast.LENGTH_LONG).show();
//                                }
                            } else {
                                Toast.makeText(context, "无数据", Toast.LENGTH_LONG).show();
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

    private void showlist_db() {
        sfadapter_db = new SalesInfoDbAdapter(R.layout.tb_dbzt, context, data_db, reportnos);
        lv_get_db.setAdapter(sfadapter_db);
        sfadapter_db.notifyDataSetChanged();
    }

    private void showlist() {
//        settoOut();
//        out_one_no = (List<SaleMakeInfo.Data>) data;
        sfadapter_dq = new SalesInfoAdapter(R.layout.tb_dqzt, context, data, reportnos);
        lv_get_dq.setAdapter(sfadapter_dq);
        sfadapter_dq.notifyDataSetChanged();
//        sp.edit().putString("NEWDATA_NO", "" + data).commit();
    }

    public class SalesInfoAdapter extends BaseAdapter {
        int id_row_layout;
        Context context;
        List<SaleMakeInfo.Data> tb_data_dq;
        LayoutInflater mInflater;
        String index;

        public SalesInfoAdapter(int tb_dqzt, Context context, List<SaleMakeInfo.Data> data, String reportnos) {
            this.id_row_layout = tb_dqzt;
            this.context = context;
            this.tb_data_dq = data;
            this.mInflater = LayoutInflater.from(context);
            this.index = reportnos;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
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
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            SaleMakeInfo.Data data_dq_infos = SalesPkActivity.this.data.get(position);
            Log.e("LiNing", "----------" + data.size());
            if (index.equals("SATGV")) {

                holder.tb_dq_data_zl.setText(data_dq_infos.getAffiliateName());
            }
            if (index.equals("SATGVP")) {

                holder.tb_dq_data_zl.setText(data_dq_infos.getBrandName());

            }
            if (index.equals("SATGVC")) {

                holder.tb_dq_data_zl.setText(data_dq_infos.getSalesChannelName());
            }
            if (index.equals("SATGVD")) {

                holder.tb_dq_data_zl.setText(data_dq_infos.getSalesDepartmentName());
            }
            if (index.equals("SATGVGC")) {

                holder.tb_dq_data_zl.setText(data_dq_infos.getSalesTerminalName());
            }
            holder.tb_dq_sn_amtn.setText(data_dq_infos.getSnAmtn());
            holder.tb_dq_gp.setText(data_dq_infos.getgP());
            holder.tb_dq_gpm.setText(data_dq_infos.getgPM());
            return convertView;
        }

        public class ViewHolder {
            TextView tb_dq_data_zl;// 分支机构
            TextView tb_dq_sn_amtn;// 净销售额
            TextView tb_dq_gp;// 销售毛利
            TextView tb_dq_gpm;// 毛利率
//            TextView brand;// 品牌
//            TextView sales_channels;// 渠道
//            TextView sales_dep;// 部门
//            TextView sales_oper;// 业务
//            TextView sales_ter;// 终端

        }
    }
    public class SalesInfoDbAdapter extends BaseAdapter {
        int id_row_layout;
        Context context;
        List<SaleMakeInfo.Data> tb_data_db;
        LayoutInflater mInflater;
        String index;

        public SalesInfoDbAdapter(int tb_dbzt, Context context, List<SaleMakeInfo.Data> data_db, String reportnos) {
            this.id_row_layout = tb_dbzt;
            this.context = context;
            this.tb_data_db = data_db;
            this.mInflater = LayoutInflater.from(context);
            this.index = reportnos;
        }


        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
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
            SaleMakeInfo.Data data_db_infos = SalesPkActivity.this.data_db.get(position);
            Log.e("LiNing", "----------" + data_db.size());
            if (index.equals("SATGV")) {

                holder.tb_db_data_zl.setText(data_db_infos.getAffiliateName());
            }
            if (index.equals("SATGVP")) {

                holder.tb_db_data_zl.setText(data_db_infos.getBrandName());

            }
            if (index.equals("SATGVC")) {

                holder.tb_db_data_zl.setText(data_db_infos.getSalesChannelName());
            }
            if (index.equals("SATGVD")) {

                holder.tb_db_data_zl.setText(data_db_infos.getSalesDepartmentName());
            }
            if (index.equals("SATGVGC")) {

                holder.tb_db_data_zl.setText(data_db_infos.getSalesTerminalName());
            }
            holder.tb_db_sn_amtn.setText(data_db_infos.getSnAmtn());
            holder.tb_db_gp.setText(data_db_infos.getgP());
            holder.tb_db_gpm.setText(data_db_infos.getgPM());
            Log.e("LiNing","净销售额率----"+data.get(position).getSnAmtn());
            //对比
            if(data.size()>0&&data!=null){

                SaleMakeInfo.Data data_dq_infos = SalesPkActivity.this.data.get(position);
                if (index.equals("SATGVP")) {
                    if(data_db_infos.getBrandName().toString().equals(data_dq_infos.getBrandName().toString())){
//                        String db_jxse = data_db_infos.getSnAmtn().toString();
//                        String dq_jxse = data_dq_infos.getSnAmtn().toString();
//                        BigDecimal d_jxse_q = new BigDecimal(dq_jxse);
//                        BigDecimal d_jxse_h = new BigDecimal(db_jxse);
//                        BigDecimal c_jxse = d_jxse_h.subtract(d_jxse_q);
//                        Log.e("LiNing","净销售额率--cc--"+c_jxse);
//                        double v = c_jxse.divide(d_jxse_q, BigDecimal.ROUND_HALF_UP).doubleValue();
//                        Log.e("LiNing","净销售额率----"+v);
//                        holder.tb_db_xs_zzl.setText(String.valueOf(v));
                        //公式套用
                        ll_c_tb(holder, data_db_infos, data_dq_infos);
                    }else {
                        holder.tb_db_xs_zzl.setText("");
                        holder.tb_db_gp_zzl.setText("");
                        holder.tb_db_gpm_c.setText("");
                    }
                }
                if (index.equals("SATGV")) {
                    if(data_db_infos.getAffiliateName().toString().equals(data_dq_infos.getAffiliateName().toString())){
                        //公式套用
                        ll_c_tb(holder, data_db_infos, data_dq_infos);

                    }else {
                        holder.tb_db_xs_zzl.setText("");
                        holder.tb_db_gp_zzl.setText("");
                        holder.tb_db_gpm_c.setText("");
                    }
                }
                if (index.equals("SATGVC")) {
                    if(data_db_infos.getSalesChannelName().toString().equals(data_dq_infos.getSalesChannelName().toString())){
                        //公式套用
                        ll_c_tb(holder, data_db_infos, data_dq_infos);

                    }else {
                        holder.tb_db_xs_zzl.setText("");
                        holder.tb_db_gp_zzl.setText("");
                        holder.tb_db_gpm_c.setText("");
                    }
                }
                if (index.equals("SATGVD")) {
                    if(data_db_infos.getSalesDepartmentName().toString().equals(data_dq_infos.getSalesDepartmentName().toString())){
                        //公式套用
                        ll_c_tb(holder, data_db_infos, data_dq_infos);

                    }else {
                        holder.tb_db_xs_zzl.setText("");
                        holder.tb_db_gp_zzl.setText("");
                        holder.tb_db_gpm_c.setText("");
                    }
                }
                if (index.equals("SATGVGC")) {
                    if(data_db_infos.getSalesTerminalName().toString().equals(data_dq_infos.getSalesTerminalName().toString())){
                        //公式套用
                        ll_c_tb(holder, data_db_infos, data_dq_infos);

                    }else {
                        holder.tb_db_xs_zzl.setText("");
                        holder.tb_db_gp_zzl.setText("");
                        holder.tb_db_gpm_c.setText("");
                    }
                }

            }
            return convertView;
        }

        private void ll_c_tb(ViewHolder holder, SaleMakeInfo.Data data_db_infos, SaleMakeInfo.Data data_dq_infos) {
            //销售增长率
            BigDecimal d_jxse_q = new BigDecimal(data_dq_infos.getSnAmtn().toString());
            BigDecimal d_jxse_h = new BigDecimal(data_db_infos.getSnAmtn().toString());
            BigDecimal c_jxse = d_jxse_h.subtract(d_jxse_q);
            Log.e("LiNing","净销售额率--cc--"+c_jxse);
            double v = c_jxse.divide(d_jxse_q, BigDecimal.ROUND_HALF_UP).doubleValue();
            Log.e("LiNing","净销售额率----"+v);
            holder.tb_db_xs_zzl.setText(String.valueOf(v));
            //毛利增长率
            BigDecimal d_ml_q = new BigDecimal(data_dq_infos.getgP().toString());
            BigDecimal d_ml_h = new BigDecimal(data_db_infos.getgP().toString());
            BigDecimal c_ml = d_ml_h.subtract(d_ml_q);
            Log.e("LiNing","毛利增长率--cc--"+c_ml);
            double v_ml = c_ml.divide(d_ml_q, BigDecimal.ROUND_HALF_UP).doubleValue();
            Log.e("LiNing","毛利增长率----"+v_ml);
            holder.tb_db_gp_zzl.setText(String.valueOf(v_ml));
            //毛利率差
            String gpm_rp = data_dq_infos.getgPM().toString().replace("%", "");
            double v1 = Double.parseDouble(gpm_rp);
            double v2 = v1 * 0.01;
            BigDecimal b1 = new BigDecimal(Double.toString(v2));
            double v3 = Double.parseDouble(data_db_infos.getgPM().toString().replace("%", ""));
            double v4 = v3 * 0.01;
            BigDecimal b2 = new BigDecimal(Double.toString(v4));
            double v5 = b1.subtract(b2).doubleValue();
            Log.e("LiNing","毛利率差----"+v5);
//            BigDecimal d_mll_q = new BigDecimal(data_dq_infos.getgPM().toString());
//            BigDecimal d_mll_h = new BigDecimal(data_db_infos.getgPM().toString());
//            BigDecimal c_mll = d_mll_h.subtract(d_mll_q);
//            Log.e("LiNing","毛利增长率--cc--"+c_mll);
//                        double v_ml = c_ml.divide(d_ml_q, BigDecimal.ROUND_HALF_UP).doubleValue();
//                        Log.e("LiNing","毛利率差----"+v_ml);
            holder.tb_db_gpm_c.setText(String.valueOf(v5));
        }

        public class ViewHolder {
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
    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu布局
        if (checked == 1) {

            popupMenu.getMenuInflater().inflate(R.menu.tbzl, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if (menuItem.getItemId() == R.id.tb_check1
                            || menuItem.getItemId() == R.id.tb_check2
                            || menuItem.getItemId() == R.id.tb_check3
                            || menuItem.getItemId() == R.id.tb_check4
                            || menuItem.getItemId() == R.id.tb_check5
                            ) {
                        menuItem.setChecked(!menuItem.isChecked());
                        head_tb.setText(menuItem.getTitle());
                        if (menuItem.getTitle().toString().equals("账套")) {
                            tb_zl_name_dq.setText(menuItem.getTitle().toString());
                            tb_zl_name_db.setText(menuItem.getTitle().toString());
                        } else {

                            String name_lbxs = menuItem.getTitle().toString().substring(3);
                            tb_zl_name_dq.setText(name_lbxs);
                            tb_zl_name_db.setText(name_lbxs);
                        }
                        return true;
                    }
                    return false;
                }
            });
            popupMenu.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    str_id = data.getStringExtra("condition_db");
                    String str_name = data.getStringExtra("condition_name");
                    if (checked == 2) {
                        tb_db_dq.setText(str_id);
                        tb_zt_name_dq.setText(str_id);
                    }
                    if (checked == 3) {
                        tb_db_db.setText(str_id);
                        tb_zt_name_db.setText(str_id);
                    }
//					if(head_sale.getText().equals("对比数据")){
//						db_Db.setText(str_id);
//					}
                    Log.e("LiNing", "提交的id====" + str_id + str_name);
                    // sp.edit().putString("USERIDquerry", str1).commit();
                }
                break;
            default:
                break;
        }
    }
    private void getRoot() {
        OkHttpClient client = new OkHttpClient();
        Request localRequest = new Request.Builder()
                .addHeader("cookie", session).url(url_query).build();
        client.newCall(localRequest).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                String string = response.body().string();
//				sp.edit().putString("all_query_tj", string).commit();
                Gson gson = new GsonBuilder().setDateFormat(
                        "yyyy-MM-dd HH:mm:ss").create();
                UserInfo info = gson.fromJson(string, UserInfo.class);
//				//查询
                List<UserInfo.User_Query> user_Query = info.getUser_Query();
                String query_json = new Gson().toJson(user_Query);
                sp.edit().putString("all_query", query_json).commit();
                Log.e("LiNing", "提交保存的数据====" + query_json);
                //模块
                user_Mod = info.getUser_Mod();
                Log.e("LiNing", "===user_Mod=====" + user_Mod);
                if (user_Mod.size() > 0 && user_Mod != null) {
                    for (int i = 0; i < user_Mod.size(); i++) {
                        mod_ID = user_Mod.get(i).getMod_ID();
                        modIds_get.add(mod_ID);

                    }
//                    if (dList != null && dList.size() > 0) {
//                        Log.e("LiNing", ",,,,,,," + dList.toString());
//                        String stritem = new Gson().toJson(dList);
//                        sp.edit().putString("CBQX", stritem).commit();
//                        Log.e("LiNing", "提交的数据是" + sp.getString("CBQX", ""));
//                    }

                }
            }

            @Override
            public void onFailure(Call arg0, IOException arg1) {

            }
        });
    }
    public void allback(View v) {
        finish();
    }


}
