package com.example.fragment;

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
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.bean.URLS;
import com.example.ydshoa.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class RootFragment extends Fragment implements OnClickListener {
	private EditText adress, user, pwd, port;
	private boolean b;
	private Context mContext;
	//	private ArrayList<LxProject> mData = null;
//	private MyAdapter<LxProject> myAdapter = null;
	private OkHttpClient okHttpClient;
	//	private ProgressDialog progressDialog = null;
	private Button rootOk;
	private SharedPreferences sp;
	private Spinner spOne;
	//	private TextView tv_name;
	private View view;
	private String session;
	//	String urllogin = "http://192.168.2.178:8080/InfManagePlatform/LoginAction.action";
	String urllogin = URLS.login_url;
	String urlcreat = URLS.creatRoot_url;
	String urlcon = URLS.conRoot_url;
	private ArrayAdapter<String> adapter;
	private List<String> list = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		this.view = inflater.inflate(R.layout.activity_root, container, false);
		// getsession();
		initData();
		return this.view;
	}

//	private void getsession() {
//		OkHttpClient client = new OkHttpClient();
//		FormBody body = new FormBody.Builder().build();
//		Request request = new Request.Builder().url(urllogin).build();
//		Call call2 = client.newCall(request);
//		call2.enqueue(new Callback() {
//
//			@Override
//			public void onResponse(Call call, Response response)
//					throws IOException {
//				if (response.isSuccessful()) {
//					Log.i("LiNing", response.body().string());
//				}
//				// session
//				Headers headers = response.headers();
//				Log.e("LiNing", "登录成功===" + headers);
//				Log.d("info_headers", "header " + headers);
//				List<String> cookies = headers.values("Set-Cookie");
//				String session = cookies.get(0);
//				Log.d("info_cookies", "onResponse-size: " + cookies);
//
//				session = session.substring(0, session.indexOf(";"));
//				Log.i("info_s", "session is  :" + session);
//
//			}
//
//			@Override
//			public void onFailure(Call arg0, IOException arg1) {
//
//			}
//		});
//	}

	private void initData() {
		sp = getActivity().getSharedPreferences("ydbg", 0);
		session = sp.getString("SESSION", "");
		Log.e("LiNing", "获取的session=====" + session);
		this.adress = (EditText) view.findViewById(R.id.et_address);
		this.port = (EditText) view.findViewById(R.id.et_enter);
		this.user = (EditText) view.findViewById(R.id.et_account);
		this.pwd = (EditText) view.findViewById(R.id.et_Rootpsd);
		this.spOne = (Spinner) view.findViewById(R.id.sp_enter);
		this.rootOk = (Button) view.findViewById(R.id.btn_rootok);
		this.rootOk.setOnClickListener(this);
		this.mContext = getActivity();
		list.add("MYSQL");
		list.add("SQLServe2005");
		list.add("SQLServe2008");
		list.add("SQLServe2012");
		list.add("SQLServe2014");
		list.add("SQLServe2016");
		adapter = new ArrayAdapter<String>(mContext,
				android.R.layout.simple_spinner_item, list);
		// 第三步：为适配器设置下拉列表下拉时的菜单样式。
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 第四步：将适配器添加到下拉列表上
		spOne.setAdapter(adapter);
		// this.spOne.setAdapter(this.myAdapter);
		spOne.setOnItemSelectedListener(new OnItemSelectedListener() {

//			private boolean one_selected = false;

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

			@SuppressLint("ClickableViewAccessibility")
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
				.add("dbExist", ""+b)
				.add("userName", this.user.getText().toString())
				.add("userPwd", this.pwd.getText().toString())
				.add("dB_Prot", this.port.getText().toString())
				.add("server_Ip", this.adress.getText().toString())
				.add("dB_Type", spOne.getSelectedItem().toString()).build();
		Request localRequest = new Request.Builder()
				.addHeader("cookie", session)
				.url(urlcreat)
				.post(localFormBody).build();
		this.okHttpClient.newCall(localRequest).enqueue(new Callback() {
			public void onFailure(Call paramAnonymousCall,
								  IOException paramAnonymousIOException) {
			}

			public void onResponse(Call paramAnonymousCall,
								   Response paramAnonymousResponse) throws IOException {
				Log.e("LiNing", paramAnonymousResponse.body().string());
				System.out.println("+++++" + paramAnonymousResponse.code());
				if(paramAnonymousResponse.code()==200){
//					startActivity(new Intent(getActivity(), MainActivity.class));
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		if ((TextUtils.isEmpty(this.user.getText().toString()))
				|| (TextUtils.isEmpty(this.port.getText().toString()))
				|| (TextUtils.isEmpty(this.adress.getText().toString()))
				|| (TextUtils.isEmpty(this.pwd.getText().toString()))) {
			Toast.makeText(getActivity(), "不能为空", Toast.LENGTH_SHORT).show();
		}
		con();
	}

	public void con() {
		okHttpClient = new OkHttpClient().newBuilder()
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
				.url(urlcon)
				.post(localFormBody).build();
		this.okHttpClient.newCall(localRequest).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response paramAnonymousResponse)
					throws IOException {
				String str = paramAnonymousResponse.body().string();
				JsonArray localJsonArray = new JsonParser().parse(str)
						.getAsJsonArray();
				Log.e("LiNing", "this res=" + str);
				System.out.println(paramAnonymousResponse.code());
				b = localJsonArray.get(0).getAsBoolean();
				if (RootFragment.this.b) {
					((Activity) RootFragment.this.mContext)
							.runOnUiThread(new Runnable() {
								public void run() {
									TextView localTextView = new TextView(
											RootFragment.this.mContext);
									localTextView
											.setText("连接成功,检测到RootDB数据库已经存在,重新创建，数据将会被注销，是否继续！！！");
									localTextView.setGravity(Gravity.CENTER);
									new AlertDialog.Builder(
											RootFragment.this.mContext)
											.setTitle("提示")
											.setView(localTextView)
											.setPositiveButton(
													"是",
													new DialogInterface.OnClickListener() {
														public void onClick(
																DialogInterface paramAnonymous3DialogInterface,
																int paramAnonymous3Int) {
															creatDbBase();
														}
													})
											.setNegativeButton(
													"否",
													new DialogInterface.OnClickListener() {
														public void onClick(
																DialogInterface paramAnonymous3DialogInterface,
																int paramAnonymous3Int) {
															RootFragment.this
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

				if (!RootFragment.this.b) {
					((Activity) RootFragment.this.mContext)
							.runOnUiThread(new Runnable() {
								public void run() {
									new AlertDialog.Builder(
											RootFragment.this.mContext)
											.setTitle("数据库不存在,是否创建？")
											.setPositiveButton(
													"是",
													new DialogInterface.OnClickListener() {
														public void onClick(
																DialogInterface paramAnonymous3DialogInterface,
																int paramAnonymous3Int) {
															RootFragment.this
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

	public void allback(View v) {
		getActivity().finish();
	}
}
