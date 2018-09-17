package com.example.ydshoa;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyFontTextView extends TextView {

	public MyFontTextView(Context paramContext)
	  {
	    super(paramContext);
	  }

	  public MyFontTextView(Context paramContext, AttributeSet paramAttributeSet)
	  {
	    super(paramContext, paramAttributeSet);
	  }

	  public MyFontTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
	  {
	    super(paramContext, paramAttributeSet, paramInt);
	  }

	  public void setTypeface(Typeface paramTypeface, int paramInt)
	  {
	    if (paramInt == 1)
	    {
	      super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/jt.ttf"));
	      return;
	    }
	    super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/jt.ttf"));
	  }
}
