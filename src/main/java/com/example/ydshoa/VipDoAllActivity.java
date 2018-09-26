package com.example.ydshoa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class VipDoAllActivity extends Activity implements View.OnClickListener {
    private Context context;
    private SharedPreferences sp;
    private String session,DB;
    private TextView head, sfty,add_vp, add_vp_dj;
    private ListView lv_vip_all;
    private Button vipquery, add, del, set;
    //接口
    String vip_get = URLS.vip_query;
    String vip_add = URLS.vip_add;
    List<VipInfos.TypeList> obj_typeList;
    VipAllAdapter vipAdapter;
    VipInfos.TypeList typeList;//item数据
    int itemOnclik=0;
    String biln_type_exra,db_id_exra,type_id_exra,type_name_exra;//item数据传递
    ArrayList<String> id_type,name_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_do_all);
        context = VipDoAllActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        DB=getIntent().getStringExtra("ZT_VIP");
        Log.e("LiNing","所用账套===="+DB);
        initView();//初始化
        getObjectType();
    }

    private void initView() {
        head = (TextView) findViewById(R.id.all_head);
        head.setText("VIP类别");
        add = (Button) findViewById(R.id.btn_vip_add_all);
        del = (Button) findViewById(R.id.btn_vip_del_all);
        set = (Button) findViewById(R.id.btn_vip_reset_all);
        vipquery.setOnClickListener(this);
        add.setOnClickListener(this);
        del.setOnClickListener(this);
        set.setOnClickListener(this);
        lv_vip_all = (ListView) findViewById(R.id.lv_vip_header_all);
        findViewById(R.id.imageButton1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//返回上一个界面
                if (lv_vip_all.getCount() > 0) {

                    lv_vip_all.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            //此处获取数据不显示
                        vipAdapter.setSelectItem(position);//刷新/
                        vipAdapter.notifyDataSetInvalidated();
                        Adapter adapter = parent.getAdapter();
                        VipInfos.TypeList typeList = (VipInfos.TypeList) adapter.getItem(position);
                        Log.e("LiNing", "id_type结果====" + type_id_exra + "-----" + type_name_exra);
                        biln_type_exra = typeList.getBiln_Type();
                        db_id_exra = typeList.getDb_Id();
                        type_id_exra = typeList.getType_ID();
                        type_name_exra = typeList.getType_Name();
                        Log.e("LiNing", "id_type结果====" + type_id_exra + "-----" + type_name_exra);
                        itemOnclik = 2;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btn_vip_add_all:
////                if(zt.getText().toString().equals("")){
////                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
////                }else{
////
////                }
//                Intent intent_vip = new Intent(context, VipDoAllActivity.class);
////                intent_vip.putExtra("ZT_VIP", zt.getText().toString());
//                startActivity(intent_vip);
////                Toast.makeText(context, "查询所有", Toast.LENGTH_LONG).show();
//                itemOnclik = 1;
//                View view_load_all = getLayoutInflater()
//                        .inflate(R.layout.vip_all_do, null);
//                TextView head_load_all = (TextView) view_load_all.findViewById(R.id.all_head);
//                head_load_all.setText("VIP类别操作");
//
//
//
//                view_load_all.findViewById(R.id.btn_vip_add_all).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Log.e("LiNing", "id_type结果====" + itemOnclik);
//                        if(itemOnclik==2) {
//                            Toast.makeText(context, "新增", Toast.LENGTH_LONG).show();
//                            View view_load_all_add = getLayoutInflater()
//                                    .inflate(R.layout.add_vip_dialog, null);
//                            TextView head_load_all = (TextView) view_load_all_add.findViewById(R.id.all_head);
//                            head_load_all.setText("VIP-新增");
//                            add_vp = (TextView) view_load_all_add.findViewById(R.id.et_vip_type);
//                            add_vp.setText(biln_type_exra);
//                            add_vp_dj = (TextView) view_load_all_add.findViewById(R.id.et_biln_type);
//                            add_vp_dj.setText(db_id_exra);
////                            add_vp_bh = (EditText) view_load_all_add.findViewById(R.id.et_type_id);
////                            add_vp_bh.setText(type_id_exra);
////                            add_vp_mc = (EditText) view_load_all_add.findViewById(R.id.et_type_name);
////                            add_vp_mc.setText(type_name_exra);
////                            view_load_all_add.findViewById(R.id.imageButton1).setOnClickListener(new View.OnClickListener() {
////                                @Override
////                                public void onClick(View v) {
////                                    addVip.dismiss();
////                                    getObjectType();
////                                }
////                            });
//                            view_load_all_add.findViewById(R.id.btn_vip_ok).setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    if(lv_vip_all!=null&&lv_vip_all.getCount()>0){
//
//                                        id_type = new ArrayList<String>();
//                                        name_type = new ArrayList<String>();
//                                        for(int i=0;i<lv_vip_all.getCount();i++){
//                                            VipInfos.TypeList typeList_all = (VipInfos.TypeList) lv_vip_all.getAdapter().getItem(i);
//                                            String type_id = typeList_all.getType_ID();
//                                            String type_name = typeList_all.getType_Name();
//                                            id_type.add(type_id);
//                                            name_type.add(type_name);
//                                        }
//                                        Log.e("LiNing", "id_type结果====" + id_type+"-----"+name_type);
//                                    }
////                                    String add_id = add_vp_bh.getText().toString();
////                                    String add_name = add_vp_mc.getText().toString();
//                                    Log.e("LiNing", "id_type结果====" + id_type);
//                                    Log.e("LiNing", "add_id结果====" + add_id);
//                                    //判断数据
//                                    if (id_type.contains(add_id)) {
//                                        Toast.makeText(VipDoAllActivity.this,
//                                                "数据已存在", Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        OkHttpClient client = new OkHttpClient();
//                                        FormBody body = new FormBody.Builder()
//                                                .add("db_Id", DB)
//                                                .add("biln_Type", "VP")
//                                                .add("Type_ID", add_id)
//                                                .add("Type_Name", add_name)
//                                                .build();
//                                        client.newCall(
//                                                new Request.Builder().addHeader("cookie", session).url(vip_add)
//                                                        .post(body).build()).enqueue(new Callback() {
//
//                                            @Override
//                                            public void onResponse(Call call, Response response)
//                                                    throws IOException {
//                                                String str = response.body().string();
//                                                Log.e("LiNing", "添加结果====" + str);
//                                                final JsonRootBean localJsonRootBean = (JsonRootBean) new Gson()
//                                                        .fromJson(str, JsonRootBean.class);
//                                                if (localJsonRootBean != null) {
//                                                    VipDoAllActivity.this.runOnUiThread(new Runnable() {
//
//                                                        @Override
//                                                        public void run() {
//                                                            boolean rlo = localJsonRootBean.getRLO();
//                                                            if (rlo == true) {
//                                                                Toast.makeText(VipDoAllActivity.this,
//                                                                        "vip新增成功", Toast.LENGTH_SHORT).show();
//                                                                getObjectType();
//                                                                if (addVip_all_add.isShowing()) {
//                                                                    addVip_all_add.dismiss();
//                                                                }
//                                                            } else if (rlo == false) {
//                                                                Toast.makeText(VipDoAllActivity.this,
//                                                                        "vip新增失败", Toast.LENGTH_SHORT).show();
//                                                            }
//                                                        }
//                                                    });
//                                                }
//                                            }
//
//                                            @Override
//                                            public void onFailure(Call arg0, IOException arg1) {
//
//                                            }
//                                        });
//                                    }
//                                }
//                            });
//                            addVip_all_add = new AlertDialog.Builder(context).create();
//                            addVip_all_add.setCancelable(false);
//                            addVip_all_add.setView(view_load_all_add);
//                            addVip_all_add.show();
//                        }else{
//                            Toast.makeText(VipDoAllActivity.this,
//                                    "请选择数据", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
//                addVip_all = new AlertDialog.Builder(context).create();
//                addVip_all.setCancelable(false);
//                addVip_all.setView(view_load_all);
//                addVip_all.show();
//                break;

            case R.id.btn_vip_del_all:
                Toast.makeText(context, "删除", Toast.LENGTH_LONG).show();
//                if (checkedIndexList != null && checkedIndexList.size() > 0) {
//                    new AlertDialog.Builder(context)
//                            .setTitle("是否删除")
//                            .setPositiveButton("是",
//                                    new DialogInterface.OnClickListener() {
//
//                                        @Override
//                                        public void onClick(
//                                                DialogInterface dialog,
//                                                int which) {
//
//                                            Log.e("LiNing", "--------删除的id==="
//                                                    + sub_id);
//                                            delCust();
//                                        }
//                                    }).setNegativeButton("否", null).show();
//                }else{
//                    Toast.makeText(context, "请选择数据", Toast.LENGTH_LONG).show();
//                }
                break;
            case R.id.btn_vip_reset_all:
                Toast.makeText(context, "修改", Toast.LENGTH_LONG).show();
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
                new Request.Builder().addHeader("cookie", session).url(vip_get)
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
                    VipDoAllActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            obj_typeList = vip_data.getTypeList();
                            Log.e("LiNing",
                                    "typeList.size()-------" + obj_typeList.size()+itemOnclik);
                            vipAdapter = new VipAllAdapter(R.layout.vip_item, obj_typeList,
                                    VipDoAllActivity.this);
                                lv_vip_all.setAdapter(vipAdapter);
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

   

    public class VipAllAdapter extends BaseAdapter {
        int id_row_layout;
        LayoutInflater mInflater;
        List<VipInfos.TypeList> obj_data;
        //item高亮显示
        private int selectItem = -1;
        public VipAllAdapter(int vip_item, List<VipInfos.TypeList> obj_typeList, VipDoAllActivity vipDoAllActivity) {
            this.id_row_layout = vip_item;
            this.mInflater = LayoutInflater.from(vipDoAllActivity);
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
            Log.e("LiNing",
                    "onclikItem-------" );
            if (position == selectItem) {
//
                convertView.setBackgroundColor(Color.RED);
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
}