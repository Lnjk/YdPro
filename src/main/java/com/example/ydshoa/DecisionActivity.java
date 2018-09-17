package com.example.ydshoa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.example.bean.TestQx;
import com.example.bean.TestQx.QX;
import com.example.bean.AccountInfo;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class DecisionActivity extends Activity implements OnClickListener {

	private ImageButton sale, gath, rec;
	private Context context;
	private TextView head;
	private String session;
	private SharedPreferences sp;
	private boolean query, add, alter, amt, cst, del, gp, gpr, out, prt, qty,
			up;
	private List<com.example.bean.UserInfo.User_Mod> user_Mod;
	String url = URLS.userInfo_url;
	String url_query =  URLS.ERP_db_url;
	private String ps_id;
	private String db_ID;
	private String mod_ID;
	ArrayList<String> modIds_get = new ArrayList<String>();
	private ArrayList<HashMap<String, Object>> dList;
	private HashMap<String, Object> item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_decision);
		context = DecisionActivity.this;
		//推送
//		PushAgent.getInstance(context).onAppStart();
		sp = getSharedPreferences("ydbg", 0);
		session = sp.getString("SESSION", "");
		ps_id = sp.getString("PASS", "");
		initView();
		getRoot();
//		getIDtoName();
	}
	private String stritem_idTname;
	private ArrayList<HashMap<String, Object>> dList_idTn;
	private void getIDtoName() {
		OkHttpClient client = new OkHttpClient();
		FormBody body = new FormBody.Builder().build();
		client.newCall(new Request.Builder().addHeader("cookie", session).post(body)
				.url(url_query).build()).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response response) throws IOException {
				String str = response.body().string();
				Log.e("LiNing", "账套数据===" + str);
				final AccountInfo aDb1 = new Gson().fromJson(str,
						AccountInfo.class);
				if (aDb1 != null) {
					DecisionActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							List<com.example.bean.AccountInfo.IdNameList> data = aDb1.getIdnamelist();
							Log.e("LiNing", "账套数据===data" + data);
							if(data.size() > 0 && data != null){

								for (int i = 0; i < data.size(); i++) {
									String db_Id = data.get(i).getId();
									String name = data.get(i).getName();
									HashMap<String, Object> item = new HashMap<String, Object>();
									item.put("ZT_ID", db_Id);
									item.put("ZT_NAME",name);
									dList_idTn.add(item);
//											stritem_idTname = new Gson().toJson(dList_idTn);
								}
							}
							if(dList_idTn!=null&&dList_idTn.size()>0){
								stritem_idTname = new Gson().toJson(dList_idTn);
								Log.e("LiNing", "11111111====333=="+stritem_idTname);
								sp.edit().putString("all_query_tj_to", stritem_idTname).commit();
							}
						}
					});
				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub

			}
		});
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
//				sp.edit().putString("all_query_tj", string).commit();
				Gson gson = new GsonBuilder().setDateFormat(
						"yyyy-MM-dd HH:mm:ss").create();
				UserInfo info = gson.fromJson(string, UserInfo.class);
