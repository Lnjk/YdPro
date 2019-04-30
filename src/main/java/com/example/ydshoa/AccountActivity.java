package com.example.ydshoa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.example.LeftOrRight.MyHScrollView;
import com.example.bean.AccountDB1;
import com.example.bean.AccountDB1.QueryListBean;
import com.example.bean.JsonRootBean;
import com.example.bean.URLS;
import com.google.gson.Gson;
//import com.umeng.message.PushAgent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class AccountActivity extends Activity implements OnClickListener {
	private SharedPreferences sp;
	private Context mContext;
	private String session, db_id_positions, server_Address, db_Name,
			db_Driver, db_Pwd, db_Url, db_User, extra;
	// private String session, db_id_positions, server_Address, str, db_Id,
	// db_Name, db_Driver, db_Pwd, db_Url, db_User, extra;
	private boolean sp_query, sp_add, sp_del, sp_set;
	private Button add, del, ok, queyr, reset, testBtn, testReset, updatReset;
	private EditText accountId, accountUser, accountPwd, accountName,
			accountIp, accountPort, ipupdata, portup, driver, upname, url,
			upuser, uppwd;
	private int db_Prot;
	// private int checkFalg, db_Prot;
	RelativeLayout mHead;
	private List<Integer> checkedIndexList;
	private List<CheckBox> checkBoxList;
	private List<String> idList;
	List<AccountDB1.QueryListBean> data;
	private ListView mStockListView;
	private AccountAdapter aDbAdatper;
	private Spinner spSql, uptype;
	private List<String> list = new ArrayList<String>();
	private AlertDialog alertDialog;
	private ArrayAdapter<String> adapter;
	private boolean rlo;
	private TextView id;
	private String del_url;
	private FormBody localFormBody;
	// String urlDel_one =
	// "http://192.168.2.178:8080/InfManagePlatform/AccountDBdeleteAccountDB.action";
	// String urlDel_more =
	// "http://192.168.2.178:8080/InfManagePlatform/AccountDBdeleteAccountDBs.action";
	String urlDel_one = URLS.account_del_url;
	String urlquery = URLS.account_url;
	String urlupdata = URLS.account_updata_url;
	String urlcon = URLS.account_con_url;
	String urladd = URLS.account_add_url;
	private String ps_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_account);
		//推送
