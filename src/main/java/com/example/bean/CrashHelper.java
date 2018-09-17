package com.example.bean;

import android.os.Handler;
import android.os.Looper;

public class CrashHelper {
	 private CrashHandler mCrashHandler;

	    private CrashHelper() {

	    }

	    private static CrashHelper getInstance() {
	        return CrashHelperHolder.crashHandler;
	    }


	    private static class CrashHelperHolder {
	        private static final CrashHelper crashHandler = new CrashHelper();
	    }

	    public static void init(CrashHandler crashHandler) {
	        getInstance().setCrashHandler(crashHandler);
	    }

	    private void setCrashHandler(CrashHandler crashHandler) {

	        mCrashHandler = crashHandler;
	        new Handler(Looper.getMainLooper()).post(new Runnable() {
	            @Override
	            public void run() {
	                for (; ; ) {
	                    try {
	                        Looper.loop();
	                    } catch (Throwable e) {
	                        if (mCrashHandler != null) {//捕获异常处理
	                            mCrashHandler.uncaughtException(Looper.getMainLooper().getThread(), e);
	                        }
	                    }
	                }
	            }
	        });

	        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
	            @Override
	            public void uncaughtException(Thread t, Throwable e) {
	                if (mCrashHandler != null) {//捕获异常处理
	                    mCrashHandler.uncaughtException(t, e);
	                }
	            }
	        });

	    }

	    public interface CrashHandler {
	        void uncaughtException(Thread t, Throwable e);
	    }
}
