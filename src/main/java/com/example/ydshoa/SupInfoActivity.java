package com.example.ydshoa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.SupInfoAdapter;
import com.example.bean.DepInfo;
import com.example.bean.URLS;
import com.example.bean.ViewHolder;
import com.example.bean.DepInfo.IdNameList;
import com.google.gson.Gson;
//import com.umeng.message.PushAgent;

public class SupInfoActivity extends Activity implements OnClickListener {
	private SupInfoAdapter adapter;
	Button btn_calcel, ok;
	Button btn_inverseSelect;
	Button btn_selectAll;
	private Context context;
	private List<HashMap<String, Object>> list;
	ArrayList<String> listStr;
	ArrayList<String> listIDs;
	ListView lv;
	private String querryID;
	// String[] name = { "信息部", "销售部", "人事部", "财务部", "总监部", "规划部", "工程部", "出纳部",
	// "购物部", "娱乐部" };
	TextView tv, headTv;
	private String dBID;
	private String session;
	private SharedPreferences sp;
	private List<IdNameList> depInfo;
	private Object[] arrays;
	private String name;
	// 搜索
	private EditText infoId, infoName, infoAll;
	private ImageButton search;
	// // 部门
	// String urlDep =
	// "http://192.168.2.178:8080/InfManagePlatform/QueryErpInfqueryDepartment.action";//
	// 部门
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
	String urlhs = URLS.ERP_hs_url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sup_info);
		context = SupInfoActivity.this;
//		PushAgent.getInstance(context).onAppStart();
		sp = getSharedPreferences("ydbg", 0);
		session = sp.getString("SESSION", "");
//		dBID = sp.getString("DB_ID", "");
		dBID=getIntent().getStringExtra("get_id");
		Log.e("LiNing", dBID + "------" + session);
		// tv = ((TextView) findViewById(R.id.tv));
		headTv = ((TextView) findViewById(R.id.tv_chage_name));
		lv = ((ListView) findViewById(R.id.lv));
		ok = ((Button) findViewById(R.id.btnok));
		btn_selectAll = ((Button) findViewById(R.id.selectall));
		btn_inverseSelect = ((Button) findViewById(R.id.inverseselect));
		btn_calcel = ((Button) findViewById(R.id.cancel));
		// 搜索
		infoId = (EditText) findViewById(R.id.et_search_id);
		infoName = (EditText) findViewById(R.id.et_search_name);
