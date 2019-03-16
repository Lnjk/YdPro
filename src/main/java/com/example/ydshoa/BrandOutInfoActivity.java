package com.example.ydshoa;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.bean.ExcelUtilsBrands;
import com.example.bean.TbSales;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BrandOutInfoActivity extends Activity {
    private SharedPreferences sp;
    public String session, info;
    private TextView head;
    private File file;
    private String fileName;
    private String zc_dh;
    private static String[] arr_price = { "名称", "净销售额", "销售毛利", "毛利率",
            "名称", "净销售额", "销售毛利", "毛利率","销售增长%","毛利增长%","毛利率差" };
    private ArrayList<ArrayList<String>> recordList;
    String name;
    List<TbSales> info_out_all;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_brand_out_info);
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        head = (TextView) findViewById(R.id.all_head);
        head.setText("品牌销售分析表转出");
        zc_dh = "品牌销售分析表";
        info_out_all = BrandFxActivity.info_out;
    }
    public void exportExcel_brand(View v) {
        file = new File(getSDPath() + "/Record");
        makeDir(file);
        ExcelUtilsBrands.initExcel(file.toString() + "/品牌销售分析表.xls", "时间", arr_price);
        fileName = getSDPath() + "/Record/品牌销售分析表.xls";
        ExcelUtilsBrands.writeObjListToExcel(getRecordData(), fileName,
                BrandOutInfoActivity.this);
        Log.e("LiNing", "=====" + getRecordData() + fileName);
        finish();
    }
    private ArrayList<ArrayList<String>> getRecordData() {
        recordList = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < info_out_all.size(); i++) {
            ArrayList<String> beanList = new ArrayList<String>();//放在此处避免数据一行显示
            beanList.add(info_out_all.get(i).getName());
            beanList.add(info_out_all.get(i).getJxsr());
            beanList.add(info_out_all.get(i).getMl());
            beanList.add(info_out_all.get(i).getMll());
            beanList.add(info_out_all.get(i).getName_db());
            beanList.add(info_out_all.get(i).getJxsr_db());
            beanList.add(info_out_all.get(i).getMl_db());
            beanList.add(info_out_all.get(i).getMll_db());
            Log.e("LiNiing","数据是==="+beanList+"/"+recordList);
            if (info_out_all.get(i).getName().toString().equals(info_out_all.get(i).getName_db())) {
                if(info_out_all.get(i).getJxsr_db().toString().equals("0.00")||info_out_all.get(i).getMl_db().equals("0.00")||info_out_all.get(i).getMll_db().equals("0.00")){
                    beanList.add("-100");
                    beanList.add("-100");
                    beanList.add("-1");
                }else {

                    //销售增长率
                    BigDecimal d_jxse_q = new BigDecimal(info_out_all.get(i).getJxsr());
                    BigDecimal d_jxse_h = new BigDecimal(info_out_all.get(i).getJxsr_db());
//                        BigDecimal c_jxse = d_jxse_h.subtract(d_jxse_q);
                    BigDecimal c_jxse = d_jxse_q.subtract(d_jxse_h);//(前-后)
                    Log.e("LiNing", "净销售额率--cc--" + c_jxse);
                    double v = c_jxse.divide(d_jxse_h, BigDecimal.ROUND_HALF_UP).doubleValue();
                    Log.e("LiNing", "净销售额率----" + v);
                    beanList.add(String.valueOf(v));
                    //毛利增长率
                    BigDecimal d_ml_q = new BigDecimal(info_out_all.get(i).getMl());
                    BigDecimal d_ml_h = new BigDecimal(info_out_all.get(i).getMl_db());
//                        BigDecimal c_ml = d_ml_h.subtract(d_ml_q);
                    BigDecimal c_ml = d_ml_q.subtract(d_ml_h);
                    Log.e("LiNing", "毛利增长率--cc--" + c_ml);
                    double v_ml = c_ml.divide(d_ml_h, BigDecimal.ROUND_HALF_UP).doubleValue();
                    Log.e("LiNing", "毛利增长率----" + v_ml);
                    beanList.add(String.valueOf(v_ml));
                    //毛利率差
                    String gpm_rp = info_out_all.get(i).getMll().replace("%", "");
                    double v1 = Double.parseDouble(gpm_rp);
//                        double v2 = v1 * 0.01;
                    BigDecimal b1 = new BigDecimal(Double.toString(v1));
                    double v3 = Double.parseDouble(info_out_all.get(i).getMll_db().toString().replace("%", ""));
//                        double v4 = v3 * 0.01;
                    BigDecimal b2 = new BigDecimal(Double.toString(v3));
//                        double v5 = b2.subtract(b1).doubleValue();
                    double v5 = b1.subtract(b2).doubleValue();
                    Log.e("LiNing", "毛利率差----" + v5);
                    beanList.add(String.valueOf(v5) + "%");
                }
            } else {
                if (info_out_all.get(i).getName().toString().equals("")) {
                    beanList.add("100");
                    beanList.add("100");
                    beanList.add("1");
                }
                if (info_out_all.get(i).getName_db().toString().equals("")) {
                    beanList.add("-100");
                    beanList.add("-100");
                    beanList.add("-1");
                }
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
