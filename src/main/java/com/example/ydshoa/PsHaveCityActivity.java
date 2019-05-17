package com.example.ydshoa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.example.bean.AddAndress;
import com.example.bean.JsonRootBean;
import com.example.bean.URLS;
import com.google.gson.Gson;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PsHaveCityActivity extends Activity implements View.OnClickListener {
    private String session,db_zt,user_Id,date_dd,dabh_befor ,sszt_befor,sub_id,sub_dz,url_do,do_save,sizes,xc_andress,add_xxdz,check_add,zt_add,dabh_add,shr_add,zcdh_add;
    String extra_zt,extra_dabh,extra_itm,extra_shr,extra_dh,extra_ss,extra_qx,extra_adress,extra_userid,extra_def;
    private TextView dzbh,head,zt,dacard,yhbh,sfmr,xxdz,ss,qx;
    private EditText shr,lxdh,add_newAress;
    private LinearLayout ll_xxdz;
    private SharedPreferences sp;
    private Context context;
    private ImageButton zt_xl,mr_xl;
    String ps_add = URLS.psxx_add;
    String ps_set = URLS.psxx_updata;
    CityPickerView mCityPickerView = new CityPickerView();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_ps_have_city);
        context = PsHaveCityActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        db_zt = sp.getString("DB_MR", "");
        user_Id = sp.getString("USER_ID", "");
        sszt_befor = getIntent().getStringExtra("SSZT_ps");
        dabh_befor = getIntent().getStringExtra("DABH_ps");
        sizes = getIntent().getStringExtra("SIZE");
        do_save = getIntent().getStringExtra("DO");
        extra_zt = getIntent().getStringExtra("ZT");
        extra_dabh = getIntent().getStringExtra("DABH");
        extra_itm = getIntent().getStringExtra("ITM");
        extra_shr = getIntent().getStringExtra("SHR");
        extra_dh = getIntent().getStringExtra("DH");
        extra_ss = getIntent().getStringExtra("SS");
        extra_qx = getIntent().getStringExtra("QX");
        extra_adress = getIntent().getStringExtra("ANDRESS");
        extra_userid = getIntent().getStringExtra("USERID");
        extra_def = getIntent().getStringExtra("DEF");
        Log.e("LiNing","+=========="+sszt_befor + dabh_befor+sizes + do_save);
        //新增客户
//        check_add = getIntent().getStringExtra("check_new");
        zt_add = getIntent().getStringExtra("SSZT_new");
        dabh_add = getIntent().getStringExtra("DABH_new");
        shr_add = getIntent().getStringExtra("SHR_new");
        zcdh_add = getIntent().getStringExtra("ZCDH_new");
        //		 * 预先加载仿iOS滚轮实现的全部数据
        mCityPickerView.init(this);
        intView();
    }

    private void intView() {
        head = (TextView) findViewById(R.id.all_head);
        head.setText("配送信息");
        zt = (TextView) findViewById(R.id.et_move_zt);
        yhbh = (TextView) findViewById(R.id.et_move_yhbh);
        sfmr = (TextView) findViewById(R.id.tv_move_ismr);
        dacard = (TextView) findViewById(R.id.tv_move_bh);
        dzbh= (TextView) findViewById(R.id.et_move_dzbh);
//        dzbh.setText("1");
        shr= (EditText) findViewById(R.id.et_move_username);
        lxdh= (EditText) findViewById(R.id.et_move_phone);
        if(do_save.equals("11")){
            zt.setText(zt_add);
            dacard.setText(dabh_add);
            dzbh.setText("1");
            shr.setText(shr_add);
            lxdh.setText(zcdh_add);
            yhbh.setText(user_Id);
        }else{
            int i = Integer.valueOf(sizes) + 1;
            dzbh.setText(""+i);
        }
        ss= (TextView) findViewById(R.id.et_move_ss);
        qx= (TextView) findViewById(R.id.et_move_qx);
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
                             xc_andress = district.getName().toString();
                        }

                        Log.e("LiNing","+=========="+"" + sb.toString());
                        ss.setText(province.getName());
                        qx.setText(city.getName()+district.getName());

                    }

                    @Override
                    public void onCancel() {
                        ToastUtils.showLongToast(context, "已取消");
                    }
                });
                mCityPickerView.showCityPicker();
            }
        });

        xxdz= (TextView) findViewById(R.id.et_move_adress);
        xxdz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //textview点击失去焦点获取地图
