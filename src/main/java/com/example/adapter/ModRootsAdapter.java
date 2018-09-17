package com.example.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.bean.ModList.Module;
import com.example.ydshoa.R;
import com.google.gson.Gson;

import android.R.integer;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ModRootsAdapter extends BaseAdapter {
	// private ViewHolder holder = null;
	private LayoutInflater inflater;
	private SharedPreferences sp;
	private List<Module> modules;
	private Editor edit;
	private int resource;
	private List<ViewHolder> lHolders = new ArrayList();
	private ArrayList<HashMap<String, Object>> InfoList = new ArrayList();
	private int flag;
	Context context;

	public ModRootsAdapter(int custer, List<Module> module, Context context) {
		this.context=context;
		this.resource = custer;
		this.modules = module;
		this.inflater = ((LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
		this.sp = context.getSharedPreferences("ydbg", 0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return modules.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return modules.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(this.resource, null);
			holder.id = ((TextView) convertView
					.findViewById(R.id.fuction_root_itemID));
			holder.name = ((TextView) convertView
					.findViewById(R.id.fuction_root_itemname));
//			holder.level = ((TextView) convertView
//					.findViewById(R.id.fuction_root_itemlevel));
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
			convertView.setTag(holder);
		} else {
			holder = ((ViewHolder) convertView.getTag());
		}
		Module module = modules.get(position);

		Log.e("LiNing", "---" + module.getMod_ID());
		holder.id.setText(module.getMod_ID());
		holder.name.setText(module.getMod_Name());
		holder.level.setText(module.getMod_Level());
		holder.querry.setText("F");
		holder.add.setText("F");
		holder.set.setText("F");
		holder.del.setText("F");
		holder.prt.setText("F");
		holder.out.setText("F");
		holder.qty.setText("F");
		holder.up.setText("F");
		holder.amt.setText("F");
		holder.cst.setText("F");
		holder.pr.setText("F");
		holder.prg.setText("F");
		lHolders.add(holder);
		lHolders.get(position).add.setTag(Boolean.valueOf(false));
		lHolders.get(position).set.setTag(Boolean.valueOf(false));
		lHolders.get(position).del.setTag(Boolean.valueOf(false));
		lHolders.get(position).prt.setTag(Boolean.valueOf(false));
		lHolders.get(position).out.setTag(Boolean.valueOf(false));
		lHolders.get(position).qty.setTag(Boolean.valueOf(false));
		lHolders.get(position).up.setTag(Boolean.valueOf(false));
		lHolders.get(position).amt.setTag(Boolean.valueOf(false));
		lHolders.get(position).cst.setTag(Boolean.valueOf(false));
		lHolders.get(position).pr.setTag(Boolean.valueOf(false));
		lHolders.get(position).prg.setTag(Boolean.valueOf(false));
		lHolders.get(position).querry.setTag(Boolean.valueOf(false));
		lHolders.get(position).add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flag = 1;
				if (!((Boolean) lHolders.get(position).add.getTag())
						.booleanValue()) {
					lHolders.get(position).add.setText("T");
					lHolders.get(position).add.setTag(Boolean.valueOf(true));
					sp.edit().putString("ADD",
							lHolders.get(position).add.getText().toString());
				} else {
					lHolders.get(position).add.setText("F");
					lHolders.get(position).add.setTag(Boolean.valueOf(false));
					sp.edit().putString("ADD",
							lHolders.get(position).add.getText().toString());
				}
				String string = lHolders.get(position).add.getText().toString();
				Log.e("LiNing", "add++++"+string);
			}
		});
		Log.e("LiNing", "add---"
				+ lHolders.get(position).add.getText().toString());
		
		lHolders.get(position).set.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flag = 1;
				if (!((Boolean) lHolders.get(position).set.getTag())
						.booleanValue()) {
					lHolders.get(position).set.setText("T");
					lHolders.get(position).set.setTag(Boolean.valueOf(true));
					sp.edit().putString("SET",
							lHolders.get(position).set.getText().toString());
				} else {
					lHolders.get(position).set.setText("F");
					lHolders.get(position).set.setTag(Boolean.valueOf(false));
					sp.edit().putString("SET",
							lHolders.get(position).set.getText().toString());
				}
			}
		});

		lHolders.get(position).del.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flag = 1;
				if (!((Boolean) lHolders.get(position).del.getTag())
						.booleanValue()) {
					lHolders.get(position).del.setText("T");
					lHolders.get(position).del.setTag(Boolean.valueOf(true));
					sp.edit().putString("DEL",
							lHolders.get(position).del.getText().toString());
				} else {
					lHolders.get(position).del.setText("F");
					lHolders.get(position).del.setTag(Boolean.valueOf(false));
				}
			}
		});
		sp.edit().putString("DEL",
				lHolders.get(position).del.getText().toString());
		lHolders.get(position).prt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flag = 1;
				if (!((Boolean) lHolders.get(position).prt.getTag())
						.booleanValue()) {
					lHolders.get(position).prt.setText("T");
					lHolders.get(position).prt.setTag(Boolean.valueOf(true));
					sp.edit().putString("PRT",
							lHolders.get(position).prt.getText().toString());
				} else {
					lHolders.get(position).prt.setText("F");
					lHolders.get(position).prt.setTag(Boolean.valueOf(false));
				}
			}
		});
		sp.edit().putString("PRT",
				lHolders.get(position).prt.getText().toString());
		lHolders.get(position).out.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flag = 1;
				if (!((Boolean) lHolders.get(position).out.getTag())
						.booleanValue()) {
					lHolders.get(position).out.setText("T");
					lHolders.get(position).out.setTag(Boolean.valueOf(true));
					sp.edit().putString("OUT",
							lHolders.get(position).out.getText().toString());
				} else {
					lHolders.get(position).out.setText("F");
					lHolders.get(position).out.setTag(Boolean.valueOf(false));
				}
			}
		});
		sp.edit().putString("OUT",
				lHolders.get(position).out.getText().toString());
		lHolders.get(position).qty.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flag = 1;
				if (!((Boolean) lHolders.get(position).qty.getTag())
						.booleanValue()) {
					lHolders.get(position).qty.setText("T");
					lHolders.get(position).qty.setTag(Boolean.valueOf(true));
					sp.edit().putString("QTY",
							lHolders.get(position).qty.getText().toString());
				} else {
					lHolders.get(position).qty.setText("F");
					lHolders.get(position).qty.setTag(Boolean.valueOf(false));
				}
			}
		});
		sp.edit().putString("QTY",
				lHolders.get(position).qty.getText().toString());
		lHolders.get(position).amt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flag = 1;
				if (!((Boolean) lHolders.get(position).amt.getTag())
						.booleanValue()) {
					lHolders.get(position).amt.setText("T");
					lHolders.get(position).amt.setTag(Boolean.valueOf(true));
					sp.edit().putString("AMT",
							lHolders.get(position).amt.getText().toString());
				} else {
					lHolders.get(position).amt.setText("F");
					lHolders.get(position).amt.setTag(Boolean.valueOf(false));
				}
			}
		});
		sp.edit().putString("AMT",
				lHolders.get(position).amt.getText().toString());
		lHolders.get(position).up.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flag = 1;
				if (!((Boolean) lHolders.get(position).up.getTag())
						.booleanValue()) {
					lHolders.get(position).up.setText("T");
					lHolders.get(position).up.setTag(Boolean.valueOf(true));
					sp.edit().putString("UP",
							lHolders.get(position).up.getText().toString());
				} else {
					lHolders.get(position).up.setText("F");
					lHolders.get(position).up.setTag(Boolean.valueOf(false));
				}
			}
		});
		sp.edit().putString("UP",
				lHolders.get(position).up.getText().toString());
		lHolders.get(position).cst.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flag = 1;
				if (!((Boolean) lHolders.get(position).cst.getTag())
						.booleanValue()) {
					lHolders.get(position).cst.setText("T");
					lHolders.get(position).cst.setTag(Boolean.valueOf(true));
					sp.edit().putString("CST",
							lHolders.get(position).cst.getText().toString());
				} else {
					lHolders.get(position).cst.setText("F");
					lHolders.get(position).cst.setTag(Boolean.valueOf(false));
				}
			}
		});
		sp.edit().putString("CST",
				lHolders.get(position).cst.getText().toString());
		lHolders.get(position).pr.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flag = 1;
				if (!((Boolean) lHolders.get(position).pr.getTag())
						.booleanValue()) {
					lHolders.get(position).pr.setText("T");
					lHolders.get(position).pr.setTag(Boolean.valueOf(true));
					sp.edit().putString("PR",
							lHolders.get(position).pr.getText().toString());
				} else {
					lHolders.get(position).pr.setText("F");
					lHolders.get(position).pr.setTag(Boolean.valueOf(false));
				}
			}
		});
		sp.edit().putString("PR",
				lHolders.get(position).pr.getText().toString());
		lHolders.get(position).prg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flag = 1;
				if (!((Boolean) lHolders.get(position).prg.getTag())
						.booleanValue()) {
					lHolders.get(position).prg.setText("T");
					lHolders.get(position).prg.setTag(Boolean.valueOf(true));
					sp.edit().putString("PRG",
							lHolders.get(position).prg.getText().toString());
				} else {
					lHolders.get(position).prg.setText("F");
					lHolders.get(position).prg.setTag(Boolean.valueOf(false));
				}
			}
		});
		sp.edit().putString("PRG",
				lHolders.get(position).prg.getText().toString());
		lHolders.get(position).querry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flag = 1;
				if (!((Boolean) lHolders.get(position).querry.getTag())
						.booleanValue()) {
					lHolders.get(position).querry.setText("T");
					lHolders.get(position).querry.setTag(Boolean.valueOf(true));
					sp.edit().putString("QUERY",
							lHolders.get(position).querry.getText().toString());
				} else {
					lHolders.get(position).querry.setText("F");
					lHolders.get(position).querry.setTag(Boolean.valueOf(false));
				}
			}
		});
		sp.edit().putString("QUERY",
				lHolders.get(position).querry.getText().toString());
		// InfoList = new ArrayList();
		HashMap<String, Object> item = new HashMap<String, Object>();
		item.put("ID", holder.id.getText().toString());
		item.put("NAME", holder.name.getText().toString());
		item.put("LEVEL", holder.level.getText().toString());

		if (flag == 1) {
			item.put("Querry", lHolders.get(position).querry.getText()
					.toString());
			item.put("Add", lHolders.get(position).add.getText().toString());
			item.put("AMT", lHolders.get(position).amt.getText().toString());
			item.put("CST", lHolders.get(position).cst.getText().toString());
			item.put("DEL", lHolders.get(position).del.getText().toString());
			item.put("OUT", lHolders.get(position).out.getText().toString());
			item.put("PR", lHolders.get(position).pr.getText().toString());
			item.put("PRG", lHolders.get(position).prg.getText().toString());
			item.put("PRT", lHolders.get(position).prt.getText().toString());
			item.put("QTY", lHolders.get(position).qty.getText().toString());
			item.put("SET", lHolders.get(position).set.getText().toString());
			item.put("UP", lHolders.get(position).up.getText().toString());
		} else {
//			Toast.makeText(context, "û���", 1).show();
			item.put("Querry", "F");
			item.put("Add", "F");
			item.put("AMT", "F");
			item.put("CST", "F");
			item.put("DEL", "F");
			item.put("OUT", "F");
			item.put("PR", "F");
			item.put("PRG", "F");
			item.put("PRT", "F");
			item.put("QTY", "F");
			item.put("SET", "F");
			item.put("UP", "F");
		}
		Log.e("LiNing", ",,,,,,," + item);
		InfoList.add(item);
		Log.e("LiNing", "====item," + item);
		Log.e("LiNing", "====InfoList," + InfoList.toString());
		String str2 = new Gson().toJson(InfoList);
		Editor edit2 = sp.edit();
		edit2.putString("QuerryInfos", str2).commit();
		Log.e("LiNing", "�õ�����" + sp.getString("QuerryInfos", ""));
		// notifyDataSetChanged();
		return convertView;
	}

	private class ViewHolder {
		public TextView id;
		public TextView name;
		public TextView level;
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

	}
}
