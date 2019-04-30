package com.example.ydshoa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.adapter.ConditionInfoAdapter;
import com.example.adapter.SupInfoAdapter;
import com.example.bean.AccountInfo;
import com.example.bean.Brand;
import com.example.bean.DepInfo;
import com.example.bean.URLS;
import com.example.bean.UserInfo;
import com.example.bean.ViewHolder;
import com.example.bean.DepInfo.IdNameList;
import com.example.bean.UserInfo.User_Query;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class CondicionInfoActivity extends Activity implements OnClickListener {

	private Context context;
	private TextView head;
	private int index, flag;// flag判断是哪些数据，index判断erp数据
	private String extraFlag, extraId, session, query_DB, querryID, name;
	// private String extraFlag, extraId, session, query_DB, query_Dep,
	// query_Sup,
	// query_Cust, query_User, querryID, name;
	private SharedPreferences sp;
	private ConditionInfoAdapter adapter;
	private ListView lv;
	private Button allCheck, rever, ok, no;
	private List<User_Query> user_Query;
	ArrayList<String> listStr, listIDs;
	private List<IdNameList> depInfo;
	private SupInfoAdapter adapter_all;
	private Request localRequest;
	// private String url_reportNo = URLS.sale_fzjg;
	private String url_area = URLS.area_url;
	private String url_employee = URLS.employee_url;
	private String url_prdNo = URLS.prdNo_url;
	private String url_prdIndex = URLS.prdIndex;
	private String url_prdWh = URLS.prdWh;
	private String url_chkUser = URLS.ERP_user_url;
	private String url_zt = URLS.ERP_db_url;
	private String url_djlb = URLS.djlb_url;
	// id转name
	// private String url_idToDB = URLS.ERP_db_url;
	private String url_idTodep = URLS.ERP_dep_url;
	private String url_idTocust = URLS.ERP_cust_url;
	private String url_idTouser = URLS.ERP_user_url;
	String url_idTohs = URLS.ERP_hs_url;
	String url_Myinfo = URLS.userInfo_url;
	private FormBody body,body_yw;
	private UserInfo info;
	private FormBody body_base;
	private String url_base;
	// id_db
	private int selectPosition = -1;
	private User_Query selectBrand;
	private MyAdapter myAdapter;
	private String strID;
	private int dex;
	// 搜索
	private EditText infoId, infoName;
	private ImageButton search;
	private String stritem_idTname;
    private String bandname_hd;
    private String string2;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_condicion_info);
		context = CondicionInfoActivity.this;
		//推送
