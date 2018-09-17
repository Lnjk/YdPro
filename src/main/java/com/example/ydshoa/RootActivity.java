package com.example.ydshoa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.MyAdapter;
import com.example.bean.LxProject;
import com.example.bean.URLS;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
//import com.umeng.message.PushAgent;

public class RootActivity extends Activity implements OnItemSelectedListener,
		OnClickListener {
	private EditText adress, user, pwd, port, custor;
	private boolean b;
	private Context mContext;
	private ArrayList<LxProject> mData = null;
	private MyAdapter<LxProject> myAdapter = null;
	private OkHttpClient okHttpClient;
	private boolean one_selected = false;
	private Button rootOk;
	private SharedPreferences sp;
	private Spinner spOne;
	private TextView tv_name;
	private String session;
	private ArrayAdapter<String> adapter;
	private List<String> list = new ArrayList<String>();
	String urllogin = URLS.login_url;
	String urlcreat = URLS.creatRoot_url;
	String urlcon = URLS.conRoot_url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.root);
//		PushAgent.getInstance(RootActivity.this).onAppStart();
		this.sp = getSharedPreferences("ydbg", 0);
		session = sp.getString("SESSION", "");
		this.adress = ((EditText) findViewById(R.id.et_address));
		// this.adress.setTypeface(Typeface.createFromAsset(getAssets(),
		// "font/jt.ttf"));
		this.port = ((EditText) findViewById(R.id.et_enter));
		// this.port.setTypeface(Typeface.createFromAsset(getAssets(),
		// "font/jt.ttf"));
		this.user = ((EditText) findViewById(R.id.et_account));
		// this.user.setTypeface(Typeface.createFromAsset(getAssets(),
		// "font/jt.ttf"));
		this.pwd = ((EditText) findViewById(R.id.et_Rootpsd));
		// this.pwd.setTypeface(Typeface.createFromAsset(getAssets(),
		// "font/jt.ttf"));
		this.spOne = ((Spinner) findViewById(R.id.sp_enter));
		this.rootOk = ((Button) findViewById(R.id.btn_rootok));
		// this.rootOk.setTypeface(Typeface.createFromAsset(getAssets(),
		// "font/jt.ttf"));
		this.rootOk.setOnClickListener(this);
		this.spOne.setOnItemSelectedListener(this);
		this.mContext = this;
		this.mData = new ArrayList<LxProject>();
		// this.mData.add(new LxProject("请选择"));
		this.mData.add(new LxProject("MYSQL"));
		this.mData.add(new LxProject("SQLServe2005"));
		this.mData.add(new LxProject("SQLServe2008"));
		this.mData.add(new LxProject("SQLServe2012"));
		this.mData.add(new LxProject("SQLServe2014"));
		this.mData.add(new LxProject("SQLServe2016"));
		list.add("MYSQL");
		list.add("SQLServe2005");
		list.add("SQLServe2008");
		list.add("SQLServe2012");
		list.add("SQLServe2014");
		list.add("SQLServe2016");
		// this.myAdapter = new MyAdapter<LxProject>(mData, R.layout.test) {
		//
		// @Override
		// public void bindView(
		// com.example.adapter.MyAdapter.ViewHolder paramViewHolder,
		// LxProject paramT) {
		// paramViewHolder.setText(R.id.tv_test, paramT.getName());
		// }
		// };
		// 第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		// 第三步：为适配器设置下拉列表下拉时的菜单样式。
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 第四步：将适配器添加到下拉列表上
		spOne.setAdapter(adapter);
		// this.spOne.setAdapter(this.myAdapter);
		spOne.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
									   int position, long id) {
				/* 将mySpinner 显示 */
				parent.setVisibility(View.VISIBLE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				parent.setVisibility(View.VISIBLE);
			}
		});
		spOne.setOnTouchListener(new Spinner.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});
		spOne.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void creatDbBase() {
		FormBody localFormBody = new FormBody.Builder()
				.add("dbExist", "" + b)
				.add("userName", this.user.getText().toString())
				.add("userPwd", this.pwd.getText().toString())
				.add("dB_Prot", this.port.getText().toString())
				.add("server_Ip", this.adress.getText().toString())
				.add("dB_Type", spOne.getSelectedItem().toString()).build();
		Request localRequest = new Request.Builder()
				.addHeader("cookie", session).url(urlcreat).post(localFormBody)
				.build();
		this.okHttpClient.newCall(localRequest).enqueue(new okhttp3.Callback() {

			@Override
			public void onResponse(Call paramAnonymouscall,
								   Response paramAnonymousResponse) throws IOException {
				Log.e("LiNing", paramAnonymousResponse.body().string());
				if(paramAnonymousResponse.code()==200){
					startActivity(new Intent(mContext, MainActivity.class));
				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {

			}
		});
	}

	@Override
	public void onItemSelected(AdapterView<?> paramAdapterView, View paramView,
							   int paramInt, long paramLong) {
		switch (paramAdapterView.getId()) {
			case R.id.sp_enter:
				if (this.one_selected) {
					tv_name = (TextView) paramView.findViewById(R.id.tv_test);
					Toast.makeText(this.mContext,
							"您的数据库类型是：" + this.tv_name.getText().toString(), Toast.LENGTH_LONG)
							.show();
				} else {

					this.one_selected = true;
				}
				break;

			default:
				break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

	public void onClick(View paramView) {
		if ((TextUtils.isEmpty(this.user.getText().toString()))
				|| (TextUtils.isEmpty(this.port.getText().toString()))
				|| (TextUtils.isEmpty(this.adress.getText().toString()))
				|| (TextUtils.isEmpty(this.pwd.getText().toString()))) {
			Toast.makeText(this, "不能为空", Toast.LENGTH_LONG).show();
		}
		con();
	}

	public void con() {
		this.okHttpClient = new OkHttpClient().newBuilder()
				.connectTimeout(100, TimeUnit.SECONDS)
				.readTimeout(200, TimeUnit.SECONDS).build();
		FormBody localFormBody = new FormBody.Builder()
				.add("userName", this.user.getText().toString())
				.add("userPwd", this.pwd.getText().toString())
				.add("dB_Prot", this.port.getText().toString())
				.add("server_Ip", this.adress.getText().toString())
				.add("dB_Type", spOne.getSelectedItem().toString()).build();
		Request localRequest = new Request.Builder()
				.addHeader("cookie", session)
				// .url("http://192.168.2.178:8080/InfManagePlatform/rootgetConnection.action")
				.url(urlcon).post(localFormBody).build();
		this.okHttpClient.newCall(localRequest).enqueue(new Callback() {

			@Override
			public void onResponse(Call paramAnonymousCall,
								   Response paramAnonymousResponse) throws IOException {
				String str = paramAnonymousResponse.body().string();
				JsonArray localJsonArray = new JsonParser().parse(str)
						.getAsJsonArray();
				b = localJsonArray.get(1).getAsBoolean();
				localJsonArray.get(0).getAsBoolean();
				if (b) {
					((Activity) RootActivity.this.mContext)
							.runOnUiThread(new Runnable() {
								public void run() {
									TextView localTextView = new TextView(
											RootActivity.this.mContext);
									localTextView
											.setText("连接成功,检测到RootDB数据库已经存在,重新创建，数据将会被注销，是否继续！！！");
									localTextView.setGravity(Gravity.CENTER);
									new AlertDialog.Builder(
											RootActivity.this.mContext)
											.setTitle("提示")
											.setView(localTextView)
											.setPositiveButton(
													"是",
													new DialogInterface.OnClickListener() {
														public void onClick(
																DialogInterface paramAnonymous3DialogInterface,
																int paramAnonymous3Int) {
															RootActivity.this
																	.creatDbBase();
														}
													})
											.setNegativeButton(
													"否",
													new DialogInterface.OnClickListener() {
														public void onClick(
																DialogInterface paramAnonymous3DialogInterface,
																int paramAnonymous3Int) {
															RootActivity.this
																	.getFragmentManager()
																	.beginTransaction()
																	.addToBackStack(
																			null)
																	.commit();
														}
													}).show();
								}
							});
				}
				Log.e("LiNing", "this res=" + str);
				if (!b) {

					((Activity) RootActivity.this.mContext)
							.runOnUiThread(new Runnable() {
								public void run() {
									new AlertDialog.Builder(
											RootActivity.this.mContext)
											.setTitle("数据库不存在,是否创建？")
											.setPositiveButton(
													"是",
													new DialogInterface.OnClickListener() {
														public void onClick(
																DialogInterface paramAnonymous3DialogInterface,
																int paramAnonymous3Int) {
															RootActivity.this
																	.creatDbBase();
														}
													})
											.setNegativeButton("否", null)
											.show();
								}
							});
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

	public void allback(View v) {
		finish();
	}
}
