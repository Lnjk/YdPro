package com.example.ydshoa;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import com.example.LeftOrRight.InterceptScrollContainer;
import com.example.LeftOrRight.MyHScrollView;
import com.example.adapter.PriceGetAdapter;
import com.example.bean.ExcelToList;
import com.example.bean.GetPriceInfo;
import com.example.bean.LxProject;
import com.example.bean.OnItemClickListenerPrice;
import com.example.bean.PriceLoadInfo;
import com.example.bean.PriceMx;
import com.example.bean.PriceNumID;
import com.example.bean.PriceNumIDZs;
import com.example.bean.TimeTo;
import com.example.bean.URLS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
//import com.umeng.message.PushAgent;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.view.View.OnClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.bean.PriceLoadInfo.*;


public class PriceActivity extends Activity implements OnClickListener {
    private Context context;
    private TextView zxqk, brand_item, brand_name_item, head, db_check, brand_check, hs_check, cust_check, user_check, operate_check, price_num_id, prdMrk, prdName, prdName_ENG,
            tv_qurey_sup, tv_query_compdep, tv_employee, tv_prdNo, tv_prdIndx, tv_prdWh, tv_prdLevre, tv_prdKND, tv_showStop;
    private EditText dwcb_item, tydj_item, zdsj_item, zcdj_item, xszk_item, zxqk_item, bzxx_item, et_num,
            et_tyzk, zcsm, zcmc, et_seach;
    private Button add, query, reset, del, go, copy, load, in, out, delmx, num_chage, start, stop, time, addone, priceset;
    private ImageButton db, brand, hs, cust, user, operate,
            query_sup, query_compdep, employee, prdNo, prdIndx, prdWh, prdLevre, prdKND, showStop;

    //参照对象，运算符
    private ArrayList<LxProject> mData = null;
    int checked, execute;
    RelativeLayout mHead, mHead_item,mHead_more;
    LinearLayout touch;
    ListView mListView1, mListView1_set;
    private String str_time, stopTime, startTime, reportnos, reportname;
    private AlertDialog alertDialog;
    private SharedPreferences sp;
    private String session, db_mr, yh_mr, date_dd;
    String url_dh_price;//临时+新增（正式）
    String url_get_price = URLS.price_getPrices;//获取（查询）主表
    String url_getmx_price = URLS.price_getPrice;//获取（查询）明细表
    String url_load_price = URLS.price_load;//货品加载
    String url_del_price = URLS.price_delPrice;//删除
    String url_reset_price = URLS.price_setPrice;//修改
    String url_excut_price = URLS.price_executePrice;//执行
    String url_add_price;//新增（保存）;//新增（保存）
    private List<PriceLoadInfo.Prdt> prdt;
    //回调id值（交互）
    private String id_prdNo, id_employee, id_hs, id_prdIndex, id_prdWh, id_querySup;
    private List<PriceLoadInfo.Prdt> prdt1;
    private PriceAdapter priceAdapter;
    private InAdapter inAdapter;
    //获取item的对应数据
    private PriceLoadInfo.Prdt prdt_index;
    private String prd_no_exra, prd_name_exra, prd_danj_exra, prd_zk_exra, prd_zxqk_exra, prd_bzxx_exra;
    private double up_sal_exra, upr_exra, up_min_exra;
    //    String url_dh_price = "http://oa.ydshce.com:8080/InfManagePlatform/BilngetBiln_no.action";
    /**
     * 记录选中item的下标
     */
    private List<Integer> checkedIndexList;
    private List<CheckBox> checkBoxList;

    private List<String> idList_prdnoID, idList_prdnoName, idList_prdIndexID, idList_prdIndexName, idList_prdWhID, idList_prdWhName,
            idList_employeeName, idList_employeeID, idList_querySup, idList_querySupID, idList_compDep, idList_compDepID,
            idList_checkBread, idList_checkBreadID, idList_checkHs, idList_checkHsID, idList_checkCust, idList_checkCustID, idList_hpdj, idList_hpdl, idList_hpdjID, idList_hpdlID;
    private Double danj;
    private Double new_danj;
    private int onclik_go, save, out_info, y_bc, y_zx;
    //获取权限
    private String p_query, p_add, p_del, p_out, p_reset;
    private FormBody localFormBody;
    private String str_name;
    private String id_queryCust;
    private ArrayList<HashMap<String, Object>> dList;
    //新增（数组）
    private String sub_id, sub_ph, sub_dwcb, sub_tysj, sub_zdsj, sub_zcdj, sub_zczk, sub_sm;
    private int done;
    //获取主表
    private List<GetPriceInfo.Prices> prices_main;
    private PriceGetAdapter getAdapter;
    private ListView list_main;
    private String accepted_one, accepted_one_mx, biln_user,erPprice_id;
    private String db_exra, pricseNo_exra;
    private String mx_db_id;
    private String mx_db_name;
    private String mx_sup_name;
    private String mx_sup_id;
    private String mx_compDepName;
    private String mx_compDepName_id;
    private String mx_salesTerminal_name;
    private String mx_salesTerminal_id;
    private String mx_price_dd;
    private String mx_priceId;
    private String mx_priceName;
    private String mx_rem;
    private List<PriceMx.PrdtUp> mx_prdtUp;
    //获取明细表
    private String db_id_mx, priceId_mx;
    //提交数据
    private String post_db_name, post_brand_name, post_hs_name, post_cust_name;
    private int ib_xl;
    private String post_brand, post_hs, post_cust;
    private String xh_tj;
    private String xh_tj1;
    private String ph_tj;
    private String dwcb_tj;
    private String tysj_tj;
    private String zdsj_tj;
    private String zcdj_tj;
    private String zczk_tj;
    private String zxqk_tj;
    private String sm_tj;
    //转出定义数据、
    public static List<PriceLoadInfo.Prdt> info_add = new ArrayList<PriceLoadInfo.Prdt>();
    public static List<PriceMx.PrdtUp> info_query = new ArrayList<PriceMx.PrdtUp>();
    public static List<HashMap<String, Object>> info_zr = new ArrayList<HashMap<String, Object>>();
    private Intent intent;
    private String s_trime;
    private FormBody localFormBody1;
    private int do_all, iscopy;
    private ArrayList<HashMap<String, Object>> in_list;
    private MxPriceAdapter mxPriceAdapter;
    private int copy_info;
    private List<PriceLoadInfo.Prdt> datas;
    private String s_datas;
    private String str_newdj;
    private Button start_quick;
    private Button stop_quick;
    private boolean isChecked;
    int bfor;//判断加载（修改）
    private String xh_tj11;
    private String prdLevre_jz;
    private String prdLevre_comit_jz;
    private String prdKND_comit_jz;
    private String save_prd_no;
    String hpdj_id;
    String hpdl_id;
    //提交主表参考对象等
    String ckdx_zb, ysf_zb, yssz_zb, tyzk_zb, sm_add, accept_yn, namePrice_add;
    //新增一条数据
    private EditText id_addone, name_addone, dwcb_addone, tydj_addone, zdsj_addone, zcdj_addone, xszk_addone, zxqk_addone, bzxx_addone;
    private Button ok, qx;
    PriceLoadInfo.Prdt new_info;
    PriceMx.PrdtUp mx_ls;
    PriceMx.PrdtUp mx_ls_mor,mx_ls_prdt;
    //获取验证的id和名称
    String name_get, prd_no;
    PriceLoadInfo.Prdt prdt2;
    PriceLoadInfo.Prdt prdt_isnow;
    PriceLoadInfo.Prdt next2;
    PriceMx.PrdtUp prdt3;
    PriceMx.PrdtUp prdt_set3;
    private List<PriceLoadInfo.Prdt> prdt_add;
    private List<PriceMx.PrdtUp> prdt_add_mx;
    PriceLoadInfo.Prdt prdt_set;
    ArrayList<String> ls_set;
    MyHScrollView headSrcrollView;
    int one_add=0;
    int more=0;
    String prd_no_prdt,zc_dj_hd;
    int iSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.price_one);
        context = PriceActivity.this;
//        PushAgent.getInstance(context).onAppStart();
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        db_mr = sp.getString("DB_MR", "");
        yh_mr = sp.getString("MR_YH", "");
        Log.e("LiNing", "db_mr========" + db_mr + "---" + yh_mr);//默认日期
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // 获取当前年份
        int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前日
        date_dd = mYear + "-" + mMonth + "-" + mDay;
        getNowTime();
        initView();
        p_query = sp.getString("PRICE_QUERY", "");
        p_add = sp.getString("PRICE_ADD", "");
        p_del = sp.getString("PRICE_DEL", "");
        p_out = sp.getString("PRICE_OUT", "");
        p_reset = sp.getString("PRICE_RESET", "");
        Log.e("LiNing", "----权限========" + p_query + p_add + p_del + p_out + p_reset);
        dList = new ArrayList();

    }

    private void getNowTime() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
        Date curDate = new Date(System.currentTimeMillis());
        str_time = formatter.format(curDate);//当前时间

    }

    ImageButton delAdd;

    private void initView() {
        delAdd = (ImageButton) findViewById(R.id.ib_adddel);
        this.mHead = ((RelativeLayout) findViewById(R.id.price_head));
        this.mHead_item = ((RelativeLayout) findViewById(R.id.price_head_item));
        mHead_item.setVisibility(View.GONE);
        this.mHead_item.setFocusable(true);
        this.mHead_item.setClickable(true);
        this.mHead_item.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        this.mHead.setFocusable(true);
        this.mHead.setClickable(true);
        this.mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        this.mListView1 = (ListView) findViewById(R.id.lv_price_header);
        this.mListView1
                .setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                HashMap<String, String> map = (HashMap<String, String>) parent
//                        .getItemAtPosition(position);
//                Toast.makeText(context, map.get("备注信息"),
//                        Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, map.get("序号"),
//                        Toast.LENGTH_SHORT).show();
            }
        });


        head = (TextView) findViewById(R.id.all_head);
        head.setText("终端定价政策设定");
        //初始化(列表)dwcb_item,tydj_item,zdsj_item,zcdj_item,xszk_item,zxqk_item,bzxx_item
        brand_item = (TextView) findViewById(R.id.price_brand_id);
        brand_name_item = (TextView) findViewById(R.id.price_brand_name);
        dwcb_item = (EditText) findViewById(R.id.price_dw_cb);
        tydj_item = (EditText) findViewById(R.id.price_ty_dj);
        zdsj_item = (EditText) findViewById(R.id.price_min);
        zcdj_item = (EditText) findViewById(R.id.price_danj);
        xszk_item = (EditText) findViewById(R.id.price_zk);
        zxqk_item = (EditText) findViewById(R.id.price_zx_qk);
        bzxx_item = (EditText) findViewById(R.id.price_bz_xx);
        //条件
        zcmc = (EditText) findViewById(R.id.et_price_polname);
        zcsm = (EditText) findViewById(R.id.et_price_say);
        zxqk = (TextView) findViewById(R.id.et_price_excut);
        et_num = (EditText) findViewById(R.id.et_price_num);
        et_tyzk = (EditText) findViewById(R.id.et_price_discount);

        add = (Button) findViewById(R.id.btn_price_add);
        query = (Button) findViewById(R.id.btn_price_query);
        reset = (Button) findViewById(R.id.btn_price_reset);
        del = (Button) findViewById(R.id.btn_price_del);
        go = (Button) findViewById(R.id.btn_price_go);
        copy = (Button) findViewById(R.id.btn_price_copy);
        load = (Button) findViewById(R.id.btn_price_load);
        in = (Button) findViewById(R.id.btn_price_in);
        out = (Button) findViewById(R.id.btn_price_out);
        delmx = (Button) findViewById(R.id.btn_price_delmx);
        delmx = (Button) findViewById(R.id.btn_price_delmx);
        num_chage = (Button) findViewById(R.id.btn_price_make);
//        addone = (Button) findViewById(R.id.addOne);
        priceset = (Button) findViewById(R.id.priceSet);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_MONTH, 1); //当前月1号和最后日期
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
        start = (Button) findViewById(R.id.btn_price_start);
        start.setText(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
        stop = (Button) findViewById(R.id.btn_price_stop);
        stop.setText(str_time);
        time = (Button) findViewById(R.id.btn_price_time);
//        if(done==1){
//        time.setText(str_time);
//        }else{
//
//        }
//        time.setText(date_dd);
        time.setText(str_time);
        db = (ImageButton) findViewById(R.id.ib_price_account);
        brand = (ImageButton) findViewById(R.id.ib_price_brand);
        hs = (ImageButton) findViewById(R.id.ib_price_hs);
        cust = (ImageButton) findViewById(R.id.ib_price_cust);
        user = (ImageButton) findViewById(R.id.ib_price_user);
        operate = (ImageButton) findViewById(R.id.ib_price_operat);

        price_num_id = (TextView) findViewById(R.id.et_price_numId);
        price_num_id.setHint("点击获取政策编号");
        price_num_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getTicket();//获取临时单号
            }
        });
        touch = (LinearLayout) findViewById(R.id.ll_touch);
        touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getTicket();//获取临时单号
            }
        });
        db_check = (TextView) findViewById(R.id.et_price_zt);
        db_check.setText(db_mr);
        db_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                View view_v = (LinearLayout)getLayoutInflater()
//                        .inflate(R.layout.textview_v_g, null);
//                TextView view= (TextView) view_v.findViewById(R.id.tv_v_g);
                TextView view = new TextView(context);
                view.setMovementMethod(ScrollingMovementMethod.getInstance());
                view.setText(db_check.getText().toString());
                alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("账套");
                alertDialog.setCancelable(true);
                alertDialog.setView(view);
                alertDialog.show();
            }
        });
        brand_check = (TextView) findViewById(R.id.et_price_brand);
        brand_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView view = new TextView(context);
                view.setMovementMethod(ScrollingMovementMethod.getInstance());
                view.setText(brand_check.getText().toString());
                alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("品牌信息");
                alertDialog.setCancelable(true);
                alertDialog.setView(view);
                alertDialog.show();
            }
        });
        hs_check = (TextView) findViewById(R.id.et_price_hs);
        hs_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView view = new TextView(context);
                view.setMovementMethod(ScrollingMovementMethod.getInstance());
                view.setText(hs_check.getText().toString());
                alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("核算单位");
                alertDialog.setCancelable(true);
                alertDialog.setView(view);
                alertDialog.show();
            }
        });
        cust_check = (TextView) findViewById(R.id.et_price_cust);
        cust_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView view = new TextView(context);
                view.setMovementMethod(ScrollingMovementMethod.getInstance());
                view.setText(cust_check.getText().toString());
                alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("终端信息");
                alertDialog.setCancelable(true);
                alertDialog.setView(view);
                alertDialog.show();
            }
        });
        user_check = (TextView) findViewById(R.id.et_price_user);
        operate_check = (TextView) findViewById(R.id.et_price_operat);
        add.setOnClickListener(this);
        query.setOnClickListener(this);
        reset.setOnClickListener(this);
        del.setOnClickListener(this);
        go.setOnClickListener(this);
        copy.setOnClickListener(this);
        load.setOnClickListener(this);
        in.setOnClickListener(this);
        out.setOnClickListener(this);
        delmx.setOnClickListener(this);
        num_chage.setOnClickListener(this);
