package com.example.ydshoa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DesignerActivity extends Activity implements View.OnClickListener {
    private Context context;
    private SharedPreferences sp;
    private String session, str_name;
    private TextView head, sfty, zt;
    private EditText sfzh, vipname, zym, lxdh, lxdh2, viplb, ssdw, bbqd, bbyw, yhkh, khyh, dbr, zdyh;
    private Button shyh;
    private ImageButton vipbtn, ztbtn;
    private ListView lv_vip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_designer);
        context = DesignerActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        initView();//初始化
    }

    private void initView() {
        head = (TextView) findViewById(R.id.all_head);
        head.setText("设计师管理");
        lv_vip = (ListView) findViewById(R.id.lv_design_header);
        zt = (TextView) findViewById(R.id.et_design_zt);
        sfzh = (EditText) findViewById(R.id.et_design_usercard);
        vipname = (EditText) findViewById(R.id.et_design_username);
        zym = (EditText) findViewById(R.id.et_design_username_old);
        lxdh = (EditText) findViewById(R.id.et_design_phone);
        lxdh2 = (EditText) findViewById(R.id.et_design_phone_two);
        viplb = (EditText) findViewById(R.id.et_design_vip);
        ssdw = (EditText) findViewById(R.id.et_design_comp);
        bbqd = (EditText) findViewById(R.id.et_design_dep);
        bbyw = (EditText) findViewById(R.id.et_design_salno);
        yhkh = (EditText) findViewById(R.id.et_design_bankno);
        khyh = (EditText) findViewById(R.id.et_design_bankdeposit);
        dbr = (EditText) findViewById(R.id.et_design_guatname);
        zdyh = (EditText) findViewById(R.id.et_design_userno);
        shyh = (Button) findViewById(R.id.btn_design_userchk);
        ztbtn = (ImageButton) findViewById(R.id.ib_design_account);
        vipbtn = (ImageButton) findViewById(R.id.ib_design_vip);
        ztbtn.setOnClickListener(this);
        shyh.setOnClickListener(this);
        vipbtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //VIP账套
            case R.id.ib_design_account:
                Intent intent = new Intent(context, CondicionInfoActivity.class);
                intent.putExtra("flag", "1");
                startActivityForResult(intent, 1);
                break;
            //VIP类别
            case R.id.ib_design_vip:
                if(zt.getText().toString().equals("")){
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                }else{

                    Intent intent_vip = new Intent(context, VipClassActivity.class);
                    intent_vip.putExtra("ZT_VIP", zt.getText().toString());
//                    startActivity(intent_vip);
                    startActivityForResult(intent_vip, 10);
                }
                break;
            //审核用户
            case R.id.btn_design_userchk:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    String str_id = data.getStringExtra("condition_db");
                    str_name = data.getStringExtra("condition_name");
                    zt.setText(str_id);
                    Log.e("LiNing", "提交的id====" + str_id + str_name);
                }

                break;
            case 10:
                if (resultCode == 1) {
                    String vip_id_hd = data.getStringExtra("VIP_ID");
                    String vip_name_hd = data.getStringExtra("VIP_NAME");
                    viplb.setText(vip_name_hd);
                    Log.e("LiNing", "提交的id====" + vip_id_hd + vip_name_hd);
                }

                break;
        }
    }

    public void allback(View v) {
        finish();
    }
}
