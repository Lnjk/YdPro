package com.example.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.LeftOrRight.MyHScrollView;
import com.example.bean.CusterInfoDB.User_Mod;
import com.example.bean.CusterInfoDB.Users;
import com.example.bean.ModRoot;
import com.example.ydshoa.R;
import com.example.ydshoa.CusterInfoActivity.CheckBoxListener;
import com.google.gson.Gson;

import android.R.integer;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class UserModAdapter extends BaseAdapter {

	private Context context;
	private List<User_Mod> userMod;
	private int resource;
	private LayoutInflater inflater;
	private ArrayList<HashMap<String, Object>> dList;
	private SharedPreferences sp;
	private OnItemClickListener onItemClickListener;
	private boolean mAllCheckStation[];
	public static Map<Integer, Boolean> isSelected;

	public UserModAdapter(Context context, List<User_Mod> userMod2,
			int fuctionItem) {
		this.context = context;
		this.userMod = userMod2;
		this.resource = fuctionItem;
		this.inflater = ((LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
		sp = context.getSharedPreferences("ydbg", 0);
		mAllCheckStation = new boolean[userMod.size()];
		init();
	}

	private void init() {
		// 这儿定义isSelected这个map是记录每个listitem的状态，初始状态全部为false。
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewCache holder = null;
		if (convertView == null) {
			holder = new ViewCache();
			convertView = inflater.inflate(this.resource, null);
			holder.checkBox = (CheckBox) convertView
					.findViewById(R.id.mod_showChecx);
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
			convertView.setTag(holder);

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
		return convertView;
	}

	public interface OnItemClickListener {

		void showResutlt(User_Mod user_Mod, int paramInt);
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		this.onItemClickListener = listener;
	}

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
