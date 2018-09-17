package com.example.ydshoa;

import java.io.IOException;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

//import com.umeng.message.PushAgent;

public class TestActivity extends Activity {
	private Context context;
	private String session;
	private SharedPreferences sp;
	private String dBID;
	// String urlDep =
	// "http://localhost:8080/InfManagePlatform/QueryErpInfqueryCustomer.action?accountNo=DB_BJ15";//
	// 部门
	String urlDep = "http://192.168.2.178:8080/InfManagePlatform/QueryErpInfqueryCustomer.action";// 部门

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sales_info_all);
		context = TestActivity.this;
//		PushAgent.getInstance(context).onAppStart();
		sp = getSharedPreferences("ydbg", 0);
		session = sp.getString("SESSION", "");
		dBID = sp.getString("DB_ID", "");
		Log.e("LiNing", dBID + "------" + session);
		// requestDep();
//		gethttp();
	}

	private void gethttp() {
		// TODO Auto-generated method stub
		// 创建okHttpClient对象
		OkHttpClient mOkHttpClient = new OkHttpClient();
		// 创建一个Request
		final Request request = new Request.Builder()
				.addHeader("cookie", session)
				.url("http://localhost:8080/InfManagePlatform/QueryErpInfqueryAccountDB.action")
				.build();
		// new call
		Call call = mOkHttpClient.newCall(request);
		// 请求加入调度
		call.enqueue(new Callback() {

			@Override
			public void onResponse(Call call, Response response)
					throws IOException {
				// TODO Auto-generated method stub
				String htmlStr = response.body().string();
				Log.e("LiNing", "-----------" + htmlStr);
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void requestDep() {
		// TODO Auto-generated method stub
		OkHttpClient client = new OkHttpClient();
		FormBody body = new FormBody.Builder().add("accountNo", dBID).build();
		client.newCall(
				new Request.Builder().addHeader("cookie", session).post(body)
						.url(urlDep).build()).enqueue(new Callback() {

			@Override
			public void onResponse(Call call, Response response)
					throws IOException {
				// TODO Auto-generated method stub
				String string = response.body().string();
				Log.e("LiNing", "请求的数据是======" + string);
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub

			}
		});
	}
}
