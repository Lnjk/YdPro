package com.example.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.bean.DepInfo.IdNameList;
import com.example.bean.ViewHolder;
import com.example.ydshoa.R;

public class SupInfoAdapter extends BaseAdapter {
	public static HashMap<Integer, Boolean> isSelected;
	private Context context = null;
	private LayoutInflater inflater = null;
	private List<IdNameList> list = null;
	private String keyString[] = null;
	private String itemString = null; // 记录每个item中textview的值
	private int idValue[] = null;// id值

	public SupInfoAdapter(Context context,
			List<IdNameList> depInfo, int paramInt,
			String[] paramArrayOfString, int[] paramArrayOfInt) {
		this.context = context;
		this.list = depInfo;
		this.keyString = new String[paramArrayOfString.length];
		this.idValue = new int[paramArrayOfInt.length];
		System.arraycopy(paramArrayOfString, 0, this.keyString, 0,
				paramArrayOfString.length);
		System.arraycopy(paramArrayOfInt, 0, this.idValue, 0,
				paramArrayOfInt.length);
		inflater = LayoutInflater.from(context);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		isSelected = new HashMap<Integer, Boolean>();
		for (int i = 0; i < list.size(); i++) {
			// if (i >= this.list.size()){
			isSelected.put(i, false);
		}
		// }
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		ViewHolder localViewHolder = new ViewHolder();
		if (paramView == null) {
			paramView = inflater.inflate(R.layout.info_item_four, null);
			localViewHolder.id = (TextView) paramView.findViewById(R.id.author_id);
			localViewHolder.tv = (TextView) paramView.findViewById(R.id.author);
			localViewHolder.cb = (CheckBox) paramView.findViewById(R.id.radio);
			paramView.setTag(localViewHolder);
		}else{
			localViewHolder=(ViewHolder) paramView.getTag();
		}
//		HashMap<String, Object> localHashMap = (HashMap) list.get(paramInt);
		IdNameList localHashMap = list.get(paramInt);
		Log.e("LiNing", "222222222222222222" + localHashMap);
		if (localHashMap != null) {
			String name = (String) localHashMap.getName();
			String id = localHashMap.getId();
			Log.e("LiNing", "1111111" + name+id);
			localViewHolder.id.setText(id);
			localViewHolder.tv.setText(name);
		}
		localViewHolder.cb.setChecked(isSelected.get(paramInt));
		return paramView;
	}

}
