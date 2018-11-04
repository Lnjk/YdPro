package com.example.ydshoa;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.bean.ExcelUtils;
import com.example.bean.SaleMakeInfo;
import com.example.bean.SaleMakeInfo.Data;
import com.example.bean.Student;
import com.example.ydshoa.SalesQueryActivity.SalesInfoAdapter;
import com.google.gson.Gson;
//import com.umeng.message.PushAgent;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class OutInfoActivity extends Activity {
	private ArrayList<ArrayList<String>> recordList;
	private ArrayList<ArrayList<String>> recordList_all;
	private List<Student> students;
	// private static String[] title = { "账套名称", "销售品牌", "销售渠道", "销售部门", "销售业务",
	// "销售终端", "货品编号", "货品中类", "销售金额", "退货金额", "退返金额", "销售返点", "销售折让",
	// "净销售额", "销售成本", "退货成本", "净销成本", "销售毛利", "毛利率" };
	private TextView head;
	private File file;
	private String fileName;
	private SharedPreferences sp;
	public String session, info;
	private List<Data> data;
	List<String> ln = new ArrayList<String>();
	private String reportnos, reportnos_name, reportnos_stime, reportnos_etime,info_out_new;
	private List<Data> out_data;
	private static String[] h_strs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_out_info);
//		PushAgent.getInstance(OutInfoActivity.this).onAppStart();
		sp = getSharedPreferences("ydbg", 0);
		session = sp.getString("SESSION", "");
		head = (TextView) findViewById(R.id.all_head);
		head.setText("数据转出");
		GetInfoActivity();
		reportnos_name = getIntent().getStringExtra("RTNAME");
		reportnos = getIntent().getStringExtra("RT");
		reportnos_stime = getIntent().getStringExtra("Stime");
		reportnos_etime = getIntent().getStringExtra("Etime");
		info_out_new = getIntent().getStringExtra("out_new");
		Log.e("LiNing", "=====string====" + info_out_new);
		Log.e("LiNing", "======aaa====" + reportnos + reportnos_name
				+ reportnos_stime + reportnos_etime);

