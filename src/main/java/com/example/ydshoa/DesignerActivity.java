package com.example.ydshoa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.LeftOrRight.MyHScrollView;
import com.example.bean.DesignAllInfos;
import com.example.bean.JsonRootBean;
import com.example.bean.PriceNumID;
import com.example.bean.URLS;
import com.example.bean.UserInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DesignerActivity extends Activity implements View.OnClickListener {
    private Context context;
    private SharedPreferences sp;
    private String session, db_zt, str_name, user_Id, user_Name, user_yh, user_dep;
    private TextView head, zt, sfty, sfqy, zdyh, viplb, bbyw, bbqd, vipcard;
    private EditText sfzh, vipname, zym, lxdh, lxdh2, ssdw, yhkh, khyh, dbr, shyh;
    private Button add, del, set, query, sh, bc;
    private ImageButton vipbtn, ztbtn, yw, ib_bbqd;
    private ListView lv_vip;
    //获取临时vip卡号
    String date_dd;
    LinearLayout touch;
    //接口
    String vip_get = URLS.design_query;
    String url = URLS.userInfo_url;
    String vip_add = URLS.design_add;
    String vip_del = URLS.design_del;
    String vip_set = URLS.design_updata;
    String url_dh_price = URLS.price_num_ls;
    //新增数据
    String des_db, des_vipcard, des_sfzh, des_vipname, des_zym, des_lxdh, des_lxdh2, des_viplb, des_ssdw, des_bbqd,des_bbqd_id, des_bbyw, des_yhkh, des_khyh, des_dbr, des_shyh, des_zdyh, des_sfty, des_sfty_cx;
    String vip_id_hd, id_bbyw;
    int do_design, do_design_set, do_design_sh;
    RelativeLayout mHead;

    //判断新增数据是否存在
    ArrayList<String> card_list;
    FormBody body;
    //数据修改
    String xh_set, vipnolb_set, action_set, dd_set, cont_set, user_set;
    List<DesignAllInfos.AlteList> alteList;
    //回调信息
    private ArrayList<HashMap<String, Object>> dList;
    private SimpleAdapter sAdapter;
    private HashMap<String, Object> item;
    boolean ischeck_bbqd=false;//用于判断bbqd_id是否点击
    boolean ischeck_vip=false;//用于判断type_id是否点击
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_designer);
        context = DesignerActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        db_zt = sp.getString("DB_MR", "");
        user_Id = sp.getString("USER_ID", "");
        user_yh = sp.getString("MR_YH", "");
        user_Name = sp.getString("USER_NAME", "");
        user_dep = sp.getString("USER_DEPBM", "");
        sp.edit().putString("VIP_ID_SJS","").commit();
        sp.edit().putString("BBQD_SJS","").commit();
        initView();//初始化
        dList = new ArrayList<>();
        sAdapter = new SimpleAdapter(context, dList, R.layout.design_dairy_item, new String[]{"序号", "VIP卡号",
                "执行动作", "变更日期", "变更内容", "变更用户"}, new int[]{
                R.id.design_showChecx, R.id.price_design_no,
                R.id.price_design_action, R.id.price_design_altedd,
                R.id.price_design_altecont, R.id.price_design_alteuser}) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = View.inflate(context,
                            R.layout.design_dairy_item, null);
                }
                return super.getView(position, convertView, parent);
            }
        };
        lv_vip.setAdapter(sAdapter);
    }

    private void initView() {
        head = (TextView) findViewById(R.id.all_head);
        head.setText("设计师管理");
        this.mHead = ((RelativeLayout) findViewById(R.id.design_head));
        this.mHead.setFocusable(true);
        this.mHead.setClickable(true);
        this.mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        lv_vip = (ListView) findViewById(R.id.lv_design_header);
        lv_vip.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        zt = (TextView) findViewById(R.id.et_design_zt);
        vipcard = (TextView) findViewById(R.id.tv_vipcard);
        vipcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTicket();
            }
        });
        touch = (LinearLayout) findViewById(R.id.ll_touch_vip);
        touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getTicket();//获取临时单号
            }
        });
        zt.setText(db_zt);
        sfqy = (TextView) findViewById(R.id.tv_design_isno);
        sfzh = (EditText) findViewById(R.id.et_design_usercard);
        vipname = (EditText) findViewById(R.id.et_design_username);
        zym = (EditText) findViewById(R.id.et_design_username_old);
        lxdh = (EditText) findViewById(R.id.et_design_phone);
        lxdh2 = (EditText) findViewById(R.id.et_design_phone_two);
        viplb = (TextView) findViewById(R.id.et_design_vip);
        ssdw = (EditText) findViewById(R.id.et_design_comp);
        bbqd = (TextView) findViewById(R.id.et_design_dep);
