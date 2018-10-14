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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.LeftOrRight.MyHScrollView;
import com.example.bean.DesignAllInfos;
import com.example.bean.URLS;
import com.example.bean.VipInfos;
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

public class QueryDesigActivity extends Activity implements View.OnClickListener {
    private Context context;
    private SharedPreferences sp;
    private String session,user_Name,user_dep,query_db;
    RelativeLayout mHead;
    private TextView head,viplb,bbyw;
    private EditText sfzh, vipname, zym, lxdh, lxdh2,  ssdw, bbqd,  yhkh, khyh, dbr, shyh,vipcard,sfqy,zdyh;
    private ListView lv_vip_qry;
    private ImageButton vipbtn, ztbtn,yw;
    String vip_id_hd,id_bbyw;//回调
    String vip_get = URLS.design_query;
    //查询条件
    String qry_vipcard,qry_sfzh,qry_vipname,qry_zym,qry_lxdh,qry_lxdh2,qry_viplb,qry_ssdw,qry_bbqd,qry_bbyw,qry_yhkh,qry_khyh,qry_dbr,qry_shyh,qry_sfqy,qry_zdyh;
    //获取所有数据
    List<DesignAllInfos.VipList> data_vipList;
    private DesignAdapter adapter_design;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_query_desig);
        context = QueryDesigActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        query_db=getIntent().getStringExtra("ZT_VIP");
        Log.e("LiNing","所用账套===="+query_db);
        user_Name = sp.getString("USER_NAME", "");
        user_dep = sp.getString("USER_DEPBM", "");
        initView();
    }

    private void initView() {
        head = (TextView) findViewById(R.id.all_head);
        head.setText("设计师查询");
//        this.mHead = ((RelativeLayout) findViewById(R.id.design_head_query));
//        this.mHead.setFocusable(true);
//        this.mHead.setClickable(true);
//        this.mHead.setOnTouchListener(new QueryDesigActivity.ListViewAndHeadViewTouchLinstener());
        lv_vip_qry = (ListView) findViewById(R.id.lv_design_header_query);
//        lv_vip_qry.setOnTouchListener(new QueryDesigActivity.ListViewAndHeadViewTouchLinstener());
        vipcard = (EditText) findViewById(R.id.tv_vipcard_query);
        sfqy = (EditText) findViewById(R.id.tv_design_isno_query);
        sfzh = (EditText) findViewById(R.id.et_design_usercard_query);
        vipname = (EditText) findViewById(R.id.et_design_username_query);
        zym = (EditText) findViewById(R.id.et_design_username_old_query);
        lxdh = (EditText) findViewById(R.id.et_design_phone_query);
        lxdh2 = (EditText) findViewById(R.id.et_design_phone_two_query);
        viplb = (TextView) findViewById(R.id.et_design_vip_query);
        ssdw = (EditText) findViewById(R.id.et_design_comp_query);
        bbqd = (EditText) findViewById(R.id.et_design_dep_query);
//        bbqd.setText(user_dep);//点击新增
        bbyw = (TextView) findViewById(R.id.et_design_salno_query);
        yhkh = (EditText) findViewById(R.id.et_design_bankno_query);
        khyh = (EditText) findViewById(R.id.et_design_bankdeposit_query);
        dbr = (EditText) findViewById(R.id.et_design_guatname_query);
        zdyh = (EditText) findViewById(R.id.et_design_userno_query);
//        zdyh.setText(user_Name);
//        Log.e("LiNing", "制单用户id---" + user_Id);
        shyh = (EditText) findViewById(R.id.et_design_userchk_query);
        vipbtn = (ImageButton) findViewById(R.id.ib_design_vip_query);
        yw = (ImageButton) findViewById(R.id.ib_design_yw_query);
        vipbtn.setOnClickListener(this);
        yw.setOnClickListener(this);
        lv_vip_qry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter_design.setSelectItem(position);//刷新
                adapter_design.notifyDataSetInvalidated();
                DesignAllInfos.VipList vipList_callback= (DesignAllInfos.VipList) parent.getAdapter().getItem(position);
                Log.e("LiNing","回调数据"+vipList_callback.getAlteList().get(0).getVip_No());
                Log.e("LiNing","回调数据"+vipList_callback);
                Intent localIntent = getIntent();
                localIntent.putExtra("VIP_INFOS_ALL", vipList_callback);
                setResult(1, localIntent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //VIP类别//-----10
            case R.id.ib_design_vip_query:
                if (query_db.equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {

                    Intent intent_vip = new Intent(context, VipClassActivity.class);
                    intent_vip.putExtra("ZT_VIP", query_db);
//                    startActivity(intent_vip);
                    startActivityForResult(intent_vip, 10);
                }
                break;
                //报备业务//-----17
            case R.id.ib_design_yw_query:
                if (query_db.equals("")) {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent17 = new Intent(context,
                            CondicionInfoActivity.class);
                    intent17.putExtra("flag", "17");
                    intent17.putExtra("queryID", query_db);
                    startActivityForResult(intent17, 17);
                }
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 10:
                if (resultCode == 1) {

                    vip_id_hd = data.getStringExtra("VIP_ID");
                    String vip_name_hd = data.getStringExtra("VIP_NAME");
                    viplb.setText(vip_name_hd);
                    Log.e("LiNing", "提交的id====" + vip_id_hd + vip_name_hd);
                }

                break;
            case 17:
                if (resultCode == 1) {
                    String str1 = data.getStringExtra("data_return");
                    id_bbyw = data.getStringExtra("data_return_ids");
                    bbyw.setText(str1);
                    // sp.edit().putString("USERIDquerry", str1).commit();
                }
                break;
        }
    }

    public void designQuery(View view){
        getinfos();//获取信息
                getAllInfos();
    }

    private void getinfos() {

        qry_vipcard = vipcard.getText().toString();
        qry_sfzh = sfzh.getText().toString();
        qry_vipname = vipname.getText().toString();
        qry_zym = zym.getText().toString();
        qry_lxdh = lxdh.getText().toString();
        qry_lxdh2 = lxdh2.getText().toString();
        qry_viplb = viplb.getText().toString();
        qry_ssdw = ssdw.getText().toString();
        qry_bbqd = bbqd.getText().toString();
        qry_bbyw = bbyw.getText().toString();
        qry_yhkh = yhkh.getText().toString();
        qry_khyh = khyh.getText().toString();
        qry_dbr = dbr.getText().toString();
        qry_shyh = shyh.getText().toString();
        qry_sfqy = sfqy.getText().toString();
        qry_zdyh = zdyh.getText().toString();
    }

    //获取所有信息
    private void getAllInfos() {

        OkHttpClient client_all=new OkHttpClient();
        FormBody body_all=new FormBody.Builder()
                .add("db_Id",query_db)
//                .add("vip_NO",qry_vipcard)
//                .add("card_Num",qry_sfzh)
//                .add("vip_Name",qry_vipname)
//                .add("for_Name",qry_zym)
//                .add("con_Tel",qry_lxdh)
//                .add("con_Tel2",qry_lxdh2)
//                .add("type_Id",qry_viplb)
//                .add("comp",qry_ssdw)
//                .add("SalesTerminal_No",qry_bbqd)
//                .add("sal_No",qry_bbyw)
//                .add("bank_No",qry_yhkh)
//                .add("bank_Deposit",qry_khyh)
//                .add("guat_name",qry_dbr)
//                .add("user_No",qry_zdyh)
//                .add("user_CHK",qry_shyh)
//                .add("Stat",qry_sfqy)
                .build();
        Log.e("LiNing","查询数据----"+query_db+"---"+qry_vipcard+"---"+qry_sfzh+"---"+qry_vipname+"---"+qry_zym+"---"+qry_lxdh+"---"+qry_lxdh2
                +"---"+qry_viplb+"---"+qry_ssdw+"---"+qry_bbqd+"---"+qry_yhkh+"---"+qry_khyh+"---"+qry_dbr+"---"+qry_zdyh+"---"+qry_shyh+"---"+qry_sfqy);
        Request request_all=new Request.Builder()
                .addHeader("cookie", session).url(vip_get)
                .post(body_all)
                .build();
        client_all.newCall(request_all).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String info_all = response.body().string();
                Log.e("LiNing", "id_type结果====" + info_all);
                Gson gson = new GsonBuilder().setDateFormat(
                        "yyyy-MM-dd HH:mm:ss").create();
                DesignAllInfos designAllInfos = gson.fromJson(info_all, DesignAllInfos.class);
                    Log.e("LiNing", "id_type结果====" + designAllInfos);
                if (designAllInfos != null) {
                    data_vipList = designAllInfos.getVipList();
                    Log.e("LiNing", "id_type结果====" + designAllInfos);
                    if(data_vipList.size()>0&&data_vipList!=null){

                        QueryDesigActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter_design = new DesignAdapter(R.layout.design_dairy_item_two, data_vipList, context);
                                lv_vip_qry.setAdapter(adapter_design);
                                adapter_design.notifyDataSetChanged();
                            }
                        });
                    }
                    else {
                        QueryDesigActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context,"数据为空",Toast.LENGTH_LONG).show();
                                lv_vip_qry.setAdapter(null);
                            }
                        });


                    }
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }
    public class DesignAdapter extends BaseAdapter {
        int id_row_layout;
        LayoutInflater mInflater;
        List<DesignAllInfos.VipList> data_adp;
        //item高亮显示
        private int selectItem = -1;
        public void setSelectItem(int selectItem) {
            this.selectItem = selectItem;
        }
        public DesignAdapter(int design_dairy_item, List<DesignAllInfos.VipList> data_vipList, Context context) {
            this.id_row_layout=design_dairy_item;
            this.data_adp=data_vipList;
            this.mInflater=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return data_adp.size();
        }

        @Override
        public Object getItem(int position) {
            return data_adp.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder=null;
            if(convertView==null){
//                synchronized (QueryDesigActivity.this) {
                    convertView = mInflater.inflate(id_row_layout, null);
                    holder = new ViewHolder();
                    MyHScrollView scrollView1 = (MyHScrollView) convertView
                            .findViewById(R.id.horizontalScrollView1);
                    holder.scrollView = scrollView1;
                    holder.design_checkboxid = (TextView) convertView.findViewById(R.id.design_showChecx);
                    holder.design_vipno = (TextView) convertView.findViewById(R.id.price_design_no);
                    holder.design_action = (TextView) convertView.findViewById(R.id.price_design_action);
                    holder.design_altedd = (TextView) convertView.findViewById(R.id.price_design_altedd);
                    holder.design_altecont = (TextView) convertView.findViewById(R.id.price_design_altecont);
                    holder.design_alteuser = (TextView) convertView.findViewById(R.id.price_design_alteuser);
//                    MyHScrollView headSrcrollView = (MyHScrollView) mHead
//                            .findViewById(R.id.horizontalScrollView1);
//                    headSrcrollView
//                            .AddOnScrollChangedListener(new OnScrollChangedListenerImp(
//                                    scrollView1));
                    convertView.setTag(holder);
//                }
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            DesignAllInfos.VipList vipList = data_adp.get(position);
            String id_itm = vipList.getAlteList().get(0).getITM();
            String design_vipno_get = vipList.getAlteList().get(0).getVip_No();
            String design_action_get = vipList.getAlteList().get(0).getAction();
            String design_altdd_get = vipList.getAlteList().get(0).getAlte_DD();
            String design_aitcont_get = vipList.getAlteList().get(0).getAlte_Cont();
            String design_ailuser_get = vipList.getAlteList().get(0).getUser_no();
            holder.design_checkboxid.setText(id_itm);
            holder.design_vipno.setText(design_vipno_get);
            holder.design_action.setText(design_action_get);
            holder.design_altedd.setText(design_altdd_get);
            holder.design_altecont.setText(design_aitcont_get);
            holder.design_alteuser.setText(design_ailuser_get);
            //操作修改，删除等
            if (position == selectItem) {
                convertView.setBackgroundColor(Color.RED);
                Log.e("LiNing", "id_type结果====" + id_itm);
            } else {
                convertView.setBackgroundColor(Color.TRANSPARENT);
            }
            return convertView;
        }
        class OnScrollChangedListenerImp implements
                MyHScrollView.OnScrollChangedListener {
            MyHScrollView mScrollViewArg;

            public OnScrollChangedListenerImp(MyHScrollView mScrollViewArg) {
                super();
                this.mScrollViewArg = mScrollViewArg;
            }

            public void onScrollChanged(int paramInt1, int paramInt2,
                                        int paramInt3, int paramInt4) {
                this.mScrollViewArg.smoothScrollTo(paramInt1, paramInt2);
            }
        }
        class ViewHolder {
            HorizontalScrollView scrollView;
            public TextView design_checkboxid;
            public TextView design_vipno;
            public TextView design_action;
            public TextView design_altedd;
            public TextView design_altecont;
            public TextView design_alteuser;
        }
    }
//    滑动
    class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {
        ListViewAndHeadViewTouchLinstener() {
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ((HorizontalScrollView) QueryDesigActivity.this.mHead
                    .findViewById(R.id.horizontalScrollView1))
                    .onTouchEvent(event);
            return false;
        }

    }
}
