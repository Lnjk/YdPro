package com.example.ydshoa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.LeftOrRight.MyHScrollView;
import com.example.bean.OnItemClickListenerPrice;
import com.example.bean.PriceLoadInfo;
import com.example.bean.PriceMx;
import com.example.bean.URLS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddMoreActivity extends Activity implements View.OnClickListener {
    private Context context;
    private List<PriceLoadInfo.Prdt> prdt_add;
    private List<PriceMx.PrdtUp> prdt_add_mx;
    PriceLoadInfo.Prdt prdt_set;
    ArrayList<String> ls_set;
    int one_add = 0;
    int more = 0;
    private ImageButton query_sup, query_compdep, employee, prdNo, prdIndx, prdWh, prdLevre, prdKND, showStop;
    private EditText dwcb_item, tydj_item, zdsj_item, zcdj_item, xszk_item, zxqk_item, bzxx_item, et_num,
            et_tyzk, zcsm, zcmc, et_seach;
    private TextView zxqk, brand_item, brand_name_item, head, db_check, brand_check, hs_check, cust_check, user_check, operate_check, price_num_id, prdMrk, prdName, prdName_ENG,
            tv_qurey_sup, tv_query_compdep, tv_employee, tv_prdNo, tv_prdIndx, tv_prdWh, tv_prdLevre, tv_prdKND, tv_showStop;
    RelativeLayout mHead;
    ListView mListView1_set;
    private List<String> idList_prdnoID, idList_prdnoName, idList_prdIndexID, idList_prdIndexName, idList_prdWhID, idList_prdWhName,
            idList_employeeName, idList_employeeID, idList_querySup, idList_querySupID, idList_compDep, idList_compDepID,
            idList_checkBread, idList_checkBreadID, idList_checkHs, idList_checkHsID, idList_checkCust, idList_checkCustID, idList_hpdj, idList_hpdl, idList_hpdjID, idList_hpdlID;
    private PriceActivity.MxPriceAdapter mxPriceAdapter;
    private PriceActivity.PriceAdapter priceAdapter;
    PriceMx.PrdtUp mx_ls_mor, prdt3;
    String prd_no;
    PriceLoadInfo.Prdt prdt2;
    String done, db, onclik_go;
    int checked;
    private AlertDialog alertDialog;
    private String id_prdNo, id_employee, id_hs, id_prdIndex, id_prdWh, id_querySup;
    String hpdj_id;
    String hpdl_id;
    private String prdLevre_comit_jz;
    private String prdKND_comit_jz;
    private SharedPreferences sp;
    private String session;
    String url_load_price = URLS.price_load;//货品加载
    private List<PriceLoadInfo.Prdt> prdt;
    AddMoreAdapter moreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_more);
        context = AddMoreActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        done = getIntent().getStringExtra("DONE");
        db = getIntent().getStringExtra("DB_PRICE");
        onclik_go = getIntent().getStringExtra("ON_CLICK");
        more = 1;
        one_add = 2;
        prdt_add = new ArrayList<PriceLoadInfo.Prdt>();
        prdt_add_mx = new ArrayList<PriceMx.PrdtUp>();
        intView();
    }

    private void intView() {
        //记录
        idList_prdnoID = new ArrayList<String>();
        idList_prdnoName = new ArrayList<String>();
        idList_prdIndexID = new ArrayList<String>();
        idList_prdIndexName = new ArrayList<String>();
        idList_prdWhID = new ArrayList<String>();
        idList_prdWhName = new ArrayList<String>();
        idList_employeeID = new ArrayList<String>();
        idList_employeeName = new ArrayList<String>();
        idList_querySupID = new ArrayList<String>();
        idList_querySup = new ArrayList<String>();
        idList_compDep = new ArrayList<String>();
        idList_compDepID = new ArrayList<String>();
        idList_checkBread = new ArrayList<String>();
        idList_checkBreadID = new ArrayList<String>();
        idList_checkHs = new ArrayList<String>();
        idList_checkHsID = new ArrayList<String>();
        idList_checkCust = new ArrayList<String>();
        idList_checkCustID = new ArrayList<String>();
        idList_hpdj = new ArrayList<String>();
        idList_hpdl = new ArrayList<String>();
        idList_hpdjID = new ArrayList<String>();
        idList_hpdlID = new ArrayList<String>();
        TextView head_load_xg = (TextView) findViewById(R.id.all_head);

        head_load_xg.setText("货品加载");
        //下拉按钮获取信息
        query_sup = (ImageButton) findViewById(R.id.ib_load_sup);
        query_compdep = (ImageButton) findViewById(R.id.ib_load_compdep);
        employee = (ImageButton) findViewById(R.id.ib_load_employee);
        prdNo = (ImageButton) findViewById(R.id.ib_load_prdNo);
        prdIndx = (ImageButton) findViewById(R.id.ib_load_prdIndx);
        prdWh = (ImageButton) findViewById(R.id.ib_load_prdWh);
        prdMrk = (EditText) findViewById(R.id.et_load_prdMrk);
        prdName = (EditText) findViewById(R.id.et_load_prdName);
        prdName_ENG = (EditText) findViewById(R.id.et_load_prdName_ENG);
        prdLevre = (ImageButton) findViewById(R.id.ib_load_prdLevel);
        prdKND = (ImageButton) findViewById(R.id.ib_load_prdKND);
        showStop = (ImageButton) findViewById(R.id.ib_load_stop);
        tv_qurey_sup = (TextView) findViewById(R.id.et_load_sup);
        tv_query_compdep = (TextView) findViewById(R.id.et_load_compdep);
        tv_employee = (TextView) findViewById(R.id.et_load_employee);
        tv_prdNo = (TextView) findViewById(R.id.et_load_prdNo);
        tv_prdIndx = (TextView) findViewById(R.id.et_load_prdIndx);
        tv_prdWh = (TextView) findViewById(R.id.et_load_prdWh);
        tv_prdLevre = (TextView) findViewById(R.id.et_load_prdLevel);
        tv_prdKND = (TextView) findViewById(R.id.et_load_prdKND);
        tv_showStop = (TextView) findViewById(R.id.et_load_stop);
        this.mHead = ((RelativeLayout) findViewById(R.id.price_head_set));
        this.mHead.setFocusable(true);
        this.mHead.setClickable(true);
        this.mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        this.mListView1_set = (ListView) findViewById(R.id.lv_salePut_header_set);
        this.mListView1_set
                .setOnTouchListener(new ListViewAndHeadViewTouchLinstener());

        //点击
        query_sup.setOnClickListener(this);
        query_compdep.setOnClickListener(this);
        employee.setOnClickListener(this);
        prdNo.setOnClickListener(this);
        prdIndx.setOnClickListener(this);
        prdWh.setOnClickListener(this);
        prdLevre.setOnClickListener(this);
        prdKND.setOnClickListener(this);
        showStop.setOnClickListener(this);
        findViewById(R.id.imageButton1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                listClear();
            }


        });
        //加载
        findViewById(R.id.btn_put).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "价格修订-加载", Toast.LENGTH_LONG).show();
                load_ok_set();
            }
        });
        //确认
        findViewById(R.id.btn_addProject).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //判断添加的数据是否存在
                if (mListView1_set.getCount() > 0) {
                    ls_set = new ArrayList<>();

                    for (int i = 0; i < mListView1_set.getCount(); i++) {
                        prdt_set = (PriceLoadInfo.Prdt) mListView1_set.getAdapter().getItem(i);
                        Log.e("LiNing", "添加数据====prdt_set====" + prdt_set);
                        prdt_add.add(prdt_set);
                        String prd_no_set = prdt_set.getPRD_NO();
                        ls_set.add(prd_no_set);
                        Log.e("LiNing", "添加数据====ls====" + ls_set);
                    }
                    Log.e("LiNing", "新增数据====prdt_add====" + prdt_add);
                    String more_list = new Gson().toJson(prdt_add);
//                    sp.edit().putString("MORElIST",more_list).commit();
                    Intent localIntent = getIntent();
                    localIntent.putExtra("MORElIST", more_list);
                    setResult(1, localIntent);
                    finish();
                } else {
                    Toast.makeText(context, "请加载数据", Toast.LENGTH_LONG).show();
                }


            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //ERP厂商获取
            case R.id.ib_load_sup:
                //获取账套
                if (!db.equals("")) {

                    Intent intent12 = new Intent(context,
                            CondicionInfoActivity.class);
                    intent12.putExtra("flag", "12");
                    intent12.putExtra("queryID", db);
                    startActivityForResult(intent12, 15);
                } else {
                    Toast.makeText(context, "请选择单账套", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(context, "erp厂商", Toast.LENGTH_LONG).show();
                break;
            //核算部门（ERP）
            case R.id.ib_load_compdep:
                if (!db.equals("")) {

                    Intent intent10 = new Intent(context,
                            CondicionInfoActivity.class);
                    intent10.putExtra("flag", "10");
                    intent10.putExtra("queryID", db);
                    startActivityForResult(intent10, 16);
                } else {
                    Toast.makeText(context, "请选择单账套", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(context, "核算部门（ERP）", Toast.LENGTH_LONG).show();
                break;
            //销售人员
            case R.id.ib_load_employee:
                if (!db.equals("")) {
                    Intent intent17 = new Intent(context,
                            CondicionInfoActivity.class);
                    intent17.putExtra("flag", "17");
                    intent17.putExtra("queryID", db);
                    startActivityForResult(intent17, 17);
                } else {
                    Toast.makeText(context, "请选择单账套", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(context, "销售人员", Toast.LENGTH_LONG).show();
                break;
            //品号
            case R.id.ib_load_prdNo:
                if (!db.equals("")) {
                    Intent intent18 = new Intent(context,
                            CondicionInfoActivity.class);
                    intent18.putExtra("flag", "18");
                    intent18.putExtra("queryID", db);
                    startActivityForResult(intent18, 18);
                } else {
                    Toast.makeText(context, "请选择单账套", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(context, "品号", Toast.LENGTH_LONG).show();
                break;
            //货品中类
            case R.id.ib_load_prdIndx:
                if (!db.equals("")) {
                    Intent intent19 = new Intent(context,
                            CondicionInfoActivity.class);
                    intent19.putExtra("flag", "19");
                    intent19.putExtra("queryID", db);
                    startActivityForResult(intent19, 19);
                } else {
                    Toast.makeText(context, "请选择单账套", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(context, "货品中类", Toast.LENGTH_LONG).show();
                break;
            //货品库区
            case R.id.ib_load_prdWh:
                if (!db.equals("")) {
                    Intent intent20 = new Intent(context,
                            CondicionInfoActivity.class);
                    intent20.putExtra("flag", "20");
                    intent20.putExtra("queryID", db);
                    startActivityForResult(intent20, 20);
                } else {
                    Toast.makeText(context, "请选择单账套", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(context, "货品库区", Toast.LENGTH_LONG).show();
                break;
            //货品等级（单选）
            case R.id.ib_load_prdLevel:
//                Toast.makeText(context, "货品等级", Toast.LENGTH_LONG).show();
//                checked = 6;
//                showPopupMenu(prdLevre);

                Intent intent30 = new Intent(context,
                        LocalInfosActivity.class);
                intent30.putExtra("flag", "1");
                startActivityForResult(intent30, 30);
                break;
            //货品大类（单选）
            case R.id.ib_load_prdKND:
//                checked = 7;
//                showPopupMenu(prdKND);
                Intent intent31 = new Intent(context,
                        LocalInfosActivity.class);
                intent31.putExtra("flag", "2");
                startActivityForResult(intent31, 31);
                break;
            //是否停用
            case R.id.ib_load_stop:
                checked = 5;
                showPopupMenu(showStop);
                break;
        }

    }

    class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {
        ListViewAndHeadViewTouchLinstener() {
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ((HorizontalScrollView) AddMoreActivity.this.mHead
                    .findViewById(R.id.horizontalScrollView1))
                    .onTouchEvent(event);
            return false;
        }

    }

    private void listClear() {
        idList_prdnoID.clear();
        idList_prdnoName.clear();
        idList_prdIndexID.clear();
        idList_prdIndexName.clear();
        idList_prdWhID.clear();
        idList_prdWhName.clear();
        idList_employeeName.clear();
        idList_employeeID.clear();
        idList_querySup.clear();
        idList_querySupID.clear();
        idList_compDep.clear();
        idList_compDepID.clear();
    }

    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu布局

        if (checked == 5) {

            popupMenu.getMenuInflater().inflate(R.menu.five, popupMenu.getMenu());
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.check1
                        || menuItem.getItemId() == R.id.check2
                        || menuItem.getItemId() == R.id.check3) {
                    menuItem.setChecked(!menuItem.isChecked());
                    user_check.setText(menuItem.getTitle());
                    return true;
                } else if (menuItem.getItemId() == R.id.check1_num
                        || menuItem.getItemId() == R.id.check2_num
                        || menuItem.getItemId() == R.id.check3_num
                        || menuItem.getItemId() == R.id.check4_num
                        ) {
                    menuItem.setChecked(!menuItem.isChecked());
                    operate_check.setText(menuItem.getTitle());
                    return true;
                } else if (menuItem.getItemId() == R.id.check1_five
                        || menuItem.getItemId() == R.id.check2_five
                        ) {
                    menuItem.setChecked(!menuItem.isChecked());
                    tv_showStop.setText(menuItem.getTitle());
                    return true;
                }
//                else if (menuItem.getItemId() == R.id.check1_six
//                        || menuItem.getItemId() == R.id.check2_six
//                        || menuItem.getItemId() == R.id.check3_six
//                        || menuItem.getItemId() == R.id.check4_six
//                        || menuItem.getItemId() == R.id.check5_six
//                        || menuItem.getItemId() == R.id.check6_six
//                        || menuItem.getItemId() == R.id.check7_six
//                        ) {
//                    menuItem.setChecked(!menuItem.isChecked());
//                    if (!idList_hpdj.contains(menuItem.getTitle())) {
//                        idList_hpdj.add(menuItem.getTitle().toString());
//                        if (!idList_hpdjID.contains(menuItem.getTitle().charAt(0))) {
//                            idList_hpdjID.add("" + menuItem.getTitle().charAt(0));
//                        }
//                    }
//                    String hpdj_str = "";
//                    for (String zt : idList_hpdj) {
//                        hpdj_str += zt + ",";
//                    }
//                    String sub_level = hpdj_str.substring(0, hpdj_str.length() - 1);
//                    Log.e("LiNing", "------新增的数据" + sub_level);
//                    if (idList_hpdj != null && idList_hpdj.size() > 0) {
//                        tv_prdKND.setText(sub_level);
//                        tv_prdKND.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                TextView view = new TextView(context);
//                                view.setMovementMethod(ScrollingMovementMethod.getInstance());
//                                view.setText(tv_prdKND.getText().toString());
//                                alertDialog = new AlertDialog.Builder(context).create();
////                                alertDialog.setTitle("品牌信息");
//                                alertDialog.setCancelable(true);
//                                alertDialog.setView(view);
//                                alertDialog.show();
//                            }
//                        });
//                    }
//
//                    String hpdjid_str = "";
//                    for (String zt : idList_hpdjID) {
//                        hpdjid_str += zt + ",";
//                    }
//
//                    hpdj_id = hpdjid_str.substring(0, hpdjid_str.length() - 1);
//                    Log.e("LiNing", "------新增的数据id" + hpdj_id);
////                    tv_prdKND.setText(menuItem.getTitle());
//                    Log.e("LiNing", "提交的大类id====" + tv_prdKND.getText().charAt(0));
//                    return true;
//                } else if (menuItem.getItemId() == R.id.check1_seven
//                        || menuItem.getItemId() == R.id.check2_seven
//                        || menuItem.getItemId() == R.id.check3_seven
//                        || menuItem.getItemId() == R.id.check4_seven
//                        || menuItem.getItemId() == R.id.check5_seven
//                        || menuItem.getItemId() == R.id.check6_seven
//                        || menuItem.getItemId() == R.id.check7_seven
//                        ) {
//                    menuItem.setChecked(!menuItem.isChecked());
//
//                    if (!idList_hpdl.contains(menuItem.getTitle())) {
//                        idList_hpdl.add(menuItem.getTitle().toString());
//                        if (!idList_hpdlID.contains(menuItem.getTitle().charAt(0))) {
//                            idList_hpdlID.add("" + menuItem.getTitle().charAt(0));
//                        }
//                    }
//                    String hpdl_str = "";
//                    for (String zt : idList_hpdl) {
//                        hpdl_str += zt + ",";
//                    }
//                    String sub_level = hpdl_str.substring(0, hpdl_str.length() - 1);
//                    Log.e("LiNing", "------新增的数据" + sub_level);
//                    if (idList_hpdl != null && idList_hpdl.size() > 0) {
//                        tv_prdLevre.setText(sub_level);
//                        tv_prdLevre.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                TextView view = new TextView(context);
//                                view.setMovementMethod(ScrollingMovementMethod.getInstance());
//                                view.setText(tv_prdLevre.getText().toString());
//                                alertDialog = new AlertDialog.Builder(context).create();
////                                alertDialog.setTitle("品牌信息");
//                                alertDialog.setCancelable(true);
//                                alertDialog.setView(view);
//                                alertDialog.show();
//                            }
//                        });
//                    }
//
//                    String hpdlid_str = "";
//                    for (String zt : idList_hpdlID) {
//                        hpdlid_str += zt + ",";
//                    }
//
//                    hpdl_id = hpdlid_str.substring(0, hpdlid_str.length() - 1);
//                    Log.e("LiNing", "------新增的数据id" + hpdl_id);
//
//
//                    return true;
//                }

                return false;
            }
        });
        popupMenu.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {


            case 15:
                if (resultCode == 1) {

                    //记录选择的数据
                    String str1 = data.getStringExtra("data_return");
                    if (str1 != null) {
                        String[] db_spls = str1.split(",");

                        Log.e("LiNing", "=====" + str1.split(","));
                        for (int i = 0; i < db_spls.length; i++) {
                            String A = db_spls[i];
                            Log.e("LiNing", "===A==" + A);
                            if (!idList_querySup.contains(A)) {
                                idList_querySup.add(A);
                            }
                        }
                    }
                    Log.e("LiNing", "新增的数据" + idList_querySup);
                    String prdname_str = "";
                    for (String zt : idList_querySup) {
                        prdname_str += zt + ",";
                    }
                    String sub_level_sup = prdname_str.substring(0, prdname_str.length() - 1);
                    Log.e("LiNing", "------新增的数据" + sub_level_sup);
                    if (idList_querySup != null && idList_querySup.size() > 0) {
                        tv_qurey_sup.setText(sub_level_sup);
                        tv_qurey_sup.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextView view = new TextView(context);
                                view.setMovementMethod(ScrollingMovementMethod.getInstance());
                                view.setText(tv_qurey_sup.getText().toString());
                                alertDialog = new AlertDialog.Builder(context).create();
                                alertDialog.setCancelable(true);
                                alertDialog.setView(view);
                                alertDialog.show();
                            }
                        });
                    }
                    //记录提交的数据
                    String id_querySup_jz = data.getStringExtra("data_return_ids");
//                    idList_prdnoID.add(id_prdNo_xs);
                    if (id_querySup_jz != null) {
                        String[] db_spls = id_querySup_jz.split(",");
                        Log.e("LiNing", "=====" + id_querySup_jz.split(","));
                        for (int i = 0; i < db_spls.length; i++) {
                            String A = db_spls[i];
                            Log.e("LiNing", "===A==" + A);
                            if (!idList_querySupID.contains(A)) {
                                idList_querySupID.add(A);
                            }
                        }
                    }
                    Log.e("LiNing", "新增的数据" + idList_querySupID);
                    String prdid_str = "";
                    for (String zt : idList_querySupID) {
                        prdid_str += zt + ",";
                    }
                    id_querySup = prdid_str.substring(0, prdid_str.length() - 1);
                    Log.e("LiNing", "------新增的数据id" + id_querySup);
                }
                break;
            case 16:
                if (resultCode == 1) {
//                    String str1 = data.getStringExtra("data_return");
//                    id_hs = data.getStringExtra("data_return_ids");
//                    tv_query_compdep.setText(str1);
                    //记录选择的数据
                    String str1 = data.getStringExtra("data_return");
                    if (str1 != null) {
                        String[] db_spls = str1.split(",");

                        Log.e("LiNing", "=====" + str1.split(","));
                        for (int i = 0; i < db_spls.length; i++) {
                            String A = db_spls[i];
                            Log.e("LiNing", "===A==" + A);
                            if (!idList_compDep.contains(A)) {
                                idList_compDep.add(A);
                            }
                        }
                    }
                    Log.e("LiNing", "新增的数据" + idList_compDep);
                    String prdname_str = "";
                    for (String zt : idList_compDep) {
                        prdname_str += zt + ",";
                    }
                    String sub_level = prdname_str.substring(0, prdname_str.length() - 1);
                    Log.e("LiNing", "------新增的数据" + sub_level);
                    if (idList_compDep != null && idList_compDep.size() > 0) {
                        tv_query_compdep.setText(sub_level);
                        tv_query_compdep.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextView view = new TextView(context);
                                view.setMovementMethod(ScrollingMovementMethod.getInstance());
                                view.setText(tv_query_compdep.getText().toString());
                                alertDialog = new AlertDialog.Builder(context).create();
//                                alertDialog.setTitle("品牌信息");
                                alertDialog.setCancelable(true);
                                alertDialog.setView(view);
                                alertDialog.show();
                            }
                        });
                    }
                    //记录提交的数据
                    String id_prdNo_xs = data.getStringExtra("data_return_ids");
//                    idList_prdnoID.add(id_prdNo_xs);
                    if (id_prdNo_xs != null) {
                        String[] db_spls = id_prdNo_xs.split(",");
                        Log.e("LiNing", "=====" + id_prdNo_xs.split(","));
                        for (int i = 0; i < db_spls.length; i++) {
                            String A = db_spls[i];
                            Log.e("LiNing", "===A==" + A);
                            if (!idList_compDepID.contains(A)) {
                                idList_compDepID.add(A);
                            }
                        }
                    }
                    Log.e("LiNing", "新增的数据" + idList_compDepID);
                    String prdid_str = "";
                    for (String zt : idList_compDepID) {
                        prdid_str += zt + ",";
                    }
                    id_hs = prdid_str.substring(0, prdid_str.length() - 1);
                    Log.e("LiNing", "------新增的数据id" + id_hs);
                }
                break;
            case 17:
                if (resultCode == 1) {
                    //记录选择的数据
                    String str1 = data.getStringExtra("data_return");
                    if (str1 != null) {
                        String[] db_spls = str1.split(",");

                        Log.e("LiNing", "=====" + str1.split(","));
                        for (int i = 0; i < db_spls.length; i++) {
                            String A = db_spls[i];
                            Log.e("LiNing", "===A==" + A);
                            if (!idList_employeeName.contains(A)) {
                                idList_employeeName.add(A);
                            }
                        }
                    }
                    Log.e("LiNing", "新增的数据" + idList_employeeName);
                    String prdname_str = "";
                    for (String zt : idList_employeeName) {
                        prdname_str += zt + ",";
                    }
                    String sub_level = prdname_str.substring(0, prdname_str.length() - 1);
                    Log.e("LiNing", "------新增的数据" + sub_level);
                    if (idList_employeeName != null && idList_employeeName.size() > 0) {
                        tv_employee.setText(sub_level);
                        tv_employee.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextView view = new TextView(context);
                                view.setMovementMethod(ScrollingMovementMethod.getInstance());
                                view.setText(tv_employee.getText().toString());
                                alertDialog = new AlertDialog.Builder(context).create();
//                                alertDialog.setTitle("品牌信息");
                                alertDialog.setCancelable(true);
                                alertDialog.setView(view);
                                alertDialog.show();
                            }
                        });
                    }
                    //记录提交的数据
                    String id_prdNo_xs = data.getStringExtra("data_return_ids");
