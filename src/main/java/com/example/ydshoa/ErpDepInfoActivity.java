package com.example.ydshoa;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.example.bean.DepInfo;
import com.example.bean.DepInfo.IdNameList;
import com.example.bean.URLS;
import com.example.ydshoa.GetInfoActivity.MyAdapter;
import com.example.ydshoa.GetInfoActivity.ViewHolder;
import com.google.gson.Gson;
//import com.umeng.message.PushAgent;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ErpDepInfoActivity extends Activity implements OnClickListener {
	private RadioGroup rg_one;
	private ListView lv_one;
	private Context context;
	private int selectPosition = -1;
	private String dBID;
	private String session;
	private SharedPreferences sp;
	private List<IdNameList> depInfo;
	private IdNameList selectBrand;
	private String name;
	// 搜索
	private EditText infoId, infoName, infoAll;
	private ImageButton search;
	// // 部门
	// String urlDep =
	// "http://192.168.2.178:8080/InfManagePlatform/QueryErpInfqueryDepartment.action";
	// // 客户
	// String urlCust =
	// "http://192.168.2.178:8080/InfManagePlatform/QueryErpInfqueryCustomer.action";
	// // 用户
	// String urlUser =
	// "http://192.168.2.178:8080/InfManagePlatform/QueryErpInfqueryErpUser.action";
	// 部门
	String urlDep = URLS.ERP_dep_url;
	// 客户
	String urlCust = URLS.ERP_cust_url;
	// 用户
	String urlUser = URLS.ERP_user_url;
	private String idString;
	private String nameString;
	private String allString;
	private String extraFlag;
	private String searUrl;
	private int cussor;
	private FormBody body;
	private String selectID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_erp_dep_info);
		context = ErpDepInfoActivity.this;
		//推送
//		PushAgent.getInstance(context).onAppStart();
		sp = getSharedPreferences("ydbg", 0);
		session = sp.getString("SESSION", "");
//		dBID = sp.getString("DB_ID", "");
		dBID = getIntent().getStringExtra("DB_ID");
		Log.e("LiNing", dBID + "------" + session);
		rg_one = (RadioGroup) findViewById(R.id.rg_info_one);
		lv_one = (ListView) findViewById(R.id.lv_rd_one);
		infoId = (EditText) findViewById(R.id.et_search_id);
		infoName = (EditText) findViewById(R.id.et_search_name);
