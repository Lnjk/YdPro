package com.example.ydshoa;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.PriceLoadInfo;
import com.google.gson.Gson;

import org.kymjs.kjframe.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class FormActivity extends Activity {
    private Button tz;
    private TextView path_name;
    private int flag=1;
    private List<PriceLoadInfo.Prdt> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        path_name= (TextView) findViewById(R.id.tv_path);
        tz = (Button) findViewById(R.id.btn_goto);
        tz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                File file = new File(String.valueOf(Environment.getExternalStorageDirectory()));
//                //获取父目录
//                File parentFlie = new File(file.getParent());
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setDataAndType(Uri.fromFile(parentFlie), "*/*");
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                startActivity(intent);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                try {
                    startActivityForResult( Intent.createChooser(intent, "Select a File to Upload"), 1);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(FormActivity.this, "Please install a File Manager.",  Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){
            return;
        }
        switch (requestCode){
            case 1 :
                if (resultCode == RESULT_OK) {
                    Uri selectedMediaUri = data.getData();
                    String path = com.example.bean.FileUtils.getPath(FormActivity.this, selectedMediaUri);
                    path_name.setText(path);
                    //填入listview
                    datas = new ArrayList<>();
                    Workbook workbook = null;
                    try {
                        workbook = Workbook.getWorkbook(new File(path));
                        Sheet sheet = workbook.getSheet(0);
                        int rows = sheet.getRows();
                        int columns = sheet.getColumns();
                        //遍历excel文件的每行每列
                        for (int i = 0; i < rows; i++) {
                            //遍历行
                            List<String> li = new ArrayList<>();
                            for (int j = 0; j < columns; j++) {
                                Cell cell = sheet.getCell(j, i);
                                String result = cell.getContents();
                                if (i != 0) {
                                    li.add(result);
                                }
                            }
                            if (li.size() > 0) {
                                datas.add(new PriceLoadInfo.Prdt(li.get(0), li.get(1), li.get(2), Double.parseDouble(li.get(3)), Double.parseDouble(li.get(4)),
                                        Double.parseDouble(li.get(5)), li.get(6), li.get(7), li.get(8), li.get(9)));
                            }
                            li = null;
                        }
                        Gson gson = new Gson();
                        String s = gson.toJson(datas);
                        Log.e("LiNing","转入的数据是"+s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                break;
            default:
                break;
        }
    }
}