//				//查询
				List<User_Query> user_Query = info.getUser_Query();
				String query_json = new Gson().toJson(user_Query);
				sp.edit().putString("all_query", query_json).commit();
				Log.e("LiNing", "提交保存的数据====" + query_json);
				//模块
				user_Mod = info.getUser_Mod();
				Log.e("LiNing", "===user_Mod=====" + user_Mod);
				if (user_Mod.size() > 0 && user_Mod != null) {
					for (int i = 0; i < user_Mod.size(); i++) {
						mod_ID = user_Mod.get(i).getMod_ID();
						modIds_get.add(mod_ID);
						if (mod_ID.equals("spmsa")) {
							db_ID = user_Mod.get(i).getDb_ID();
							out = user_Mod.get(i).isMod_Out();// 转出
							prt = user_Mod.get(i).isMod_Prt();// 打印
							qty = user_Mod.get(i).isMod_Cst();// 成本
							gp = user_Mod.get(i).isMod_GP();// 毛利
							gpr = user_Mod.get(i).isMod_GPR();// 毛利率
							amt = user_Mod.get(i).isMod_Amt();//金额
							Log.e("LiNing", "========" + i + mod_ID + qty + gp
									+ gpr+amt+out+prt);
							sp.edit().putString("CB", "" + qty).commit();
							sp.edit().putString("ML", "" + gp).commit();
							sp.edit().putString("MLL", "" + gpr).commit();
							sp.edit().putString("ZC", "" + out).commit();
							sp.edit().putString("DY", "" + prt).commit();
							sp.edit().putString("JE", "" + amt).commit();
							item = new HashMap<String, Object>();
							item.put("账套", db_ID);
							item.put("转出", "" + out);
							item.put("打印", "" + prt);
							item.put("成本", ""+qty);
							item.put("毛利", ""+gp);
							item.put("毛利率", ""+gpr);
							item.put("金额", ""+amt);
							dList.add(item);
							Log.e("LiNing", "===map=====" + item);
							Log.e("LiNing", "===map=new====" + dList);

							// 封装map
							// Map<String,String[]> map = new
							// HashMap<String,String[]>();
							// map.put(db_ID, new
							// String[]{""+out,""+prt,""+qty,""+gp,""+gpr});
							// Log.e("LiNing", "===map=====" + map);
							// 封装对象
							// TestQx tq = new TestQx();
							// tq.setDB_ID(db_ID);
							// QX qx = new QX();
							// qx.setZc("" + out);
							// qx.setDy("" + prt);
							// qx.setCb("" + qty);
							// qx.setMl("" + gp);
							// qx.setMll("" + gpr);
							// tq.setQx(qx);
							// Intent intent = new Intent(this,
							// SalesNumActivity.class);
							// intent.putExtra("TQ", new Gson().toJson(tq));
							// startActivity(intent);
							// sp.edit().putString("TQ", new Gson().toJson(tq))
							// .commit();
						}

					}
					if (dList != null && dList.size() > 0) {
						Log.e("LiNing", ",,,,,,," + dList.toString());
						String stritem = new Gson().toJson(dList);
						sp.edit().putString("CBQX", stritem).commit();
						Log.e("LiNing", "提交的数据是" + sp.getString("CBQX", ""));
					}
					// if (query == false && add == false && alter == false
					// && amt == false && cst == false && del == false
					// && gp == false && gpr == false && out == false
					// && prt == false && qty == false && up == false) {
					// Log.e("LiNing", "------" + "无此权限");
					// // Toast.makeText(context, "无此权限", 1).show();
					//
					// } else {
					// sp.edit().putString("TJ", head.getText().toString())
					// .commit();
					// Intent intent = new Intent(context,
					// SalesNumActivity.class);
					// intent.putExtra("reportB", "SA");
					// startActivity(intent);
					// }

				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {

			}
		});
	}

	private void initView() {
		dList_idTn = new ArrayList();
		head = (TextView) findViewById(R.id.all_head);
		head.setText("统计报表数据");
		sale = (ImageButton) findViewById(R.id.btn_dec_sale);
		// gath = (ImageButton) findViewById(R.id.btn_dec_receipt);
		rec = (ImageButton) findViewById(R.id.btn_dec_shrec);
		sale.setOnClickListener(this);// 销售
		// gath.setOnClickListener(this);// 收款
		rec.setOnClickListener(this);// 应收
		dList = new ArrayList();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_dec_sale:
				if (ps_id.equals("1")) {
					sp.edit().putString("TJ", head.getText().toString()).commit();
					Intent intent = new Intent(context, SalesNumActivity.class);
					intent.putExtra("reportB", "SA");
					startActivity(intent);
				} else {
					if (modIds_get.contains("spmsa")) {
						sp.edit().putString("TJ", head.getText().toString())
								.commit();
						Intent intent = new Intent(context, SalesNumActivity.class);
						intent.putExtra("reportB", "SA");
						startActivity(intent);

					}
				}
				break;
			case R.id.btn_dec_shrec:
				// Intent intent2=new Intent(context, SalesQueryActivity.class);
				// intent2.putExtra("reportB", "ARP");
				// startActivity(intent2);
				break;

			default:
				break;
		}
	}

	public void allback(View v) {
		finish();
	}
}
