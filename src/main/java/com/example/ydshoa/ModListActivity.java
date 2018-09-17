package com.example.ydshoa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;

import com.example.bean.ModId;
import com.example.bean.ModList;
import com.example.bean.AccountInfo.IdNameList;
import com.example.bean.ModList.Module;
import com.example.bean.URLS;
import com.google.gson.Gson;
//import com.umeng.message.PushAgent;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ModListActivity extends Activity implements OnClickListener {
	private RadioGroup rg_one;
	private TextView head;
	private ListView lv_one;
	private Context context;
	private int selectPosition = -1;
	private String session;
	private SharedPreferences sp;
	private EditText infoId, infoName, infolevel;
	private ImageButton search;
	private MyAdapter myAdapter;
	private Module selectBrand;
	private String strID, name, level;
	// String urlAll =
	// "http://192.168.2.178:8080/InfManagePlatform/ModuleListgetModules.action";
	// String urlid =
	// "http://192.168.2.178:8080/InfManagePlatform/ModuleListgetModule.action";
	// String urlname =
	// "http://192.168.2.178:8080/InfManagePlatform/ModuleListgetmodulesWithName.action";
	// String urllevel =
	// "http://192.168.2.178:8080/InfManagePlatform/ModuleListgetSubModules.action";
	String urlAll = URLS.mod_list_url;
	String urlid = URLS.mod_id_list_url;
	String urlname = URLS.mod_name_list_url;
	String urllevel = URLS.mod_level_list_url;
	private List<Module> data;
	private TextView item_tv_id;
	private TextView item_tv_name;
	private TextView item_tv_level;
	private RadioButton item_tv_select;
	private LinearLayout vieworgo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mod_list);
		context = ModListActivity.this;
