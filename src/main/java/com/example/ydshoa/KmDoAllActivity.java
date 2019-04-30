package com.example.ydshoa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class KmDoAllActivity extends Activity {
    private Context context;
    private SharedPreferences sp;
    private String session, DB;
    private TextView head, sfty, add_vp, add_vp_dj, add_vp_nulladd, add_vp_dj_nulladd;
    private ListView lv_jf_all;
    private Button jfquery, add, del, set;
    private EditText add_vp_bh, add_vp_mc, add_vp_bh_nulladd, add_vp_mc_nulladd;
    //接口
    String jf_get = URLS.vip_query;
    String jf_add = URLS.vip_add;
    String jf_del = URLS.vip_del;
    String jf_set = URLS.vip_updata;
    List<VipInfos.TypeList> obj_typeList;
    JfAllAdapter jfAdapter;
    VipInfos.TypeList typeList;//item数据
    int itemOnclik = 0;
    String biln_type_exra, db_id_exra, type_id_exra, type_name_exra;//item数据传递
    ArrayList<String> id_type, name_type;
    AlertDialog addjf_all, addjf_all_add, addjf_all_del, addjf_all_set;
    //更新数据
    private ArrayList<String> jf_DB_IDs, jf_BILES, jf_TYPE_IDs, jf_TYPE_NAMEs;
    private ImageButton null_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_km_do_all);
        context = KmDoAllActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        DB = getIntent().getStringExtra("ZT_VIP");
        Log.e("LiNing", "所用账套====" + DB);
        lv_jf_all = (ListView) findViewById(R.id.lv_km_header_all);
        getObjectType();
        initView();//初始化
    }

    private void initView() {
        head = (TextView) findViewById(R.id.all_head);
        head.setText("款别类型");

        null_add = (ImageButton) findViewById(R.id.ib_nulladd);
        //封装新数据（新+旧=更新）
        jf_DB_IDs = new ArrayList<String>();
        jf_BILES = new ArrayList<String>();
        jf_TYPE_IDs = new ArrayList<String>();
        jf_TYPE_NAMEs = new ArrayList<String>();
        if (lv_jf_all.getCount() > 0) {
            null_add.setVisibility(View.GONE);
        } else {
            null_add.setVisibility(View.VISIBLE);
//            Toast.makeText(context,"请添加新数据",Toast.LENGTH_LONG).show();
        }
        null_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view_load_all_add_nulladd = getLayoutInflater()
                        .inflate(R.layout.null_add_new_km, null);
                TextView head_load_all = (TextView) view_load_all_add_nulladd.findViewById(R.id.all_head);
                head_load_all.setText("款别类型-添加");

                add_vp_nulladd = (TextView) view_load_all_add_nulladd.findViewById(R.id.et_jf_type_nulladd_jf);
                add_vp_nulladd.setText("RQ");
                add_vp_dj_nulladd = (TextView) view_load_all_add_nulladd.findViewById(R.id.et_biln_type_nulladd_jf);
                add_vp_dj_nulladd.setText(DB);
                add_vp_bh_nulladd = (EditText) view_load_all_add_nulladd.findViewById(R.id.et_type_id_nulladd_jf);
                add_vp_mc_nulladd = (EditText) view_load_all_add_nulladd.findViewById(R.id.et_type_name_nulladd_jf);
                view_load_all_add_nulladd.findViewById(R.id.imageButton1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addjf_all_add.dismiss();
//                                    getObjectType();
                    }
                });
                view_load_all_add_nulladd.findViewById(R.id.btn_jf_add_nulladd_jf).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String add_id = add_vp_bh_nulladd.getText().toString();
                        String add_name = add_vp_mc_nulladd.getText().toString();
                        OkHttpClient client = new OkHttpClient();
                        FormBody body = new FormBody.Builder()
                                .add("db_Id", DB)
                                .add("biln_Type", "RQ")
                                .add("Type_ID", add_id)
                                .add("Type_Name", add_name)
                                .build();
                        client.newCall(new Request.Builder().addHeader("cookie", session).url(jf_add)
                                .post(body).build()).enqueue(new Callback() {
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String str = response.body().string();
                                Log.e("LiNing", "添加结果====" + str);
                                final JsonRootBean localJsonRootBean = (JsonRootBean) new Gson()
                                        .fromJson(str, JsonRootBean.class);
                                if (localJsonRootBean != null) {
                                    KmDoAllActivity.this.runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            boolean rlo = localJsonRootBean.getRLO();
                                            if (rlo == true) {
                                                Toast.makeText(KmDoAllActivity.this,
                                                        "款别新增成功", Toast.LENGTH_SHORT).show();
                                                getObjectType();
                                                if (addjf_all_add.isShowing()) {
                                                    addjf_all_add.dismiss();
                                                }
                                            } else if (rlo == false) {
                                                Toast.makeText(KmDoAllActivity.this,
                                                        "款别新增失败", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onFailure(Call call, IOException e) {

                            }
                        });
                    }
                });
                addjf_all_add = new AlertDialog.Builder(context).create();
                addjf_all_add.setCancelable(false);
                addjf_all_add.setView(view_load_all_add_nulladd);
                addjf_all_add.show();
            }
        });
        lv_jf_all.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //此处获取数据不显示