//        bbqd.setText(user_dep);//点击新增
        bbyw = (TextView) findViewById(R.id.et_design_salno);
        yhkh = (EditText) findViewById(R.id.et_design_bankno);
        khyh = (EditText) findViewById(R.id.et_design_bankdeposit);
        dbr = (EditText) findViewById(R.id.et_design_guatname);
        zdyh = (TextView) findViewById(R.id.et_design_userno);
        zdyh.setText(user_Name);
//        zdyh.setText(user_yh);
        Log.e("LiNing", "制单用户id---" + user_Id);
        shyh = (EditText) findViewById(R.id.et_design_userchk);
        ztbtn = (ImageButton) findViewById(R.id.ib_design_account);
        vipbtn = (ImageButton) findViewById(R.id.ib_design_vip);
        yw = (ImageButton) findViewById(R.id.ib_design_yw);
        ib_bbqd = (ImageButton) findViewById(R.id.ib_design_bbqd);
        add = (Button) findViewById(R.id.btn_design_add);
        del = (Button) findViewById(R.id.btn_design_del);
        set = (Button) findViewById(R.id.btn_design_reset);
        query = (Button) findViewById(R.id.btn_design_query);
        sh = (Button) findViewById(R.id.btn_design_sh);
        bc = (Button) findViewById(R.id.btn_design_save);

        add.setOnClickListener(this);
        del.setOnClickListener(this);
        set.setOnClickListener(this);
        query.setOnClickListener(this);
        sh.setOnClickListener(this);
        bc.setOnClickListener(this);
        ztbtn.setOnClickListener(this);
        vipbtn.setOnClickListener(this);
        yw.setOnClickListener(this);
        ib_bbqd.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //VIP账套//-----1
            case R.id.ib_design_account:
                Intent intent = new Intent(context, CondicionInfoActivity.class);
                intent.putExtra("flag", "1");
                startActivityForResult(intent, 1);
                break;
            //VIP类别//-----10
            case R.id.ib_design_vip:
                ischeck_vip = true;
                if (zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {

                    Intent intent_vip = new Intent(context, VipClassActivity.class);
                    intent_vip.putExtra("ZT_VIP", zt.getText().toString());
//                    startActivity(intent_vip);
                    startActivityForResult(intent_vip, 10);
                }
                break;
            //报备渠道（终端用户）
            case R.id.ib_design_bbqd:
//                showPopupMenu(ib_bbqd);
                ischeck_bbqd = true;
                if (zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent13 = new Intent(context,
                            CondicionInfoActivity.class);
                    intent13.putExtra("flag", "13");
                    intent13.putExtra("queryID", zt.getText().toString());
                    startActivityForResult(intent13, 13);
                }
                break;
            //报备业务//-----17
            case R.id.ib_design_yw:


                if (zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent17 = new Intent(context,
                            CondicionInfoActivity.class);
                    intent17.putExtra("flag", "17");
                    intent17.putExtra("queryID", zt.getText().toString());
                    startActivityForResult(intent17, 17);
                }
                break;
            //新增
            case R.id.btn_design_add:

                //获取所有数据
                getInfos_all();
                //清空数据
                clearInfo();
                if (lv_vip.getCount() > 0) {
                    dList.clear();
//                    lv_vip.setAdapter(null);
                    sAdapter.notifyDataSetChanged();
                }
                do_design = 1;
                dbr.setEnabled(false);
                shyh.setEnabled(false);
                sfqy.setText("未审核");
                break;
            //删除
            case R.id.btn_design_del:
                //判断listview列表，避免乱点
                if(lv_vip.getCount()>0){
                    //获取删除的数据字段
                    String zt_hd = zt.getText().toString();
                    String vipno_hd = vipcard.getText().toString();
                    String card_hd = sfzh.getText().toString();
                    String userno_hd = zdyh.getText().toString();
                    if (!zt_hd.equals("null") && !userno_hd.equals("null")) {
                        OkHttpClient client = new OkHttpClient();
                        FormBody body = new FormBody.Builder()
                                .add("db_Id", zt_hd)
                                .add("user_No", userno_hd)
                                .add("vip_NO", vipno_hd)
                                .add("card_Num", card_hd)
                                .build();
                        Log.e("LiNing", "删除结果====" + zt_hd + "---" + userno_hd + "---" + vipno_hd + "---" + card_hd);
                        client.newCall(
                                new Request.Builder().addHeader("cookie", session).url(vip_del)
                                        .post(body).build()).enqueue(new Callback() {
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String str = response.body().string();
                                Log.e("LiNing", "删除结果====" + str);
                                final JsonRootBean localJsonRootBean = (JsonRootBean) new Gson()
                                        .fromJson(str, JsonRootBean.class);
                                if (localJsonRootBean != null) {
                                    DesignerActivity.this.runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            boolean rlo = localJsonRootBean.getRLO();
                                            if (rlo == true) {
                                                Toast.makeText(DesignerActivity.this,
                                                        "design删除成功", Toast.LENGTH_SHORT).show();
                                                clearInfo();
                                                dList.clear();
                                            } else if (rlo == false) {
                                                Toast.makeText(DesignerActivity.this,
                                                        "design删除失败", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onFailure(Call call, IOException e) {

                            }
                        });

                    } else {
                        Toast.makeText(context, "账套和制单用户不能为空", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(context,"数据不完整",Toast.LENGTH_LONG).show();
                }


                break;
            //修改
            case R.id.btn_design_reset:
                //判断listview列表，避免乱点
                if(lv_vip.getCount()>0){
                    //数据库和单号保持不变
                    Log.e("LiNing", "lv_vip大小===" + lv_vip.getCount());
                    vipcard.setEnabled(false);
                    ztbtn.setEnabled(false);
                    dbr.setEnabled(false);
                    shyh.setEnabled(false);
                    do_design_set = 1;  Log.e("LiNing", "-----" + vipcard.getText().toString());

                }else{
                    Toast.makeText(context,"数据不完整",Toast.LENGTH_LONG).show();
                }

                break;
            //查询//-----11
            case R.id.btn_design_query:
                dList.clear();
                if (zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {
                    user_Name = sp.getString("USER_NAME", "");
                    user_dep = sp.getString("USER_DEPBM", "");
                    Intent intent_vip = new Intent(context, QueryDesigActivity.class);
                    intent_vip.putExtra("ZT_VIP", zt.getText().toString());
                    startActivityForResult(intent_vip, 11);
                }
                break;
            //审核用户
            case R.id.btn_design_sh:
                //判断listview列表，避免乱点
                if(lv_vip.getCount()>0){
                    //审核用户和担保人可编辑
                    dbr.setEnabled(true);
                    shyh.setEnabled(false);
                    shyh.setText(user_Name);
                    do_design_sh = 1;
                }else{
                    Toast.makeText(context,"数据不完整",Toast.LENGTH_LONG).show();
                }

                break;
            //保存
            case R.id.btn_design_save:
                if (do_design_set == 1) {
                    commitInfos();//主表信息
                    getinfos_queryToset();//修改
                }
                if (do_design_sh == 1) {
                    commitInfos();//主表信息(包含担保人和审核人)
                    //判断上传数据
                    getinfos_queryToset();//审核
                }
                if (do_design == 1) {
                    commitInfos();
                    Log.e("LiNing", "id_type结果====" + card_list);
                    if (card_list.contains(des_sfzh)) {
                        Toast.makeText(context, "该设计师已存在", Toast.LENGTH_LONG).show();
                    } else if (des_vipcard.equals("")) {
                        Toast.makeText(context, "请获取vip单号", Toast.LENGTH_LONG).show();
                    } else if (des_sfzh.length() >= 18 && des_sfzh.length() < 20) {
                        Toast.makeText(context, "请添加正确身份证号", Toast.LENGTH_LONG).show();
                    } else {
                        OkHttpClient client = new OkHttpClient();
                        FormBody body = new FormBody.Builder()
                                .add("db_Id", des_db)
                                .add("vip_NO", des_vipcard)
                                .add("card_Num", des_sfzh)
                                .add("vip_Name", des_vipname)
                                .add("for_Name", des_zym)
                                .add("con_Tel", des_lxdh)
                                .add("con_Tel2", des_lxdh2)
                                .add("type_Id", vip_id_hd)
                                .add("comp", des_ssdw)
                                .add("SalesTerminal_No", des_bbqd_id)
                                .add("sal_No", des_bbyw)
                                .add("bank_No", des_yhkh)
                                .add("bank_Deposit", des_khyh)
                                .add("user_No", user_yh)
                                .add("Stat", des_sfty)
//                        .add("user_CHK", "N")
                                .build();
                        Log.e("LiNing", "添加结果====" + des_db + "---" + des_vipcard + "---" + des_sfzh + "---" + vip_id_hd + "---" + des_lxdh + "---"
                                + des_viplb + "---" + des_ssdw + "---" + des_bbqd_id + "---" + des_bbyw + "---" + des_yhkh + "---" + des_khyh + "---" + user_Id + "---" + des_sfty
                                + "---" + des_zym + "---" + des_lxdh2 + "---" + des_dbr);
                        client.newCall(
                                new Request.Builder().addHeader("cookie", session).url(vip_add)
                                        .post(body).build()).enqueue(new Callback() {

                            @Override
                            public void onResponse(Call call, Response response)
                                    throws IOException {
                                String str = response.body().string();
                                Log.e("LiNing", "添加结果====" + str);
                                final JsonRootBean localJsonRootBean = (JsonRootBean) new Gson()
                                        .fromJson(str, JsonRootBean.class);
                                if (localJsonRootBean != null) {
                                    DesignerActivity.this.runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            boolean rlo = localJsonRootBean.getRLO();
                                            if (rlo == true) {
                                                Toast.makeText(DesignerActivity.this,
                                                        "新增成功", Toast.LENGTH_SHORT).show();
                                            } else if (rlo == false) {
                                                Toast.makeText(DesignerActivity.this,
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

                break;


        }
    }

    //获取数据进行修改
    private void getinfos_queryToset() {
        OkHttpClient client = new OkHttpClient();
        Log.e("LiNing","-----"+ischeck_vip+"-----"+ischeck_bbqd);
        if(ischeck_vip==false){
            if(do_design==1){
                String vipid_sjs = sp.getString("VIP_ID_SJS","");
                vip_id_hd=vipid_sjs;
                Log.e("LiNing","---xz--"+vip_id_hd+"-----"+des_bbqd_id);
            }else{
                vip_id_hd=viplb.getText().toString();
                Log.e("LiNing","---cx--"+vip_id_hd+"-----"+des_bbqd_id);
            }

        }
        if(ischeck_bbqd==false){
            String bbqd_sjs = sp.getString("BBQD_SJS","");
            des_bbqd_id=bbqd_sjs;
        }
        Log.e("LiNing","--z---"+vip_id_hd+"-----"+des_bbqd_id);
        if (do_design_set == 1) {
            body = new FormBody.Builder()
                    .add("db_Id", des_db)
                    .add("vip_NO", des_vipcard)
                    .add("card_Num", des_sfzh)
                    .add("vip_Name", des_vipname)
                    .add("for_Name", des_zym)
                    .add("con_Tel", des_lxdh)
                    .add("con_Tel2", des_lxdh2)
                    .add("type_Id", vip_id_hd)
                    .add("comp", des_ssdw)
                    .add("SalesTerminal_No", des_bbqd_id)
                    .add("sal_No", des_bbyw)
                    .add("bank_No", des_yhkh)
                    .add("bank_Deposit", des_khyh)
                    .add("user_No", user_yh)
                    .add("Stat", des_sfty)
                    .build();
        }
        if (do_design_sh == 1) {
            body = new FormBody.Builder()
                    .add("db_Id", des_db)
                    .add("vip_NO", des_vipcard)
                    .add("card_Num", des_sfzh)
                    .add("vip_Name", des_vipname)
                    .add("for_Name", des_zym)
                    .add("con_Tel", des_lxdh)
                    .add("con_Tel2", des_lxdh2)
                    .add("type_Id", vip_id_hd)
                    .add("comp", des_ssdw)
                    .add("SalesTerminal_No", des_bbqd_id)
                    .add("sal_No", des_bbyw)
                    .add("bank_No", des_yhkh)
                    .add("bank_Deposit", des_khyh)
                    .add("guat_name", des_dbr)
                    .add("user_No", user_Id)
                    .add("user_CHK", user_Id)
                    .add("Stat", des_sfty)
                    .build();
        }

        Log.e("LiNing", "提交结果====" + des_db + "---" + des_vipcard + "---" + des_sfzh + "---" + vip_id_hd + "---" + des_lxdh + "---"
                + des_viplb + "---" + des_ssdw + "---" + des_bbqd_id + "---" + des_bbyw + "---" + des_yhkh + "---" + des_khyh + "---" + user_Id + "---" + des_sfty
                + "---" + des_zym + "---" + des_lxdh2 + "---" + "---" + des_dbr + "---" + des_shyh);
        client.newCall(
                new Request.Builder().addHeader("cookie", session).url(vip_set)
                        .post(body).build()).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                String str = response.body().string();
                Log.e("LiNing", "修改结果====" + str);
                final JsonRootBean localJsonRootBean = (JsonRootBean) new Gson()
                        .fromJson(str, JsonRootBean.class);
                if (localJsonRootBean != null) {
                    DesignerActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            boolean rlo = localJsonRootBean.getRLO();
                            if (do_design_sh == 1) {
                                if (rlo == true) {
                                    Toast.makeText(DesignerActivity.this,
                                            "审核成功", Toast.LENGTH_SHORT).show();
                                } else if (rlo == false) {
                                    Toast.makeText(DesignerActivity.this,
                                            "审核失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                            if (do_design_set == 1) {

                                if (rlo == true) {
                                    Toast.makeText(DesignerActivity.this,
                                            "修改成功", Toast.LENGTH_SHORT).show();
                                } else if (rlo == false) {
                                    Toast.makeText(DesignerActivity.this,
                                            "修改失败", Toast.LENGTH_SHORT).show();
                                }
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

    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.bbqd_info, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.check1
                        || menuItem.getItemId() == R.id.check2
                        || menuItem.getItemId() == R.id.check3
                        || menuItem.getItemId() == R.id.check4
                        || menuItem.getItemId() == R.id.check5
                        || menuItem.getItemId() == R.id.check6
                        || menuItem.getItemId() == R.id.check7
                        || menuItem.getItemId() == R.id.check8
                        ) {
                    menuItem.setChecked(!menuItem.isChecked());
                    bbqd.setText(menuItem.getTitle());
                    String s = menuItem.getTitle().toString();
                    String substring = s.substring(0, 1);
                    des_bbqd_id = substring;
                    Log.e("LiNing", "----" + des_bbqd_id + "----" + substring);

                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void clearInfo() {
        zt.setText(db_zt);
        vipcard.setText("");
        sfzh.setText("");
        vipname.setText("");
        zym.setText("");
        lxdh.setText("");
        lxdh2.setText("");
        viplb.setText("");
        ssdw.setText("");
        bbqd.setText(user_dep);
        yhkh.setText("");
        khyh.setText("");
        dbr.setText("");
        zdyh.setText(user_Name);
        shyh.setText("");

    }

    private void getInfos_all() {

        if (!zt.getText().toString().equals("")) {
            OkHttpClient client_all = new OkHttpClient();
            FormBody body_all = new FormBody.Builder()
                    .add("db_Id", zt.getText().toString())
                    .build();
            Request request_all = new Request.Builder()
                    .addHeader("cookie", session).url(vip_get)
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
                    }
                }

                @Override
                public void onFailure(Call call, IOException e) {

                }
            });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    String str_id = data.getStringExtra("condition_db");
                    str_name = data.getStringExtra("condition_name");
                    zt.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + str_name);
                }

                break;
            case 10:
                if (resultCode == 1) {

                    vip_id_hd = data.getStringExtra("VIP_ID");
                    String vip_name_hd = data.getStringExtra("VIP_NAME");
                    viplb.setText(vip_name_hd);
                    sp.edit().putString("VIP_ID_SJS",vip_id_hd).commit();
                    Log.e("LiNing", "提交的id====" + vip_id_hd + vip_name_hd);
                }

                break;
            case 11:
                if (resultCode == 1) {
                    DesignAllInfos.VipList vipList_hd = (DesignAllInfos.VipList) data.getSerializableExtra("VIP_INFOS_ALL");
                    Log.e("LiNing", "提交的id====" + data.getSerializableExtra("VIP_INFOS_ALL"));
                    Log.e("LiNing", "提交的id====" + vipList_hd);
                    if (vipList_hd.getDb_Id() != null) {
                        zt.setText(vipList_hd.getDb_Id());
                    } else {
                        zt.setText("null");
                    }
                    if (vipList_hd.getVip_NO() != null) {
                        vipcard.setText(vipList_hd.getVip_NO());
                    } else {
                        vipcard.setText("null");
                    }
                    if (vipList_hd.getCard_Num() != null) {
                        String w_null = vipList_hd.getCard_Num().replaceAll("\\s+$", "");
                        sfzh.setText(w_null);
//                        sfzh.setText(vipList_hd.getCard_Num());
                    } else {
                        sfzh.setText("null");
                    }
                    if (vipList_hd.getVip_Name() != null) {
                        vipname.setText(vipList_hd.getVip_Name());
                    } else {
                        vipname.setText("null");
                    }
                    if (vipList_hd.getFor_Name() != null) {
                        zym.setText(vipList_hd.getFor_Name());
                    } else {
                        zym.setText("null");
                    }
//                    String contel_hd = vipList_hd.getCon_Tel().toString();
                    if (vipList_hd.getCon_Tel() != null) {
                        lxdh.setText(vipList_hd.getCon_Tel());
                    } else {
                        lxdh.setText("null");
                    }

//                    String contel2_hd = vipList_hd.getCon_Tel2().toString();
                    if (vipList_hd.getCon_Tel2() != null) {
                        lxdh2.setText(vipList_hd.getCon_Tel2());
                    } else {
                        lxdh2.setText("null");
                    }
                    if (vipList_hd.getType_Id() != null) {
                        viplb.setText(vipList_hd.getType_Id());
                    } else {
                        viplb.setText("null");
                    }
                    if (vipList_hd.getComp() != null) {
                        ssdw.setText(vipList_hd.getComp());
                    } else {
                        ssdw.setText("null");
                    }
                    if (vipList_hd.getSalesTerminal_No() != null) {
                        bbqd.setText(vipList_hd.getSalesTerminal_No());
                    } else {
                        bbqd.setText("null");
                    }
                    if (vipList_hd.getSal_No() != null) {
                        bbyw.setText(vipList_hd.getSal_No());
                    } else {
                        bbyw.setText("null");
                    }
                    if (vipList_hd.getBank_No() != null) {
                        yhkh.setText(vipList_hd.getBank_No());
                    } else {
                        yhkh.setText("null");
                    }
                    if (vipList_hd.getBank_Deposit() != null) {
                        khyh.setText(vipList_hd.getBank_Deposit());
                    } else {
                        khyh.setText("null");
                    }
                    if (vipList_hd.getGuat_name() != null) {
                        dbr.setText(vipList_hd.getGuat_name());
                    } else {
                        dbr.setText("null");
                    }
                    if (vipList_hd.getUser_No() != null) {
                        zdyh.setText(vipList_hd.getUser_No());
                    } else {
                        zdyh.setText("null");
                    }
                    if (vipList_hd.getUser_CHK() != null) {

                        shyh.setText(vipList_hd.getUser_CHK());
                    } else {
                        shyh.setText("null");
                    }
                    if (vipList_hd.getStat() != null) {
                        if (vipList_hd.getStat().equals("1")) {
                            des_sfty_cx = "启用";
                        }
                        if (vipList_hd.getStat().equals("2")) {
                            des_sfty_cx = "停用";
                        }
                        if (vipList_hd.getStat().equals("3")) {
                            des_sfty_cx = "未审核";
                        }
                        sfqy.setText(des_sfty_cx);
                    } else {
                        sfqy.setText("null");
                    }
                    //列表信息
                    if (vipList_hd.getAlteList() != null && vipList_hd.getAlteList().size() > 0) {

                        alteList = vipList_hd.getAlteList();
                        Log.e("LiNing", "----1----more--" + alteList);
                        if (alteList.size() == 1) {
                            item = new HashMap<String, Object>();
                            item.put("序号", alteList.get(0).getITM());
                            item.put("VIP卡号", alteList.get(0).getVip_No());
                            item.put("执行动作", alteList.get(0).getAction());
                            SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            try {
                                Date parse = sf1.parse(alteList.get(0).getAlte_DD().toString());
                                String format = new SimpleDateFormat("yyyy-MM-dd").format(parse);
                                Log.e("LiNing", "时间====xin=====" + format);
                                item.put("变更日期", format);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            item.put("变更内容", alteList.get(0).getAlte_Cont());
                            item.put("变更用户", alteList.get(0).getUser_no());
                            Log.e("LiNing", "----1------" + item);
                            dList.add(item);
                            sAdapter.notifyDataSetChanged();
                            Log.e("LiNing", "----1------" + dList);
                        } else if (alteList.size() > 1) {
                            for (int i = 0; i < alteList.size(); i++) {
                                item = new HashMap<String, Object>();
                                item.put("序号", alteList.get(i).getITM());
                                item.put("VIP卡号", alteList.get(i).getVip_No());
                                item.put("执行动作", alteList.get(i).getAction());
                                SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                try {
                                    Date parse = sf1.parse(alteList.get(i).getAlte_DD().toString());
                                    String format = new SimpleDateFormat("yyyy-MM-dd").format(parse);
                                    Log.e("LiNing", "时间====xin=====" + format);
                                    item.put("变更日期", format);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                item.put("变更内容", alteList.get(i).getAlte_Cont());
                                item.put("变更用户", alteList.get(i).getUser_no());
                                Log.e("LiNing", "----more------" + item);
                                dList.add(item);
                            }
                            sAdapter.notifyDataSetChanged();
                            Log.e("LiNing", "----more---list---" + dList);
                        } else {
                            item = new HashMap<String, Object>();
                            item.put("序号", "");
                            item.put("VIP卡号", "");
                            item.put("执行动作", "");
                            item.put("变更日期", "");
                            item.put("变更内容", "");
                            item.put("变更用户", "");
                            Log.e("LiNing", "----无------" + item);
                            dList.add(item);
                            sAdapter.notifyDataSetChanged();
                        }
                    }
                }

                break;
            case 17:
                if (resultCode == 1) {
                    String str1 = data.getStringExtra("data_return");
                    id_bbyw = data.getStringExtra("data_return_ids");
                    bbyw.setText(str1);
                    // sp.edit().putString("USERIDquerry", str1).commit();
                }
            case 13:
                if (resultCode == 1) {
                    String str1 = data.getStringExtra("data_return");
                    des_bbqd_id= data.getStringExtra("data_return_ids");
                    bbqd.setText(str1);
                    sp.edit().putString("BBQD_SJS",des_bbqd_id).commit();
                }
                break;
        }
    }

    public void allback(View v) {
        finish();
    }

    private void commitInfos() {
        des_db = zt.getText().toString();
        if (des_db.equals("")) {
            Toast.makeText(context, "请添加账套", Toast.LENGTH_LONG).show();
        }
        des_vipcard = vipcard.getText().toString();
        Log.e("LiNing", "-----" + des_vipcard);
        if (des_vipcard.equals("")) {
            Toast.makeText(context, "请添加VIP卡号", Toast.LENGTH_LONG).show();
        }
        des_sfzh = sfzh.getText().toString();
        Log.e("LiNing", "长度=====" + des_sfzh.length());
        if (des_sfzh.equals("")) {
            Toast.makeText(context, "请添加身份证号", Toast.LENGTH_LONG).show();
        }
//        if (des_sfzh.length() >= 18 && des_sfzh.length() < 20) {
//            Toast.makeText(context, "请添加正确身份证号", Toast.LENGTH_LONG).show();
//        }

        des_vipname = vipname.getText().toString();
        if (des_vipname.equals("")) {
            Toast.makeText(context, "请添加姓名", Toast.LENGTH_LONG).show();
        }
        des_zym = zym.getText().toString();
        des_lxdh = lxdh.getText().toString();
        if (des_lxdh.equals("")) {
            Toast.makeText(context, "请添加联系电话", Toast.LENGTH_LONG).show();
        }
        des_lxdh2 = lxdh2.getText().toString();
        des_viplb = viplb.getText().toString();
        if (des_viplb.equals("")) {
            Toast.makeText(context, "请添加vip类别", Toast.LENGTH_LONG).show();
            vip_id_hd = "0";
        }else{
            vip_id_hd=vip_id_hd;
        }
        des_ssdw = ssdw.getText().toString();
        if (des_ssdw.equals("")) {
            Toast.makeText(context, "请添加所属", Toast.LENGTH_LONG).show();
        }
        des_bbqd = bbqd.getText().toString();
        if (des_bbqd.equals("")) {
            Toast.makeText(context, "请添加报备渠道", Toast.LENGTH_LONG).show();
        }
        des_bbyw = bbyw.getText().toString();
        if (des_bbyw.equals("")) {
            Toast.makeText(context, "请添加报备业务", Toast.LENGTH_LONG).show();
            id_bbyw = "0";//默认报备业务
        }
        des_yhkh = yhkh.getText().toString();
        if (des_yhkh.equals("")) {
            Toast.makeText(context, "请添加银行卡号", Toast.LENGTH_LONG).show();
        }
        des_khyh = khyh.getText().toString();
        if (des_khyh.equals("")) {
            Toast.makeText(context, "请添加开户银行", Toast.LENGTH_LONG).show();
        }
        des_dbr = dbr.getText().toString();
        if (do_design != 1) {

            if (des_dbr.equals("")) {
                Toast.makeText(context, "请添加担保人", Toast.LENGTH_LONG).show();
            }
        }
        des_shyh = shyh.getText().toString();
        if (do_design != 1) {

            if (des_shyh.equals("")) {
                Toast.makeText(context, "审核用户为空", Toast.LENGTH_LONG).show();
            }
        }
//        des_zdyh = zdyh.getText().toString();
//        if(des_zdyh.equals("")){
//            Toast.makeText(context,"请添加制单用户",Toast.LENGTH_LONG).show();
//        }
        String sfty_sj = sfqy.getText().toString();
        if (sfty_sj.equals("启用")) {
            des_sfty = "1";
        }
        if (sfty_sj.equals("停用")) {
            des_sfty = "2";
        }
        if (sfty_sj.equals("未审核")) {
            des_sfty = "3";
        }
    }

    class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {
        ListViewAndHeadViewTouchLinstener() {
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ((HorizontalScrollView) DesignerActivity.this.mHead
                    .findViewById(R.id.horizontalScrollView1))
                    .onTouchEvent(event);
            return false;
        }

    }

    //临时单号
    private void getTicket() {
        String DB_LS = zt.getText().toString();
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
                    .add("bn_Type", "VP")
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
                            DesignerActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    String id_ls = bean_id.getBil_no();
                                    vipcard.setText(id_ls);
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
}
