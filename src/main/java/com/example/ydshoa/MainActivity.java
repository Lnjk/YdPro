package com.example.ydshoa;

import com.example.fragment.HomeFragment;
import com.example.fragment.MyFragment;
import com.example.fragment.RootFragment;
//import com.umeng.message.PushAgent;

import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private TextView head;
	private HomeFragment homeFragment;
	private ImageView ivone, ivtwo, ivthree;
	private LinearLayout llone, lltwo, llthree;
	private MyFragment myFragment;
	private RootFragment rootFragment;
	private android.support.v4.app.FragmentTransaction transaction;
	private TextView tvone, tvtwo, tvthree;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		//推送
		//PushAgent.getInstance(MainActivity.this).onAppStart();
		initDate();// 初始化
	}

	public static int dip2px(Context paramContext, float paramFloat) {
		return (int) (0.5F + paramFloat
				* paramContext.getResources().getDisplayMetrics().density);
	}

	private void initDate() {
		this.ivone = ((ImageView) findViewById(R.id.iv_one));
		this.ivtwo = ((ImageView) findViewById(R.id.iv_two));
		this.ivthree = ((ImageView) findViewById(R.id.iv_three));
		this.head = ((TextView) findViewById(R.id.tv_head));
		this.llone = ((LinearLayout) findViewById(R.id.ll_one));
		this.lltwo = ((LinearLayout) findViewById(R.id.ll_two));
		this.llthree = ((LinearLayout) findViewById(R.id.ll_three));
		this.llone.setOnClickListener(this);
		this.lltwo.setOnClickListener(this);
		this.llthree.setOnClickListener(this);
		setSelect(1);
		this.head.setText("主页");
	}

	private void setSelect(int i) {
		this.transaction = getSupportFragmentManager().beginTransaction();
		hideFragments();
		// resetTab();
		switch (i) {
			case 0:
				if (this.rootFragment == null) {

					this.rootFragment = new RootFragment();
					this.transaction.add(R.id.fl_main, this.rootFragment);
				}
				this.transaction.show(this.rootFragment);
				break;
			case 1:
				if (this.homeFragment == null) {
					this.homeFragment = new HomeFragment();

					this.transaction.add(R.id.fl_main, this.homeFragment);
				}
				this.transaction.show(this.homeFragment);
				break;
			case 2:
				if (this.myFragment == null) {
					this.myFragment = new MyFragment();
					this.transaction.add(R.id.fl_main, this.myFragment);
				}
				this.transaction.show(this.myFragment);
				break;
			default:
				break;
		}
		this.transaction.commit();
	}

	private void resetTab() {

	}

	private void hideFragments() {
		if (this.homeFragment != null)
			this.transaction.hide(this.homeFragment);
		if (this.myFragment != null)
			this.transaction.hide(this.myFragment);
		if (this.rootFragment != null)
			this.transaction.hide(this.rootFragment);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ll_one:
				setSelect(0);
				this.head.setText("ROOT构建");
				break;
			case R.id.ll_two:
				setSelect(1);
				this.head.setText("主页");
				break;
			case R.id.ll_three:
				setSelect(2);
				this.head.setText("用户详情");
				break;

			default:
				break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}


}