//                    idList_prdnoID.add(id_prdNo_xs);
                    if (id_prdNo_xs != null) {
                        String[] db_spls = id_prdNo_xs.split(",");
                        Log.e("LiNing", "=====" + id_prdNo_xs.split(","));
                        for (int i = 0; i < db_spls.length; i++) {
                            String A = db_spls[i];
                            Log.e("LiNing", "===A==" + A);
                            if (!idList_employeeID.contains(A)) {
                                idList_employeeID.add(A);
                            }
                        }
                    }
                    Log.e("LiNing", "新增的数据" + idList_employeeID);
                    String prdid_str = "";
                    for (String zt : idList_employeeID) {
                        prdid_str += zt + ",";
                    }
                    id_employee = prdid_str.substring(0, prdid_str.length() - 1);
                    Log.e("LiNing", "------新增的数据id" + id_employee);
                }
                break;
            case 18:
                if (resultCode == 1) {
                    //记录选择的数据
                    String str1 = data.getStringExtra("data_return");
                    if (str1 != null) {
                        String[] db_spls = str1.split(",");

                        Log.e("LiNing", "=====" + str1.split(","));
                        for (int i = 0; i < db_spls.length; i++) {
                            String A = db_spls[i];
                            Log.e("LiNing", "===A==" + A);
                            if (!idList_prdnoName.contains(A)) {
                                idList_prdnoName.add(A);
                            }
                        }
                    }
                    Log.e("LiNing", "新增的数据" + idList_prdnoName);
                    String prdname_str = "";
                    for (String zt : idList_prdnoName) {
                        prdname_str += zt + ",";
                    }
                    String sub_level = prdname_str.substring(0, prdname_str.length() - 1);
                    Log.e("LiNing", "------新增的数据" + sub_level);
                    if (idList_prdnoName != null && idList_prdnoName.size() > 0) {
                        tv_prdNo.setText(sub_level);
                        tv_prdNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextView view = new TextView(context);
                                view.setMovementMethod(ScrollingMovementMethod.getInstance());
                                view.setText(tv_prdNo.getText().toString());
                                alertDialog = new AlertDialog.Builder(context).create();
//                                alertDialog.setTitle("品牌信息");
                                alertDialog.setCancelable(true);
                                alertDialog.setView(view);
                                alertDialog.show();
                            }
                        });
                    }
                    //记录提交的数据
                    String id_prdNo_xs = data.getStringExtra("data_return_ids");
