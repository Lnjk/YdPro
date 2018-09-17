package com.example.adapter;

import java.util.ArrayList;

import com.example.bean.LxProject;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class MyAdapter<T> extends BaseAdapter {
	private ArrayList<T> mData;
	private int mLayoutRes;

	public MyAdapter() {
	}

	public MyAdapter(ArrayList<T> paramArrayList, int paramInt) {
		this.mData = paramArrayList;
		this.mLayoutRes = paramInt;
	}

	public void add(int paramInt, T paramT) {
		if (this.mData == null) {
			this.mData = new ArrayList<T>();
		}
		this.mData.add(paramInt, paramT);
		notifyDataSetChanged();
	}

	public void add(T paramT) {
		if (mData == null) {
			mData = new ArrayList<T>();
		}
		mData.add(paramT);
		notifyDataSetChanged();
	}

	public abstract void bindView(ViewHolder paramViewHolder, T paramT);

	public void clear() {
		if (mData != null) {
			mData.clear();
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mData != null ? mData.size() : 0;
	}

	@Override
	public T getItem(int paramInt) {
		return mData.get(paramInt);
	}

	@Override
	public long getItemId(int paramInt) {
		return paramInt;
	}

	@Override
	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		ViewHolder localViewHolder = ViewHolder.bind(
				paramViewGroup.getContext(), paramView, paramViewGroup,
				this.mLayoutRes, paramInt);
		bindView(localViewHolder, getItem(paramInt));
		return localViewHolder.getItemView();
	}

	public void remove(int paramInt) {
		if (mData != null) {
			mData.remove(paramInt);
		}
		notifyDataSetChanged();
	}

	public void remove(T paramT) {
		if (mData != null) {
			mData.remove(paramT);
		}
		notifyDataSetChanged();
	}

	public static class ViewHolder {
		private SparseArray<View> mViews; // 存储ListView 的 item中的View
		private View item; // 存放convertView
		private int position; // 游标
		private Context context; // Context上下文

		private ViewHolder(Context paramContext, ViewGroup paramViewGroup,
				int paramInt) {
			mViews = new SparseArray<View>();
			this.context = paramContext;
			View convertView = LayoutInflater.from(context).inflate(paramInt,
					paramViewGroup, false);
			convertView.setTag(this);
			item = convertView;
		}

		public static ViewHolder bind(Context paramContext, View paramView,
				ViewGroup paramViewGroup, int paramInt1, int paramInt2) {

			ViewHolder holder;
			if (paramView == null) {
				holder = new ViewHolder(paramContext, paramViewGroup, paramInt1);
			} else {
				holder = (ViewHolder) paramView.getTag();
				holder.item = paramView;
			}
			holder.position = paramInt2;
			return holder;
		}

		public int getItemPosition() {
			return this.position;
		}

		public View getItemView() {
			return this.item;
		}

		public <T extends View> T getView(int paramInt) {
			T t = (T) mViews.get(paramInt);
			if (t == null) {
				t = (T) item.findViewById(paramInt);
				mViews.put(paramInt, t);
			}
			return t;
		}

		public ViewHolder setImageResource(int paramInt1, int paramInt2) {
			View view = getView(paramInt1);
			if (view instanceof ImageView) {
				((ImageView) view).setImageResource(paramInt2);
			} else {
				view.setBackgroundResource(paramInt2);
			}
			return this;
		}

		public ViewHolder setOnClickListener(int paramInt,
				View.OnClickListener paramOnClickListener) {
			getView(paramInt).setOnClickListener(paramOnClickListener);
			return this;
		}

		public ViewHolder setTag(int paramInt, Object paramObject) {
			getView(paramInt).setTag(paramObject);
			return this;
		}

		public ViewHolder setText(int paramInt, CharSequence paramCharSequence) {
			View view = getView(paramInt);
			if (view instanceof TextView) {
				((TextView) view).setText(paramCharSequence);
			}
			return this;
		}

		public ViewHolder setVisibility(int paramInt1, int paramInt2) {
			getView(paramInt1).setVisibility(paramInt2);
			return this;
		}
	}

}
