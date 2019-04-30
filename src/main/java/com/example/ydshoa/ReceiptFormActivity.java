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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.LeftOrRight.MyHScrollView;
import com.example.adapter.LxfkAdapter;
import com.example.bean.Brand;
import com.example.bean.CustAllObjectInfos;
import com.example.bean.DepInfo;
import com.example.bean.LxInfos;
import com.example.bean.NumberToCN;
import com.example.bean.PriceNumID;
import com.example.bean.ReceiptInfos;
import com.example.bean.ReceiptSkdForm;
import com.example.bean.SkdYingsMx;
import com.example.bean.URLS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ReceiptFormActivity extends Activity implements View.OnClickListener {
    //根数据
    private Context context;
    private SharedPreferences sp;
    private String session,user_yh,kh_bh_tj,kh_bh,str_id_zdwd,id_ls_bddh,str_id_bm,str_id_ywy,str_id_djlb;
    //表头操作（功能）
    private Button query, add, reset, del, print, save, info, out,yings_mx,yus_mx,tjlb_qtzk,tjlb_qtzk_sc,tjlb_qtzk_xg,tjlb_qtzk_commit;
    //基本数据
    private String db_zt, date_dd, date_dd_dh, db_bm, db_kh,lx_type;
    RelativeLayout mHead_yis_item, mHead_yings;
    private ListView lv_skd_qry_yus, lv_skd_qry_yings;
    private TextView head, skd_tv_zt, skd_time, skd_tv_dh, skd_lydh,skd_bm, skd_kh, skd_ywy, skd_zdwd, skd_djlb,  skd_yscz,
            skd_time_sx, skd_time_zs, skd_xxje, skd_hjje,skd_cske,skd_cyingse,skd_skje,skd_cyusee,skd_zdr,skd_shr,skd_yushoujine,skd_yuchongtiqu,kb_qtzk;
    private EditText et,skd_zy,skd_pjje, skd_yhzh, skd_qtzk, skd_xjzh,je_qtzk;
    private ImageButton skd_ib_zt, skd_ib_bm, skd_ib_kh, skd_ib_ywy, skd_ib_zdwd, skd_ib_djlb, skd_ib_pjje, skd_ib_yhzh, skd_ib_qtzk, skd_ib_xjzh, skd_ib_yscz;
    LinearLayout touch;
    String url_dh_skd = URLS.price_num_ls;
    AlertDialog alg_qtzk,alg_xjzh;
    CustAllObjectInfos.CustList cust_callback;
    String s_zh,clientRows,comit_zt,comit_time,comit_time_zb,comit_dh,comit_dep,comit_czy,comit_zdwd,comit_zdkh,comit_djlb,comit_zdr,comit_shr,
            comit_lydh,comit_zy,comit_bil_ID,comit_sor_ID;
    String url_query_skd =  URLS.skd_url_query;
    String url_add_skd =  URLS.skd_url_add;
    String url_add_mx =  URLS.skd_url_mx;
    List<ReceiptSkdForm.Mf_monList> mf_monList;
    List<ReceiptSkdForm.Mf_monList> mf_monList_all= new ArrayList<ReceiptSkdForm.Mf_monList>();
    //dialog数据
    private Button query_ok_cd,start_quick,stop_quick,skd_get;
    private ImageButton query_fh_cd;
    private ListView lv_query_skd;
    String str_time, stopTime;
    //新增的数据
    String add_zt,add_rp_dd,add_rp_NO,add_bil_NO,add_bil_type,add_dep,add_usr_NO,add_zdwd,add_cus_NO,
            add_rem,add_amtn_BC,add_amtn_BB,add_amtn_Net,add_amtn_qt,add_amtn_hj,add_amtn_xxhj,add_amtn_IRP,add_amtn_CLS
            ,add_cske,add_cyse,add_skje,add_cyuse,add_usr,add_sxrq,add_shr,add_zsrq;
    //付款类型
    int click_obj,index_lx;//标识符
    List<LxInfos.IdNameList> lxInfosIdNameList;
    String fklx_id,fklx_id_xj;
    //应收明细
    List<SkdYingsMx.MfArpList> mfArpList_yings;
    String sub_yis_xh,sub_yis_khbh,sub_yis_khmc,sub_yis_ysph,sub_yis_ysdh,sub_yis_djrq,
            sub_yis_ysje,sub_yis_ycje,sub_yis_bccx,sub_yis_wcjy,sub_yis_yshl,sub_yis_kzbz;
    List<ReceiptSkdForm.Mf_CHK> mf_chk_fh;
    //其他金额
    private ArrayList<HashMap<String, Object>> dList;
    private SimpleAdapter sAdapter;
    /** 记录选中item的下标 */
    private List<Integer> checkedIndexList;
    /** 保存每个item中的checkbox */
    private List<CheckBox> checkBoxList;
    private HashMap<String, Object> items;
    private HashMap<String, Object> item;
    private Integer index;
    private int checkFalg,flag;
    private Boolean mCheckable = Boolean.valueOf(false);
    String jf_id_hd;
    ListView lv_qtzk;
    String idcopy,namecopy,jecopy,sub_id,sub_name,sub_xh,sub_je;
    private ArrayList<String> listJes, listIDs, listNAMEs,listXhs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_receipt_form);
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        context = ReceiptFormActivity.this;
        db_zt = sp.getString("DB_MR", "");
        db_bm = sp.getString("USER_DEPBM", "");
        db_kh = sp.getString("USER_CUSTKH", "");
        user_yh = sp.getString("MR_YH", "");
        Log.e("LiNing","----"+user_yh);
        getNowTime();
        intView();
    }

    private void getNowTime() {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // 获取当前年份
        int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前日
        date_dd = mYear + "-" + mMonth + "-" + mDay;
    }

    private void intView() {
        head = (TextView) findViewById(R.id.all_head);
        head.setText("收款单");

        mHead_yis_item = ((RelativeLayout) findViewById(R.id.skd_head_item));
        mHead_yis_item.setVisibility(View.GONE);
        this.mHead_yis_item.setFocusable(true);
        this.mHead_yis_item.setClickable(true);
        this.mHead_yis_item.setOnTouchListener(new ListViewAndHeadViewTouchLinstener_yings());
        mHead_yings = ((RelativeLayout) findViewById(R.id.skdmx_head_yings));
        mHead_yings.setFocusable(true);
        mHead_yings.setClickable(true);
        mHead_yings.setOnTouchListener(new ListViewAndHeadViewTouchLinstener_yings());
//        lv_skd_qry_yus = (ListView) findViewById(R.id.lv_skd_header_yus);
//        lv_skd_qry_yus.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        lv_skd_qry_yings = (ListView) findViewById(R.id.lv_skd_header_yings);
        lv_skd_qry_yings.setOnTouchListener(new ListViewAndHeadViewTouchLinstener_yings());
        skd_tv_zt = (TextView) findViewById(R.id.et_skd_all_zt);
        skd_tv_zt.setText(db_zt);
        skd_ib_zt = (ImageButton) findViewById(R.id.ib_skd_all_account);
        skd_ib_zt.setOnClickListener(this);
        skd_time = (TextView) findViewById(R.id.tv_time_skd);
        skd_time.setText(date_dd);
        skd_time.setOnClickListener(new View.OnClickListener() {
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
                                skd_time.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        skd_time_sx = (TextView) findViewById(R.id.tv_hj_sxrq);
        skd_time_sx.setText(date_dd);
        skd_time_sx.setOnClickListener(new View.OnClickListener() {
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
                                skd_time_sx.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        skd_time_zs = (TextView) findViewById(R.id.tv_hj_zsrq);
        skd_time_zs.setText(date_dd);
        skd_time_zs.setOnClickListener(new View.OnClickListener() {
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
                                skd_time_zs.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        skd_tv_dh = (TextView) findViewById(R.id.et_skd_all_skdh);
        skd_tv_dh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTicket();
            }
        });
        touch = (LinearLayout) findViewById(R.id.ll_touch_skd_all);
        touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getTicket();//获取临时档案编号
            }
        });
        //来源单号
        skd_lydh = (TextView) findViewById(R.id.tv_skd_lydh);
        //摘要
        skd_zy = (EditText) findViewById(R.id.tv_skd_bzxx);
        //部门(id转name)
        skd_bm = (TextView) findViewById(R.id.tv_skd_jybm);
        skd_bm.setText(db_bm);
        skd_ib_bm = (ImageButton) findViewById(R.id.ib_skd_jybm);
        skd_ib_bm.setOnClickListener(this);
        //默认客户（添加模糊查询）
        skd_kh = (TextView) findViewById(R.id.tv_skd_zdkh);
        skd_ib_kh = (ImageButton) findViewById(R.id.ib_skd_zdkh);
        skd_ib_kh.setOnClickListener(this);
        //业务员
        skd_ywy = (TextView) findViewById(R.id.tv_skd_zryw);
        skd_ib_ywy = (ImageButton) findViewById(R.id.ib_skd_zryw);
        skd_ib_ywy.setOnClickListener(this);
        //终端网点客户(id转name)
        skd_zdwd = (TextView) findViewById(R.id.tv_skd_zdwd);
        skd_zdwd.setText(db_kh);
        skd_ib_zdwd = (ImageButton) findViewById(R.id.ib_skd_zdwd);
        skd_ib_zdwd.setOnClickListener(this);
        //单据类别
        skd_djlb = (TextView) findViewById(R.id.tv_skd_djlb);
        skd_ib_djlb = (ImageButton) findViewById(R.id.ib_skd_djlb);
        skd_ib_djlb.setOnClickListener(this);
        //票据金额
        skd_pjje = (EditText) findViewById(R.id.et_skd_pj);
        skd_ib_pjje = (ImageButton) findViewById(R.id.ib_skd_pj);
        skd_ib_pjje.setOnClickListener(this);
        //其他账款
        skd_qtzk = (EditText) findViewById(R.id.et_skd_qtje);
        skd_ib_qtzk = (ImageButton) findViewById(R.id.ib_skd_qtje);
        skd_ib_qtzk.setOnClickListener(this);
        //现金账户
        skd_xjzh = (EditText) findViewById(R.id.tv_skd_xj);
        skd_ib_xjzh = (ImageButton) findViewById(R.id.ib_skd_xjzh);
        skd_ib_xjzh.setOnClickListener(this);
        //银行账户
        skd_yhzh = (EditText) findViewById(R.id.tv_skd_yh);
        skd_ib_yhzh = (ImageButton) findViewById(R.id.ib_skd_yh);
        skd_ib_yhzh.setOnClickListener(this);
        //数字金额大小写切换
        skd_xxje = (TextView) findViewById(R.id.et_skd_jexx);
        skd_xxje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skd_xxje.setText(""+i_heje);
            }
        });
        skd_hjje = (TextView) findViewById(R.id.et_skd_skhj);
        BigDecimal numberOfMoney = new BigDecimal(Double.parseDouble(skd_xxje.getText().toString()));
        String s = NumberToCN.number2CNMontrayUnit(numberOfMoney);
        skd_hjje.setText(s);
        skd_yushoujine = (TextView) findViewById(R.id.tv_bchj_yushj);
        skd_yuchongtiqu = (TextView) findViewById(R.id.tv_bchj_yuc);

        //底部数据
        skd_cske = (TextView) findViewById(R.id.tv_hj_cske);//冲收款额
        skd_cyingse = (TextView) findViewById(R.id.tv_hj_cyingse);//冲应收额
        skd_skje = (TextView) findViewById(R.id.tv_hj_skje);//收款金额
        skd_cyusee = (TextView) findViewById(R.id.tv_hj_cyuse);//冲预收额
        skd_zdr = (TextView) findViewById(R.id.tv_hj_zdr);//制单人(录入员,登录者id)
        skd_shr = (TextView) findViewById(R.id.tv_hj_shr);//审核人(登录者id)


        //表头操作（功能）
        query = (Button) findViewById(R.id.btn_skd_all_quickquery);
        add = (Button) findViewById(R.id.btn_skd_all_add);
        reset = (Button) findViewById(R.id.btn_skd_all_reset);
        del = (Button) findViewById(R.id.btn_skd_all_quickquery);
        print = (Button) findViewById(R.id.btn_skd_all_prite);
        save = (Button) findViewById(R.id.btn_skd_all_save);
        info = (Button) findViewById(R.id.btn_skd_all_okd);
        out = (Button) findViewById(R.id.btn_skd_all_out);
        //明细数据
        yings_mx = (Button) findViewById(R.id.btn_yingsmx);
        yus_mx = (Button) findViewById(R.id.btn_yusmx);
        query.setOnClickListener(this);
        add.setOnClickListener(this);
        reset.setOnClickListener(this);
        del.setOnClickListener(this);
        print.setOnClickListener(this);
        save.setOnClickListener(this);
        info.setOnClickListener(this);
        out.setOnClickListener(this);
        yings_mx.setOnClickListener(this);
        yus_mx.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_skd_all_quickquery:
                //    http://oa.ydshce.com:8080/InfManagePlatform/PaymentsqueryPayment.action?db_Id=DB_BJ18&&showRow=300&&clientRows=0&&rp_ID=1&&rp_NO=RT201805230051