//                    idList_prdnoID.add(id_prdNo_xs);
                    if (id_prdNo_xs != null) {
                        String[] db_spls = id_prdNo_xs.split(",");
                        Log.e("LiNing", "=====" + id_prdNo_xs.split(","));
                        for (int i = 0; i < db_spls.length; i++) {
                            String A = db_spls[i];
                            Log.e("LiNing", "===A==" + A);
                            if (!idList_prdnoID.contains(A)) {
                                idList_prdnoID.add(A);
                            }
                        }
                    }
                    Log.e("LiNing", "新增的数据" + idList_prdnoID);
                    String prdid_str = "";
                    for (String zt : idList_prdnoID) {
                        prdid_str += zt + ",";
                    }
                    id_prdNo = prdid_str.substring(0, prdid_str.length() - 1);
                    Log.e("LiNing", "------新增的数据id" + id_prdNo);
                }
                break;
            case 19:
                if (resultCode == 1) {
                    //记录选择的数据
                    String str1 = data.getStringExtra("data_return");
                    if (str1 != null) {
                        String[] db_spls = str1.split(",");

                        Log.e("LiNing", "=====" + str1.split(","));
                        for (int i = 0; i < db_spls.length; i++) {
                            String A = db_spls[i];
                            Log.e("LiNing", "===A==" + A);
                            if (!idList_prdIndexName.contains(A)) {
                                idList_prdIndexName.add(A);
                            }
                        }
                    }
                    Log.e("LiNing", "新增的数据" + idList_prdIndexName);
                    String prdname_str = "";
                    for (String zt : idList_prdIndexName) {
                        prdname_str += zt + ",";
                    }
                    String sub_level = prdname_str.substring(0, prdname_str.length() - 1);
                    Log.e("LiNing", "------新增的数据" + sub_level);
                    if (idList_prdIndexName != null && idList_prdIndexName.size() > 0) {
                        tv_prdIndx.setText(sub_level);
                        tv_prdIndx.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextView view = new TextView(context);
                                view.setMovementMethod(ScrollingMovementMethod.getInstance());
                                view.setText(tv_prdIndx.getText().toString());
                                alertDialog = new AlertDialog.Builder(context).create();
//                                alertDialog.setTitle("品牌信息");
                                alertDialog.setCancelable(true);
                                alertDialog.setView(view);
                                alertDialog.show();
                            }
                        });
                    }
                    //记录提交的数据
                    String id_prdNo_xs = data.getStringExtra("data_return_ids");
