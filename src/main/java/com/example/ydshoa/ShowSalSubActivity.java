package com.example.ydshoa;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

//import com.umeng.message.PushAgent;

public class ShowSalSubActivity extends Activity {
	private TextView head;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_show_sal_sub);
//		PushAgent.getInstance(ShowSalSubActivity.this).onAppStart();
		initView();
	}

	private void initView() {
		head = (TextView) findViewById(R.id.all_head);
		head.setText("销售统计表");
	}

	public void allback(View v) {
		finish();
	}
}
