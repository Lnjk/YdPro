package com.example.ydshoa;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bean.MyDBHelper;
//import com.umeng.message.PushAgent;

public class RegisterActivity extends Activity {
	private MyDBHelper dbHelper;
	private EditText ip;
	private EditText name;
	private EditText psd;
	private Button regist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
//		PushAgent.getInstance(RegisterActivity.this).onAppStart();
		this.dbHelper = new MyDBHelper(this, "UserStoreS.db", null, 1);
		this.name = ((EditText)findViewById(R.id.phone_regist));
//		    this.name.setTypeface(Typeface.createFromAsset(getAssets(), "font/youhua.ttf"));
		this.psd = ((EditText)findViewById(R.id.psd_regist));
//		    this.psd.setTypeface(Typeface.createFromAsset(getAssets(), "font/youhua.ttf"));
		this.ip = ((EditText)findViewById(R.id.ip_regist));
//		    this.ip.setTypeface(Typeface.createFromAsset(getAssets(), "font/youhua.ttf"));
		this.regist = ((Button)findViewById(R.id.regist));
//		    this.regist.setTypeface(Typeface.createFromAsset(getAssets(), "font/youhua.ttf"));
		regist.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (RegisterActivity.this.isNew(RegisterActivity.this.name.getText().toString())){

					Toast.makeText(RegisterActivity.this, "该用户名已被注册，注册失败", Toast.LENGTH_LONG).show();
				}
				if ((TextUtils.isEmpty(RegisterActivity.this.name.getText().toString())) || (TextUtils.isEmpty(RegisterActivity.this.psd.getText().toString())) || (TextUtils.isEmpty(RegisterActivity.this.ip.getText().toString())))
				{
					Toast.makeText(RegisterActivity.this, "请输入信息！", Toast.LENGTH_LONG).show();
				}
				if(RegisterActivity.this.insert(RegisterActivity.this.name.getText().toString(), RegisterActivity.this.psd.getText().toString(), RegisterActivity.this.ip.getText().toString())){

					Toast.makeText(RegisterActivity.this, "插入数据表成功", Toast.LENGTH_LONG).show();
					RegisterActivity.this.startActivity(new Intent(RegisterActivity.this, LoginMainActivity.class));
					RegisterActivity.this.finish();
				}
			}
		});
	}
	public boolean insert(String paramString1, String paramString2, String paramString3)
	{
		SQLiteDatabase localSQLiteDatabase = this.dbHelper.getReadableDatabase();
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("name", paramString1);
		localContentValues.put("password", paramString2);
		localContentValues.put("IP", paramString3);
		localSQLiteDatabase.insert("userData", null, localContentValues);
		localSQLiteDatabase.close();
		return true;
	}

	public boolean isNew(String paramString)
	{
////		Cursor localCursor = this.dbHelper.getWritableDatabase().rawQuery("Select * from userData where name=?", new String[] { paramString });
//		if (localCursor.getCount() > 0)
//		{
//			localCursor.close();
//			return true;
//		}
//		localCursor.close();
		return false;
	}
}
