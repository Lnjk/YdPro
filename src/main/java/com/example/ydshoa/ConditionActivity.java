package com.example.ydshoa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.example.LeftOrRight.MyHScrollView;
import com.google.gson.Gson;
//import com.umeng.message.PushAgent;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ConditionActivity extends Activity implements OnClickListener {
	private Context context;
	private SharedPreferences sp;
	private TextView head, c_queryDb, c_queryHs, c_queryDep, c_querySup,
			c_queryCust, c_queryUser, c_chkUser, c_area, c_employee, c_prdNo,
			c_prdIndex, c_prdWh;
	private EditText c_bilNo, c_custNo, c_custName, c_custPhone, c_custAdress,
			c_inputNo, c_custOSNO, c_rem, c_prdMark;
	private ImageButton btnc_queryDb, btnc_queryHs, btnc_queryDep,
			btnc_querySup, btnc_queryCust, btnc_queryUser, btnc_chkUser,
			btnc_area, btnc_employee, btnc_prdNo, btnc_prdIndex, btnc_prdWh;
	private Button add, put, reset, del;
	private String str_id;
	private ListView lv_sale;
	RelativeLayout mHead;
	private SimpleAdapter sAdapter;
	private ArrayList<HashMap<String, Object>> dList;
	private HashMap<String, Object> item;
	private String str_db, str_hs, str_dep, str_dsup, str_cust, str_user,
			str_chkUser, str_area, str_empl, str_prdNo, str_prdIndex,
			str_prdWh, str_bilNo, str_custName, str_custPhone, str_custAdress,
			str_inputNo, str_custOSNO, str_rem, str_prdMark, str_custNo;
	private String id_dep, id_hs, id_prdWh, id_prdIndex, id_prdNo, id_employee,
			id_area, id_queryUser, id_chkUser, id_querySup, id_queryCust;
	/** 记录选中item的下标 */
	private List<Integer> checkedIndexList;
	/** 保存每个item中的checkbox */
	private List<CheckBox> checkBoxList;
	private int checkflag;
	private HashMap<String, Object> item_get;
	ArrayList<String> queryZTs_get = new ArrayList<String>();
	private HashMap<String, Object> del_item;
	private int flag_back;
	String page,dqzt,gjss;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_condition);
		context = ConditionActivity.this;
		//推送
