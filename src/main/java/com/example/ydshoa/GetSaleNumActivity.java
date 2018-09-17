package com.example.ydshoa;

import java.util.ArrayList;
import java.util.List;

import com.example.bean.Brand;
import com.example.bean.SaleNum;
import com.example.bean.AccountInfo.IdNameList;
import com.example.ydshoa.GetInfoActivity.MyAdapter;
import com.example.ydshoa.GetInfoActivity.ViewHolder;
//import com.umeng.message.PushAgent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class GetSaleNumActivity extends Activity {

	private Context context;
	private RadioGroup rg_one;
	private ListView lv_one;
	private String extraFlag;
	private List<SaleNum> namesList;
	private MyAdapter myAdapter;
	private int selectPosition = -1;
	private SaleNum selectBrand;
	private SharedPreferences sp;
	private String strID;
	private TextView head;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_get_sale_num);
		context = GetSaleNumActivity.this;
//		PushAgent.getInstance(context).onAppStart();
		head = (TextView) findViewById(R.id.all_head);
		head.setText("报表编号");
		rg_one = (RadioGroup) findViewById(R.id.rg_saleNum_one);
		lv_one = (ListView) findViewById(R.id.lv_saleNum_one);

		// 请求数据
		getFlag();
	}

	private void getFlag() {
		extraFlag = getIntent().getStringExtra("flag");
		Log.e("LiNing", "flag====" + extraFlag);
		// 1，获取账套
		if (extraFlag.equals("num")) {
			initDatas();
		}
	}

	private void initDatas() {

		// 初始化ListView适配器的数据
		namesList = new ArrayList<SaleNum>();
		SaleNum brand = new SaleNum("SATG", "分支机构销售统计表");
		SaleNum brand0 = new SaleNum("SATGP", "分支机构+品牌销售统计表");
		SaleNum brand1 = new SaleNum("SATGPC", "分支机构+品牌+渠道销售统计表");
		SaleNum brand2 = new SaleNum("SATGPD", "分支机构+品牌+部门销售统计表");
		SaleNum brand3 = new SaleNum("SATGPS", "分支机构+品牌+业务销售统计表");
		SaleNum brand4 = new SaleNum("SATGPGC", "分支机构+品牌+终端网点销售统计表");
		SaleNum brand5 = new SaleNum("SATGC", "分支机构+渠道销售统计表");
		SaleNum brand6 = new SaleNum("SATGCD", "分支机构+渠道+部门销售统计表");
		SaleNum brand7 = new SaleNum("SATGCS", "分支机构+渠道+业务销售统计表");
		SaleNum brand8 = new SaleNum("SATGCGC", "分支机构+渠道+终端网点销售统计表");
		SaleNum brand9 = new SaleNum("SATGD", "分支机构+部门销售统计表");
		SaleNum brand10 = new SaleNum("SATGDS", "分支机构+部门+业务销售统计表");
		SaleNum brand11 = new SaleNum("SATGDGC", "分支机构+部门+终端网点销售统计表");
		SaleNum brand12 = new SaleNum("SATGS", "分支机构+业务销售统计表");
		SaleNum brand13 = new SaleNum("SATGSGC", "分支机构+业务+终端网点销售统计表");
		SaleNum brand14 = new SaleNum("SATGGC", "分支机构+终端网点销售统计表");
		SaleNum brand15 = new SaleNum("SATGPPN", "分支机构+品牌+品号销售统计表");
		SaleNum brand16 = new SaleNum("SATGPI", "分支机构+品牌+货品中类销售统计表");
		SaleNum brand17 = new SaleNum("SATGPIPN", "分支机构+品牌+货品中类+品号销售统计表");
		SaleNum brand18 = new SaleNum("SATGPCPN", "分支机构+品牌+渠道+品号销售统计表");
		SaleNum brand19 = new SaleNum("SATGPCI", "分支机构+品牌+渠道+货品中类销售统计表");
		SaleNum brand20 = new SaleNum("SATGPCIPN", "分支机构+品牌+渠道+货品中类+品号销售统计表");
		SaleNum brand21 = new SaleNum("SATGPDPN", "分支机构+品牌+部门+品号销售统计表");
		SaleNum brand22 = new SaleNum("SATGPDI", "分支机构+品牌+部门+货品中类销售统计表");
		SaleNum brand23 = new SaleNum("SATGPDIPN", "分支机构+品牌+部门+货品中类+品号销售统计表");
		SaleNum brand24 = new SaleNum("SATGPSPN", "分支机构+品牌+业务+品号销售统计表");
		SaleNum brand25 = new SaleNum("SATGPSI", "分支机构+品牌+业务+货品中类销售统计表");
		SaleNum brand26 = new SaleNum("SATGPSIPN", "分支机构+品牌+业务+货品中类+品号销售统计表");
		SaleNum brand27 = new SaleNum("SATGPGCPN", "分支机构+品牌+终端+品号销售统计表");
		SaleNum brand28 = new SaleNum("SATGPGCI", "分支机构+品牌+终端+货品中类销售统计表");
		SaleNum brand29 = new SaleNum("SATGPGCIPN", "分支机构+品牌+终端+货品中类+品号销售统计表");
		namesList.add(brand);
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
		namesList.add(brand10);
		namesList.add(brand11);
		namesList.add(brand12);
		namesList.add(brand13);
		namesList.add(brand14);
		namesList.add(brand15);
		namesList.add(brand16);
		namesList.add(brand17);
		namesList.add(brand18);
		namesList.add(brand19);
		namesList.add(brand20);
		namesList.add(brand21);
		namesList.add(brand22);
		namesList.add(brand23);
		namesList.add(brand24);
		namesList.add(brand25);
		namesList.add(brand26);
		namesList.add(brand27);
		namesList.add(brand28);
		namesList.add(brand29);
		showCheckBoxListView();
	}

	private void showCheckBoxListView() {
		myAdapter = new MyAdapter(context, namesList);
		lv_one.setAdapter(myAdapter);
		lv_one.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// 获取选中的参数
				selectPosition = position;
				myAdapter.notifyDataSetChanged();
				selectBrand = namesList.get(position);
				strID = selectBrand.getId();
			}
		});

	}

	// 获取结果
	public void one_ok(View v) {
		Intent localIntent = getIntent();
		localIntent.putExtra("data_return", strID);
		setResult(1, localIntent);
		finish();
	}

	public class MyAdapter extends BaseAdapter {

		Context context;
		List<SaleNum> brandsList;
		LayoutInflater mInflater;

		public MyAdapter(Context context, List<SaleNum> namesList) {
			this.context = context;
			this.brandsList = namesList;
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
				convertView = mInflater.inflate(R.layout.salenum_item, parent,
						false);
				viewHolder = new ViewHolder();
				viewHolder.id = (TextView) convertView
						.findViewById(R.id.id_item_sale);
				viewHolder.name = (TextView) convertView
						.findViewById(R.id.name_item_sale);
				viewHolder.select = (RadioButton) convertView
						.findViewById(R.id.id_select_sale);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.id.setText(brandsList.get(position).getId());
			viewHolder.name.setText(brandsList.get(position).getIdNum());
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

	public void allback(View v) {
		finish();
	}
}
