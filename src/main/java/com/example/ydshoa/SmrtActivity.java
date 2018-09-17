package com.example.ydshoa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.example.bean.SysApplication;
import com.example.bean.URLS;
import com.example.bean.UserInfo;
import com.example.bean.UserInfo.User_Query;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
//import com.umeng.message.PushAgent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class SmrtActivity extends Activity implements OnClickListener {
	private ImageButton root, djgl;
	private Context context;
	private String session;
	private SharedPreferences sp;
	private List<String> idList = new ArrayList<String>();
	private boolean query, add, alter, amt, cst, del, gp, gpr, out, prt, qty,
			up;
	private List<com.example.bean.UserInfo.User_Mod> user_Mod;
	String url = URLS.userInfo_url;
	ArrayList<String> modIds_get = new ArrayList<String>();
	private String ps_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_smrt);
		context = SmrtActivity.this;
//		PushAgent.getInstance(context).onAppStart();
		sp = getSharedPreferences("ydbg", 0);
		session = sp.getString("SESSION", "");
		ps_id = sp.getString("PASS", "");
		initData();
		getRoot();
	}

	private int flag;

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
					flag = 1;
				} else {
					// }
					List<com.example.bean.UserInfo.User_Mod> user_Mod = info
							.getUser_Mod();
					if (user_Mod.size() > 0 && user_Mod != null) {
						for (int i = 0; i < user_Mod.size(); i++) {

							String mod_ID = user_Mod.get(i).getMod_ID();
							modIds_get.add(mod_ID);
							sp.edit().putString("modIds", "" + modIds_get)
									.commit();
							if (mod_ID.equals("smprice")) {

								query = user_Mod.get(i).isMod_Query();//查询
								add = user_Mod.get(i).isMod_Add();//新增
								del = user_Mod.get(i).isMod_Del();//删除
								out = user_Mod.get(i).isMod_Out();//转出
								alter = user_Mod.get(i).isMod_Alter();//更新
								sp.edit().putString("PRICE_QUERY", "" + query).commit();
								sp.edit().putString("PRICE_ADD", "" + add).commit();
								sp.edit().putString("PRICE_DEL", "" + del).commit();
								sp.edit().putString("PRICE_OUT", "" + out).commit();
								sp.edit().putString("PRICE_RESET", "" + alter).commit();
							}
							amt = user_Mod.get(i).isMod_Amt();
							cst = user_Mod.get(i).isMod_Cst();
							gp = user_Mod.get(i).isMod_GP();
							gpr = user_Mod.get(i).isMod_GPR();
							prt = user_Mod.get(i).isMod_Prt();
							qty = user_Mod.get(i).isMod_Qty();
							up = user_Mod.get(i).isMod_Up();

						}
					}
				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {

			}
		});
	}

	private void initData() {
		root = (ImageButton) findViewById(R.id.btn_sys_smrt);
		findViewById(R.id.sys_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();
			}
		});
		root.setOnClickListener(this);
		djgl = (ImageButton) findViewById(R.id.btn_sys_djgl);
		djgl.setOnClickListener(this);
		String string = sp.getString("modIds", "");
		Log.e("LiNing", "1111111111111" + string);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_sys_smrt:
				if (flag == 1) {
					startActivity(new Intent(context, SysActivity.class));
				} else {

					if (modIds_get.contains("smrt")) {
						startActivity(new Intent(context, SysActivity.class));
					} else {
						Toast.makeText(this.context, "无此权限", Toast.LENGTH_LONG).show();
					}
				}
				break;
			case R.id.btn_sys_djgl:
				if (flag == 1) {
					startActivity(new Intent(context, PriceActivity.class));
				} else {

					if (modIds_get.contains("smprice")) {
						startActivity(new Intent(context, PriceActivity.class));
					} else {
						Toast.makeText(this.context, "无此权限", Toast.LENGTH_LONG).show();
					}
				}
				break;

			default:
				break;
		}
	}

}