//        addone.setOnClickListener(this);
        priceset.setOnClickListener(this);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        time.setOnClickListener(this);
        db.setOnClickListener(this);
        brand.setOnClickListener(this);
        hs.setOnClickListener(this);
        cust.setOnClickListener(this);
        user.setOnClickListener(this);
        operate.setOnClickListener(this);
        checkedIndexList = new ArrayList<Integer>();
        checkBoxList = new ArrayList<CheckBox>();
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
        //新增之前不能操作
        noDo();
    }

    private void noDo() {
        brand.setEnabled(false);
        hs.setEnabled(false);
        cust.setEnabled(false);
        user.setEnabled(false);
        operate.setEnabled(false);
        et_num.setFocusable(false);
        et_tyzk.setFocusable(false);
        zcsm.setFocusable(false);
        zcmc.setFocusable(false);
        zxqk.setFocusable(false);
        start.setEnabled(false);
        stop.setEnabled(false);
        time.setEnabled(false);
        num_chage.setEnabled(false);
//        addone.setEnabled(false);
        priceset.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.addOne:
//                one_add=1;
//                Toast.makeText(context, "新增子表一行", Toast.LENGTH_LONG).show();
//                View view_add = getLayoutInflater()
//                        .inflate(R.layout.add_one, null);
////
//                id_addone = (EditText) view_add.findViewById(R.id.et_addone_id);
//                name_addone = (EditText) view_add.findViewById(R.id.et_addone_name);
//                dwcb_addone = (EditText) view_add.findViewById(R.id.et_addone_dwcb);
//                tydj_addone = (EditText) view_add.findViewById(R.id.et_addone_tydj);
//                zdsj_addone = (EditText) view_add.findViewById(R.id.et_addone_zdsj);
//                zcdj_addone = (EditText) view_add.findViewById(R.id.et_addone_zcdj);
//                xszk_addone = (EditText) view_add.findViewById(R.id.et_addone_xszk);
//                zxqk_addone = (EditText) view_add.findViewById(R.id.et_addone_zxqk);
//                bzxx_addone = (EditText) view_add.findViewById(R.id.et_addone_bzxx);
//
//                ok = (Button) view_add.findViewById(R.id.addOne_ok);
//                qx = (Button) view_add.findViewById(R.id.addOne_qx);
//                ok.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        // 判断是否存在
//                        String bh = id_addone.getText().toString();
//                        String mc = name_addone.getText().toString();
//                        String dwcb = dwcb_addone.getText().toString();
//                        String tydj = tydj_addone.getText().toString();
//                        String zdsj = zdsj_addone.getText().toString();
//                        String zcdj = zcdj_addone.getText().toString();
//                        String xszk = xszk_addone.getText().toString();
//                        String zxqk = zxqk_addone.getText().toString();
//                        String bzxx = bzxx_addone.getText().toString();
//                        if (mListView1.getCount() > 0 || mListView1 != null) {
//                            for (int i = 0; i < mListView1.getCount(); i++) {
//                                if (done == 1) {
//                                    PriceLoadInfo.Prdt prdt_ls = (PriceLoadInfo.Prdt) mListView1.getAdapter().getItem(i);
//                                        if (!prdt_ls.getPRD_NO().equals(bh) || !prdt_ls.getNAME().equals(mc)) {
//
//                                            new_info = new PriceLoadInfo.Prdt("0", bh, mc, Double.parseDouble(dwcb),
//                                                    Double.parseDouble(tydj), Double.parseDouble(zdsj), zcdj, xszk, zxqk, bzxx);
//                                            Log.e("LiNing", "添加数据==========" + new_info);
//                                        } else {
//                                            Toast.makeText(context, "此数据已存在", Toast.LENGTH_LONG).show();
//                                        }
//                                }
//                                if (done == 3) {
//                                    mx_ls = (PriceMx.PrdtUp) mListView1.getAdapter().getItem(i);
//                                    if (!mx_ls.getPrdNo().equals(bh) ) {
//
//                                        mx_ls = new PriceMx.PrdtUp(0, bh, mc, dwcb, tydj, zdsj, zcdj, xszk, zxqk, bzxx);
//                                        Log.e("LiNing", "添加数据==========" + new_info);
//                                    } else {
//                                        Toast.makeText(context, "此数据已存在", Toast.LENGTH_LONG).show();
//                                    }
//                                }
//                            }
//
//                        }
//                        if (done == 1) {
//                            prdt.add(0, new_info);
//                            Log.e("LiNing", "新数据==========" + prdt);
//                            priceAdapter = new PriceAdapter(one_add,onclik_go, R.layout.price_fuction_item1, prdt, PriceActivity.this);
//                            priceAdapter.notifyDataSetChanged();
//                            mListView1.setAdapter(priceAdapter);
//                            if (alertDialog.isShowing()) {
//                                alertDialog.dismiss();
//                            }
//                        }
//                        if (done == 3) {
//                            mx_prdtUp.add(0, mx_ls);
//                            Log.e("LiNing", "新数据==========" + prdt);
//                            mxPriceAdapter = new MxPriceAdapter(one_add,onclik_go, R.layout.price_fuction_item1, mx_prdtUp, (PriceActivity) context);
//                            mxPriceAdapter.notifyDataSetChanged();
//                            mListView1.setAdapter(mxPriceAdapter);
//                            if (alertDialog.isShowing()) {
//                                alertDialog.dismiss();
//                            }
//                        }
//
//                        Toast.makeText(context, "修改中。。。", Toast.LENGTH_LONG).show();
//                        alertDialog.dismiss();
//                    }
//                });
//                qx.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        alertDialog.dismiss();
//                    }
//                });
//                alertDialog = new AlertDialog.Builder(context).create();
//                alertDialog.setCancelable(false);
//                alertDialog.setView(view_add);
//                alertDialog.show();
//                break;
            case R.id.priceSet:
                more=2;
                one_add=2;
                Intent intent_set=new Intent(context,AddMoreActivity.class);
                intent_set.putExtra("DONE",""+done);
                intent_set.putExtra("DB_PRICE",db_check.getText().toString());
                intent_set.putExtra("ON_CLICK",""+onclik_go);
                startActivityForResult(intent_set, 21);
                break;
//            case R.id.priceSet:
//                 more=1;
//                one_add=2;
//                prdt_add = new ArrayList<PriceLoadInfo.Prdt>();
//                prdt_add_mx = new ArrayList<PriceMx.PrdtUp>();
//
//                Toast.makeText(context, "价格修订", Toast.LENGTH_LONG).show();
//                View view_load_xg = getLayoutInflater()
//                        .inflate(R.layout.set_price, null);
//                TextView head_load_xg = (TextView) view_load_xg.findViewById(R.id.all_head);
//
//                head_load_xg.setText("货品加载");
//                //下拉按钮获取信息
//                query_sup = (ImageButton) view_load_xg.findViewById(R.id.ib_load_sup);
//                query_compdep = (ImageButton) view_load_xg.findViewById(R.id.ib_load_compdep);
//                employee = (ImageButton) view_load_xg.findViewById(R.id.ib_load_employee);
//                prdNo = (ImageButton) view_load_xg.findViewById(R.id.ib_load_prdNo);
//                prdIndx = (ImageButton) view_load_xg.findViewById(R.id.ib_load_prdIndx);
//                prdWh = (ImageButton) view_load_xg.findViewById(R.id.ib_load_prdWh);
//                prdMrk = (EditText) view_load_xg.findViewById(R.id.et_load_prdMrk);
//                prdName = (EditText) view_load_xg.findViewById(R.id.et_load_prdName);
//                prdName_ENG = (EditText) view_load_xg.findViewById(R.id.et_load_prdName_ENG);
//                prdLevre = (ImageButton) view_load_xg.findViewById(R.id.ib_load_prdLevel);
//                prdKND = (ImageButton) view_load_xg.findViewById(R.id.ib_load_prdKND);
//                showStop = (ImageButton) view_load_xg.findViewById(R.id.ib_load_stop);
//                tv_qurey_sup = (TextView) view_load_xg.findViewById(R.id.et_load_sup);
//                tv_query_compdep = (TextView) view_load_xg.findViewById(R.id.et_load_compdep);
//                tv_employee = (TextView) view_load_xg.findViewById(R.id.et_load_employee);
//                tv_prdNo = (TextView) view_load_xg.findViewById(R.id.et_load_prdNo);
//                tv_prdIndx = (TextView) view_load_xg.findViewById(R.id.et_load_prdIndx);
//                tv_prdWh = (TextView) view_load_xg.findViewById(R.id.et_load_prdWh);
//                tv_prdLevre = (TextView) view_load_xg.findViewById(R.id.et_load_prdLevel);
//                tv_prdKND = (TextView) view_load_xg.findViewById(R.id.et_load_prdKND);
//                tv_showStop = (TextView) view_load_xg.findViewById(R.id.et_load_stop);
//                this.mHead = ((RelativeLayout) view_load_xg.findViewById(R.id.price_head_set));
//                this.mHead.setFocusable(true);
//                this.mHead.setClickable(true);
////                this.mHead_more.setOnTouchListener(new ListViewAndHeadViewTouchLinstener_more());
//                this.mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
//                this.mListView1_set = (ListView) view_load_xg.findViewById(R.id.lv_salePut_header_set);
//                this.mListView1_set
//                        .setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
//
//                //点击
//                query_sup.setOnClickListener(this);
//                query_compdep.setOnClickListener(this);
//                employee.setOnClickListener(this);
//                prdNo.setOnClickListener(this);
//                prdIndx.setOnClickListener(this);
//                prdWh.setOnClickListener(this);
//                prdLevre.setOnClickListener(this);
//                prdKND.setOnClickListener(this);
//                showStop.setOnClickListener(this);
//                view_load_xg.findViewById(R.id.imageButton1).setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (alertDialog.isShowing()) {
//
//                            alertDialog.dismiss();
//                        }
//                        listClear();
//                    }
//
//
//                });
//                //加载
//                view_load_xg.findViewById(R.id.btn_put).setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(context, "价格修订-加载", Toast.LENGTH_LONG).show();
//                        load_ok_set();
//                    }
//                });
//                //确认
//                view_load_xg.findViewById(R.id.btn_addProject).setOnClickListener(new OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                        //判断添加的数据是否存在
//                        if (mListView1_set.getCount() > 0) {
//                            ls_set = new ArrayList<>();
//                            for (int i = 0; i < mListView1_set.getCount(); i++) {
//                                prdt_set = (PriceLoadInfo.Prdt) mListView1_set.getAdapter().getItem(i);
//                                Log.e("LiNing", "添加数据====prdt_set====" + prdt_set);
//                                if (done == 1) {
//                                    prdt_add.add(prdt_set);
//                                }
//                                mx_ls_mor = new PriceMx.PrdtUp(Integer.parseInt(prdt_set.getXH()), prdt_set.getPRD_NO(), prdt_set.getNAME(),
//                                        "" + prdt_set.getUP_SAL(), "" + prdt_set.getUPR(), "" + prdt_set.getUP_MIN(), prdt_set.getZC_DJ(), prdt_set.getZC_ZK(),
//                                        prdt_set.getZC_ZXQK(), prdt_set.getZC_BZXX());
//                                if (done == 3) {
//                                    prdt_add_mx.add(mx_ls_mor);
//                                }
//                                String prd_no_set = prdt_set.getPRD_NO();
//                                ls_set.add(prd_no_set);
//                                Log.e("LiNing", "添加数据====ls====" + ls_set);
//                            }
//                        }else{
//                            Toast.makeText(context, "请加载数据", Toast.LENGTH_LONG).show();
//                        }
//                        if (done == 1) {
//
//                            for (int j = 0; j < mListView1.getCount(); j++) {
//                                prdt2 = (PriceLoadInfo.Prdt) mListView1.getAdapter().getItem(j);
//                                prd_no = prdt2.getPRD_NO();
//                                Log.e("LiNing", "添加数据====pd====" + prd_no);
//                                Log.e("LiNing", "添加数据====ls====" + ls_set);
//                                if (!ls_set.contains(prd_no)) {
//                                    prdt_add.add(prdt2);
//                                    Log.e("LiNing", "包含数据====pd====" + prdt2);
//                                }
//                            }
//                            Log.e("LiNing", "添加数据=====新=====" + prdt_add);
//                            priceAdapter = new PriceAdapter(one_add,onclik_go, R.layout.price_fuction_item1, prdt_add, PriceActivity.this);
//                            mListView1.setAdapter(priceAdapter);
//                            priceAdapter.notifyDataSetChanged();
//                            if (alertDialog.isShowing()) {
//                                alertDialog.dismiss();
//                            }
//                            Log.e("LiNing", "添加数据=====3=====" + prdt_add.size());
//
//                        }
//                        if (done == 3) {
//                            for (int j = 0; j < mListView1.getCount(); j++) {
//                                prdt3 = (PriceMx.PrdtUp) mListView1.getAdapter().getItem(j);
//                                prd_no = prdt3.getPrdNo();
//                                Log.e("LiNing", "添加数据====pd====" + prd_no);
//                                Log.e("LiNing", "添加数据====ls====" + ls_set);
//                                if (!ls_set.contains(prd_no)) {
//                                    prdt_add_mx.add(prdt3);
//                                    Log.e("LiNing", "包含数据====pd====" + prdt3);
//                                }
//                            }
//                            mxPriceAdapter = new MxPriceAdapter(one_add,onclik_go, R.layout.price_fuction_item1, prdt_add_mx, (PriceActivity) context);
//                            mListView1.setAdapter(mxPriceAdapter);
//                            mxPriceAdapter.notifyDataSetChanged();
//                            if (alertDialog.isShowing()) {
//                                alertDialog.dismiss();
//                            }
//                        }
//
//                    }
//                });
//                alertDialog = new AlertDialog.Builder(context).create();
//                alertDialog.setCancelable(false);
//                alertDialog.setView(view_load_xg);
//                alertDialog.show();
//
//                break;

            //政策短时间
            case R.id.btn_price_time:

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
                                startTime = String.format("%d-%d-%d", year,
                                        monthOfYear + 1, dayOfMonth);
                                time.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间


//                Calendar c_time = Calendar.getInstance();
//                int mYear_time = c_time.get(Calendar.YEAR); // 获取当前年份
//                new DatePickerDialog(context, DatePickerDialog.THEME_HOLO_LIGHT,
//                        new DatePickerDialog.OnDateSetListener() {
//
//                            @Override
//                            public void onDateSet(DatePicker view, int year,
//                                                  int monthOfYear, int dayOfMonth) {
//                                startTime = String.format("%d-%d-%d", year,
//                                        monthOfYear + 1, dayOfMonth);
//                                time.setText(startTime);
//
//                            }
////					}, 2015, 0, 1).show();
//                        }, mYear_time, 0, 1).show();
                break;
            //开始时间
            case R.id.btn_price_start:
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
            //结束时间
            case R.id.btn_price_stop:
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
            //账套选择
            case R.id.ib_price_account:
                ib_xl = 1;
                Intent intent = new Intent(context, CondicionInfoActivity.class);
                intent.putExtra("flag", "1");
                startActivityForResult(intent, 1);
                break;
            //品牌选择
            case R.id.ib_price_brand:
                ib_xl = 2;
//                Intent intent2 = new Intent(context, BrandNumActivity.class);
//                intent2.putExtra("flag", "2");
//                startActivityForResult(intent2, 2);
                if (!db_check.getText().toString().equals("")) {

                    Intent intent12 = new Intent(context,
                            CondicionInfoActivity.class);
                    intent12.putExtra("flag", "12");
                    intent12.putExtra("queryID", db_check.getText().toString());
                    startActivityForResult(intent12, 12);
                } else {
                    Toast.makeText(context, "请选择单账套", Toast.LENGTH_LONG).show();
                }
                break;
            //核算单位
            case R.id.ib_price_hs:
                ib_xl = 3;
                if (!db_check.getText().toString().equals("")) {

                    Intent intent10 = new Intent(context,
                            CondicionInfoActivity.class);
                    intent10.putExtra("flag", "10");
                    intent10.putExtra("queryID", db_check.getText().toString());
                    startActivityForResult(intent10, 10);
                } else {
                    Toast.makeText(context, "请选择单账套", Toast.LENGTH_LONG).show();
                }
                break;
            //适用终端
            case R.id.ib_price_cust:
                ib_xl = 4;
                if (!db_check.getText().toString().equals("")) {

                    Intent intent13 = new Intent(context,
                            CondicionInfoActivity.class);
                    intent13.putExtra("flag", "13");
                    intent13.putExtra("queryID", db_check.getText().toString());
                    startActivityForResult(intent13, 13);
                } else {
                    Toast.makeText(context, "请选择单账套", Toast.LENGTH_LONG).show();
                }
                break;

            //参照对象
            case R.id.ib_price_user:
//                et_num.setText("0");
//                et_tyzk.setText("100");
                checked = 1;
                showPopupMenu(user);
                break;
            //是否停用
            case R.id.ib_load_stop:
                checked = 5;
                showPopupMenu(showStop);
                break;
            //运算符
            case R.id.ib_price_operat:
                checked = 2;
                showPopupMenu(operate);
                break;
            //新增
            case R.id.btn_price_add:
                if (p_add.equals("true")) {
                    //清空数据
                    clearInfo();
                    eAbleInfo();//可编辑
                    done = 1;
//                    out_info=1;
                } else {

                    Toast.makeText(context, "无此权限", Toast.LENGTH_LONG).show();
                }

                break;

            //查询
            case R.id.btn_price_query:

                queryInfo();

                break;
            //修改
            case R.id.btn_price_reset:
                if (p_reset.equals("true")) {
                    //传单号和数据库(未执行的)
//                    if (execute == 1) {
                    if (do_all == 2) {

//                        if (accepted_one_mx.equals("Y")) {
//                            Toast.makeText(context, "该单号不能被修改", Toast.LENGTH_LONG).show();
//                        } else {

                        new AlertDialog.Builder(context)
                                .setTitle("是否修改")
                                .setPositiveButton("是",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                //此处判断哪个的修改（速查，新增，转入）
                                                onclik_go = 3;
                                                eAbleInfo();//可编辑
                                                done = 3;
                                                mxPriceAdapter = new MxPriceAdapter(onclik_go, R.layout.price_fuction_item1, mx_prdtUp, (PriceActivity) context);
                                                mListView1.setAdapter(mxPriceAdapter);
                                                mxPriceAdapter.notifyDataSetChanged();
                                            }
                                        }).setNegativeButton("否", null).show();
//                        }
                    }
                    if (do_all == 1) {
                        new AlertDialog.Builder(context)
                                .setTitle("是否修改")
                                .setPositiveButton("是",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                eAbleInfo();//可编辑
                                                done = 1;
                                            }
                                        }).setNegativeButton("否", null).show();
                    }
                    if (do_all == 4) {
                        new AlertDialog.Builder(context)
                                .setTitle("是否修改")
                                .setPositiveButton("是",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                eAbleInfo();//可编辑
                                                done = 4;
                                                onclik_go = 4;
                                                inAdapter = new InAdapter(onclik_go, R.layout.price_fuction_item1, in_list, context);
                                                mListView1.setAdapter(inAdapter);
                                                inAdapter.notifyDataSetChanged();
                                            }
                                        }).setNegativeButton("否", null).show();
                    }
                } else {

                    Toast.makeText(context, "无此权限", Toast.LENGTH_LONG).show();
                }
                break;
            //删除
            case R.id.btn_price_del:
                if (p_del.equals("true")) {
                    if (do_all == 2) {

//                        if (accepted_one_mx.equals("Y")) {
//                            Toast.makeText(context, "该单号不能被删除", Toast.LENGTH_LONG).show();
//                        } else {
                        // 传单号和数据库
                        new AlertDialog.Builder(context)
                                .setTitle("是否删除")
                                .setPositiveButton("是",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                delPrice();
                                            }
                                        }).setNegativeButton("否", null).show();
//                        }
                    }
                    if (do_all == 1) {
                        new AlertDialog.Builder(context)
                                .setTitle("是否删除")
                                .setPositiveButton("是",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                delPrice();
                                            }
                                        }).setNegativeButton("否", null).show();
                    }
                    if (do_all == 4) {
                        new AlertDialog.Builder(context)
                                .setTitle("是否删除")
                                .setPositiveButton("是",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                delPrice();
                                            }
                                        }).setNegativeButton("否", null).show();
                    }


                } else {

                    Toast.makeText(context, "无此权限", Toast.LENGTH_LONG).show();
                }
                break;
            //保存
            case R.id.btn_price_go:
//                if (y_bc == 1) {
//                    Toast.makeText(context, "已保存", Toast.LENGTH_LONG).show();
//                } else {

                save = 1;
                onclik_go = 2;
                if (mListView1 != null && mListView1.getCount() > 0) {
                    //判断不同的操作（新增，修改，执行）
                    if (done == 1) {
                        if (cust_check.getText().toString().equals("null")
                                || cust_check.getText().toString().equals("")
                                ) {
                            eAbleInfo();
                            Toast.makeText(context, "请选择终端", Toast.LENGTH_LONG).show();

                        } else {
//                            主表信息（参考对象等）
                            MainInfoAdd();
                            //新增
                            if(one_add==2){
                                mxPriceAdapter = new MxPriceAdapter(onclik_go, R.layout.price_fuction_item1, prdt_add_mx, (PriceActivity) context);
                                mListView1.setAdapter(mxPriceAdapter);
                                mxPriceAdapter.notifyDataSetChanged();
                            }else{

                                priceAdapter = new PriceAdapter(one_add,onclik_go, R.layout.price_fuction_item1, prdt, PriceActivity.this);
                                mListView1.setAdapter(priceAdapter);
                                priceAdapter.notifyDataSetChanged();
                            }
                            getList_no();//获取list数据，数组格式提交
                            postAdd();
                        }

                    }
                    if (done == 3) {
//                        MxPriceAdapter mxPriceAdapter = new MxPriceAdapter(onclik_go, R.layout.price_fuction_item1, mx_prdtUp, (PriceActivity) context);
//                        mListView1.setAdapter(mxPriceAdapter);
//                        postSet();
//                        setPrice();
                        MainInfoAdd();
                        if(one_add==2){
                            mxPriceAdapter = new MxPriceAdapter(onclik_go, R.layout.price_fuction_item1, prdt_add_mx, (PriceActivity) context);
                            mListView1.setAdapter(mxPriceAdapter);
                            mxPriceAdapter.notifyDataSetChanged();
                        }else{
                            mxPriceAdapter = new MxPriceAdapter(onclik_go, R.layout.price_fuction_item1, mx_prdtUp, (PriceActivity) context);
                            mListView1.setAdapter(mxPriceAdapter);
                            mxPriceAdapter.notifyDataSetChanged();
                        }
                        getList_no();//获取list数据，数组格式提交
                        postAdd();
                        fouseAbleInfo();//禁止编辑
                    }
                    if (done == 4) {
                        if (db_check.getText().toString().equals("null")
                                || db_check.getText().toString().equals("")
                                ) {
                            eAbleInfo();
                            Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();

                        } else if (cust_check.getText().toString().equals("null")
                                || cust_check.getText().toString().equals("")
                                ) {
                            eAbleInfo();
                            Toast.makeText(context, "请选择终端", Toast.LENGTH_LONG).show();

                        } else {
                            MainInfoAdd();
                            getList_no();//获取list数据，数组格式提交
                            postAdd();
                            fouseAbleInfo();//禁止编辑
                        }
                    }

                } else {
                    Toast.makeText(this.context, "请加载数据", Toast.LENGTH_LONG).show();
                }
//                }
                break;
            //政策拷贝
            case R.id.btn_price_copy:
                queryInfo();//获取拷贝订单
                if (copy_info == 1) {
                    iscopy = 1;
                    price_num_id.setText(null);
                    zxqk.setText("N");
                    do_all = 2;
                    done = 3;
                } else {
                    Toast.makeText(context, "请选择拷贝订单信息", Toast.LENGTH_LONG).show();
                }
                break;

            //货品加载
            case R.id.btn_price_load:
                if(done==3||done==4){
                    isChecked = true;
                    bfor = 1;
                }
                View view_load = getLayoutInflater()
                        .inflate(R.layout.load_item, null);
                TextView head_load = (TextView) view_load.findViewById(R.id.all_head);

                head_load.setText("货品加载");
                //下拉按钮获取信息
                query_sup = (ImageButton) view_load.findViewById(R.id.ib_load_sup);
                query_compdep = (ImageButton) view_load.findViewById(R.id.ib_load_compdep);
                employee = (ImageButton) view_load.findViewById(R.id.ib_load_employee);
                prdNo = (ImageButton) view_load.findViewById(R.id.ib_load_prdNo);
                prdIndx = (ImageButton) view_load.findViewById(R.id.ib_load_prdIndx);
                prdWh = (ImageButton) view_load.findViewById(R.id.ib_load_prdWh);
                prdMrk = (EditText) view_load.findViewById(R.id.et_load_prdMrk);
                prdName = (EditText) view_load.findViewById(R.id.et_load_prdName);
                prdName_ENG = (EditText) view_load.findViewById(R.id.et_load_prdName_ENG);
                prdLevre = (ImageButton) view_load.findViewById(R.id.ib_load_prdLevel);
                prdKND = (ImageButton) view_load.findViewById(R.id.ib_load_prdKND);
                showStop = (ImageButton) view_load.findViewById(R.id.ib_load_stop);
                tv_qurey_sup = (TextView) view_load.findViewById(R.id.et_load_sup);
//                tv_qurey_sup.setText(brand_check.getText().toString());//同步主表信息
                tv_query_compdep = (TextView) view_load.findViewById(R.id.et_load_compdep);
//                tv_query_compdep.setText(hs_check.getText().toString());//同步主表信息
                tv_employee = (TextView) view_load.findViewById(R.id.et_load_employee);
                tv_prdNo = (TextView) view_load.findViewById(R.id.et_load_prdNo);
                tv_prdIndx = (TextView) view_load.findViewById(R.id.et_load_prdIndx);
                tv_prdWh = (TextView) view_load.findViewById(R.id.et_load_prdWh);
                tv_prdLevre = (TextView) view_load.findViewById(R.id.et_load_prdLevel);
                tv_prdKND = (TextView) view_load.findViewById(R.id.et_load_prdKND);
                tv_showStop = (TextView) view_load.findViewById(R.id.et_load_stop);
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

                alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setCancelable(false);
                alertDialog.setView(view_load);
                alertDialog.show();
                view_load.findViewById(R.id.imageButton1).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (alertDialog.isShowing()) {

                            alertDialog.dismiss();
                        }
                        listClear();
                    }


                });
                break;
            //政策转入
            case R.id.btn_price_in:
//                clearInfo();
                //①直接获取对应表信息