//    &&rp_DD=2018-05-23&&usr_NO=L022&&dep=2023&&rem=孙黎明&&bil_NO=SO201805230097&&irp_ID=T&&CLS_ID=T&&cus_NO=KH031&&bil_TYPE=06&&usr=Z002&&chk_MAN=Z002
//    &&cus_NO_OS=123
                //速查（是否限制条件，账套必须传）
                //http://oa.ydshce.com:8080/InfManagePlatform/PaymentsqueryPayment.action?db_Id=DB_BJ18&&showRow=20&&clientRows=0
                get_aleartDialog();

//                Intent intent_query=new Intent(context,ReceiptQueryActivity.class);
//                startActivity(intent_query);
                break;
            case R.id.btn_skd_all_add:
                //新增
                click_obj = 1;
                clear_infos_add();
                add_comit();
                break;
            case R.id.btn_skd_all_reset:
                //编辑
                //测试（修改数据）
                click_obj = 2;
                add_comit();
                clear_infos_add();
                break;
            case R.id.btn_skd_all_del:
                //测试
                add_comit();
                //通过子表数据判断是否提交
                comit_add_infosAll();
                //删除
                break;
            case R.id.btn_skd_all_out:
                //关闭
                finish();
                break;
            case R.id.btn_skd_all_save:
                //保存
                if( click_obj == 1){
                    add_comit();
                    //通过子表数据判断是否提交
                    comit_add_infosAll();
                }
                break;
            case R.id.ib_skd_pj:
                //回调票据/金额信息
