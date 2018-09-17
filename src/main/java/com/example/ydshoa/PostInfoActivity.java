package com.example.ydshoa;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.adapter.InfoShowAdapter;
import com.example.bean.BackApplication;
import com.example.bean.NetUtil;
import com.example.bean.SysApplication;
import com.google.gson.Gson;
//import com.umeng.message.PushAgent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PostInfoActivity extends Activity implements OnClickListener {
	private TextView accountId;
	private ImageButton up, close, complete;
	private Button reset, add, del;
	private CheckBox cBox;
	private boolean checked = false;
	private Context context;
	private String nameCopy, custCopy, depCopy, supCopy, usrCopy, hsCopy;
	private ArrayList<HashMap<String, Object>> dList;
	private TextView dep, cust, user, sup, hs;
	private SharedPreferences.Editor editor;
	private int flag, checkFalg, flag_db;
	private TextView head;
	private HashMap<String, Object> item;
	private HashMap<String, Object> items;
	private TextView lv_cust, lv_dep, lv_sup, lv_user, lv_ztid;
	private ListView lv_info;
	private Boolean mCheckable = Boolean.valueOf(false);
	private ImageButton rooUser, rootCust, rootDep, rootAccount, rootSup,
			rootHs;
	private SimpleAdapter sAdapter;
	private SharedPreferences sp;
	private Spinner spAccpount;
	private String spinnerText;
	private TableRow tvNull;
	private TextView tvDep, tvSup, tvCust, tvUser, tvAccount, tvHs;
	private JSONArray ids, names, levels, querrys, adds, amts, csts, dels,
			outs, prs, prgs, prts, qtys, sets, ups, querryDBs, querrydeps,
			querrycusts, querrysups, querryusers;
	private Integer index;
	public String session;
	private String uSERZT, uSERNAME, uSERPWD, uSERACCOUNT, uSERDEP, uSERCUST,
			uSERERPUSER, image, imageER, deviceID;
	private String resultStr = ""; // 服务端返回结果集
	ArrayList<String> queryZTs_get = new ArrayList<String>();
	Handler handler = new Handler(new Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					try {
						JSONObject jsonObject = new JSONObject(resultStr);
						Log.e("LiNing", "数据是===" + jsonObject);
						Log.e("LiNing", "数据是===" + resultStr.toString());

					} catch (JSONException e) {
						e.printStackTrace();
					}
					break;

				default:
					break;
			}
			return false;
		}
	});
	/** 记录选中item的下标 */
	private List<Integer> checkedIndexList;
	/** 保存每个item中的checkbox */
	private List<CheckBox> checkBoxList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_post_info);
		BackApplication.getInstance().addActivity(PostInfoActivity.this);
		this.context = PostInfoActivity.this;
