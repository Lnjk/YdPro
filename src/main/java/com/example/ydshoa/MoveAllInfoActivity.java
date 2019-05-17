package com.example.ydshoa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.LeftOrRight.MyHScrollView;
import com.example.bean.CustPsxxInfos;
import com.example.bean.CusterInfoDB;
import com.example.bean.CustesrAllInfos;
import com.example.bean.JsonRootBean;
import com.example.bean.PriceNumID;
import com.example.bean.URLS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MoveAllInfoActivity extends Activity implements View.OnClickListener {
    private Context context;
    private SharedPreferences sp;
    RelativeLayout mHead;
    ListView mListView1;
    private Button del, xd, newAdd, reset;
    //     记录选中item的下标 //    保存每个item中的checkbox
    private List<Integer> checkedIndexList;
    private List<CheckBox> checkBoxList;
    private List<String> idList,idList_dz,tel_List,idList_shr,idList_shdh,idList_ss,idList_qx;
    private AlertDialog alertDialog;
    private String session,db_zt,user_Id,date_dd,dabh_befor ,sszt_befor,sub_id,sub_dz,sub_shr,sub_shdh,sub_ss,sub_qx,url_do;
    private TextView dzbh,head,zt,dacard,yhbh,sfmr,xxdz,ss,qx;
    private EditText shr,lxdh;
    private ImageButton zt_xl,mr_xl;
    LinearLayout touch;
    String url_dh_price = URLS.price_num_ls;
    String ps_add = URLS.psxx_add;
    String ps_del = URLS.psxx_del;
    String ps_set = URLS.psxx_updata;
    String ps_query = URLS.psxx_query;
    CstAdapter cst_adapter;
    List<CustPsxxInfos.CustConInfList> custList;//回调要用
    String extra_zt,extra_dabh,extra_itm,extra_shr,extra_dh,extra_ss,extra_qx,extra_adress,extra_userid,extra_def;
    int do_save;
    CityPickerView mCityPickerView = new CityPickerView();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_move_all_info);
        context = MoveAllInfoActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        db_zt = sp.getString("DB_MR", "");
        user_Id = sp.getString("USER_ID", "");
         sszt_befor = getIntent().getStringExtra("SSZT");
         dabh_befor = getIntent().getStringExtra("DABH");

        InitView();
        //		 * 预先加载仿iOS滚轮实现的全部数据
        mCityPickerView.init(this);
    }

    private void InitView() {
        this.mHead = ((RelativeLayout) findViewById(R.id.move_head));
        this.mHead.setFocusable(true);
        this.mHead.setClickable(true);
        this.mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        this.mListView1 = ((ListView) findViewById(R.id.lv_moveAll_header));
        this.mListView1
                .setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        this.newAdd = ((Button) findViewById(R.id.btn_moveAll_add));
        this.reset = ((Button) findViewById(R.id.btn_moveAll_reset));
        this.del = ((Button) findViewById(R.id.btn_moveAll_del));
        this.xd = ((Button) findViewById(R.id.btn_moveAll_info));
        this.newAdd.setOnClickListener(this);
        this.reset.setOnClickListener(this);
        this.del.setOnClickListener(this);
        this.xd.setOnClickListener(this);
        checkedIndexList = new ArrayList<Integer>();
        idList = new ArrayList<String>();
        idList_dz = new ArrayList<String>();
        tel_List = new ArrayList<String>();
        idList_shr = new ArrayList<String>();
        idList_shdh = new ArrayList<String>();
        idList_ss = new ArrayList<String>();
        idList_qx = new ArrayList<String>();
        checkBoxList = new ArrayList<CheckBox>();
        // 异步加载数据
        getInfoCust();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_moveAll_add://判断电话是否存在
                do_save=1;
//                updataInfos();
                Intent intent_add = new Intent(context, PsHaveCityActivity.class);
                intent_add.putExtra("DO", "1");
                intent_add.putExtra("SSZT_ps", sszt_befor);
                intent_add.putExtra("DABH_ps", dabh_befor);
                intent_add.putExtra("SIZE",""+custList.size() );
                startActivity(intent_add);
//                finish();
                break;
            case R.id.ib_move_account:
                Intent intent = new Intent(context, CondicionInfoActivity.class);
                intent.putExtra("flag", "1");
                startActivityForResult(intent, 1);
                break;
            case R.id.ib_move_sfmr:
                showPopupMenu(mr_xl);
                break;
            case R.id.btn_moveAll_reset:
                //修改
                if (0 < idList.size() && idList.size() <= 1) {
                    //回调数据
                    do_save=2;
//                    updataInfos();
                    Intent intent_set = new Intent(context, PsHaveCityActivity.class);
                    intent_set.putExtra("DO", ""+do_save);
                    intent_set.putExtra("SIZE",""+custList.size() );
                    intent_set.putExtra("ZT", extra_zt);
                    intent_set.putExtra("DABH", extra_dabh);
                    intent_set.putExtra("ITM",extra_itm);
                    intent_set.putExtra("SHR",extra_shr);
                    intent_set.putExtra("DH",extra_dh);
                    intent_set.putExtra("SS",extra_ss);
                    intent_set.putExtra("QX",extra_qx);
                    intent_set.putExtra("ANDRESS",extra_adress);
                    intent_set.putExtra("USERID",extra_userid);
                    intent_set.putExtra("DEF",extra_def);
                    startActivity(intent_set);
//                    finish();
                }else {
                    Toast.makeText(this.context, "请选择一条数据", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_moveAll_del:
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

                                            Log.e("LiNing", "--------删除的id==="
                                                    + sub_id);
                                            delCust();
                                        }
                                    }).setNegativeButton("否", null).show();
                }else {
                    Toast.makeText(context, "请选择数据", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_moveAll_info:
                //选定
                if (0 < idList.size() && idList.size() <= 1) {
                    Intent localIntent = getIntent();
                    localIntent.putExtra("ITM_ID", sub_id);
                    localIntent.putExtra("PSXX_DZ", sub_dz);
                    localIntent.putExtra("PSXX_SHR", sub_shr);
                    localIntent.putExtra("PSXX_SHDH", sub_shdh);
                    localIntent.putExtra("PSXX_SS", sub_ss);
                    localIntent.putExtra("PSXX_QX", sub_qx);
//                    String stritem = new Gson().toJson(this.idList);
//                    localIntent.putExtra("PSXX_SHR", stritem);
                    setResult(1, localIntent);
                    finish();

                }else {
                    Toast.makeText(this.context, "请选择一条数据", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void updataInfos() {
        View view = getLayoutInflater()
                .inflate(R.layout.activity_mode_info, null);
        head = (TextView) view.findViewById(R.id.all_head);
        head.setText("配送信息");
        zt = (TextView) view.findViewById(R.id.et_move_zt);
        yhbh = (TextView) view.findViewById(R.id.et_move_yhbh);
        sfmr = (TextView) view.findViewById(R.id.tv_move_ismr);
        dacard = (TextView) view.findViewById(R.id.tv_move_bh);
        dzbh= (TextView) view.findViewById(R.id.et_move_dzbh);
        int i = custList.size() + 1;
        dzbh.setText(""+i);
        shr= (EditText) view.findViewById(R.id.et_move_username);
        lxdh= (EditText) view.findViewById(R.id.et_move_phone);
        ss= (TextView) view.findViewById(R.id.et_move_ss);
        qx= (TextView) view.findViewById(R.id.et_move_qx);
        ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityConfig cityConfig = new CityConfig.Builder().title("选择城市")//标题
                        .build();

                mCityPickerView.setConfig(cityConfig);
                mCityPickerView.setOnCityItemClickListener(new OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("选择的结果：\n");
                        if (province != null) {
                            sb.append(province.getName() + " " + province.getId() + "\n");
                        }

                        if (city != null) {
                            sb.append(city.getName() + " " + city.getId() + ("\n"));
                        }

                        if (district != null) {
                            sb.append(district.getName() + " " + district.getId() + ("\n"));
                        }

                        Log.e("LiNing","+=========="+"" + sb.toString());
                        ss.setText(province.getName());
                        qx.setText(city.getName());

                    }

                    @Override
                    public void onCancel() {
                        ToastUtils.showLongToast(context, "已取消");
                    }
                });
                mCityPickerView.showCityPicker();
            }
        });

        xxdz= (TextView) view.findViewById(R.id.et_move_adress);
        xxdz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //textview点击失去焦点获取地图
