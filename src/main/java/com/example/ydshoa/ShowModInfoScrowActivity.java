package com.example.ydshoa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.LeftOrRight.MyHScrollView;
import com.example.bean.CusterInfoDB;
import com.example.bean.CusterInfoDB.User_Mod;
import com.example.bean.OnItemClickListener;
import com.example.bean.PriceLoadInfo;
import com.example.bean.URLS;
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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShowModInfoScrowActivity extends Activity implements
		OnClickListener {

	private LinearLayout half_info, three_info;
	private Button addok;
	private TextView head;
	private ListView modList;
	private Context context;
	private SharedPreferences sp;
	private String session;
	private UserModAdapter adapter;
	//	String urlGet = "http://192.168.2.178:8080/InfManagePlatform/UsergetUsers.action";
	String urlGet = URLS.all_user_url;
	private List<User_Mod> userMod;
	private int result;
	private ImageButton add, del, reset;
	private boolean b_addinfo, b_queryInfo, b_setInfo, b_delInfo, b_prtInfo,
			b_outInfo, b_cstInfo, b_gpInfo, b_gprInfo, b_qtyInfo, b_upInfo,
			b_amtInfo;
	private List<CheckBox> checkBoxList;
	private List<Integer> checkedIndexList;
	private List<String> idList;

	private TableLayout tvText;
	private int flag, checkflag;
	private Map<Integer, Boolean> isSelected;
	RelativeLayout mHead;
	private String mod_ID, mod_Name,idsUser;
	private boolean mod_Add, mod_Query, mod_Alter, mod_Del, mod_Amt, mod_Prt,
			mod_Out, mod_Cst, mod_GP, mod_GPR, mod_Up, mod_Qty;

	// ibbtn_mod_add
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_show_info);
		context = ShowModInfoScrowActivity.this;
