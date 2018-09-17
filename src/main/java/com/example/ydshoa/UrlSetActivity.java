package com.example.ydshoa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

//import com.umeng.message.PushAgent;

public class UrlSetActivity extends Activity {
	private SharedPreferences sp;
	private Context context;
	private EditText name, name_one, name_two, ip, ip_one, ip_two;
	private TextView head;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_url_set);
		sp = getSharedPreferences("ydbg", 0);
		context = UrlSetActivity.this;
//		PushAgent.getInstance(context).onAppStart();
		initView();
	}

	private void initView() {
		head = (TextView) findViewById(R.id.all_head);
		head.setText("网络设置");
		name = (EditText) findViewById(R.id.editText1);
		name_one = (EditText) findViewById(R.id.editText1_t);
		name_two = (EditText) findViewById(R.id.editText1_t_t);
		ip = (EditText) findViewById(R.id.editText2);
		ip_one = (EditText) findViewById(R.id.editText2_t);
		ip_two = (EditText) findViewById(R.id.editText2_t_t);
		String name1 = sp.getString("N1", "");
		String name2 = sp.getString("N2", "");
		String name3 = sp.getString("N3", "");
		String ip1 = sp.getString("P1", "");
		String ip2 = sp.getString("P2", "");
		String ip3 = sp.getString("P3", "");
		if (name1.equals("")) {
			name.setText("");
		} else {
			name.setText(name1);
		}
		if (name2.equals("")) {
			name_one.setText("");
		} else {
			name_one.setText(name2);
		}
		if (name3.equals("")) {
			name_two.setText("");
		} else {
			name_two.setText(name3);
		}
		if (ip1.equals("")) {
			ip.setText("");
		} else {
			ip.setText(ip1);
		}
		if (ip2.equals("")) {
			ip_one.setText("");
		} else {
			ip_one.setText(ip2);
		}
		if (ip3.equals("")) {
			ip_two.setText("");
		} else {
			ip_two.setText(ip3);
		}

	}

	public void allback(View v) {
		sp.edit().putString("N1", name.getText().toString()).commit();
		sp.edit().putString("N2", name_one.getText().toString()).commit();
		sp.edit().putString("N3", name_two.getText().toString()).commit();
		sp.edit().putString("P1", ip.getText().toString()).commit();
		sp.edit().putString("P2", ip_one.getText().toString()).commit();
		sp.edit().putString("P3", ip_two.getText().toString()).commit();
		startActivity(new Intent(UrlSetActivity.this, LoginMainActivity.class));
		finish();
	}

	public void btn_http_save(View v) {
		sp.edit().putString("N1", name.getText().toString()).commit();
		sp.edit().putString("N2", name_one.getText().toString()).commit();
		sp.edit().putString("N3", name_two.getText().toString()).commit();
		sp.edit().putString("P1", ip.getText().toString()).commit();
		sp.edit().putString("P2", ip_one.getText().toString()).commit();
		sp.edit().putString("P3", ip_two.getText().toString()).commit();
		startActivity(new Intent(UrlSetActivity.this, LoginMainActivity.class));
		finish();
	}
}
