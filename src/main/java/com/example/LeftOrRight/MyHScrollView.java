package com.example.LeftOrRight;

import java.util.ArrayList;
import java.util.List;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class MyHScrollView extends HorizontalScrollView {
	ScrollViewObserver mScrollViewObserver = new ScrollViewObserver();

	public MyHScrollView(Context paramContext) {
		super(paramContext);
	}

	public MyHScrollView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public MyHScrollView(Context paramContext, AttributeSet paramAttributeSet,
			int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	public void AddOnScrollChangedListener(
			OnScrollChangedListener paramOnScrollChangedListener) {
		this.mScrollViewObserver
				.AddOnScrollChangedListener(paramOnScrollChangedListener);
	}

	public void RemoveOnScrollChangedListener(
			OnScrollChangedListener paramOnScrollChangedListener) {
		this.mScrollViewObserver
				.RemoveOnScrollChangedListener(paramOnScrollChangedListener);
	}

	protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3,
			int paramInt4) {
		if (this.mScrollViewObserver != null)
			this.mScrollViewObserver.NotifyOnScrollChanged(paramInt1,
					paramInt2, paramInt3, paramInt4);
		super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
	}

	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouchEvent(MotionEvent paramMotionEvent) {
		Log.i("pdwy", "MyHScrollView onTouchEvent");
		return super.onTouchEvent(paramMotionEvent);
	}

	public static abstract interface OnScrollChangedListener {
		public abstract void onScrollChanged(int paramInt1, int paramInt2,
				int paramInt3, int paramInt4);
	}

	public static class ScrollViewObserver {
		List<MyHScrollView.OnScrollChangedListener> mList = new ArrayList<OnScrollChangedListener>();

		public void AddOnScrollChangedListener(
				MyHScrollView.OnScrollChangedListener paramOnScrollChangedListener) {
			this.mList.add(paramOnScrollChangedListener);
		}

		public void NotifyOnScrollChanged(int paramInt1, int paramInt2,
				int paramInt3, int paramInt4) {
			for (int i = 0; i < this.mList.size(); i++){
				if (this.mList.get(i) != null){
		            ((MyHScrollView.OnScrollChangedListener)this.mList.get(i)).onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
		      }
			}
		}

		public void RemoveOnScrollChangedListener(
				OnScrollChangedListener paramOnScrollChangedListener) {
			this.mList.remove(paramOnScrollChangedListener);
		}
	}
}
