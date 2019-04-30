package com.example.ydshoa;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.LeftOrRight.MyHScrollView;
import com.example.bean.SaleMakeInfo;
import com.example.bean.TbSales;
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

public class TbSalesActivity extends Activity implements View.OnClickListener {
    Context context;
    public String session;
    private SharedPreferences sp;
    private Button tb_all_zl, tb_all_zc, tb_all_dy, tb_all_jz, tb_sx_one, tb_sx_two, tb_dq_kstime, tb_db_kstime, tb_dq_jstime, tb_db_jstime;
    private ImageButton tb_zt_dq_xl, tb_zt_db_xl,tj_head_show;
    private TextView head_tb, tb_db_dq, tb_db_db, tb_zl_name_dq, tb_zt_name_dq, tb_zl_name_db, tb_zt_name_db;
    int checked,gjss_num,ischeck_gj;
    String reportnos_qx,reportnos, str_id, str_name,str_id_db, str_name_db, hq_kssj_dq, hq_jssj_dq, hq_kssj_db, hq_jssj_db;
    String all_info_dq, all_info_dq_db, db_ids, str_all;
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
    private List<SaleMakeInfo.Data> data, data_db;
    SalesInfoDbAdapter sfadapter_db;
    private ListView lv_get_one;
    public static List<TbSales> info_out = new ArrayList<TbSales>();
    String url_query = URLS.userInfo_url;
    ArrayList<String> modIds_get = new ArrayList<String>();
    private ArrayList<HashMap<String, Object>> dList;
    private HashMap<String, Object> item;
    private String ps_id,db_ID,mod_ID;
    private List<com.example.bean.UserInfo.User_Mod> user_Mod;
    LinearLayout mHead,tj_head_hint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tb_sales);
        context = TbSalesActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        initView();
        getRoot();
    }

    private void initView() {
        head_tb = (TextView) findViewById(R.id.all_head);
        tj_head_show = (ImageButton) findViewById(R.id.ib_nulladd);
        tj_head_hint = (LinearLayout) findViewById(R.id.ll_tb_show);
        mHead = (LinearLayout) findViewById(R.id.sales_scrowHead);
        mHead.setFocusable(true);
        mHead.setClickable(true);
        mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());

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
        lv_get_one = (ListView) findViewById(R.id.tb_dbzt_lv_one);
        lv_get_one.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