//                    idList_prdnoID.add(id_prdNo_xs);
                    if (id_prdNo_xs != null) {
                        String[] db_spls = id_prdNo_xs.split(",");
                        Log.e("LiNing", "=====" + id_prdNo_xs.split(","));
                        for (int i = 0; i < db_spls.length; i++) {
                            String A = db_spls[i];
                            Log.e("LiNing", "===A==" + A);
                            if (!idList_prdIndexID.contains(A)) {
                                idList_prdIndexID.add(A);
                            }
                        }
                    }
                    Log.e("LiNing", "新增的数据" + idList_prdIndexID);
                    String prdid_str = "";
                    for (String zt : idList_prdIndexID) {
                        prdid_str += zt + ",";
                    }
                    id_prdIndex = prdid_str.substring(0, prdid_str.length() - 1);
                    Log.e("LiNing", "------新增的数据id" + id_prdIndex);
                }
                break;
            case 20:
                if (resultCode == 1) {
                    //记录选择的数据
                    String str1 = data.getStringExtra("data_return");
                    if (str1 != null) {
                        String[] db_spls = str1.split(",");

                        Log.e("LiNing", "=====" + str1.split(","));
                        for (int i = 0; i < db_spls.length; i++) {
                            String A = db_spls[i];
                            Log.e("LiNing", "===A==" + A);
                            if (!idList_prdWhName.contains(A)) {
                                idList_prdWhName.add(A);
                            }
                        }
                    }
                    Log.e("LiNing", "新增的数据" + idList_prdWhName);
                    String prdname_str = "";
                    for (String zt : idList_prdWhName) {
                        prdname_str += zt + ",";
                    }
                    String sub_level = prdname_str.substring(0, prdname_str.length() - 1);
                    Log.e("LiNing", "------新增的数据" + sub_level);
                    if (idList_prdWhName != null && idList_prdWhName.size() > 0) {
                        tv_prdWh.setText(sub_level);
                        tv_prdWh.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextView view = new TextView(context);
                                view.setMovementMethod(ScrollingMovementMethod.getInstance());
                                view.setText(tv_prdWh.getText().toString());
                                alertDialog = new AlertDialog.Builder(context).create();
//                                alertDialog.setTitle("品牌信息");
                                alertDialog.setCancelable(true);
                                alertDialog.setView(view);
                                alertDialog.show();
                            }
                        });
                    }
                    //记录提交的数据
                    String id_prdNo_xs = data.getStringExtra("data_return_ids");