//                Intent intent1 = new Intent(context, SearchMapActivity.class);
                Intent intent1 = new Intent(context, MapInfosActivity.class);
                intent1.putExtra("SF", ss.getText().toString());
                intent1.putExtra("QX", qx.getText().toString());
                intent1.putExtra("XC", xc_andress);
                startActivityForResult(intent1, 2);
            }
        });
        ll_xxdz= (LinearLayout) findViewById(R.id.ll_xxdz_new);
        add_newAress= (EditText) findViewById(R.id.et_addnewandress);

        zt_xl= (ImageButton) findViewById(R.id.ib_move_account);
        mr_xl= (ImageButton) findViewById(R.id.ib_move_sfmr);
        if(do_save.equals("1")){
            zt.setText(sszt_befor);
            dacard.setText(dabh_befor);
            yhbh.setText(user_Id);
        }
        else if(do_save.equals("2")){
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

        findViewById(R.id.imageButton1).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                       finish();
                    }
                });
        findViewById(R.id.btn_move_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(do_save.equals("11")){
                    AddAndress andress=new AddAndress();
                    andress.setZt_add_and(zt.getText().toString());
                    andress.setDabh_add_and(dacard.getText().toString());
                    andress.setDzbh_add_and(dzbh.getText().toString());
                    andress.setShr_add_and(shr.getText().toString());
                    andress.setLxdh_add_and(lxdh.getText().toString());
                    andress.setSs_add_and(ss.getText().toString());
                    andress.setQx_add_and(qx.getText().toString());

                    andress.setXxdz_add_and(xxdz.getText().toString()+"~"+add_newAress.getText().toString());
                    andress.setYhbh_add_and(yhbh.getText().toString());
                    andress.setSfmr_add_and(sfmr.getText().toString());
                    Intent localIntent = getIntent();
                    localIntent.putExtra("Andress_add_ALL", andress);
                    setResult(1, localIntent);
                    finish();
                }else{
                    if(!xxdz.equals("")){
//                    ll_xxdz.setVisibility(View.VISIBLE);
                        String add_xxdz_tv = xxdz.getText().toString();
                        String add_xxdz_et = add_newAress.getText().toString();
                        add_xxdz = add_xxdz_tv+"~"+add_xxdz_et;
                        postPsxx();
                    }else{
                        Toast.makeText(context,"请填写地址",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.ib_move_sfmr:
                showPopupMenu(mr_xl);
                break;
        }
    }
    private void postPsxx() {
        if (do_save .equals("1")) {
            url_do = ps_add;

        }
        else if (do_save.equals("2") ) {
            url_do = ps_set;
        }
        String add_lxdh = lxdh.getText().toString();
        String add_zt = zt.getText().toString();
        String add_dacard = dacard.getText().toString();
        String add_dzbh = dzbh.getText().toString();
        String add_shr = shr.getText().toString();
        String add_ss = ss.getText().toString();
        String add_qx = qx.getText().toString();
//        String add_xxdz = xxdz.getText().toString();
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
                    PsHaveCityActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            boolean rlo = localJsonRootBean.getRLO();
                            if (rlo == true) {//新增，修改合并
                                Toast.makeText(PsHaveCityActivity.this,
                                        "保存成功", Toast.LENGTH_SHORT).show();
                                finish();
//                                if(do_save.equals("1")||do_save.equals("2")){
//
//                                    startActivity(new Intent(context,
//                                            MoveAllInfoActivity.class));
//                                }
                            } else if (rlo == false) {
                                Toast.makeText(PsHaveCityActivity.this,
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 2:
                if (resultCode == 1) {
                    String str_andress = data.getStringExtra("ANDRESS");
                    xxdz.setText(str_andress);
                    Log.e("LiNing", "提交的id====" + str_andress);
                    if(!xxdz.equals("")){
                        ll_xxdz.setVisibility(View.VISIBLE);
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
