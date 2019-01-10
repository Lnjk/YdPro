package com.example.ydshoa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.bean.CustAllObjectInfos;
import com.example.bean.URLS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestSearchActivity extends Activity {
    private Context context;
    private SharedPreferences sp;
    private String session, query_db;
    private TextView head;
    String cust_get = URLS.cust_z_query;
    List<CustAllObjectInfos.CustList> custList;
    private List<String> mData = new ArrayList<String>();  // 这个数据会改变
    private SearchView mSearchView;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_test_search);
        context = TestSearchActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        query_db = getIntent().getStringExtra("ZT_VIP");
        Log.e("LiNing", "所用账套====" + query_db);
        getAllInfos();
        initView();
    }
    private void initView() {
        head = (TextView) findViewById(R.id.all_head);
//        head.setText("设计师查询");
        head.setText("客户查询");
        mSearchView = (SearchView) findViewById(R.id.searchView);
        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(new ArrayAdapter<String>(TestSearchActivity.this,android.R.layout.simple_list_item_1,mData));
        mListView.setTextFilterEnabled(true);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!TextUtils.isEmpty(newText)){
                    mListView.setFilterText(newText);
                }else{
                    mListView.clearTextFilter();
                }
                return false;
            }
        });


//        lv_cust_qry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                qtyAdapter.setSelectItem(position);//刷新
//                qtyAdapter.notifyDataSetInvalidated();
////                CustesrAllInfos.CustList cust_callback = (CustesrAllInfos.CustList) parent.getAdapter().getItem(position);
//                CustAllObjectInfos.CustList cust_callback = (CustAllObjectInfos.CustList) parent.getAdapter().getItem(position);
//                Intent localIntent = getIntent();
//                localIntent.putExtra("CUST_QTY",cust_callback);
//                setResult(1, localIntent);
//                finish();
//            }
//        });
    }
    private void getAllInfos() {
        OkHttpClient client_all = new OkHttpClient();
        FormBody body_all = new FormBody.Builder()
                .add("Cust_Acc", "DB_BJ15")
                .build();
        Log.e("LiNing", "查询数据----" + query_db);
        Request request_all = new Request.Builder()
                .addHeader("cookie", session).url(cust_get)
                .post(body_all)
                .build();
        client_all.newCall(request_all).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String info_all = response.body().string();
                Log.e("LiNing", "id_type结果====" + info_all);
                Gson gson = new GsonBuilder().setDateFormat(
                        "yyyy-MM-dd HH:mm:ss").create();
                CustAllObjectInfos custesrAllInfos = gson.fromJson(info_all, CustAllObjectInfos.class);
//                CustAllObjectInfos custesrAllInfos = gson.fromJson(info_all, CustAllObjectInfos.class);
                Log.e("LiNing", "id_type结果====" + custesrAllInfos);
                if (custesrAllInfos != null) {
                    custList = custesrAllInfos.getCustList();
                    for(int i=0;i<custList.size();i++){
                        String cust_no = custList.get(i).getCust_No();
                        String cust_name = custList.get(i).getCust_Name();
                        String cust_tel = custList.get(i).getCust_Tel();
                        String infos=cust_no+"-"+cust_name+"-"+cust_tel;
                        mData.add(infos);
                    }
                    Log.e("LiNing", "mData结果====" + mData);
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }
    public void allback(View v) {
        finish();
    }
}