//                    idList_prdnoID.add(id_prdNo_xs);
                    if (id_prdNo_xs != null) {
                        String[] db_spls = id_prdNo_xs.split(",");
                        Log.e("LiNing", "=====" + id_prdNo_xs.split(","));
                        for (int i = 0; i < db_spls.length; i++) {
                            String A = db_spls[i];
                            Log.e("LiNing", "===A==" + A);
                            if (!idList_prdWhID.contains(A)) {
                                idList_prdWhID.add(A);
                            }
                        }
                    }
                    Log.e("LiNing", "新增的数据" + idList_prdWhID);
                    String prdid_str = "";
                    for (String zt : idList_prdWhID) {
                        prdid_str += zt + ",";
                    }
                    id_prdWh = prdid_str.substring(0, prdid_str.length() - 1);
                    Log.e("LiNing", "------新增的数据id" + id_prdWh);
                }
                break;
            case 30:
                if (resultCode == 1) {
                    //记录选择的数据
                    String str1 = data.getStringExtra("data_return");
                    if (str1 != null) {
                        String[] db_spls = str1.split(",");

                        Log.e("LiNing", "=====" + str1.split(","));
                        for (int i = 0; i < db_spls.length; i++) {
                            String A = db_spls[i];
                            Log.e("LiNing", "===A==" + A);
                            if (!idList_hpdl.contains(A)) {
                                idList_hpdl.add(A);
                            }
                        }
                    }
                    Log.e("LiNing", "新增的数据" + idList_hpdl);
                    String prdname_str = "";
                    for (String zt : idList_hpdl) {
                        prdname_str += zt + ",";
                    }
                    String sub_level = prdname_str.substring(0, prdname_str.length() - 1);
                    Log.e("LiNing", "------新增的数据" + sub_level);
                    if (idList_hpdl != null && idList_hpdl.size() > 0) {
                        tv_prdLevre.setText(sub_level);
                        tv_prdLevre.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextView view = new TextView(context);
                                view.setMovementMethod(ScrollingMovementMethod.getInstance());
                                view.setText(tv_prdLevre.getText().toString());
                                alertDialog = new AlertDialog.Builder(context).create();
//                                alertDialog.setTitle("品牌信息");
                                alertDialog.setCancelable(true);
                                alertDialog.setView(view);
                                alertDialog.show();
                            }
                        });
                    }
                    //记录提交的数据
                    String id_prdNo_xs = data.getStringExtra("data_return_ids");