//		PushAgent.getInstance(context).onAppStart();
		sp = getSharedPreferences("ydbg", 0);
		session = sp.getString("SESSION", "");
		initView();

	}

	private void initView() {
		dList_idTn = new ArrayList();
		head = (TextView) findViewById(R.id.all_head);
		// head.setText("条件信息");// 最后根据flag判断表头
		lv = (ListView) findViewById(R.id.lv_codition);
		allCheck = (Button) findViewById(R.id.c_selectall);
		rever = (Button) findViewById(R.id.c_inverseselect);
		ok = (Button) findViewById(R.id.c_btnok);
		no = (Button) findViewById(R.id.c_cancel);
		allCheck.setOnClickListener(this);
		rever.setOnClickListener(this);
		ok.setOnClickListener(this);
		no.setOnClickListener(this);
//		getIDtoName();
		getFlag();
		// 搜索
		infoId = (EditText) findViewById(R.id.et_search_id);
		infoName = (EditText) findViewById(R.id.et_search_name);
		// infoAll = (EditText) findViewById(R.id.et_search_all);
		search = (ImageButton) findViewById(R.id.iv_search_info);
		search.setOnClickListener(this);

		
	}

	private void getIDtoName() {
		OkHttpClient client = new OkHttpClient();
		FormBody body = new FormBody.Builder().build();
		client.newCall(new Request.Builder().addHeader("cookie", session).post(body)
				.url(url).build()).enqueue(new Callback() {
					
					@Override
					public void onResponse(Call arg0, Response response) throws IOException {
						String str = response.body().string();
						Log.e("LiNing", "账套数据===" + str);
						final AccountInfo aDb1 = new Gson().fromJson(str,
								AccountInfo.class);
						if (aDb1 != null) {
							CondicionInfoActivity.this.runOnUiThread(new Runnable() {

								

								

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
	private ArrayList<HashMap<String, Object>> dList_idTn;
	private void getFlag() {

		extraFlag = getIntent().getStringExtra("flag");

		if (extraFlag.equals("1")) {
			// requestDB();// 获取账套
			findViewById(R.id.search_all_head).setVisibility(View.GONE);
			flag = 1;
			allCheck.setVisibility(View.GONE);
			rever.setVisibility(View.GONE);
			no.setVisibility(View.GONE);
			head.setText("ERP账套");
			index = 1;
			getERPQueryinfo_db();
			// getERPQueryinfo();
//			 getERPQueryinfo_DB_ID();
		}
		if (extraFlag.equals("10")) {
			// flag = 1;
			head.setText("ERP核算单位");
			index = 21;
			getERPQueryinfo();
		}
		if (extraFlag.equals("11")) {
			// flag = 1;
			head.setText("ERP部门");
			index = 2;
			getERPQueryinfo();
		}
		if (extraFlag.equals("12")) {
			// flag = 1;
			head.setText("ERP厂商");
			index = 3;
			getERPQueryinfo();
		}
		if (extraFlag.equals("13")) {
			// flag = 1;
			head.setText("ERP终端");
			index = 4;
			getERPQueryinfo();
		}
		if (extraFlag.equals("14")) {
			// flag = 1;
			head.setText("ERP用户");
			index = 5;
			getERPQueryinfo();
		}
		if (extraFlag.equals("15")) {
			head.setText("ERP审核人");
			getClassinfo();
		}
		if (extraFlag.equals("16")) {
			head.setText("销售渠道");
			getClassinfo();
		}
		if (extraFlag.equals("17")) {
			head.setText("销售人员");
			getClassinfo();
		}
		if (extraFlag.equals("18")) {
			head.setText("品号");
			getClassinfo();
		}
		if (extraFlag.equals("19")) {
			head.setText("货品中类");
			getClassinfo();
		}
		if (extraFlag.equals("20")) {
			head.setText("货品库区");
			getClassinfo();
		}
		if (extraFlag.equals("21")) {
			 depInfo=new ArrayList<IdNameList>();
			IdNameList mnsj=new IdNameList("1","冠珠");
			IdNameList mnsj1=new IdNameList("2","LD");
			IdNameList mnsj2=new IdNameList("3","法恩莎");
			IdNameList mnsj3=new IdNameList("4","好莱客");
			depInfo.add(mnsj);
			depInfo.add(mnsj1);
			depInfo.add(mnsj2);
			depInfo.add(mnsj3);
			showCheckBoxListView();
		}
		if (extraFlag.equals("22")) {
			head.setText("单据类别");
			getClassinfo();
		}

	}

	

	private void getClassinfo() {
		extraId = getIntent().getStringExtra("queryID");
		Log.e("LiNing", "====" + extraId + "===" + query_DB);
		OkHttpClient client = new OkHttpClient();
		if(extraFlag.equals("22")){
			body_yw = new FormBody.Builder().add("accountNo", extraId)
					.add("bil_Id","SA")
					.build();
		}else{

			body_yw = new FormBody.Builder().add("accountNo", extraId)
					.build();
		}
		if (extraFlag.equals("15")) {

			localRequest = new Request.Builder().addHeader("cookie", session)
					.post(body_yw).url(url_chkUser).build();
		}
		if (extraFlag.equals("16")) {

			localRequest = new Request.Builder().addHeader("cookie", session)
					.post(body_yw).url(url_area).build();
		}
		if (extraFlag.equals("17")) {

			localRequest = new Request.Builder().addHeader("cookie", session)
					.post(body_yw).url(url_employee).build();
		}
		if (extraFlag.equals("18")) {

			localRequest = new Request.Builder().addHeader("cookie", session)
					.post(body_yw).url(url_prdNo).build();
		}
		if (extraFlag.equals("19")) {

			localRequest = new Request.Builder().addHeader("cookie", session)
					.post(body_yw).url(url_prdIndex).build();
		}
		if (extraFlag.equals("20")) {

			localRequest = new Request.Builder().addHeader("cookie", session)
					.post(body_yw).url(url_prdWh).build();
		}
		if (extraFlag.equals("22")) {

			localRequest = new Request.Builder().addHeader("cookie", session)
					.post(body_yw).url(url_djlb).build();
		}
		client.newCall(localRequest).enqueue(new Callback() {

			@Override
			public void onResponse(Call call, Response response)
					throws IOException {
				String str = response.body().string();
				final DepInfo dInfo = new Gson().fromJson(str, DepInfo.class);
				if (dInfo.isRLO() == false) {
					CondicionInfoActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(context, "数据库已断开",
									Toast.LENGTH_SHORT).show();
						}
					});
				} else {
					if (dInfo != null) {
						CondicionInfoActivity.this
								.runOnUiThread(new Runnable() {

									@Override
									public void run() {
										depInfo = dInfo.getIdNameList();
										if (depInfo != null) {
											showCheckBoxListView();
										} else {
											Toast.makeText(context, "数据为空",
													Toast.LENGTH_SHORT).show();
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
	private String string;
	private void getERPQueryinfo_db() {
		String string_tj = sp.getString("all_query_tj", "");
		stritem_idTname = sp.getString("all_query_tj_to", "");
		Log.e("LiNing", "用户信息---string_tj-----" + string_tj);
		Log.e("LiNing", "用户信息--string_tj_to--" + stritem_idTname);
		Gson gson = new GsonBuilder().setDateFormat(
				"yyyy-MM-dd HH:mm:ss").create();
		info = gson.fromJson(string_tj, UserInfo.class);
		user_Query = info.getUser_Query();
		if (user_Query != null && user_Query.size() > 0) {
			CondicionInfoActivity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// setCheck();// 多选
					setdb_id();// 单选
				}
			});

		}

	}
	String url =  URLS.ERP_db_url;
	private String str;
	
	private void getERPQueryinfo() {

		OkHttpClient client = new OkHttpClient();
		Request localRequest = new Request.Builder()
				.addHeader("cookie", session)
				.url(url_Myinfo)
//				.url("http://oa.ydshce.com:8080/InfManagePlatform/LoginUserInf.action")
				.build();
		client.newCall(localRequest).enqueue(new Callback() {

			@Override
			public void onResponse(Call call, Response response)
					throws IOException {
				String string = response.body().string();
				// Log.e("LiNing", "用户信息----" + string);
				Gson gson = new GsonBuilder().setDateFormat(
						"yyyy-MM-dd HH:mm:ss").create();
				info = gson.fromJson(string, UserInfo.class);
				user_Query = info.getUser_Query();
				if (user_Query != null && user_Query.size() > 0) {
					for (int i = 0; i < user_Query.size(); i++) {
						extraId = getIntent().getStringExtra("queryID");
						Log.e("LiNing", "用户信息--all--" + extraId);
						String query_DB2 = user_Query.get(i).getQuery_DB();
						String query_Hs2 = user_Query.get(i).getQuery_CompDep();
						String query_Dep2 = user_Query.get(i).getQuery_Dep();
						String query_Sup2 = user_Query.get(i).getQuery_Sup();
						String query_Cust2 = user_Query.get(i).getQuery_Cust();
						String query_User2 = user_Query.get(i).getQuery_User();
						Log.e("LiNing", "用户信息----" + query_Sup2 + query_User2);
						OkHttpClient client = new OkHttpClient();
						// if (extraFlag.equals("1")) {
						//
						// body_base = new FormBody.Builder()
						// .add("accountNo", query_DB2)
						// .add("id", query_DB2).build();
						// url_base = url_idToDB;
						// }

						if (extraId.equals(query_DB2)) {
							//通过id获取。。。
							if (extraFlag.equals("10")) {
								if (query_Hs2.equals("null")) {
									body_base = new FormBody.Builder().add(
											"accountNo", extraId).build();
									url_base = url_idTohs;

								} else {

									body_base = new FormBody.Builder()
											.add("accountNo", extraId)
											.add("id", query_Hs2).build();
									url_base = url_idTohs;
								}
							}
							if (extraFlag.equals("11")) {
								if (query_Dep2.equals("null")) {
									body_base = new FormBody.Builder().add(
											"accountNo", extraId).build();
									url_base = url_idTodep;

								} else {

									body_base = new FormBody.Builder()
											.add("accountNo", extraId)
											.add("id", query_Dep2).build();
									url_base = url_idTodep;
								}
							}
							if (extraFlag.equals("14")) {
								if (query_User2.equals("null")) {
									body_base = new FormBody.Builder().add(
											"accountNo", extraId).build();
									url_base = url_idTouser;
								} else {

									body_base = new FormBody.Builder()
											.add("accountNo", extraId)
											.add("id", query_User2).build();
									url_base = url_idTouser;
								}
							}
							if (extraFlag.equals("12")) {
								if (query_Sup2.equals("null")) {
									body_base = new FormBody.Builder()
											.add("accountNo", extraId)
											.add("custType", "" + 2).build();
									url_base = url_idTocust;
								} else {

									body_base = new FormBody.Builder()
											.add("accountNo", extraId)
											.add("id", query_Sup2)

											.build();
									url_base = url_idTocust;
								}
							}
							if (extraFlag.equals("13")) {
								if (query_Cust2.equals("null")) {
									body_base = new FormBody.Builder()
											.add("accountNo", extraId)
											.add("custType", "" + 1).build();
									url_base = url_idTocust;

								} else {

									body_base = new FormBody.Builder()
											.add("accountNo", extraId)
											.add("id", query_Cust2)

											.build();
									url_base = url_idTocust;
								}
							}

							Request localRequest = new Request.Builder()
									.addHeader("cookie", session)
									.post(body_base).url(url_base).build();
							client.newCall(localRequest).enqueue(
									new Callback() {

										@Override
										public void onResponse(Call call,
												Response response)
												throws IOException {
											String str = response.body()
													.string();
											Log.e("LiNing", "用户信息--should--"
													+ str);
											
											final DepInfo dInfo = new Gson()
													.fromJson(str,
															DepInfo.class);

											if (dInfo.isRLO() == false) {
												CondicionInfoActivity.this
														.runOnUiThread(new Runnable() {

															@Override
															public void run() {
																Toast.makeText(
																		context,
																		"数据库已断开",
																		Toast.LENGTH_SHORT)
																		.show();
															}
														});
											} else {
												if (dInfo != null) {
													CondicionInfoActivity.this
															.runOnUiThread(new Runnable() {

																@Override
																public void run() {
																	depInfo = dInfo
																			.getIdNameList();
																	Log.e("LiNing", "用户信息--should--"
																			+ depInfo);
																	if (depInfo != null) {

																		showCheckBoxListView();
																	} else {
																		Toast.makeText(
																				context,
																				"数据为空",
																				Toast.LENGTH_SHORT)
																				.show();
																	}
																}

															});
												}
											}
										}

										@Override
										public void onFailure(Call arg0,
												IOException arg1) {

										}
									});
						} else {
							Log.e("LiNing", "NO");
						}
					}
					// setCheck();

				}

			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {

			}
		});
	}

	// 其他数据
	private void showCheckBoxListView() {

		
		adapter_all = new SupInfoAdapter(context, depInfo,
				R.layout.info_item_four, new String[] { "item_id", "item_tv",
						"item_cb" }, new int[] { R.id.author_id, R.id.author,
						R.id.radio });
		lv.setAdapter(adapter_all);
		listStr = new ArrayList<String>();
		listIDs = new ArrayList<String>();
		lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> paramAnonymousAdapterView,
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

	protected void setdb_id() {
		myAdapter = new MyAdapter(context, user_Query);
		lv.setAdapter(myAdapter);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
				// 获取选中的参数
				selectPosition = position;
				myAdapter.notifyDataSetChanged();
				selectBrand = user_Query.get(position);
				strID = selectBrand.getQuery_DB();
				Log.e("LiNing", "用户信息----" + strID);
				// Toast.makeText(context,
				// "您选择的是：" + selectBrand.getDb_Id() + position,
				// Toast.LENGTH_SHORT).show();
				stritem_idTname = sp.getString("all_query_tj_to", "");
				Log.e("LiNing", "用户信息--string_tj_to--" + stritem_idTname);
                try {
                    JSONArray arr = new JSONArray(stritem_idTname);
                    int size = arr.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject jsonObject = arr.getJSONObject(i);
                        Log.e("LiNing", "11111111====jsonObject==="+jsonObject);
                        String str_zt = jsonObject.get("ZT_ID").toString();
                        Log.e("LiNing", "11111111======="+str_zt);
                        if (strID.equals(str_zt)) {
                            string2 = jsonObject.get("ZT_NAME").toString();
                        }
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

			}
		});
	}

	// erp用户
	protected void setCheck() {
		dex = 1;
		adapter = new ConditionInfoAdapter(index, context, user_Query,
				R.layout.info_item_four, new String[] { "item_id", "item_tv",
						"item_cb" }, new int[] { R.id.author_id, R.id.author,
						R.id.radio });
		lv.setAdapter(adapter);
		listStr = new ArrayList<String>();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ViewHolder localViewHolder = (ViewHolder) view.getTag();
				localViewHolder.cb.toggle();
				ConditionInfoAdapter.isSelected.put(position,
						localViewHolder.cb.isChecked());
				if (extraFlag.equals("1")) {
					query_DB = user_Query.get(position).getQuery_DB();
					// getidtoName();
				}

				if (localViewHolder.cb.isChecked()) {

					listStr.add(query_DB);
				} else {
					listStr.remove(query_DB);
				}
			}
		});
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.iv_search_info:
			getsearch();
			break;
		case R.id.c_selectall:
			if (flag == 1) {
				for (int i = 0; i < user_Query.size(); i++) {
					ConditionInfoAdapter.isSelected.put(i, true);
					decideInfo(i);
				}
				adapter.notifyDataSetChanged();// 注意这一句必须加上，否则checkbox无法正常更新状态
			} else {
				for (int i = 0; i < depInfo.size(); i++) {
					SupInfoAdapter.isSelected.put(i, true);
					listStr.add(depInfo.get(i).name);
					listIDs.add(depInfo.get(i).id);
				}
				adapter_all.notifyDataSetChanged();// 注意这一句必须加上，否则checkbox无法正常更新状态
			}

			Log.e("LiNing", "listStr========" + listStr);
			break;
		case R.id.c_inverseselect:
			if (flag == 1) {

				for (int i = 0; i < user_Query.size(); i++) {
					if (ConditionInfoAdapter.isSelected.get(i) == false) {
						ConditionInfoAdapter.isSelected.put(i, true);
						decideInfo(i);

					} else {
						ConditionInfoAdapter.isSelected.put(i, false);
						// listStr.remove(user_Query.get(i).getQuery_DB());
						if (extraFlag.equals("1")) {

							listStr.remove(user_Query.get(i).getQuery_DB());
						}
						if (extraFlag.equals("11")) {

							listStr.remove(user_Query.get(i).getQuery_Dep());
						}
						if (extraFlag.equals("12")) {

							listStr.remove(user_Query.get(i).getQuery_Sup());
						}
						if (extraFlag.equals("13")) {

							listStr.remove(user_Query.get(i).getQuery_Cust());
						}
						if (extraFlag.equals("14")) {

							listStr.remove(user_Query.get(i).getQuery_User());
						}
					}

				}
				adapter.notifyDataSetChanged();
			} else {
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
				adapter_all.notifyDataSetChanged();
			}

			break;
		case R.id.c_btnok:
			if (flag == 1) {

				if (user_Query == null) {
					Toast.makeText(context, "数据库已断开", Toast.LENGTH_SHORT)
							.show();
					finish();
				} else {
					if (dex == 1) {
						if (listStr.size() > 0) {
							String str = "";
							for (String name : listStr) {
								if (extraFlag.equals("1")) {

									str += name + ",";
								} else {
									str += name + ";";
								}
							}
							String sub_db_query = str.substring(0,
									str.length() - 1);
							Log.e("LiNing", "222222===" + str.toString());
							Intent localIntent = getIntent();
							// localIntent.putExtra("condition_db", strID);
							localIntent.putExtra("condition_db", sub_db_query);
							setResult(1, localIntent);
							finish();
						} else if (listStr.size() == 0) {
							Toast.makeText(context, "数据为空", Toast.LENGTH_SHORT)
									.show();

						} else {
							Toast.makeText(context, "请选择数据", Toast.LENGTH_SHORT)
									.show();

						}
					} else {
						Intent localIntent = getIntent();
						localIntent.putExtra("condition_db", strID);
						localIntent.putExtra("condition_name", string2);
						setResult(1, localIntent);
						finish();
					}

				}
			} else {
				if (depInfo == null) {
					Toast.makeText(context, "数据库已断开", Toast.LENGTH_SHORT)
							.show();
					finish();
				} else {
					Log.e("LiNing", "111111111" + listStr.size());

					if (listStr.size() != 0) {
						String str = "";
						for (String name : listStr) {
							str += name + ",";
						}
						String sub_db_query = str
								.substring(0, str.length() - 1);
						String strid = "";
						for (String id : listIDs) {
							strid += id + ",";
						}

						String strIds = strid.substring(0, strid.length() - 1);
						Log.e("LiNing", "222222===" + str.toString());
						Intent localIntent = getIntent();
						localIntent.putExtra("data_return", sub_db_query);
						localIntent.putExtra("data_return_ids", strIds);
						setResult(1, localIntent);
						finish();
					} else if (listStr.size() == 0) {
						Toast.makeText(context, "数据为空", Toast.LENGTH_SHORT)
								.show();
						Intent localIntent = getIntent();
						localIntent.putExtra("data_return", "");
						localIntent.putExtra("data_return_ids", "");
						setResult(1, localIntent);
						finish();
					} else {
						Toast.makeText(context, "请选择数据", Toast.LENGTH_SHORT)
								.show();

					}
				}
			}
			break;
		case R.id.c_cancel:
			finish();
			break;

		default:
			break;
		}
	}

	private void decideInfo(int i) {
		if (extraFlag.equals("1")) {

			listStr.add(user_Query.get(i).getQuery_DB());
		}
		if (extraFlag.equals("11")) {

			listStr.add(user_Query.get(i).getQuery_Dep());
		}
		if (extraFlag.equals("12")) {

			listStr.add(user_Query.get(i).getQuery_Sup());
		}
		if (extraFlag.equals("13")) {

			listStr.add(user_Query.get(i).getQuery_Cust());
		}
		if (extraFlag.equals("14")) {

			listStr.add(user_Query.get(i).getQuery_User());
		}
	}

	public class MyAdapter extends BaseAdapter {

		Context context;
		List<User_Query> brandsList;
		LayoutInflater mInflater;

		public MyAdapter(Context context, List<User_Query> user_Query) {
			this.context = context;
			this.brandsList = user_Query;
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
			String id_query_new = brandsList.get(position).getQuery_DB();
			viewHolder.id.setText(id_query_new);
			Log.e("LiNing", "11111111=====2222=="+id_query_new);
//			viewHolder.id.setText(brandsList.get(position).getQuery_DB());
			try {
				JSONArray arr = new JSONArray(stritem_idTname);
				int size = arr.length();
				for (int i = 0; i < size; i++) {
					JSONObject jsonObject = arr.getJSONObject(i);
					Log.e("LiNing", "11111111====jsonObject==="+jsonObject);
					String str_zt = jsonObject.get("ZT_ID").toString();
					Log.e("LiNing", "11111111======="+str_zt);
					if (id_query_new.equals(str_zt)) {
						String string2 = jsonObject.get("ZT_NAME").toString();
						viewHolder.name.setText(string2);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			}
			if (selectPosition == position) {
				viewHolder.select.setChecked(true);
			} else {
				viewHolder.select.setChecked(false);
			}
			return convertView;
		}

		public class ViewHolder {
			TextView id;
			TextView name;
			RadioButton select;
		}
	}

	private String idString;
	private String nameString;
	// private String allString;
	private int cussor;
	private Object[] arrays;
	private List list;
	private List<IdNameList> list_new;

	private void getsearch() {
		idString = infoId.getText().toString();
		nameString = infoName.getText().toString();
		// allString = infoAll.getText().toString();
		if (TextUtils.isEmpty(idString) && TextUtils.isEmpty(nameString)) {
			if (extraFlag.equals("10") || extraFlag.equals("11")
					|| extraFlag.equals("12") || extraFlag.equals("13")
					|| extraFlag.equals("14")) {

				getERPQueryinfo();
			}
			if (extraFlag.equals("1")) {

				getERPQueryinfo_db();
			}
			if (extraFlag.equals("15") || extraFlag.equals("16")
					|| extraFlag.equals("17") || extraFlag.equals("18")
					|| extraFlag.equals("19") || extraFlag.equals("20")) {
				getClassinfo();
			}
		} else if (!idString.equals("") && TextUtils.isEmpty(nameString)) {
			cussor = 1;
			getSearchId();

		} else if (!nameString.equals("") && TextUtils.isEmpty(idString)) {
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
		if (extraFlag.equals("15")) {
			OkHttpClient client = new OkHttpClient();
			if (cussor == 1) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("id", idString).build();
			} else if (cussor == 2) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("name", nameString).build();
			} else if (cussor == 3) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("name", nameString).add("id", idString).build();
			} else if (cussor == 4) {
				getClassinfo();
			}

			Request request = new Request.Builder()
					.addHeader("cookie", session).url(url_chkUser).post(body)
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
						CondicionInfoActivity.this
								.runOnUiThread(new Runnable() {

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
		} else if (extraFlag.equals("1")) {
			OkHttpClient client = new OkHttpClient();
			if (cussor == 1) {
				body = new FormBody.Builder().add("id", idString).build();
			} else if (cussor == 2) {
				body = new FormBody.Builder().add("name", nameString).build();
			} else if (cussor == 3) {
				body = new FormBody.Builder().add("name", nameString)
						.add("id", idString).build();
			} else if (cussor == 4) {
				getERPQueryinfo_db();
			}

			Request request = new Request.Builder()
					.addHeader("cookie", session).url(url_zt).post(body)
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
						CondicionInfoActivity.this
								.runOnUiThread(new Runnable() {

									@Override
									public void run() {
										depInfo = dInfo.getIdNameList();
										// arrays = depInfo.toArray();
										// Log.e("LiNing", "888" + arrays);
										setdb_id();// 单选
									}

								});
					}
				}

				@Override
				public void onFailure(Call arg0, IOException arg1) {

				}
			});
		} else if (extraFlag.equals("20")) {
			OkHttpClient client = new OkHttpClient();
			if (cussor == 1) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("id", idString).build();
			} else if (cussor == 2) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("name", nameString).build();
			} else if (cussor == 3) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("name", nameString).add("id", idString).build();
			} else if (cussor == 4) {
				getClassinfo();
			}

			Request request = new Request.Builder()
					.addHeader("cookie", session).url(url_prdWh).post(body)
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
						CondicionInfoActivity.this
								.runOnUiThread(new Runnable() {

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
		} else if (extraFlag.equals("16")) {
			OkHttpClient client = new OkHttpClient();
			if (cussor == 1) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("id", idString).build();
			} else if (cussor == 2) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("name", nameString).build();
			} else if (cussor == 3) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("name", nameString).add("id", idString).build();
			} else if (cussor == 4) {
				getClassinfo();
			}

			Request request = new Request.Builder()
					.addHeader("cookie", session).url(url_area).post(body)
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
						CondicionInfoActivity.this
								.runOnUiThread(new Runnable() {

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
		} else if (extraFlag.equals("17")) {
			OkHttpClient client = new OkHttpClient();
			if (cussor == 1) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("id", idString).build();
			} else if (cussor == 2) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("name", nameString).build();
			} else if (cussor == 3) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("name", nameString).add("id", idString).build();
			} else if (cussor == 4) {
				getClassinfo();
			}

			Request request = new Request.Builder()
					.addHeader("cookie", session).url(url_employee).post(body)
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
						CondicionInfoActivity.this
								.runOnUiThread(new Runnable() {

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
		} else if (extraFlag.equals("18")) {
			OkHttpClient client = new OkHttpClient();
			if (cussor == 1) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("id", idString).build();
			} else if (cussor == 2) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("name", nameString).build();
			} else if (cussor == 3) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("name", nameString).add("id", idString).build();
			} else if (cussor == 4) {
				getClassinfo();
			}

			Request request = new Request.Builder()
					.addHeader("cookie", session).url(url_prdNo).post(body)
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
						CondicionInfoActivity.this
								.runOnUiThread(new Runnable() {

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
		} else if (extraFlag.equals("19")) {
			OkHttpClient client = new OkHttpClient();
			if (cussor == 1) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("id", idString).build();
			} else if (cussor == 2) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("name", nameString).build();
			} else if (cussor == 3) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("name", nameString).add("id", idString).build();
			} else if (cussor == 4) {
				getClassinfo();
			}

			Request request = new Request.Builder()
					.addHeader("cookie", session).url(url_prdIndex).post(body)
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
						CondicionInfoActivity.this
								.runOnUiThread(new Runnable() {

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
		} else if (extraFlag.equals("10")) {
			OkHttpClient client = new OkHttpClient();
			if (cussor == 1) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("id", idString).build();
			} else if (cussor == 2) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("name", nameString).build();
			} else if (cussor == 3) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("name", nameString).add("id", idString).build();
			} else if (cussor == 4) {
				getERPQueryinfo();
			}

			Request request = new Request.Builder()
					.addHeader("cookie", session).url(url_idTohs).post(body)
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
						CondicionInfoActivity.this
								.runOnUiThread(new Runnable() {

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
		} else if (extraFlag.equals("11")) {
			OkHttpClient client = new OkHttpClient();
			if (cussor == 1) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("id", idString).build();
			} else if (cussor == 2) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("name", nameString).build();
			} else if (cussor == 3) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("name", nameString).add("id", idString).build();
			} else if (cussor == 4) {
				getERPQueryinfo();
			}

			Request request = new Request.Builder()
					.addHeader("cookie", session).url(url_idTodep).post(body)
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
						CondicionInfoActivity.this
								.runOnUiThread(new Runnable() {

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
		} else if (extraFlag.equals("12")) {
			OkHttpClient client = new OkHttpClient();
			// FormBody body = new FormBody.Builder().add("accountNo", extraId)
			// .add("custType", "" + 1).add("id", idString).build();
			if (cussor == 1) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("custType", "" + 2).add("id", idString).build();
			} else if (cussor == 2) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("custType", "" + 2).add("name", nameString)
						.build();
			} else if (cussor == 3) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("custType", "" + 2).add("name", nameString)
						.add("id", idString).build();
			} else if (cussor == 4) {
				getERPQueryinfo();
			}
			Request request = new Request.Builder()
					.addHeader("cookie", session).url(url_idTocust).post(body)
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
						CondicionInfoActivity.this
								.runOnUiThread(new Runnable() {

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
		} else if (extraFlag.equals("13")) {
			OkHttpClient client = new OkHttpClient();
			// FormBody body = new FormBody.Builder().add("accountNo", extraId)
			// .add("custType", "" + 1).add("id", idString).build();
			if (cussor == 1) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("custType", "" + 1).add("id", idString).build();
			} else if (cussor == 2) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("custType", "" + 1).add("name", nameString)
						.build();
			} else if (cussor == 3) {
				body = new FormBody.Builder().add("accountNo", extraId)
						.add("custType", "" + 1).add("name", nameString)
						.add("id", idString).build();
			} else if (cussor == 4) {
				getERPQueryinfo();
			}
			Request request = new Request.Builder()
					.addHeader("cookie", session).url(url_idTocust).post(body)
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
						CondicionInfoActivity.this
								.runOnUiThread(new Runnable() {

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
		} else if (extraFlag.equals("14")) {
			OkHttpClient client = new OkHttpClient();
			// FormBody body = new FormBody.Builder().add("accountNo", extraId)
			// .add("custType", "" + 2).add("id", idString).build();
			if (cussor == 1) {
				body = new FormBody.Builder().add("accountNo", extraId)
				// .add("custType", "" + 2)
						.add("id", idString).build();
			} else if (cussor == 2) {
				body = new FormBody.Builder().add("accountNo", extraId)
				// .add("custType", "" + 2)
						.add("name", nameString).build();
			} else if (cussor == 3) {
				body = new FormBody.Builder().add("accountNo", extraId)
				// .add("custType", "" + 2)
						.add("name", nameString).add("id", idString).build();
			} else if (cussor == 4) {
				getERPQueryinfo();
			}
			Request request = new Request.Builder()
					.addHeader("cookie", session).url(url_idTouser).post(body)
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
						CondicionInfoActivity.this
								.runOnUiThread(new Runnable() {

									@Override
									public void run() {
										depInfo = dInfo.getIdNameList();
										showCheckBoxListView();
									}

								});
					} else {
						Toast.makeText(context, "数据为空", Toast.LENGTH_SHORT)
								.show();
					}
				}

				@Override
				public void onFailure(Call arg0, IOException arg1) {

				}
			});
		}
	}

	public void allback(View v) {
		finish();
	}
}
