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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class KmClassActivity extends Activity implements View.OnClickListener {
    private Context context;
    private SharedPreferences sp;
    private String session,DB;
    private TextView head, sfty,add_vp, add_vp_dj;
    private EditText jfno, jfname,  add_vp_bh, add_vp_mc;
    private Button jfquery, add, del, set, get,all_zsg,flash;
    private ImageButton jfbtn;
    private ListView lv_jf;
    //接口
    String jf_get = URLS.vip_query;
    List<VipInfos.TypeList> obj_typeList;
    JfAdapter jfAdapter;
    VipInfos.TypeList typeList;//item数据
    //    int itemOnclik=0;
    String biln_type_exra,db_id_exra,type_id_exra,type_name_exra;//item数据传递
    ArrayList<String> id_type,name_type;
    //查询
    String et_id_query,et_name_query;
    int cussor;
    private FormBody body;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_km_class);
        context = KmClassActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        DB=getIntent().getStringExtra("ZT_VIP");
        Log.e("LiNing","所用账套===="+DB);
        initView();//初始化
    }

    private void initView() {
        head = (TextView) findViewById(R.id.all_head);
        head.setText("款别类型");
        lv_jf = (ListView) findViewById(R.id.lv_km_header);
        all_zsg = (Button) findViewById(R.id.btn_km_zsg);
        flash = (Button) findViewById(R.id.btn_km_sx);
        flash.setOnClickListener(this);
        all_zsg.setOnClickListener(this);
        getObjectType();
        lv_jf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("LiNing","所选数据===="+position);
                VipInfos.TypeList typeList = (VipInfos.TypeList) parent.getAdapter().getItem(position);
                String type_id = typeList.getType_ID();
                String type_name = typeList.getType_Name();
                Log.e("LiNing","所选数据===="+type_id+type_name);

                Intent localIntent = getIntent();
                localIntent.putExtra("jf_ID", type_id);
                localIntent.putExtra("jf_NAME", type_name);
                setResult(1, localIntent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_km_sx:
                getObjectType();
                break;

            case R.id.btn_km_zsg:
                Intent intent_jf = new Intent(context, KmDoAllActivity.class);
                intent_jf.putExtra("ZT_VIP", DB);
                startActivity(intent_jf);
                break;
            default:
                break;
        }

    }
    //获取vip所有信息
    private void getObjectType() {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("db_Id",DB)
                .add("biln_Type","RQ")
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
                    KmClassActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            obj_typeList = vip_data.getTypeList();
                            jfAdapter = new JfAdapter(R.layout.vip_item, obj_typeList,
                                    KmClassActivity.this);
                            lv_jf.setAdapter(jfAdapter);
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
    public class JfAdapter extends BaseAdapter {
        int id_row_layout;
        LayoutInflater mInflater;
        List<VipInfos.TypeList> obj_data;
        //item高亮显示
        private int selectItem = -1;
        public JfAdapter(int vip_item, List<VipInfos.TypeList> obj_typeList, KmClassActivity kmClassActivity) {
            this.id_row_layout = vip_item;
            this.mInflater = LayoutInflater.from(kmClassActivity);
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
    public void allback(View v) {
        finish();
    }
}
