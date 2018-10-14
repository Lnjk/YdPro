package com.example.ydshoa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Secure;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.example.bean.MyDBHelper;
import com.example.bean.SysApplication;
import com.example.bean.URLS;
import com.example.zxing.CaptureActivity;
//import com.umeng.message.PushAgent;

public class LoginMainActivity extends Activity implements OnClickListener {
    private TextView account, pwd, head;
    private CheckBox checkboxButton = null;
    private MyDBHelper dbHelper;
    private Button login, getDevice;
    private EditText user, psd, yzm;
    private ImageView set, yzmtp, er, gonek, goneg;
    private SharedPreferences sp;
    private String str1, str2, str3, s, iMEI;
    private OkHttpClient okHttpClient;
    private static final int SUCCESS = 1;
    private static final int FALL = 2;
    private boolean isHideFirst = true;// 输入框密码是否是隐藏的，默认为true
    private List<String> list = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    // private String base_Url = "http://oa.ydshce.com:8080";
    private String base_Url;
    private Spinner spUrl;
    private String login_url;
    // String urllogin = URLS.login_url;
    // String urlyzm = URLS.base_url +
    // "/InfManagePlatform/IdentityServlet.do?ts="
    // + new Date().getTime();
    // String urlyzm = base_Url + "/InfManagePlatform/IdentityServlet.do?ts="
    // + new Date().getTime();
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    byte[] Picture = (byte[]) msg.obj;
                    Bitmap bitmap = BitmapFactory.decodeByteArray(Picture, 0,
                            Picture.length);
                    yzmtp.setImageBitmap(bitmap);

                    break;
                // 当加载网络失败执行的逻辑代码
                case FALL:
                    Toast.makeText(LoginMainActivity.this, "网络出现了问题",
                            Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_main);
        //推送
//        PushAgent.getInstance(LoginMainActivity.this).onAppStart();
        SysApplication.getInstance().addActivity(this);
        this.dbHelper = new MyDBHelper(this, "UserStoreS.db", null, 1);
        this.sp = getSharedPreferences("ydbg", 0);
        // 获取手机唯一ID
        iMEI = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
        sp.edit().putString("ANDROIDID", iMEI).commit();
//是否第一次登陆
        isfirstLogin();
        // 初始化
        this.user = ((EditText) findViewById(R.id.et_login_account));
        this.psd = ((EditText) findViewById(R.id.et_login_pwd));