//		infoAll = (EditText) findViewById(R.id.et_search_all);
		search = (ImageButton) findViewById(R.id.iv_search_info);
		search.setOnClickListener(this);
		// 请求数据
		getFlag();

	}

	private void getFlag() {
		extraFlag = getIntent().getStringExtra("flag");
		Log.e("LiNing", "flag====" + extraFlag);
		// 1，获取部门
		if (extraFlag.equals("2")) {

			requestDep();
		}
		// 2，获取客户
		if (extraFlag.equals("3")) {

			requestcust();
		}
		// 3，获取用户
		if (extraFlag.equals("4")) {

			requestuser();
		}
	}

	private void requestuser() {
		OkHttpClient client = new OkHttpClient();
		FormBody body = new FormBody.Builder().add("accountNo", dBID).build();
		Request request = new Request.Builder().addHeader("cookie", session)
				.url(urlUser).post(body).build();
		Call call = client.newCall(request);
		call.enqueue(new Callback() {

			@Override
			public void onResponse(Call call, Response response)
					throws IOException {
				String struser = response.body().string();
				Log.e("LiNing", "用户数据===" + struser);

				final DepInfo dInfo = new Gson().fromJson(struser,
						DepInfo.class);
				if (dInfo.isRLO() == false) {
					ErpDepInfoActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(context, "数据库已断开",
									Toast.LENGTH_SHORT).show();
						}
					});
				} else {

					if (dInfo != null) {
						ErpDepInfoActivity.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								depInfo = dInfo.getIdNameList();
								showCheckBoxListViewDep();
							}

						});
					} else {
						Toast.makeText(context, "数据为空", Toast.LENGTH_LONG).show();
					}
				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {

			}
		});
	}

	private void requestcust() {
		OkHttpClient client = new OkHttpClient();
		FormBody body = new FormBody.Builder().add("accountNo", dBID)
				.add("custType", "" + 1).build();
		Request request = new Request.Builder().addHeader("cookie", session)
				.url(urlCust).post(body).build();
		Call call = client.newCall(request);
		call.enqueue(new Callback() {

			@Override
			public void onResponse(Call call, Response response)
					throws IOException {
				String str = response.body().string();
				Log.e("LiNing", "客户数据===" + str);
				final DepInfo dInfo = new Gson().fromJson(str, DepInfo.class);
				if (dInfo.isRLO() == false) {
					ErpDepInfoActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(context, "数据库已断开",
									Toast.LENGTH_SHORT).show();
						}
					});
				} else {

					if (dInfo != null) {
						ErpDepInfoActivity.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								depInfo = dInfo.getIdNameList();
								showCheckBoxListViewDep();
							}

						});
					} else {
						Toast.makeText(context, "数据为空", Toast.LENGTH_LONG).show();
					}
				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {

			}
		});
	}

	private void requestDep() {
		OkHttpClient client = new OkHttpClient();
		FormBody body = new FormBody.Builder().add("accountNo", dBID).build();
		Request request = new Request.Builder().addHeader("cookie", session)
				.url(urlDep).post(body).build();
		Call call = client.newCall(request);
		call.enqueue(new Callback() {

			@Override
			public void onResponse(Call call, Response response)
					throws IOException {
				String str = response.body().string();
				Log.e("LiNing", "部门数据===" + str);
				final DepInfo dInfo = new Gson().fromJson(str, DepInfo.class);
				if (dInfo.isRLO() == false) {
					ErpDepInfoActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(context, "数据库已断开",
									Toast.LENGTH_SHORT).show();
						}
					});
				} else {

					if (dInfo != null) {
						ErpDepInfoActivity.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								depInfo = dInfo.getIdNameList();
								showCheckBoxListViewDep();
							}

						});
					} else {
						Toast.makeText(context, "数据为空", Toast.LENGTH_LONG).show();
					}
				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {

			}
		});

	}

	private void showCheckBoxListViewDep() {
		final MyAdapter myAdapter = new MyAdapter(context, depInfo);
		lv_one.setAdapter(myAdapter);
		if (depInfo != null) {

			lv_one.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
										int position, long id) {
					// 获取选中的参数
					selectPosition = position;
					myAdapter.notifyDataSetChanged();
					selectBrand = depInfo.get(position);
					selectID = selectBrand.getId();
					// sp.edit().putString("DEPID", selectID).commit();
					name = selectBrand.getName();
					// Toast.makeText(context,
					// "您选择的是：" + selectBrand.getId() + position,
					// Toast.LENGTH_SHORT).show();
				}
			});
		} else {
			Toast.makeText(context, "数据为空", Toast.LENGTH_LONG).show();
		}
	}

	public class MyAdapter extends BaseAdapter {

		List<IdNameList> brandsList;
		Context context;
		LayoutInflater mInflater;

		public MyAdapter(Context context, List<IdNameList> data) {
			this.context = context;
			this.brandsList = data;
			mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return brandsList.size();
		}

		@Override
		public Object getItem(int position) {
			return brandsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.adapter_item, parent,
						false);
				viewHolder = new ViewHolder();
				viewHolder.id = (TextView) convertView
						.findViewById(R.id.id_item);
				viewHolder.name = (TextView) convertView
						.findViewById(R.id.id_name);
				viewHolder.select = (RadioButton) convertView
						.findViewById(R.id.id_select);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.id.setText(brandsList.get(position).getId());
			viewHolder.name.setText(brandsList.get(position).getName());
			if (selectPosition == position) {
				viewHolder.select.setChecked(true);
			} else {
				viewHolder.select.setChecked(false);
			}
			return convertView;
		}
	}

	public class ViewHolder {
		TextView name;
		TextView id;
		RadioButton select;
	}

	// 获取结果
	public void one_ok(View v) {
		if (name != null) {

			Intent localIntent = getIntent();
			localIntent.putExtra("data_dep", name);
			localIntent.putExtra("data_dep_id", selectID);
			setResult(1, localIntent);
			finish();
		} else {
			Toast.makeText(context, "数据为空", Toast.LENGTH_LONG).show();
			finish();
		}
	}

	@Override
	public void onClick(View v) {
		idString = infoId.getText().toString();
		nameString = infoName.getText().toString();
//		allString = infoAll.getText().toString();
		if (TextUtils.isEmpty(idString) && TextUtils.isEmpty(nameString)
				) {
			if (extraFlag.equals("2")) {

				requestDep();
			}
			if (extraFlag.equals("3")) {
				requestcust();
			}
			if (extraFlag.equals("4")) {
				requestuser();
			}
		} else if (!idString.equals("") && TextUtils.isEmpty(nameString)
				) {
			cussor = 1;
			getSearchId();

		} else if (!nameString.equals("") && TextUtils.isEmpty(idString)
				) {
			// name查询
			cussor = 2;
			getSearchId();
		} else if (!idString.equals("") && !nameString.equals("")) {
			// id+name查询
			cussor = 3;
			getSearchId();
		} else {
			// 全部查询
			cussor = 4;
			getSearchId();
		}
	}

	private void getSearchId() {
		if (extraFlag.equals("2")) {
			OkHttpClient client = new OkHttpClient();
			if (cussor == 1) {
				body = new FormBody.Builder().add("accountNo", dBID)
						.add("id", idString).build();
			} else if (cussor == 2) {
				body = new FormBody.Builder().add("accountNo", dBID)
						.add("name", nameString).build();
			} else if (cussor == 3) {
				body = new FormBody.Builder().add("accountNo", dBID)
						.add("name", nameString).add("id", idString).build();
			} else if (cussor == 4) {
				requestDep();
			}

			Request request = new Request.Builder()
					.addHeader("cookie", session).url(urlDep).post(body)
					.build();
			Call call = client.newCall(request);
			call.enqueue(new Callback() {

				@Override
				public void onResponse(Call call, Response response)
						throws IOException {
					String str = response.body().string();
					Log.e("LiNing", "查询数据===" + str);
					final DepInfo dInfo = new Gson().fromJson(str,
							DepInfo.class);
					if (dInfo != null) {
						ErpDepInfoActivity.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								depInfo = dInfo.getIdNameList();
								showCheckBoxListViewDep();
							}

						});
					}
				}

				@Override
				public void onFailure(Call arg0, IOException arg1) {

				}
			});
		} else if (extraFlag.equals("3")) {
			OkHttpClient client = new OkHttpClient();
			// FormBody body = new FormBody.Builder().add("accountNo", dBID)
			// .add("custType", "" + 1).add("id", idString).build();
			if (cussor == 1) {
				body = new FormBody.Builder().add("accountNo", dBID)
						.add("custType", "" + 1).add("id", idString).build();
			} else if (cussor == 2) {
				body = new FormBody.Builder().add("accountNo", dBID)
						.add("custType", "" + 1).add("name", nameString)
						.build();
			} else if (cussor == 3) {
				body = new FormBody.Builder().add("accountNo", dBID)
						.add("custType", "" + 1).add("name", nameString)
						.add("id", idString).build();
			} else if (cussor == 4) {
				requestcust();
			}
			Request request = new Request.Builder()
					.addHeader("cookie", session).url(urlCust).post(body)
					.build();
			Call call = client.newCall(request);
			call.enqueue(new Callback() {

				@Override
				public void onResponse(Call call, Response response)
						throws IOException {
					String str = response.body().string();
					Log.e("LiNing", "查询数据===" + str);
					final DepInfo dInfo = new Gson().fromJson(str,
							DepInfo.class);
					if (dInfo != null) {
						ErpDepInfoActivity.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								depInfo = dInfo.getIdNameList();
								showCheckBoxListViewDep();
							}

						});
					}
				}

				@Override
				public void onFailure(Call arg0, IOException arg1) {

				}
			});
		} else if (extraFlag.equals("4")) {
			OkHttpClient client = new OkHttpClient();
			// FormBody body = new FormBody.Builder().add("accountNo", dBID)
			// .add("custType", "" + 2).add("id", idString).build();
			if (cussor == 1) {
				body = new FormBody.Builder().add("accountNo", dBID)
						// .add("custType", "" + 2)
						.add("id", idString).build();
			} else if (cussor == 2) {
				body = new FormBody.Builder().add("accountNo", dBID)
						// .add("custType", "" + 2)
						.add("name", nameString).build();
			} else if (cussor == 3) {
				body = new FormBody.Builder().add("accountNo", dBID)
						// .add("custType", "" + 2)
						.add("name", nameString).add("id", idString).build();
			} else if (cussor == 4) {
				requestuser();
			}
			Request request = new Request.Builder()
					.addHeader("cookie", session).url(urlUser).post(body)
					.build();
			Call call = client.newCall(request);
			call.enqueue(new Callback() {

				@Override
				public void onResponse(Call call, Response response)
						throws IOException {
					String str = response.body().string();
					Log.e("LiNing", "查询数据===" + str);
					final DepInfo dInfo = new Gson().fromJson(str,
							DepInfo.class);
					if (dInfo != null) {
						ErpDepInfoActivity.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								depInfo = dInfo.getIdNameList();
								showCheckBoxListViewDep();
							}

						});
					} else {
						Toast.makeText(context, "数据为空", Toast.LENGTH_LONG).show();
					}
				}

				@Override
				public void onFailure(Call arg0, IOException arg1) {

				}
			});
		}

	}
}