//                String in_info = ExcelToList.ImportExcelData();
//                Log.e("LiNing", "转入数据----" + ExcelToList.ImportExcelData());
                //②跳转到文件管理器选择
                Intent intent_zr = new Intent(Intent.ACTION_GET_CONTENT);
                intent_zr.setType("*/*");
                intent_zr.addCategory(Intent.CATEGORY_OPENABLE);
                try {
                    startActivityForResult(Intent.createChooser(intent_zr, "Select a File to Upload"), 2);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(PriceActivity.this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
                }
                break;
            //政策转出
            case R.id.btn_price_out:
                if (p_out.equals("true")) {
                    if (mListView1 != null && mListView1.getCount() > 0) {
                        if (out_info == 1) {
                            new AlertDialog.Builder(context)
                                    .setTitle("请先查询此订单(*直接转出数据可能变化*)")
                                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            done = 1;
                                            getList_no();
                                            Log.e("LiNing", out_info + "----" + price_num_id.getText().toString() + info_add);
                                            Intent intent1 = new Intent(context, PriceOutActivity.class);
                                            intent1.putExtra("ZC_ID", "" + out_info);
                                            intent1.putExtra("ZC_DH", price_num_id.getText().toString());
                                            startActivity(intent1);
                                        }
                                    })
                                    .setNegativeButton("否",null)
                                    .show();

                        } else if (out_info == 2) {
                            done = 3;
                            getList_no();
                            Log.e("LiNing", out_info + "----" + price_num_id.getText().toString() + info_query);
                            Intent intent1 = new Intent(context, PriceOutActivity.class);
                            intent1.putExtra("ZC_CHECK", "" + isChecked);
                            intent1.putExtra("ZC_ID", "" + out_info);
                            intent1.putExtra("ZC_DH", price_num_id.getText().toString());
                            startActivity(intent1);
                        } else if (out_info == 4) {
                            done = 4;
                            getList_no();
                            Log.e("LiNing", out_info + "----" + price_num_id.getText().toString() + info_zr);
                            Intent intent1 = new Intent(context, PriceOutActivity.class);
                            intent1.putExtra("ZC_CHECK", "" + isChecked);
                            intent1.putExtra("ZC_ID", "" + out_info);
                            intent1.putExtra("ZC_DH", price_num_id.getText().toString());
                            startActivity(intent1);
                        } else {
                            Toast.makeText(this.context, "请选择转出数据", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(this.context, "请加载数据", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "无此权限", Toast.LENGTH_LONG).show();
                }
                break;
            //政策执行
            case R.id.btn_price_delmx:
//                done = 3;
//                Toast.makeText(this.context, "已点击", Toast.LENGTH_LONG).show();
//                eAbleInfo();//可编辑
//                        if(y_zx==1){
//                            Toast.makeText(this.context, "已执行", Toast.LENGTH_LONG).show();
//                        }else {

                if (mListView1 != null && mListView1.getCount() > 0) {
                    if (do_all == 1) {

                        getList_no();//获取list数据，数组格式提交
                        MainInfoAdd();
                        exuteInfo();
                    } else if (do_all == 2) {
//                        if (accepted_one_mx.equals("Y")) {
//                            Toast.makeText(context, "该单号不能被执行", Toast.LENGTH_LONG).show();
//                        } else {
                        getList_no();//获取list数据，数组格式提交
                        MainInfoAdd();
                        exuteInfo();
//                        }
                    } else {
                        Toast.makeText(context, "操作错误", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(this.context, "请加载数据", Toast.LENGTH_LONG).show();
                }
//                        }
                break;
            //执行运算
            case R.id.btn_price_make:
                onclik_go = 1;
                if (mListView1.getCount() > 0 && mListView1 != null) {

                    Log.e("LiNing", "转入数据--done--" + done);
                    if (done == 1) {
                        if(one_add==1||more==1){

                            priceAdapter = new PriceAdapter(one_add,onclik_go, R.layout.price_fuction_item1, prdt_add, PriceActivity.this);
                            mListView1.setAdapter(priceAdapter);
                            priceAdapter.notifyDataSetChanged();
                        }
                        else if(one_add==2||more==2){

                            mxPriceAdapter = new MxPriceAdapter(onclik_go, R.layout.price_fuction_item1, prdt_add_mx, (PriceActivity) context);
                            mListView1.setAdapter(mxPriceAdapter);
                            mxPriceAdapter.notifyDataSetChanged();
                        }
                        else{
                            priceAdapter = new PriceAdapter(one_add,onclik_go, R.layout.price_fuction_item1, prdt, PriceActivity.this);
                            mListView1.setAdapter(priceAdapter);
                            priceAdapter.notifyDataSetChanged();
                        }
                    } else if (done == 3) {
                        if (isChecked == true) {
//                            if(one_add==2){
//
//                                priceAdapter = new PriceAdapter(one_add,onclik_go, R.layout.price_fuction_item1, prdt_add, PriceActivity.this);
//                                mListView1.setAdapter(priceAdapter);
//                                priceAdapter.notifyDataSetChanged();
//                            }
                            if(more==2||one_add==2){

                                mxPriceAdapter = new MxPriceAdapter(onclik_go, R.layout.price_fuction_item1, prdt_add_mx, (PriceActivity) context);
                                mListView1.setAdapter(mxPriceAdapter);
                                mxPriceAdapter.notifyDataSetChanged();
                            }
                            else{
                                priceAdapter = new PriceAdapter(one_add,onclik_go, R.layout.price_fuction_item1, prdt, PriceActivity.this);
                                mListView1.setAdapter(priceAdapter);
                                priceAdapter.notifyDataSetChanged();
                            }
//                            priceAdapter = new PriceAdapter(one_add,onclik_go, R.layout.price_fuction_item1, prdt, PriceActivity.this);
//                            mListView1.setAdapter(priceAdapter);
//                            priceAdapter.notifyDataSetChanged();
                        } else {//prdt_add_mx

//                            if(one_add==2){
//                                mxPriceAdapter = new MxPriceAdapter(one_add,onclik_go, R.layout.price_fuction_item1, prdt_add_mx, (PriceActivity) context);
//                                mListView1.setAdapter(mxPriceAdapter);
//                                mxPriceAdapter.notifyDataSetChanged();
//                            }
                            if(more==2||one_add==2){

                                mxPriceAdapter = new MxPriceAdapter(onclik_go, R.layout.price_fuction_item1, prdt_add_mx, (PriceActivity) context);
                                mListView1.setAdapter(mxPriceAdapter);
                                mxPriceAdapter.notifyDataSetChanged();
                            }
                            else{

                                mxPriceAdapter = new MxPriceAdapter(onclik_go, R.layout.price_fuction_item1, mx_prdtUp, (PriceActivity) context);
                                mListView1.setAdapter(mxPriceAdapter);
                                mxPriceAdapter.notifyDataSetChanged();
                            }
                        }
                    } else if (done == 4) {
                        if (isChecked == true) {

                            priceAdapter = new PriceAdapter(one_add,onclik_go, R.layout.price_fuction_item1, prdt, PriceActivity.this);
                            mListView1.setAdapter(priceAdapter);
                            priceAdapter.notifyDataSetChanged();
                        } else {

                            inAdapter = new InAdapter(onclik_go, R.layout.price_fuction_item1, in_list, context);
                            mListView1.setAdapter(inAdapter);
                            inAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    Toast.makeText(this.context, "请加载数据", Toast.LENGTH_LONG).show();
                }

                break;
            //ERP厂商获取
            case R.id.ib_load_sup:
                //获取账套
//                String DB_name=db_check.getText().toString();
//                Log.e("LiNing","DB_name--------"+DB_name);
                if (!db_check.getText().toString().equals("")) {

                    Intent intent12 = new Intent(context,
                            CondicionInfoActivity.class);
                    intent12.putExtra("flag", "12");
                    intent12.putExtra("queryID", db_check.getText().toString());
                    startActivityForResult(intent12, 15);
                } else {
                    Toast.makeText(context, "请选择单账套", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(context, "erp厂商", Toast.LENGTH_LONG).show();
                break;
            //核算部门（ERP）
            case R.id.ib_load_compdep:
                if (!db_check.getText().toString().equals("")) {

                    Intent intent10 = new Intent(context,
                            CondicionInfoActivity.class);
                    intent10.putExtra("flag", "10");
                    intent10.putExtra("queryID", db_check.getText().toString());
                    startActivityForResult(intent10, 16);
                } else {
                    Toast.makeText(context, "请选择单账套", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(context, "核算部门（ERP）", Toast.LENGTH_LONG).show();
                break;
            //销售人员
            case R.id.ib_load_employee:
                if (!db_check.getText().toString().equals("")) {
                    Intent intent17 = new Intent(context,
                            CondicionInfoActivity.class);
                    intent17.putExtra("flag", "17");
                    intent17.putExtra("queryID", db_check.getText().toString());
                    startActivityForResult(intent17, 17);
                } else {
                    Toast.makeText(context, "请选择单账套", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(context, "销售人员", Toast.LENGTH_LONG).show();
                break;
            //品号
            case R.id.ib_load_prdNo:
                if (!db_check.getText().toString().equals("")) {
                    Intent intent18 = new Intent(context,
                            CondicionInfoActivity.class);
                    intent18.putExtra("flag", "18");
                    intent18.putExtra("queryID", db_check.getText().toString());
                    startActivityForResult(intent18, 18);
                } else {
                    Toast.makeText(context, "请选择单账套", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(context, "品号", Toast.LENGTH_LONG).show();
                break;
            //货品中类
            case R.id.ib_load_prdIndx:
                if (!db_check.getText().toString().equals("")) {
                    Intent intent19 = new Intent(context,
                            CondicionInfoActivity.class);
                    intent19.putExtra("flag", "19");
                    intent19.putExtra("queryID", db_check.getText().toString());
                    startActivityForResult(intent19, 19);
                } else {
                    Toast.makeText(context, "请选择单账套", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(context, "货品中类", Toast.LENGTH_LONG).show();
                break;
            //货品库区
            case R.id.ib_load_prdWh:
                if (!db_check.getText().toString().equals("")) {
                    Intent intent20 = new Intent(context,
                            CondicionInfoActivity.class);
                    intent20.putExtra("flag", "20");
                    intent20.putExtra("queryID", db_check.getText().toString());
                    startActivityForResult(intent20, 20);
                } else {
                    Toast.makeText(context, "请选择单账套", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(context, "货品库区", Toast.LENGTH_LONG).show();
                break;
            //货品等级（单选）
            case R.id.ib_load_prdKND:
//                Toast.makeText(context, "货品等级", Toast.LENGTH_LONG).show();
//                checked = 6;
//                showPopupMenu(prdLevre);
                Intent intent31 = new Intent(context,
                        LocalInfosActivity.class);
                intent31.putExtra("flag", "2");
                startActivityForResult(intent31, 31);
                break;
            //货品大类（单选）
            case R.id.ib_load_prdLevel:
//                Toast.makeText(context, "货品大类", Toast.LENGTH_LONG).show();
//                checked = 7;
//                showPopupMenu(prdKND);
                Intent intent30 = new Intent(context,
                        LocalInfosActivity.class);
                intent30.putExtra("flag", "1");
                startActivityForResult(intent30, 30);
                break;
        }
    }



    private void MainInfoAdd() {
        ckdx_zb = user_check.getText().toString();//参考对象
        ysf_zb = operate_check.getText().toString();//运算符
        yssz_zb = et_num.getText().toString();//运算数值
        tyzk_zb = et_tyzk.getText().toString();//统一折扣
        sm_add = zcsm.getText().toString();//政策说明
        accept_yn = zxqk.getText().toString();//执行情况
        namePrice_add = zcmc.getText().toString();//政策名称
        if (ckdx_zb.equals("")) {
            ckdx_zb = "";
        } else {
            if (ckdx_zb.equals("统一定价")) {
                ckdx_zb = "1";
            }
            if (ckdx_zb.equals("最低售价")) {
                ckdx_zb = "2";
            }
            if (ckdx_zb.equals("单位成本")) {
                ckdx_zb = "3";
            }
        }
        if (ysf_zb.equals("")) {
            ysf_zb = "";
        }

        if (yssz_zb.equals("")) {
            yssz_zb = "";
        }

        if (tyzk_zb.equals("")) {
            tyzk_zb = "100";
        }

        if (namePrice_add.equals("")) {
            namePrice_add = "";
        }

        if (sm_add.equals("")) {
            sm_add = "";
        }
    }

    private void queryInfo() {
        if (p_query.equals("true")) {
            View view = getLayoutInflater()
                    .inflate(R.layout.quick_query, null);
            start_quick = (Button) view.findViewById(R.id.btn_start_time);
            stop_quick = (Button) view.findViewById(R.id.btn_stop_time);
            et_seach = (EditText) view.findViewById(R.id.et_search_price);
            list_main = (ListView) view.findViewById(R.id.lv_main);
            TextView head_quick = (TextView) view.findViewById(R.id.all_head);
            Button dh_open = (Button) view.findViewById(R.id.btn_price_query_dh);
            dh_open.setVisibility(View.VISIBLE);
            head_quick.setText("主表信息");
            start_quick.setText(str_time);
            stop_quick.setText(str_time);
            start_quick.setOnClickListener(new OnClickListener() {
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
            stop_quick.setOnClickListener(new OnClickListener() {
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
            view.findViewById(R.id.imageButton1).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            dh_open.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //获取主表信息
                    getPrices();
                }
            });

            list_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    getAdapter.setSelectItem(position);//刷新
                    getAdapter.notifyDataSetInvalidated();
                    GetPriceInfo.Prices h2 = (GetPriceInfo.Prices) parent.getItemAtPosition(position);
                    db_id_mx = h2.getDb_Id();
                    priceId_mx = h2.getPriceId();
                    Log.e("LiNing", "---即将明细的数据" + h2.getDb_Id() + h2);
                    if (!db_id_mx.equals("") && !priceId_mx.equals("")) {
                        getInfo_mx();

                        alertDialog.dismiss();
                    }
                }
            });

            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setCancelable(true);
            alertDialog.setView(view);
            alertDialog.show();
        } else {

            Toast.makeText(context, "无此权限", Toast.LENGTH_LONG).show();
        }
    }

    private void postSet() {
        String db_add = db_check.getText().toString();
        String price_no_add = price_num_id.getText().toString();
        if (db_add.equals("")) {
            Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
        } else {
            OkHttpClient client = new OkHttpClient();
            url_add_price = URLS.price_addPrice;
            Log.e("LiNing", "----提交数据" + price_no_add + "/" + db_add);
            FormBody localFormBody = new FormBody.Builder()
//                    .add("isApp", "T")
                    .add("priceId", price_no_add)
                    .add("db_Id", db_add)
                    .build();

            Request localRequest = new Request.Builder()
                    .addHeader("cookie", session).url(url_add_price)
                    .post(localFormBody)
                    .build();
            client.newCall(localRequest).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    String str_add = response.body().string();
                    Log.e("LiNing", "修改的结果===" + str_add);
                    final PriceNumIDZs bean_id_new = (PriceNumIDZs) new Gson()
                            .fromJson(str_add, PriceNumIDZs.class);
                    if (done == 1) {
                        if (bean_id_new != null) {
                            PriceActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    String id_ls = bean_id_new.getBil_No();
                                    Log.e("LiNing", "保存的结果===" + id_ls);
                                    price_num_id.setText(id_ls);
                                    if (bean_id_new.getRLO() == true) {
                                        getMxInfo();
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
    }

    private void getInfo_mx() {
        onclik_go = 2;

        OkHttpClient client = new OkHttpClient();
        FormBody localFormBody = new FormBody.Builder()
                .add("db_Id", db_id_mx)
                .add("priceId", priceId_mx)
                .build();
        Request localRequest = new Request.Builder()
                .addHeader("cookie", session).url(url_getmx_price)
                .post(localFormBody)
                .build();
        client.newCall(localRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String mx_str = response.body().string();
                Log.e("LiNing", "所有政策表===" + mx_str);
                // 解析包含date的数据必须添加此代码(InputStream型)
                Gson gson = new GsonBuilder().setDateFormat(
                        "yyyy-MM-dd HH:mm:ss").create();
                final PriceMx cInfoDB = gson.fromJson(mx_str,
                        PriceMx.class);
                if (cInfoDB != null) {
                    PriceActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            //条件get
                            erPprice_id = cInfoDB.getPrice().getERPprice_Id();
                            Log.e("LiNing","----"+erPprice_id);
                            accepted_one_mx = cInfoDB.getPrice().getAccepted();
                            biln_user = cInfoDB.getPrice().getBiln_user();
                            mx_db_id = cInfoDB.getPrice().getDb_Id();
                            mx_db_name = cInfoDB.getPrice().getDb_Name();
                            db_check.setText(mx_db_id);
                            mx_sup_name = cInfoDB.getPrice().getSup_Name();
                            mx_sup_id = cInfoDB.getPrice().getSup_No();
                            brand_check.setText(mx_sup_name);
                            mx_compDepName_id = cInfoDB.getPrice().getCompDep();
                            mx_compDepName = cInfoDB.getPrice().getCompDepName();
                            hs_check.setText(mx_compDepName);
                            mx_salesTerminal_id = cInfoDB.getPrice().getSalesTerminal_No();
                            mx_salesTerminal_name = cInfoDB.getPrice().getSalesTerminal_Name();
                            cust_check.setText(mx_salesTerminal_name);
                            mx_price_dd = cInfoDB.getPrice().getPrice_DD().toString();
                            s_trime = TimeTo.openTime(mx_price_dd);
                            time.setText(s_trime);
                            mx_priceId = cInfoDB.getPrice().getPriceId();
                            price_num_id.setText(mx_priceId);
                            mx_priceName = cInfoDB.getPrice().getPriceName();
                            zcmc.setText(mx_priceName);
                            mx_rem = cInfoDB.getPrice().getRem();
                            zcsm.setText(mx_rem);
                            if(accepted_one_mx.equals("N")){
                                accepted_one_mx="未执行";
                            }
                            if(accepted_one_mx.equals("Y")){
                                accepted_one_mx="已执行";
                            }
                            zxqk.setText(accepted_one_mx);
                            String obj_type = cInfoDB.getPrice().getObj_Type();
                            if (obj_type.equals("1")) {
                                user_check.setText("统一定价");
                            }
                            if (obj_type.equals("2")) {
                                user_check.setText("最低售价");
                            }
                            if (obj_type.equals("3")) {
                                user_check.setText("单位成本");
                            }
                            String operator = cInfoDB.getPrice().getOperator();
                            operate_check.setText(operator);
                            String operator_val = cInfoDB.getPrice().getOperator_Val();
                            et_num.setText(operator_val);
                            String discount = cInfoDB.getPrice().getDiscount();
                            et_tyzk.setText(discount);
                            //listview数据
                            mx_prdtUp = cInfoDB.getPrice().getPrdtUp();

                            Log.e("LiNing", "返回结果===" + mx_prdtUp);
                            mxPriceAdapter = new MxPriceAdapter(onclik_go, R.layout.price_fuction_item1, mx_prdtUp, (PriceActivity) context);
                            mListView1.setAdapter(mxPriceAdapter);
                            Log.e("LiNing", "返回结果===" + accepted_one_mx);
                            fouseAbleInfo();//禁止编辑
                            do_all = 2;
                            done = 3;
                            out_info = 2;
                            copy_info = 1;

                        }
                    });

                }

            }
        });

    }

    private void exuteInfo() {

        String db_add = db_check.getText().toString();//账套
        String brand_add = brand_check.getText().toString();//品牌
        String hs_add = hs_check.getText().toString();//核算单位
        String cust_add = cust_check.getText().toString();//终端
        String price_no_add = price_num_id.getText().toString();//政策编号
        String time_add = time.getText().toString();//政策日期
        String time_start = start.getText().toString();//开始日期
        String time_stop = stop.getText().toString();//结束日期
        if(erPprice_id!=null){
            erPprice_id=erPprice_id;
        }else{
            erPprice_id="null";
        }
        Log.e("LiNing","--erp--"+erPprice_id);
//        if (db_add.equals("")) {
        if (db_add.equals("") || db_add == null) {
            Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
        } else if (price_no_add.equals("")) {
            Toast.makeText(context, "请新增单号", Toast.LENGTH_LONG).show();
        } else if (time_add.equals("")) {
            Toast.makeText(context, "请选政策日期", Toast.LENGTH_LONG).show();
        } else if (time_start.equals("")) {
            Toast.makeText(context, "请选开始日期", Toast.LENGTH_LONG).show();
        } else if (time_stop.equals("")) {
            Toast.makeText(context, "请选结束日期", Toast.LENGTH_LONG).show();
        } else {

            OkHttpClient client = new OkHttpClient();
            if (done == 1) {
                post_brand = id_querySup;
                post_hs = id_hs;
                post_cust = id_queryCust;
                post_db_name = str_name;
                if (brand_add.equals("") || brand_add == null || brand_add.equals(null)) {
                    brand_add = "all";
                    id_querySup = "all";
                }
                if (hs_add.equals("") || hs_add == null || hs_add.equals(null)) {
                    hs_add = "all";
                    id_hs = "all";
//                    Toast.makeText(context, "请选择核算人", Toast.LENGTH_LONG).show();
                }
            } else if (done == 3) {
                if (ib_xl == 1) {//点击下拉
                    post_db_name = str_name;

                } else {
                    post_db_name = mx_db_name;
                }
                if (ib_xl == 2) {//点击下拉
                    post_brand = id_querySup;
                } else {
                    post_brand = mx_sup_id;
                }
                if (ib_xl == 3) {//点击下拉
                    post_hs = id_hs;
                } else {
                    post_hs = mx_compDepName_id;
                }
                if (ib_xl == 4) {
                    post_cust = id_queryCust;
                } else {
                    post_cust = mx_salesTerminal_id;
                }
            }
            Log.e("LiNing", "提交的执行数据" +erPprice_id+ db_add + "/" + post_db_name + "/" + post_brand + "/" + brand_add + "/" + post_hs + "/" + hs_add + "/" + post_cust + "/" + cust_add
                    + "/" + ckdx_zb + "/" + ysf_zb + "/" + yssz_zb + "/" + tyzk_zb + "/" + price_no_add + "/" + namePrice_add + "/" + time_add + "/" + time_start + "/" + time_stop
                    + "/" + sm_add + "/" + accept_yn + "/" + sub_id + "/" + sub_ph + "/" + sub_dwcb + "/" + sub_tysj + "/" + sub_zdsj + "/" + sub_zcdj + "/" + sub_zczk + "/" + sub_sm);
            FormBody localFormBody = new FormBody.Builder()
                    .add("isApp", "T")
                    //主表信息/
                    .add("ERPprice_Id", erPprice_id)
                    .add("db_Id", db_add)//账套
                    .add("db_Name", post_db_name)//账套名称
                    .add("sup_No", post_brand)//品牌
                    .add("sup_Name", brand_add)//品牌名称
                    .add("compDep", post_hs)//核算
                    .add("compDepName", hs_add)//核算名称
                    .add("SalesTerminal_No", post_cust)//终端
                    .add("SalesTerminal_Name", cust_add)//终端名称
                    .add("obj_Type", ckdx_zb)//参考对象
                    .add("operator", ysf_zb)//运算符
                    .add("operator_Val", yssz_zb)//运算数值
                    .add("discount", tyzk_zb)//统一折扣
//                    .add("discount", Integer.parseInt(tyzk_zb))//统一折扣（整型）
                    .add("priceId", price_no_add)//政策编号
                    .add("priceName", namePrice_add)//政策名称
                    .add("price_DD", time_add)//政策日期
                    .add("useDate", time_start)//开始日期
                    .add("stopDate", time_stop)//结束日期
                    .add("rem", sm_add)//主表说明
                    .add("accepted", accept_yn)//执行情况
                    //明细表
                    .add("ITM", sub_id)
                    .add("prdNo", sub_ph)
                    .add("cst_Up", sub_dwcb)
                    .add("UPR", sub_tysj)
                    .add("MIN_UP", sub_zdsj)
                    .add("prdUp", sub_zcdj)
                    .add("dis_CNT", sub_zczk)
//                    .add("dis_CNT", Integer.parseInt(sub_zczk))//折扣（整型）
                    .add("REM", sub_sm)


                    .build();
            Request localRequest = new Request.Builder()
                    .addHeader("cookie", session).url(url_excut_price)
                    .post(localFormBody)
                    .build();
            client.newCall(localRequest).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    String str_add = response.body().string();
                    Log.e("LiNing", "执行的结果===" + str_add);
                    final PriceNumIDZs bean_id_new = (PriceNumIDZs) new Gson()
                            .fromJson(str_add, PriceNumIDZs.class);
                    if (bean_id_new != null) {
                        PriceActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                String id_ls = bean_id_new.getBil_No();
                                Log.e("LiNing", "执行的结果===" + id_ls);
                                boolean rlo = bean_id_new.getRLO();
                                getMxInfo();
                                if (rlo == true) {
                                    execute = 1;//标识已经执行（禁止修改）
                                    Toast.makeText(context, "执行成功", Toast.LENGTH_LONG).show();
                                    y_zx = 1;
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

    private void eAbleInfo() {
        //num_chage, start, stop, time,db, brand, hs, cust, user, operate,zcsm, zcmc, zxqk,et_num,et_tyzk
        db.setEnabled(true);
        brand.setEnabled(true);
        hs.setEnabled(true);
        cust.setEnabled(true);
        user.setEnabled(true);
        operate.setEnabled(true);
//        price_num_id.setFocusable(true);//
        et_num.setFocusableInTouchMode(true);
        et_tyzk.setFocusableInTouchMode(true);
        zcsm.setFocusableInTouchMode(true);
        zcmc.setFocusableInTouchMode(true);
        zxqk.setFocusableInTouchMode(true);
        start.setEnabled(true);
        stop.setEnabled(true);
        time.setEnabled(true);
        //此处时间不变
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_MONTH, 1); //当前月1号和最后日期
        start.setText(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
        stop.setText(str_time);
        time.setText(s_trime);
//        time.setText(date_dd);
//        start.setText(startTime);
        num_chage.setEnabled(true);
//        addone.setEnabled(true);
        priceset.setEnabled(true);


    }

    private void fouseAbleInfo() {
        db.setEnabled(false);
        brand.setEnabled(false);
        hs.setEnabled(false);
        cust.setEnabled(false);
        user.setEnabled(false);
        operate.setEnabled(false);
//        price_num_id.setFocusable(true);//
        et_num.setFocusable(false);
        et_tyzk.setFocusable(false);
        zcsm.setFocusable(false);
        zcmc.setFocusable(false);
        zxqk.setFocusable(false);
        start.setEnabled(false);
        stop.setEnabled(false);
        time.setEnabled(false);
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
//        cal.set(Calendar.DAY_OF_MONTH, 1); //当前月1号和最后日期
//        start.setText(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
//        stop.setText(str_time);
//        time.setText(date_dd);
    }


    //保存——新增
    private void postAdd() {

        String db_add = db_check.getText().toString();//账套
        String brand_add = brand_check.getText().toString();//品牌
        String hs_add = hs_check.getText().toString();//核算单位
        String cust_add = cust_check.getText().toString();//终端
        String price_no_add = price_num_id.getText().toString();//政策编号
        String time_add = time.getText().toString();//政策日期
        String time_start = start.getText().toString();//开始日期
        String time_stop = stop.getText().toString();//结束日期
        if(erPprice_id!=null){
            erPprice_id=erPprice_id;
        }else{
            erPprice_id="null";
        }
        if (db_add.equals("") || db_add == null) {
            Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
        } else if (price_no_add.equals("")) {
            Toast.makeText(context, "请新增单号", Toast.LENGTH_LONG).show();
        } else if (time_add.equals("")) {
            Toast.makeText(context, "请选政策日期", Toast.LENGTH_LONG).show();
        } else if (time_start.equals("")) {
            Toast.makeText(context, "请选开始日期", Toast.LENGTH_LONG).show();
        } else if (time_stop.equals("")) {
            Toast.makeText(context, "请选结束日期", Toast.LENGTH_LONG).show();
        } else {
            OkHttpClient client = new OkHttpClient();
            if (done == 1 || done == 4) {
                url_add_price = URLS.price_addPrice;

                if (brand_add.equals("") || brand_add == null || brand_add.equals(null)) {
                    brand_add = "all";
                    id_querySup = "all";
                }
                if (hs_add.equals("") || hs_add == null || hs_add.equals(null)) {
                    hs_add = "all";
                    id_hs = "all";
//                    Toast.makeText(context, "请选择核算人", Toast.LENGTH_LONG).show();
                }
                post_brand = id_querySup;
                post_hs = id_hs;
                post_cust = id_queryCust;
                post_db_name = str_name;
                localFormBody1 = new FormBody.Builder()

                        .add("isApp", "T")
                        //主表信息
                        .add("ERPprice_Id", erPprice_id)
                        .add("db_Id", db_add)//账套
                        .add("db_Name", post_db_name)//账套名称
                        .add("sup_No", post_brand)//品牌
                        .add("sup_Name", brand_add)//品牌名称
                        .add("compDep", post_hs)//核算
                        .add("compDepName", hs_add)//核算名称
                        .add("SalesTerminal_No", post_cust)//终端
                        .add("SalesTerminal_Name", cust_add)//终端名称
                        .add("obj_Type", ckdx_zb)//参考对象
                        .add("operator", ysf_zb)//运算符
                        .add("operator_Val", yssz_zb)//运算数值
                        .add("discount", tyzk_zb)//统一折扣
//                        .add("discount", Integer.parseInt(tyzk_zb))//统一折扣(整型)

                        .add("priceId", price_no_add)//政策编号
                        .add("priceName", namePrice_add)//政策名称
                        .add("price_DD", time_add)//政策日期
                        .add("useDate", time_start)//开始日期
                        .add("stopDate", time_stop)//结束日期
                        .add("rem", sm_add)//主表说明
//                        .add("accepted", accept_yn)//执行情况
                        //明细表
                        .add("ITM", sub_id)
                        .add("prdNo", sub_ph)
                        .add("cst_Up", sub_dwcb)
                        .add("UPR", sub_tysj)
                        .add("MIN_UP", sub_zdsj)
                        .add("prdUp", sub_zcdj)
                        .add("dis_CNT", sub_zczk)
//                        .add("dis_CNT", Integer.parseInt(sub_zczk))//折扣整型
                        .add("REM", sub_sm)

                        .build();
                Log.e("LiNing", "提交的保存数据" +erPprice_id+ db_add + "/" + post_db_name + "/" + post_brand + "/" + brand_add + "/" + post_hs + "/" + hs_add + "/" + post_cust + "/" + cust_add
                        + "/" + ckdx_zb + "/" + ysf_zb + "/" + yssz_zb + "/" + tyzk_zb + "/" + price_no_add + "/" + namePrice_add + "/" + time_add + "/" + time_start + "/" + time_stop
                        + "/" + sm_add + "/" + accept_yn + "/" + sub_id + "/" + sub_ph + "/" + sub_dwcb + "/" + sub_tysj + "/" + sub_zdsj + "/" + sub_zcdj + "/" + sub_zczk + "/" + sub_sm);
            } else if (done == 3) {
                url_add_price = URLS.price_setPrice;
                if (ib_xl == 1) {//点击下拉
                    post_db_name = str_name;

                } else {
                    post_db_name = mx_db_name;
                }
                if (ib_xl == 2) {//点击下拉
                    post_brand = id_querySup;
                } else {
                    post_brand = mx_sup_id;
                }
                if (ib_xl == 3) {//点击下拉
                    post_hs = id_hs;
                } else {
                    post_hs = mx_compDepName_id;
                }
                if (ib_xl == 4) {
                    post_cust = id_queryCust;
                } else {
                    post_cust = mx_salesTerminal_id;
                }

                localFormBody1 = new FormBody.Builder()
                        .add("isApp", "T")
                        //主表信息
                        .add("ERPprice_Id", erPprice_id)
                        .add("db_Id", db_add)//账套
                        .add("db_Name", post_db_name)//账套名称
                        .add("sup_No", post_brand)//品牌
                        .add("sup_Name", brand_add)//品牌名称
                        .add("compDep", post_hs)//核算
                        .add("compDepName", hs_add)//核算名称
                        .add("SalesTerminal_No", post_cust)//终端
                        .add("SalesTerminal_Name", cust_add)//终端名称
                        .add("obj_Type", ckdx_zb)//参考对象
                        .add("operator", ysf_zb)//运算符
                        .add("operator_Val", yssz_zb)//运算数值
                        .add("discount", tyzk_zb)//统一折扣
//                        .add("discount", Integer.parseInt(tyzk_zb))//统一折扣(整型)
                        .add("priceId", price_no_add)//政策编号
                        .add("priceName", namePrice_add)//政策名称
                        .add("price_DD", time_add)//政策日期
                        .add("useDate", time_start)//开始日期
                        .add("stopDate", time_stop)//结束日期
                        .add("rem", sm_add)//主表说明
//                        .add("accepted", accept_yn)//执行情况
                        //明细表
                        .add("ITM", sub_id)
                        .add("prdNo", sub_ph)
                        .add("cst_Up", sub_dwcb)
                        .add("UPR", sub_tysj)
                        .add("MIN_UP", sub_zdsj)
                        .add("prdUp", sub_zcdj)
                        .add("dis_CNT", sub_zczk)
//                        .add("dis_CNT", Integer.parseInt(sub_zczk))//折扣整型
                        .add("REM", sub_sm)

                        .build();


                Log.e("LiNing", "提交的保存数据" +erPprice_id+ db_add + "/" + post_db_name + "/" + post_brand + "/" + brand_add + "/" + post_hs + "/" + hs_add + "/" + post_cust + "/" + cust_add
                        + "/" + ckdx_zb + "/" + ysf_zb + "/" + yssz_zb + "/" + tyzk_zb + "/" + price_no_add + "/" + namePrice_add + "/" + time_add + "/" + time_start + "/" + time_stop
                        + "/" + sm_add + "/" + accept_yn + "/" + sub_id + "/" + sub_ph + "/" + sub_dwcb + "/" + sub_tysj + "/" + sub_zdsj + "/" + sub_zcdj + "/" + sub_zczk + "/" + sub_sm);
            }

            Request localRequest = new Request.Builder()
                    .addHeader("cookie", session).url(url_add_price)
                    .post(localFormBody1)
                    .build();
            client.newCall(localRequest).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    String str_add = response.body().string();
                    Log.e("LiNing", "修改的结果===" + str_add);
                    final PriceNumIDZs bean_id_new = (PriceNumIDZs) new Gson()
                            .fromJson(str_add, PriceNumIDZs.class);
                    if (done == 1) {
                        if (bean_id_new != null) {
                            PriceActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    String id_ls = bean_id_new.getBil_No();
                                    Log.e("LiNing", "保存的结果===" + id_ls);
                                    price_num_id.setText(id_ls);
                                    if (bean_id_new.getRLO() == true) {
                                        Toast.makeText(context, "保存成功", Toast.LENGTH_LONG).show();
                                        fouseAbleInfo();//禁止编辑
//                                        getMxInfo();
                                        out_info = 1;
                                        do_all = 1;
                                        y_bc = 1;//强调已保存
                                    }

                                }
                            });
                        }
                    } else if (done == 3) {
                        if (bean_id_new != null) {
                            PriceActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    if (bean_id_new.getRLO() == true) {
                                        if (iscopy == 1) {
                                            Toast.makeText(context, "拷贝成功", Toast.LENGTH_LONG).show();
                                            fouseAbleInfo();//禁止编辑
                                            out_info = 2;
                                            do_all = 2;
                                            y_bc = 1;//强调已保存
                                        } else {

                                            Toast.makeText(context, "修改成功", Toast.LENGTH_LONG).show();
                                            fouseAbleInfo();//禁止编辑
                                            out_info = 2;
                                            do_all = 2;
                                            y_bc = 1;//强调已保存
                                        }
                                    }
                                }
                            });
                        }
                    } else if (done == 4) {
                        if (bean_id_new != null) {
                            PriceActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    if (bean_id_new.getRLO() == true) {
                                        Toast.makeText(context, "保存转入成功", Toast.LENGTH_LONG).show();
                                        fouseAbleInfo();//禁止编辑
                                        out_info = 4;
                                        do_all = 4;
                                        y_bc = 1;//强调已保存
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
    }

    private void getMxInfo() {
        String dh = price_num_id.getText().toString();
        String db_zt = db_check.getText().toString();
        Log.e("LiNing", "所有政策表===" + dh + "----" + db_zt);

        OkHttpClient client = new OkHttpClient();
        FormBody localFormBody = new FormBody.Builder()
                .add("db_Id", db_zt)
                .add("priceId", dh)
                .build();
        Request localRequest = new Request.Builder()
                .addHeader("cookie", session).url(url_getmx_price)
                .post(localFormBody)
                .build();
        client.newCall(localRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String mx_str = response.body().string();
                Log.e("LiNing", "所有政策表===" + mx_str);
                // 解析包含date的数据必须添加此代码(InputStream型)
                Gson gson = new GsonBuilder().setDateFormat(
                        "yyyy-MM-dd HH:mm:ss").create();
                final PriceMx cInfoDB = gson.fromJson(mx_str,
                        PriceMx.class);
                if (cInfoDB != null) {
                    PriceActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            accepted_one = cInfoDB.getPrice().getAccepted();
                            zxqk.setText(accepted_one);
                            Log.e("LiNing", "返回结果===" + accepted_one);

                        }
                    });

                }

            }
        });
    }


    //获取列表数据提交
    private void getList_no() {
        if(info_add.size()>0) {
            info_add.clear();
        }
        if(info_query.size()>0) {
            info_query.clear();
        }
        if (mListView1.getCount() > 0) {

            ArrayList<String> ITM = new ArrayList<String>();
            ArrayList<String> prdNo = new ArrayList<String>();
            ArrayList<String> cst_Up = new ArrayList<String>();
            ArrayList<String> UPR = new ArrayList<String>();
            ArrayList<String> MIN_UP = new ArrayList<String>();
            ArrayList<String> prdUp = new ArrayList<String>();
            ArrayList<String> dis_CNT = new ArrayList<String>();
            ArrayList<String> REM = new ArrayList<String>();
            ArrayList<String> zxqk = new ArrayList<String>();
            for (int i = 0; i < mListView1.getCount(); i++) {
                if (done == 1) {
                    if(one_add==2){
                        PriceMx.PrdtUp prdt2 = (PriceMx.PrdtUp) mListView1.getAdapter().getItem(i);
                        Log.e("LiNing", "--多条--xh--prdt2" + prdt2);
//                        if(info_query.size()>0){
//                            info_query.clear();
//                            info_query.add(prdt2);
//                        }else{
//                            info_query.add(prdt2);
//                        }
                        info_query.add(prdt2);
                        xh_tj = "" + i;
                        ph_tj = prdt2.getPrdNo();
                        dwcb_tj = "" + prdt2.getCst_Up();
                        tysj_tj = "" + prdt2.getUPR();
                        zdsj_tj = "" + prdt2.getMIN_UP();
                        zcdj_tj = "" + prdt2.getPrdUp();
//                        zczk_tj = prdt2.getZC_ZK();
                        zczk_tj = prdt2.getDis_CNT();
                        zxqk_tj = prdt2.getYN();
                        sm_tj = prdt2.getREM();
                        Log.e("LiNing", "----xh2--数据" + xh_tj + "---" + ph_tj + "---" + dwcb_tj + "---" + tysj_tj
                                + "---" + zdsj_tj + "---" + zcdj_tj + "---" + zczk_tj + "---" + zxqk_tj + "---" + sm_tj);
                        if (zczk_tj == null) {

                            zczk_tj = "100";
                        }
                        if (zcdj_tj == null) {

                            zcdj_tj = "0.00";
                        }
                        if (zxqk_tj == null || zxqk_tj.equals("null")) {
                            zxqk_tj = "N";
                        }
                        if (sm_tj == null || sm_tj.equals("null")) {
                            sm_tj = null;
                        }
                    }else{

                        PriceLoadInfo.Prdt prdt2 = (PriceLoadInfo.Prdt) mListView1.getAdapter().getItem(i);
                        Log.e("LiNing", "-PriceLoadInfo---xh--prdt2" + prdt2);
//                        if(info_add.size()>0){
//                            info_add.clear();
//                            info_add.add(prdt2);
//                        }else{
//                            info_add.add(prdt2);
//                        }
//                        info_add.add(prdt2);
                        xh_tj = "" + i;
                        ph_tj = prdt2.getPRD_NO();
                        dwcb_tj = "" + prdt2.getUP_SAL();
                        tysj_tj = "" + prdt2.getUPR();
                        zdsj_tj = "" + prdt2.getUP_MIN();
                        zcdj_tj = "" + prdt2.getZC_DJ();
                        zczk_tj = prdt2.getZC_ZK();
                        zxqk_tj = prdt2.getZC_ZXQK();
                        sm_tj = prdt2.getZC_BZXX();
                        Log.e("LiNing", "----xh--数据" + xh_tj + "---" + ph_tj + "---" + dwcb_tj + "---" + tysj_tj
                                + "---" + zdsj_tj + "---" + zcdj_tj + "---" + zczk_tj + "---" + zxqk_tj + "---" + sm_tj);
                        if (zczk_tj == null) {

                            zczk_tj = "100";
                        }
                        if (zcdj_tj == null || zcdj_tj.equals("null")) {

                            zcdj_tj = "0.00";
                        }
                        if (zxqk_tj == null || zxqk_tj.equals("null")) {
                            zxqk_tj = "N";
                        }
                        if (sm_tj == null || sm_tj.equals("null")) {
                            sm_tj = null;
                        }
                        info_add.add(prdt2);
                    }

                } else if (done == 3) {
                    if (isChecked == true&&bfor==1) {
                        PriceLoadInfo.Prdt prdt2 = (PriceLoadInfo.Prdt) mListView1.getAdapter().getItem(i);
                        Log.e("LiNing", "----xh--prdt2" + prdt2);
//                        if(info_add.size()>0){
//                            info_add.clear();
//                            info_add.add(prdt2);
//                        }else{
//                            info_add.add(prdt2);
//                        }
                        info_add.add(prdt2);
                        xh_tj = "" + i;
                        ph_tj = prdt2.getPRD_NO();
                        dwcb_tj = "" + prdt2.getUP_SAL();
                        tysj_tj = "" + prdt2.getUPR();
                        zdsj_tj = "" + prdt2.getUP_MIN();
                        zcdj_tj = "" + prdt2.getZC_DJ();
                        zczk_tj = prdt2.getZC_ZK();
                        zxqk_tj = prdt2.getZC_ZXQK();
                        sm_tj = prdt2.getZC_BZXX();
                        Log.e("LiNing", "----xh--数据" + xh_tj1 + "---" + ph_tj + "---" + dwcb_tj + "---" + tysj_tj
                                + "---" + zdsj_tj + "---" + zcdj_tj + "---" + zczk_tj + "---" + zxqk_tj + "---" + sm_tj);
                        if (zczk_tj == null) {

                            zczk_tj = "100";
                        }
                        if (zcdj_tj == null) {

                            zcdj_tj = "0.00";
                        }
                        if (zxqk_tj == null || zxqk_tj.equals("null")) {
                            zxqk_tj = "N";
                        }
                        if (sm_tj == null || sm_tj.equals("null")) {
                            sm_tj = null;
                        }
                    } else {

                        PriceMx.PrdtUp prdt2 = (PriceMx.PrdtUp) mListView1.getAdapter().getItem(i);
                        Log.e("LiNing", "--PriceMx--xh--prdt2" + prdt2);
//                        if(info_query.size()>0){
//                            info_query.clear();
//                            info_query.add(prdt2);
//                        }else{
//                            info_query.add(prdt2);
//                        }
                        info_query.add(prdt2);
                        xh_tj = "" + i;
                        ph_tj = prdt2.getPrdNo();
                        dwcb_tj = "" + prdt2.getCst_Up();
                        tysj_tj = "" + prdt2.getUPR();
                        zdsj_tj = "" + prdt2.getMIN_UP();
                        zcdj_tj = "" + prdt2.getPrdUp();
                        zczk_tj = prdt2.getZC_ZK();
//                        zczk_tj = prdt2.getDis_CNT();
                        zxqk_tj = prdt2.getYN();
                        sm_tj = prdt2.getREM();
                        Log.e("LiNing", "----xh2--数据" + xh_tj + "---" + ph_tj + "---" + dwcb_tj + "---" + tysj_tj
                                + "---" + zdsj_tj + "---" + zcdj_tj + "---" + zczk_tj + "---" + zxqk_tj + "---" + sm_tj);
                        if (zczk_tj == null) {

                            zczk_tj = "100";
                        }
                        if (zcdj_tj == null) {

                            zcdj_tj = "0.00";
                        }
                        if (zxqk_tj == null || zxqk_tj.equals("null")) {
                            zxqk_tj = "N";
                        }
                        if (sm_tj == null || sm_tj.equals("null")) {
                            sm_tj = null;
                        }
                    }
                } else if (done == 4) {
                    if (isChecked == true&&bfor==1) {
                        PriceLoadInfo.Prdt prdt2 = (PriceLoadInfo.Prdt) mListView1.getAdapter().getItem(i);
                        Log.e("LiNing", "----xh--prdt2" + prdt2);
//                        if(info_add.size()>0){
//                            info_add.clear();
//                            info_add.add(prdt2);
//                        }else{
//                            info_add.add(prdt2);
//                        }
                        info_add.add(prdt2);
                        xh_tj = "" + i;

                        ph_tj = prdt2.getPRD_NO();
                        dwcb_tj = "" + prdt2.getUP_SAL();
                        tysj_tj = "" + prdt2.getUPR();
                        zdsj_tj = "" + prdt2.getUP_MIN();
                        zcdj_tj = "" + prdt2.getZC_DJ();
                        zczk_tj = prdt2.getZC_ZK();
                        zxqk_tj = prdt2.getZC_ZXQK();
                        sm_tj = prdt2.getZC_BZXX();
                        Log.e("LiNing", "----xh--数据" + xh_tj1 + "---" + ph_tj + "---" + dwcb_tj + "---" + tysj_tj
                                + "---" + zdsj_tj + "---" + zcdj_tj + "---" + zczk_tj + "---" + zxqk_tj + "---" + sm_tj);
                        if (zczk_tj == null) {

                            zczk_tj = "100";
                        }
                        if (zcdj_tj == null) {

                            zcdj_tj = "0.00";
                        }
                        if (zxqk_tj == null || zxqk_tj.equals("null")) {
                            zxqk_tj = "N";
                        }
                        if (sm_tj == null || sm_tj.equals("null")) {
                            sm_tj = null;
                        }
                    } else {
                        HashMap<String, Object> item_get_in = (HashMap<String, Object>) mListView1.getAdapter().getItem(i);
                        info_zr.add(item_get_in);
                        xh_tj = item_get_in.get("转入序号").toString();
                        ph_tj = item_get_in.get("转入货品编号").toString();
                        dwcb_tj = item_get_in.get("转入单位成本").toString();
                        tysj_tj = item_get_in.get("转入统一定价").toString();
                        zdsj_tj = item_get_in.get("转入最低售价").toString();
                        zcdj_tj = item_get_in.get("转入政策定价").toString();
                        zczk_tj = item_get_in.get("转入销售折扣").toString();
                        zxqk_tj = item_get_in.get("转入执行情况").toString();
                        sm_tj = item_get_in.get("转入备注信息").toString();
                        Log.e("LiNing", "----xh2--数据" + xh_tj + "---" + ph_tj + "---" + dwcb_tj + "---" + tysj_tj
                                + "---" + zdsj_tj + "---" + zcdj_tj + "---" + zczk_tj + "---" + zxqk_tj + "---" + sm_tj);
                        if (zczk_tj.equals("")) {

                            zczk_tj = "100";
                        }
                        if (zxqk_tj.equals("")) {

                            zxqk_tj = "N";
                        }
                        if (sm_tj.equals("")) {

                            sm_tj = "null";
                        }
                        if (zcdj_tj.equals("")) {

                            zcdj_tj = "0.00";
                        }
                    }
                }
                prdUp.add(zcdj_tj);
                ITM.add(xh_tj);
                prdNo.add(ph_tj);
                cst_Up.add(dwcb_tj);
                UPR.add(tysj_tj);
                MIN_UP.add(zdsj_tj);
                dis_CNT.add(zczk_tj);
                zxqk.add(zxqk_tj);
                REM.add(sm_tj);
//                if (!zcdj_tj.equals("null") && !zcdj_tj.equals("") && zcdj_tj != null) {
//                    prdUp.add(zcdj_tj);
//                } else {
//                    prdUp.add("123");
////                    Toast.makeText(context, "请填写政策定价", Toast.LENGTH_LONG).show();
//                }

            }
            Log.e("LiNing", "----xh--新数据" + ITM);
//            for (int i = 0; i < dList.size(); i++) {
//                //摘取获取的数据。。。
//                Log.e("LiNing", "------新数据" + dList.get(i).get("序号").toString());
//
//                String xh_tj = dList.get(i).get("序号").toString();
//                String ph_tj = dList.get(i).get("品号").toString();
//                String dwcb_tj = dList.get(i).get("单位成本").toString();
//                String tysj_tj = dList.get(i).get("统一售价").toString();
//                String zdsj_tj = dList.get(i).get("最低售价").toString();
//                String zcdj_tj = dList.get(i).get("政策单价").toString();
//                String zczk_tj = dList.get(i).get("政策折扣").toString();
//                String sm_tj = dList.get(i).get("说明").toString();
//                ITM.add(xh_tj);
//                prdNo.add(ph_tj);
//                cst_Up.add(dwcb_tj);
//                UPR.add(tysj_tj);
//                MIN_UP.add(zdsj_tj);
//                prdUp.add(zcdj_tj);
////                dis_CNT.add("" + 1);
////                REM.add(null);
//                            if(zczk_tj.equals("")){
//                                dis_CNT.add(""+1);
//                            }else{
//                                dis_CNT.add(zczk_tj);
//                            }
//                            if(sm_tj.equals("")){
//                                REM.add(null);
//                            }else{
//                                REM.add(sm_tj);
//                            }
//            }

            //开始拼接
            String zts_str = "";
            for (String zt : ITM) {
                zts_str += zt + ",";
            }
            sub_id = zts_str.substring(0, zts_str.length() - 1);
            Log.e("LiNing", "------新数据" + sub_id);
            String ph_str = "";
            for (String zt : prdNo) {
                ph_str += zt + ",";
            }
            sub_ph = ph_str.substring(0, ph_str.length() - 1);
            Log.e("LiNing", "------新数据" + sub_ph);
            String dwcb_str = "";
            for (String zt : cst_Up) {
                dwcb_str += zt + ",";
            }
            sub_dwcb = dwcb_str.substring(0, dwcb_str.length() - 1);
            Log.e("LiNing", "------新数据" + sub_dwcb);
            String tysj_str = "";
            for (String zt : UPR) {
                tysj_str += zt + ",";
            }
            sub_tysj = tysj_str.substring(0, tysj_str.length() - 1);
            Log.e("LiNing", "------新数据" + sub_tysj);
            String zdsj_str = "";
            for (String zt : MIN_UP) {
                zdsj_str += zt + ",";
            }
            sub_zdsj = zdsj_str.substring(0, zdsj_str.length() - 1);
            Log.e("LiNing", "------新数据" + sub_zdsj);
            String zcdj_str = "";
            for (String zt : prdUp) {
                zcdj_str += zt + ",";
            }
            sub_zcdj = zcdj_str.substring(0, zcdj_str.length() - 1);
            Log.e("LiNing", "------新数据" + sub_zcdj);
            String zczk_str = "";
            for (String zt : dis_CNT) {
                zczk_str += zt + ",";
            }
            sub_zczk = zczk_str.substring(0, zczk_str.length() - 1);
            Log.e("LiNing", "------新数据" + sub_zczk);
            String sm_str = "";
            for (String zt : REM) {
                sm_str += zt + ",";
            }
            sub_sm = sm_str.substring(0, sm_str.length() - 1);
            Log.e("LiNing", "------新数据" + sub_sm);
        }
    }


    private void setPrice() {
        String DB_LS = db_check.getText().toString();
        String price_no = price_num_id.getText().toString();
        if (DB_LS.equals("")) {
            Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
        } else if (price_no.equals("")) {
            Toast.makeText(context, "请选择单号", Toast.LENGTH_LONG).show();
        } else {
            OkHttpClient client = new OkHttpClient();
            FormBody localFormBody = new FormBody.Builder()
                    .add("db_Id", DB_LS)
                    .add("priceId", price_no)
                    .build();
            Request localRequest = new Request.Builder()
                    .addHeader("cookie", session).url(url_reset_price)
                    .post(localFormBody)
                    .build();
            client.newCall(localRequest).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String str_del = response.body().string();
                    Log.e("LiNing", "修改的结果===" + str_del);
                }

                @Override
                public void onFailure(Call call, IOException e) {

                }
            });

        }
    }

    private void delPrice() {
        if (done == 4) {
            clearInfo();
            mListView1.setAdapter(null);
            Toast.makeText(context, "Excel表信息删除成功", Toast.LENGTH_LONG).show();
        } else {

            String DB_LS = db_check.getText().toString();
            String price_no = price_num_id.getText().toString();
            String time_del = time.getText().toString();
            if (DB_LS.equals("")) {
                Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
            } else if (price_no.equals("")) {
                Toast.makeText(context, "请选择单号", Toast.LENGTH_LONG).show();
            } else {
                OkHttpClient client = new OkHttpClient();
                FormBody localFormBody = new FormBody.Builder()
                        .add("db_Id", DB_LS)
                        .add("priceId", price_no)
                        .add("price_DD", time_del)
                        .build();
                Request localRequest = new Request.Builder()
                        .addHeader("cookie", session).url(url_del_price)
                        .post(localFormBody)
                        .build();
                client.newCall(localRequest).enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String str_del = response.body().string();
                        Log.e("LiNing", "删除的结果===" + str_del);
                        final PriceNumIDZs bean_id_new = (PriceNumIDZs) new Gson()
                                .fromJson(str_del, PriceNumIDZs.class);
                        if (bean_id_new != null) {
                            PriceActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    String id_ls = bean_id_new.getBil_No();
                                    Log.e("LiNing", "删除的单号===" + id_ls);
                                    price_num_id.setText(id_ls);
                                    if (bean_id_new.getRLO() == true) {
                                        clearInfo();
                                        eAbleInfo();
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


    //获取所有定价政策
    private void getPrices() {
        String DB_LS = db_check.getText().toString();
        String start_time_query = start_quick.getText().toString();
        String stop_time_query = stop_quick.getText().toString();
        String price_no = price_num_id.getText().toString();
        String price_search = et_seach.getText().toString();
        if (DB_LS.equals("")) {
            Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
        }
//        else if (price_no.equals("")) {
//            Toast.makeText(context, "请选择单号", Toast.LENGTH_LONG).show();
//        }
        else if (start_time_query.equals("")) {
            Toast.makeText(context, "请获取开始时间", Toast.LENGTH_LONG).show();
        } else if (stop_time_query.equals("")) {
            Toast.makeText(context, "请获取结束时间", Toast.LENGTH_LONG).show();
        } else {
            Log.e("LiNing", "所有政策表===" + DB_LS + "---" + price_no + "---" + start_time_query + "----" + stop_time_query);
            OkHttpClient client = new OkHttpClient();
            if (price_search.equals("")) {

                localFormBody = new FormBody.Builder()
                        .add("db_Id", DB_LS)
                        .add("priceId", price_search)
                        .add("beginDate", start_time_query)
                        .add("endDate", stop_time_query)
                        .build();
            } else {
                localFormBody = new FormBody.Builder()
                        .add("db_Id", DB_LS)
                        .add("beginDate", start_time_query)
                        .add("endDate", stop_time_query)
                        .build();
            }
            Request localRequest = new Request.Builder()
                    .addHeader("cookie", session).url(url_get_price)
                    .post(localFormBody)
                    .build();
            client.newCall(localRequest).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String str = response.body().string();
                    Log.e("LiNing", "所有政策表===" + str);
                    // 解析包含date的数据必须添加此代码(InputStream型)

                    Gson gson = new GsonBuilder().setDateFormat(
                            "yyyy-MM-dd HH:mm:ss").create();
                    final GetPriceInfo cInfoDB = gson.fromJson(str,
                            GetPriceInfo.class);
                    if (cInfoDB != null) {
                        PriceActivity.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                prices_main = cInfoDB.getPrices();
                                Log.e("LiNing", "cInfoDB表===" + prices_main.size());
                                getAdapter = new PriceGetAdapter(R.layout.quick_head_main_item, prices_main, context);
                                list_main.setAdapter(getAdapter);
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

    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu布局
        if (checked == 1) {

            popupMenu.getMenuInflater().inflate(R.menu.main, popupMenu.getMenu());
        }
        if (checked == 2) {

            popupMenu.getMenuInflater().inflate(R.menu.number, popupMenu.getMenu());
        }
        if (checked == 3) {

            popupMenu.getMenuInflater().inflate(R.menu.one, popupMenu.getMenu());
        }
        if (checked == 4) {

            popupMenu.getMenuInflater().inflate(R.menu.two, popupMenu.getMenu());
        }
        if (checked == 5) {

            popupMenu.getMenuInflater().inflate(R.menu.five, popupMenu.getMenu());
        }
//        if (checked == 6) {
//
//            popupMenu.getMenuInflater().inflate(R.menu.seven, popupMenu.getMenu());
//        }
//        if (checked == 7) {
//
//            popupMenu.getMenuInflater().inflate(R.menu.six, popupMenu.getMenu());
//        }
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
            case 1:
                if (resultCode == 1) {
                    String str_id = data.getStringExtra("condition_db");
                    str_name = data.getStringExtra("condition_name");
                    db_check.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + str_name);
                }

                break;
            case 12:
                if (resultCode == 1) {
//                    String str1 = data.getStringExtra("data_return");
//                    id_querySup = data.getStringExtra("data_return_ids");
//                    brand_check.setText(str1);
//                    Log.e("LiNing", "提交的id====" + str1);
//                    if (ib_xl == 2) {
//                    } else {
//                        if (alertDialog.isShowing()) {
//                            tv_qurey_sup.setText(str1);
//                        }
//                    }
                    // sp.edit().putString("USERIDquerry", str1).commit();
                    //记录选择的数据
                    String str1 = data.getStringExtra("data_return");
                    if (str1 != null) {
                        String[] db_spls = str1.split(",");

                        Log.e("LiNing", "=====" + str1.split(","));
                        for (int i = 0; i < db_spls.length; i++) {
                            String A = db_spls[i];
                            Log.e("LiNing", "===A==" + A);
                            if (!idList_checkBread.contains(A)) {
                                idList_checkBread.add(A);
                            }
                        }
                    }
                    Log.e("LiNing", "新增的数据" + idList_checkBread);
                    String prdname_str = "";
                    for (String zt : idList_checkBread) {
                        prdname_str += zt + ",";
                    }
                    String sub_level_sup = prdname_str.substring(0, prdname_str.length() - 1);
                    Log.e("LiNing", "------新增的数据" + sub_level_sup);
                    if (idList_checkBread != null && idList_checkBread.size() > 0) {
                        brand_check.setText(sub_level_sup);
                        brand_check.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextView view = new TextView(context);
                                view.setMovementMethod(ScrollingMovementMethod.getInstance());
                                view.setText(brand_check.getText().toString());
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
                            if (!idList_checkBreadID.contains(A)) {
                                idList_checkBreadID.add(A);
                            }
                        }
                    }
                    Log.e("LiNing", "新增的数据" + idList_checkBreadID);
                    String prdid_str = "";
                    for (String zt : idList_checkBreadID) {
                        prdid_str += zt + ",";
                    }
                    id_querySup = prdid_str.substring(0, prdid_str.length() - 1);
                    Log.e("LiNing", "------新增的数据id" + id_querySup);
                }
                break;
            case 10:
                if (resultCode == 1) {
//                    String str1 = data.getStringExtra("data_return");
//                    id_hs = data.getStringExtra("data_return_ids");
//                    hs_check.setText(str1);
//                    if (ib_xl == 3) {
//
//                    } else {
//
//                        if (alertDialog.isShowing()) {
//                            tv_query_compdep.setText(str1);
//                        }
//                    }
//                    Log.e("LiNing", "提交的id====" + str1 + id_hs);
                    // sp.edit().putString("USERIDquerry", str1).commit();id_queryCust,id_hs
                    //记录选择的数据
                    String str1 = data.getStringExtra("data_return");
                    if (str1 != null) {
                        String[] db_spls = str1.split(",");

                        Log.e("LiNing", "=====" + str1.split(","));
                        for (int i = 0; i < db_spls.length; i++) {
                            String A = db_spls[i];
                            Log.e("LiNing", "===A==" + A);
                            if (!idList_checkHs.contains(A)) {
                                idList_checkHs.add(A);
                            }
                        }
                    }
                    Log.e("LiNing", "新增的数据" + idList_checkHs);
                    String prdname_str = "";
                    for (String zt : idList_checkHs) {
                        prdname_str += zt + ",";
                    }
                    String sub_level_sup = prdname_str.substring(0, prdname_str.length() - 1);
                    Log.e("LiNing", "------新增的数据" + sub_level_sup);
                    if (idList_checkHs != null && idList_checkHs.size() > 0) {
                        hs_check.setText(sub_level_sup);
                        hs_check.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextView view = new TextView(context);
                                view.setMovementMethod(ScrollingMovementMethod.getInstance());
                                view.setText(hs_check.getText().toString());
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
                            if (!idList_checkHsID.contains(A)) {
                                idList_checkHsID.add(A);
                            }
                        }
                    }
                    Log.e("LiNing", "新增的数据" + idList_checkHsID);
                    String prdid_str = "";
                    for (String zt : idList_checkHsID) {
                        prdid_str += zt + ",";
                    }
                    id_hs = prdid_str.substring(0, prdid_str.length() - 1);
                    Log.e("LiNing", "------新增的数据id" + id_hs);
                }
                break;
            case 13:
                if (resultCode == 1) {
                    String str1 = data.getStringExtra("data_return");
                    id_queryCust = data.getStringExtra("data_return_ids");
                    cust_check.setText(str1);
//                    Log.e("LiNing", "提交的id====" + str1);
                    // sp.edit().putString("USERIDquerry", str1).commit();
//                    //记录选择的数据
//                    String str1 = data.getStringExtra("data_return");
//                    if (str1 != null) {
//                        String[] db_spls = str1.split(",");
//
//                        Log.e("LiNing", "=====" + str1.split(","));
//                        for (int i = 0; i < db_spls.length; i++) {
//                            String A = db_spls[i];
//                            Log.e("LiNing", "===A==" + A);
//                            if (!idList_checkCust.contains(A)) {
//                                idList_checkCust.add(A);
//                            }
//                        }
//                    }
//                    Log.e("LiNing", "新增的数据" + idList_checkCust);
//                    String prdname_str = "";
//                    for (String zt : idList_checkCust) {
//                        prdname_str += zt + ",";
//                    }
//                    String sub_level_sup = prdname_str.substring(0, prdname_str.length() - 1);
//                    Log.e("LiNing", "------新增的数据" + sub_level_sup);
//                    if (idList_checkCust != null && idList_checkCust.size() > 0) {
//                        cust_check.setText(sub_level_sup);
//                        cust_check.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                TextView view = new TextView(context);
//                                view.setMovementMethod(ScrollingMovementMethod.getInstance());
//                                view.setText(cust_check.getText().toString());
//                                alertDialog = new AlertDialog.Builder(context).create();
//                                alertDialog.setCancelable(true);
//                                alertDialog.setView(view);
//                                alertDialog.show();
//                            }
//                        });
//                    }
//                    //记录提交的数据
//                    String id_querySup_jz = data.getStringExtra("data_return_ids");
////                    idList_prdnoID.add(id_prdNo_xs);
//                    if (id_querySup_jz != null) {
//                        String[] db_spls = id_querySup_jz.split(",");
//                        Log.e("LiNing", "=====" + id_querySup_jz.split(","));
//                        for (int i = 0; i < db_spls.length; i++) {
//                            String A = db_spls[i];
//                            Log.e("LiNing", "===A==" + A);
//                            if (!idList_checkCustID.contains(A)) {
//                                idList_checkCustID.add(A);
//                            }
//                        }
//                    }
//                    Log.e("LiNing", "新增的数据" + idList_checkCustID);
//                    String prdid_str = "";
//                    for (String zt : idList_checkCustID) {
//                        prdid_str += zt + ",";
//                    }
//                    id_queryCust = prdid_str.substring(0, prdid_str.length() - 1);
//                    Log.e("LiNing", "------新增的数据id" + id_queryCust);
                }
                break;
            case 15:
                if (resultCode == 1) {
//                    String str1 = data.getStringExtra("data_return");
//                    id_querySup = data.getStringExtra("data_return_ids");
//                    tv_qurey_sup.setText(str1);
//                    String str1 = data.getStringExtra("data_return");
//                    id_querySup = data.getStringExtra("data_return_ids");
//                    tv_qurey_sup.setText(str1);
//                    Log.e("LiNing", "提交的id====" + str1);
                    // sp.edit().putString("USERIDquerry", str1).commit();

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
//                    String str1 = data.getStringExtra("data_return");
//                    id_employee = data.getStringExtra("data_return_ids");
//                    tv_employee.setText(str1);//idList_employeeName,idList_employeeID
                    // sp.edit().putString("USERIDquerry", str1).commit();
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
//                    String str1 = data.getStringExtra("data_return");
//                    sp.edit().putString("USERIDquerry", str1).commit();
//                    id_prdNo = data.getStringExtra("data_return_ids");
//                    tv_prdNo.setText(str1);


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
//                    String str1 = data.getStringExtra("data_return");
//                    id_prdIndex = data.getStringExtra("data_return_ids");
//                    tv_prdIndx.setText(str1);idList_prdIndexName/idList_prdIndexID
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
//                    String str1 = data.getStringExtra("data_return");
//                    id_prdWh = data.getStringExtra("data_return_ids");
//                    tv_prdWh.setText(str1);//idList_prdWhName,id_prdNo_xs
                    // sp.edit().putString("USERIDquerry", str1).commit();
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
            case 2:
                if (resultCode == RESULT_OK) {
                    Uri selectedMediaUri = data.getData();
                    String path = com.example.bean.FileUtils.getPath(PriceActivity.this, selectedMediaUri);
//                    path_name.setText(path);
                    //填入listview
                    datas = new ArrayList<>();
                    Log.e("LiNing", "转入的数据是" + datas);
                    Workbook workbook = null;
                    try {
                        workbook = Workbook.getWorkbook(new File(path));
                        Sheet sheet = workbook.getSheet(0);
                        int rows = sheet.getRows();
                        int columns = sheet.getColumns();
                        //遍历excel文件的每行每列
                        for (int i = 0; i < rows; i++) {
                            //遍历行
                            List<String> li = new ArrayList<>();
                            for (int j = 0; j < columns; j++) {
                                Cell cell = sheet.getCell(j, i);
                                String result = cell.getContents();
                                if (i != 0) {
                                    li.add(result);
                                }
                            }
                            if (li.size() > 0) {
                                datas.add(new PriceLoadInfo.Prdt(li.get(0), li.get(1), li.get(2), Double.parseDouble(li.get(3)), Double.parseDouble(li.get(4)),
                                        Double.parseDouble(li.get(5)), li.get(6), li.get(7), li.get(8), li.get(9)));
                            }
                            li = null;
                        }
                        Gson gson = new Gson();
                        s_datas = gson.toJson(datas);
                        Log.e("LiNing", "转入的数据是" + s_datas);
                        if (s_datas != null && !s_datas.equals("") && !s_datas.equals("null")) {
                            Log.e("LiNing", "转入数据--s_datas--" + s_datas);

                            //将此数据添加到listview中

                            in_list = new ArrayList();
                            JSONArray jsonArray = null;
                            try {
//                    jsonArray = new JSONArray(in_info);//①
                                jsonArray = new JSONArray(s_datas);//②
                                int iSize = jsonArray.length();
                                System.out.println("Size:" + iSize);
                                for (int i = 0; i < iSize; i++) {
                                    HashMap<String, Object> item = new HashMap<String, Object>();
                                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                                    String zr_id = jsonObj.get("NAME").toString();
                                    String zr_name = jsonObj.get("PRD_NO").toString();
                                    String zr_dwcb = jsonObj.get("UP_SAL").toString();
                                    String zr_tydj = jsonObj.get("UPR").toString();
                                    String zr_zdsj = jsonObj.get("UP_MIN").toString();
                                    String zr_zcdj = jsonObj.get("ZC_DJ").toString();
                                    String zr_xszk = jsonObj.get("UP_SAL").toString();
                                    String zr_zxqk = jsonObj.get("ZC_ZXQK").toString();
                                    String zr_bzxx = jsonObj.get("REM").toString();
                                    item.put("转入序号", i);
                                    item.put("转入货品编号", zr_id);
                                    item.put("转入货品名称", zr_name);
                                    item.put("转入单位成本", zr_dwcb);
                                    item.put("转入统一定价", zr_tydj);
                                    item.put("转入最低售价", zr_zdsj);
                                    item.put("转入政策定价", zr_zcdj);
                                    item.put("转入销售折扣", zr_xszk);
                                    item.put("转入执行情况", zr_zxqk);
                                    item.put("转入备注信息", zr_bzxx);
                                    in_list.add(item);
                                    Log.e("LiNing", "转入数据list----" + in_list);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Log.e("LiNing", "转入数据list==最终----" + in_list);
                            inAdapter = new InAdapter(onclik_go, R.layout.price_fuction_item1, in_list, context);
                            mListView1.setAdapter(inAdapter);
                            inAdapter.notifyDataSetChanged();
                            if (mListView1.getCount() > 0 && mListView1 != null) {
                                do_all = 4;//转入成功
                                done = 4;
                            } else {
                                Toast.makeText(context, "Excel数据转入失败", Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                break;
            case 21:
                if (resultCode == 1) {

                    String morElIST = data.getStringExtra("MORElIST");
                    prdt_add_mx = new ArrayList<PriceMx.PrdtUp>();
                    ls_set = new ArrayList<>();
                    Log.e("LiNing", "---回调数据---" +morElIST);
                    try {
                        JSONArray jsonArray = new JSONArray(morElIST);
                        iSize = jsonArray.length();
                        System.out.println("Size:" + iSize);
                        for (int i = 0; i < iSize; i++) {
                            JSONObject jsonObj = jsonArray.getJSONObject(i);
                            String xh_hd = jsonObj.get("XH").toString();//2
                            String prd_no_hd = jsonObj.get("PRD_NO").toString();//A1728SQ
                            String name_hd = jsonObj.get("NAME").toString();//A1728SQ五件套浴缸
                            String up_sal_hd = jsonObj.get("UP_SAL").toString();//0.00
                            String upr_hd = jsonObj.get("UPR").toString();//438.0
                            String up_min_hd = jsonObj.get("UP_MIN").toString();//1.0
                            if(jsonObj.get("ZC_DJ")!=null){

                                zc_dj_hd = jsonObj.get("ZC_DJ").toString();//999
                                if(zc_dj_hd.equals("")||zc_dj_hd.equals("null")||zc_dj_hd==null){
                                    zc_dj_hd="0.00";
                                }
                            }

                            String zc_zk_hd = jsonObj.get("ZC_ZK").toString();//80

                            if(zc_zk_hd.equals("")||zc_zk_hd.equals("null")||zc_zk_hd==null){
                                zc_zk_hd="100";
                            }
                            String zc_zxqk_hd = jsonObj.get("ZC_ZXQK").toString();//N
                            if(zc_zxqk_hd.equals("")||zc_zxqk_hd.equals("null")||zc_zxqk_hd==null){
                                zc_zxqk_hd="N";
                            }
                            String zc_bzxx_hd = jsonObj.get("ZC_BZXX").toString();//备注信息
                            if(zc_bzxx_hd.equals("")||zc_bzxx_hd.equals("null")||zc_bzxx_hd==null){
                                zc_bzxx_hd="null";
                            }
                            Log.e("LiNing", "---提交回调数据---" +prd_no_hd+name_hd+up_sal_hd+upr_hd+up_min_hd+zc_dj_hd
                                    +zc_zk_hd+zc_zxqk_hd+zc_bzxx_hd);
                            mx_ls_mor = new PriceMx.PrdtUp(Integer.parseInt(xh_hd), prd_no_hd, name_hd,
                                    up_sal_hd, upr_hd, up_min_hd, zc_dj_hd, zc_zk_hd,
                                    zc_zxqk_hd, zc_bzxx_hd);
                            prdt_add_mx.add(mx_ls_mor);
                            ls_set.add(prd_no_hd);
                        }
                        Log.e("LiNing", "---保存回调数据---" +prdt_add_mx);
                        Log.e("LiNing", "---保存回调数据---" +ls_set);
                        //必须判断数据（新增、修改）
                        if(done==1&&mListView1.getCount()>0){
                            for (int j = 0; j < mListView1.getCount(); j++) {
                                prdt2 = (PriceLoadInfo.Prdt) mListView1.getAdapter().getItem(j);
                                Log.e("LiNing", "添加数据====pd=xg===" + prdt2);
                                String xh_prdt = prdt2.getXH();
                                if(xh_prdt.equals("")||xh_prdt.equals("null")||xh_prdt==null){
                                    xh_prdt="j";
                                }
                                prd_no_prdt = prdt2.getPRD_NO();
                                String name_prdt = prdt2.getNAME();
                                double up_sal_prdt = prdt2.getUP_SAL();
                                double upr_prdt = prdt2.getUPR();
                                double up_min_prdt = prdt2.getUP_MIN();
                                String zc_dj_prdt = prdt2.getZC_DJ();
                                String zc_zk_prdt = prdt2.getZC_ZK();
                                String zc_zxqk_prdt = prdt2.getZC_ZXQK();
                                String zc_bzxx_prdt = prdt2.getZC_BZXX();
                                if (!ls_set.contains(prd_no_prdt)) {
                                    mx_ls_prdt = new PriceMx.PrdtUp(Integer.parseInt(xh_prdt)+iSize, prd_no_prdt, name_prdt,
                                            "" + up_sal_prdt, "" +upr_prdt, "" + up_min_prdt, zc_dj_prdt, zc_zk_prdt,
                                            zc_zxqk_prdt, zc_bzxx_prdt);
                                    prdt_add_mx.add(mx_ls_prdt);
                                    Log.e("LiNing", "包含数据==xin==pd====" + mx_ls_prdt);
                                }
                            }
                            onclik_go=21;
                            mxPriceAdapter = new MxPriceAdapter(onclik_go, R.layout.price_fuction_item1, prdt_add_mx, (PriceActivity) context);
                            mxPriceAdapter.notifyDataSetChanged();
                            mListView1.setAdapter(mxPriceAdapter);

                        }else{

                            for (int j = 0; j < mListView1.getCount(); j++) {
                                prdt3 = (PriceMx.PrdtUp) mListView1.getAdapter().getItem(j);
                                prd_no = prdt3.getPrdNo();
                                Log.e("LiNing", "添加数据==xin==pd====" + prd_no);
                                if (!ls_set.contains(prd_no)) {
                                    prdt_add_mx.add(prdt3);
                                    Log.e("LiNing", "包含数据==xin==pd====" + prdt3);
                                }
                            }
                        }
                        onclik_go=21;
                        Log.e("LiNing", "包含数据==xin==pd====" + prdt_add_mx);
                        mxPriceAdapter = new MxPriceAdapter(onclik_go, R.layout.price_fuction_item1, prdt_add_mx, (PriceActivity) context);
                        mxPriceAdapter.notifyDataSetChanged();
                        mListView1.setAdapter(mxPriceAdapter);
                        Log.e("LiNing", "修改的数据==新====" + mListView1.getCount()+"<<<<<"+prdt_add_mx);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

    class ListViewAndHeadViewTouchLinstener implements OnTouchListener {
        ListViewAndHeadViewTouchLinstener() {
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ((HorizontalScrollView) PriceActivity.this.mHead
                    .findViewById(R.id.horizontalScrollView1))
                    .onTouchEvent(event);
            return false;
        }

    }
    class ListViewAndHeadViewTouchLinstener_more implements OnTouchListener {
        ListViewAndHeadViewTouchLinstener_more() {
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ((HorizontalScrollView) PriceActivity.this.mHead_more
                    .findViewById(R.id.horizontalScrollView1))
                    .onTouchEvent(event);
            return false;
        }

    }

    public void allback(View v) {
        finish();
    }

    //临时单号
    private void getTicket() {
        String DB_LS = db_check.getText().toString();
        if (DB_LS.equals("")) {
            Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
        } else if (date_dd.equals("")) {
            Toast.makeText(context, "请获取时间", Toast.LENGTH_LONG).show();
        } else {

            url_dh_price = URLS.price_num_ls;
            Log.e("LiNing", "临时单号===" + session + "---" + URLS.price_num_ls + "---" + DB_LS + "----" + date_dd);
//            if (save == 1) {
//                url_dh_price = URLS.price_num_ls;
//            } else {
//                url_dh_price = URLS.price_num_zs;
//            }
            OkHttpClient client = new OkHttpClient();
            FormBody localFormBody = new FormBody.Builder()
                    .add("bn_Type", "DJ")
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
                        if (save == 1) {
                            //设置数据不可编辑
                            PriceActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    //button
                                    db.setEnabled(false);
                                    brand.setEnabled(false);
                                    hs.setEnabled(false);
                                    cust.setEnabled(false);
                                    user.setEnabled(false);
                                    operate.setEnabled(false);
                                    start.setEnabled(false);
                                    stop.setEnabled(false);
                                    time.setEnabled(false);
                                    //editext
//                                    et_num.setFocusable(false);
//                                    et_tyzk.setFocusable(false);
//                                    zcmc.setFocusable(false);
//                                    zcsm.setFocusable(false);
//                                    zxqk.setFocusable(false);
//                                    brand_item.setFocusable(false);
//                                    brand_name_item.setFocusable(false);
//                                    dwcb_item.setFocusable(false);
//                                    tydj_item.setFocusable(false);
//                                    zdsj_item.setFocusable(false);
//                                    zcdj_item.setFocusable(false);
//                                    xszk_item.setFocusable(false);
//                                    zxqk_item.setFocusable(false);
//                                    bzxx_item.setFocusable(false);
//                                    priceAdapter.check();

                                }
                            });

                        }
                        String str = response.body().string();
                        Log.e("LiNing", "临时单号===" + str);
                        final PriceNumID bean_id = (PriceNumID) new Gson()
                                .fromJson(str, PriceNumID.class);
                        if (bean_id != null) {
                            PriceActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    String id_ls = bean_id.getBil_no();
                                    price_num_id.setText(id_ls);
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
    public boolean onTouchEvent(MotionEvent event) {
//        Toast.makeText(context,"有反应",Toast.LENGTH_LONG).show();
//        getTicket();//获取临时单号
        return true;
    }

    //货品加载（提交数据）
    public void load_ok(View v) {
        if(more==2){
            prdt_add_mx = new ArrayList<PriceMx.PrdtUp>();
            String morElIST = sp.getString("MORElIST", "");
            Log.e("LiNing", "---回调数据---" +morElIST);
            try {
                JSONArray jsonArray = new JSONArray(morElIST);
                int iSize = jsonArray.length();
                System.out.println("Size:" + iSize);
                for (int i = 0; i < iSize; i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    String xh_hd = jsonObj.get("XH").toString();//2
                    String prd_no_hd = jsonObj.get("PRD_NO").toString();//A1728SQ
                    String name_hd = jsonObj.get("NAME").toString();//A1728SQ五件套浴缸
                    String up_sal_hd = jsonObj.get("UP_SAL").toString();//0.00
                    String upr_hd = jsonObj.get("UPR").toString();//438.0
                    String up_min_hd = jsonObj.get("UP_MIN").toString();//1.0
                    String zc_dj_hd = jsonObj.get("ZC_DJ").toString();//999
                    String zc_zk_hd = jsonObj.get("ZC_ZK").toString();//80
                    String zc_zxqk_hd = jsonObj.get("ZC_ZXQK").toString();//N
                    String zc_bzxx_hd = jsonObj.get("ZC_BZXX").toString();//备注信息
                    mx_ls_mor = new PriceMx.PrdtUp(Integer.parseInt(xh_hd), prd_no_hd, name_hd,
                            up_sal_hd, upr_hd, up_min_hd, zc_dj_hd, zc_zk_hd,
                            zc_zxqk_hd, zc_bzxx_hd);
                    prdt_add_mx.add(mx_ls_mor);
                }
                Log.e("LiNing", "---保存回调数据---" +prdt_add_mx);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        alertDialog.dismiss();
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
        if (showStop_comit.equals("否") ) {
            showStop_comit = "F";
        }
        if (showStop_comit.equals("是") ) {
            showStop_comit = "T";
        }
        String prdMrk_comit = prdMrk.getText().toString();
        String prdName_comit = prdName.getText().toString();
        String prdName_ENG_comit = prdName_ENG.getText().toString();
        Log.e("LiNing", "-----str-----" + db_check.getText().toString() + showStop_comit);
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
        if (showStop_comit.equals("否") ||showStop_comit.equals("") || showStop_comit == null) {
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
                .add("db_Id", db_check.getText().toString())
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
//        &query_Sup=CJ001&query_CompDep=all&employee=all&prdNO=all&prdIndx=all&prdWh=all&prdLevel=all&prdKND=all&showStop=F&prdMrk=all&prdName=CJ001&prdName_ENG=CJ001
        Request localRequest = new Request.Builder()
                .addHeader("cookie", session).url(url_load_price)
                .post(localFormBody)
                .build();
        Log.e("LiNing", "-----str-----" + url_load_price);
        Log.e("LiNing", "-----str-----" + db_check.getText().toString() + id_querySup + id_hs
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
//                final PriceLoadInfo cInfoDB = new Gson()
//                        .fromJson(str, PriceLoadInfo.class);
                if (cInfoDB != null) {
                    PriceActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            prdt = cInfoDB.getPrdt();
                            Log.e("LiNing", "prdt-----str-----" + PriceActivity.this.prdt);
                            priceAdapter = new PriceAdapter(one_add,onclik_go, R.layout.price_fuction_item1, prdt, context);
                            mListView1.setAdapter(priceAdapter);
                            priceAdapter.notifyDataSetChanged();
                            if (alertDialog.isShowing()) {
                                alertDialog.dismiss();
                            }
                            listClear();

                        }
                    });
                }

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    //    PriceAdapter.ViewHolder holder = null;
    public class PriceAdapter extends BaseAdapter {
        int id_row_layout, clik;
        LayoutInflater mInflater;
        private List<PriceLoadInfo.Prdt> infos;
        boolean ischeck = false;
        private OnItemClickListenerPrice priceListener;
        private int index;
        private int add_one;

        public PriceAdapter(int one_add, int onclik_go, int price_fuction_item1, List<PriceLoadInfo.Prdt> prdt_add_xz, Context context) {
            this.id_row_layout = price_fuction_item1;
            this.mInflater = LayoutInflater.from(context);
            this.infos = prdt_add_xz;
            this.clik = onclik_go;
            this.add_one = one_add;
            Log.e("LiNing", "添加数据===infos==新===数据==" + infos);
            Log.e("LiNing", "添加数据=====新===数据==" + prdt_add);
        }


//        public PriceAdapter(int one_add,int onclik_go, int price_fuction_item1, List<PriceLoadInfo.Prdt> prdts, PriceActivity priceActivity) {
//            this.id_row_layout = price_fuction_item1;
//            this.mInflater = LayoutInflater.from(context);
//            this.infos = prdts;
//            this.clik = onclik_go;
//            this.add_one = one_add;
//            Log.e("LiNing", "添加数据=====新===数据==" + prdts);
//        }



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
            Log.e("LiNing","----新数据more-----"+infos.size());
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
                synchronized (PriceActivity.this) {
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
                    holder.price_zxqk.setText(zxqk.getText().toString());
                    holder.price_bzxx.setText(zcsm.getText().toString());
//                    if(more==1){
//                         headSrcrollView = (MyHScrollView) mHead_more
//                                .findViewById(R.id.horizontalScrollView1);
//                    }else{
//
//                    }
                headSrcrollView = (MyHScrollView) mHead
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
            //编辑EditextSet(******放于此处避免listview数据错乱)
            EditextSet_add(holder, prdt_item);
            Log.e("LiNing", "添加数据=====新=====" + prdt_item);
            double v1 = new BigDecimal(prdt_item.getUP_MIN()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
            Log.e("LiNing", "4位数据====保留=====" + prdt_item.getUP_MIN());
            Log.e("LiNing", "4位数据====保留=====" + v1);
            int id_add = position + 1;
            int id = position;
            if(one_add==1){
                holder.checkbox.setText("" + id_add);
            }else{

                holder.checkbox.setText("" + id);
            }
            prdt_item.setXH("" + id);
            holder.price_name.setText(prdt_item.getNAME());
            holder.price_prdNo.setText(prdt_item.getPRD_NO());
            //保留4位小数
            holder.up_sal.setText("" + new BigDecimal(prdt_item.getUP_SAL()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
            holder.upr.setText("" + new BigDecimal(prdt_item.getUPR()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
            holder.up_min.setText("" + new BigDecimal(prdt_item.getUP_MIN()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
//            holder.up_sal.setText("" + prdt_item.getUP_SAL());
//            holder.upr.setText("" + prdt_item.getUPR());
//            holder.up_min.setText("" + prdt_item.getUP_MIN());
//            holder.price_zxqk.setText(prdt_item.getZC_ZXQK());
//            holder.price_bzxx.setText(prdt_item.getZC_BZXX());

            Log.e("LiNing", "---------" + clik);
            if (clik == 1) {
                String user_go = user_check.getText().toString();
                String opr_go = operate_check.getText().toString();
                String num_go = et_num.getText().toString();
                String tyzk_go = et_tyzk.getText().toString();
                if (user_go.equals("") || user_go == null) {
                    Toast.makeText(PriceActivity.this, "请选择参照对象", Toast.LENGTH_LONG).show();
                }
                if (opr_go.equals("") || opr_go == null) {
                    Toast.makeText(PriceActivity.this, "请选择运算符", Toast.LENGTH_LONG).show();
                }
                if (tyzk_go.equals("") || tyzk_go == null) {
                    tyzk_go = "100";
                }
                if (num_go.equals("") || num_go == null) {
                    num_go = "0";
                }
                if (num_go.equals("") || num_go == null && tyzk_go.equals("") || tyzk_go == null) {
                    Toast.makeText(PriceActivity.this, "请填写数值或折扣", Toast.LENGTH_LONG).show();
                }

//                else {

                //点击执行运算后更新listview的政策单价
                for(int i=0;i<infos.size();i++){
                    infos.get(i).setZC_ZK(tyzk_go);
                    holder.price_zk.setText(infos.get(i).getZC_ZK());
                    if (user_go.equals("单位成本")) {
                        if (opr_go.equals("+")) {
                            double v2 = infos.get(i).getUP_SAL() + Double.parseDouble(num_go);
                            double v = new BigDecimal(v2).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                            Log.e("LiNing", "添加数据=====新==4===" + ""+v2 );
                            Log.e("LiNing", "添加数据=====新==4===" + ""+v );
                            infos.get(i).setZC_DJ(""+v);
                            Log.e("LiNing", "添加数据=====新====dj=" + infos.get(i).getZC_DJ());
                        }
                        if (opr_go.equals("-")) {
                            double v2 = infos.get(i).getUP_SAL() - Double.parseDouble(num_go);
                            double v = new BigDecimal(v2).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            infos.get(i).setZC_DJ(""+v);
                            Log.e("LiNing", "添加数据=====新====dj=" + infos.get(i).getZC_DJ());
                        }
                        if (opr_go.equals("*")) {
                            double v2 = infos.get(i).getUP_SAL() * Double.parseDouble(num_go);
                            double v = new BigDecimal(v2).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            infos.get(i).setZC_DJ(""+v);
                            Log.e("LiNing", "添加数据=====新====dj=" + infos.get(i).getZC_DJ());
                        }
                        if (opr_go.equals("/")) {
                            double v2 = infos.get(i).getUP_SAL() / Double.parseDouble(num_go);
                            double v = new BigDecimal(v2).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            infos.get(i).setZC_DJ(""+v);
                            Log.e("LiNing", "添加数据=====新====dj=" + infos.get(i).getZC_DJ());
                        }
                    }
                    if (user_go.equals("统一定价")) {
                        if (opr_go.equals("+")) {
                            double v2 = infos.get(i).getUPR() + Double.parseDouble(num_go);
                            double v = new BigDecimal(v2).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            infos.get(i).setZC_DJ(""+v);
                            Log.e("LiNing", "添加数据=====新====dj=" + infos.get(i).getZC_DJ());
                        }
                        if (opr_go.equals("-")) {
                            double v2 = infos.get(i).getUPR() - Double.parseDouble(num_go);
                            double v = new BigDecimal(v2).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            infos.get(i).setZC_DJ(""+v);
                            Log.e("LiNing", "添加数据=====新====dj=" + infos.get(i).getZC_DJ());
                        }
                        if (opr_go.equals("*")) {
                            double v2 = infos.get(i).getUPR() * Double.parseDouble(num_go);
                            double v = new BigDecimal(v2).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
//                            double v = infos.get(i).getUPR() * Double.parseDouble(num_go);
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            infos.get(i).setZC_DJ(""+v);
                            Log.e("LiNing", "添加数据=====新====dj=" + infos.get(i).getZC_DJ());
                        }
                        if (opr_go.equals("/")) {
                            double v2 = infos.get(i).getUPR() / Double.parseDouble(num_go);
                            double v = new BigDecimal(v2).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            infos.get(i).setZC_DJ(""+v);
                            Log.e("LiNing", "添加数据=====新====dj=" + infos.get(i).getZC_DJ());
                        }
                    }
                    if (user_go.equals("最低售价")) {
                        if (opr_go.equals("+")) {
//                            double v = infos.get(i).getUP_MIN() + Double.parseDouble(num_go);
                            double v2 = infos.get(i).getUP_MIN() + Double.parseDouble(num_go);
                            double v = new BigDecimal(v2).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            infos.get(i).setZC_DJ(""+v);
                            Log.e("LiNing", "添加数据=====新====dj=" + infos.get(i).getZC_DJ());
                        }
                        if (opr_go.equals("-")) {
                            double v2 = infos.get(i).getUP_MIN() - Double.parseDouble(num_go);
                            double v = new BigDecimal(v2).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            infos.get(i).setZC_DJ(""+v);
                            Log.e("LiNing", "添加数据=====新====dj=" + infos.get(i).getZC_DJ());
                        }
                        if (opr_go.equals("*")) {
                            double v2 = infos.get(i).getUP_MIN() * Double.parseDouble(num_go);
                            double v = new BigDecimal(v2).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            infos.get(i).setZC_DJ(""+v);
                            Log.e("LiNing", "添加数据=====新====dj=" + infos.get(i).getZC_DJ());
                        }
                        if (opr_go.equals("/")) {
                            double v2 = infos.get(i).getUP_MIN() / Double.parseDouble(num_go);
                            double v = new BigDecimal(v2).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            infos.get(i).setZC_DJ(""+v);
                            Log.e("LiNing", "添加数据=====新====dj=" + infos.get(i).getZC_DJ());
                        }
                    }


                }
                //保留4位小数
//                Log.e("LiNing", "4位=====新===数据==" + infos.get(position).getZC_DJ());
//                Log.e("LiNing", "4位=====新===数据==" + Double.parseDouble(infos.get(position).getZC_DJ()));
//                Log.e("LiNing", "4位=====新===数据==" + new BigDecimal(Double.parseDouble(infos.get(position).getZC_DJ())).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
//                holder.price_danj.setText(""+new BigDecimal(Double.parseDouble(infos.get(position).getZC_DJ())).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
                holder.price_danj.setText(infos.get(position).getZC_DJ());
//                    JJCC_adapter(holder, prdt_item, user_go, opr_go, num_go, tyzk_go);

//                }
            }
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
            holder.checkbox.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    prdt.remove(position);
                    priceAdapter.notifyDataSetChanged();
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
                        prdt_item.setZC_DJ("0");
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
                        prdt_item.setZC_ZK("0");
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
                        prdt_item.setZC_ZXQK("0");
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
                        prdt_item.setZC_BZXX("0");
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

        private  class ViewHolder {
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

    public class MxPriceAdapter extends BaseAdapter {
        int id_row_layout, clik,add_one;
        LayoutInflater mInflater;
        private List<PriceMx.PrdtUp> infos;
        boolean ischeck = false;
        private OnItemClickListenerPrice priceListener;
        PriceMx.PrdtUp prdt_item_mx;

        public MxPriceAdapter(int onclik_go, int price_fuction_item1, List<PriceMx.PrdtUp> prdt, PriceActivity priceActivity) {
            this.id_row_layout = price_fuction_item1;
            this.mInflater = LayoutInflater.from(context);
            this.infos = prdt;
            this.clik = onclik_go;
        }

//        public MxPriceAdapter(int one_add, int onclik_go, int price_fuction_item1, List<PriceMx.PrdtUp> mx_prdtUp, PriceActivity context) {
//            this.id_row_layout = price_fuction_item1;
//            this.mInflater = LayoutInflater.from(context);
//            this.infos = mx_prdtUp;
//            this.clik = onclik_go;
//            this.add_one=one_add;
//        }

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
            final ViewHolder holder;
            if (convertView == null) {
                synchronized (PriceActivity.this) {
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

            prdt_item_mx = infos.get(position);
            Log.e("LiNing","-----xh==cx----"+prdt_item_mx);
            Log.e("LiNing","------"+prdt_item_mx.getDis_CNT());

            //编辑EditextSet
            EditextSet_set(holder, prdt_item_mx);
            final int itm = prdt_item_mx.getITM();
            Log.e("LiNing","-----xh==cx----"+itm);
            Log.e("LiNing","--p---xh==cx----"+position);
//            int itm_add = prdt_item_mx.getITM()+1;
//            Log.e("LiNing","-----xh==cx----"+itm+"------"+itm_add);
//            if(add_one==1){
//                itm= itm+infos.size();
//                holder.checkbox.setText("" + itm_add);
//            }else{
//                holder.checkbox.setText("" + itm);
//            }
            holder.checkbox.setText(""+position);
//            if(add_one==1||add_one==2){
//            }else{
//                holder.checkbox.setText(""+itm);
//            }
            holder.price_name.setText(prdt_item_mx.getPrice());
//            holder.price_prdNo.setText(prdt_item_mx.getPrice_Id());
            holder.price_prdNo.setText(prdt_item_mx.getPrdNo());
//            保留4位小数
            holder.up_sal.setText("" + new BigDecimal(prdt_item_mx.getCst_Up()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
            holder.upr.setText("" + new BigDecimal(prdt_item_mx.getUPR()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
            holder.up_min.setText("" + new BigDecimal(prdt_item_mx.getMIN_UP()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
            holder.price_danj.setText("" + new BigDecimal(prdt_item_mx.getPrdUp()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
//            holder.up_sal.setText("" + prdt_item_mx.getCst_Up());
//            holder.upr.setText("" + prdt_item_mx.getUPR());
//            holder.up_min.setText("" + prdt_item_mx.getMIN_UP());
//            holder.price_danj.setText("" + prdt_item_mx.getPrdUp());
            holder.price_zk.setText(prdt_item_mx.getDis_CNT());
            holder.price_zxqk.setText(prdt_item_mx.getYN());
            holder.price_bzxx.setText(prdt_item_mx.getREM());
            //不可编辑
//            NoSet(holder);

            Log.e("LiNing", "---------" + clik);

            if (clik == 1) {
                String user_go = user_check.getText().toString();
                String opr_go = operate_check.getText().toString();
                String num_go = et_num.getText().toString();
                String tyzk_go = et_tyzk.getText().toString();
                if (user_go.equals("") || user_go == null) {
                    Toast.makeText(PriceActivity.this, "请选择参照对象", Toast.LENGTH_LONG).show();
                }
                if (opr_go.equals("") || opr_go == null) {
                    Toast.makeText(PriceActivity.this, "请选择运算符", Toast.LENGTH_LONG).show();
                }
                if (tyzk_go.equals("") || tyzk_go == null) {
                    tyzk_go = "100";
                }
                if (num_go.equals("") || num_go == null) {
                    num_go = "0";
                }
                if (num_go.equals("") || num_go == null && tyzk_go.equals("") || tyzk_go == null) {
                    Toast.makeText(PriceActivity.this, "请填写数值或折扣", Toast.LENGTH_LONG).show();
                }

//                JJCC_adapter_mx(holder, prdt_item_mx, user_go, opr_go, num_go, tyzk_go);
                //点击执行运算后更新listview的政策单价
                for(int i=0;i<infos.size();i++){
                    infos.get(i).setZC_ZK(tyzk_go);
                    holder.price_zk.setText(infos.get(i).getZC_ZK());
                    if (user_go.equals("单位成本")) {
                        if (opr_go.equals("+")) {
//                            double v = Double.parseDouble(infos.get(i).getCst_Up()) + Double.parseDouble(num_go);
                            double v2 = Double.parseDouble(infos.get(i).getCst_Up()) + Double.parseDouble(num_go);
                            double v = new BigDecimal(v2).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            infos.get(i).setPrdUp(""+v);
                            Log.e("LiNing", "添加数据=====新====dj=" + infos.get(i).getPrdUp());
                        }
                        if (opr_go.equals("-")) {
//                            double v = Double.parseDouble(infos.get(i).getCst_Up()) - Double.parseDouble(num_go);
                            double v2 = Double.parseDouble(infos.get(i).getCst_Up()) - Double.parseDouble(num_go);
                            double v = new BigDecimal(v2).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            infos.get(i).setPrdUp(""+v);
                            Log.e("LiNing", "添加数据=====新====dj=" + infos.get(i).getPrdUp());
                        }
                        if (opr_go.equals("*")) {
//                            double v = Double.parseDouble(infos.get(i).getCst_Up()) * Double.parseDouble(num_go);
                            double v2 = Double.parseDouble(infos.get(i).getCst_Up()) * Double.parseDouble(num_go);
                            double v = new BigDecimal(v2).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            infos.get(i).setPrdUp(""+v);
                            Log.e("LiNing", "添加数据=====新====dj=" + infos.get(i).getPrdUp());
                        }
                        if (opr_go.equals("/")) {
//                            double v = Double.parseDouble(infos.get(i).getCst_Up()) / Double.parseDouble(num_go);
                            double v2 = Double.parseDouble(infos.get(i).getCst_Up()) / Double.parseDouble(num_go);
                            double v = new BigDecimal(v2).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            infos.get(i).setPrdUp(""+v);
                            Log.e("LiNing", "添加数据=====新====dj=" + infos.get(i).getPrdUp());
                        }
                    }
                    if (user_go.equals("统一定价")) {
                        if (opr_go.equals("+")) {
//                            double v = Double.parseDouble(infos.get(i).getUPR()) + Double.parseDouble(num_go);
                            double v2 = Double.parseDouble(infos.get(i).getUPR()) + Double.parseDouble(num_go);
                            double v = new BigDecimal(v2).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            infos.get(i).setPrdUp(""+v);
                            Log.e("LiNing", "添加数据=====新====dj=" + infos.get(i).getPrdUp());
                        }
                        if (opr_go.equals("-")) {
//                            double v = Double.parseDouble(infos.get(i).getUPR()) - Double.parseDouble(num_go);
                            double v2 = Double.parseDouble(infos.get(i).getUPR()) - Double.parseDouble(num_go);
                            double v = new BigDecimal(v2).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            infos.get(i).setPrdUp(""+v);
                            Log.e("LiNing", "添加数据=====新====dj=" + infos.get(i).getPrdUp());
                        }
                        if (opr_go.equals("*")) {
//                            double v = Double.parseDouble(infos.get(i).getUPR()) * Double.parseDouble(num_go);
                            double v2 = Double.parseDouble(infos.get(i).getUPR()) * Double.parseDouble(num_go);
                            double v = new BigDecimal(v2).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            infos.get(i).setPrdUp(""+v);
                            Log.e("LiNing", "添加数据=====新====dj=" + infos.get(i).getPrdUp());
                        }
                        if (opr_go.equals("/")) {
//                            double v = Double.parseDouble(infos.get(i).getUPR()) / Double.parseDouble(num_go);
                            double v2 = Double.parseDouble(infos.get(i).getUPR()) / Double.parseDouble(num_go);
                            double v = new BigDecimal(v2).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            infos.get(i).setPrdUp(""+v);
                            Log.e("LiNing", "添加数据=====新====dj=" + infos.get(i).getPrdUp());
                        }
                    }
                    if (user_go.equals("最低售价")) {
                        if (opr_go.equals("+")) {
//                            double v = Double.parseDouble(infos.get(i).getMIN_UP()) + Double.parseDouble(num_go);
                            double v2 = Double.parseDouble(infos.get(i).getMIN_UP()) + Double.parseDouble(num_go);
                            double v = new BigDecimal(v2).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            infos.get(i).setPrdUp(""+v);
                            Log.e("LiNing", "添加数据=====新====dj=" + infos.get(i).getPrdUp());
                        }
                        if (opr_go.equals("-")) {
//                            double v = Double.parseDouble(infos.get(i).getMIN_UP()) - Double.parseDouble(num_go);
                            double v2 = Double.parseDouble(infos.get(i).getMIN_UP()) - Double.parseDouble(num_go);
                            double v = new BigDecimal(v2).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            infos.get(i).setPrdUp(""+v);
                            Log.e("LiNing", "添加数据=====新====dj=" + infos.get(i).getPrdUp());
                        }
                        if (opr_go.equals("*")) {
//                            double v = Double.parseDouble(infos.get(i).getMIN_UP()) * Double.parseDouble(num_go);
                            double v2 = Double.parseDouble(infos.get(i).getMIN_UP()) * Double.parseDouble(num_go);
                            double v = new BigDecimal(v2).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            infos.get(i).setPrdUp(""+v);
                            Log.e("LiNing", "添加数据=====新====dj=" + infos.get(i).getPrdUp());
                        }
                        if (opr_go.equals("/")) {
//                            double v = Double.parseDouble(infos.get(i).getMIN_UP()) / Double.parseDouble(num_go);
                            double v2 = Double.parseDouble(infos.get(i).getMIN_UP()) / Double.parseDouble(num_go);
                            double v = new BigDecimal(v2).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            infos.get(i).setPrdUp(""+v);
                            Log.e("LiNing", "添加数据=====新====dj=" + infos.get(i).getPrdUp());
                        }
                    }

                }
//                保留4位小数
//                holder.price_danj.setText("" + new BigDecimal(infos.get(position).getPrdUp()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
//                holder.price_danj.setText("" + new BigDecimal(Double.parseDouble(infos.get(position).getPrdUp())).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
                holder.price_danj.setText(infos.get(position).getPrdUp());

//                }
            }

            else if (clik == 3) {//修改

                Set(holder);
            } else {
                NoSet(holder);
            }
//            if (clik == 2) {//保存
//
//                NoSet(holder);
//            }
            if(add_one==2){
                convertView.findViewById(R.id.ib_adddel).setVisibility(View.GONE);
            }else{
                holder.checkbox.setVisibility(View.VISIBLE);
                holder.checkbox.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mx_prdtUp.remove(position);
                        mxPriceAdapter.notifyDataSetChanged();
                        Log.e("LiNing", "----1-----" + mx_prdtUp.size());
                    }
                });
            }
            return convertView;
        }


        private void EditextSet_set(ViewHolder holder, final PriceMx.PrdtUp prdt_item_mx) {
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
                        prdt_item_mx.setCst_Up("0");
                    } else {
                        prdt_item_mx.setCst_Up(s.toString());
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
                        prdt_item_mx.setUPR("0");
                    } else {
                        prdt_item_mx.setUPR(s.toString());
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
                        prdt_item_mx.setMIN_UP("0");
                    } else {
                        prdt_item_mx.setMIN_UP(s.toString());
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
                        prdt_item_mx.setPrdUp("0");
                    } else {
                        prdt_item_mx.setPrdUp(s.toString());
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
                        prdt_item_mx.setDis_CNT("0");
                    } else {
                        prdt_item_mx.setDis_CNT(s.toString());
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
                        prdt_item_mx.setYN("n");
                    } else {
                        prdt_item_mx.setYN(s.toString());
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
                        prdt_item_mx.setREM("0");
                    } else {
                        prdt_item_mx.setREM(s.toString());
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

        private void NoSet(ViewHolder holder) {
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

        private void Set(ViewHolder holder) {
            holder.price_name.setFocusable(false);
            holder.price_prdNo.setFocusable(false);
            holder.up_sal.setFocusable(true);
            holder.upr.setFocusable(true);
            holder.up_min.setFocusable(true);
            holder.price_danj.setFocusable(true);
            holder.price_zk.setFocusable(true);
            holder.price_zxqk.setFocusable(true);
            holder.price_bzxx.setFocusable(true);
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
            public RelativeLayout layouts;
            HorizontalScrollView scrollView;
            public EditText price_id;
            public TextView price_name;
            public TextView price_prdNo;
            public EditText up_sal;
            public EditText upr;
            public EditText up_min;
            public EditText price_danj;
            public EditText price_zk;
            public EditText price_zxqk;
            public EditText price_bzxx;
            public TextView checkbox;


        }


    }

    public class InAdapter extends BaseAdapter {
        private Context context;
        int id_row_layout, clik;
        LayoutInflater mInflater;
        private ArrayList<HashMap<String, Object>> infos;

        public InAdapter(int onclik_go, int price_fuction_item1, ArrayList<HashMap<String, Object>> in_info, Context context) {
            this.id_row_layout = price_fuction_item1;
            this.infos = in_info;
            this.context = context;
            mInflater = LayoutInflater.from(context);
            this.clik = onclik_go;
        }

        @Override
        public int getCount() {
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
            ViewHolderIn holderIn;
            if (convertView == null) {
                synchronized (PriceActivity.this) {
                    convertView = mInflater.inflate(id_row_layout, null);
                    holderIn = new ViewHolderIn();
                    MyHScrollView scrollView1 = (MyHScrollView) convertView
                            .findViewById(R.id.horizontalScrollView1);

                    holderIn.scrollView = scrollView1;
                    holderIn.xh = (TextView) convertView
                            .findViewById(R.id.price_showChecx);
                    holderIn.xh.setVisibility(View.VISIBLE);
                    holderIn.checkbox = (TextView) convertView
                            .findViewById(R.id.price_showChecx);
                    holderIn.hp_bh = (TextView) convertView.findViewById(R.id.price_brand_id);
                    holderIn.hp_mc = (TextView) convertView.findViewById(R.id.price_brand_name);
                    holderIn.dw_cb = (EditText) convertView.findViewById(R.id.price_dw_cb);
                    holderIn.ty_dj = (EditText) convertView.findViewById(R.id.price_ty_dj);
                    holderIn.zd_sj = (EditText) convertView.findViewById(R.id.price_min);
                    holderIn.zc_dj = (EditText) convertView.findViewById(R.id.price_danj);
                    holderIn.xs_zk = (EditText) convertView.findViewById(R.id.price_zk);
                    holderIn.zx_qk = (EditText) convertView.findViewById(R.id.price_zx_qk);
                    holderIn.bz_xx = (EditText) convertView.findViewById(R.id.price_bz_xx);
                    MyHScrollView headSrcrollView = (MyHScrollView) mHead
                            .findViewById(R.id.horizontalScrollView1);
                    headSrcrollView
                            .AddOnScrollChangedListener(new OnScrollChangedListenerImp(
                                    scrollView1));
                    convertView.setTag(holderIn);
                }
            } else {
                holderIn = (ViewHolderIn) convertView.getTag();
            }

            HashMap<String, Object> item_get_in = infos.get(position);
            //编辑editext
            EditextSet_in(holderIn, item_get_in);
            String xh_get_in = item_get_in.get("转入序号").toString();
            String bh_get_in = item_get_in.get("转入货品编号").toString();
            String mc_get_in = item_get_in.get("转入货品名称").toString();
            String dwcb_get_in = item_get_in.get("转入单位成本").toString();
            String tydj_get_in = item_get_in.get("转入统一定价").toString();
            String zdsj_get_in = item_get_in.get("转入最低售价").toString();
            String zcdj_get_in = item_get_in.get("转入政策定价").toString();
            String xszk_get_in = item_get_in.get("转入销售折扣").toString();
            String zxqk_get_in = item_get_in.get("转入执行情况").toString();
            String bzxx_get_in = item_get_in.get("转入备注信息").toString();
//            new DecimalFormat("#.00").format(dwcb_get_in);
            holderIn.xh.setText(xh_get_in);
            holderIn.hp_bh.setText(bh_get_in);
            holderIn.hp_mc.setText(mc_get_in);
            if (xszk_get_in.equals("")) {
                holderIn.xs_zk.setText("");
            } else {
//                holderIn.xs_zk.setText(new DecimalFormat("#.00").format(Double.parseDouble(xszk_get_in)));
                holderIn.xs_zk.setText(xszk_get_in);
            }
//            holderIn.dw_cb.setText(new DecimalFormat("#.00").format(Double.parseDouble(dwcb_get_in)));
//            holderIn.ty_dj.setText(new DecimalFormat("#.00").format(Double.parseDouble(tydj_get_in)));
//            holderIn.zd_sj.setText(new DecimalFormat("#.00").format(Double.parseDouble(zdsj_get_in)));
//            holderIn.zc_dj.setText(new DecimalFormat("#.00").format(Double.parseDouble(zcdj_get_in)));
            holderIn.dw_cb.setText(dwcb_get_in);
            holderIn.ty_dj.setText(tydj_get_in);
            holderIn.zd_sj.setText(zdsj_get_in);
            holderIn.zc_dj.setText(zcdj_get_in);

            holderIn.zx_qk.setText(zxqk_get_in);
            holderIn.bz_xx.setText(bzxx_get_in);

//            JJCC_adapter_in(holderIn, item_get_in, user_go, opr_go, num_go, tyzk_go);

            if (clik == 1) {

                String user_go = user_check.getText().toString();
                String opr_go = operate_check.getText().toString();
                String num_go = et_num.getText().toString();
                String tyzk_go = et_tyzk.getText().toString();
                if (user_go.equals("") || user_go == null) {
                    Toast.makeText(PriceActivity.this, "请选择参照对象", Toast.LENGTH_LONG).show();
                }
                if (opr_go.equals("") || opr_go == null) {
                    Toast.makeText(PriceActivity.this, "请选择运算符", Toast.LENGTH_LONG).show();
                }
                if (tyzk_go.equals("") || tyzk_go == null) {
                    tyzk_go = "100";
                }
                if (num_go.equals("") || num_go == null) {
                    num_go = "0";
                }
                if (num_go.equals("") || num_go == null && tyzk_go.equals("") || tyzk_go == null) {
                    Toast.makeText(PriceActivity.this, "请填写数值或折扣", Toast.LENGTH_LONG).show();
                }

                //点击执行运算后更新listview的政策单价
                for(int i=0;i<infos.size();i++){
//                    infos.get(i).setZC_ZK(tyzk_go);

                    String zrzk = infos.get(i).get("转入销售折扣").toString();
                    zrzk=tyzk_go;
                    holderIn.xs_zk.setText(zrzk);
                    String zcdj_zrcs;
                    zcdj_zrcs = infos.get(i).get("转入政策定价").toString();
                    if (user_go.equals("单位成本")) {
                        if (opr_go.equals("+")) {
                            double v = Double.parseDouble(infos.get(i).get("转入单位成本").toString()) + Double.parseDouble(num_go);
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            zcdj_zrcs=""+v;
                            Log.e("LiNing", "添加数据=====新====dj=" + zcdj_zrcs);
                        }
                        if (opr_go.equals("-")) {
                            double v = Double.parseDouble(infos.get(i).get("转入单位成本").toString()) - Double.parseDouble(num_go);
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            zcdj_zrcs=""+v;
                            Log.e("LiNing", "添加数据=====新====dj=" + zcdj_zrcs);
                        }
                        if (opr_go.equals("*")) {
                            double v = Double.parseDouble(infos.get(i).get("转入单位成本").toString()) * Double.parseDouble(num_go);
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            zcdj_zrcs=""+v;
                            Log.e("LiNing", "添加数据=====新====dj=" + zcdj_zrcs);
                        }
                        if (opr_go.equals("/")) {
                            double v = Double.parseDouble(infos.get(i).get("转入单位成本").toString()) / Double.parseDouble(num_go);
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            zcdj_zrcs=""+v;
                            Log.e("LiNing", "添加数据=====新====dj=" + zcdj_zrcs);
                        }
                    }
                    if (user_go.equals("统一定价")) {
                        if (opr_go.equals("+")) {
                            double v = Double.parseDouble(infos.get(i).get("转入统一定价").toString()) + Double.parseDouble(num_go);
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            zcdj_zrcs=""+v;
                            Log.e("LiNing", "添加数据=====新====dj=" + zcdj_zrcs);
                        }
                        if (opr_go.equals("-")) {
                            double v = Double.parseDouble(infos.get(i).get("转入统一定价").toString()) - Double.parseDouble(num_go);
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            zcdj_zrcs=""+v;
                            Log.e("LiNing", "添加数据=====新====dj=" + zcdj_zrcs);
                        }
                        if (opr_go.equals("*")) {
                            double v = Double.parseDouble(infos.get(i).get("转入统一定价").toString()) * Double.parseDouble(num_go);
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            zcdj_zrcs=""+v;
                            Log.e("LiNing", "添加数据=====新====dj=" + zcdj_zrcs);
                        }
                        if (opr_go.equals("/")) {
                            double v = Double.parseDouble(infos.get(i).get("转入统一定价").toString()) / Double.parseDouble(num_go);
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            zcdj_zrcs=""+v;
                            Log.e("LiNing", "添加数据=====新====dj=" + zcdj_zrcs);
                        }
                    }
                    if (user_go.equals("最低售价")) {
                        if (opr_go.equals("+")) {
                            double v = Double.parseDouble(infos.get(i).get("转入最低售价").toString()) + Double.parseDouble(num_go);
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            zcdj_zrcs=""+v;
                            Log.e("LiNing", "添加数据=====新====dj=" + zcdj_zrcs);
                        }
                        if (opr_go.equals("-")) {
                            double v = Double.parseDouble(infos.get(i).get("转入最低售价").toString()) - Double.parseDouble(num_go);
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            zcdj_zrcs=""+v;
                            Log.e("LiNing", "添加数据=====新====dj=" + zcdj_zrcs);
                        }
                        if (opr_go.equals("*")) {
                            double v = Double.parseDouble(infos.get(i).get("转入最低售价").toString()) * Double.parseDouble(num_go);
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            zcdj_zrcs=""+v;
                            Log.e("LiNing", "添加数据=====新====dj=" + zcdj_zrcs);
                        }
                        if (opr_go.equals("/")) {
                            double v = Double.parseDouble(infos.get(i).get("转入最低售价").toString()) / Double.parseDouble(num_go);
                            Log.e("LiNing", "添加数据=====新=====" + ""+v );
                            zcdj_zrcs=""+v;
                            Log.e("LiNing", "添加数据=====新====dj=" + zcdj_zrcs);
                        }
                    }

                    holderIn.zc_dj.setText(zcdj_zrcs);
                }

//                }
            }


            else if (clik == 4) {

                Set(holderIn);
            }
//            else {
//                NoSet(holderIn);
//            }
            if (clik == 2) {

                NoSet(holderIn);
            }
            holderIn.checkbox.setVisibility(View.VISIBLE);
            holderIn.checkbox.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    in_list.remove(position);
                    inAdapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }

        private void NoSet(ViewHolderIn holder) {
            holder.hp_mc.setFocusable(false);
            holder.hp_bh.setFocusable(false);
            holder.dw_cb.setFocusable(false);
            holder.ty_dj.setFocusable(false);
            holder.zd_sj.setFocusable(false);
            holder.zc_dj.setFocusable(false);
            holder.xs_zk.setFocusable(false);
            holder.zx_qk.setFocusable(false);
            holder.bz_xx.setFocusable(false);
        }

        private void Set(ViewHolderIn holder) {
            holder.hp_mc.setFocusable(false);
            holder.hp_bh.setFocusable(false);
            holder.dw_cb.setFocusable(true);
            holder.ty_dj.setFocusable(true);
            holder.zd_sj.setFocusable(true);
            holder.zc_dj.setFocusable(true);
            holder.xs_zk.setFocusable(true);
            holder.zx_qk.setFocusable(true);
            holder.bz_xx.setFocusable(true);
        }

        private void JJCC_adapter_in(ViewHolderIn holderIn, HashMap<String, Object> item_get_in, String user_go, String opr_go, String num_go, String tyzk_go) {
            if (user_go.equals("单位成本")) {
                if (opr_go.equals("+")) {
                    danj = Double.parseDouble(item_get_in.get("转入单位成本").toString()) + Double.parseDouble(num_go);
                    new_danj = danj * Double.parseDouble(tyzk_go);
                    holderIn.zc_dj.setText("" + danj);
//                    str_newdj = new DecimalFormat("#.00").format(new_danj);
//                    holderIn.zc_dj.setText(str_newdj);
                }
                if (opr_go.equals("-")) {
                    danj = Double.parseDouble(item_get_in.get("转入单位成本").toString()) - Double.parseDouble(num_go);
                    new_danj = danj * Double.parseDouble(tyzk_go);
//                    str_newdj = new DecimalFormat("#.00").format(new_danj);
//                    holderIn.zc_dj.setText(str_newdj);
                    holderIn.zc_dj.setText("" + danj);
                }
                if (opr_go.equals("*")) {
                    danj = Double.parseDouble(item_get_in.get("转入单位成本").toString()) * Double.parseDouble(num_go);
                    new_danj = danj * Double.parseDouble(tyzk_go);
//                    str_newdj = new DecimalFormat("#.00").format(new_danj);
//                    holderIn.zc_dj.setText(str_newdj);
                    holderIn.zc_dj.setText("" + danj);
                }
                if (opr_go.equals("/")) {
                    danj = Double.parseDouble(item_get_in.get("转入单位成本").toString()) / Double.parseDouble(num_go);
                    new_danj = danj * Double.parseDouble(tyzk_go);
//                    str_newdj = new DecimalFormat("#.00").format(new_danj);
//                    holderIn.zc_dj.setText(str_newdj);
                    holderIn.zc_dj.setText("" + danj);
                }

            }
            if (user_go.equals("统一定价")) {
                if (opr_go.equals("+")) {
                    danj = Double.parseDouble(item_get_in.get("转入统一定价").toString()) + Double.parseDouble(num_go);
                    new_danj = danj * Double.parseDouble(tyzk_go);
//                    str_newdj = new DecimalFormat("#.00").format(new_danj);
//                    holderIn.zc_dj.setText(str_newdj);
                    holderIn.zc_dj.setText("" + danj);
                }
                if (opr_go.equals("-")) {
                    danj = Double.parseDouble(item_get_in.get("转入统一定价").toString()) - Double.parseDouble(num_go);
                    new_danj = danj * Double.parseDouble(tyzk_go);
//                    str_newdj = new DecimalFormat("#.00").format(new_danj);
//                    holderIn.zc_dj.setText(str_newdj);
                    holderIn.zc_dj.setText("" + danj);
                }
                if (opr_go.equals("*")) {
                    danj = Double.parseDouble(item_get_in.get("转入统一定价").toString()) * Double.parseDouble(num_go);
                    new_danj = danj * Double.parseDouble(tyzk_go);
//                    str_newdj = new DecimalFormat("#.00").format(new_danj);
//                    holderIn.zc_dj.setText(str_newdj);
                    holderIn.zc_dj.setText("" + danj);
                }
                if (opr_go.equals("/")) {
                    danj = Double.parseDouble(item_get_in.get("转入统一定价").toString()) / Double.parseDouble(num_go);
                    new_danj = danj * Double.parseDouble(tyzk_go);
//                    str_newdj = new DecimalFormat("#.00").format(new_danj);
//                    holderIn.zc_dj.setText(str_newdj);
                    holderIn.zc_dj.setText("" + danj);
                }

            }
            if (user_go.equals("最低售价")) {
                if (opr_go.equals("+")) {
                    danj = Double.parseDouble(item_get_in.get("转入最低售价").toString()) + Double.parseDouble(num_go);
                    new_danj = danj * Double.parseDouble(tyzk_go);
//                    str_newdj = new DecimalFormat("#.00").format(new_danj);
//                    holderIn.zc_dj.setText(str_newdj);
                    holderIn.zc_dj.setText("" + danj);
                }
                if (opr_go.equals("-")) {
                    danj = Double.parseDouble(item_get_in.get("转入最低售价").toString()) - Double.parseDouble(num_go);
                    new_danj = danj * Double.parseDouble(tyzk_go);
//                    str_newdj = new DecimalFormat("#.00").format(new_danj);
//                    holderIn.zc_dj.setText(str_newdj);
                    holderIn.zc_dj.setText("" + danj);
                }
                if (opr_go.equals("*")) {
                    danj = Double.parseDouble(item_get_in.get("转入最低售价").toString()) * Double.parseDouble(num_go);
                    new_danj = danj * Double.parseDouble(tyzk_go);
//                    str_newdj = new DecimalFormat("#.00").format(new_danj);
//                    holderIn.zc_dj.setText(str_newdj);
                    holderIn.zc_dj.setText("" + danj);
                }
                if (opr_go.equals("/")) {
                    danj = Double.parseDouble(item_get_in.get("转入最低售价").toString()) / Double.parseDouble(num_go);
                    new_danj = danj * Double.parseDouble(tyzk_go);
//                    str_newdj = new DecimalFormat("#.00").format(new_danj);
//                    holderIn.zc_dj.setText(str_newdj);
                    holderIn.zc_dj.setText("" + danj);
                }

            }
        }

        private void EditextSet_in(ViewHolderIn holderIn, final HashMap<String, Object> item_get_in) {
            if (holderIn.dw_cb.getTag() instanceof TextWatcher) {
                holderIn.dw_cb.removeTextChangedListener((TextWatcher) holderIn.dw_cb.getTag());
            }
            if (holderIn.ty_dj.getTag() instanceof TextWatcher) {
                holderIn.ty_dj.removeTextChangedListener((TextWatcher) holderIn.ty_dj.getTag());
            }
            if (holderIn.zd_sj.getTag() instanceof TextWatcher) {
                holderIn.zd_sj.removeTextChangedListener((TextWatcher) holderIn.zd_sj.getTag());
            }
            if (holderIn.zc_dj.getTag() instanceof TextWatcher) {
                holderIn.zc_dj.removeTextChangedListener((TextWatcher) holderIn.zc_dj.getTag());
            }
            if (holderIn.xs_zk.getTag() instanceof TextWatcher) {
                holderIn.xs_zk.removeTextChangedListener((TextWatcher) holderIn.xs_zk.getTag());
            }
            if (holderIn.zx_qk.getTag() instanceof TextWatcher) {
                holderIn.zx_qk.removeTextChangedListener((TextWatcher) holderIn.zx_qk.getTag());
            }
            if (holderIn.bz_xx.getTag() instanceof TextWatcher) {
                holderIn.bz_xx.removeTextChangedListener((TextWatcher) holderIn.bz_xx.getTag());
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
                        item_get_in.put("转入单位成本", "0");
                    } else {
                        item_get_in.put("转入单位成本", s.toString());
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
                        item_get_in.put("转入统一定价", "0");
                    } else {
                        item_get_in.put("转入统一定价", s.toString());
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
                        item_get_in.put("转入最低售价", "0");
                    } else {
                        item_get_in.put("转入最低售价", s.toString());
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
                        item_get_in.put("转入政策定价", "0");
                    } else {
                        item_get_in.put("转入政策定价", s.toString());
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
                        item_get_in.put("转入销售折扣", "0");
                    } else {
                        item_get_in.put("转入销售折扣", s.toString());
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
                        item_get_in.put("转入执行情况", "n");
                    } else {
                        item_get_in.put("转入执行情况", s.toString());
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
                        item_get_in.put("转入备注信息", "0");
                    } else {
                        item_get_in.put("转入备注信息", s.toString());
                    }
                }
            };
            holderIn.dw_cb.addTextChangedListener(watcher_dwcb);
            holderIn.dw_cb.setTag(watcher_dwcb);
            holderIn.ty_dj.addTextChangedListener(watcher_tydj);
            holderIn.ty_dj.setTag(watcher_tydj);
            holderIn.zd_sj.addTextChangedListener(watcher_zdsj);
            holderIn.zd_sj.setTag(watcher_zdsj);
            holderIn.zc_dj.addTextChangedListener(watcher_zcdj);
            holderIn.zc_dj.setTag(watcher_zcdj);
            holderIn.xs_zk.addTextChangedListener(watcher_zk);
            holderIn.xs_zk.setTag(watcher_zk);
            holderIn.zx_qk.addTextChangedListener(watcher_zcqk);
            holderIn.zx_qk.setTag(watcher_zcqk);
            holderIn.bz_xx.addTextChangedListener(watcher_bzxx);
            holderIn.bz_xx.setTag(watcher_bzxx);
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

        public class ViewHolderIn {
            HorizontalScrollView scrollView;
            public TextView checkbox;
            public TextView xh;
            public TextView hp_bh;
            public TextView hp_mc;
            public TextView dw_cb;
            public TextView ty_dj;
            public TextView zd_sj;
            public TextView zc_dj;
            public TextView xs_zk;
            public TextView zx_qk;
            public TextView bz_xx;

        }
    }

    //清空数据
    private void clearInfo() {
        mListView1.setAdapter(null);
//        tv_prdKND.setText(null);
//        tv_prdLevre.setText(null);
        db_check.setText(null);
        brand_check.setText(null);
        hs_check.setText(null);
        cust_check.setText(null);
        user_check.setText(null);
        operate_check.setText(null);
        price_num_id.setText(null);
        et_num.setText(null);
        et_tyzk.setText(null);
        zcsm.setText(null);
        zcmc.setText(null);
//        zxqk.setText(null);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_MONTH, 1); //当前月1号和最后日期
        start.setText(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
        stop.setText(str_time);
        time.setText(date_dd);
        one_add=0;
        more=0;
    }

    public void saveEditData(int position, String str) {
        Toast.makeText(this, str + "----" + position, Toast.LENGTH_LONG).show();
        Log.e("LiNing", "---" + position + "---" + str);
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
        idList_hpdj.clear();
        idList_hpdl.clear();
        idList_hpdjID.clear();
        idList_hpdlID.clear();
        //新9.18
    }


    //    test9.13，
    public void cs_anoter(View v){
        more=2;
        one_add=2;
        Intent intent=new Intent(context,AddMoreActivity.class);
        intent.putExtra("DONE",""+done);
        intent.putExtra("DB_PRICE",db_check.getText().toString());
        intent.putExtra("ON_CLICK",""+onclik_go);
        startActivityForResult(intent, 21);
    }

}