        user.setText(sp.getString("USER", "").toString());
        psd.setText(sp.getString("PWD", "").toString());
        this.yzm = ((EditText) findViewById(R.id.et_login_yzm));
        this.checkboxButton = ((CheckBox) findViewById(R.id.savePasswordCB));
        if (this.sp.getBoolean("checkboxBoolean", false)) {
            this.user.setText(this.sp.getString("uname", null));
            this.psd.setText(this.sp.getString("upswd", null));
            this.checkboxButton.setChecked(true);
        }
        this.head = ((TextView) findViewById(R.id.textView1));
        this.login = ((Button) findViewById(R.id.btn_login));
        this.getDevice = ((Button) findViewById(R.id.getDevice));
        this.yzmtp = ((ImageView) findViewById(R.id.iv_yzm));
        this.er = ((ImageView) findViewById(R.id.iv_er));
        this.gonek = ((ImageView) findViewById(R.id.iv_gonek));
        spUrl = (Spinner) findViewById(R.id.sp_url);
        gonek.setImageResource(R.drawable.yanjingkai);
        gonek.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isHideFirst == true) {
                    gonek.setImageResource(R.drawable.yanjingkai);
                    psd.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());
                    isHideFirst = false;
                } else {
                    gonek.setImageResource(R.drawable.yanjingguan);
                    psd.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());
                    isHideFirst = true;
                }
                int index = psd.getText().toString().length();// 光标的位置
                psd.setSelection(index);
            }
        });
        getUrl();

        this.login.setOnClickListener(this);// 登录
        this.getDevice.setOnClickListener(this);// 获取设备码
        this.yzmtp.setOnClickListener(this);// 获取验证码

        getSpToNull();
    }

    private static LoginMainActivity loginMainActivity;

    public LoginMainActivity() {
        loginMainActivity = this;
    }

    public static LoginMainActivity getMainActivity() {
        return loginMainActivity;
    }

    public String Url() {
        return base_Url;
    }

    public void getUrl() {
        if (list.size() < 0 && list == null) {
            Toast.makeText(LoginMainActivity.this, "请新增IP", Toast.LENGTH_LONG).show();
        } else {
            final String name1 = sp.getString("N1", "");
            final String name2 = sp.getString("N2", "");
            final String name3 = sp.getString("N3", "");
            final String ip1 = sp.getString("P1", "");
            final String ip2 = sp.getString("P2", "");
            final String ip3 = sp.getString("P3", "");
            Log.e("LiNing", "======" + name1 + name2 + name3);
            if (!name1.equals("")) {
                list.add(name1);
            }
            if (!name2.equals("")) {
                list.add(name2);
            }
            if (!name3.equals("")) {
                list.add(name3);
            }
            Log.e("LiNing", "======" + list);
            adapter = new ArrayAdapter<String>(LoginMainActivity.this,
                    android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spUrl.setAdapter(adapter);
            spUrl.setSelection(0);
            spUrl.setOnItemSelectedListener(new OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    /* 将mySpinner 显示 */
                    parent.setVisibility(View.VISIBLE);
                    if (spUrl.getSelectedItem().equals(name1)) {
                        base_Url = ip1;
                        Log.e("LiNing", "--------" + base_Url);
                    }
                    if (spUrl.getSelectedItem().equals(name2)) {
                        base_Url = ip2;
                    }
                    if (spUrl.getSelectedItem().equals(name3)) {
                        base_Url = ip3;
                    }
                    login_url = base_Url
                            + "/InfManagePlatform/LoginAction.action";
                    getsession();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    parent.setVisibility(View.VISIBLE);
                }
            });
            spUrl.setOnTouchListener(new Spinner.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
            spUrl.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                }
            });
        }

    }

    private void getsession() {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder().build();
        Request request = new Request.Builder().url(login_url).build();
        Call call2 = client.newCall(request);
        call2.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                if (response.isSuccessful()) {
                    Log.i("LiNing", response.body().string());
                }
                // session
                Headers headers = response.headers();
                Log.i("LiNing", headers + "");
                Log.d("info_headers", "header " + headers);
                List<String> cookies = headers.values("Set-Cookie");
                String session = cookies.get(0);
                Log.d("info_cookies", "onResponse-size: " + cookies);
                s = session.substring(0, session.indexOf(";"));
                Log.i("info_s", "session is  :" + s);
                Log.e("LiNing", "session is  :" + s);
                Editor edit = sp.edit();
                edit.putString("SESSION", s).commit();

            }

            @Override
            public void onFailure(Call arg0, IOException arg1) {

            }
        });
    }

    public void baner(View v) {
        Toast.makeText(this, "二维码登陆", Toast.LENGTH_LONG).show();// 后续开发
        config();
        startActivityForResult(new Intent(LoginMainActivity.this,
                CaptureActivity.class), 0);
    }

    // 提高屏幕亮度
    private void config() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.screenBrightness = 1.0f;
        getWindow().setAttributes(lp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");
            Log.e("LiNing", "======" + result);
            Toast.makeText(this, "扫描结果===" + result, Toast.LENGTH_LONG).show();
        }
    }

    public void onClick(View paramView) {
        switch (paramView.getId()) {
            case R.id.btn_login:

                if (spUrl.getSelectedItem().toString().trim().equals("")
                        || spUrl.getSelectedItem().toString().trim().equals(null)) {
                    Toast.makeText(LoginMainActivity.this, "请新增IP", Toast.LENGTH_LONG).show();
                }

                str1 = this.user.getText().toString();
                str2 = this.psd.getText().toString();
                str3 = yzm.getText().toString();
                if (str1.trim().equals("")) {
                    Toast.makeText(this, "请您输入用户名！", Toast.LENGTH_LONG).show();
                }
                if (str2.trim().equals("")) {
                    Toast.makeText(this, "请您输入密码！", Toast.LENGTH_LONG).show();
                }
                if (this.checkboxButton.isChecked()) {
                    SharedPreferences.Editor localEditor2 = this.sp.edit();
                    localEditor2.putString("uname", str1);
                    localEditor2.putString("upswd", str2);
                    localEditor2.putBoolean("checkboxBoolean", true);
                    localEditor2.commit();
                } else {
                    SharedPreferences.Editor localEditor1 = this.sp.edit();
                    localEditor1.putString("uname", null);
                    localEditor1.putString("upswd", null);
                    localEditor1.putBoolean("checkboxBoolean", false);
                    localEditor1.commit();
                }
                OkHttpClient client = new OkHttpClient();
                FormBody body = new FormBody.Builder().add("userId", str1)
                        .add("passWord", str2).add("loginImage", str3).build();
                Request request = new Request.Builder().addHeader("cookie", s)
                        .url(URLS.login_url).post(body).build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {

                    @Override
                    public void onResponse(Call call, Response response)
                            throws IOException {

                        if (response.isSuccessful()) {
                            Log.i("info_call2success", response.body().string());
                            Log.e("LiNing", "spn======" + response.code()+str1+str2+str3+URLS.login_url);
                            if (response.code() == 200) {
                                Headers headers = response.headers();
                                List<String> values = headers
                                        .values("Content-Length");
                                if (values.size() > 0 && values != null) {
                                    LoginMainActivity.this
                                            .runOnUiThread(new Runnable() {

                                                @Override
                                                public void run() {
                                                    Toast.makeText(
                                                            LoginMainActivity.this,
                                                            "登录信息有误，请确认信息", Toast.LENGTH_LONG)
                                                            .show();
                                                }
                                            });
                                } else {
                                    startActivity(new Intent(
                                            LoginMainActivity.this,
                                            MainActivity.class));
                                    // finish();
                                }
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call arg0, IOException arg1) {

                    }
                });
                break;
            case R.id.iv_yzm:

                if (spUrl.getSelectedItem().toString().trim().equals("")
                        || spUrl.getSelectedItem().toString().trim().equals(null)) {
                    Toast.makeText(LoginMainActivity.this, "请新增IP", Toast.LENGTH_LONG).show();
                } else {
                    ChangeImage();
                }
                Log.e("LiNing", "spn======" + spUrl.getSelectedItem());
                break;
            case R.id.getDevice:
//                startActivity(new Intent(LoginMainActivity.this,
//                        GetDeviseActivity.class));
                startActivity(new Intent(LoginMainActivity.this,
                        PriceActivity.class));
                break;

            default:
                break;
        }

    }

    // String url000 = URLS.yzm_url;

    private void ChangeImage() {
        Log.e("LiNing",
                "url=====" + base_Url
                        + "/InfManagePlatform/IdentityServlet.do?ts="
                        + new Date().getTime());
        if (s == null) {
            Toast.makeText(LoginMainActivity.this, "连接失败，请检查", Toast.LENGTH_LONG).show();
        } else {
            okHttpClient = new OkHttpClient();
            // Request request = new Request.Builder().addHeader("cookie",
            // s)
            // .url(urlyzm).build();
            Request request = new Request.Builder().addHeader("cookie", s)
                    .url(URLS.yzm_url).build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {

                @Override
                public void onResponse(Call call, Response response)
                        throws IOException {
                    byte[] byte_image = response.body().bytes();
                    Log.e("LiNing", "验证码22222222==" + byte_image);
                    // 通过handler更新UI
                    Message message = handler.obtainMessage();
                    message.obj = byte_image;
                    message.what = SUCCESS;
                    Log.i("info_handler", "-------" + message);
                    handler.sendMessage(message);
                    Log.e("LiNing", "验证码表头==" + response.headers());
                    Log.e("LiNing",
                            "验证码cookie=="
                                    + response.headers().values("Set-Cookie"));
                }

                @Override
                public void onFailure(Call arg0, IOException arg1) {

                }
            });
        }
    }

    public void url_get(View v) {
        sp.edit().putString("USER", user.getText().toString()).commit();
        sp.edit().putString("PWD", psd.getText().toString()).commit();
        startActivity(new Intent(LoginMainActivity.this, UrlSetActivity.class));
        finish();
    }

    private void getSpToNull() {
        //条件单次查询
        sp.edit().putString("CSTOSNO_CDTA", "").commit();
        sp.edit().putString("REM_CDTA", "").commit();
        sp.edit().putString("REM_CDTA", "").commit();
        sp.edit().putString("HS_CDTA", "").commit();
        sp.edit().putString("DEP_CDTA", "").commit();
        sp.edit().putString("SUP_CDTA", "").commit();
        sp.edit().putString("CUST_CDTA", "").commit();
        sp.edit().putString("USER_CDTA", "").commit();
        sp.edit().putString("CHKUSER_CDTA", "").commit();
        sp.edit().putString("AREA_CDTA", "").commit();
        sp.edit().putString("EMPL_CDTA", "").commit();
        sp.edit().putString("PRDNO_CDTA", "").commit();
        sp.edit().putString("PRDINDEX_CDTA", "").commit();
        sp.edit().putString("PRDWH_CDTA", "").commit();
        sp.edit().putString("DB_CDTA", "").commit();
        sp.edit().putString("BILNO_CDTA", "").commit();
        sp.edit().putString("CUSTNO_CDTA", "").commit();
        sp.edit().putString("CSTNAME_CDTA", "").commit();
        sp.edit().putString("CSTP_CDTA", "").commit();
        sp.edit().putString("CSTARD_CDTA", "").commit();
        sp.edit().putString("INPUTNO_CDTA", "").commit();
        //数据传递
        sp.edit().putString("sp_query_old", "").commit();
        sp.edit().putString("sp_query_new", "").commit();
        sp.edit().putString("all_query", "").commit();
        sp.edit().putString("CBQX", "").commit();
        //照片
        sp.edit().putString("photoURL", "").commit();
        //權限
        sp.edit().putString("ZC", "").commit();
        //查询账套（条件）
        sp.edit().putString("all_query_tj_to", "").commit();
        sp.edit().putString("all_query_tj", "").commit();
        sp.edit().putString("DB_MR","").commit();
        //定价管理权限
        sp.edit().putString("PRICE_QUERY", "").commit();
        sp.edit().putString("PRICE_ADD", "").commit();
        sp.edit().putString("PRICE_DEL", "").commit();
        sp.edit().putString("PRICE_OUT", "").commit();
        sp.edit().putString("PRICE_RESET", "").commit();
        sp.edit().putString("MR_YH", "").commit();
//        sp.edit().putString("modIds", "").commit();
        sp.edit().putString("USER_NAME", "").commit();
        sp.edit().putString("USER_ID", "").commit();
    }

    public void isfirstLogin() {
        boolean fristload = sp.getBoolean("fristload", true);//从SharedPreferences中获取是否第一次启动   默认为true
        if (fristload) {
            startActivity(new Intent(LoginMainActivity.this, WelcomeActivity.class));
            sp.edit().putBoolean("fristload", false).commit();//第一次启动后，将firstload 置为false 以便以后直接进入主界面不再显示欢迎界面
        }
//        else {
//            startActivity(new Intent(LoginMainActivity.this, WelcomeActivity.class));
//        }
    }
}
