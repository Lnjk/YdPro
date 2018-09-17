package com.example.ydshoa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

//import com.umeng.message.PushAgent;

public class SalesManageActivity extends Activity {
    private TextView head;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sales_manage);
        context = SalesManageActivity.this;
//        PushAgent.getInstance(context).onAppStart();
        initView();
    }

    private void initView() {
        head = (TextView) findViewById(R.id.all_head);
        head.setText("销售管理");
    }

    public void allback(View v) {
        finish();
    }

    public void offer(View v) {

//        startActivity(new Intent(context, PriceActivity.class));
    }
}
