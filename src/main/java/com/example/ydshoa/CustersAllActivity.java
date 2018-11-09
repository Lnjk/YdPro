package com.example.ydshoa;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.LeftOrRight.MyHScrollView;
import com.example.bean.Brand;
import com.example.bean.CstAddInfos;
import com.example.bean.CustAllObjectInfos;
import com.example.bean.CustesrAllInfos;
import com.example.bean.DepInfo;
import com.example.bean.DesignAllInfos;
import com.example.bean.FollInfos;
import com.example.bean.JsonRootBean;
import com.example.bean.PriceNumID;
import com.example.bean.URLS;
import com.example.fragment.GzFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
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

public class CustersAllActivity extends FragmentActivity implements View.OnClickListener {
    private Context context;
    private SharedPreferences sp;
    private String session, db_zt, user_Id, des_bbqd_id, des_xsgw_id, mr_yh, cus_no, vip_no_cust, qty_cust_zt, qty_cust_dabh, format;
    private Button cust_add, cust_set, cust_del, cust_query, cust_save, cust_out;
    private CheckBox ck_ws, ck_kt, ck_cf, ck_wsj, ck_yt, ck_dxs;
    private ImageButton cust_zt, cust_xb, cust_psxx, cust_xfcc, cust_ly, cust_lplx, cust_zxlb, cust_sjs, cust_xszd, cust_xsgw;
    private TextView head, cust_ed_xsgw, cust_ed_xszd, cust_ed_dabh, cust_tv_zt, cust_tv_xb, cust_tv_xfcc, cust_tv_khly, cust_tv_lplx, cust_tv_zxlb, cust_tv_sjs, cust_ed_kzsj, da_time;
    private EditText cust_ed_khxm, cust_ed_zcdh, cust_ed_khnl, cust_ed_khzy, cust_ed_zxfg, cust_ed_zxys, cust_ed_zxgs,
            cust_ed_zxjd, cust_ed_yxpp, cust_ed_bzxx, cust_tv_psxx, cust_tv_hxjg, cust_tv_clyy;
    //新增信息
    String cust_db_z, cust_datime_z, cust_dabh_z, cust_xm_z, cust_zcdh_z, cust_nl_z, cust_zy_z, cust_xb_z, cust_psxx_z, cust_xfcc_z, cust_ly_z, cust_lplx_z, cust_zxlb_z, cust_zxfg_z, cust_zxys_z, cust_zxgs_z, cust_sjs_z, cust_kzsj_z, cust_zxjd_z,
            cust_xszd_z, cust_xsgw_z, cust_yxpp_z, cust_bzxx_z, cust_hxjg_z, cust_clyy_z, str_id_itm;
    int checked;
    String date_dd, url_do, url_do_gz;

    LinearLayout touch;
    private String url_prdNo = URLS.prdNo_url;//通过id获取名称
    String url_dh_price = URLS.price_num_ls;
    String cust_add_z = URLS.cust_z_add;
    String cust_del_z = URLS.cust_z_del;
    String cust_set_z = URLS.cust_z_updata;
    String cust_query_z = URLS.cust_z_query;
    //信息跟踪
    String cust_gzadd_z = URLS.cust_gzxx_add;
    String cust_gzset_z = URLS.cust_gzxx_updata;
    //    CustesrAllInfos.CustList cust_callback;
    CustAllObjectInfos.CustList cust_callback;
    int check_do;
    boolean ischeck_bbqd = false;//用于判断bbqd_id是否点击
    boolean ischeck_vip = false;//用于判断type_id是否点击
    boolean ischeck_psxx = false;//用于判断psxx是否点击
    boolean ischeck_xsgw = false;//用于判断xsgw是否点击
    private List<String> list_clyy;
    //回调信息
    String vipid_hd, psxxid_hd, xsgw_z, idtoname, idtoname_gw;
    RadioGroup rg_gz;
    RadioButton gz_obj_id,bj_obj_id,xs_obj_id;
    //跟踪信息表格
    private TextView tv_bt_xxg;
    private Button bg_gzxx_set, bg_gzxx_del;
    private int gz_do;
    private ViewPager mViewPager;// 用来放置界面切换
    ImageButton btn_gz_rb, btn_bj_rb, btn_xs_rb, btn_hk_rb, btn_ps_rb, btn_sh_rb;
    // 初始化3个Fragment
    private GzFragment gz_Fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_custers_all);
        context = CustersAllActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        db_zt = sp.getString("DB_MR", "");
        user_Id = sp.getString("USER_ID", "");
        mr_yh = sp.getString("MR_YH", "");
        Log.e("LiNing", "----" + mr_yh);
        getNowTime();
        list_clyy = new ArrayList<String>();
        initView();
    }

    private void getNowTime() {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // 获取当前年份
        int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前日
        date_dd = mYear + "-" + mMonth + "-" + mDay;
    }

    //初始化
    private void initView() {
//        //viewpager切换
//        mViewPager = (ViewPager) findViewById(R.id.id_viewpage);
//        btn_gz_rb = (ImageButton) findViewById(R.id.rb_gzxx);
//        btn_bj_rb = (ImageButton) findViewById(R.id.rb_bjxx);
//        btn_xs_rb = (ImageButton) findViewById(R.id.rb_xsxx);
//        btn_hk_rb = (ImageButton) findViewById(R.id.rb_hkxx);
//        btn_ps_rb = (ImageButton) findViewById(R.id.rb_psxx_fg);
//        btn_sh_rb = (ImageButton) findViewById(R.id.rb_shxx);
//        initEvent();
//        initViewPage(0);
        //----------------------
        rg_gz= (RadioGroup) findViewById(R.id.rg_gzxx_obj);
        gz_obj_id= (RadioButton) findViewById(R.id.rb_gzxx);
        bj_obj_id= (RadioButton) findViewById(R.id.rb_bjxx);
        xs_obj_id= (RadioButton) findViewById(R.id.rb_xsxx);
        rg_gz.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(gz_obj_id.getId()==checkedId){
                    Toast.makeText(context, "显示跟踪数据", Toast.LENGTH_LONG).show();
                    findViewById(R.id.rl_bj_obj).setVisibility(View.GONE);
                    findViewById(R.id.rl_xs_obj).setVisibility(View.GONE);
                }
                if(bj_obj_id.getId()==checkedId){
                    findViewById(R.id.rl_bj_obj).setVisibility(View.VISIBLE);
                    findViewById(R.id.ll_xxobj).setVisibility(View.GONE);
                    findViewById(R.id.rl_xs_obj).setVisibility(View.GONE);
                }
                if(xs_obj_id.getId()==checkedId){
                    findViewById(R.id.ll_xxobj).setVisibility(View.GONE);
                    findViewById(R.id.rl_bj_obj).setVisibility(View.GONE);
                    findViewById(R.id.rl_xs_obj).setVisibility(View.VISIBLE);
                }
            }
        });
        checkedIndexList = new ArrayList<Integer>();
        idList = new ArrayList<String>();
        checkBoxList = new ArrayList<CheckBox>();
        head = (TextView) findViewById(R.id.all_head);
        head.setText("客户管理");
        da_time = (TextView) findViewById(R.id.tv_time_cust);
        da_time.setText(date_dd);
        da_time.setOnClickListener(new View.OnClickListener() {
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
                                da_time.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        //editext
        cust_ed_dabh = (TextView) findViewById(R.id.et_cust_all_dabh);
        cust_ed_dabh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTicket();
            }
        });
        touch = (LinearLayout) findViewById(R.id.ll_touch_da_all);
        touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getTicket();//获取临时档案编号
            }
        });
        cust_ed_khxm = (EditText) findViewById(R.id.et_cust_all_khxm);
        cust_ed_zcdh = (EditText) findViewById(R.id.et_cust_all_zcdh);
        cust_ed_khnl = (EditText) findViewById(R.id.et_cust_all_khnl);
        cust_ed_khzy = (EditText) findViewById(R.id.et_cust_all_khzy);
        cust_ed_zxfg = (EditText) findViewById(R.id.et_cust_all_zxfg);
        cust_ed_zxys = (EditText) findViewById(R.id.et_cust_all_zxys);
        cust_ed_zxgs = (EditText) findViewById(R.id.et_cust_all_zxgs);
        cust_ed_zxjd = (EditText) findViewById(R.id.et_cust_all_zxjd);
        cust_ed_xszd = (TextView) findViewById(R.id.et_cust_all_xszd);
        cust_ed_xsgw = (TextView) findViewById(R.id.et_cust_all_xsgw);
        cust_ed_yxpp = (EditText) findViewById(R.id.et_cust_all_yxpp);
        cust_ed_bzxx = (EditText) findViewById(R.id.et_cust_all_bzxx);
        cust_tv_psxx = (EditText) findViewById(R.id.et_cust_all_psxx);
        cust_tv_hxjg = (EditText) findViewById(R.id.et_cust_all_hxjg);