//		PushAgent.getInstance(context).onAppStart();
		sp = getSharedPreferences("ydbg", 0);
		session = sp.getString("SESSION", "");

		checkedIndexList = new ArrayList<Integer>();
		idList = new ArrayList<String>();
		checkBoxList = new ArrayList<CheckBox>();
		initView();
	}

	private void initView() {
		this.mHead = ((RelativeLayout) findViewById(R.id.head_scrow));
		this.mHead.setFocusable(true);
		this.mHead.setClickable(true);
		this.mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
		half_info = (LinearLayout) findViewById(R.id.ll_show_half);
		addok = (Button) findViewById(R.id.btn_modadd_new);
		addok.setOnClickListener(this);
		three_info = (LinearLayout) findViewById(R.id.ll_zsg);
		head = (TextView) findViewById(R.id.all_head);
		head.setText("模块权限");
		modList = (ListView) findViewById(R.id.lv_showmod_header);
		modList.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
		tvText = ((TableLayout) findViewById(R.id.stock_list_item_table_layout));
		add = (ImageButton) findViewById(R.id.ibbtn_mod_add);
		del = (ImageButton) findViewById(R.id.ibbtn_mod_del);
		// reset = (ImageButton) findViewById(R.id.ibbtn_mod_reset);
		add.setOnClickListener(this);
		del.setOnClickListener(this);
		// reset.setOnClickListener(this);
		idsUser = getIntent().getStringExtra("userId");
		String info_flag = getIntent().getStringExtra("MOD_FLAG");
		String stringExtra = getIntent().getStringExtra("INFO_int");
		result = Integer.valueOf(stringExtra);
		Log.e("LiNing", "result----" + result);
		Log.e("LiNing", "info_flag----" + info_flag);
		if (info_flag.equals("modset")) {
			three_info.setVisibility(View.VISIBLE);
			getinfos();
		} else {
			yc = 1;
			findViewById(R.id.newmod_scrow).setVisibility(View.GONE);
			three_info.setVisibility(View.GONE);
			getinfos();
		}
		// getinfos();
		// modList.setOnItemClickListener(new AdapterView.OnItemClickListener()
		// {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// ViewCache tag = (ViewCache) view.getTag();
		// tag.checkBox.toggle();
		// boolean checked = tag.checkBox.isChecked();
		// Log.e("LiNing", "-----111" + checked);
		// isSelected.put(position, checked);
		// if (checked == true) {
		// checkedIndexList.add(position);
		// Log.e("LiNing", "-----111" + checkedIndexList);
		// // String mod_ID = userMod.get(position).getMod_ID();
		// // idList.add(mod_ID);
		// // Log.e("LiNing", "--------" + idList);
		// checked = false;
		// } else {
		// checked = true;
		// checkedIndexList.remove((Integer) position);
		// Log.e("LiNing", "-----222" + checkedIndexList);
		// }
		//
		// }
		// });
	}

	class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {
		ListViewAndHeadViewTouchLinstener() {
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			((HorizontalScrollView) ShowModInfoScrowActivity.this.mHead
					.findViewById(R.id.horizontalScrollView1))
					.onTouchEvent(event);
			return false;
		}

	}

	// 获取所有数据
	private void getinfos() {
		// tvText.setVisibility(View.GONE);
		OkHttpClient client = new OkHttpClient();
		FormBody body = new FormBody.Builder()
				.add("user_Id",idsUser)
				.build();
		client.newCall(
				new Request.Builder().addHeader("cookie", session).url(urlGet)
						.post(body).build()).enqueue(new Callback() {

			@Override
			public void onResponse(Call call, Response response)
					throws IOException {
				String str = response.body().string();
				Log.e("LiNing", "--------" + str);
				// 解析包含date的数据必须添加此代码(InputStream型)
				Gson gson = new GsonBuilder().setDateFormat(
						"yyyy-MM-dd HH:mm:ss").create();
				final CusterInfoDB cInfoDB = gson.fromJson(str,
						CusterInfoDB.class);
				if (cInfoDB != null) {
					ShowModInfoScrowActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							userMod = cInfoDB.getUsers().get(0)
									.getUser_Mod();
							Log.e("LiNing",
									"userMod--------" + userMod.toString());
							adapter = new UserModAdapter(context, userMod,
									R.layout.fuction_item_scrow);
							modList.setAdapter(adapter);
							adapter.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void showResutlt(User_Mod user_Mod, int paramInt) {
									userMod.remove(paramInt);
									userMod.add(paramInt, user_Mod);
									adapter.notifyDataSetChanged();
									Log.e("LiNing",
											"修改了第：" + paramInt + "----result:"
													+ userMod.toString());
									Log.e("LiNing",
											"22222222222 " + userMod.toString());
									// saveInfo();
								}



							});
						}
					});

				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {

			}
		});
	}

	// 保存数据
	private void saveInfo() {
		if (modList.getCount() > 0) {

			ArrayList<String> list_id = new ArrayList<String>();
			ArrayList<String> listNAMEs = new ArrayList<String>();
			ArrayList<String> listModDBS = new ArrayList<String>();
			ArrayList<String> listQUERYs = new ArrayList<String>();
			ArrayList<String> listADDs = new ArrayList<String>();
			ArrayList<String> listAMTs = new ArrayList<String>();
			ArrayList<String> listCSTs = new ArrayList<String>();
			ArrayList<String> listDELs = new ArrayList<String>();
			ArrayList<String> listPRs = new ArrayList<String>();
			ArrayList<String> listPRGs = new ArrayList<String>();
			ArrayList<String> listPRTs = new ArrayList<String>();
			ArrayList<String> listQTYs = new ArrayList<String>();
			ArrayList<String> listSETs = new ArrayList<String>();
			ArrayList<String> listUPs = new ArrayList<String>();
			ArrayList<String> listOUTs = new ArrayList<String>();
			for (int i = 0; i < userMod.size(); i++) {
				String mod_DB = userMod.get(i).getDb_ID();
				listModDBS.add(mod_DB);
				String mod_ID = userMod.get(i).getMod_ID();
				list_id.add(mod_ID);
				String mod_name = userMod.get(i).getMod_Name();
				listNAMEs.add(mod_name);
				boolean mod_Add = userMod.get(i).isMod_Add();
				String s_add = String.valueOf(mod_Add);
				Log.e("LiNing", "得到数据-----list" + s_add);
				if (mod_Add == true) {
					s_add = "T";
				} else if (mod_Add == false) {
					s_add = "F";
				}
				listADDs.add(s_add);
				boolean mod_querry = userMod.get(i).isMod_Query();
				String s_query = String.valueOf(mod_querry);
				if (mod_querry == true) {
					s_query = "T";
				} else if (mod_querry == false) {
					s_query = "F";
				}
				listQUERYs.add(s_query);
				boolean mod_amt = userMod.get(i).isMod_Amt();
				String s_amt = String.valueOf(mod_amt);
				if (mod_amt == true) {
					s_amt = "T";
				} else if (mod_amt == false) {
					s_amt = "F";
				}
				listAMTs.add(s_amt);
				boolean mod_cst = userMod.get(i).isMod_Cst();
				String s_cst = String.valueOf(mod_cst);
				if (mod_cst == true) {
					s_cst = "T";
				} else if (mod_cst == false) {
					s_cst = "F";
				}
				listCSTs.add(s_cst);
				boolean mod_del = userMod.get(i).isMod_Del();
				String s_del = String.valueOf(mod_del);
				if (mod_del == true) {
					s_del = "T";
				} else if (mod_del == false) {
					s_del = "F";
				}
				listDELs.add(s_del);
				boolean mod_gp = userMod.get(i).isMod_GP();
				String s_gp = String.valueOf(mod_gp);
				if (mod_gp == true) {
					s_gp = "T";
				} else if (mod_gp == false) {
					s_gp = "F";
				}
				listPRs.add(s_gp);
				boolean mod_gpr = userMod.get(i).isMod_GPR();
				String s_gpr = String.valueOf(mod_gpr);
				if (mod_gpr == true) {
					s_gpr = "T";
				} else if (mod_gpr == false) {
					s_gpr = "F";
				}
				listPRGs.add(s_gpr);
				boolean mod_prt = userMod.get(i).isMod_Prt();
				String s_prt = String.valueOf(mod_prt);
				if (mod_prt == true) {
					s_prt = "T";
				} else if (mod_prt == false) {
					s_prt = "F";
				}
				listPRTs.add(s_prt);
				boolean mod_qty = userMod.get(i).isMod_Qty();
				String s_qty = String.valueOf(mod_qty);
				if (mod_qty == true) {
					s_qty = "T";
				} else if (mod_qty == false) {
					s_qty = "F";
				}
				listQTYs.add(s_qty);
				boolean mod_set = userMod.get(i).isMod_Alter();
				String s_set = String.valueOf(mod_set);
				if (mod_set == true) {
					s_set = "T";
				} else if (mod_set == false) {
					s_set = "F";
				}
				listSETs.add(s_set);
				boolean mod_up = userMod.get(i).isMod_Up();
				String s_up = String.valueOf(mod_up);
				if (mod_up == true) {
					s_up = "T";
				} else if (mod_up == false) {
					s_up = "F";
				}
				listUPs.add(s_up);
				boolean mod_out = userMod.get(i).isMod_Out();
				String s_out = String.valueOf(mod_out);
				if (mod_out == true) {
					s_out = "T";
				} else if (mod_out == false) {
					s_out = "F";
				}
				listOUTs.add(s_out);
			}
			String zts_str = "";
			for (String zt : listModDBS) {
				zts_str += zt + ",";
			}
			String dbs = zts_str.substring(0, zts_str.length() - 1);
			String ids_str = "";
			for (String id : list_id) {
				ids_str += id + ",";
			}
			String ids = ids_str.substring(0, ids_str.length() - 1);
			// json_id = net.sf.json.JSONArray.fromObject(ids_str);
			String names_str = "";
			for (String name : listNAMEs) {
				names_str += name + ",";
			}
			String names = names_str.substring(0, names_str.length() - 1);
			String querys_str = "";
			for (String query : listQUERYs) {
				querys_str += query + ",";
			}
			String querys = querys_str.substring(0, querys_str.length() - 1);
			String adds_str = "";
			for (String add : listADDs) {
				adds_str += add + ",";
			}
			String adds = adds_str.substring(0, adds_str.length() - 1);
			String alters_str = "";
			for (String alt : listSETs) {
				alters_str += alt + ",";
			}
			String sets = alters_str.substring(0, alters_str.length() - 1);
			String dels_str = "";
			for (String del : listDELs) {
				dels_str += del + ",";
			}
			String dels = dels_str.substring(0, dels_str.length() - 1);
			String prt_str = "";
			for (String prt : listPRTs) {
				prt_str += prt + ",";
			}
			String prts = prt_str.substring(0, prt_str.length() - 1);
			String out_str = "";
			for (String out : listOUTs) {
				out_str += out + ",";
			}
			String outs = out_str.substring(0, out_str.length() - 1);
			String qty_str = "";
			for (String qty : listQTYs) {
				qty_str += qty + ",";
			}
			String qtys = qty_str.substring(0, qty_str.length() - 1);
			String up_str = "";
			for (String up : listUPs) {
				up_str += up + ",";
			}
			String ups = up_str.substring(0, up_str.length() - 1);
			String amt_str = "";
			for (String amt : listAMTs) {
				amt_str += amt + ",";
			}
			String amts = amt_str.substring(0, amt_str.length() - 1);
			String cst_str = "";
			for (String cst : listCSTs) {
				cst_str += cst + ",";
			}
			String csts = cst_str.substring(0, cst_str.length() - 1);
			String gp_str = "";
			for (String gp : listPRs) {
				gp_str += gp + ",";
			}
			String prs = gp_str.substring(0, gp_str.length() - 1);
			String gpr_str = "";
			for (String gpr : listPRGs) {
				gpr_str += gpr + ",";
			}
			String prgs = gpr_str.substring(0, gpr_str.length() - 1);
			Log.e("LiNing", "得到数据-----list" + ids + "///" + names + "///"
					+ adds);
			// 数据提交前封装（模块）
			Editor edit2 = sp.edit();
			edit2.putString("modinfo_id", ids).commit();
			edit2.putString("modinfo_db", dbs).commit();
			edit2.putString("modinfo_name", names).commit();
			edit2.putString("modinfo_add", adds).commit();
			edit2.putString("modinfo_query", querys).commit();
			edit2.putString("modinfo_amt", amts).commit();
			edit2.putString("modinfo_cst", csts).commit();
			edit2.putString("modinfo_del", dels).commit();
			edit2.putString("modinfo_pr", prs).commit();
			edit2.putString("modinfo_prg", prgs).commit();
			edit2.putString("modinfo_prt", prts).commit();
			edit2.putString("modinfo_qty", qtys).commit();
			edit2.putString("modinfo_set", sets).commit();
			edit2.putString("modinfo_up", ups).commit();
			edit2.putString("modinfo_out", outs).commit();
			Log.e("LiNing", "得到数据88888888" + sp.getString("modinfo_id", ""));
			Log.e("LiNing", "得到数据3333" + sp.getString("modinfo_name", ""));
			Log.e("LiNing", "得到数据3333" + sp.getString("modinfo_db", ""));
			// Log.e("LiNing", "得到数据---大小" + userMod.size());
			finish();
		} else {
			Toast.makeText(context, "无数据", Toast.LENGTH_LONG).show();
			finish();
		}
	}

	public void modInfo(View v) {
		saveInfo();
		finish();
		// if (flag == 1) {
		// finish();
		// } else {
		// finish();
		// }
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			// 显示
			case R.id.ibbtn_mod_add:
				flag = 1;
				half_info.setVisibility(View.VISIBLE);
				initView_new();
				break;
			// 添加
			case R.id.btn_modadd_new:

				// 判断是否存在？
				ztInfo = account.getSelectedItem().toString();
				idInfo = id.getText().toString();
				nameInfo = name.getText().toString();
				levelInfo = level.getText().toString();
				addInfo = add_new.getSelectedItem().toString();
				if (addInfo.equals("T")) {
					addInfo = "true";
					b_addinfo = Boolean.parseBoolean(addInfo);
				} else if (addInfo.equals("F")) {
					b_addinfo = false;
					b_addinfo = Boolean.parseBoolean(addInfo);
				}
				Log.e("LiNing", "---" + addInfo + "----" + b_addinfo);
				queryInfo = query.getSelectedItem().toString();
				if (queryInfo.equals("T")) {
					queryInfo = "true";
					b_queryInfo = Boolean.parseBoolean(queryInfo);
				} else if (queryInfo.equals("F")) {
					queryInfo = "false";
					b_queryInfo = Boolean.parseBoolean(queryInfo);
				}
				setInfo = set.getSelectedItem().toString();
				if (setInfo.equals("T")) {
					setInfo = "true";
					b_setInfo = Boolean.parseBoolean(setInfo);
				} else if (setInfo.equals("F")) {
					setInfo = "false";
					b_setInfo = Boolean.parseBoolean(setInfo);
				}
				delInfo = del_new.getSelectedItem().toString();
				if (delInfo.equals("T")) {
					delInfo = "true";
					b_delInfo = Boolean.parseBoolean(delInfo);
				} else if (delInfo.equals("F")) {
					delInfo = "false";
					b_delInfo = Boolean.parseBoolean(delInfo);
				}
				prtInfo = prt.getSelectedItem().toString();
				if (prtInfo.equals("T")) {
					prtInfo = "true";
					b_prtInfo = Boolean.parseBoolean(prtInfo);
				} else if (prtInfo.equals("F")) {
					prtInfo = "false";
					b_prtInfo = Boolean.parseBoolean(prtInfo);
				}
				outInfo = out.getSelectedItem().toString();
				if (outInfo.equals("T")) {
					outInfo = "true";
					b_outInfo = Boolean.parseBoolean(outInfo);
				} else if (outInfo.equals("F")) {
					outInfo = "false";
					b_outInfo = Boolean.parseBoolean(outInfo);
				}
				cstInfo = cst.getSelectedItem().toString();
				if (cstInfo.equals("T")) {
					cstInfo = "true";
					b_cstInfo = Boolean.parseBoolean(cstInfo);
				} else if (cstInfo.equals("F")) {
					cstInfo = "false";
					b_cstInfo = Boolean.parseBoolean(cstInfo);
				}
				gpInfo = gp.getSelectedItem().toString();
				if (gpInfo.equals("T")) {
					gpInfo = "true";
					b_gpInfo = Boolean.parseBoolean(gpInfo);
				} else if (gpInfo.equals("F")) {
					gpInfo = "false";
					b_gpInfo = Boolean.parseBoolean(gpInfo);
				}
				gprInfo = gpr.getSelectedItem().toString();
				if (gprInfo.equals("T")) {
					gprInfo = "true";
					b_gprInfo = Boolean.parseBoolean(gprInfo);
				} else if (gprInfo.equals("F")) {
					gprInfo = "false";
					b_gprInfo = Boolean.parseBoolean(gprInfo);
				}
				qtyInfo = qty.getSelectedItem().toString();
				if (qtyInfo.equals("T")) {
					qtyInfo = "true";
					b_qtyInfo = Boolean.parseBoolean(qtyInfo);
				} else if (qtyInfo.equals("F")) {
					qtyInfo = "false";
					b_qtyInfo = Boolean.parseBoolean(qtyInfo);
				}
				upInfo = up.getSelectedItem().toString();
				if (upInfo.equals("T")) {
					upInfo = "true";
					b_upInfo = Boolean.parseBoolean(upInfo);
				} else if (upInfo.equals("F")) {
					upInfo = "false";
					b_upInfo = Boolean.parseBoolean(upInfo);
				}
				amtInfo = amt.getSelectedItem().toString();
				if (amtInfo.equals("T")) {
					amtInfo = "true";
					b_amtInfo = Boolean.parseBoolean(amtInfo);
				} else if (amtInfo.equals("F")) {
					amtInfo = "false";
					b_amtInfo = Boolean.parseBoolean(amtInfo);
				}
				User_Mod mod = new User_Mod(ztInfo, idInfo, nameInfo, b_addinfo,
						b_setInfo, b_amtInfo, b_cstInfo, b_delInfo, b_gpInfo,
						b_gprInfo, b_outInfo, b_prtInfo, b_qtyInfo, b_queryInfo,
						b_upInfo);

				if (userMod.size() > 0 && userMod != null) {
					ArrayList<String> modIDs_get = new ArrayList<String>();
					ArrayList<String> modZTs_get = new ArrayList<String>();
					for (int i = 0; i < userMod.size(); i++) {
						modIDs_get.add(userMod.get(i).getMod_ID());
						modZTs_get.add(userMod.get(i).getDb_ID());
						Log.e("LiNing", "LiNing--------" + modIDs_get
								+ "LiNing--------" + modZTs_get);
					}
					if (modZTs_get.contains(ztInfo) && modIDs_get.contains(idInfo)) {

						Toast.makeText(context, "此模块已存在", Toast.LENGTH_LONG).show();
					} else {
						userMod.add(0, mod);
						adapter.notifyDataSetChanged();
						modList.invalidate();
						Log.e("LiNing", "LiNing----NEW----" + modIDs_get
								+ "LiNing----NEW----" + modZTs_get);
					}
				} else {
					userMod.add(0, mod);
					adapter.notifyDataSetChanged();
					modList.invalidate();
				}

				break;
			case R.id.mod_search:
				Intent intent = new Intent(ShowModInfoScrowActivity.this,
						ModListActivity.class);
				startActivityForResult(intent, 1);
				break;
			case R.id.ibbtn_mod_del:
				if (checkedIndexList.size() > 0 && checkedIndexList != null) {
					new AlertDialog.Builder(context)
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
					Toast.makeText(context, "点击item选中数据", Toast.LENGTH_SHORT)
							.show();
				}

				break;
			case R.id.ibbtn_mod_reset:
				if (modList.getCount() > 0) {
					flag = 9;
					if (checkflag == 1) {
						if (checkflag == 1 && 0 < checkedIndexList.size()
								&& checkedIndexList.size() <= 1) {
							id.setText(mod_ID);
							name.setText(mod_Name);
							query.setSelection(0);
							add_new.setSelection(0);
							set.setSelection(0);
							del_new.setSelection(0);
							prt.setSelection(0);
							out.setSelection(0);
							cst.setSelection(0);
							gp.setSelection(0);
							gpr.setSelection(0);
							qty.setSelection(0);
							up.setSelection(0);
							amt.setSelection(0);
						} else {
							Toast.makeText(this.context, "请选择一条数据", Toast.LENGTH_LONG).show();
							checkedIndexList.clear();
							adapter.notifyDataSetChanged();
						}

					} else {

						Toast.makeText(context, "请选择要操作的数据", Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(this.context, "无数据，请添加", Toast.LENGTH_LONG).show();
				}
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
			userMod.remove((int) checkedIndexList.get(i));
			checkBoxList.remove(checkedIndexList.get(i));
		}
		for (int i = 0; i < checkBoxList.size(); i++) {
			// 将已选的设置成未选状态
			checkBoxList.get(i).setChecked(false);
			// 将checkbox设置为不可见
			checkBoxList.get(i).setVisibility(View.VISIBLE);
		}
		// 更新数据源
		adapter.notifyDataSetChanged();
		// 清空checkedIndexList,避免影响下一次删除
		checkedIndexList.clear();
		Log.e("LiNing", "---sch" + checkedIndexList);
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
					String mod_id_date = data.getStringExtra("data_id");
					id.setText(mod_id_date);
					String mod_name_date = data.getStringExtra("data_name");
					name.setText(mod_name_date);
					String mod_level_date = data.getStringExtra("data_level");
					level.setText(mod_level_date);
					// sp.edit().putString("ACCOUNTID", best_db).commit();
					Log.e("LiNing", "提交的id====" + id);
				}
				break;

			default:
				break;
		}
	}

	private Spinner query, add_new, set, del_new, prt, out, cst, gp, gpr, qty,
			up, amt, account;
	private EditText id, name, level;
	private ImageButton ib_mod;
	private String ztInfo, idInfo, nameInfo, levelInfo, addInfo, queryInfo,
			setInfo, delInfo, prtInfo, outInfo, cstInfo, gpInfo, gprInfo,
			qtyInfo, upInfo, amtInfo, str_db;
	private String test_id;
	private int yc;

	private void initView_new() {
		account = (Spinner) findViewById(R.id.newmod_sp_account);
		String QuerryRoot = sp.getString("QuerryRoot", "");
		ArrayList<String> dblist = new ArrayList<String>();
		JSONArray arr;
		try {
			arr = new JSONArray(QuerryRoot);
			int size = arr.length();
			for (int i = 0; i < size; i++) {
				JSONObject jsonObject = arr.getJSONObject(i);
				str_db = jsonObject.get("账套编号").toString();
				dblist.add(str_db);
				Log.e("LiNing", "str_db////////////" + str_db);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e("LiNing", "str_db==========" + str_db);
		ArrayAdapter sadapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, dblist);

		sadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		account.setAdapter(sadapter);
		// 搜索框
		id = (EditText) findViewById(R.id.mod_search_id);
		name = (EditText) findViewById(R.id.mod_search_name);
		level = (EditText) findViewById(R.id.mod_search_all);
		ib_mod = (ImageButton) findViewById(R.id.mod_search);
		ib_mod.setOnClickListener(this);
		query = (Spinner) findViewById(R.id.newmod_sp_query);
		add_new = (Spinner) findViewById(R.id.newmod_sp_add);
		set = (Spinner) findViewById(R.id.newmod_sp_set);
		del_new = (Spinner) findViewById(R.id.newmod_sp_del);
		prt = (Spinner) findViewById(R.id.newmod_sp_prt);
		out = (Spinner) findViewById(R.id.newmod_sp_out);
		cst = (Spinner) findViewById(R.id.newmod_sp_cst);
		gp = (Spinner) findViewById(R.id.newmod_sp_gp);
		gpr = (Spinner) findViewById(R.id.newmod_sp_gpr);
		qty = (Spinner) findViewById(R.id.newmod_sp_qty);
		up = (Spinner) findViewById(R.id.newmod_sp_up);
		amt = (Spinner) findViewById(R.id.newmod_sp_amt);
	}

	public void allback(View v) {
		finish();
	}

	public class UserModAdapter extends BaseAdapter {

		private Context context;
		private List<User_Mod> userMod;
		private int resource;
		private LayoutInflater inflater;
		private ArrayList<HashMap<String, Object>> dList;
		private SharedPreferences sp;
		private OnItemClickListener onItemClickListener;
		private boolean mAllCheckStation[];

		// public static Map<Integer, Boolean> isSelected;

		public UserModAdapter(Context context, List<User_Mod> userMod2,
							  int fuctionItem) {
			this.context = context;
			this.userMod = userMod2;
			this.resource = fuctionItem;
			this.inflater = ((LayoutInflater) context
					.getSystemService(LAYOUT_INFLATER_SERVICE));
			sp = context.getSharedPreferences("ydbg", 0);
			mAllCheckStation = new boolean[userMod.size()];
			init();
		}

		private void init() {
			isSelected = new HashMap<Integer, Boolean>();
			for (int i = 0; i < userMod.size(); i++) {
				isSelected.put(i, false);
			}
		}

		public void itemSelect(int position) {
			mAllCheckStation[position] = !mAllCheckStation[position];
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return userMod.size();
		}

		@Override
		public Object getItem(int position) {

			return userMod.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(final int position, View convertView,
							ViewGroup parent) {
			ViewCache holder = null;
			if (convertView == null) {
				synchronized (ShowModInfoScrowActivity.this) {
					holder = new ViewCache();
					convertView = inflater.inflate(this.resource, null);
					MyHScrollView scrollView1 = (MyHScrollView) convertView
							.findViewById(R.id.horizontalScrollView1);
					holder.checkBox = (CheckBox) convertView
							.findViewById(R.id.mod_showChecx);
					if(yc==1){
						holder.checkBox.setVisibility(View.GONE);
					}
					holder.zt = ((TextView) convertView
							.findViewById(R.id.fuction_root_itemZT));
					holder.id = ((TextView) convertView
							.findViewById(R.id.fuction_root_itemID));
					holder.name = ((TextView) convertView
							.findViewById(R.id.fuction_root_itemname));
					// holder.level = ((TextView) convertView
					// .findViewById(R.id.fuction_root_itemlevel));
					holder.querry = ((TextView) convertView
							.findViewById(R.id.fuction_root_itemquery));
					holder.add = ((TextView) convertView
							.findViewById(R.id.fuction_root_itemadd));
					holder.set = ((TextView) convertView
							.findViewById(R.id.fuction_root_itemset));
					holder.del = ((TextView) convertView
							.findViewById(R.id.fuction_root_itemdel));
					holder.prt = ((TextView) convertView
							.findViewById(R.id.fuction_root_itemprt));
					holder.out = ((TextView) convertView
							.findViewById(R.id.fuction_root_itemqut));
					holder.qty = ((TextView) convertView
							.findViewById(R.id.fuction_root_itemqty));
					holder.up = ((TextView) convertView
							.findViewById(R.id.fuction_root_itemup));
					holder.amt = ((TextView) convertView
							.findViewById(R.id.fuction_root_itemamt));
					holder.cst = ((TextView) convertView
							.findViewById(R.id.fuction_root_itemcst));
					holder.pr = ((TextView) convertView
							.findViewById(R.id.fuction_root_itempr));
					holder.prg = ((TextView) convertView
							.findViewById(R.id.fuction_root_itemprg));
					// this.lHolders.add(holder);

					MyHScrollView headSrcrollView = (MyHScrollView) mHead
							.findViewById(R.id.horizontalScrollView1);
					headSrcrollView
							.AddOnScrollChangedListener(new OnScrollChangedListenerImp(
									scrollView1));
					checkBoxList.add(holder.checkBox);
					convertView.setTag(holder);
				}
			} else {
				holder = (ViewCache) convertView.getTag();
			}
			final User_Mod user_Mod = userMod.get(position);
			// Log.e("LiNing", "---------"+user_Mod.get(position).getMod_ID());
			holder.zt.setText(user_Mod.getDb_ID());
			holder.id.setText(user_Mod.getMod_ID());
			holder.name.setText(user_Mod.getMod_Name());
			// holder.level.setText(user_Mod.getLevel());
			if (user_Mod.isMod_Add() == true) {
				holder.add.setText("T");
			} else {
				holder.add.setText("F");
			}
			if (user_Mod.isMod_Cst() == true) {
				holder.cst.setText("T");
			} else {
				holder.cst.setText("F");
			}
			if (user_Mod.isMod_Amt() == true) {
				holder.amt.setText("T");
			} else {
				holder.amt.setText("F");
			}
			if (user_Mod.isMod_Del() == true) {
				holder.del.setText("T");
			} else {
				holder.del.setText("F");
			}
			if (user_Mod.isMod_Out() == true) {
				holder.out.setText("T");
			} else {
				holder.out.setText("F");
			}
			if (user_Mod.isMod_GP() == true) {
				holder.pr.setText("T");
			} else {
				holder.pr.setText("F");
			}
			if (user_Mod.isMod_GPR() == true) {
				holder.prg.setText("T");
			} else {
				holder.prg.setText("F");
			}
			if (user_Mod.isMod_Prt() == true) {
				holder.prt.setText("T");
			} else {
				holder.prt.setText("F");
			}
			if (user_Mod.isMod_Qty() == true) {
				holder.qty.setText("T");
			} else {
				holder.qty.setText("F");
			}
			if (user_Mod.isMod_Query() == true) {
				holder.querry.setText("T");
			} else {
				holder.querry.setText("F");
			}
			if (user_Mod.isMod_Alter() == true) {
				holder.set.setText("T");
			} else {
				holder.set.setText("F");
			}
			if (user_Mod.isMod_Up() == true) {
				holder.up.setText("T");
			} else {
				holder.up.setText("F");
			}

			holder.add.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					user_Mod.setMod_Add(!user_Mod.isMod_Add());
					onItemClickListener.showResutlt(user_Mod, position);
				}
			});
			holder.cst.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					user_Mod.setMod_Cst(!user_Mod.isMod_Cst());
					onItemClickListener.showResutlt(user_Mod, position);
				}
			});
			holder.amt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					user_Mod.setMod_Amt(!user_Mod.isMod_Amt());
					onItemClickListener.showResutlt(user_Mod, position);
				}
			});
			holder.del.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					user_Mod.setMod_Del(!user_Mod.isMod_Del());
					onItemClickListener.showResutlt(user_Mod, position);
				}
			});
			holder.out.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					user_Mod.setMod_Out(!user_Mod.isMod_Out());
					onItemClickListener.showResutlt(user_Mod, position);
				}
			});
			holder.pr.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					user_Mod.setMod_GP(!user_Mod.isMod_GP());
					onItemClickListener.showResutlt(user_Mod, position);
				}
			});
			holder.prg.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					user_Mod.setMod_GPR(!user_Mod.isMod_GPR());
					onItemClickListener.showResutlt(user_Mod, position);
				}
			});
			holder.prt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					user_Mod.setMod_Prt(!user_Mod.isMod_Prt());
					onItemClickListener.showResutlt(user_Mod, position);
				}
			});
			holder.qty.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					user_Mod.setMod_Qty(!user_Mod.isMod_Qty());
					onItemClickListener.showResutlt(user_Mod, position);
				}
			});
			holder.querry.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					user_Mod.setMod_Query(!user_Mod.isMod_Query());
					onItemClickListener.showResutlt(user_Mod, position);
				}
			});
			holder.set.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					user_Mod.setMod_Alter(!user_Mod.isMod_Alter());
					onItemClickListener.showResutlt(user_Mod, position);
				}
			});
			holder.up.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					user_Mod.setMod_Up(!user_Mod.isMod_Up());
					onItemClickListener.showResutlt(user_Mod, position);
				}
			});

			if (isSelected.get(position) == null) {
				Boolean boolean1 = isSelected.get(position);
				boolean1 = false;
				holder.checkBox.setChecked(boolean1);
			}
			holder.checkBox.setOnCheckedChangeListener(new CheckBoxListener(
					position));
			return convertView;
		}

		public void setOnItemClickListener(OnItemClickListener listener) {
			this.onItemClickListener = listener;
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

		public class ViewCache {
			public TextView zt;
			public TextView id;
			public TextView name;
			public TextView add;
			public TextView amt;
			public TextView cst;
			public TextView del;
			public TextView out;
			public TextView pr;
			public TextView prg;
			public TextView prt;
			public TextView qty;
			public TextView querry;
			public TextView set;
			public TextView up;
			public CheckBox checkBox;

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
				checkflag = 1;
				checkedIndexList.add(positions);
				User_Mod mod = userMod.get(positions);
				String db_ID = mod.getDb_ID();
				mod_ID = mod.getMod_ID();
				mod_Name = mod.getMod_Name();
				mod_Add = mod.isMod_Add();
				mod_Query = mod.isMod_Query();
				mod_Alter = mod.isMod_Alter();
				mod_Del = mod.isMod_Del();
				mod_Amt = mod.isMod_Amt();
				mod_Prt = mod.isMod_Prt();
				mod_Out = mod.isMod_Out();
				mod_Cst = mod.isMod_Cst();
				mod_GP = mod.isMod_GP();
				mod_GPR = mod.isMod_GPR();
				mod_Up = mod.isMod_Up();
				mod_Qty = mod.isMod_Qty();
			} else {
				checkflag = 0;
				checkedIndexList.remove((Integer) positions);
			}
		}

	}
}
