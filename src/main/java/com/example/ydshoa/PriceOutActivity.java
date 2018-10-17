package com.example.ydshoa;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.bean.ExcelUtilsPrice;
import com.example.bean.PriceLoadInfo;
import com.example.bean.PriceMx;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PriceOutActivity extends Activity {
    private SharedPreferences sp;
    public String session, info;
    private TextView head;
    private List<PriceLoadInfo.Prdt> info_add;
    private List<PriceMx.PrdtUp> info_query;
    private List<HashMap<String, Object>> info_zr;
    private File file;
    private String fileName;
    private String zc_dh;
    private static String[] arr_price = { "序号", "货品编号", "货品名称", "单位成本", "统一定价",
     "最低售价", "政策定价", "销售折扣", "执行情况", "备注信息" };
    private ArrayList<ArrayList<String>> recordList;
    private String zc_id,zc_chcek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_price_out);
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        head = (TextView) findViewById(R.id.all_head);
        head.setText("政策转出");
        zc_chcek = getIntent().getStringExtra("ZC_CHECK");
        zc_id = getIntent().getStringExtra("ZC_ID");
        zc_dh = getIntent().getStringExtra("ZC_DH");
        Log.e("LiNing", "=====" + zc_id + "------"+zc_dh);
        if(zc_id.equals("1")){//新增
            info_add = PriceActivity.info_add;
            Log.e("LiNing", "=xz==转出数据==" + info_add );
        }
            if(zc_id.equals("2")){//修改
                if(zc_chcek.equals("true")) {
                    info_add = PriceActivity.info_add;
                    Log.e("LiNing", "==jz=转出数据==" + info_add );
                }else{

                    info_query = PriceActivity.info_query;
                    Log.e("LiNing", "===转出数据==" +info_query);
                }
        }
            if(zc_id.equals("4")){//转入的数据
                if(zc_chcek.equals("true")) {
                    info_add = PriceActivity.info_add;
                }else {
                    info_zr = PriceActivity.info_zr;
                }
                Log.e("LiNing", "=zr==转出数据==" + info_zr);
        }
    }

    public void exportExcel_price(View v) {
        file = new File(getSDPath() + "/Record");
        makeDir(file);
        ExcelUtilsPrice.initExcel(file.toString() + "/终端定价政策统计表.xlsx", zc_dh, arr_price);
        fileName = getSDPath() + "/Record/终端定价政策统计表.xlsx";
        ExcelUtilsPrice.writeObjListToExcel(getRecordData(), fileName,
                PriceOutActivity.this);
        Log.e("LiNing", "=====" + getRecordData() + fileName);

        if(zc_id.equals("1")){

            info_add.clear();
            finish();
        }
        if(zc_id.equals("2")){

            info_query.clear();
            finish();
        }
        if(zc_id.equals("4")){

            info_zr.clear();
            finish();
        }


    }

    private ArrayList<ArrayList<String>> getRecordData() {
        recordList = new ArrayList<ArrayList<String>>();
        if(zc_id.equals("1")){

            for (int i = 0; i < info_add.size(); i++) {
                ArrayList<String> beanList = new ArrayList<String>();//放在此处避免数据一行显示
//                beanList.add("i");
                String xh = info_add.get(i).getXH();
                Integer integer = Integer.valueOf(xh);
                int i1 = integer + 1;
                String Xh_new=""+Integer.parseInt(xh)+1;
                Log.e("LiNing","======="+xh+"======="+Xh_new+"======="+i1);
                beanList.add(""+i1);
//                beanList.add(info_add.get(i).getXH());
                beanList.add(info_add.get(i).getPRD_NO());
                beanList.add(info_add.get(i).getNAME());
                beanList.add(""+info_add.get(i).getUP_SAL());
                beanList.add(""+info_add.get(i).getUPR());
                beanList.add(""+info_add.get(i).getUP_MIN());
                beanList.add(info_add.get(i).getZC_DJ());
                beanList.add(info_add.get(i).getZC_ZK());
                beanList.add(info_add.get(i).getZC_ZXQK());
                beanList.add(info_add.get(i).getZC_BZXX());
//                recordList.add(beanList);
                Log.e("LiNiing","数据是=序号=="+i+"/"+info_add.get(i).getXH());
                Log.e("LiNiing","数据是==="+i+"/"+info_add.get(i).getPRD_NO()+"----"+info_add.get(i).getUPR());
                Log.e("LiNiing","数据是==="+beanList+"/"+recordList);

                recordList.add(beanList);
            }
        }
        if(zc_id.equals("2")){
            if(zc_chcek.equals("true")){
                for (int i = 0; i < info_add.size(); i++) {
                    ArrayList<String> beanList = new ArrayList<String>();
//                    beanList.add("i");
                    String xh = info_add.get(i).getXH();
                    Integer integer = Integer.valueOf(xh);
                    int i1 = integer + 1;
                    String Xh_new=""+Integer.parseInt(xh)+1;
                    Log.e("LiNing","======="+xh+"======="+Xh_new+"======="+i1);
                    beanList.add(""+i1);
//                beanList.add(info_add.get(i).getXH());
                    beanList.add(info_add.get(i).getPRD_NO());
                    beanList.add(info_add.get(i).getNAME());
                    beanList.add(""+info_add.get(i).getUP_SAL());
                    beanList.add(""+info_add.get(i).getUPR());
                    beanList.add(""+info_add.get(i).getUP_MIN());
                    beanList.add(info_add.get(i).getZC_DJ());
                    beanList.add(info_add.get(i).getZC_ZK());
                    beanList.add(info_add.get(i).getZC_ZXQK());
                    beanList.add(info_add.get(i).getZC_BZXX());
//                recordList.add(beanList);
                    Log.e("LiNiing","数据是=序号=="+i+"/"+info_add.get(i).getXH());
                    Log.e("LiNiing","数据是==="+i+"/"+info_add.get(i).getPRD_NO()+"----"+info_add.get(i).getUPR());
                    Log.e("LiNiing","数据是==="+beanList+"/"+recordList);

                    recordList.add(beanList);
                }
            }else{


            for (int i = 0; i < info_query.size(); i++) {
                ArrayList<String> beanList = new ArrayList<String>();
//                beanList.add("i");
                int itm = info_query.get(i).getITM();
               int itm_int=itm+1;
                beanList.add(""+itm_int);
                Log.e("LiNiing","数据是==="+itm+"/"+itm_int);
//                beanList.add(""+info_query.get(i).getITM());
                beanList.add(info_query.get(i).getPrice_Id());
                beanList.add(info_query.get(i).getPrdNo());
                beanList.add(info_query.get(i).getCst_Up());
                beanList.add(info_query.get(i).getUPR());
                beanList.add(info_query.get(i).getMIN_UP());
                beanList.add(info_query.get(i).getPrdUp());
                beanList.add(info_query.get(i).getDis_CNT());
                beanList.add(info_query.get(i).getYN());
                beanList.add(info_query.get(i).getREM());
//                recordList.add(beanList);
                Log.e("LiNiing","数据是=序号=="+i+"/"+info_query.get(i).getITM());
                Log.e("LiNiing","数据是==="+i+"/"+info_query.get(i).getPrdNo()+"----"+info_query.get(i).getUPR());
                Log.e("LiNiing","数据是==="+beanList+"/"+recordList);
                recordList.add(beanList);
            }
            }
        }
        if(zc_id.equals("4")){
            for (int i = 0; i < info_zr.size(); i++) {
                ArrayList<String> beanList = new ArrayList<String>();
                String xh = info_zr.get(i).get("转入序号").toString();
                String Xh_new=""+Integer.parseInt(xh)+1;
                beanList.add(Xh_new);
//                beanList.add(info_zr.get(i).get("转入序号").toString());
                beanList.add(info_zr.get(i).get("转入货品编号").toString());
                beanList.add(info_zr.get(i).get("转入货品名称").toString());
//                beanList.add("null");
                beanList.add(info_zr.get(i).get("转入单位成本").toString());
                beanList.add(info_zr.get(i).get("转入统一定价").toString());
                beanList.add(info_zr.get(i).get("转入最低售价").toString());
                beanList.add(info_zr.get(i).get("转入政策定价").toString());
                beanList.add(info_zr.get(i).get("转入销售折扣").toString());
                beanList.add(info_zr.get(i).get("转入执行情况").toString());
                beanList.add(info_zr.get(i).get("转入备注信息").toString());
//                recordList.add(beanList);
                Log.e("LiNiing","数据是==="+beanList+"/"+recordList);
                recordList.add(beanList);
            }
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
