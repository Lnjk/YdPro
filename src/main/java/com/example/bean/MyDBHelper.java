package com.example.bean;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
	 public static final String CREATE_USERDATA = "create table userData(id integer primary key autoincrement,name text,password text,ip text)";
	  public static final String Id_SQL = "create table idSql(id integer primary key autoincrement,get_Id text)";
	  private Context mContext;
	  public MyDBHelper(Context paramContext, String paramString, SQLiteDatabase.CursorFactory paramCursorFactory, int paramInt)
	  {
	    super(paramContext, paramString, paramCursorFactory, paramInt);
	    this.mContext = paramContext;
	  }

	  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
	  {
	    paramSQLiteDatabase.execSQL("create table userData(id integer primary key autoincrement,name text,password text,ip text)");
	    paramSQLiteDatabase.execSQL("create table idSql(id integer primary key autoincrement,get_Id text)");
	  }

	  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
	  {
	  }

}
