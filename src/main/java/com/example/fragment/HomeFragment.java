package com.example.fragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.example.bean.AccountInfo;
import com.example.bean.URLS;
import com.example.bean.UserInfo;
import com.example.bean.UserInfo.User_Query;
import com.example.ydshoa.CustersAllActivity;
import com.example.ydshoa.DecisionActivity;
import com.example.ydshoa.DesignerActivity;
import com.example.ydshoa.FormActivity;
import com.example.ydshoa.MapInfosActivity;
import com.example.ydshoa.MoreAddressActivity;
import com.example.ydshoa.R;
import com.example.ydshoa.SalesManageActivity;
import com.example.ydshoa.SmrtActivity;
import com.example.ydshoa.SpActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private LinearLayout custer, decision, form, offer, sell, system;
    private String session;
    private SharedPreferences sp;
    private boolean gp, gpr, qty;
    // private boolean add, query, alter, amt, cst, del, gp, gpr, out, prt, qty,
    // up;
    ArrayList<String> modIds_get = new ArrayList<String>();
    ArrayList<String> queryDB_get = new ArrayList<String>();
    String url = URLS.userInfo_url;
    private int pass;
    private String stritem_idTname;
    private ArrayList<HashMap<String, Object>> dList_idTn;
    String url_query =  URLS.ERP_db_url;
    public View onCreateView(LayoutInflater paramLayoutInflater,
                             ViewGroup paramViewGroup, Bundle paramBundle) {
        View localView = paramLayoutInflater.inflate(R.layout.home,
                paramViewGroup, false);
        context = getActivity();
        sp = getActivity().getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        this.custer = ((LinearLayout) localView.findViewById(R.id.ll_custer));
        this.offer = ((LinearLayout) localView.findViewById(R.id.ll_offer));
        this.form = ((LinearLayout) localView.findViewById(R.id.ll_form));
        this.sell = ((LinearLayout) localView.findViewById(R.id.ll_sell));
        this.decision = ((LinearLayout) localView
                .findViewById(R.id.ll_decision));
        this.system = ((LinearLayout) localView.findViewById(R.id.ll_system));
        this.custer.setOnClickListener(this);
        this.offer.setOnClickListener(this);
        this.form.setOnClickListener(this);
        this.sell.setOnClickListener(this);
        this.decision.setOnClickListener(this);
        this.system.setOnClickListener(this);
        dList_idTn = new ArrayList();
        getIDtoName();
//        getRoot();
        return localView;
    }

    public void onClick(View paramView) {
        switch (paramView.getId()) {
            case R.id.ll_custer:
                //客户管理
                startActivity(new Intent(this.context, CustersAllActivity.class));
                break;
            case R.id.ll_offer:
                //设计师管理
//                Toast.makeText(this.context, "生产管理暂未开放，请等待...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this.context, DesignerActivity.class));
                break;
            case R.id.ll_form:
//                物流管理
                Toast.makeText(this.context, "暂未开放，请等待...", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(this.context, MoreAddressActivity.class));
                break;
            case R.id.ll_sell:
//                销售管理
                startActivity(new Intent(this.context, SalesManageActivity.class));
//                Toast.makeText(this.context, "暂未开放，请等待...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_decision:
//                决策报表
                Log.e("LiNing", "-------" + sp.getString("PASS", ""));
                if (sp.getString("PASS", "").equals("1")) {
                    startActivity(new Intent(context, SpActivity.class));
                } else {
                    if (modIds_get.contains("sp")) {
                        startActivity(new Intent(context, SpActivity.class));
                    } else {
                        Toast.makeText(this.context, "无此权限", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.ll_system:
//                系统管理
                Log.e("LiNing", "---modid===FFFFFF---" + modIds_get);
                if (sp.getString("PASS", "").equals("1")) {

                    startActivity(new Intent(context, SmrtActivity.class));
                } else if (modIds_get.contains("sm")) {

                    startActivity(new Intent(context, SmrtActivity.class));
                } else {
                    Toast.makeText(this.context, "无此权限", Toast.LENGTH_SHORT).show();
                }
            default:
                break;
        }
    }
    private void getIDtoName() {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder().build();
        client.newCall(new Request.Builder().addHeader("cookie", session).post(body)
                .url(url_query).build()).enqueue(new Callback() {

            @Override
            public void onResponse(Call arg0, Response response) throws IOException {
                String str = response.body().string();
                Log.e("LiNing", "账套数据===" + str);
                final AccountInfo aDb1 = new Gson().fromJson(str,
                        AccountInfo.class);
                if (aDb1 != null) {
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            List<com.example.bean.AccountInfo.IdNameList> data = aDb1.getIdnamelist();
                            Log.e("LiNing", "账套数据===data" + data);
                            if(data.size() > 0 && data != null){

                                for (int i = 0; i < data.size(); i++) {
                                    String db_Id = data.get(i).getId();
                                    String name = data.get(i).getName();
                                    HashMap<String, Object> item = new HashMap<String, Object>();
                                    item.put("ZT_ID", db_Id);
                                    item.put("ZT_NAME",name);
                                    Log.e("LiNing", "账套数据==SSSS=data" + item);
                                    dList_idTn.add(item);
//											stritem_idTname = new Gson().toJson(dList_idTn);
                                }
                            }
                            if(dList_idTn!=null&&dList_idTn.size()>0){
                                stritem_idTname = new Gson().toJson(dList_idTn);
                                Log.e("LiNing", "11111111====333=="+stritem_idTname);
                                sp.edit().putString("all_query_tj_to", stritem_idTname).commit();
                            }
                        }
                    });
                    if(response.code()==200){
                        getRoot();
                    }
                }
            }

            @Override
            public void onFailure(Call arg0, IOException arg1) {
                // TODO Auto-generated method stub

            }
        });
    }
    // 判断是否进入
    private void getRoot() {
        OkHttpClient client = new OkHttpClient();
        Request localRequest = new Request.Builder()
                .addHeader("cookie", session).url(url).build();
        client.newCall(localRequest).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                String string = response.body().string();
                sp.edit().putString("all_query_tj", string).commit();
                Log.e("LiNing", "---user_QueryFFFFFFFFFFF---"
                        + string);
                Gson gson = new GsonBuilder().setDateFormat(
                        "yyyy-MM-dd HH:mm:ss").create();
                UserInfo info = gson.fromJson(string, UserInfo.class);
                String user_Id = info.getUser_Id();
                String user_Pwd = info.getUser_Pwd();
                String user_name = info.getUser_Name();
                String user_db = info.getUser_DB();
                String user_erp = info.getUser_erpUser();
                String user_dep = info.getUser_Dep();
                sp.edit().putString("DB_MR", user_db).commit();
                sp.edit().putString("MR_YH", user_erp).commit();
                sp.edit().putString("USER_NAME", user_name).commit();
                sp.edit().putString("USER_ID", user_Id).commit();
                sp.edit().putString("USER_DEPBM", user_dep).commit();
                if (user_Id.equalsIgnoreCase("admin")
                        && user_Pwd.equalsIgnoreCase("admin")) {
                    pass = 1;
                    sp.edit().putString("PASS", "" + pass).commit();

                } else {

//                    List<User_Query> user_Query = info.getUser_Query();
//                    Log.e("LiNing", "---FFFFFFuser_QueryFFFFFFFFFFF---"
//                            + user_Query);
//                    if (user_Query.size() > 0 && user_Query != null) {
//                        for (int i = 0; i < user_Query.size(); i++) {
//                            String query_DB = user_Query.get(i).getQuery_DB();
//                            queryDB_get.add(query_DB);
//                            Log.e("LiNing", "---FFFFFFFFFFFFFFFFF---"
//                                    + query_DB);
//                        }
//                        String stritem = new Gson().toJson(queryDB_get);
//                        sp.edit().putString("QueryDB", stritem).commit();
//                        Log.e("LiNing", "---FFFFFFFFFFFFFFFFF---" + stritem);
//                    }
                    List<com.example.bean.UserInfo.User_Mod> user_Mod = info
                            .getUser_Mod();
                    if ( user_Mod != null&&user_Mod.size()>0 ) {
                        for (int i = 0; i < user_Mod.size(); i++) {

                            String mod_ID = user_Mod.get(i).getMod_ID();
                            modIds_get.add(mod_ID);
                            sp.edit().putString("modIds", "" + modIds_get)
                                    .commit();
//							query = user_Mod.get(i).isMod_Query();
//							add = user_Mod.get(i).isMod_Add();
//							alter = user_Mod.get(i).isMod_Alter();
//							amt = user_Mod.get(i).isMod_Amt();
//							cst = user_Mod.get(i).isMod_Cst();
//							del = user_Mod.get(i).isMod_Del();
//							out = user_Mod.get(i).isMod_Out();
//							prt = user_Mod.get(i).isMod_Prt();
//							up = user_Mod.get(i).isMod_Up();
                            qty = user_Mod.get(i).isMod_Qty();// �ɱ�
                            gp = user_Mod.get(i).isMod_GP();// ë��
                            gpr = user_Mod.get(i).isMod_GPR();// ë����
                            sp.edit().putString("CB", "" + qty).commit();
                            sp.edit().putString("ML", "" + gp).commit();
                            sp.edit().putString("MLL", "" + gpr).commit();
                            qty = user_Mod.get(i).isMod_Qty();// �ɱ�
                            gp = user_Mod.get(i).isMod_GP();// ë��
                            gpr = user_Mod.get(i).isMod_GPR();// ë����

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