//                if(mf_chk_fh!=null&&mf_chk_fh.size()>0){
//
//                    //传递数据mf_chk_fh
//                    Intent intent_pjje = new Intent(context, BillAmtActivity.class);
//                    String str_items = new Gson().toJson(mf_chk_fh);
//                    intent_pjje.putExtra("fh_sjs", str_items);
//                    startActivityForResult(intent_pjje, 1);
//                }else{
//                    Toast.makeText(context, "无数据", Toast.LENGTH_LONG).show();
//                }
                Intent intent_pjje = new Intent(context, BillAmtActivity.class);
                final String str_items = new Gson().toJson(mf_chk_fh);
                intent_pjje.putExtra("fh_sjs", str_items);
                startActivityForResult(intent_pjje, 1);//此处要变
                break;

            case R.id.ib_skd_qtje:
                //其他账款
                dList = new ArrayList();
                checkedIndexList = new ArrayList<Integer>();
                checkBoxList = new ArrayList<CheckBox>();
                View view_qtzk = getLayoutInflater()
                        .inflate(R.layout.skd_qtzk, null);
                TextView head_qtzk = (TextView) view_qtzk.findViewById(R.id.all_head);
                head_qtzk.setText("其他账款");
                 kb_qtzk = (TextView) view_qtzk.findViewById(R.id.tv_skd_qt_kb);
                 je_qtzk = (EditText) view_qtzk.findViewById(R.id.et_skd_qt_bwbje);
                 tjlb_qtzk = (Button) view_qtzk.findViewById(R.id.btn_km_obj);
                 tjlb_qtzk_xg = (Button) view_qtzk.findViewById(R.id.btn_km_obj_xg);
                 tjlb_qtzk_sc = (Button) view_qtzk.findViewById(R.id.btn_km_obj_sc);
                 tjlb_qtzk_commit = (Button) view_qtzk.findViewById(R.id.btn_km_obj_tj);

                lv_qtzk = (ListView) view_qtzk.findViewById(R.id.lv_skd_qtzk_head);
                ImageButton ib_qt= (ImageButton) view_qtzk.findViewById(R.id.ib_skd_qt_kb);
                this.sAdapter = new SimpleAdapter(this.context, this.dList,
                        R.layout.skd_qtzk_head_item, new String[] { "科目代号", "科目名称",
                        "本位币金额" }, new int[] {
                        R.id.new_querry_item_kbid, R.id.new_querry_item_kbmc,
                        R.id.new_querry_item_kbje}) {
                    @Override
                    public View getView(final int position, View convertView,
                                        ViewGroup parent) {
                        // 获取相应的view中的checkbox对象
                        if (convertView == null)
                            convertView = View.inflate(ReceiptFormActivity.this,
                                    R.layout.skd_qtzk_head_item, null);
                        final CheckBox checkBox = (CheckBox) convertView
                                .findViewById(R.id.listDeleteCheckBox);
                        checkBoxList.add(checkBox);
                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(CompoundButton buttonView,
                                                         boolean isChecked) {
                                // TODO Auto-generated method stub
                                if (isChecked) {
                                    checkFalg = 1;
                                    checkedIndexList.add(position);

                                } else {
                                    checkFalg = 0;
                                    checkedIndexList.remove((Integer) position);
                                }
                                if (0 < checkedIndexList.size()
                                        && checkedIndexList.size() <= 1) {
                                    for (int i = 0; i < checkedIndexList.size(); i++) {
                                        index = checkedIndexList.get(i);
                                        items = ((HashMap) ReceiptFormActivity.this.dList
                                                .get(index));
                                        Log.e("LiNing", "11111111"
                                                + ReceiptFormActivity.this.items);
                                        Boolean localBoolean = (Boolean) ReceiptFormActivity.this.items
                                                .get("checkbox");
                                        Log.e("LiNing", "isChecked====" + localBoolean);
                                         idcopy= items.get("科目代号").toString();
                                         namecopy = items.get("科目名称").toString();
                                         jecopy = items.get("本位币金额").toString();
                                    }
                                    Log.e("LiNing", "index-----111" + index);
                                } else {
                                    // Toast.makeText(context, "修改时请选择单条数据", 1).show();
                                }
                            }
                        });
                        return super.getView(position, convertView, parent);
                    }
                };
                lv_qtzk.setAdapter(this.sAdapter);
                lv_qtzk.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        for (int i = 0; i < checkBoxList.size(); i++) {
                            checkBoxList.get(i).setVisibility(View.VISIBLE);
                        }
                    }
                });
                ib_qt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "获取数据（其他金额）", Toast.LENGTH_LONG).show();
                        Intent intent_km = new Intent(context, KmClassActivity.class);
                        intent_km.putExtra("ZT_VIP", skd_tv_zt.getText().toString());
                        startActivityForResult(intent_km, 9);
                    }
                });
                view_qtzk.findViewById(R.id.imageButton1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alg_qtzk.dismiss();
                    }
                });
                tjlb_qtzk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (flag == 9) {// 修改标识
                            if (checkFalg == 1) {// 选中标识
                                item = new HashMap<String, Object>();
                                item.put("checkbox", mCheckable);
                                item.put("科目代号", idcopy);
                                item.put("科目名称", kb_qtzk.getText().toString());
                                item.put("本位币金额", je_qtzk.getText().toString());
                                dList.remove(items);
                                dList.add(0, item);
                                sAdapter.notifyDataSetChanged();
                                kb_qtzk.setText(null);
                                je_qtzk.setText(null);
                                checkFalg = 0;
                                for (int i = 0; i < checkBoxList.size(); i++) {
                                    // 将已选的设置成未选状态
                                    checkBoxList.get(i).setChecked(false);
                                }
                            }
                        }else {

                            //添加数据到列表
                            item = new HashMap<String, Object>();
                            item.put("checkbox", mCheckable);
                            item.put("科目代号", jf_id_hd);
                            item.put("科目名称", kb_qtzk.getText().toString());
                            item.put("本位币金额", je_qtzk.getText().toString());
                            dList.add(item);
//                            String str2 = new Gson().toJson(dList);
//                            sp.edit().putString("QtJeMx", str2).commit();
                            sAdapter.notifyDataSetChanged();
                            kb_qtzk.setText(null);
                            je_qtzk.setText(null);
                        }

                    }
                });
                tjlb_qtzk_sc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (0 < checkedIndexList.size() && checkedIndexList != null) {
                            new AlertDialog.Builder(ReceiptFormActivity.this)
                                    .setTitle("是否删除数据？")
                                    .setPositiveButton("确定",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(
                                                        DialogInterface paramAnonymousDialogInterface,
                                                        int paramAnonymousInt) {
                                                    delMore();

                                                }
                                            }).setNegativeButton("取消", null).show();
                        } else {
                            Toast.makeText(ReceiptFormActivity.this, "请选择要操作的数据", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                tjlb_qtzk_xg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (lv_qtzk.getCount() > 0) {
                            flag = 9;
                            // if (checkFalg == 1) {
                            if (0 < checkedIndexList.size() && checkedIndexList.size() <= 1) {
                                kb_qtzk.setText(namecopy);
                                je_qtzk.setText(jecopy);

                            } else {
                                Toast.makeText(ReceiptFormActivity.this, "请选择一条数据", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(ReceiptFormActivity.this, "无数据，请添加", Toast.LENGTH_LONG).show();
                        }
                        sAdapter.notifyDataSetChanged();
                    }
                });
                tjlb_qtzk_commit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listXhs = new ArrayList<String>();
                        listIDs = new ArrayList<String>();
                        listNAMEs = new ArrayList<String>();
                        listJes = new ArrayList<String>();
                        int sum=0;
                        Toast.makeText(ReceiptFormActivity.this, "等等", Toast.LENGTH_LONG).show();
                        if (dList != null && dList.size() > 0) {

                           //遍历list
                            for (int i=0;i<dList.size();i++){
                                int index = i + 1;
                                listXhs.add(""+index);
                                listIDs.add(dList.get(i).get("科目代号").toString());
                                listNAMEs.add(dList.get(i).get("科目名称").toString());
                                listJes.add(dList.get(i).get("本位币金额").toString());
                                int je = Integer.valueOf(dList.get(i).get("本位币金额").toString());
                                sum+=je;
                                Log.e("LiNing", ",,,,,,," + sum);
                                Log.e("LiNing", ",,,,,,," + listNAMEs+",,,,,,," + listIDs);
                                String xhs_str = "";
                                for (String name : listXhs) {
                                    xhs_str += name + ",";
                                }
                                 sub_xh = xhs_str.substring(0,
                                         xhs_str.length() - 1);
                                String ids_str = "";
                                for (String name : listIDs) {
                                    ids_str += name + ",";
                                }
                                 sub_id = ids_str.substring(0,
                                        ids_str.length() - 1);
                                String names_str = "";
                                for (String name : listNAMEs) {
                                    names_str += name + ",";
                                }
                                sub_name = names_str.substring(0,
                                        names_str.length() - 1);
                                String jes_str = "";
                                for (String name : listJes) {
                                    jes_str += name + ",";
                                }
                                sub_je = jes_str.substring(0,
                                        jes_str.length() - 1);

                            }
                            if(sub_id!=null&&!sub_id.equals("")){
                                skd_qtzk.setText(""+sum);
                                alg_qtzk.dismiss();
                                Log.e("LiNing", ",,,tj,,,," + sub_id+",,,,,,," + sub_name+sub_xh+sub_je);
                            }
                        } else {
                            Toast.makeText(context, "请选择查询赋权", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

                alg_qtzk=new AlertDialog.Builder(context).create();
                alg_qtzk.setCancelable(false);
                alg_qtzk.setView(view_qtzk);
                alg_qtzk.show();
                break;
            case R.id.ib_skd_xjzh:

                index_lx = 2;
                if (skd_tv_zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {
                    getTicket_BT();//临时变动代号
                    Intent djlb_intent = new Intent(context, ErpDepInfoActivity.class);
                    djlb_intent.putExtra("DB_ID", skd_tv_zt.getText().toString());
                    djlb_intent.putExtra("fk_lx", "2");
                    djlb_intent.putExtra("flag", "30");
                    startActivityForResult(djlb_intent, 30);
                }
                break;
            case R.id.ib_skd_yh:

                index_lx = 1;
                if (skd_tv_zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {
                    Intent djlb_intent = new Intent(context, ErpDepInfoActivity.class);
                    djlb_intent.putExtra("DB_ID", skd_tv_zt.getText().toString());
                    djlb_intent.putExtra("fk_lx", "1");
                    djlb_intent.putExtra("flag", "30");
                    startActivityForResult(djlb_intent, 30);
                }
                break;


            case R.id.ib_skd_all_account:
                Intent intent = new Intent(context, CondicionInfoActivity.class);
                intent.putExtra("flag", "1");
                startActivityForResult(intent, 1);
                break;
            case R.id.ib_skd_jybm:
                if (skd_tv_zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {
                    Intent bm_intent = new Intent(context, ErpDepInfoActivity.class);
                    bm_intent.putExtra("DB_ID", skd_tv_zt.getText().toString());
                    bm_intent.putExtra("flag", "2");
                    startActivityForResult(bm_intent, 2);
                }
                break;
            case R.id.ib_skd_zdkh:
                if (skd_tv_zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {
                    //调取查询客户的界面
                    Intent intent_cust = new Intent(context, QueryCustersActivity.class);
                    intent_cust.putExtra("ZT_VIP", skd_tv_zt.getText().toString());
                    startActivityForResult(intent_cust, 6);


                }
                break;
            case R.id.ib_skd_zryw:
                if (skd_tv_zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {
                    Intent yw_intent = new Intent(context, ErpDepInfoActivity.class);
                    yw_intent.putExtra("DB_ID", skd_tv_zt.getText().toString());
                    yw_intent.putExtra("flag", "5");
                    startActivityForResult(yw_intent, 5);
                }
                break;
            case R.id.ib_skd_zdwd:
                if (skd_tv_zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {
                    Intent zl_intent = new Intent(context, ErpDepInfoActivity.class);
                    zl_intent.putExtra("DB_ID", skd_tv_zt.getText().toString());
                    zl_intent.putExtra("flag", "3");
                    startActivityForResult(zl_intent, 3);

                }
                break;
            case R.id.btn_yingsmx:
//                get_yys_mx();
                if(!skd_zdwd.getText().toString().equals("")&&!str_id_zdwd.equals("")&&str_id_zdwd!=null
                        &&skd_kh.getText().toString().equals("")){
                    //获取明细
                    kh_bh_tj=str_id_zdwd;
                    get_yys_mx();

                }else  if(!skd_kh.getText().toString().equals("")&&!kh_bh.equals("")&&kh_bh!=null){
                    //获取明细
                    kh_bh_tj=kh_bh;
                    get_yys_mx();
                }
                break;
            case R.id.btn_yusmx:
                if(!skd_kh.getText().toString().equals("")&&!kh_bh.equals("")&&kh_bh!=null){
                    Intent intent_yus = new Intent(context, YuShouActivity.class);
                    intent_yus.putExtra("DB_ID", skd_tv_zt.getText().toString());
                    intent_yus.putExtra("ZD_KH", kh_bh);
                    startActivityForResult(intent_yus, 4);//返回预收数据
                }

                break;
            case R.id.ib_skd_djlb:
                if (skd_tv_zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {
                    Intent djlb_intent = new Intent(context, ErpDepInfoActivity.class);
                    djlb_intent.putExtra("DB_ID", skd_tv_zt.getText().toString());
                    djlb_intent.putExtra("flag", "22");
                    startActivityForResult(djlb_intent, 4);
                }
                break;

            default:
                break;
        }
    }
    private void delMore() {
        // 先将checkedIndexList中的元素从大到小排列,否则可能会出现错位删除或下标溢出的错误
        checkedIndexList = sortCheckedIndexList(checkedIndexList);
        for (int i = 0; i < checkedIndexList.size(); i++) {
            // 需要强转为int,才会删除对应下标的数据,否则默认删除与括号中对象相同的数据
            dList.remove((int) checkedIndexList.get(i));
            checkBoxList.remove(checkedIndexList.get(i));
        }
        for (int i = 0; i < checkBoxList.size(); i++) {
            // 将已选的设置成未选状态
            checkBoxList.get(i).setChecked(false);
            // 将checkbox设置为不可见
//			checkBoxList.get(i).setVisibility(View.INVISIBLE);
        }
        // 更新数据源
        sAdapter.notifyDataSetChanged();
        // 清空checkedIndexList,避免影响下一次删除
        checkedIndexList.clear();
    }

    private List<Integer> sortCheckedIndexList(List<Integer> list) {
        int[] ass = new int[list.size()];// 辅助数组
        for (int i = 0; i < list.size(); i++) {
            ass[i] = list.get(i);
        }
        Arrays.sort(ass);
        list.clear();
        for (int i = ass.length - 1; i >= 0; i--) {
            list.add(ass[i]);
        }
        return list;
    }
    int lastItem;
    private View below;
    private TextView tv;
    private ProgressBar pb;
    private void get_aleartDialog() {
        View view = getLayoutInflater()
                .inflate(R.layout.skd_query, null);
        lv_query_skd= (ListView) view.findViewById(R.id.lv_skd_header_query);
        query_ok_cd= (Button) view.findViewById(R.id.btn_skd_dialogOk);
        query_fh_cd= (ImageButton) view.findViewById(R.id.imageButton1);
        start_quick = (Button) view.findViewById(R.id.btn_start_time);
        stop_quick = (Button) view.findViewById(R.id.btn_stop_time);
        skd_get = (Button) view.findViewById(R.id.search_skd);
        start_quick.setText(date_dd);
        stop_quick.setText(date_dd);
        below = getLayoutInflater().inflate(R.layout.bottom_jz, null);
        tv = (TextView) below.findViewById(R.id.textView);
        pb = (ProgressBar) below.findViewById(R.id.progressBar1);

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


        skd_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_info_comint();//提交查询条件
                get_allSkds();//请求数据
                Log.e("LiNing", "mf_monList提交数据===" + mf_monList);

            }
        });
        lv_query_skd.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE&& Integer.parseInt(clientRows) <=mf_monList_all.size()) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        tv.setVisibility(View.GONE);
                        pb.setVisibility(View.VISIBLE);
                        loadData();
                    }

                }
//                else{
//                    Toast.makeText(context, "没有更多数据啦...", Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastItem = firstVisibleItem + visibleItemCount - 1 ;
                if(lastItem==totalItemCount){
                    Toast.makeText(context,"已经是最后一条数据",Toast.LENGTH_LONG).show();
                }
            }
        });
