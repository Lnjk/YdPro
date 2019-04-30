package com.example.ydshoa;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.example.LeftOrRight.MyHScrollView;
import com.example.bean.BackApplication;
import com.example.bean.CusterInfoDB;
import com.example.bean.JsonRootBean;
import com.example.bean.NetUtil;
import com.example.bean.SysApplication;
import com.example.bean.URLS;
import com.example.bean.UserInfo;
import com.example.bean.CusterInfoDB.User_Mod;
import com.example.bean.CusterInfoDB.User_Query;
import com.example.bean.CusterInfoDB.Users;
import com.example.zxing.EncodingHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.WriterException;
//import com.umeng.message.PushAgent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CusterInfoActivity extends Activity implements OnClickListener {
    String urlUpdate = URLS.reset_user_url;
    String urlUser = URLS.add_user_url;
    String urlDel_one = URLS.del_one_user_url;
    String urlDel_more = URLS.del_more_user_url;
    String urlGet = URLS.all_user_url;
    private Context context;
    private String session;
    private SharedPreferences sp;
    RelativeLayout mHead;
    ListView mListView1;
    LinearLayout main;
    private int checkFalg, isnull;
    private int flag = 0;
    private int flag2 = 0;
    private int iln;
    private ImageView close, save, et_cust_image, cust_image;
    private Button del, info, newAdd, reset;
    MyAdapter myAdapter;
    private Users info2;
    private String idExtra, nameExtra, pwdExtra, accountExtra, depExtra,
            custExtra, userExtra, imgExtra, signatureExtra, device_IdExtra,
            sighnpwdExtra;
    private String adb1, db1, mod1, modName1, qdb1, dep1, sup1, cust1, user1;
    private boolean add1, alt1, amt1, cst1, del1, gp1, gpr1, out1, prt1, qty1,
            query1, up1;
    private List<User_Query> user_Query2;
    private List<com.example.bean.CusterInfoDB.Users> users;
    private EditText et_cust_name, et_cust_pwd, et_cust_deviceid, et_cust_signature,
            et_cust_sighnpwd;
    TextView et_cust_custer, et_cust_user, et_cust_dep, et_cust_zt;
    private int tag, tag_query;
    private TextView et_cust_id, cust_id, cust_name, cust_pwd, cust_zt,
            cust_dep, cust_custer, cust_user, cust_deviceid, cust_signature,
            cust_sighn_pwd;
    private AlertDialog alertDialog, alertDialog_mod;
    private ImageButton setDB, setDep, setCust, setUser;
    private String setZT, setDEP, setCUST, setUSER;
    private String resultStr = ""; // 服务端返回结果集
    private String best_db;
    private File file_sighn;
    private String new_modid, new_moddb, new_modname, new_modquery, new_modadd,
            new_modprg, new_modset, new_moddel, new_modprt, new_modout,
            new_modqty, new_modup, new_modamt, new_modcst, new_modpr;
    private String new_queryid, new_querydep, new_querysup, new_querycust,
            new_queryuser, new_queryhs;
    public boolean isSelected = false;
    public boolean isSelected_query = false;
    /**
     * 记录选中item的下标
     */
    private List<Integer> checkedIndexList;
    private List<CheckBox> checkBoxList;
    private List<String> idList;
    /**
     * 保存每个item中的checkbox
     */
    private String sub_id;
    private Bitmap localBitmap;
    private FormBody body;
    private String del_url;
    private boolean sp_query, sp_add, sp_del, sp_set;
    private String ps_id;
    private int check;
    private File imag_file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_custer_info);
        BackApplication.getInstance().addActivity(CusterInfoActivity.this);
        context = CusterInfoActivity.this;
        //推送
