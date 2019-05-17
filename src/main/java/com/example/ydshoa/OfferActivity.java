package com.example.ydshoa;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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
import android.widget.ImageView;
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
import com.example.bean.DepInfo;
import com.example.bean.DesignAllInfos;
import com.example.bean.JsonRootBean;
import com.example.bean.NetUtil;
import com.example.bean.PriceLoadInfo;
import com.example.bean.PriceNumID;
import com.example.bean.QuotationTwo;
import com.example.bean.URLS;
import com.example.bean.WebService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
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
//    http://oa.ydshce.com:8523/InfManagePlatform/ImgOfSellManagerment/Quotation/QT1905090008_kh1811040005_DB_BJ15.png(图片拼接地址)
    private Context context;
    private SharedPreferences sp;
    private String session,pathSignature;
    private static final int PHOTO_REQUEST_GALLERY = 8;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 9;// 结果
    String date_dd, date_dd_dh, db_zt, erp_yh, qty_cust_zt, qty_cust_dabh, jhfs_id,ywlx_id,vip_no_cust;
    LinearLayout touch;
    String url_dh_bjxx = URLS.price_num_ls;
    private TextView head, khxm, khdh, lplx, zxgs, jhfs, xsdd, shr, shdh, ss, qx, xxdz, bj_time, bjd_tv_bjdh, bjd_tv_zt, khly, tjmj, djlb, xszd, xsbm, xsgw,
            fwgj, ztsj, styw, qdyw,  dyzt, tv_shenher, tv_zdr,ywlx;
    private EditText xxbz, htdh, gwdh, gjdh, sjdh,yshk;
    private Button offer_query, offer_add, offer_set, offer_del, offer_prite, offer_save, offer_qd, offer_out, offer_tszc, offer_bjmx;
    private ImageButton add_mx_zcsp_ib,add_mx_ph_ib, ib_zt_xl, ib_djlb_xl,ib_ywlx_xl, ib_ztsj_xl, ib_xszd_xl, ib_xsbm_xl, ib_xsgw_xl, ib_fwgj_xl, ib_styw_xl, ib_qdyw_xl, ib_yshk_xl, ib_kh_xl,
            ib_jhfs_xl, ib_shr_xl, ib_tjmj_xl;
    BjdQtyAdapter bjdQtyAdapter;
    private ListView lv_offer_qry;
    RelativeLayout mHead;
    AlertDialog addBjmx_all;
    //报价单明细
    List<QuotationTwo.QuotationT> quotationT;
    QuotationTwo.QuotationT quotationT_item;
    private TextView  add_mx_bzdw, add_mx_bzhs, add_mx_bzsl, add_mx_bzzl, add_mx_spzl,
            add_mx_je, add_mx_jgdh, add_mx_zcdj, add_mx_tybj, add_mx_zdsj,add_mx_tszc,
            add_mx_bzcb, add_mx_ygml, add_mx_mll, add_mx_kw, add_mx_dw, add_mx_xh, add_mx_ph, add_mx_sh, sh_time, qd_time;
    private EditText add_mx_spgg,add_mx_bzdx,add_mx_sl, add_mx_dj, add_mx_zk, add_mx_sdsl, add_mx_zy,add_mx_scsl,add_mx_sddh,add_mx_llsl;
    private String add_mx_xh_save, add_mx_ph_save, add_mx_sh_save, add_mx_kw_save, add_mx_dw_save, add_mx_sl_save, add_mx_dj_save, add_mx_zk_save, add_mx_je_save, add_mx_jgdh_save, add_mx_zcdj_save, add_mx_tybj_save, add_mx_zdsj_save, add_mx_bzcb_save, add_mx_ygml_save, add_mx_mll_save, add_mx_sdsl_save, add_mx_scsl_save,
            add_mx_sddh_save,add_mx_llsl_save ,add_mx_tszc_save,
    add_mx_spgg_save, add_mx_bzdw_save, add_mx_bzhs_save, add_mx_bzsl_save, add_mx_bzzl_save, add_mx_spzl_save, add_mx_bzdx_save, add_mx_zy_save, add_mx_wjfj_save;
    private String djlb_id_sub, zd_id_sub, bm_id_sub, gw_id_sub, fwgj_id_sub, ztsj_id_sub, styw_id_sub, qdyw_id_sub, pm_nmae_sub;
    String url_bj_add = URLS.cust_bjxx_add;
    String url_bj_del = URLS.cust_bjxx_del;
    String url_bj_updata = URLS.cust_bjxx_update;
    private String url_employee = URLS.employee_url;//name转id
    private String url_idTodep = URLS.ERP_dep_url;//部门
    private String url_idTocust = URLS.ERP_cust_url;//终端+1
    private String url_idTodjlb = URLS.djlb_url;//单据类别
    String url_dh_toname = URLS.design_toname;//设计师
    String url_dabh_toname = URLS.id_Custbh;//客户编号

    String url_do;
    String url_load_price = URLS.price_load;//货品加载
    CustAllObjectInfos.CustList cust_callback;
    private SimpleAdapter sAdapter;
    private List<CheckBox> checkBoxList;
    private List<Integer> checkedIndexList;
    private int checkFalg, flag_sc, save_check, checked;
    String xh_hd, pm_hd, ph_hd, sh_hd, kw_hd, dw_hd, sl_hd, dj_hd, zk_hd, je_hd, jgdh_hd, zcdj_hd, tybj_hd, zdsj_hd, bzcb_hd, ygml_hd, mll_hd, sdsl_hd, scsl_hd,llsl_hd,sddh_hd ,spgg_hd, bzdw_hd, bzhs_hd, bzsl_hd, bzzl_hd, spzl_hd,
            bzdx_hd,zy_hd,tszc_hd;
    //主表信息(提交)
    String post_zt, post_bj_time, post_bjd_tv_bjdh, post_khly, post_tjmj, post_djlb, post_htdh, post_xszd, post_xsbm, post_xsgw, post_gwdh, post_fwgj, post_gjdh,
            post_ztsj, post_sjdh,post_ywlx, post_styw, post_qdyw, post_yshk, post_dyzt, post_khxm, post_khdh, post_lplx, post_zxgs, post_jhfs, post_xsdd, post_shr,
            post_shdh, post_ss, post_qx, post_xxdz, post_xxbz, post_sh_time, post_qd_time, post_zdr, post_shenher;
    //明细表信息（提交）
    ArrayList<String> list_xh, list_pm, list_ph, list_sh, list_kw, list_dw, list_sl, list_dj, list_zk, list_je, list_jgdh, list_zcdj, list_tybj,
            list_zdsj, list_bzcb, list_ygml, list_mll, list_sdsl, list_scsl,list_sddh,list_llsl, list_spgg, list_bzdw, list_bzhs, list_bzsl, list_bzzl, list_spzl, list_bzdx,list_tszc,list_zy;
    String sub_xh, sub_pm, sub_ph, sub_sh, sub_kw, sub_dw, sub_sl, sub_dj, sub_zk, sub_je, sub_jgdh, sub_zcdj, sub_tybj, sub_zdsj, sub_bzcb, sub_ygml, sub_mll, sub_sdsl, sub_scsl, sub_spgg, sub_bzdw, sub_bzhs, sub_bzsl, sub_bzzl, sub_spzl, sub_bzdx
            ,sub_zy,sub_tszc,sub_sddh,sub_llsl;
    boolean ischeck_khxm,ischeck_khly,ischeck_ywlx,ischeck_jhfs,ischeck_djlb, ischeck_xsqd, ischeck_xsbm, ischeck_xsgw, ischeck_fwgj, ischeck_ztsj, ischeck_styw, ischeck_qdyw ,ischeck_xj = false;
    String xszd_id_cx, xsbm_id_cx, xsgw_id_cx;
    //是否点击传入id
    String id_djlb_zh,xszd_z, xsbm_z, xsgw_z, fwgj_z, ztsj_z, styw_z, qdyw_z, idtoname_fwgj, idtoname_ztsj, idtoname_styw, idtoname_qdyw,djlb_z,
    ywlx_hd_zh,jhfs_hd_zh,ywlx_z,jhfs_z,kh_bh_hd,khxm_z;
    private File file_sighn;
    private ImageView  signatureImg;
    private ImageButton signatureAdd;
    private Bitmap bitmap_xc;
    private String img_path;

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
        signatureAdd = (ImageButton) findViewById(R.id.ib_signatureadd);
        signatureImg = ((ImageView) findViewById(R.id.iv_fj));
        signatureAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ischeck_xj=true;
                // 激活系统图库，选择一张图片
                Intent intentPhoto = new Intent(Intent.ACTION_PICK);
                intentPhoto.setType("image/*");
                // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
                startActivityForResult(intentPhoto, PHOTO_REQUEST_GALLERY);
            }
        });
        ywlx = (TextView) findViewById(R.id.tv_offer_all_ywlx);
        khly = (TextView) findViewById(R.id.et_offer_all_khly);
        tjmj = (EditText) findViewById(R.id.et_offer_all_tjmj);
        djlb = (TextView) findViewById(R.id.et_offer_all_djlb);
        htdh = (EditText) findViewById(R.id.et_offer_all_htdh);
        xszd = (TextView) findViewById(R.id.et_offer_all_xszd);
        xsbm = (TextView) findViewById(R.id.et_offer_all_xsbm);
        xsgw = (TextView) findViewById(R.id.et_offer_all_xsgw);
        fwgj = (TextView) findViewById(R.id.et_offer_all_fwgj);
        ztsj = (TextView) findViewById(R.id.et_offer_all_ztsj);
        styw = (TextView) findViewById(R.id.et_offer_all_styw);
        qdyw = (TextView) findViewById(R.id.tv_offer_all_qdyw);
        yshk = (EditText) findViewById(R.id.et_offer_all_yshk);
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
        ib_ywlx_xl = (ImageButton) findViewById(R.id.ib_offer_all_ywlx);
        ib_ywlx_xl.setOnClickListener(this);
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
        //vip会员（调接口vip接口）
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
        //推荐媒介（调pop）
        ib_tjmj_xl = (ImageButton) findViewById(R.id.ib_offer_all_tjmj);
        ib_tjmj_xl.setOnClickListener(this);
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
        ph_id_list = new ArrayList();
        ph_id_list_xg = new ArrayList();
        checkedIndexList = new ArrayList<Integer>();
        checkBoxList = new ArrayList<CheckBox>();
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
        list_sddh = new ArrayList<String>();
        list_llsl = new ArrayList<String>();

        list_spgg = new ArrayList<String>();
        list_bzdw = new ArrayList<String>();
        list_bzhs = new ArrayList<String>();
        list_bzsl = new ArrayList<String>();
        list_bzzl = new ArrayList<String>();
        list_spzl = new ArrayList<String>();
        list_bzdx = new ArrayList<String>();
        list_zy = new ArrayList<String>();
        list_tszc = new ArrayList<String>();
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
                    startActivityForResult(intent_djlb, 5);
                    ischeck_djlb = true;//用于判断是否点击
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
                    ischeck_xsqd = true;//用于判断是否点击
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
                    ischeck_xsbm = true;//用于判断是否点击
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
                    ischeck_xsgw = true;//用于判断是否点击
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
                    ischeck_fwgj = true;//用于判断是否点击
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
                    ischeck_ztsj = true;//用于判断是否点击
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
                    ischeck_styw = true;//用于判断是否点击
                }
                break;
            //确单业务-16---修改为设计师
            case R.id.ib_offer_all_qdyw:
                if (bjd_tv_zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {

                    Intent intent_vip = new Intent(context, QueryDesigActivity.class);
                    intent_vip.putExtra("ZT_VIP", bjd_tv_zt.getText().toString());
                    intent_vip.putExtra("CUST_DO", "2");
                    startActivityForResult(intent_vip, 16);
                    ischeck_qdyw = true;//用于判断是否点击
                }
                break;
            case R.id.ib_offer_all_khxm:
                //判断权限
//                if (kh_query_qx == true) {
//
//                } else {
//                    Toast.makeText(context, "请等待...", Toast.LENGTH_LONG).show();
//                }
                ischeck_khxm = true;//用于判断是否点击
                //调取查询客户的界面
                Intent intent_cust = new Intent(context, QueryCustersActivity.class);
                intent_cust.putExtra("ZT_VIP", bjd_tv_zt.getText().toString());
                intent_cust.putExtra("ZT_zdwd", "CUST");
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
            case R.id.ib_mx_add_ph:
                //调取查询品号的界面
                if (bjd_tv_zt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {

                    Intent intent_mxph = new Intent(context,
                            ErpDepInfoActivity.class);
                    intent_mxph.putExtra("flag", "18");
                    intent_mxph.putExtra("DB_ID", bjd_tv_zt.getText().toString());
                    startActivityForResult(intent_mxph, 18);
                }
                break;
            //特殊政策
            case R.id.ib_mx_add_zcsp:
                checked = 4;
                showPopupMenu(add_mx_zcsp_ib);
                break;
            //预收货款
            case R.id.ib_offer_all_yshk:
                Toast.makeText(context,"调取预收货款的接口",Toast.LENGTH_LONG).show();
                break;
            //销售订单
//            case R.id.ib_offer_all_xsdd:
//                Toast.makeText(context,"调取想销售订单的接口",Toast.LENGTH_LONG).show();
//                break;
            //交货方式
            case R.id.ib_offer_all_jhfs:
                ischeck_jhfs = true;//用于判断是否点击
                checked = 2;
                showPopupMenu(ib_jhfs_xl);
                break;
            //业务类型
            case R.id.ib_offer_all_ywlx:
                ischeck_ywlx = true;//用于判断是否点击
                checked = 3;
                showPopupMenu(ib_ywlx_xl);
                break;
            //推荐媒介
            case R.id.ib_offer_all_tjmj:
//                if (!khly.getText().toString().equals("")) {
//                    Log.e("LiNing", "---" + khly.getText().toString());
//                    ib_tjmj_xl.setFocusable(true);
//                    checked = 1;
//                    showPopupMenu(ib_tjmj_xl);
//                } else {
//                    ib_tjmj_xl.setFocusable(false);
//                }
                ischeck_khly= true;//用于判断是否点击
                checked = 1;
                showPopupMenu(ib_tjmj_xl);
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
                //新增
                save_check = 1;
                clearInfos();

                break;
            case R.id.btn_offer_all_reset:
                //修改
                save_check = 2;
                //主表信息不可编辑
                ib_zt_xl.setEnabled(false);
                bjd_tv_zt.setEnabled(false);
                touch.setEnabled(false);
//                resetBjd();
                break;
            //保存
            case R.id.btn_offer_all_save:
                if(lv_offer_qry.getCount()<0){
                    Toast.makeText(context,"请添加明细信息",Toast.LENGTH_LONG).show();
                }else{
                    getPostInfos();
                    addBjd_ys();
                }
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
//                    Toast.makeText(context, "请等待...", Toast.LENGTH_LONG).show();
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
                if (checkedIndexList != null
                        && checkedIndexList.size() > 1) {
                    Toast.makeText(context, "请选择单条数据", Toast.LENGTH_LONG).show();
                } else {

                    View bjd_addmx = getLayoutInflater()
                            .inflate(R.layout.bjd_mx_add, null);
                    TextView head_load_all = (TextView) bjd_addmx.findViewById(R.id.all_head);
                    head_load_all.setText("报价单-明细");

                    add_mx_xh = (TextView) bjd_addmx.findViewById(R.id.tv_mx_add_xh);
                    add_mx_ph = (TextView) bjd_addmx.findViewById(R.id.tv_mx_add_ph);
                    add_mx_sh = (TextView) bjd_addmx.findViewById(R.id.tv_mx_add_sh);
                    add_mx_kw = (TextView) bjd_addmx.findViewById(R.id.tv_mx_add_kw);
                    add_mx_dw = (TextView) bjd_addmx.findViewById(R.id.tv_mx_add_dw);
                    add_mx_sl = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_sl);
                    add_mx_dj = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_dj);
                    add_mx_zk = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_zk);
                    add_mx_je = (TextView) bjd_addmx.findViewById(R.id.tv_mx_add_je);
                    add_mx_jgdh = (TextView) bjd_addmx.findViewById(R.id.tv_mx_add_jgdh);
                    add_mx_zcdj = (TextView) bjd_addmx.findViewById(R.id.tv_mx_add_zcdj);
                    add_mx_tybj = (TextView) bjd_addmx.findViewById(R.id.tv_mx_add_tybj);
                    add_mx_zdsj = (TextView) bjd_addmx.findViewById(R.id.tv_mx_add_zdsj);
                    add_mx_bzcb = (TextView) bjd_addmx.findViewById(R.id.tv_mx_add_bzcb);
                    add_mx_ygml = (TextView) bjd_addmx.findViewById(R.id.tv_mx_add_ygml);
                    add_mx_mll = (TextView) bjd_addmx.findViewById(R.id.tv_mx_add_mll);
                    add_mx_sddh = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_sddh);
                    add_mx_sdsl = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_sdsl);
                    add_mx_scsl = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_scsl);
                    add_mx_llsl = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_llsl);
                    add_mx_spgg = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_spgg);
                    add_mx_bzdw = (TextView) bjd_addmx.findViewById(R.id.tv_mx_add_bzdw);
                    add_mx_bzhs = (TextView) bjd_addmx.findViewById(R.id.tv_mx_add_bzhs);
                    add_mx_bzsl = (TextView) bjd_addmx.findViewById(R.id.tv_mx_add_bzsl);
                    add_mx_bzzl = (TextView) bjd_addmx.findViewById(R.id.tv_mx_add_bzzl);
                    add_mx_spzl = (TextView) bjd_addmx.findViewById(R.id.tv_mx_add_spzl);
                    add_mx_bzdx = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_bzdx);
                    add_mx_zy = (EditText) bjd_addmx.findViewById(R.id.tv_mx_add_zy);
                    add_mx_tszc = (TextView) bjd_addmx.findViewById(R.id.tv_mx_add_zcsp);
                    add_mx_tszc.setText("否");

                    add_mx_ph_ib = (ImageButton) bjd_addmx.findViewById(R.id.ib_mx_add_ph);
                    add_mx_ph_ib.setOnClickListener(this);
                    add_mx_zcsp_ib = (ImageButton) bjd_addmx.findViewById(R.id.ib_mx_add_zcsp);
                    add_mx_zcsp_ib.setOnClickListener(this);

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
                        add_mx_sddh.setText(sddh_hd);
                        add_mx_llsl.setText(llsl_hd);
                        add_mx_spgg.setText(spgg_hd);
                        add_mx_bzdw.setText(bzdw_hd);
                        add_mx_bzhs.setText(bzhs_hd);
                        add_mx_bzsl.setText(bzsl_hd);
                        add_mx_bzzl.setText(bzzl_hd);
                        add_mx_spzl.setText(spzl_hd);
                        add_mx_bzdx.setText(bzdx_hd);
                        add_mx_zy.setText(zy_hd);
                        add_mx_tszc.setText(tszc_hd);
                    } else {
                        if (save_check == 1) {
                            //判断明细listview的数量
                            if (mxList == null) {
                                add_mx_xh.setText("1");
                            } else {
                                int i = mxList.size() + 1;
                                add_mx_xh.setText("" + i);
                            }
                        } else if (save_check == 2) {
                            int i = lv_offer_qry.getCount() + 1;
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
//                        sAdapter.notifyDataSetChanged();

                        }
                    });
                    bjd_addmx.findViewById(R.id.btn_mx_del).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (save_check == 1) {
                                //删除
                                mxList.remove(flag_sc);
                                addBjmx_all.dismiss();
                                sAdapter.notifyDataSetChanged();
                            } else {
                                quotationT.remove(quotationT_item);
                                checkedIndexList.clear();
                                bjdQtyAdapter.notifyDataSetChanged();
                                lv_offer_qry.invalidate();
                                addBjmx_all.dismiss();
                            }
                        }
                    });

                    addBjmx_all = new AlertDialog.Builder(context).create();
                    addBjmx_all.setCancelable(false);
                    addBjmx_all.setView(bjd_addmx);
                    addBjmx_all.show();
                    break;
                }
        }
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        if (checked == 2) {

            popupMenu.getMenuInflater().inflate(R.menu.jhfs, popupMenu.getMenu());
        } else if (checked == 1) {
            popupMenu.getMenuInflater().inflate(R.menu.cust_khly_info, popupMenu.getMenu());

        }else if (checked == 3) {
            popupMenu.getMenuInflater().inflate(R.menu.ywlx_pop, popupMenu.getMenu());

        }else if (checked == 4) {
            popupMenu.getMenuInflater().inflate(R.menu.sfhz, popupMenu.getMenu());

        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.check1_jhfs
                        || menuItem.getItemId() == R.id.check2_jhfs
                        || menuItem.getItemId() == R.id.check3_jhfs
                        || menuItem.getItemId() == R.id.check4_jhfs
                        || menuItem.getItemId() == R.id.check5_jhfs
                        || menuItem.getItemId() == R.id.check6_jhfs
                        ) {
                    menuItem.setChecked(!menuItem.isChecked());
                    jhfs_id = menuItem.getTitle().toString().substring(0, 1);
                    Log.e("LiNing", "------新增的数据id" + jhfs_id);
                    jhfs.setText(menuItem.getTitle());
                    return true;
                } else if (menuItem.getItemId() == R.id.check1_khly
                        || menuItem.getItemId() == R.id.check2_khly
                        || menuItem.getItemId() == R.id.check3_khly
                        || menuItem.getItemId() == R.id.check4_khly
                        || menuItem.getItemId() == R.id.check5_khly
                        || menuItem.getItemId() == R.id.check6_khly
                        || menuItem.getItemId() == R.id.check7_khly
                        || menuItem.getItemId() == R.id.check8_khly
                        ) {
                    menuItem.setChecked(!menuItem.isChecked());
                    khly.setText(menuItem.getTitle());
                } else if (menuItem.getItemId() == R.id.check1_ywlx
                        || menuItem.getItemId() == R.id.check2_ywlx
                        ) {
                    menuItem.setChecked(!menuItem.isChecked());
                    ywlx_id = menuItem.getTitle().toString().substring(0, 2);
                    Log.e("LiNing", "------新增的数据ywlx_id" + ywlx_id);
                    ywlx.setText(menuItem.getTitle());
                }else if (menuItem.getItemId() == R.id.check1
                        || menuItem.getItemId() == R.id.check2
                        ) {
                    menuItem.setChecked(!menuItem.isChecked());
                    add_mx_tszc.setText(menuItem.getTitle());
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
        post_ywlx= ywlx.getText().toString();
        post_qdyw = qdyw.getText().toString();//设计师
        post_khly = khly.getText().toString();
        post_tjmj = tjmj.getText().toString();
        post_djlb = djlb.getText().toString();
        post_htdh = htdh.getText().toString();
        post_xszd = xszd.getText().toString();
        post_xsbm = xsbm.getText().toString();
        post_xsgw = xsgw.getText().toString();
        post_fwgj = fwgj.getText().toString();
        post_ztsj = ztsj.getText().toString();
        post_styw = styw.getText().toString();

        post_yshk = yshk.getText().toString();
        post_dyzt = dyzt.getText().toString();
        post_khxm = khxm.getText().toString();
        post_khdh = khdh.getText().toString();
        post_lplx = lplx.getText().toString();
        post_zxgs = zxgs.getText().toString();
        post_jhfs = jhfs.getText().toString();
        post_xsdd = xsdd.getText().toString();//配送列号
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

        // 明细表数据
        if (save_check == 1) {
            if (mxList != null && mxList.size() > 0) {
                for (int i = 0; i < mxList.size(); i++) {

                        String post_xh_hd = mxList.get(i).get("序号").toString();
                        list_xh.add(post_xh_hd);
                    String post_pm_hd = mxList.get(i).get("品名").toString();
                    list_pm.add(post_pm_hd);
                    String post_ph_hd = mxList.get(i).get("品号").toString();
                    list_ph.add(post_ph_hd);
                    String post_sh_hd = mxList.get(i).get("色号").toString();
                    if(!post_sh_hd.equals("")||post_sh_hd.equals("null")){

                        list_sh.add(post_sh_hd);
                    }else{

                        list_sh.add("SH");
                    }
                    String post_kw_hd = mxList.get(i).get("库位").toString();
                    list_kw.add(post_kw_hd);
                    String post_dw_hd = mxList.get(i).get("单位").toString();
                    if(!post_sh_hd.equals("")||post_sh_hd.equals("null")){

                    list_dw.add(post_dw_hd);
                    }else{

                        list_dw.add("1");
                    }
                    String post_sl_hd = mxList.get(i).get("数量").toString();
                    list_sl.add(post_sl_hd);
                    String post_dj_hd = mxList.get(i).get("单价").toString();
                    list_dj.add(post_dj_hd);
                    String post_zk_hd = mxList.get(i).get("折扣").toString();
                    list_zk.add(post_zk_hd);
                    String post_je_hd = mxList.get(i).get("金额").toString();
                    list_je.add(post_je_hd);
                    String post_jgdh_hd = mxList.get(i).get("政策代号").toString();
                    if(!post_jgdh_hd.equals("")||post_jgdh_hd.equals("null")){
                        list_jgdh.add(post_jgdh_hd);
                    }else{
                        list_jgdh.add("01");


                    }
                    String post_zcdj_hd = mxList.get(i).get("政策单价").toString();
                    if(!post_zcdj_hd.equals("")||post_zcdj_hd.equals("null")){
                        list_zcdj.add(post_zcdj_hd);
                    }else{

                        list_zcdj.add("100");
                    }

                    String post_tybj_hd = mxList.get(i).get("统一标价").toString();
                    list_tybj.add(post_tybj_hd);
                    String post_zdsj_hd = mxList.get(i).get("最低售价").toString();
                    list_zdsj.add(post_zdsj_hd);
                    String post_bzcb_hd = mxList.get(i).get("标准成本").toString();
                    list_bzcb.add(post_bzcb_hd);
                    String post_ygml_hd = mxList.get(i).get("预估毛利").toString();
                    list_ygml.add(post_ygml_hd);
                    String post_mll_hd = mxList.get(i).get("毛利率").toString();
                    if(!post_mll_hd.equals("")||post_mll_hd.equals("null")){

                        list_mll.add(post_mll_hd);
                    }else {

                        list_mll.add("0");
                    }
                    String post_sdsl_hd = mxList.get(i).get("受订数量").toString();
                    if(!post_sdsl_hd.equals("")||post_sdsl_hd.equals("null")){

                        list_sdsl.add(post_sdsl_hd);
                    }else {

                        list_sdsl.add("0");
                    }
                    String post_sddh_hd = mxList.get(i).get("受订单号").toString();
                    if(!post_sddh_hd.equals("")||post_sddh_hd.equals("null")){

                        list_sddh.add(post_sddh_hd);
                    }else {

                        list_sddh.add("00");
                    }
                    String post_scsl_hd = mxList.get(i).get("生产单号").toString();
                    if(!post_scsl_hd.equals("")||post_scsl_hd.equals("null")){

                        list_scsl.add(post_scsl_hd);
                    }else {

                        list_scsl.add("01");
                    }
                    String post_llsl_hd = mxList.get(i).get("领料数量").toString();
                    if(!post_scsl_hd.equals("")||post_scsl_hd.equals("null")){

                        list_llsl.add(post_llsl_hd);
                    }else{

                        list_llsl.add("0");
                    }
                    String post_spgg_hd = mxList.get(i).get("商品规格").toString();
                    if(!post_spgg_hd.equals("")||post_spgg_hd.equals("null")){

                        list_spgg.add(post_spgg_hd);
                    }else{
                        list_spgg.add("1*1*1");
                    }
                    String post_bzdd_hd = mxList.get(i).get("包装单位").toString();
                    list_bzdw.add(post_bzdd_hd);
                    String post_bzhs_hd = mxList.get(i).get("包装换算").toString();
                    list_bzhs.add(post_bzhs_hd);
                    String post_bzsl_hd = mxList.get(i).get("包装数量").toString();
                    list_bzsl.add(post_bzsl_hd);
                    String post_bzzl_hd = mxList.get(i).get("包装重量").toString();
                    list_bzzl.add(post_bzzl_hd);
                    String post_spzl_hd = mxList.get(i).get("商品重量").toString();
                    if(!post_scsl_hd.equals("")||post_scsl_hd.equals("null")){

                    list_spzl.add(post_spzl_hd);
                    }else {

                        list_spzl.add("0");
                    }
                    String post_bzdx_hd = mxList.get(i).get("包装大小").toString();
                    if(!post_scsl_hd.equals("")||post_scsl_hd.equals("null")){

                    list_bzdx.add(post_bzdx_hd);
                    }else {

                        list_bzdx.add("1");
                    }
                    String post_zy_hd = mxList.get(i).get("摘要").toString();
                    if(!post_scsl_hd.equals("")||post_scsl_hd.equals("null")){

                    list_zy.add(post_zy_hd);
                    }else {
                        list_zy.add("备注信息");
                    }

                    String post_zc_hd = mxList.get(i).get("特殊政策").toString();
                    if(!post_zc_hd.equals("")||post_zc_hd.equals("null")){
                        if(post_zc_hd.equals("是")){
                            list_tszc.add("T");
                        }else {

                            list_tszc.add("F");
                        }
                    }else {

                        list_tszc.add("F");
                    }
                }
            }

        } else if (save_check == 2) {
            if (quotationT != null && quotationT.size() > 0) {
                for (int i = 0; i < quotationT.size(); i++) {
                    if(quotationT.get(i).getITM()!=null){

                        String post_xh_hd = quotationT.get(i).getITM().toString();
                        list_xh.add(post_xh_hd);
                    }else {
                        list_xh.add("");
                    }
                    if(quotationT.get(i).getPRD_NAME()!=null){

                        String post_pm_hd = quotationT.get(i).getPRD_NAME().toString();
                        list_pm.add(post_pm_hd);
                    }else {
                        list_pm.add("");
                    }
                    if(quotationT.get(i).getPRD_NO()!=null){
                        String post_ph_hd = quotationT.get(i).getPRD_NO().toString();
                        list_ph.add(post_ph_hd);
                    }else{
                        list_ph.add("");
                    }

                    if(quotationT.get(i).getPRD_MARK()!=null){
                        String post_sh_hd = quotationT.get(i).getPRD_MARK().toString();
                        list_sh.add(post_sh_hd);
                    }else{
                        list_sh.add("");
                    }

                    if(quotationT.get(i).getWH()!=null){
                        String post_kw_hd = quotationT.get(i).getWH().toString();
                        list_kw.add(post_kw_hd);
                    }else{
                        list_kw.add("0");
                    }

                    if(quotationT.get(i).getUNIT()!=null){
                        String post_dw_hd = quotationT.get(i).getUNIT().toString();
                    list_dw.add(post_dw_hd);
                    }else{
                        list_dw.add("1");

                    }

                    if(quotationT.get(i).getQTY()!=null){
                        String post_sl_hd = quotationT.get(i).getQTY().toString();
                        list_sl.add(post_sl_hd);
                    }else{
                        list_sl.add("0");
                    }

                    if(quotationT.get(i).getUP()!=null){
//                        String post_dj_hd = quotationT.get(i).getUP().toString();
                        String post_dj_hd = ""+new BigDecimal(quotationT.get(i).getUP().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        list_dj.add(post_dj_hd);
                    }else{
                        list_dj.add("0");
                    }

                    if(quotationT.get(i).getDIS_CNT()!=null){
//                        String post_zk_hd = quotationT.get(i).getDIS_CNT().toString();
                        String post_zk_hd = ""+new BigDecimal(quotationT.get(i).getDIS_CNT().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        list_zk.add(post_zk_hd);
                    }else{
                        list_zk.add("1");
                    }

                    if(quotationT.get(i).getAMTN()!=null){
//                        String post_je_hd = quotationT.get(i).getAMTN().toString();
                        String post_je_hd = ""+new BigDecimal(quotationT.get(i).getAMTN().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        list_je.add(post_je_hd);
                    }else{
                        list_je.add("0");
                    }

                    if(quotationT.get(i).getPRICE_ID()!=null){
                        String post_jgdh_hd = quotationT.get(i).getPRICE_ID().toString();
                        list_jgdh.add(post_jgdh_hd);
                    }else{
                        list_jgdh.add("0");
                    }

                    if(quotationT.get(i).getPrdUp()!=null){
//                        String post_zcdj_hd = quotationT.get(i).getPrdUp().toString();
                        String post_zcdj_hd = ""+new BigDecimal(quotationT.get(i).getPrdUp().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        list_zcdj.add(post_zcdj_hd);
                    }else{
                        list_zcdj.add("0");
                    }

                    if(quotationT.get(i).getUPR()!=null){
//                        String post_tybj_hd = quotationT.get(i).getUPR().toString();
                        String post_tybj_hd = ""+new BigDecimal(quotationT.get(i).getUPR().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        list_tybj.add(post_tybj_hd);
                    }else{
                        list_tybj.add("0");
                    }

                    if(quotationT.get(i).getUP_MIN()!=null){
//                        String post_zdsj_hd = quotationT.get(i).getUP_MIN().toString();
                        String post_zdsj_hd = ""+new BigDecimal(quotationT.get(i).getUP_MIN().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        list_zdsj.add(post_zdsj_hd);
                    }else{
                        list_zdsj.add("0");
                    }

                    if(quotationT.get(i).getCST_STD()!=null){
//                        String post_bzcb_hd = quotationT.get(i).getCST_STD().toString();
                        String post_bzcb_hd = ""+new BigDecimal(quotationT.get(i).getCST_STD().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        list_bzcb.add(post_bzcb_hd);
                    }else{
                        list_bzcb.add("0");
                    }

                    if(quotationT.get(i).getEST_SUB()!=null){
//                        String post_ygml_hd = quotationT.get(i).getEST_SUB().toString();
                        String post_ygml_hd = ""+new BigDecimal(quotationT.get(i).getEST_SUB().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        list_ygml.add(post_ygml_hd);
                    }else{
                        list_ygml.add("0");
                    }

                    if(quotationT.get(i).getSUB_GPR()!=null){
//                        String post_mll_hd = quotationT.get(i).getSUB_GPR().toString();
                        String post_mll_hd = ""+new BigDecimal(quotationT.get(i).getSUB_GPR().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        list_mll.add(post_mll_hd);
                    }else{
                        list_mll.add("0");
                    }

                    if(quotationT.get(i).getOS_NO()!=null){
                        String post_sddh_hd = quotationT.get(i).getOS_NO().toString();
                        list_sddh.add(post_sddh_hd);
                    }else{
                        list_sddh.add("");
                    }

                    if(quotationT.get(i).getOS_QTY()!=null){
                        String post_sdsl_hd = quotationT.get(i).getOS_QTY().toString();
                        list_sdsl.add(post_sdsl_hd);
                    }else{
                        list_sdsl.add("0");
                    }

                    if(quotationT.get(i).getSPC_NO()!=null){
                        String post_scsl_hd = quotationT.get(i).getSPC_NO().toString();
                        list_scsl.add(post_scsl_hd);
                    }else{
                        list_scsl.add("0");
                    }

                    if(quotationT.get(i).getSPC_QTY()!=null){
                        String post_llsl_hd = quotationT.get(i).getSPC_QTY().toString();
                        list_llsl.add(post_llsl_hd);
                    }else{
                        list_llsl.add("0");
                    }

                    if(quotationT.get(i).getSPC()!=null){
                        String post_spgg_hd = quotationT.get(i).getSPC().toString();
                        list_spgg.add(post_spgg_hd);
                    }else{
                        list_spgg.add("0*0*0");
                    }

                    if(quotationT.get(i).getPAK_UNIT()!=null){
                        String post_bzdd_hd = quotationT.get(i).getPAK_UNIT().toString();
                        list_bzdw.add(post_bzdd_hd);
                    }else{
                        list_bzdw.add("箱");
                    }

                    if(quotationT.get(i).getPAK_EXC()!=null){
//                        String post_bzhs_hd = quotationT.get(i).getPAK_EXC().toString();
                        String post_bzhs_hd = ""+new BigDecimal(quotationT.get(i).getPAK_EXC().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        list_bzhs.add(post_bzhs_hd);
                    }else{
                        list_bzhs.add("1");
                    }

                    if(quotationT.get(i).getPAK_QTY()!=null){
                        String post_bzsl_hd = quotationT.get(i).getPAK_QTY().toString();
                        list_bzsl.add(post_bzsl_hd);
                    }else{
                        list_bzsl.add("0");
                    }

                    if(quotationT.get(i).getPAK_NW()!=null){
//                        String post_bzzl_hd = quotationT.get(i).getPAK_NW().toString();
                        String post_bzzl_hd = ""+new BigDecimal(quotationT.get(i).getPAK_NW().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        list_bzzl.add(post_bzzl_hd);
                    }else{
                        list_bzzl.add("0");
                    }

                    if(quotationT.get(i).getPAK_GW()!=null){
//                        String post_spzl_hd = quotationT.get(i).getPAK_GW().toString();
                        String post_spzl_hd = ""+new BigDecimal(quotationT.get(i).getPAK_GW().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        list_spzl.add(post_spzl_hd);
                    }else{
                        list_spzl.add("0");
                    }

                    if(quotationT.get(i).getPAK_MEAST()!=null){
//                        String post_bzdx_hd = quotationT.get(i).getPAK_MEAST().toString();
                        String post_bzdx_hd = ""+new BigDecimal(quotationT.get(i).getPAK_MEAST().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        list_bzdx.add(post_bzdx_hd);
                    }else{
                        list_bzdx.add("0");
                    }

                    if(quotationT.get(i).getREMT()!=null){
                        String post_zy_hd = quotationT.get(i).getREMT().toString();
                        list_zy.add(post_zy_hd);
                    }else{
                        list_zy.add("");
                    }

                    if(quotationT.get(i).getISLP()!=null){
                        String post_tszc_hd = quotationT.get(i).getISLP().toString();
                        list_tszc.add(post_tszc_hd);
                    }else{
                        list_tszc.add("F");
                    }


                }
            }

        }

    }

    private void clearInfos() {

        bj_time.setText(date_dd);
        bjd_tv_bjdh.setText("");
        khly.setText("");
        tjmj.setText("");
        djlb.setText("");
        htdh.setText("");
        xszd.setText("");
        xsbm.setText("");
        xsgw.setText("");
        fwgj.setText("");
        ztsj.setText("");
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
        sh_time.setText(date_dd);
        qd_time.setText(date_dd);
        tv_shenher.setText("ADMIN");
        tv_zdr.setText("ADMIN");
        lv_offer_qry.setAdapter(null);
        mxList.clear();
        checkedIndexList.clear();
        //提交的数据集合
        list_xh.clear();
        list_pm.clear();
        list_ph.clear();
        list_sh.clear();
        list_kw.clear();
        list_dw.clear();
        list_sl.clear();
        list_dj.clear();
        list_zk.clear();
        list_je.clear();
        list_jgdh.clear();
        list_zcdj.clear();
        list_tybj.clear();
        list_zdsj.clear();
        list_bzcb.clear();
        list_ygml.clear();
        list_mll.clear();
        list_spgg.clear();
        list_scsl.clear();
        list_llsl.clear();
        list_bzdw.clear();
        list_bzhs.clear();
        list_bzhs.clear();
        list_bzsl.clear();
        list_spzl.clear();
        list_bzdx.clear();
//        list_zy.clear();
//        list_tszc.clear();
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

        String sddhs_str = "";
        for (String name : list_sddh) {
            sddhs_str += name + ",";
        }
        sub_sddh = sddhs_str.substring(0,
                sddhs_str.length() - 1);
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
        String llsls_str = "";
        for (String name : list_llsl) {
            llsls_str += name + ",";
        }
        sub_llsl = llsls_str.substring(0,
                llsls_str.length() - 1);
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
        String bzzys_str = "";
        for (String name : list_zy) {
            bzzys_str += name + ",";
        }
        sub_zy = bzzys_str.substring(0,
                bzzys_str.length() - 1);
        String tszcs_str = "";
        for (String name : list_tszc) {
            tszcs_str += name + ",";
        }
        sub_tszc = tszcs_str.substring(0,
                tszcs_str.length() - 1);
        //此处判断新增和修改的标识
        if (save_check == 1) {
            //新增
            url_do = url_bj_add;
        } else if (save_check == 2) {
            //修改
            url_do = url_bj_updata;
        }
        //判断不能为空数据
        //判断是否点击（点击传id,没点击获取id或转换）
        if (ischeck_djlb== false) {
            djlb_z = id_djlb_zh;
        } else {
            djlb_z = djlb_id_sub;
        }
        if (ischeck_ywlx== false) {
            ywlx_z = ywlx_hd_zh;
        } else {
            ywlx_z = ywlx_id;
        }
        if (ischeck_jhfs== false) {
            jhfs_z = jhfs_hd_zh;
        } else {
            jhfs_z = jhfs_id;
        }
        if (ischeck_xsqd == false) {
            xszd_z = xszd_id_cx;
        } else {
            xszd_z = zd_id_sub;
        }
        if (ischeck_xsbm == false) {
            xsbm_z = xsbm_id_cx;
        } else {
            xsbm_z = bm_id_sub;
        }
        if (ischeck_xsgw == false) {
            xsgw_z = xsgw_id_cx;
        } else {
            xsgw_z = gw_id_sub;
        }
        if (ischeck_fwgj == false) {
            fwgj_z = idtoname_fwgj;
        } else {
            fwgj_z = fwgj_id_sub;
        }
        if (ischeck_ztsj == false) {
            ztsj_z = idtoname_ztsj;
        } else {
            ztsj_z = ztsj_id_sub;
        }
        if (ischeck_styw == false) {
            styw_z = idtoname_styw;
        } else {
            styw_z = styw_id_sub;
        }
        if (ischeck_qdyw == false) {
            qdyw_z = idtoname_qdyw;
        } else {
            qdyw_z = vip_no_cust;
        }
        if (ischeck_khxm == false) {
            khxm_z = kh_bh_hd;
        } else {
            khxm_z = qty_cust_dabh;
        }
        //t图片
        if (save_check == 1) {
            //新增
            //图片还需解析
            if(pathSignature==null){
                pathSignature="";
                file_sighn = new File(pathSignature);
                Log.e("LiNing", "pathSignature－－－－－－－－" + pathSignature);
            }else{

                file_sighn = new File(pathSignature);
            }
            Log.e("LiNing", "file_sighn" + file_sighn);
        } else if (save_check == 2) {
            //修改
            Log.e("LiNing", "img_path" + img_path);
            if(ischeck_xj==false){
                file_sighn = new File(img_path);
            }else {
                //图片还需解析
                if(pathSignature==null){
                    pathSignature="";
                    file_sighn = new File(pathSignature);
                    Log.e("LiNing", "pathSignature－－－－－－－－" + pathSignature);
                }else{

                    file_sighn = new File(pathSignature);
                }
                Log.e("LiNing", "file_sighn" + file_sighn);
            }

        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 创建一个URL对象
                try {
                    URL url = new URL(url_do);
                    Map<String, String> textParams = new HashMap<String, String>();
                    Map<String, File> fileparams = new HashMap<String, File>();

                    textParams.put("DB_ID", post_zt);
                    textParams.put("BZ_TYPE", ywlx_z);//业务类型
                    textParams.put("QT_DD", post_bj_time);//时间
                    textParams.put("QT_NO", post_bjd_tv_bjdh);//报价单号
                    textParams.put("Cust_Src", post_khly);//客户来源
                    textParams.put("Remd", post_tjmj);//推荐媒介
                    textParams.put("BIL_TYPE", djlb_z);//单据类别vip
                    textParams.put("Cus_No", xszd_z);//终端编号
                    textParams.put("DEP_NO", xsbm_z);//部门编号


                    textParams.put("SAL_NO", xsgw_z);//顾问编号
                    textParams.put("SER_NO", fwgj_z);//服务管家
                    textParams.put("STYL_NO", ztsj_z);//制图设计
                    textParams.put("AFFI_NO", styw_z);//生产业务
                    textParams.put("Vip_NO", qdyw_z);//设计师
                    textParams.put("DEP_RED", post_yshk);//预收货款
                    textParams.put("PRT_SW", post_dyzt);//打印状态
                    textParams.put("KH_NO", khxm_z);//客户编号
                    textParams.put("Cust_Tel", post_khdh);//客户电话
                    textParams.put("Hous_Type", post_lplx);//楼盘类型
                    textParams.put("Deco_Com", post_zxgs);//装修公司
                    textParams.put("SEND_MTH", jhfs_z);//交货方式1，送货
                    textParams.put("CUS_OS_NO", post_htdh);//合同单号
                    textParams.put("CUS_CON_ITM", post_xsdd);//配送列号
                    textParams.put("Con_Per", post_shr);//收货人
                    textParams.put("Con_Tel", post_shdh);//收货电话
                    textParams.put("Con_Crt", post_ss);//省市
                    textParams.put("Con_Spa", post_qx);//区县
                    textParams.put("Con_Add", post_xxdz);//详细地址
                    textParams.put("REM", post_xxbz);//备注
                    textParams.put("CHK_DD", post_sh_time);//审核日期
                    textParams.put("AFFI_DD", post_qd_time);//确单日期
                    textParams.put("USR", post_zdr);//制单人
                    textParams.put("USR_CHK", post_shenher);//审核人
                    //明细
                    textParams.put("ITM", sub_xh);//行号
                    textParams.put("PRD_NAME", sub_pm);//品名
                    textParams.put("PRD_NO", sub_ph);//品号
                    textParams.put("PRD_MARK", sub_sh);//色号
                    textParams.put("WH", sub_kw);//库位
                    textParams.put("UNIT", sub_dw);//单位不能传汉子“套
                    textParams.put("QTY", sub_sl);//数量
                    textParams.put("UP", sub_dj);//单价
                    textParams.put("DIS_CNT", sub_zk);//折扣
                    textParams.put("AMTN", sub_je);//金额
                    textParams.put("PRICE_ID", sub_jgdh);//政策代号
                    textParams.put("prdUp", sub_zcdj);//政策单价

                    textParams.put("UPR", sub_tybj);//统一标价
                    textParams.put("UP_MIN", sub_zdsj);//最低售价
                    textParams.put("CST_STD", sub_bzcb);//标准成本
                    textParams.put("EST_SUB", sub_ygml);//预估毛利
                    textParams.put("SUB_GPR", sub_mll);//毛利率
                    textParams.put("OS_NO", sub_sddh);//受订单号
                    textParams.put("OS_QTY", sub_sdsl);//受订数量
                    textParams.put("SPC_NO", sub_scsl);//生产单号
                    textParams.put("SPC_QTY", sub_llsl);//领料数量
                    textParams.put("SPC", sub_spgg);//商品规格
                    textParams.put("PAK_UNIT", sub_bzdw);//包装单位
                    textParams.put("PAK_EXC", sub_bzhs);//包装换算
                    textParams.put("PAK_QTY", sub_bzsl);//包装数量
                    textParams.put("PAK_NW", sub_bzzl);//包装净重
                    textParams.put("PAK_GW", sub_spzl);//包装毛重
                    textParams.put("PAK_MEAST", sub_bzdx);//包装大小
                    textParams.put("REMT", sub_zy);//摘要
                    textParams.put("ISLP", sub_tszc);//是否是特殊政策


                    Log.e("LiNing", "user数据*******" + textParams);
                    if (!file_sighn.equals("") && file_sighn != null
                            && file_sighn.exists()) {

                        fileparams.put("FILES", file_sighn);
                    }
                    Log.e("LiNing", "签章文件***********" + fileparams);
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
                    if (!file_sighn.equals("") && file_sighn != null
                            && file_sighn.exists()) {

                        NetUtil.writeFileParams(fileparams, ds);
                    }
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
//                        如果为true清空数据
                        if(resultStr.equals("true")){
                            clearInfos();
                        }
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
                                clearInfos();
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
    private ArrayList<String> ph_id_list, ph_id_list_xg;

    private void getload_infos() {
        Log.e("LiNing","---------"+add_mx_xh.getText().toString());
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
        add_mx_sddh_save = add_mx_sddh.getText().toString();
        add_mx_sdsl_save = add_mx_sdsl.getText().toString();
        add_mx_scsl_save = add_mx_scsl.getText().toString();
        add_mx_llsl_save = add_mx_llsl.getText().toString();
        add_mx_spgg_save = add_mx_spgg.getText().toString();
        add_mx_bzdw_save = add_mx_bzdw.getText().toString();
        add_mx_bzhs_save = add_mx_bzhs.getText().toString();
        add_mx_bzsl_save = add_mx_bzsl.getText().toString();
        add_mx_bzzl_save = add_mx_bzzl.getText().toString();
        add_mx_spzl_save = add_mx_spzl.getText().toString();
        add_mx_bzdx_save = add_mx_bzdx.getText().toString();
        add_mx_zy_save = add_mx_zy.getText().toString();
        add_mx_tszc_save = add_mx_tszc.getText().toString();
        //修改的操作
        if (save_check == 2) {
//            QuotationT qt_xg = new QuotationT();
            Log.e("LiNing","---------"+add_mx_xh.getText().toString());
            QuotationTwo.QuotationT qt_xg = new QuotationTwo.QuotationT();
            qt_xg.setITM(add_mx_xh_save);
            qt_xg.setPRD_NAME(pm_nmae_sub);
            qt_xg.setPRD_NO(add_mx_ph_save);
            qt_xg.setPRD_MARK(add_mx_sh_save);
            qt_xg.setWH(add_mx_kw_save);
            qt_xg.setUNIT(add_mx_dw_save);
            qt_xg.setQTY(add_mx_sl_save);
            qt_xg.setUP(add_mx_dj_save);
            qt_xg.setDIS_CNT(add_mx_zk_save);
            qt_xg.setAMTN(add_mx_je_save);
            qt_xg.setPRICE_ID(add_mx_jgdh_save);
            qt_xg.setPrdUp(add_mx_zcdj_save);
            qt_xg.setUPR(add_mx_tybj_save);
            qt_xg.setUP_MIN(add_mx_zdsj_save);
            qt_xg.setCST_STD(add_mx_bzcb_save);
            qt_xg.setEST_SUB(add_mx_ygml_save);
            qt_xg.setSUB_GPR(add_mx_mll_save);
            qt_xg.setOS_NO(add_mx_sddh_save);
            qt_xg.setOS_QTY(add_mx_sdsl_save);
            qt_xg.setSPC_NO(add_mx_scsl_save);
            qt_xg.setSPC_QTY(add_mx_llsl_save);
            qt_xg.setSPC(add_mx_spgg_save);
            qt_xg.setPAK_UNIT(add_mx_bzdw_save);
            qt_xg.setPAK_EXC(add_mx_bzhs_save);
            qt_xg.setPAK_QTY(add_mx_bzsl_save);
            qt_xg.setPAK_NW(add_mx_bzzl_save);
            qt_xg.setPAK_GW(add_mx_spzl_save);
            qt_xg.setPAK_MEAST(add_mx_bzdx_save);
            qt_xg.setREMT(add_mx_zy_save);
            qt_xg.setISLP(add_mx_tszc_save);

            //判断是否包含此数据
            for (int j = 0; j < quotationT.size(); j++) {
                String ph_cx_xg = quotationT.get(j).getPRD_NO().toString();
                ph_id_list_xg.add(ph_cx_xg);
            }
            if (ph_id_list_xg.contains(add_mx_ph_save)) {
                Toast.makeText(context, "已包含此数据", Toast.LENGTH_LONG).show();
            } else {
                if (add_mx_xh_save.equals(xh_hd)) {
                    quotationT.remove(quotationT_item);
                    quotationT.add(qt_xg);
                    bjdQtyAdapter.notifyDataSetChanged();
                    addBjmx_all.dismiss();
                    lv_offer_qry.invalidate();
                } else {
                    quotationT.add(qt_xg);
                    bjdQtyAdapter.notifyDataSetChanged();
                    addBjmx_all.dismiss();
                    lv_offer_qry.invalidate();
                }
            }

        } else if (save_check == 1) {
            for (int i = 0; i < mxList.size(); i++) {
                String ph_lv = mxList.get(i).get("品号").toString();
                ph_id_list.add(ph_lv);
            }
            if (ph_id_list.contains(add_mx_ph_save)) {

                Toast.makeText(context, "已包含此数据", Toast.LENGTH_LONG).show();
            } else {
                if (add_mx_xh_save.equals(xh_hd)) {
                    mxList.remove(flag_sc);
                    sAdapter.notifyDataSetChanged();
                }

                item = new HashMap<String, Object>();
                item.put("序号", add_mx_xh_save);

                item.put("品名", pm_nmae_sub);
                item.put("品号", add_mx_ph_save);
                item.put("色号", add_mx_sh_save);
                item.put("库位", add_mx_kw_save);
                item.put("单位", add_mx_dw_save);
                item.put("数量", add_mx_sl_save);
                item.put("单价", add_mx_dj_save);
                item.put("折扣", add_mx_zk_save);
                item.put("金额", add_mx_je_save);
                item.put("政策代号", add_mx_jgdh_save);
                item.put("政策单价", add_mx_zcdj_save);

                item.put("统一标价", add_mx_tybj_save);
                item.put("最低售价", add_mx_zdsj_save);
                item.put("标准成本", add_mx_bzcb_save);
                item.put("预估毛利", add_mx_ygml_save);
                item.put("毛利率", add_mx_mll_save);
                item.put("受订单号", add_mx_sddh_save);
                item.put("受订数量", add_mx_sdsl_save);
                item.put("生产单号", add_mx_scsl_save);
                item.put("领料数量", add_mx_llsl_save);

                item.put("商品规格", add_mx_spgg_save);
                item.put("包装单位", add_mx_bzdw_save);
                item.put("包装换算", add_mx_bzhs_save);
                item.put("包装数量", add_mx_bzsl_save);
                item.put("包装重量", add_mx_bzzl_save);
                item.put("商品重量", add_mx_spzl_save);
                item.put("包装大小", add_mx_bzdx_save);
                item.put("摘要", add_mx_zy_save);
                item.put("特殊政策", add_mx_tszc_save);
                mxList.add(item);

                Log.e("LiNing", "mxList-------" + mxList);
                addBjmx_all.dismiss();
            }
            sAdapter = new SimpleAdapter(context, mxList,
                    R.layout.bjmxobj_head, new String[]{"序号", "品名",
                    "品号", "色号", "库位", "单位", "数量", "单价", "折扣", "金额", "政策代号", "政策单价"
                    , "统一标价", "最低售价", "标准成本", "预估毛利", "毛利率", "受订单号","受订数量", "生产单号", "领料数量"
                    , "商品规格", "包装单位", "包装换算", "包装数量", "包装重量", "商品重量", "包装大小", "摘要", "特殊政策"}, new int[]{
                    R.id.textView1_id_bjmxxh, R.id.textView2_bjmxpm,
                    R.id.textView3_bjmxph, R.id.textView4_bjmxsh,
                    R.id.textView5_bjmxkw, R.id.textView6_bjmxdw,
                    R.id.textView7_bjmxsl, R.id.textView8_bjmxdj,
                    R.id.tv_bjmxzk, R.id.tv_bjmxje,
                    R.id.tv_bjmxjgdh, R.id.tv_bjmxzcdj,
                    R.id.tv_bjmxtybj, R.id.tv_bjmxzdsj,
                    R.id.tv_bjmxbzcb, R.id.tv_bjmxygml,
                    R.id.tv_bjmxmll, R.id.tv_bjmxsddh,
                    R.id.tv_bjmxsdsl,R.id.tv_bjmxscsl,R.id.tv_bjmxllsl,
                    R.id.tv_bjmxspgg, R.id.tv_bjmxbzdw,
                    R.id.tv_bjmxbzhs, R.id.tv_bjmxbzsl,
                    R.id.tv_bjmxbzzl, R.id.tv_bjmxspzl,
                    R.id.tv_bjmxbzdx,R.id.tv_bjmxzy,R.id.tv_bjmxtszc}) {
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
                                        jgdh_hd = mxList.get(flag_sc).get("政策代号").toString();
                                        zcdj_hd = mxList.get(flag_sc).get("政策单价").toString();
                                        tybj_hd = mxList.get(flag_sc).get("统一标价").toString();
                                        zdsj_hd = mxList.get(flag_sc).get("最低售价").toString();
                                        bzcb_hd = mxList.get(flag_sc).get("标准成本").toString();
                                        ygml_hd = mxList.get(flag_sc).get("预估毛利").toString();
                                        mll_hd = mxList.get(flag_sc).get("毛利率").toString();
                                        sddh_hd = mxList.get(flag_sc).get("受订单号").toString();
                                        sdsl_hd = mxList.get(flag_sc).get("受订数量").toString();
                                        scsl_hd = mxList.get(flag_sc).get("生产单号").toString();
                                        llsl_hd = mxList.get(flag_sc).get("领料数量").toString();

                                        spgg_hd = mxList.get(flag_sc).get("商品规格").toString();
                                        bzdw_hd = mxList.get(flag_sc).get("包装单位").toString();
                                        bzhs_hd = mxList.get(flag_sc).get("包装换算").toString();
                                        bzsl_hd = mxList.get(flag_sc).get("包装数量").toString();
                                        bzzl_hd = mxList.get(flag_sc).get("包装重量").toString();
                                        spzl_hd = mxList.get(flag_sc).get("商品重量").toString();
                                        bzdx_hd = mxList.get(flag_sc).get("包装大小").toString();
                                        zy_hd = mxList.get(flag_sc).get("摘要").toString();
                                        tszc_hd = mxList.get(flag_sc).get("特殊政策").toString();
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

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

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
                    QuotationTwo.QuotationList quotationList = (QuotationTwo.QuotationList) data.getSerializableExtra("BJD_INFOS_ALL");
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
                        if (quotationList.getCHK_DD() != null) {
//                                SimpleDateFormat sf1 = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
                            SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            try {
                                Date parse = sf1.parse(quotationList.getCHK_DD().toString());
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
                        if (quotationList.getBZ_TYPE()!= null) {
                            ywlx_hd_zh = quotationList.getBZ_TYPE().toString();
                            if(quotationList.getBZ_TYPE().toString().equals("01")){

                                ywlx.setText("01,常规");
                            }
                            if(quotationList.getBZ_TYPE().toString().equals("02")){

                                ywlx.setText("02,定制");
                            }
                        } else {
                            ywlx.setText("");

                        }
                        if (quotationList.getCust_Src() != null) {
                            khly.setText(quotationList.getCust_Src().toString());
                        } else {
                            khly.setText("");

                        }
                        if (quotationList.getCUS_CON_ITM() != null) {
                            xsdd.setText(quotationList.getCUS_CON_ITM().toString());
                        } else {
                            xsdd.setText("");

                        }
                        if (quotationList.getRemd() != null) {
                            tjmj.setText(quotationList.getRemd().toString());
//                            tjmj.setText(quotationList.getCust_Src().toString());
//                            tjmj.setText(khly.getText().toString());
                            //name转id
                        } else {
                            tjmj.setText(khly.getText().toString());
//                            tjmj.setText(quotationList.getCust_Src().toString());

                        }
                        if (quotationList.getBIL_TYPE() != null) {
                            id_djlb_zh = quotationList.getBIL_TYPE().toString();
                            OkHttpClient client = new OkHttpClient();
                            FormBody body = new FormBody.Builder().add("accountNo", bjd_tv_zt.getText().toString())
                                    .add("id", quotationList.getBIL_TYPE().toString()).build();
                            Request request = new Request.Builder()
                                    .addHeader("cookie", session).url(url_idTodjlb).post(body)
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
                                        OfferActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                List<DepInfo.IdNameList> idNameList = dInfo.getIdNameList();
                                                if (idNameList != null && idNameList.size() > 0) {
                                                    String s = idNameList.get(0).getName().toString();
                                                     djlb.setText(s);
                                                } else {
                                                    djlb.setText("");
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
                            djlb.setText("");

                        }
                        if (quotationList.getCUS_OS_NO() != null) {
                            htdh.setText(quotationList.getCUS_OS_NO().toString());

                        } else {
                            htdh.setText("");

                        }
                        if (quotationList.getCus_No() != null) {
//                            xszd.setText(quotationList.getCUS_NAME().toString());//转换
                            xszd_id_cx = quotationList.getCus_No().toString();
                            OkHttpClient client = new OkHttpClient();
                            FormBody body = new FormBody.Builder().add("accountNo", bjd_tv_zt.getText().toString())
                                    .add("custType", "" + 1)
                                    .add("id", xszd_id_cx).build();
                            Request request = new Request.Builder()
                                    .addHeader("cookie", session).url(url_idTocust).post(body)
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
                                        OfferActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                List<DepInfo.IdNameList> idNameList = dInfo.getIdNameList();
                                                if (idNameList != null && idNameList.size() > 0) {
                                                    String xsgw_hd_name = idNameList.get(0).getName().toString();
                                                    xszd.setText(xsgw_hd_name);
                                                } else {
                                                    xszd.setText(xszd_z);
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
                            xszd.setText("");//

                        }
                        if (quotationList.getDEP_NO() != null) {
//                            xsbm.setText(quotationList.getDEP_NAME().toString());//
                            xsbm_id_cx = quotationList.getDEP_NO().toString();
                            OkHttpClient client = new OkHttpClient();
                            FormBody body = new FormBody.Builder().add("accountNo", bjd_tv_zt.getText().toString())
                                    .add("id", xsbm_id_cx).build();
                            Request request = new Request.Builder()
                                    .addHeader("cookie", session).url(url_idTodep).post(body)
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
                                        OfferActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                List<DepInfo.IdNameList> idNameList = dInfo.getIdNameList();
                                                if (idNameList != null && idNameList.size() > 0) {
                                                    String xsgw_hd_name = idNameList.get(0).getName().toString();
                                                    xsbm.setText(xsgw_hd_name);
                                                } else {
                                                    xsbm.setText(xsbm_z);
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
                            xsbm.setText("");//

                        }
                        if (quotationList.getSAL_NO() != null) {
//                            xsgw.setText(quotationList.getSAL_NAME().toString());//
                            xsgw_id_cx = quotationList.getSAL_NO().toString();
                            OkHttpClient client = new OkHttpClient();
                            FormBody body = new FormBody.Builder().add("accountNo", bjd_tv_zt.getText().toString())
                                    .add("id", xsgw_id_cx).build();
                            Request request = new Request.Builder()
                                    .addHeader("cookie", session).url(url_employee).post(body)
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
                                        OfferActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                List<DepInfo.IdNameList> idNameList = dInfo.getIdNameList();
                                                if (idNameList != null && idNameList.size() > 0) {
                                                    String xsgw_hd_name = idNameList.get(0).getName().toString();
                                                    xsgw.setText(xsgw_hd_name);
                                                } else {
                                                    xsgw.setText(xsgw_z);
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
                            xsgw.setText("");//

                        }
//                        if (quotationList.getSECE() != null) {
//                            fwgj.setText(quotationList.getSECE().toString());
//                            //name转id
//                        } else {
//                            fwgj.setText("");
//                        }
                        if (quotationList.getSER_NO() != null) {
                            idtoname_fwgj = quotationList.getSER_NO().toString();
                            Log.e("LiNing", "查询数据===" + idtoname_fwgj);
                            OkHttpClient client = new OkHttpClient();
                            FormBody body = new FormBody.Builder().add("accountNo", bjd_tv_zt.getText().toString())
                                    .add("id", idtoname_fwgj).build();
                            Request request = new Request.Builder()
                                    .addHeader("cookie", session).url(url_employee).post(body)
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
                                        OfferActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                List<DepInfo.IdNameList> idNameList = dInfo.getIdNameList();
                                                if (idNameList != null && idNameList.size() > 0) {
                                                    String xsgw_hd_name = idNameList.get(0).getName().toString();
                                                    fwgj.setText(xsgw_hd_name);
                                                } else {
                                                    fwgj.setText(idtoname_fwgj);
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
                            fwgj.setText("");
                        }
//                        if (quotationList.getSTYL() != null) {
//                            ztsj.setText(quotationList.getSTYL().toString());
//
//                        } else {
//                            ztsj.setText("");
//
//                        }
                        if (quotationList.getSTYL_NO() != null) {
                            idtoname_ztsj = quotationList.getSTYL_NO().toString();
                            Log.e("LiNing", "查询数据===" + idtoname_ztsj);
                            OkHttpClient client = new OkHttpClient();
                            FormBody body = new FormBody.Builder().add("accountNo", bjd_tv_zt.getText().toString())
                                    .add("id", idtoname_ztsj).build();
                            Request request = new Request.Builder()
                                    .addHeader("cookie", session).url(url_employee).post(body)
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
                                        OfferActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                List<DepInfo.IdNameList> idNameList = dInfo.getIdNameList();
                                                if (idNameList != null && idNameList.size() > 0) {
                                                    String xsgw_hd_name = idNameList.get(0).getName().toString();
                                                    ztsj.setText(xsgw_hd_name);
                                                } else {
                                                    ztsj.setText(idtoname_ztsj);
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
                            ztsj.setText("");
                        }
//                        if (quotationList.getPLAW() != null) {
//                            styw.setText(quotationList.getPLAW().toString());
//
//                        } else {
//                            styw.setText("");
//
//                        }
                        if (quotationList.getAFFI_NO() != null) {
                            idtoname_styw = quotationList.getAFFI_NO().toString();
                            Log.e("LiNing", "查询数据===" + idtoname_styw);
                            OkHttpClient client = new OkHttpClient();
                            FormBody body = new FormBody.Builder().add("accountNo", bjd_tv_zt.getText().toString())
                                    .add("id", idtoname_styw).build();
                            Request request = new Request.Builder()
                                    .addHeader("cookie", session).url(url_employee).post(body)
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
                                        OfferActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                List<DepInfo.IdNameList> idNameList = dInfo.getIdNameList();
                                                if (idNameList != null && idNameList.size() > 0) {
                                                    String xsgw_hd_name = idNameList.get(0).getName().toString();
                                                    styw.setText(xsgw_hd_name);
                                                } else {
                                                    styw.setText(idtoname_styw);
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
                            styw.setText("");
                        }
//                        if (quotationList.getAFFI() != null) {
//                            qdyw.setText(quotationList.getAFFI().toString());
//
//                        } else {
//                            qdyw.setText("");
//
//                        }

                        //设计师
                        if (quotationList.getVip_NO() != null) {
                            idtoname_qdyw = quotationList.getVip_NO().toString();
                            Log.e("LiNing", "查询数据===" + idtoname_qdyw);
                            OkHttpClient client = new OkHttpClient();
                            FormBody body = new FormBody.Builder().add("accountNo", bjd_tv_zt.getText().toString())
                                    .add("id", idtoname_qdyw).build();
                            Request request = new Request.Builder()
                                    .addHeader("cookie", session).url(url_dh_toname).post(body)
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
                                        OfferActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                List<DepInfo.IdNameList> idNameList = dInfo.getIdNameList();
                                                if (idNameList != null && idNameList.size() > 0) {
                                                    String xsgw_hd_name = idNameList.get(0).getName().toString();
                                                    qdyw.setText(xsgw_hd_name);
                                                } else {
                                                    qdyw.setText(idtoname_qdyw);
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
                        //客户转
                        if (quotationList.getKH_NO()!= null) {
                             kh_bh_hd = quotationList.getKH_NO().toString();
                            OkHttpClient client = new OkHttpClient();
                            FormBody body = new FormBody.Builder().add("accountNo", bjd_tv_zt.getText().toString())
                                    .add("id", kh_bh_hd).build();
                            Request request = new Request.Builder()
                                    .addHeader("cookie", session).url(url_dabh_toname).post(body)
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
                                        OfferActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                List<DepInfo.IdNameList> idNameList = dInfo.getIdNameList();
                                                if (idNameList != null && idNameList.size() > 0) {
                                                    String xsgw_hd_name = idNameList.get(0).getName().toString();
                                                    khxm.setText(xsgw_hd_name);
                                                } else {
                                                    khxm.setText("");
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
                            khxm.setText("");

                        }
                        if (quotationList.getCust_Tel() != null) {
                            khdh.setText(quotationList.getCust_Tel().toString());

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
                             jhfs_hd_zh = quotationList.getSEND_MTH().toString();
                            if (quotationList.getSEND_MTH().toString().equals("1")) {
                                jhfs.setText("1,送货");
                            }
                            if (quotationList.getSEND_MTH().toString().equals("2")) {
                                jhfs.setText("2,货运");
                            }
                            if (quotationList.getSEND_MTH().toString().equals("3")) {
                                jhfs.setText("3,邮寄");
                            }
                            if (quotationList.getSEND_MTH().toString().equals("4")) {
                                jhfs.setText("4,快递");
                            }
                            if (quotationList.getSEND_MTH().toString().equals("5")) {
                                jhfs.setText("5,自取");
                            }
                            if (quotationList.getSEND_MTH().toString().equals("6")) {
                                jhfs.setText("6,其他");
                            }

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
                        if (quotationList.getUSR()!= null) {
                            tv_zdr.setText(quotationList.getUSR().toString());

                        } else {
                            tv_zdr.setText("");

                        }
                        if (quotationList.getUSR_CHK()!= null) {
                            tv_shenher.setText(quotationList.getUSR_CHK().toString());

                        } else {
                            tv_shenher.setText("");

                        }

                        quotationT = quotationList.getQuotationT();
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


                    //图片转换
                    if(quotationList.getFILES()!=null){
                         img_path = quotationList.getFILES().toString();
                        final String path_zh="http://oa.ydshce.com:8523/InfManagePlatform/"+img_path;
                        Log.e("LiNing", "提交的path_zh====" + path_zh);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Log.e("LiNing", "提交的path_zh====" + path_zh);
                                    byte[] data_hd= WebService.getImage(path_zh); //得到图片的输入流
                                    //二进制数据生成实图
                                    Bitmap bit=BitmapFactory.decodeByteArray(data_hd, 0, data_hd.length);
                                    signatureImg.setImageBitmap(bit);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }


                }
                break;

            case 5:
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
                    String str_dzbh = data.getStringExtra("ITM_ID");
                    xsdd.setText(str_dzbh);

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
//                    String str_id = data.getStringExtra("data_return");
//                    qdyw_id_sub = data.getStringExtra("data_return_ids");
//                    qdyw.setText(str_id);
//                    Log.e("LiNing", "提交的id====" + str_id + qdyw_id_sub);
                    DesignAllInfos.VipList vipList_hd = (DesignAllInfos.VipList) data.getSerializableExtra("VIP_INFOS_ALL");
                    Log.e("LiNing", "提交的id====" + data.getSerializableExtra("VIP_INFOS_ALL"));
                    if (vipList_hd.getVip_Name() != null) {
                        qdyw.setText(vipList_hd.getVip_Name());
                    } else {
                        qdyw.setText("null");
                    }
                    if (vipList_hd.getVip_NO() != null) {
                        vip_no_cust = vipList_hd.getVip_NO().toString();
                        Log.e("LiNing", "提交的vip_no_cust====" + vip_no_cust);
                    }
                }
                break;
            case 18:
                if (resultCode == 1) {
                    String str_id = data.getStringExtra("data_dep_id");
                    pm_nmae_sub = data.getStringExtra("data_dep");
                    add_mx_ph.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + pm_nmae_sub);
                    if (!add_mx_ph.getText().toString().equals("")) {
                        getLoad_ph_Infos();
                    }
                }
                break;
            case PHOTO_REQUEST_GALLERY:
                // 从相册返回的数据
                if (data != null) {
                    // 得到图片的全路径
                    Uri uri = data.getData();
                    Log.e("LiNing", "-------" + uri);
                    crop(uri);
                    String[] pojo = { MediaStore.MediaColumns.DATA };
                    Cursor cursor = getContentResolver().query(uri, pojo, null,
                            null, null);
                    if (cursor != null) {
                        int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
                        cursor.moveToFirst();
                        pathSignature = cursor.getString(columnIndex);

                        BitmapFactory.Options newOpts = new BitmapFactory.Options();
                        newOpts.inJustDecodeBounds = true;
                        bitmap_xc = BitmapFactory
                                .decodeFile(pathSignature, newOpts);
                        newOpts.inJustDecodeBounds = false;

                        int w = newOpts.outWidth;
                        int h = newOpts.outHeight;
                        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
                        float hh = 800f;// 这里设置高度为800f
                        float ww = 480f;// 这里设置宽度为480f
                        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
                        int be = 1;// be=1表示不缩放
                        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
                            be = (int) (newOpts.outWidth / ww);
                        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
                            be = (int) (newOpts.outHeight / hh);
                        }
                        if (be <= 0)
                            be = 1;
                        newOpts.inSampleSize = be;// 设置缩放比例
                        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
                        bitmap_xc = BitmapFactory
                                .decodeFile(pathSignature, newOpts);
//                        sp.edit().putString("photoURL", pathSignature).commit();

                    }
                }
                break;
            case PHOTO_REQUEST_CUT:
                // 从剪切图片返回的数据
                if (data != null) {
                    Bitmap bitmap = data.getParcelableExtra("data");
                    // * 获得图片
                    signatureImg.setImageBitmap(bitmap);
                    // 保存到SharedPreferences
                    saveBitmapToSharedPreferences(bitmap);

                    saves(bitmap);
                }
                break;
                default:
                    break;
        }
    }

    private void getLoad_ph_Infos() {
        OkHttpClient client = new OkHttpClient();
        FormBody localFormBody = new FormBody.Builder()
                .add("db_Id", bjd_tv_zt.getText().toString())
                .add("prdNO", add_mx_ph.getText().toString())
                .add("query_Sup", "all")
                .add("query_CompDep", "all")
                .add("employee", "all")
                .add("prdIndx", "all")
                .add("prdWh", "all")
                .add("prdLevel", "all")
                .add("prdKND", "all")
                .add("showStop", "F")
                .add("prdMrk", "all")
                .add("prdName", "all")
                .add("prdName_ENG", "all")
                .build();
        Log.e("LiNing", "-----str----" + bjd_tv_zt.getText().toString() + add_mx_ph.getText().toString());
        Request localRequest = new Request.Builder()
                .addHeader("cookie", session).url(url_load_price)
                .post(localFormBody)
                .build();
        client.newCall(localRequest).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e("LiNing", "-----str----" + str);
                Gson gson = new GsonBuilder().setDateFormat(
                        "yyyy-MM-dd'T'HH:mm:ss").create();//特殊格式
                final PriceLoadInfo cInfoDB = gson.fromJson(str,
                        PriceLoadInfo.class);
                if (cInfoDB != null) {
                    OfferActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            List<PriceLoadInfo.Prdt> prdt = cInfoDB.getPrdt();
                            Log.e("LiNing", "prdt-----size-----" + prdt.size());
                            Log.e("LiNing", "prdt-----size-----" + prdt.get(0).getSPC());
                            //商品规格
                            if (prdt.get(0).getSPC() != null) {
                                add_mx_spgg.setText(prdt.get(0).getSPC().toString());
                            } else {
                                add_mx_spgg.setText("");
                            }
                            //包装单位
                            if (prdt.get(0).getPAK_UNIT() != null) {
                                add_mx_bzdw.setText(prdt.get(0).getPAK_UNIT().toString());
                            } else {
                                add_mx_bzdw.setText("1");
                            }
                            //包装换算
                            if (String.valueOf(prdt.get(0).getPAK_EXC()) != null) {
                                if (String.valueOf(prdt.get(0).getPAK_EXC()).equals("")) {
                                    add_mx_bzhs.setText("1");
                                } else {

                                    add_mx_bzhs.setText("" + prdt.get(0).getPAK_EXC());
                                }
                            } else {
                                add_mx_bzhs.setText("1");
                            }

                            //包装重量
                            if (prdt.get(0).getPAK_NW() != null) {
                                add_mx_bzzl.setText(prdt.get(0).getPAK_NW());
                                if (String.valueOf(prdt.get(0).getPAK_NW()).equals("")) {
                                    add_mx_bzzl.setText("1");
                                } else {

                                    add_mx_bzzl.setText("" + prdt.get(0).getPAK_EXC());
                                }
                            } else {
                                add_mx_bzzl.setText("1");
                            }
                            //包装数量
                            add_mx_bzsl.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String zl_bz = add_mx_bzzl.getText().toString();
                                    String hs_bz = add_mx_bzhs.getText().toString();
                                    double bzsl_ys = Double.valueOf(zl_bz) / Double.valueOf(hs_bz);
                                    add_mx_bzsl.setText("" + bzsl_ys);
                                }
                            });

                            if (String.valueOf(prdt.get(0).getPK2_QTY()) != null) {
                                add_mx_bzsl.setText("" + prdt.get(0).getPK2_QTY());
                            } else {
                                add_mx_bzsl.setText("0");
                            }
                            //商品重量(无)
                            if (prdt.get(0).getPAK_GW() != null) {
                                add_mx_spzl.setText(prdt.get(0).getPAK_GW());
                            } else {
                                add_mx_spzl.setText("0");
                            }
                            //包装大小(无)
                            if (prdt.get(0).getPAK_MEAST() != null) {
                                add_mx_bzdx.setText(prdt.get(0).getPAK_MEAST());
                            } else {
                                add_mx_bzdx.setText("0");
                            }
                            //库位
                            if (prdt.get(0).getWH() != null) {
                                add_mx_kw.setText(prdt.get(0).getWH());
                            } else {
                                add_mx_kw.setText("");
                            }
                            //单位（
                            if (prdt.get(0).getUT() != null) {
                                add_mx_dw.setText(prdt.get(0).getUT());
                            } else {
                                add_mx_dw.setText("");
                            }

                            //统一标价
                            if (String.valueOf(prdt.get(0).getUPR()) != null) {
                                add_mx_tybj.setText("" + prdt.get(0).getUPR());
                            } else {
                                add_mx_tybj.setText("0");
                            }
                            //最低售价
                            if (String.valueOf(prdt.get(0).getUP_MIN()) != null) {
                                add_mx_zdsj.setText("" + prdt.get(0).getUP_MIN());
                            } else {
                                add_mx_zdsj.setText("0");
                            }
                            //标准成本
                            if (prdt.get(0).getCST_STD() != null) {
                                add_mx_bzcb.setText("" + prdt.get(0).getCST_STD());
                            } else {
                                add_mx_bzcb.setText("100");
                            }
                            //做运算
                            add_mx_je.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    String dj_ys = add_mx_dj.getText().toString();
                                    String sl_ys = add_mx_sl.getText().toString();
                                    String zk_ys = add_mx_zk.getText().toString();
                                    Log.e("LiNing", "运算数值=====" + dj_ys + sl_ys + zk_ys);
                                    //金额
                                    if (!dj_ys.equals("") && !sl_ys.equals("") ) {
                                        if(zk_ys.equals("")){
                                            zk_ys="1";
                                        }
                                        double je = Double.valueOf(dj_ys) * Double.valueOf(sl_ys) * Double.valueOf(zk_ys);
                                        add_mx_je.setText("" + new BigDecimal(je).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
//                                        add_mx_je.setText("" + je);
                                    } else {
                                        Toast.makeText(context, "请填写单价/数量", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                            //预估毛利

                            add_mx_ygml.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String je_ys = add_mx_je.getText().toString();
                                    String cb_ys = add_mx_bzcb.getText().toString();
                                    String sl_ys = add_mx_sl.getText().toString();
                                    Log.e("LiNing", "运算数值=====" + je_ys + cb_ys);
                                    if (!je_ys.equals("") && !sl_ys.equals("") && !cb_ys.equals("")) {
                                        double ygml_ys = Double.valueOf(je_ys) - Double.valueOf(sl_ys) * Double.valueOf(cb_ys);

//                                        add_mx_ygml.setText("" + ygml_ys);
                                        add_mx_ygml.setText("" +  new BigDecimal(ygml_ys).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                                    } else {
                                        Toast.makeText(context, "请填写总金额/数量/标准成本", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                            //毛利率

                            add_mx_mll.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String je_ys = add_mx_je.getText().toString();
                                    String ygml_ys = add_mx_ygml.getText().toString();
                                    Log.e("LiNing", "运算数值=====" + je_ys + ygml_ys);
                                    if (!je_ys.equals("") && !ygml_ys.equals("")) {
                                        double mll_ys = Double.valueOf(ygml_ys) / Double.valueOf(je_ys);
//                                        String mll_str = mll_ys * 100 + "%";
//                                        String mll_str = ""+mll_ys ;
                                        String mll_str = ""+new BigDecimal(mll_ys).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() ;
                                        add_mx_mll.setText( mll_str);
                                    } else {
                                        Toast.makeText(context, "请填写总金额/预估毛利", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
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
        private List<QuotationTwo.QuotationT> infos_content;

        public BjdQtyAdapter(int bjmxobj_head, List<QuotationTwo.QuotationT> quotationT, Context context) {
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
                    holder.checkbox = (CheckBox) convertView
                            .findViewById(R.id.bjmx_listDeleteCheckBox);
                    holder.checkbox.setVisibility(View.VISIBLE);
                    checkBoxList.add(holder.checkbox);
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
                    holder.mx_sddh = (TextView) convertView.findViewById(R.id.tv_bjmxsddh);
                    holder.mx_scsl = (TextView) convertView.findViewById(R.id.tv_bjmxscsl);
                    holder.mx_llsl = (TextView) convertView.findViewById(R.id.tv_bjmxllsl);

                    holder.mx_spgg = (TextView) convertView.findViewById(R.id.tv_bjmxspgg);
                    holder.mx_bzdw = (TextView) convertView.findViewById(R.id.tv_bjmxbzdw);
                    holder.mx_bzhs = (TextView) convertView.findViewById(R.id.tv_bjmxbzhs);
                    holder.mx_bzsl = (TextView) convertView.findViewById(R.id.tv_bjmxbzsl);
                    holder.mx_bzzl = (TextView) convertView.findViewById(R.id.tv_bjmxbzzl);
                    holder.mx_spzl = (TextView) convertView.findViewById(R.id.tv_bjmxspzl);
                    holder.mx_bzdx = (TextView) convertView.findViewById(R.id.tv_bjmxbzdx);
                    holder.mx_zy = (TextView) convertView.findViewById(R.id.tv_bjmxzy);
                    holder.mx_tszc= (TextView) convertView.findViewById(R.id.tv_bjmxtszc);
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
            QuotationTwo.QuotationT quotationT_mx = infos_content.get(position);
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

            if (quotationT_mx.getPRD_MARK()!= null) {
                holder.mx_sh.setText(quotationT_mx.getPRD_MARK().toString());

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
                holder.mx_dj.setText(""+new BigDecimal(quotationT_mx.getUP().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

            } else {
                holder.mx_dj.setText("");

            }
            if (quotationT_mx.getDIS_CNT() != null) {
                holder.mx_zk.setText(""+new BigDecimal(quotationT_mx.getDIS_CNT().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
//                holder.mx_zk.setText(quotationT_mx.getDIS_CNT().toString());

            } else {
                holder.mx_zk.setText("");

            }
            if (quotationT_mx.getAMTN() != null) {
                holder.mx_je.setText(""+new BigDecimal(quotationT_mx.getAMTN().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
//                holder.mx_je.setText(quotationT_mx.getAMTN().toString());

            } else {
                holder.mx_je.setText("");

            }
            if (quotationT_mx.getPRICE_ID() != null) {
                holder.mx_jgdh.setText(quotationT_mx.getPRICE_ID().toString());

            } else {
                holder.mx_jgdh.setText("");

            }
            if (quotationT_mx.getPrdUp() != null) {
                holder.mx_zcdj.setText(""+new BigDecimal(quotationT_mx.getPrdUp().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
//                holder.mx_zcdj.setText(quotationT_mx.getPrdUp().toString());

            } else {
                holder.mx_zcdj.setText("");

            }
            if (quotationT_mx.getUPR() != null) {
                holder.mx_tybj.setText(""+new BigDecimal(quotationT_mx.getUPR().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
//                holder.mx_tybj.setText(quotationT_mx.getUPR().toString());

            } else {
                holder.mx_tybj.setText("");

            }
            if (quotationT_mx.getUP_MIN() != null) {
                holder.mx_zdsj.setText(""+new BigDecimal(quotationT_mx.getUP_MIN().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
//                holder.mx_zdsj.setText(quotationT_mx.getUP_MIN().toString());

            } else {
                holder.mx_zdsj.setText("");

            }
            if (quotationT_mx.getCST_STD() != null) {
                holder.mx_bzcb.setText(""+new BigDecimal(quotationT_mx.getCST_STD().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
//                holder.mx_bzcb.setText(quotationT_mx.getCST_STD().toString());

            } else {
                holder.mx_bzcb.setText("");

            }
            if (quotationT_mx.getEST_SUB() != null) {
                holder.mx_ygml.setText(""+new BigDecimal(quotationT_mx.getEST_SUB().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
//                holder.mx_ygml.setText(quotationT_mx.getEST_SUB().toString());

            } else {
                holder.mx_ygml.setText("");

            }
            if (quotationT_mx.getSUB_GPR() != null) {
                holder.mx_mll.setText(""+new BigDecimal(quotationT_mx.getSUB_GPR().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
//                holder.mx_mll.setText(quotationT_mx.getSUB_GPR().toString());

            } else {
                holder.mx_mll.setText("");

            }
            if (quotationT_mx.getOS_NO() != null) {
                holder.mx_sddh.setText(quotationT_mx.getOS_NO().toString());

            } else {
                holder.mx_sddh.setText("");

            }
            if (quotationT_mx.getOS_QTY() != null) {
                holder.mx_sdsl.setText(quotationT_mx.getOS_QTY().toString());

            } else {
                holder.mx_sdsl.setText("");

            }
            if (quotationT_mx.getSPC_NO() != null) {
                holder.mx_scsl.setText(quotationT_mx.getSPC_NO().toString());

            } else {
                holder.mx_scsl.setText("");

            }
            if (quotationT_mx.getSPC_QTY() != null) {
                holder.mx_llsl.setText(quotationT_mx.getSPC_QTY().toString());

            } else {
                holder.mx_llsl.setText("");

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
                holder.mx_bzhs.setText(""+new BigDecimal(quotationT_mx.getPAK_EXC().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
//                holder.mx_bzhs.setText(quotationT_mx.getPAK_EXC().toString());

            } else {
                holder.mx_bzhs.setText("");

            }
            if (quotationT_mx.getPAK_QTY() != null) {
                holder.mx_bzsl.setText(quotationT_mx.getPAK_QTY().toString());

            } else {
                holder.mx_bzsl.setText("");

            }
            if (quotationT_mx.getPAK_NW() != null) {
                holder.mx_bzzl.setText(""+new BigDecimal(quotationT_mx.getPAK_NW().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
//                holder.mx_bzzl.setText(quotationT_mx.getPAK_NW().toString());

            } else {
                holder.mx_bzzl.setText("");

            }
            if (quotationT_mx.getPAK_GW() != null) {
                if(quotationT_mx.getPAK_GW().toString().equals("0E-8")){
                    holder.mx_spzl.setText("0");
                }else{
                    holder.mx_spzl.setText(""+new BigDecimal(quotationT_mx.getPAK_GW().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
//                    holder.mx_spzl.setText(quotationT_mx.getPAK_GW().toString());
                }

            } else {
                holder.mx_spzl.setText("");

            }
            if (quotationT_mx.getPAK_MEAST() != null) {
                holder.mx_bzdx.setText(""+new BigDecimal(quotationT_mx.getPAK_MEAST().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
//                holder.mx_bzdx.setText(quotationT_mx.getPAK_MEAST().toString());

            } else {
                holder.mx_bzdx.setText("");

            }
            if (quotationT_mx.getREMT() != null) {
                holder.mx_zy.setText(quotationT_mx.getREMT().toString());

            } else {
                holder.mx_zy.setText("");

            }
            if (quotationT_mx.getISLP() != null) {
                holder.mx_tszc.setText(quotationT_mx.getISLP().toString());

            } else {
                holder.mx_tszc.setText("");

            }
            holder.checkbox.setOnCheckedChangeListener(new CheckBoxListener(
                    position));
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
            public TextView mx_zy;
            public TextView mx_tszc;
            public TextView mx_sddh;
            public TextView mx_llsl;
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
                flag_sc = positions;
                checkFalg = 1;
                checkedIndexList.add(positions);


                Log.e("LiNing", "--------" + flag_sc);
                Log.e("LiNing", "--------" + checkedIndexList);
                if (0 < checkedIndexList.size() && checkedIndexList.size() <= 1) {
//                    for (int k = 0; k < checkedIndexList.size(); k++) {
//                        Integer integer = checkedIndexList.get(k);
//                        quotationT_item = OfferActivity.this.quotationT.get(integer);
//                        //此处判断选择的那一条
//                    }
//                }
//                    Log.e("LiNing", "--------" + quotationT_item);
//                    Log.e("LiNing", "--------" + quotationT_item.getITM());
                    quotationT_item= OfferActivity.this.quotationT.get(flag_sc);
                xh_hd = OfferActivity.this.quotationT.get(flag_sc).getITM().toString();
                pm_hd = OfferActivity.this.quotationT.get(flag_sc).getPRD_NAME().toString();
                ph_hd = OfferActivity.this.quotationT.get(flag_sc).getPRD_NO().toString();
                if (OfferActivity.this.quotationT.get(flag_sc).getPRD_MARK() != null) {
                    sh_hd = OfferActivity.this.quotationT.get(flag_sc).getPRD_MARK().toString();
                } else {
                    sh_hd = "sh";
                }
                kw_hd = OfferActivity.this.quotationT.get(flag_sc).getWH().toString();
                dw_hd = OfferActivity.this.quotationT.get(flag_sc).getUNIT().toString();
                sl_hd = OfferActivity.this.quotationT.get(flag_sc).getQTY().toString();
//                dj_hd = OfferActivity.this.quotationT.get(flag_sc).getUP().toString();
                dj_hd = ""+new BigDecimal(OfferActivity.this.quotationT.get(flag_sc).getUP().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//                zk_hd = OfferActivity.this.quotationT.get(flag_sc).getDIS_CNT().toString();
                zk_hd =""+new BigDecimal(OfferActivity.this.quotationT.get(flag_sc).getDIS_CNT().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//                je_hd = OfferActivity.this.quotationT.get(flag_sc).getAMTN().toString();
                je_hd =""+new BigDecimal(OfferActivity.this.quotationT.get(flag_sc).getAMTN().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                jgdh_hd = OfferActivity.this.quotationT.get(flag_sc).getPRICE_ID().toString();
//                zcdj_hd = OfferActivity.this.quotationT.get(flag_sc).getPrdUp().toString();
                zcdj_hd = ""+new BigDecimal(OfferActivity.this.quotationT.get(flag_sc).getPrdUp().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//                tybj_hd = OfferActivity.this.quotationT.get(flag_sc).getUPR().toString();
                tybj_hd = ""+new BigDecimal(OfferActivity.this.quotationT.get(flag_sc).getUPR().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//                zdsj_hd = OfferActivity.this.quotationT.get(flag_sc).getUP_MIN().toString();
                zdsj_hd = ""+new BigDecimal(OfferActivity.this.quotationT.get(flag_sc).getUP_MIN().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//                bzcb_hd = OfferActivity.this.quotationT.get(flag_sc).getCST_STD().toString();
                bzcb_hd = ""+new BigDecimal(OfferActivity.this.quotationT.get(flag_sc).getCST_STD().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//                ygml_hd = OfferActivity.this.quotationT.get(flag_sc).getEST_SUB().toString();
                ygml_hd = ""+new BigDecimal(OfferActivity.this.quotationT.get(flag_sc).getEST_SUB().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//                mll_hd = OfferActivity.this.quotationT.get(flag_sc).getSUB_GPR().toString();
                mll_hd = ""+new BigDecimal(OfferActivity.this.quotationT.get(flag_sc).getSUB_GPR().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                sddh_hd = OfferActivity.this.quotationT.get(flag_sc).getOS_NO().toString();
                sdsl_hd = OfferActivity.this.quotationT.get(flag_sc).getOS_QTY().toString();
                scsl_hd = OfferActivity.this.quotationT.get(flag_sc).getSPC_NO().toString();
                llsl_hd = OfferActivity.this.quotationT.get(flag_sc).getSPC_QTY().toString();

                spgg_hd = OfferActivity.this.quotationT.get(flag_sc).getSPC().toString();
                bzdw_hd = OfferActivity.this.quotationT.get(flag_sc).getPAK_UNIT().toString();
//                bzhs_hd = OfferActivity.this.quotationT.get(flag_sc).getPAK_EXC().toString();
                bzhs_hd = ""+new BigDecimal(OfferActivity.this.quotationT.get(flag_sc).getPAK_EXC().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                bzsl_hd = OfferActivity.this.quotationT.get(flag_sc).getPAK_QTY().toString();
//                bzzl_hd = OfferActivity.this.quotationT.get(flag_sc).getPAK_NW().toString();
                bzzl_hd = ""+new BigDecimal(OfferActivity.this.quotationT.get(flag_sc).getPAK_NW().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//                spzl_hd = OfferActivity.this.quotationT.get(flag_sc).getPAK_GW().toString();
                spzl_hd =""+new BigDecimal(OfferActivity.this.quotationT.get(flag_sc).getPAK_GW().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//                bzdx_hd = OfferActivity.this.quotationT.get(flag_sc).getPAK_MEAST().toString();
                bzdx_hd =""+new BigDecimal(OfferActivity.this.quotationT.get(flag_sc).getPAK_MEAST().toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                zy_hd = OfferActivity.this.quotationT.get(flag_sc).getREMT().toString();
                tszc_hd = OfferActivity.this.quotationT.get(flag_sc).getISLP().toString();
                } else {
                    Toast.makeText(context, "修改时请选择单条数据", Toast.LENGTH_LONG).show();
                }

            } else {
                checkFalg = 0;
                checkFalg = 0;
                checkedIndexList.remove((Integer) positions);
                Log.e("LiNing", "--------" + checkedIndexList);
            }
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
    private void saves(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 40, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String imageString = new String(Base64.encodeToString(byteArray,
                Base64.DEFAULT));
        sp.edit().putString("testImg", imageString).commit();
    }
    private void saveBitmapToSharedPreferences(Bitmap bitmap) {
        // 第一步:将Bitmap压缩至字节数组输出流ByteArrayOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 40, byteArrayOutputStream);
        // 第二步:利用Base64将字节数组输出流中的数据转换成字符串String
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String imageString = new String(Base64.encodeToString(byteArray,
                Base64.DEFAULT));
        InputStream signature = new ByteArrayInputStream(imageString.getBytes());
        // 第三步:将String保持至SharedPreferences
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("STR_SImg", imageString);
        editor.putString("image", "" + signature);
        editor.commit();

        // 上传参数
        File file = new File(pathSignature);
        if (!file.exists()) {
            file.mkdir();
        }
        BufferedOutputStream bos;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }
    public void allback(View v) {
        finish();
    }
}
