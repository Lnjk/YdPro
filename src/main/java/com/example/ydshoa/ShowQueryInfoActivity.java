package com.example.ydshoa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.example.adapter.UserQueryAdapter.ViewHolder;
import com.example.adapter.UserModAdapter;
import com.example.adapter.UserQueryAdapter;
import com.example.bean.CusterInfoDB;
import com.example.bean.CusterInfoDB.User_Mod;
import com.example.bean.CusterInfoDB.User_Query;
import com.example.bean.CusterInfoDB.Users;
import com.example.bean.URLS;
import com.example.ydshoa.CusterInfoActivity.CheckBoxListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
//import com.umeng.message.PushAgent;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ShowQueryInfoActivity extends Activity implements OnClickListener {
    private LinearLayout half_info, half_two;
    private TextView head;
    private ListView queryList;
    private Context context;
    private SharedPreferences sp;
    private String session;
    private UserQueryAdapter adapter;
    // String urlGet =
    // "http://192.168.2.178:8080/InfManagePlatform/UsergetUsers.action";
    String urlGet = URLS.all_user_url;
    private List<User_Query> userQuery;
    private TableLayout tvText;
    private int result, flag, lntag;
    private TextView tvDep, tvSup, tvCust, tvUser, tvAccount, tvHs;
    private String depCopy, custCopy, nameCopy, supCopy, usrCopy, usrHs;
    private ImageButton complete;
    private Button add, reset, del;
    private String db_exar, dep_exar, sup_exar, cust_exar, user_exar, hs_exar;
    private Spinner spAccpount;
    private HashMap<String, String> item;
    private ArrayList<HashMap<String, Object>> dList;
    private User_Query item2;
    private ImageButton rootDep, rootAccount, rootSup, rootCust, rooUser,
            rooHs;
    private TextView lv_cust, lv_ztid, lv_user, lv_sup, lv_dep, lv_hs;
    private Button queryAddOk;
    private String str1id, str2id, str3id, str4id, str5id, straccount;
    private TableRow tvNull;
    private String dB_comit,idsUser;
    private String test_db;
    private List<CheckBox> checkBoxList;
    private List<Integer> checkedIndexList;
    private boolean checked;
    ArrayList<String> queryZTs_get = new ArrayList<String>();
    private int yc;
    private int dj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_show_query_info);
        context = ShowQueryInfoActivity.this;