//		PushAgent.getInstance(AccountActivity.this).onAppStart();
		this.mContext = this;
		this.sp = getSharedPreferences("ydbg", 0);
		session = sp.getString("SESSION", "");
		ps_id = sp.getString("PASS", "");
		Log.e("LiNing", "获取的session=====" + session);
		sp_query = sp.getBoolean("ACCOUNT_QUERY", false);
		sp_add = sp.getBoolean("ACCOUNT_ADD", false);
		sp_del = sp.getBoolean("ACCOUNT_DEL", false);
		sp_set = sp.getBoolean("ACCOUNT_UP", false);
		Log.e("LiNing", "sp数据----" + sp_add + sp_query + sp_del + sp_set);
		queyr = ((Button) findViewById(R.id.query));
		// this.queyr.setTypeface(Typeface.createFromAsset(getAssets(),
		// "font/jt.ttf"));
		add = ((Button) findViewById(R.id.add));
		del = ((Button) findViewById(R.id.del));
		reset = ((Button) findViewById(R.id.resets));
		queyr.setOnClickListener(this);
		add.setOnClickListener(this);
		del.setOnClickListener(this);
		reset.setOnClickListener(this);
		this.mHead = ((RelativeLayout) findViewById(R.id.account_head));
		this.mHead.setFocusable(true);
		this.mHead.setClickable(true);
		this.mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
		checkedIndexList = new ArrayList<Integer>();
		idList = new ArrayList<String>();
		checkBoxList = new ArrayList<CheckBox>();
		this.mStockListView = ((ListView) findViewById(R.id.stock_list_view));
		mStockListView
				.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
		mStockListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				for (int i = 0; i < checkBoxList.size(); i++) {
					checkBoxList.get(i).setVisibility(View.VISIBLE);
				}
			}
		});
	}

	class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {
		ListViewAndHeadViewTouchLinstener() {
		}

		@SuppressLint("ClickableViewAccessibility")
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			((HorizontalScrollView) AccountActivity.this.mHead
					.findViewById(R.id.horizontalScrollView1))
					.onTouchEvent(event);
			return false;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.query:
				if (ps_id.equals("1")) {
					requestNetWorkForContacts();// 查询
				} else {
					if (sp_query == true) {
						requestNetWorkForContacts();// 查询
						// this.torf.setVisibility(View.GONE);
						Toast.makeText(mContext, "以上为全部信息", Toast.LENGTH_SHORT)
								.show();
					} else {
						Toast.makeText(mContext, "请等待...", Toast.LENGTH_SHORT).show();
					}
				}
				break;
			case R.id.add:
				// 判断是否有该权限
				if (ps_id.equals("1")) {
					insertAccount();// 新增
				} else {
					if (sp_add == true) {
						insertAccount();// 新增
					} else {
						Toast.makeText(mContext, "请等待...", Toast.LENGTH_SHORT).show();
					}
				}
				break;
			case R.id.del:

				if (checkedIndexList != null && checkedIndexList.size() > 0) {
					if (ps_id.equals("1")) {
//					delAccount();
						new AlertDialog.Builder(this)
								.setTitle("是否删除数据？")
								.setPositiveButton("确定",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface paramAnonymousDialogInterface,
													int paramAnonymousInt) {
												delAccount();
												Toast.makeText(
														AccountActivity.this.mContext,
														"当前选择账套删除成功",
														Toast.LENGTH_SHORT)
														.show();
											}
										}).setNegativeButton("取消", null).show();
					} else {
						if (sp_del == true) {
							new AlertDialog.Builder(this)
									.setTitle("是否删除数据？")
									.setPositiveButton("确定",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface paramAnonymousDialogInterface,
														int paramAnonymousInt) {
													delAccount();
													Toast.makeText(
															AccountActivity.this.mContext,
															"当前选择账套删除成功",
															Toast.LENGTH_SHORT)
															.show();
												}
											}).setNegativeButton("取消", null).show();
						} else {
							Toast.makeText(mContext, "请等待...", Toast.LENGTH_SHORT)
									.show();
						}
					}
				} else {
					Toast.makeText(mContext, "请选择数据", Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.resets:
				if (0 < idList.size() && idList.size() <= 1) {
					if (ps_id.equals("1")) {
						update();
					} else {

						if (sp_set == true) {
							update();
							Toast.makeText(mContext, "更改成功", Toast.LENGTH_SHORT)
									.show();
						} else {
							Toast.makeText(mContext, "请等待......", Toast.LENGTH_SHORT)
									.show();
						}
					}
				} else {
					checkedIndexList.clear();
					idList.clear();
					Toast.makeText(mContext, "请选择一条数据", Toast.LENGTH_SHORT).show();
				}
				break;

			default:
				break;
		}
	}

	@SuppressLint("InflateParams")
	private void update() {

		View localView = getLayoutInflater()
				.inflate(R.layout.update_info, null);
		this.id = ((TextView) localView.findViewById(R.id.infoet_id));
		this.id.setText(this.extra);
		// this.id.setTypeface(Typeface
		// .createFromAsset(getAssets(), "font/jt.ttf"));
		this.uppwd = ((EditText) localView.findViewById(R.id.infoet_pwd));
		this.uppwd.setText(this.db_Pwd);
		this.upname = ((EditText) localView.findViewById(R.id.infoet_name));
		this.upname.setText(this.db_Name);
		this.upuser = ((EditText) localView.findViewById(R.id.infoet_user));
		this.upuser.setText(this.db_User);
		this.ipupdata = ((EditText) localView.findViewById(R.id.infoet_adress));
		this.ipupdata.setText(this.server_Address);
		this.portup = ((EditText) localView.findViewById(R.id.infoet_port));
		this.portup.setText("" + this.db_Prot);
		uptype = (Spinner) localView.findViewById(R.id.sp_ZTenter_update);
		// this.uptype.isSelected();
		list.add("MYSQL");
		list.add("SQLServe2005");
		list.add("SQLServe2008");
		list.add("SQLServe2012");
		list.add("SQLServe2014");
		list.add("SQLServe2016");
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		// 第三步：为适配器设置下拉列表下拉时的菜单样式。
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 第四步：将适配器添加到下拉列表上
		uptype.setAdapter(adapter);
		uptype.setSelection(1);
		uptype.setOnItemSelectedListener(new OnItemSelectedListener() {

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
		uptype.setOnTouchListener(new Spinner.OnTouchListener() {

			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});
		uptype.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub

			}
		});
		this.driver = ((EditText) localView.findViewById(R.id.infoet_driver));
		this.driver.setText(this.db_Driver);
		this.url = ((EditText) localView.findViewById(R.id.infoet_url));
		this.url.setText(this.db_Url);
		this.testReset = ((Button) localView.findViewById(R.id.testUpdata));
		this.updatReset = ((Button) localView.findViewById(R.id.update));
		localView.findViewById(R.id.ib_update_ald).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						alertDialog.dismiss();
					}
				});
		this.alertDialog = new AlertDialog.Builder(this.mContext).create();
		this.alertDialog.setView(localView);
		this.alertDialog.setCancelable(true);
		this.alertDialog.show();
		this.testReset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					public void run() {
						AccountActivity.this.testUpcon();
					}
				}).start();
			}
		});
		this.updatReset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				OkHttpClient localOkHttpClient = new OkHttpClient();
				FormBody localFormBody = new FormBody.Builder()
						.add("connectionTest", "true")
						.add("oldDb_Id", AccountActivity.this.extra)
						.add("db_Name",
								AccountActivity.this.upname.getText()
										.toString())
						.add("db_Pwd",
								AccountActivity.this.uppwd.getText().toString())
						.add("db_Prot",
								AccountActivity.this.portup.getText()
										.toString())
						.add("server_Address",
								AccountActivity.this.ipupdata.getText()
										.toString())
						.add("db_Type", uptype.getSelectedItem().toString())
						.add("db_Id",
								AccountActivity.this.id.getText().toString())
						.add("db_User",
								AccountActivity.this.upuser.getText()
										.toString()).build();

				Request localBuilder = new Request.Builder()
						.addHeader("cookie", session).url(urlupdata)
						.post(localFormBody).build();
				Log.e("LiNing", "更新数据======="+extra+id.getText().toString());
				localOkHttpClient.newCall(localBuilder).enqueue(new Callback() {

					@Override
					public void onResponse(Call paramAnonymous2Call,
										   Response paramAnonymous2Response)
							throws IOException {
						Log.e("LiNing", paramAnonymous2Response.body().string());
						Log.e("LiNing", "更改成功");
						AccountActivity.this.alertDialog.dismiss();
						requestNetWorkForContacts();
					}

					@Override
					public void onFailure(Call arg0, IOException arg1) {
						Log.e("LiNing", "更改失败");
					}
				});
			}
		});

	}

	protected void testUpcon() {
		String str1 = this.id.getText().toString();
		String db_Name = this.upname.getText().toString();
		String str2 = this.upuser.getText().toString();
		String str3 = this.uppwd.getText().toString();
		String str4 = this.portup.getText().toString();
		String str5 = this.ipupdata.getText().toString();
		OkHttpClient localOkHttpClient = new OkHttpClient();
		FormBody localFormBody = new FormBody.Builder().add("db_Id", str1)
				.add("db_Name", db_Name).add("db_Pwd", str3)
				.add("db_Prot", str4).add("server_Address", str5)
				.add("db_Type", uptype.getSelectedItem().toString())
				.add("db_User", str2).build();
		System.out.println(localFormBody);
		localOkHttpClient.newCall(
				new Request.Builder().addHeader("cookie", session)
						// .url("http://192.168.2.178:8080/InfManagePlatform/AccountDBgetConnectionTest.action")
						.url(urlcon).post(localFormBody).build()).enqueue(
				new Callback() {

					@Override
					public void onResponse(Call paramAnonymousCall,
										   Response paramAnonymousResponse) throws IOException {
						String str = paramAnonymousResponse.body().string();
						Log.e("LiNing", str);
						final JsonRootBean localJsonRootBean = (JsonRootBean) new Gson()
								.fromJson(str, JsonRootBean.class);
						if (localJsonRootBean != null)
							AccountActivity.this.runOnUiThread(new Runnable() {
								public void run() {
									AccountActivity.this.rlo = localJsonRootBean
											.getRLO();
									Log.e("LiNing", "88888"
											+ AccountActivity.this.rlo);
									if (AccountActivity.this.rlo) {
										AccountActivity.this.testReset
												.setVisibility(View.GONE);
										AccountActivity.this.updatReset
												.setVisibility(View.VISIBLE);
									} else {

										Toast.makeText(
												AccountActivity.this.mContext,
												"连接失败", Toast.LENGTH_SHORT)
												.show();
									}
								}
							});
					}

					@Override
					public void onFailure(Call arg0, IOException arg1) {

					}
				});
	}

	protected void delAccount() {

		if (this.data != null) {
			Log.e("LiNing 45", this.extra);
			OkHttpClient localOkHttpClient = new OkHttpClient();
			if (0 < idList.size() && idList.size() <= 1) {
				localFormBody = new FormBody.Builder().add("delDb_ID", extra)
						.add("connectionTest", "true").build();
				del_url = urlDel_one;
			} else {
				// del_url = urlDel_more;
				// localFormBody = new FormBody.Builder().add("isApp", "T")
				// .add("delDb_ID", extra).build();
				localFormBody = new FormBody.Builder().add("delDb_ID", extra)
						.add("connectionTest", "true").build();
				del_url = urlDel_one;
			}
			localOkHttpClient.newCall(
					new Request.Builder().addHeader("cookie", session)
							.url(del_url).post(localFormBody).build()).enqueue(
					new Callback() {

						@Override
						public void onResponse(Call paramAnonymousCall,
											   Response paramAnonymousResponse)
								throws IOException {
							Log.e("LiNing s", paramAnonymousResponse.body()
									.string());
							Log.e("LiNing", "当前选择账套删除成功！");
							requestNetWorkForContacts();
						}

						@Override
						public void onFailure(Call arg0, IOException arg1) {
							Log.e("LiNing", "当前选择账套删除失败！");
						}
					});
		}

	}

	@SuppressLint({ "InflateParams", "ClickableViewAccessibility" })
	private void insertAccount() {
		View localView = getLayoutInflater().inflate(R.layout.account_insert,
				null);
		this.accountId = ((EditText) localView.findViewById(R.id.et_ZTnumber));
		this.accountName = (EditText) localView.findViewById(R.id.et_ZTname);
		this.accountIp = ((EditText) localView.findViewById(R.id.et_ip));
		this.accountPort = ((EditText) localView
				.findViewById(R.id.et_servicEnter));
		this.accountUser = ((EditText) localView.findViewById(R.id.et_guest));
		this.accountPwd = ((EditText) localView.findViewById(R.id.et_ZTpsd));
		this.testBtn = ((Button) localView.findViewById(R.id.btn_test));
		this.testBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ZTtest();// 添加前测试
			}
		});
		this.ok = ((Button) localView.findViewById(R.id.btn_ok));
		this.ok.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				AccountActivity.this.testAcc();
				Toast.makeText(AccountActivity.this.mContext, "添加成功",
						Toast.LENGTH_SHORT).show();
			}
		});
		this.spSql = ((Spinner) localView.findViewById(R.id.sp_ZTenter));
		list.add("MYSQL");
		list.add("SQLServe2005");
		list.add("SQLServe2008");
		list.add("SQLServe2012");
		list.add("SQLServe2014");
		list.add("SQLServe2016");
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		// 第三步：为适配器设置下拉列表下拉时的菜单样式。
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 第四步：将适配器添加到下拉列表上
		spSql.setAdapter(adapter);
		spSql.setSelection(1);
		spSql.setOnItemSelectedListener(new OnItemSelectedListener() {

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
		spSql.setOnTouchListener(new Spinner.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});
		spSql.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub

			}
		});
		localView.findViewById(R.id.ib_add_ald).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						alertDialog.dismiss();
					}
				});
		this.alertDialog = new AlertDialog.Builder(this.mContext).create();
		this.alertDialog.setView(localView);
		this.alertDialog.setCancelable(true);
		this.alertDialog.show();
	}

	protected void testAcc() {

		String str1 = this.accountId.getText().toString();
		String str2 = this.accountName.getText().toString();
		String str3 = this.accountUser.getText().toString();
		String str4 = this.accountPwd.getText().toString();
		String str5 = this.accountPort.getText().toString();
		String str6 = this.accountIp.getText().toString();
		Log.e("LiNing", str1 + str2 + str3 + str4 + str5 + str6);
		if ((TextUtils.isEmpty(str1)) || (TextUtils.isEmpty(str2))
				|| (TextUtils.isEmpty(str3)) || (TextUtils.isEmpty(str4))
				|| (TextUtils.isEmpty(str5)) || (TextUtils.isEmpty(str6))
				|| (TextUtils.isEmpty(this.spSql.toString()))) {
			Toast.makeText(this.mContext, "数据不能为空", Toast.LENGTH_SHORT).show();
		} else {
			OkHttpClient localOkHttpClient = new OkHttpClient();
			FormBody localFormBody = new FormBody.Builder().add("db_Id", str1)
					.add("db_Name", str2).add("db_Pwd", str4)
					.add("db_Prot", str5).add("server_Address", str6)
					.add("db_Type", spSql.getSelectedItem().toString())
					.add("connectionTest", "true").add("db_User", str3).build();
			System.out.println(localFormBody);
			localOkHttpClient.newCall(
					new Request.Builder().addHeader("cookie", session)
							// .url("http://192.168.2.178:8080/InfManagePlatform/AccountDBinsertAccountDB.action")
							.url(urladd).post(localFormBody).build()).enqueue(
					new Callback() {

						@Override
						public void onResponse(Call paramAnonymousCall,
											   Response paramAnonymousResponse)
								throws IOException {
							Log.e("LiNing", paramAnonymousResponse.body()
									.string());
							Log.e("LiNing", "添加中，请稍等");
							AccountActivity.this.alertDialog.dismiss();
							requestNetWorkForContacts();
						}

						@Override
						public void onFailure(Call arg0, IOException arg1) {
							Log.e("LiNing", "添加" + arg1);
						}
					});
		}
	}

	protected void ZTtest() {

		String str1 = this.accountId.getText().toString();
		String str2 = this.accountName.getText().toString();
		String str3 = this.accountUser.getText().toString();
		String str4 = this.accountPwd.getText().toString();
		String str5 = this.accountPort.getText().toString();
		String str6 = this.accountIp.getText().toString();
		Log.e("LiNing", str1 + str2 + str3 + str4 + str5 + str6);
		if ((TextUtils.isEmpty(str1)) || (TextUtils.isEmpty(str2))
				|| (TextUtils.isEmpty(str3)) || (TextUtils.isEmpty(str4))
				|| (TextUtils.isEmpty(str5)) || (TextUtils.isEmpty(str6))
				|| (TextUtils.isEmpty(this.spSql.toString()))) {
			Toast.makeText(this.mContext, "数据不能为空", Toast.LENGTH_SHORT).show();
		} else {
			OkHttpClient localOkHttpClient = new OkHttpClient();
			FormBody localFormBody = new FormBody.Builder().add("db_Id", str1)
					.add("db_Pwd", str4).add("db_Prot", str5)
					.add("server_Address", str6)
					.add("db_Type", spSql.getSelectedItem().toString())
					.add("db_User", str3).build();
			System.out.println(localFormBody);
			localOkHttpClient.newCall(
					new Request.Builder().addHeader("cookie", session)
							.url(urlcon).post(localFormBody).build()).enqueue(
					new Callback() {

						@Override
						public void onResponse(Call call,
											   Response paramAnonymousResponse)
								throws IOException {
							String str = paramAnonymousResponse.body().string()
									.trim();
							Log.e("LiNing", str);
							final JsonRootBean localJsonRootBean = (JsonRootBean) new Gson()
									.fromJson(str, JsonRootBean.class);
							if (localJsonRootBean != null) {
								AccountActivity.this
										.runOnUiThread(new Runnable() {

											@Override
											public void run() {
												AccountActivity.this.rlo = localJsonRootBean
														.getRLO();
												Log.e("LiNing",
														"88888"
																+ AccountActivity.this.rlo);
												if (rlo == true) {
													AccountActivity.this.testBtn
															.setVisibility(View.GONE);
													AccountActivity.this.ok
															.setVisibility(View.VISIBLE);
												} else if (rlo == false) {
													Toast.makeText(
															AccountActivity.this.mContext,
															"连接失败,数据库不存在",
															Toast.LENGTH_SHORT)
															.show();
												}
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

	private void requestNetWorkForContacts() {
		OkHttpClient localOkHttpClient = new OkHttpClient();
		FormBody localFormBody = new FormBody.Builder().build();
		localOkHttpClient.newCall(
				new Request.Builder().addHeader("cookie", session)
						// .url("http://192.168.2.178:8080/InfManagePlatform/AccountDBqueryAccountDB.action")
						.url(urlquery).post(localFormBody).build()).enqueue(
				new Callback() {

					@Override
					public void onResponse(Call paramAnonymousCall,
										   Response paramAnonymousResponse) throws IOException {
						String str = paramAnonymousResponse.body().string();
						Log.e("LiNing  str", str);

						final AccountDB1 localAccountDB1 = (AccountDB1) new Gson()
								.fromJson(str, AccountDB1.class);
						if (localAccountDB1.isRLO() == false) {
							AccountActivity.this.runOnUiThread(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									Toast.makeText(AccountActivity.this, "无数据",
											Toast.LENGTH_SHORT).show();
								}
							});
						} else {

							if (localAccountDB1 != null)
								AccountActivity.this
										.runOnUiThread(new Runnable() {
											public void run() {
												data = localAccountDB1
														.getQueryList();
												aDbAdatper = new AccountAdapter(
														R.layout.account, data,
														AccountActivity.this);
												mStockListView
														.setAdapter(aDbAdatper);
												aDbAdatper
														.notifyDataSetChanged();
												checkedIndexList.clear();
												idList.clear();
											}
										});
						}
					}

					@Override
					public void onFailure(Call arg0, IOException arg1) {

					}
				});
	}

	public class AccountAdapter extends BaseAdapter {
		int id_row_layout;
		public List<ViewHolder> mHolderList = new ArrayList<com.example.ydshoa.AccountActivity.AccountAdapter.ViewHolder>();
		LayoutInflater mInflater;
		private List<QueryListBean> users;

		// private int selectedPosition = -1;// 选中的位置

		public AccountAdapter(int id_row_layout, List<QueryListBean> data,
							  AccountActivity accountActivity) {
			this.id_row_layout = id_row_layout;
			this.users = data;
			this.mInflater = LayoutInflater.from(mContext);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return users.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		// public void setSelectedPosition(int position) {
		// selectedPosition = position;
		// }

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				synchronized (AccountActivity.this) {
					convertView = mInflater.inflate(id_row_layout, null);
					holder = new ViewHolder();

					MyHScrollView scrollView1 = (MyHScrollView) convertView
							.findViewById(R.id.horizontalScrollView1);

					holder.scrollView = scrollView1;
					holder.checkbox = (CheckBox) convertView
							.findViewById(R.id.cust_listDeleteCheckBox_account);
					holder.checkbox.setVisibility(View.VISIBLE);
					holder.layouts = (RelativeLayout) convertView
							.findViewById(R.id.rl_item_account);
					holder.id = (TextView) convertView
							.findViewById(R.id.textView1_id_acc);
					holder.ip = (TextView) convertView
							.findViewById(R.id.textView5_ip_acc);
					holder.ip.setSelected(true);// 一直滚动
					holder.name = (TextView) convertView
							.findViewById(R.id.textView2_sql_acc);
					holder.port = (TextView) convertView
							.findViewById(R.id.textView6_post_acc);
					holder.pwd = (TextView) convertView
							.findViewById(R.id.textView4_psd_acc);
					holder.type = (TextView) convertView
							.findViewById(R.id.textView7_sqlsp_acc);
					holder.type.setSelected(true);
					holder.user = (TextView) convertView
							.findViewById(R.id.textView3_user_acc);
					MyHScrollView headSrcrollView = (MyHScrollView) mHead
							.findViewById(R.id.horizontalScrollView1);
					headSrcrollView
							.AddOnScrollChangedListener(new OnScrollChangedListenerImp(
									scrollView1));
					// 将item中的checkbox放到checkBoxList中
					checkBoxList.add(holder.checkbox);
					convertView.setTag(holder);
				}
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			QueryListBean bean = users.get(position);
			holder.id.setText(bean.getDb_Id());
			holder.user.setText(bean.getDb_User());
			holder.pwd.setText(bean.getDb_Pwd());
			holder.port.setText("" + bean.getDb_Prot());
			holder.ip.setText(bean.getDb_Url());
			holder.type.setText(bean.getDb_Type());
			holder.name.setText(bean.getDb_Name());
			holder.checkbox.setOnCheckedChangeListener(new CheckBoxListener(
					position));
			return convertView;
		}

		class OnScrollChangedListenerImp implements
				MyHScrollView.OnScrollChangedListener {
			MyHScrollView mScrollViewArg;

			public OnScrollChangedListenerImp(MyHScrollView mScrollViewArg) {
				super();
				this.mScrollViewArg = mScrollViewArg;
			}

			public void onScrollChanged(int paramInt1, int paramInt2,
										int paramInt3, int paramInt4) {
				this.mScrollViewArg.smoothScrollTo(paramInt1, paramInt2);
			}
		};

		class ViewHolder {
			HorizontalScrollView scrollView;
			CheckBox checkbox;
			public RelativeLayout layouts;
			public TextView id;
			public TextView ip;
			public TextView name;
			public TextView port;
			public TextView pwd;
			public View tabItem;
			public TextView type;
			public TextView user;

			ViewHolder() {
			}
		}
	}

	public class CheckBoxListener implements OnCheckedChangeListener {
		int positions;

		public CheckBoxListener(int position) {
			this.positions = position;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
									 boolean isChecked) {
			if (isChecked) {
				// checkFalg = 1;
				// Toast.makeText(AccountActivity.this, "选中了" + positions, 0)
				// .show();
				checkedIndexList.add(positions);
				QueryListBean listBean = data.get(positions);
				db_id_positions = listBean.getDb_Id();
				db_Name = listBean.getDb_Name();
				db_User = listBean.getDb_User();
				db_Pwd = listBean.getDb_Pwd();
				db_Prot = listBean.getDb_Prot();
				// db_Type = listBean.getDb_Type();
				db_Url = listBean.getDb_Url();
				db_Driver = listBean.getDb_Driver();
				server_Address = listBean.getServer_Address();
				idList.add(db_id_positions);
				String zts_str = "";
				for (String ids_db : idList) {
					zts_str += ids_db + ",";
				}
				extra = zts_str.substring(0, zts_str.length() - 1);
				Log.e("LiNing", "--------sub_id" + extra);
			} else {
				// checkFalg = 0;
				checkedIndexList.remove((Integer) positions);
				idList.remove(db_id_positions);
				Log.e("LiNing", "--------删除id集合" + idList);
				if (idList != null && idList.size() > 0) {
					String zts_str = "";
					for (String ids_db : idList) {
						zts_str += ids_db + ",";
					}
					extra = zts_str.substring(0, zts_str.length() - 1);
					Log.e("LiNing", "--------sub_id" + extra);
				} else {
					Toast.makeText(AccountActivity.this, "未选中数据",
							Toast.LENGTH_SHORT).show();
				}
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

	public void allback(View v) {
		finish();
	}
}
