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

public class SalesObjectActivity extends Activity implements View.OnClickListener {
    Context context;
    private TextView head;
    private String reportBus;
    private ImageButton sale_tj, sale_tb,sale_ppfx;
    private String session;
    private SharedPreferences sp;
    String url = URLS.userInfo_url;
    String url_query =  URLS.ERP_db_url;
    ArrayList<String> modIds_get = new ArrayList<String>();
    private ArrayList<HashMap<String, Object>> dList;
    private HashMap<String, Object> item;
    private String ps_id,db_ID,mod_ID;
    private List<com.example.bean.UserInfo.User_Mod> user_Mod;
    private boolean query, add, alter, amt, cst, del, gp, gpr, out, prt, qty,
            up;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sales_object);
        context=SalesObjectActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        ps_id = sp.getString("PASS", "");
        reportBus = getIntent().getStringExtra("reportB");// 获取业务类型
        initView();
        getRoot();
    }

    private void initView() {
        head = (TextView) findViewById(R.id.all_head);
        head.setText("报表种类");
        sale_tj = (ImageButton) findViewById(R.id.btn_dec_sale_tj);
        sale_tb = (ImageButton) findViewById(R.id.btn_dec_shrec_tb);
        sale_ppfx = (ImageButton) findViewById(R.id.btn_dec_sale_ppfx);
        sale_tj.setOnClickListener(this);// 销售统计
        sale_tb.setOnClickListener(this);// 销售同比
        sale_ppfx.setOnClickListener(this);// 品牌分析
        dList = new ArrayList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_dec_sale_tj:
                Intent intent = new Intent(context, SalesNumActivity.class);
                intent.putExtra("reportB", "SA");
                intent.putExtra("sal_obj", "1");
                startActivity(intent);
                break;
            case R.id.btn_dec_shrec_tb:
                if (ps_id.equals("1")) {
//                    sp.edit().putString("TJ", head.getText().toString()).commit();
////					Intent intent = new Intent(context, SalesNumActivity.class);
//                    Intent intent_tb = new Intent(context,SalesPkActivity.class);
//                    intent_tb.putExtra("reportB", "SA");
//                    startActivity(intent_tb);
                    Intent intent_tb = new Intent(context, TbSalesActivity.class);
                    intent_tb.putExtra("reportB", "SA");
//                intent_tb.putExtra("sal_obj", "2");//第一版（弹弹弹）
                    intent_tb.putExtra("sal_obj", "3");
                    startActivity(intent_tb);
                } else {
                    if (modIds_get.contains("spm")) {
//                        sp.edit().putString("TJ", head.getText().toString())
//                                .commit();
////						Intent intent = new Intent(context, SalesNumActivity.class);
//                        Intent intent_tb = new Intent(context, SalesPkActivity.class);
//                        intent_tb.putExtra("reportB", "SA");
//                        startActivity(intent_tb);
                        Intent intent_tb = new Intent(context, TbSalesActivity.class);
                        intent_tb.putExtra("reportB", "SA");
//                intent_tb.putExtra("sal_obj", "2");//第一版（弹弹弹）
                        intent_tb.putExtra("sal_obj", "3");
                        startActivity(intent_tb);
                    }
                }

                break;
            case R.id.btn_dec_sale_ppfx:
                if (ps_id.equals("1")) {
                    sp.edit().putString("TJ", head.getText().toString()).commit();
                    Intent intent_fx = new Intent(context, BrandFxActivity.class);
//                intent_fx.putExtra("reportB", "SA");
//                intent_fx.putExtra("sal_obj", "3");
                    startActivity(intent_fx);
                } else {
                    if (modIds_get.contains("SATGVTP")) {
                        Intent intent_fx = new Intent(context, BrandFxActivity.class);
//                intent_fx.putExtra("reportB", "SA");
//                intent_fx.putExtra("sal_obj", "3");
                        startActivity(intent_fx);

                    }
                    else{
                        Toast.makeText(context, "此种类无权限", Toast.LENGTH_LONG).show();
                    }
                }

                break;
                default:
                    break;
        }
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
//				sp.edit().putString("all_query_tj", string).commit();
                Gson gson = new GsonBuilder().setDateFormat(
                        "yyyy-MM-dd HH:mm:ss").create();
                UserInfo info = gson.fromJson(string, UserInfo.class);