//		PushAgent.getInstance(context).onAppStart();
		sp = getSharedPreferences("ydbg", 0);
		session = sp.getString("SESSION", "");
		head = (TextView) findViewById(R.id.all_head);
		head.setText("模块列表");
		vieworgo = (LinearLayout) findViewById(R.id.ll_view_go);
		item_tv_id = (TextView) findViewById(R.id.id_item_mod);
		item_tv_name = (TextView) findViewById(R.id.id_name_mod);
		item_tv_level = (TextView) findViewById(R.id.id_level_mod);
		item_tv_select = (RadioButton) findViewById(R.id.id_select_mod);

		rg_one = (RadioGroup) findViewById(R.id.rg_mod_one);
		lv_one = (ListView) findViewById(R.id.lv_mod_one);

		infoId = (EditText) findViewById(R.id.search_id);
		infoName = (EditText) findViewById(R.id.search_name);
		infolevel = (EditText) findViewById(R.id.search_all);
		search = (ImageButton) findViewById(R.id.search);
		search.setOnClickListener(this);
		// 获取信息
		requestNetWorkForContacts();

	}

	private void requestNetWorkForContacts() {
		vieworgo.setVisibility(View.GONE);
		OkHttpClient client = new OkHttpClient();
		FormBody body = new FormBody.Builder().build();
		client.newCall(
				new Request.Builder().addHeader("cookie", session).post(body)
						.url(urlAll).build()).enqueue(new Callback() {

			@Override
			public void onResponse(Call call, Response response)
					throws IOException {
				String str = response.body().string();
				Log.e("LiNing", "--------" + str);
				final ModList cInfoDB = new Gson().fromJson(str, ModList.class);
				if (cInfoDB.isRLO() == false) {
					ModListActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(context, "数据库已断开",
									Toast.LENGTH_SHORT).show();
						}
					});
				} else {
					if (cInfoDB != null) {
						ModListActivity.this.runOnUiThread(new Runnable() {

							private List<Map> list;
							private Map<String, String> map;

							@Override
							public void run() {
								data = cInfoDB.getModule();
								Log.e("LiNing", "data--------" + data);
								showCheckBoxListView();
								ArrayList<String> modIDs = new ArrayList<String>();
								ArrayList<String> levels = new ArrayList<String>();
								list = new ArrayList<Map>();
								map = new HashMap<String, String>();
								// 集合组建
								for (int i = 0; i < data.size(); i++) {

									modIDs.add(data.get(i).getMod_ID()
											.toString());
									levels.add(data.get(i).getMod_Level()
											.toString());

									map.put(data.get(i).getMod_ID().toString(),
											data.get(i).getMod_Level()
													.toString());
								}
								Map<String, ArrayList<String>> map2 = new HashMap<String, ArrayList<String>>();
								String entryValue = null;
								String entryKay = null;
								ArrayList<String> tmpValue = new ArrayList<String>();
								ArrayList<String> tmpMap2Value = new ArrayList<String>();
								for (Entry<String, String> entry : map
										.entrySet()) {
									tmpValue.clear();
									tmpMap2Value.clear();
									entryKay = entry.getKey();
									entryValue = entry.getValue();

									if (map2.keySet().contains(entryValue)) {
										tmpMap2Value = map2.get(entryValue);
										tmpMap2Value.add(entryKay);
										map2.put(
												entryValue,
												(ArrayList<String>) tmpMap2Value
														.clone());
									} else {
										tmpValue.add(entryKay);
										map2.put(entryValue,
												(ArrayList<String>) tmpValue
														.clone());
									}
								}
								Log.e("LiNing", "data--------" + map2);
							}

						});
					}
				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void showCheckBoxListView() {
		// TODO Auto-generated method stub
		myAdapter = new MyAdapter(context, data);
		lv_one.setAdapter(myAdapter);
		lv_one.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// 获取选中的参数
				selectPosition = position;
				myAdapter.notifyDataSetChanged();
				selectBrand = data.get(position);
				strID = selectBrand.getMod_ID();
				name = selectBrand.getMod_Name();
				level = selectBrand.getMod_Level();
				// Toast.makeText(context,
				// "您选择的是：" + selectBrand.getMod_ID() + position,
				// Toast.LENGTH_SHORT).show();
				sp.edit().putString("DB_ID", strID).commit();
			}
		});
	}

	protected void setItemClick(final List<Module> data2) {
		lv_one.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// 获取选中的参数
				selectPosition = position;
				myAdapter.notifyDataSetChanged();
				selectBrand = data.get(position);
				strID = selectBrand.getMod_ID();
				Toast.makeText(context,
						"您选择的是：" + selectBrand.getMod_ID() + position,
						Toast.LENGTH_SHORT).show();
				sp.edit().putString("DB_ID", strID).commit();

			}
		});
	}

	public class MyAdapter extends BaseAdapter {

		Context context;
		LayoutInflater mInflater;
		List<Module> brandsList;

		public MyAdapter(Context context, List<Module> data) {
			this.context = context;
			this.brandsList = data;
			mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return brandsList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return brandsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.modlist_adapter,
						parent, false);
				viewHolder = new ViewHolder();
				viewHolder.id = (TextView) convertView
						.findViewById(R.id.id_item_mod);
				viewHolder.name = (TextView) convertView
						.findViewById(R.id.id_name_mod);
				viewHolder.level = (TextView) convertView
						.findViewById(R.id.id_level_mod);
				viewHolder.select = (RadioButton) convertView
						.findViewById(R.id.id_select_mod);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			String mod_ID = brandsList.get(position).getMod_ID();
			String mod_Name = brandsList.get(position).getMod_Name();
			String mod_Level = brandsList.get(position).getMod_Level();
			viewHolder.id.setText(mod_ID);
			viewHolder.name.setText(mod_Name);
			viewHolder.level.setText(mod_Level);
			if (selectPosition == position) {
				viewHolder.select.setChecked(true);
			} else {
				viewHolder.select.setChecked(false);
			}
			return convertView;
		}
	}

	public class ViewHolder {
		TextView id;
		TextView name;
		TextView level;
		RadioButton select;
	}

	private String id_search;
	private String name_search;
	private String level_search;
	private int cussor;

	// 条件查询
	@Override
	public void onClick(View v) {
		id_search = infoId.getText().toString();
		name_search = infoName.getText().toString();
		level_search = infolevel.getText().toString();
		if (TextUtils.isEmpty(id_search) && TextUtils.isEmpty(name_search)
				&& TextUtils.isEmpty(level_search)) {
			requestNetWorkForContacts();
			// Toast.makeText(ModListActivity.this, "请输入查询条件", 1).show();
		} else if (!id_search.equals("") && TextUtils.isEmpty(name_search)
				&& TextUtils.isEmpty(level_search)) {
			// cussor = 1;
			// getinfos();
			id_search();
		} else if (!name_search.equals("") && TextUtils.isEmpty(id_search)
				&& TextUtils.isEmpty(level_search)) {
			// name查询
			cussor = 2;
			getinfos();
		} else if (!level_search.equals("") && TextUtils.isEmpty(id_search)
				&& TextUtils.isEmpty(name_search)) {
			// level查询
			cussor = 3;
			getinfos();
		}
	}

	private Request request;
	private int flag;

	private void getinfos() {
		// TODO Auto-generated method stub
		OkHttpClient client = new OkHttpClient();
		if (cussor == 1) {

		} else if (cussor == 2) {
			FormBody body = new FormBody.Builder().add("mod_Name", name_search)
					.build();
			request = new Request.Builder().addHeader("cookie", session)
					.url(urlname).post(body).build();
		} else if (cussor == 3) {
			FormBody body = new FormBody.Builder().add("mod_Level",
					level_search).build();
			request = new Request.Builder().addHeader("cookie", session)
					.url(urllevel).post(body).build();
		}

		Call call = client.newCall(request);
		call.enqueue(new Callback() {

			@Override
			public void onResponse(Call call, Response response)
					throws IOException {
				// TODO Auto-generated method stub
				String str = response.body().string();
				Log.e("LiNing", "--------" + str);
				final ModList cInfoDB = new Gson().fromJson(str, ModList.class);
				if (cInfoDB != null) {
					ModListActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							data = cInfoDB.getModule();
							showCheckBoxListView();
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

	private void id_search() {
		vieworgo.setVisibility(View.VISIBLE);
		flag = 1;
		OkHttpClient client = new OkHttpClient();
		FormBody body = new FormBody.Builder().add("mod_ID", id_search).build();
		Request request = new Request.Builder().addHeader("cookie", session)
				.url(urlid).post(body).build();
		Call call = client.newCall(request);
		call.enqueue(new Callback() {

			@Override
			public void onResponse(Call call, Response response)
					throws IOException {
				String string = response.body().string();
				Log.e("LiNing", "showInfoId----" + string);
				final ModId cInfoID = new Gson().fromJson(string, ModId.class);
				Log.e("LiNing", "cInfoDB----" + cInfoID.toString());
				if (cInfoID != null) {
					ModListActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							lv_one.setAdapter(null);
							com.example.bean.ModId.Module moduleid = cInfoID
									.getModule();
							item_tv_id.setText(moduleid.getMod_ID());
							item_tv_name.setText(moduleid.getMod_Name());
							item_tv_level.setText(moduleid.getMod_Level());
							// item_tv_select.setChecked(true);
							item_tv_select.setClickable(true);
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

	// 获取结果
	public void one_ok(View v) {
		if (flag == 1) {
			if (item_tv_select.isChecked()) {
				Intent localIntent = getIntent();
				ViewHolder holder = new ViewHolder();
				Log.e("LiNing", "======" + holder.level);
				localIntent
						.putExtra("data_id", item_tv_id.getText().toString());
				localIntent.putExtra("data_name", item_tv_name.getText()
						.toString());
				localIntent.putExtra("data_level", item_tv_level.getText()
						.toString());
				setResult(1, localIntent);
				finish();
			} else {
				Toast.makeText(context, "请选择数据", Toast.LENGTH_LONG).show();
			}
		} else {
			if (strID != null) {

				if (strID.equals("0") || strID.equals("rt01")) {
					Toast.makeText(context, "该数据不能添加，请重新选择", Toast.LENGTH_LONG).show();
				} else {

					Intent localIntent = getIntent();
					localIntent.putExtra("data_id", strID);
					localIntent.putExtra("data_name", name);
					localIntent.putExtra("data_level", level);
					setResult(1, localIntent);
					finish();
				}
			} else {
				Toast.makeText(context, "请选择数据", Toast.LENGTH_LONG).show();
			}
		}
	}

	public void allback(View v) {
		finish();
	}
}
