package com.example.ydshoa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.CusterInfoDB;
import com.example.bean.JsonRootBean;
import com.example.bean.URLS;
import com.example.bean.VipInfos;
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

import static okhttp3.Request.*;

public class VipClassActivity extends Activity implements View.OnClickListener {
    private Context context;
    private SharedPreferences sp;
    private String session,DB;
    private TextView head, sfty,add_vp, add_vp_dj;
    private EditText vipno, vipname,  add_vp_bh, add_vp_mc;
    private Button vipquery, add, del, set, get,all_zsg,flash;
    private ImageButton vipbtn;
    private ListView lv_vip;
    AlertDialog addVip,addVip_all,addVip_all_add,addVip_all_del,addVip_all_set;
    //接口
    String vip_get = URLS.vip_query;
    String vip_add = URLS.vip_add;
    List<VipInfos.TypeList> obj_typeList;
    VipAdapter vipAdapter;
    VipInfos.TypeList typeList;//item数据
//    int itemOnclik=0;
    String biln_type_exra,db_id_exra,type_id_exra,type_name_exra;//item数据传递
    ArrayList<String> id_type,name_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_vip_class);
        context = VipClassActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        DB=getIntent().getStringExtra("ZT_VIP");
        Log.e("LiNing","所用账套===="+DB);
        initView();//初始化
    }

    private void initView() {
        head = (TextView) findViewById(R.id.all_head);
        head.setText("VIP类别");
        lv_vip = (ListView) findViewById(R.id.lv_vip_header);
        vipno = (EditText) findViewById(R.id.vip_no);
        vipname = (EditText) findViewById(R.id.et_vip_name);
        vipquery = (Button) findViewById(R.id.vip_query);
        all_zsg = (Button) findViewById(R.id.btn_vip_zsg);
        flash = (Button) findViewById(R.id.btn_vip_sx);
        flash.setOnClickListener(this);
        all_zsg.setOnClickListener(this);
        vipquery.setOnClickListener(this);
        getObjectType();
        lv_vip.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("LiNing","所选数据===="+position);
                VipInfos.TypeList typeList = (VipInfos.TypeList) parent.getAdapter().getItem(position);
                String type_id = typeList.getType_ID();
                String type_name = typeList.getType_Name();
                Log.e("LiNing","所选数据===="+type_id+type_name);

                Intent localIntent = getIntent();
                localIntent.putExtra("VIP_ID", type_id);
                localIntent.putExtra("VIP_NAME", type_name);
                setResult(1, localIntent);
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vip_query:
                Toast.makeText(context, "查询所有", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_vip_sx:
                getObjectType();
                break;

            case R.id.btn_vip_zsg:
                Intent intent_vip = new Intent(context, VipDoAllActivity.class);
                intent_vip.putExtra("ZT_VIP", DB);
                startActivity(intent_vip);
                break;
        }
    }

    //获取vip所有信息
    private void getObjectType() {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("db_Id",DB)
                .add("biln_Type","VP")
                .build();
        client.newCall(
                new Builder().addHeader("cookie", session).url(vip_get)
                        .post(body).build()).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                String str = response.body().string();
                Log.e("LiNing", "信息数据====" + str);
                // 解析包含date的数据必须添加此代码(InputStream型)
                Gson gson = new GsonBuilder().setDateFormat(
                        "yyyy-MM-dd HH:mm:ss").create();
                final VipInfos vip_data = gson.fromJson(str,
                        VipInfos.class);
                if (vip_data != null) {
                    VipClassActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            obj_typeList = vip_data.getTypeList();
                            vipAdapter = new VipAdapter(R.layout.vip_item, obj_typeList,
                                    VipClassActivity.this);
                            lv_vip.setAdapter(vipAdapter);
                            vipAdapter.notifyDataSetChanged();
//                            checkedIndexList.clear();
//                            idList_vip.clear();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call arg0, IOException arg1) {

            }
        });
    }

    public class VipAdapter extends BaseAdapter{
        int id_row_layout;
        LayoutInflater mInflater;
        List<VipInfos.TypeList> obj_data;
        //item高亮显示
        private int selectItem = -1;
        public VipAdapter(int vip_item, List<VipInfos.TypeList> obj_typeList, VipClassActivity vipClassActivity) {
            this.id_row_layout = vip_item;
            this.mInflater = LayoutInflater.from(vipClassActivity);
            this.obj_data=obj_typeList;
        }
        public void setSelectItem(int selectItem) {
            this.selectItem = selectItem;
        }

        @Override
        public int getCount() {
            return obj_data.size();
        }

        @Override
        public Object getItem(int position) {
            return obj_data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vip_holder=null;
            if(convertView==null){
                convertView=mInflater.inflate(id_row_layout,null);
                vip_holder=new ViewHolder();
                vip_holder.vip_id= (TextView) convertView.findViewById(R.id.tv_vipno);
                vip_holder.vip_name= (TextView) convertView.findViewById(R.id.tv_vipname);
                convertView.setTag(vip_holder);
            }else{
                vip_holder= (ViewHolder) convertView.getTag();
            }

            typeList = obj_data.get(position);
            String type_id = typeList.getType_ID();
            String type_name = typeList.getType_Name();
            vip_holder.vip_id.setText(type_id);
            vip_holder.vip_name.setText(type_name);
            if (position == selectItem) {
                convertView.setBackgroundColor(Color.RED);
//                CheckBoxListener(position);
            } else {
                convertView.setBackgroundColor(Color.TRANSPARENT);
            }
            return convertView;
        }
        class ViewHolder {
            public TextView vip_id;
            public TextView vip_name;
        }
    }

    private void CheckBoxListener(int position) {
        VipInfos.TypeList typeList = obj_typeList.get(position);
        biln_type_exra = typeList.getBiln_Type();
        db_id_exra = typeList.getDb_Id();
        type_id_exra = typeList.getType_ID();
        type_name_exra = typeList.getType_Name();
        Log.e("LiNing", "id_type结果====" + type_id_exra + "-----" + type_name_exra);
//        itemOnclik = 2;
    }

    public void allback(View v) {
        finish();
    }
}