//		out_data = SalesQueryTwoActivity.out_one_no;
		out_data = SalesQueryTwoActivity.test;
		Log.e("LiNing", "=====string====" + out_data);

		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// out_data = SalesQueryTwoActivity.out_one_no;
		// Log.e("LiNing", "=====string====" + out_data);
		//
		// }
		// }).start();

		setHeads();
	}

	private void setHeads() {
		Log.e("LiNing", "======aaa====" + reportnos);
		// private static String[] title = {
		// "账套名称", "销售品牌", "销售渠道", "销售部门", "销售业务","销售终端", "货品编号", "货品中类",
		// ,销售金额,退货金额,销售返点,销售折让,净销售额,销售成本,退货成本,净销成本,销售毛利,毛利率};10
		if (reportnos.equals("SATG")) {
			h_strs = new String[11];// 1
			h_strs[0] = ln.get(0);
			h_strs[1] = ln.get(1);
			h_strs[2] = ln.get(2);
			h_strs[3] = ln.get(3);
			h_strs[4] = ln.get(4);
			h_strs[5] = ln.get(5);
			h_strs[6] = ln.get(6);
			h_strs[7] = ln.get(7);
			h_strs[8] = ln.get(8);
			h_strs[9] = ln.get(9);
			h_strs[10] = ln.get(10);
			Log.e("LiNing", "======h_strs====" + h_strs);
		}
		if (reportnos.equals("SATGP") || reportnos.equals("SATGC")
				|| reportnos.equals("SATGD") || reportnos.equals("SATGGC")
				|| reportnos.equals("SATGS") || reportnos.equals("SATGPPN")
				|| reportnos.equals("SATGPI")) {
			h_strs = new String[12];// 2
			h_strs[0] = ln.get(0);
			h_strs[1] = ln.get(1);
			h_strs[2] = ln.get(2);
			h_strs[3] = ln.get(3);
			h_strs[4] = ln.get(4);
			h_strs[5] = ln.get(5);
			h_strs[6] = ln.get(6);
			h_strs[7] = ln.get(7);
			h_strs[8] = ln.get(8);
			h_strs[9] = ln.get(9);
			h_strs[10] = ln.get(10);
			h_strs[11] = ln.get(11);
			Log.e("LiNing", "======h_strs====" + h_strs);
		}
		if (reportnos.equals("SATGPC") || reportnos.equals("SATGPD")
				|| reportnos.equals("SATGPS") || reportnos.equals("SATGPGC")
				|| reportnos.equals("SATGCD") || reportnos.equals("SATGCS")
				|| reportnos.equals("SATGCGC") || reportnos.equals("SATGDS")
				|| reportnos.equals("SATGDGC") || reportnos.equals("SATGSGC")
				|| reportnos.equals("SATGPIPN") || reportnos.equals("SATGPCPN")
				|| reportnos.equals("SATGPCI") || reportnos.equals("SATGPDPN")
				|| reportnos.equals("SATGPDI") || reportnos.equals("SATGPSPN")
				|| reportnos.equals("SATGPSI") || reportnos.equals("SATGPGCPN")
				|| reportnos.equals("SATGPGCI")) {
			h_strs = new String[13];// 3
			h_strs[0] = ln.get(0);
			h_strs[1] = ln.get(1);
			h_strs[2] = ln.get(2);
			h_strs[3] = ln.get(3);
			h_strs[4] = ln.get(4);
			h_strs[5] = ln.get(5);
			h_strs[6] = ln.get(6);
			h_strs[7] = ln.get(7);
			h_strs[8] = ln.get(8);
			h_strs[9] = ln.get(9);
			h_strs[10] = ln.get(10);
			h_strs[11] = ln.get(11);
			h_strs[12] = ln.get(12);
			Log.e("LiNing", "======h_strs====" + h_strs);
		}
		if (reportnos.equals("SATGPCIPN") || reportnos.equals("SATGPDIPN")
				|| reportnos.equals("SATGPSIPN")
				|| reportnos.equals("SATGPGCIPN")) {
			h_strs = new String[14];// 5
			h_strs[0] = ln.get(0);
			h_strs[1] = ln.get(1);
			h_strs[2] = ln.get(2);
			h_strs[3] = ln.get(3);
			h_strs[4] = ln.get(4);
			h_strs[5] = ln.get(5);
			h_strs[6] = ln.get(6);
			h_strs[7] = ln.get(7);
			h_strs[8] = ln.get(8);
			h_strs[9] = ln.get(9);
			h_strs[10] = ln.get(10);
			h_strs[11] = ln.get(11);
			h_strs[12] = ln.get(12);
			h_strs[13] = ln.get(13);
			// h_strs[14] = ln.get(14);
			Log.e("LiNing", "======h_strs====" + h_strs.toString());
		}
	}

	private void GetInfoActivity() {

		// info = getIntent().getStringExtra("ALLINJFO");
		info = getIntent().getStringExtra("INFO");
		Log.e("LiNing", "======aaa====" + info);
		// final SaleMakeInfo aDb1 = new Gson().fromJson(info,
		// SaleMakeInfo.class);
		// String head = aDb1.getHead();
		if (info != null) {
			String[] db_spls = info.split(",");

			Log.e("LiNing", "=====" + info.split(","));
			for (int i = 0; i < db_spls.length; i++) {
				String A = db_spls[i];
				Log.e("LiNing", "===A==" + A);
				ln.add(A);
			}
			Log.e("LiNing", "==ln===" + ln);
		}

	}

	public void exportExcel(View view) {
		file = new File(getSDPath() + "/Record");
		makeDir(file);
		// +reportnos_name+"时间:"+reportnos_stime+"---"+reportnos_etime
		String all_info = reportnos_name + "/" + reportnos_stime + "/"
				+ reportnos_etime;
		ExcelUtils.initExcel(file.toString() + "/销售统计表.xls", all_info, h_strs);
		// fileName = getSDPath() + reportnos_name+"时间:"+reportnos_stime
		// +"---"+reportnos_etime+"/Record/销售统计表.xls";
		fileName = getSDPath() + "/Record/销售统计表.xls";
		ExcelUtils.writeObjListToExcel(getRecordData(), fileName,
				OutInfoActivity.this);
		Log.e("LiNing", "=====" + getRecordData() + fileName);

		finish();
	}

	private ArrayList<ArrayList<String>> getRecordData() {
		recordList = new ArrayList<ArrayList<String>>();

		for (int i = 0; i < out_data.size(); i++) {
			ArrayList<String> beanList = new ArrayList<String>();
			if (ln.contains("账套名称")) {
				beanList.add(out_data.get(i).getAffiliateNo());
			}
			if (ln.contains("销售品牌")) {
				beanList.add(out_data.get(i).getBrandName());
//				Log.e("LiNing", "aaaaaaaaaa==" + out_data.get(i).getBrandName());
			}
			// else {
			// beanList.add("");
			// }
			if (ln.contains("销售渠道")) {
				beanList.add(out_data.get(i).getSalesChannelName());
			}
			if (ln.contains("销售部门")) {
				beanList.add(out_data.get(i).getSalesDepartmentName());
			}
			if (ln.contains("销售业务")) {
				beanList.add(out_data.get(i).getSellingOperationName());
			}

			if (ln.contains("销售终端")) {
				beanList.add(out_data.get(i).getSalesTerminalName());
			}
			if (ln.contains("商品编号")) {
				beanList.add(out_data.get(i).getPrdNo());
			}
			if (ln.contains("商品中类")) {
				beanList.add(out_data.get(i).getPrdIndx());
			}
//			try {
//				JSONArray arr = new JSONArray(info_out_new);
//				int size = arr.length();
//				for (int j = 0; j < size; j++) {
//					JSONObject jsonObject = arr.getJSONObject(i);
//					if (ln.contains("销售金额")) {
//						beanList.add(jsonObject.get("saAmtn").toString());
//					}
//					if (ln.contains("退货金额")) {
//						beanList.add(jsonObject.get("sbAmtn").toString());
//					}
//					if (ln.contains("退返金额")) {
//						beanList.add(jsonObject.get("sbsAmatn").toString());
//					}
//					if (ln.contains("销售返点")) {
//						beanList.add(jsonObject.get("ssAmtn").toString());
//					}
//					if (ln.contains("销售折让")) {
//						beanList.add(jsonObject.get("sdAmtn").toString());
//					}
//					if (ln.contains("净销售额")) {
//						beanList.add(jsonObject.get("snAmtn").toString());
//					}
//					if (ln.contains("销售成本")) {
//						beanList.add(jsonObject.get("saCst").toString());
//					}
//					if (ln.contains("退货成本")) {
//						beanList.add(jsonObject.get("sbCst").toString());
//					}
//					if (ln.contains("净销成本")) {
//						beanList.add(jsonObject.get("snCst").toString());
//					}
//					if (ln.contains("销售毛利")) {
//						beanList.add(jsonObject.get("gP").toString());
//					}
//					if (ln.contains("毛利率")) {
//						beanList.add(jsonObject.get("gPM").toString());
//					}
//				}
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			if (ln.contains("销售金额")) {
				beanList.add(out_data.get(i).getSaAmtn());
			}
			if (ln.contains("退货金额")) {
				beanList.add(out_data.get(i).getSbAmtn());
			}
			if (ln.contains("退返金额")) {
				beanList.add(out_data.get(i).getSbsAmatn());
			}
			if (ln.contains("销售返点")) {
				beanList.add(out_data.get(i).getSsAmtn());
			}
			if (ln.contains("销售折让")) {
				beanList.add(out_data.get(i).getSdAmtn());
			}
			if (ln.contains("净销售额")) {
				beanList.add(out_data.get(i).getSnAmtn());
			}
			if (ln.contains("销售成本")) {
				beanList.add(out_data.get(i).getSaCst());
			}
			if (ln.contains("退货成本")) {
				beanList.add(out_data.get(i).getSbCst());
			}
			if (ln.contains("净销成本")) {
				beanList.add(out_data.get(i).getSnCst());
			}
			if (ln.contains("销售毛利")) {
				beanList.add(out_data.get(i).getgP());
			}
			if (ln.contains("毛利率")) {
				beanList.add(out_data.get(i).getgPM());
			}
			recordList.add(beanList);
		}
		return recordList;
	}

	private String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();
		}
		String dir = sdDir.toString();
		return dir;
	}

	public void makeDir(File dir) {
		if (!dir.getParentFile().exists()) {
			makeDir(dir.getParentFile());
		}
		dir.mkdir();
	}
	public void allback(View v) {
		finish();
	}
}
