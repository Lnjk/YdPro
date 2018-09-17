package com.example.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.adapter.UserModAdapter.OnItemClickListener;
import com.example.bean.CusterInfoDB.User_Mod;
import com.example.bean.CusterInfoDB.User_Query;
import com.example.bean.CusterInfoDB.Users;
import com.example.ydshoa.R;
import com.google.gson.Gson;

import android.R.integer;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class UserQueryAdapter extends BaseAdapter {
	ViewHolder holder = null;
	private Context context;
	private List<User_Query> usesInfo;
	private int resource;
	private LayoutInflater inflater;
	private int code, yc;
	private ArrayList<HashMap<String, Object>> dList;
	private SharedPreferences sp;
	private OnItemClickListener onItemClickListener;
	public static Map<Integer, Boolean> isSelected;

	public UserQueryAdapter(int yc, Context context,
			List<User_Query> userQuery, int fuctionItem) {
		this.yc = yc;
		this.context = context;
		this.usesInfo = userQuery;
		this.resource = fuctionItem;
		this.inflater = ((LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
		sp = context.getSharedPreferences("ydbg", 0);
		init();
	}

	private void init() {
// 这儿定义isSelected这个map是记录每个listitem的状态，初始状态全部为false。
 		isSelected = new HashMap<Integer, Boolean>();
		for (int i = 0; i < usesInfo.size(); i++) {
			isSelected.put(i, false);
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return usesInfo.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return usesInfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(this.resource, null);
			holder.checkBox = (CheckBox) convertView
					.findViewById(R.id.listDeleteCheckBox);
			if (yc == 1) {
				holder.checkBox.setVisibility(View.GONE);
			}
			holder.db = (TextView) convertView
					.findViewById(R.id.new_querry_item_dbid);
			holder.dep = (TextView) convertView
					.findViewById(R.id.new_querry_item_dep);
			holder.sup = (TextView) convertView
					.findViewById(R.id.new_querry_item_sup);
			holder.cust = (TextView) convertView
					.findViewById(R.id.new_querry_item_cust);
			holder.user = (TextView) convertView
					.findViewById(R.id.new_querry_item_user);
			holder.hs = (TextView) convertView
					.findViewById(R.id.new_querry_item_hs);
			convertView.setTag(holder);
		} else {
			holder = ((ViewHolder) convertView.getTag());
		}
		Log.e("LiNing", "---------" + code);
		User_Query user_Query = usesInfo.get(position);
		Log.e("LiNing", "---------" + user_Query);
		holder.db.setText(user_Query.getQuery_DB());
		holder.dep.setText(user_Query.getQuery_Dep());
		holder.sup.setText(user_Query.getQuery_Sup());
		holder.cust.setText(user_Query.getQuery_Cust());
		holder.user.setText(user_Query.getQuery_User());
		holder.hs.setText(user_Query.getQuery_CompDep());
		Log.e("LiNing", "---------bolean----" + isSelected.get(position));
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

	public class ViewHolder {
		public TextView db;
		public TextView dep;
		public TextView sup;
		public TextView cust;
		public TextView user;
		public TextView hs;
		public CheckBox checkBox;
	}
}
