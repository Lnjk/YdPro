package com.example.bean;

import android.os.Environment;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * Created by ${宁.金珂} on 2018/7/16.
 */

public class ExcelToList {
    //将excel文件导入到内存中
    private static List<PriceLoadInfo.Prdt> datas;

    public static String ImportExcelData() {
        datas = new ArrayList<>();
        Workbook workbook = null;
        String fileName = "Record/终端定价政策统计表.xls";
        try {
            workbook = Workbook.getWorkbook(new File(Environment.getExternalStorageDirectory() + "/" + fileName));
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
            return gson.toJson(datas);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return "error";
    }
}
