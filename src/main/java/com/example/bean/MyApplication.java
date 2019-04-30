package com.example.bean;

import com.example.bean.CrashHelper.CrashHandler;
//import com.umeng.message.IUmengRegisterCallback;
//import com.umeng.message.PushAgent;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyApplication extends Application {
	public static Context context;
	public static Handler handler;
	public static Thread mainThread;
	public static int mainThreadId;

	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		handler = new Handler();
		mainThread = Thread.currentThread();

//		CrashHelper.init(new CrashHandler() {
//
//			@Override
//			public void uncaughtException(Thread t, Throwable e) {
//				// TODO Auto-generated method stub
//				Log.e("LiNing", "此处有bug");
//				e.printStackTrace();
//			}
//		});



		//友盟推送（初始化）

//		PushAgent mPushAgent = PushAgent.getInstance(this);
//
//		//注册推送服务，每次调用register方法都会回调该接口
//		mPushAgent.register(new IUmengRegisterCallback() {
//			@Override
//			public void onSuccess(String deviceToken) {
//				//注册成功会返回device token
//				Log.e("LiNing","测试设备===="+deviceToken);
//			}
//			@Override
//			public void onFailure(String s, String s1) {
//			}
//		});

//		时间转换
//		SimpleDateFormat sf1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		try {
//			Date parse = sf1.parse(mf_monList_all.getPoints_Date().toString());
//			String format = new SimpleDateFormat("yyyy-MM-dd").format(parse);
//			Log.e("LiNing","时间====xin====="+format);
//			holder.jf_qty_jfrq.setText(format);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
	}
	
}