//        lv_get_db = (ListView) findViewById(R.id.tb_dbzt_lv);
        dList = new ArrayList();
        tj_head_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tj_head_hint.isShown()){
                    tj_head_hint.setVisibility(View.GONE);
                }else{
                    tj_head_hint.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    // 表头滑动事件
    class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {
        ListViewAndHeadViewTouchLinstener() {
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ((HorizontalScrollView) TbSalesActivity.this.mHead
                    .findViewById(R.id.horizontalScrollView1))
                    .onTouchEvent(event);
            return false;
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tb_btn_condition_zl:
                checked = 1;
                showPopupMenu(tb_all_zl);
                break;
            case R.id.tb_btn_out:
                if(info_out!=null&&info_out.size()>0){

                    Intent intent2 = new Intent(context, SalesOutInfoActivity.class);
//                intent2.putExtra("Stime", start.getText().toString());
                    Log.e("LiNing", "传递数据====" + info_out.size() + "===" + info_out);
                    startActivity(intent2);
                }else{
                    Toast.makeText(context, "请加载转出数据", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.tb_jz:
                if (tb_db_dq.getText().toString().equals("")
                        || tb_db_db.getText().toString().equals("")
                        || tb_dq_kstime.getText().toString().equals("")
                        || tb_dq_jstime.getText().toString().equals("")
                        || tb_db_kstime.getText().toString().equals("")
                        || tb_db_jstime.getText().toString().equals("")
                        ) {
                    Toast.makeText(context, "请选择当前账套", Toast.LENGTH_LONG).show();
                } else {

                    head_bt_sale();
                    tb_zt_name_dq.setText(str_name+"/"+hq_kssj_dq+"/"+hq_jssj_dq);
//                    tb_zt_name_db.setText(str_name+"/"+hq_kssj_db+"/"+hq_jssj_db);
                    //判断是否有权限
                Log.e("LiNing", "权限-" + reportnos.toString());
                Log.e("LiNing", "modIds_get权限-" + modIds_get);
                if (modIds_get.contains(reportnos_qx)) {
                    Log.e("LiNing", "--modIds_get----值-" + modIds_get);
                    checked = 4;
                    first_load();
                }else{
                    Toast.makeText(context, "此种类无权限", Toast.LENGTH_LONG).show();
                }

                }
//                getDbInfos();//成功加载当前数据后加载对比数据
                break;
            case R.id.ib_queryDb_dqzt:
//                head_bt_sale();
                checked = 2;
                Intent intent = new Intent(context, CondicionInfoActivity.class);
                intent.putExtra("flag", "1");
                startActivityForResult(intent, 1);
                break;
            case R.id.ib_queryDb_bjzt:
//                head_bt_sale();
                if (tb_db_dq.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择当前账套", Toast.LENGTH_LONG).show();
                } else {
                    head_bt_sale();
                    checked = 3;
//                    first_load();


                    Intent intent_db = new Intent(context, CondicionInfoActivity.class);
                    intent_db.putExtra("flag", "1");
                    startActivityForResult(intent_db, 1);

                }
                break;
            case R.id.btn_gjsx_dq:
                ischeck_gj=1;
                if (tb_db_dq.getText().toString().equals("")) {
                    Toast.makeText(context, "账套不能为空", Toast.LENGTH_LONG).show();
                } else {

                    gjss_num=1;
                    // 获取查询条件
                    Intent intent_tj_dq = new Intent(context, ConditionActivity.class);
                    intent_tj_dq.putExtra("page", "1");
//                    intent_tj_dq.putExtra("dqzt_tb", tb_db_dq.getText().toString());//显示名称，传id
                    intent_tj_dq.putExtra("dqzt_tb", str_id);//显示名称，传id
                    intent_tj_dq.putExtra("gjss", "1");//显示名称，传id
                    startActivity(intent_tj_dq);
                }
                break;
            case R.id.btn_gjsx_bj:
                ischeck_gj=1;
                if (tb_db_dq.getText().toString().equals("")) {
                    Toast.makeText(context, "账套不能为空", Toast.LENGTH_LONG).show();
                } else {
                    gjss_num=2;
                    tb_zt_name_dq.setText(tb_db_dq.getText().toString() + "/" + hq_kssj_dq + "/" + hq_jssj_dq);
//                    sp.edit().putString("sale_tjInfo", "").commit();
                    // 获取查询条件
                    Intent intent_tj_db = new Intent(context, ConditionActivity.class);
                    intent_tj_db.putExtra("page", "1");
//                    intent_tj_db.putExtra("dqzt_tb", tb_db_db.getText().toString());//显示名称，传id
                    intent_tj_db.putExtra("dqzt_tb", str_id_db);//显示名称，传id
                    intent_tj_db.putExtra("gjss", "2");//显示名称，传id
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

    private void head_bt_sale() {
        String name_bt = head_tb.getText().toString();
//        if (name_bt.equals("账套")) {
//            reportnos = "SATG";
//        }
//        if (name_bt.equals("账套+品牌")) {
//            reportnos = "SATGP";
//        }
//        if (name_bt.equals("账套+渠道")) {
//            reportnos = "SATGC";
//        }
//        if (name_bt.equals("账套+部门")) {
//            reportnos = "SATGD";
//        }
//        if (name_bt.equals("账套+网点")) {
//            reportnos = "SATGGC";
//        }
        if (name_bt.equals("帐套销售同比表")) {
            reportnos_qx="SATGV";
            reportnos = "SATG";
        }
        if (name_bt.equals("帐套+品牌销售同比表")) {
            reportnos_qx="SATGVP";
            reportnos = "SATGP";
        }
        if (name_bt.equals("帐套+渠道销售同比表")) {
            reportnos_qx="SATGVC";
            reportnos = "SATGC";
        }
        if (name_bt.equals("帐套+部门销售同比表")) {
            reportnos_qx="SATGVD";
            reportnos = "SATGD";
        }
        if (name_bt.equals("帐套+网点销售同比表")) {
            reportnos_qx="SATGVGC";
            reportnos = "SATGGC";
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
//            if (checked == 4) {
//                getDbInfos();
//            } else {
//                beforPost();
//            }
            beforPost();
        } else {
            postNull();
//            Toast.makeText(context, "数据不能为空", Toast.LENGTH_LONG).show();
            // 空数据就是全部。。。
        }
    }

    private void postNull() {
        OkHttpClient client = new OkHttpClient();
        // if (flag_index == 1) {
        Log.e("LiNing", "-当前数据----" + str_id);
        FormBody body_null = new FormBody.Builder()
                // .add("reprotNo", reportNo_get)
                .add("reprotNo", reportnos)
                .add("beginDate", tb_dq_kstime.getText().toString())
                .add("endDate", tb_dq_jstime.getText().toString())
                .add("reporyBusiness", "SA")
                .add("isGroupSum", "T")
                .add("query_DB", str_id).add("bilNo", "")
                .add("custNo", "").add("custName", "").add("custPhone", "")
                .add("custAddress", "").add("inputNo", "")
                .add("cust_OS_NO", "").add("rem", "").add("prdMark", "")
                .add("query_CompDep", "")
                .add("query_Dep", "")
                .add("query_Sup", "")
                .add("query_Cust", "")
                .add("query_User", "").add("chk_User", "ALL")
                .add("area", "ALL").add("employee", "ALL").add("prdNO", "ALL")
                .add("prdIndx", "ALL").add("prdWh", "ALL").build();
        Request request = new Request.Builder().addHeader("cookie", session)
                .url(url).post(body_null).build();
        Log.e("LiNing", "-当前数据----" + body_null.toString());

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
                    TbSalesActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                        }
                    });
                } else {
                    Toast.makeText(TbSalesActivity.this, "无数据。。。", Toast.LENGTH_LONG)
                            .show();
                }
                final SaleMakeInfo aDb1 = new Gson().fromJson(str_all,
                        SaleMakeInfo.class);
                if (aDb1 != null) {
                    TbSalesActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            data = aDb1.getData();
//                            head_all = aDb1.getHead();
                            Log.e("LiNing", "========" + data.size()+data);

                            if (data != null && data.size() > 0) {
//                                String s = data.get(0).getAffiliateName().toString();
//                                tb_zt_name_db.setText(s + "/" + hq_kssj_dq + "/" + hq_jssj_dq);
//                                showlist();//暂时不显示
//                                //同时加载对比数据
//                                if (all_info_db != null && !all_info_db.equals("")) {
//                                    getDbInfos();//成功加载当前数据后加载对比数据
//                                }else{
//                                    Toast.makeText(context,"对比数据为空",Toast.LENGTH_LONG).show();
//                                }
//                                tb_zt_name_dq.setText(data.get(0).getAffiliateName().toString() + "/" + hq_kssj_dq + "/" + hq_jssj_dq);
                                two_load_null();
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

    private void two_load_null() {
        OkHttpClient client = new OkHttpClient();
        // if (flag_index == 1) {
        Log.e("LiNing", "---db_ids--" + str_id_db);
        FormBody body_null2 = new FormBody.Builder()
                // .add("reprotNo", reportNo_get)
                .add("reprotNo", reportnos)
                .add("beginDate", tb_db_kstime.getText().toString())
                .add("endDate", tb_db_jstime.getText().toString())
                .add("reporyBusiness", "SA")
                .add("isGroupSum", "T")
                .add("query_DB", str_id_db).add("bilNo", "")
                .add("custNo", "").add("custName", "").add("custPhone", "")
                .add("custAddress", "").add("inputNo", "")
                .add("cust_OS_NO", "").add("rem", "").add("prdMark", "")
                .add("query_CompDep", "")
                .add("query_Dep", "")
                .add("query_Sup", "")
                .add("query_Cust", "")
                .add("query_User", "").add("chk_User", "ALL")
                .add("area", "ALL").add("employee", "ALL").add("prdNO", "ALL")
                .add("prdIndx", "ALL").add("prdWh", "ALL").build();
        Request request = new Request.Builder().addHeader("cookie", session)
                .url(url).post(body_null2).build();
        Log.e("LiNing", "-----" + body_null2.toString());

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
                    TbSalesActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                        }
                    });
                } else {
                    Toast.makeText(TbSalesActivity.this, "无数据。。。", Toast.LENGTH_LONG)
                            .show();
                }
                final SaleMakeInfo aDb1 = new Gson().fromJson(str_all,
                        SaleMakeInfo.class);
                if (aDb1 != null) {
                    TbSalesActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            data_db = aDb1.getData();
//                            head_all = aDb1.getHead();
//                            Log.e("LiNing", "========" + data.size());
                            if (data_db != null && data_db.size() > 0) {
//                                tb_zt_name_dq.setText(tb_db_dq.getText().toString() + "/" + hq_kssj_dq + "/" + hq_jssj_dq);

                                tb_zt_name_db.setText(data_db.get(0).getAffiliateName().toString() + "/" + hq_kssj_db + "/" + hq_jssj_db);
                                compareInfos();
//                                showlist_db();
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

    private void two_load() {
        all_info_dq_db = sp.getString("sale_tjInfo_db", "");
        Log.e("LiNing", "--更新数据-----" + all_info_dq_db);
        if (all_info_dq_db != null && !all_info_dq_db.equals("")) {
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
                JSONArray arr = new JSONArray(all_info_dq_db);
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
//            if (checked == 4) {
//                getDbInfos();
//            } else {
//                beforPost();
//            }
            getDbInfos();
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
                .add("query_DB", str_id).add("bilNo", sub_bils)
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
        Log.e("LiNing", "-db_ids----" + db_ids);
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
                    TbSalesActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                        }
                    });
                } else {
                    Toast.makeText(TbSalesActivity.this, "无数据。。。", Toast.LENGTH_LONG)
                            .show();
                }
                final SaleMakeInfo aDb1 = new Gson().fromJson(str_all,
                        SaleMakeInfo.class);
                if (aDb1 != null) {
                    TbSalesActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            data = aDb1.getData();
//                            head_all = aDb1.getHead();
                            Log.e("LiNing", "========" + data.size()+data);

                            if (data != null && data.size() > 0) {
//                                String s = data.get(0).getAffiliateName().toString();
//                                tb_zt_name_db.setText(s + "/" + hq_kssj_dq + "/" + hq_jssj_dq);
//                                showlist();//暂时不显示
//                                //同时加载对比数据
//                                if (all_info_db != null && !all_info_db.equals("")) {
//                                    getDbInfos();//成功加载当前数据后加载对比数据
//                                }else{
//                                    Toast.makeText(context,"对比数据为空",Toast.LENGTH_LONG).show();
//                                }
                                two_load();
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
                .add("query_DB", str_id_db).add("bilNo", sub_bils)
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
        Log.e("LiNing", "---db_ids--" + db_ids);
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
                    TbSalesActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                        }
                    });
                } else {
                    Toast.makeText(TbSalesActivity.this, "无数据。。。", Toast.LENGTH_LONG)
                            .show();
                }
                final SaleMakeInfo aDb1 = new Gson().fromJson(str_all,
                        SaleMakeInfo.class);
                if (aDb1 != null) {
                    TbSalesActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            data_db = aDb1.getData();
//                            head_all = aDb1.getHead();
//                            Log.e("LiNing", "========" + data.size());
                            if (data_db != null && data_db.size() > 0) {
                                tb_zt_name_db.setText(data_db.get(0).getAffiliateName().toString() + "/" + hq_kssj_db + "/" + hq_jssj_db);
                                compareInfos();
//                                showlist_db();
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



    private ArrayList<String> names;
    private ArrayList<String> names_2;
    String s_pp, s_jxse, s_ml, s_mll;
    private List<List<TbSales>> tb_datas_new;
    private List<TbSales> tb_data1;
    private List<TbSales> tb_data2;
    private List<TbSales> tb_data3;
    private List<TbSales> tb_data_zj;

    private void compareInfos() {
        names = new ArrayList<String>();
        names_2 = new ArrayList<String>();
        tb_data1 = new ArrayList<TbSales>();
        tb_data2 = new ArrayList<TbSales>();
        tb_data3 = new ArrayList<TbSales>();
        tb_data_zj = new ArrayList<TbSales>();
        tb_datas_new = new ArrayList<>();
        Log.e("LiNing", "数据大小--------" + data.size() + "---" + data_db.size());
        if (data_db != null && data != null) {
            //遍历名称
            bl_sales_names();

            //集合所有信息
            bl_sales_info_add();
        }
    }
    String zj_pp_m,zj_jxse_m,zj_ml_m,zj_mll_m;
    String db_s_pp_m_yh,db_s_jxse_m_yh,db_s_ml_m_yh,db_s_mll_m_yh;
    private void bl_sales_info_add() {
        if (reportnos.equals("SATGP")) {
            for (int m = 0; m < data_db.size(); m++) {
                zj_q(m);
//                if(data_db.get(m).getAffiliateName().equals("总计：")){
//                    zj_pp_m = data_db.get(m).getAffiliateName().toString();
//                    zj_jxse_m = data_db.get(m).getSnAmtn().toString();
//                    zj_ml_m = data_db.get(m).getgP().toString();
//                    zj_mll_m = data_db.get(m).getgPM().toString();
//                    Log.e("LiNing", "总计数据-------" + zj_pp_m + zj_jxse_m);
//                }
                if (data_db.get(m).getBrandName() != null && !data_db.get(m).getBrandName().equals("")) {
                    String db_s_pp_m = data_db.get(m).getBrandName().toString();
                    String db_s_jxse_m = data_db.get(m).getSnAmtn().toString();
                    String db_s_ml_m = data_db.get(m).getgP().toString();
                    String db_s_mll_m = data_db.get(m).getgPM().toString();

                    if (!names_2.contains(db_s_pp_m)) {
                        TbSales tbSales_bt_m = new TbSales("", "", "", "", db_s_pp_m, db_s_jxse_m, db_s_ml_m, db_s_mll_m);
//                        TbSales tbSales_bt_m = new TbSales(db_s_pp_m, db_s_jxse_m, db_s_ml_m, db_s_mll_m,"", "", "", "");
                        tb_data3.add(tbSales_bt_m);
                    }
                }
            }
            for (int j = 0; j < data.size(); j++) {
                zj_h(j);
//                if(data.get(j).getAffiliateName().equals("总计：")){
//                    String zj_pp_m_db = data.get(j).getAffiliateName().toString();
//                    String zj_jxse_m_db = data.get(j).getSnAmtn().toString();
//                    String zj_ml_m_db = data.get(j).getgP().toString();
//                    String zj_mll_m_db = data.get(j).getgPM().toString();
//                    TbSales tbSales_zj = new TbSales(zj_pp_m, zj_jxse_m, zj_ml_m, zj_mll_m, zj_pp_m_db, zj_jxse_m_db, zj_ml_m_db, zj_mll_m_db);
//                    tb_data_zj.add(tbSales_zj);
//                    Log.e("LiNing", "tb_data_zj----总计:----" + tb_data_zj.size() + "-----" + tbSales_zj);
//                }
                if (data.get(j).getBrandName() != null && !data.get(j).getBrandName().equals("")) {
                    String db_s_pp = data.get(j).getBrandName().toString();
                    String db_s_jxse = data.get(j).getSnAmtn().toString();
                    String db_s_ml = data.get(j).getgP().toString();
                    String db_s_mll = data.get(j).getgPM().toString();

                    if (names.contains(db_s_pp)) {
                        for (int i = 0; i < data_db.size(); i++) {
                            if (data_db.get(i).getBrandName() != null && !data_db.get(i).getBrandName().equals("")) {

                                String s_pp_q = data_db.get(i).getBrandName().toString();
                                if (s_pp_q.equals(db_s_pp)) {

                                    String s_jxse_q = data_db.get(i).getSnAmtn().toString();
                                    String s_ml_q = data_db.get(i).getgP().toString();
                                    String s_mll_q = data_db.get(i).getgPM().toString();

//                                    TbSales tbSales = new TbSales(s_pp_q, s_jxse_q, s_ml_q, s_mll_q, db_s_pp, db_s_jxse, db_s_ml, db_s_mll);
                                    TbSales tbSales = new TbSales( db_s_pp, db_s_jxse, db_s_ml, db_s_mll,s_pp_q, s_jxse_q, s_ml_q, s_mll_q);
                                    tb_data1.add(tbSales);
                                }
                            }
                        }
//                        Log.e("LiNing", "tb_data1.size--------" + tb_data1.size() + "-----" + tb_data1);
                    } else {
                        TbSales tbSales_bt = new TbSales(db_s_pp, db_s_jxse, db_s_ml, db_s_mll, "", "", "", "");
//                        TbSales tbSales_bt = new TbSales("", "", "", "",db_s_pp, db_s_jxse, db_s_ml, db_s_mll);
                        tb_data2.add(tbSales_bt);
                    }
                }

            }
//            Log.e("LiNing", "names_2--------" + names_2.size() + names_2);
            tb_data1.addAll(tb_data2);
            tb_data1.addAll(tb_data3);
            tb_data1.addAll(tb_data_zj);
//            Log.e("LiNing", "tb_data1.size--------" + tb_data1.size() + "-----" + tb_data1);
            showlist_db();//显示数据
        }
        if (reportnos.equals("SATG")) {
            for (int m = 0; m < data_db.size(); m++) {
                zj_q(m);
                if (data_db.get(m).getAffiliateName() != null && !data_db.get(m).getAffiliateName().equals("")) {

//                     db_s_pp_m_yh = data_db.get(m).getAffiliateName().toString();
                     db_s_jxse_m_yh = data_db.get(m).getSnAmtn().toString();
                     db_s_ml_m_yh = data_db.get(m).getgP().toString();
                     db_s_mll_m_yh = data_db.get(m).getgPM().toString();
//                    String db_s_pp_m = data_db.get(m).getAffiliateName().toString();
//                    String db_s_jxse_m = data_db.get(m).getSnAmtn().toString();
//                    String db_s_ml_m = data_db.get(m).getgP().toString();
//                    String db_s_mll_m = data_db.get(m).getgPM().toString();
//                    if (!names_2.contains(db_s_pp_m)) {
//                        TbSales tbSales_bt_m = new TbSales("", "", "", "", db_s_pp_m, db_s_jxse_m, db_s_ml_m, db_s_mll_m);
////                        TbSales tbSales_bt_m = new TbSales(db_s_pp_m, db_s_jxse_m, db_s_ml_m, db_s_mll_m,"", "", "", "");
//                        tb_data3.add(tbSales_bt_m);
//                    }
                    Log.e("LiNing", "names_3--------" + tb_data3.size() + tb_data3);
                }
            }
            for (int j = 0; j < data.size(); j++) {
                zj_h(j);
                if (data.get(j).getAffiliateName() != null && !data.get(j).getAffiliateName().equals("")) {
                    String db_s_pp = data.get(j).getAffiliateName().toString();
                    String db_s_jxse = data.get(j).getSnAmtn().toString();
                    String db_s_ml = data.get(j).getgP().toString();
                    String db_s_mll = data.get(j).getgPM().toString();

                    if (names.contains(db_s_pp)) {
                        for (int i = 0; i < data_db.size(); i++) {
                            if (data_db.get(i).getAffiliateName() != null && !data_db.get(i).getAffiliateName().equals("")) {

                                String s_pp_q = data_db.get(i).getAffiliateName().toString();
                                if (s_pp_q.equals(db_s_pp)&&!s_pp_q.equals("总计：")&&!db_s_pp.equals("总计：")) {

                                    String s_jxse_q = data_db.get(i).getSnAmtn().toString();
                                    String s_ml_q = data_db.get(i).getgP().toString();
                                    String s_mll_q = data_db.get(i).getgPM().toString();

//                                    TbSales tbSales = new TbSales(s_pp_q, s_jxse_q, s_ml_q, s_mll_q, db_s_pp, db_s_jxse, db_s_ml, db_s_mll);
                                    TbSales tbSales = new TbSales(db_s_pp, db_s_jxse, db_s_ml, db_s_mll,s_pp_q, s_jxse_q, s_ml_q, s_mll_q);
                                    tb_data1.add(tbSales);//(账套不加，避免总计重复显示)
                                }
                            }
                        }
                        Log.e("LiNing", "tb_data1.size--------" + tb_data1.size() + "-----" + tb_data1);
                    } else {

                        //同一行
                        TbSales tbSales_bt = new TbSales(db_s_pp, db_s_jxse, db_s_ml, db_s_mll, data_db.get(0).getAffiliateName().toString(), db_s_jxse_m_yh, db_s_ml_m_yh, db_s_mll_m_yh);
//                        TbSales tbSales_bt = new TbSales(db_s_pp, db_s_jxse, db_s_ml, db_s_mll, "", "", "", "");
                        tb_data2.add(tbSales_bt);
                    }
                }

            }
            Log.e("LiNing", "names_2--------" + names_2.size() + names_2);
//            tb_data1.addAll(tb_data3);
            tb_data1.addAll(tb_data2);
            tb_data1.addAll(tb_data_zj);
            Log.e("LiNing", "tb_data1.size--------" + tb_data1.size() + "-----" + tb_data1);
            showlist_db();//显示数据
        }
        if (reportnos.equals("SATGC")) {
            for (int m = 0; m < data_db.size(); m++) {
                zj_q(m);
                if (data_db.get(m).getSalesChannelName() != null && !data_db.get(m).getSalesChannelName().equals("")) {
                    String db_s_pp_m = data_db.get(m).getSalesChannelName().toString();
                    String db_s_jxse_m = data_db.get(m).getSnAmtn().toString();
                    String db_s_ml_m = data_db.get(m).getgP().toString();
                    String db_s_mll_m = data_db.get(m).getgPM().toString();
                    if (!names_2.contains(db_s_pp_m)) {
                        TbSales tbSales_bt_m = new TbSales("", "", "", "", db_s_pp_m, db_s_jxse_m, db_s_ml_m, db_s_mll_m);
//                        TbSales tbSales_bt_m = new TbSales(db_s_pp_m, db_s_jxse_m, db_s_ml_m, db_s_mll_m,"", "", "", "");
                        tb_data3.add(tbSales_bt_m);
                    }
                    Log.e("LiNing", "names_3--------" + tb_data3.size() + tb_data3);
                }
            }
            for (int j = 0; j < data.size(); j++) {
                zj_h(j);
                if (data.get(j).getSalesChannelName() != null && !data.get(j).getSalesChannelName().equals("")) {
                    String db_s_pp = data.get(j).getSalesChannelName().toString();
                    String db_s_jxse = data.get(j).getSnAmtn().toString();
                    String db_s_ml = data.get(j).getgP().toString();
                    String db_s_mll = data.get(j).getgPM().toString();

                    if (names.contains(db_s_pp)) {
                        for (int i = 0; i < data_db.size(); i++) {
                            if (data_db.get(i).getSalesChannelName() != null && !data_db.get(i).getSalesChannelName().equals("")) {

                                String s_pp_q = data_db.get(i).getSalesChannelName().toString();
                                if (s_pp_q.equals(db_s_pp)) {

                                    String s_jxse_q = data_db.get(i).getSnAmtn().toString();
                                    String s_ml_q = data_db.get(i).getgP().toString();
                                    String s_mll_q = data_db.get(i).getgPM().toString();

//                                    TbSales tbSales = new TbSales(s_pp_q, s_jxse_q, s_ml_q, s_mll_q, db_s_pp, db_s_jxse, db_s_ml, db_s_mll);
                                    TbSales tbSales = new TbSales(db_s_pp, db_s_jxse, db_s_ml, db_s_mll,s_pp_q, s_jxse_q, s_ml_q, s_mll_q);
                                    tb_data1.add(tbSales);
                                }
                            }
                        }
                        Log.e("LiNing", "tb_data1.size--------" + tb_data1.size() + "-----" + tb_data1);
                    } else {
                        TbSales tbSales_bt = new TbSales(db_s_pp, db_s_jxse, db_s_ml, db_s_mll, "", "", "", "");
//                        TbSales tbSales_bt = new TbSales("", "", "", "",db_s_pp, db_s_jxse, db_s_ml, db_s_mll);
                        tb_data2.add(tbSales_bt);
                    }
                }

            }
            Log.e("LiNing", "names_2--------" + names_2.size() + names_2);
            tb_data1.addAll(tb_data2);
            tb_data1.addAll(tb_data3);
            tb_data1.addAll(tb_data_zj);
            Log.e("LiNing", "tb_data1.size--------" + tb_data1.size() + "-----" + tb_data1);
            showlist_db();//显示数据
        }
        if (reportnos.equals("SATGD")) {
            for (int m = 0; m < data_db.size(); m++) {
                zj_q(m);
                if (data_db.get(m).getSalesDepartmentName() != null && !data_db.get(m).getSalesDepartmentName().equals("")) {
                    String db_s_pp_m = data_db.get(m).getSalesDepartmentName().toString();
                    String db_s_jxse_m = data_db.get(m).getSnAmtn().toString();
                    String db_s_ml_m = data_db.get(m).getgP().toString();
                    String db_s_mll_m = data_db.get(m).getgPM().toString();
                    if (!names_2.contains(db_s_pp_m)) {
                        TbSales tbSales_bt_m = new TbSales("", "", "", "", db_s_pp_m, db_s_jxse_m, db_s_ml_m, db_s_mll_m);
//                        TbSales tbSales_bt_m = new TbSales(db_s_pp_m, db_s_jxse_m, db_s_ml_m, db_s_mll_m,"", "", "", "");
                        tb_data3.add(tbSales_bt_m);
                    }
                    Log.e("LiNing", "names_3--------" + tb_data3.size() + tb_data3);
                }
            }
            for (int j = 0; j < data.size(); j++) {
                zj_h(j);
                if (data.get(j).getSalesDepartmentName() != null && !data.get(j).getSalesDepartmentName().equals("")) {
                    String db_s_pp = data.get(j).getSalesDepartmentName().toString();
                    String db_s_jxse = data.get(j).getSnAmtn().toString();
                    String db_s_ml = data.get(j).getgP().toString();
                    String db_s_mll = data.get(j).getgPM().toString();

                    if (names.contains(db_s_pp)) {
                        for (int i = 0; i < data_db.size(); i++) {
                            if (data_db.get(i).getSalesDepartmentName() != null && !data_db.get(i).getSalesDepartmentName().equals("")) {

                                String s_pp_q = data_db.get(i).getSalesDepartmentName().toString();
                                if (s_pp_q.equals(db_s_pp)) {

                                    String s_jxse_q = data_db.get(i).getSnAmtn().toString();
                                    String s_ml_q = data_db.get(i).getgP().toString();
                                    String s_mll_q = data_db.get(i).getgPM().toString();

//                                    TbSales tbSales = new TbSales(s_pp_q, s_jxse_q, s_ml_q, s_mll_q, db_s_pp, db_s_jxse, db_s_ml, db_s_mll);
                                    TbSales tbSales = new TbSales(db_s_pp, db_s_jxse, db_s_ml, db_s_mll,s_pp_q, s_jxse_q, s_ml_q, s_mll_q);
                                    tb_data1.add(tbSales);
                                }
                            }
                        }
                        Log.e("LiNing", "tb_data1.size--------" + tb_data1.size() + "-----" + tb_data1);
                    } else {
                        TbSales tbSales_bt = new TbSales(db_s_pp, db_s_jxse, db_s_ml, db_s_mll, "", "", "", "");
//                        TbSales tbSales_bt = new TbSales("", "", "", "",db_s_pp, db_s_jxse, db_s_ml, db_s_mll);
                        tb_data2.add(tbSales_bt);
                    }
                }

            }
            Log.e("LiNing", "names_2--------" + names_2.size() + names_2);
            tb_data1.addAll(tb_data2);
            tb_data1.addAll(tb_data3);
            tb_data1.addAll(tb_data_zj);
            Log.e("LiNing", "tb_data1.size--------" + tb_data1.size() + "-----" + tb_data1);
            showlist_db();//显示数据
        }
        if (reportnos.equals("SATGGC")) {
            for (int m = 0; m < data_db.size(); m++) {
                zj_q(m);
                if (data_db.get(m).getSalesTerminalName() != null && !data_db.get(m).getSalesTerminalName().equals("")) {
                    String db_s_pp_m = data_db.get(m).getSalesTerminalName().toString();
                    String db_s_jxse_m = data_db.get(m).getSnAmtn().toString();
                    String db_s_ml_m = data_db.get(m).getgP().toString();
                    String db_s_mll_m = data_db.get(m).getgPM().toString();
                    if (!names_2.contains(db_s_pp_m)) {
                        TbSales tbSales_bt_m = new TbSales("", "", "", "", db_s_pp_m, db_s_jxse_m, db_s_ml_m, db_s_mll_m);
//                        TbSales tbSales_bt_m = new TbSales(db_s_pp_m, db_s_jxse_m, db_s_ml_m, db_s_mll_m,"", "", "", "");
                        tb_data3.add(tbSales_bt_m);
                    }
                    Log.e("LiNing", "names_3--------" + tb_data3.size() + tb_data3);
                }
            }
            for (int j = 0; j < data.size(); j++) {
                zj_h(j);
                if (data.get(j).getSalesTerminalName() != null && !data.get(j).getSalesTerminalName().equals("")) {
                    String db_s_pp = data.get(j).getSalesTerminalName().toString();
                    String db_s_jxse = data.get(j).getSnAmtn().toString();
                    String db_s_ml = data.get(j).getgP().toString();
                    String db_s_mll = data.get(j).getgPM().toString();

                    if (names.contains(db_s_pp)) {
                        for (int i = 0; i < data_db.size(); i++) {
                            if (data_db.get(i).getSalesTerminalName() != null && !data_db.get(i).getSalesTerminalName().equals("")) {

                                String s_pp_q = data_db.get(i).getSalesTerminalName().toString();
                                if (s_pp_q.equals(db_s_pp)) {

                                    String s_jxse_q = data_db.get(i).getSnAmtn().toString();
                                    String s_ml_q = data_db.get(i).getgP().toString();
                                    String s_mll_q = data_db.get(i).getgPM().toString();

//                                    TbSales tbSales = new TbSales(s_pp_q, s_jxse_q, s_ml_q, s_mll_q, db_s_pp, db_s_jxse, db_s_ml, db_s_mll);
                                    TbSales tbSales = new TbSales(db_s_pp, db_s_jxse, db_s_ml, db_s_mll,s_pp_q, s_jxse_q, s_ml_q, s_mll_q);
                                    tb_data1.add(tbSales);
                                }
                            }
                        }
                        Log.e("LiNing", "tb_data1.size--------" + tb_data1.size() + "-----" + tb_data1);
                    } else {
                        TbSales tbSales_bt = new TbSales(db_s_pp, db_s_jxse, db_s_ml, db_s_mll, "", "", "", "");
//                        TbSales tbSales_bt = new TbSales("", "", "", "",db_s_pp, db_s_jxse, db_s_ml, db_s_mll);
                        tb_data2.add(tbSales_bt);
                    }
                }

            }
            Log.e("LiNing", "names_2--------" + names_2.size() + names_2);
            tb_data1.addAll(tb_data2);
            tb_data1.addAll(tb_data3);
            tb_data1.addAll(tb_data_zj);
//            Log.e("LiNing", "tb_data1.size--------" + tb_data1.size() + "-----" + tb_data1);
            showlist_db();//显示数据
        }

    }

    private void zj_h(int j) {
        if(data.get(j).getAffiliateName().equals("总计：")){
            String zj_pp_m_db = data.get(j).getAffiliateName().toString();
            String zj_jxse_m_db = data.get(j).getSnAmtn().toString();
            String zj_ml_m_db = data.get(j).getgP().toString();
            String zj_mll_m_db = data.get(j).getgPM().toString();
//            TbSales tbSales_zj = new TbSales(zj_pp_m, zj_jxse_m, zj_ml_m, zj_mll_m, zj_pp_m_db, zj_jxse_m_db, zj_ml_m_db, zj_mll_m_db);
            TbSales tbSales_zj = new TbSales(zj_pp_m_db, zj_jxse_m_db, zj_ml_m_db, zj_mll_m_db,zj_pp_m, zj_jxse_m, zj_ml_m, zj_mll_m);
            tb_data_zj.add(tbSales_zj);
            Log.e("LiNing", "tb_data_zj----总计:----" + tb_data_zj.size() + "-----" + tbSales_zj);
        }
//        if(data_db.get(j).getAffiliateName().equals("总计：")){
//            String zj_pp_m_db = data_db.get(j).getAffiliateName().toString();
//            String zj_jxse_m_db = data_db.get(j).getSnAmtn().toString();
//            String zj_ml_m_db = data_db.get(j).getgP().toString();
//            String zj_mll_m_db = data_db.get(j).getgPM().toString();
//            TbSales tbSales_zj = new TbSales(zj_pp_m, zj_jxse_m, zj_ml_m, zj_mll_m, zj_pp_m_db, zj_jxse_m_db, zj_ml_m_db, zj_mll_m_db);
//            tb_data_zj.add(tbSales_zj);
//            Log.e("LiNing", "tb_data_zj----总计:----" + tb_data_zj.size() + "-----" + tbSales_zj);
//        }
    }

    private void zj_q(int m) {
        //暂时没用
//        if (data_db.get(m).getAffiliateName() != null && data_db.get(m).getAffiliateName().equals("")) {
//
//              zj_pp_m = data_db.get(m).getAffiliateName().toString();
//            if(zj_pp_m.equals("总计: ")){
//
//                 zj_jxse_m = data_db.get(m).getSnAmtn().toString();
//                 zj_ml_m = data_db.get(m).getgP().toString();
//                 zj_mll_m = data_db.get(m).getgPM().toString();
//            }
//        }
        //有用
        if(data_db.get(m).getAffiliateName().equals("总计：")){
            zj_pp_m = data_db.get(m).getAffiliateName().toString();
            zj_jxse_m = data_db.get(m).getSnAmtn().toString();
            zj_ml_m = data_db.get(m).getgP().toString();
            zj_mll_m = data_db.get(m).getgPM().toString();
            Log.e("LiNing", "总计数据-------" + zj_pp_m + zj_jxse_m);
        }
//        if(data.get(m).getAffiliateName().equals("总计：")){
//            zj_pp_m = data.get(m).getAffiliateName().toString();
//            zj_jxse_m = data.get(m).getSnAmtn().toString();
//            zj_ml_m = data.get(m).getgP().toString();
//            zj_mll_m = data.get(m).getgPM().toString();
//            Log.e("LiNing", "总计数据-------" + zj_pp_m + zj_jxse_m);
//        }
    }

    //遍历名称
    private void bl_sales_names() {
        for (int i = 0; i < data_db.size(); i++) {
            if (reportnos.equals("SATG")) {

                if (data_db.get(i).getAffiliateName() != null && !data_db.get(i).getAffiliateName().equals("")) {

                    s_pp = data_db.get(i).getAffiliateName().toString();
                    names.add(s_pp);
                }
            }
            if (reportnos.equals("SATGP")) {

                if (data_db.get(i).getBrandName() != null && !data_db.get(i).getBrandName().equals("")) {

                    s_pp = data_db.get(i).getBrandName().toString();
                    names.add(s_pp);
                }
            }
            if (reportnos.equals("SATGC")) {

                if (data_db.get(i).getSalesChannelName() != null && !data_db.get(i).getSalesChannelName().equals("")) {

                    s_pp = data_db.get(i).getSalesChannelName().toString();
                    names.add(s_pp);
                }
            }
            if (reportnos.equals("SATGD")) {

                if (data_db.get(i).getSalesDepartmentName() != null && !data_db.get(i).getSalesDepartmentName().equals("")) {

                    s_pp = data_db.get(i).getSalesDepartmentName().toString();
                    names.add(s_pp);
                }
            }
            if (reportnos.equals("SATGGC")) {

                if (data_db.get(i).getSalesTerminalName() != null && !data_db.get(i).getSalesTerminalName().equals("")) {

                    s_pp = data_db.get(i).getSalesTerminalName().toString();
                    names.add(s_pp);
                }
            }
        }
        for (int i = 0; i < data.size(); i++) {
            if (reportnos.equals("SATG")) {

                if (data.get(i).getAffiliateName() != null && !data.get(i).getAffiliateName().equals("")) {

                    String s_pp_db = data.get(i).getAffiliateName().toString();
                    names_2.add(s_pp_db);
                }
            }
            if (reportnos.equals("SATGP")) {

                if (data.get(i).getBrandName() != null && !data.get(i).getBrandName().equals("")) {

                    String s_pp_db = data.get(i).getBrandName().toString();
                    names_2.add(s_pp_db);
                }
            }
            if (reportnos.equals("SATGC")) {

                if (data.get(i).getSalesChannelName() != null && !data.get(i).getSalesChannelName().equals("")) {

                    String s_pp_db = data.get(i).getSalesChannelName().toString();
                    names_2.add(s_pp_db);
                }
            }
            if (reportnos.equals("SATGD")) {

                if (data.get(i).getSalesDepartmentName() != null && !data.get(i).getSalesDepartmentName().equals("")) {

                    String s_pp_db = data.get(i).getSalesDepartmentName().toString();
                    names_2.add(s_pp_db);
                }
            }
            if (reportnos.equals("SATGGC")) {

                if (data.get(i).getSalesTerminalName() != null && !data.get(i).getSalesTerminalName().equals("")) {

                    String s_pp_db = data.get(i).getSalesTerminalName().toString();
                    names_2.add(s_pp_db);
                }
            }
        }
//        Log.e("LiNing", "names--------" + names.size() + names);
//        Log.e("LiNing", "names_2--------" + names_2.size() + names_2);
    }

    private void showlist_db() {
        sfadapter_db = new SalesInfoDbAdapter(R.layout.tb_one_listview_team, context, tb_data1, reportnos);
        lv_get_one.setAdapter(sfadapter_db);
        sfadapter_db.notifyDataSetChanged();
        if(lv_get_one.getCount()>0){
            tj_head_hint.setVisibility(View.GONE);
            tj_head_show.setVisibility(View.VISIBLE);
        }
    }


    public class SalesInfoDbAdapter extends BaseAdapter {
        SaleMakeInfo.Data data_dq_infos;
        int id_row_layout;
        Context context;
        List<TbSales> tb_data_db;
        LayoutInflater mInflater;
        String index;


        public SalesInfoDbAdapter(int tb_one_listview_team, Context context, List<TbSales> tb_data1, String reportnos) {
            this.id_row_layout = tb_one_listview_team;
            this.context = context;
            this.tb_data_db = tb_data1;
            this.mInflater = LayoutInflater.from(context);
            this.index = reportnos;
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
                synchronized (TbSalesActivity.this) {
                convertView = mInflater.inflate(id_row_layout, null);
                    scrow(convertView);
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
            }
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            TbSales tbSales = tb_data_db.get(position);
//            Log.e("LiNing", "----tbSales数据------" + tb_data_db.size() + tb_data_db);
//            if (index.equals("SATGP")) {
                if (tbSales != null) {
                    holder.tb_dq_data_zl.setText(tbSales.getName());
                    holder.tb_db_data_zl.setText(tbSales.getName_db());
                    holder.tb_dq_sn_amtn.setText(tbSales.getJxsr());
                    holder.tb_dq_gp.setText(tbSales.getMl());
                    holder.tb_dq_gpm.setText(tbSales.getMll());
                    holder.tb_db_sn_amtn.setText(tbSales.getJxsr_db());
                    holder.tb_db_gp.setText(tbSales.getMl_db());
                    holder.tb_db_gpm.setText(tbSales.getMll_db());
                    if (tbSales.getName().toString().equals(tbSales.getName_db())) {
                        //销售增长率
                        BigDecimal d_jxse_q = new BigDecimal(tbSales.getJxsr());
                        BigDecimal d_jxse_h = new BigDecimal(tbSales.getJxsr_db());
//                        BigDecimal c_jxse = d_jxse_h.subtract(d_jxse_q);
                        BigDecimal c_jxse = d_jxse_q.subtract(d_jxse_h);//(前-后)
                        Log.e("LiNing", "净销售额率--cc--" + c_jxse);
                        double v = c_jxse.divide(d_jxse_h, BigDecimal.ROUND_HALF_UP).doubleValue();
                        Log.e("LiNing", "净销售额率----" + v);
                        holder.tb_db_xs_zzl.setText(String.valueOf(v));
                        //毛利增长率
                        BigDecimal d_ml_q = new BigDecimal(tbSales.getMl());
                        BigDecimal d_ml_h = new BigDecimal(tbSales.getMl_db());
//                        BigDecimal c_ml = d_ml_h.subtract(d_ml_q);
                        BigDecimal c_ml = d_ml_q.subtract(d_ml_h);
                        Log.e("LiNing", "毛利增长率--cc--" + c_ml);
                        double v_ml = c_ml.divide(d_ml_h, BigDecimal.ROUND_HALF_UP).doubleValue();
                        Log.e("LiNing", "毛利增长率----" + v_ml);
                        holder.tb_db_gp_zzl.setText(String.valueOf(v_ml));
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
                        holder.tb_db_gpm_c.setText(String.valueOf(v5 )+"%");
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
        private void scrow(View convertView) {
            MyHScrollView scrollView1 = (MyHScrollView) convertView
                    .findViewById(R.id.horizontalScrollView1);
            MyHScrollView headSrcrollView = (MyHScrollView) mHead
                    .findViewById(R.id.horizontalScrollView1);
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
//                            tb_zl_name_dq.setText(menuItem.getTitle().toString());
                            tb_zl_name_db.setText(menuItem.getTitle().toString());
                        } else {

                            String name_lbxs = menuItem.getTitle().toString().substring(3);
//                            tb_zl_name_dq.setText(name_lbxs);
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
                    if (checked == 2) {
                        str_id = data.getStringExtra("condition_db");
                        str_name = data.getStringExtra("condition_name");
                        tb_db_dq.setText(str_name);
                        Log.e("LiNing", "提交的id====" + str_id + str_name);
                    }
                    if (checked == 3) {
                        str_id_db = data.getStringExtra("condition_db");
                        str_name_db = data.getStringExtra("condition_name");
                        tb_db_db.setText(str_name_db);
                        Log.e("LiNing", "提交的id====" + str_id_db + str_name_db);
                    }
//					if(head_sale.getText().equals("对比数据")){
//						db_Db.setText(str_id);
//					}
//                    Log.e("LiNing", "提交的id====" + str_id + str_name);
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
