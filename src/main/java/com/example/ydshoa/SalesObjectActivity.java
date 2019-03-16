package com.example.ydshoa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

public class SalesObjectActivity extends Activity implements View.OnClickListener {
    Context context;
    private TextView head;
    private String reportBus;
    private ImageButton sale_tj, sale_tb,sale_ppfx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sales_object);
        context=SalesObjectActivity.this;
        reportBus = getIntent().getStringExtra("reportB");// 获取业务类型
        initView();
    }

    private void initView() {
        head = (TextView) findViewById(R.id.all_head);
        head.setText("报表种类");
        sale_tj = (ImageButton) findViewById(R.id.btn_dec_sale_tj);
        sale_tb = (ImageButton) findViewById(R.id.btn_dec_shrec_tb);
        sale_ppfx = (ImageButton) findViewById(R.id.btn_dec_sale_ppfx);
        sale_tj.setOnClickListener(this);// 销售统计
        sale_tb.setOnClickListener(this);// 销售同比
        sale_ppfx.setOnClickListener(this);// 品牌分析
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_dec_sale_tj:
                Intent intent = new Intent(context, SalesNumActivity.class);
                intent.putExtra("reportB", "SA");
                intent.putExtra("sal_obj", "1");
                startActivity(intent);
                break;
            case R.id.btn_dec_shrec_tb:
                Intent intent_tb = new Intent(context, TbSalesActivity.class);
                intent_tb.putExtra("reportB", "SA");
//                intent_tb.putExtra("sal_obj", "2");//第一版（弹弹弹）
                intent_tb.putExtra("sal_obj", "3");
                startActivity(intent_tb);
                break;
            case R.id.btn_dec_sale_ppfx:
                Intent intent_fx = new Intent(context, BrandFxActivity.class);
//                intent_fx.putExtra("reportB", "SA");
//                intent_fx.putExtra("sal_obj", "3");
                startActivity(intent_fx);
                break;
                default:
                    break;
        }
    }
    public void allback(View v) {
        finish();
    }
}