//                    idList_prdnoID.add(id_prdNo_xs);
                    if (id_prdNo_xs != null) {
                        String[] db_spls = id_prdNo_xs.split(",");
                        Log.e("LiNing", "=====" + id_prdNo_xs.split(","));
                        for (int i = 0; i < db_spls.length; i++) {
                            String A = db_spls[i];
                            Log.e("LiNing", "===A==" + A);
                            if (!idList_hpdlID.contains(A)) {
                                idList_hpdlID.add(A);
                            }
                        }
                    }
                    Log.e("LiNing", "新增的数据" + idList_prdWhID);
                    String prdid_str = "";
                    for (String zt : idList_hpdlID) {
                        prdid_str += zt + ",";
                    }
                    hpdl_id = prdid_str.substring(0, prdid_str.length() - 1);
                    Log.e("LiNing", "------新增的数据id" + hpdl_id);
                }
                break;
            case 31:
                if (resultCode == 1) {
                    //记录选择的数据
                    String str1 = data.getStringExtra("data_return");
                    if (str1 != null) {
                        String[] db_spls = str1.split(",");

                        Log.e("LiNing", "=====" + str1.split(","));
                        for (int i = 0; i < db_spls.length; i++) {
                            String A = db_spls[i];
                            Log.e("LiNing", "===A==" + A);
                            if (!idList_hpdj.contains(A)) {
                                idList_hpdj.add(A);
                            }
                        }
                    }
                    Log.e("LiNing", "新增的数据" + idList_hpdj);
                    String prdname_str = "";
                    for (String zt : idList_hpdj) {
                        prdname_str += zt + ",";
                    }
                    String sub_level = prdname_str.substring(0, prdname_str.length() - 1);
                    Log.e("LiNing", "------新增的数据" + sub_level);
                    if (idList_hpdj != null && idList_hpdj.size() > 0) {
                        tv_prdKND.setText(sub_level);
                        tv_prdKND.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextView view = new TextView(context);
                                view.setMovementMethod(ScrollingMovementMethod.getInstance());
                                view.setText(tv_prdKND.getText().toString());
                                alertDialog = new AlertDialog.Builder(context).create();
//                                alertDialog.setTitle("品牌信息");
                                alertDialog.setCancelable(true);
                                alertDialog.setView(view);
                                alertDialog.show();
                            }
                        });
                    }
                    //记录提交的数据
                    String id_prdNo_xs = data.getStringExtra("data_return_ids");
