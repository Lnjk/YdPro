package com.example.ydshoa;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class DesigneDhrActivity extends Activity {
    private Context context;
    private SharedPreferences sp;
    private String session, db_zt;
    private TextView head;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_designe_dhr);
        context = DesigneDhrActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        initView();//初始化
    }

    private void initView() {
        head = (TextView) findViewById(R.id.all_head);
        head.setText("积分商品对换表");
    }
    public void allback(View v) {
        finish();
    }
}
