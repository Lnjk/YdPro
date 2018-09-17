package com.example.adapter;

import java.util.HashMap;
import java.util.List;

import com.example.bean.ViewHolder;
import com.example.bean.UserInfo.User_Query;
import com.example.ydshoa.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ConditionInfoAdapter extends BaseAdapter {
	public static HashMap<Integer, Boolean> isSelected;
	private Context context;
	private List<User_Query> data;
	private LayoutInflater inflater;
	private String keyString[] = null;
	private int idValue[] = null;
	private String itemString = null;
	private int index;

	public ConditionInfoAdapter(int index, Context context,
			List<User_Query> user_Query, int infoItemFour, String[] strings,
			int[] is) {
		this.index = index;
		this.context = context;
		this.data = user_Query;
		this.keyString = new String[strings.length];
		this.idValue = new int[is.length];
		inflater = LayoutInflater.from(context);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		isSelected = new HashMap<Integer, Boolean>();
		for (int i = 0; i < data.size(); i++) {
			isSelected.put(i, false);
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = new ViewHolder();
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.info_item_four, null);
			holder.id = (TextView) convertView.findViewById(R.id.author_id);
			holder.tv = (TextView) convertView.findViewById(R.id.author);
			holder.cb = (CheckBox) convertView.findViewById(R.id.radio);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		User_Query user_Query = data.get(position);
		if (user_Query != null) {
			String query_DB = user_Query.getQuery_DB();
			String query_Dep = user_Query.getQuery_Dep();
			String query_Sup = user_Query.getQuery_Sup();
			String query_Cust = user_Query.getQuery_Cust();
			String query_User = user_Query.getQuery_User();
			if (index == 1) {

				if (query_DB.equals("DB_BJ15")) {
					holder.id.setText(query_DB);
					holder.tv.setText("北京15");
				}
				if (query_DB.equals("DB_BT15")) {
					holder.id.setText(query_DB);
					holder.tv.setText("包头15");
				}

			}
			if (index == 2) {

				holder.tv.setText(query_Dep);
			}
			if (index == 3) {

				holder.tv.setText(query_Sup);
			}
			if (index == 4) {

				holder.tv.setText(query_Cust);
			}
			if (index == 5) {

				holder.tv.setText(query_User);
			}
		}
		holder.cb.setChecked(isSelected.get(position));
		return convertView;
	}

}