//                    idList_prdnoID.add(id_prdNo_xs);
                    if (id_prdNo_xs != null) {
                        String[] db_spls = id_prdNo_xs.split(",");
                        Log.e("LiNing", "=====" + id_prdNo_xs.split(","));
                        for (int i = 0; i < db_spls.length; i++) {
                            String A = db_spls[i];
                            Log.e("LiNing", "===A==" + A);
                            if (!idList_hpdjID.contains(A)) {
                                idList_hpdjID.add(A);
                            }
                        }
                    }
                    Log.e("LiNing", "新增的数据" + idList_hpdjID);
                    String prdid_str = "";
                    for (String zt : idList_hpdjID) {
                        prdid_str += zt + ",";
                    }
                    hpdj_id = prdid_str.substring(0, prdid_str.length() - 1);
                    Log.e("LiNing", "------新增的数据id" + hpdj_id);
                }
                break;

        }

    }

    private void load_ok_set() {
        Toast.makeText(context, "数据加载中，请等待...", Toast.LENGTH_LONG).show();
        String sup_comit = tv_qurey_sup.getText().toString();
        String compdep_comit = tv_query_compdep.getText().toString();
        String employee_comit = tv_employee.getText().toString();
        String prdNo_comit = tv_prdNo.getText().toString();
        String prdIndx_comit = tv_prdIndx.getText().toString();
        String prdWh_comit = tv_prdWh.getText().toString();
        String prdLevre_comit = tv_prdLevre.getText().toString();
        String prdKND_comit = tv_prdKND.getText().toString();
        String showStop_comit = tv_showStop.getText().toString();
        String prdMrk_comit = prdMrk.getText().toString();
        String prdName_comit = prdName.getText().toString();
        String prdName_ENG_comit = prdName_ENG.getText().toString();
        if (sup_comit.equals("") || sup_comit == null) {
            id_querySup = "all";
//            id_querySup = "";
        }
        if (compdep_comit.equals("") || compdep_comit == null) {
            id_hs = "all";
        }
        if (employee_comit.equals("") || employee_comit == null) {
            id_employee = "all";
        }
        if (prdNo_comit.equals("") || prdNo_comit == null) {
            id_prdNo = "all";
        }
        if (prdIndx_comit.equals("") || prdIndx_comit == null) {
            id_prdIndex = "all";
        }
        if (prdWh_comit.equals("") || prdWh_comit == null) {
            id_prdWh = "all";
        }
        if (prdLevre_comit.equals("") || prdLevre_comit == null || prdLevre_comit.equals("null")) {
            prdLevre_comit_jz = "all";
        } else {
//            prdLevre_comit_jz = "" + prdLevre_comit.charAt(0);
            prdLevre_comit_jz = hpdl_id;
        }
        if (prdKND_comit.equals("") || prdKND_comit == null || prdKND_comit.equals("null")) {
            prdKND_comit_jz = "all";
        } else {
//            prdKND_comit_jz = "" + prdKND_comit.charAt(0);
            prdKND_comit_jz = hpdj_id;
        }
        if (showStop_comit.equals("") || showStop_comit == null) {
            showStop_comit = "F";
        }
        if (prdMrk_comit.equals("") || prdMrk_comit == null) {
            prdMrk_comit = "all";
        }
        if (prdName_comit.equals("") || prdName_comit == null) {
            prdName_comit = "all";
        }
        if (prdName_ENG_comit.equals("") || prdName_ENG_comit == null) {
            prdName_ENG_comit = "all";
        }
        Log.e("LiNing", "-----str-----" + prdLevre_comit_jz + prdKND_comit_jz +
                showStop_comit + prdMrk_comit + prdName_comit + prdName_ENG_comit);
        OkHttpClient client = new OkHttpClient();
        FormBody localFormBody = new FormBody.Builder()
                .add("db_Id", db)
                .add("query_Sup", id_querySup)
                .add("query_CompDep", id_hs)
                .add("employee", id_employee)
                .add("prdNO", id_prdNo)
                .add("prdIndx", id_prdIndex)
                .add("prdWh", id_prdWh)
                .add("prdLevel", prdLevre_comit_jz)
                .add("prdKND", prdKND_comit_jz)
                .add("showStop", showStop_comit)
                .add("prdMrk", prdMrk_comit)
                .add("prdName", prdName_comit)
                .add("prdName_ENG", prdName_ENG_comit)
                .build();
        Request localRequest = new Request.Builder()
                .addHeader("cookie", session).url(url_load_price)
                .post(localFormBody)
                .build();
        Log.e("LiNing", "-----str-----" + url_load_price);
        Log.e("LiNing", "-----str-----" + db + id_querySup + id_hs
                + id_employee + id_prdNo + id_prdIndex + id_prdWh + prdLevre_comit + prdKND_comit +
                showStop_comit + prdMrk_comit + prdName_comit + prdName_ENG_comit);
        client.newCall(localRequest).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e("LiNing", "-----str---sj--" + str);
                Gson gson = new GsonBuilder().setDateFormat(
                        "yyyy-MM-dd'T'HH:mm:ss").create();//特殊格式
                final PriceLoadInfo cInfoDB = gson.fromJson(str,
                        PriceLoadInfo.class);
                if (cInfoDB != null) {
                    AddMoreActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            prdt = cInfoDB.getPrdt();
                            Log.e("LiNing", "prdt-----str-----" + AddMoreActivity.this.prdt);
                            moreAdapter = new AddMoreAdapter(R.layout.price_fuction_item1, prdt, context);
                            mListView1_set.setAdapter(moreAdapter);
                            //此处做判断
//                            listClear();

                        }
                    });
                }

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    public class AddMoreAdapter extends BaseAdapter {
        int id_row_layout, clik;
        LayoutInflater mInflater;
        private List<PriceLoadInfo.Prdt> infos;
        boolean ischeck = false;
        private OnItemClickListenerPrice priceListener;
        private int index;
        private int add_one;

        public AddMoreAdapter(int price_fuction_item1, List<PriceLoadInfo.Prdt> prdt, Context context) {
            this.id_row_layout = price_fuction_item1;
            this.mInflater = LayoutInflater.from(context);
            this.infos = prdt;
        }

        @Override
        public boolean isEnabled(int position) {
            return ischeck;
        }

        public void check() {
            ischeck = false;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            Log.e("LiNing", "----新数据more-----" + infos.size());
            return infos.size();
        }

        @Override
        public Object getItem(int position) {
            return infos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                synchronized (AddMoreActivity.this) {
                    convertView = mInflater.inflate(id_row_layout, null);
                    holder = new ViewHolder();
                    MyHScrollView scrollView1 = (MyHScrollView) convertView
                            .findViewById(R.id.horizontalScrollView1);

                    holder.scrollView = scrollView1;
                    holder.checkbox = (TextView) convertView
                            .findViewById(R.id.price_showChecx);
                    holder.checkbox.setVisibility(View.VISIBLE);
                    holder.price_name = (TextView) convertView.findViewById(R.id.price_brand_name);
                    holder.price_prdNo = (TextView) convertView.findViewById(R.id.price_brand_id);
                    holder.up_sal = (EditText) convertView.findViewById(R.id.price_dw_cb);
                    holder.upr = (EditText) convertView.findViewById(R.id.price_ty_dj);
                    holder.up_min = (EditText) convertView.findViewById(R.id.price_min);
                    holder.price_danj = (EditText) convertView.findViewById(R.id.price_danj);
                    holder.price_zk = (EditText) convertView.findViewById(R.id.price_zk);
                    holder.price_zxqk = (EditText) convertView.findViewById(R.id.price_zx_qk);
                    holder.price_bzxx = (EditText) convertView.findViewById(R.id.price_bz_xx);

//                    holder.price_zxqk.setText(zxqk.getText().toString());
//                    holder.price_bzxx.setText(zcsm.getText().toString());
//                    if(more==1){
//                         headSrcrollView = (MyHScrollView) mHead_more
//                                .findViewById(R.id.horizontalScrollView1);
//                    }else{
//
//                    }
                    MyHScrollView headSrcrollView = (MyHScrollView) mHead
                            .findViewById(R.id.horizontalScrollView1);
                    headSrcrollView
                            .AddOnScrollChangedListener(new OnScrollChangedListenerImp(
                                    scrollView1));
                    convertView.setTag(holder);
                }

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final PriceLoadInfo.Prdt prdt_item = infos.get(position);
            Log.e("LiNing", "添加数据=====新=====" + prdt_item);
            int id_add = position + 1;
            int id = position;
            holder.checkbox.setText("" + id);
            prdt_item.setXH("" + id);
            holder.price_name.setText(prdt_item.getNAME());
            holder.price_prdNo.setText(prdt_item.getPRD_NO());
            holder.up_sal.setText("" + prdt_item.getUP_SAL());
            holder.upr.setText("" + prdt_item.getUPR());
            holder.up_min.setText("" + prdt_item.getUP_MIN());
            prdt_item.setZC_DJ("0.00");
            prdt_item.setZC_ZK("100");
            prdt_item.setZC_ZXQK("N");
            prdt_item.setZC_BZXX("null");

//            holder.price_zxqk.setText(prdt_item.getZC_ZXQK());
//            holder.price_bzxx.setText(prdt_item.getZC_BZXX());
            //编辑EditextSet
            EditextSet_add(holder, prdt_item);
            //获取焦点(手机测试===关于软键盘弹出，editext获取焦点的问题)

            Log.e("LiNing", "---------" + clik);

            if (clik == 2) {

                holder.price_name.setFocusable(false);
                holder.price_prdNo.setFocusable(false);
                holder.up_sal.setFocusable(false);
                holder.upr.setFocusable(false);
                holder.up_min.setFocusable(false);
                holder.price_danj.setFocusable(false);
                holder.price_zk.setFocusable(false);
                holder.price_zxqk.setFocusable(false);
                holder.price_bzxx.setFocusable(false);
            }
            holder.checkbox.setVisibility(View.VISIBLE);
            holder.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prdt.remove(position);
                    moreAdapter.notifyDataSetChanged();
                }
            });

            return convertView;
        }

        private void EditextSet_add(ViewHolder holder, final PriceLoadInfo.Prdt prdt_item) {
            if (holder.up_sal.getTag() instanceof TextWatcher) {
                holder.up_sal.removeTextChangedListener((TextWatcher) holder.up_sal.getTag());
            }
            if (holder.upr.getTag() instanceof TextWatcher) {
                holder.upr.removeTextChangedListener((TextWatcher) holder.upr.getTag());
            }
            if (holder.up_min.getTag() instanceof TextWatcher) {
                holder.up_min.removeTextChangedListener((TextWatcher) holder.up_min.getTag());
            }
            if (holder.price_danj.getTag() instanceof TextWatcher) {
                holder.price_danj.removeTextChangedListener((TextWatcher) holder.price_danj.getTag());
            }
            if (holder.price_zk.getTag() instanceof TextWatcher) {
                holder.price_zk.removeTextChangedListener((TextWatcher) holder.price_zk.getTag());
            }
            if (holder.price_zxqk.getTag() instanceof TextWatcher) {
                holder.price_zxqk.removeTextChangedListener((TextWatcher) holder.price_zxqk.getTag());
            }
            if (holder.price_bzxx.getTag() instanceof TextWatcher) {
                holder.price_bzxx.removeTextChangedListener((TextWatcher) holder.price_bzxx.getTag());
            }


            TextWatcher watcher_dwcb = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) {
                        prdt_item.setUP_SAL(Double.parseDouble("0"));
                    } else {
                        prdt_item.setUP_SAL(Double.parseDouble(s.toString()));
                    }
                }
            };
            TextWatcher watcher_tydj = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) {
                        prdt_item.setUPR(Double.parseDouble("0"));
                    } else {
                        prdt_item.setUPR(Double.parseDouble(s.toString()));
                    }
                }
            };
            TextWatcher watcher_zdsj = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) {
                        prdt_item.setUP_MIN(Double.parseDouble("0"));
                    } else {
                        prdt_item.setUP_MIN(Double.parseDouble(s.toString()));
                        Log.e("LiNing", "-----最新----" + prdt_item.getUP_SAL());
                    }
                }
            };
            TextWatcher watcher_zcdj = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) {
                        prdt_item.setZC_DJ("0.00");
                    } else {
                        prdt_item.setZC_DJ(s.toString());
                    }
                }
            };
            TextWatcher watcher_zk = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) {
                        prdt_item.setZC_ZK("100");
                    } else {
                        prdt_item.setZC_ZK(s.toString());
                    }
                }
            };
            TextWatcher watcher_zcqk = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) {
                        prdt_item.setZC_ZXQK("N");
                    } else {
                        prdt_item.setZC_ZXQK(s.toString());
                    }
                }
            };
            TextWatcher watcher_bzxx = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) {
                        prdt_item.setZC_BZXX("null");
                    } else {
                        prdt_item.setZC_BZXX(s.toString());
                    }
                }
            };
            holder.up_sal.addTextChangedListener(watcher_dwcb);
            holder.up_sal.setTag(watcher_dwcb);
            holder.upr.addTextChangedListener(watcher_tydj);
            holder.upr.setTag(watcher_tydj);
            holder.up_min.addTextChangedListener(watcher_zdsj);
            holder.up_min.setTag(watcher_zdsj);
            holder.price_danj.addTextChangedListener(watcher_zcdj);
            holder.price_danj.setTag(watcher_zcdj);
            holder.price_zk.addTextChangedListener(watcher_zk);
            holder.price_zk.setTag(watcher_zk);
            holder.price_zxqk.addTextChangedListener(watcher_zcqk);
            holder.price_zxqk.setTag(watcher_zcqk);
            holder.price_bzxx.addTextChangedListener(watcher_bzxx);
            holder.price_bzxx.setTag(watcher_bzxx);
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

        class ViewHolder {
            private RelativeLayout layouts;
            HorizontalScrollView scrollView;
            private EditText price_id;
            private TextView price_name;
            private TextView price_prdNo;
            private EditText up_sal;
            private EditText upr;
            private EditText up_min;
            private EditText price_danj;
            private EditText price_zk;
            private EditText price_zxqk;
            private EditText price_bzxx;
            private TextView checkbox;

        }

    }
}