//        cust_tv_clyy= (EditText) findViewById(R.id.et_cust_all_psxx);
        //textview
        cust_tv_zt = (TextView) findViewById(R.id.et_cust_all_zt);
        cust_tv_zt.setText(db_zt);
        cust_tv_xb = (TextView) findViewById(R.id.et_cust_all_xb);
        cust_tv_xfcc = (TextView) findViewById(R.id.et_cust_all_xfcc);
        cust_tv_khly = (TextView) findViewById(R.id.et_cust_all_khly);
        cust_tv_lplx = (TextView) findViewById(R.id.et_cust_all_lplx);
        cust_tv_zxlb = (TextView) findViewById(R.id.et_cust_all_zxlb);
        cust_tv_sjs = (TextView) findViewById(R.id.tv_cust_all_sjs);
        cust_ed_kzsj = (TextView) findViewById(R.id.et_cust_all_kzsj);
        cust_ed_kzsj.setText(date_dd);
        cust_ed_kzsj.setOnClickListener(new View.OnClickListener() {
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
                                cust_ed_kzsj.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        //CheckBox(ck_ws,ck_kt,ck_cf,ck_wsj,ck_yt,ck_dxs;)
        ck_ws = (CheckBox) findViewById(R.id.cb_ws);
        ck_kt = (CheckBox) findViewById(R.id.cb_kt);
        ck_cf = (CheckBox) findViewById(R.id.cb_cf);
        ck_wsj = (CheckBox) findViewById(R.id.cb_wsj);
        ck_yt = (CheckBox) findViewById(R.id.cb_yt);
        ck_dxs = (CheckBox) findViewById(R.id.cb_dxs);
        //button
        cust_add = (Button) findViewById(R.id.btn_cust_all_add);
        cust_set = (Button) findViewById(R.id.btn_cust_all_reset);
        cust_del = (Button) findViewById(R.id.btn_cust_all_del);
        cust_query = (Button) findViewById(R.id.btn_cust_all_query);
        cust_save = (Button) findViewById(R.id.btn_cust_all_save);
        cust_out = (Button) findViewById(R.id.btn_cust_all_out);
        //imagebutton
        cust_zt = (ImageButton) findViewById(R.id.ib_cust_all_account);
        cust_xb = (ImageButton) findViewById(R.id.ib_cust_all_xb);
        cust_psxx = (ImageButton) findViewById(R.id.ib_cust_all_psxx);
        cust_xfcc = (ImageButton) findViewById(R.id.ib_cust_all_xfcc);
        cust_ly = (ImageButton) findViewById(R.id.ib_cust_all_khly);
        cust_lplx = (ImageButton) findViewById(R.id.ib_cust_all_lplx);
        cust_zxlb = (ImageButton) findViewById(R.id.ib_cust_all_zxlb);
        cust_sjs = (ImageButton) findViewById(R.id.ib_cust_all_sjs);
        cust_xszd = (ImageButton) findViewById(R.id.ib_cust_all_xszd);
        cust_xsgw = (ImageButton) findViewById(R.id.ib_cust_all_xsgw);
        cust_xsgw.setOnClickListener(this);//顾问
        cust_xszd.setOnClickListener(this);//终端
        cust_zt.setOnClickListener(this);//账套
        cust_xb.setOnClickListener(this);//性别
        cust_psxx.setOnClickListener(this);//配送信息
        cust_xfcc.setOnClickListener(this);//消费层次
        cust_ly.setOnClickListener(this);//客户来源
        cust_lplx.setOnClickListener(this);//楼盘类型
        cust_zxlb.setOnClickListener(this);//装修类别
        cust_sjs.setOnClickListener(this);//设计师
        cust_add.setOnClickListener(this);//新增
        cust_set.setOnClickListener(this);//修改
        cust_del.setOnClickListener(this);//删除
        cust_query.setOnClickListener(this);//速查
        cust_save.setOnClickListener(this);//保存
        cust_out.setOnClickListener(this);//关闭
    }

//    private void initViewPage(int i) {
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction transaction = fm.beginTransaction();// 创建一个事务
//        hideFragment(transaction);// 我们先把所有的Fragment隐藏了，然后下面再开始处理具体要显示的Fragment
//        switch (i) {
//            case 0:
//                if (gz_Fragment == null) {
//                    gz_Fragment = new GzFragment();
//                    transaction.add(R.id.id_content, gz_Fragment);// 将微信聊天界面的Fragment添加到Activity中
//                } else {
//                    transaction.show(gz_Fragment);
//                }
//                break;
//        }
//    }
//
//    private void hideFragment(FragmentTransaction transaction) {
//        if (gz_Fragment != null) {
//            transaction.hide(gz_Fragment);
//        }
//    }
//
//    private void initEvent() {
//        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                int currentItem = mViewPager.getCurrentItem();
//                switch (currentItem) {
//                    case 0:
//                        resetImg();
////                        btn_gz_rb.setImageResource(R.drawable.tab_bar_04_check);
//                        break;
//                }
//            }
//
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//    }
//
//    private void resetImg() {
////        btn_gz_rb.setImageResource(R.drawable.tab_bar_04_check);
//    }

    public void allback(View v) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_cust_all_sjs:
                ischeck_vip = true;
//                设计师
                Intent intent_vip = new Intent(context, QueryDesigActivity.class);
                intent_vip.putExtra("ZT_VIP", cust_tv_zt.getText().toString());
                intent_vip.putExtra("CUST_DO", "1");
                startActivityForResult(intent_vip, 2);
                break;
            case R.id.btn_cust_all_add:
                cust_psxx.setEnabled(true);
                check_do = 1;
//                cust_tv_psxx.setText("1");
                clearInfos();
                clearChecbox();
                break;
            case R.id.btn_cust_all_reset:
                if (cust_callback != null) {

                    if (!qty_cust_zt.equals("") && !qty_cust_dabh.equals("")) {
                        cust_psxx.setEnabled(true);
                        cust_ed_dabh.setEnabled(false);
                        touch.setEnabled(false);
                        check_do = 2;
                    } else {
                        Toast.makeText(context, "客户不能编辑", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "客户数据为空", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_cust_all_del:
                if (cust_callback != null) {
                    if (!qty_cust_zt.equals("") && !qty_cust_dabh.equals("")) {

                        new AlertDialog.Builder(context)
                                .setTitle("是否删除")
                                .setPositiveButton("是",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                delCust_qty();
                                            }
                                        }).setNegativeButton("否", null).show();
                    } else {
                        Toast.makeText(context, "客户不能删除", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "客户数据为空", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.btn_cust_all_query:
                //速查
                clearChecbox();
                Intent intent_cust = new Intent(context, QueryCustersActivity.class);
                intent_cust.putExtra("ZT_VIP", cust_tv_zt.getText().toString());
                startActivityForResult(intent_cust, 3);
                break;
            case R.id.btn_cust_all_save:
                getinfos();
                addCust();
                break;
            //(跟进信息)
            case R.id.btn_cust_all_out:
                if (cust_callback != null) {
                   /* if (!qty_cust_zt.equals("") && !qty_cust_dabh.equals("")) {
                        Intent intent_gz = new Intent(context, TraceAllInfosActivity.class);
                        intent_gz.putExtra("SSZT", qty_cust_zt);
                        intent_gz.putExtra("DABH", qty_cust_dabh);
                        startActivity(intent_gz);
                    } else {
                        Toast.makeText(context, "客户数据不完整", Toast.LENGTH_LONG).show();
                    }*/
                    gz_do = 1;
                    addorSetInfos_obj();
                } else {
                    Toast.makeText(context, "客户数据为空", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.ib_cust_all_account:
                Intent intent = new Intent(context, CondicionInfoActivity.class);
                intent.putExtra("flag", "1");
                startActivityForResult(intent, 1);
                break;
            case R.id.ib_cust_all_xszd:
                if (cust_tv_zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {

                    Intent intent13 = new Intent(context,
                            CondicionInfoActivity.class);
                    intent13.putExtra("flag", "13");
                    intent13.putExtra("queryID", cust_tv_zt.getText().toString());
                    startActivityForResult(intent13, 13);
                    ischeck_bbqd = true;//用于判断bbqd_id是否点击
                }
                break;
            case R.id.ib_cust_all_xsgw:
                if (cust_tv_zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {

                    Intent intent17 = new Intent(context,
                            CondicionInfoActivity.class);
                    intent17.putExtra("flag", "17");
                    intent17.putExtra("queryID", cust_tv_zt.getText().toString());
                    startActivityForResult(intent17, 17);
                    ischeck_bbqd = true;//用于判断bbqd_id是否点击
                }
                break;
            case R.id.ib_cust_all_psxx:
                if (check_do == 2) {
                    if (!qty_cust_zt.equals("") && !qty_cust_dabh.equals("")) {
                        ischeck_psxx = true;
                        Intent intent1 = new Intent(context, MoveAllInfoActivity.class);
                        intent1.putExtra("SSZT", qty_cust_zt);
                        intent1.putExtra("DABH", qty_cust_dabh);
                        startActivityForResult(intent1, 4);
                        Log.e("LiNing", "删除数据====" + qty_cust_zt + "---" + qty_cust_dabh);
                    } else {
                        Toast.makeText(context, "配送信息不能编辑", Toast.LENGTH_LONG).show();
                    }
                } else if (check_do == 1) {
                    Toast.makeText(context, "配送信息不存在", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(context, "请编辑信息", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.ib_cust_all_xb:
                checked = 1;
                showPopupMenu(cust_xb);
                break;
            case R.id.ib_cust_all_xfcc:
                checked = 2;
                showPopupMenu(cust_xfcc);
                break;
            case R.id.ib_cust_all_khly:
                checked = 3;
                showPopupMenu(cust_tv_khly);
                break;
            case R.id.ib_cust_all_lplx:
                checked = 4;
                showPopupMenu(cust_tv_lplx);
                break;
            case R.id.ib_cust_all_zxlb:
                checked = 5;
                showPopupMenu(cust_tv_zxlb);
                break;
        }
    }

    private AlertDialog alertDialog;
    private TextView obj_head_add, gjrq, gjfs, gjlb;
    //    private CheckBox obj_zjdm,obj_yydm,obj_dhgj,obj_wldh,obj_sjdx,obj_qt,obj_zkh,obj_yxkh,obj_yzqzkh,obj_ybqzkh;
    private RadioGroup rg_fs, rg_lb;
    private EditText obj_gjzt, gjxq, gjry;
    private Button obj_post;
    private ImageButton fs, lb;
    String tv_zjdm, tv_yydm, tv_dhgj, tv_wldh, tv_sjdx, tv_qt, tv_zkh, tv_yxkh, tv_yzqzkh, tv_ybqzkh, tv_gzzt, tv_gjxq, tv_gjry, time_gz;
    String sub_jdlb, sub_gjfs, no_gz, user_Id_post;

    private void addorSetInfos_obj() {

        View view = getLayoutInflater()
                .inflate(R.layout.obj_two, null);
        obj_head_add = (TextView) view.findViewById(R.id.all_head);
        obj_gjzt = (EditText) view.findViewById(R.id.et_gj_obj_zt);
        gjxq = (EditText) view.findViewById(R.id.et_gj_obj_xq);
        gjry = (EditText) view.findViewById(R.id.et_gj_obj_ry);
        gjlb = (TextView) view.findViewById(R.id.tv_gj_obj_lb);
        gjfs = (TextView) view.findViewById(R.id.tv_gj_obj_fs);
        gjrq = (TextView) view.findViewById(R.id.tv_gj_obj_time);
        gjrq.setText(da_time.getText().toString());
        fs = (ImageButton) view.findViewById(R.id.ib_gj_obj_fs);
        lb = (ImageButton) view.findViewById(R.id.ib_gj_obj_lb);
        fs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked = 6;
                showPopupMenu(fs);
            }
        });
        lb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked = 7;
                showPopupMenu(lb);
            }
        });
        gjrq.setOnClickListener(new View.OnClickListener() {
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
                                gjrq.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });

        if (gz_do == 1) {
            getTicket_gz();//获取临时跟踪编号
            user_Id_post = user_Id;
            obj_head_add.setText("新增跟进信息");
        } else if (gz_do == 2) {
            obj_head_add.setText("修改跟进信息");
            no_gz = foll_no_arr;
            user_Id_post = extra_foll_userno;

            gjfs.setText(extra_foll_way);
            gjlb.setText(extra_foll_jdlb);
            obj_gjzt.setText(extra_foll_them);
            gjxq.setText(extra_foll_case);
            gjry.setText(extra_foll_per);
            SimpleDateFormat sf1 = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
            try {
                Date parse = sf1.parse(extra_foll_time);
                format = new SimpleDateFormat("yyyy-MM-dd").format(parse);
                Log.e("LiNing", "时间====xin=====" + format);
                gjrq.setText(format);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        view.findViewById(R.id.imageButton1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        obj_post = (Button) view.findViewById(R.id.btn_ok_obj);
        obj_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post_objInfos();//新增或修改
            }
        });

        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setCancelable(true);
        alertDialog.setView(view);
        alertDialog.show();
    }

    private void getTicket_gz() {
        String DB_LS = cust_tv_zt.getText().toString();
        date_dd = da_time.getText().toString();
        if (DB_LS.equals("")) {
            Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
        } else if (date_dd.equals("")) {
            Toast.makeText(context, "请获取时间", Toast.LENGTH_LONG).show();
        } else {

            url_dh_price = URLS.price_num_ls;
            Log.e("LiNing", "临时档案编号===" + session + "---" + URLS.price_num_ls + "---" + DB_LS + "----" + date_dd);
            OkHttpClient client = new OkHttpClient();
            FormBody localFormBody = new FormBody.Builder()
                    .add("bn_Type", "CF")
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
                        Log.e("LiNing", "临时跟踪编号===" + str);
                        final PriceNumID bean_id = (PriceNumID) new Gson()
                                .fromJson(str, PriceNumID.class);
                        if (bean_id != null) {
                            CustersAllActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    String follNo_gz = bean_id.getBil_no();
                                    no_gz = follNo_gz;
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

    private void post_objInfos() {
        getPostInfos();
        if (gz_do == 1) {
            url_do_gz = cust_gzadd_z;

        } else if (gz_do == 2) {
            url_do_gz = cust_gzset_z;
        }

        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("Foll_No", no_gz)
                .add("Cust_Acc", qty_cust_zt)
                .add("Cust_No", qty_cust_dabh)
                .add("Foll_Way", sub_gjfs)
                .add("Stag_Class", sub_jdlb)
                .add("Foll_Them", tv_gzzt)
                .add("Foll_Case", tv_gjxq)
                .add("Foll_Per", tv_gjry)
                .add("Foll_DD", time_gz)
                .add("User_No", user_Id_post)
                .build();
        Log.e("LiNing", "添加结果====" + no_gz + "---" + qty_cust_zt + "---" + qty_cust_dabh + "---" + sub_gjfs + "---" + sub_jdlb + "---"
                + tv_gzzt + "---" + user_Id + "---" + tv_gjxq + "---" + tv_gjry + "---" + time_gz);
        client.newCall(
                new Request.Builder().addHeader("cookie", session).url(url_do_gz)
                        .post(body).build()).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e("LiNing", "添加结果====" + str);
                final JsonRootBean localJsonRootBean = (JsonRootBean) new Gson()
                        .fromJson(str, JsonRootBean.class);
                if (localJsonRootBean != null) {
                    CustersAllActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            boolean rlo = localJsonRootBean.getRLO();
                            if (rlo == true) {//新增，修改合并
                                Toast.makeText(CustersAllActivity.this,
                                        "保存成功", Toast.LENGTH_SHORT).show();
                                if (alertDialog.isShowing()) {
                                    alertDialog.dismiss();
                                    getFool_xx();
                                }
                            } else if (rlo == false) {
                                Toast.makeText(CustersAllActivity.this,
                                        "保存失败", Toast.LENGTH_SHORT).show();
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

    private void getPostInfos() {

        sub_gjfs = gjfs.getText().toString();
        sub_jdlb = gjlb.getText().toString();
        tv_gzzt = obj_gjzt.getText().toString();
        tv_gjxq = gjxq.getText().toString();
        tv_gjry = gjry.getText().toString();
        time_gz = gjrq.getText().toString();

    }

    private void delCust_qty() {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("Cust_Acc", qty_cust_zt)
                .add("Cust_No", qty_cust_dabh)
                .add("card_DD", format)
//                .add("cust_Acc", qty_cust_zt)
//                .add("cust_No", qty_cust_dabh)
                .build();
        Log.e("LiNing", "删除数据====" + qty_cust_zt + "---" + qty_cust_dabh);
        client.newCall(
                new Request.Builder().addHeader("cookie", session).url(cust_del_z)
                        .post(body).build()).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string().trim();
                Log.e("LiNing", str);
                final JsonRootBean localJsonRootBean = (JsonRootBean) new Gson()
                        .fromJson(str, JsonRootBean.class);
                if (localJsonRootBean != null) {
                    CustersAllActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            boolean rlo = localJsonRootBean.getRLO();
                            if (rlo == true) {
                                Toast.makeText(CustersAllActivity.this,
                                        "客户删除成功", Toast.LENGTH_SHORT).show();
                                clearInfos();
                            } else if (rlo == false) {
                                Toast.makeText(CustersAllActivity.this,
                                        "客户删除失败", Toast.LENGTH_SHORT).show();
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

    private void addCust() {
        if (cust_psxx_z.equals("")) {
            Toast.makeText(context, "请填写配送信息", Toast.LENGTH_LONG).show();
        } else if (cust_db_z.equals("")) {
            Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
        } else if (cust_dabh_z.equals("")) {
            Toast.makeText(context, "请获取档案编号", Toast.LENGTH_LONG).show();
        } else if (cust_xm_z.equals("")) {
            Toast.makeText(context, "请填写收货人信息", Toast.LENGTH_LONG).show();
        } else if (cust_zcdh_z.equals("")) {
            Toast.makeText(context, "请填写注册电话", Toast.LENGTH_LONG).show();
        } else if (cust_xb_z.equals("")) {
            Toast.makeText(context, "请选择性别", Toast.LENGTH_LONG).show();
        } else if (cust_xszd_z.equals("")) {
            Toast.makeText(context, "请选择销售终端", Toast.LENGTH_LONG).show();
        } else if (cust_xsgw_z.equals("")) {
            Toast.makeText(context, "请填写销售顾问", Toast.LENGTH_LONG).show();
        } else if (cust_yxpp_z.equals("")) {
            Toast.makeText(context, "请填写意向品牌", Toast.LENGTH_LONG).show();
        } else {
            Log.e("LiNing", "-----" + ischeck_vip + "-----" + ischeck_bbqd + "---" + ischeck_psxx);
            if (check_do == 2) {//后补配送信息的传值
                if (ischeck_vip == true) {
                    cust_sjs_z = vip_no_cust;
                } else {
//                    cust_sjs_z=cust_tv_sjs.getText().toString();
                    cust_sjs_z = vipid_hd;
                }
                if (ischeck_bbqd == false) {
//                    des_bbqd_id=cust_xszd_z;
                    des_bbqd_id = idtoname;
                } else {
                    des_bbqd_id = des_bbqd_id;
                }
                if (ischeck_xsgw = false) {
                    xsgw_z = idtoname_gw;
                } else {
                    xsgw_z = des_xsgw_id;
                }
                if (ischeck_psxx == true) {
                    cust_psxx_z = str_id_itm;
                } else {
//                    cust_psxx_z = cust_tv_psxx.getText().toString();
                    cust_psxx_z = psxxid_hd;
                }
                url_do = cust_set_z;
            }
            if (check_do == 1) {
                url_do = cust_add_z;
                cust_psxx_z = "1";
            }
            Log.e("LiNing", "-----" + cust_sjs_z + "-----" + des_bbqd_id);
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("cust_Acc", cust_db_z)
                    .add("card_DD", cust_datime_z)
                    .add("cust_Name", cust_xm_z)
                    .add("cust_Tel", cust_zcdh_z)
                    .add("cust_Sex", cust_xb_z)
                    .add("cust_age", cust_nl_z)
                    .add("cust_Con", cust_psxx_z)
                    .add("cust_Pro", cust_zy_z)
                    .add("cons_lev", cust_xfcc_z)
                    .add("cust_Src", cust_ly_z)
                    .add("hous_Type", cust_lplx_z)
                    .add("deco_Class", cust_zxlb_z)
                    .add("hous_Stru", cust_hxjg_z)
                    .add("mate_App", cust_clyy_z)
//                    .add("mate_App", "卧室,客厅")
                    .add("deco_Style", cust_zxfg_z)
                    .add("deco_Bud", cust_zxys_z)
                    .add("deco_Com", cust_zxgs_z)
//                    .add("deco_DD", "NULL")
                    .add("deco_DD", cust_kzsj_z)
//                    .add("deco_DD", "2018-11-5")
                    .add("deco_Sch", cust_zxjd_z)
                    .add("deco_Vip_ID", cust_sjs_z)
                    .add("cus_No_ERP", des_bbqd_id)
//                    .add("sal_No_ERP", cust_xsgw_z)
                    .add("sal_No_ERP", xsgw_z)
                    .add("mak_ERP", cust_yxpp_z)
                    .add("user_ID_ERP", user_Id)
                    .build();
            Log.e("LiNing", "添加结果====" + cust_db_z + "---" + cust_datime_z + "---" + cust_dabh_z + "---" + cust_xm_z + "---" + cust_zcdh_z + "---"
                    + cust_xb_z + "---" + des_bbqd_id + "---" + xsgw_z + "---" + cust_yxpp_z + "---" + user_Id + "---" + cust_nl_z);
            client.newCall(
                    new Request.Builder().addHeader("cookie", session).url(url_do)
                            .post(body).build()).enqueue(new Callback() {

                @Override
                public void onResponse(Call call, Response response)
                        throws IOException {
                    String str = response.body().string();
                    Log.e("LiNing", "添加结果====" + str);
                    final CstAddInfos localJsonRootBean = (CstAddInfos) new Gson()
                            .fromJson(str, CstAddInfos.class);
                    if (localJsonRootBean != null) {
                        CustersAllActivity.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                boolean rlo = localJsonRootBean.getRLO();
                                cus_no = localJsonRootBean.getCUS_NO();
                                Log.e("LiNing", "添加结果====" + rlo + "----" + cus_no);
                                if (check_do == 1) {
                                    if (rlo == true) {
                                        Toast.makeText(CustersAllActivity.this,
                                                "客户新增成功", Toast.LENGTH_SHORT).show();
                                        check_do = 0;//初始化，避免提示错乱
                                        ischeck_bbqd = false;
                                        ischeck_vip = false;
                                        ischeck_psxx = false;
                                    } else if (rlo == false) {
                                        Toast.makeText(CustersAllActivity.this,
                                                "客户新增失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                if (check_do == 2) {
                                    if (rlo == true) {
                                        Toast.makeText(CustersAllActivity.this,
                                                "客户修改成功", Toast.LENGTH_SHORT).show();
                                        check_do = 0;//初始化，避免提示错乱
                                        ischeck_bbqd = false;
                                        ischeck_vip = false;
                                        ischeck_psxx = false;
                                    } else if (rlo == false) {
                                        Toast.makeText(CustersAllActivity.this,
                                                "客户修改失败", Toast.LENGTH_SHORT).show();
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
    }

    private void getinfos() {
        //后补户型结构，材料应用
        cust_db_z = cust_tv_zt.getText().toString();
        cust_datime_z = da_time.getText().toString();
        cust_dabh_z = cust_ed_dabh.getText().toString();
        cust_xm_z = cust_ed_khxm.getText().toString();
        cust_zcdh_z = cust_ed_zcdh.getText().toString();
        cust_psxx_z = cust_tv_psxx.getText().toString();
        cust_xb_z = cust_tv_xb.getText().toString();
        cust_xsgw_z = cust_ed_xsgw.getText().toString();
        cust_yxpp_z = cust_ed_yxpp.getText().toString();
        cust_xszd_z = cust_ed_xszd.getText().toString();
        if (ck_ws.isChecked()) {
            list_clyy.add(ck_ws.getText().toString());
        }
        if (ck_kt.isChecked()) {
            list_clyy.add(ck_kt.getText().toString());
        }
        if (ck_cf.isChecked()) {
            list_clyy.add(ck_cf.getText().toString());
        }
        if (ck_wsj.isChecked()) {
            list_clyy.add(ck_wsj.getText().toString());
        }
        if (ck_yt.isChecked()) {
            list_clyy.add(ck_yt.getText().toString());
        }
        if (ck_dxs.isChecked()) {
            list_clyy.add(ck_dxs.getText().toString());
        }
        Log.e("LiNing", "材料数据====" + list_clyy);
        cust_nl_z = cust_ed_khnl.getText().toString();
        if (cust_nl_z.equals("")) {
            cust_nl_z = "NULL";
        }
        cust_zy_z = cust_ed_khzy.getText().toString();
        if (cust_zy_z.equals("")) {
            cust_zy_z = "NULL";
        }
        cust_xfcc_z = cust_tv_xfcc.getText().toString();
        if (cust_xfcc_z.equals("")) {
            cust_xfcc_z = "NULL";
        }
        cust_ly_z = cust_tv_khly.getText().toString();
        if (cust_ly_z.equals("")) {
            cust_ly_z = "NULL";
        }
        cust_lplx_z = cust_tv_lplx.getText().toString();
        if (cust_lplx_z.equals("")) {
            cust_lplx_z = "NULL";
        }
        cust_zxlb_z = cust_tv_zxlb.getText().toString();
        if (cust_zxlb_z.equals("")) {
            cust_zxlb_z = "NULL";
        }
        cust_hxjg_z = cust_tv_hxjg.getText().toString();
        if (cust_hxjg_z.equals("")) {
            cust_hxjg_z = "NULL";
        }
        if (list_clyy.size() > 0) {

            String cls_str = "";
            for (String querys_db : list_clyy) {
                cls_str += querys_db + ",";
            }
            cust_clyy_z = cls_str.substring(0, cls_str.length() - 1);
            Log.e("LiNing", "--------cust_clyy_z" + cust_clyy_z);
        } else {
            cust_clyy_z = "NULL";
        }
//        if(cust_clyy_z.equals("")){
//            cust_clyy_z="NULL";
//        }
        cust_zxfg_z = cust_ed_zxfg.getText().toString();
        if (cust_zxfg_z.equals("")) {
            cust_zxfg_z = "NULL";
        }
        cust_zxys_z = cust_ed_zxys.getText().toString();
        if (cust_zxys_z.equals("")) {
            cust_zxys_z = "NULL";
        }
        cust_zxgs_z = cust_ed_zxgs.getText().toString();
        if (cust_zxgs_z.equals("")) {
            cust_zxgs_z = "NULL";
        }
//         cust_sjs_z = cust_tv_sjs.getText().toString();
        if (cust_tv_sjs.getText().toString().equals("")) {
            cust_sjs_z = "NULL";
        } else {
            cust_sjs_z = vip_no_cust;
        }
        cust_kzsj_z = cust_ed_kzsj.getText().toString();
        if (cust_kzsj_z.equals("")) {
            cust_kzsj_z = "NULL";
        }
        cust_zxjd_z = cust_ed_zxjd.getText().toString();
        if (cust_zxjd_z.equals("")) {
            cust_zxjd_z = "NULL";
        }
        cust_bzxx_z = cust_ed_bzxx.getText().toString();
        if (cust_bzxx_z.equals("")) {
            cust_bzxx_z = "NULL";
        }
    }

    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu布局
        if (checked == 1) {
            popupMenu.getMenuInflater().inflate(R.menu.cust_xb_info, popupMenu.getMenu());
        }
        if (checked == 2) {
            popupMenu.getMenuInflater().inflate(R.menu.cust_xfcc_info, popupMenu.getMenu());
        }
        if (checked == 3) {
            popupMenu.getMenuInflater().inflate(R.menu.cust_khly_info, popupMenu.getMenu());
        }
        if (checked == 4) {
            popupMenu.getMenuInflater().inflate(R.menu.cust_lplx_info, popupMenu.getMenu());
        }
        if (checked == 5) {
            popupMenu.getMenuInflater().inflate(R.menu.cust_zxlb_info, popupMenu.getMenu());
        }
        if (checked == 6) {
            popupMenu.getMenuInflater().inflate(R.menu.obj_fs, popupMenu.getMenu());
        }
        if (checked == 7) {
            popupMenu.getMenuInflater().inflate(R.menu.obj_lb, popupMenu.getMenu());
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.check1
                        || menuItem.getItemId() == R.id.check2
                        ) {
                    menuItem.setChecked(!menuItem.isChecked());
                    cust_tv_xb.setText(menuItem.getTitle());
                    return true;
                } else if (menuItem.getItemId() == R.id.check1_xfcc
                        || menuItem.getItemId() == R.id.check2_xfcc
                        || menuItem.getItemId() == R.id.check3_xfcc
                        ) {
                    menuItem.setChecked(!menuItem.isChecked());
                    cust_tv_xfcc.setText(menuItem.getTitle());
                    return true;
                } else if (menuItem.getItemId() == R.id.check1_khly
                        || menuItem.getItemId() == R.id.check2_khly
                        || menuItem.getItemId() == R.id.check3_khly
                        || menuItem.getItemId() == R.id.check4_khly
                        || menuItem.getItemId() == R.id.check5_khly
                        || menuItem.getItemId() == R.id.check6_khly
                        || menuItem.getItemId() == R.id.check7_khly
                        || menuItem.getItemId() == R.id.check8_khly
                        || menuItem.getItemId() == R.id.check9_khly
                        ) {
                    menuItem.setChecked(!menuItem.isChecked());
                    cust_tv_khly.setText(menuItem.getTitle());
                    return true;
                } else if (menuItem.getItemId() == R.id.check1_lplx
                        || menuItem.getItemId() == R.id.check2_lplx
                        || menuItem.getItemId() == R.id.check3_lplx
                        ) {
                    menuItem.setChecked(!menuItem.isChecked());
                    cust_tv_lplx.setText(menuItem.getTitle());
                    return true;
                } else if (menuItem.getItemId() == R.id.check1_zxlb
                        || menuItem.getItemId() == R.id.check2_zxlb
                        || menuItem.getItemId() == R.id.check3_zxlb
                        ) {
                    menuItem.setChecked(!menuItem.isChecked());
                    cust_tv_zxlb.setText(menuItem.getTitle());
                    return true;
                } else if (menuItem.getItemId() == R.id.check1_fs
                        || menuItem.getItemId() == R.id.check2_fs
                        || menuItem.getItemId() == R.id.check3_fs
                        || menuItem.getItemId() == R.id.check4_fs
                        || menuItem.getItemId() == R.id.check5_fs
                        || menuItem.getItemId() == R.id.check6_fs
                        ) {
                    menuItem.setChecked(!menuItem.isChecked());
                    gjfs.setText(menuItem.getTitle());
                    return true;
                } else if (menuItem.getItemId() == R.id.check1_lb
                        || menuItem.getItemId() == R.id.check2_lb
                        || menuItem.getItemId() == R.id.check3_lb
                        || menuItem.getItemId() == R.id.check4_lb
                        ) {
                    menuItem.setChecked(!menuItem.isChecked());
                    gjlb.setText(menuItem.getTitle());
                    return true;
                }

                return false;
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
                    String str_name = data.getStringExtra("condition_name");
                    cust_tv_zt.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + str_name);
                }
                break;
            case 4:
                if (resultCode == 1) {
                    str_id_itm = data.getStringExtra("ITM_ID");
                    String str_name = data.getStringExtra("PSXX_DZ");
                    cust_tv_psxx.setText(str_name);
                    Log.e("LiNing", "提交的id====" + str_name);
                }
                break;
            case 2:
                if (resultCode == 1) {
                    DesignAllInfos.VipList vipList_hd = (DesignAllInfos.VipList) data.getSerializableExtra("VIP_INFOS_ALL");
                    Log.e("LiNing", "提交的id====" + data.getSerializableExtra("VIP_INFOS_ALL"));
                    if (vipList_hd.getVip_Name() != null) {
                        cust_tv_sjs.setText(vipList_hd.getVip_Name());
                    } else {
                        cust_tv_sjs.setText("null");
                    }
                    if (vipList_hd.getVip_NO() != null) {
                        vip_no_cust = vipList_hd.getVip_NO().toString();
                        Log.e("LiNing", "提交的vip_no_cust====" + vip_no_cust);
                    }
                }
                break;
            case 13:
                if (resultCode == 1) {
                    String str1 = data.getStringExtra("data_return");
                    des_bbqd_id = data.getStringExtra("data_return_ids");
                    cust_ed_xszd.setText(str1);
                }
                break;
            case 17:
                if (resultCode == 1) {
                    String str1 = data.getStringExtra("data_return");
                    des_xsgw_id = data.getStringExtra("data_return_ids");
                    cust_ed_xsgw.setText(str1);
                    xsgw_z = des_xsgw_id;
                }
                break;
            case 3:
                if (resultCode == 1) {
                    cust_callback = (CustAllObjectInfos.CustList) data.getSerializableExtra("CUST_QTY");
//                    cust_callback = (CustAllObjectInfos.CustList) data.getSerializableExtra("CUST_QTY");
                    Log.e("LiNing", "提交的id====" + data.getSerializableExtra("VIP_INFOS_ALL"));
                    qty_cust_zt = cust_callback.getCust_Acc().toString();
                    qty_cust_dabh = cust_callback.getCust_No().toString();
                    if (cust_callback.getCust_Acc() != null) {
                        cust_tv_zt.setText(cust_callback.getCust_Acc());
                    } else {
                        cust_tv_zt.setText("");
                    }
                    if (cust_callback.getCard_DD() != null) {
                        SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            Date parse = sf1.parse(cust_callback.getCard_DD().toString());
                            format = new SimpleDateFormat("yyyy-MM-dd").format(parse);
                            Log.e("LiNing", "时间====xin=====" + format);
                            da_time.setText(format);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else {
                        da_time.setText("");
                    }
                    if (cust_callback.getCust_No() != null) {
                        cust_ed_dabh.setText(cust_callback.getCust_No());
                        cust_ed_dabh.setEnabled(false);
                        touch.setEnabled(false);
                    } else {
                        cust_ed_dabh.setText("");
                    }
                    if (cust_callback.getCust_Name() != null) {
                        cust_ed_khxm.setText(cust_callback.getCust_Name());
                    } else {
                        cust_ed_khxm.setText("");
                    }
                    if (cust_callback.getCust_Tel() != null) {
                        cust_ed_zcdh.setText(cust_callback.getCust_Tel());
                    } else {
                        cust_ed_zcdh.setText("");
                    }
                    if (cust_callback.getCust_age() != null) {
                        cust_ed_khnl.setText(cust_callback.getCust_age());
                    } else {
                        cust_ed_khnl.setText("");
                    }
                    if (cust_callback.getCust_Pro() != null) {
                        cust_ed_khzy.setText(cust_callback.getCust_Pro());
                    } else {
                        cust_ed_khzy.setText("");
                    }
                    if (cust_callback.getCust_Sex() != null) {
                        cust_tv_xb.setText(cust_callback.getCust_Sex());
                    } else {
                        cust_tv_xb.setText("");
                    }
                    //配送信息
                    if (cust_callback.getCustConObject() != null && cust_callback.getCustConObject().size() > 0) {
                        cust_tv_psxx.setText(cust_callback.getCustConObject().get(0).getCon_Add());
                        psxxid_hd = cust_callback.getCust_Con().toString();
                        Log.e("LiNing", "地址编号====xin=====" + cust_callback.getCust_Con());
                    } else {
                        cust_tv_psxx.setText("1");
                    }
//                    if (cust_callback.getCust_Con() != null) {
//                        cust_tv_psxx.setText(cust_callback.getCust_Con());
//                    } else {
//                        cust_tv_psxx.setText("");
//                    }
                    //设计师
                    if (cust_callback.getDecoVipObject().size() > 0 && cust_callback.getDecoVipObject() != null) {
                        String vip_name = cust_callback.getDecoVipObject().get(0).getVip_Name();
                        vipid_hd = cust_callback.getDeco_Vip_ID().toString();
                        Log.e("LiNing", "vip编号====xin=====" + cust_callback.getDeco_Vip_ID());
                        cust_tv_sjs.setText(vip_name);
                    } else {
                        cust_tv_sjs.setText(cust_callback.getDeco_Vip_ID());
                    }
//                    if (cust_callback.getDeco_Vip_ID() != null) {
//                        cust_tv_sjs.setText(cust_callback.getDeco_Vip_ID());
//                    } else {
//                        cust_tv_sjs.setText("");
//                    }
                    if (cust_callback.getCons_lev() != null) {
                        cust_tv_xfcc.setText(cust_callback.getCons_lev());
                    } else {
                        cust_tv_xfcc.setText("");
                    }
                    if (cust_callback.getCust_Src() != null) {
                        cust_tv_khly.setText(cust_callback.getCust_Src());
                    } else {
                        cust_tv_khly.setText("");
                    }
                    if (cust_callback.getHous_Type() != null) {
                        cust_tv_lplx.setText(cust_callback.getHous_Type());
                    } else {
                        cust_tv_lplx.setText("");
                    }
                    if (cust_callback.getDeco_Class() != null) {
                        cust_tv_zxlb.setText(cust_callback.getDeco_Class());
                    } else {
                        cust_tv_zxlb.setText("");
                    }
                    if (cust_callback.getHous_Stru() != null) {
                        cust_tv_hxjg.setText(cust_callback.getHous_Stru());
                    } else {
                        cust_tv_hxjg.setText("");
                    }
                    List<String> cl_zz = new ArrayList<String>();
                    if (cust_callback.getMate_App() != null) {
                        String cl = cust_callback.getMate_App().toString();
                        String[] db_spls = cl.split(",");
                        for (int i = 0; i < db_spls.length; i++) {
                            String A = db_spls[i];
                            Log.e("LiNing", "===A==" + A);
                            cl_zz.add(A);
                        }
                        Log.e("LiNing", "==ln===" + cl_zz);
//                        CheckBox(ck_ws,ck_kt,ck_cf,ck_wsj,ck_yt,ck_dxs;)
                        if (cl_zz.contains("卧室")) {
                            ck_ws.setChecked(true);
                        }
                        if (cl_zz.contains("客厅")) {
                            ck_kt.setChecked(true);
                        }
                        if (cl_zz.contains("厨房")) {
                            ck_cf.setChecked(true);
                        }
                        if (cl_zz.contains("卫生间")) {
                            ck_wsj.setChecked(true);
                        }
                        if (cl_zz.contains("阳台")) {
                            ck_yt.setChecked(true);
                        }
                        if (cl_zz.contains("地下室")) {
                            ck_dxs.setChecked(true);
                        }
                    } else {
                        clearChecbox();
                    }
                    if (cust_callback.getDeco_Style() != null) {
                        cust_ed_zxfg.setText(cust_callback.getDeco_Style());
                    } else {
                        cust_ed_zxfg.setText("");
                    }
                    if (cust_callback.getDeco_Bud() != null) {
                        cust_ed_zxys.setText(cust_callback.getDeco_Bud());
                    } else {
                        cust_ed_zxys.setText("");
                    }
                    if (cust_callback.getDeco_Com() != null) {
                        cust_ed_zxgs.setText(cust_callback.getDeco_Com());
                    } else {
                        cust_ed_zxgs.setText("");
                    }

                    if (cust_callback.getDeco_DD() != null) {
//                        cust_ed_kzsj.setText(""+cust_callback.getDeco_DD());
                        SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            Date parse = sf1.parse(cust_callback.getDeco_DD().toString());
                            format = new SimpleDateFormat("yyyy-MM-dd").format(parse);
                            Log.e("LiNing", "时间====xin=====" + format);
                            da_time.setText(format);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else {
                        cust_ed_kzsj.setText("");
                    }
                    if (cust_callback.getDeco_Sch() != null) {
                        cust_ed_zxjd.setText(cust_callback.getDeco_Sch());
                    } else {
                        cust_ed_zxjd.setText("");
                    }
                    if (cust_callback.getSal_No_ERP() != null) {
//                        cust_ed_xsgw.setText(cust_callback.getSal_No_ERP());
                        idtoname_gw = cust_callback.getSal_No_ERP().toString();
                        OkHttpClient client = new OkHttpClient();
                        FormBody body = new FormBody.Builder().add("accountNo", cust_tv_zt.getText().toString())
                                .add("id", idtoname_gw).build();
                        Request request = new Request.Builder()
                                .addHeader("cookie", session).url(url_prdNo).post(body)
                                .build();
                        Call call = client.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String str = response.body().string();
                                Log.e("LiNing", "查询数据===" + str);
                                final DepInfo dInfo = new Gson().fromJson(str,
                                        DepInfo.class);
                                if (dInfo != null) {
                                    CustersAllActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            List<DepInfo.IdNameList> idNameList = dInfo.getIdNameList();
                                            if (idNameList != null && idNameList.size() > 0) {
                                                String xsgw_hd_name = idNameList.get(0).getName().toString();
                                                cust_ed_xsgw.setText(xsgw_hd_name);
                                            } else {
                                                cust_ed_xsgw.setText(idtoname_gw);
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
                        cust_ed_xsgw.setText("");
                    }
                    if (cust_callback.getCus_No_ERP() != null) {
                        //显示名字传id
//                        cust_ed_xszd.setText(cust_callback.getCus_No_ERP());
                        idtoname = cust_callback.getCus_No_ERP().toString();
                        OkHttpClient client = new OkHttpClient();
                        FormBody body = new FormBody.Builder().add("accountNo", cust_tv_zt.getText().toString())
                                .add("id", idtoname).build();
                        Request request = new Request.Builder()
                                .addHeader("cookie", session).url(url_prdNo).post(body)
                                .build();
                        Call call = client.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String str = response.body().string();
                                Log.e("LiNing", "查询数据===" + str);
                                final DepInfo dInfo = new Gson().fromJson(str,
                                        DepInfo.class);
                                if (dInfo != null) {
                                    CustersAllActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            List<DepInfo.IdNameList> idNameList = dInfo.getIdNameList();
//                                            String xszd_hd_name = idNameList.get(0).getName().toString();

                                            if (idNameList != null && idNameList.size() > 0) {

                                                String xsgw_hd_name = idNameList.get(0).getName().toString();
                                                cust_ed_xszd.setText(xsgw_hd_name);
                                            } else {
                                                cust_ed_xszd.setText(idtoname);
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
                        cust_ed_xszd.setText("");
                    }
                    if (cust_callback.getMak_ERP() != null) {
                        cust_ed_yxpp.setText(cust_callback.getMak_ERP());
                    } else {
                        cust_ed_yxpp.setText("");
                    }

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void clearChecbox() {
        ck_ws.setChecked(false);
        ck_kt.setChecked(false);
        ck_cf.setChecked(false);
        ck_wsj.setChecked(false);
        ck_yt.setChecked(false);
        ck_dxs.setChecked(false);
    }

    private void getTicket() {
        String DB_LS = cust_tv_zt.getText().toString();
//        Calendar c = Calendar.getInstance();
//        int mYear = c.get(Calendar.YEAR); // 获取当前年份
//        int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
//        int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前日
//        date_dd = mYear + "-" + mMonth + "-" + mDay;
        date_dd = da_time.getText().toString();
        if (DB_LS.equals("")) {
            Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
        } else if (date_dd.equals("")) {
            Toast.makeText(context, "请获取时间", Toast.LENGTH_LONG).show();
        } else {

            url_dh_price = URLS.price_num_ls;
            Log.e("LiNing", "临时档案编号===" + session + "---" + URLS.price_num_ls + "---" + DB_LS + "----" + date_dd);
            OkHttpClient client = new OkHttpClient();
            FormBody localFormBody = new FormBody.Builder()
                    .add("bn_Type", "CT")
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
                        Log.e("LiNing", "临时档案编号===" + str);
                        final PriceNumID bean_id = (PriceNumID) new Gson()
                                .fromJson(str, PriceNumID.class);
                        if (bean_id != null) {
                            CustersAllActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    String id_ls = bean_id.getBil_no();
                                    cust_ed_dabh.setText(id_ls);
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

    private void clearInfos() {
        cust_tv_zt.setText("");
        da_time.setText(date_dd);
        cust_ed_dabh.setText("");
        cust_ed_khxm.setText("");
        cust_ed_zcdh.setText("");
        cust_ed_khnl.setText("");
        cust_ed_khzy.setText("");
        cust_tv_xb.setText("");
        cust_tv_xfcc.setText("");
        cust_tv_khly.setText("");
        cust_tv_lplx.setText("");
//        材料应用、户型结构（后补）
        cust_tv_zxlb.setText("");
        cust_ed_zxfg.setText("");
        cust_ed_zxys.setText("");
        cust_ed_zxgs.setText("");
        cust_tv_sjs.setText("");
        cust_ed_kzsj.setText(date_dd);
        cust_ed_zxjd.setText("");
        cust_ed_xszd.setText("");
        cust_ed_xsgw.setText("");
        cust_ed_yxpp.setText("");
        cust_ed_bzxx.setText("");
    }

    private ListView lv_xxgz;
    private RelativeLayout head_xxgz;
    String xxgz_query = URLS.cust_gzxx_query;
    String xxgz_del = URLS.cust_gzxx_del;
    FollAdapter follAdapter;
    List<FollInfos.FollList> follList_All;
    private List<Integer> checkedIndexList;
    private List<CheckBox> checkBoxList;
    private List<String> idList;
    String foll_no_arr, sub_follNo;
    String extra_foll_time, extra_foll_way, extra_foll_them, extra_foll_case, extra_foll_jdlb, extra_foll_per, extra_foll_userno;

    public void rb_gz(View v) {
        findViewById(R.id.rl_bj_obj).setVisibility(View.GONE);
        findViewById(R.id.rl_xs_obj).setVisibility(View.GONE);
        findViewById(R.id.ll_xxobj).setVisibility(View.VISIBLE);
        tv_bt_xxg = (TextView) findViewById(R.id.tv_id_btnName);
        bg_gzxx_set = (Button) findViewById(R.id.btn_gzxx_xg);
        bg_gzxx_del = (Button) findViewById(R.id.btn_gzxx_sc);

        lv_xxgz = (ListView) findViewById(R.id.lv_xxobj_header);
        head_xxgz = (RelativeLayout) findViewById(R.id.xxObj_head);
        head_xxgz.setFocusable(true);
        head_xxgz.setClickable(true);
        head_xxgz.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        lv_xxgz.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        getFool_xx();
        bg_gzxx_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除
                if (checkedIndexList != null && checkedIndexList.size() > 0) {
                    new AlertDialog.Builder(context)
                            .setTitle("是否删除")
                            .setPositiveButton("是",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {

                                            delCust_gzxx();
                                        }
                                    }).setNegativeButton("否", null).show();
                } else {
                    Toast.makeText(context, "请选择数据", Toast.LENGTH_LONG).show();
                }
            }
        });
        bg_gzxx_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改
                if (0 < idList.size() && idList.size() <= 1) {
                    //回调数据
                    gz_do = 2;
                    addorSetInfos_obj();
                } else {
                    Toast.makeText(context, "请选择一条数据", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void delCust_gzxx() {
        if(idList!=null&&idList.size()>0){
            for (int i=0;i<idList.size();i++){
                String del_id = idList.get(i);
                OkHttpClient client = new OkHttpClient();
                FormBody body = new FormBody.Builder()
                        .add("Cust_Acc", qty_cust_zt)
                        .add("Cust_No", qty_cust_dabh)
                        .add("Foll_No", del_id)
                        .build();
                client.newCall(
                        new Request.Builder().addHeader("cookie", session).url(xxgz_del)
                                .post(body).build()).enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String str = response.body().string();
                        Log.e("LiNing", "删除结果====" + str);
                        final JsonRootBean localJsonRootBean = (JsonRootBean) new Gson()
                                .fromJson(str, JsonRootBean.class);
                        if (localJsonRootBean != null) {
                            CustersAllActivity.this.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    boolean rlo = localJsonRootBean.getRLO();
                                    if (rlo == true) {
                                        Toast.makeText(CustersAllActivity.this,
                                                "删除结果", Toast.LENGTH_SHORT).show();
                                        getFool_xx();
                                    } else if (rlo == false) {
                                        Toast.makeText(CustersAllActivity.this,
                                                "删除结果", Toast.LENGTH_SHORT).show();
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

    }

    private void getFool_xx() {
        if (cust_callback != null) {


        if (!qty_cust_zt.equals("") && !qty_cust_dabh.equals("")) {
            OkHttpClient client = new OkHttpClient();
            FormBody localFormBody = new FormBody.Builder()
                    .add("Cust_Acc", qty_cust_zt)
                    .add("Cust_No", qty_cust_dabh)
                    .build();
            Request localRequest = new Request.Builder()
                    .addHeader("cookie", session).url(xxgz_query)
                    .post(localFormBody)
                    .build();
            client.newCall(localRequest).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String str = response.body().string();
                    Log.e("LiNing", "信息数据====" + str);
                    // 解析包含date的数据必须添加此代码(InputStream型)
                    Gson gson = new GsonBuilder().setDateFormat(
                            "yyyy-MM-dd HH:mm:ss").create();
                    final FollInfos cInfoDB = gson.fromJson(str,
                            FollInfos.class);
                    if (cInfoDB != null) {
                        CustersAllActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                follList_All = cInfoDB.getFollList();
                                follAdapter = new FollAdapter(R.layout.xxobj_head, follList_All, context);
                                lv_xxgz.setAdapter(follAdapter);
                                follAdapter.notifyDataSetChanged();
                                checkedIndexList.clear();
                                idList.clear();
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call call, IOException e) {

                }
            });
        }
        }else{
            Toast.makeText(context,"无信息",Toast.LENGTH_LONG).show();
        }
    }

    public class FollAdapter extends BaseAdapter {
        int id_row;
        List<FollInfos.FollList> foll_infos;
        LayoutInflater mInflater;
        private int selectedPosition = -1;// 选中的位置

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public FollAdapter(int xxobj_head, List<FollInfos.FollList> follList_All, Context context) {
            this.id_row = xxobj_head;
            this.foll_infos = follList_All;
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return foll_infos.size();
        }

        @Override
        public Object getItem(int position) {
            return foll_infos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                synchronized (CustersAllActivity.this) {
                    convertView = mInflater.inflate(id_row, null);
                    holder = new ViewHolder();
                    MyHScrollView scrollView1 = (MyHScrollView) convertView
                            .findViewById(R.id.horizontalScrollView1);
                    holder.scrollView = scrollView1;
                    holder.checkbox = (CheckBox) convertView.findViewById(R.id.xxobj_listDeleteCheckBox);
                    holder.checkbox.setVisibility(View.VISIBLE);
                    holder.layouts = (RelativeLayout) convertView
                            .findViewById(R.id.rl_item);
                    holder.foll_dabh = (TextView) convertView.findViewById(R.id.textView1_id_dabh);
                    holder.foll_gzbh = (TextView) convertView.findViewById(R.id.textView2_gzbh);
                    holder.foll_gjrq = (TextView) convertView.findViewById(R.id.textView3_gjrq);
                    holder.foll_gjfs = (TextView) convertView.findViewById(R.id.textView4_gjfs);
                    holder.foll_gjzt = (TextView) convertView.findViewById(R.id.textView5_gjzt);
                    holder.foll_gjqk = (TextView) convertView.findViewById(R.id.textView6_gjqk);
                    holder.foll_jdlb = (TextView) convertView.findViewById(R.id.textView7_jdlb);
                    holder.foll_gjry = (TextView) convertView.findViewById(R.id.textView8_gjry);
                    MyHScrollView headSrcrollView = (MyHScrollView) head_xxgz
                            .findViewById(R.id.horizontalScrollView1);
                    headSrcrollView
                            .AddOnScrollChangedListener(new OnScrollChangedListenerImp(
                                    scrollView1));
                    // 将item中的checkbox放到checkBoxList中
                    checkBoxList.add(holder.checkbox);
                    convertView.setTag(holder);
                }
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            FollInfos.FollList follList = foll_infos.get(position);
            holder.foll_dabh.setText(follList.getCust_No().toString());
            holder.foll_gzbh.setText(follList.getFoll_No().toString());
            //            holder.foll_gjrq.setText(follList.getFoll_DD().toString());
//            SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sf1 = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
            try {
                Date parse = sf1.parse(follList.getFoll_DD().toString());
                format = new SimpleDateFormat("yyyy-MM-dd").format(parse);
                Log.e("LiNing", "时间====xin=====" + format);
                holder.foll_gjrq.setText(format);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            holder.foll_gjfs.setText(follList.getFoll_Way().toString());
            holder.foll_gjzt.setText(follList.getFoll_Them().toString());
            holder.foll_gjqk.setText(follList.getFoll_Case().toString());
            holder.foll_jdlb.setText(follList.getStag_Class().toString());
            holder.foll_gjry.setText(follList.getFoll_Per().toString());
            holder.checkbox.setOnCheckedChangeListener(new CheckBoxListener(
                    position));
            return convertView;
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
            CheckBox checkbox;
            public RelativeLayout layouts;
            HorizontalScrollView scrollView;
            public TextView foll_dabh;
            public TextView foll_gzbh;
            public TextView foll_gjrq;
            public TextView foll_gjfs;
            public TextView foll_gjzt;
            public TextView foll_gjqk;
            public TextView foll_jdlb;
            public TextView foll_gjry;


        }
    }

    public class CheckBoxListener implements CompoundButton.OnCheckedChangeListener {
        int positions;
        private String userIds;

        public CheckBoxListener(int position) {
            this.positions = position;

        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                checkedIndexList.add(positions);
                foll_no_arr = follList_All.get(positions).getFoll_No();
                idList.add(foll_no_arr);
                String zts_str = "";
                for (String querys_db : idList) {
                    zts_str += querys_db + ",";
                }
                sub_follNo = zts_str.substring(0, zts_str.length() - 1);

                extra_foll_time = follList_All.get(positions).getFoll_DD().toString();
                extra_foll_way = follList_All.get(positions).getFoll_Way().toString();
                extra_foll_them = follList_All.get(positions).getFoll_Them().toString();
                extra_foll_case = follList_All.get(positions).getFoll_Case().toString();
                extra_foll_jdlb = follList_All.get(positions).getStag_Class().toString();
                extra_foll_per = follList_All.get(positions).getFoll_Per().toString();
                extra_foll_userno = follList_All.get(positions).getUser_No().toString();
            } else {
                checkedIndexList.remove((Integer) positions);
                Log.e("LiNing", "--------删除集合" + checkedIndexList);
                idList.remove(foll_no_arr);
                if (idList != null && idList.size() > 0) {
//                isnull = 1;
                    String zts_str = "";
                    for (String querys_db : idList) {
                        zts_str += querys_db + ",";
                    }
                    sub_follNo = zts_str.substring(0, zts_str.length() - 1);
                    Log.e("LiNing", "--------sub_id" + sub_follNo);
                } else {
                    Toast.makeText(CustersAllActivity.this, "未选中数据", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {
        ListViewAndHeadViewTouchLinstener() {
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ((HorizontalScrollView) CustersAllActivity.this.head_xxgz
                    .findViewById(R.id.horizontalScrollView1))
                    .onTouchEvent(event);
            return false;
        }

    }
}