//		PushAgent.getInstance(context).onAppStart();
		sp = getSharedPreferences("ydbg", 0);
		//同比数据========
		page = getIntent().getStringExtra("page");
		dqzt = getIntent().getStringExtra("dqzt_tb");
		gjss = getIntent().getStringExtra("gjss");
		initView();
	}

	private void initView() {
		head = (TextView) findViewById(R.id.all_head);
		head.setText("查询条件");
		c_queryDb = (TextView) findViewById(R.id.et_condition_queryDb);


		c_bilNo = (EditText) findViewById(R.id.et_condition_bileNo);
		c_custNo = (EditText) findViewById(R.id.et_condition_custNo);
		c_custName = (EditText) findViewById(R.id.et_condition_custNme);
		c_custPhone = (EditText) findViewById(R.id.et_condition_custPh);
		c_custAdress = (EditText) findViewById(R.id.et_condition_custAdress);
		c_inputNo = (EditText) findViewById(R.id.et_condition_inputNo);
		c_rem = (EditText) findViewById(R.id.et_condition_rem);
		c_custOSNO = (EditText) findViewById(R.id.et_condition_custOSNO);
		c_prdMark = (EditText) findViewById(R.id.et_condition_prdMark);
		c_queryHs = (TextView) findViewById(R.id.et_condition_queryhs);
		c_queryDep = (TextView) findViewById(R.id.et_condition_queryDep);
		c_querySup = (TextView) findViewById(R.id.et_condition_querySup);
		c_queryCust = (TextView) findViewById(R.id.et_condition_queryCust);
		c_queryUser = (TextView) findViewById(R.id.et_condition_queryUser);
		c_chkUser = (TextView) findViewById(R.id.et_condition_chkUser);
		c_area = (TextView) findViewById(R.id.et_condition_area);
		c_employee = (TextView) findViewById(R.id.et_condition_employee);
		c_prdNo = (TextView) findViewById(R.id.et_condition_prdNo);
		c_prdIndex = (TextView) findViewById(R.id.et_condition_prdIndex);
		c_prdWh = (TextView) findViewById(R.id.et_condition_prdWh);
		if (!sp.getString("CSTOSNO_CDTA", "").equals("")) {
			c_custOSNO.setText(sp.getString("CSTOSNO_CDTA", ""));
		}
		if (!sp.getString("REM_CDTA", "").equals("")) {
			c_rem.setText(sp.getString("REM_CDTA", ""));
		}

		if (!sp.getString("PRD_CDTA", "").equals("")) {
			c_prdMark.setText(sp.getString("PRD_CDTA", ""));
		}

		if (!sp.getString("HS_CDTA", "").equals("")) {
			c_queryHs.setText(sp.getString("HS_CDTA", ""));
		}
		if (!sp.getString("DEP_CDTA", "").equals("")) {
			c_queryDep.setText(sp.getString("DEP_CDTA", ""));
		}

		if (!sp.getString("SUP_CDTA", "").equals("")) {
			c_querySup.setText(sp.getString("SUP_CDTA", ""));
		}

		if (!sp.getString("CUST_CDTA", "").equals("")) {
			c_queryCust.setText(sp.getString("CUST_CDTA", ""));
		}

		if (!sp.getString("USER_CDTA", "").equals("")) {
			c_queryUser.setText(sp.getString("USER_CDTA", ""));
		}

		if (!sp.getString("CHKUSER_CDTA", "").equals("")) {
			c_chkUser.setText(sp.getString("CHKUSER_CDTA", ""));
		}

		if (!sp.getString("AREA_CDTA", "").equals("")) {
			c_area.setText(sp.getString("AREA_CDTA", ""));
		}

		if (!sp.getString("EMPL_CDTA", "").equals("")) {
			c_employee.setText(sp.getString("EMPL_CDTA", ""));
		}

		if (!sp.getString("PRDNO_CDTA", "").equals("")) {
			c_prdNo.setText(sp.getString("PRDNO_CDTA", ""));
		}

		if (!sp.getString("PRDINDEX_CDTA", "").equals("")) {
			c_prdIndex.setText(sp.getString("PRDINDEX_CDTA", ""));
		}

		if (!sp.getString("PRDWH_CDTA", "").equals("")) {
			c_prdWh.setText(sp.getString("PRDWH_CDTA", ""));
		}
//		if (!sp.getString("DB_CDTA", "").equals("")) {
//		}
//		c_queryDb.setText(sp.getString("DB_CDTA", ""));
		if (!sp.getString("BILNO_CDTA", "").equals("")) {
			c_bilNo.setText(sp.getString("BILNO_CDTA", ""));
		}

		if (!sp.getString("CUSTNO_CDTA", "").equals("")) {
			c_custNo.setText(sp.getString("CUSTNO_CDTA", ""));
		}

		if (!sp.getString("CSTNAME_CDTA", "").equals("")) {
			c_custName.setText(sp.getString("CSTNAME_CDTA", ""));
		}

		if (!sp.getString("CSTP_CDTA", "").equals("")) {
			c_custPhone.setText(sp.getString("CSTP_CDTA", ""));
		}

		if (!sp.getString("CSTARD_CDTA", "").equals("")) {
			c_custAdress.setText(sp.getString("CSTARD_CDTA", ""));
		}

		if (!sp.getString("INPUTNO_CDTA", "").equals("")) {
			c_inputNo.setText(sp.getString("INPUTNO_CDTA", ""));
		}

		btnc_queryDb = (ImageButton) findViewById(R.id.ib_queryDb);
		btnc_queryHs = (ImageButton) findViewById(R.id.ib_queryhs);
		btnc_queryDep = (ImageButton) findViewById(R.id.ib_queryDep);
		btnc_querySup = (ImageButton) findViewById(R.id.ib_querySup);
		btnc_queryCust = (ImageButton) findViewById(R.id.ib_queryCust);
		btnc_queryUser = (ImageButton) findViewById(R.id.ib_queryUser);
		btnc_chkUser = (ImageButton) findViewById(R.id.ib_chkUser);
		btnc_area = (ImageButton) findViewById(R.id.ib_area);
		btnc_employee = (ImageButton) findViewById(R.id.ib_employee);
		btnc_prdNo = (ImageButton) findViewById(R.id.ib_prdNo);
		btnc_prdIndex = (ImageButton) findViewById(R.id.ib_prdIndex);
		btnc_prdWh = (ImageButton) findViewById(R.id.ib_prdWh);
		add = (Button) findViewById(R.id.btn_addProject);
		put = (Button) findViewById(R.id.btn_put);
		// reset = (Button) findViewById(R.id.btn_set);
		del = (Button) findViewById(R.id.btn_del);
		add.setOnClickListener(this);
		put.setOnClickListener(this);
		// reset.setOnClickListener(this);
		del.setOnClickListener(this);
		btnc_queryDb.setOnClickListener(this);
		btnc_queryHs.setOnClickListener(this);
		btnc_queryDep.setOnClickListener(this);
		btnc_querySup.setOnClickListener(this);
		btnc_queryCust.setOnClickListener(this);
		btnc_queryUser.setOnClickListener(this);
		btnc_chkUser.setOnClickListener(this);
		btnc_area.setOnClickListener(this);
		btnc_employee.setOnClickListener(this);
		btnc_prdNo.setOnClickListener(this);
		btnc_prdIndex.setOnClickListener(this);
		btnc_prdWh.setOnClickListener(this);
		if(page.equals("1")){
			c_queryDb.setText(dqzt);
			btnc_queryDb.setEnabled(false);
		}else{
			btnc_queryDb.setEnabled(true);
			if (!sp.getString("DB_CDTA", "").equals("")) {
			}
			c_queryDb.setText(sp.getString("DB_CDTA", ""));

		}
		// 添加数据列表显示
		this.mHead = ((RelativeLayout) findViewById(R.id.sale_scrow_head));
		this.mHead.setFocusable(true);
		this.mHead.setClickable(true);
		this.mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
		lv_sale = (ListView) findViewById(R.id.lv_salePut_header);
		lv_sale.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
		dList = new ArrayList();
		checkedIndexList = new ArrayList<Integer>();
		checkBoxList = new ArrayList<CheckBox>();
		sAdapter = new SimpleAdapter(context, dList, R.layout.sale_scrow_item,
				new String[] { "账套", "单号", "客户编号", "客户名称", "客户电话", "客户地址",
				"转入单号", "客户订单", "备注信息", "色号", "ERP核算单位", "ERP部门",
				"ERP厂商", "ERP客户", "ERP用户", "ERP审核人", "销售渠道", "销售人员",
				"品号", "货品中类", "货品库区" }, new int[] {
						R.id.salecrsow_itemZT, R.id.salecrsow_itembileNo,
						R.id.salecrsow_itemcustNo, R.id.salecrsow_itemcustName,
						R.id.salecrsow_itemcustPhone,
						R.id.salecrsow_itemcustAdr, R.id.salecrsow_iteminputNo,
						R.id.salecrsow_itemcust_OS, R.id.salecrsow_itemrem,
						R.id.salecrsow_itemprdMark, R.id.salecrsow_itemERPhs,
						R.id.salecrsow_itemERPdep, R.id.salecrsow_itemERPsup,
						R.id.salecrsow_itemERPcust, R.id.salecrsow_itemERPuser,
						R.id.salecrsow_itemchk, R.id.salecrsow_itemarea,
						R.id.salecrsow_itememployee, R.id.salecrsow_itemprdNO,
						R.id.salecrsow_itemprdIndex, R.id.salecrsow_itemprdWh }) {
			public View getView(final int position, View convertView,
					android.view.ViewGroup parent) {
				synchronized (ConditionActivity.this) {
					if (convertView == null)
						convertView = View.inflate(ConditionActivity.this,
								R.layout.sale_scrow_item, null);
					MyHScrollView scrollView1 = (MyHScrollView) convertView
							.findViewById(R.id.horizontalScrollView1);
					MyHScrollView headSrcrollView = (MyHScrollView) mHead
							.findViewById(R.id.horizontalScrollView1);
					headSrcrollView
							.AddOnScrollChangedListener(new OnScrollChangedListenerImp(
									scrollView1));
					final CheckBox checkBox = (CheckBox) convertView
							.findViewById(R.id.saleitem_listDeleteCheckBox);
					checkBox.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							if (((CheckBox) v).isChecked()) {
								checkflag = 1;
								checkedIndexList.add(position);
								item_get = dList.get(position);
							} else {
								checkflag = 0;
								checkedIndexList.remove((Integer) position);
							}
						}
					});
				}
				return super.getView(position, convertView, parent);
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
		};
		lv_sale.setAdapter(sAdapter);

	}

	// 点击事件
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_addProject:
			// 添加item
			getInfo();
			if (str_db.equals("")) {
				Toast.makeText(context, "请选择查询账套", Toast.LENGTH_LONG).show();
			} else {
				if (dList != null && dList.size() > 0) {
					for (int i = 0; i < dList.size(); i++) {
						del_item = dList.get(i);
						queryZTs_get.add(del_item.get("账套").toString());
					}

				}
				if (queryZTs_get.contains(str_db)) {
					Toast.makeText(context, "此数据已存在", Toast.LENGTH_LONG).show();
				} else {

					item = new HashMap<String, Object>();
					setInfo();
					dList.add(item);
					setNull();
					Log.e("LiNing", "提交保存的数据====" + dList);
					sAdapter.notifyDataSetChanged();
				}
			}
			break;
		case R.id.btn_put:
			// 添加item
			// putAndadd();
			if(dList!=null&&dList.size()>0){
				
				String str_json = new Gson().toJson(dList);
				if(gjss.equals("1")){

					sp.edit().putString("sale_tjInfo", str_json).commit();
				}
				if(gjss.equals("2")){

					sp.edit().putString("sale_tjInfo_db", str_json).commit();
				}
				Log.e("LiNing", "提交保存的数据====" + str_json);
				finish();
			}else{
				Toast.makeText(context, "请添加账套", Toast.LENGTH_LONG).show();
			}
			// 提交
			break;
		case R.id.btn_del:
			if (checkflag == 1) {
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
				Toast.makeText(context, "请选择要操作的数据", Toast.LENGTH_LONG).show();
			}
			// 删除
			break;
		case R.id.ib_queryDb:
			Intent intent = new Intent(context, CondicionInfoActivity.class);