//		infoAll = (EditText) findViewById(R.id.et_search_all);
		search = (ImageButton) findViewById(R.id.iv_search_info);
		search.setOnClickListener(this);
		// getInfo();
		// 请求数据
		getFlag();
		// showCheckBoxListView();
		btn_selectAll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				for (int i = 0; i < depInfo.size(); i++) {
					SupInfoAdapter.isSelected.put(i, true);
					listStr.add(depInfo.get(i).name);
					listIDs.add(depInfo.get(i).id);
				}
				adapter.notifyDataSetChanged();// 注意这一句必须加上，否则checkbox无法正常更新状态
				// tv.setText("已选中" + listStr.size() + "项");
				Log.e("LiNing", "listStr========" + listStr);
				// Toast.makeText(context, "" + listStr, 1).show();
			}
		});
		btn_inverseSelect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				for (int i = 0; i < depInfo.size(); i++) {
					if (SupInfoAdapter.isSelected.get(i) == false) {
						SupInfoAdapter.isSelected.put(i, true);
						// listStr.add(arrays[i].toString());
						listStr.add(depInfo.get(i).name);
						listIDs.add(depInfo.get(i).id);
					} else {
						SupInfoAdapter.isSelected.put(i, false);
						// listStr.remove(arrays[i].toString());
						listStr.remove(depInfo.get(i).name);
						listIDs.remove(depInfo.get(i).id);
					}
				}
				adapter.notifyDataSetChanged();
				// tv.setText("已选中" + listStr.size() + "项");
				// Toast.makeText(context, "" + listStr, 1).show();
			}
		});
		btn_calcel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// if (depInfo == null) {
				// finish();
				// } else {
				//
				// for (int i = 0; i < depInfo.size(); i++) {
				// if (SupInfoAdapter.isSelected.get(i) == true) {
				// SupInfoAdapter.isSelected.put(i, false);
				// listStr.remove(arrays[i].toString());
				// }
				// }
				// adapter.notifyDataSetChanged();
				// // tv.setText("已选中" + listStr.size() + "项");
				// }
				finish();
			}
		});

	}

	private void getFlag() {
		extraFlag = getIntent().getStringExtra("flag");
		Log.e("LiNing", "flag====" + extraFlag);
		// 1，获取部门
		if (extraFlag.equals("2")) {
			requestDep();
			headTv.setText("部门选择");
		}
		// 2，获取客户
		if (extraFlag.equals("3")) {

			requestcust();
			headTv.setText("客户选择");
		}
		// 3，获取用户
		if (extraFlag.equals("4")) {

			requestuser();
			headTv.setText("用户选择");
		}
		// 4，获取供应商
		if (extraFlag.equals("5")) {

			requestSup();
			headTv.setText("供应商选择");
		}
		// 5,获取核算单位
		if (extraFlag.equals("6")) {

			requestDep();
			headTv.setText("核算单位选择");
		}

	}

	private void requestSup() {
		// TODO Auto-generated method stub
		// if (!dBID.equals("DB_BJ15")) {
		// Toast.makeText(context, "此账套无数据", Toast.LENGTH_SHORT).show();
		// } else {

		OkHttpClient client = new OkHttpClient();
		FormBody body = new FormBody.Builder().add("accountNo", dBID)
				.add("custType", "" + 2).build();
		Request request = new Request.Builder().addHeader("cookie", session)
				.url(urlCust).post(body).build();
		Call call = client.newCall(request);
		call.enqueue(new Callback() {

			@Override
			public void onResponse(Call call, Response response)
					throws IOException {
				String str = response.body().string();
				Log.e("LiNing", "供应商数据===" + str);
				final DepInfo dInfo = new Gson().fromJson(str, DepInfo.class);
				if (dInfo.isRLO() == false) {
					SupInfoActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(context, "数据库已断开",
									Toast.LENGTH_SHORT).show();
						}
					});
				} else {
					if (dInfo != null) {
						SupInfoActivity.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								depInfo = dInfo.getIdNameList();
								if (depInfo != null) {

									arrays = depInfo.toArray();
									Log.e("LiNing", "888" + arrays);
									showCheckBoxListView();
								} else {
									Toast.makeText(context, "数据为空", Toast.LENGTH_LONG).show();
								}
							}

						});
					}
				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {

			}
		});
		// }
	}

	private void requestuser() {
		// if (!dBID.equals("DB_BJ15")) {
		// Toast.makeText(context, "此账套无数据", Toast.LENGTH_SHORT).show();
		// } else {

		OkHttpClient client = new OkHttpClient();
		FormBody body = new FormBody.Builder().add("accountNo", dBID).build();
		Request request = new Request.Builder().addHeader("cookie", session)
				.url(urlUser).post(body).build();
		Call call = client.newCall(request);
		call.enqueue(new Callback() {

			@Override
			public void onResponse(Call call, Response response)
					throws IOException {
				String str = response.body().string();
				Log.e("LiNing", "用户数据===" + str);

				final DepInfo dInfo = new Gson().fromJson(str, DepInfo.class);
				if (dInfo.isRLO() == false) {
					SupInfoActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(context, "数据库已断开",
									Toast.LENGTH_SHORT).show();
						}
					});
				} else {
					if (dInfo != null) {
						SupInfoActivity.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								depInfo = dInfo.getIdNameList();
								if (depInfo != null) {

									arrays = depInfo.toArray();
									Log.e("LiNing", "888" + arrays);
									showCheckBoxListView();
								} else {
									Toast.makeText(context, "数据为空", Toast.LENGTH_LONG).show();
								}
							}

						});
					}
				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {

			}
		});
		// }
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
					SupInfoActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(context, "数据库已断开",
									Toast.LENGTH_SHORT).show();
						}
					});
				} else {
					if (dInfo != null) {
						SupInfoActivity.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								depInfo = dInfo.getIdNameList();
								if (depInfo != null) {

									arrays = depInfo.toArray();
									Log.e("LiNing", "888" + arrays);
									showCheckBoxListView();
								} else {
									Toast.makeText(context, "数据为空", Toast.LENGTH_LONG).show();
								}
							}

						});
					}
				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {

			}
		});
	}

	private void getInfo() {
		// TODO Auto-generated method stub
		requestDep();
	}

	private void requestDep() {
		// if (!dBID.equals("DB_BJ15")) {
		// Toast.makeText(context, "此账套无数据", Toast.LENGTH_SHORT).show();
		// } else {
		if (extraFlag.equals("6")) {
			url_sum = urlhs;
		} else {
			url_sum = urlDep;
		}
		OkHttpClient client = new OkHttpClient();
		FormBody body = new FormBody.Builder().add("accountNo", dBID).build();
		Request request = new Request.Builder().addHeader("cookie", session)
				.url(url_sum).post(body).build();
		Call call = client.newCall(request);
		call.enqueue(new Callback() {

			@Override
			public void onResponse(Call call, Response response)
					throws IOException {
				String str = response.body().string();
				Log.e("LiNing", "部门数据===" + str);
				final DepInfo dInfo = new Gson().fromJson(str, DepInfo.class);
				if (dInfo.isRLO() == false) {
					SupInfoActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(context, "数据库已断开",
									Toast.LENGTH_SHORT).show();
						}
					});
				} else {
					if (dInfo != null) {
						SupInfoActivity.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								depInfo = dInfo.getIdNameList();
								if (depInfo != null) {

									arrays = depInfo.toArray();
									Log.e("LiNing", "888" + arrays);
									showCheckBoxListView();
								} else {
									Toast.makeText(context, "数据为空", Toast.LENGTH_LONG).show();
								}
							}

						});
					}
				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {

			}
		});
		// }
	}

	private void showCheckBoxListView() {
		// TODO Auto-generated method stub
		// list = new ArrayList<HashMap<String, Object>>();
		// for (int i = 0; i < name.length; i++) {
		//
		// HashMap<String, Object> localHashMap = new HashMap<String, Object>();
		// localHashMap.put("item_tv", name[i]);
		// localHashMap.put("item_cb", false);
		// list.add(localHashMap);
		if (depInfo != null) {

			adapter = new SupInfoAdapter(context, depInfo,
					R.layout.info_item_four, new String[] { "item_id",
					"item_tv", "item_cb" }, new int[] { R.id.author_id,
					R.id.author, R.id.radio });
			lv.setAdapter(adapter);
			listStr = new ArrayList<String>();
			listIDs = new ArrayList<String>();
			lv.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(
						AdapterView<?> paramAnonymousAdapterView,
						View paramAnonymousView, int paramAnonymousInt,
						long paramAnonymousLong) {
					ViewHolder localViewHolder = (ViewHolder) paramAnonymousView
							.getTag();
					localViewHolder.cb.toggle();
					SupInfoAdapter.isSelected.put(paramAnonymousInt,
							localViewHolder.cb.isChecked());
					name = depInfo.get(paramAnonymousInt).getName();
					querryID = depInfo.get(paramAnonymousInt).getId();
					Log.e("LiNing", "选择的数据是=======" + name);
					if (localViewHolder.cb.isChecked()) {

						// listStr.add( arrays[paramAnonymousInt].toString());
						listStr.add(name);
						listIDs.add(querryID);
					} else {
						// listStr.remove(arrays[paramAnonymousInt].toString());
						listStr.remove(name);
						listIDs.remove(querryID);
					}
					// tv.setText("已选中" + listStr.size() + "项");

				}
			});
		}
	}

	public void back(View paramView) {
		finish();
	}

	public void check(View paramView) {
		if (depInfo == null) {
			Toast.makeText(context, "数据库已断开", Toast.LENGTH_SHORT).show();
			finish();
		} else {
			Log.e("LiNing", "111111111" + listStr.size());

			if (listStr.size() != 0) {
				String str = "";
				for (String name : listStr) {
					str += name + ",";
				}
				String sub_db_query = str.substring(0, str.length() - 1);
				String strid = "";
				for (String id : listIDs) {
					strid += id + ",";
				}

				String strIds = strid.substring(0, strid.length() - 1);
				// String str = listStr.toString().substring(1,
				// -1 + listStr.toString().length());
				// String strIds = listIDs.toString().substring(1,
				// -1 + listIDs.toString().length());
				Log.e("LiNing", "222222===" + str.toString());
				Intent localIntent = getIntent();
				localIntent.putExtra("data_return", sub_db_query);
				localIntent.putExtra("data_return_ids", strIds);
				setResult(1, localIntent);
				finish();
			} else if (listStr.size() == 0) {
				Toast.makeText(context, "数据为空", Toast.LENGTH_LONG).show();
				Intent localIntent = getIntent();
				localIntent.putExtra("data_return", "");
				localIntent.putExtra("data_return_ids", "");
				setResult(1, localIntent);
				finish();
			} else {
				Toast.makeText(context, "请选择数据", Toast.LENGTH_LONG).show();

			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	private String idString;
	private String nameString;
	private String allString;
	private String extraFlag;
	private String searUrl;
	private int cussor;
	private FormBody body;
	private String url_sum;

	@Override
	public void onClick(View v) {
		idString = infoId.getText().toString();
		nameString = infoName.getText().toString();
//		allString = infoAll.getText().toString();
		if (TextUtils.isEmpty(idString) && TextUtils.isEmpty(nameString)
				) {
			if (extraFlag.equals("2") || extraFlag.equals("6")) {

				requestDep();
			}
			if (extraFlag.equals("3")) {
				requestcust();
			}
			if (extraFlag.equals("4")) {

				requestuser();
			}
			if (extraFlag.equals("5")) {

				requestSup();
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
		if (extraFlag.equals("6")) {
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
					.addHeader("cookie", session).url(urlhs).post(body).build();
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
						SupInfoActivity.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								depInfo = dInfo.getIdNameList();
								arrays = depInfo.toArray();
								Log.e("LiNing", "888" + arrays);
								showCheckBoxListView();
							}

						});
					}
				}

				@Override
				public void onFailure(Call arg0, IOException arg1) {

				}
			});
		} else if (extraFlag.equals("2")) {
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
						SupInfoActivity.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								depInfo = dInfo.getIdNameList();
								arrays = depInfo.toArray();
								Log.e("LiNing", "888" + arrays);
								showCheckBoxListView();
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
						SupInfoActivity.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								depInfo = dInfo.getIdNameList();
								arrays = depInfo.toArray();
								Log.e("LiNing", "888" + arrays);
								showCheckBoxListView();
							}

						});
					}
				}

				@Override
				public void onFailure(Call arg0, IOException arg1) {

				}
			});
		} else if (extraFlag.equals("5")) {
			OkHttpClient client = new OkHttpClient();
			// FormBody body = new FormBody.Builder().add("accountNo", dBID)
			// .add("custType", "" + 1).add("id", idString).build();
			if (cussor == 1) {
				body = new FormBody.Builder().add("accountNo", dBID)
						.add("custType", "" + 2).add("id", idString).build();
			} else if (cussor == 2) {
				body = new FormBody.Builder().add("accountNo", dBID)
						.add("custType", "" + 2).add("name", nameString)
						.build();
			} else if (cussor == 3) {
				body = new FormBody.Builder().add("accountNo", dBID)
						.add("custType", "" + 2).add("name", nameString)
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
						SupInfoActivity.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								depInfo = dInfo.getIdNameList();
								arrays = depInfo.toArray();
								Log.e("LiNing", "888" + arrays);
								showCheckBoxListView();
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
						SupInfoActivity.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								depInfo = dInfo.getIdNameList();
								showCheckBoxListView();
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
