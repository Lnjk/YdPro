package com.example.fragment;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bean.SysApplication;
import com.example.bean.URLS;
import com.example.bean.UserInfo;
import com.example.ydshoa.R;
import com.example.ydshoa.SetActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MyFragment extends Fragment implements OnClickListener {
	private Context context;
	private TextView ztdep, ztid, ztname, ztpwd, ztzt;
	//	private TextView id, name, db, dep, ztdep, ztid, ztname, ztpwd, ztzt;
//	private Button desdroy;
	private Button out;
	//	private EditText pwd;
	private View view;
	private SharedPreferences sp;
	private String session;
	//	private String session, spSession;
	private String user_Id, user_Name, user_Pwd, user_DB, user_Dep;
	String url = URLS.userInfo_url;
	@Override
	public View onCreateView(LayoutInflater paramLayoutInflater,
							 ViewGroup paramViewGroup, Bundle savedInstanceState) {
		this.view = paramLayoutInflater.inflate(R.layout.myfrag,
				paramViewGroup, false);
		this.context = getActivity();
		sp = getActivity().getSharedPreferences("ydbg", 0);
		session = sp.getString("SESSION", "");
		SysApplication.getInstance().addActivity(getActivity());
		getInfo();
		initData();// 初始化
		return this.view;
	}

//	private void conInfo() {
//		if (spSession != null) {
//			OkHttpClient client = new OkHttpClient();
//			Request localRequest = new Request.Builder()
//					.addHeader("cookie", spSession)
//					.url(url)
//					.build();
//			client.newCall(localRequest).enqueue(new Callback() {
//
//				@Override
//				public void onResponse(Call call, Response response)
//						throws IOException {
//					String string = response.body().string();
//					Log.e("LiNing", "可以解析的用户信息----" + string);
//				}
//
//				@Override
//				public void onFailure(Call arg0, IOException arg1) {
//
//				}
//			});
//		} else {
//			Toast.makeText(context, "session无效", 1).show();
//		}
//	}

	private void getInfo() {
		OkHttpClient client = new OkHttpClient();
		Request localRequest = new Request.Builder()
				.addHeader("cookie", session)
				.url(url)
				.build();
		client.newCall(localRequest).enqueue(new Callback() {

			@Override
			public void onResponse(Call call, Response response)
					throws IOException {
				String string = response.body().string();
				Log.e("LiNing", "用户信息----" + string);
				Log.e("LiNing", "返回码" + response.code());
				Gson gson = new GsonBuilder().setDateFormat(
						"yyyy-MM-dd HH:mm:ss").create();
				UserInfo info = gson.fromJson(string, UserInfo.class);
				user_Id = info.getUser_Id();
				user_Name = info.getUser_Name();
				user_Pwd = info.getUser_Pwd();
				user_DB = info.getUser_DB();
				user_Dep = info.getUser_Dep();
				Log.e("LiNing", "需要的信息===" + user_Id + user_Name + user_Pwd
						+ user_DB + user_Dep);
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						ztid.setText(user_Id);
						ztname.setText(user_Name);
						ztpwd.setText(user_Pwd);
						ztzt.setText(user_DB);
						ztdep.setText(user_Dep);
					}
				});

			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {

			}
		});
	}

//	private void finish() {
//		((FragmentActivity) context).onBackPressed();
//	}

	private void initData() {
		this.ztid = (TextView) view.findViewById(R.id.et_myfrag_id);
		this.ztname = (TextView) view.findViewById(R.id.et_myfrag_name);
		this.ztpwd = (TextView) view.findViewById(R.id.et_myfrag_pwd);
		this.ztzt = (TextView) view.findViewById(R.id.et_myfrag_db);
		this.ztdep = (TextView) view.findViewById(R.id.et_myfrag_dep);
//		this.ztid.setTypeface(Typeface.createFromAsset(getActivity()
//				.getAssets(), "font/jt.ttf"));
		this.out = ((Button) this.view.findViewById(R.id.btn_myset));
		this.out.setOnClickListener(this);
		// this.desdroy.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			// case R.id.my_out:
			// finish();
			// break;
			case R.id.btn_myset:
				startActivity(new Intent(this.context, SetActivity.class));
				break;

			default:
				break;
		}
	}
}