//                    jfAdapter.setSelectItem(position);//刷新/
//                    jfAdapter.notifyDataSetInvalidated();
                Log.e("LiNing", "所选数据====" + position);
                VipInfos.TypeList typeList = (VipInfos.TypeList) parent.getAdapter().getItem(position);
                biln_type_exra = typeList.getBiln_Type();
                jf_BILES.add(biln_type_exra);
                db_id_exra = typeList.getDb_Id();
                jf_DB_IDs.add(db_id_exra);
                type_id_exra = typeList.getType_ID();
                jf_TYPE_IDs.add(type_id_exra);
                type_name_exra = typeList.getType_Name();
                jf_TYPE_NAMEs.add(type_name_exra);
                Log.e("LiNing", "id_type结果====" + type_id_exra + "-----" + type_name_exra + biln_type_exra + "-----" + db_id_exra);
                itemOnclik = 1;

                View view_load_all_add = getLayoutInflater()
                        .inflate(R.layout.add_km_dialog, null);
                TextView head_load_all = (TextView) view_load_all_add.findViewById(R.id.all_head);
                head_load_all.setText("款别-操作");
                add_vp = (TextView) view_load_all_add.findViewById(R.id.et_jf_type_jf);
                add_vp.setText(biln_type_exra);
                add_vp_dj = (TextView) view_load_all_add.findViewById(R.id.et_biln_type_jf);
                add_vp_dj.setText(db_id_exra);
                add_vp_bh = (EditText) view_load_all_add.findViewById(R.id.et_type_id_jf);
                add_vp_bh.setText(type_id_exra);
                add_vp_mc = (EditText) view_load_all_add.findViewById(R.id.et_type_name_jf);
                add_vp_mc.setText(type_name_exra);
                view_load_all_add.findViewById(R.id.imageButton1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addjf_all_add.dismiss();
//                                    getObjectType();
                    }
                });

                view_load_all_add.findViewById(R.id.btn_jf_add_all_jf).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (lv_jf_all != null && lv_jf_all.getCount() > 0) {

                            id_type = new ArrayList<String>();
                            name_type = new ArrayList<String>();
                            for (int i = 0; i < lv_jf_all.getCount(); i++) {
                                VipInfos.TypeList typeList_all = (VipInfos.TypeList) lv_jf_all.getAdapter().getItem(i);
                                String type_id = typeList_all.getType_ID();
                                String type_name = typeList_all.getType_Name();
                                id_type.add(type_id);
                                name_type.add(type_name);
                            }
                            Log.e("LiNing", "id_type结果====" + id_type + "-----" + name_type);
                        }
                        String add_id = add_vp_bh.getText().toString();
                        String add_name = add_vp_mc.getText().toString();
                        Log.e("LiNing", "id_type结果====" + id_type);
                        Log.e("LiNing", "add_id结果====" + add_id);
                        //判断数据
                        if (id_type.contains(add_id)) {
                            Toast.makeText(KmDoAllActivity.this,
                                    "数据已存在", Toast.LENGTH_SHORT).show();
                        } else {
                            OkHttpClient client = new OkHttpClient();
                            FormBody body = new FormBody.Builder()
                                    .add("db_Id", DB)
                                    .add("biln_Type", "RQ")
                                    .add("Type_ID", add_id)
                                    .add("Type_Name", add_name)
                                    .build();
                            client.newCall(
                                    new Request.Builder().addHeader("cookie", session).url(jf_add)
                                            .post(body).build()).enqueue(new Callback() {

                                @Override
                                public void onResponse(Call call, Response response)
                                        throws IOException {
                                    String str = response.body().string();
                                    Log.e("LiNing", "添加结果====" + str);
                                    final JsonRootBean localJsonRootBean = (JsonRootBean) new Gson()
                                            .fromJson(str, JsonRootBean.class);
                                    if (localJsonRootBean != null) {
                                        KmDoAllActivity.this.runOnUiThread(new Runnable() {

                                            @Override
                                            public void run() {
                                                boolean rlo = localJsonRootBean.getRLO();
                                                if (rlo == true) {
                                                    Toast.makeText(KmDoAllActivity.this,
                                                            "款别新增成功", Toast.LENGTH_SHORT).show();
                                                    getObjectType();
                                                    if (addjf_all_add.isShowing()) {
                                                        addjf_all_add.dismiss();
                                                    }
                                                } else if (rlo == false) {
                                                    Toast.makeText(KmDoAllActivity.this,
                                                            "款别新增失败", Toast.LENGTH_SHORT).show();
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
                });
                view_load_all_add.findViewById(R.id.btn_jf_del_all_jf).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(context)
                                .setTitle("是否删除")
                                .setPositiveButton("是",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {

                                                Log.e("LiNing", "--------删除的id==="
                                                        + type_id_exra);
                                                delCust();
                                            }
                                        }).setNegativeButton("否", null).show();
                    }
                });
                view_load_all_add.findViewById(R.id.btn_jf_reset_all_jf).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String add_vp_new = add_vp.getText().toString();
                        jf_BILES.add(add_vp_new);
                        String add_dj_new = add_vp_dj.getText().toString();
                        jf_DB_IDs.add(add_dj_new);
                        String add_id_new = add_vp_bh.getText().toString();
                        jf_TYPE_IDs.add(add_id_new);
                        String add_name_new = add_vp_mc.getText().toString();
                        jf_TYPE_NAMEs.add(add_name_new);
                        Log.e("LiNing", "修改结果====" + jf_BILES + "---" + jf_DB_IDs + "---" + jf_TYPE_IDs + "---" + jf_TYPE_NAMEs);
                        String zts_str = "";
                        for (String zt : jf_DB_IDs) {
                            zts_str += zt + ",";
                        }
                        String sub_id_jf = zts_str.substring(0, zts_str.length() - 1);
                        String vp_str = "";
                        for (String zt : jf_BILES) {
                            vp_str += zt + ",";
                        }
                        String sub_vp_jf = vp_str.substring(0, vp_str.length() - 1);
                        String typeid_str = "";
                        for (String zt : jf_TYPE_IDs) {
                            typeid_str += zt + ",";
                        }
                        String sub_typeid_jf = typeid_str.substring(0, typeid_str.length() - 1);
                        String typename_str = "";
                        for (String zt : jf_TYPE_NAMEs) {
                            typename_str += zt + ",";
                        }
                        String sub_typename_jf = typename_str.substring(0, typename_str.length() - 1);
                        //判断数据
                        OkHttpClient client = new OkHttpClient();
                        FormBody body = new FormBody.Builder()
                                .add("db_Id", sub_id_jf)
                                .add("biln_Type", sub_vp_jf)
                                .add("Type_ID", sub_typeid_jf)
                                .add("Type_Name", sub_typename_jf)
//                                    .add("db_Id", DB)
//                                    .add("biln_Type", "VP")
//                                    .add("Type_ID", add_id)
//                                    .add("Type_Name", add_name)
                                .build();
                        client.newCall(
                                new Request.Builder().addHeader("cookie", session).url(jf_set)
                                        .post(body).build()).enqueue(new Callback() {

                            @Override
                            public void onResponse(Call call, Response response)
                                    throws IOException {
                                String str = response.body().string();
                                Log.e("LiNing", "修改结果====" + str);
                                final JsonRootBean localJsonRootBean = (JsonRootBean) new Gson()
                                        .fromJson(str, JsonRootBean.class);
                                if (localJsonRootBean != null) {
                                    KmDoAllActivity.this.runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            boolean rlo = localJsonRootBean.getRLO();
                                            if (rlo == true) {
                                                Toast.makeText(KmDoAllActivity.this,
                                                        "款别修改成功", Toast.LENGTH_SHORT).show();
                                                getObjectType();
                                                if (addjf_all_add.isShowing()) {
                                                    addjf_all_add.dismiss();
                                                }
                                            } else if (rlo == false) {
                                                Toast.makeText(KmDoAllActivity.this,
                                                        "款别修改失败", Toast.LENGTH_SHORT).show();
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
                });

                addjf_all_add = new AlertDialog.Builder(context).create();
                addjf_all_add.setCancelable(false);
                addjf_all_add.setView(view_load_all_add);
                addjf_all_add.show();
            }
        });
    }

    private void delCust() {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("db_Id", db_id_exra)
                .add("biln_Type", biln_type_exra)
                .add("Type_ID", type_id_exra)
                .add("Type_Name", type_name_exra)
                .build();
        client.newCall(
                new Request.Builder().addHeader("cookie", session).url(jf_del)
                        .post(body).build()).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                String str = response.body().string();
                Log.e("LiNing", "信息数据====" + str);
                final JsonRootBean localJsonRootBean = (JsonRootBean) new Gson()
                        .fromJson(str, JsonRootBean.class);
                if (localJsonRootBean != null) {
                    KmDoAllActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            boolean rlo = localJsonRootBean.getRLO();
                            if (rlo == true) {
                                Toast.makeText(KmDoAllActivity.this,
                                        "信息删除成功", Toast.LENGTH_SHORT).show();
                                if (addjf_all_add.isShowing()) {
                                    addjf_all_add.dismiss();
                                }
                                getObjectType();
                            } else if (rlo == false) {
                                Toast.makeText(KmDoAllActivity.this,
                                        "信息删除失败", Toast.LENGTH_SHORT).show();
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

    //获取vip所有信息
    private void getObjectType() {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("db_Id", DB)
                .add("biln_Type", "RQ")
                .build();
        client.newCall(
                new Request.Builder().addHeader("cookie", session).url(jf_get)
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
                    KmDoAllActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            obj_typeList = vip_data.getTypeList();
                            jfAdapter = new JfAllAdapter(R.layout.vip_item, obj_typeList,
                                    KmDoAllActivity.this);
                            lv_jf_all.setAdapter(jfAdapter);
                            jfAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call arg0, IOException arg1) {

            }
        });
    }

    public class JfAllAdapter extends BaseAdapter {
        int id_row_layout;
        LayoutInflater mInflater;
        List<VipInfos.TypeList> obj_data;
        //item高亮显示
        private int selectItem = -1;

        public JfAllAdapter(int vip_item, List<VipInfos.TypeList> obj_typeList, KmDoAllActivity KmDoAllActivity) {
            this.id_row_layout = vip_item;
            this.mInflater = LayoutInflater.from(KmDoAllActivity);
            this.obj_data = obj_typeList;
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
            ViewHolder vip_holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(id_row_layout, null);
                vip_holder = new ViewHolder();
                vip_holder.vip_id = (TextView) convertView.findViewById(R.id.tv_vipno);
                vip_holder.vip_name = (TextView) convertView.findViewById(R.id.tv_vipname);
                convertView.setTag(vip_holder);
            } else {
                vip_holder = (ViewHolder) convertView.getTag();
            }

            typeList = obj_data.get(position);
            String type_id = typeList.getType_ID();
            String type_name = typeList.getType_Name();
            vip_holder.vip_id.setText(type_id);
            vip_holder.vip_name.setText(type_name);
            if (position == selectItem) {
                convertView.setBackgroundColor(Color.RED);
                Log.e("LiNing", "id_type结果====" + type_id + type_name);
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

    public void allback(View v) {
        finish();
    }
}
