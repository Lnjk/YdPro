package com.example.ydshoa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.CustAllObjectInfos;
import com.example.bean.CustesrAllInfos;
import com.example.bean.DesignAllInfos;
import com.example.bean.URLS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QueryCustersActivity extends Activity {
    private Context context;
    private SharedPreferences sp;
    private String session, query_db;
    private TextView head;
    private ListView lv_cust_qry;
    String cust_get = URLS.cust_z_query;
//    List<CustesrAllInfos.CustList> custList;
    List<CustAllObjectInfos.CustList> custList;
    CustersQtyAdapter qtyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_query_custers);
        context = QueryCustersActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        query_db = getIntent().getStringExtra("ZT_VIP");
        Log.e("LiNing", "所用账套====" + query_db);
        initView();
    }

    private void initView() {
        head = (TextView) findViewById(R.id.all_head);
//        head.setText("设计师查询");
        head.setText("客户查询");
        lv_cust_qry = (ListView) findViewById(R.id.lv_cust_header_query);
        getAllInfos();
        lv_cust_qry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                qtyAdapter.setSelectItem(position);//刷新
                qtyAdapter.notifyDataSetInvalidated();
//                CustesrAllInfos.CustList cust_callback = (CustesrAllInfos.CustList) parent.getAdapter().getItem(position);
                CustAllObjectInfos.CustList cust_callback = (CustAllObjectInfos.CustList) parent.getAdapter().getItem(position);
                    Intent localIntent = getIntent();
                    localIntent.putExtra("CUST_QTY",cust_callback);
                    setResult(1, localIntent);
                    finish();
            }
        });
    }

    private void getAllInfos() {
        OkHttpClient client_all = new OkHttpClient();
        FormBody body_all = new FormBody.Builder()
                .add("Cust_Acc", query_db)
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
                    QueryCustersActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            qtyAdapter = new CustersQtyAdapter(R.layout.custer_head, custList, context);
                            lv_cust_qry.setAdapter(qtyAdapter);
                            qtyAdapter.notifyDataSetChanged();
                        }

                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    public class CustersQtyAdapter extends BaseAdapter {
        int id_row_layout;
        LayoutInflater mInflater;
        List<CustAllObjectInfos.CustList> custqty_infos;
        //item高亮显示
        private int selectItem = -1;

        public void setSelectItem(int selectItem) {
            this.selectItem = selectItem;
        }

        public CustersQtyAdapter(int custer_head, List<CustAllObjectInfos.CustList> custList, Context context) {
            this.id_row_layout = custer_head;
            this.custqty_infos = custList;
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return custqty_infos.size();
        }

        @Override
        public Object getItem(int position) {
            return custqty_infos.get(position);
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
                holder.cust_dabh_qty = (TextView) convertView.findViewById(R.id.cust_listDeleteCheckBox);
                holder.cust_zt_qty = (TextView) convertView.findViewById(R.id.textView2_cust_zt);
                holder.cust_xm_qty = (TextView) convertView.findViewById(R.id.textView3_cust_name);
                holder.cust_dh_qty = (TextView) convertView.findViewById(R.id.textView4_cust_phone);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            CustAllObjectInfos.CustList custList_adp = custqty_infos.get(position);
            holder.cust_dabh_qty.setText(custList_adp.getCust_No());
            holder.cust_zt_qty.setText(custList_adp.getCust_Acc());
            holder.cust_xm_qty.setText(custList_adp.getCust_Name());
            holder.cust_dh_qty.setText(custList_adp.getCust_Tel());
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
            HorizontalScrollView scrollView;
            public TextView cust_dabh_qty;
            public TextView cust_zt_qty;
            public TextView cust_xm_qty;
            public TextView cust_dh_qty;
        }
    }
    public void allback(View v) {
        finish();
    }
}
