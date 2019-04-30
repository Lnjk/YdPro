package com.example.ydshoa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.bean.SysApplication;
import com.example.bean.URLS;
import com.example.bean.UserInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
//import com.umeng.message.PushAgent;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class SysActivity extends Activity implements OnClickListener {

	private ImageButton root, account, user;
	private Context context;
	private String session;
	private SharedPreferences sp;
	private List<String> idList = new ArrayList<String>();
	private boolean query, add, alter, amt, cst, del, gp, gpr, out, prt, qty,
			up;
	private List<com.example.bean.UserInfo.User_Mod> user_Mod;
	String url = URLS.userInfo_url;
	private String ps_id;
	private String mod_ID;
	ArrayList<String> modIds_get = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.system);
		context = SysActivity.this;
//		PushAgent.getInstance(context).onAppStart();
		sp = getSharedPreferences("ydbg", 0);
		session = sp.getString("SESSION", "");
		ps_id = sp.getString("PASS", "");
		initData();
		getRoot();
	}

	private void initData() {
		root = (ImageButton) findViewById(R.id.btn_sys_root);
		account = (ImageButton) findViewById(R.id.btn_sys_accout);
		user = (ImageButton) findViewById(R.id.btn_sys_user);
		findViewById(R.id.sys_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();
			}
		});
		root.setOnClickListener(this);
		account.setOnClickListener(this);
		user.setOnClickListener(this);
		String string = sp.getString("modIds", "");
		Log.e("LiNing", "1111111111111" + string);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_sys_root:
				if (ps_id.equals("1")) {
					startActivity(new Intent(this.context, RootActivity.class));
				} else {
					if(modIds_get.contains("smrtrc")){
						startActivity(new Intent(this.context,
								RootActivity.class));
					}else{
						Toast.makeText(context, "请等待...", Toast.LENGTH_LONG).show();
					}

				}
				// startActivity(new Intent(this.context, RootActivity.class));
				break;
			case R.id.btn_sys_accout:
				if (ps_id.equals("1")) {
					startActivity(new Intent(this.context, AccountActivity.class));
				} else {
					if(modIds_get.contains("smrtra")){
						startActivity(new Intent(this.context,
								AccountActivity.class));
					}else{
						Toast.makeText(context, "请等待...", Toast.LENGTH_LONG).show();
					}

					// startActivity(new Intent(this.context,
					// AccountActivity.class));
				}
				break;
			case R.id.btn_sys_user:
				if (ps_id.equals("1")) {
					startActivity(new Intent(this.context, CusterInfoActivity.class));
				} else {
					if(modIds_get.contains("smrtru")){
						startActivity(new Intent(this.context,
								CusterInfoActivity.class));
					}else{
						Toast.makeText(context, "请等待...", Toast.LENGTH_LONG).show();
					}

				}
				// startActivity(new Intent(this.context,
				// CusterInfoActivity.class));
				break;
			default:
				break;
		}
	}

	// public void allback(View v) {
	// finish();
	// }

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

				user_Mod = info.getUser_Mod();
				if (user_Mod.size() > 0 && user_Mod != null) {
					for (int i = 0; i < user_Mod.size(); i++) {
						mod_ID = user_Mod.get(i).getMod_ID();
						modIds_get.add(mod_ID);
						if (mod_ID.equals("smrtru")) {
							query = user_Mod.get(i).isMod_Query();
							add = user_Mod.get(i).isMod_Add();
							alter = user_Mod.get(i).isMod_Alter();
							amt = user_Mod.get(i).isMod_Amt();
							cst = user_Mod.get(i).isMod_Cst();
							del = user_Mod.get(i).isMod_Del();
							gp = user_Mod.get(i).isMod_GP();
							gpr = user_Mod.get(i).isMod_GPR();
							out = user_Mod.get(i).isMod_Out();
							prt = user_Mod.get(i).isMod_Prt();
							qty = user_Mod.get(i).isMod_Qty();
							up = user_Mod.get(i).isMod_Up();
							Log.e("LiNing", "------" + query);
							sp.edit().putBoolean("USER_QUERY", query).commit();
							sp.edit().putBoolean("USER_ADD", add).commit();
							sp.edit().putBoolean("USER_DEL", del).commit();
							sp.edit().putBoolean("USER_UP", alter).commit();
						}else if (mod_ID.equals("smrtra")) {
							query = user_Mod.get(i).isMod_Query();
							add = user_Mod.get(i).isMod_Add();
							alter = user_Mod.get(i).isMod_Alter();
							amt = user_Mod.get(i).isMod_Amt();
							cst = user_Mod.get(i).isMod_Cst();
							del = user_Mod.get(i).isMod_Del();
							gp = user_Mod.get(i).isMod_GP();
							gpr = user_Mod.get(i).isMod_GPR();
							out = user_Mod.get(i).isMod_Out();
							prt = user_Mod.get(i).isMod_Prt();
							qty = user_Mod.get(i).isMod_Qty();
							up = user_Mod.get(i).isMod_Up();
							Log.e("LiNing", "------" + query);
							sp.edit().putBoolean("ACCOUNT_QUERY", query)
									.commit();
							sp.edit().putBoolean("ACCOUNT_ADD", add).commit();
							sp.edit().putBoolean("ACCOUNT_DEL", del).commit();
							sp.edit().putBoolean("ACCOUNT_UP", alter).commit();
						}
					}
//					if (query == false && add == false && alter == false
//							&& amt == false && cst == false && del == false
//							&& gp == false && gpr == false && out == false
//							&& prt == false && qty == false && up == false) {
//						Log.e("LiNing", "------" + "请等待...");
//						// Toast.makeText(context, "请等待...", 1).show();
//
//					} else {
//						startActivity(new Intent(this.context,
//								CusterInfoActivity.class));
//					}

				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {

			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