//				//查询
                List<UserInfo.User_Query> user_Query = info.getUser_Query();
                String query_json = new Gson().toJson(user_Query);
                sp.edit().putString("all_query", query_json).commit();
                Log.e("LiNing", "提交保存的数据====" + query_json);
                //模块
                user_Mod = info.getUser_Mod();
                Log.e("LiNing", "===user_Mod=====" + user_Mod);
                if (user_Mod.size() > 0 && user_Mod != null) {
                    for (int i = 0; i < user_Mod.size(); i++) {
                        mod_ID = user_Mod.get(i).getMod_ID();
                        modIds_get.add(mod_ID);
                        if (mod_ID.equals("spmsa")) {
                            db_ID = user_Mod.get(i).getDb_ID();
                            out = user_Mod.get(i).isMod_Out();// 转出
                            prt = user_Mod.get(i).isMod_Prt();// 打印
                            qty = user_Mod.get(i).isMod_Cst();// 成本
                            gp = user_Mod.get(i).isMod_GP();// 毛利
                            gpr = user_Mod.get(i).isMod_GPR();// 毛利率
                            amt = user_Mod.get(i).isMod_Amt();//金额
                            Log.e("LiNing", "========" + i + mod_ID + qty + gp
                                    + gpr+amt+out+prt);
                            sp.edit().putString("CB", "" + qty).commit();
                            sp.edit().putString("ML", "" + gp).commit();
                            sp.edit().putString("MLL", "" + gpr).commit();
                            sp.edit().putString("ZC", "" + out).commit();
                            sp.edit().putString("DY", "" + prt).commit();
                            sp.edit().putString("JE", "" + amt).commit();
                            item = new HashMap<String, Object>();
                            item.put("账套", db_ID);
                            item.put("转出", "" + out);
                            item.put("打印", "" + prt);
                            item.put("成本", ""+qty);
                            item.put("毛利", ""+gp);
                            item.put("毛利率", ""+gpr);
                            item.put("金额", ""+amt);
                            dList.add(item);
                            Log.e("LiNing", "===map=====" + item);
                            Log.e("LiNing", "===map=new====" + dList);

                        }
                        if (mod_ID.equals("spmsrp")) {
                            db_ID = user_Mod.get(i).getDb_ID();
                            out = user_Mod.get(i).isMod_Out();// 转出
                            prt = user_Mod.get(i).isMod_Prt();// 打印
                            qty = user_Mod.get(i).isMod_Cst();// 成本
                            gp = user_Mod.get(i).isMod_GP();// 毛利
                            gpr = user_Mod.get(i).isMod_GPR();// 毛利率
                            amt = user_Mod.get(i).isMod_Amt();//金额
                            Log.e("LiNing", "========" + i + mod_ID + qty + gp
                                    + gpr+amt+out+prt);
                            sp.edit().putString("CB", "" + qty).commit();
                            sp.edit().putString("ML", "" + gp).commit();
                            sp.edit().putString("MLL", "" + gpr).commit();
                            sp.edit().putString("ZC", "" + out).commit();
                            sp.edit().putString("DY", "" + prt).commit();
                            sp.edit().putString("JE", "" + amt).commit();
                            item = new HashMap<String, Object>();
                            item.put("账套", db_ID);
                            item.put("转出", "" + out);
                            item.put("打印", "" + prt);
                            item.put("成本", ""+qty);
                            item.put("毛利", ""+gp);
                            item.put("毛利率", ""+gpr);
                            item.put("金额", ""+amt);
                            dList.add(item);
                            Log.e("LiNing", "===map=====" + item);
                            Log.e("LiNing", "===map=new====" + dList);

                        }

                    }
                    if (dList != null && dList.size() > 0) {
                        Log.e("LiNing", ",,,,,,," + dList.toString());
                        String stritem = new Gson().toJson(dList);
                        sp.edit().putString("CBQX", stritem).commit();
                        Log.e("LiNing", "提交的数据是" + sp.getString("CBQX", ""));
                    }

                }
            }

            @Override
            public void onFailure(Call arg0, IOException arg1) {

            }
        });
    }
    public void allback(View v) {
        finish();
    }
}
