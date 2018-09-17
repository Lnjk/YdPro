package com.example.ydshoa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.bean.SysApplication;
//import com.umeng.message.PushAgent;

public class WelcomeActivity extends Activity {
    private SharedPreferences sp;
    private Boolean isFirstUse;
    private Context context;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    startActivity(new Intent(context,LoginMainActivity.class));
                    finish();
                    break;
                    default:
                        break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        //推送
//        PushAgent.getInstance(WelcomeActivity.this).onAppStart();
        SysApplication.getInstance().addActivity(this);
        context=WelcomeActivity.this;
//        handler.sendEmptyMessageAtTime(1,5000);
    }
    public void open(View v){
        startActivity(new Intent(context,LoginMainActivity.class));
        finish();
    }
}
