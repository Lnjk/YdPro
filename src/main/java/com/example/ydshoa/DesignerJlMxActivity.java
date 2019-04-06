package com.example.ydshoa;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.DesignAllInfos;
import com.example.bean.JfMxBean;
import com.example.bean.URLS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DesignerJlMxActivity extends Activity implements View.OnClickListener {
    private Context context;
    private SharedPreferences sp;
    private String session,db_zt,user_Id, user_yh, user_Name, jf_id_hd,date_dd,mxtj,str_name,vip_no_cust;
    private TextView head, jlmx_sszt, jlmx_sjsdh, jlmx_jfdh, jlmx_jflx, jlmx_jfrqks,jlmx_jfrqjs, jlmx_jabz, jlmx_zdr, jlmx_shr, jlmx_lydh,jlmx_sfzh;
    private Button jlmx_add, jlmx_del, jlmx_reset, jlmx_query, jlmx_save, jlmx_sh;
    private EditText  jlmx_szmc, jlmx_jfbd, jlmx_jfms;
    private ImageButton jlmx_sszt_ib, jlmx_sjsdh_ib, jlmx_jabz_ib, jlmx_jalx_ib;
    String s_zh, clientRows, comitmx_zt, comitmx_time, comitmx_time_zb, comitmx_dh, comitmx_sjsdh, comitmx_sfzh, comitmx_jflx, comitmx_szmc, comitmx_jabz, comitmx_jabz_quy,
            comitmx_zdr, comitmx_jfbd, comitmx_lydh, comitmx_jfms,jf_fh_jflx,jf_fh_sjsdh;
    List<JfMxBean.Data> mf_monList;
    List<JfMxBean.QueryResult> mf_monList_tj;
    private ListView lv_query_jfmx;
    JfsQtyAdapter qtyAdapter;
    JfsQtyAdapterTj qtyAdapter_tj;
    //接口
    String jfjl_get_mx = URLS.design_jfdjquery_mx;
    String jfjl_get_tj = URLS.design_jfdj_query;
    String url_infos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_designer_jl_mx);

        context = DesignerJlMxActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        user_Id = sp.getString("USER_ID", "");
        user_yh = sp.getString("MR_YH", "");
        user_Name = sp.getString("USER_NAME", "");
        session = sp.getString("SESSION", "");
         mxtj = getIntent().getStringExtra("MXTJ");
        getNowTime();
        initView();//初始化
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
        if(mxtj.equals("1")){
            head.setText("会员明细表");
        }
        if(mxtj.equals("2")){
            head.setText("会员统计表");
        }

        lv_query_jfmx = (ListView)findViewById(R.id.lv_designjlmx_header);
        jlmx_sszt = (TextView) findViewById(R.id.et_designjlmx_zt);
        jlmx_sszt.setText(db_zt);
        jlmx_sjsdh = (TextView) findViewById(R.id.et_designjlmx_sjsdh);
        jlmx_jfdh = (EditText) findViewById(R.id.tv_jfcard);
        jlmx_jflx = (EditText) findViewById(R.id.et_designjlmx_jflx);
        jlmx_jfrqks = (TextView) findViewById(R.id.et_designjlmx_jfrqks);
        jlmx_jfrqjs = (TextView) findViewById(R.id.et_designjlmx_jfrqjs);
        jlmx_jfrqks.setText(date_dd);
        jlmx_jfrqjs.setText(date_dd);
        jlmx_jfrqks.setOnClickListener(new View.OnClickListener() {
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
                                jlmx_jfrqks.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        jlmx_jfrqjs.setOnClickListener(new View.OnClickListener() {
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
                                jlmx_jfrqjs.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        jlmx_jabz = (TextView) findViewById(R.id.et_designjlmx_jabz);
        jlmx_zdr = (EditText) findViewById(R.id.et_designjlmx_zdr);
        jlmx_shr = (EditText) findViewById(R.id.et_designjlmx_shr);
        jlmx_lydh = (EditText) findViewById(R.id.et_designjlmx_lydh);

        jlmx_sfzh = (EditText) findViewById(R.id.et_designjlmx_usercard);
        jlmx_szmc = (EditText) findViewById(R.id.et_designjlmx_szmc);
        jlmx_jfms = (EditText) findViewById(R.id.et_designjlmx_jfms);

        jlmx_sszt_ib = (ImageButton) findViewById(R.id.ib_designjlmx_account);
        jlmx_sjsdh_ib = (ImageButton) findViewById(R.id.ib_designjlmx_sjsdh);
        jlmx_jabz_ib = (ImageButton) findViewById(R.id.ib_designjlmx_jabz);
        jlmx_jalx_ib = (ImageButton) findViewById(R.id.ib_designjlmx_jflx);

        jlmx_query = (Button) findViewById(R.id.btn_designjlmx_query);
        jlmx_query.setOnClickListener(this);
        jlmx_sszt_ib.setOnClickListener(this);
        jlmx_sjsdh_ib.setOnClickListener(this);
        jlmx_jabz_ib.setOnClickListener(this);
        jlmx_jalx_ib.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_designjlmx_account:
                Intent intent = new Intent(context, CondicionInfoActivity.class);
                intent.putExtra("flag", "1");
                startActivityForResult(intent, 1);
                break;
            case R.id.ib_designjlmx_sjsdh:
//                设计师
                Intent intent_vip = new Intent(context, QueryDesigActivity.class);
                intent_vip.putExtra("ZT_VIP", jlmx_sszt.getText().toString());
                intent_vip.putExtra("CUST_DO", "2");
                startActivityForResult(intent_vip, 2);
                break;
            case R.id.ib_designjlmx_jflx:
                if (jlmx_sszt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent_jflx = new Intent(context, JfClassActivity.class);
                    intent_jflx.putExtra("ZT_VIP", jlmx_sszt.getText().toString());
                    startActivityForResult(intent_jflx, 3);
                }
                break;
            case R.id.ib_designjlmx_jabz:
                showPopupMenu(jlmx_jabz_ib);
                break;
            
            case R.id.btn_designjlmx_query:
                if (jlmx_sszt.getText().toString().equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {
                    if(jlmx_jfrqks.getText().toString().equals("")||jlmx_jfrqjs.getText().toString().equals("")){
                        Toast.makeText(context, "请填写日期", Toast.LENGTH_LONG).show();
                    }else{

                        get_info_comint();//提交查询条件
                        get_alljfs();//请求数据
                    }
                }
                break;
          
            default:
                break;
        }
    }
    private void get_alljfs() {
        Log.e("LiNing", "提交数据===" + comitmx_zt + comitmx_time + clientRows);

        if (lv_query_jfmx.getCount() <= 0) {
            clientRows = "0";
        }
        if(mxtj.equals("1")){
            url_infos=jfjl_get_mx;
        }
            if(mxtj.equals("2")){
                url_infos=jfjl_get_tj;
        }
        Log.e("LiNing", "提交数据===" + clientRows);
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("db_Id", comitmx_zt)
                .add("showRow", "50")
                .add("clientRows", clientRows)
//                .add("clientRows","0")
                .add("vip_NO", comitmx_sjsdh)
                .add("card_Num", comitmx_sfzh)
                .add("vp_No", comitmx_dh)
                .add("points_Type", comitmx_jflx)
                .add("item_Name", comitmx_szmc)
                .add("source_No", comitmx_lydh)
                .add("points_Date", comitmx_time)
//                .add("dep",comit_dep)//有默认值
                .add("cls_Id", comitmx_jabz_quy)
                .add("rem", comitmx_jfms)
                .add("usr_No", user_yh)
                .add("chk_No", user_yh)
                .build();
        Log.e("LiNing", "提交数据=1==" + comitmx_zt + comitmx_time + clientRows);
        client.newCall(new Request.Builder().addHeader("cookie", session).post(body)
                .url(url_infos).build()).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e("LiNing", "所有收款单===new" + str);
                // 解析包含date的数据必须添加此代码(InputStream型)
                Gson gson = new GsonBuilder().setDateFormat(
                        "yyyy-MM-dd HH:mm:ss").create();
                final JfMxBean jf_all = gson.fromJson(str,
                        JfMxBean.class);
                int sumShowRow = jf_all.getSumShowRow();
                s_zh = String.valueOf(sumShowRow);
                clientRows = s_zh;
                if (jf_all != null) {
                    DesignerJlMxActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            if(mxtj.equals("1")){

                                mf_monList = jf_all.getData();
                                if (mf_monList != null && mf_monList.size() > 0) {


                                    qtyAdapter = new JfsQtyAdapter(R.layout.desigh_jfjl_mx, mf_monList, context);
                                    lv_query_jfmx.setAdapter(qtyAdapter);
                                    qtyAdapter.notifyDataSetChanged();
                                }
                            }
                            if(mxtj.equals("2")){
                                mf_monList_tj = jf_all.getQueryResult();
                                if (mf_monList_tj != null && mf_monList_tj.size() > 0) {


                                    qtyAdapter_tj = new JfsQtyAdapterTj(R.layout.desigh_jfjl_mx, mf_monList_tj, context);
                                    lv_query_jfmx.setAdapter(qtyAdapter_tj);
                                    qtyAdapter_tj.notifyDataSetChanged();
                                }
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
    public class JfsQtyAdapter extends BaseAdapter {
        int id_row_layout;
        LayoutInflater mInflater;
        List<JfMxBean.Data> skdqty_infos;
        //item高亮显示
        private int selectItem = -1;

        public void setSelectItem(int selectItem) {
            this.selectItem = selectItem;
        }

        public JfsQtyAdapter(int desigh_jfjl_mx, List<JfMxBean.Data> mf_monList, Context context) {
            this.id_row_layout = desigh_jfjl_mx;
            this.skdqty_infos = mf_monList;
            this.mInflater = LayoutInflater.from(context);
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
            if (convertView == null) {
                convertView = mInflater.inflate(id_row_layout, null);
                holder = new ViewHolder();
                holder.jf_qty_jfmx_xh = (TextView) convertView.findViewById(R.id.designjlmx_xh_item);
                holder.jf_qty_jfmx_zt = (TextView) convertView.findViewById(R.id.tv_jlmx_zt_item);
                holder.jf_qty_jfmx_sjsdh = (TextView) convertView.findViewById(R.id.tv_jlmx_sjsdh_item);
                holder.jf_qty_zjfmx_sfzh = (TextView) convertView.findViewById(R.id.tv_jlmx_sfzh_item);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            JfMxBean.Data mf_monList_all = skdqty_infos.get(position);
            int i = position + 1;
            holder.jf_qty_jfmx_xh.setText(""+i);
            holder.jf_qty_jfmx_zt.setText(mf_monList_all.getDepco_Vip().getDb_Id().toString());
            holder.jf_qty_jfmx_sjsdh.setText(mf_monList_all.getDepco_Vip().getVip_NO().toString());
            holder.jf_qty_zjfmx_sfzh.setText(mf_monList_all.getDepco_Vip().getCard_Num().toString());

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
            public TextView jf_qty_jfmx_xh;
            public TextView jf_qty_jfmx_zt;
            public TextView jf_qty_jfmx_sjsdh;
            public TextView jf_qty_zjfmx_sfzh;
        }
    }
    public class JfsQtyAdapterTj extends BaseAdapter {
        int id_row_layout;
        LayoutInflater mInflater;
        List<JfMxBean.QueryResult> skdqty_infos;
        //item高亮显示
        private int selectItem = -1;

        public JfsQtyAdapterTj(int desigh_jfjl_mx, List<JfMxBean.QueryResult> mf_monList_tj, Context context) {
            this.id_row_layout = desigh_jfjl_mx;
            this.skdqty_infos = mf_monList_tj;
            this.mInflater = LayoutInflater.from(context);
        }

        public void setSelectItem(int selectItem) {
            this.selectItem = selectItem;
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
            if (convertView == null) {
                convertView = mInflater.inflate(id_row_layout, null);
                holder = new ViewHolder();
                holder.jf_qty_jfmx_xh = (TextView) convertView.findViewById(R.id.designjlmx_xh_item);
                holder.jf_qty_jfmx_zt = (TextView) convertView.findViewById(R.id.tv_jlmx_zt_item);
                holder.jf_qty_jfmx_sjsdh = (TextView) convertView.findViewById(R.id.tv_jlmx_sjsdh_item);
                holder.jf_qty_zjfmx_sfzh = (TextView) convertView.findViewById(R.id.tv_jlmx_sfzh_item);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            JfMxBean.QueryResult mf_monList_all_tj = mf_monList_tj.get(position);
            int i = position + 1;
            holder.jf_qty_jfmx_xh.setText(""+i);
            holder.jf_qty_jfmx_zt.setText(mf_monList_all_tj.getDepco_Vip().getDb_Id().toString());
            holder.jf_qty_jfmx_sjsdh.setText(mf_monList_all_tj.getDepco_Vip().getVip_NO().toString());
            holder.jf_qty_zjfmx_sfzh.setText(mf_monList_all_tj.getDepco_Vip().getCard_Num().toString());

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
            public TextView jf_qty_jfmx_xh;
            public TextView jf_qty_jfmx_zt;
            public TextView jf_qty_jfmx_sjsdh;
            public TextView jf_qty_zjfmx_sfzh;
        }
    }
    private void get_info_comint() {
        Log.e("LiNing", "初始化值====" + clientRows);
        if (lv_query_jfmx.getCount() < 0) {

            clientRows = "0";
        }

        comitmx_zt = jlmx_sszt.getText().toString();
        if (comitmx_zt.equals("")) {
            Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
        }

        if (!jlmx_jfrqks.getText().toString().equals("") && !jlmx_jfrqjs.getText().toString().equals("")) {
            comitmx_time = jlmx_jfrqks.getText().toString() + ";" + jlmx_jfrqjs.getText().toString();
        } else {
            Toast.makeText(context, "请填写时间", Toast.LENGTH_LONG).show();

        }
        comitmx_dh = jlmx_jfdh.getText().toString();
        if (comitmx_dh.equals("") || comitmx_dh == null) {
            comitmx_dh = "null";
        }
        comitmx_sjsdh = jlmx_sjsdh.getText().toString();
        if (comitmx_sjsdh.equals("")) {
            comitmx_sjsdh = "null";
        }
        comitmx_sfzh = jlmx_sfzh.getText().toString();
        if (comitmx_sfzh.equals("")) {
            comitmx_sfzh = "null";
        }
        comitmx_jflx = jlmx_jflx.getText().toString();
        if (comitmx_jflx.equals("")) {
            comitmx_jflx = "null";
        }
        comitmx_szmc = jlmx_szmc.getText().toString();
        if (comitmx_szmc.equals("")) {
            comitmx_szmc = "null";
        }
        comitmx_jabz = jlmx_jabz.getText().toString();
        if (comitmx_jabz.equals("")) {
            comitmx_jabz_quy = "null";
        } else {
            if (comitmx_jabz.equals("是")) {
                comitmx_jabz_quy = "T";
            }
            if (comitmx_jabz.equals("否")) {
                comitmx_jabz_quy = "F";
            }
        }
        comitmx_jfms = jlmx_jfms.getText().toString();
        if (comitmx_jfms.equals("")) {
            comitmx_jfms = "null";

        }
        comitmx_lydh = jlmx_lydh.getText().toString();
        if (comitmx_lydh.equals("") || comitmx_lydh == null) {
            comitmx_lydh = "null";
        }
        comitmx_zdr = jlmx_zdr.getText().toString();
        if (comitmx_zdr.equals("")) {
            comitmx_zdr = "null";
        }

        Log.e("LiNing", "获取数据===" + comitmx_dh + comitmx_lydh);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    String str_id = data.getStringExtra("condition_db");
                    str_name = data.getStringExtra("condition_name");
                    jlmx_sszt.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + str_name);
                }
                break;
            case 2:
                if (resultCode == 1) {
                    DesignAllInfos.VipList vipList_hd = (DesignAllInfos.VipList) data.getSerializableExtra("VIP_INFOS_ALL");
                    Log.e("LiNing", "提交的id====" + data.getSerializableExtra("VIP_INFOS_ALL"));
                    if (vipList_hd.getVip_Name() != null) {
                        jlmx_sjsdh.setText(vipList_hd.getVip_Name());
                    } else {
                        jlmx_sjsdh.setText("null");
                    }
                    if (vipList_hd.getDb_Id() != null) {
                        Log.e("LiNing", "提交的id====" + vipList_hd.getDb_Id().toString());
                        jlmx_sszt.setText(vipList_hd.getDb_Id().toString());
                    } else {
                        jlmx_sjsdh.setText(db_zt);
                    }
                    if (vipList_hd.getCard_Num() != null) {
                        jlmx_sfzh.setText(vipList_hd.getCard_Num().toString());
                    } else {
                        jlmx_sfzh.setText("");
                    }

                    if (vipList_hd.getVip_NO() != null) {
                        vip_no_cust = vipList_hd.getVip_NO().toString();
                        Log.e("LiNing", "提交的vip_no_cust====" + vip_no_cust);
                    }
                }
                break;
            case 3:
                if (resultCode == 1) {
                    jf_id_hd = data.getStringExtra("jf_ID");
                    String vip_name_hd = data.getStringExtra("jf_NAME");
                    jlmx_jflx.setText(jf_id_hd);
                    Log.e("LiNing", "提交的id====" + jf_id_hd + vip_name_hd);
                }
                break;
            default:
                break;
        }
    }
    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.sfhz, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.check1
                        || menuItem.getItemId() == R.id.check2
                        ) {
                    menuItem.setChecked(!menuItem.isChecked());
                    jlmx_jabz.setText(menuItem.getTitle());
                    Log.e("LiNing", "----" + jlmx_jabz);

                }
                return true;
            }
        });
        popupMenu.show();
    }
    public void allback(View v) {
        finish();
    }
}
