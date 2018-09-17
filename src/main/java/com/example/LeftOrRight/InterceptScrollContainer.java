package com.example.LeftOrRight;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class InterceptScrollContainer extends LinearLayout {
	public InterceptScrollContainer(Context paramContext)
	  {
	    super(paramContext);
	  }

	  public InterceptScrollContainer(Context paramContext, AttributeSet paramAttributeSet)
	  {
	    super(paramContext, paramAttributeSet);
	  }

	  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
	  {
	    Log.i("pdwy", "ScrollContainer onInterceptTouchEvent");
	    return true;
	  }
}