//			Intent intent = new Intent(context, GetInfoActivity.class);
			intent.putExtra("flag", "1");
			startActivityForResult(intent, 1);
			break;

		case R.id.ib_queryDep:
			if (!c_queryDb.getText().toString().equals("")) {

				Intent intent11 = new Intent(context,
						CondicionInfoActivity.class);
				intent11.putExtra("flag", "11");
				intent11.putExtra("queryID", c_queryDb.getText().toString());
				startActivityForResult(intent11, 11);
			} else {
				Toast.makeText(context, "请选择单账套", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.ib_queryhs:
			if (!c_queryDb.getText().toString().equals("")) {

				Intent intent10 = new Intent(context,
						CondicionInfoActivity.class);
				intent10.putExtra("flag", "10");
				intent10.putExtra("queryID", c_queryDb.getText().toString());
				startActivityForResult(intent10, 10);
			} else {
				Toast.makeText(context, "请选择单账套", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.ib_querySup:
			if (!c_queryDb.getText().toString().equals("")) {

				Intent intent12 = new Intent(context,
						CondicionInfoActivity.class);
				intent12.putExtra("flag", "12");
				intent12.putExtra("queryID", c_queryDb.getText().toString());
				startActivityForResult(intent12, 12);
			} else {
				Toast.makeText(context,"请选择单账套", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.ib_queryCust:
			if (!c_queryDb.getText().toString().equals("")) {

				Intent intent13 = new Intent(context,
						CondicionInfoActivity.class);
				intent13.putExtra("flag", "13");
				intent13.putExtra("queryID", c_queryDb.getText().toString());
				startActivityForResult(intent13, 13);
			} else {
				Toast.makeText(context, "请选择单账套", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.ib_queryUser:
			if (!c_queryDb.getText().toString().equals("")) {

				Intent intent14 = new Intent(context,
						CondicionInfoActivity.class);
				intent14.putExtra("flag", "14");
				intent14.putExtra("queryID", c_queryDb.getText().toString());
				startActivityForResult(intent14, 14);
			} else {
				Toast.makeText(context, "请选择单账套", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.ib_chkUser:
			if (!c_queryDb.getText().toString().equals("")) {

				Intent intent15 = new Intent(context,
						CondicionInfoActivity.class);
				intent15.putExtra("flag", "15");
				intent15.putExtra("queryID", c_queryDb.getText().toString());
				startActivityForResult(intent15, 15);
			} else {
				Toast.makeText(context, "请选择单账套", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.ib_area:
			if (!c_queryDb.getText().toString().equals("")) {

				Intent intent16 = new Intent(context,
						CondicionInfoActivity.class);
				intent16.putExtra("flag", "16");
				intent16.putExtra("queryID", c_queryDb.getText().toString());
				startActivityForResult(intent16, 16);
			} else {
				Toast.makeText(context, "请选择单账套", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.ib_employee:
			if (!c_queryDb.getText().toString().equals("")) {
				Intent intent17 = new Intent(context,
						CondicionInfoActivity.class);
				intent17.putExtra("flag", "17");
				intent17.putExtra("queryID", c_queryDb.getText().toString());
				startActivityForResult(intent17, 17);
			} else {
				Toast.makeText(context, "请选择单账套", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.ib_prdNo:
			if (!c_queryDb.getText().toString().equals("")) {
				Intent intent18 = new Intent(context,
						CondicionInfoActivity.class);
				intent18.putExtra("flag", "18");
				intent18.putExtra("queryID", c_queryDb.getText().toString());
				startActivityForResult(intent18, 18);
			} else {
				Toast.makeText(context, "请选择单账套", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.ib_prdIndex:
			if (!c_queryDb.getText().toString().equals("")) {
				Intent intent19 = new Intent(context,
						CondicionInfoActivity.class);
				intent19.putExtra("flag", "19");
				intent19.putExtra("queryID", c_queryDb.getText().toString());
				startActivityForResult(intent19, 19);
			} else {
				Toast.makeText(context, "请选择单账套", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.ib_prdWh:
			if (!c_queryDb.getText().toString().equals("")) {
				Intent intent20 = new Intent(context,
						CondicionInfoActivity.class);
				intent20.putExtra("flag", "20");
				intent20.putExtra("queryID", c_queryDb.getText().toString());
				startActivityForResult(intent20, 20);
			} else {
				Toast.makeText(context, "请选择单账套", Toast.LENGTH_LONG).show();
			}
			break;
		default:
			break;
		}
	}

	private void putAndadd() {
		getInfo();
		if (str_db.equals("")) {
			Toast.makeText(context, "请选择查询账套", Toast.LENGTH_LONG).show();
		} else {
			if (dList != null && dList.size() > 0) {
				for (int i = 0; i < dList.size(); i++) {
					del_item = dList.get(i);
					queryZTs_get.add(del_item.get("账套").toString());
				}

			}
			if (queryZTs_get.contains(str_db)) {
				Toast.makeText(context, "此数据已存在", Toast.LENGTH_LONG).show();
			} else {

				item = new HashMap<String, Object>();
				setInfo();
				dList.add(item);
				setNull();
				Log.e("LiNing", "提交保存的数据====" + dList);
				sAdapter.notifyDataSetChanged();
			}
		}
	}

	protected void delMore() {

		// 先将checkedIndexList中的元素从大到小排列,否则可能会出现错位删除或下标溢出的错误
		checkedIndexList = sortCheckedIndexList(checkedIndexList);
		for (int i = 0; i < checkedIndexList.size(); i++) {
			// 需要强转为int,才会删除对应下标的数据,否则默认删除与括号中对象相同的数据
			dList.remove((int) checkedIndexList.get(i));
			checkBoxList.remove(checkedIndexList.get(i));
			Log.e("LiNing",  "删除的数据====" + checkedIndexList.get(i));
		}
		for (int i = 0; i < checkBoxList.size(); i++) {
			// 将已选的设置成未选状态״̬
			checkBoxList.get(i).setChecked(false);
			// 将checkbox设置为不可见
			checkBoxList.get(i).setVisibility(View.VISIBLE);
		}

		// 清空checkedIndexList,避免影响下一次删除
		checkedIndexList.clear();
		// 更新数据源
		sAdapter.notifyDataSetChanged();
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

	private void setInfo() {
		// item.put("账套", str_db);
				item.put("单号", str_bilNo);
				item.put("客户编号", str_custNo);
				item.put("客户名称", str_custName);
				item.put("客户电话", str_custPhone);
				item.put("客户地址", str_custAdress);
				item.put("转入单号", str_inputNo);
				item.put("客户订单", str_custOSNO);
				item.put("备注信息", str_rem);
				item.put("色号", str_prdMark);
		if (c_queryDb.getText().toString().equals("")
				|| c_queryDb.getText().toString().equals("null")) {
			item.put("账套", "All");
		} else {

			item.put("账套", str_db);
		}
		if (c_queryHs.getText().toString().equals("")
				|| c_queryHs.getText().toString().equals("null")) {
			item.put("ERP核算单位", "All");
		} else {

			item.put("ERP核算单位", id_hs);
		}
		if (c_queryDep.getText().toString().equals("")
				|| c_queryDep.getText().toString().equals("null")) {
			item.put("ERP部门", "All");
		} else {

			item.put("ERP部门", id_dep);
		}
		if (c_querySup.getText().toString().equals("")
				|| c_querySup.getText().toString().equals("null")) {
			item.put("ERP厂商", "All");
		} else {
			item.put("ERP厂商", id_querySup);
		}
		if (c_queryCust.getText().toString().equals("")
				|| c_queryCust.getText().toString().equals("null")) {

			item.put("ERP客户", "All");
		} else {
			item.put("ERP客户", id_queryCust);
		}
		if (c_queryUser.getText().toString().equals("")
				|| c_queryUser.getText().toString().equals("null")) {

			item.put("ERP用户", "All");
		} else {
			item.put("ERP用户", id_queryUser);
		}
		if (c_chkUser.getText().toString().equals("")) {

			item.put("ERP审核人", "All");
		} else {
			item.put("ERP审核人", id_chkUser);
		}
		if (c_area.getText().toString().equals("")) {

			item.put("销售渠道", "All");
		} else {
			item.put("销售渠道", id_area);
		}
		if (c_employee.getText().toString().equals("")) {

			item.put("销售人员", "All");
		} else {
			item.put("销售人员", id_employee);
		}
		if (c_prdNo.getText().toString().equals("")) {

			item.put("品号", "All");
		} else {
			item.put("品号", id_prdNo);
		}
		if (c_prdIndex.getText().toString().equals("")) {

			item.put("货品中类", "All");
		} else {
			item.put("货品中类", id_prdIndex);
		}
		if (c_prdWh.getText().toString().equals("")) {

			item.put("货品库区", "All");
		} else {
			item.put("货品库区", id_prdWh);
		}
	}

	private void setNull() {
		c_queryDb.setText(null);
		c_queryDep.setText(null);
		c_querySup.setText(null);
		c_queryCust.setText(null);
		c_queryUser.setText(null);
		c_chkUser.setText(null);
		c_area.setText(null);
		c_employee.setText(null);
		c_prdNo.setText(null);
		c_prdIndex.setText(null);
		c_prdWh.setText(null);
		c_bilNo.setText(null);
		c_custNo.setText(null);
		c_custName.setText(null);
		c_custPhone.setText(null);
		c_custAdress.setText(null);
		c_inputNo.setText(null);
		c_custOSNO.setText(null);
		c_rem.setText(null);
		c_prdMark.setText(null);
	}

	private void getInfo() {
		str_db = c_queryDb.getText().toString();
		sp.edit().putString("DB_CDTA", str_db).commit();
		str_hs = c_queryHs.getText().toString();
		sp.edit().putString("HS_CDTA", str_hs).commit();
		str_dep = c_queryDep.getText().toString();
		sp.edit().putString("DEP_CDTA", str_dep).commit();
		str_dsup = c_querySup.getText().toString();
		sp.edit().putString("SUP_CDTA", str_dsup).commit();
		str_cust = c_queryCust.getText().toString();
		sp.edit().putString("CUST_CDTA", str_cust).commit();
		str_user = c_queryUser.getText().toString();
		sp.edit().putString("USER_CDTA", str_user).commit();
		str_chkUser = c_chkUser.getText().toString();
		sp.edit().putString("CHKUSER_CDTA", str_chkUser).commit();
		str_area = c_area.getText().toString();
		sp.edit().putString("AREA_CDTA", str_area).commit();
		str_empl = c_employee.getText().toString();
		sp.edit().putString("EMPL_CDTA", str_empl).commit();
		str_prdNo = c_prdNo.getText().toString();
		sp.edit().putString("PRDNO_CDTA", str_prdNo).commit();
		str_prdIndex = c_prdIndex.getText().toString();
		sp.edit().putString("PRDINDEX_CDTA", str_prdIndex).commit();
		str_prdWh = c_prdWh.getText().toString();
		sp.edit().putString("PRDWH_CDTA", str_prdWh).commit();
		str_bilNo = c_bilNo.getText().toString();
		sp.edit().putString("BILNO_CDTA", str_bilNo).commit();
		str_custNo = c_custNo.getText().toString();
		sp.edit().putString("CUSTNO_CDTA", str_custNo).commit();
		str_custName = c_custName.getText().toString();
		sp.edit().putString("CSTNAME_CDTA", str_custName).commit();
		str_custPhone = c_custPhone.getText().toString();
		sp.edit().putString("CSTP_CDTA", str_custPhone).commit();
		str_custAdress = c_custAdress.getText().toString();
		sp.edit().putString("CSTARD_CDTA", str_custAdress).commit();
		str_inputNo = c_inputNo.getText().toString();
		sp.edit().putString("INPUTNO_CDTA", str_inputNo).commit();
		str_custOSNO = c_custOSNO.getText().toString();
		sp.edit().putString("CSTOSNO_CDTA", str_custOSNO).commit();
		str_rem = c_rem.getText().toString();
		sp.edit().putString("REM_CDTA", str_rem).commit();
		str_prdMark = c_prdMark.getText().toString();
		sp.edit().putString("PRD_CDTA", str_prdMark).commit();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			if (resultCode == 1) {
//				str_id = data.getStringExtra("data_return");
				str_id = data.getStringExtra("condition_db");
				String str_name = data.getStringExtra("condition_name");
				c_queryDb.setText(str_id);
				Log.e("LiNing", "提交的id====" + str_id + str_name);
				// sp.edit().putString("USERIDquerry", str1).commit();
			}
			break;
		case 10:
			if (resultCode == 1) {
				String str1 = data.getStringExtra("data_return");
				id_hs = data.getStringExtra("data_return_ids");
				c_queryHs.setText(str1);
				Log.e("LiNing", "提交的id====" + str1 + id_hs);
				// sp.edit().putString("USERIDquerry", str1).commit();
			}
			break;
		case 11:
			if (resultCode == 1) {
				String str1 = data.getStringExtra("data_return");
				id_dep = data.getStringExtra("data_return_ids");
				c_queryDep.setText(str1);
				Log.e("LiNing", "提交的id====" + str1 + id_dep);
				// sp.edit().putString("USERIDquerry", str1).commit();
			}
			break;
		case 12:
			if (resultCode == 1) {
				String str1 = data.getStringExtra("data_return");
				id_querySup = data.getStringExtra("data_return_ids");
				c_querySup.setText(str1);
				Log.e("LiNing", "提交的id====" + str1);
				// sp.edit().putString("USERIDquerry", str1).commit();
			}
			break;
		case 13:
			if (resultCode == 1) {
				String str1 = data.getStringExtra("data_return");
				id_queryCust = data.getStringExtra("data_return_ids");
				c_queryCust.setText(str1);
				Log.e("LiNing", "提交的id====" + str1);
				// sp.edit().putString("USERIDquerry", str1).commit();
			}
			break;
		case 14:
			if (resultCode == 1) {
				String str1 = data.getStringExtra("data_return");
				id_queryUser = data.getStringExtra("data_return_ids");
				c_queryUser.setText(str1);
				Log.e("LiNing", "提交的id====" + str1);
				// sp.edit().putString("USERIDquerry", str1).commit();
			}
			break;
		case 15:
			if (resultCode == 1) {
				String str1 = data.getStringExtra("data_return");
				id_chkUser = data.getStringExtra("data_return_ids");
				c_chkUser.setText(str1);
				Log.e("LiNing", "提交的id====" + str1);
				// sp.edit().putString("USERIDquerry", str1).commit();
			}
			break;
		case 16:
			if (resultCode == 1) {
				String str1 = data.getStringExtra("data_return");
				id_area = data.getStringExtra("data_return_ids");
				c_area.setText(str1);
				// sp.edit().putString("USERIDquerry", str1).commit();
			}
			break;
		case 17:
			if (resultCode == 1) {
				String str1 = data.getStringExtra("data_return");
				id_employee = data.getStringExtra("data_return_ids");
				c_employee.setText(str1);
				// sp.edit().putString("USERIDquerry", str1).commit();
			}
			break;
		case 18:
			if (resultCode == 1) {
				String str1 = data.getStringExtra("data_return");
				id_prdNo = data.getStringExtra("data_return_ids");
				c_prdNo.setText(str1);
				// sp.edit().putString("USERIDquerry", str1).commit();
			}
			break;
		case 19:
			if (resultCode == 1) {
				String str1 = data.getStringExtra("data_return");
				id_prdIndex = data.getStringExtra("data_return_ids");
				c_prdIndex.setText(str1);
				// sp.edit().putString("USERIDquerry", str1).commit();
			}
			break;
		case 20:
			if (resultCode == 1) {
				String str1 = data.getStringExtra("data_return");
				id_prdWh = data.getStringExtra("data_return_ids");
				c_prdWh.setText(str1);
				// sp.edit().putString("USERIDquerry", str1).commit();
			}
			break;

		default:
			break;
		}
	}

	class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {
		ListViewAndHeadViewTouchLinstener() {
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			((HorizontalScrollView) ConditionActivity.this.mHead
					.findViewById(R.id.horizontalScrollView1))
					.onTouchEvent(event);
			return false;
		}

	}

	public void allback(View v) {
		flag_back = 1;
		finish();
	}

}
