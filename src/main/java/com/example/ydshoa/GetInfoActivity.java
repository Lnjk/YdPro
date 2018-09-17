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

import com.example.bean.AccountInfo;
import com.example.bean.AccountInfo.IdNameList;
import com.example.bean.Brand;
import com.example.bean.DepInfo;
import com.example.bean.FilterListenerDEP;
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
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import android.widget.Filter;
import android.widget.Filter.FilterListener;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class GetInfoActivity extends Activity implements OnClickListener {

	private RadioGroup rg_one;
	private ListView lv_one;
	// String[] name = { "信息部", "销售部", "人事部", "财务部", "总监部", "规划部", "工程部", "出纳部",
	// "购物部", "娱乐部" };
	private Context context;
	private int selectPosition = -1;
	private IdNameList selectBrand;
	private List<Brand> namesList;
	private String session;
	private SharedPreferences sp;
	private String strID;
	private String strName;
	// 账套
//	String url = "http://192.168.2.178:8080/InfManagePlatform/QueryErpInfqueryAccountDB.action";//
//	String url_reportNo = "http://192.168.2.178:8080/InfManagePlatform/QueryErpInfqueryAccountDB.action";//
	String url =  URLS.ERP_db_url;//
	String url_reportNo = URLS.ERP_db_url;//
	private List<IdNameList> data;
	private String nameDep;
	private String dBID;
	private MyAdapter myAdapter;
	// 搜索
	private EditText infoId, infoName, infoAll;
	private ImageButton search;
	private int cussor;
	private FormBody body;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_get_info);
		context = GetInfoActivity.this;
//		PushAgent.getInstance(context).onAppStart();
		sp = getSharedPreferences("ydbg", 0);
		session = sp.getString("SESSION", "");
		rg_one = (RadioGroup) findViewById(R.id.rg_info_one);
		lv_one = (ListView) findViewById(R.id.lv_rd_one);

		infoId = (EditText) findViewById(R.id.et_search_id_zt);
		infoName = (EditText) findViewById(R.id.et_search_name_zt);
