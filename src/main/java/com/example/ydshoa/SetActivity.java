package com.example.ydshoa;


import com.example.bean.SysApplication;
//import com.umeng.message.PushAgent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SetActivity extends Activity implements OnClickListener {

	private Button out, desdroy, clean;
	private TextView head;

	//
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);
	 requestWindowFeature(Window.FEATURE_NO_TITLE);
	 setContentView(R.layout.activity_set);
//		 PushAgent.getInstance(SetActivity.this).onAppStart();
	 this.out = (Button) findViewById(R.id.my_out);
	 this.desdroy = (Button) findViewById(R.id.my_desdroy);
	 this.clean = (Button) findViewById(R.id.my_clean);
	 this.out.setOnClickListener(this);
	 this.desdroy.setOnClickListener(this);
	 this.clean.setOnClickListener(this);
	 head = (TextView) findViewById(R.id.all_head);
		 head.setText("设置");
	 SysApplication.getInstance().addActivity(this);
	 }
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.my_out:
			
			SysApplication.getInstance().exit();
			break;
		case R.id.my_desdroy:
//			 startActivity(new Intent(SetActivity.this,
//			 LoginMainActivity.class));
//			 finish();
			startActivity(new Intent(SetActivity.this, LoginMainActivity.class));
			break;
		case R.id.my_clean:
			break;

		default:
			break;
		}
	}

	public void allback(View v) {
		finish();
	}

}