//                Intent intent1 = new Intent(context, SearchMapActivity.class);
                Intent intent1 = new Intent(context, MapInfosActivity.class);
                intent1.putExtra("SF", ss.getText().toString());
                intent1.putExtra("QX", qx.getText().toString());
                startActivityForResult(intent1, 2);
            }
        });
        zt_xl= (ImageButton) view.findViewById(R.id.ib_move_account);
        mr_xl= (ImageButton) view.findViewById(R.id.ib_move_sfmr);
        if(do_save==1){
            zt.setText(sszt_befor);
            dacard.setText(dabh_befor);
            yhbh.setText(user_Id);
        }else if(do_save==2){
            zt_xl.setEnabled(false);
            dzbh.setEnabled(false);
            zt.setText(extra_zt);
            dacard.setText(extra_dabh);
            dzbh.setText(extra_itm);
            shr.setText(extra_shr);
            lxdh.setText(extra_dh);
            ss.setText(extra_ss);
            qx.setText(extra_qx);
            xxdz.setText(extra_adress);
            yhbh.setText(extra_userid);
            sfmr.setText(extra_def);
        }
        zt_xl.setOnClickListener(this);
        mr_xl.setOnClickListener(this);

        view.findViewById(R.id.imageButton1).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
        view.findViewById(R.id.btn_move_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(do_save==1){
//                    if (tel_List.contains(lxdh.getText().toString())) {
//                        Toast.makeText(context, "该电话已存在", Toast.LENGTH_LONG).show();
//                    }else{
//
//                        postPsxx();
//                    }
                    postPsxx();
                    //此处修改。。。（只能点击一次）
                }
                if(do_save==2){
                    postPsxx();
                }
            }
        });

        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setCancelable(false);
        alertDialog.setView(view);
        alertDialog.show();
    }

    private void delCust() {
        if(idList!=null&&idList.size()>0){
            for (int i=0;i<idList.size();i++) {
                String del_id = idList.get(i);
                OkHttpClient client = new OkHttpClient();
                FormBody body = new FormBody.Builder()
                        .add("Cust_Acc", sszt_befor)
                        .add("Cust_No", dabh_befor)
                        .add("iTM", del_id)
                        .build();
                Log.e("LiNing", "删除结果====" + del_id);
                client.newCall(
                        new Request.Builder().addHeader("cookie", session).url(ps_del)
                                .post(body).build()).enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String str = response.body().string();
                        Log.e("LiNing", "删除结果====" + str);
                        final JsonRootBean localJsonRootBean = (JsonRootBean) new Gson()
                                .fromJson(str, JsonRootBean.class);
                        if (localJsonRootBean != null) {
                            MoveAllInfoActivity.this.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    boolean rlo = localJsonRootBean.getRLO();
                                    if (rlo == true) {
                                        Toast.makeText(MoveAllInfoActivity.this,
                                                "删除结果", Toast.LENGTH_SHORT).show();
                                        getInfoCust();
                                    } else if (rlo == false) {
                                        Toast.makeText(MoveAllInfoActivity.this,
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
            }        }

    }

    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.sfmr, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.check1
                        || menuItem.getItemId() == R.id.check2
                        ) {
                    menuItem.setChecked(!menuItem.isChecked());
                    sfmr.setText(menuItem.getTitle());
                    Log.e("LiNing", "----" + sfmr );

                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void postPsxx() {
        if (do_save == 1) {
            url_do = ps_add;

        } else if (do_save == 2) {
            url_do = ps_set;
        }
        Log.e("LiNing", "---------" + tel_List);
        String add_lxdh = lxdh.getText().toString();
//        if (tel_List.contains(add_lxdh)) {
//            Toast.makeText(context, "该电话已存在", Toast.LENGTH_LONG).show();
//        } else {
        String add_zt = zt.getText().toString();
        String add_dacard = dacard.getText().toString();
        String add_dzbh = dzbh.getText().toString();
        String add_shr = shr.getText().toString();
        String add_ss = ss.getText().toString();
        String add_qx = qx.getText().toString();
        String add_xxdz = xxdz.getText().toString();
        String add_yhbh = yhbh.getText().toString();
        String add_sfmr = sfmr.getText().toString();
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("Cust_Acc", add_zt)
                .add("Cust_No", add_dacard)
                .add("iTM", add_dzbh)
                .add("Con_Per", add_shr)
                .add("Con_Tel", add_lxdh)
                .add("Con_Crt", add_ss)
                .add("Con_Spa", add_qx)
                .add("Con_Add", add_xxdz)
                .add("User_ID", add_yhbh)
                .add("Def", add_sfmr)
                .build();
        Log.e("LiNing", "添加结果====" + add_zt + "---" + add_dacard + "---" + add_dzbh + "---" + add_shr + "---" + add_lxdh + "---"
                + add_ss + "---" + add_qx + "---" + add_xxdz + "---" + add_yhbh + "---" + add_sfmr);
        client.newCall(
                new Request.Builder().addHeader("cookie", session).url(url_do)
                        .post(body).build()).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                String str = response.body().string();
                Log.e("LiNing", "添加结果====" + str);
                final JsonRootBean localJsonRootBean = (JsonRootBean) new Gson()
                        .fromJson(str, JsonRootBean.class);
                if (localJsonRootBean != null) {
                    MoveAllInfoActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            boolean rlo = localJsonRootBean.getRLO();
                            if (rlo == true) {//新增，修改合并
                                Toast.makeText(MoveAllInfoActivity.this,
                                        "保存成功", Toast.LENGTH_SHORT).show();
                                if (alertDialog.isShowing()) {
                                    alertDialog.dismiss();
                                    getInfoCust();
                                }
                            } else if (rlo == false) {
                                Toast.makeText(MoveAllInfoActivity.this,
                                        "保存失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call arg0, IOException arg1) {

            }
        });
//    }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode == 1) {
                    String str_id = data.getStringExtra("condition_db");
                    String str_name = data.getStringExtra("condition_name");
                    zt.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + str_name);
                }
                break;
            case 2:
                if (resultCode == 1) {
                    String str_andress = data.getStringExtra("ANDRESS");
                    xxdz.setText(str_andress);
                    Log.e("LiNing", "提交的id====" + str_andress);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    //适配器（配送信息列表）
    public class CstAdapter extends BaseAdapter{
        int id_row_layout;
        LayoutInflater mInflater;
        List<CustPsxxInfos.CustConInfList> cst_infos;

        public CstAdapter(int move_infos_head, List<CustPsxxInfos.CustConInfList> custList, Context context) {
            this.id_row_layout=move_infos_head;
            this.mInflater=LayoutInflater.from(context);
            this.cst_infos=custList;
        }

        @Override
        public int getCount() {
            return cst_infos.size();
        }

        @Override
        public Object getItem(int position) {
            return cst_infos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView==null){
                synchronized (MoveAllInfoActivity.this) {

                    convertView=mInflater.inflate(id_row_layout,null);
                    holder=new ViewHolder();
                    MyHScrollView scrollView1 = (MyHScrollView) convertView
                            .findViewById(R.id.horizontalScrollView1);
                    holder.scrollView = scrollView1;
                    holder.checkbox = (CheckBox) convertView
                            .findViewById(R.id.psxx_listDeleteCheckBox);
                    holder.checkbox.setVisibility(View.VISIBLE);
                    holder.layouts = (RelativeLayout) convertView
                            .findViewById(R.id.rl_item);
                    holder.itm_hp = (TextView) convertView
                            .findViewById(R.id.textView1_dzbh);
                    holder.dabh_hp = (TextView) convertView
                            .findViewById(R.id.textView2_dabh);
                    holder.sszt_hp = (TextView) convertView
                            .findViewById(R.id.textView2_sszt);
                    holder.shr_hp = (TextView) convertView
                            .findViewById(R.id.textView2_shr);
                    holder.lxdh_hp = (TextView) convertView
                            .findViewById(R.id.textView3_lxdh);
                    holder.xxdz_hp = (TextView) convertView
                            .findViewById(R.id.textView5_adress);
                    holder.xxss_hp = (TextView) convertView
                            .findViewById(R.id.textView4_szss);
                    holder.xxqx_hp = (TextView) convertView
                            .findViewById(R.id.textView4_szqx);
                    holder.yhbh_hp = (TextView) convertView
                            .findViewById(R.id.textView5_yhbh);
                    holder.sfmr_hp = (TextView) convertView
                            .findViewById(R.id.textView6_sfmr);
                    MyHScrollView headSrcrollView = (MyHScrollView) mHead
                            .findViewById(R.id.horizontalScrollView1);
                    headSrcrollView
                            .AddOnScrollChangedListener(new OnScrollChangedListenerImp(
                                    scrollView1));
                    // 将item中的checkbox放到checkBoxList中
                    checkBoxList.add(holder.checkbox);
                    convertView.setTag(holder);
                }
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            CustPsxxInfos.CustConInfList custConInfList = cst_infos.get(position);
            holder.itm_hp.setText(custConInfList.getITM());
            holder.dabh_hp.setText(custConInfList.getCust_No());
            holder.sszt_hp.setText(custConInfList.getCust_Acc());
            holder.shr_hp.setText(custConInfList.getCon_Per());
            holder.lxdh_hp.setText(custConInfList.getCon_Tel());
            holder.xxdz_hp.setText(custConInfList.getCon_Add());
            holder.xxss_hp.setText(custConInfList.getCon_Crt());
            holder.xxqx_hp.setText(custConInfList.getCon_Spa());
            holder.yhbh_hp.setText(custConInfList.getUser_ID());
            holder.sfmr_hp.setText(""+custConInfList.getDef());
            holder.checkbox.setOnCheckedChangeListener(new CheckBoxListener(
                    position));
            tel_List.add(custConInfList.getCon_Tel());
            Log.e("LiNing","---------"+tel_List);
            return convertView;
        }
        class ViewHolder {
            HorizontalScrollView scrollView;
            public RelativeLayout layouts;
            CheckBox checkbox;
            public TextView itm_hp;
            public TextView sszt_hp;
            public TextView dabh_hp;
            public TextView shr_hp;
            public TextView lxdh_hp;
            public TextView xxdz_hp;
            public TextView xxss_hp;
            public TextView xxqx_hp;
            public TextView yhbh_hp;
            public TextView sfmr_hp;
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
        };
    }
    //滑动（水平）
    class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {
        ListViewAndHeadViewTouchLinstener() {
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ((HorizontalScrollView) MoveAllInfoActivity.this.mHead
                    .findViewById(R.id.horizontalScrollView1))
                    .onTouchEvent(event);
            return false;
        }

    }
    private void getInfoCust() {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("Cust_Acc", sszt_befor)
                .add("Cust_No", dabh_befor)
                .build();
//        Log.e("LiNing", "添加结果====" + add_zt + "---" + add_dacard);
        client.newCall(
                new Request.Builder().addHeader("cookie", session).url(ps_query)
                        .post(body).build()).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                String str = response.body().string();
                Log.e("LiNing", "查询结果====" + str);
                // 解析包含date的数据必须添加此代码(InputStream型)
                Gson gson = new GsonBuilder().setDateFormat(
                        "yyyy-MM-dd HH:mm:ss").create();
                final CustPsxxInfos custBean = (CustPsxxInfos) gson
                        .fromJson(str, CustPsxxInfos.class);
                if (custBean != null) {
                    MoveAllInfoActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            custList = custBean.getCustConInfList();
                            Log.e("LiNing", "数据====" + custList);
                            if(custList!=null){

                                cst_adapter=new CstAdapter(R.layout.move_infos_head,custList,context);
                                mListView1.setAdapter(cst_adapter);
                                cst_adapter.notifyDataSetChanged();
                                checkedIndexList.clear();
                                idList.clear();
                                idList_dz.clear();
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
    int checkFalg;
    public class CheckBoxListener implements CompoundButton.OnCheckedChangeListener {
        int positions;
        private String itm,adress,shr_bjd,shdh_bjd,ss_bjd,qx_bjd;
    public CheckBoxListener(int position) {
        this.positions = position;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView,
                                 boolean isChecked) {
        if (isChecked) {
             checkFalg = 1;
            int iln = positions;
             itm = custList.get(positions).getITM();
            adress= custList.get(positions).getCon_Add();
            //报价单应用
            shr_bjd= custList.get(positions).getCon_Per();
            shdh_bjd= custList.get(positions).getCon_Tel();
            ss_bjd= custList.get(positions).getCon_Crt();
            qx_bjd= custList.get(positions).getCon_Spa();
            //——————
            checkedIndexList.add(positions);
            idList.add(itm);
            idList_dz.add(adress);
            idList_shr.add(shr_bjd);
            idList_shdh.add(shdh_bjd);
            idList_ss.add(ss_bjd);
            idList_qx.add(qx_bjd);
            Log.e("LiNing", "--------集合" + checkedIndexList);
            Log.e("LiNing", "--------id集合" + idList+"---"+idList_dz);

            String zts_str = "";
            for (String querys_db : idList) {
                zts_str += querys_db + ",";
            }
             sub_id = zts_str.substring(0, zts_str.length() - 1);
            Log.e("LiNing", "--------sub_id" + sub_id);
            String dzs_str = "";
            for (String querys_db : idList_dz) {
                dzs_str += querys_db + ",";
            }
             sub_dz = dzs_str.substring(0, dzs_str.length() - 1);
            Log.e("LiNing", "--------sub_dz" + sub_dz);
            // ==================================
            String shrs_str = "";
            for (String querys_db : idList_shr) {
                shrs_str += querys_db + ",";
            }
             sub_shr = shrs_str.substring(0, shrs_str.length() - 1);
            Log.e("LiNing", "--------sub_dz" + sub_dz);
            // ==================================
            String shdhs_str = "";
            for (String querys_db : idList_shdh) {
                shdhs_str += querys_db + ",";
            }
             sub_shdh = shdhs_str.substring(0, shdhs_str.length() - 1);
            Log.e("LiNing", "--------sub_dz" + sub_dz);
            // ==================================
            String sss_str = "";
            for (String querys_db : idList_ss) {
                sss_str += querys_db + ",";
            }
             sub_ss = sss_str.substring(0, sss_str.length() - 1);
            Log.e("LiNing", "--------sub_dz" + sub_dz);
            // ==================================
            String qx_str = "";
            for (String querys_db : idList_qx) {
                qx_str += querys_db + ",";
            }
             sub_qx = qx_str.substring(0, qx_str.length() - 1);
            Log.e("LiNing", "--------sub_dz" + sub_dz);
            // ==================================
            //修改数据
            CustPsxxInfos.CustConInfList hd_custinfos = custList.get(positions);

             extra_zt = hd_custinfos.getCust_Acc().toString();
             extra_dabh = hd_custinfos.getCust_No().toString();
             extra_itm = hd_custinfos.getITM().toString();
             extra_shr = hd_custinfos.getCon_Per().toString();
             extra_dh = hd_custinfos.getCon_Tel().toString();
             extra_ss = hd_custinfos.getCon_Crt().toString();
             extra_qx = hd_custinfos.getCon_Spa().toString();
             extra_adress = hd_custinfos.getCon_Add().toString();
             extra_userid = hd_custinfos.getUser_ID().toString();
            boolean def = hd_custinfos.getDef();
             extra_def=""+def;


        } else {
            checkFalg = 0;
            checkedIndexList.remove((Integer) positions);
            Log.e("LiNing", "--------删除集合" + checkedIndexList);
            idList.remove(itm);
            idList_dz.remove(adress);
            idList_shr.remove(shr_bjd);
            idList_shdh.remove(shdh_bjd);
            idList_ss.remove(ss_bjd);
            idList_qx.remove(qx_bjd);
            Log.e("LiNing", "--------删除id集合" + idList);
            Log.e("LiNing", "--------删除id集合" + idList_dz);
            if (idList != null && idList.size() > 0) {
//                isnull = 1;
                String zts_str = "";
                for (String querys_db : idList) {
                    zts_str += querys_db + ",";
                }
                sub_id = zts_str.substring(0, zts_str.length() - 1);
                Log.e("LiNing", "--------sub_id" + sub_id);
            } else {
                Toast.makeText(MoveAllInfoActivity.this, "未选中数据", Toast.LENGTH_LONG).show();
            }

        }
    }

}
    public void allback(View v) {
        finish();
    }
    public void flash(View v) {
        getInfoCust();
    }
}
