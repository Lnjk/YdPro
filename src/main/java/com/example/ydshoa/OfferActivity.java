package com.example.ydshoa;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
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
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.LeftOrRight.MyHScrollView;
import com.example.bean.BackApplication;
import com.example.bean.CustAllObjectInfos;
import com.example.bean.JsonRootBean;
import com.example.bean.NetUtil;
import com.example.bean.PriceNumID;
import com.example.bean.Quotation;
import com.example.bean.URLS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OfferActivity extends Activity implements View.OnClickListener {
    private Context context;
    private SharedPreferences sp;
    private String session;
    String date_dd, date_dd_dh, db_zt, erp_yh, qty_cust_zt, qty_cust_dabh;
    LinearLayout touch;
    String url_dh_bjxx = URLS.price_num_ls;
    private TextView head, khxm, khdh, lplx, zxgs, jhfs, xsdd, shr, shdh, ss, qx, xxdz, bj_time, bjd_tv_bjdh, bjd_tv_zt, khly, tjmj, djlb, xszd, xsbm, xsgw,
            fwgj, ztsj, styw, qdyw, yshk, dyzt, tv_shenher, tv_zdr;
    private EditText xxbz, htdh, gwdh, gjdh, sjdh;
    private Button offer_query, offer_add, offer_set, offer_del, offer_prite, offer_save, offer_qd, offer_out, offer_tszc, offer_bjmx;
    private ImageButton ib_zt_xl, ib_djlb_xl, ib_ztsj_xl, ib_xszd_xl, ib_xsbm_xl, ib_xsgw_xl, ib_fwgj_xl, ib_styw_xl, ib_qdyw_xl, ib_yshk_xl, ib_kh_xl, ib_jhfs_xl, ib_shr_xl;
    BjdQtyAdapter bjdQtyAdapter;
    private ListView lv_offer_qry;
    RelativeLayout mHead;
    AlertDialog addBjmx_all;
    //报价单明细
    private TextView add_mx_xh, add_mx_ph, add_mx_sh, sh_time, qd_time;
    private EditText add_mx_kw, add_mx_dw, add_mx_sl, add_mx_dj, add_mx_zk, add_mx_je, add_mx_jgdh, add_mx_zcdj, add_mx_tybj, add_mx_zdsj,
            add_mx_bzcb, add_mx_ygml, add_mx_mll,
            add_mx_sdsl, add_mx_scsl, add_mx_spgg, add_mx_bzdw, add_mx_bzhs, add_mx_bzsl, add_mx_bzzl, add_mx_spzl, add_mx_bzdx, add_mx_zy;
    private String add_mx_xh_save, add_mx_ph_save, add_mx_sh_save, add_mx_kw_save, add_mx_dw_save, add_mx_sl_save, add_mx_dj_save, add_mx_zk_save, add_mx_je_save, add_mx_jgdh_save, add_mx_zcdj_save, add_mx_tybj_save, add_mx_zdsj_save, add_mx_bzcb_save, add_mx_ygml_save, add_mx_mll_save, add_mx_sdsl_save, add_mx_scsl_save, add_mx_spgg_save, add_mx_bzdw_save, add_mx_bzhs_save, add_mx_bzsl_save, add_mx_bzzl_save, add_mx_spzl_save, add_mx_bzdx_save, add_mx_zy_save, add_mx_wjfj_save;
    private String djlb_id_sub, zd_id_sub, bm_id_sub, gw_id_sub, fwgj_id_sub, ztsj_id_sub, styw_id_sub, qdyw_id_sub;
    String url_bj_add = URLS.cust_bjxx_add;
    String url_bj_del = URLS.cust_bjxx_del;
    String url_bj_updata = URLS.cust_bjxx_update;
    String cust_get = URLS.cust_z_query;
    CustAllObjectInfos.CustList cust_callback;
    private SimpleAdapter sAdapter;
    private List<CheckBox> checkBoxList;
    private List<Integer> checkedIndexList;
    private int checkFalg, flag_sc;
    String xh_hd, pm_hd, ph_hd, sh_hd, kw_hd, dw_hd, sl_hd, dj_hd, zk_hd, je_hd, jgdh_hd, zcdj_hd, tybj_hd, zdsj_hd, bzcb_hd, ygml_hd, mll_hd, sdsl_hd, scsl_hd, spgg_hd, bzdw_hd, bzhs_hd, bzsl_hd, bzzl_hd, spzl_hd, bzdx_hd;
    //主表信息(提交)
    String post_zt, post_bj_time, post_bjd_tv_bjdh, post_khly, post_tjmj, post_djlb, post_htdh, post_xszd, post_xsbm, post_xsgw, post_gwdh, post_fwgj, post_gjdh,
            post_ztsj, post_sjdh, post_styw, post_qdyw, post_yshk, post_dyzt, post_khxm, post_khdh, post_lplx, post_zxgs, post_jhfs, post_xsdd, post_shr,
            post_shdh, post_ss, post_qx, post_xxdz, post_xxbz, post_sh_time, post_qd_time, post_zdr, post_shenher;
    //明细表信息（提交）
    ArrayList<String> list_xh, list_pm, list_ph, list_sh, list_kw, list_dw, list_sl, list_dj, list_zk, list_je, list_jgdh, list_zcdj, list_tybj,
            list_zdsj, list_bzcb, list_ygml, list_mll, list_sdsl, list_scsl, list_spgg, list_bzdw, list_bzhs, list_bzsl, list_bzzl, list_spzl, list_bzdx;
    String sub_xh, sub_pm, sub_ph, sub_sh, sub_kw, sub_dw, sub_sl, sub_dj, sub_zk, sub_je, sub_jgdh, sub_zcdj, sub_tybj, sub_zdsj, sub_bzcb, sub_ygml, sub_mll, sub_sdsl, sub_scsl, sub_spgg, sub_bzdw, sub_bzhs, sub_bzsl, sub_bzzl, sub_spzl, sub_bzdx;
    Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    try {
                        JSONObject jsonObject = new JSONObject(resultStr);
                        Log.e("LiNing", "数据是===" + jsonObject);
                        Log.e("LiNing", "数据是===" + resultStr.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                default:
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_offer);
        context = OfferActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        db_zt = sp.getString("DB_MR", "");
        erp_yh = sp.getString("MR_YH", "");
        getNowTime();
        initView();
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
        head.setText("报价单");
        this.mHead = ((RelativeLayout) findViewById(R.id.bjmx_head));
        this.mHead.setFocusable(true);
        this.mHead.setClickable(true);
        this.mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        lv_offer_qry = (ListView) findViewById(R.id.lv_bjmx_header);
        lv_offer_qry.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        bjd_tv_zt = (TextView) findViewById(R.id.et_offer_all_zt);
        bjd_tv_zt.setText(db_zt);
        bj_time = (TextView) findViewById(R.id.tv_time_offer);
        bj_time.setText(date_dd);
        bj_time.setOnClickListener(new View.OnClickListener() {
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
                                bj_time.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        bjd_tv_bjdh = (TextView) findViewById(R.id.et_offer_all_dabh);
        bjd_tv_bjdh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTicket();
            }
        });
        touch = (LinearLayout) findViewById(R.id.ll_touch_bj_all);
        touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getTicket();//获取临时档案编号
            }
        });
        khly = (TextView) findViewById(R.id.et_offer_all_khly);
        tjmj = (TextView) findViewById(R.id.et_offer_all_tjmj);
        djlb = (TextView) findViewById(R.id.et_offer_all_djlb);
        htdh = (EditText) findViewById(R.id.et_offer_all_htdh);
        xszd = (TextView) findViewById(R.id.et_offer_all_xszd);
        xsbm = (TextView) findViewById(R.id.et_offer_all_xsbm);
        xsgw = (TextView) findViewById(R.id.et_offer_all_xsgw);
        gwdh = (EditText) findViewById(R.id.et_offer_all_gwdh);
        fwgj = (TextView) findViewById(R.id.et_offer_all_fwgj);
        gjdh = (EditText) findViewById(R.id.et_offer_all_gjdh);
        ztsj = (TextView) findViewById(R.id.et_offer_all_ztsj);
        sjdh = (EditText) findViewById(R.id.et_offer_all_sjdh);
        styw = (TextView) findViewById(R.id.et_offer_all_styw);
        qdyw = (TextView) findViewById(R.id.tv_offer_all_qdyw);
        yshk = (TextView) findViewById(R.id.et_offer_all_yshk);
        dyzt = (TextView) findViewById(R.id.et_offer_all_dyzt);
        khxm = (TextView) findViewById(R.id.et_offer_all_khxm);
        khdh = (TextView) findViewById(R.id.et_offer_all_khdh);
        lplx = (TextView) findViewById(R.id.et_offer_all_lplx);
        zxgs = (TextView) findViewById(R.id.et_offer_all_zxgs);
        jhfs = (TextView) findViewById(R.id.et_offer_all_jhfs);
        xsdd = (TextView) findViewById(R.id.et_offer_all_xsdd);
        shr = (TextView) findViewById(R.id.et_offer_all_shr);
        shdh = (TextView) findViewById(R.id.et_offer_all_shdh);
        ss = (TextView) findViewById(R.id.et_offer_all_ss);
        qx = (TextView) findViewById(R.id.et_offer_all_qx);
        xxdz = (TextView) findViewById(R.id.et_offer_all_xxdz);

        xxbz = (EditText) findViewById(R.id.et_offer_all_bzxx);
        //审核日期
        sh_time = (TextView) findViewById(R.id.tv_offer_all_shrq);
        sh_time.setText(date_dd);
        //确单日期
        qd_time = (TextView) findViewById(R.id.tv_offer_all_qdrq);
        qd_time.setText(date_dd);
        //制单人
        tv_zdr = (TextView) findViewById(R.id.tv_offer_all_zdr);
        tv_zdr.setText(erp_yh);
        //审核人
        tv_shenher = (TextView) findViewById(R.id.tv_offer_all_shenhr);
        tv_shenher.setText(erp_yh);

        //ImageButton
        ib_zt_xl = (ImageButton) findViewById(R.id.ib_offer_all_account);
        ib_zt_xl.setOnClickListener(this);
        //单据类别
        ib_djlb_xl = (ImageButton) findViewById(R.id.ib_offer_all_djlb);
        ib_djlb_xl.setOnClickListener(this);
        //终端（调接口）
        ib_xszd_xl = (ImageButton) findViewById(R.id.ib_offer_all_xszd);
        ib_xszd_xl.setOnClickListener(this);
        //部门（调接口）
        ib_xsbm_xl = (ImageButton) findViewById(R.id.ib_offer_all_xsbm);
        ib_xsbm_xl.setOnClickListener(this);
        //顾问（调接口）
        ib_xsgw_xl = (ImageButton) findViewById(R.id.ib_offer_all_xsgw);
        ib_xsgw_xl.setOnClickListener(this);
        //服务管家（调接口业务）
        ib_fwgj_xl = (ImageButton) findViewById(R.id.ib_offer_all_fwgj);
        ib_fwgj_xl.setOnClickListener(this);
        //制图设计（调接口业务）
        ib_ztsj_xl = (ImageButton) findViewById(R.id.ib_offer_all_ztsj);
        ib_ztsj_xl.setOnClickListener(this);
        //审图业务（调接口业务）
        ib_styw_xl = (ImageButton) findViewById(R.id.ib_offer_all_styw);
        ib_styw_xl.setOnClickListener(this);
        //确单业务（调接口业务）
        ib_qdyw_xl = (ImageButton) findViewById(R.id.ib_offer_all_qdyw);
        ib_qdyw_xl.setOnClickListener(this);
        //预收货款（调收款单）
        ib_yshk_xl = (ImageButton) findViewById(R.id.ib_offer_all_yshk);
        ib_yshk_xl.setOnClickListener(this);
        //客户姓名（调客户接口，下边信息带出）
        ib_kh_xl = (ImageButton) findViewById(R.id.ib_offer_all_khxm);
        ib_kh_xl.setOnClickListener(this);
        //收货人（调配送信息接口，下边信息带出）
        ib_shr_xl = (ImageButton) findViewById(R.id.ib_offer_all_shr);
        ib_shr_xl.setOnClickListener(this);
        //交货方式（调pop）
        ib_jhfs_xl = (ImageButton) findViewById(R.id.ib_offer_all_jhfs);
        ib_jhfs_xl.setOnClickListener(this);

        //button
        offer_query = (Button) findViewById(R.id.btn_offer_all_quickquery);
        offer_add = (Button) findViewById(R.id.btn_offer_all_add);
        offer_set = (Button) findViewById(R.id.btn_offer_all_reset);
        offer_del = (Button) findViewById(R.id.btn_offer_all_del);
        offer_prite = (Button) findViewById(R.id.btn_offer_all_prite);
        offer_save = (Button) findViewById(R.id.btn_offer_all_save);
        offer_qd = (Button) findViewById(R.id.btn_offer_all_okd);
        offer_out = (Button) findViewById(R.id.btn_offer_all_out);
        offer_tszc = (Button) findViewById(R.id.btn_offer_all_tszc);
        offer_bjmx = (Button) findViewById(R.id.btn_offer_all_bjmx);
        offer_query.setOnClickListener(this);
        offer_add.setOnClickListener(this);
        offer_set.setOnClickListener(this);
        offer_del.setOnClickListener(this);
        offer_prite.setOnClickListener(this);
        offer_save.setOnClickListener(this);
        offer_qd.setOnClickListener(this);
        offer_out.setOnClickListener(this);
        offer_tszc.setOnClickListener(this);
        offer_bjmx.setOnClickListener(this);
        mxList = new ArrayList();
        checkedIndexList = new ArrayList<Integer>();
        checkBoxList = new ArrayList<CheckBox>();

        sAdapter = new SimpleAdapter(context, mxList,
                R.layout.bjmxobj_head, new String[]{"序号", "品名",
                "品号", "色号", "库位", "单位", "数量", "单价", "折扣", "金额", "价格代号", "政策单价"
                , "统一标价", "最低售价", "标准成本", "预估毛利", "毛利率", "受订数量", "生产数量"
                , "商品规格", "包装单位", "包装换算", "包装数量", "包装重量", "商品重量", "包装大小"}, new int[]{
                R.id.textView1_id_bjmxxh, R.id.textView2_bjmxpm,
                R.id.textView3_bjmxph, R.id.textView4_bjmxsh,
                R.id.textView5_bjmxkw, R.id.textView6_bjmxdw,
                R.id.textView7_bjmxsl, R.id.textView8_bjmxdj,
                R.id.tv_bjmxzk, R.id.tv_bjmxje,
                R.id.tv_bjmxjgdh, R.id.tv_bjmxzcdj,
                R.id.tv_bjmxtybj, R.id.tv_bjmxzdsj,
                R.id.tv_bjmxbzcb, R.id.tv_bjmxygml,
                R.id.tv_bjmxmll, R.id.tv_bjmxsdsl,
                R.id.tv_bjmxscsl,
                R.id.tv_bjmxspgg, R.id.tv_bjmxbzdw,
                R.id.tv_bjmxbzhs, R.id.tv_bjmxbzsl,
                R.id.tv_bjmxbzzl, R.id.tv_bjmxspzl,
                R.id.tv_bjmxbzdx}) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                ViewHolders holders = null;
                if (convertView == null) {
                    synchronized (OfferActivity.this) {
                        convertView = View.inflate(context,
                                R.layout.bjmxobj_head, null);
                        holders = new ViewHolders();
                        final CheckBox checkBox = (CheckBox) convertView
                                .findViewById(R.id.bjmx_listDeleteCheckBox);
                        checkBox.setVisibility(View.VISIBLE);
                        checkBoxList.add(checkBox);
                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    flag_sc = position;
                                    checkFalg = 1;
                                    checkedIndexList.add(position);
                                    Log.e("LiNing", "--------" + checkedIndexList);

                                } else {
                                    checkFalg = 0;
                                    checkFalg = 0;
                                    checkedIndexList.remove((Integer) position);
                                    Log.e("LiNing", "--------" + checkedIndexList);

                                }
                                if (checkFalg == 1) {

                                }
                                if (0 < checkedIndexList.size()
                                        && checkedIndexList.size() <= 1) {

                                    xh_hd = mxList.get(flag_sc).get("序号").toString();
                                    pm_hd = mxList.get(flag_sc).get("品名").toString();
                                    ph_hd = mxList.get(flag_sc).get("品号").toString();
                                    sh_hd = mxList.get(flag_sc).get("色号").toString();
                                    kw_hd = mxList.get(flag_sc).get("库位").toString();
                                    dw_hd = mxList.get(flag_sc).get("单位").toString();
                                    sl_hd = mxList.get(flag_sc).get("数量").toString();
                                    dj_hd = mxList.get(flag_sc).get("单价").toString();
                                    zk_hd = mxList.get(flag_sc).get("折扣").toString();
                                    je_hd = mxList.get(flag_sc).get("金额").toString();
                                    jgdh_hd = mxList.get(flag_sc).get("价格代号").toString();
                                    zcdj_hd = mxList.get(flag_sc).get("政策单价").toString();
                                    tybj_hd = mxList.get(flag_sc).get("统一标价").toString();
                                    zdsj_hd = mxList.get(flag_sc).get("最低售价").toString();
                                    bzcb_hd = mxList.get(flag_sc).get("标准成本").toString();
                                    ygml_hd = mxList.get(flag_sc).get("预估毛利").toString();
                                    mll_hd = mxList.get(flag_sc).get("毛利率").toString();
                                    sdsl_hd = mxList.get(flag_sc).get("受订数量").toString();
                                    scsl_hd = mxList.get(flag_sc).get("生产数量").toString();

                                    spgg_hd = mxList.get(flag_sc).get("商品规格").toString();
                                    bzdw_hd = mxList.get(flag_sc).get("包装单位").toString();
                                    bzhs_hd = mxList.get(flag_sc).get("包装换算").toString();
                                    bzsl_hd = mxList.get(flag_sc).get("包装数量").toString();
                                    bzzl_hd = mxList.get(flag_sc).get("包装重量").toString();
                                    spzl_hd = mxList.get(flag_sc).get("商品重量").toString();
                                    bzdx_hd = mxList.get(flag_sc).get("包装大小").toString();
                                }
//                                else{
//                                    Toast.makeText(context, "请选择单条数据", Toast.LENGTH_LONG).show();
//                                }

                            }
                        });


                        MyHScrollView scrollView1 = (MyHScrollView) convertView
                                .findViewById(R.id.horizontalScrollView1);
                        holders.scrollView = scrollView1;
                        MyHScrollView headSrcrollView = (MyHScrollView) mHead
                                .findViewById(R.id.horizontalScrollView1);
                        headSrcrollView
                                .AddOnScrollChangedListener(new OnScrollChangedListenerImp(
                                        scrollView1));

                    }

                }
                return super.getView(position, convertView, parent);
            }

            class ViewHolders {
                HorizontalScrollView scrollView;
                public CheckBox checkbox;
                public TextView mx_xh;
                public TextView mx_pm;
                public TextView mx_ph;
                public TextView mx_sh;
                public TextView mx_kw;
                public TextView mx_dw;
                public TextView mx_sl;
                public TextView mx_dj;
                public TextView mx_zk;
                public TextView mx_je;
                public TextView mx_jgdh;
                public TextView mx_zcdj;
                public TextView mx_tybj;
                public TextView mx_zdsj;
                public TextView mx_bzcb;
                public TextView mx_ygml;
                public TextView mx_mll;
                public TextView mx_sdsl;
                public TextView mx_scsl;
            }
        };
        lv_offer_qry.setAdapter(this.sAdapter);
    }

    private void getTicket() {
        String DB_LS = bjd_tv_zt.getText().toString();
        date_dd_dh = bj_time.getText().toString();
        if (DB_LS.equals("")) {
            Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
        } else if (date_dd.equals("")) {
            Toast.makeText(context, "请获取时间", Toast.LENGTH_LONG).show();
        } else {
            OkHttpClient client = new OkHttpClient();
            FormBody localFormBody = new FormBody.Builder()
                    .add("bn_Type", "QT")
                    .add("db_Id", DB_LS)
                    .add("bn_Date", date_dd)
                    .build();
            Request localRequest = new Request.Builder()
                    .addHeader("cookie", session).url(url_dh_bjxx)
                    .post(localFormBody)
                    .build();
            Log.e("LiNing", "临时档案编号===" + session + "---" + URLS.price_num_ls + "---" + DB_LS + "----" + date_dd);
            client.newCall(localRequest).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.code() == 200) {

                        String str = response.body().string();
                        Log.e("LiNing", "临时档案编号===" + str);
                        final PriceNumID bean_id = (PriceNumID) new Gson()
                                .fromJson(str, PriceNumID.class);
                        if (bean_id != null) {
                            OfferActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    String id_ls = bean_id.getBil_no();
                                    bjd_tv_bjdh.setText(id_ls);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //账套
            case R.id.ib_offer_all_account:
                Intent intent = new Intent(context, CondicionInfoActivity.class);
                intent.putExtra("flag", "1");
                startActivityForResult(intent, 1);
                break;
            //单据类别-9
            case R.id.ib_offer_all_djlb:
                if (bjd_tv_zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {

                    Intent intent_djlb = new Intent(context,
                            ErpDepInfoActivity.class);
                    intent_djlb.putExtra("flag", "22");
                    intent_djlb.putExtra("DB_ID", bjd_tv_zt.getText().toString());
                    startActivityForResult(intent_djlb, 9);
//                    ischeck_bbqd = true;//用于判断bbqd_id是否点击
                }
                break;
            //销售终端-10
            case R.id.ib_offer_all_xszd:
                if (bjd_tv_zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {

                    Intent intent_zd = new Intent(context,
                            CondicionInfoActivity.class);
                    intent_zd.putExtra("flag", "13");
                    intent_zd.putExtra("queryID", bjd_tv_zt.getText().toString());
                    startActivityForResult(intent_zd, 10);
//                    ischeck_bbqd = true;//用于判断bbqd_id是否点击
                }
                break;
            //销售部门-11
            case R.id.ib_offer_all_xsbm:
                if (bjd_tv_zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {

                    Intent intent_bm = new Intent(context,
                            CondicionInfoActivity.class);
                    intent_bm.putExtra("flag", "11");
                    intent_bm.putExtra("queryID", bjd_tv_zt.getText().toString());
                    startActivityForResult(intent_bm, 11);
//                    ischeck_bbqd = true;//用于判断bbqd_id是否点击
                }
                break;
            //销售顾问-12
            case R.id.ib_offer_all_xsgw:
                if (bjd_tv_zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {

                    Intent intent_gw = new Intent(context,
                            CondicionInfoActivity.class);
                    intent_gw.putExtra("flag", "17");
                    intent_gw.putExtra("queryID", bjd_tv_zt.getText().toString());
                    startActivityForResult(intent_gw, 12);
//                    ischeck_bbqd = true;//用于判断bbqd_id是否点击
                }
                break;
            //服务管家-13
            case R.id.ib_offer_all_fwgj:
                if (bjd_tv_zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {

                    Intent intent_gj = new Intent(context,
                            CondicionInfoActivity.class);
                    intent_gj.putExtra("flag", "17");
                    intent_gj.putExtra("queryID", bjd_tv_zt.getText().toString());
                    startActivityForResult(intent_gj, 13);
//                    ischeck_bbqd = true;//用于判断bbqd_id是否点击
                }
                break;
            //制图设计-14
            case R.id.ib_offer_all_ztsj:
                if (bjd_tv_zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {

                    Intent intent_ztsj = new Intent(context,
                            CondicionInfoActivity.class);
                    intent_ztsj.putExtra("flag", "17");
                    intent_ztsj.putExtra("queryID", bjd_tv_zt.getText().toString());
                    startActivityForResult(intent_ztsj, 14);
//                    ischeck_bbqd = true;//用于判断bbqd_id是否点击
                }
                break;
            //审图业务-15
            case R.id.ib_offer_all_styw:
                if (bjd_tv_zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {

                    Intent intent_styw = new Intent(context,
                            CondicionInfoActivity.class);
                    intent_styw.putExtra("flag", "17");
                    intent_styw.putExtra("queryID", bjd_tv_zt.getText().toString());
                    startActivityForResult(intent_styw, 15);
//                    ischeck_bbqd = true;//用于判断bbqd_id是否点击
                }
                break;
            //确单业务-16
            case R.id.ib_offer_all_qdyw:
                if (bjd_tv_zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {

                    Intent intent_qdyw = new Intent(context,
                            CondicionInfoActivity.class);
                    intent_qdyw.putExtra("flag", "17");
                    intent_qdyw.putExtra("queryID", bjd_tv_zt.getText().toString());
                    startActivityForResult(intent_qdyw, 16);
//                    ischeck_bbqd = true;//用于判断bbqd_id是否点击
                }
                break;
            case R.id.ib_offer_all_khxm:
                //判断权限
//                if (kh_query_qx == true) {
//
//                } else {
//                    Toast.makeText(context, "无此权限", Toast.LENGTH_LONG).show();
//                }
                //调取查询客户的界面
                Intent intent_cust = new Intent(context, QueryCustersActivity.class);
                intent_cust.putExtra("ZT_VIP", bjd_tv_zt.getText().toString());
                startActivityForResult(intent_cust, 3);

                break;
            case R.id.ib_offer_all_shr:
                //调取查询客户的界面
                if (!qty_cust_zt.equals("") && !qty_cust_dabh.equals("")) {
                    Intent intent_shr = new Intent(context, MoveAllInfoActivity.class);
                    intent_shr.putExtra("SSZT", qty_cust_zt);
                    intent_shr.putExtra("DABH", qty_cust_dabh);
                    startActivityForResult(intent_shr, 4);
                    Log.e("LiNing", "删除数据====" + qty_cust_zt + "---" + qty_cust_dabh);
                } else {
                    Toast.makeText(context, "配送信息不能编辑", Toast.LENGTH_LONG).show();
                }

                break;
                //交货方式
            case R.id.ib_offer_all_jhfs:
//                checked = 1;
                showPopupMenu(ib_jhfs_xl);
                break;
            //速查
            case R.id.btn_offer_all_quickquery:
                //加权限
                if (bjd_tv_zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent_vip = new Intent(context, QueryBjdActivity.class);
                    intent_vip.putExtra("ZT_VIP", bjd_tv_zt.getText().toString());
                    startActivityForResult(intent_vip, 2);
                }
                break;
            case R.id.btn_offer_all_add:
                clearInfos();
                break;
            //保存
            case R.id.btn_offer_all_save:
                //新增
                getPostInfos();
                addBjd_ys();
                break;
            case R.id.btn_offer_all_reset:
//                resetBjd();
                break;
            case R.id.btn_offer_all_del:
//                if (sp_del == true) {

                new AlertDialog.Builder(context)
                        .setTitle("是否删除")
                        .setPositiveButton("是",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {

                                        delBjd();
                                    }
                                }).setNegativeButton("否", null).show();
//                } else {
//                    Toast.makeText(context, "无此权限", Toast.LENGTH_LONG).show();
//                }

                break;
            case R.id.btn_offer_all_prite:
                break;
            case R.id.btn_offer_all_okd:
                break;
            case R.id.btn_offer_all_out:
                finish();
                break;
            case R.id.btn_offer_all_tszc:
                break;
            case R.id.btn_offer_all_bjmx:
                View bjd_addmx = getLayoutInflater()
                        .inflate(R.layout.bjd_mx_add, null);
                TextView head_load_all = (TextView) bjd_addmx.findViewById(R.id.all_head);
                head_load_all.setText("报价单-明细");

                add_mx_xh = (TextView) bjd_addmx.findViewById(R.id.tv_mx_add_xh);
                add_mx_ph = (TextView) bjd_addmx.findViewById(R.id.tv_mx_add_ph);
                add_mx_sh = (TextView) bjd_addmx.findViewById(R.id.tv_mx_add_sh);
                add_mx_kw = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_kw);
                add_mx_dw = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_dw);
                add_mx_sl = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_sl);
                add_mx_dj = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_dj);
                add_mx_zk = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_zk);
                add_mx_je = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_je);
                add_mx_jgdh = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_jgdh);
                add_mx_zcdj = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_zcdj);
                add_mx_tybj = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_tybj);
                add_mx_zdsj = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_zdsj);
                add_mx_bzcb = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_bzcb);
                add_mx_ygml = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_ygml);
                add_mx_mll = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_mll);
                add_mx_sdsl = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_sdsl);
                add_mx_scsl = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_scsl);
                add_mx_spgg = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_spgg);
                add_mx_bzdw = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_bzdw);
                add_mx_bzhs = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_bzhs);
                add_mx_bzsl = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_bzsl);
                add_mx_bzzl = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_bzzl);
                add_mx_spzl = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_spzl);
                add_mx_bzdx = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_bzdx);
                add_mx_zy = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_zy);


                if (0 < checkedIndexList.size()
                        && checkedIndexList.size() <= 1) {
                    add_mx_xh.setText(xh_hd);
                    add_mx_ph.setText(ph_hd);
                    add_mx_sh.setText(sh_hd);
                    add_mx_kw.setText(kw_hd);
                    add_mx_dw.setText(dw_hd);
                    add_mx_sl.setText(sl_hd);
                    add_mx_dj.setText(dj_hd);
                    add_mx_zk.setText(zk_hd);
                    add_mx_je.setText(je_hd);
                    add_mx_jgdh.setText(jgdh_hd);
                    add_mx_zcdj.setText(zcdj_hd);
                    add_mx_tybj.setText(tybj_hd);
                    add_mx_zdsj.setText(zdsj_hd);
                    add_mx_bzcb.setText(bzcb_hd);
                    add_mx_ygml.setText(ygml_hd);
                    add_mx_mll.setText(mll_hd);
                    add_mx_sdsl.setText(sdsl_hd);
                    add_mx_scsl.setText(scsl_hd);
                    add_mx_spgg.setText(spgg_hd);
                    add_mx_bzdw.setText(bzdw_hd);
                    add_mx_bzhs.setText(bzhs_hd);
                    add_mx_bzsl.setText(bzsl_hd);
                    add_mx_bzzl.setText(bzzl_hd);
                    add_mx_spzl.setText(spzl_hd);
                    add_mx_bzdx.setText(bzdx_hd);
                } else {
                    //判断明细listview的数量
                    if (mxList == null) {
                        add_mx_xh.setText("1");
                    } else {
                        int i = mxList.size() + 1;
                        add_mx_xh.setText("" + i);
                    }

                }
                bjd_addmx.findViewById(R.id.imageButton1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addBjmx_all.dismiss();
                    }
                });
                bjd_addmx.findViewById(R.id.btn_mx_save).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //保存
                        getload_infos();
                        sAdapter.notifyDataSetChanged();
                        addBjmx_all.dismiss();
                    }
                });
                bjd_addmx.findViewById(R.id.btn_mx_del).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //删除
                        mxList.remove(flag_sc);
                        addBjmx_all.dismiss();
                        sAdapter.notifyDataSetChanged();
                    }
                });

                addBjmx_all = new AlertDialog.Builder(context).create();
                addBjmx_all.setCancelable(false);
                addBjmx_all.setView(bjd_addmx);
                addBjmx_all.show();
                break;
        }
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);

        popupMenu.getMenuInflater().inflate(R.menu.jhfs, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.check1
                        || menuItem.getItemId() == R.id.check2
                        || menuItem.getItemId() == R.id.check3
                        || menuItem.getItemId() == R.id.check4
                        || menuItem.getItemId() == R.id.check5
                        || menuItem.getItemId() == R.id.check6
                        ){
                    menuItem.setChecked(!menuItem.isChecked());
                    jhfs.setText(menuItem.getTitle());
                    return true;
                }
                return true;
            }
        });
        popupMenu.show();
    }


    //获取提交数据
    private void getPostInfos() {
//        主表数据
        post_zt = bjd_tv_zt.getText().toString();
        post_bj_time = bj_time.getText().toString();
        post_bjd_tv_bjdh = bjd_tv_bjdh.getText().toString();
        post_khly = khly.getText().toString();
        post_tjmj = tjmj.getText().toString();
        post_djlb = djlb.getText().toString();
        post_htdh = htdh.getText().toString();
        post_xszd = xszd.getText().toString();
        post_xsbm = xsbm.getText().toString();
        post_xsgw = xsgw.getText().toString();
        post_gwdh = gwdh.getText().toString();
        post_fwgj = fwgj.getText().toString();
        post_gjdh = gjdh.getText().toString();
        post_ztsj = ztsj.getText().toString();
        post_sjdh = sjdh.getText().toString();
        post_styw = styw.getText().toString();
        post_qdyw = qdyw.getText().toString();
        post_yshk = yshk.getText().toString();
        post_dyzt = dyzt.getText().toString();
        post_khxm = khxm.getText().toString();
        post_khdh = khdh.getText().toString();
        post_lplx = lplx.getText().toString();
        post_zxgs = zxgs.getText().toString();
        post_jhfs = jhfs.getText().toString();
        post_xsdd = xsdd.getText().toString();
        post_shr = shr.getText().toString();
        post_shdh = shdh.getText().toString();
        post_ss = ss.getText().toString();
        post_qx = qx.getText().toString();
        post_xxdz = xxdz.getText().toString();
        post_xxbz = xxbz.getText().toString();
        post_sh_time = sh_time.getText().toString();
        post_qd_time = qd_time.getText().toString();
        post_zdr = tv_zdr.getText().toString();
        post_shenher = tv_shenher.getText().toString();

        list_xh = new ArrayList<String>();
        list_pm = new ArrayList<String>();
        list_ph = new ArrayList<String>();
        list_sh = new ArrayList<String>();
        list_kw = new ArrayList<String>();
        list_dw = new ArrayList<String>();
        list_sl = new ArrayList<String>();
        list_dj = new ArrayList<String>();
        list_zk = new ArrayList<String>();
        list_je = new ArrayList<String>();
        list_jgdh = new ArrayList<String>();
        list_zcdj = new ArrayList<String>();
        list_tybj = new ArrayList<String>();
        list_zdsj = new ArrayList<String>();
        list_bzcb = new ArrayList<String>();
        list_ygml = new ArrayList<String>();
        list_mll = new ArrayList<String>();
        list_sdsl = new ArrayList<String>();
        list_scsl = new ArrayList<String>();

        list_spgg = new ArrayList<String>();
        list_bzdw = new ArrayList<String>();
        list_bzhs = new ArrayList<String>();
        list_bzsl = new ArrayList<String>();
        list_bzzl = new ArrayList<String>();
        list_spzl = new ArrayList<String>();
        list_bzdx = new ArrayList<String>();

        // 明细表数据
        if (mxList != null && mxList.size() > 0) {
            for (int i = 0; i < mxList.size(); i++) {
                String post_xh_hd = mxList.get(i).get("序号").toString();
                list_xh.add(post_xh_hd);
                String post_pm_hd = mxList.get(i).get("品名").toString();
                list_pm.add(post_pm_hd);
                String post_ph_hd = mxList.get(i).get("品号").toString();
                list_ph.add(post_ph_hd);
                String post_sh_hd = mxList.get(i).get("色号").toString();
                list_sh.add(post_sh_hd);
                String post_kw_hd = mxList.get(i).get("库位").toString();
                list_kw.add(post_kw_hd);
                String post_dw_hd = mxList.get(i).get("单位").toString();
                list_dw.add(post_dw_hd);
                String post_sl_hd = mxList.get(i).get("数量").toString();
                list_sl.add(post_sl_hd);
                String post_dj_hd = mxList.get(i).get("单价").toString();
                list_dj.add(post_dj_hd);
                String post_zk_hd = mxList.get(i).get("折扣").toString();
                list_zk.add(post_zk_hd);
                String post_je_hd = mxList.get(i).get("金额").toString();
                list_je.add(post_je_hd);
                String post_jgdh_hd = mxList.get(i).get("价格代号").toString();
                list_jgdh.add(post_jgdh_hd);
                String post_zcdj_hd = mxList.get(i).get("政策单价").toString();
                list_zcdj.add(post_zcdj_hd);
                String post_tybj_hd = mxList.get(i).get("统一标价").toString();
                list_tybj.add(post_tybj_hd);
                String post_zdsj_hd = mxList.get(i).get("最低售价").toString();
                list_zdsj.add(post_zdsj_hd);
                String post_bzcb_hd = mxList.get(i).get("标准成本").toString();
                list_bzcb.add(post_bzcb_hd);
                String post_ygml_hd = mxList.get(i).get("预估毛利").toString();
                list_ygml.add(post_ygml_hd);
                String post_mll_hd = mxList.get(i).get("毛利率").toString();
                list_mll.add(post_mll_hd);
                String post_sdsl_hd = mxList.get(i).get("受订数量").toString();
                list_sdsl.add(post_sdsl_hd);
                String post_scsl_hd = mxList.get(i).get("生产数量").toString();
                list_scsl.add(post_scsl_hd);
                String post_spgg_hd = mxList.get(i).get("商品规格").toString();
                list_spgg.add(post_spgg_hd);
                String post_bzdd_hd = mxList.get(i).get("包装单位").toString();
                list_bzdw.add(post_bzdd_hd);
                String post_bzhs_hd = mxList.get(i).get("包装换算").toString();
                list_bzhs.add(post_bzhs_hd);
                String post_bzsl_hd = mxList.get(i).get("包装数量").toString();
                list_bzsl.add(post_bzsl_hd);
                String post_bzzl_hd = mxList.get(i).get("包装重量").toString();
                list_bzzl.add(post_bzzl_hd);
                String post_spzl_hd = mxList.get(i).get("商品重量").toString();
                list_spzl.add(post_spzl_hd);
                String post_bzdx_hd = mxList.get(i).get("包装大小").toString();
                list_bzdx.add(post_bzdx_hd);
                String xhs_str = "";
                for (String name : list_xh) {
                    xhs_str += name + ",";
                }
                sub_xh = xhs_str.substring(0,
                        xhs_str.length() - 1);

                String pms_str = "";
                for (String name : list_pm) {
                    pms_str += name + ",";
                }
                sub_pm = pms_str.substring(0,
                        pms_str.length() - 1);

                String phs_str = "";
                for (String name : list_ph) {
                    phs_str += name + ",";
                }
                sub_ph = phs_str.substring(0,
                        phs_str.length() - 1);

                String shs_str = "";
                for (String name : list_sh) {
                    shs_str += name + ",";
                }
                sub_sh = shs_str.substring(0,
                        shs_str.length() - 1);

                String kws_str = "";
                for (String name : list_kw) {
                    kws_str += name + ",";
                }
                sub_kw = kws_str.substring(0,
                        kws_str.length() - 1);

                String dws_str = "";
                for (String name : list_dw) {
                    dws_str += name + ",";
                }
                sub_dw = dws_str.substring(0,
                        dws_str.length() - 1);

                String sls_str = "";
                for (String name : list_sl) {
                    sls_str += name + ",";
                }
                sub_sl = sls_str.substring(0,
                        sls_str.length() - 1);

                String djs_str = "";
                for (String name : list_dj) {
                    djs_str += name + ",";
                }
                sub_dj = djs_str.substring(0,
                        djs_str.length() - 1);

                String zks_str = "";
                for (String name : list_zk) {
                    zks_str += name + ",";
                }
                sub_zk = zks_str.substring(0,
                        zks_str.length() - 1);

                String jes_str = "";
                for (String name : list_je) {
                    jes_str += name + ",";
                }
                sub_je = jes_str.substring(0,
                        jes_str.length() - 1);

                String jgdhs_str = "";
                for (String name : list_jgdh) {
                    jgdhs_str += name + ",";
                }
                sub_jgdh = jgdhs_str.substring(0,
                        jgdhs_str.length() - 1);

                String zcdjs_str = "";
                for (String name : list_zcdj) {
                    zcdjs_str += name + ",";
                }
                sub_zcdj = zcdjs_str.substring(0,
                        zcdjs_str.length() - 1);

                String tybjs_str = "";
                for (String name : list_tybj) {
                    tybjs_str += name + ",";
                }
                sub_tybj = tybjs_str.substring(0,
                        tybjs_str.length() - 1);

                String zdsjs_str = "";
                for (String name : list_zdsj) {
                    zdsjs_str += name + ",";
                }
                sub_zdsj = zdsjs_str.substring(0,
                        zdsjs_str.length() - 1);

                String bzcbs_str = "";
                for (String name : list_bzcb) {
                    bzcbs_str += name + ",";
                }
                sub_bzcb = bzcbs_str.substring(0,
                        bzcbs_str.length() - 1);

                String ygmls_str = "";
                for (String name : list_ygml) {
                    ygmls_str += name + ",";
                }
                sub_ygml = ygmls_str.substring(0,
                        ygmls_str.length() - 1);

                String mlls_str = "";
                for (String name : list_mll) {
                    mlls_str += name + ",";
                }
                sub_mll = mlls_str.substring(0,
                        mlls_str.length() - 1);

                String sdsls_str = "";
                for (String name : list_sdsl) {
                    sdsls_str += name + ",";
                }
                sub_sdsl = sdsls_str.substring(0,
                        sdsls_str.length() - 1);

                String scsls_str = "";
                for (String name : list_scsl) {
                    scsls_str += name + ",";
                }
                sub_scsl = scsls_str.substring(0,
                        scsls_str.length() - 1);
//
                String spggs_str = "";
                for (String name : list_spgg) {
                    spggs_str += name + ",";
                }
                sub_spgg = spggs_str.substring(0,
                        spggs_str.length() - 1);

                String bzdws_str = "";
                for (String name : list_bzdw) {
                    bzdws_str += name + ",";
                }

                sub_bzdw = bzdws_str.substring(0,
                        bzdws_str.length() - 1);

                String bzhss_str = "";
                for (String name : list_bzhs) {
                    bzhss_str += name + ",";
                }
                sub_bzhs = bzhss_str.substring(0,
                        bzhss_str.length() - 1);
                String bzsls_str = "";
                for (String name : list_bzsl) {
                    bzsls_str += name + ",";
                }

                sub_bzsl = bzsls_str.substring(0,
                        bzsls_str.length() - 1);

                String bzzls_str = "";
                for (String name : list_bzzl) {
                    bzzls_str += name + ",";
                }
                sub_bzzl = bzzls_str.substring(0,
                        bzzls_str.length() - 1);
                String spzls_str = "";
                for (String name : list_spzl) {
                    spzls_str += name + ",";
                }

                sub_spzl = spzls_str.substring(0,
                        spzls_str.length() - 1);
                String bzdxs_str = "";
                for (String name : list_bzdx) {
                    bzdxs_str += name + ",";
                }
                sub_bzdx = bzdxs_str.substring(0,
                        bzdxs_str.length() - 1);

            }
        }
    }

    private void clearInfos() {

        bj_time.setText("");
        bjd_tv_bjdh.setText("");
        khly.setText("");
        tjmj.setText("");
        djlb.setText("");
        htdh.setText("");
        xszd.setText("");
        xsbm.setText("");
        xsgw.setText("");
        gwdh.setText("");
        fwgj.setText("");
        gjdh.setText("");
        ztsj.setText("");
        sjdh.setText("");
        styw.setText("");
        qdyw.setText("");
        yshk.setText("");
        dyzt.setText("N");
        khxm.setText("");
        khdh.setText("");
        lplx.setText("");
        zxgs.setText("");
        jhfs.setText("");
        xsdd.setText("");
        shr.setText("");
        shdh.setText("");
        ss.setText("");
        qx.setText("");
        xxdz.setText("");
        xxbz.setText("");
        sh_time.setText("");
        qd_time.setText("");
        tv_shenher.setText("ADMIN");
        tv_zdr.setText("ADMIN");
        lv_offer_qry.setAdapter(null);
        mxList.clear();
        checkedIndexList.clear();
    }

    private void resetBjd() {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("DB_ID", "DB_BJ15")
                .add("QT_DD", bj_time.getText().toString())
                .add("QT_NO", "QT1812120001")
                .add("Cust_Src", "市场2")
                .add("Remd", "网络2")
                .add("BIL_TYPE", djlb_id_sub)
                .add("CUS_OS_NO", "20180328")
                .add("CUS_NO", zd_id_sub)
                .add("DEP_NO", bm_id_sub)
                .add("SAL_NO", gw_id_sub)
                .add("Cust_Name", "大王2")
                .add("Cust_Con", "18511403630")
                .add("Con_Per", "李宁2")
                .add("Con_Tel", "18511403631")
                .add("Con_Crt", "北京市2")
                .add("Con_Spa", "朝阳区2")
                .add("Con_Add", "北京市十里河2")
                .add("ITM", "2")
                .add("chk_dd", sh_time.getText().toString())
                .add("AFFI_DD", qd_time.getText().toString())
                .add("usr", "ln111")
                .build();
        Log.e("LiNing", "添加结果====" + bjd_tv_bjdh.getText().toString() + "---" + djlb_id_sub + "---" +
                zd_id_sub + "---" + bm_id_sub + "---" + gw_id_sub + "---");
        client.newCall(
                new Request.Builder().addHeader("cookie", session).url(url_bj_updata)
                        .post(body).build()).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e("LiNing", "修改结果====" + str);
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    private String resultStr = ""; // 服务端返回结果集

    private void addBjd_ys() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 创建一个URL对象
                try {
                    URL url = new URL(url_bj_add);
                    Map<String, String> textParams = new HashMap<String, String>();
                    Map<String, File> fileparams = new HashMap<String, File>();

                    textParams.put("DB_ID", post_zt);
                    textParams.put("QT_DD", post_bj_time);
                    textParams.put("QT_NO", post_bjd_tv_bjdh);
                    textParams.put("Cust_Src", post_khly);
                    textParams.put("Remd", post_tjmj);
                    textParams.put("BIL_TYPE", post_djlb);
                    textParams.put("CUS_OS_NO", post_htdh);
                    textParams.put("CUS_NO", post_xszd);
                    textParams.put("CUS_NAME", zd_id_sub);
                    textParams.put("DEP_NO", post_xsbm);
                    textParams.put("DEP_NAME", bm_id_sub);
                    textParams.put("SAL_NO", post_xsgw);
                    textParams.put("SAL_NAME", gw_id_sub);
                    textParams.put("SAL_Tel", post_gwdh);
                    textParams.put("SECE", post_fwgj);
                    textParams.put("SECE_TEL", post_gjdh);
                    textParams.put("STYL", post_ztsj);
                    textParams.put("STYL_TEL", post_sjdh);
                    textParams.put("PLAW", post_styw);
                    textParams.put("AFFI", post_qdyw);
                    textParams.put("DEP_RED", post_yshk);
                    textParams.put("PRT_SW", post_dyzt);
                    textParams.put("Cust_Name", post_khxm);
                    textParams.put("Cust_Con", post_khdh);
                    textParams.put("Hous_Type", post_lplx);
                    textParams.put("Deco_Com", post_zxgs);
                    textParams.put("SEND_MTH", post_jhfs);
                    textParams.put("Con_Per", post_shr);
                    textParams.put("Con_Tel", post_shdh);
                    textParams.put("Con_Crt", post_ss);
                    textParams.put("Con_Spa", post_qx);
                    textParams.put("Con_Add", post_xxdz);
                    textParams.put("REM", post_xxbz);
                    textParams.put("chk_dd", post_sh_time);
                    textParams.put("AFFI_DD", post_qd_time);
                    textParams.put("usr", post_zdr);
                    textParams.put("usr_chk", post_shenher);
                    //明细
                    textParams.put("ITM", sub_xh);
                    textParams.put("PRD_NAME", sub_pm);
                    textParams.put("PRD_NO", sub_ph);
                    textParams.put("RPD_MARK", sub_sh);
                    textParams.put("WH", sub_kw);
                    textParams.put("UNIT", sub_dw);
                    textParams.put("QTY", sub_sl);
                    textParams.put("UP", sub_dj);
                    textParams.put("DIS_CNT", sub_zk);
                    textParams.put("AMTN", sub_je);
                    textParams.put("PRICE_ID", sub_jgdh);
                    textParams.put("prdUp", sub_zcdj);
                    textParams.put("UPR", sub_tybj);
                    textParams.put("UP_MIN", sub_zdsj);
                    textParams.put("CST_STD", sub_bzcb);
                    textParams.put("EST_SUB", sub_ygml);
                    textParams.put("SUB_GPR", sub_mll);
                    textParams.put("OS_QTY", sub_sdsl);
                    textParams.put("SC_QTY", sub_scsl);
                    textParams.put("SPC", sub_spgg);
                    textParams.put("PAK_UNIT", sub_bzdw);
                    textParams.put("PAK_EXC", sub_bzhs);
                    textParams.put("PAK_QTY", sub_bzsl);
                    textParams.put("PAK_NW", sub_bzzl);
                    textParams.put("COM_NW", sub_spzl);
                    textParams.put("PAKG", sub_bzdx);


                    Log.e("LiNing", "user数据*******" + textParams);
//                    if (!file_sighn.equals("") && file_sighn != null
//                            && file_sighn.exists()) {
//
//                        fileparams.put("Signature", file_sighn);
//                    }
//                    Log.e("LiNing", "签章文件***********" + fileparams);
                    // 利用HttpURLConnection对象从网络中获取网页数据
                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    // 添加表头
                    conn.addRequestProperty("cookie", session);
                    // 设置连接超时（记得设置连接超时,如果网络不好,Android系统在超过默认时间会收回资源中断操作）
                    conn.setConnectTimeout(5000);
                    // 设置允许输出（发送POST请求必须设置允许输出）
                    conn.setDoOutput(true);
                    // 设置使用POST的方式发送
                    conn.setRequestMethod("POST");
                    // 设置不使用缓存（容易出现问题）
                    conn.setUseCaches(false);
                    // 在开始用HttpURLConnection对象的setRequestProperty()设置,就是生成HTML文件头
                    conn.setRequestProperty("ser-Agent", "Fiddler");
                    // 设置contentType
                    conn.setRequestProperty("Content-Type",
                            "multipart/form-data; boundary=" + NetUtil.BOUNDARY);
                    OutputStream os = conn.getOutputStream();
                    DataOutputStream ds = new DataOutputStream(os);
                    NetUtil.writeStringParams(textParams, ds);
                    //图片上传判断（此处很重要）
//                    if (!file_sighn.equals("") && file_sighn != null
//                            && file_sighn.exists()) {
//
//                        NetUtil.writeFileParams(fileparams, ds);
//                    }
                    NetUtil.paramsEnd(ds);
                    // 对文件流操作完,要记得及时关闭
                    os.close();
                    // 服务器返回的响应吗
                    int code = conn.getResponseCode(); // 从Internet获取网页,发送请求,将网页以流的形式读回来
                    Log.e("LiNing", "ddddddddddd" + code);
                    // 对响应码进行判断
                    if (code == 200) {// 返回的响应码200,是成功
                        // 得到网络返回的输入流
                        InputStream is = conn.getInputStream();
                        resultStr = NetUtil.readString(is);
                        Log.e("LiNing", "ddddddddddd" + resultStr);
                        // startActivity(new Intent(context,
                        // CusterInfoActivity.class));
                        // finish();
                        BackApplication.getInstance().exit();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);// 执行耗时的方法之后发送消给handler
            }
        }).start();
    }

    private void delBjd() {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("DB_ID", bjd_tv_zt.getText().toString())
                .add("QT_NO", bjd_tv_bjdh.getText().toString())
                .build();
        Log.e("LiNing", "删除数据====" + bjd_tv_zt.getText().toString() + "---" + bjd_tv_bjdh.getText().toString());
        client.newCall(
                new Request.Builder().addHeader("cookie", session).url(url_bj_del)
                        .post(body).build()).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e("LiNing", "删除结果====" + str);
                final JsonRootBean localJsonRootBean = (JsonRootBean) new Gson()
                        .fromJson(str, JsonRootBean.class);
                if (localJsonRootBean != null) {
                    OfferActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            boolean rlo = localJsonRootBean.getRLO();
                            if (rlo == true) {
                                Toast.makeText(OfferActivity.this,
                                        "信息删除成功", Toast.LENGTH_SHORT).show();
                                //清空数据
                            } else if (rlo == false) {
                                Toast.makeText(OfferActivity.this,
                                        "信息删除失败", Toast.LENGTH_SHORT).show();
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

    private HashMap<String, Object> item;
    private ArrayList<HashMap<String, Object>> mxList;

    private void getload_infos() {
        add_mx_xh_save = add_mx_xh.getText().toString();
        add_mx_ph_save = add_mx_ph.getText().toString();
        add_mx_sh_save = add_mx_sh.getText().toString();
        add_mx_kw_save = add_mx_kw.getText().toString();
        add_mx_dw_save = add_mx_dw.getText().toString();
        add_mx_sl_save = add_mx_sl.getText().toString();
        add_mx_dj_save = add_mx_dj.getText().toString();
        add_mx_zk_save = add_mx_zk.getText().toString();
        add_mx_je_save = add_mx_je.getText().toString();
        add_mx_jgdh_save = add_mx_jgdh.getText().toString();
        add_mx_zcdj_save = add_mx_zcdj.getText().toString();
        add_mx_tybj_save = add_mx_tybj.getText().toString();
        add_mx_zdsj_save = add_mx_zdsj.getText().toString();
        add_mx_bzcb_save = add_mx_bzcb.getText().toString();
        add_mx_ygml_save = add_mx_ygml.getText().toString();
        add_mx_mll_save = add_mx_mll.getText().toString();
        add_mx_sdsl_save = add_mx_sdsl.getText().toString();
        add_mx_scsl_save = add_mx_scsl.getText().toString();
        add_mx_spgg_save = add_mx_spgg.getText().toString();
        add_mx_bzdw_save = add_mx_bzdw.getText().toString();
        add_mx_bzhs_save = add_mx_bzhs.getText().toString();
        add_mx_bzsl_save = add_mx_bzsl.getText().toString();
        add_mx_bzzl_save = add_mx_bzzl.getText().toString();
        add_mx_spzl_save = add_mx_spzl.getText().toString();
        add_mx_bzdx_save = add_mx_bzdx.getText().toString();
        add_mx_zy_save = add_mx_zy.getText().toString();
        if (add_mx_xh_save.equals(xh_hd)) {
            mxList.remove(flag_sc);
        }

        sAdapter.notifyDataSetChanged();
        item = new HashMap<String, Object>();
        item.put("序号", add_mx_xh_save);
        item.put("品名", "品名");
        item.put("品号", add_mx_ph_save);
        item.put("色号", add_mx_sh_save);
        item.put("库位", add_mx_kw_save);
        item.put("单位", add_mx_dw_save);
        item.put("数量", add_mx_sl_save);
        item.put("单价", add_mx_dj_save);
        item.put("折扣", add_mx_zk_save);
        item.put("金额", add_mx_je_save);
        item.put("价格代号", add_mx_jgdh_save);
        item.put("政策单价", add_mx_zcdj_save);

        item.put("统一标价", add_mx_tybj_save);
        item.put("最低售价", add_mx_zdsj_save);
        item.put("标准成本", add_mx_bzcb_save);
        item.put("预估毛利", add_mx_ygml_save);
        item.put("毛利率", add_mx_mll_save);
        item.put("受订数量", add_mx_sdsl_save);
        item.put("生产数量", add_mx_scsl_save);

        item.put("商品规格", add_mx_tybj_save);
        item.put("包装单位", add_mx_bzdw_save);
        item.put("包装换算", add_mx_bzhs_save);
        item.put("包装数量", add_mx_bzsl_save);
        item.put("包装重量", add_mx_bzzl_save);
        item.put("商品重量", add_mx_spzl_save);
        item.put("包装大小", add_mx_bzdx_save);
        mxList.add(item);
        Log.e("LiNing", "mxList-------" + mxList);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    String str_id = data.getStringExtra("condition_db");
                    String str_name = data.getStringExtra("condition_name");
                    bjd_tv_zt.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + str_name);
                }
                break;
            case 2:
                if (resultCode == 1) {
                    Quotation.QuotationList quotationList = (Quotation.QuotationList) data.getSerializableExtra("BJD_INFOS_ALL");
                    Log.e("LiNing", "提交的id====" + data.getSerializableExtra("BJD_INFOS_ALL"));
                    if (quotationList != null) {
                        if (quotationList.getQT_DD() != null) {
//                                SimpleDateFormat sf1 = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
                            SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            try {
                                Date parse = sf1.parse(quotationList.getQT_DD().toString());
                                String format = new SimpleDateFormat("yyyy-MM-dd").format(parse);
                                Log.e("LiNing", "时间====xin=====" + format);
                                bj_time.setText(format);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            bj_time.setText("");
                        }
                        if (quotationList.getChk_dd() != null) {
//                                SimpleDateFormat sf1 = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
                            SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            try {
                                Date parse = sf1.parse(quotationList.getChk_dd().toString());
                                String format = new SimpleDateFormat("yyyy-MM-dd").format(parse);
                                Log.e("LiNing", "时间====xin=====" + format);
                                sh_time.setText(format);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            sh_time.setText("");
                        }
                        if (quotationList.getAFFI_DD() != null) {
//                                SimpleDateFormat sf1 = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
                            SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            try {
                                Date parse = sf1.parse(quotationList.getAFFI_DD().toString());
                                String format = new SimpleDateFormat("yyyy-MM-dd").format(parse);
                                Log.e("LiNing", "时间====xin=====" + format);
                                qd_time.setText(format);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            qd_time.setText("");
                        }
                        if (quotationList.getQT_NO() != null) {

                            bjd_tv_bjdh.setText(quotationList.getQT_NO().toString());
//                                bjd_tv_bjdh.setText("you");
                        } else {
                            bjd_tv_bjdh.setText("");
                        }
                        if (quotationList.getCust_Src() != null) {
                            khly.setText(quotationList.getCust_Src().toString());

                        } else {
                            khly.setText("");

                        }
                        if (quotationList.getRemd() != null) {
                            tjmj.setText(quotationList.getRemd().toString());

                        } else {
                            tjmj.setText("");

                        }
                        if (quotationList.getBIL_TYPE() != null) {
                            djlb.setText(quotationList.getBIL_TYPE().toString());

                        } else {
                            djlb.setText("");

                        }
                        if (quotationList.getCUS_OS_NO() != null) {
                            htdh.setText(quotationList.getCUS_OS_NO().toString());

                        } else {
                            htdh.setText("");

                        }
                        if (quotationList.getCUS_NAME() != null) {
                            xszd.setText(quotationList.getCUS_NAME().toString());//

                        } else {
                            xszd.setText("");//

                        }
                        if (quotationList.getDEP_NAME() != null) {
                            xsbm.setText(quotationList.getDEP_NAME().toString());//

                        } else {
                            xsbm.setText("");//

                        }
                        if (quotationList.getSAL_NAME() != null) {
                            xsgw.setText(quotationList.getSAL_NAME().toString());//

                        } else {
                            xsgw.setText("");//

                        }
                        if (quotationList.getSAL_Tel() != null) {
                            gwdh.setText(quotationList.getSAL_Tel().toString());

                        } else {
                            gwdh.setText("");

                        }
                        if (quotationList.getSECE() != null) {
                            fwgj.setText(quotationList.getSECE().toString());

                        } else {
                            fwgj.setText("");

                        }
                        if (quotationList.getSECE_TEL() != null) {
                            gjdh.setText(quotationList.getSECE_TEL().toString());

                        } else {
                            gjdh.setText("");

                        }
                        if (quotationList.getSTYL() != null) {
                            ztsj.setText(quotationList.getSTYL().toString());

                        } else {
                            ztsj.setText("");

                        }
                        if (quotationList.getSTYL_TEL() != null) {
                            sjdh.setText(quotationList.getSTYL_TEL().toString());

                        } else {
                            sjdh.setText("");

                        }
                        if (quotationList.getPLAW() != null) {
                            styw.setText(quotationList.getPLAW().toString());

                        } else {
                            styw.setText("");

                        }
                        if (quotationList.getAFFI() != null) {
                            qdyw.setText(quotationList.getAFFI().toString());

                        } else {
                            qdyw.setText("");

                        }
                        if (quotationList.getDEP_RED() != null) {
                            yshk.setText(quotationList.getDEP_RED().toString());

                        } else {
                            yshk.setText("");

                        }
                        if (quotationList.getPRT_SW() != null) {
                            dyzt.setText(quotationList.getPRT_SW().toString());

                        } else {
                            dyzt.setText("");

                        }
                        if (quotationList.getCust_Name() != null) {
                            khxm.setText(quotationList.getCust_Name().toString());

                        } else {
                            khxm.setText("");

                        }
                        if (quotationList.getCust_Con() != null) {
                            khdh.setText(quotationList.getCust_Con().toString());

                        } else {
                            khdh.setText("");

                        }
                        if (quotationList.getHous_Type() != null) {
                            lplx.setText(quotationList.getHous_Type().toString());

                        } else {
                            lplx.setText("");

                        }
                        if (quotationList.getDeco_Com() != null) {
                            zxgs.setText(quotationList.getDeco_Com().toString());

                        } else {
                            zxgs.setText("");

                        }
                        if (quotationList.getSEND_MTH() != null) {
                            jhfs.setText(quotationList.getSEND_MTH().toString());

                        } else {
                            jhfs.setText("");

                        }
//                        if (quotationList.getQT_DD() != null) {
//                            xsdd.setText(quotationList.getQT_DD().toString());
//
//                        } else {
//                            xsdd.setText("");
//
//                        }
                        if (quotationList.getCon_Per() != null) {
                            shr.setText(quotationList.getCon_Per().toString());

                        } else {
                            shr.setText("");

                        }
                        if (quotationList.getCon_Tel() != null) {
                            shdh.setText(quotationList.getCon_Tel().toString());

                        } else {
                            shdh.setText("");

                        }
                        if (quotationList.getREM() != null) {
                            xxbz.setText(quotationList.getREM().toString());

                        } else {
                            xxbz.setText("");

                        }
                        if (quotationList.getCon_Add() != null) {
                            xxdz.setText(quotationList.getCon_Add().toString());

                        } else {
                            xxdz.setText("");

                        }
                        if (quotationList.getCon_Crt() != null) {
                            ss.setText(quotationList.getCon_Crt().toString());

                        } else {
                            ss.setText("");

                        }
                        if (quotationList.getCon_Spa() != null) {
                            qx.setText(quotationList.getCon_Spa().toString());

                        } else {
                            qx.setText("");

                        }
                        if (quotationList.getUsr() != null) {
                            tv_zdr.setText(quotationList.getUsr().toString());

                        } else {
                            tv_zdr.setText("");

                        }
                        if (quotationList.getUsr_chk() != null) {
                            tv_shenher.setText(quotationList.getUsr_chk().toString());

                        } else {
                            tv_shenher.setText("");

                        }
                        final List<Quotation.QuotationT> quotationT = quotationList.getQuotationT();
                        if (quotationT != null && quotationT.size() > 0) {

                            OfferActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    bjdQtyAdapter = new BjdQtyAdapter(R.layout.bjmxobj_head, quotationT, context);
                                    lv_offer_qry.setAdapter(bjdQtyAdapter);
                                    bjdQtyAdapter.notifyDataSetChanged();
                                }

                            });
                        }
                    } else {
                        Toast.makeText(context, "此账套无数据", Toast.LENGTH_LONG).show();
                    }


                }
                break;

            case 9:
                if (resultCode == 1) {
                    String str_id = data.getStringExtra("data_dep");
                    djlb_id_sub = data.getStringExtra("data_dep_id");
                    djlb.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + djlb_id_sub);
                }
                break;
            case 3:
                if (resultCode == 1) {
                    cust_callback = (CustAllObjectInfos.CustList) data.getSerializableExtra("CUST_QTY");
                    qty_cust_zt = cust_callback.getCust_Acc().toString();
                    qty_cust_dabh = cust_callback.getCust_No().toString();
                    if (cust_callback.getCust_Name() != null) {
                        khxm.setText(cust_callback.getCust_Name());
                    } else {
                        khxm.setText("");
                    }
                    if (cust_callback.getCust_Tel() != null) {
                        khdh.setText(cust_callback.getCust_Tel());
                    } else {
                        khdh.setText("");
                    }
                    if (cust_callback.getCust_Src() != null) {
                        khly.setText(cust_callback.getCust_Src());
                    } else {
                        khly.setText("");
                    }
                    if (cust_callback.getHous_Type() != null) {
                        lplx.setText(cust_callback.getHous_Type());
                    } else {
                        lplx.setText("");
                    }
                    if (cust_callback.getDeco_Com() != null) {
                        zxgs.setText(cust_callback.getDeco_Com());
                    } else {
                        zxgs.setText("");
                    }
                }
                break;
            case 4:
                if (resultCode == 1) {
                    String str_shr = data.getStringExtra("PSXX_SHR");
                    shr.setText(str_shr);
                    String str_shdh = data.getStringExtra("PSXX_SHDH");
                    shdh.setText(str_shdh);
                    String str_ss = data.getStringExtra("PSXX_SS");
                    ss.setText(str_ss);
                    String str_qx = data.getStringExtra("PSXX_QX");
                    qx.setText(str_qx);
                    String str_xxzd = data.getStringExtra("PSXX_DZ");
                    xxdz.setText(str_xxzd);

                }
                break;
            case 10:
                if (resultCode == 1) {
                    String str_id = data.getStringExtra("data_return");
                    zd_id_sub = data.getStringExtra("data_return_ids");
                    xszd.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + zd_id_sub);
                }
                break;
            case 11:
                if (resultCode == 1) {
                    String str_id = data.getStringExtra("data_return");
                    bm_id_sub = data.getStringExtra("data_return_ids");
                    xsbm.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + bm_id_sub);
                }
                break;
            case 12:
                if (resultCode == 1) {
                    String str_id = data.getStringExtra("data_return");
                    gw_id_sub = data.getStringExtra("data_return_ids");
                    xsgw.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + gw_id_sub);
                }
                break;
            case 13:
                if (resultCode == 1) {
                    String str_id = data.getStringExtra("data_return");
                    fwgj_id_sub = data.getStringExtra("data_return_ids");
                    fwgj.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + fwgj_id_sub);
                }
                break;
            case 14:
                if (resultCode == 1) {
                    String str_id = data.getStringExtra("data_return");
                    ztsj_id_sub = data.getStringExtra("data_return_ids");
                    ztsj.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + ztsj_id_sub);
                }
                break;
            case 15:
                if (resultCode == 1) {
                    String str_id = data.getStringExtra("data_return");
                    styw_id_sub = data.getStringExtra("data_return_ids");
                    styw.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + styw_id_sub);
                }
                break;
            case 16:
                if (resultCode == 1) {
                    String str_id = data.getStringExtra("data_return");
                    qdyw_id_sub = data.getStringExtra("data_return_ids");
                    qdyw.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + qdyw_id_sub);
                }
                break;
        }
    }

    class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {
        ListViewAndHeadViewTouchLinstener() {
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ((HorizontalScrollView) OfferActivity.this.mHead
                    .findViewById(R.id.horizontalScrollView1))
                    .onTouchEvent(event);
            return false;
        }

    }

    public class BjdQtyAdapter extends BaseAdapter {
        int id_row_layout;
        LayoutInflater mInflater;
        private List<Quotation.QuotationT> infos_content;

        public BjdQtyAdapter(int bjmxobj_head, List<Quotation.QuotationT> quotationT, Context context) {
            this.id_row_layout = bjmxobj_head;
            this.infos_content = quotationT;
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return infos_content.size();
        }

        @Override
        public Object getItem(int position) {
            return infos_content.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                synchronized (OfferActivity.this) {
                    convertView = mInflater.inflate(id_row_layout, null);
                    holder = new ViewHolder();
                    MyHScrollView scrollView1 = (MyHScrollView) convertView
                            .findViewById(R.id.horizontalScrollView1);
                    holder.scrollView = scrollView1;
                    //点击新增或编辑显示
//                    holder.checkbox = (CheckBox) convertView
//                            .findViewById(R.id.bjmx_listDeleteCheckBox);
//                    holder.checkbox.setVisibility(View.VISIBLE);
                    holder.mx_xh = (TextView) convertView.findViewById(R.id.textView1_id_bjmxxh);
                    holder.mx_pm = (TextView) convertView.findViewById(R.id.textView2_bjmxpm);
                    holder.mx_ph = (TextView) convertView.findViewById(R.id.textView3_bjmxph);
                    holder.mx_sh = (TextView) convertView.findViewById(R.id.textView4_bjmxsh);
                    holder.mx_kw = (TextView) convertView.findViewById(R.id.textView5_bjmxkw);
                    holder.mx_dw = (TextView) convertView.findViewById(R.id.textView6_bjmxdw);
                    holder.mx_sl = (TextView) convertView.findViewById(R.id.textView7_bjmxsl);
                    holder.mx_dj = (TextView) convertView.findViewById(R.id.textView8_bjmxdj);
                    holder.mx_zk = (TextView) convertView.findViewById(R.id.tv_bjmxzk);
                    holder.mx_je = (TextView) convertView.findViewById(R.id.tv_bjmxje);
                    holder.mx_jgdh = (TextView) convertView.findViewById(R.id.tv_bjmxjgdh);
                    holder.mx_zcdj = (TextView) convertView.findViewById(R.id.tv_bjmxzcdj);
                    holder.mx_tybj = (TextView) convertView.findViewById(R.id.tv_bjmxtybj);
                    holder.mx_zdsj = (TextView) convertView.findViewById(R.id.tv_bjmxzdsj);
                    holder.mx_bzcb = (TextView) convertView.findViewById(R.id.tv_bjmxbzcb);
                    holder.mx_ygml = (TextView) convertView.findViewById(R.id.tv_bjmxygml);
                    holder.mx_mll = (TextView) convertView.findViewById(R.id.tv_bjmxmll);
                    holder.mx_sdsl = (TextView) convertView.findViewById(R.id.tv_bjmxsdsl);
                    holder.mx_scsl = (TextView) convertView.findViewById(R.id.tv_bjmxscsl);

                    holder.mx_spgg = (TextView) convertView.findViewById(R.id.tv_bjmxspgg);
                    holder.mx_bzdw = (TextView) convertView.findViewById(R.id.tv_bjmxbzdw);
                    holder.mx_bzhs = (TextView) convertView.findViewById(R.id.tv_bjmxbzhs);
                    holder.mx_bzsl = (TextView) convertView.findViewById(R.id.tv_bjmxbzsl);
                    holder.mx_bzzl = (TextView) convertView.findViewById(R.id.tv_bjmxbzzl);
                    holder.mx_spzl = (TextView) convertView.findViewById(R.id.tv_bjmxspzl);
                    holder.mx_bzdx = (TextView) convertView.findViewById(R.id.tv_bjmxbzdx);
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
            Quotation.QuotationT quotationT_mx = infos_content.get(position);
            if (quotationT_mx.getITM() != null) {
                holder.mx_xh.setText(quotationT_mx.getITM().toString());

            } else {
                holder.mx_xh.setText("");

            }
            if (quotationT_mx.getPRD_NAME() != null) {
                holder.mx_pm.setText(quotationT_mx.getPRD_NAME().toString());

            } else {
                holder.mx_pm.setText("");

            }
            if (quotationT_mx.getPRD_NO() != null) {
                holder.mx_ph.setText(quotationT_mx.getPRD_NO().toString());

            } else {
                holder.mx_ph.setText("");

            }

            if (quotationT_mx.getRPD_MARK() != null) {
                holder.mx_sh.setText(quotationT_mx.getRPD_MARK().toString());

            } else {
                holder.mx_sh.setText("");

            }
            if (quotationT_mx.getWH() != null) {
                holder.mx_kw.setText(quotationT_mx.getWH().toString());

            } else {
                holder.mx_kw.setText("");

            }
            if (quotationT_mx.getUNIT() != null) {
                holder.mx_dw.setText(quotationT_mx.getUNIT().toString());

            } else {
                holder.mx_dw.setText("");

            }
            if (quotationT_mx.getQTY() != null) {
                holder.mx_sl.setText(quotationT_mx.getQTY().toString());

            } else {
                holder.mx_sl.setText("");

            }
            if (quotationT_mx.getUP() != null) {
                holder.mx_dj.setText(quotationT_mx.getUP().toString());

            } else {
                holder.mx_dj.setText("");

            }
            if (quotationT_mx.getDIS_CNT() != null) {
                holder.mx_zk.setText(quotationT_mx.getDIS_CNT().toString());

            } else {
                holder.mx_zk.setText("");

            }
            if (quotationT_mx.getAMTN() != null) {
                holder.mx_je.setText(quotationT_mx.getAMTN().toString());

            } else {
                holder.mx_je.setText("");

            }
            if (quotationT_mx.getPRICE_ID() != null) {
                holder.mx_jgdh.setText(quotationT_mx.getPRICE_ID().toString());

            } else {
                holder.mx_jgdh.setText("");

            }
            if (quotationT_mx.getPrdUp() != null) {
                holder.mx_zcdj.setText(quotationT_mx.getPrdUp().toString());

            } else {
                holder.mx_zcdj.setText("");

            }
            if (quotationT_mx.getUPR() != null) {
                holder.mx_tybj.setText(quotationT_mx.getUPR().toString());

            } else {
                holder.mx_tybj.setText("");

            }
            if (quotationT_mx.getUP_MIN() != null) {
                holder.mx_zdsj.setText(quotationT_mx.getUP_MIN().toString());

            } else {
                holder.mx_zdsj.setText("");

            }
            if (quotationT_mx.getCST_STD() != null) {
                holder.mx_bzcb.setText(quotationT_mx.getCST_STD().toString());

            } else {
                holder.mx_bzcb.setText("");

            }
            if (quotationT_mx.getEST_SUB() != null) {
                holder.mx_ygml.setText(quotationT_mx.getEST_SUB().toString());

            } else {
                holder.mx_ygml.setText("");

            }
            if (quotationT_mx.getSUB_GPR() != null) {
                holder.mx_mll.setText(quotationT_mx.getSUB_GPR().toString());

            } else {
                holder.mx_mll.setText("");

            }
            if (quotationT_mx.getOS_QTY() != null) {
                holder.mx_sdsl.setText(quotationT_mx.getOS_QTY().toString());

            } else {
                holder.mx_sdsl.setText("");

            }
            if (quotationT_mx.getSC_QTY() != null) {
                holder.mx_scsl.setText(quotationT_mx.getSC_QTY().toString());

            } else {
                holder.mx_scsl.setText("");

            }

            if (quotationT_mx.getSPC() != null) {
                holder.mx_spgg.setText(quotationT_mx.getSPC().toString());

            } else {
                holder.mx_spgg.setText("");

            }
            if (quotationT_mx.getPAK_UNIT() != null) {
                holder.mx_bzdw.setText(quotationT_mx.getPAK_UNIT().toString());

            } else {
                holder.mx_bzdw.setText("");

            }
            if (quotationT_mx.getPAK_EXC() != null) {
                holder.mx_bzhs.setText(quotationT_mx.getPAK_EXC().toString());

            } else {
                holder.mx_bzhs.setText("");

            }
            if (quotationT_mx.getPAK_QTY() != null) {
                holder.mx_bzsl.setText(quotationT_mx.getPAK_QTY().toString());

            } else {
                holder.mx_bzsl.setText("");

            }
            if (quotationT_mx.getPAK_NW() != null) {
                holder.mx_bzzl.setText(quotationT_mx.getPAK_NW().toString());

            } else {
                holder.mx_bzzl.setText("");

            }
            if (quotationT_mx.getCOM_NW() != null) {
                holder.mx_spzl.setText(quotationT_mx.getCOM_NW().toString());

            } else {
                holder.mx_spzl.setText("");

            }
            if (quotationT_mx.getPAKG() != null) {
                holder.mx_bzdx.setText(quotationT_mx.getPAKG().toString());

            } else {
                holder.mx_bzdx.setText("");

            }
            return convertView;
        }

        class ViewHolder {
            HorizontalScrollView scrollView;
            public CheckBox checkbox;
            public TextView mx_xh;
            public TextView mx_pm;
            public TextView mx_ph;
            public TextView mx_sh;
            public TextView mx_kw;
            public TextView mx_dw;
            public TextView mx_sl;
            public TextView mx_dj;
            public TextView mx_zk;
            public TextView mx_je;
            public TextView mx_jgdh;
            public TextView mx_zcdj;
            public TextView mx_tybj;
            public TextView mx_zdsj;
            public TextView mx_bzcb;
            public TextView mx_ygml;
            public TextView mx_mll;
            public TextView mx_sdsl;
            public TextView mx_scsl;

            public TextView mx_spgg;
            public TextView mx_bzdw;
            public TextView mx_bzhs;
            public TextView mx_bzsl;
            public TextView mx_bzzl;
            public TextView mx_spzl;
            public TextView mx_bzdx;
        }
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

    public void allback(View v) {
        finish();
    }
}