//		PushAgent.getInstance(context).onAppStart();
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        ps_id = sp.getString("PASS", "");
        newInitView();
    }

    private void newInitView() {
        this.mHead = ((RelativeLayout) findViewById(R.id.cust_head));
        this.mHead.setFocusable(true);
        this.mHead.setClickable(true);
        this.mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        this.mListView1 = ((ListView) findViewById(R.id.lv_account_header));
        this.mListView1
                .setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        mListView1.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int positionLN, long id) {
                // flag = 1;
                // iln = positionLN;
                // myAdapter.setSelectedPosition(positionLN);
                // myAdapter.notifyDataSetChanged();
                // myAdapter.notifyDataSetInvalidated();
                // 将checkbox设置为可见
                for (int i = 0; i < checkBoxList.size(); i++) {
                    checkBoxList.get(i).setVisibility(View.VISIBLE);
                }
            }
        });
        this.newAdd = ((Button) findViewById(R.id.btn_new_add));
        this.reset = ((Button) findViewById(R.id.btn_account_reset));
        this.del = ((Button) findViewById(R.id.btn_account_del));
        this.info = ((Button) findViewById(R.id.btn_account_info));
        this.newAdd.setOnClickListener(this);
        this.reset.setOnClickListener(this);
        this.del.setOnClickListener(this);
        this.info.setOnClickListener(this);
        checkedIndexList = new ArrayList<Integer>();
        idList = new ArrayList<String>();
        checkBoxList = new ArrayList<CheckBox>();
        // 异步加载数据
        getInfoCust();
        sp_query = sp.getBoolean("USER_QUERY", false);
        sp_add = sp.getBoolean("USER_ADD", false);
        sp_del = sp.getBoolean("USER_DEL", false);
        sp_set = sp.getBoolean("USER_UP", false);
        Log.e("LiNing", "sp数据----" + sp_add + sp_query + sp_del + sp_set);
    }

    private void getInfoCust() {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder().build();
        client.newCall(
                new Request.Builder().addHeader("cookie", session).url(urlGet)
                        .post(body).build()).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                String str = response.body().string();
                Log.e("LiNing", "信息数据====" + str);
                // 解析包含date的数据必须添加此代码(InputStream型)
                Gson gson = new GsonBuilder().setDateFormat(
                        "yyyy-MM-dd HH:mm:ss").create();
                final CusterInfoDB cInfoDB = gson.fromJson(str,
                        CusterInfoDB.class);
                if (cInfoDB != null) {
                    CusterInfoActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            users = cInfoDB.getUsers();
                            Log.e("LiNing",
                                    "users.size()-------" + users.size());
                            myAdapter = new MyAdapter(R.layout.custer, users,
                                    CusterInfoActivity.this);
                            mListView1.setAdapter(myAdapter);
                            myAdapter.notifyDataSetChanged();
                            checkedIndexList.clear();
                            idList.clear();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call arg0, IOException arg1) {

            }
        });
    }

    class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {
        ListViewAndHeadViewTouchLinstener() {
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ((HorizontalScrollView) CusterInfoActivity.this.mHead
                    .findViewById(R.id.horizontalScrollView1))
                    .onTouchEvent(event);
            return false;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    best_db = data.getStringExtra("data_return");
                    et_cust_zt.setText(best_db);
                    sp.edit().putString("ACCOUNTID_set", best_db).commit();
                    Log.e("LiNing", "提交的id====" + best_db);
                }
                break;
            case 2:
                if (resultCode == 1) {
                    String str1 = data.getStringExtra("data_dep");
                    String IDdep = data.getStringExtra("data_dep_id");
                    sp.edit().putString("DEPID_set", IDdep).commit();
                    Log.e("LiNing", "提交的id====" + IDdep);
                    et_cust_dep.setText(IDdep);
                }
                break;
            case 3:
                if (resultCode == 1) {
                    String str1 = data.getStringExtra("data_dep");
                    String IDcust = data.getStringExtra("data_dep_id");
                    sp.edit().putString("CUSTID_set", IDcust).commit();
                    Log.e("LiNing", "提交的id====" + IDcust);
                    et_cust_custer.setText(IDcust);
                }
                break;
            case 4:
                if (resultCode == 1) {
                    String str1 = data.getStringExtra("data_dep");
                    String IDuser = data.getStringExtra("data_dep_id");
                    sp.edit().putString("USERID_set", IDuser).commit();
                    Log.e("LiNing", "提交的id====" + IDuser);
                    et_cust_user.setText(IDuser);
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_account_set:
                flag2 = 1;
                Intent intent = new Intent(context, GetInfoActivity.class);
                intent.putExtra("flag", "1");
                startActivityForResult(intent, 1);
                break;
            // 默认部门选取(单选)
            case R.id.ib_dep_set:
//			if (flag2 == 1) {
                if (!et_cust_zt.getText().toString().equals("")) {
                    Intent intent1 = new Intent(CusterInfoActivity.this,
                            ErpDepInfoActivity.class);
                    intent1.putExtra("flag", "2");
                    intent1.putExtra("DB_ID", et_cust_zt.getText().toString());
                    startActivityForResult(intent1, 2);
//				if (et_cust_zt.getText().toString().equals("DB_BJ15")) {
//
//					Intent intent1 = new Intent(CusterInfoActivity.this,
//							ErpDepInfoActivity.class);
//					intent1.putExtra("flag", "2");
//					startActivityForResult(intent1, 2);
//				} else {
//					Toast.makeText(CusterInfoActivity.this, "数据库无数据",
//							Toast.LENGTH_SHORT).show();
//				}
                } else {
                    Toast.makeText(CusterInfoActivity.this, "请先选择默认账套",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            // 默认客户选取(单选)
            case R.id.ib_cust_set:
//			if (flag2 == 1) {
                if (!et_cust_zt.getText().toString().equals("")) {
                    Intent intent2 = new Intent(context,
                            ErpDepInfoActivity.class);
                    intent2.putExtra("flag", "3");
                    intent2.putExtra("DB_ID", et_cust_zt.getText().toString());
                    startActivityForResult(intent2, 3);
//				if (et_cust_zt.getText().toString().equals("DB_BJ15")) {
//
//					Intent intent2 = new Intent(context,
//							ErpDepInfoActivity.class);
//					intent2.putExtra("flag", "3");
//					startActivityForResult(intent2, 3);
//				} else {
//					Toast.makeText(CusterInfoActivity.this, "数据库无数据",
//							Toast.LENGTH_SHORT).show();
//				}
                } else {
                    Toast.makeText(CusterInfoActivity.this, "请先选择默认账套",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            // 默认用户选取(单选)
            case R.id.ib_user_set:
//			if (flag2 == 1) {
                if (!et_cust_zt.getText().toString().equals("")) {
                    Intent intent4 = new Intent(context,
                            ErpDepInfoActivity.class);
                    intent4.putExtra("flag", "4");
                    intent4.putExtra("DB_ID", et_cust_zt.getText().toString());
                    startActivityForResult(intent4, 4);
//				if (et_cust_zt.getText().toString().equals("DB_BJ15")) {
//
//					Intent intent4 = new Intent(context,
//							ErpDepInfoActivity.class);
//					intent4.putExtra("flag", "4");
//					startActivityForResult(intent4, 4);
//				} else {
//					Toast.makeText(CusterInfoActivity.this, "数据库无数据",
//							Toast.LENGTH_SHORT).show();
//				}
                } else {
                    Toast.makeText(CusterInfoActivity.this, "请先选择默认账套",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_new_add:
                // 判断是否有该权限
                if (ps_id.equals("1")) {
                    startActivity(new Intent(context, NewAccountActivity.class));
                } else {
                    if (sp_add == true) {
                        startActivity(new Intent(context, NewAccountActivity.class));
                    } else {
                        Toast.makeText(context, "请等待...", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.btn_account_reset:

                if (0 < idList.size() && idList.size() <= 1) {
                    if (ps_id.equals("1")) {
                        resetInfo();
                    } else {
                        if (sp_set == true) {
                            resetInfo();
                        } else {
                            Toast.makeText(context, "请等待...", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    // checkedIndexList.clear();
                    // idList.clear();
                    Toast.makeText(this.context, "请选择一条数据", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.btn_account_del:
                // if (0 < idList.size() && idList.size() <= 1) {
                if (checkedIndexList != null && checkedIndexList.size() > 0) {
                    if (ps_id.equals("1")) {
                        new AlertDialog.Builder(context)
                                .setTitle("是否删除")
                                .setPositiveButton("是",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {

                                                Log.e("LiNing", "--------删除的id==="
                                                        + sub_id);
                                                delCust();
                                            }
                                        }).setNegativeButton("否", null).show();
                    } else {

                        if (sp_del == true) {

                            new AlertDialog.Builder(context)
                                    .setTitle("是否删除")
                                    .setPositiveButton("是",
                                            new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(
                                                        DialogInterface dialog,
                                                        int which) {

                                                    Log.e("LiNing",
                                                            "--------删除的id==="
                                                                    + sub_id);
                                                    delCust();
                                                }
                                            }).setNegativeButton("否", null).show();
                        } else {
                            Toast.makeText(context, "请等待...", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(context, "请选择数据", Toast.LENGTH_LONG).show();
                }
                // } else {
                // checkedIndexList.clear();
                // idList.clear();
                // Toast.makeText(this.context, "请选择一条数据", 1).show();
                // }

                break;
            case R.id.btn_account_info:
                tag = 0;// 信息显示
                if (0 < idList.size() && idList.size() <= 1) {
                    if (ps_id.equals("1")) {
                        showInfo();
                    } else {

                        if (sp_query == true) {
                            showInfo();
                        } else {
                            Toast.makeText(context, "请等待...", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    // checkedIndexList.clear();
                    // idList.clear();
                    Toast.makeText(this.context, "请选择一条数据", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.img_yhewm:
                getImg();
                et_cust_image.setImageBitmap(localBitmap);
                break;
            case R.id.tv_yhewm:
                getImg();
                cust_image.setImageBitmap(localBitmap);
                break;
            default:
                break;
        }
    }

    private void getImg() {
        // 通过设备码生成二维码
        if (device_IdExtra != null) {
            try {
                localBitmap = EncodingHandler.createQRCode(device_IdExtra, 200);

                File tempFile = new File(
                        Environment.getExternalStorageDirectory(),
                        getPhotoFileName());
                String path_er = Uri.fromFile(tempFile).getPath();
                // sp.edit().putString("TEST_er",
                // path_test_er).commit();
                Log.e("LiNing", "二维码路径是====" + path_er);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "没有设备码", Toast.LENGTH_LONG).show();
        }
    }

    protected void delok() {
        CusterInfoActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                new AlertDialog.Builder(CusterInfoActivity.this)
                        .setTitle("是否删除数据？")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface paramAnonymousDialogInterface,
                                            int paramAnonymousInt) {
                                        delCust();
                                        // moreDel();
                                    }

                                }).setNegativeButton("取消", null).show();
            }
        });
    }

    private void mainUp() {
        CusterInfoActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(context, "请等待...", Toast.LENGTH_LONG).show();
            }
        });
    }

    // 更改信息
    private void resetInfo() {
        View view = getLayoutInflater()
                .inflate(R.layout.custinfo_up, null);
        et_cust_id = (TextView) view.findViewById(R.id.et_yhbh);
        et_cust_id.setText(idExtra);
        et_cust_name = (EditText) view.findViewById(R.id.et_yhmc);
        et_cust_name.setText(nameExtra);
        et_cust_pwd = (EditText) view.findViewById(R.id.et_yhpwd);
        et_cust_pwd.setText(pwdExtra);
        et_cust_zt = (TextView) view.findViewById(R.id.et_yhmrzt);
        et_cust_zt.setText(accountExtra);
        et_cust_dep = (TextView) view.findViewById(R.id.et_yhmrbm);
        et_cust_dep.setText(depExtra);
        et_cust_custer = (TextView) view.findViewById(R.id.et_yhmrkh);
        et_cust_custer.setText(custExtra);
        et_cust_user = (TextView) view.findViewById(R.id.et_yhmryh);
        et_cust_user.setText(userExtra);
        et_cust_image = (ImageView) view.findViewById(R.id.img_yhewm);
        et_cust_image.setOnClickListener(this);
        et_cust_deviceid = (EditText) view.findViewById(R.id.et_yhmrsbm);
        et_cust_deviceid.setText(device_IdExtra);
        et_cust_sighnpwd = (EditText) view.findViewById(R.id.et_yhmrqzmm);
        et_cust_sighnpwd.setText(sighnpwdExtra);
        et_cust_signature = (EditText) view.findViewById(R.id.et_yhmrqz);
        et_cust_signature.setText((CharSequence) signatureExtra);
        setDB = (ImageButton) view.findViewById(R.id.ib_account_set);
        setDep = (ImageButton) view.findViewById(R.id.ib_dep_set);
        setCust = (ImageButton) view.findViewById(R.id.ib_cust_set);
        setUser = (ImageButton) view.findViewById(R.id.ib_user_set);
        setDB.setOnClickListener(this);
        setDep.setOnClickListener(this);
        setCust.setOnClickListener(this);
        setUser.setOnClickListener(this);
        imag_file = new File(et_cust_signature.getText().toString());
        view.findViewById(R.id.ib_reset_ald).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
        // 信息判断

//		if (flag2 == 1) {
//			setZT = et_cust_zt.getText().toString();
//			setDEP = et_cust_dep.getText().toString();
//			setCUST = et_cust_custer.getText().toString();
//			setUSER = et_cust_user.getText().toString();
//		} else if (flag2 == 0) {
//
//			setZT = sp.getString("ACCOUNTID_set", "");
//			setDEP = sp.getString("DEPID_set", "");
//			setCUST = sp.getString("CUSTID_set", "");
//			setUSER = sp.getString("USERID_set", "");
//		}

        view.findViewById(R.id.btn_getinfo_mod).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tag = 1;
//						check=1;
//						 showMod();
                        Intent intent = new Intent(context,
                                ShowModInfoActivity.class);
                        intent.putExtra("MOD_FLAG", "modset");
                        intent.putExtra("userId", idExtra);
                        intent.putExtra("INFO_int", "" + iln);
                        intent.putExtra("ischeck", "" + check);
                        startActivity(intent);
                    }
                });
        view.findViewById(R.id.btn_getinfo_querry).setOnClickListener(
                new OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        tag_query = 1;
                        check = 1;
                        // showQuery();
                        Intent intent = new Intent(CusterInfoActivity.this,
                                ShowQueryInfoActivity.class);
                        intent.putExtra("QUERY_FLAG", "queryset");
                        intent.putExtra("userId", idExtra);
                        intent.putExtra("INFO_int", "" + iln);
                        startActivity(intent);
                    }
                });
        view.findViewById(R.id.btn_resetinfo).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        getnewQuery();
                        getnewMod();
                        postNews();
                    }

                });

        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setCancelable(true);
        alertDialog.setView(view);
        alertDialog.show();
    }

    private void postNews() {
        // 获取数据（判断是否是最新）

        if (flag == 9) {

            if (et_cust_name.getText().toString() != null) {
                new Thread(new Runnable() {
                    public void run() {
                        try {

                            URL url = new URL(urlUpdate);// 创建一个URL对象
                            // json提交
                            Map<String, String> textParams = new HashMap<String, String>();
                            Map<String, File> fileparams = new HashMap<String, File>();
                            // 用户参数
                            textParams.put("user_Id", et_cust_id.getText()
                                    .toString());
                            textParams.put("user_Name", et_cust_name.getText()
                                    .toString());
                            textParams.put("user_Pwd", et_cust_pwd.getText()
                                    .toString());
                            setZT = et_cust_zt.getText().toString();
                            setDEP = et_cust_dep.getText().toString();
                            setCUST = et_cust_custer.getText().toString();
                            setUSER = et_cust_user.getText().toString();
                            textParams.put("user_DB", setZT);// user_DB;//
                            // 默认数据库
                            textParams.put("user_Dep", setDEP);// user_Dep;//
                            // 默认部门
                            textParams.put("user_Cust", setCUST);
                            textParams.put("user_erpUser", setUSER);// 默认ERP用户
                            // 默认数据库Device_Id/SignaturePWD
                            textParams.put("Device_Id", et_cust_deviceid
                                    .getText().toString());
                            textParams.put("SignaturePWD", et_cust_sighnpwd
                                    .getText().toString());
// 查询数据
                            textParams.put("query_DB", new_queryid);// // 帐套编号
                            textParams.put("query_Dep", new_querydep);// 查询部门
                            textParams.put("query_Sup", new_querysup);// 查询供应商
                            textParams.put("query_Cust", new_querycust);// 查询客户
                            textParams.put("query_User", new_queryuser);// 查询用户
                            textParams.put("query_CompDep", new_queryhs);// 查询核算单位
                            Log.e("LiNing", "***********8" + new_querydep + "===" + new_querysup + "===" + new_querycust
                                    + "===" + new_queryuser + "===" + new_queryhs);
                            // 权限参数
                            textParams.put("isApp", "T");
                            textParams.put("mod_ID", new_modid);// mod_ID;//
                            textParams.put("mod_DB", new_moddb);// mod_DB;//账套编号
                            textParams.put("mod_Name", new_modname);// mod_Name;//
                            textParams.put("mod_Query", new_modquery);// mod_Query;//
                            textParams.put("mod_Add", new_modadd);// mod_Add;//
                            textParams.put("mod_Alter", new_modset);// mod_Alter;//
                            textParams.put("mod_Del", new_moddel);// mod_Del;//
                            textParams.put("mod_Prt", new_modprt);// mod_Prt;//
                            textParams.put("mod_Out", new_modout);// mod_Out;//
                            textParams.put("mod_Qty", new_modcst);// mod_Qty;//
                            textParams.put("mod_Up", new_modup);// mod_Up;//
                            textParams.put("mod_Amt", new_modamt);// mod_Amt;//
                            textParams.put("mod_Cst", new_modqty);// mod_Cst;//
                            textParams.put("mod_GP", new_modpr);// mod_GP;//
                            textParams.put("mod_GPR", new_modprg);

                            Log.e("LiNing", "***********8" + textParams);
                            // 后续判断是否上传默认图片
                            if (!imag_file.equals("") && imag_file != null
                                    && imag_file.exists()) {

                                fileparams.put("Signature", imag_file);
//						fileparams.put("Signature",
//								new File(sp.getString("photoURL", "")));
                            }
                            Log.e("LiNing", "***********9" + fileparams);


                            // 利用HttpURLConnection对象从网络中获取网页数据
                            HttpURLConnection conn = (HttpURLConnection) url
                                    .openConnection();
                            // 添加表头
                            conn.addRequestProperty("cookie", session);
                            conn.setConnectTimeout(5000);
                            // 设置允许输出（发送POST请求必须设置允许输出）
                            conn.setDoOutput(true);
                            conn.setRequestMethod("POST");
                            conn.setUseCaches(false);
                            // 在开始用HttpURLConnection对象的setRequestProperty()设置,就是生成HTML文件头
                            conn.setRequestProperty("ser-Agent", "Fiddler");
                            conn.setRequestProperty("Content-Type",
                                    "multipart/form-data; boundary="
                                            + NetUtil.BOUNDARY);
                            OutputStream os = conn.getOutputStream();
                            DataOutputStream ds = new DataOutputStream(os);
                            NetUtil.writeStringParams(textParams, ds);
                            if (!imag_file.equals("") && imag_file != null
                                    && imag_file.exists()) {

                                NetUtil.writeFileParams(fileparams, ds);
                            }
                            NetUtil.paramsEnd(ds);
                            os.close();// 及时关闭
                            int code = conn.getResponseCode();
                            Log.e("LiNing", "ddddddddddd" + code);
                            // 对响应码进行判断
                            if (code == 200) {
                                // 得到网络返回的输入流
                                InputStream is = conn.getInputStream();
                                resultStr = NetUtil.readString(is);
                                Log.e("LiNing", "ddddddddddd" + resultStr);
                                // startActivity(new Intent(context,
                                // CusterInfoActivity.class));
                                if (alertDialog.isShowing()) {

                                    alertDialog.dismiss();
                                }
                                getInfoCust();
                                // finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // handler.sendEmptyMessage(0);// 执行耗时的方法之后发送消给handler
                    }
                }).start();
            }
        }
    }

    private void getnewQuery() {
        flag = 9;
//		if (isSelected_query) {
        if (tag_query == 1) {
            new_queryid = sp.getString("queryinfo_id", "");
            new_querydep = sp.getString("queryinfo_dep", "");
            new_querysup = sp.getString("queryinfo_sup", "");
            new_queryhs = sp.getString("queryinfo_hs", "");
            new_querycust = sp.getString("queryinfo_cust", "");
            new_queryuser = sp.getString("queryinfo_user", "");
            Log.e("LiNing", "id=====" + new_queryid + "dep====" + new_querydep);
        } else {
            new_queryid = sp.getString("old_queryinfo_id", "");
            new_querydep = sp.getString("old_queryinfo_dep", "");
            new_querysup = sp.getString("old_queryinfo_sup", "");
            new_queryhs = sp.getString("old_queryinfo_hs", "");
            new_querycust = sp.getString("old_queryinfo_cust", "");
            new_queryuser = sp.getString("old_queryinfo_user", "");
        }
    }

    private void getnewMod() {
        flag = 9;
//		if (isSelected) {
        if (tag == 1) {

            new_modid = sp.getString("modinfo_id", "");
            new_moddb = sp.getString("modinfo_db", "");
            new_modname = sp.getString("modinfo_name", "");
            new_modquery = sp.getString("modinfo_query", "");
            new_modadd = sp.getString("modinfo_add", "");
            new_modset = sp.getString("modinfo_set", "");
            new_moddel = sp.getString("modinfo_del", "");
            new_modprt = sp.getString("modinfo_prt", "");
            new_modout = sp.getString("modinfo_out", "");
            new_modqty = sp.getString("modinfo_qty", "");
            new_modup = sp.getString("modinfo_up", "");
            new_modamt = sp.getString("modinfo_amt", "");
            new_modcst = sp.getString("modinfo_cst", "");
            new_modpr = sp.getString("modinfo_pr", "");
            new_modprg = sp.getString("modinfo_prg", "");
        } else {
            new_modid = sp.getString("old_modinfo_id", "");
            new_moddb = sp.getString("old_modinfo_db", "");
            new_modname = sp.getString("old_modinfo_name", "");
            new_modquery = sp.getString("old_modinfo_query", "");
            new_modadd = sp.getString("old_modinfo_add", "");
            new_modset = sp.getString("old_modinfo_set", "");
            new_moddel = sp.getString("old_modinfo_del", "");
            new_modprt = sp.getString("old_modinfo_prt", "");
            new_modout = sp.getString("old_modinfo_out", "");
            new_modqty = sp.getString("old_modinfo_qty", "");
            new_modup = sp.getString("old_modinfo_up", "");
            new_modamt = sp.getString("old_modinfo_amt", "");
            new_modcst = sp.getString("old_modinfo_cst", "");
            new_modpr = sp.getString("old_modinfo_pr", "");
            new_modprg = sp.getString("old_modinfo_prg", "");
        }
        Log.e("LiNing", "冲突db_id" + new_moddb + "冲突mod_id" + new_modid
                + "冲突mod_id" + new_modprg);
    }

    private void delCust() {
        // if (checkFalg == 1) {

        OkHttpClient client = new OkHttpClient();
        if (0 < idList.size() && idList.size() <= 1) {
            body = new FormBody.Builder().add("user_Id", sub_id).build();
            del_url = urlDel_one;
        } else {
            del_url = urlDel_more;
            body = new FormBody.Builder().add("isApp", "T")
                    .add("user_Ids", sub_id).build();
        }
        client.newCall(
                new Request.Builder().addHeader("cookie", session).url(del_url)
                        .post(body).build()).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                String str = response.body().string().trim();
                Log.e("LiNing", str);
                final JsonRootBean localJsonRootBean = (JsonRootBean) new Gson()
                        .fromJson(str, JsonRootBean.class);
                if (localJsonRootBean != null) {
                    CusterInfoActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            boolean rlo = localJsonRootBean.getRLO();
                            if (rlo == true) {
                                Toast.makeText(CusterInfoActivity.this,
                                        "信息删除成功", Toast.LENGTH_SHORT).show();
                                getInfoCust();
                            } else if (rlo == false) {
                                Toast.makeText(CusterInfoActivity.this,
                                        "信息删除失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call arg0, IOException arg1) {

            }
        });

        // } else {
        // Toast.makeText(CusterInfoActivity.this, "未选择数据",
        // Toast.LENGTH_SHORT).show();
        // }
    }

    // 信息显示
    private void showInfo() {
        View view = getLayoutInflater().inflate(R.layout.cust_info, null);
        cust_id = (TextView) view.findViewById(R.id.tv_yhbh);
        cust_id.setText("" + idExtra);
        cust_name = (TextView) view.findViewById(R.id.tv_yhmc);
        cust_name.setText(nameExtra);
        cust_pwd = (TextView) view.findViewById(R.id.tv_yhpwd);
        cust_pwd.setText(pwdExtra);
        cust_zt = (TextView) view.findViewById(R.id.tv_yhmrzt);
        cust_zt.setText(accountExtra);
        cust_dep = (TextView) view.findViewById(R.id.tv_yhmrbm);
        cust_dep.setText(depExtra);
        cust_custer = (TextView) view.findViewById(R.id.tv_yhmrkh);
        cust_custer.setText(custExtra);
        cust_user = (TextView) view.findViewById(R.id.tv_yhmryh);
        cust_user.setText(userExtra);
        cust_image = (ImageView) view.findViewById(R.id.tv_yhewm);
        cust_image.setOnClickListener(this);
        cust_deviceid = (TextView) view.findViewById(R.id.tv_yhmrsbm);
        cust_deviceid.setText(device_IdExtra);
        cust_sighn_pwd = (TextView) view.findViewById(R.id.tv_yhmrqzmm);
        cust_sighn_pwd.setText(sighnpwdExtra);
        cust_signature = (TextView) view.findViewById(R.id.tv_yhmrqz);
        cust_signature.setText((CharSequence) signatureExtra);
        // 展示模块
        view.findViewById(R.id.btn_getinfo_modshow).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        tag = 0;
                        // showMod();
                        Intent intent = new Intent(context,
                                ShowModInfoScrowActivity.class);
                        intent.putExtra("userId", idExtra);
                        intent.putExtra("MOD_FLAG", "mod");
                        intent.putExtra("INFO_int", "" + iln);
                        startActivity(intent);
                    }
                });
        // 展示查询
        view.findViewById(R.id.btn_getinfo_querryshow).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        tag = 0;
//						tag_query = 0;
                        // showQuery();
                        Intent intent = new Intent(context,
                                ShowQueryInfoActivity.class);
                        intent.putExtra("userId", idExtra);
                        intent.putExtra("QUERY_FLAG", "query");
                        intent.putExtra("INFO_int", "" + iln);
                        startActivity(intent);
                    }
                });
        view.findViewById(R.id.ib_show_ald).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        alertDialog_mod.dismiss();
                    }
                });
        alertDialog_mod = new AlertDialog.Builder(context).create();
        alertDialog_mod.setView(view);
        alertDialog_mod.setCancelable(true);
        alertDialog_mod.show();
        view.findViewById(R.id.btn_custOK).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        alertDialog_mod.dismiss();
                    }
                });
    }

    public class MyAdapter extends BaseAdapter {
        int id_row_layout;
        LayoutInflater mInflater;
        public List<ViewHolder> mHolderList = new ArrayList();
        private List<Users> users;
        private List<User_Mod> user_Mod;
        private List<User_Query> user_Query;
        private int selectedPosition = -1;// 选中的位置

        public MyAdapter(int id_row_layout, List<Users> users2, Context context) {
            super();
            this.id_row_layout = id_row_layout;
            this.users = users2;
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return this.users.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                synchronized (CusterInfoActivity.this) {
                    convertView = mInflater.inflate(id_row_layout, null);
                    holder = new ViewHolder();

                    MyHScrollView scrollView1 = (MyHScrollView) convertView
                            .findViewById(R.id.horizontalScrollView1);

                    holder.scrollView = scrollView1;
                    holder.checkbox = (CheckBox) convertView
                            .findViewById(R.id.cust_listDeleteCheckBox);
                    holder.checkbox.setVisibility(View.VISIBLE);
                    holder.layouts = (RelativeLayout) convertView
                            .findViewById(R.id.rl_item);
                    holder.id = (TextView) convertView
                            .findViewById(R.id.textView1_id);
                    holder.name = (TextView) convertView
                            .findViewById(R.id.textView2_name);
                    holder.pwd = (TextView) convertView
                            .findViewById(R.id.textView3_pwd);
                    holder.zt = (TextView) convertView
                            .findViewById(R.id.textView4_account);
                    holder.dep = (TextView) convertView
                            .findViewById(R.id.textView5_dep);
                    holder.cust = (TextView) convertView
                            .findViewById(R.id.textView6_cust);
                    holder.user = (TextView) convertView
                            .findViewById(R.id.textView7_user);
                    holder.img = (TextView) convertView
                            .findViewById(R.id.textView8_er);
                    holder.deviceId = (TextView) convertView
                            .findViewById(R.id.textView9_deviceID);
                    holder.signature = (TextView) convertView
                            .findViewById(R.id.textView10_signature);
                    holder.signaturepwd = (TextView) convertView
                            .findViewById(R.id.textView11_signaturepwd);
                    MyHScrollView headSrcrollView = (MyHScrollView) mHead
                            .findViewById(R.id.horizontalScrollView1);
                    headSrcrollView
                            .AddOnScrollChangedListener(new OnScrollChangedListenerImp(
                                    scrollView1));
                    // 将item中的checkbox放到checkBoxList中
                    checkBoxList.add(holder.checkbox);
                    convertView.setTag(holder);
                }
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            info2 = users.get(position);
            holder.id.setText(info2.getUser_Id());
            holder.name.setText(info2.getUser_Name());
            holder.pwd.setText(info2.getUser_Pwd());
            holder.zt.setText(info2.getUser_DB());
            holder.dep.setText(info2.getUser_Dep());
            holder.cust.setText(info2.getUser_Cust());
            holder.user.setText(info2.getUser_erpUser());
            holder.deviceId.setText(info2.getDevice_Id());
            holder.signaturepwd.setText(info2.getSignaturePWD());
            holder.img.setText("" + info2.getDevice_image());
            holder.signature.setText("" + info2.getSignature());
            Log.e("LiNing", "info2.getUser_Mod()------" + info2.getUser_Mod());

            holder.checkbox.setOnCheckedChangeListener(new CheckBoxListener(
                    position));
            return convertView;
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

        class ViewHolder {
            public RelativeLayout layouts;
            public TextView cust;
            public TextView dep;
            public TextView id;
            public TextView img;
            public TextView name;
            public TextView pwd;
            HorizontalScrollView scrollView;
            public TextView user;
            public TextView zt;
            public TextView deviceId;
            public TextView signature;
            public TextView signaturepwd;
            CheckBox checkbox;

            ViewHolder() {
            }

        }

    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";

    }

    /**
     * checkbox的监听器
     */
    public class CheckBoxListener implements OnCheckedChangeListener {
        /**
         * 列表item的下标位置
         */
        int positions;
        private String userIds;

        public CheckBoxListener(int position) {
            this.positions = position;

        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            if (isChecked) {
                checkFalg = 1;
                iln = positions;

                userIds = users.get(positions).getUser_Id();
                Log.e("LiNing", "--------" + userIds);
                checkedIndexList.add(positions);
                idList.add(userIds);
                Log.e("LiNing", "--------集合" + checkedIndexList);
                Log.e("LiNing", "--------id集合" + idList);

                String zts_str = "";
                for (String querys_db : idList) {
                    zts_str += querys_db + ",";
                }
                sub_id = zts_str.substring(0, zts_str.length() - 1);
                Log.e("LiNing", "--------sub_id" + sub_id);
                // ==================================

                Users new_info = users.get(positions);
                idExtra = new_info.getUser_Id();
                nameExtra = new_info.getUser_Name();
                pwdExtra = new_info.getUser_Pwd();
                accountExtra = new_info.getUser_DB();
                depExtra = new_info.getUser_Dep();
                custExtra = new_info.getUser_Cust();
                userExtra = new_info.getUser_erpUser();
                // imgExtra = new_info.getDevice_image();
                device_IdExtra = new_info.getDevice_Id();
                sighnpwdExtra = new_info.getSignaturePWD();
                signatureExtra = new_info.getSignature();
//				oldInfo();
                oldInfoTwo();
            } else {
                checkFalg = 0;
                checkedIndexList.remove((Integer) positions);
                Log.e("LiNing", "--------删除集合" + checkedIndexList);
                idList.remove(userIds);
                Log.e("LiNing", "--------删除id集合" + idList);
                if (idList != null && idList.size() > 0) {
                    isnull = 1;
                    String zts_str = "";
                    for (String querys_db : idList) {
                        zts_str += querys_db + ",";
                    }
                    sub_id = zts_str.substring(0, zts_str.length() - 1);
                    Log.e("LiNing", "--------sub_id" + sub_id);
                } else {
                    Toast.makeText(CusterInfoActivity.this, "未选中数据", Toast.LENGTH_LONG).show();
                }

            }
        }

        private void oldInfo() {
            //旧数据mod
            List<User_Mod> user_Mod2 = users.get(positions).getUser_Mod();
            Log.e("LiNing", "mk数据是====" + user_Mod2.toString());
            ArrayList list_id_mod = new ArrayList<String>();
            ArrayList listNAMEs = new ArrayList<String>();
            ArrayList listmODDBs = new ArrayList<String>();
            ArrayList listQUERYs = new ArrayList<String>();
            ArrayList listADDs = new ArrayList<String>();
            ArrayList listAMTs = new ArrayList<String>();
            ArrayList listCSTs = new ArrayList<String>();
            ArrayList listDELs = new ArrayList<String>();
            ArrayList listPRs = new ArrayList<String>();
            ArrayList listPRGs = new ArrayList<String>();
            ArrayList listPRTs = new ArrayList<String>();
            ArrayList listQTYs = new ArrayList<String>();
            ArrayList listSETs = new ArrayList<String>();
            ArrayList listUPs = new ArrayList<String>();
            ArrayList listOUTs = new ArrayList<String>();
            for (int i = 0; i < user_Mod2.size(); i++) {
                String mod_ID = user_Mod2.get(i).getMod_ID();
                list_id_mod.add(mod_ID);
                String mod_DB = user_Mod2.get(i).getDb_ID();
                listmODDBs.add(mod_DB);
                String mod_name = user_Mod2.get(i).getMod_Name();
                listNAMEs.add(mod_name);
                boolean mod_Add = user_Mod2.get(i).isMod_Add();
                String s_add = String.valueOf(mod_Add);
                Log.e("LiNing", "得到数据-----list" + s_add);
                if (mod_Add == true) {
                    s_add = "T";
                } else if (mod_Add == false) {
                    s_add = "F";
                }
                listADDs.add(s_add);
                boolean mod_querry = user_Mod2.get(i).isMod_Query();
                String s_query = String.valueOf(mod_querry);
                if (mod_querry == true) {
                    s_query = "T";
                } else if (mod_querry == false) {
                    s_query = "F";
                }
                listQUERYs.add(s_query);
                boolean mod_amt = user_Mod2.get(i).isMod_Amt();
                String s_amt = String.valueOf(mod_amt);
                if (mod_amt == true) {
                    s_amt = "T";
                } else if (mod_amt == false) {
                    s_amt = "F";
                }
                listAMTs.add(s_amt);
                boolean mod_cst = user_Mod2.get(i).isMod_Cst();
                String s_cst = String.valueOf(mod_cst);
                if (mod_cst == true) {
                    s_cst = "T";
                } else if (mod_cst == false) {
                    s_cst = "F";
                }
                listCSTs.add(s_cst);
                boolean mod_del = user_Mod2.get(i).isMod_Del();
                String s_del = String.valueOf(mod_del);
                if (mod_del == true) {
                    s_del = "T";
                } else if (mod_del == false) {
                    s_del = "F";
                }
                listDELs.add(s_del);
                boolean mod_gp = user_Mod2.get(i).isMod_GP();
                String s_gp = String.valueOf(mod_gp);
                if (mod_gp == true) {
                    s_gp = "T";
                } else if (mod_gp == false) {
                    s_gp = "F";
                }
                listPRs.add(s_gp);
                boolean mod_gpr = user_Mod2.get(i).isMod_GPR();
                String s_gpr = String.valueOf(mod_gpr);
                if (mod_gpr == true) {
                    s_gpr = "T";
                } else if (mod_gpr == false) {
                    s_gpr = "F";
                }
                listPRGs.add(s_gpr);
                boolean mod_prt = user_Mod2.get(i).isMod_Prt();
                String s_prt = String.valueOf(mod_prt);
                if (mod_prt == true) {
                    s_prt = "T";
                } else if (mod_prt == false) {
                    s_prt = "F";
                }
                listPRTs.add(s_prt);
                boolean mod_qty = user_Mod2.get(i).isMod_Qty();
                String s_qty = String.valueOf(mod_qty);
                if (mod_qty == true) {
                    s_qty = "T";
                } else if (mod_qty == false) {
                    s_qty = "F";
                }
                listQTYs.add(s_qty);
                boolean mod_set = user_Mod2.get(i).isMod_Alter();
                String s_set = String.valueOf(mod_set);
                if (mod_set == true) {
                    s_set = "T";
                } else if (mod_set == false) {
                    s_set = "F";
                }
                listSETs.add(s_set);
                boolean mod_up = user_Mod2.get(i).isMod_Up();
                String s_up = String.valueOf(mod_up);
                if (mod_up == true) {
                    s_up = "T";
                } else if (mod_up == false) {
                    s_up = "F";
                }
                listUPs.add(s_up);
                boolean mod_out = user_Mod2.get(i).isMod_Out();
                String s_out = String.valueOf(mod_out);
                if (mod_out == true) {
                    s_out = "T";
                } else if (mod_out == false) {
                    s_out = "F";
                }
                listOUTs.add(s_out);
            }
            String ids_mod = list_id_mod.toString().substring(1,
                    -1 + list_id_mod.toString().length());
            String ids_db = listmODDBs.toString().substring(1,
                    -1 + listmODDBs.toString().length());
            String names = listNAMEs.toString().substring(1,
                    -1 + listNAMEs.toString().length());
            String adds = listADDs.toString().substring(1,
                    -1 + listADDs.toString().length());
            String querys = listQUERYs.toString().substring(1,
                    -1 + listQUERYs.toString().length());
            String amts = listAMTs.toString().substring(1,
                    -1 + listAMTs.toString().length());
            String csts = listCSTs.toString().substring(1,
                    -1 + listCSTs.toString().length());
            String dels = listDELs.toString().substring(1,
                    -1 + listDELs.toString().length());
            String prs = listPRs.toString().substring(1,
                    -1 + listPRs.toString().length());
            String prgs = listPRGs.toString().substring(1,
                    -1 + listPRGs.toString().length());
            String prts = listPRTs.toString().substring(1,
                    -1 + listPRTs.toString().length());
            String qtys = listQTYs.toString().substring(1,
                    -1 + listQTYs.toString().length());
            String sets = listSETs.toString().substring(1,
                    -1 + listSETs.toString().length());
            String ups = listUPs.toString().substring(1,
                    -1 + listUPs.toString().length());
            String outs = listOUTs.toString().substring(1,
                    -1 + listOUTs.toString().length());
            Log.e("LiNing", "得到数据-----list" + ids_mod + "///" + names
                    + "///" + adds);
            // 数据提交前封装（模块）
            Editor edit2 = sp.edit();
            edit2.putString("old_modinfo_id", ids_mod).commit();
            edit2.putString("old_modinfo_db", ids_db).commit();
            edit2.putString("old_modinfo_name", names).commit();
            edit2.putString("old_modinfo_add", adds).commit();
            edit2.putString("old_modinfo_query", querys).commit();
            edit2.putString("old_modinfo_amt", amts).commit();
            edit2.putString("old_modinfo_cst", csts).commit();
            edit2.putString("old_modinfo_del", dels).commit();
            edit2.putString("old_modinfo_pr", prs).commit();
            edit2.putString("old_modinfo_prg", prgs).commit();
            edit2.putString("old_modinfo_prt", prts).commit();
            edit2.putString("old_modinfo_qty", qtys).commit();
            edit2.putString("old_modinfo_set", sets).commit();
            edit2.putString("old_modinfo_up", ups).commit();
            edit2.putString("old_modinfo_out", outs).commit();
            Log.e("LiNing",
                    "得到数据88888888" + sp.getString("old_modinfo_id", ""));
            //旧数据query
            user_Query2 = users.get(positions).getUser_Query();
            Log.e("LiNing", "cx数据是====" + user_Query2.toString());
            ArrayList list_id = new ArrayList<String>();
            ArrayList listdep = new ArrayList<String>();
            ArrayList listsup = new ArrayList<String>();
            ArrayList listcust = new ArrayList<String>();
            ArrayList listuser = new ArrayList<String>();
            ArrayList lisths = new ArrayList<String>();
            for (int i = 0; i < user_Query2.size(); i++) {
                String DB_comit = user_Query2.get(i).getQuery_DB();
                list_id.add(DB_comit);
                String dep_comit = user_Query2.get(i).getQuery_Dep();
                listdep.add(dep_comit);
                String sup_comit = user_Query2.get(i).getQuery_Sup();
                listsup.add(sup_comit);
                String cust_comit = user_Query2.get(i).getQuery_Cust();
                listcust.add(cust_comit);
                String user_comit = user_Query2.get(i).getQuery_User();
                listuser.add(user_comit);
                String hs_comit = user_Query2.get(i).getQuery_CompDep();
                lisths.add(hs_comit);
            }
//			sp.edit().putString("sp_query_old", ""+new Gson().toJson(user_Query2)).commit();
            String ids = list_id.toString().substring(1,
                    -1 + list_id.toString().length());
            String deps = listdep.toString().substring(1,
                    -1 + listdep.toString().length());
            String sups = listsup.toString().substring(1,
                    -1 + listsup.toString().length());
            String custs = listcust.toString().substring(1,
                    -1 + listcust.toString().length());
            String users = listuser.toString().substring(1,
                    -1 + listuser.toString().length());
            String hs = lisths.toString().substring(1,
                    -1 + lisths.toString().length());
            // 数据提交前封装（查询）
            Editor edit21 = sp.edit();
            edit21.putString("old_queryinfo_id", ids).commit();
            edit21.putString("old_queryinfo_dep", deps).commit();
            edit21.putString("old_queryinfo_sup", sups).commit();
            edit21.putString("old_queryinfo_cust", custs).commit();
            edit21.putString("old_queryinfo_user", users).commit();
            edit21.putString("old_queryinfo_hs", hs).commit();
            Log.e("LiNing", "得到数据======list" + ids + "///" + deps + "///"
                    + sups);
            Toast.makeText(CusterInfoActivity.this, nameExtra + "被选中！",
                    Toast.LENGTH_SHORT).show();
        }

        List<User_Mod> user_Mod2;

        private void oldInfoTwo() {
            //旧数据mod
//			List<User_Mod> user_Mod2 = users.get(positions).getUser_Mod();
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("user_Id", idExtra)
                    .build();
            client.newCall(
                    new Request.Builder().addHeader("cookie", session).url(urlGet)
                            .post(body).build()).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String str = response.body().string();
                    Log.e("LiNing", "--------" + str);
                    Gson gson = new GsonBuilder().setDateFormat(
                            "yyyy-MM-dd HH:mm:ss").create();
                    final CusterInfoDB cInfoDB = gson.fromJson(str,
                            CusterInfoDB.class);
                    if (cInfoDB != null) {
                        CusterInfoActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                user_Mod2 = cInfoDB.getUsers().get(0)
                                        .getUser_Mod();
                                user_Query2 = cInfoDB.getUsers().get(0)
                                        .getUser_Query();
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call call, IOException e) {

                }


            });

//			Log.e("LiNing", "mk数据是====" + user_Mod2.toString());
            if (user_Mod2 != null && user_Mod2.size() > 0) {

                ArrayList<String> list_id_mod = new ArrayList<String>();
                ArrayList<String> listNAMEs = new ArrayList<String>();
                ArrayList<String> listmODDBs = new ArrayList<String>();
                ArrayList<String> listQUERYs = new ArrayList<String>();
                ArrayList<String> listADDs = new ArrayList<String>();
                ArrayList<String> listAMTs = new ArrayList<String>();
                ArrayList<String> listCSTs = new ArrayList<String>();
                ArrayList<String> listDELs = new ArrayList<String>();
                ArrayList<String> listPRs = new ArrayList<String>();
                ArrayList<String> listPRGs = new ArrayList<String>();
                ArrayList<String> listPRTs = new ArrayList<String>();
                ArrayList<String> listQTYs = new ArrayList<String>();
                ArrayList<String> listSETs = new ArrayList<String>();
                ArrayList<String> listUPs = new ArrayList<String>();
                ArrayList<String> listOUTs = new ArrayList<String>();
                for (int i = 0; i < user_Mod2.size(); i++) {
                    String mod_ID = user_Mod2.get(i).getMod_ID();
                    list_id_mod.add(mod_ID);
                    String mod_DB = user_Mod2.get(i).getDb_ID();
                    listmODDBs.add(mod_DB);
                    String mod_name = user_Mod2.get(i).getMod_Name();
                    listNAMEs.add(mod_name);
                    boolean mod_Add = user_Mod2.get(i).isMod_Add();
                    String s_add = String.valueOf(mod_Add);
                    Log.e("LiNing", "得到数据-----list" + s_add);
                    if (mod_Add == true) {
                        s_add = "T";
                    } else if (mod_Add == false) {
                        s_add = "F";
                    }
                    listADDs.add(s_add);
                    boolean mod_querry = user_Mod2.get(i).isMod_Query();
                    String s_query = String.valueOf(mod_querry);
                    if (mod_querry == true) {
                        s_query = "T";
                    } else if (mod_querry == false) {
                        s_query = "F";
                    }
                    listQUERYs.add(s_query);
                    boolean mod_amt = user_Mod2.get(i).isMod_Amt();
                    String s_amt = String.valueOf(mod_amt);
                    if (mod_amt == true) {
                        s_amt = "T";
                    } else if (mod_amt == false) {
                        s_amt = "F";
                    }
                    listAMTs.add(s_amt);
                    boolean mod_cst = user_Mod2.get(i).isMod_Cst();
                    String s_cst = String.valueOf(mod_cst);
                    if (mod_cst == true) {
                        s_cst = "T";
                    } else if (mod_cst == false) {
                        s_cst = "F";
                    }
                    listCSTs.add(s_cst);
                    boolean mod_del = user_Mod2.get(i).isMod_Del();
                    String s_del = String.valueOf(mod_del);
                    if (mod_del == true) {
                        s_del = "T";
                    } else if (mod_del == false) {
                        s_del = "F";
                    }
                    listDELs.add(s_del);
                    boolean mod_gp = user_Mod2.get(i).isMod_GP();
                    String s_gp = String.valueOf(mod_gp);
                    if (mod_gp == true) {
                        s_gp = "T";
                    } else if (mod_gp == false) {
                        s_gp = "F";
                    }
                    listPRs.add(s_gp);
                    boolean mod_gpr = user_Mod2.get(i).isMod_GPR();
                    String s_gpr = String.valueOf(mod_gpr);
                    if (mod_gpr == true) {
                        s_gpr = "T";
                    } else if (mod_gpr == false) {
                        s_gpr = "F";
                    }
                    listPRGs.add(s_gpr);
                    boolean mod_prt = user_Mod2.get(i).isMod_Prt();
                    String s_prt = String.valueOf(mod_prt);
                    if (mod_prt == true) {
                        s_prt = "T";
                    } else if (mod_prt == false) {
                        s_prt = "F";
                    }
                    listPRTs.add(s_prt);
                    boolean mod_qty = user_Mod2.get(i).isMod_Qty();
                    String s_qty = String.valueOf(mod_qty);
                    if (mod_qty == true) {
                        s_qty = "T";
                    } else if (mod_qty == false) {
                        s_qty = "F";
                    }
                    listQTYs.add(s_qty);
                    boolean mod_set = user_Mod2.get(i).isMod_Alter();
                    String s_set = String.valueOf(mod_set);
                    if (mod_set == true) {
                        s_set = "T";
                    } else if (mod_set == false) {
                        s_set = "F";
                    }
                    listSETs.add(s_set);
                    boolean mod_up = user_Mod2.get(i).isMod_Up();
                    String s_up = String.valueOf(mod_up);
                    if (mod_up == true) {
                        s_up = "T";
                    } else if (mod_up == false) {
                        s_up = "F";
                    }
                    listUPs.add(s_up);
                    boolean mod_out = user_Mod2.get(i).isMod_Out();
                    String s_out = String.valueOf(mod_out);
                    if (mod_out == true) {
                        s_out = "T";
                    } else if (mod_out == false) {
                        s_out = "F";
                    }
                    listOUTs.add(s_out);
                }

                //-------------------
                String zts_str = "";

                for (String zt : listmODDBs) {
                    zts_str += zt + ",";
                }
                String sub_zt = zts_str.substring(0, zts_str.length() - 1);
                String ids_str = "";
                for (String id : list_id_mod) {
                    ids_str += id + ",";
                }
                String sub_id = ids_str.substring(0, ids_str.length() - 1);
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
                // 数据提交前封装（模块）
                Editor edit2 = sp.edit();
                edit2.putString("old_modinfo_id", sub_id).commit();
                edit2.putString("old_modinfo_db", sub_zt).commit();
                edit2.putString("old_modinfo_name", sub_name).commit();
                edit2.putString("old_modinfo_add", sub_add).commit();
                edit2.putString("old_modinfo_query", sub_query).commit();
                edit2.putString("old_modinfo_amt", sub_amt).commit();
                edit2.putString("old_modinfo_cst", sub_cst).commit();
                edit2.putString("old_modinfo_del", sub_del).commit();
                edit2.putString("old_modinfo_pr", sub_gp).commit();
                edit2.putString("old_modinfo_prg", sub_gpr).commit();
                edit2.putString("old_modinfo_prt", sub_prt).commit();
                edit2.putString("old_modinfo_qty", sub_qty).commit();
                edit2.putString("old_modinfo_set", sub_alt).commit();
                edit2.putString("old_modinfo_up", sub_up).commit();
                edit2.putString("old_modinfo_out", sub_out).commit();
                Log.e("LiNing",
                        "得到数据88888888" + sp.getString("old_modinfo_id", ""));
            }
            //旧数据query
//			user_Query2 = users.get(positions).getUser_Query();
            if (user_Query2 != null && user_Query2.size() > 0) {

                Log.e("LiNing", "cx数据是====" + user_Query2.toString());
                ArrayList<String> list_id = new ArrayList<String>();
                ArrayList<String> listdep = new ArrayList<String>();
                ArrayList<String> listsup = new ArrayList<String>();
                ArrayList<String> listcust = new ArrayList<String>();
                ArrayList<String> listuser = new ArrayList<String>();
                ArrayList<String> lisths = new ArrayList<String>();
                for (int i = 0; i < user_Query2.size(); i++) {
                    String DB_comit = user_Query2.get(i).getQuery_DB();
                    list_id.add(DB_comit);
                    String dep_comit = user_Query2.get(i).getQuery_Dep();
                    listdep.add(dep_comit);
                    String sup_comit = user_Query2.get(i).getQuery_Sup();
                    listsup.add(sup_comit);
                    String cust_comit = user_Query2.get(i).getQuery_Cust();
                    listcust.add(cust_comit);
                    String user_comit = user_Query2.get(i).getQuery_User();
                    listuser.add(user_comit);
                    String hs_comit = user_Query2.get(i).getQuery_CompDep();
                    lisths.add(hs_comit);
                }
                sp.edit().putString("sp_query_old", "" + new Gson().toJson(user_Query2)).commit();
                String querys_db_str = "";
                for (String querys_db : list_id) {
                    querys_db_str += querys_db + ",";
                }
                String sub_querys_db = querys_db_str.substring(0,
                        querys_db_str.length() - 1);
                String querys_dep_str = "";
                for (String querys_dep : listdep) {
                    querys_dep_str += querys_dep + ";";
                }
                String sub_querys_dep = querys_dep_str.substring(0,
                        querys_dep_str.length() - 1);
                String querys_sup_str = "";
                for (String querys_sup : listsup) {
                    querys_sup_str += querys_sup + ";";
                }
                String sub_querys_sup = querys_sup_str.substring(0,
                        querys_sup_str.length() - 1);
                String querys_cust_str = "";
                for (String querys_cust : listcust) {
                    querys_cust_str += querys_cust + ";";
                }
                String sub_querys_cust = querys_cust_str.substring(0,
                        querys_cust_str.length() - 1);
                String querys_user_str = "";
                for (String querys_user : listuser) {
                    querys_user_str += querys_user + ";";
                }
                String sub_querys_user = querys_user_str.substring(0,
                        querys_user_str.length() - 1);
                String querys_hs_str = "";
                for (String querys_hs : lisths) {
                    querys_hs_str += querys_hs + ";";
                }
                String sub_querys_hs = querys_hs_str.substring(0,
                        querys_hs_str.length() - 1);
                // 数据提交前封装（查询）
                Editor edit21 = sp.edit();
                edit21.putString("old_queryinfo_id", sub_querys_db).commit();
                edit21.putString("old_queryinfo_dep", sub_querys_dep).commit();
                edit21.putString("old_queryinfo_sup", sub_querys_sup).commit();
                edit21.putString("old_queryinfo_cust", sub_querys_cust).commit();
                edit21.putString("old_queryinfo_user", sub_querys_user).commit();
                edit21.putString("old_queryinfo_hs", sub_querys_hs).commit();
                Toast.makeText(CusterInfoActivity.this, nameExtra + "被选中！",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void allback(View v) {
        finish();
    }

}
