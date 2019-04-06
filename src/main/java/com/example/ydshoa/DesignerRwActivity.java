package com.example.ydshoa;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.DesignAllInfos;
import com.example.bean.JsonRootBean;
import com.example.bean.PriceNumID;
import com.example.bean.URLS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

public class DesignerRwActivity extends Activity implements View.OnClickListener {
    private Context context;
    private SharedPreferences sp;
    private String session, db_zt,user_Id,user_yh,user_Name;
    private TextView head,rw_sszt,rw_rwrq,rw_sxrq,rw_dqrq,rw_sjsdh,rw_rwdh,rw_zdr,rw_shr,rw_dqzky;
    private Button rw_add,rw_del,rw_reset,rw_query,rw_save,rw_sh;
    private EditText rw_sfzh,rw_mc,rw_rwjf;
    private ImageButton rw_sszt_ib,rw_sjsbh_ib,rw_dqzky_ib;
    private String str_name,date_dd;
    int do_design_rw;
    String zt_comit_rw,jfdh_comit_rw,rwdh_comit_rw,sfzh_comit_rw,sjsbh_comit_rw,rwrq_comit_rw,rwmc_comit_rw,rwjf_comit_rw,sxrq_comit_rw,dqrq_comit_rw,dqzky_comit_rw
            ,zdr_comit_rw,shr_comit_rw;
    //判断新增数据是否存在
    ArrayList<String> card_list;
    LinearLayout touch;
    //接口
    String jfrw_get = URLS.design_query;
    String jfrw_add = URLS.design_query;
    String jfrw_del = URLS.design_query;
    String jfrw_set = URLS.design_query;
    String url_dh_price = URLS.price_num_ls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_designer_rw);
        context = DesignerRwActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        user_Id = sp.getString("USER_ID", "");
        user_yh = sp.getString("MR_YH", "");
        user_Name = sp.getString("USER_NAME", "");
        session = sp.getString("SESSION", "");
        getNowTime();
        session = sp.getString("SESSION", "");
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
        head.setText("会员任务单");
        rw_sszt = (TextView) findViewById(R.id.et_designrw_zt);
        rw_sszt.setText(db_zt);
        touch = (LinearLayout) findViewById(R.id.ll_touch_vip);
        touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getTicket();//获取临时单号
            }
        });
        rw_sjsdh = (TextView) findViewById(R.id.et_designrw_sjsbh);
        rw_rwdh = (TextView) findViewById(R.id.tv_rwdh);
        rw_rwrq = (TextView) findViewById(R.id.et_designrw_rwrq);
        rw_sxrq = (TextView) findViewById(R.id.et_designrw_sxrq);
        rw_dqrq = (TextView) findViewById(R.id.et_designrw_dqrq);
        rw_rwrq.setText(date_dd);
        rw_sxrq.setText(date_dd);
        rw_dqrq.setText(date_dd);
        rw_rwrq.setOnClickListener(new View.OnClickListener() {
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
                                rw_rwrq.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        rw_sxrq.setOnClickListener(new View.OnClickListener() {
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
                                rw_sxrq.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        rw_dqrq.setOnClickListener(new View.OnClickListener() {
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
                                rw_dqrq.setText(startTime);

                            }
                        }, mYear_time, mMonth_time, mDay_time).show();//获取当前时间
            }
        });
        rw_dqzky = (TextView) findViewById(R.id.et_designrw_dqzky);
        rw_zdr = (TextView) findViewById(R.id.et_designrw_zdr);
        rw_zdr.setText(user_Name);
        rw_shr = (TextView) findViewById(R.id.et_designrw_shr);

        rw_sfzh = (EditText) findViewById(R.id.et_designrw_usercard);
        rw_mc = (EditText) findViewById(R.id.et_designrw_rwmc);
        rw_rwjf = (EditText) findViewById(R.id.et_designrw_rwjf);

        rw_sszt_ib= (ImageButton) findViewById(R.id.ib_designrw_account);
        rw_sjsbh_ib= (ImageButton) findViewById(R.id.ib_designrw_sjsbh);
        rw_dqzky_ib= (ImageButton) findViewById(R.id.ib_designrw_dqzky);

        rw_add= (Button) findViewById(R.id.btn_designrw_add);
        rw_del= (Button) findViewById(R.id.btn_designrw_del);
        rw_reset= (Button) findViewById(R.id.btn_designrw_reset);
        rw_query= (Button) findViewById(R.id.btn_designrw_query);
        rw_save= (Button) findViewById(R.id.btn_designrw_save);
        rw_sh= (Button) findViewById(R.id.btn_designrw_sh);
        rw_add.setOnClickListener(this);
        rw_del.setOnClickListener(this);
        rw_reset.setOnClickListener(this);
        rw_query.setOnClickListener(this);
        rw_save.setOnClickListener(this);
        rw_sh.setOnClickListener(this);
        rw_sszt_ib.setOnClickListener(this);
        rw_sjsbh_ib.setOnClickListener(this);
        rw_dqzky_ib.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_designrw_account:
                Intent intent = new Intent(context, CondicionInfoActivity.class);
                intent.putExtra("flag", "1");
                startActivityForResult(intent, 1);
                break;
            case R.id.ib_designrw_dqzky:
                showPopupMenu(rw_dqzky_ib);
                break;
            case R.id.btn_designrw_add:
                clearInfo();

                do_design_rw = 1;
                rw_zdr.setEnabled(false);
                rw_shr.setEnabled(false);
                rw_dqzky.setText("否");
                rw_dqzky_ib.setEnabled(false);

                break;
            case R.id.btn_designrw_del:
                break;
            case R.id.btn_designrw_reset:
                break;
            case R.id.btn_designrw_query:
                break;
            case R.id.btn_designrw_save:
                if (do_design_rw == 1) {
                    commitInfos();
                    //获取所有数据（主要是判断身份证）
                    getInfos_all();

                }
                break;
            case R.id.btn_designrw_sh:
                break;
            default:
                break;
        }

    }
    private void getInfos_all() {
        if (!rw_sszt.getText().toString().equals("")) {
            OkHttpClient client_all = new OkHttpClient();
            FormBody body_all = new FormBody.Builder()
                    .add("db_Id", rw_sszt.getText().toString())
                    .build();
            Request request_all = new Request.Builder()
                    .addHeader("cookie", session).url(jfrw_get)
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
                        if (response.code() == 200) {

                            save_add();
                        }
                    }
                }

                @Override
                public void onFailure(Call call, IOException e) {

                }
            });
        }
    }

    private void save_add() {
        Log.e("LiNing", "id_type结果====" + card_list);
        if (card_list.contains(sfzh_comit_rw)) {
            DesignerRwActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "该设计师已存在", Toast.LENGTH_LONG).show();
                }
            });

        }  else if (sfzh_comit_rw.length() < 18 || sfzh_comit_rw.length() > 20) {
            DesignerRwActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "请添加正确身份证号", Toast.LENGTH_LONG).show();
                }
            });

        } else {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("db_Id", zt_comit_rw)
                    .add("vip_NO", sjsbh_comit_rw)
                    .add("Card_Num", sfzh_comit_rw)
                    .add("vt_No", rwdh_comit_rw)
                    .add("task_Name", rwmc_comit_rw)
                    .add("task_Points", rwjf_comit_rw)
                    .add("task_Date", rwrq_comit_rw)
                    .add("beg_Date", sxrq_comit_rw)
                    .add("end_Date", dqrq_comit_rw)
                    .add("isTran", dqzky_comit_rw)
                    .add("user_No", user_yh)
                    .add("chk_No", user_yh)
                    .build();
            Log.e("LiNing", "添加结果====" + zt_comit_rw + "---" + sjsbh_comit_rw + "---" + sfzh_comit_rw + "---" + rwdh_comit_rw + "---" + rwmc_comit_rw + "---"
                    + rwjf_comit_rw + "---" + rwrq_comit_rw + "---" + sxrq_comit_rw + "---" + dqrq_comit_rw + "---" + dqzky_comit_rw + "---" + user_yh  );
            client.newCall(
                    new Request.Builder().addHeader("cookie", session).url(jfrw_add)
                            .post(body).build()).enqueue(new Callback() {

                @Override
                public void onResponse(Call call, Response response)
                        throws IOException {
                    String str = response.body().string();
                    Log.e("LiNing", "添加结果====" + str);
                    final JsonRootBean localJsonRootBean = (JsonRootBean) new Gson()
                            .fromJson(str, JsonRootBean.class);
                    if (localJsonRootBean != null) {
                        DesignerRwActivity.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                boolean rlo = localJsonRootBean.getRLO();
                                if (rlo == true) {
                                    Toast.makeText(DesignerRwActivity.this,
                                            "新增成功", Toast.LENGTH_SHORT).show();
                                } else if (rlo == false) {
                                    Toast.makeText(DesignerRwActivity.this,
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
    private void commitInfos() {
        zt_comit_rw = rw_sszt.getText().toString();
        if (zt_comit_rw.equals("")) {
            Toast.makeText(context, "请添加账套", Toast.LENGTH_LONG).show();
        }
        rwdh_comit_rw = rw_rwdh.getText().toString();
        Log.e("LiNing", "-----" + jfdh_comit_rw);
        if (rwdh_comit_rw.equals("")) {
            Toast.makeText(context, "请添加任务单号", Toast.LENGTH_LONG).show();
        }
        sjsbh_comit_rw = rw_sjsdh.getText().toString();
        Log.e("LiNing", "长度=====" + sjsbh_comit_rw.length());
        if (sjsbh_comit_rw.equals("")) {
            Toast.makeText(context, "请添加设计师代号", Toast.LENGTH_LONG).show();
        }
        sfzh_comit_rw = rw_sfzh.getText().toString();
        Log.e("LiNing", "长度=====" + sfzh_comit_rw.length());
        if (sfzh_comit_rw.equals("")) {
            Toast.makeText(context, "请添加正确的身份证号", Toast.LENGTH_LONG).show();
        }
        rwrq_comit_rw = rw_rwrq.getText().toString();
        if (rwrq_comit_rw.equals("")) {
            Toast.makeText(context, "请添加任务日期", Toast.LENGTH_LONG).show();
        }
        rwmc_comit_rw = rw_mc.getText().toString();
        if (rwmc_comit_rw.equals("")) {
            Toast.makeText(context, "请添加任务名称", Toast.LENGTH_LONG).show();
        }
        rwjf_comit_rw = rw_rwjf.getText().toString();
        if (rwjf_comit_rw.equals("")) {
            Toast.makeText(context, "请添加任务积分", Toast.LENGTH_LONG).show();
        }
        sxrq_comit_rw = rw_sxrq.getText().toString();
        if (sxrq_comit_rw.equals("")) {
            Toast.makeText(context, "请添加生效日期", Toast.LENGTH_LONG).show();
        }

        dqrq_comit_rw = rw_dqrq.getText().toString();
        if (dqrq_comit_rw.equals("")) {
            Toast.makeText(context, "请添加到期日期", Toast.LENGTH_LONG).show();
        }


        zdr_comit_rw = rw_zdr.getText().toString();
        if (do_design_rw != 1) {

            if (zdr_comit_rw.equals("")) {
                Toast.makeText(context, "请添加制单人", Toast.LENGTH_LONG).show();
            }
        }
        shr_comit_rw = rw_shr.getText().toString();
        if (do_design_rw != 1) {

            if (shr_comit_rw.equals("")) {
                Toast.makeText(context, "审核用户为空", Toast.LENGTH_LONG).show();
            }
        }
        String sfty_sj = rw_dqzky.getText().toString();
        if (sfty_sj.equals("是")) {
            dqzky_comit_rw = "T";
        }
        if (sfty_sj.equals("否")) {
            dqzky_comit_rw = "F";
        }
    }
    private void clearInfo() {
        rw_rwdh.setText("");
        rw_sjsdh.setText("");
        rw_sfzh.setText("");
        rw_mc.setText("");
        rw_rwjf.setText("");
        rw_shr.setText("");

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
                    rw_dqzky.setText(menuItem.getTitle());
                    Log.e("LiNing", "----" + rw_dqzky);

                }
                return true;
            }
        });
        popupMenu.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode==1){
                    String str_id = data.getStringExtra("condition_db");
                    str_name = data.getStringExtra("condition_name");
                    rw_sszt.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + str_name);
                }
                break;
        }
    }
    //临时单号
    private void getTicket() {
        String DB_LS = rw_sszt.getText().toString();
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
                    .add("bn_Type", "VO")
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
                            DesignerRwActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    String id_ls = bean_id.getBil_no();
                                    rw_rwdh.setText(id_ls);
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
