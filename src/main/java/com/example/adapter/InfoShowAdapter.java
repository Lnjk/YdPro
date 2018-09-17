package com.example.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class InfoShowAdapter extends BaseAdapter{
	private Context context;
	  private List list;
	  public InfoShowAdapter(Context paramContext, List paramList)
	  {
	    this.context = paramContext;
	    this.list = paramList;
	  }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return Integer.valueOf(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
//		 if (paramView == null){
//
//			 paramView = LayoutInflater.from(this.context).inflate(2130903103, null);
//		 }
		    return paramView;
	}

}
