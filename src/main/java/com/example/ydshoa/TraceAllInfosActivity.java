package com.example.ydshoa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class TraceAllInfosActivity extends Activity implements View.OnClickListener {
    private Context context;
    private SharedPreferences sp;
    private String session,dabh_befor ,sszt_befor,user_Id;
    private TextView head;
    private Button gz_add,gz_del,gz_update,gz_save;
    private int do_save;
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_trace_all_infos);
        context = TraceAllInfosActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        user_Id = sp.getString("USER_ID", "");
        sszt_befor = getIntent().getStringExtra("SSZT");
        dabh_befor = getIntent().getStringExtra("DABH");
        initView();
    }

    private void initView() {
        head= (TextView) findViewById(R.id.all_head);
        head.setText("跟踪信息");
        gz_add = (Button) findViewById(R.id.btn_trace_all_add);
        gz_update = (Button) findViewById(R.id.btn_trace_all_reset);
        gz_del = (Button) findViewById(R.id.btn_trace_all_del);
        gz_save = (Button) findViewById(R.id.btn_trace_all_save);
        gz_add.setOnClickListener(this);
        gz_update.setOnClickListener(this);
        gz_del.setOnClickListener(this);
        gz_save.setOnClickListener(this);
    }

    public void allback(View v) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //新增（总）
            case R.id.btn_trace_all_add:
                do_save=1;
                updataInfos();
            break;
            //更新（总）
            case R.id.btn_trace_all_reset:
            break;
            //删除（总）
            case R.id.btn_trace_all_del:
            break;
            //保存（总）
            case R.id.btn_trace_all_save:
            break;
        }
    }

    private void updataInfos() {
        View view = getLayoutInflater()
                .inflate(R.layout.gz_add_kp, null);
        head = (TextView) view.findViewById(R.id.all_head);
        head.setText("客户跟进记录卡");

//        zt = (TextView) view.findViewById(R.id.et_move_zt);
//        yhbh = (TextView) view.findViewById(R.id.et_move_yhbh);
//        sfmr = (TextView) view.findViewById(R.id.tv_move_ismr);
//        dacard = (TextView) view.findViewById(R.id.tv_move_bh);
//        dzbh= (EditText) view.findViewById(R.id.et_move_dzbh);
//        shr= (EditText) view.findViewById(R.id.et_move_username);
//        lxdh= (EditText) view.findViewById(R.id.et_move_phone);
//        ss= (EditText) view.findViewById(R.id.et_move_ss);
//        qx= (EditText) view.findViewById(R.id.et_move_qx);
//        xxdz= (EditText) view.findViewById(R.id.et_move_adress);
//        zt_xl= (ImageButton) view.findViewById(R.id.ib_move_account);
//        mr_xl= (ImageButton) view.findViewById(R.id.ib_move_sfmr);
//        if(do_save==1){
//            zt.setText(sszt_befor);
//            dacard.setText(dabh_befor);
//            yhbh.setText(user_Id);
//        }else if(do_save==2){
//            zt_xl.setEnabled(false);
//            dzbh.setEnabled(false);
//            zt.setText(extra_zt);
//            dacard.setText(extra_dabh);
//            dzbh.setText(extra_itm);
//            shr.setText(extra_shr);
//            lxdh.setText(extra_dh);
//            ss.setText(extra_ss);
//            qx.setText(extra_qx);
//            xxdz.setText(extra_adress);
//            yhbh.setText(extra_userid);
//            sfmr.setText(extra_def);
//        }
//        zt_xl.setOnClickListener(this);
//        mr_xl.setOnClickListener(this);

        view.findViewById(R.id.imageButton1).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
        view.findViewById(R.id.btn_move_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                postPsxx();
            }
        });

        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setCancelable(true);
        alertDialog.setView(view);
        alertDialog.show();
    }
}
