package com.example.ydshoa;

import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.LeftOrRight.MyHScrollView;
import com.example.bean.BackApplication;
import com.example.bean.ModId;
import com.example.bean.NetUtil;
import com.example.bean.SysApplication;
import com.example.bean.URLS;
import com.example.ydshoa.CusterInfoActivity.ListViewAndHeadViewTouchLinstener;
import com.example.ydshoa.CusterInfoActivity.MyAdapter.OnScrollChangedListenerImp;
import com.example.ydshoa.R.string;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
//import com.umeng.message.PushAgent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class NewModActivity extends Activity implements OnClickListener {

    private String[] mod_Id;
    private EditText id, name, level;
    private ImageButton ib_mod, exit, last, next;
    private Button enter, reset, pass;
    private TextView head;
    private ListView lv_info;
    private Spinner query, add, set, del, prt, out, cst, gp, gpr, qty, up, amt,
            account;
    private TableRow tvNull;
    private HashMap<String, Object> item;
    private Boolean mCheckable = false;
    private ArrayList<HashMap<String, Object>> dList;
    private Context context;
    private SimpleAdapter sAdapter;
    private SharedPreferences sp;
    public String session;
    private HashMap<String, Object> item_one;
    // 获取item信息
    private String zt_exra, id_exra, name_exra, level_exra, query_exra,
            add_exra, set_exra, del_exra, prt_exra, out_exra, cst_exra,
            up_exra, amt_exra, gp_exra, gpr_exra, qty_exra;
    // 提交信息
    private String ztInfo, idInfo, nameInfo, levelInfo, addInfo, queryInfo,
            setInfo, delInfo, prtInfo, outInfo, cstInfo, gpInfo, gprInfo,
            qtyInfo, upInfo, amtInfo;
    private int flag, checkflag;
    private Boolean checkbox;
    private String str_db;
    private String resultStr = ""; // 服务端返回结果集
    private ImageView testImg, testImger;
    // 提交所有数据
    // String urlUser =
    // "http://192.168.2.178:8080/InfManagePlatform/UseraddUser.action";
    String urlUser = URLS.add_user_url;
    private String uSERZT, uSERNAME, uSERPWD, uSERACCOUNT, uSERDEP, uSERCUST,
            uSERERPUSER, image, imageER, deviceID, sighnpwd;
    private JSONArray ids, names, levels, querrys, adds, amts, csts, dels,
            outs, prs, prgs, prts, qtys, sets, ups;
    private ArrayList<String> querryDBs, querrydeps, querrycusts, querrysups,
            querryusers, querryhs;
    private ArrayList<String> modIDs, listIDs, listNAMEs, listLEVELs,
            listQUERYs, listADDs, listAMTs, listCSTs, listDELs, listPRs,
            listPRGs, listPRTs, listQTYs, listSETs, listUPs, listOUTs;

    private File file_sighn;
    /**
     * 记录选中item的下标
     */
    private List<Integer> checkedIndexList;
    /**
     * 保存每个item中的checkbox
     */
    private List<CheckBox> checkBoxList;
    ArrayList<String> modIDs_get = new ArrayList<String>();
    ArrayList<String> modZTs_get = new ArrayList<String>();
    RelativeLayout mHead;
    Handler handler = new Handler(new Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    try {
                        JSONObject jsonObject = new JSONObject(resultStr);
                        Log.e("LiNing", "数据是===" + jsonObject);
                        Log.e("LiNing", "数据是===" + resultStr.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                default:
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_new_mod);
        BackApplication.getInstance().addActivity(NewModActivity.this);
        context = NewModActivity.this;
//        PushAgent.getInstance(context).onAppStart();
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        intView();
    }

    private void intView() {
        this.mHead = ((RelativeLayout) findViewById(R.id.newmod_scrow_head));
        this.mHead.setFocusable(true);
        this.mHead.setClickable(true);
        this.mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        head = (TextView) findViewById(R.id.all_head);
        head.setText("模块权限");
        this.tvNull = ((TableRow) findViewById(R.id.newmod_root_item));
        // 搜索框
        id = (EditText) findViewById(R.id.mod_search_id);
        name = (EditText) findViewById(R.id.mod_search_name);
        level = (EditText) findViewById(R.id.mod_search_all);
        ib_mod = (ImageButton) findViewById(R.id.mod_search);
        ib_mod.setOnClickListener(this);
        // 账套填充
        account = (Spinner) findViewById(R.id.sp_account);
        // String QuerryRoot = sp.getString("QuerryRoot", "");
        String QuerryRoot = sp.getString("NewQuery", "");
        ArrayList<String> dblist = new ArrayList<String>();
        JSONArray arr;
        try {
            arr = new JSONArray(QuerryRoot);
            int size = arr.length();
            for (int i = 0; i < size; i++) {
                JSONObject jsonObject = arr.getJSONObject(i);
                str_db = jsonObject.get("账套编号").toString();
                dblist.add(str_db);
                Log.e("LiNing", "str_db////////////" + str_db);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.e("LiNing", "str_db==========" + str_db);
        ArrayAdapter sadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dblist);

        sadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        account.setAdapter(sadapter);
        checkedIndexList = new ArrayList<Integer>();
        checkBoxList = new ArrayList<CheckBox>();
        // 权限赋值
        query = (Spinner) findViewById(R.id.sp_query);
        add = (Spinner) findViewById(R.id.sp_add);
        set = (Spinner) findViewById(R.id.sp_set);
        del = (Spinner) findViewById(R.id.sp_del);
        prt = (Spinner) findViewById(R.id.sp_prt);
        out = (Spinner) findViewById(R.id.sp_out);
        cst = (Spinner) findViewById(R.id.sp_cst);
        gp = (Spinner) findViewById(R.id.sp_gp);
        gpr = (Spinner) findViewById(R.id.sp_gpr);
        qty = (Spinner) findViewById(R.id.sp_qty);
        up = (Spinner) findViewById(R.id.sp_up);
        amt = (Spinner) findViewById(R.id.sp_amt);
        // 功能操作
        enter = (Button) findViewById(R.id.ibbtn_mod_add);
        reset = (Button) findViewById(R.id.ibbtn_mod_reset);
        pass = (Button) findViewById(R.id.ibbtn_mod_del);
        enter.setOnClickListener(this);
        reset.setOnClickListener(this);
        pass.setOnClickListener(this);
        dList = new ArrayList();
        lv_info = ((ListView) findViewById(R.id.lv_newmod_header));
        lv_info.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        this.sAdapter = new SimpleAdapter(context, dList,
                R.layout.newmod_scrow_item, new String[]{"账套", "序号", "名称",
                "查询", "增加", "更新", "删除", "打印", "转出", "数量", "单价", "金额",
                "成本", "毛利", "毛利率"}, new int[]{
                R.id.newmod_root_itemZT, R.id.newmod_root_itemID,
                R.id.newmod_root_itemname, R.id.newmod_root_itemquery,
                R.id.newmod_root_itemadd, R.id.newmod_root_itemset,
                R.id.newmod_root_itemdel, R.id.newmod_root_itemprt,
                R.id.newmod_root_itemqut, R.id.newmod_root_itemcst,
                R.id.newmod_root_itemup, R.id.newmod_root_itemamt,
                R.id.newmod_root_itemqty, R.id.newmod_root_itempr,
                R.id.newmod_root_itemprg}) {
            @Override
            public View getView(final int position, View convertView,
                                ViewGroup parent) {
                synchronized (NewModActivity.this) {
                    if (convertView == null)
                        convertView = View.inflate(NewModActivity.this,
                                R.layout.newmod_scrow_item, null);
                    MyHScrollView scrollView1 = (MyHScrollView) convertView
                            .findViewById(R.id.horizontalScrollView1);
                    MyHScrollView headSrcrollView = (MyHScrollView) mHead
                            .findViewById(R.id.horizontalScrollView1);
                    headSrcrollView
                            .AddOnScrollChangedListener(new OnScrollChangedListenerImp(
                                    scrollView1));
                    final CheckBox checkBox = (CheckBox) convertView
                            .findViewById(R.id.mod_listDeleteCheckBox);
                    checkBoxList.add(checkBox);
                    checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            if (isChecked) {
                                checkflag = 1;
                                checkedIndexList.add(position);
                                Log.e("LiNing", "删除的" + checkedIndexList);
                                if (0 < checkedIndexList.size()
                                        && checkedIndexList.size() <= 1) {
                                    item_one = dList.get(position);
                                    checkbox = (Boolean) item_one
                                            .get("checkbox");
                                    zt_exra = (String) item_one.get("账套");
                                    id_exra = (String) item_one.get("序号");
                                    name_exra = (String) item_one.get("名称");
                                    query_exra = (String) item_one.get("查询");
                                    add_exra = (String) item_one.get("增加");
                                    set_exra = (String) item_one.get("更新");
                                    del_exra = (String) item_one.get("删除");
                                    prt_exra = (String) item_one.get("打印");
                                    out_exra = (String) item_one.get("转出");
                                    cst_exra = (String) item_one.get("数量");
                                    up_exra = (String) item_one.get("单价");
                                    amt_exra = (String) item_one.get("金额");
                                    gp_exra = (String) item_one.get("毛利");
                                    gpr_exra = (String) item_one.get("毛利率");
                                    qty_exra = (String) item_one.get("成本");
                                    Log.e("LiNing", "删除的" + checkedIndexList
                                            + query_exra);
                                }
                            } else {
                                checkflag = 0;
                                checkedIndexList.remove((Integer) position);
                                Log.e("LiNing", "删除的" + checkedIndexList);
                            }
                        }
                    });
                    // checkBox.setOnClickListener(new OnClickListener() {
                    //
                    // @Override
                    // public void onClick(View v) {
                    // if (((CheckBox) v).isChecked()) {
                    // checkflag = 1;
                    // // Toast.makeText(NewModActivity.this,
                    // // "选中了" + position, 0).show();
                    // checkedIndexList.add(position);
                    //
                    // } else {
                    // checkflag = 0;
                    // checkedIndexList.remove((Integer) position);
                    // Log.e("LiNing", "--------删除集合"
                    // + checkedIndexList);
                    // }
                    // if (0 < checkedIndexList.size()
                    // && checkedIndexList.size() <= 1) {
                    // item_one = dList.get(position);
                    // checkbox = (Boolean) item_one.get("checkbox");
                    // zt_exra = (String) item_one.get("账套");
                    // id_exra = (String) item_one.get("序号");
                    // name_exra = (String) item_one.get("名称");
                    // query_exra = (String) item_one.get("查询");
                    // add_exra = (String) item_one.get("增加");
                    // set_exra = (String) item_one.get("更新");
                    // del_exra = (String) item_one.get("删除");
                    // prt_exra = (String) item_one.get("打印");
                    // out_exra = (String) item_one.get("转出");
                    // cst_exra = (String) item_one.get("数量");
                    // up_exra = (String) item_one.get("单价");
                    // amt_exra = (String) item_one.get("金额");
                    // gp_exra = (String) item_one.get("毛利");
                    // gpr_exra = (String) item_one.get("毛利率");
                    // qty_exra = (String) item_one.get("成本");
                    // Log.e("LiNing", "删除的" + checkedIndexList);
                    // }
                    // }
                    // });
                }
                return super.getView(position, convertView, parent);
            }

            class OnScrollChangedListenerImp implements
                    MyHScrollView.OnScrollChangedListener {
                MyHScrollView mScrollViewArg;

                public OnScrollChangedListenerImp(MyHScrollView mScrollViewArg) {
                    super();
                    this.mScrollViewArg = mScrollViewArg;
                }

                public void onScrollChanged(int paramInt1, int paramInt2,
                                            int paramInt3, int paramInt4) {
                    this.mScrollViewArg.smoothScrollTo(paramInt1, paramInt2);
                }
            }

            ;
        };
        lv_info.setAdapter(this.sAdapter);
        lv_info.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                for (int i = 0; i < checkBoxList.size(); i++) {
                    checkBoxList.get(i).setVisibility(View.VISIBLE);
                }
            }
        });
        // 下一步操作
        last = (ImageButton) findViewById(R.id.btn_newmod_last);
        next = (ImageButton) findViewById(R.id.btn_newmod_next);
        exit = (ImageButton) findViewById(R.id.btn_newmod_exit);
        last.setOnClickListener(this);
        next.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {
        ListViewAndHeadViewTouchLinstener() {
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ((HorizontalScrollView) NewModActivity.this.mHead
                    .findViewById(R.id.horizontalScrollView1))
                    .onTouchEvent(event);
            return false;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mod_search:
                Intent intent = new Intent(NewModActivity.this,
                        ModListActivity.class);
                startActivityForResult(intent, 1);
                break;
            // 添加
            case R.id.ibbtn_mod_add:
                ztInfo = account.getSelectedItem().toString();
                idInfo = id.getText().toString();
                nameInfo = name.getText().toString();
                addInfo = add.getSelectedItem().toString();
                queryInfo = query.getSelectedItem().toString();
                setInfo = set.getSelectedItem().toString();
                delInfo = del.getSelectedItem().toString();
                prtInfo = prt.getSelectedItem().toString();
                outInfo = out.getSelectedItem().toString();
                cstInfo = cst.getSelectedItem().toString();
                gpInfo = gp.getSelectedItem().toString();
                gprInfo = gpr.getSelectedItem().toString();
                qtyInfo = qty.getSelectedItem().toString();
                upInfo = up.getSelectedItem().toString();
                amtInfo = amt.getSelectedItem().toString();

                if (idInfo.equals("") || nameInfo.equals("")) {
                    Toast.makeText(context, "请选择赋权模块", Toast.LENGTH_LONG).show();
                }
                // if (dList == null) {
                // // 新增
                // newAddItem();
                // id.setText(null);
                // name.setText(null);
                // level.setText(null);
                // } else {
                if (map1 != null) {
                    map1.clear();
                }
                map1.put(ztInfo, idInfo);
                Log.e("LiNing", "list-----new ----" + map1);
                if (list.contains(map1)) {
                    Toast.makeText(context, "此模块已存在", Toast.LENGTH_LONG).show();
                } else {
                    if (idInfo.equals("smrt") && modZTs_get.contains(ztInfo)) {
                        for (int i = 0; i < dList.size(); i++) {
                            Log.e("LiNing", "dList是===list_del888" + dList);
                            Iterator it = dList.iterator();
                            while (it.hasNext()) {
                                HashMap<String, Object> next2 = (HashMap<String, Object>) it
                                        .next();
                                Log.e("LiNing", "next2===list_del888" + next2);
//                                &&next2.get("账套").equals(ztInfo)
                                if (next2.get("账套").equals(ztInfo)&&next2.get("序号").equals(idInfo)) {

                                    it.remove();
                                }

                            }
                        }
                        newAddItem();
                        id.setText(null);
                        name.setText(null);
                        level.setText(null);

                    } else if (idInfo.equals("sp") && modZTs_get.contains(ztInfo)) {
                        for (int i = 0; i < dList.size(); i++) {
                            Log.e("LiNing", "dList是===list_del888" + dList);
                            Iterator it = dList.iterator();
                            while (it.hasNext()) {
                                HashMap<String, Object> next2 = (HashMap<String, Object>) it
                                        .next();
                                if (next2.get("账套").equals(ztInfo)&&next2.get("序号").equals(idInfo)) {

                                    it.remove();
                                }

                            }
                        }
                        newAddItem();
                        id.setText(null);
                        name.setText(null);
                        level.setText(null);

                    } else if (idInfo.equals("spm") && modZTs_get.contains(ztInfo)) {
                        for (int i = 0; i < dList.size(); i++) {
                            Log.e("LiNing", "dList是===list_del888" + dList);
                            Iterator it = dList.iterator();
                            while (it.hasNext()) {
                                HashMap<String, Object> next2 = (HashMap<String, Object>) it
                                        .next();
                                if (next2.get("账套").equals(ztInfo)&&next2.get("序号").equals(idInfo)) {

                                    it.remove();
                                }

                            }
                        }
                        newAddItem();
                        id.setText(null);
                        name.setText(null);
                        level.setText(null);

                    } else if (idInfo.equals("spt") && modZTs_get.contains(ztInfo)) {
                        for (int i = 0; i < dList.size(); i++) {
                            Log.e("LiNing", "dList是===list_del888" + dList);
                            Iterator it = dList.iterator();
                            while (it.hasNext()) {
                                HashMap<String, Object> next2 = (HashMap<String, Object>) it
                                        .next();
                                if (next2.get("账套").equals(ztInfo)&&next2.get("序号").equals(idInfo)) {

                                    it.remove();
                                }

                            }
                        }
                        newAddItem();
                        id.setText(null);
                        name.setText(null);
                        level.setText(null);

                    } else if (idInfo.equals("sk") && modZTs_get.contains(ztInfo)) {
                        for (int i = 0; i < dList.size(); i++) {
                            Log.e("LiNing", "dList是===list_del888" + dList);
                            Iterator it = dList.iterator();
                            while (it.hasNext()) {
                                HashMap<String, Object> next2 = (HashMap<String, Object>) it
                                        .next();
                                if (next2.get("账套").equals(ztInfo)&&next2.get("序号").equals(idInfo)) {

                                    it.remove();
                                }

                            }
                        }
                        newAddItem();
                        id.setText(null);
                        name.setText(null);
                        level.setText(null);

                    } else if (idInfo.equals("spmsa") && modZTs_get.contains(ztInfo)) {
                        for (int i = 0; i < dList.size(); i++) {
                            Log.e("LiNing", "dList是===list_del888" + dList);
                            Iterator it = dList.iterator();
                            while (it.hasNext()) {
                                HashMap<String, Object> next2 = (HashMap<String, Object>) it
                                        .next();
                                if (next2.get("账套").equals(ztInfo)&&next2.get("序号").equals(idInfo)) {

                                    it.remove();
                                }

                            }
                        }
                        newAddItem();
                        id.setText(null);
                        name.setText(null);
                        level.setText(null);

                    } else if (idInfo.equals("spmarp") && modZTs_get.contains(ztInfo)) {
                        for (int i = 0; i < dList.size(); i++) {
                            Log.e("LiNing", "dList是===list_del888" + dList);
                            Iterator it = dList.iterator();
                            while (it.hasNext()) {
                                HashMap<String, Object> next2 = (HashMap<String, Object>) it
                                        .next();
                                if (next2.get("账套").equals(ztInfo)&&next2.get("序号").equals(idInfo)) {

                                    it.remove();
                                }

                            }
                        }
                        newAddItem();
                        id.setText(null);
                        name.setText(null);
                        level.setText(null);

                    } else if (idInfo.equals("sm") && modZTs_get.contains(ztInfo)) {
                        for (int i = 0; i < dList.size(); i++) {
                            Log.e("LiNing", "dList是===list_del888" + dList);
                            Iterator it = dList.iterator();
                            while (it.hasNext()) {
                                HashMap<String, Object> next2 = (HashMap<String, Object>) it
                                        .next();
                                if (next2.get("账套").equals(ztInfo)&&next2.get("序号").equals(idInfo)) {

                                    it.remove();
                                }

                            }
                        }
                        newAddItem();
                        id.setText(null);
                        name.setText(null);
                        level.setText(null);

                    } else if (idInfo.equals("sys") && modZTs_get.contains(ztInfo)) {
                        for (int i = 0; i < dList.size(); i++) {
                            Log.e("LiNing", "dList是===list_del888" + dList);
                            Iterator it = dList.iterator();
                            while (it.hasNext()) {
                                HashMap<String, Object> next2 = (HashMap<String, Object>) it
                                        .next();
                                if (next2.get("账套").equals(ztInfo)&&next2.get("序号").equals(idInfo)) {

                                    it.remove();
                                }

                            }
                        }
                        newAddItem();
                        id.setText(null);
                        name.setText(null);
                        level.setText(null);

                    } else {

                        newAddItem();
                        id.setText(null);
                        name.setText(null);
                        level.setText(null);
                    }
                }

                lv_info.setSelector(R.color.white);
                if (flag == 9) {
                    if (checkflag == 1) {
                        item = new HashMap<String, Object>();
                        item.put("序号", idInfo);
                        itemRoot();
                        Log.e("LiNing", "item数据是===" + item);
                        if (!item_one.get("序号").equals(idInfo)
                                && !item_one.get("账套").equals(ztInfo)) {

                            if (dList.size() > 0 && dList != null) {
                                for (int i = 0; i < dList.size(); i++) {
                                    modIDs_get.add(dList.get(i).get("序号")
                                            .toString());
                                    modZTs_get.add(dList.get(i).get("账套")
                                            .toString());
                                    Log.e("LiNing", "LiNing--------" + modIDs_get
                                            + "LiNing--------" + modZTs_get);
                                }
                                if (modZTs_get.contains(ztInfo)
                                        && modIDs_get.contains(idInfo)) {

                                    Toast.makeText(context, "此模块已存在", Toast.LENGTH_LONG).show();
                                } else {
                                    dList.add(0, item);
                                    sAdapter.notifyDataSetChanged();
                                    lv_info.invalidate();
                                    Log.e("LiNing", "LiNing----NEW----"
                                            + modIDs_get + "LiNing----NEW----"
                                            + modZTs_get);
                                    id.setText(null);
                                    name.setText(null);
                                    level.setText(null);
                                    checkflag = 0;
                                    for (int i = 0; i < checkBoxList.size(); i++) {
                                        checkBoxList.get(i).setChecked(false);
                                    }
                                }
                            }
                        } else {
                            // 上级带下级=====修改（改正）
                            if (idInfo.equals("sys") || idInfo.equals("sm")
                                    || idInfo.equals("sp") || idInfo.equals("smrt")
                                    || idInfo.equals("spm") || idInfo.equals("spt")) {
                                Toast.makeText(context, "请选择分支", Toast.LENGTH_LONG).show();
                                // if (item.get("账套").toString().equals(ztInfo)) {
                                // //sm01修改有问题
                                // dList.remove(item);
                                // }
                                // newAddItem();
                                // id.setText(null);
                                // name.setText(null);
                                // level.setText(null);

                            } else {

                                this.dList.remove(item_one);
                                this.dList.add(0, this.item);
                                id.setText(null);
                                name.setText(null);
                                level.setText(null);
                                checkflag = 0;
                                for (int i = 0; i < checkBoxList.size(); i++) {
                                    checkBoxList.get(i).setChecked(false);
                                }
                            }
                        }
                    }
                }

                sAdapter.notifyDataSetChanged();
                Log.e("LiNing", "提交的模块数据====" + dList);
                break;
            // 修改
            case R.id.ibbtn_mod_reset:
                if (lv_info.getCount() > 0) {
                    flag = 9;
                    // if (checkflag == 1) {
                    if (0 < checkedIndexList.size() && checkedIndexList.size() <= 1) {
                        account.setSelection(0);
                        id.setText(id_exra);
                        name.setText(name_exra);
                        if (query_exra.equals("F")) {
                            query.setSelection(0);
                        } else {
                            query.setSelection(1);
                        }
                        if (add_exra.equals("F")) {
                            add.setSelection(0);
                        } else {
                            add.setSelection(1);
                        }
                        if (set_exra.equals("F")) {
                            set.setSelection(0);
                        } else {
                            set.setSelection(1);
                        }
                        if (del_exra.equals("F")) {
                            del.setSelection(0);
                        } else {
                            del.setSelection(1);
                        }
                        if (prt_exra.equals("F")) {
                            prt.setSelection(0);
                        } else {
                            prt.setSelection(1);
                        }
                        if (out_exra.equals("F")) {
                            out.setSelection(0);
                        } else {
                            out.setSelection(1);
                        }
                        if (cst_exra.equals("F")) {
                            cst.setSelection(0);
                        } else {
                            cst.setSelection(1);
                        }
                        if (gp_exra.equals("F")) {
                            gp.setSelection(0);
                        } else {
                            gp.setSelection(1);
                        }
                        if (gpr_exra.equals("F")) {
                            gpr.setSelection(0);
                        } else {
                            gpr.setSelection(1);
                        }
                        if (qty_exra.equals("F")) {
                            qty.setSelection(0);
                        } else {
                            qty.setSelection(1);
                        }
                        if (up_exra.equals("F")) {
                            up.setSelection(0);
                        } else {
                            up.setSelection(1);
                        }
                        if (amt_exra.equals("F")) {
                            amt.setSelection(0);
                        } else {
                            amt.setSelection(1);
                        }

                    } else {
                        Toast.makeText(this.context, "请选择一条数据", Toast.LENGTH_LONG).show();
                        // checkedIndexList.clear();
                        sAdapter.notifyDataSetChanged();
                    }

                    // } else {
                    //
                    // Toast.makeText(context, "请选择要操作的数据", 1).show();
                    // }
                } else {
                    Toast.makeText(this.context, "无数据，请添加", Toast.LENGTH_LONG).show();
                }

                // sAdapter.notifyDataSetChanged();
                break;
            // 删除
            case R.id.ibbtn_mod_del:
                if (0 < checkedIndexList.size() && checkedIndexList != null) {
                    new AlertDialog.Builder(this)
                            .setTitle("是否删除数据？")
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface paramAnonymousDialogInterface,
                                                int paramAnonymousInt) {
                                            delMore();

                                        }
                                    }).setNegativeButton("取消", null).show();

                } else {
                    Toast.makeText(context, "请选择要操作的数据", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.btn_newmod_last:
                finish();
                break;
            case R.id.btn_newmod_next:
                String stritem = new Gson().toJson(dList);
                Log.e("LiNing", "item数据是===111" + stritem);
                sp.edit().putString("NewMod", stritem).commit();
                postInfo();

                break;
            case R.id.btn_newmod_exit:
                // startActivity(new Intent(this.context, SysActivity.class));
                // finish();
                SysApplication.getInstance().exit();
                break;

            default:
                break;
        }
    }

    protected void delMore() {
        // 先将checkedIndexList中的元素从大到小排列,否则可能会出现错位删除或下标溢出的错误
        checkedIndexList = sortCheckedIndexList(checkedIndexList);
        for (int i = 0; i < checkedIndexList.size(); i++) {
            // 需要强转为int,才会删除对应下标的数据,否则默认删除与括号中对象相同的数据
            dList.remove((int) checkedIndexList.get(i));
            checkBoxList.remove(checkedIndexList.get(i));
            Log.e("LiNing", "删除的数据====" + checkedIndexList.get(i));
        }
        for (int i = 0; i < checkBoxList.size(); i++) {
            // 将已选的设置成未选状态
            checkBoxList.get(i).setChecked(false);
            // 将checkbox设置为不可见
            // checkBoxList.get(i).setVisibility(View.INVISIBLE);
        }

        // 更新数据源
        sAdapter.notifyDataSetChanged();
        // 清空checkedIndexList,避免影响下一次删除
        checkedIndexList.clear();
    }

    private List<Integer> sortCheckedIndexList(List<Integer> list) {
        int[] ass = new int[list.size()];// 辅助数组
        for (int i = 0; i < list.size(); i++) {
            ass[i] = list.get(i);
        }
        Arrays.sort(ass);
        list.clear();
        for (int i = ass.length - 1; i >= 0; i--) {
            list.add(ass[i]);
        }
        return list;
    }

    // 全部模块
    ArrayList<String> list_sys = new ArrayList<String>() {
        {
            add("smrtra");//43
            add("smrtrc");//44
            add("smrtru");//45
            add("smrt");//42
            add("sm");//40
            add("sptrt");//52
            add("sptsa");//53
            add("spt");//51
            add("spmrt");//49
            add("spmsa");//50
            add("spm");//销售统计表//47
            add("spmarp");//48
//            add("spr");//应收统计表
            add("sp");//46
            add("sys");//62
            add("smprice");//41

            add("SATG");//13
            add("SATGP");//16
            add("SATGPC");//17
            add("SATGPD");//22
            add("SATGPCS");//21
            add("SATGPGC");//26
            add("SATGC");//14
            add("SATGCD");//54
            add("SATGCS");//56
            add("SATGCGC");//55
            add("SATGD");//15
            add("SATGDS");//58
            add("SATGDGC");//57
            add("SATGS");//60
            add("SATGSGC");//61
            add("SATGGC");//59
            add("SATGPPN");//32
            add("SATGPI");//30
            add("SATGPIPN");//31
            add("SATGPCPN");//20
            add("SATGPCI");//18
            add("SATGPCIPN");//19
            add("SATGPDPN");//25
            add("SATGPDI");//23
            add("SATGPDIPN");//24
            add("SATGPSPN");//35
            add("SATGPSI");//33
            add("SATGPSIPN");//34
            add("SATGPGCPN");//29
            add("SATGPGCI");//27
            add("SATGPGCIPN");//28

            add("ArpTG");//0
            add("ArpTGGC");//3
            add("ArpTGP");//4
            add("ArpTGC");//1
            add("ArpTGS");//12
            add("ArpTGD");//2
            add("ArpTGPC");//5
            add("ArpTGPD");//7
            add("ArpTGPS");//10
            add("ArpTGPGC");//9
            add("ArpTGPCGC");//6
            add("ArpTGPDGC");//8
            add("ArpTGPSGC");//11

            add("skvp");//39
            add("skf");//37
            add("skh");//38
            add("sk");//36



        }

    };
    // 决策报表
    ArrayList<String> list_sp = new ArrayList<String>() {
        {
            add("spmrt");
            add("spmsa");
            add("spmarp");
            add("spm");
            add("sptrt");
            add("sptsa");
            add("spt");
            add("sp");
            add("SATG");
            add("SATGP");
            add("SATGPC");
            add("SATGPD");
            add("SATGPCS");
            add("SATGPGC");
            add("SATGC");
            add("SATGCD");
            add("SATGCS");
            add("SATGCGC");
            add("SATGD");
            add("SATGDS");
            add("SATGDGC");
            add("SATGS");
            add("SATGSGC");
            add("SATGGC");
            add("SATGPPN");
            add("SATGPI");
            add("SATGPIPN");
            add("SATGPCPN");
            add("SATGPCI");
            add("SATGPCIPN");
            add("SATGPDPN");
            add("SATGPDI");
            add("SATGPDIPN");
            add("SATGPSPN");
            add("SATGPSI");
            add("SATGPSIPN");
            add("SATGPGCPN");
            add("SATGPGCI");
            add("SATGPGCIPN");

            add("ARPTG");
            add("ARPTGGC");
            add("ArpTGP");
            add("ArpTGC");
            add("ArpTGS");
            add("ArpTGD");
            add("ArpTGPC");
            add("ArpTGPD");
            add("ArpTGPS");
            add("ArpTGPGC");
            add("ArpTGPCGC");
            add("ArpTGPDGC");
            add("ArpTGPSGC");
        }

    };
    // 销售统计表报表
    ArrayList<String> list_spmarp = new ArrayList<String>() {
        {
            add("spmarp");
            add("ARPTG");
            add("ARPTGGC");
            add("ArpTGP");
            add("ArpTGC");
            add("ArpTGS");
            add("ArpTGD");
            add("ArpTGPC");
            add("ArpTGPD");
            add("ArpTGPS");
            add("ArpTGPGC");
            add("ArpTGPCGC");
            add("ArpTGPDGC");
            add("ArpTGPSGC");
        }

    };
    // 销售统计表报表
    ArrayList<String> list_spmsa = new ArrayList<String>() {
        {
            add("spmsa");
            add("SATG");
            add("SATGP");
            add("SATGPC");
            add("SATGPD");
            add("SATGPCS");
            add("SATGPGC");
            add("SATGC");
            add("SATGCD");
            add("SATGCS");
            add("SATGCGC");
            add("SATGD");
            add("SATGDS");
            add("SATGDGC");
            add("SATGS");
            add("SATGSGC");
            add("SATGGC");
            add("SATGPPN");
            add("SATGPI");
            add("SATGPIPN");
            add("SATGPCPN");
            add("SATGPCI");
            add("SATGPCIPN");
            add("SATGPDPN");
            add("SATGPDI");
            add("SATGPDIPN");
            add("SATGPSPN");
            add("SATGPSI");
            add("SATGPSIPN");
            add("SATGPGCPN");
            add("SATGPGCI");
            add("SATGPGCIPN");

        }

    };
    // 统计报表
    ArrayList<String> list_spm = new ArrayList<String>() {
        {
            add("spmrt");
            add("spmsa");
            add("spm");
            add("SATG");
            add("SATGP");
            add("SATGPC");
            add("SATGPD");
            add("SATGPCS");
            add("SATGPGC");
            add("SATGC");
            add("SATGCD");
            add("SATGCS");
            add("SATGCGC");
            add("SATGD");
            add("SATGDS");
            add("SATGDGC");
            add("SATGS");
            add("SATGSGC");
            add("SATGGC");
            add("SATGPPN");
            add("SATGPI");
            add("SATGPIPN");
            add("SATGPCPN");
            add("SATGPCI");
            add("SATGPCIPN");
            add("SATGPDPN");
            add("SATGPDI");
            add("SATGPDIPN");
            add("SATGPSPN");
            add("SATGPSI");
            add("SATGPSIPN");
            add("SATGPGCPN");
            add("SATGPGCI");
            add("SATGPGCIPN");
            add("spmarp");
            add("ARPTG");
            add("ARPTGGC");
            add("ArpTGP");
            add("ArpTGC");
            add("ArpTGS");
            add("ArpTGD");
            add("ArpTGPC");
            add("ArpTGPD");
            add("ArpTGPS");
            add("ArpTGPGC");
            add("ArpTGPCGC");
            add("ArpTGPDGC");
            add("ArpTGPSGC");
        }

    };

    // 明细报表
    ArrayList<String> list_spt = new ArrayList<String>() {
        {
            add("sptrt");
            add("sptsa");
            add("spt");
        }

    };
    // root配置
    ArrayList<String> list_smrt = new ArrayList<String>() {
        {
            add("smrtra");
            add("smrtrc");
            add("smrtru");
            add("smrt");
        }
    };
    // 系统管理
    ArrayList<String> list_sm = new ArrayList<String>() {
        {
            add("smrtra");
            add("smrtrc");
            add("smrtru");
            add("smrt");
            add("sm");
            add("smprice");
        }
    };
    // 客户管理
    ArrayList<String> list_sk = new ArrayList<String>() {
        {
            add("sk");
            add("skh");
            add("skf");
            add("skvp");
        }
    };
    ArrayList<String> list_rt01_name = new ArrayList<String>();
    private String ids_str, name_str;

    Map<String, String> map1 = new HashMap<String, String>();
    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    List<Map<String, Object>> list_del = new ArrayList<Map<String, Object>>();
    private String str_dbname;
    private HashMap<String, String> map;
    private HashMap<String, Object> item_del;
    private Map<String, String> map3;
    private HashMap<String, Object> hashMap;
    private String string;

    private void newAddItem() {

        if (idInfo.equals("smrt")) {
            for (int j = 0; j < list_smrt.size(); j++) {
                ids_str = list_smrt.get(j);
                Log.e("LiNing", "ids_str数据是===" + ids_str);
                if (ids_str.equals("smrtra")) {
                    nameInfo = "账套配置";
                } else if (ids_str.equals("smrtrc")) {
                    nameInfo = "Root构建";
                } else if (ids_str.equals("smrtru")) {
                    nameInfo = "用户管理";
                } else if (ids_str.equals("smrt")) {
                    nameInfo = "Root配置";
                }
                Log.e("LiNing", "ids_str数据是===" + list_smrt);
                item = new HashMap<String, Object>();
                item.put("序号", ids_str);
                itemRoot();
                dList.add(item);

                for (int i = 0; i < dList.size(); i++) {
                    map = new HashMap<String, String>();
                    item_del = dList.get(i);
                    str_dbname = item_del.get("账套").toString();
                    modIDs_get.add(dList.get(i).get("序号").toString());
                    modZTs_get.add(dList.get(i).get("账套").toString());
                    Log.e("LiNing", "LiNing--------" + modIDs_get
                            + "LiNing--------" + modZTs_get);
                    map.put(dList.get(i).get("账套").toString(), dList.get(i)
                            .get("序号").toString());

                }
                list.add(map);
            }
        } else if (idInfo.equals("sys")) {
            for (int j = 0; j < list_sys.size(); j++) {
                ids_str = list_sys.get(j);
                Log.e("LiNing", "ids_str数据是===" + ids_str);
                if (ids_str.equals("smrtra")) {
                    nameInfo = "账套配置";
                } else if (ids_str.equals("smrtrc")) {
                    nameInfo = "Root构建";
                } else if (ids_str.equals("smrtru")) {
                    nameInfo = "用户管理";
                } else if (ids_str.equals("smrt")) {
                    nameInfo = "Root配置";
                } else if (ids_str.equals("smprice")) {
                    nameInfo = "定价管理";
                } else if (ids_str.equals("sm")) {
                    nameInfo = "系统管理";
                } else if (ids_str.equals("sys")) {
                    nameInfo = "全部模块";
                } else if (ids_str.equals("spmrt")) {
                    nameInfo = "收款统计表";
                } else if (ids_str.equals("spm")) {
                    nameInfo = "统计报表";
                } else if (ids_str.equals("sptrt")) {
                    nameInfo = "收款明细表";
                } else if (ids_str.equals("sptsa")) {
                    nameInfo = "销售明细表";
                } else if (ids_str.equals("spt")) {
                    nameInfo = "明细报表";
                } else if (ids_str.equals("sp")) {
                    nameInfo = "决策报表";
                } else if (ids_str.equals("SATG")) {
                    nameInfo = "分支机构销售统计表";
                } else if (ids_str.equals("SATGP")) {
                    nameInfo = "分支机构+品牌销售统计表";
                } else if (ids_str.equals("SATGPC")) {
                    nameInfo = "分支机构+品牌+渠道销售统计表";
                } else if (ids_str.equals("SATGPD")) {
                    nameInfo = "分支机构+品牌+部门销售统计表";
                }  else if (ids_str.equals("SATGPCS")) {
                    nameInfo = "分支机构+品牌+业务销售统计表";
                } else if (ids_str.equals("SATGPGC")) {
                    nameInfo = "分支机构+品牌+终端网点销售统计表";
                } else if (ids_str.equals("SATGC")) {
                    nameInfo = "分支机构+渠道销售统计表";
                } else if (ids_str.equals("SATGCD")) {
                    nameInfo = "分支机构+渠道+部门销售统计表";
                } else if (ids_str.equals("SATGCS")) {
                    nameInfo = "分支机构+渠道+业务销售统计表";
                } else if (ids_str.equals("SATGCGC")) {
                    nameInfo = "分支机构+渠道+终端网点销售统计表";
                } else if (ids_str.equals("SATGD")) {
                    nameInfo = "分支机构+部门销售统计表";
                } else if (ids_str.equals("SATGDS")) {
                    nameInfo = "分支机构+部门+业务销售统计表";
                } else if (ids_str.equals("SATGDGC")) {
                    nameInfo = "分支机构+部门+终端网点销售统计表";
                } else if (ids_str.equals("SATGS")) {
                    nameInfo = "分支机构+业务销售统计表";
                } else if (ids_str.equals("SATGSGC")) {
                    nameInfo = "分支机构+业务+终端网点销售统计表";
                } else if (ids_str.equals("SATGGC")) {
                    nameInfo = "分支机构+终端网点销售统计表";
                } else if (ids_str.equals("SATGPPN")) {
                    nameInfo = "分支机构+品牌+品号销售统计表";
                } else if (ids_str.equals("SATGPI")) {
                    nameInfo = "分支机构+品牌+货品中类销售统计表";
                } else if (ids_str.equals("SATGPIPN")) {
                    nameInfo = "分支机构+品牌+货品中类+品号销售统计表";
                } else if (ids_str.equals("SATGPCPN")) {
                    nameInfo = "分支机构+品牌+渠道+品号销售统计表";
                } else if (ids_str.equals("SATGPCI")) {
                    nameInfo = "分支机构+品牌+渠道+货品中类销售统计表";
                } else if (ids_str.equals("SATGPCIPN")) {
                    nameInfo = "分支机构+品牌+渠道+货品中类+品号销售统计表";
                } else if (ids_str.equals("SATGPDPN")) {
                    nameInfo = "分支机构+品牌+部门+品号销售统计表";
                } else if (ids_str.equals("SATGPDI")) {
                    nameInfo = "分支机构+品牌+部门+品号+货品中类销售统计表";
                } else if (ids_str.equals("SATGPDIPN")) {
                    nameInfo = "分支机构+品牌+部门+品号+货品中类+品号销售统计表";
                } else if (ids_str.equals("SATGPSPN")) {
                    nameInfo = "分支机构+品牌+业务+品号销售统计表";
                } else if (ids_str.equals("SATGPSI")) {
                    nameInfo = "分支机构+品牌+业务+货品中类销售统计表";
                } else if (ids_str.equals("SATGPSIPN")) {
                    nameInfo = "分支机构+品牌+业务+货品中类+品号销售统计表";
                } else if (ids_str.equals("SATGPGCPN")) {
                    nameInfo = "分支机构+品牌+终端+品号销售统计表";
                } else if (ids_str.equals("SATGPGCI")) {
                    nameInfo = "分支机构+品牌+终端+货品中类销售统计表";
                } else if (ids_str.equals("SATGPGCIPN")) {
                    nameInfo = "分支机构+品牌+终端+货品中类+品号销售统计表";
                } else if (ids_str.equals("spmsa")) {
                    nameInfo = "销售统计表";
                } else if (ids_str.equals("ArpTG")) {
                    nameInfo = "分支机构应收账龄表";
                } else if (ids_str.equals("ArpTGGC")) {
                    nameInfo = "机构+终端网点应收账龄表";
                } else if (ids_str.equals("ArpTGP")) {
                    nameInfo = "机构+核算单位应收账龄表";
                } else if (ids_str.equals("ArpTGC")) {
                    nameInfo = "机构+渠道应收账龄表";
                } else if (ids_str.equals("ArpTGS")) {
                    nameInfo = "机构+业务应收账龄表";
                } else if (ids_str.equals("ArpTGD")) {
                    nameInfo = "机构+部门应收账龄表";
                } else if (ids_str.equals("ArpTGPC")) {
                    nameInfo = "机构+核算单位+渠道应收账龄表";
                } else if (ids_str.equals("ArpTGPD")) {
                    nameInfo = "机构+核算单位+部门应收账龄表";
                } else if (ids_str.equals("ArpTGPS")) {
                    nameInfo = "机构+核算单位+业务应收账龄表";
                } else if (ids_str.equals("ArpTGPGC")) {
                    nameInfo = "机构+核算单位+终端网点应收账龄表";
                } else if (ids_str.equals("ArpTGPCGC")) {
                    nameInfo = "机构+核算单位+渠道+终端网点应收账龄表";
                } else if (ids_str.equals("ArpTGPDGC")) {
                    nameInfo = "机构+核算单位+部门+终端网点应收账龄表";
                } else if (ids_str.equals("ArpTGPSGC")) {
                    nameInfo = "机构+核算单位+业务+终端网点应收账龄表";
                } else if (ids_str.equals("spmarp")) {
                    nameInfo = "应收账龄表";
                } else if (ids_str.equals("skh")) {
                    nameInfo = "客户建档";
                } else if (ids_str.equals("skf")) {
                    nameInfo = "客户跟踪";
                } else if (ids_str.equals("skvp")) {
                    nameInfo = "会员建档";
                } else if (ids_str.equals("sk")) {
                    nameInfo = "客户管理";
                }
//                else if (ids_str.equals("spr")) {
//                    nameInfo = "应收统计表";
//                }
                Log.e("LiNing", "ids_str数据是===" + list_sys);
                item = new HashMap<String, Object>();
                item.put("序号", ids_str);
                itemRoot();
                dList.add(item);
                Log.e("LiNing", "==========ids_str数据是===" + dList.size());

                for (int i = 0; i < dList.size(); i++) {
                    map = new HashMap<String, String>();
                    item_del = dList.get(i);
                    str_dbname = item_del.get("账套").toString();
                    modIDs_get.add(dList.get(i).get("序号").toString());
                    modZTs_get.add(dList.get(i).get("账套").toString());
                    Log.e("LiNing", "LiNing--------" + modIDs_get
                            + "LiNing--------" + modZTs_get);
                    map.put(dList.get(i).get("账套").toString(), dList.get(i)
                            .get("序号").toString());

                }
                list.add(map);
            }
        } else if (idInfo.equals("spmsa")) {
            for (int j = 0; j < list_spmsa.size(); j++) {
                ids_str = list_spmsa.get(j);
                Log.e("LiNing", "ids_str数据是===" + ids_str);
                if (ids_str.equals("SATG")) {
                    nameInfo = "分支机构销售统计表";
                } else if (ids_str.equals("SATGP")) {
                    nameInfo = "分支机构+品牌销售统计表";
                } else if (ids_str.equals("SATGPC")) {
                    nameInfo = "分支机构+品牌+渠道销售统计表";
                } else if (ids_str.equals("SATGPD")) {
                    nameInfo = "分支机构+品牌+部门销售统计表";
                }else if (ids_str.equals("SATGPCS")) {
                    nameInfo = "分支机构+品牌+业务销售统计表";
                } else if (ids_str.equals("SATGPGC")) {
                    nameInfo = "分支机构+品牌+终端网点销售统计表";
                } else if (ids_str.equals("SATGC")) {
                    nameInfo = "分支机构+渠道销售统计表";
                } else if (ids_str.equals("SATGCD")) {
                    nameInfo = "分支机构+渠道+部门销售统计表";
                } else if (ids_str.equals("SATGCS")) {
                    nameInfo = "分支机构+渠道+业务销售统计表";
                } else if (ids_str.equals("SATGCGC")) {
                    nameInfo = "分支机构+渠道+终端网点销售统计表";
                } else if (ids_str.equals("SATGD")) {
                    nameInfo = "分支机构+部门销售统计表";
                } else if (ids_str.equals("SATGDS")) {
                    nameInfo = "分支机构+部门+业务销售统计表";
                } else if (ids_str.equals("SATGDGC")) {
                    nameInfo = "分支机构+部门+终端网点销售统计表";
                } else if (ids_str.equals("SATGS")) {
                    nameInfo = "分支机构+业务销售统计表";
                } else if (ids_str.equals("SATGSGC")) {
                    nameInfo = "分支机构+业务+终端网点销售统计表";
                } else if (ids_str.equals("SATGGC")) {
                    nameInfo = "分支机构+终端网点销售统计表";
                } else if (ids_str.equals("SATGPPN")) {
                    nameInfo = "分支机构+品牌+品号销售统计表";
                } else if (ids_str.equals("SATGPI")) {
                    nameInfo = "分支机构+品牌+货品中类销售统计表";
                } else if (ids_str.equals("SATGPIPN")) {
                    nameInfo = "分支机构+品牌+货品中类+品号销售统计表";
                } else if (ids_str.equals("SATGPCPN")) {
                    nameInfo = "分支机构+品牌+渠道+品号销售统计表";
                } else if (ids_str.equals("SATGPCI")) {
                    nameInfo = "分支机构+品牌+渠道+货品中类销售统计表";
                } else if (ids_str.equals("SATGPCIPN")) {
                    nameInfo = "分支机构+品牌+渠道+货品中类+品号销售统计表";
                } else if (ids_str.equals("SATGPDPN")) {
                    nameInfo = "分支机构+品牌+部门+品号销售统计表";
                } else if (ids_str.equals("SATGPDI")) {
                    nameInfo = "分支机构+品牌+部门+品号+货品中类销售统计表";
                } else if (ids_str.equals("SATGPDIPN")) {
                    nameInfo = "分支机构+品牌+部门+品号+货品中类+品号销售统计表";
                } else if (ids_str.equals("SATGPSPN")) {
                    nameInfo = "分支机构+品牌+业务+品号销售统计表";
                } else if (ids_str.equals("SATGPSI")) {
                    nameInfo = "分支机构+品牌+业务+货品中类销售统计表";
                } else if (ids_str.equals("SATGPSIPN")) {
                    nameInfo = "分支机构+品牌+业务+货品中类+品号销售统计表";
                } else if (ids_str.equals("SATGPGCPN")) {
                    nameInfo = "分支机构+品牌+终端+品号销售统计表";
                } else if (ids_str.equals("SATGPGCI")) {
                    nameInfo = "分支机构+品牌+终端+货品中类销售统计表";
                } else if (ids_str.equals("SATGPGCIPN")) {
                    nameInfo = "分支机构+品牌+终端+货品中类+品号销售统计表";
                } else if (ids_str.equals("spmsa")) {
                    nameInfo = "销售统计表";
                }
                Log.e("LiNing", "ids_str数据是===" + list_spmsa);
                item = new HashMap<String, Object>();
                item.put("序号", ids_str);
                itemRoot();
                dList.add(item);

                for (int i = 0; i < dList.size(); i++) {
                    map = new HashMap<String, String>();
                    item_del = dList.get(i);
                    str_dbname = item_del.get("账套").toString();
                    modIDs_get.add(dList.get(i).get("序号").toString());
                    modZTs_get.add(dList.get(i).get("账套").toString());
                    Log.e("LiNing", "LiNing--------" + modIDs_get
                            + "LiNing--------" + modZTs_get);
                    map.put(dList.get(i).get("账套").toString(), dList.get(i)
                            .get("序号").toString());

                }
                list.add(map);
            }
        } else if (idInfo.equals("spmarp")) {
            for (int j = 0; j < list_spmarp.size(); j++) {
                ids_str = list_spmarp.get(j);
                Log.e("LiNing", "ids_str数据是===" + ids_str);
                if (ids_str.equals("ArpTG")) {
                    nameInfo = "分支机构应收账龄表";
                } else if (ids_str.equals("ArpTGGC")) {
                    nameInfo = "机构+终端网点应收账龄表";
                } else if (ids_str.equals("ArpTGP")) {
                    nameInfo = "机构+核算单位应收账龄表";
                } else if (ids_str.equals("ArpTGC")) {
                    nameInfo = "机构+渠道应收账龄表";
                } else if (ids_str.equals("ArpTGS")) {
                    nameInfo = "机构+业务应收账龄表";
                } else if (ids_str.equals("ArpTGD")) {
                    nameInfo = "机构+部门应收账龄表";
                } else if (ids_str.equals("ArpTGPC")) {
                    nameInfo = "机构+核算单位+渠道应收账龄表";
                } else if (ids_str.equals("ArpTGPD")) {
                    nameInfo = "机构+核算单位+部门应收账龄表";
                } else if (ids_str.equals("ArpTGPS")) {
                    nameInfo = "机构+核算单位+业务应收账龄表";
                } else if (ids_str.equals("ArpTGPGC")) {
                    nameInfo = "机构+核算单位+终端网点应收账龄表";
                } else if (ids_str.equals("ArpTGPCGC")) {
                    nameInfo = "机构+核算单位+渠道+终端网点应收账龄表";
                } else if (ids_str.equals("ArpTGPDGC")) {
                    nameInfo = "机构+核算单位+部门+终端网点应收账龄表";
                } else if (ids_str.equals("ArpTGPSGC")) {
                    nameInfo = "机构+核算单位+业务+终端网点应收账龄表";
                } else if (ids_str.equals("spmarp")) {
                    nameInfo = "应收账龄表";
                }
                Log.e("LiNing", "ids_str数据是===" + list_spmarp);
                item = new HashMap<String, Object>();
                item.put("序号", ids_str);
                itemRoot();
                dList.add(item);

                for (int i = 0; i < dList.size(); i++) {
                    map = new HashMap<String, String>();
                    item_del = dList.get(i);
                    str_dbname = item_del.get("账套").toString();
                    modIDs_get.add(dList.get(i).get("序号").toString());
                    modZTs_get.add(dList.get(i).get("账套").toString());
                    Log.e("LiNing", "LiNing--------" + modIDs_get
                            + "LiNing--------" + modZTs_get);
                    map.put(dList.get(i).get("账套").toString(), dList.get(i)
                            .get("序号").toString());

                }
                list.add(map);
            }
        } else if (idInfo.equals("sk")) {
            for (int j = 0; j < list_sk.size(); j++) {
                ids_str = list_sk.get(j);
                Log.e("LiNing", "ids_str数据是===" + ids_str);
                if (ids_str.equals("skh")) {
                    nameInfo = "客户建档";
                } else if (ids_str.equals("skf")) {
                    nameInfo = "客户跟踪";
                } else if (ids_str.equals("skvp")) {
                    nameInfo = "会员建档";
                } else if (ids_str.equals("sk")) {
                    nameInfo = "客户管理";
                }
                Log.e("LiNing", "ids_str数据是===" + list_sk);
                item = new HashMap<String, Object>();
                item.put("序号", ids_str);
                itemRoot();
                dList.add(item);

                for (int i = 0; i < dList.size(); i++) {
                    map = new HashMap<String, String>();
                    item_del = dList.get(i);
                    str_dbname = item_del.get("账套").toString();
                    modIDs_get.add(dList.get(i).get("序号").toString());
                    modZTs_get.add(dList.get(i).get("账套").toString());
                    Log.e("LiNing", "LiNing--------" + modIDs_get
                            + "LiNing--------" + modZTs_get);
                    map.put(dList.get(i).get("账套").toString(), dList.get(i)
                            .get("序号").toString());

                }
                list.add(map);
            }
        } else if (idInfo.equals("sm")) {
            for (int j = 0; j < list_sm.size(); j++) {
                ids_str = list_sm.get(j);
                Log.e("LiNing", "ids_str数据是===" + ids_str);
                if (ids_str.equals("smrtra")) {
                    nameInfo = "账套配置";
                } else if (ids_str.equals("smrtrc")) {
                    nameInfo = "Root构建";
                } else if (ids_str.equals("smrtru")) {
                    nameInfo = "用户管理";
                } else if (ids_str.equals("smrt")) {
                    nameInfo = "Root配置";
                } else if (ids_str.equals("sm")) {
                    nameInfo = "系统管理";
                } else if (ids_str.equals("smprice")) {
                    nameInfo = "定价管理";
                }
                Log.e("LiNing", "ids_str数据是===" + list_sm);
                item = new HashMap<String, Object>();
                item.put("序号", ids_str);
                itemRoot();
                dList.add(item);

                for (int i = 0; i < dList.size(); i++) {
                    map = new HashMap<String, String>();
                    item_del = dList.get(i);
                    str_dbname = item_del.get("账套").toString();
                    modIDs_get.add(dList.get(i).get("序号").toString());
                    modZTs_get.add(dList.get(i).get("账套").toString());
                    Log.e("LiNing", "LiNing--------" + modIDs_get
                            + "LiNing--------" + modZTs_get);
                    map.put(dList.get(i).get("账套").toString(), dList.get(i)
                            .get("序号").toString());

                }
                list.add(map);
            }
        } else if (idInfo.equals("spm")) {
            for (int j = 0; j < list_spm.size(); j++) {
                ids_str = list_spm.get(j);
                Log.e("LiNing", "ids_str数据是===" + ids_str);
                if (ids_str.equals("spmrt")) {
                    nameInfo = "收款统计表";
                } else if (ids_str.equals("spm")) {
                    nameInfo = "统计报表";
                } else if (ids_str.equals("SATG")) {
                    nameInfo = "分支机构销售统计表";
                } else if (ids_str.equals("SATGP")) {
                    nameInfo = "分支机构+品牌销售统计表";
                } else if (ids_str.equals("SATGPC")) {
                    nameInfo = "分支机构+品牌+渠道销售统计表";
                } else if (ids_str.equals("SATGPD")) {
                    nameInfo = "分支机构+品牌+部门销售统计表";
                } else if (ids_str.equals("SATGPCS")) {
                    nameInfo = "分支机构+品牌+业务销售统计表";
                } else if (ids_str.equals("SATGPGC")) {
                    nameInfo = "分支机构+品牌+终端网点销售统计表";
                } else if (ids_str.equals("SATGC")) {
                    nameInfo = "分支机构+渠道销售统计表";
                } else if (ids_str.equals("SATGCD")) {
                    nameInfo = "分支机构+渠道+部门销售统计表";
                } else if (ids_str.equals("SATGCS")) {
                    nameInfo = "分支机构+渠道+业务销售统计表";
                } else if (ids_str.equals("SATGCGC")) {
                    nameInfo = "分支机构+渠道+终端网点销售统计表";
                } else if (ids_str.equals("SATGD")) {
                    nameInfo = "分支机构+部门销售统计表";
                } else if (ids_str.equals("SATGDS")) {
                    nameInfo = "分支机构+部门+业务销售统计表";
                } else if (ids_str.equals("SATGDGC")) {
                    nameInfo = "分支机构+部门+终端网点销售统计表";
                } else if (ids_str.equals("SATGS")) {
                    nameInfo = "分支机构+业务销售统计表";
                } else if (ids_str.equals("SATGSGC")) {
                    nameInfo = "分支机构+业务+终端网点销售统计表";
                } else if (ids_str.equals("SATGGC")) {
                    nameInfo = "分支机构+终端网点销售统计表";
                } else if (ids_str.equals("SATGPPN")) {
                    nameInfo = "分支机构+品牌+品号销售统计表";
                } else if (ids_str.equals("SATGPI")) {
                    nameInfo = "分支机构+品牌+货品中类销售统计表";
                } else if (ids_str.equals("SATGPIPN")) {
                    nameInfo = "分支机构+品牌+货品中类+品号销售统计表";
                } else if (ids_str.equals("SATGPCPN")) {
                    nameInfo = "分支机构+品牌+渠道+品号销售统计表";
                } else if (ids_str.equals("SATGPCI")) {
                    nameInfo = "分支机构+品牌+渠道+货品中类销售统计表";
                } else if (ids_str.equals("SATGPCIPN")) {
                    nameInfo = "分支机构+品牌+渠道+货品中类+品号销售统计表";
                } else if (ids_str.equals("SATGPDPN")) {
                    nameInfo = "分支机构+品牌+部门+品号销售统计表";
                } else if (ids_str.equals("SATGPDI")) {
                    nameInfo = "分支机构+品牌+部门+品号+货品中类销售统计表";
                } else if (ids_str.equals("SATGPDIPN")) {
                    nameInfo = "分支机构+品牌+部门+品号+货品中类+品号销售统计表";
                } else if (ids_str.equals("SATGPSPN")) {
                    nameInfo = "分支机构+品牌+业务+品号销售统计表";
                } else if (ids_str.equals("SATGPSI")) {
                    nameInfo = "分支机构+品牌+业务+货品中类销售统计表";
                } else if (ids_str.equals("SATGPSIPN")) {
                    nameInfo = "分支机构+品牌+业务+货品中类+品号销售统计表";
                } else if (ids_str.equals("SATGPGCPN")) {
                    nameInfo = "分支机构+品牌+终端+品号销售统计表";
                } else if (ids_str.equals("SATGPGCI")) {
                    nameInfo = "分支机构+品牌+终端+货品中类销售统计表";
                } else if (ids_str.equals("SATGPGCIPN")) {
                    nameInfo = "分支机构+品牌+终端+货品中类+品号销售统计表";
                } else if (ids_str.equals("spmsa")) {
                    nameInfo = "销售统计表";
                } else if (ids_str.equals("ArpTG")) {
                    nameInfo = "分支机构应收账龄表";
                } else if (ids_str.equals("ArpTGGC")) {
                    nameInfo = "机构+终端网点应收账龄表";
                } else if (ids_str.equals("ArpTGP")) {
                    nameInfo = "机构+核算单位应收账龄表";
                } else if (ids_str.equals("ArpTGC")) {
                    nameInfo = "机构+渠道应收账龄表";
                } else if (ids_str.equals("ArpTGS")) {
                    nameInfo = "机构+业务应收账龄表";
                } else if (ids_str.equals("ArpTGD")) {
                    nameInfo = "机构+部门应收账龄表";
                } else if (ids_str.equals("ArpTGPC")) {
                    nameInfo = "机构+核算单位+渠道应收账龄表";
                } else if (ids_str.equals("ArpTGPD")) {
                    nameInfo = "机构+核算单位+部门应收账龄表";
                } else if (ids_str.equals("ArpTGPS")) {
                    nameInfo = "机构+核算单位+业务应收账龄表";
                } else if (ids_str.equals("ArpTGPGC")) {
                    nameInfo = "机构+核算单位+终端网点应收账龄表";
                } else if (ids_str.equals("ArpTGPCGC")) {
                    nameInfo = "机构+核算单位+渠道+终端网点应收账龄表";
                } else if (ids_str.equals("ArpTGPDGC")) {
                    nameInfo = "机构+核算单位+部门+终端网点应收账龄表";
                } else if (ids_str.equals("ArpTGPSGC")) {
                    nameInfo = "机构+核算单位+业务+终端网点应收账龄表";
                } else if (ids_str.equals("spmarp")) {
                    nameInfo = "应收账龄表";
                }
                Log.e("LiNing", "ids_str数据是===" + list_spm);
                item = new HashMap<String, Object>();
                item.put("序号", ids_str);
                itemRoot();
                dList.add(item);

                for (int i = 0; i < dList.size(); i++) {
                    map = new HashMap<String, String>();
                    item_del = dList.get(i);
                    str_dbname = item_del.get("账套").toString();
                    modIDs_get.add(dList.get(i).get("序号").toString());
                    modZTs_get.add(dList.get(i).get("账套").toString());
                    Log.e("LiNing", "LiNing--------" + modIDs_get
                            + "LiNing--------" + modZTs_get);
                    map.put(dList.get(i).get("账套").toString(), dList.get(i)
                            .get("序号").toString());

                }
                list.add(map);
            }
        } else if (idInfo.equals("sp")) {
            for (int j = 0; j < list_sp.size(); j++) {
                ids_str = list_sp.get(j);
                Log.e("LiNing", "ids_str数据是===" + ids_str);
                if (ids_str.equals("spmrt")) {
                    nameInfo = "收款统计表";
                } else if (ids_str.equals("spm")) {
                    nameInfo = "统计报表";
                } else if (ids_str.equals("sptrt")) {
                    nameInfo = "收款明细表";
                } else if (ids_str.equals("sptsa")) {
                    nameInfo = "销售明细表";
                } else if (ids_str.equals("spt")) {
                    nameInfo = "明细报表";
                } else if (ids_str.equals("sp")) {
                    nameInfo = "决策报表";
                } else if (ids_str.equals("SATG")) {
                    nameInfo = "分支机构销售统计表";
                } else if (ids_str.equals("SATGP")) {
                    nameInfo = "分支机构+品牌销售统计表";
                } else if (ids_str.equals("SATGPC")) {
                    nameInfo = "分支机构+品牌+渠道销售统计表";
                } else if (ids_str.equals("SATGPD")) {
                    nameInfo = "分支机构+品牌+部门销售统计表";
                }else if (ids_str.equals("SATGPCS")) {
                    nameInfo = "分支机构+品牌+业务销售统计表";
                } else if (ids_str.equals("SATGPGC")) {
                    nameInfo = "分支机构+品牌+终端网点销售统计表";
                } else if (ids_str.equals("SATGC")) {
                    nameInfo = "分支机构+渠道销售统计表";
                } else if (ids_str.equals("SATGCD")) {
                    nameInfo = "分支机构+渠道+部门销售统计表";
                } else if (ids_str.equals("SATGCS")) {
                    nameInfo = "分支机构+渠道+业务销售统计表";
                } else if (ids_str.equals("SATGCGC")) {
                    nameInfo = "分支机构+渠道+终端网点销售统计表";
                } else if (ids_str.equals("SATGD")) {
                    nameInfo = "分支机构+部门销售统计表";
                } else if (ids_str.equals("SATGDS")) {
                    nameInfo = "分支机构+部门+业务销售统计表";
                } else if (ids_str.equals("SATGDGC")) {
                    nameInfo = "分支机构+部门+终端网点销售统计表";
                } else if (ids_str.equals("SATGS")) {
                    nameInfo = "分支机构+业务销售统计表";
                } else if (ids_str.equals("SATGSGC")) {
                    nameInfo = "分支机构+业务+终端网点销售统计表";
                } else if (ids_str.equals("SATGGC")) {
                    nameInfo = "分支机构+终端网点销售统计表";
                } else if (ids_str.equals("SATGPPN")) {
                    nameInfo = "分支机构+品牌+品号销售统计表";
                } else if (ids_str.equals("SATGPI")) {
                    nameInfo = "分支机构+品牌+货品中类销售统计表";
                } else if (ids_str.equals("SATGPIPN")) {
                    nameInfo = "分支机构+品牌+货品中类+品号销售统计表";
                } else if (ids_str.equals("SATGPCPN")) {
                    nameInfo = "分支机构+品牌+渠道+品号销售统计表";
                } else if (ids_str.equals("SATGPCI")) {
                    nameInfo = "分支机构+品牌+渠道+货品中类销售统计表";
                } else if (ids_str.equals("SATGPCIPN")) {
                    nameInfo = "分支机构+品牌+渠道+货品中类+品号销售统计表";
                } else if (ids_str.equals("SATGPDPN")) {
                    nameInfo = "分支机构+品牌+部门+品号销售统计表";
                } else if (ids_str.equals("SATGPDI")) {
                    nameInfo = "分支机构+品牌+部门+品号+货品中类销售统计表";
                } else if (ids_str.equals("SATGPDIPN")) {
                    nameInfo = "分支机构+品牌+部门+品号+货品中类+品号销售统计表";
                } else if (ids_str.equals("SATGPSPN")) {
                    nameInfo = "分支机构+品牌+业务+品号销售统计表";
                } else if (ids_str.equals("SATGPSI")) {
                    nameInfo = "分支机构+品牌+业务+货品中类销售统计表";
                } else if (ids_str.equals("SATGPSIPN")) {
                    nameInfo = "分支机构+品牌+业务+货品中类+品号销售统计表";
                } else if (ids_str.equals("SATGPGCPN")) {
                    nameInfo = "分支机构+品牌+终端+品号销售统计表";
                } else if (ids_str.equals("SATGPGCI")) {
                    nameInfo = "分支机构+品牌+终端+货品中类销售统计表";
                } else if (ids_str.equals("SATGPGCIPN")) {
                    nameInfo = "分支机构+品牌+终端+货品中类+品号销售统计表";
                } else if (ids_str.equals("spmsa")) {
                    nameInfo = "销售统计表";
                } else if (ids_str.equals("ArpTG")) {
                    nameInfo = "分支机构应收账龄表";
                } else if (ids_str.equals("ArpTGGC")) {
                    nameInfo = "机构+终端网点应收账龄表";
                } else if (ids_str.equals("ArpTGP")) {
                    nameInfo = "机构+核算单位应收账龄表";
                } else if (ids_str.equals("ArpTGC")) {
                    nameInfo = "机构+渠道应收账龄表";
                } else if (ids_str.equals("ArpTGS")) {
                    nameInfo = "机构+业务应收账龄表";
                } else if (ids_str.equals("ArpTGD")) {
                    nameInfo = "机构+部门应收账龄表";
                } else if (ids_str.equals("ArpTGPC")) {
                    nameInfo = "机构+核算单位+渠道应收账龄表";
                } else if (ids_str.equals("ArpTGPD")) {
                    nameInfo = "机构+核算单位+部门应收账龄表";
                } else if (ids_str.equals("ArpTGPS")) {
                    nameInfo = "机构+核算单位+业务应收账龄表";
                } else if (ids_str.equals("ArpTGPGC")) {
                    nameInfo = "机构+核算单位+终端网点应收账龄表";
                } else if (ids_str.equals("ArpTGPCGC")) {
                    nameInfo = "机构+核算单位+渠道+终端网点应收账龄表";
                } else if (ids_str.equals("ArpTGPDGC")) {
                    nameInfo = "机构+核算单位+部门+终端网点应收账龄表";
                } else if (ids_str.equals("ArpTGPSGC")) {
                    nameInfo = "机构+核算单位+业务+终端网点应收账龄表";
                } else if (ids_str.equals("spmarp")) {
                    nameInfo = "应收账龄表";
                }

                Log.e("LiNing", "ids_str数据是===" + list_sp);
                item = new HashMap<String, Object>();
                item.put("序号", ids_str);
                itemRoot();
                dList.add(item);

                for (int i = 0; i < dList.size(); i++) {
                    map = new HashMap<String, String>();
                    item_del = dList.get(i);
                    str_dbname = item_del.get("账套").toString();
                    modIDs_get.add(dList.get(i).get("序号").toString());
                    modZTs_get.add(dList.get(i).get("账套").toString());
                    Log.e("LiNing", "LiNing--------" + modIDs_get
                            + "LiNing--------" + modZTs_get);
                    map.put(dList.get(i).get("账套").toString(), dList.get(i)
                            .get("序号").toString());

                }
                list.add(map);
            }
        } else if (idInfo.equals("spt")) {
            for (int j = 0; j < list_spt.size(); j++) {
                ids_str = list_spt.get(j);
                Log.e("LiNing", "ids_str数据是===" + ids_str);
                if (ids_str.equals("sptrt")) {
                    nameInfo = "收款明细表";
                } else if (ids_str.equals("sptsa")) {
                    nameInfo = "销售明细表";
                } else if (ids_str.equals("spt")) {
                    nameInfo = "明细报表";
                }
                Log.e("LiNing", "ids_str数据是===" + list_spm);
                item = new HashMap<String, Object>();
                item.put("序号", ids_str);
                itemRoot();
                dList.add(item);

                for (int i = 0; i < dList.size(); i++) {
                    map = new HashMap<String, String>();
                    item_del = dList.get(i);
                    str_dbname = item_del.get("账套").toString();
                    modIDs_get.add(dList.get(i).get("序号").toString());
                    modZTs_get.add(dList.get(i).get("账套").toString());
                    Log.e("LiNing", "LiNing--------" + modIDs_get
                            + "LiNing--------" + modZTs_get);
                    map.put(dList.get(i).get("账套").toString(), dList.get(i)
                            .get("序号").toString());

                }
                list.add(map);
            }
        } else {
            item = new HashMap<String, Object>();
            item.put("序号", idInfo);
            itemRoot();
            dList.add(item);
            for (int i = 0; i < dList.size(); i++) {
                map = new HashMap<String, String>();
                item_del = dList.get(i);
                str_dbname = item_del.get("账套").toString();
                modIDs_get.add(dList.get(i).get("序号").toString());
                modZTs_get.add(dList.get(i).get("账套").toString());
                Log.e("LiNing", "LiNing--------" + modIDs_get
                        + "LiNing--------" + modZTs_get);
                map.put(dList.get(i).get("账套").toString(),
                        dList.get(i).get("序号").toString());

            }
            list.add(map);
        }

        // dList.add(item_more);

    }

    private void itemRoot() {
        item.put("名称", nameInfo);
        item.put("账套", ztInfo);
        item.put("查询", queryInfo);
        item.put("增加", addInfo);
        item.put("更新", setInfo);
        item.put("删除", delInfo);
        item.put("打印", prtInfo);
        item.put("转出", outInfo);
        item.put("数量", cstInfo);
        item.put("单价", upInfo);
        item.put("金额", amtInfo);
        item.put("成本", qtyInfo);
        item.put("毛利", gpInfo);
        item.put("毛利率", gprInfo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    String mod_id_date = data.getStringExtra("data_id");
                    id.setText(mod_id_date);
                    String mod_name_date = data.getStringExtra("data_name");
                    name.setText(mod_name_date);
                    String mod_level_date = data.getStringExtra("data_level");
                    level.setText(mod_level_date);
                    // sp.edit().putString("ACCOUNTID", best_db).commit();
                    Log.e("LiNing", "提交的id====" + id);
                }
                break;

            default:
                break;
        }
    }

    private void postInfo() {
        uSERZT = sp.getString("USERZTID", "");
        Log.e("LiNing", "USERZT数据是===" + uSERZT);
        uSERNAME = sp.getString("USERNAME", "");
        Log.e("LiNing", "USERNAME数据是===" + uSERNAME);
        uSERPWD = sp.getString("USERPWD", "");
        Log.e("LiNing", "USERPWD数据是===" + uSERPWD);
        uSERACCOUNT = sp.getString("ACCOUNTID", "");
        Log.e("LiNing", "USERACCOUNT数据是===" + uSERACCOUNT);
        uSERDEP = sp.getString("DEPID", "");
        Log.e("LiNing", "USERDEP数据是===" + uSERDEP);
        uSERCUST = sp.getString("CUSTID", "");
        Log.e("LiNing", "USERCUST数据是===" + uSERCUST);
        uSERERPUSER = sp.getString("USERID", "");
        Log.e("LiNing", "USERERPUSER数据是===" + uSERERPUSER);
        image = sp.getString("photoURL", "");
        Log.e("LiNing", "image数据是===" + image);

        if (image == null) {
            Toast.makeText(context, "请添加照片", Toast.LENGTH_LONG).show();
            // file_sighn = new File("IMG_20180124_011924.jpg");
        } else {
            file_sighn = new File(image);
        }
        Log.e("LiNing", "file_sighn数据是===" + file_sighn);
        // imageER = sp.getString("imageER", "");
        deviceID = sp.getString("DEVICEID_et", "");
        sighnpwd = sp.getString("SIGHN_pwd", "");
        // Log.e("LiNing", "imageER数据是===" + imageER);
        // 赋权限数据
        // String strQuerry = sp.getString("NewQuery", "");
        String strQuerry = sp.getString("NewMod", "");
        Log.e("LiNing", "strQuerry数据是===" + strQuerry);

        modIDs = new ArrayList<String>();
        // modIDs.put(uSERACCOUNT.toString());
        // Log.e("LiNing", "mod_db[]=======" + modIDs);//数组
        listIDs = new ArrayList<String>();
        listNAMEs = new ArrayList<String>();
        listQUERYs = new ArrayList<String>();
        listADDs = new ArrayList<String>();
        listAMTs = new ArrayList<String>();
        listCSTs = new ArrayList<String>();
        listDELs = new ArrayList<String>();
        listPRs = new ArrayList<String>();
        listPRGs = new ArrayList<String>();
        listPRTs = new ArrayList<String>();
        listQTYs = new ArrayList<String>();
        listSETs = new ArrayList<String>();
        listUPs = new ArrayList<String>();
        listOUTs = new ArrayList<String>();

        try {
            JSONArray jsonArray = new JSONArray(strQuerry);
            int iSize = jsonArray.length();
            System.out.println("Size:" + iSize);
            for (int i = 0; i < iSize; i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                modIDs.add(jsonObj.get("序号").toString());
                listIDs.add(jsonObj.get("账套").toString());
                listNAMEs.add(jsonObj.get("名称").toString());
                listQUERYs.add(jsonObj.get("查询").toString());
                listADDs.add(jsonObj.get("增加").toString());
                listAMTs.add(jsonObj.get("金额").toString());
                listCSTs.add(jsonObj.get("数量").toString());
                listDELs.add(jsonObj.get("删除").toString());
                listOUTs.add(jsonObj.get("转出").toString());
                listPRs.add(jsonObj.get("毛利").toString());
                listPRGs.add(jsonObj.get("毛利率").toString());
                listPRTs.add(jsonObj.get("打印").toString());
                listQTYs.add(jsonObj.get("成本").toString());
                listSETs.add(jsonObj.get("更新").toString());
                listUPs.add(jsonObj.get("单价").toString());

            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        Log.e("LiNing", "listIDs数据是===" + listIDs + listQUERYs);
        // 查询权限数据

        // String QuerryRoot = sp.getString("QuerryRoot", "");
        String QuerryRoot = sp.getString("NewQuery", "");
        Log.e("LiNing", "QuerryRoot数据是===" + QuerryRoot);
        querryDBs = new ArrayList<String>();
        querrydeps = new ArrayList<String>();
        querrycusts = new ArrayList<String>();
        querrysups = new ArrayList<String>();
        querryusers = new ArrayList<String>();
        querryhs = new ArrayList<String>();

        try {
            JSONArray arr = new JSONArray(QuerryRoot);
            int size = arr.length();
            for (int i = 0; i < size; i++) {
                JSONObject jsonObject = arr.getJSONObject(i);
                if (jsonObject.get("账套编号").toString().equals("")) {
                    querryDBs.add(null);
                } else {

                    querryDBs.add(jsonObject.get("账套编号").toString());
                }
                if (jsonObject.get("部门").toString().equals("")) {
                    querrydeps.add(null);
                } else {

                    querrydeps.add(jsonObject.get("部门").toString());
                }
                if (jsonObject.get("供应商").toString().equals("")) {
                    querrycusts.add(null);
                } else {

                    querrycusts.add(jsonObject.get("供应商").toString());
                }
                if (jsonObject.get("客户").toString().equals("")) {
                    querrysups.add(null);
                } else {

                    querrysups.add(jsonObject.get("客户").toString());
                }

                if (jsonObject.get("用户").toString().equals("")) {
                    querryusers.add(null);
                } else {

                    querryusers.add(jsonObject.get("用户").toString());
                }
                if (jsonObject.get("核算单位").toString().equals("")) {
                    querryhs.add(null);
                } else {

                    querryhs.add(jsonObject.get("核算单位").toString());
                }
                // querryDBs.add(jsonObject.get("账套编号").toString());
                // querryusers.add(jsonObject.get("用户").toString());
                // querrysups.add(jsonObject.get("客户").toString());
                // querrycusts.add(jsonObject.get("供应商").toString());
                // querrydeps.add(jsonObject.get("部门").toString());

            }
            Log.e("LiNing", "QuerryRoot数据是===" + querryDBs + "----"
                    + querrydeps);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        postAllInfo();
    }

    private void postAllInfo() {
        new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    // 创建一个URL对象
                    URL url = new URL(urlUser);
                    Map<String, String> textParams_user = new HashMap<String, String>();
                    Map<String, String> textParams = new HashMap<String, String>();
                    Map<String, File> fileparams = new HashMap<String, File>();
                    // 要上传的普通参数
                    // 用户参数
                    textParams_user.put("user_Id", uSERZT);
                    textParams_user.put("user_Name", uSERNAME);
                    textParams_user.put("user_Pwd", uSERPWD);
                    textParams_user.put("user_DB", uSERACCOUNT);// user_DB;//
                    // 默认数据库
                    textParams_user.put("user_Dep", uSERDEP);// user_Dep;// 默认部门
                    textParams_user.put("user_Cust", uSERCUST);
                    textParams_user.put("user_erpUser", uSERERPUSER);// 默认ERP用户
                    textParams_user.put("Device_Id", deviceID);//device_Id
                    textParams_user.put("SignaturePWD", sighnpwd);//signaturePWD
                    Log.e("LiNing", "user数据*******" + textParams_user);

                    // 权限参数


                    String zts_str = "";
                    for (String zt : modIDs) {
                        zts_str += zt + ",";
                    }
                    String sub_id = zts_str.substring(0, zts_str.length() - 1);
                    String ids_str = "";
                    for (String id : listIDs) {
                        ids_str += id + ",";
                    }
                    String sub_zt = ids_str.substring(0, ids_str.length() - 1);
                    // json_id = net.sf.json.JSONArray.fromObject(ids_str);
                    String names_str = "";
                    for (String name : listNAMEs) {
                        names_str += name + ",";
                    }
                    String sub_name = names_str.substring(0,
                            names_str.length() - 1);
                    String querys_str = "";
                    for (String query : listQUERYs) {
                        querys_str += query + ",";
                    }
                    String sub_query = querys_str.substring(0,
                            querys_str.length() - 1);
                    String adds_str = "";
                    for (String add : listADDs) {
                        adds_str += add + ",";
                    }
                    String sub_add = adds_str.substring(0,
                            adds_str.length() - 1);
                    String alters_str = "";
                    for (String alt : listSETs) {
                        alters_str += alt + ",";
                    }
                    String sub_alt = alters_str.substring(0,
                            alters_str.length() - 1);
                    String dels_str = "";
                    for (String del : listDELs) {
                        dels_str += del + ",";
                    }
                    String sub_del = dels_str.substring(0,
                            dels_str.length() - 1);
                    String prt_str = "";
                    for (String prt : listPRTs) {
                        prt_str += prt + ",";
                    }
                    String sub_prt = prt_str.substring(0, prt_str.length() - 1);
                    String out_str = "";
                    for (String out : listOUTs) {
                        out_str += out + ",";
                    }
                    String sub_out = out_str.substring(0, out_str.length() - 1);
                    String qty_str = "";
                    for (String qty : listQTYs) {
                        qty_str += qty + ",";
                    }
                    String sub_qty = qty_str.substring(0, qty_str.length() - 1);
                    String up_str = "";
                    for (String up : listUPs) {
                        up_str += up + ",";
                    }
                    String sub_up = up_str.substring(0, up_str.length() - 1);
                    String amt_str = "";
                    for (String amt : listAMTs) {
                        amt_str += amt + ",";
                    }
                    String sub_amt = amt_str.substring(0, amt_str.length() - 1);
                    String cst_str = "";
                    for (String cst : listCSTs) {
                        cst_str += cst + ",";
                    }
                    String sub_cst = cst_str.substring(0, cst_str.length() - 1);
                    String gp_str = "";
                    for (String gp : listPRs) {
                        gp_str += gp + ",";
                    }
                    String sub_gp = gp_str.substring(0, gp_str.length() - 1);
                    String gpr_str = "";
                    for (String gpr : listPRGs) {
                        gpr_str += gpr + ",";
                    }
                    String sub_gpr = gp_str.substring(0, gp_str.length() - 1);
//                    Log.e("LiNing", "qqqqqqqqq=====" + sub_id);// listIDs无引号
                    textParams.put("isApp", "T");
                    textParams.put("mod_ID", sub_id);// mod_ID;// 模块编号
                    textParams.put("mod_DB", sub_zt);// mod_DB;//账套编号
                    textParams.put("mod_Name", sub_name);// mod_Name;// 模块名称
                    textParams.put("mod_Query", sub_query);// mod_Query;// 查看
                    textParams.put("mod_Add", sub_add);// mod_Add;// 新增
                    textParams.put("mod_Alter", sub_alt);// mod_Alter;// 修改
                    textParams.put("mod_Del", sub_del);// mod_Del;// 删除
                    textParams.put("mod_Prt", sub_prt);// mod_Prt;// 打印
                    textParams.put("mod_Out", sub_out);// mod_Out;// 导出
                    textParams.put("mod_Qty", sub_cst);// mod_Qty;// 数量
                    textParams.put("mod_Up", sub_up);// mod_Up;// 单价
                    textParams.put("mod_Amt", sub_amt);// mod_Amt;// 金额
                    textParams.put("mod_Cst", sub_qty);// mod_Cst;// 成本
                    textParams.put("mod_GP", sub_gp);// mod_GP;// 毛利
                    textParams.put("mod_GPR", sub_gpr);// mod_GPR;// 毛利率
                    Log.e("LiNing", "sub_name=====" + sub_name);// listIDs无引号
                    String querys_db_str = "";
                    for (String querys_db : querryDBs) {
                        querys_db_str += querys_db + ",";
                    }
                    String sub_querys_db = querys_db_str.substring(0,
                            querys_db_str.length() - 1);
                    String querys_dep_str = "";
                    for (String querys_dep : querrydeps) {
                        querys_dep_str += querys_dep + ";";
                    }
                    String sub_querys_dep = querys_dep_str.substring(0,
                            querys_dep_str.length() - 1);
                    String querys_sup_str = "";
                    for (String querys_sup : querrysups) {
                        querys_sup_str += querys_sup + ";";
                    }
                    String sub_querys_sup = querys_sup_str.substring(0,
                            querys_sup_str.length() - 1);
                    String querys_cust_str = "";
                    for (String querys_cust : querrycusts) {
                        querys_cust_str += querys_cust + ";";
                    }
                    String sub_querys_cust = querys_cust_str.substring(0,
                            querys_cust_str.length() - 1);
                    String querys_user_str = "";
                    for (String querys_user : querryusers) {
                        querys_user_str += querys_user + ";";
                    }
                    String sub_querys_user = querys_user_str.substring(0,
                            querys_user_str.length() - 1);
                    String querys_hs_str = "";
                    for (String querys_hs : querryhs) {
                        querys_hs_str += querys_hs + ";";
                    }
                    String sub_querys_hs = querys_hs_str.substring(0,
                            querys_hs_str.length() - 1);
                    textParams.put("query_DB", sub_querys_db);// // 帐套编号
                    textParams.put("query_Dep", sub_querys_dep);// 查询部门
                    textParams.put("query_Cust", sub_querys_sup);// 查询供应商
                    textParams.put("query_Sup", sub_querys_cust);// 查询客户
                    textParams.put("query_User", sub_querys_user);// 查询用户
                    textParams.put("query_CompDep", sub_querys_hs);// 查询核算单位
                    Log.e("LiNing", "模块和查询的***********" + textParams);
                    if (!file_sighn.equals("") && file_sighn != null
                            && file_sighn.exists()) {

                        fileparams.put("Signature", file_sighn);
                    }
                    Log.e("LiNing", "签章文件***********" + fileparams);
                    // 利用HttpURLConnection对象从网络中获取网页数据
                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    // 添加表头
                    conn.addRequestProperty("cookie", session);
                    // 设置连接超时（记得设置连接超时,如果网络不好,Android系统在超过默认时间会收回资源中断操作）
                    conn.setConnectTimeout(5000);
                    // 设置允许输出（发送POST请求必须设置允许输出）
                    conn.setDoOutput(true);
                    // 设置使用POST的方式发送
                    conn.setRequestMethod("POST");
                    // 设置不使用缓存（容易出现问题）
                    conn.setUseCaches(false);
                    // 在开始用HttpURLConnection对象的setRequestProperty()设置,就是生成HTML文件头
                    conn.setRequestProperty("ser-Agent", "Fiddler");
                    // 设置contentType
                    conn.setRequestProperty("Content-Type",
                            "multipart/form-data; boundary=" + NetUtil.BOUNDARY);
                    OutputStream os = conn.getOutputStream();
                    DataOutputStream ds = new DataOutputStream(os);
                    NetUtil.writeStringParams(textParams_user, ds);
                    NetUtil.writeStringParams(textParams, ds);
                    Log.e("LiNing", "ddddddddddd" + fileparams.get("Signature"));
                    //图片上传判断（此处很重要）
                    if (!file_sighn.equals("") && file_sighn != null
                            && file_sighn.exists()) {

                        NetUtil.writeFileParams(fileparams, ds);
                    }
                    NetUtil.paramsEnd(ds);
                    // 对文件流操作完,要记得及时关闭
                    os.close();
                    // 服务器返回的响应吗
                    int code = conn.getResponseCode(); // 从Internet获取网页,发送请求,将网页以流的形式读回来
                    Log.e("LiNing", "ddddddddddd" + code);
                    // 对响应码进行判断
                    if (code == 200) {// 返回的响应码200,是成功
                        // 得到网络返回的输入流
                        InputStream is = conn.getInputStream();
                        resultStr = NetUtil.readString(is);
                        Log.e("LiNing", "ddddddddddd" + resultStr);
                        // startActivity(new Intent(context,
                        // CusterInfoActivity.class));
                        // finish();
                        BackApplication.getInstance().exit();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);// 执行耗时的方法之后发送消给handler
            }
        }).start();
    }

    public void allback(View v) {
        finish();
    }

    public void postOk(View v) {
        String stritem = new Gson().toJson(dList);
        Log.e("LiNing", "item数据是===111" + stritem);
        sp.edit().putString("NewMod", stritem).commit();
        postInfo();
    }
}
