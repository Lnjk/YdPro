package com.example.ydshoa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.URLS;
import com.example.bean.UserInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DesignerJfbActivity extends Activity implements View.OnClickListener {
    Context context;
    private TextView head;
    private String reportBus;
    private ImageButton jf_jl, jf_rw, jf_dh;
    private String session;
    private SharedPreferences sp;
    String url = URLS.userInfo_url;
    ArrayList<String> modIds_get = new ArrayList<String>();
    private ArrayList<HashMap<String, Object>> dList;
    private HashMap<String, Object> item;
    private String ps_id, db_ID, mod_ID;
    private List<UserInfo.User_Mod> user_Mod;
    private int pass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_designer_jfb);
        context = DesignerJfbActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        ps_id = sp.getString("PASS", "");
        initView();
        getRoot();
    }

    private void initView() {
        head = (TextView) findViewById(R.id.all_head);
        head.setText("积分表管理");
        jf_jl = (ImageButton) findViewById(R.id.btn_jf_jlb);
        jf_rw = (ImageButton) findViewById(R.id.btn_jf_rwb);
        jf_dh = (ImageButton) findViewById(R.id.btn_jf_dhb);
        jf_jl.setOnClickListener(this);// 积分记录
        jf_rw.setOnClickListener(this);// 积分任务
        jf_dh.setOnClickListener(this);// 积分对换
        dList = new ArrayList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_jf_jlb:
                if (pass2 == 1) {
                    startActivity(new Intent(context, DesignerJlActivity.class));
                } else {
                    //判断对应权限
                    startActivity(new Intent(context, DesignerJlActivity.class));
                }

                break;
            case R.id.btn_jf_rwb:
                if (pass2 == 1) {
                    //增添新页面（明细）
                    //判断对应权限
                    Intent intent = new Intent(context, DesignerJlMxActivity.class);
                    intent.putExtra("MXTJ", "1");
                    startActivity(intent);
                } else {
                    //判断对应权限
                    Intent intent = new Intent(context, DesignerJlMxActivity.class);
                    intent.putExtra("MXTJ", "1");
                    startActivity(intent);
                }

                break;
            case R.id.btn_jf_dhb:
                if (pass2 == 1) {
                    //增添新页面（统计）
                    Intent intent = new Intent(context, DesignerJlMxActivity.class);
                    intent.putExtra("MXTJ", "2");
                    startActivity(intent);
                } else {
                    //判断对应权限
                    Intent intent = new Intent(context, DesignerJlMxActivity.class);
                    intent.putExtra("MXTJ", "2");
                    startActivity(intent);
                }

                break;

            default:
                break;
        }
    }

    public void allback(View v) {
        finish();
    }

    private void getRoot() {
        OkHttpClient client = new OkHttpClient();
        Request localRequest = new Request.Builder()
                .addHeader("cookie", session).url(url).build();
        client.newCall(localRequest).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                String string = response.body().string();
                Log.e("LiNing", "------" + response.code() + "------" + string);
                Gson gson = new GsonBuilder().setDateFormat(
                        "yyyy-MM-dd HH:mm:ss").create();
                UserInfo info = gson.fromJson(string, UserInfo.class);

                if (info.getUser_Id().equalsIgnoreCase("admin")
                        && info.getUser_Pwd().equalsIgnoreCase("admin")) {
                    pass2 = 1;
                } else {
                    List<UserInfo.User_Mod> user_Mod = info
                            .getUser_Mod();
                    if (user_Mod.size() > 0 && user_Mod != null) {
                        for (int i = 0; i < user_Mod.size(); i++) {

                            String mod_ID = user_Mod.get(i).getMod_ID();
                            modIds_get.add(mod_ID);
                            sp.edit().putString("modIds", "" + modIds_get)
                                    .commit();

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call arg0, IOException arg1) {

            }
        });
    }
}