//		PushAgent.getInstance(context).onAppStart();
		sp = getSharedPreferences("ydbg", 0);
		session = sp.getString("SESSION", "");
		initview();
		this.dList = new ArrayList();
		this.sAdapter = new SimpleAdapter(this.context, this.dList,
				R.layout.querry_list_head_item, new String[] { "账套编号", "部门",
				"供应商", "客户", "用户", "核算单位" }, new int[] {
				R.id.new_querry_item_dbid, R.id.new_querry_item_dep,
				R.id.new_querry_item_sup, R.id.new_querry_item_cust,
				R.id.new_querry_item_user, R.id.new_querry_item_hs }) {
			@Override
			public View getView(final int position, View convertView,
								ViewGroup parent) {
				// 获取相应的view中的checkbox对象
				if (convertView == null)
					convertView = View.inflate(PostInfoActivity.this,
							R.layout.querry_list_head_item, null);
				final CheckBox checkBox = (CheckBox) convertView
						.findViewById(R.id.listDeleteCheckBox);
				checkBoxList.add(checkBox);
				checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
												 boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							checkFalg = 1;
							checkedIndexList.add(position);

						} else {
							checkFalg = 0;
							checkedIndexList.remove((Integer) position);
						}
						if (0 < checkedIndexList.size()
								&& checkedIndexList.size() <= 1) {
							for (int i = 0; i < checkedIndexList.size(); i++) {
								index = checkedIndexList.get(i);
								PostInfoActivity.this.items = ((HashMap) PostInfoActivity.this.dList
										.get(index));
								Log.e("LiNing", "11111111"
										+ PostInfoActivity.this.items);
								Boolean localBoolean = (Boolean) PostInfoActivity.this.items
										.get("checkbox");
								Log.e("LiNing", "isChecked====" + localBoolean);
								PostInfoActivity.this.nameCopy = ((String) PostInfoActivity.this.items
										.get("账套编号"));
								Log.e("LiNing", "nameCopy===="
										+ PostInfoActivity.this.nameCopy);
								PostInfoActivity.this.depCopy = ((String) PostInfoActivity.this.items
										.get("部门"));
								PostInfoActivity.this.supCopy = ((String) PostInfoActivity.this.items
										.get("供应商"));
								PostInfoActivity.this.custCopy = ((String) PostInfoActivity.this.items
										.get("客户"));
								PostInfoActivity.this.usrCopy = ((String) PostInfoActivity.this.items
										.get("用户"));
								PostInfoActivity.this.hsCopy = ((String) PostInfoActivity.this.items
										.get("核算单位"));
							}
							Log.e("LiNing", "index-----111" + index);
						} else {
							// Toast.makeText(context, "修改时请选择单条数据", 1).show();
						}
					}
				});
				return super.getView(position, convertView, parent);
			}
		};
		this.lv_info.setAdapter(this.sAdapter);
		lv_info.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				for (int i = 0; i < checkBoxList.size(); i++) {
					checkBoxList.get(i).setVisibility(View.VISIBLE);
				}
			}
		});
		// this.lv_info.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> paramAnonymousAdapterView,
		// View paramAnonymousView, int paramAnonymousInt,
		// long paramAnonymousLong) {
		// PostInfoActivity.this.items = ((HashMap) PostInfoActivity.this.dList
		// .get(paramAnonymousInt));
		// Log.e("LiNing", "11111111" + PostInfoActivity.this.items);
		// Boolean localBoolean = (Boolean) PostInfoActivity.this.items
		// .get("checkbox");
		// Log.e("LiNing", "isChecked====" + localBoolean);
		// PostInfoActivity.this.nameCopy = ((String)
		// PostInfoActivity.this.items
		// .get("账套编号"));
		// Log.e("LiNing", "nameCopy====" + PostInfoActivity.this.nameCopy);
		// PostInfoActivity.this.depCopy = ((String) PostInfoActivity.this.items
		// .get("部门"));
		// PostInfoActivity.this.supCopy = ((String) PostInfoActivity.this.items
		// .get("供应商"));
		// PostInfoActivity.this.custCopy = ((String)
		// PostInfoActivity.this.items
		// .get("客户"));
		// PostInfoActivity.this.usrCopy = ((String) PostInfoActivity.this.items
		// .get("用户"));
		// HashMap localHashMap = PostInfoActivity.this.items;
		// if (!localBoolean) {
		// // localHashMap.put("checkbox", false);
		// PostInfoActivity.this.checkFalg = 1;
		// PostInfoActivity.this.lv_info
		// .setSelector(R.color.color_red);
		// Toast.makeText(
		// PostInfoActivity.this.getApplicationContext(),
		// PostInfoActivity.this.nameCopy + " 被选中！", 0).show();
		// } else {
		// PostInfoActivity.this.checkFalg = 0;
		// PostInfoActivity.this.lv_info
		// .setSelector(R.color.color_white);
		// Toast.makeText(
		// PostInfoActivity.this.getApplicationContext(),
		// PostInfoActivity.this.nameCopy + " 被取消！", 0).show();
		// }
		// PostInfoActivity.this.sAdapter.notifyDataSetChanged();
		//
		// }
		// });
	}

	private void initview() {
		this.head = ((TextView) findViewById(R.id.all_head));
		this.head.setText("查询授权");
		this.tvNull = ((TableRow) findViewById(R.id.new_querry_header_item));
		// 功能键操作
		add = ((Button) findViewById(R.id.ibbtn_querry_add));
		this.reset = ((Button) findViewById(R.id.ibbtn_querry_reset));
		this.del = ((Button) findViewById(R.id.ibbtn_querry_del));
		this.complete = ((ImageButton) findViewById(R.id.btn_querry_next));
		this.up = ((ImageButton) findViewById(R.id.btn_querry_last));
		this.close = ((ImageButton) findViewById(R.id.btn_querry_exit));
		// 信息获取（跳转）
		this.rootAccount = ((ImageButton) findViewById(R.id.ib_account));
		this.rootDep = ((ImageButton) findViewById(R.id.ib_dep));
		this.rootSup = ((ImageButton) findViewById(R.id.ib_sup));
		this.rootCust = ((ImageButton) findViewById(R.id.ib_cust));
		this.rooUser = ((ImageButton) findViewById(R.id.ib_user));
		this.rootHs = ((ImageButton) findViewById(R.id.ib_hs));
		// listview信息
		this.lv_ztid = ((TextView) findViewById(R.id.querry_item_dbid));
		this.lv_dep = ((TextView) findViewById(R.id.querry_item_dep));
		this.lv_sup = ((TextView) findViewById(R.id.querry_item_sup));
		this.lv_cust = ((TextView) findViewById(R.id.querry_item_cust));
		this.lv_user = ((TextView) findViewById(R.id.querry_item_user));
		// listview表头
		this.lv_info = ((ListView) findViewById(R.id.lv_querry_header));
		this.accountId = ((TextView) findViewById(R.id.querry_dbid));
		this.dep = ((TextView) findViewById(R.id.querry_dep));
		this.sup = ((TextView) findViewById(R.id.querry_sup));
		this.cust = ((TextView) findViewById(R.id.querry_cust));
		this.user = ((TextView) findViewById(R.id.querry_user));
		// 回调信息
		tvAccount = (TextView) findViewById(R.id.et_querry_zt);
		tvDep = (TextView) findViewById(R.id.et_querry_dep);
		// tvDep.setOnClickListener(new OnClickListener() {
		// Boolean flag = true;
		//
		// @Override
		// public void onClick(View v) {
		// if (flag) {
		// flag = false;
		// tvDep.setEllipsize(null); // 展开
		// tvDep.setSingleLine(false);
		// } else {
		// flag = true;
		// tvDep.setEllipsize(TextUtils.TruncateAt.END); // 收缩
		// tvDep.setSingleLine(true);
		// }
		//
		// }
		// });
		tvSup = (TextView) findViewById(R.id.et_querry_sup);
		tvCust = (TextView) findViewById(R.id.et_querry_cust);
		tvUser = (TextView) findViewById(R.id.et_querry_user);
		tvHs = (TextView) findViewById(R.id.et_querry_hs);
		this.rootAccount.setOnClickListener(this);
		this.rootDep.setOnClickListener(this);
		this.rootSup.setOnClickListener(this);
		this.rootCust.setOnClickListener(this);
		this.rooUser.setOnClickListener(this);
		this.rootHs.setOnClickListener(this);
		add.setOnClickListener(this);
		this.reset.setOnClickListener(this);
		this.del.setOnClickListener(this);
		this.complete.setOnClickListener(this);
		this.close.setOnClickListener(this);
		this.up.setOnClickListener(this);
		checkedIndexList = new ArrayList<Integer>();
		checkBoxList = new ArrayList<CheckBox>();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

			case R.id.ib_account:
				// if (flag == 9) {
				// Toast.makeText(context, "账套不能被修改", 1).show();
				// } else {
				//
				// }
				flag_db = 1;
				Intent intentAccount = new Intent(PostInfoActivity.this,
						GetInfoActivity.class);
				intentAccount.putExtra("flag", "1");
				startActivityForResult(intentAccount, 5);
				break;
			case R.id.ib_sup:
				if (flag_db == 1) {// 点击账套标识
					// if (tvAccount.getText().toString().equals("")) {// 点击账套标识
					Intent intent = new Intent(PostInfoActivity.this,
							SupInfoActivity.class);
					intent.putExtra("flag", "5");
					intent.putExtra("get_id", tvAccount.getText().toString());
					startActivityForResult(intent, 3);
				} else {
					Toast.makeText(this.context, "请先选择账套", Toast.LENGTH_LONG).show();
				}
				break;
			case R.id.ib_hs:
				if (flag_db == 1) {// 点击账套标识
					Intent intent = new Intent(PostInfoActivity.this,
							SupInfoActivity.class);
					intent.putExtra("flag", "6");
					intent.putExtra("get_id", tvAccount.getText().toString());
					startActivityForResult(intent, 6);
				} else {
					Toast.makeText(this.context, "请先选择账套", Toast.LENGTH_LONG).show();
				}
				break;
			case R.id.ib_dep:
				if (flag_db == 1) {// 点击账套标识
					Intent intent1 = new Intent(PostInfoActivity.this,
							SupInfoActivity.class);
					intent1.putExtra("flag", "2");
					intent1.putExtra("get_id", tvAccount.getText().toString());
					startActivityForResult(intent1, 4);
				} else {
					Toast.makeText(this.context, "请先选择账套", Toast.LENGTH_LONG).show();
				}
				break;
			case R.id.ib_user:
				if (flag_db == 1) {// 点击账套标识
					Intent intentuser = new Intent(PostInfoActivity.this,
							SupInfoActivity.class);
					intentuser.putExtra("flag", "4");
					intentuser.putExtra("get_id", tvAccount.getText().toString());
					startActivityForResult(intentuser, 1);
				} else {
					Toast.makeText(this.context, "请先选择账套", Toast.LENGTH_LONG).show();
				}
				break;
			case R.id.ib_cust:
				if (flag_db == 1) {// 点击账套标识
					Intent intentcust = new Intent(PostInfoActivity.this,
							SupInfoActivity.class);
					intentcust.putExtra("flag", "3");
					intentcust.putExtra("get_id", tvAccount.getText().toString());
					startActivityForResult(intentcust, 2);
				} else {
					Toast.makeText(this.context, "请先选择账套", Toast.LENGTH_LONG).show();
				}
				break;
			case R.id.ibbtn_querry_add:
				// 后改
				sAdapter.notifyDataSetChanged();
				if (dList.size() > 0 && dList != null) {
					for (int i = 0; i < dList.size(); i++) {
						queryZTs_get.add(dList.get(i).get("账套编号").toString());
					}
				}
				if (flag == 9) {// 修改标识
					if (checkFalg == 1) {// 选中标识
						// if(flag_db==1){//点击账套标识
						this.item = new HashMap<String, Object>();
						this.item.put("checkbox", this.mCheckable);
						this.item.put("账套编号", tvAccount.getText().toString());
						this.item.put("部门", this.tvDep.getText().toString());
						this.item.put("供应商", this.tvSup.getText().toString());
						this.item.put("客户", this.tvCust.getText().toString());
						this.item.put("用户", this.tvUser.getText().toString());
						this.item.put("核算单位", this.tvHs.getText().toString());
						Log.e("LiNing", "111111" + this.item);
						if (!items.get("账套编号").equals(
								tvAccount.getText().toString())) {
							if (!queryZTs_get.contains(tvAccount.getText()
									.toString())) {
								this.dList.add(0, this.item);
								sAdapter.notifyDataSetChanged();
								tvAccount.setText(null);
								tvDep.setText(null);
								tvSup.setText(null);
								tvCust.setText(null);
								tvUser.setText(null);
								tvHs.setText(null);
								checkFalg = 0;
								for (int i = 0; i < checkBoxList.size(); i++) {
									// 将已选的设置成未选状态
									checkBoxList.get(i).setChecked(false);
								}
							} else {
								Toast.makeText(this.context, "账套已存在", Toast.LENGTH_LONG).show();
							}
						} else {

							this.dList.remove(this.items);
							this.dList.add(0, this.item);
							sAdapter.notifyDataSetChanged();
							tvAccount.setText(null);
							tvDep.setText(null);
							tvSup.setText(null);
							tvCust.setText(null);
							tvUser.setText(null);
							tvHs.setText(null);
							checkFalg = 0;
							for (int i = 0; i < checkBoxList.size(); i++) {
								// 将已选的设置成未选状态
								checkBoxList.get(i).setChecked(false);
							}
						}

					}
				}

				this.sAdapter.notifyDataSetChanged();
				if (tvAccount.getText().toString().equals("")) {
					Toast.makeText(context, "账套不能为空", Toast.LENGTH_LONG).show();
				} else {

					tvNull.setVisibility(View.GONE);
					lv_info.setSelector(R.color.white);

					if (this.item == null) {
						this.item = new HashMap<String, Object>();
						this.item.put("checkbox", this.mCheckable);
						this.item.put("账套编号", tvAccount.getText().toString());
						this.item.put("部门", this.tvDep.getText().toString());
						this.item.put("供应商", this.tvSup.getText().toString());
						this.item.put("客户", this.tvCust.getText().toString());
						this.item.put("用户", this.tvUser.getText().toString());
						this.item.put("核算单位", this.tvHs.getText().toString());
						this.dList.add(this.item);
						String str2 = new Gson().toJson(this.dList);
						this.editor = this.sp.edit();
						this.editor.putString("QuerryRoot", str2);
						this.editor.commit();
						sAdapter.notifyDataSetChanged();
						tvAccount.setText(null);
						tvDep.setText(null);
						tvSup.setText(null);
						tvCust.setText(null);
						tvUser.setText(null);
						tvHs.setText(null);
					} else {

						if (queryZTs_get.contains(tvAccount.getText().toString())) {

							Toast.makeText(this.context, "账套已存在", Toast.LENGTH_LONG).show();
						} else {
							// if
							// (!tvAccount.getText().toString().equals("DB_BJ15")) {
							// Toast.makeText(PostInfoActivity.this,
							// "此账套无数据，不能添加...",
							// Toast.LENGTH_SHORT).show();
							// } else {
							this.item = new HashMap<String, Object>();
							this.item.put("checkbox", this.mCheckable);
							this.item.put("账套编号", tvAccount.getText().toString());
							this.item.put("部门", this.tvDep.getText().toString());
							this.item.put("供应商", this.tvSup.getText().toString());
							this.item.put("客户", this.tvCust.getText().toString());
							this.item.put("用户", this.tvUser.getText().toString());
							this.item.put("核算单位", this.tvHs.getText().toString());
							this.dList.add(this.item);
							String str1 = new Gson().toJson(this.dList);
							this.editor = this.sp.edit();
							this.editor.putString("QuerryRoot", str1);
							this.editor.commit();
							sAdapter.notifyDataSetChanged();
							tvAccount.setText(null);
							tvDep.setText(null);
							tvSup.setText(null);
							tvCust.setText(null);
							tvUser.setText(null);
							tvHs.setText(null);
						}
					}

				}
				break;
			case R.id.ibbtn_querry_reset:
				if (lv_info.getCount() > 0) {
					flag = 9;
					// if (checkFalg == 1) {
					if (0 < checkedIndexList.size() && checkedIndexList.size() <= 1) {
						this.tvAccount.setText(this.nameCopy);
						this.tvDep.setText(this.depCopy);
						this.tvSup.setText(this.supCopy);
						this.tvCust.setText(this.custCopy);
						this.tvUser.setText(this.usrCopy);
						this.tvHs.setText(this.hsCopy);
						if (nameCopy.equals("DB_BJ14")) {
							tvDep.setText(null);
							tvSup.setText(null);
							tvCust.setText(null);
							tvUser.setText(null);
							tvHs.setText(null);
						}
					} else {
						Toast.makeText(this.context, "请选择一条数据", Toast.LENGTH_LONG).show();
						// checkedIndexList.clear();
						// sAdapter.notifyDataSetChanged();
					}
					// } else {
					//
					// Toast.makeText(this.context, "请选择要操作的数据", 1).show();
					// }
				} else {
					Toast.makeText(this.context, "无数据，请添加", Toast.LENGTH_LONG).show();
				}
				this.sAdapter.notifyDataSetChanged();
				break;
			case R.id.ibbtn_querry_del:
				if (0 < checkedIndexList.size() && checkedIndexList != null) {
					new AlertDialog.Builder(this)
							.setTitle("是否删除数据？")
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface paramAnonymousDialogInterface,
												int paramAnonymousInt) {
											delMore();

										}
									}).setNegativeButton("取消", null).show();
				} else {
					Toast.makeText(this.context, "请选择要操作的数据", Toast.LENGTH_LONG).show();
				}

				break;
			case R.id.btn_querry_last:
				finish();
				// startActivity(new Intent(this.context,
				// NewAccountActivity.class));
				break;
			case R.id.btn_querry_next:
				if (dList != null && dList.size() > 0) {
					Log.e("LiNing", ",,,,,,," + dList.toString());
					String stritem = new Gson().toJson(this.dList);
					sp.edit().putString("NewQuery", stritem).commit();
					Log.e("LiNing", "提交的数据是" + sp.getString("NewQuery", ""));

					startActivity(new Intent(context, NewModActivity.class));
				} else {
					Toast.makeText(context, "请选择查询赋权", Toast.LENGTH_SHORT).show();

				}
				break;
			case R.id.btn_querry_exit:
				startActivity(new Intent(this.context, SysActivity.class));
				finish();
				break;

			default:
				break;
		}

	}

	private void delMore() {
		// 先将checkedIndexList中的元素从大到小排列,否则可能会出现错位删除或下标溢出的错误
		checkedIndexList = sortCheckedIndexList(checkedIndexList);
		for (int i = 0; i < checkedIndexList.size(); i++) {
			// 需要强转为int,才会删除对应下标的数据,否则默认删除与括号中对象相同的数据
			dList.remove((int) checkedIndexList.get(i));
			checkBoxList.remove(checkedIndexList.get(i));
		}
		for (int i = 0; i < checkBoxList.size(); i++) {
			// 将已选的设置成未选状态
			checkBoxList.get(i).setChecked(false);
			// 将checkbox设置为不可见
//			checkBoxList.get(i).setVisibility(View.INVISIBLE);
		}
		// 更新数据源
		sAdapter.notifyDataSetChanged();
		// 清空checkedIndexList,避免影响下一次删除
		checkedIndexList.clear();
	}

	private List<Integer> sortCheckedIndexList(List<Integer> list) {
		int[] ass = new int[list.size()];// 辅助数组
		for (int i = 0; i < list.size(); i++) {
			ass[i] = list.get(i);
		}
		Arrays.sort(ass);
		list.clear();
		for (int i = ass.length - 1; i >= 0; i--) {
			list.add(ass[i]);
		}
		return list;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case 1:
				if (resultCode == 1) {
					String str1 = data.getStringExtra("data_return");
					// this.tvUser.setText(str1);
					String str1id = data.getStringExtra("data_return_ids");
					this.tvUser.setText(str1id);
					Log.e("LiNing", "提交的id====" + str1id);
					sp.edit().putString("USERIDquerry", str1id).commit();
				}
				break;
			case 2:
				if (resultCode == 1) {
					String str2 = data.getStringExtra("data_return");
					// this.tvCust.setText(str2);
					String str2id = data.getStringExtra("data_return_ids");
					this.tvCust.setText(str2id);
					Log.e("LiNing", "提交的id====" + str2id);
					sp.edit().putString("CUSTIDquerry", str2id).commit();
				}
				break;
			case 3:
				if (resultCode == 1) {
					String str3 = data.getStringExtra("data_return");
					// this.tvSup.setText(str3);
					String str3id = data.getStringExtra("data_return_ids");
					this.tvSup.setText(str3id);
					Log.e("LiNing", "提交的id====" + str3id);
					sp.edit().putString("SUPIDquerry", str3id).commit();
				}
				break;
			case 6:
				if (resultCode == 1) {
					String str6 = data.getStringExtra("data_return");
					// this.tvSup.setText(str3);
					String str6id = data.getStringExtra("data_return_ids");
					this.tvHs.setText(str6id);
					Log.e("LiNing", "提交的id====" + str6id);
					sp.edit().putString("HSIDquerry", str6id).commit();
				}
				break;
			case 4:
				if (resultCode == 1) {
					String str4 = data.getStringExtra("data_return");
					// this.tvDep.setText(str4);
					String str4id = data.getStringExtra("data_return_ids");
					this.tvDep.setText(str4id);
					Log.e("LiNing", "提交的id====" + str4id);
					sp.edit().putString("DEPIDquerry", str4id).commit();
				}
				break;
			case 5:
				if (resultCode == 1) {
					String str4 = data.getStringExtra("data_return");
					this.tvAccount.setText(str4);
					sp.edit().putString("ACCOUNTIDquerry", str4).commit();
					Log.e("LiNing", "提交的id====" + str4);
				}
				break;

			default:
				break;
		}
	}

	public void allback(View v) {
		finish();
	}
}
