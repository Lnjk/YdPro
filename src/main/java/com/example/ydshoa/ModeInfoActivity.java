package com.example.ydshoa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.PriceNumID;
import com.example.bean.URLS;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ModeInfoActivity extends Activity implements View.OnClickListener {
    private Context context;
    private SharedPreferences sp;
    private String session;
    String url_dh_price = URLS.price_num_ls;
    String url = URLS.userInfo_url;
    String ps_add = URLS.psxx_add;
    String ps_del = URLS.psxx_del;
    String ps_set = URLS.psxx_updata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_info);
        context = ModeInfoActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");

        initView();//初始化
    }

    private void initView() {

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

}