//        PushAgent.getInstance(context).onAppStart();
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        checkBoxList = new ArrayList<CheckBox>();
        checkedIndexList = new ArrayList<Integer>();
        initView();
    }

    private void initView() {
        half_info = (LinearLayout) findViewById(R.id.ll_modquery_go);
        half_two = (LinearLayout) findViewById(R.id.ll_two);
        head = (TextView) findViewById(R.id.all_head);
        head.setText("查询权限");
        queryList = (ListView) findViewById(R.id.lv_modquerry_header);
        tvText = ((TableLayout) findViewById(R.id.stock_list_item_table_layout));
        idsUser = getIntent().getStringExtra("userId");
        String info_flag = getIntent().getStringExtra("QUERY_FLAG");
        String stringExtra = getIntent().getStringExtra("INFO_int");
        result = Integer.valueOf(stringExtra);
        Log.e("LiNing", "result----" + result);
        Log.e("LiNing", "info_flag----" + info_flag);
        if (info_flag.equals("queryset")) {

            half_two.setVisibility(View.VISIBLE);
            flag = 6;
            getinfos();
        } else {
            yc = 1;
            findViewById(R.id.tv_quy_xz).setVisibility(View.GONE);
            half_two.setVisibility(View.GONE);
            getinfos();
        }
        // getinfos();
        tvAccount = (TextView) findViewById(R.id.et_modquerry_zt);
        tvDep = (TextView) findViewById(R.id.et_modquerry_dep);
        tvSup = (TextView) findViewById(R.id.et_modquerry_sup);
        tvCust = (TextView) findViewById(R.id.et_modquerry_cust);
        tvUser = (TextView) findViewById(R.id.et_modquerry_user);
        tvHs = (TextView) findViewById(R.id.et_modquerry_hs);
        add = ((Button) findViewById(R.id.ibbtn_modquerry_add));
        reset = ((Button) findViewById(R.id.ibbtn_modquerry_reset));
        del = ((Button) findViewById(R.id.ibbtn_modquerry_del));
        add.setOnClickListener(this);
        reset.setOnClickListener(this);
        del.setOnClickListener(this);
        dList = new ArrayList();
        queryList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int positionLN, long id) {
                // flag = 1;
                // iln = positionLN;
                // myAdapter.setSelectedPosition(positionLN);
                // myAdapter.notifyDataSetChanged();
                // myAdapter.notifyDataSetInvalidated();
                // 将checkbox设置为可见
//				for (int i = 0; i < checkBoxList.size(); i++) {
//					checkBoxList.get(i).setVisibility(View.VISIBLE);
//				}
            }
        });
        // item获取
        // queryList.setOnItemClickListener(new OnItemClickListener() {
        //
        // private Integer index;
        //
        // @Override
        // public void onItemClick(AdapterView<?> parent, View view,
        // int position, long id) {
        //
        // ViewHolder tag = (ViewHolder) view.getTag();
        // tag.checkBox.toggle();
        // checked = tag.checkBox.isChecked();
        // Log.e("LiNing", "-----111" + checked);
        // UserQueryAdapter.isSelected.put(position, checked);
        // if (checked == true) {
        // lntag = 1;
        // checkedIndexList.add(position);
        // Log.e("LiNing", "-----111" + checkedIndexList);
        //
        // } else {
        // lntag = 0;
        // checkedIndexList.remove((Integer) position);// 删除时切记转换，不然报错
        // Log.e("LiNing", "-----222" + checkedIndexList);
        // adapter.notifyDataSetChanged();
        // }
        // if (0 < checkedIndexList.size() && checkedIndexList.size() <= 1) {
        // for (int i = 0; i < checkedIndexList.size(); i++) {
        // index = checkedIndexList.get(i);
        // }
        // Log.e("LiNing", "index-----111" + index);
        // item2 = userQuery.get(index);
        // db_exar = item2.getQuery_DB();
        // dep_exar = item2.getQuery_Dep();
        // sup_exar = item2.getQuery_Sup();
        // cust_exar = item2.getQuery_Cust();
        // user_exar = item2.getQuery_User();
        // hs_exar = item2.getQuery_CompDep();
        // Log.e("LiNing", "-----111---item2" + item2);
        // } else {
        // Toast.makeText(context, "修改时请选择单条数据", 1).show();
        // }
        // queryList.setSelector(R.color.color_white);
        // }
        // });

    }

    private void getinfos() {
        tvText.setVisibility(View.GONE);
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("user_Id",idsUser)
                .build();
        client.newCall(
                new Request.Builder().addHeader("cookie", session).url(urlGet)
                        .post(body).build()).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                String str = response.body().string();
                Log.e("LiNing", "--------" + str);
                // 解析包含date的数据必须添加此代码(InputStream型)
                Gson gson = new GsonBuilder().setDateFormat(
                        "yyyy-MM-dd HH:mm:ss").create();
                final CusterInfoDB cInfoDB = gson.fromJson(str,
                        CusterInfoDB.class);
                if (cInfoDB != null) {
                    ShowQueryInfoActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
//                            userQuery = cInfoDB.getUsers().get(result)
//                                    .getUser_Query();
                            userQuery = cInfoDB.getUsers().get(0)
                                    .getUser_Query();
                            Log.e("LiNing",
                                    "userMod--------" + userQuery.toString());

                            adapter = new UserQueryAdapter(yc, context,
                                    userQuery, R.layout.querry_list_head_item);
                            queryList.setAdapter(adapter);
                            adapter.notifyDataSetInvalidated();
                            // saveInfo();
                        }

                    });

                }
            }

            @Override
            public void onFailure(Call arg0, IOException arg1) {

            }
        });
    }

    // 封装提交前数据
    private void saveInfo() {
        if (queryList.getCount() > 0) {

            ArrayList<String> list_id = new ArrayList<String>();
            ArrayList<String> listdep = new ArrayList<String>();
            ArrayList<String> listsup = new ArrayList<String>();
            ArrayList<String> listcust = new ArrayList<String>();
            ArrayList<String> listuser = new ArrayList<String>();
            ArrayList<String> lisths = new ArrayList<String>();

            for (int i = 0; i < userQuery.size(); i++) {
                dB_comit = userQuery.get(i).getQuery_DB();
                if (dB_comit == null&&dB_comit.equals("")) {
                    list_id.add("null");
                } else {

                    list_id.add(dB_comit);
                }
                String dep_comit = userQuery.get(i).getQuery_Dep();
                Log.e("LiNing", "=======" + dep_comit);
                if (dep_comit == null&&dep_comit.equals("")) {
                    listdep.add("null");
                } else {

                    listdep.add(dep_comit);
                }

                String sup_comit = userQuery.get(i).getQuery_Sup();
                if (sup_comit == null&&sup_comit.equals("")) {
                    listsup.add("null");
                } else {

                    listsup.add(sup_comit);
                }
                String hs_comit = userQuery.get(i).getQuery_CompDep();
                if (hs_comit == null&&hs_comit.equals("")) {
                    lisths.add("null");
                } else {

                    lisths.add(hs_comit);
                }

                String cust_comit = userQuery.get(i).getQuery_Cust();
                if (cust_comit == null&&cust_comit.equals("")) {
                    listcust.add("null");
                } else {

                    listcust.add(cust_comit);
                }

                String user_comit = userQuery.get(i).getQuery_User();
                if (user_comit == null&&user_comit.equals("")) {
                    listuser.add("null");
                } else {

                    listuser.add(user_comit);
                }

            }
            sp.edit()
                    .putString("sp_query_new",
                            "" + new Gson().toJson(userQuery)).commit();
            Log.e("LiNing", "得到数据-----userQuery" + userQuery.size());
            Log.e("LiNing", "得到数据-----userQuery" + userQuery);
            Log.e("LiNing", "得到数据-----list" + list_id + "///" + listdep + "///"
                    + lisths);
            String querys_id_str = "";
            for (String querys_id : list_id) {
                querys_id_str += querys_id + ",";
            }
            String ids = querys_id_str.substring(0, querys_id_str.length() - 1);
            String querys_dep_str = "";
            for (String querys_dep : listdep) {
                querys_dep_str += querys_dep + ";";
            }
            String deps = querys_dep_str.substring(0,
                    querys_dep_str.length() - 1);
            String querys_sup_str = "";
            for (String querys_sup : listsup) {
                querys_sup_str += querys_sup + ";";
            }
            String sups = querys_sup_str.substring(0,
                    querys_sup_str.length() - 1);
            String querys_hs_str = "";
            for (String querys_hs : lisths) {
                querys_hs_str += querys_hs + ";";
            }
            String hs = querys_hs_str.substring(0, querys_hs_str.length() - 1);
            String querys_cust_str = "";
            for (String querys_cust : listcust) {
                querys_cust_str += querys_cust + ";";
            }
            String custs = querys_cust_str.substring(0,
                    querys_cust_str.length() - 1);
            String querys_user_str = "";
            for (String querys_user : listuser) {
                querys_user_str += querys_user + ";";
            }
            String users = querys_user_str.substring(0,
                    querys_user_str.length() - 1);
            // 数据提交前封装（查询）
            Editor edit2 = sp.edit();
            edit2.putString("queryinfo_id", ids).commit();
            edit2.putString("queryinfo_dep", deps).commit();
            edit2.putString("queryinfo_sup", sups).commit();
            edit2.putString("queryinfo_cust", custs).commit();
            edit2.putString("queryinfo_user", users).commit();
            edit2.putString("queryinfo_hs", hs).commit();
            Log.e("LiNing", "得到数据======list" + ids + "///" + deps + "///" + hs);
            finish();
        } else {
            Toast.makeText(context, "无数据", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void myinfoOut(View v) {
        saveInfo();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibbtn_modquerry_add:
                flag = 1;
                half_info.setVisibility(View.VISIBLE);
                initView_new();

                break;
            // 新增
            case R.id.btn_query_new:
                if (tvAccount.getText().toString().equals("")) {
                    Toast.makeText(context, "账套不能为空", Toast.LENGTH_LONG).show();
                } else {
                    ArrayList<String> queryZTs_get = new ArrayList<String>();
                    if (userQuery.size() > 0 && userQuery != null) {
                        for (int i = 0; i < userQuery.size(); i++) {
                            queryZTs_get.add(userQuery.get(i).getQuery_DB()
                                    .toString());
                        }
                        Log.e("LiNing", "LiNing--------" + queryZTs_get);
                    }
                    if (flag == 1) {
                        // User_Query query = new User_Query(straccount, str2id,
                        // str4id, str3id, str5id, str1id);
                        User_Query query = new User_Query(tvAccount.getText()
                                .toString(), tvCust.getText().toString(), tvDep.getText().toString(), tvSup
                                .getText().toString(), tvHs.getText()
                                .toString(),
                                tvUser.getText().toString());
                        if (!queryZTs_get.contains(tvAccount.getText().toString())) {
                            userQuery.add(0, query);
                            tvNull.setVisibility(View.GONE);

//                            tvAccount.setText(null);
//                            tvDep.setText(null);
//                            tvSup.setText(null);
//                            tvCust.setText(null);
//                            tvUser.setText(null);
//                            tvHs.setText(null);
                            tvAccount.setText("null");
                            tvDep.setText("null");
                            tvSup.setText("null");
                            tvCust.setText("null");
                            tvUser.setText("null");
                            tvHs.setText("null");
                            lntag = 0;
                            adapter.notifyDataSetInvalidated();
                            queryList.invalidate();
                            Log.e("LiNing", "LiNing-----NEW---" + queryZTs_get);
                        } else {
                            Toast.makeText(context, "此模块已存在", Toast.LENGTH_LONG).show();
                        }

                        // userQuery.add(0, query);
                        // adapter.notifyDataSetChanged();
                        // queryList.invalidate();
                        // tvAccount.setText(null);
                        // tvDep.setText(null);
                        // tvSup.setText(null);
                        // tvCust.setText(null);
                        // tvUser.setText(null);
                        // lntag = 0;
                    }

                    if (flag == 3) {
                        // User_Query query = new User_Query(straccount, str2id,
                        // str4id, str3id, str5id, str1id);
                        User_Query query = new User_Query(tvAccount.getText()
                                .toString(), tvCust.getText().toString(), tvDep.getText().toString(), tvSup
                                .getText().toString(), tvHs.getText()
                                .toString(),
                                tvUser.getText().toString());
                        if (!item2.getQuery_DB().equals(
                                tvAccount.getText().toString())) {

                            if (!queryZTs_get.contains(tvAccount.getText()
                                    .toString())) {
                                userQuery.add(0, query);
                                tvNull.setVisibility(View.GONE);

//                                tvAccount.setText(null);
//                                tvDep.setText(null);
//                                tvSup.setText(null);
//                                tvCust.setText(null);
//                                tvUser.setText(null);
//                                tvHs.setText(null);
                                tvAccount.setText("null");
                                tvDep.setText("null");
                                tvSup.setText("null");
                                tvCust.setText("null");
                                tvUser.setText("null");
                                tvHs.setText("null");
                                lntag = 0;
                                adapter.notifyDataSetInvalidated();
                                queryList.invalidate();
                                Log.e("LiNing", "LiNing-----NEW---" + queryZTs_get);
                                for (int i = 0; i < checkBoxList.size(); i++) {
                                    checkBoxList.get(i).setChecked(false);
                                }
                            } else {
                                Toast.makeText(context, "此模块已存在", Toast.LENGTH_LONG).show();
                            }
                        } else {

                            userQuery.remove(item2);

                            userQuery.add(0, query);
                            tvNull.setVisibility(View.GONE);

//                            tvAccount.setText(null);
//                            tvDep.setText(null);
//                            tvSup.setText(null);
//                            tvCust.setText(null);
//                            tvUser.setText(null);
//                            tvHs.setText(null);
                            tvAccount.setText("null");
                            tvDep.setText("null");
                            tvSup.setText("null");
                            tvCust.setText("null");
                            tvUser.setText("null");
                            tvHs.setText("null");
                            lntag = 0;
                            adapter.notifyDataSetChanged();
                            queryList.invalidate();
                            for (int i = 0; i < checkBoxList.size(); i++) {
                                checkBoxList.get(i).setChecked(false);
                            }
                        }
                    }
                }
                break;
            case R.id.ibbtn_modquerry_reset:
                if (queryList.getCount() > 0) {
                    flag = 3;
                    initView_new();
                    if (0 < checkedIndexList.size() && checkedIndexList.size() <= 1) {
                        tvNull.setVisibility(View.GONE);
                        half_info.setVisibility(View.VISIBLE);
                        tvAccount.setText(db_exar);
                        tvDep.setText(dep_exar);
                        tvSup.setText(sup_exar);
                        tvCust.setText(cust_exar);
                        tvUser.setText(user_exar);
                        tvHs.setText(hs_exar);
                    } else {
                        Toast.makeText(this.context, "请选择一条数据", Toast.LENGTH_LONG).show();
//                        tvAccount.setText(null);
//                        tvDep.setText(null);
//                        tvSup.setText(null);
//                        tvCust.setText(null);
//                        tvUser.setText(null);
//                        tvHs.setText(null);
                        tvAccount.setText("null");
                        tvDep.setText("null");
                        tvSup.setText("null");
                        tvCust.setText("null");
                        tvUser.setText("null");
                        tvHs.setText("null");
                    }

                } else {
                    Toast.makeText(this.context, "无数据，请添加", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.ibbtn_modquerry_del:
                if (checkedIndexList.size() > 0 && checkedIndexList != null) {
                    new AlertDialog.Builder(context)
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
                    Toast.makeText(context, "点击item选中数据", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case R.id.ib_modaccount:
                dj = 1;
                Intent intentAccount = new Intent(ShowQueryInfoActivity.this,
                        GetInfoActivity.class);
                intentAccount.putExtra("flag", "1");
                startActivityForResult(intentAccount, 5);
                break;
            case R.id.ib_moddep:
                if (!tvAccount.getText().equals("")) {

                    Intent intent1 = new Intent(ShowQueryInfoActivity.this,
                            SupInfoActivity.class);
                    intent1.putExtra("flag", "2");
                    intent1.putExtra("get_id", tvAccount.getText().toString());
                    startActivityForResult(intent1, 4);
                } else {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.ib_modsup:
                if (!tvAccount.getText().equals("")) {
                    Intent intent = new Intent(ShowQueryInfoActivity.this,
                            SupInfoActivity.class);
                    intent.putExtra("flag", "5");
                    intent.putExtra("get_id", tvAccount.getText().toString());
                    startActivityForResult(intent, 3);
                } else {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.ib_hs:
                if (!tvAccount.getText().equals("")) {
                    Intent intent6 = new Intent(ShowQueryInfoActivity.this,
                            SupInfoActivity.class);
                    intent6.putExtra("flag", "6");
                    intent6.putExtra("get_id", tvAccount.getText().toString());
                    startActivityForResult(intent6, 6);
                } else {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.ib_modcust:
                if (!tvAccount.getText().equals("")) {
                    Intent intentcust = new Intent(ShowQueryInfoActivity.this,
                            SupInfoActivity.class);
                    intentcust.putExtra("flag", "3");
                    intentcust.putExtra("get_id", tvAccount.getText().toString());
                    startActivityForResult(intentcust, 2);
                } else {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.ib_moduser:
                if (!tvAccount.getText().equals("")) {
                    Intent intentuser = new Intent(ShowQueryInfoActivity.this,
                            SupInfoActivity.class);
                    intentuser.putExtra("flag", "4");
                    intentuser.putExtra("get_id", tvAccount.getText().toString());
                    startActivityForResult(intentuser, 1);
                } else {
                    Toast.makeText(context, "请选择账套", Toast.LENGTH_LONG).show();
                }

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
            userQuery.remove((int) checkedIndexList.get(i));
            checkBoxList.remove(checkedIndexList.get(i));
        }
        for (int i = 0; i < checkBoxList.size(); i++) {
            // 将已选的设置成未选状态
            checkBoxList.get(i).setChecked(false);
            // 将checkbox设置为不可见
            // checkBoxList.get(i).setVisibility(View.INVISIBLE);
        }
        // 更新数据源
        adapter.notifyDataSetChanged();
        // 清空checkedIndexList,避免影响下一次删除
        checkedIndexList.clear();
        Log.e("LiNing", "---sch" + checkedIndexList);
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

    private void initView_new() {
        // 信息获取（跳转）
        queryAddOk = (Button) findViewById(R.id.btn_query_new);
        rootAccount = ((ImageButton) findViewById(R.id.ib_modaccount));
        rootDep = ((ImageButton) findViewById(R.id.ib_moddep));
        rootSup = ((ImageButton) findViewById(R.id.ib_modsup));
        rootCust = ((ImageButton) findViewById(R.id.ib_modcust));
        rooUser = ((ImageButton) findViewById(R.id.ib_moduser));
        rooHs = ((ImageButton) findViewById(R.id.ib_hs));
        queryAddOk.setOnClickListener(this);
        rootAccount.setOnClickListener(this);
        rootDep.setOnClickListener(this);
        rootSup.setOnClickListener(this);
        rootCust.setOnClickListener(this);
        rooUser.setOnClickListener(this);
        rooHs.setOnClickListener(this);
        tvNull = ((TableRow) findViewById(R.id.new_querry_header_item));
        lv_ztid = ((TextView) findViewById(R.id.new_querry_item_dbid));
        lv_dep = ((TextView) findViewById(R.id.new_querry_item_dep));
        lv_sup = ((TextView) findViewById(R.id.new_querry_item_sup));
        lv_cust = ((TextView) findViewById(R.id.new_querry_item_cust));
        lv_user = ((TextView) findViewById(R.id.new_querry_item_user));
        lv_hs = ((TextView) findViewById(R.id.new_querry_item_hs));

    }

    public void get(View v) {
        // for (int i = 0; i < adapter.getCount(); i++) {
        // LinearLayout view = (LinearLayout) queryList.getAdapter().getView(i,
        // null, null);
        // Log.e("LiNing", "item2======="+view.toString());
        // Log.e("LiNing", "数据======="+view.getChildAt(0));
        // Log.e("LiNing", "数据======="+view.getChildAt(i));
        //
        // }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    String str1 = data.getStringExtra("data_return");
                    str1id = data.getStringExtra("data_return_ids");
                    this.tvUser.setText(str1id);
                    Log.e("LiNing", "提交的id====" + str1id);
                    sp.edit().putString("USERIDquerry", str1id).commit();
                }
                break;
            case 2:
                if (resultCode == 1) {
                    String str2 = data.getStringExtra("data_return");
                    str2id = data.getStringExtra("data_return_ids");
                    this.tvCust.setText(str2id);
                    Log.e("LiNing", "提交的id====" + str2id);
                    sp.edit().putString("CUSTIDquerry", str2id).commit();
                }
                break;
            case 3:
                if (resultCode == 1) {
                    String str3 = data.getStringExtra("data_return");
                    str3id = data.getStringExtra("data_return_ids");
                    this.tvSup.setText(str3id);
                    Log.e("LiNing", "提交的id====" + str3id);
                    sp.edit().putString("SUPIDquerry", str3id).commit();
                }
                break;
            case 6:
                if (resultCode == 1) {
                    String str6 = data.getStringExtra("data_return");
                    str5id = data.getStringExtra("data_return_ids");
                    this.tvHs.setText(str5id);
                    Log.e("LiNing", "提交的id====" + str5id);
                    sp.edit().putString("HSIDquerry", str5id).commit();
                }
                break;
            case 4:
                if (resultCode == 1) {
                    String str4 = data.getStringExtra("data_return");
                    str4id = data.getStringExtra("data_return_ids");
                    this.tvDep.setText(str4id);
                    Log.e("LiNing", "提交的id====" + str4id);
                    sp.edit().putString("DEPIDquerry", str4id).commit();
                }
                break;
            case 5:
                if (resultCode == 1) {
                    straccount = data.getStringExtra("data_return");
                    this.tvAccount.setText(straccount);
                    sp.edit().putString("ACCOUNTIDquerry", straccount).commit();
                    Log.e("LiNing", "提交的id====" + straccount);
                }
                break;

            default:
                break;
        }
    }

    public void allback(View v) {
        finish();
    }

    class UserQueryAdapter extends BaseAdapter {
        ViewHolder holder = null;
        private Context context;
        private List<User_Query> usesInfo;
        private int resource;
        private LayoutInflater inflater;
        private int code, yc;
        private ArrayList<HashMap<String, Object>> dList;
        private SharedPreferences sp;
        private OnItemClickListener onItemClickListener;

        public UserQueryAdapter(int yc, Context context,
                                List<User_Query> userQuery, int fuctionItem) {
            this.yc = yc;
            this.context = context;
            this.usesInfo = userQuery;
            this.resource = fuctionItem;
            this.inflater = ((LayoutInflater) context
                    .getSystemService(LAYOUT_INFLATER_SERVICE));
            sp = context.getSharedPreferences("ydbg", 0);
        }

        @Override
        public int getCount() {

            return usesInfo.size();
        }

        @Override
        public Object getItem(int position) {

            return usesInfo.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(this.resource, null);
                holder.checkBox = (CheckBox) convertView
                        .findViewById(R.id.listDeleteCheckBox);
                if (yc == 1) {
                    holder.checkBox.setVisibility(View.GONE);
                }
                holder.db = (TextView) convertView
                        .findViewById(R.id.new_querry_item_dbid);
                holder.dep = (TextView) convertView
                        .findViewById(R.id.new_querry_item_dep);
                holder.sup = (TextView) convertView
                        .findViewById(R.id.new_querry_item_sup);
                holder.cust = (TextView) convertView
                        .findViewById(R.id.new_querry_item_cust);
                holder.user = (TextView) convertView
                        .findViewById(R.id.new_querry_item_user);
                holder.hs = (TextView) convertView
                        .findViewById(R.id.new_querry_item_hs);
                checkBoxList.add(holder.checkBox);
                convertView.setTag(holder);
            } else {
                holder = ((ViewHolder) convertView.getTag());
            }
            Log.e("LiNing", "---------" + code);
            User_Query user_Query = usesInfo.get(position);
//			Log.e("LiNing", "---------" + user_Query);
            holder.db.setText(user_Query.getQuery_DB());
            if(user_Query.getQuery_Dep().equals("")||user_Query.getQuery_Dep().toString()==null){
                holder.dep.setText("null");
            }else{

                holder.dep.setText(user_Query.getQuery_Dep());
            }
            if(user_Query.getQuery_Sup().equals("")||user_Query.getQuery_Sup().toString()==null){
                holder.sup.setText("null");
            }else{

                holder.sup.setText(user_Query.getQuery_Sup());
            }
            if(user_Query.getQuery_Cust().equals("")||user_Query.getQuery_Cust().toString()==null){
                holder.cust.setText("null");
            }else{

                holder.cust.setText(user_Query.getQuery_Cust());
            }
            if(user_Query.getQuery_User().equals("")||user_Query.getQuery_User().toString()==null){
                holder.user.setText("null");
            }else{

                holder.user.setText(user_Query.getQuery_User());
            }
            if(user_Query.getQuery_CompDep().equals("")||user_Query.getQuery_CompDep().toString()==null){
                holder.hs.setText("null");
            }else{

                holder.hs.setText(user_Query.getQuery_CompDep());
            }
            // 设置判断数据
            holder.checkBox.setOnCheckedChangeListener(new CheckBoxListener(
                    position));
            Log.e("LiNing", "-----convertView----" + convertView);
            return convertView;
        }

        public class ViewHolder {
            public TextView db;
            public TextView dep;
            public TextView sup;
            public TextView cust;
            public TextView user;
            public TextView hs;
            public CheckBox checkBox;
        }
    }

    public class CheckBoxListener implements OnCheckedChangeListener {
        int positions;

        public CheckBoxListener(int position) {

            this.positions = position;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {

            if (isChecked) {
                lntag = 1;
                checkedIndexList.add(positions);
                Log.e("LiNing", "-----111" + checkedIndexList);
                if (0 < checkedIndexList.size() && checkedIndexList.size() <= 1) {

                    item2 = userQuery.get(positions);
                    db_exar = item2.getQuery_DB();
                    dep_exar = item2.getQuery_Dep();
                    sup_exar = item2.getQuery_Sup();
                    cust_exar = item2.getQuery_Cust();
                    user_exar = item2.getQuery_User();
                    hs_exar = item2.getQuery_CompDep();
                    Log.e("LiNing", "-----111---item2" + item2);
                } else {
                    Toast.makeText(context, "修改时请选择单条数据", Toast.LENGTH_LONG).show();
                }
            } else {
                lntag = 0;
                checkedIndexList.remove((Integer) positions);// 删除时切记转换，不然报错

            }

        }

    }
}
