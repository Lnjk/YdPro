package com.example.ydshoa;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.bean.ArpInfos;
import com.example.bean.ExcelUtilsArpInfos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ArpOutActivity extends Activity {
    private ArrayList<ArrayList<String>> recordList;
    private ArrayList<ArrayList<String>> recordList_all;
    private TextView head;
    private File file;
    private String fileName;
    private SharedPreferences sp;
    public String session, info;
    List<String> ln = new ArrayList<String>();
    private String reportnos, reportnos_name, reportnos_stime, reportnos_etime;
    private static String[] h_strs;
    private List<ArpInfos.Data> out_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_arp_out);
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        head = (TextView) findViewById(R.id.all_head);
        head.setText("数据转出");
        GetInfoActivity();
        reportnos_name = getIntent().getStringExtra("RTNAME");
        reportnos = getIntent().getStringExtra("RT");
        reportnos_stime = getIntent().getStringExtra("Stime");
        reportnos_etime = getIntent().getStringExtra("Etime");
        Log.e("LiNing", "======aaa====" + reportnos + reportnos_name
                + reportnos_stime + reportnos_etime);

        out_data = SalesQueryArpActivity.out_infos_arp;
        Log.e("LiNing", "=====string====" + out_data);
        setHeads();
    }
    private void setHeads() {
        Log.e("LiNing", "======aaa====" + reportnos);
        // private static String[] title = {
        // "账套名称", "销售品牌", "销售渠道", "销售部门", "销售业务","销售终端", "货品编号", "货品中类",
        // ,销售金额,退货金额,销售返点,销售折让,净销售额,销售成本,退货成本,净销成本,销售毛利,毛利率};10
        if (reportnos.equals("ArpTG")) {
            h_strs = new String[3];// 1
            h_strs[0] = ln.get(0);
            h_strs[1] = ln.get(1);
            h_strs[2] = ln.get(2);
            Log.e("LiNing", "======h_strs====" + h_strs);
        }
        if (reportnos.equals("ArpTGP") || reportnos.equals("ArpTGC")
                || reportnos.equals("ArpTGD") || reportnos.equals("ArpTGGC")
                || reportnos.equals("ArpTGS") ) {
            h_strs = new String[4];// 2
            h_strs[0] = ln.get(0);
            h_strs[1] = ln.get(1);
            h_strs[2] = ln.get(2);
            h_strs[3] = ln.get(3);
            Log.e("LiNing", "======h_strs====" + h_strs);
        }
        if (reportnos.equals("ArpTGPC") || reportnos.equals("ArpTGPD")
                || reportnos.equals("ArpTGPS") || reportnos.equals("ArpTGPGC")
               ) {
            h_strs = new String[5];// 3
            h_strs[0] = ln.get(0);
            h_strs[1] = ln.get(1);
            h_strs[2] = ln.get(2);
            h_strs[3] = ln.get(3);
            h_strs[4] = ln.get(4);
            Log.e("LiNing", "======h_strs====" + h_strs);
        }
        if (reportnos.equals("ArpTGPCGC") || reportnos.equals("ArpTGPDGC")
                || reportnos.equals("ArpTGPSGC")
                ) {
            h_strs = new String[6];// 5
            h_strs[0] = ln.get(0);
            h_strs[1] = ln.get(1);
            h_strs[2] = ln.get(2);
            h_strs[3] = ln.get(3);
            h_strs[4] = ln.get(4);
            h_strs[5] = ln.get(5);
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
        ExcelUtilsArpInfos.initExcel(file.toString() + "/应收统计表.xls", all_info, h_strs);
        // fileName = getSDPath() + reportnos_name+"时间:"+reportnos_stime
        // +"---"+reportnos_etime+"/Record/销售统计表.xls";
        fileName = getSDPath() + "/Record/应收统计表.xls";
        ExcelUtilsArpInfos.writeObjListToExcel(getRecordData(), fileName,
                ArpOutActivity.this);
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
            if (ln.contains("终端名称")) {
                beanList.add(out_data.get(i).getSalesTerminalName());
            }
            if (ln.contains("单位名称")) {
                beanList.add(out_data.get(i).getCompDepName());
            }
            if (ln.contains("渠道名称")) {
                beanList.add(out_data.get(i).getSalesChannelName());
            }
            if (ln.contains("业务名称")) {
                beanList.add(out_data.get(i).getSellingOperationName());
            }
            if (ln.contains("部门名称")) {
                beanList.add(out_data.get(i).getSalesDepartmentName());
            }

            if (ln.contains("应收年月")) {
                beanList.add(out_data.get(i).getBiln_DD());
            }
            if (ln.contains("未结金额")) {
                beanList.add(out_data.get(i).getSAmtn_ARPJ());
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
