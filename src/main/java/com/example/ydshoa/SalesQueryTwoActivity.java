package com.example.ydshoa;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.LeftOrRight.MyHScrollView;
import com.example.bean.SaleMakeInfo;
import com.example.bean.SaleMakeInfo.Data;
import com.example.bean.TestQx;
import com.example.bean.URLS;
import com.example.ydshoa.SalesQueryTwoActivity.ListViewAndHeadViewTouchLinstener;
import com.example.ydshoa.SalesQueryTwoActivity.SalesInfoAdapter;
import com.example.ydshoa.SalesQueryTwoActivity.SalesInfoAdapter.OnScrollChangedListenerImp;
import com.example.ydshoa.SalesQueryTwoActivity.SalesInfoAdapter.ViewHolder;
import com.google.gson.Gson;
//import com.umeng.message.PushAgent;

public class SalesQueryTwoActivity extends Activity implements OnClickListener {
    private Context context;
    private Button start, stop, make, condicion, out, printe, back;
    private TextView head, saleName, saleNum, fzsj;
    RelativeLayout mHead;
    private ImageButton salesButton, numButton;
    private ListView lv_get;
    private String reportBus, reportnos, reportname;
    private SharedPreferences sp;
    public String session;
    private ArrayList<String> querryDBs, querryDBs_all, bilNos, custNos,
            custNames, custPhones, custAdress, inputNos, cust_os, rems,
            prdMarks, query_deps, query_sups, query_custs, query_users,
            query_deps_all, query_sups_all, query_custs_all, query_users_all,
            chk_users, areas, query_hs, query_hs_all, employees, prdNos,
            prdIndexs, prdWhs, zts, zcs, dys, cbs, mls, mlls, jes, cbs_dj,
            mls_dj, mlls_dj, jes_dj;
    // String url = "http://oa.ydshce.com:8080/InfManagePlatform/Report.action";
    String url = URLS.sale_url;
    private List<com.example.bean.SaleMakeInfo.Data> data;
    private int flag_index;
    private String reportNo_get;
    private SalesInfoAdapter sfadapter;
    private TextView tv_one, tv_two, tv_three, tv_four, tv_five;
    private FormBody body;
    private String startTime, stopTime;
    private String time_start, time_stop;
    private TextView hj_saamtn, hj_one, hj_two, hj_three, hj_four, hj_five,
            hj_sbamtn, hj_ssamtn, hj_sdamtn, hj_snamtn, hj_sacst, hj_sbcst,
            hj_sncst, hj_sbsamtn, hj_gp, hj_gpm;
    private LinearLayout ll_fz, ll_one, ll_two, ll_three, ll_fore;
    // 渠道
    private HashMap<String, String> getSaAmtn, getSbAmtn, getSsAmtn, getSdAmtn,
            getSnAmtn, getSaCst, getSbCst, getSnCst, getSbsAmatn, getgp,
            getgpm;
    // 品牌
    private HashMap<String, Double> getSaAmtn_pp, getSbAmtn_pp, getSsAmtn_pp,
            getSdAmtn_pp, getSnAmtn_pp, getSaCst_pp, getSbCst_pp, getSnCst_pp,
            getSbsAmatn_pp, getgp_pp, getgpm_pp;
    // 货品中类
    private HashMap<String, Double> getSaAmtn_hp, getSbAmtn_hp, getSsAmtn_hp,
            getSdAmtn_hp, getSnAmtn_hp, getSaCst_hp, getSbCst_hp, getSnCst_hp,
            getSbsAmatn_hp, getgp_hp, getgpm_hp;
    private PopupWindow popupWindow;
    private int pp_sub, PH_TF;
    private BigDecimal saAmtn;
    private String cb_xy, ml_xy, mll_xy, zc_xy, dy_xy;
    private String str_time;
    private ArrayList<SaleMakeInfo.Data> dataBeans;
    private String ps_id;
    // public static ArrayList<SaleMakeInfo.Data> newata = new
    // ArrayList<SaleMakeInfo.Data>();
    private String sub_querys_db;
    private View loadMoreView;
    private Button loadMoreButton;

    private int visibleLastIndex = 0; // 最后的可视项索引
    private int visibleItemCount; // 当前窗口可见项总数
    private Handler handler = new Handler();
    int currpage = 1;
    private String zc_ln, dy_ln, cb_ln, ml_ln, mll_ln;
    public static List<SaleMakeInfo.Data> out_one_no = new ArrayList<SaleMakeInfo.Data>();
    public static List<SaleMakeInfo.Data> test = new ArrayList<SaleMakeInfo.Data>();
    private ArrayList<HashMap<String, Object>> dList;
    private HashMap<String, Object> item;

    private ImageButton sum;
    private TextView tv_sum;
    public String arp_sum;
    //表头展开收缩
    LinearLayout tj_head_hint;
    private ImageButton tj_head_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sales_query_two);
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        ps_id = sp.getString("PASS", "");
        context = SalesQueryTwoActivity.this;
//        PushAgent.getInstance(context).onAppStart();
        reportBus = getIntent().getStringExtra("reportB");// 获取业务类型
        reportnos = getIntent().getStringExtra("reportNo");// 获取种类类型id
        reportname = getIntent().getStringExtra("reportName");// 获取种类类型名称
        Log.e("LiNing", reportBus + reportnos+reportname);
        getNowTime();
        initView();