//        //分页加载
//        lv_query_skd.setOnScrollListener(new AbsListView.OnScrollListener() {
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
        lv_query_skd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReceiptSkdForm.Mf_monList item_skdqty = (ReceiptSkdForm.Mf_monList) parent.getAdapter().getItem(position);
                String skd_fh_rq = item_skdqty.getRp_DD().toString();
                String skd_fh_dh = item_skdqty.getRp_NO().toString();
                String skd_fh_lydh = item_skdqty.getBil_NO().toString();//来源单号
                String skd_fh_bm = item_skdqty.getDep().toString();
                String skd_fh_yw = item_skdqty.getUsr_NO().toString();
                String skd_fh_zy = item_skdqty.getRem().toString();
                String xx_hj = item_skdqty.getAmtn().toString();//本位币金额（小写）


                //日期转换
                Date date = new Date(skd_fh_rq);
                SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd");
                String a1 = dateformat1.format(date);
                skd_time.setText(a1);
                skd_tv_dh.setText(skd_fh_dh);
                skd_bm.setText(skd_fh_bm);
                skd_ywy.setText(skd_fh_yw);
                skd_zy.setText(skd_fh_zy);
                skd_xxje.setText(xx_hj);
                if(skd_xxje.getText().toString().equals("")){

                    BigDecimal numberOfMoney = new BigDecimal(Double.parseDouble(xx_hj));
                    String s = NumberToCN.number2CNMontrayUnit(numberOfMoney);
                    skd_hjje.setText(s);
                }
                skd_lydh.setText(skd_fh_lydh);
                if(item_skdqty.getTf_MON().getAmtn_BC()!=null){
                    String skd_fh_xj = item_skdqty.getTf_MON().getAmtn_BC().toString();//现金收入
                    skd_xjzh.setText(skd_fh_xj);
                }
                if(item_skdqty.getTf_MON().getAmtn_BB()!=null){
                    String skd_fh_yh = item_skdqty.getTf_MON().getAmtn_BB().toString();//银行收入
                    skd_yhzh.setText(skd_fh_yh);
                }

                mf_chk_fh = item_skdqty.getTf_MON().getMf_CHK();

            }
        });
        query_ok_cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //传递数据
                alertDialog.dismiss();
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

    private void loadData() {
//        int count = qtyAdapter.getCount() + 1;
//        for(int i = count; i < count + 50; i++) {  //每次加载50条数据
////        for(int i = 0; i < count/50; i++) {  //每次加载50条数据
//            get_info_comint();//提交查询条件
//            get_allSkds();//请求数据
//        }
        get_info_comint();//提交查询条件
        get_allSkds();//请求数据
    }

    private void get_yys_mx() {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("db_Id",skd_tv_zt.getText().toString())
//                .add("cus_NO",kh_bh_tj)
                .add("cus_NO","KH347")
                .build();
        Log.e("LiNing", "提交数据" + skd_tv_zt.getText().toString()+"----"+kh_bh);
        client.newCall(new Request.Builder().addHeader("cookie", session).post(body)
                .url(url_add_mx).build()).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str_mx = response.body().string();
                Log.e("LiNing", "应收明细数据" + str_mx);
                Gson gson = new GsonBuilder().setDateFormat(
                        "yyyy-MM-dd HH:mm:ss").create();
                SkdYingsMx skdysAllInfos = gson.fromJson(str_mx, SkdYingsMx.class);
                Log.e("LiNing", "id_type结果====" + skdysAllInfos);
                if(skdysAllInfos!=null){

                    mfArpList_yings = skdysAllInfos.getArpResult().getMfArpList();
                    ReceiptFormActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            yingsAdapter = new YingsMxAdapter(R.layout.pskd_head_yings_item1, mfArpList_yings, context);
                            lv_skd_qry_yings.setAdapter(yingsAdapter);
                            yingsAdapter.notifyDataSetChanged();
                        }
                    });

                }
            }
            @Override
            public void onFailure(Call call, IOException e) {

            }


        });
    }


    private void comit_add_infosAll() {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("rp_ID","1")//收付款识别码（1，收款）---Y
//                .add("irp_ID","此处判断冲销明细（）")//是预收付款否
                .add("irp_ID","F")//是预收付款否模拟---Y
                .add("cls_ID","F")//冲销注记---Y
               //主表
                .add("db_Id",add_zt)//账套---Y
                .add("rp_NO",add_rp_NO)//收付款单号---Y
                .add("bil_NO","SO2018052300097")//来源单号---Y
                .add("rp_DD",add_rp_dd)//收付款日期---Y
                .add("dep",str_id_bm)//部门代号---Y,str_id_ywy,
                .add("usr_NO",str_id_ywy)//操作员（业务员）---Y
//                .add("cus_NO",add_cus_NO)//客户，厂商（终端网点）---Y
                .add("cus_NO",str_id_zdwd)//客户，厂商（终端网点）---Y
                .add("bil_TYPE",str_id_djlb)//单据类别---Y
                .add("rem",add_rem)//摘要---Y
                .add("amtn_BC",add_amtn_BC)//现金收付款---Y
                .add("amtn_BB",add_amtn_BB)//银行存付款---Y
//                .add("atmn_Net",add_amtn_Net)//票据金额
//                .add("atmn_qt",add_amtn_qt)//其他金额
                .add("atmn",add_amtn_xxhj)//小写合计
//                .add("amtn","500")//小写合计---Y
                //下拉合计
//                .add("atmn_yshj",add_amtn_hj)//预收金额：预收合计
//                .add("atmn_tqje",add_amtn_hj)//预冲提取：提取金额
                //明细数据（TC_MON）应收
//                .add("arpExc_RTO",sub_yis_yshl)//应收汇率
//                .add("arp_OPN_ID",sub_yis_kzbz)//开帐标志
//                .add("pre_ITM",sub_yis_xh)//初始序号（无操作）
//                .add("tcmonitm2","有几个冲销数据")//序号（操作后）
//                .add("arp_NO",sub_yis_ysdh)//立冲账单号
//                .add("arpAmtn_CLS",sub_yis_bccx)//本次冲销
//                .add("bil_DD",sub_yis_djrq)//单据日期
                .add("arpExc_RTO","1,2")//应收汇率---Y
                .add("arp_OPN_ID","1,2")//开帐标志---Y
                .add("pre_ITM","1,2")//初始序号（无操作）---Y
                .add("tcmonitm2","1,2")//序号（操作后）---Y
                .add("arp_NO","AR7C310155,AR86210003")//立冲账单号---Y
                .add("arpAmtn_CLS","-100,750")//本次冲销---Y
                .add("bil_DD","2017-12-31,2018-6-21")//单据日期---Y


//                .add("cus_name",sub_yis_khmc)//无参数
//                .add("ysph",sub_yis_ysph)//无参数
//                .add("atmn_net",sub_yis_ysje)//应收金额
//                .add("atmn_rcv",sub_yis_ycje)//已冲金额
//                .add("atmn_cske",sub_yis_wcjy)//未冲结余


                //表下数据
//                .add("atmn_cske",add_cske)//冲收款额
//                .add("atmn_cyse",add_cyse)//冲应收取
//                .add("atmn_skje",add_skje)//收款金额
//                .add("atmn_cyuse",add_cyuse)//冲预收额(add_amtn_IRP,add_amtn_CLS)
//                .add("atmn_cske","500")//冲收款额
//                .add("atmn_cyse","500")//冲应收取
//                .add("atmn_skje","500")//收款金额
//                .add("atmn_cyuse","500")//冲预收额(add_amtn_IRP,add_amtn_CLS)
                .add("eff_DD",add_sxrq)//生效日期---Y
                .add("cls_DATE",add_zsrq)//终审日期---Y
//                .add("usr",add_usr)//录入员---Y
                .add("usr",user_yh)//录入员---Y
                .add("chk_MAN",user_yh)//审核人---Y
                //票据金额
//                .add("chk_NO","1")//票据号码
//                .add("amtn_Net","100")//票据金额
//                .add("bank_NO","1")//票据银行
//                .add("rcv_DD","单据日期")//收、付票日
//                .add("cah_DD","后加")//预兑日
//                .add("end_DD","2018-3-28")//到期日
//                .add("trs_DD","当前日期")//变动日
//                .add("chk_KND","支票")//票据种类
                .add("chk_NO","ln2,ln222")//票据号码---Y(先判断是否包含此票据)
                .add("amtn_Net","10,20")//票据金额---Y
                .add("bank_NO","1,2")//票据银行---Y
                .add("rcv_DD","2018-4-25,2018-4-25")//收、付票日---Y
                .add("cah_DD","2018-4-29,2018-4-29")//预兑日---Y
                .add("end_DD","2018-4-29,2018-4-29")//到期日---Y
                .add("trs_DD","2018-3-28,2018-3-28")//变动日---Y
                .add("chk_KND","1,2")//票据种类---Y
                //其他
                .add("mon3itm2",sub_xh)//项次1---Y
                .add("acc_NO",sub_id)//会计科目---Y
                .add("mon3amtn",sub_je)//本位币金额---Y
                //默认值
//                .add("bil_Id",comit_bil_ID)//来源单识别码---Y
                .add("bil_ID","SO")//来源单识别码---Y
//                .add("amtn_ARP","本次冲销合计")//应收付账款
                .add("amtn_ARP","650")//应收付账款模拟---Y
                .add("itm","1")//项次---Y
                .add("record_DD",add_rp_dd)//创建日期---Y
//                .add("sor_ID",comit_sor_ID)//来源单标识---Y
                .add("sor_ID","1")//来源单标识---Y
                .add("exc_RTO","1")//汇率---Y
                .add("bb_NO",id_ls_bddh)//银行账户变动代号---Y
                .add("bc_NO",id_ls_bddh)//现金变动代号---Y
                .add("bacc_NO",fklx_id)//银行账户代号---Y
                .add("cacc_NO",fklx_id_xj)//现金账户代号---Y
//                .add("amtn_IRP","冲应收额")//预收付款（预收本冲合计）
//                .add("amtn_CLS","应收取")//冲销金额（预收本冲合计）
                .add("amtn_IRP","500")//预收付款（预收本冲合计）模拟---Y
                .add("amtn_CLS","650")//冲销金额（预收本冲合计）模拟---Y
                .add("cus_NO_OS",kh_bh)//客户编号---Y
                //预收明细
//                .add("irp_NO","预收明细的单号")//预收付款单号
//                .add("irpitm","itm")//预收付款单号的itm
//                .add("irpAmtn_CLS","itm")//预收付款单号的冲销金额
                .add("irp_NO","RT78050152")//预收付款单号（模拟---Y
                .add("irpitm","1")//预收付款单号的itm）模拟---Y
                .add("irpAmtn_CLS","500")//预收付款单号的冲销金额（模拟---Y

                .build();
//        oa.ydshce.com:8080/InfManagePlatform/PaymentsqueryMf_ARP.action?db_Id=DB_BJ18&&cus_NO=""&&cus_NO_OS=""
        Log.e("LiNing", "提交数据=新增==" + add_rp_NO+add_zt);
        client.newCall(new Request.Builder().addHeader("cookie", session).post(body)
                .url(url_add_skd).build()).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str_add_infos = response.body().string();
                Log.e("LiNing", "提交新增结果==" +str_add_infos);

            }
            @Override
            public void onFailure(Call call, IOException e) {

            }


        });
    }

    private void clear_infos_add() {

//        skd_time.setText("");
//        skd_tv_dh.setText("");
        skd_lydh.setText("");
        skd_djlb.setText("");
        skd_bm.setText("");
        skd_ywy.setText("");
        skd_zdwd.setText("");
        skd_kh.setText("");

        skd_zy.setText("");
        skd_xjzh.setText("");
        skd_yhzh.setText("");
        skd_pjje.setText("");
        skd_qtzk.setText("");
        skd_hjje.setText("");
        skd_xxje.setText("");
        skd_yushoujine.setText("");
        skd_yuchongtiqu.setText("");
        skd_cske.setText("");
        skd_cyingse.setText("");
        skd_skje.setText("");
        skd_cyusee.setText("");
        skd_zdr.setText("");
        skd_shr.setText("");
        skd_time_sx.setText("");
        skd_time_zs.setText("");
    }
    int xj_je_int,yh_je_int,pj_je_int,qt_je_int,i_heje;
    private void add_comit() {

         add_zt = skd_tv_zt.getText().toString();
        if(add_zt.equals("")){
            Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
        }else {
             add_rp_dd = skd_time.getText().toString();
            if(add_rp_dd.equals("")){
                Toast.makeText(context, "请选择日期", Toast.LENGTH_LONG).show();
            }else{
                 add_rp_NO = skd_tv_dh.getText().toString();
                if(add_rp_NO.equals("")){
                    Toast.makeText(context, "请生成收付款单号", Toast.LENGTH_LONG).show();
                }else{
                     add_bil_NO = skd_lydh.getText().toString();
                    if(add_bil_NO.equals("")){
                        add_bil_NO="";
                        Toast.makeText(context, "新增传空", Toast.LENGTH_LONG).show();
                    }
                     add_bil_type = skd_djlb.getText().toString();
                    if(add_bil_type.equals("")){
                        Toast.makeText(context, "请选择单据类别", Toast.LENGTH_LONG).show();
                    }
                     add_dep = skd_bm.getText().toString();
                    if(add_dep.equals("")){
                        Toast.makeText(context, "请选择部门", Toast.LENGTH_LONG).show();
                    }
                     add_usr_NO = skd_ywy.getText().toString();
                    if(add_usr_NO.equals("")){
                        Toast.makeText(context, "请选择业务员", Toast.LENGTH_LONG).show();
                    }
                     add_zdwd = skd_zdwd.getText().toString();
                    if(add_zdwd.equals("")){
                        Toast.makeText(context, "请选择终端网点", Toast.LENGTH_LONG).show();
                    }
                     add_cus_NO = skd_kh.getText().toString();
                    if(add_cus_NO.equals("")){
                        Toast.makeText(context, "请选择客户", Toast.LENGTH_LONG).show();
                    }
                     add_rem = skd_zy.getText().toString();
                    if(add_rem.equals("")){
                        Toast.makeText(context, "请选择摘要", Toast.LENGTH_LONG).show();
                    }

                     add_amtn_BC = skd_xjzh.getText().toString();
                    if(add_amtn_BC.equals("")){
                        Toast.makeText(context, "请选择现金金额", Toast.LENGTH_LONG).show();
                    }else{

                         xj_je_int = Integer.valueOf(add_amtn_BC);
                    }
                    add_amtn_BB = skd_yhzh.getText().toString();
                    if(add_amtn_BB.equals("")){
                        Toast.makeText(context, "请选择银行金额", Toast.LENGTH_LONG).show();
                    }else{

                         yh_je_int = Integer.valueOf(add_amtn_BB);
                    }
                     add_amtn_Net = skd_pjje.getText().toString();
                    if(add_amtn_Net.equals("")){
                        Toast.makeText(context, "请选择票据金额", Toast.LENGTH_LONG).show();
                    }else {

                         pj_je_int = Integer.valueOf(add_amtn_Net);
                    }
                    //其他金额
                     add_amtn_qt = skd_qtzk.getText().toString();
                    if(add_amtn_qt.equals("")){
                        Toast.makeText(context, "请选择其他金额", Toast.LENGTH_LONG).show();
                    }else {

                         qt_je_int = Integer.valueOf(add_amtn_qt);
                    }
                     i_heje = xj_je_int + yh_je_int + pj_je_int + qt_je_int;
                    //合计金额
                     add_amtn_hj = skd_hjje.getText().toString();
                    if(add_amtn_hj.equals("")){
                        Toast.makeText(context, "请选择合计金额", Toast.LENGTH_LONG).show();
                    }
                    //小写合计金额
                     add_amtn_xxhj = skd_xxje.getText().toString();
                    if(add_amtn_xxhj.equals("")){
                        Toast.makeText(context, "请选择小写合计金额", Toast.LENGTH_LONG).show();
                        add_amtn_xxhj=""+i_heje;
                    }
                    else{
                        add_amtn_xxhj=""+i_heje;
                        Log.e("LiNing","合计金额===="+i_heje);
                    }
                    //预收金额
                     add_amtn_IRP = skd_yushoujine.getText().toString();
                    if(add_amtn_IRP.equals("")){
                        Toast.makeText(context, "请选择预收金额", Toast.LENGTH_LONG).show();
                    }
                    //预冲提取
                     add_amtn_CLS = skd_yuchongtiqu.getText().toString();
                    if(add_amtn_CLS.equals("")){
                        Toast.makeText(context, "请选择预冲提取", Toast.LENGTH_LONG).show();
                    }

                    //冲收款额
                     add_cske = skd_cske.getText().toString();
                    if(add_cske.equals("")){
                        Toast.makeText(context, "请选择冲收款额", Toast.LENGTH_LONG).show();
                    }
                    //冲应收额
                     add_cyse = skd_cyingse.getText().toString();
                    if(add_cyse.equals("")){
                        Toast.makeText(context, "请选择预冲应收额", Toast.LENGTH_LONG).show();
                    }
                    //收款金额
                     add_skje = skd_skje.getText().toString();
                    if(add_skje.equals("")){
                        Toast.makeText(context, "请选择预收款金额", Toast.LENGTH_LONG).show();
                    }
                    //冲预收额
                     add_cyuse = skd_cyusee.getText().toString();
                    if(add_cyuse.equals("")){
                        Toast.makeText(context, "请选择冲预收额", Toast.LENGTH_LONG).show();
                    }
                    //制单人
                     add_usr = skd_zdr.getText().toString();
                    if(add_usr.equals("")){
                        Toast.makeText(context, "请选择制单人", Toast.LENGTH_LONG).show();
                    }
                    //生效日期
                     add_sxrq = skd_time_sx.getText().toString();
                    if(add_sxrq.equals("")){
                        Toast.makeText(context, "请选择生效日期", Toast.LENGTH_LONG).show();
                    }
                    //审核人skd_time_zs
                     add_shr = skd_shr.getText().toString();
                    if(add_shr.equals("")){
                        Toast.makeText(context, "请选择审核人", Toast.LENGTH_LONG).show();
                    }
                    //终审日期
                     add_zsrq = skd_time_zs.getText().toString();
                    if(add_zsrq.equals("")){
                        Toast.makeText(context, "终审日期", Toast.LENGTH_LONG).show();
                    }
//                    表格数据
                    getList_no();//获取list数据，数组格式提交


                }
            }
        }
    }

    private void getList_no() {
        if(lv_skd_qry_yings.getCount()>0){
            ArrayList<String> YIS_XH = new ArrayList<String>();
            ArrayList<String> YIS_KHBH = new ArrayList<String>();
            ArrayList<String> YIS_KHMC = new ArrayList<String>();
            ArrayList<String> YIS_YSPH = new ArrayList<String>();
            ArrayList<String> YIS_YSDH = new ArrayList<String>();
            ArrayList<String> YIS_DJRQ = new ArrayList<String>();
            ArrayList<String> YIS_YSJE = new ArrayList<String>();
            ArrayList<String> YIS_YCJE = new ArrayList<String>();
            ArrayList<String> YIS_BCCX = new ArrayList<String>();
            ArrayList<String> YIS_WCJY = new ArrayList<String>();
            ArrayList<String> YIS_YSHL= new ArrayList<String>();
            ArrayList<String> YIS_KZBZ = new ArrayList<String>();
            for (int i = 0; i < lv_skd_qry_yings.getCount(); i++) {
                SkdYingsMx.MfArpList yis_infos = (SkdYingsMx.MfArpList) lv_skd_qry_yings.getAdapter().getItem(i);
                Log.e("LiNing", "-mxInfo---xh--prdt2" + yis_infos.getYis_khmc()+"---"+yis_infos.getYis_wcjy());
                String xh_tj = "" + i;
                String khbh_tj = yis_infos.getCus_NO().toString();
                if(yis_infos.getYis_khmc()!=null&&yis_infos.getYis_khmc().equals("")&&yis_infos.getYis_khmc().equals("null")){

                    String khmc_tj = yis_infos.getYis_khmc().toString();
                }
                if(yis_infos.getYis_ysph()!=null&&yis_infos.getYis_ysph().equals("")&&yis_infos.getYis_ysph().equals("null")){

                    String ysph_tj = yis_infos.getYis_ysph().toString();//应收票号
                }
                String ysdh_tj = yis_infos.getArp_NO().toString();//应收单号
                String djqr_tj = yis_infos.getBil_DD().toString();
                String ysje_tj = yis_infos.getAmtn_NET().toString();
                String ycje_tj = yis_infos.getAmtn_RCV().toString();
                if(yis_infos.getYis_bccx()!=null&&yis_infos.getYis_bccx().equals("")&&yis_infos.getYis_bccx().equals("null")){

                    String bccx_tj = yis_infos.getYis_bccx().toString();
                }
                if(yis_infos.getYis_wcjy()!=null&&yis_infos.getYis_wcjy().equals("")&&yis_infos.getYis_wcjy().equals("null")){

                    String wcjy_tj = yis_infos.getYis_wcjy().toString();
                }
                if(yis_infos.getYis_yshl()!=null&&yis_infos.getYis_yshl().equals("")&&yis_infos.getYis_yshl().equals("null")){

                    String yshl_tj = yis_infos.getYis_yshl().toString();
                }
                if(yis_infos.getYis_kzbz()!=null&&yis_infos.getYis_kzbz().equals("")&&yis_infos.getYis_kzbz().equals("null")){

                    String kzbz_tj = yis_infos.getYis_kzbz().toString();
                }

                YIS_XH.add(xh_tj);
                YIS_KHBH.add(khbh_tj);
                YIS_KHMC.add("客户名称");
                YIS_YSPH.add("001");
                YIS_YSDH.add(ysdh_tj);
                YIS_DJRQ.add(djqr_tj);
                YIS_YSJE.add(ysje_tj);
                YIS_YCJE.add(ycje_tj);
                YIS_BCCX.add("0.00");
                YIS_WCJY.add("0.00");
                YIS_YSHL.add("0.00");
                YIS_KZBZ.add("0.00");


                //开始拼接
                String yis_xhs_str = "";
                for (String str : YIS_XH) {
                    yis_xhs_str += str + ",";
                }
                sub_yis_xh = yis_xhs_str.substring(0, yis_xhs_str.length() - 1);
                Log.e("LiNing", "------新数据" + sub_yis_xh);
                String yis_khbhs_str = "";
                for (String str : YIS_KHBH) {
                    yis_khbhs_str += str + ",";
                }
                sub_yis_khbh = yis_khbhs_str.substring(0, yis_khbhs_str.length() - 1);
                Log.e("LiNing", "------新数据" + sub_yis_khbh);
                String yis_khmcs_str = "";
                for (String str : YIS_KHMC) {
                    yis_khmcs_str += str + ",";
                }
                sub_yis_khmc = yis_khmcs_str.substring(0, yis_khmcs_str.length() - 1);
                Log.e("LiNing", "------新数据" + sub_yis_khmc);
                String yis_ysphs_str = "";
                for (String str : YIS_YSPH) {
                    yis_ysphs_str += str + ",";
                }
                sub_yis_ysph = yis_ysphs_str.substring(0, yis_ysphs_str.length() - 1);
                Log.e("LiNing", "------新数据" + sub_yis_ysph);
                String yis_ysdhs_str = "";
                for (String str : YIS_YSDH) {
                    yis_ysdhs_str += str + ",";
                }
                sub_yis_ysdh = yis_ysdhs_str.substring(0, yis_ysdhs_str.length() - 1);
                Log.e("LiNing", "------新数据" + sub_yis_ysdh);
                String yis_djrqs_str = "";
                for (String str : YIS_DJRQ) {
                    yis_djrqs_str += str + ",";
                }
                sub_yis_djrq = yis_djrqs_str.substring(0, yis_djrqs_str.length() - 1);
                Log.e("LiNing", "------新数据" + sub_yis_djrq);
                String yis_ysjes_str = "";
                for (String str : YIS_YSJE) {
                    yis_ysjes_str += str + ";";//日期以;号隔开
                }
                sub_yis_ysje = yis_ysjes_str.substring(0, yis_ysjes_str.length() - 1);
                Log.e("LiNing", "------新数据" + sub_yis_ysje);
                String yis_ycjes_str = "";
                for (String str : YIS_YCJE) {
                    yis_ycjes_str += str + ",";
                }
                sub_yis_ycje = yis_ycjes_str.substring(0, yis_ycjes_str.length() - 1);
                Log.e("LiNing", "------新数据" + sub_yis_ycje);
                String yis_bccxs_str = "";
                for (String str : YIS_BCCX) {
                    yis_bccxs_str += str + ",";
                }
                sub_yis_bccx = yis_bccxs_str.substring(0, yis_bccxs_str.length() - 1);
                Log.e("LiNing", "------新数据" + sub_yis_bccx);
                String yis_wcjys_str = "";
                for (String str : YIS_WCJY) {
                    yis_wcjys_str += str + ",";
                }
                sub_yis_wcjy = yis_wcjys_str.substring(0, yis_wcjys_str.length() - 1);
                Log.e("LiNing", "------新数据" + sub_yis_wcjy);
                String yis_yshls_str = "";
                for (String str : YIS_YSHL) {
                    yis_yshls_str += str + ",";
                }
                sub_yis_yshl = yis_yshls_str.substring(0, yis_yshls_str.length() - 1);
                Log.e("LiNing", "------新数据" + sub_yis_yshl);
                String yis_kzbzs_str = "";
                for (String str : YIS_KZBZ) {
                    yis_kzbzs_str += str + ",";
                }
                sub_yis_kzbz = yis_kzbzs_str.substring(0, yis_kzbzs_str.length() - 1);
                Log.e("LiNing", "------新数据" + sub_yis_kzbz);

            }
        }
    }

    private AlertDialog alertDialog;
    private SkdsQtyAdapter qtyAdapter;
    private YingsMxAdapter yingsAdapter;
    private void get_allSkds() {
        //    http://oa.ydshce.com:8080/InfManagePlatform/PaymentsqueryPayment.action?db_Id=DB_BJ18&&showRow=300&&clientRows=0&&rp_ID=1&&rp_NO=RT201805230051
//    &&rp_DD=2018-05-23&&usr_NO=L022&&dep=2023&&rem=孙黎明&&bil_NO=SO201805230097&&irp_ID=T&&CLS_ID=T&&cus_NO=KH031&&bil_TYPE=06&&usr=Z002&&chk_MAN=Z002
//    &&cus_NO_OS=123
        Log.e("LiNing", "提交数据===" + comit_zt+comit_time+clientRows);
        if(lv_query_skd.getCount()<=0){
            clientRows="0";
        }
        Log.e("LiNing", "提交数据===" + clientRows);
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("db_Id",comit_zt)
                .add("showRow","50")
                .add("clientRows",clientRows)
//                .add("clientRows","0")
                .add("rp_ID","1")
                .add("irp_ID","T")
                .add("CLS_ID","T")
                .add("rp_NO",comit_dh)
                .add("bil_NO",comit_lydh)
                .add("rp_DD",comit_time)
                .add("usr_NO",comit_czy)
//                .add("dep",comit_dep)//有默认值
                .add("cus_NO",kh_bh)
                .add("bil_TYPE",comit_djlb)
                .add("usr",comit_zdr)
                .add("chk_MAN",comit_shr)
                .add("rem",comit_zy)
                .build();
        Log.e("LiNing", "提交数据=1==" + comit_zt+comit_time+clientRows);
        client.newCall(new Request.Builder().addHeader("cookie", session).post(body)
                .url(url_query_skd).build()).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e("LiNing", "所有收款单===new" + str);
                if(str!=null&&!str.equals("")&&!str.equals("null")) {

                    // 解析包含date的数据必须添加此代码(InputStream型)
                    Gson gson = new GsonBuilder().setDateFormat(
                            "yyyy-MM-dd HH:mm:ss").create();
                    final ReceiptSkdForm skd_all = gson.fromJson(str,
                            ReceiptSkdForm.class);
                    int sumShowRow = skd_all.getSumShowRow();
                    s_zh = String.valueOf(sumShowRow);
                    clientRows = s_zh;
                    if (skd_all != null) {
                        ReceiptFormActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                mf_monList = skd_all.getMf_monList();

                                if (mf_monList != null && mf_monList.size() > 0) {

                                    mf_monList_all.addAll(mf_monList);
                                    qtyAdapter = new SkdsQtyAdapter(R.layout.skd_had, mf_monList_all, context);
//                                    qtyAdapter.bindData(mf_monList_all);
//                                    lv_query_skd.addFooterView(below);
//                                    if (clientRows.equals("50")) {
//                                        lv_query_skd.setAdapter(qtyAdapter);
//                                    }
//                                    qtyAdapter.notifyDataSetChanged();

//                                    qtyAdapter = new SkdsQtyAdapter(R.layout.skd_had, mf_monList, context);
                                    lv_query_skd.addFooterView(below);
                                    lv_query_skd.setAdapter(qtyAdapter);
                                    qtyAdapter.notifyDataSetChanged();

                                }
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
    MyHScrollView headSrcrollView;
    private Date date;
    private String url_prdNo = URLS.prdNo_url;//通过id获取名称
    public class YingsMxAdapter extends BaseAdapter {

        int id_row_layout;
        LayoutInflater mInflater;
        List<SkdYingsMx.MfArpList> skdysmx_infos;
        double v_ysje,v_ycje;
        List<DepInfo.IdNameList> depInfo;
        String name;
        boolean ischeck = false;


        public YingsMxAdapter(int pskd_head_yings_item1, List<SkdYingsMx.MfArpList> mfArpList_yings, Context context) {
            this.id_row_layout=pskd_head_yings_item1;
            this.skdysmx_infos=mfArpList_yings;
            this.mInflater = LayoutInflater.from(context);
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
            return skdysmx_infos.size();
        }

        @Override
        public Object getItem(int position) {
            return skdysmx_infos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder_ysmx;
            if(convertView==null){
                synchronized (ReceiptFormActivity.this) {


                convertView=mInflater.inflate(id_row_layout,null);
                holder_ysmx=new ViewHolder();
                    MyHScrollView scrollView1 = (MyHScrollView) convertView
                            .findViewById(R.id.horizontalScrollView1);
                    holder_ysmx.scrollView = scrollView1;
                holder_ysmx.yingsmx_xh= (TextView) convertView.findViewById(R.id.skdmx_yings_xh_item);
                holder_ysmx.yingsmx_khbh= (TextView) convertView.findViewById(R.id.skdmx_yings_khbh_item);
                holder_ysmx.yingsmx_khmc= (TextView) convertView.findViewById(R.id.skdmx_yings_khmc_item);
                holder_ysmx.yingsmx_ysph= (TextView) convertView.findViewById(R.id.tv_skdmx_yingsph_item);
                holder_ysmx.yingsmx_ysdh= (TextView) convertView.findViewById(R.id.tv_skdmx_yingsdh_item);
                holder_ysmx.yingsmx_djrq= (TextView) convertView.findViewById(R.id.tv_skdmx_djrq_item);
                holder_ysmx.yingsmx_ysje= (TextView) convertView.findViewById(R.id.tv_skdmx_yingsje_item);
                holder_ysmx.yingsmx_ycje= (TextView) convertView.findViewById(R.id.tv_skdmx_yingsycje_item);
                holder_ysmx.yingsmx_bccx= (EditText) convertView.findViewById(R.id.tv_skdmx_yingsbccx_item);
                holder_ysmx.yingsmx_wcjy= (TextView) convertView.findViewById(R.id.tv_skdmx_yingswcjy_item);
                holder_ysmx.yingsmx_bz= (TextView) convertView.findViewById(R.id.tv_skdmx_yingsbz_item);
                    headSrcrollView = (MyHScrollView) mHead_yings
                            .findViewById(R.id.horizontalScrollView1);
                    headSrcrollView
                            .AddOnScrollChangedListener(new OnScrollChangedListenerImp(
                                    scrollView1));
                    convertView.setTag(holder_ysmx);
            }
            }else{
                holder_ysmx= (ViewHolder) convertView.getTag();
            }
            SkdYingsMx.MfArpList mfArpList = skdysmx_infos.get(position);
            //编辑EditextSet(******放于此处避免listview数据错乱)
            EditextSet_add(holder_ysmx, mfArpList);
            int id_add = position + 1;
            holder_ysmx.yingsmx_xh.setText(""+id_add);
            holder_ysmx.yingsmx_khbh.setText(mfArpList.getCus_NO());
            holder_ysmx.yingsmx_bz.setText(mfArpList.getRem());
            //客户名称（id转化）
            //此处请求接口获取名称
            OkHttpClient client = new OkHttpClient();
            Log.e("LiNing","-----"+mfArpList.getCus_NO().toString());
            FormBody body = new FormBody.Builder().add("accountNo", "DB_BJ18")
                    .add("id", skdysmx_infos.get(position).getCus_NO()).build();
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
                        ReceiptFormActivity.this
                                .runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        depInfo = dInfo.getIdNameList();
                                        if(depInfo!=null&&depInfo.size()>0){
                                            for(int i=0;i<depInfo.size();i++){
                                                name = depInfo.get(i).getName();
                                                holder_ysmx.yingsmx_khmc.setText(name);
                                                skdysmx_infos.get(i).setYis_khmc(name);
                                                Log.e("LiNing","数据name"+skdysmx_infos.get(position).getYis_khmc());
                                            }
                                        }
                                        skdysmx_infos.get(position).setYis_khmc(name);
//                                        holder.price_name.setText(infos.get(position).getNAME_ZDY());
                                        Log.e("LiNing","数据name---"+skdysmx_infos.get(position).getYis_khmc());
                                    }

                                });
                    }
                }
                @Override
                public void onFailure(Call call, IOException e) {

                }


            });
            holder_ysmx.yingsmx_ysph.setText("应收票号");
            holder_ysmx.yingsmx_ysdh.setText(mfArpList.getArp_NO());
            SimpleDateFormat sf1 = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
            try {
                date = sf1.parse(mfArpList.getBil_DD().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
            String format_data = sf2.format(date);
            holder_ysmx.yingsmx_djrq.setText(format_data);
            if(mfArpList.getAmtn_NET().toString()!=null){

                 v_ysje = new BigDecimal(mfArpList.getAmtn_NET()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                holder_ysmx.yingsmx_ysje.setText("" + v_ysje);
            }
            if(mfArpList.getAmtn_RCV().toString()!=null){

                 v_ycje = new BigDecimal(mfArpList.getAmtn_RCV()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                holder_ysmx.yingsmx_ycje.setText("" +v_ycje );
            }
            double v_wcjy = v_ysje - v_ysje;
            skdysmx_infos.get(position).setYis_wcjy(""+v_wcjy);
//            holder_ysmx.yingsmx_wcjy.setText("" +v_wcjy );
            holder_ysmx.yingsmx_wcjy.setText(skdysmx_infos.get(position).getYis_wcjy());
            skdysmx_infos.get(position).setYis_ysph("应收票号");
            holder_ysmx.yingsmx_ysph.setText( skdysmx_infos.get(position).getYis_ysph());
            return convertView;
        }

        private void EditextSet_add(ViewHolder holder_ysmx, final SkdYingsMx.MfArpList mfArpList) {
            if (holder_ysmx.yingsmx_bccx.getTag() instanceof TextWatcher) {
                holder_ysmx.yingsmx_bccx.removeTextChangedListener((TextWatcher) holder_ysmx.yingsmx_bccx.getTag());
            }


            TextWatcher watcher_bccx = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    //editext改变，更新textview的数据
                    if (TextUtils.isEmpty(s)) {
                        mfArpList.setYis_bccx("");
                    } else {
                        mfArpList.setYis_bccx(s.toString());
                    }
                }
            };
            holder_ysmx.yingsmx_bccx.addTextChangedListener(watcher_bccx);
            holder_ysmx.yingsmx_bccx.setTag(watcher_bccx);

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
            HorizontalScrollView scrollView;
            public TextView yingsmx_xh;
            public TextView yingsmx_khbh;
            public TextView yingsmx_khmc;
            public TextView yingsmx_ysph;
            public TextView yingsmx_ysdh;
            public TextView yingsmx_djrq;
            public TextView yingsmx_ysje;
            public TextView yingsmx_ycje;
            public EditText yingsmx_bccx;
            public TextView yingsmx_wcjy;
            public TextView yingsmx_bz;
        }
    }
    public class SkdsQtyAdapter extends BaseAdapter {
        int id_row_layout;
        LayoutInflater mInflater;
        List<ReceiptSkdForm.Mf_monList> skdqty_infos;
        //item高亮显示
        private int selectItem = -1;

        public void setSelectItem(int selectItem) {
            this.selectItem = selectItem;
        }
        public SkdsQtyAdapter(int skd_had, List<ReceiptSkdForm.Mf_monList> mf_monList, Context context) {
            this.id_row_layout = skd_had;
            this.skdqty_infos = mf_monList;
            this.mInflater = LayoutInflater.from(context);
        }
        public void bindData( List<ReceiptSkdForm.Mf_monList> mf_monList) {
            this.skdqty_infos = mf_monList;
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
            if(convertView==null){
                convertView=mInflater.inflate(id_row_layout,null);
                holder=new ViewHolder();
                holder.skd_qty_dh= (TextView) convertView.findViewById(R.id.skd_sfkdh);
                holder.skd_qty_rq= (TextView) convertView.findViewById(R.id.textView2_skd_rq);
                holder.skd_qty_lrr= (TextView) convertView.findViewById(R.id.textView3_skd_lrr);
                holder.skd_qty_shr= (TextView) convertView.findViewById(R.id.textView4_skd_shr);
                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            ReceiptSkdForm.Mf_monList mf_monList_all = skdqty_infos.get(position);
            holder.skd_qty_dh.setText(mf_monList_all.getRp_NO().toString());
            Log.e("LiNing", "时间====xin=====" + mf_monList_all.getRp_DD().toString());

            Date date = new Date(mf_monList_all.getRp_DD().toString());
            SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd");
            String a1 = dateformat1.format(date);
            holder.skd_qty_rq.setText(a1);

            if(mf_monList_all.getTf_MON()!=null){
                holder.skd_qty_lrr.setText(mf_monList_all.getTf_MON().getUsr().toString());
                holder.skd_qty_shr.setText(mf_monList_all.getTf_MON().getChk_MAN().toString());
            }

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

            public TextView skd_qty_dh;
            public TextView skd_qty_rq;
            public TextView skd_qty_lrr;
            public TextView skd_qty_shr;
        }
    }
    private void get_info_comint() {
        Log.e("LiNing","初始化值===="+clientRows);
        if(lv_query_skd.getCount()<0){

            clientRows="0";
        }

         comit_zt = skd_tv_zt.getText().toString();
        if (comit_zt.equals("")) {
            Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
        }
         comit_time_zb = skd_time.getText().toString();
        if (comit_time_zb.equals("")) {
            comit_time_zb = "null";
        }

        if (!start_quick.getText().toString().equals("")&&!stop_quick.getText().toString().equals("")) {
            comit_time = start_quick.getText().toString()+";"+stop_quick.getText().toString();
        }else{
            comit_time = "null";

        }
         comit_dh = skd_tv_dh.getText().toString();
        if (comit_dh.equals("")||comit_dh==null) {
            comit_dh = "null";
        }
         comit_dep = skd_bm.getText().toString();
        if (comit_dep.equals("")) {
            comit_dep = "null";
        }
         comit_czy = skd_ywy.getText().toString();
        if (comit_czy.equals("")) {
            comit_czy = "null";
        }
         comit_zdwd = skd_zdwd.getText().toString();
        if (comit_zdwd.equals("")) {
            comit_zdwd = "null";
        }
         comit_zdkh = skd_kh.getText().toString();
        if (comit_zdkh.equals("")) {
            comit_zdkh = "null";
        }
         comit_djlb = skd_djlb.getText().toString();
        if (comit_djlb.equals("")) {
            comit_djlb = "null";
        }
         comit_zdr = skd_zdr.getText().toString();
        if (comit_zdr.equals("")) {
            comit_zdr = "null";
        }
         comit_shr = skd_shr.getText().toString();
        if (comit_shr.equals("")) {
            comit_shr = "null";

        }
         comit_lydh = skd_lydh.getText().toString();
        if (comit_lydh.equals("")||comit_lydh==null) {
            comit_lydh = "null";
            comit_bil_ID="null";
            comit_sor_ID="2";
        }else{
            String substring_bil = comit_lydh.substring(0, 2);
            comit_bil_ID=substring_bil;
            comit_sor_ID="1";
        }
         comit_zy = skd_zy.getText().toString();
        if (comit_zy.equals("")) {
            comit_zy = "null";
        }
        if (skd_kh.getText().toString().equals("")) {
            kh_bh = "null";
        }
        Log.e("LiNing", "获取数据===" + comit_dh+comit_lydh);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    String str_id = data.getStringExtra("condition_db");
                    String str_name = data.getStringExtra("condition_name");
                    skd_tv_zt.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + str_name);
                }
                break;
            case 2:
                if (resultCode == 1) {
                    String str_name = data.getStringExtra("data_dep");
                     str_id_bm = data.getStringExtra("data_dep_id");
                    skd_bm.setText(str_name);
                    Log.e("LiNing", "提交的id====" + str_id_bm + str_name);
                }
                break;
            case 3:
                if (resultCode == 1) {
                    String str_name = data.getStringExtra("data_dep");
                     str_id_zdwd = data.getStringExtra("data_dep_id");
                    skd_zdwd.setText(str_name);
                    Log.e("LiNing", "提交的id====" + str_id_zdwd + str_name);
                }
                break;
            case 4:
                if (resultCode == 1) {
                    String str_name = data.getStringExtra("data_dep");
                     str_id_djlb = data.getStringExtra("data_dep_id");
                    skd_djlb.setText(str_name);
                    Log.e("LiNing", "提交的id====" + str_id_djlb + str_name);
                }
                break;
            case 5:
                if (resultCode == 1) {
                    String str_name = data.getStringExtra("data_dep");
                     str_id_ywy = data.getStringExtra("data_dep_id");
                    skd_ywy.setText(str_name);
                    Log.e("LiNing", "提交的id====" + str_id_ywy + str_name);
                }
                break;
            case 6:
                if (resultCode == 1) {
                    cust_callback = (CustAllObjectInfos.CustList) data.getSerializableExtra("CUST_QTY");
                    if (cust_callback.getCust_Name() != null) {
                        skd_kh.setText(cust_callback.getCust_Name());
                         kh_bh = cust_callback.getCust_No().toString();
                    } else {
                        skd_kh.setText("");
                    }

                }
                break;
            case 9:
                if (resultCode == 1) {
                     jf_id_hd = data.getStringExtra("jf_ID");
                    String vip_name_hd = data.getStringExtra("jf_NAME");
                    kb_qtzk.setText(vip_name_hd);
//                    jflx_comit_jl = jf_id_hd;
                    Log.e("LiNing", "提交的id====" + jf_id_hd + vip_name_hd);
                }
                break;
            case 30:
                if (resultCode == 1) {
                    String str_name = data.getStringExtra("data_dep");
                    String str_id = data.getStringExtra("data_dep_id");
                    if(index_lx==2){
                        fklx_id_xj=str_id;
                        Log.e("LiNing", "提交的id====" + fklx_id + str_name);
                    }
                    if(index_lx==1){
                        fklx_id=str_id;
                        Log.e("LiNing", "提交的id====" + fklx_id + str_name);
                    }
                }
                break;
                default:
                    break;
        }
    }

    //    class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {
//        ListViewAndHeadViewTouchLinstener() {
//        }
//
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//            ((HorizontalScrollView) ReceiptFormActivity.this.mHead_yus
//                    .findViewById(R.id.horizontalScrollView1))
//                    .onTouchEvent(event);
//            return false;
//        }
//
//    }
    class ListViewAndHeadViewTouchLinstener_yings implements View.OnTouchListener {
        ListViewAndHeadViewTouchLinstener_yings() {
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ((HorizontalScrollView) ReceiptFormActivity.this.mHead_yings
                    .findViewById(R.id.horizontalScrollView1))
                    .onTouchEvent(event);
            return false;
        }

    }

    private void getTicket() {
        String DB_LS = skd_tv_zt.getText().toString();
        date_dd_dh = skd_time.getText().toString();
        if (DB_LS.equals("")) {
            Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
        } else if (date_dd.equals("")) {
            Toast.makeText(context, "请获取时间", Toast.LENGTH_LONG).show();
        } else {
            OkHttpClient client = new OkHttpClient();
            FormBody localFormBody = new FormBody.Builder()
                    .add("bn_Type", "RT")
                    .add("db_Id", DB_LS)
                    .add("bn_Date", date_dd)
                    .build();
            Request localRequest = new Request.Builder()
                    .addHeader("cookie", session).url(url_dh_skd)
                    .post(localFormBody)
                    .build();
            Log.e("LiNing", "临时单号===" + session + "---" + URLS.price_num_ls + "---" + DB_LS + "----" + date_dd);
            client.newCall(localRequest).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.code() == 200) {

                        String str = response.body().string();
                        Log.e("LiNing", "临时档案编号===" + str);
                        final PriceNumID bean_id = (PriceNumID) new Gson()
                                .fromJson(str, PriceNumID.class);
                        if (bean_id != null) {
                            ReceiptFormActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    String id_ls = bean_id.getBil_no();
                                    skd_tv_dh.setText(id_ls);
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
    private void getTicket_BT() {
        String DB_LS = skd_tv_zt.getText().toString();
        date_dd_dh = skd_time.getText().toString();
        if (DB_LS.equals("")) {
            Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
        } else if (date_dd.equals("")) {
            Toast.makeText(context, "请获取时间", Toast.LENGTH_LONG).show();
        } else {
            OkHttpClient client = new OkHttpClient();
            FormBody localFormBody = new FormBody.Builder()
                    .add("bn_Type", "BT")
                    .add("db_Id", DB_LS)
                    .add("bn_Date", date_dd)
                    .build();
            Request localRequest = new Request.Builder()
                    .addHeader("cookie", session).url(url_dh_skd)
                    .post(localFormBody)
                    .build();
            Log.e("LiNing", "临时单号===" + session + "---" + URLS.price_num_ls + "---" + DB_LS + "----" + date_dd);
            client.newCall(localRequest).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.code() == 200) {

                        String str = response.body().string();
                        Log.e("LiNing", "临时档案编号===" + str);
                        final PriceNumID bean_id = (PriceNumID) new Gson()
                                .fromJson(str, PriceNumID.class);
                        if (bean_id != null) {
                            ReceiptFormActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                     id_ls_bddh = bean_id.getBil_no();
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
