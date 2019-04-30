package com.example.ydshoa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.example.bean.URLS;
import com.example.bean.UserInfo;
import com.example.bean.UserInfo.User_Query;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
//import com.umeng.message.PushAgent;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SpActivity extends Activity implements OnClickListener {
	private ImageButton sale, gath, rec;
	private Context context;
	private TextView head;
	private String session;
	ArrayList<String> modIds_get = new ArrayList<String>();
	String urllogin = URLS.login_url;
	private SharedPreferences sp;
	private boolean query, add, alter, amt, cst, del, gp, gpr, out, prt, qty,
			up;
	String url = URLS.userInfo_url;
	private String ps_id;
	private int pass2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_spm);
		context = SpActivity.this;
//		PushAgent.getInstance(context).onAppStart();
		sp = getSharedPreferences("ydbg", 0);
		session = sp.getString("SESSION", "");
		ps_id = sp.getString("PASS", "");
		initView();
		getRoot();
	}

	private void getRoot() {
		OkHttpClient client = new OkHttpClient();
		Request localRequest = new Request.Builder()
				.addHeader("cookie", session).url(url).build();
		client.newCall(localRequest).enqueue(new Callback() {

			@Override
			public void onResponse(Call call, Response response)
					throws IOException {
				String string = response.body().string();
				Log.e("LiNing", "------" + response.code() + "------" + string);
				Gson gson = new GsonBuilder().setDateFormat(
						"yyyy-MM-dd HH:mm:ss").create();
				UserInfo info = gson.fromJson(string, UserInfo.class);

				if (info.getUser_Id().equalsIgnoreCase("admin")
						&& info.getUser_Pwd().equalsIgnoreCase("admin")) {
					pass2 = 1;
				} else {
					List<com.example.bean.UserInfo.User_Mod> user_Mod = info
							.getUser_Mod();
					if (user_Mod.size() > 0 && user_Mod != null) {
						for (int i = 0; i < user_Mod.size(); i++) {

							String mod_ID = user_Mod.get(i).getMod_ID();
							modIds_get.add(mod_ID);
							sp.edit().putString("modIds", "" + modIds_get)
									.commit();

						}
					}
				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {

			}
		});
	}

	private void initView() {
		head = (TextView) findViewById(R.id.all_head);
		head.setText("决策报表");
		sale = (ImageButton) findViewById(R.id.btn_dec_spm);
		gath = (ImageButton) findViewById(R.id.btn_dec_spt);
		sale.setOnClickListener(this);// 销售
		gath.setOnClickListener(this);// 收款
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_dec_spm:
				if (pass2 == 1) {
					startActivity(new Intent(context, DecisionActivity.class));
					sp.edit().putString("JC", head.getText().toString()).commit();
				} else {

					if (modIds_get.contains("spm")) {
						startActivity(new Intent(context, DecisionActivity.class));
						sp.edit().putString("JC", head.getText().toString())
								.commit();
					} else {
						Toast.makeText(this.context, "请等待...", Toast.LENGTH_LONG).show();
					}
				}

				break;
			case R.id.btn_dec_spt:
//			startActivity(new Intent(context, PriceActivity.class));

				if (modIds_get.contains("spt")) {
					Toast.makeText(this.context, "暂未开放", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(this.context, "请等待...", Toast.LENGTH_LONG).show();
				}
				break;

			default:
				break;
		}
	}

	public void allback(View v) {
		finish();
	}
}