//		infoAll = (EditText) findViewById(R.id.et_search_all_zt);
		search = (ImageButton) findViewById(R.id.iv_search_info_zt);
		search.setOnClickListener(this);
		// initDatas();
		// 请求数据

		getFlag();

	}
	String url_Myinfo = URLS.userInfo_url;
	private String string_new;
	private void getquery() {
		OkHttpClient client = new OkHttpClient();
		Request localRequest = new Request.Builder()
				.addHeader("cookie", session)
				.url(url_Myinfo)
				.build();
		client.newCall(localRequest).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response response) throws IOException {
				string_new = response.body().string();
				Log.e("LiNing", "string用户信息----" + string_new);
				if(string_new!=null){
					requestNetWorkForContacts_sale();
				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void getFlag() {
		extraFlag = getIntent().getStringExtra("flag");
		Log.e("LiNing", "flag====" + extraFlag);
		// 1，获取账套
		if (extraFlag.equals("1")) {

			requestNetWorkForContacts();
		}
		// 2，获取分支机构
		if (extraFlag.equals("2")) {

			requestNetWorkForContacts();
		}
		if (extraFlag.equals("111")) {
			getquery();
//			requestNetWorkForContacts_sale();
		}

	}
	private String str;
	private ArrayList dList;
	private void requestNetWorkForContacts_sale() {
		Gson gson = new GsonBuilder().setDateFormat(
				"yyyy-MM-dd HH:mm:ss").create();
		UserInfo fromJson = gson.fromJson(string_new, UserInfo.class);
		List<User_Query> user_Query = fromJson.getUser_Query();
		Log.e("LiNing", "用户信息----" + user_Query);
		if (user_Query != null && user_Query.size() > 0) {
			for (int i = 0; i < user_Query.size(); i++) {
				String query_DB2 = user_Query.get(i).getQuery_DB();
				OkHttpClient client = new OkHttpClient();
				FormBody new_body = new FormBody.Builder().add("id", query_DB2).build();
				Request request = new Request.Builder().addHeader("cookie", session)
						.url(url).post(new_body).build();
				Call call = client.newCall(request);
				call.enqueue(new Callback() {

					@Override
					public void onResponse(Call arg0, Response response) throws IOException {
						str = response.body().string();
						Log.e("LiNing", "查询数据===" + str);
						final AccountInfo aDb1 = new Gson().fromJson(str,
								AccountInfo.class);
						if (aDb1 != null) {
							GetInfoActivity.this.runOnUiThread(new Runnable() {

								@Override
								public void run() {
									data = aDb1.getIdnamelist();
									Log.e("LiNing", "查询数据======data=" + data);
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
		}
	}

	// 获取账套
	private void requestNetWorkForContacts() {
		OkHttpClient client = new OkHttpClient();
		FormBody body = new FormBody.Builder().build();
		if(extraFlag.equals("1")){
			base_url = url;
		}
		if(extraFlag.equals("2")){
			base_url = url_reportNo;
		}
		client.newCall(
				new Request.Builder().addHeader("cookie", session).post(body)
						.url(base_url).build()).enqueue(new Callback() {

			@Override
			public void onResponse(Call call, Response response)
					throws IOException {
				String str = response.body().string();
				Log.e("LiNing", "账套数据===" + str);
				final AccountInfo aDb1 = new Gson().fromJson(str,
						AccountInfo.class);
				Log.e("LiNing", "账套数据===aDb1" + aDb1);
				if (aDb1.isRlo() == false) {
					GetInfoActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(context, "数据库已断开",
									Toast.LENGTH_SHORT).show();
						}
					});
				} else {
					if (aDb1 != null) {
						GetInfoActivity.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								data = aDb1.getIdnamelist();
								Log.e("LiNing", "账套数据===data" + data);
								if(data.size() > 0 && data != null){
									for (int i = 0; i < data.size(); i++) {
										String db_Id = data.get(i).getDb_Id();
										String name = data.get(i).getName();
										HashMap<String, Object> item = new HashMap<String, Object>();
										item.put("账套ID", db_Id);
										item.put("账套NAME",name);
										ArrayList<HashMap<String, Object>> dList = new ArrayList();
										dList.add(item);
										String stritem = new Gson().toJson(dList);
									}
								}
								showCheckBoxListView();
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

	private void initDatas() {
		// 初始化ListView适配器的数据
		namesList = new ArrayList<Brand>();
		Brand brand0 = new Brand("信息部");
		Brand brand1 = new Brand("销售部");
		Brand brand2 = new Brand("人事部");
		Brand brand3 = new Brand("财务部");
		Brand brand4 = new Brand("总监部");
		Brand brand5 = new Brand("规划部");
		Brand brand6 = new Brand("工程部");
		Brand brand7 = new Brand("出纳部");
		Brand brand8 = new Brand("购物部");
		Brand brand9 = new Brand("娱乐部");
		namesList.add(brand0);
		namesList.add(brand1);
		namesList.add(brand2);
		namesList.add(brand3);
		namesList.add(brand4);
		namesList.add(brand5);
		namesList.add(brand6);
		namesList.add(brand7);
		namesList.add(brand8);
		namesList.add(brand9);
		showCheckBoxListView();

	}

	private void showCheckBoxListView() {
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
				strID = selectBrand.getId();
				strName = selectBrand.getName();
//				Toast.makeText(context,
//						"您选择的是：" + selectBrand.getDb_Id() + position,
//						Toast.LENGTH_SHORT).show();
				sp.edit().putString("DB_ID", strID).commit();
			}
		});

	}

	protected void setItemClick(final List<IdNameList> data2) {
		// TODO Auto-generated method stub
		lv_one.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// 获取选中的参数
				selectPosition = position;
				myAdapter.notifyDataSetChanged();
				selectBrand = data.get(position);
				strID = selectBrand.getId();
				strName = selectBrand.getName();
				Toast.makeText(context,
						"您选择的是：" + selectBrand.getId() + position,
						Toast.LENGTH_SHORT).show();
				sp.edit().putString("DB_ID", strID).commit();

			}
		});
	}

	public class MyAdapter extends BaseAdapter {

		Context context;
		List<IdNameList> brandsList;
		LayoutInflater mInflater;

		// // 搜索
		// private List<IdNameList> list = new ArrayList<IdNameList>();
		// private MyFilter filter = null;// 创建MyFilter对象
		// private FilterListenerDEP listener = null;// 接口对象

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
		TextView id;
		TextView name;
		RadioButton select;
	}

	// 获取结果
	public void one_ok(View v) {
		Intent localIntent = getIntent();
		localIntent.putExtra("data_return", strID);
		localIntent.putExtra("data_return_name", strName);
		setResult(1, localIntent);
		finish();
	}

	private String idString;
	private String nameString;
	private String allString;
	private String extraFlag;
	private String base_url;

	@Override
	public void onClick(View v) {
		idString = infoId.getText().toString();
		nameString = infoName.getText().toString();
//		allString = infoAll.getText().toString();
		if (TextUtils.isEmpty(idString) && TextUtils.isEmpty(nameString)
				) {
			requestNetWorkForContacts();
			Toast.makeText(context, "请输入查询条件", Toast.LENGTH_LONG).show();
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
			requestNetWorkForContacts();
		}
	}

	private void getSearchId() {
		OkHttpClient client = new OkHttpClient();
		if (cussor == 1) {
			body = new FormBody.Builder().add("id", idString).build();
		} else if (cussor == 2) {
			body = new FormBody.Builder().add("name", nameString).build();
		} else if (cussor == 3) {
			body = new FormBody.Builder().add("name", nameString)
					.add("id", idString).build();
		}

		Request request = new Request.Builder().addHeader("cookie", session)
				.url(url).post(body).build();
		Call call = client.newCall(request);
		call.enqueue(new Callback() {
			@Override
			public void onResponse(Call call, Response response)
					throws IOException {
				String str = response.body().string();
				Log.e("LiNing", "查询数据===" + str);
				final AccountInfo aDb1 = new Gson().fromJson(str,
						AccountInfo.class);
				if (aDb1 != null) {
					GetInfoActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							data = aDb1.getIdnamelist();
							showCheckBoxListView();
						}
					});
				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {

			}
		});
	}
}
