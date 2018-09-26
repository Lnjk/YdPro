package com.example.ydshoa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.LocalAdapter;
import com.example.bean.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalInfosActivity extends Activity implements View.OnClickListener {
    private Context context;
    private TextView head;
    private SharedPreferences sp;
    private String session,extraFlag,querryID, name;
    private ListView lv;
    private Button allCheck, rever, ok, no;
    private ArrayList<HashMap<String, Object>> dList_idTn;
    private LocalAdapter adapter_all;
    ArrayList<String> listStr, listIDs;
    ArrayList<HashMap<String, Object>> dList;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_local_infos);
        context = LocalInfosActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        initView();
    }

    private void initView() {

        head = (TextView) findViewById(R.id.all_head);
        // head.setText("条件信息");// 最后根据flag判断表头
        lv = (ListView) findViewById(R.id.lv_codition_local);
        allCheck = (Button) findViewById(R.id.c_selectall_local);
        rever = (Button) findViewById(R.id.c_inverseselect_local);
        ok = (Button) findViewById(R.id.c_btnok_local);
        no = (Button) findViewById(R.id.c_cancel_local);
        allCheck.setOnClickListener(this);
        rever.setOnClickListener(this);
        ok.setOnClickListener(this);
        no.setOnClickListener(this);
        getFlag();
        findViewById(R.id.imageButton1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }


        });
    }

    private void getFlag() {
        dList = new ArrayList();
        extraFlag = getIntent().getStringExtra("flag");
        if (extraFlag.equals("1")) {

            head.setText("货品大类");
            HashMap<String, Object> item = new HashMap<String, Object>();
            item.put("序号", 1);
            item.put("名称", "商品");
            HashMap<String, Object> item1 = new HashMap<String, Object>();
            item1.put("序号", 2);
            item1.put("名称", "制成品");
            HashMap<String, Object> item2= new HashMap<String, Object>();
            item2.put("序号", 3);
            item2.put("名称", "半成品");
            HashMap<String, Object> item3 = new HashMap<String, Object>();
            item3.put("序号", 4);
            item3.put("名称", "原料");
            HashMap<String, Object> item4 = new HashMap<String, Object>();
            item4.put("序号", 5);
            item4.put("名称", "物料");
            HashMap<String, Object> item5 = new HashMap<String, Object>();
            item5.put("序号", 6);
            item5.put("名称", "下脚品");
            HashMap<String, Object> item6 = new HashMap<String, Object>();
            item6.put("序号", 7);
            item6.put("名称", "包装物");
            dList.add(item);
            dList.add(item1);
            dList.add(item2);
            dList.add(item3);
            dList.add(item4);
            dList.add(item5);
            dList.add(item6);
            showCheckBoxListView();
        }
        if (extraFlag.equals("2")) {
            head.setText("货品等级");
            HashMap<String, Object> item = new HashMap<String, Object>();
            item.put("序号", 1);
            item.put("名称", "特级");
            HashMap<String, Object> item1 = new HashMap<String, Object>();
            item1.put("序号", 2);
            item1.put("名称", "优等");
            HashMap<String, Object> item2= new HashMap<String, Object>();
            item2.put("序号", 3);
            item2.put("名称", "一级");
            HashMap<String, Object> item3 = new HashMap<String, Object>();
            item3.put("序号", 4);
            item3.put("名称", "二级");
            HashMap<String, Object> item4 = new HashMap<String, Object>();
            item4.put("序号", 5);
            item4.put("名称", "三级");
            HashMap<String, Object> item5 = new HashMap<String, Object>();
            item5.put("序号", 6);
            item5.put("名称", "合格");
            HashMap<String, Object> item6 = new HashMap<String, Object>();
            item6.put("序号", 7);
            item6.put("名称", "不合格");
            dList.add(item);
            dList.add(item1);
            dList.add(item2);
            dList.add(item3);
            dList.add(item4);
            dList.add(item5);
            dList.add(item6);
            showCheckBoxListView();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.c_selectall_local:
                    for (int i = 0; i < dList.size(); i++) {
                        LocalAdapter.isSelected.put(i, true);
                        listIDs.add(dList.get(i).get("序号").toString());
                        listStr.add(dList.get(i).get("名称").toString());
                    }
                    adapter_all.notifyDataSetChanged();// 注意这一句必须加上，否则checkbox无法正常更新状态

                Log.e("LiNing", "listStr========" + listStr);
                break;
            case R.id.c_inverseselect_local:
                    for (int i = 0; i < dList.size(); i++) {
                        if (LocalAdapter.isSelected.get(i) == false) {
                            LocalAdapter.isSelected.put(i, true);
                            // listStr.add(arrays[i].toString());
                            listIDs.add(dList.get(i).get("序号").toString());
                            listStr.add(dList.get(i).get("名称").toString());
                        } else {
                            LocalAdapter.isSelected.put(i, false);
                            // listStr.remove(arrays[i].toString());
                            listIDs.remove(dList.get(i).get("序号").toString());
                            listStr.remove(dList.get(i).get("名称").toString());
                        }
                    }
                    adapter_all.notifyDataSetChanged();

                break;
            case R.id.c_btnok_local:
                 
                    if (dList == null) {
                        Toast.makeText(context, "数据库已断开", Toast.LENGTH_SHORT)
                                .show();
                        finish();
                    } else {
                        Log.e("LiNing", "111111111" + listStr.size());

                        if (listStr.size() != 0) {
                            String str = "";
                            for (String name : listStr) {
                                str += name + ",";
                            }
                            String sub_db_query = str
                                    .substring(0, str.length() - 1);
                            String strid = "";
                            for (String id : listIDs) {
                                strid += id + ",";
                            }

                            String strIds = strid.substring(0, strid.length() - 1);
                            Log.e("LiNing", "222222===" + str.toString());
                            Intent localIntent = getIntent();
                            localIntent.putExtra("data_return", sub_db_query);
                            localIntent.putExtra("data_return_ids", strIds);
                            setResult(1, localIntent);
                            finish();
                        } else if (listStr.size() == 0) {
                            Toast.makeText(context, "数据为空", Toast.LENGTH_SHORT)
                                    .show();
                            Intent localIntent = getIntent();
                            localIntent.putExtra("data_return", "");
                            localIntent.putExtra("data_return_ids", "");
                            setResult(1, localIntent);
                            finish();
                        } else {
                            Toast.makeText(context, "请选择数据", Toast.LENGTH_SHORT)
                                    .show();

                        }
                    }
                break;
            case R.id.c_cancel_local:
                finish();
                // }
                break;

            default:
                break;
        }
    }
    // 其他数据
    private void showCheckBoxListView() {

        adapter_all = new LocalAdapter(context, dList,
                R.layout.info_item_four, new String[] { "item_id", "item_tv",
                "item_cb" }, new int[] { R.id.author_id, R.id.author,
                R.id.radio });
        lv.setAdapter(adapter_all);
        listStr = new ArrayList<String>();
        listIDs = new ArrayList<String>();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> paramAnonymousAdapterView,
                                    View paramAnonymousView, int paramAnonymousInt,
                                    long paramAnonymousLong) {
                ViewHolder localViewHolder = (ViewHolder) paramAnonymousView
                        .getTag();
                localViewHolder.cb.toggle();
                LocalAdapter.isSelected.put(paramAnonymousInt,
                        localViewHolder.cb.isChecked());
                querryID = dList.get(paramAnonymousInt).get("序号").toString();
                name = dList.get(paramAnonymousInt).get("名称").toString();
                Log.e("LiNing", "选择的数据是=======" + name);
                if (localViewHolder.cb.isChecked()) {

                    // listStr.add( arrays[paramAnonymousInt].toString());
                    listStr.add(name);
                    listIDs.add(querryID);
                } else {
                    // listStr.remove(arrays[paramAnonymousInt].toString());
                    listStr.remove(name);
                    listIDs.remove(querryID);
                }
                // tv.setText("已选中" + listStr.size() + "项");

            }
        });
    }
}