//        if (savedInstanceState != null) {//横竖屏切换必须实现接口。。。Adapter同理
////            lv_get = (ListView) findViewById(R.id.lv_saleGet_header);
////            lv_get.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
////            sfadapter = (SalesInfoAdapter) savedInstanceState.getSerializable("select");
////            lv_get.setAdapter(sfadapter);
////            sfadapter.notifyDataSetChanged();
//            Toast.makeText(context,"请重新点击制表",Toast.LENGTH_LONG).show();
//
//        }
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {//横竖屏切换必须实现接口。。。Adapter同理
//        super.onSaveInstanceState(outState);
//
//        outState.putSerializable("select", sfadapter);
//
//    }

    private void getNowTime() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
        Date curDate = new Date(System.currentTimeMillis());
        str_time = formatter.format(curDate);
//		 Calendar c = Calendar.getInstance();
//		 int mYear = c.get(Calendar.YEAR); // 获取当前年份
//		 int mMonth = c.get(Calendar.MONTH) ;// 获取当前月份cal.set(Calendar.DAY_OF_MONTH, 1);
//		 int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
//		 c.set(Calendar.DAY_OF_MONTH, 1);
//		 Log.e("LiNing", "----"+c.getTime().toString());
//		 Log.e("LiNing", "y"+mYear+"/y"+mMonth+"/r"+mDay);
//		String one=mYear+"-"+mMonth+"-"+mDay;

    }

    private void initView() {
        findViewById(R.id.imageButton1).setVisibility(View.GONE);

        zc_xy = sp.getString("ZC", "");
        dy_xy = sp.getString("DY", "");
        cb_xy = sp.getString("CB", "");
        ml_xy = sp.getString("ML", "");
        mll_xy = sp.getString("MLL", "");
        Log.e("LiNing", "----" + zc_xy + cb_xy + ml_xy + mll_xy);
        tj_head_show = (ImageButton) findViewById(R.id.ib_nulladd);
        tj_head_hint = (LinearLayout) findViewById(R.id.ll_tb_show);
        head = (TextView) findViewById(R.id.all_head);
        head.setTextSize(15);
        tv_sum = (TextView) findViewById(R.id.tv_sfhz);
        String jc_bb = sp.getString("JC", "");
        String tj_bb = sp.getString("TJ", "");
        String zbHead = jc_bb + "/" + tj_bb + "/" + reportname;
        //
        String TQ_GET = sp.getString("TQ", "");
        TestQx book = new Gson().fromJson(TQ_GET, TestQx.class);
        head.setText(zbHead);
        mHead = ((RelativeLayout) findViewById(R.id.sales_scrowHead));
        mHead.setFocusable(true);
        mHead.setClickable(true);
        mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        lv_get = (ListView) findViewById(R.id.lv_saleGet_header);
        lv_get.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        lv_get.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sfadapter.setSelectItem(position);//刷新
                sfadapter.notifyDataSetInvalidated();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // saleName = (TextView) findViewById(R.id.tv_saleName);
        saleNum = (TextView) findViewById(R.id.tv_makeNum);
        start = (Button) findViewById(R.id.btn_start_time);
        stop = (Button) findViewById(R.id.btn_stop_time);
//		start.setText(str_time);
        stop.setText(str_time);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_MONTH, 1); //当前月1号和最后日期
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
        start.setText(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
        cal.roll(Calendar.DAY_OF_MONTH, -1);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
//        stop.setText(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
        make = (Button) findViewById(R.id.btn_making);
        condicion = (Button) findViewById(R.id.btn_condition);
        out = (Button) findViewById(R.id.btn_out);
        printe = (Button) findViewById(R.id.btn_print);
        back = (Button) findViewById(R.id.btn_back_zb);
        // salesButton = (ImageButton) findViewById(R.id.ib_sale_getsub);
        // numButton = (ImageButton) findViewById(R.id.ib_sale_getNum);
        //后补（是否小计）
        sum = (ImageButton) findViewById(R.id.ib_sfhz);
        sum.setOnClickListener(this);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        make.setOnClickListener(this);
        condicion.setOnClickListener(this);
        out.setOnClickListener(this);
        printe.setOnClickListener(this);
        back.setOnClickListener(this);
        // salesButton.setOnClickListener(this);
        // numButton.setOnClickListener(this);

        // 加载更多。。。
        loadMoreView = getLayoutInflater().inflate(R.layout.load_more, null);
        loadMoreButton = (Button) loadMoreView
                .findViewById(R.id.loadMoreButton);
        // lv_get.addFooterView(loadMoreView); // 设置列表底部视图
        // -------------------------
        ll_one = (LinearLayout) findViewById(R.id.ll_gd_head);
        ll_two = (LinearLayout) findViewById(R.id.ll_gd_head1);
        ll_three = (LinearLayout) findViewById(R.id.ll_gd_head2);
        ll_fore = (LinearLayout) findViewById(R.id.ll_gd_head3);
        tv_one = (TextView) findViewById(R.id.tv_1);
        tv_two = (TextView) findViewById(R.id.tv_2);
        tv_three = (TextView) findViewById(R.id.tv_3);
        tv_four = (TextView) findViewById(R.id.tv_4);
        tv_five = (TextView) findViewById(R.id.tv_5);

        // time_start = sp.getString("START_time", "");
        // time_stop = sp.getString("STOP_time", "");
        // if (time_start.equals("")) {
        // start.setText(str_time);
        // } else {
        // start.setText(time_start);
        // }
        // if (time_stop.equals("")) {
        // stop.setText(str_time);
        // } else {
        // stop.setText(time_stop);
        // }
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
        getXSqx_all();
    }
    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.sfhz, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.check1
                        || menuItem.getItemId() == R.id.check2
                        ) {
                    menuItem.setChecked(!menuItem.isChecked());
                    tv_sum.setText(menuItem.getTitle());
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }
    private void getXSqx_all() {
        String qx_info = sp.getString("CBQX", "");
        // String all_info = getIntent().getStringExtra("sale_tjInfo");
        Log.e("LiNing", "----" + qx_info);
        if (qx_info != null) {

            zts = new ArrayList<String>();
            zcs = new ArrayList<String>();
            dys = new ArrayList<String>();
            cbs = new ArrayList<String>();
            mls = new ArrayList<String>();
            mlls = new ArrayList<String>();
            jes = new ArrayList<String>();
            try {
                JSONArray arr = new JSONArray(qx_info);
                int size = arr.length();
                for (int i = 0; i < size; i++) {
                    JSONObject jsonObject = arr.getJSONObject(i);
                    db_ids_out = jsonObject.get("账套").toString();
                    String zc_ids = jsonObject.get("转出").toString();
                    String dy_ids = jsonObject.get("打印").toString();
                    cb_ids_out = jsonObject.get("成本").toString();
                    ml_ids_out = jsonObject.get("毛利").toString();
                    mll_ids_out = jsonObject.get("毛利率").toString();
                    je_ids_out = jsonObject.get("金额").toString();
                    zts.add(db_ids_out);
                    zcs.add(zc_ids);
                    dys.add(dy_ids);
                    cbs.add(cb_ids_out);
                    mls.add(ml_ids_out);
                    mlls.add(mll_ids_out);
                    jes.add(je_ids_out);
                    zc_ln = zc_ids;
                    dy_ln = dy_ids;
                    cb_ln = cb_ids_out;
                    ml_ln = ml_ids_out;
                    mll_ln = mll_ids_out;

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("LiNing", "--zts-----" + zts + cbs + mlls);
            Log.e("LiNing", "--f/t-----" + cb_ln + ml_ln + mll_ln);
        }
    }

    // 显示权限集合
    private void getXSqx() {
        String qx_info = sp.getString("CBQX", "");
        // String all_info = getIntent().getStringExtra("sale_tjInfo");
        Log.e("LiNing", "----" + qx_info);
        if (qx_info != null) {

            zts = new ArrayList<String>();
            zcs = new ArrayList<String>();
            dys = new ArrayList<String>();
            cbs = new ArrayList<String>();
            mls = new ArrayList<String>();
            mlls = new ArrayList<String>();
            jes = new ArrayList<String>();
            try {
                JSONArray arr = new JSONArray(qx_info);
                int size = arr.length();
                for (String B : ln) {
                    for (int i = 0; i < size; i++) {
                        JSONObject jsonObject = arr.getJSONObject(i);
                        String db_ids = jsonObject.get("账套").toString();
                        String zc_ids = jsonObject.get("转出").toString();
                        String dy_ids = jsonObject.get("打印").toString();
                        String cb_ids = jsonObject.get("成本").toString();
                        String ml_ids = jsonObject.get("毛利").toString();
                        String mll_ids = jsonObject.get("毛利率").toString();
                        String je_ids = jsonObject.get("金额").toString();
                        zts.add(db_ids);
                        zcs.add(zc_ids);
                        dys.add(dy_ids);
                        cbs.add(cb_ids);
                        mls.add(ml_ids);
                        mlls.add(mll_ids);
                        jes.add(je_ids);
                        if (B.equals(db_ids)) {
                            Log.e("LiNing", ml_ids);
                            zc_ln = zc_ids;
                            dy_ln = dy_ids;
                            cb_ln = cb_ids;
                            ml_ln = ml_ids;
                            mll_ln = mll_ids;
                        }
                    }

                }

            } catch (JSONException e) {

                e.printStackTrace();
            }
            Log.e("LiNing", "--zts--dj=---" + zts + cbs + mlls);
            Log.e("LiNing", "--zts--dj=---" + cb_ln + ml_ln + mll_ln);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
//					}, 2015, 1, 13).show();
                break;
            case R.id.btn_making:
                // if (saleNum.getText().toString().equals("")) {
                String sfhz_sum = tv_sum.getText().toString();
                if(sfhz_sum.equals("是")){
                    arp_sum="T";
                }
                if(sfhz_sum.equals("否")){
                    arp_sum="F";
                }
                if (reportnos.equals("")) {
                    Toast.makeText(context, "请选择报表种类", Toast.LENGTH_LONG).show();
                } else if (start.getText().toString().equals("")
                        || stop.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择时间", Toast.LENGTH_LONG).show();
                } else {

                    if (flag_index == 1) {
                        getRootTorF();
                        getpopWindow();
                        getFlagreportNo();

                        postAllInfo();
                    } else {
                        getXSqx_all();
                        getpopWindow();
                        getFlagreportNo();
                        getAllQuery();// 获取查询数据（all）
                        // postNoInfo();
                    }

                }
                break;
            case R.id.btn_condition:
                lv_get.setAdapter(null);
                // 判断
                sp.edit().putString("sale_tjInfo", "").commit();
                flag_index = 1;
                // 获取查询条件
                Intent intent = new Intent(context, ConditionActivity.class);
                 intent.putExtra("page", "0");
                 intent.putExtra("gjss", "1");
                startActivity(intent);
                break;
            case R.id.btn_out:
                if (zc_xy.equals("true")) {
                    String stritem = new Gson().toJson(dList);
                    Intent intent2 = new Intent(context, OutInfoActivity.class);
                    intent2.putExtra("Stime", start.getText().toString());
                    intent2.putExtra("Etime", stop.getText().toString());
                    intent2.putExtra("RTNAME", reportname);
                    intent2.putExtra("RT", reportnos);
                    intent2.putExtra("INFO", head_all);
                    intent2.putExtra("out_new", stritem);
                    Log.e("LiNing", "传递数据====" + reportnos + "===" + str_all);
                    startActivity(intent2);
                } else {
                    Toast.makeText(context, "请等待...", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_print:

                if (dy_xy.equals("false")) {

                    Toast.makeText(context, "请等待...", Toast.LENGTH_LONG).show();
                } else {

                }
                break;
            case R.id.btn_back_zb:
                // startActivity(new Intent(context, SalesNumActivity.class));
                getSpToNull();
                finish();
                // saveTime();
                break;
            case R.id.ib_sfhz:
                showPopupMenu(sum);
                break;
            default:
                break;
        }
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

    List<String> ln = new ArrayList<String>();

    private void getRootTorF() {
        String all_info = sp.getString("sale_tjInfo", "");
        ArrayList<String> ln_db = new ArrayList<String>();
        if (all_info != null) {
            JSONArray arr;
            try {
                arr = new JSONArray(all_info);
                int size = arr.length();
                for (int i = 0; i < size; i++) {
                    JSONObject jsonObject = arr.getJSONObject(i);
                    String db_ids = jsonObject.get("账套").toString();
                    if (db_ids.equals("") || db_ids.equals("null")
                            || db_ids.equals("All")) {
                        ln_db.add("All");
                    } else {
                        ln_db.add(db_ids);
                    }
                }
            } catch (JSONException e) {

                e.printStackTrace();
            }

        }

        String ln_sub_db = "";
        for (String querys_db : ln_db) {
            ln_sub_db += querys_db + ",";
        }
        String[] db_spls = ln_sub_db.split(",");
        Log.e("LiNing", "=====" + ln_sub_db.split(","));
        for (int i = 0; i < db_spls.length; i++) {
            String A = db_spls[i];
            Log.e("LiNing", "===A==" + A);
            ln.add(A);
        }
        getXSqx();
    }

    private void getForT() {
        // 通过是否包含判断隐显
        // if (cb_xy.equals("false")) {
        if (!cbs.contains("true")) {
            findViewById(R.id.textView19_SaCst).setVisibility(View.GONE);
            findViewById(R.id.textView14_SbCst).setVisibility(View.GONE);
            findViewById(R.id.textView15_SnCst).setVisibility(View.GONE);
            // findViewById(R.id.textView19_SaCst_item_hj)
            // .setVisibility(View.GONE);
            // findViewById(R.id.textView14_SbCst_item_hj)
            // .setVisibility(View.GONE);
            // findViewById(R.id.textView15_SnCst_item_hj)
            // .setVisibility(View.GONE);

        }
        // if (ml_xy.equals("false")) {
        if (!mls.contains("true")) {
            findViewById(R.id.textView17_GP).setVisibility(View.GONE);
            // findViewById(R.id.textView17_GP_item_hj).setVisibility(View.GONE);
        }
        // if (mll_xy.equals("false")) {
        if (!mlls.contains("true")) {
            findViewById(R.id.textView18_GPM).setVisibility(View.GONE);
            findViewById(R.id.textView18_GPM_item_hj).setVisibility(View.GONE);
        }
        if (jes.contains("true")) {

            findViewById(R.id.textView9_SaAmtn).setVisibility(View.VISIBLE);
            findViewById(R.id.textView13_SnAmtn).setVisibility(View.VISIBLE);
            if (reportnos.equals("SATGPGCIPN") || reportnos.equals("SATGPSIPN")
                    || reportnos.equals("SATGPDIPN")
                    || reportnos.equals("SATGPCIPN")
                    || reportnos.equals("SATGPGCPN")
                    || reportnos.equals("SATGPSPN")
                    || reportnos.equals("SATGPDPN")
                    || reportnos.equals("SATGPCPN")
                    || reportnos.equals("SATGPIPN")
                    || reportnos.equals("SATGPPN")
                    || reportnos.equals("SATGPGCI")
                    || reportnos.equals("SATGPSI")
                    || reportnos.equals("SATGPDI")
                    || reportnos.equals("SATGPCI")
                    || reportnos.equals("SATGPI")) {

                findViewById(R.id.textView10_SbAmtn)
                        .setVisibility(View.VISIBLE);
                findViewById(R.id.textView11_SsAmtn)
                        .setVisibility(View.VISIBLE);
            } else {
                findViewById(R.id.textView16_SbsAmatn).setVisibility(
                        View.VISIBLE);
            }
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
                .add("reprotNo", reportnos)
                .add("beginDate", start.getText().toString())
                .add("endDate", stop.getText().toString())
                // .add("reporyBusiness", "SA")SAL
                .add("reporyBusiness", reportBus)
                .add("isGroupSum", arp_sum)
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
        Log.e("LiNing", "-----" + reportnos+"---"+start.getText().toString()
                +"---"+stop.getText().toString()+"---"+reportBus+"---"+arp_sum
                +"---"+sub_querys_db_all+"---"+sub_query_hss_all+"---"+sub_query_deps_all+"---"+sub_query_sups_all
                +"---"+sub_query_custs_all+"---"+sub_query_users_all
        );
        Log.e("LiNing", "-----" + sub_querys_db_all + sub_query_deps_all);
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {

                str_all = response.body().string();

                sp.edit().putString("ZCinfo", str_all).commit();
                Log.e("LiNing", "-----" + str_all + "-----" + response.code());
                Log.e("LiNing", "-----" + response.code());
                if (str_all != null) {
                    SalesQueryTwoActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            popupWindow.dismiss();
                        }
                    });
                } else {
                    Toast.makeText(SalesQueryTwoActivity.this, "无数据。。。", Toast.LENGTH_LONG)
                            .show();
                }
                final SaleMakeInfo aDb1 = new Gson().fromJson(str_all,
                        SaleMakeInfo.class);
                if (aDb1 != null) {
                    SalesQueryTwoActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            data = aDb1.getData();
                            head_all = aDb1.getHead();
                            Log.e("LiNing", "========" + data.size());
                            showlist();
                            // 转换成object
                        }

                    });
                }

            }

            @Override
            public void onFailure(Call arg0, IOException arg1) {

            }
        });
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
        // new Handler().postDelayed(new Runnable() {
        // @Override
        // public void run() {
        // popupWindow.dismiss();
        // }
        // }, 2000);
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
            postNoInfo();

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

    private String str_all;
    private String head_all;

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

        body = new FormBody.Builder()
                // .add("reprotNo", reportNo_get)
                .add("reprotNo", reportnos)
                .add("beginDate", start.getText().toString())
                .add("endDate", stop.getText().toString())
                // .add("reporyBusiness", "SA")
                .add("reporyBusiness", reportBus)
                .add("isGroupSum", arp_sum)
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
        Log.e("LiNing", "-----" + reportnos+"---888"+start.getText().toString()+"---888"+reportBus+"---888"+arp_sum+"---888"+sub_querys_db
                +"---888"+sub_bils+"---888"+sub_custNos+"---888"+sub_custNames+"---888"+sub_custPhones+"---888"+sub_custAdress+"---888"+sub_inputNos
                +"---888"+sub_cust_os+"---888"+sub_rems+"---888"+sub_prdMarks+"---888"+sub_query_deps+"---888"+sub_query_hss+"---888"+sub_query_sups
                +"---888"+sub_query_custs+"---888"+sub_query_users+"---888"+sub_chk_users+"---888"+sub_areas+"---888"+sub_employees
                +"---888"+sub_prdNos+"---888"+sub_prdIndexs+"---888"+sub_prdWhs);
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
                    SalesQueryTwoActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            popupWindow.dismiss();
                        }
                    });
                } else {
                    Toast.makeText(SalesQueryTwoActivity.this, "无数据。。。", Toast.LENGTH_LONG)
                            .show();
                }
                final SaleMakeInfo aDb1 = new Gson().fromJson(str_all,
                        SaleMakeInfo.class);
                if (aDb1 != null) {
                    SalesQueryTwoActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            data = aDb1.getData();
                            head_all = aDb1.getHead();
//                            Log.e("LiNing", "========" + data.size());
                            if(data!=null&&data.size()>0){

                                showlist();
                            }else{
                                Toast.makeText(context,"无数据",Toast.LENGTH_LONG).show();
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

    int pageSize;
    int lastnumber;
    private String db_ids_out;
    private String cb_ids_out;
    private String ml_ids_out;
    private String mll_ids_out;
    private String je_ids_out;
    private String affiliateNo_OUT;

    private void showlist() {
        settoOut();
        out_one_no = (List<Data>) data;
        sfadapter = new SalesInfoAdapter(context, data, reportnos);
        lv_get.setAdapter(sfadapter);
        sfadapter.notifyDataSetChanged();
        sp.edit().putString("NEWDATA_NO", "" + data).commit();
        if(lv_get.getCount()>0){
            tj_head_hint.setVisibility(View.GONE);
            tj_head_show.setVisibility(View.VISIBLE);
        }
    }

    private void settoOut() {
        dList = new ArrayList();
        item = new HashMap<String, Object>();
        try {
            String str_json_out = new Gson().toJson(data);
            Log.e("LiNing", "22222222222====" + str_json_out);
            JSONArray arr = new JSONArray(str_json_out);
            int size = arr.length();
            for (int i = 0; i < size; i++) {
                JSONObject jsonObject = arr.getJSONObject(i);

                Log.e("LiNing",
                        "22222222222===="
                                + jsonObject.get("affiliateNo").toString());
                // 表头
                if (jsonObject.get("affiliateNo").toString() != null) {

                    affiliateNo_OUT = jsonObject.get("affiliateNo").toString();
                }

                // if(jsonObject.get("brandNO").toString()!=null){
                //
                // String brandNO_OUT = jsonObject.get("brandNO").toString();
                // }
                // if(jsonObject.get("prdIndx").toString()!=null){
                // String prdIndx_OUT = jsonObject.get("prdIndx").toString();
                // }
                // if(jsonObject.get("prdNo").toString()!=null){
                //
                // String prdNo_OUT = jsonObject.get("prdNo").toString();
                // }
                // if(jsonObject.get("salesChannelNo").toString()!=null){
                //
                // String salesChannelNo_OUT =
                // jsonObject.get("salesChannelNo").toString();
                // }
                // if(jsonObject.get("salesDepartmentNo").toString()!=null){
                //
                // String salesDepartmentNo_OUT =
                // jsonObject.get("salesDepartmentNo").toString();
                // }
                // if(jsonObject.get("salesTerminalNo").toString()!=null){
                //
                // String salesTerminalNo_OUT =
                // jsonObject.get("salesTerminalNo").toString();
                // }
                // if(jsonObject.get("sellingOperationNo").toString()!=null){
                //
                // String sellingOperationNo_OUT =
                // jsonObject.get("sellingOperationNo").toString();
                // }
                // 数据
                String saAmtn_OUT = jsonObject.get("saAmtn").toString();
                // String sbsAmatn_OUT = jsonObject.get("sbsAmatn").toString();
                String sbAmtn_OUT = jsonObject.get("sbAmtn").toString();
                String ssAmtn_OUT = jsonObject.get("ssAmtn").toString();
                String sdAmtn_OUT = jsonObject.get("sdAmtn").toString();
                String snAmtn_OUT = jsonObject.get("snAmtn").toString();
                String saCst_OUT = jsonObject.get("saCst").toString();
                String sbCst_OUT = jsonObject.get("sbCst").toString();
                String snCst_OUT = jsonObject.get("snCst").toString();
                String gP_OUT = jsonObject.get("gP").toString();
                String gPM_OUT = jsonObject.get("gPM").toString();

                if (affiliateNo_OUT.equals(db_ids_out)) {
                    if (je_ids_out.equals("false")) {
                        saAmtn_OUT = "";
                        sbAmtn_OUT = "";
                        ssAmtn_OUT = "";
                        sdAmtn_OUT = "";
                        snAmtn_OUT = "";
                        // sbsAmatn_OUT="";
                    }
                    if (cb_ids_out.equals("false")) {
                        saCst_OUT = "";
                        sbCst_OUT = "";
                        snCst_OUT = "";
                    }
                    if (gP_OUT.equals("false")) {
                        gP_OUT = "";
                    }
                    if (gPM_OUT.equals("false")) {
                        gPM_OUT = "";
                    }
                    item.put("销售金额", saAmtn_OUT);
                    // item.put("退返金额", sbsAmatn_OUT);
                    item.put("退货金额", sbAmtn_OUT);
                    item.put("销售返点", ssAmtn_OUT);
                    item.put("销售折让", sdAmtn_OUT);
                    item.put("净销售额", snAmtn_OUT);
                    item.put("销售成本", saCst_OUT);
                    item.put("退货成本", sbCst_OUT);
                    item.put("净销成本", snCst_OUT);
                    item.put("销售毛利", gP_OUT);
                    item.put("毛利率", gPM_OUT);
                    item.put("销售折让", sdAmtn_OUT);
                    dList.add(item);
                    Log.e("LiNing", "22222222222====" + dList);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("LiNing", "22222222222==ALL==" + dList);
    }

//    public class SalesInfoAdapter extends BaseAdapter implements Serializable {//横竖屏切换必须实现接口。。。Adapter同理
    public class SalesInfoAdapter extends BaseAdapter  {
        Context context;
        List<com.example.bean.SaleMakeInfo.Data> brandsList;
        LayoutInflater mInflater;
        String index;
        private String brandName;
        private String tj_sj;
        private String cb_ids_xy;
        private String ml_ids_xy;
        private String mll_ids_xy;
        private String je_ids_xy;
        //item高亮显示
        private int selectItem = -1;

        public void setSelectItem(int selectItem) {
            this.selectItem = selectItem;
        }


        public SalesInfoAdapter(Context context,
                                List<com.example.bean.SaleMakeInfo.Data> data, String reportnos) {

            this.context = context;
            this.brandsList = data;
            this.mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.index = reportnos;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return brandsList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return brandsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                synchronized (SalesQueryTwoActivity.this) {
                    holder = new ViewHolder();
                    convertView = mInflater.inflate(R.layout.sale_info_test,
                            parent, false);
                    scrow(convertView);
                    holder.ll_head_1 = (LinearLayout) convertView
                            .findViewById(R.id.ll_gd_test);
                    holder.ll_head_2 = (LinearLayout) convertView
                            .findViewById(R.id.ll_gd1_test);
                    holder.ll_head_3 = (LinearLayout) convertView
                            .findViewById(R.id.ll_gd2_test);
                    holder.ll_head_4 = (LinearLayout) convertView
                            .findViewById(R.id.ll_gd3_test);
                    holder.affiliate = (TextView) convertView
                            .findViewById(R.id.textView1_id_item_test);
                    holder.brand = (TextView) convertView
                            .findViewById(R.id.tv_2_item_test);
                    holder.sales_channels = (TextView) convertView
                            .findViewById(R.id.tv_3_item_test);
                    holder.sales_dep = (TextView) convertView
                            .findViewById(R.id.tv_4_item_test);
                    holder.sales_oper = (TextView) convertView
                            .findViewById(R.id.tv_5_item_test);
                    holder.sa_amtn = (TextView) convertView
                            .findViewById(R.id.textView9_SaAmtn_item_test);
                    holder.sb_amtn = (TextView) convertView
                            .findViewById(R.id.textView10_SbAmtn_item_test);
                    holder.ss_amtn = (TextView) convertView
                            .findViewById(R.id.textView11_SsAmtn_item_test);
                    holder.sd_amtn = (TextView) convertView
                            .findViewById(R.id.textView12_SdAmtn_item_test);
                    holder.sn_amtn = (TextView) convertView
                            .findViewById(R.id.textView13_SnAmtn_item_test);
                    holder.sa_cst = (TextView) convertView
                            .findViewById(R.id.textView19_SaCst_item_test);
                    holder.sb_cst = (TextView) convertView
                            .findViewById(R.id.textView14_SbCst_item_test);
                    holder.sn_cst = (TextView) convertView
                            .findViewById(R.id.textView15_SnCst_item_test);
                    holder.sb_samatn = (TextView) convertView
                            .findViewById(R.id.textView16_SbsAmatn_item_test);
                    holder.gp = (TextView) convertView
                            .findViewById(R.id.textView17_GP_item_test);
                    holder.gpm = (TextView) convertView
                            .findViewById(R.id.textView18_GPM_item_test);
                    // if (cb_xy.equals("false")) {
                    // if (!cbs.contains("true")) {
                    // holder.sa_cst.setVisibility(View.GONE);
                    // holder.sb_cst.setVisibility(View.GONE);
                    // holder.sn_cst.setVisibility(View.GONE);
                    // }
                    // // if (ml_xy.equals("false")) {
                    // if (!mls.contains("true")) {
                    // holder.gp.setVisibility(View.GONE);
                    // }
                    // // if (mll_xy.equals("false")) {
                    // if (!mlls.contains("true")) {
                    // holder.gpm.setVisibility(View.GONE);
                    // }
                    // if (!jes.contains("true")) {
                    //
                    // }
                    convertView.setTag(holder);
                }
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Log.e("LiNing", "添加数据=====新=====" + brandsList.get(position));
            // 动态表头：判断表头包含信息，隐藏或显示无关字段（开始全隐藏）
            // 区分类型
            thirty(position, holder);
            // 退返金额=退货金额+销售返点
            if (index.equals("SATGPGCIPN") || index.equals("SATGPSIPN")
                    || index.equals("SATGPDIPN") || index.equals("SATGPCIPN")
                    || index.equals("SATGPGCPN") || index.equals("SATGPGCI")
                    || index.equals("SATGPSPN") || index.equals("SATGPSI")
                    || index.equals("SATGPDPN") || index.equals("SATGPDI")
                    || index.equals("SATGPCPN") || index.equals("SATGPCI")
                    || index.equals("SATGPIPN") || index.equals("SATGPPN")
                    || index.equals("SATGPI")) {
                holder.sb_amtn.setVisibility(View.GONE);
                holder.ss_amtn.setVisibility(View.GONE);
                holder.sb_samatn
                        .setText(brandsList.get(position).getSbsAmatn());
//				Log.e("LiNing",
//						"-----0/1-----" + holder.sb_amtn.getVisibility());
//				Log.e("LiNing",
//						"-----0/1--2---" + holder.ss_amtn.getVisibility());
            } else {
                holder.sb_samatn.setVisibility(View.GONE);
                holder.sb_amtn.setText(brandsList.get(position).getSbAmtn());
                holder.ss_amtn.setText(brandsList.get(position).getSsAmtn());
//				Log.e("LiNing",
//						"-----0/1-----" + holder.sb_amtn.getVisibility());
//				Log.e("LiNing",
//						"-----0/1--2---" + holder.ss_amtn.getVisibility());
            }
            // holder.affiliate.setText(brandsList.get(position)
            // .getAffiliateName());
            holder.affiliate.setText(brandsList.get(position)
                    .getAffiliateName());
            String qx_info = sp.getString("CBQX", "");
            Log.e("LiNing", "----" + qx_info);
            cbs_dj = new ArrayList<String>();
            mls_dj = new ArrayList<String>();
            mlls_dj = new ArrayList<String>();
            jes_dj = new ArrayList<String>();
            if (qx_info != null) {
                try {
                    JSONArray arr = new JSONArray(qx_info);
                    int size = arr.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject jsonObject = arr.getJSONObject(i);
                        String str_zt = jsonObject.get("账套").toString();
                        // if(brandsList.get(position).getAffiliateNo().equals(str_zt)){
                        cbs_dj.add(jsonObject.get("成本").toString());
                        mls_dj.add(jsonObject.get("毛利").toString());
                        mlls_dj.add(jsonObject.get("毛利率").toString());
                        jes_dj.add(jsonObject.get("金额").toString());
                        Log.e("LiNing", "===总计集合==" + cbs_dj + mls_dj + mlls_dj
                                + jes_dj);

                        if (brandsList.get(position).getAffiliateNo() != null) {
                            if (brandsList.get(position).getAffiliateNo()
                                    .equals(str_zt)) {
                                cb_ids_xy = jsonObject.get("成本").toString();
                                ml_ids_xy = jsonObject.get("毛利").toString();
                                mll_ids_xy = jsonObject.get("毛利率").toString();
                                je_ids_xy = jsonObject.get("金额").toString();
                                Log.e("LiNing", "===new====" + str_zt
                                        + cb_ids_xy + ml_ids_xy + mll_ids_xy
                                        + je_ids_xy);
                                // cbs_dj.add(cb_ids_xy);
                                // mls_dj.add(ml_ids_xy);
                                // mlls_dj.add(mll_ids_xy);
                                // jes_dj.add(je_ids_xy);
                                // Log.e("LiNing", "===new====" + cbs_dj
                                // + mls_dj + mlls_dj + jes_dj);
                                if (je_ids_xy.equals("false")) {
                                    // 判断显示（退返）
                                    holder.sa_amtn.setText("");
                                    holder.sn_amtn.setText("");
                                    holder.sb_amtn.setText("");
                                    holder.ss_amtn.setText("");
                                    holder.sb_samatn.setText("");
                                    // brandsList.get(position).setSaAmtn(holder.sa_amtn.getText().toString());
                                    // brandsList.get(position).setSnAmtn(holder.sn_amtn.getText().toString());
                                    // brandsList.get(position).setSbAmtn(holder.sb_amtn.getText().toString());
                                    // brandsList.get(position).setSsAmtn(holder.ss_amtn.getText().toString());
                                    // brandsList.get(position).setSbsAmatn(holder.sb_samatn.getText().toString());
                                    brandsList.get(position).setSaAmtn("");
                                    brandsList.get(position).setSnAmtn("");
                                    brandsList.get(position).setSbAmtn("");
                                    brandsList.get(position).setSsAmtn("");
                                    brandsList.get(position).setSbsAmatn("");

                                } else {
                                    holder.sa_amtn.setText(brandsList.get(
                                            position).getSaAmtn());
                                    holder.sn_amtn.setText(brandsList.get(
                                            position).getSnAmtn());
                                    holder.sb_amtn.setText(brandsList.get(
                                            position).getSbAmtn());
                                    holder.ss_amtn.setText(brandsList.get(
                                            position).getSsAmtn());
                                    holder.sb_samatn.setText(brandsList.get(
                                            position).getSbsAmatn());
                                }
                                if (cb_ids_xy.equals("false")) {
                                    holder.sa_cst.setText("");
                                    holder.sb_cst.setText("");
                                    holder.sn_cst.setText("");
                                    // brandsList.get(position).setSaCst(holder.sa_cst.getText().toString());
                                    // brandsList.get(position).setSbCst(holder.sb_cst.getText().toString());
                                    // brandsList.get(position).setSnCst(holder.sn_cst.getText().toString());
                                    brandsList.get(position).setSaCst("");
                                    brandsList.get(position).setSbCst("");
                                    brandsList.get(position).setSnCst("");
                                } else {
                                    holder.sa_cst.setText(brandsList.get(
                                            position).getSaCst());
                                    holder.sb_cst.setText(brandsList.get(
                                            position).getSbCst());
                                    holder.sn_cst.setText(brandsList.get(
                                            position).getSnCst());
                                }
                                if (mll_ids_xy.equals("false")) {
                                    holder.gpm.setText("");
                                    // brandsList.get(position).setgPM(holder.gpm.getText().toString());
                                    brandsList.get(position).setgPM("");
                                } else {
                                    holder.gpm.setText(brandsList.get(position)
                                            .getgPM());
                                }
                                if (ml_ids_xy.equals("false")) {
                                    holder.gp.setText("");
                                    brandsList.get(position).setgP("");
                                    // brandsList.get(position).setgP(holder.gp.getText().toString());
                                } else {
                                    holder.gp.setText(brandsList.get(position)
                                            .getgP());
                                }
                            }
                            if (brandsList.get(position).getAffiliateNo()
                                    .equals("All")) {
                                Log.e("LiNing", "===总计封装===zj=" + cbs_dj
                                        + mls_dj + mlls_dj + jes_dj);
                                if (cbs_dj.contains("false")) {
                                    holder.sa_cst.setText("");
                                    holder.sb_cst.setText("");
                                    holder.sn_cst.setText("");
                                    // brandsList.get(position).setSaCst(holder.sa_cst.getText().toString());
                                    // brandsList.get(position).setSbCst(holder.sb_cst.getText().toString());
                                    // brandsList.get(position).setSnCst(holder.sn_cst.getText().toString());
                                    brandsList.get(position).setSaCst("");
                                    brandsList.get(position).setSbCst("");
                                    brandsList.get(position).setSnCst("");
                                } else {
                                    holder.sa_cst.setText(brandsList.get(
                                            position).getSaCst());
                                    holder.sb_cst.setText(brandsList.get(
                                            position).getSbCst());
                                    holder.sn_cst.setText(brandsList.get(
                                            position).getSnCst());
                                }
                                if (mls_dj.contains("false")) {
                                    holder.gp.setText("");
                                    brandsList.get(position).setgP("");
                                    // brandsList.get(position).setgP(holder.gp.getText().toString());
                                } else {
                                    holder.gp.setText(brandsList.get(position)
                                            .getgP());
                                }
                                if (mlls_dj.contains("false")) {
                                    holder.gpm.setText("");
                                    brandsList.get(position).setgPM("");
                                    // brandsList.get(position).setgPM(holder.gpm.getText().toString());
                                } else {
                                    holder.gpm.setText(brandsList.get(position)
                                            .getgPM());
                                }
                                if (jes_dj.contains("false")) {
                                    holder.sa_amtn.setText("");
                                    holder.sn_amtn.setText("");
                                    holder.sb_amtn.setText("");
                                    holder.ss_amtn.setText("");
                                    holder.sb_samatn.setText("");
                                    // brandsList.get(position).setSaAmtn(holder.sa_amtn.getText().toString());
                                    // brandsList.get(position).setSnAmtn(holder.sn_amtn.getText().toString());
                                    // brandsList.get(position).setSbAmtn(holder.sb_amtn.getText().toString());
                                    // brandsList.get(position).setSsAmtn(holder.ss_amtn.getText().toString());
                                    // brandsList.get(position).setSbsAmatn(holder.sb_samatn.getText().toString());
                                    brandsList.get(position).setSaAmtn("");
                                    brandsList.get(position).setSnAmtn("");
                                    brandsList.get(position).setSbAmtn("");
                                    brandsList.get(position).setSsAmtn("");
                                    brandsList.get(position).setSbsAmatn("");
                                } else {
                                    holder.sa_amtn.setText(brandsList.get(
                                            position).getSaAmtn());
                                    holder.sn_amtn.setText(brandsList.get(
                                            position).getSnAmtn());
                                    holder.sb_amtn.setText(brandsList.get(
                                            position).getSbAmtn());
                                    holder.ss_amtn.setText(brandsList.get(
                                            position).getSsAmtn());
                                    holder.sb_samatn.setText(brandsList.get(
                                            position).getSbsAmatn());
                                }

                            }

                        } else {
                            // 账套为空，后边不为空。。。
                            // holder.sa_amtn.setText(brandsList.get(
                            // position).getSaAmtn());
                            // holder.sn_amtn.setText(brandsList.get(
                            // position).getSnAmtn());
                            // holder.sb_amtn.setText(brandsList.get(
                            // position).getSbAmtn());
                            // holder.ss_amtn.setText(brandsList.get(
                            // position).getSsAmtn());
                            // holder.sb_samatn.setText(brandsList.get(
                            // position).getSbsAmatn());
                            // holder.sa_cst.setText(brandsList.get(position)
                            // .getSaCst());
                            // holder.sb_cst.setText(brandsList.get(position)
                            // .getSbCst());
                            // holder.sn_cst.setText(brandsList.get(position)
                            // .getSnCst());
                            // holder.gp.setText(brandsList.get(position).getgP());
                            // holder.gpm.setText(brandsList.get(position)
                            // .getgPM());
                        }
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }

            // holder.sa_amtn.setText(brandsList.get(position).getSaAmtn());
            holder.sd_amtn.setText(brandsList.get(position).getSdAmtn());
            // holder.sn_amtn.setText(brandsList.get(position).getSnAmtn());
            // 小计【整体】
            test = brandsList;
            Log.e("LiNing", "--新数据" + test);


            if (position == selectItem) {
                convertView.setBackgroundColor(Color.RED);
            } else {
                convertView.setBackgroundColor(Color.TRANSPARENT);
            }
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

        private void thirty(int position, ViewHolder holder) {
            if (index.equals("SATG")) {// 2个条件
                holder.ll_head_1.setVisibility(View.GONE);
                threeNo(holder);
            }
            if (index.equals("SATGC")) {// 2个条件
                holder.ll_head_1.setVisibility(View.VISIBLE);
                threeNo(holder);

                holder.brand.setText(brandsList.get(position)
                        .getSalesChannelName());

            }
            if (index.equals("SATGD")) {
                holder.ll_head_1.setVisibility(View.VISIBLE);
                threeNo(holder);
                holder.brand.setText(brandsList.get(position)
                        .getSalesDepartmentName());

            }
            if (index.equals("SATGS")) {
                holder.ll_head_1.setVisibility(View.VISIBLE);
                threeNo(holder);
                holder.brand.setText(brandsList.get(position)
                        .getSellingOperationName());

            }
            if (index.equals("SATGP")) {
                holder.ll_head_1.setVisibility(View.VISIBLE);
                threeNo(holder);
                holder.brand.setText(brandsList.get(position).getBrandName());

            }
            if (index.equals("SATGGC")) {
                holder.ll_head_1.setVisibility(View.VISIBLE);
                threeNo(holder);
                holder.brand.setText(brandsList.get(position)
                        .getSalesTerminalName());

            }
            if (index.equals("SATGPC")) {// 3个条件
                holder.ll_head_1.setVisibility(View.VISIBLE);
                holder.ll_head_2.setVisibility(View.VISIBLE);
                twoNo(holder);
                holder.brand.setText(brandsList.get(position).getBrandName());
                holder.sales_channels.setText(brandsList.get(position)
                        .getSalesChannelName());
                Log.e("LiNing", "qd============="
                        + brandsList.get(position).getSalesChannelName());
            }
            if (index.equals("SATGPI")) {
                holder.ll_head_1.setVisibility(View.VISIBLE);
                holder.ll_head_2.setVisibility(View.VISIBLE);
                twoNo(holder);
                holder.brand.setText(brandsList.get(position).getBrandName());
                holder.sales_channels.setText(brandsList.get(position)
                        .getPrdIndx());

            }
            if (index.equals("SATGPPN")) {
                holder.ll_head_1.setVisibility(View.VISIBLE);
                holder.ll_head_2.setVisibility(View.VISIBLE);
                twoNo(holder);
                holder.brand.setText(brandsList.get(position).getBrandName());
                holder.sales_channels.setText(brandsList.get(position)
                        .getPrdNo());
                Log.e("LiNing", "qd============="
                        + brandsList.get(position).getSalesChannelName());

            }
            if (index.equals("SATGSGC")) {
                holder.ll_head_1.setVisibility(View.VISIBLE);
                holder.ll_head_2.setVisibility(View.VISIBLE);
                twoNo(holder);
                holder.brand.setText(brandsList.get(position)
                        .getSellingOperationName());
                holder.sales_channels.setText(brandsList.get(position)
                        .getSalesTerminalName());

            }
            if (index.equals("SATGDGC")) {
                holder.ll_head_1.setVisibility(View.VISIBLE);
                holder.ll_head_2.setVisibility(View.VISIBLE);
                twoNo(holder);
                holder.brand.setText(brandsList.get(position)
                        .getSalesDepartmentName());
                holder.sales_channels.setText(brandsList.get(position)
                        .getSalesTerminalName());

            }
            if (index.equals("SATGDS")) {
                holder.ll_head_1.setVisibility(View.VISIBLE);
                holder.ll_head_2.setVisibility(View.VISIBLE);
                twoNo(holder);
                holder.brand.setText(brandsList.get(position)
                        .getSalesDepartmentName());
                holder.sales_channels.setText(brandsList.get(position)
                        .getSellingOperationName());

            }
            if (index.equals("SATGCGC")) {
                holder.ll_head_1.setVisibility(View.VISIBLE);
                holder.ll_head_2.setVisibility(View.VISIBLE);
                twoNo(holder);
                holder.brand.setText(brandsList.get(position)
                        .getSalesChannelName());
                holder.sales_channels.setText(brandsList.get(position)
                        .getSalesTerminalName());

            }
            if (index.equals("SATGCS")) {
                holder.ll_head_1.setVisibility(View.VISIBLE);
                holder.ll_head_2.setVisibility(View.VISIBLE);
                twoNo(holder);
                holder.brand.setText(brandsList.get(position)
                        .getSalesChannelName());
                holder.sales_channels.setText(brandsList.get(position)
                        .getSellingOperationName());

            }
            if (index.equals("SATGCD")) {
                holder.ll_head_1.setVisibility(View.VISIBLE);
                holder.ll_head_2.setVisibility(View.VISIBLE);
                twoNo(holder);
                holder.brand.setText(brandsList.get(position)
                        .getSalesChannelName());
                holder.sales_channels.setText(brandsList.get(position)
                        .getSalesDepartmentName());

            }
            if (index.equals("SATGPGC")) {
                holder.ll_head_1.setVisibility(View.VISIBLE);
                holder.ll_head_2.setVisibility(View.VISIBLE);
                twoNo(holder);
                holder.brand.setText(brandsList.get(position).getBrandName());
                holder.sales_channels.setText(brandsList.get(position)
                        .getSalesTerminalName());

            }
            if (index.equals("SATGPD")) {
                holder.ll_head_1.setVisibility(View.VISIBLE);
                holder.ll_head_2.setVisibility(View.VISIBLE);
                twoNo(holder);
                holder.brand.setText(brandsList.get(position).getBrandName());
                holder.sales_channels.setText(brandsList.get(position)
                        .getSalesDepartmentName());
                Log.e("LiNing", "qd============="
                        + brandsList.get(position).getSalesChannelName());
            }
            if (index.equals("SATGPS")) {
                holder.ll_head_1.setVisibility(View.VISIBLE);
                holder.ll_head_2.setVisibility(View.VISIBLE);
                twoNo(holder);
                holder.brand.setText(brandsList.get(position).getBrandName());
                holder.sales_channels.setText(brandsList.get(position)
                        .getSellingOperationName());
                Log.e("LiNing", "qd============="
                        + brandsList.get(position).getSalesChannelName());
            }
            // 11
            if (index.equals("SATGPGCI")) {// 4个条件
                threeOk(holder);

                holder.ll_head_4.setVisibility(View.GONE);

                holder.brand.setText(brandsList.get(position).getBrandName());
                holder.sales_channels.setText(brandsList.get(position)
                        .getSalesTerminalName());
                holder.sales_dep.setText(brandsList.get(position).getPrdIndx());

            }
            if (index.equals("SATGPIPN")) {
                threeOk(holder);

                holder.ll_head_4.setVisibility(View.GONE);
                holder.brand.setText(brandsList.get(position).getBrandName());
                holder.sales_channels.setText(brandsList.get(position)
                        .getPrdIndx());
                holder.sales_dep.setText(brandsList.get(position).getPrdNo());

            }
            if (index.equals("SATGPCPN")) {
                threeOk(holder);

                holder.ll_head_4.setVisibility(View.GONE);
                holder.brand.setText(brandsList.get(position).getBrandName());
                holder.sales_channels.setText(brandsList.get(position)
                        .getSalesChannelName());
                holder.sales_dep.setText(brandsList.get(position).getPrdNo());

            }
            if (index.equals("SATGPCI")) {
                threeOk(holder);

                holder.ll_head_4.setVisibility(View.GONE);
                holder.brand.setText(brandsList.get(position).getBrandName());
                holder.sales_channels.setText(brandsList.get(position)
                        .getSalesChannelName());
                holder.sales_dep.setText(brandsList.get(position).getPrdIndx());

            }
            if (index.equals("SATGPDPN")) {
                threeOk(holder);

                holder.ll_head_4.setVisibility(View.GONE);
                holder.brand.setText(brandsList.get(position).getBrandName());
                holder.sales_channels.setText(brandsList.get(position)
                        .getSalesDepartmentName());
                holder.sales_dep.setText(brandsList.get(position).getPrdNo());

            }
            if (index.equals("SATGPDI")) {
                threeOk(holder);

                holder.ll_head_4.setVisibility(View.GONE);
                holder.brand.setText(brandsList.get(position).getBrandName());
                holder.sales_channels.setText(brandsList.get(position)
                        .getSalesDepartmentName());
                holder.sales_dep.setText(brandsList.get(position).getPrdIndx());

            }
            if (index.equals("SATGPSPN")) {
                threeOk(holder);

                holder.ll_head_4.setVisibility(View.GONE);
                holder.brand.setText(brandsList.get(position).getBrandName());
                holder.sales_channels.setText(brandsList.get(position)
                        .getSellingOperationName());
                holder.sales_dep.setText(brandsList.get(position).getPrdNo());

            }
            if (index.equals("SATGPSI")) {
                threeOk(holder);

                holder.ll_head_4.setVisibility(View.GONE);
                holder.brand.setText(brandsList.get(position).getBrandName());
                holder.sales_channels.setText(brandsList.get(position)
                        .getSellingOperationName());
                holder.sales_dep.setText(brandsList.get(position).getPrdIndx());

            }
            if (index.equals("SATGPGCPN")) {
                threeOk(holder);

                holder.ll_head_4.setVisibility(View.GONE);
                holder.brand.setText(brandsList.get(position).getBrandName());
                holder.sales_channels.setText(brandsList.get(position)
                        .getSalesTerminalName());
                holder.sales_dep.setText(brandsList.get(position).getPrdNo());

            }
            if (index.equals("SATGPGCIPN")) {// 5个条件
                threeOk(holder);

                holder.ll_head_4.setVisibility(View.VISIBLE);
                holder.brand.setText(brandsList.get(position).getBrandName());
                holder.sales_channels.setText(brandsList.get(position)
                        .getSalesTerminalName());
                holder.sales_dep.setText(brandsList.get(position).getPrdIndx());
                holder.sales_oper.setText(brandsList.get(position).getPrdNo());

            }
            if (index.equals("SATGPCIPN")) {
                threeOk(holder);

                holder.ll_head_4.setVisibility(View.VISIBLE);
                holder.brand.setText(brandsList.get(position).getBrandName());
                holder.sales_channels.setText(brandsList.get(position)
                        .getSalesChannelName());
                holder.sales_dep.setText(brandsList.get(position).getPrdIndx());
                holder.sales_oper.setText(brandsList.get(position).getPrdNo());

            }
            if (index.equals("SATGPDIPN")) {
                threeOk(holder);

                holder.ll_head_4.setVisibility(View.VISIBLE);
                holder.brand.setText(brandsList.get(position).getBrandName());
                holder.sales_channels.setText(brandsList.get(position)
                        .getSalesDepartmentName());
                holder.sales_dep.setText(brandsList.get(position).getPrdIndx());
                holder.sales_oper.setText(brandsList.get(position).getPrdNo());

            }
            if (index.equals("SATGPSIPN")) {
                threeOk(holder);

                holder.ll_head_4.setVisibility(View.VISIBLE);
                holder.brand.setText(brandsList.get(position).getBrandName());
                holder.sales_channels.setText(brandsList.get(position)
                        .getSellingOperationName());
                holder.sales_dep.setText(brandsList.get(position).getPrdIndx());

                holder.sales_oper.setText(brandsList.get(position).getPrdNo());

            }
        }

        private void threeOk(ViewHolder holder) {
            holder.ll_head_1.setVisibility(View.VISIBLE);
            holder.ll_head_2.setVisibility(View.VISIBLE);
            holder.ll_head_3.setVisibility(View.VISIBLE);
        }

        private void twoNo(ViewHolder holder) {
            holder.ll_head_3.setVisibility(View.GONE);
            holder.ll_head_4.setVisibility(View.GONE);
        }

        private void threeNo(ViewHolder holder) {
            holder.ll_head_2.setVisibility(View.GONE);
            holder.ll_head_3.setVisibility(View.GONE);
            holder.ll_head_4.setVisibility(View.GONE);
            // twoNo(holder);
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
            LinearLayout ll_head_1;
            LinearLayout ll_head_2;
            LinearLayout ll_head_3;
            LinearLayout ll_head_4;
            TextView affiliate;// 分支机构
            TextView brand;// 品牌
            TextView sales_channels;// 渠道
            TextView sales_dep;// 部门
            TextView sales_oper;// 业务
            TextView sales_ter;// 终端
            TextView sa_amtn;// 销售金额
            TextView sb_amtn;// 退货金额
            TextView ss_amtn;// 销售返点
            TextView sd_amtn;// 销售折让
            TextView sn_amtn;// 净销售额
            TextView sa_cst;// 销售成本
            TextView sb_cst;// 退货成本
            TextView sn_cst;// 净销成本
            TextView sb_samatn;// 退返金额
            TextView gp;// 销售毛利
            TextView gpm;// 毛利率
            TextView prdno;// 货品编号
            TextView prdindx;// 货品中类
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 2:
                if (resultCode == 1) {
                    String str1id = data.getStringExtra("data_return");
                    saleNum.setText(str1id);
                    Log.e("LiNing", "提交的id====" + str1id + saleNum);
                }
                break;

            default:
                break;
        }
    }

    // 表头滑动事件
    class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {
        ListViewAndHeadViewTouchLinstener() {
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ((HorizontalScrollView) SalesQueryTwoActivity.this.mHead
                    .findViewById(R.id.horizontalScrollView1))
                    .onTouchEvent(event);
            return false;
        }

    }

    private void getFlagreportNo() {
        // String flagBh = saleNum.getText().toString();
        String flagBh = reportnos;
        // 2--5
        if (flagBh.equals("SATGPGCIPN") || flagBh.equals("SATGPSIPN")
                || flagBh.equals("SATGPDIPN") || flagBh.equals("SATGPCIPN")
                || flagBh.equals("SATGPGCPN") || flagBh.equals("SATGPSPN")
                || flagBh.equals("SATGPDPN") || flagBh.equals("SATGPCPN")
                || flagBh.equals("SATGPIPN") || flagBh.equals("SATGPPN")
                || flagBh.equals("SATGPGCI") || flagBh.equals("SATGPSI")
                || flagBh.equals("SATGPDI") || flagBh.equals("SATGPCI")
                || flagBh.equals("SATGPI")) {

            findViewById(R.id.textView10_SbAmtn).setVisibility(View.GONE);
            findViewById(R.id.textView11_SsAmtn).setVisibility(View.GONE);
        } else {
            findViewById(R.id.textView16_SbsAmatn).setVisibility(View.GONE);
        }
        if (flagBh.equals("SATG")) {
            tv_two.setVisibility(View.GONE);
            tv_three.setVisibility(View.GONE);
            tv_four.setVisibility(View.GONE);
        }
        if (flagBh.equals("SATGP")) {
            // tv_one.setVisibility(View.VISIBLE);
            ll_one.setVisibility(View.VISIBLE);
            tv_one.setText("品牌");
            twoShow();
        }
        if (flagBh.equals("SATGC")) {
            ll_one.setVisibility(View.VISIBLE);
            tv_one.setText("渠道");

            twoShow();
        }
        if (flagBh.equals("SATGD")) {
            ll_one.setVisibility(View.VISIBLE);
            tv_one.setText("部门");

            twoShow();
        }
        if (flagBh.equals("SATGS")) {
            ll_one.setVisibility(View.VISIBLE);
            tv_one.setText("业务");

            twoShow();
        }
        if (flagBh.equals("SATGGC")) {
            ll_one.setVisibility(View.VISIBLE);
            tv_one.setText("终端网点");

            twoShow();
        }
        // 3
        if (flagBh.equals("SATGPC")) {
            ll_one.setVisibility(View.VISIBLE);
            ll_two.setVisibility(View.VISIBLE);
            tv_one.setText("品牌");
            tv_two.setText("渠道");

            threeShow();

        }
        if (flagBh.equals("SATGPD")) {
            ll_one.setVisibility(View.VISIBLE);
            ll_two.setVisibility(View.VISIBLE);
            tv_one.setText("品牌");
            tv_two.setText("部门");

            threeShow();
        }
        if (flagBh.equals("SATGPS")) {
            ll_one.setVisibility(View.VISIBLE);
            ll_two.setVisibility(View.VISIBLE);
            tv_one.setText("品牌");
            tv_two.setText("业务");

            threeShow();
        }
        if (flagBh.equals("SATGPGC")) {
            ll_one.setVisibility(View.VISIBLE);
            ll_two.setVisibility(View.VISIBLE);
            tv_one.setText("品牌");
            tv_two.setText("终端网点");

            threeShow();
        }
        if (flagBh.equals("SATGCD")) {
            ll_one.setVisibility(View.VISIBLE);
            ll_two.setVisibility(View.VISIBLE);
            tv_one.setText("渠道");
            tv_two.setText("部门");

            threeShow();
        }
        if (flagBh.equals("SATGCS")) {
            ll_one.setVisibility(View.VISIBLE);
            ll_two.setVisibility(View.VISIBLE);
            tv_one.setText("渠道");
            tv_two.setText("业务");

            threeShow();
        }
        if (flagBh.equals("SATGCGC")) {
            ll_one.setVisibility(View.VISIBLE);
            ll_two.setVisibility(View.VISIBLE);
            tv_one.setText("渠道");
            tv_two.setText("终端网点");

            threeShow();
        }
        if (flagBh.equals("SATGDS")) {
            ll_one.setVisibility(View.VISIBLE);
            ll_two.setVisibility(View.VISIBLE);
            tv_one.setText("部门");
            tv_two.setText("业务");

            threeShow();
        }
        if (flagBh.equals("SATGDGC")) {
            ll_one.setVisibility(View.VISIBLE);
            ll_two.setVisibility(View.VISIBLE);
            tv_one.setText("部门");
            tv_two.setText("终端网点");

            threeShow();
        }
        if (flagBh.equals("SATGSGC")) {
            ll_one.setVisibility(View.VISIBLE);
            ll_two.setVisibility(View.VISIBLE);
            tv_one.setText("业务");
            tv_two.setText("终端网点");

            threeShow();
        }
        if (flagBh.equals("SATGPPN")) {
            ll_one.setVisibility(View.VISIBLE);
            ll_two.setVisibility(View.VISIBLE);
            tv_one.setText("品牌");
            tv_two.setText("品号");

            threeShow();
        }
        if (flagBh.equals("SATGPI")) {
            ll_one.setVisibility(View.VISIBLE);
            ll_two.setVisibility(View.VISIBLE);
            tv_one.setText("品牌");
            tv_two.setText("货品中类");

            threeShow();
        }
        // 4
        if (flagBh.equals("SATGPIPN")) {
            pp_sub = 5;
            ll_one.setVisibility(View.VISIBLE);
            ll_two.setVisibility(View.VISIBLE);
            ll_three.setVisibility(View.VISIBLE);
            tv_one.setText("品牌");
            tv_two.setText("货品中类");
            tv_three.setText("品号");

            tv_four.setVisibility(View.GONE);
        }
        if (flagBh.equals("SATGPCPN")) {
            pp_sub = 1;
            ll_one.setVisibility(View.VISIBLE);
            ll_two.setVisibility(View.VISIBLE);
            ll_three.setVisibility(View.VISIBLE);
            tv_one.setText("品牌");
            tv_two.setText("渠道");
            tv_three.setText("品号");

            tv_four.setVisibility(View.GONE);
        }
        if (flagBh.equals("SATGPCI")) {
            pp_sub = 1;
            ll_one.setVisibility(View.VISIBLE);
            ll_two.setVisibility(View.VISIBLE);
            ll_three.setVisibility(View.VISIBLE);
            tv_one.setText("品牌");
            tv_two.setText("渠道");
            tv_three.setText("货品中类");

            tv_four.setVisibility(View.GONE);
        }
        if (flagBh.equals("SATGPDPN")) {
            pp_sub = 2;
            ll_one.setVisibility(View.VISIBLE);
            ll_two.setVisibility(View.VISIBLE);
            ll_three.setVisibility(View.VISIBLE);
            tv_one.setText("品牌");
            tv_two.setText("部门");
            tv_three.setText("品号");

            tv_four.setVisibility(View.GONE);
        }
        if (flagBh.equals("SATGPDI")) {
            pp_sub = 2;
            ll_one.setVisibility(View.VISIBLE);
            ll_two.setVisibility(View.VISIBLE);
            ll_three.setVisibility(View.VISIBLE);
            tv_one.setText("品牌");
            tv_two.setText("部门");
            tv_three.setText("货品中类");

            tv_four.setVisibility(View.GONE);
        }
        if (flagBh.equals("SATGPSPN")) {
            pp_sub = 3;
            ll_one.setVisibility(View.VISIBLE);
            ll_two.setVisibility(View.VISIBLE);
            ll_three.setVisibility(View.VISIBLE);
            tv_one.setText("品牌");
            tv_two.setText("业务");
            tv_three.setText("品号");

            tv_four.setVisibility(View.GONE);
        }
        if (flagBh.equals("SATGPSI")) {
            pp_sub = 3;
            ll_one.setVisibility(View.VISIBLE);
            ll_two.setVisibility(View.VISIBLE);
            ll_three.setVisibility(View.VISIBLE);
            tv_one.setText("品牌");
            tv_two.setText("业务");
            tv_three.setText("货品中类");

            tv_four.setVisibility(View.GONE);
        }
        if (flagBh.equals("SATGPGCPN")) {
            pp_sub = 4;
            ll_one.setVisibility(View.VISIBLE);
            ll_two.setVisibility(View.VISIBLE);
            ll_three.setVisibility(View.VISIBLE);
            tv_one.setText("品牌");
            tv_two.setText("终端");
            tv_three.setText("品号");

            tv_four.setVisibility(View.GONE);
        }
        if (flagBh.equals("SATGPGCI")) {
            pp_sub = 4;
            ll_one.setVisibility(View.VISIBLE);
            ll_two.setVisibility(View.VISIBLE);
            ll_three.setVisibility(View.VISIBLE);
            tv_one.setText("品牌");
            tv_two.setText("终端");
            tv_three.setText("货品中类");

            tv_four.setVisibility(View.GONE);
        }
        // 5
        if (flagBh.equals("SATGPCIPN")) {
            // pp_sub = 6;
            pp_sub = 4;
            PH_TF = 1;
            ll_one.setVisibility(View.VISIBLE);
            ll_two.setVisibility(View.VISIBLE);
            ll_three.setVisibility(View.VISIBLE);
            ll_fore.setVisibility(View.VISIBLE);
            tv_one.setText("品牌");
            tv_two.setText("渠道");
            tv_three.setText("货品中类");
            tv_four.setText("品号");

        }
        if (flagBh.equals("SATGPDIPN")) {
            // pp_sub = 6;
            pp_sub = 5;
            PH_TF = 1;
            ll_one.setVisibility(View.VISIBLE);
            ll_two.setVisibility(View.VISIBLE);
            ll_three.setVisibility(View.VISIBLE);
            ll_fore.setVisibility(View.VISIBLE);
            tv_one.setText("品牌");
            tv_two.setText("部门");
            tv_three.setText("货品中类");
            tv_four.setText("品号");

        }
        if (flagBh.equals("SATGPSIPN")) {
            // pp_sub = 6;
            pp_sub = 3;
            PH_TF = 1;
            ll_one.setVisibility(View.VISIBLE);
            ll_two.setVisibility(View.VISIBLE);
            ll_three.setVisibility(View.VISIBLE);
            ll_fore.setVisibility(View.VISIBLE);
            tv_one.setText("品牌");
            tv_two.setText("业务");
            tv_three.setText("货品中类");
            tv_four.setText("品号");

        }
        if (flagBh.equals("SATGPGCIPN")) {
            // pp_sub = 6;
            pp_sub = 4;
            PH_TF = 1;
            ll_one.setVisibility(View.VISIBLE);
            ll_two.setVisibility(View.VISIBLE);
            ll_three.setVisibility(View.VISIBLE);
            ll_fore.setVisibility(View.VISIBLE);
            tv_one.setText("品牌");
            tv_two.setText("终端");
            tv_three.setText("货品中类");
            tv_four.setText("品号");

        }
        // if (flagBh.equals("SATGPGCIPN") || flagBh.equals("SATGPSIPN")
        // || flagBh.equals("SATGPDIPN") || flagBh.equals("SATGPCIPN")
        // || flagBh.equals("SATGPGCPN") || flagBh.equals("SATGPSPN")
        // || flagBh.equals("SATGPDPN") || flagBh.equals("SATGPCPN")
        // || flagBh.equals("SATGPIPN") || flagBh.equals("SATGPPN")
        // || flagBh.equals("SATGPIPN")) {
        // hj_sbamtn.setVisibility(View.GONE);
        // hj_ssamtn.setVisibility(View.GONE);
        // } else {
        // hj_sbsamtn.setVisibility(View.GONE);
        // }
    }

    private void threeShow() {
        tv_three.setVisibility(View.GONE);
        tv_four.setVisibility(View.GONE);
    }

    private void twoShow() {
        tv_two.setVisibility(View.GONE);

        threeShow();
    }

    public void allback(View v) {
        finish();
        // 时间保存
        saveTime();
        // page_get="0";
    }

    private void saveTime() {
        sp.edit().putString("START_time", start.getText().toString()).commit();
        sp.edit().putString("STOP_time", stop.getText().toString()).commit();
    }

    public static String string2BigDecimal(String str) {
        DecimalFormat df1 = new DecimalFormat("#0.00"); // 保留两位小数，如果不足两位小数则自动补零
        String val = df1.format(Double.valueOf(str));
        return val;
    }
}
