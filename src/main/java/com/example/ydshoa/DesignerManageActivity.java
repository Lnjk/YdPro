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
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DesignerManageActivity extends Activity implements View.OnClickListener {
    private ImageButton bbb, jfb,rwb;
    private Context context;
    private TextView head;
    private String session;
    ArrayList<String> modIds_get = new ArrayList<String>();
    private SharedPreferences sp;
    String url = URLS.userInfo_url;
    private String ps_id;
    private int pass2;
//    ArrayList<String> modIds_get;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_designer_manage);
        context = DesignerManageActivity.this;
//		PushAgent.getInstance(context).onAppStart();
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
//        ps_id = sp.getString("PASS", "");
//        pass2 = getIntent().getIntExtra("PASS", 0);
//        modIds_get = getIntent().getStringArrayListExtra("MODIDS");
         pass2 = Integer.valueOf(sp.getString("PASS", ""));
        String modIds = sp.getString("modIds", "");
        String substring = modIds.substring(1, modIds.length() - 1);
        String[] db_spls = substring.split(",");
        for (int i = 0; i < db_spls.length; i++) {
            String A = db_spls[i];
            modIds_get.add(A);
        }
        Log.e("LiNing", "===A==" +pass2+ modIds_get.size()+modIds_get);
        initView();
//        getRoot();
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
//                            Boolean query = user_Mod.get(i).isMod_Query();//查询
//                            Boolean add = user_Mod.get(i).isMod_Add();//新增
//                            Boolean  del = user_Mod.get(i).isMod_Del();//删除
//                            Boolean alter = user_Mod.get(i).isMod_Alter();//更新
//                            Boolean  out = user_Mod.get(i).isMod_Out();//转出
//                            sp.edit().putString("PRICE_QUERY", "" + query).commit();
//                            sp.edit().putString("PRICE_ADD", "" + add).commit();
//                            sp.edit().putString("PRICE_DEL", "" + del).commit();
//                            sp.edit().putString("PRICE_OUT", "" + out).commit();
//                            sp.edit().putString("USER_UP", "" + alter).commit();

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call arg0, IOException arg1) {

            }
        });
    }
    private void initView() {
        head = (TextView) findViewById(R.id.all_head);
        head.setText("会员管理表");
        bbb = (ImageButton) findViewById(R.id.design_bbb);
        jfb = (ImageButton) findViewById(R.id.design_jfb);
        rwb = (ImageButton) findViewById(R.id.design_jfb1);
        bbb.setOnClickListener(this);// 设计师报备表
        jfb.setOnClickListener(this);// 设计师积分表
        rwb.setOnClickListener(this);// 设计师任务表
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.design_bbb:
                if (pass2 == 1) {
                    startActivity(new Intent(context, DesignThreeActivity.class));
                }else {
                    if (modIds_get.contains("skvphb")) {

                        startActivity(new Intent(context, DesignThreeActivity.class));
                    } else {
                        Toast.makeText(this.context, "请等待...", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.design_jfb:
                if (pass2 == 1) {
                    startActivity(new Intent(context, DesignerJfbActivity.class));
                }else {
                    if (modIds_get.contains("skvpjb")) {

                        startActivity(new Intent(context, DesignerJfbActivity.class));
                    } else {
                        Toast.makeText(this.context, "请等待...", Toast.LENGTH_SHORT).show();
                    }
                }


                break;
            case R.id.design_jfb1:
                if (pass2 == 1) {
                    startActivity(new Intent(context, DesignerRwManageActivity.class));
                }else {
                    if (modIds_get.contains("skvpwb")) {

                        startActivity(new Intent(context, DesignerRwManageActivity.class));
                    } else {
                        Toast.makeText(this.context, "请等待...", Toast.LENGTH_SHORT).show();
                    }
                }


                break;

            default:
                break;
        }
    }
    public void allback(View v) {
        finish();
    }
}
