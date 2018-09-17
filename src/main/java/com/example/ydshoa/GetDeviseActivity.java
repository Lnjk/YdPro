package com.example.ydshoa;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

//import com.umeng.message.PushAgent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class GetDeviseActivity extends Activity {

	private TextView devise,head;
	private String iMEI;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_get_devise);
		//推送
//		PushAgent.getInstance(GetDeviseActivity.this).onAppStart();
		devise = (TextView) findViewById(R.id.tv_devise);
		iMEI = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
		devise.setText(iMEI);
		head = (TextView) findViewById(R.id.all_head);
		head.setText("设备码");
	}

	public void devise_back(View v) {
		finish();
	}
	public void allback(View v){
		finish();
	}
	public void getview(View v){
		screenshot();
	}

	private void screenshot() {
		// 获取屏幕
		View dView = getWindow().getDecorView();
		dView.setDrawingCacheEnabled(true);
		dView.buildDrawingCache();
		Bitmap bmp = dView.getDrawingCache();
		if (bmp != null)
		{

			try {
				// 获取内置SD卡路径①
//				String sdCardPath = Environment.getExternalStorageDirectory().getPath();
				//-------------------------------
				// 获取内置SD卡路径②
				File sdCardPath = null;
				//判断SDCard是否存在
				boolean sdcardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
				Log.e("LiNing","---------"+sdcardExist);
				if(sdcardExist){
					sdCardPath = Environment.getExternalStorageDirectory();
				}else{
					Toast.makeText(GetDeviseActivity.this,"无sd卡",Toast.LENGTH_LONG).show();
				}
				// 图片文件路径
				String filePath = sdCardPath + File.separator + "screenshot.png";
				File file = new File(filePath);
				FileOutputStream os = new FileOutputStream(file);
				bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
				os.flush();
				os.close();
				finish();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.activity_get_devise, menu);
//		return true;
//	}
}
