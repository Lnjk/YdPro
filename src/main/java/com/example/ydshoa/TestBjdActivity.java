package com.example.ydshoa;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.bean.BackApplication;
import com.example.bean.NetUtil;
import com.example.bean.URLS;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class TestBjdActivity extends Activity  {
    private ImageButton up, out, next, er, signatureAdd;
    private ImageView qrcodeImageView, signatureImg;
    private SharedPreferences sp;
    private static final int PHOTO_REQUEST_GALLERY = 8;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 9;// 结果
    private String pathSignature,session;
    private Bitmap bitmap_xc;
    private File file_sighn;
    private String resultStr = ""; // 服务端返回结果集
    String urlUser = URLS.cust_bjxx_add;
//    String urlUser = URLS.cust_bjxx_qyery;
//    String urlUser = URLS.cust_bjxx_update;
//    String urlUser = URLS.cust_bjxx_del;
    Handler handler = new Handler(new Handler.Callback() {

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
        setContentView(R.layout.activity_test_bjd);
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        signatureAdd = (ImageButton) findViewById(R.id.ib_signatureadd);
        signatureImg = ((ImageView) findViewById(R.id.iv_signature));
        signatureAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 激活系统图库，选择一张图片
                Intent intentPhoto = new Intent(Intent.ACTION_PICK);
                intentPhoto.setType("image/*");
                // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
                startActivityForResult(intentPhoto, PHOTO_REQUEST_GALLERY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case PHOTO_REQUEST_GALLERY:
                // 从相册返回的数据
                if (data != null) {
                    // 得到图片的全路径
                    Uri uri = data.getData();
                    Log.e("LiNing", "-------" + uri);
                    crop(uri);
                    String[] pojo = { MediaStore.MediaColumns.DATA };
                    Cursor cursor = getContentResolver().query(uri, pojo, null,
                            null, null);
                    if (cursor != null) {
                        int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
                        cursor.moveToFirst();
                        pathSignature = cursor.getString(columnIndex);

                        BitmapFactory.Options newOpts = new BitmapFactory.Options();
                        newOpts.inJustDecodeBounds = true;
                        bitmap_xc = BitmapFactory
                                .decodeFile(pathSignature, newOpts);
                        newOpts.inJustDecodeBounds = false;

                        int w = newOpts.outWidth;
                        int h = newOpts.outHeight;
                        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
                        float hh = 800f;// 这里设置高度为800f
                        float ww = 480f;// 这里设置宽度为480f
                        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
                        int be = 1;// be=1表示不缩放
                        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
                            be = (int) (newOpts.outWidth / ww);
                        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
                            be = (int) (newOpts.outHeight / hh);
                        }
                        if (be <= 0)
                            be = 1;
                        newOpts.inSampleSize = be;// 设置缩放比例
                        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
                        bitmap_xc = BitmapFactory
                                .decodeFile(pathSignature, newOpts);
                        sp.edit().putString("photoURL", pathSignature).commit();

                    }
                }
                break;
            case PHOTO_REQUEST_CUT:
                // 从剪切图片返回的数据
                if (data != null) {
                    Bitmap bitmap = data.getParcelableExtra("data");
                    // * 获得图片
                    signatureImg.setImageBitmap(bitmap);
                    // 保存到SharedPreferences
                    saveBitmapToSharedPreferences(bitmap);

                    saves(bitmap);
                }
                break;
            default:
                break;
        }
    }
    private void saves(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 40, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String imageString = new String(Base64.encodeToString(byteArray,
                Base64.DEFAULT));
        sp.edit().putString("testImg", imageString).commit();
    }
    private void saveBitmapToSharedPreferences(Bitmap bitmap) {
        // 第一步:将Bitmap压缩至字节数组输出流ByteArrayOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 40, byteArrayOutputStream);
        // 第二步:利用Base64将字节数组输出流中的数据转换成字符串String
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String imageString = new String(Base64.encodeToString(byteArray,
                Base64.DEFAULT));
        InputStream signature = new ByteArrayInputStream(imageString.getBytes());
        // 第三步:将String保持至SharedPreferences
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("STR_SImg", imageString);
        editor.putString("image", "" + signature);
        editor.commit();

        // 上传参数
        File file = new File(pathSignature);
        if (!file.exists()) {
            file.mkdir();
        }
        BufferedOutputStream bos;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }
    public void bjdtest(View v) {

        if(pathSignature==null){
            pathSignature="";
            file_sighn = new File(pathSignature);
            Log.e("LiNing", "pathSignature－－－－－－－－" + pathSignature);
        }else{

            file_sighn = new File(pathSignature);
        }
        Log.e("LiNing", "file_sighn" + file_sighn);
        new Thread(new Runnable() {
            @Override
            public void run() {


        try {
        // 创建一个URL对象
        URL url = new URL(urlUser);
        Map<String, String> textParams_user = new HashMap<String, String>();
        Map<String, File> fileparams = new HashMap<String, File>();
        // 要上传的普通参数
        textParams_user.put("DB_ID", "DB_BJ15");
        textParams_user.put("QT_NO", "QT1905090006");//报价单号
        textParams_user.put("BZ_TYPE", "01");//业务类型
        textParams_user.put("QT_DD", "2019-5-9");//时间
        textParams_user.put("Cust_Src", "网络");//客户来源
        textParams_user.put("Remd", "ln222");//推荐媒介
        textParams_user.put("BIL_TYPE", "009");//单据类别vip
        textParams_user.put("Cus_No", "JH001");//终端编号
        textParams_user.put("DEP_NO", "0");//部门编号

        textParams_user.put("SAL_NO", "A001");//顾问编号
        textParams_user.put("SER_NO", "A002");//服务管家
        textParams_user.put("STYL_NO", "A001");//制图设计
        textParams_user.put("AFFI_NO", "A001");//生产业务
        textParams_user.put("DEP_RED", "3");//预收货款
        textParams_user.put("PRT_SW", "N");//打印状态
        textParams_user.put("KH_NO", "kh1811040005");//客户编号
        textParams_user.put("Cust_Tel", "18511403631");//客户电话
        textParams_user.put("Hous_Type", "小区");//楼盘类型

        textParams_user.put("Deco_Com", "远东神华");//装修公司
        textParams_user.put("Vip_NO", "VP1810160003");//vip会员
        textParams_user.put("SEND_MTH", "1");//交货方式1，送货
        textParams_user.put("CUS_OS_NO", "no.1");//合同单号
        textParams_user.put("CUS_CON_ITM", "04");//配送列号
        textParams_user.put("Con_Per", "李宁");//收货人
        textParams_user.put("Con_Tel", "18511403631");//收货电话
        textParams_user.put("Con_Crt", "北京市");//省市
        textParams_user.put("Con_Spa", "北京市");//区县
//            private String FILESFileName;
        textParams_user.put("Con_Add", "新华百货~A座");//详细地址
        textParams_user.put("REM", "测试");//备注
        textParams_user.put("USR", "ADMIN");//制单人
        textParams_user.put("USR_CHK", "ADMIN");//审核人
        textParams_user.put("CHK_DD", "2019-5-9");//审核日期
        textParams_user.put("AFFI_DD", "2019-5-9");//确单日期
        //数组
            textParams_user.put("ITM", "1,3");//行号
            textParams_user.put("PRD_NO", "A1178C,LN2");//品号
            textParams_user.put("PRD_NAME", "A1178C单孔面盆龙头,李宁2");//品名
            textParams_user.put("PRD_MARK", "AA,CC");//色号
            textParams_user.put("WH", "019,021");//库位
            textParams_user.put("UNIT", "1,3");//单位不能传汉子“套
            textParams_user.put("QTY", "10,30");//数量
            textParams_user.put("UP", "20,40");//单价
            textParams_user.put("DIS_CNT", "0.5,0.7");//折扣
            textParams_user.put("AMTN", "100,300");//金额
            textParams_user.put("PRICE_ID", "01,03");//政策代号
            textParams_user.put("prdUp", "100,300");//政策单价
            textParams_user.put("UPR", "382.4,456");//统一标价
            textParams_user.put("UP_MIN", "2.0,4.0");//最低售价
            textParams_user.put("CST_STD", "100,300");//标准成本
            textParams_user.put("EST_SUB", "-900,-300");//预估毛利
            textParams_user.put("SUB_GPR", "-9.0,-3.0");//毛利率
            textParams_user.put("OS_NO", "SO001,S0003");//受订单号
            textParams_user.put("OS_QTY", "100,300");//受订数量
            textParams_user.put("SPC_NO", "PF001,PF002");//生产单号
            textParams_user.put("SPC_QTY", "10,30");//领料数量

            textParams_user.put("SPC", "3*4*5,3*4*5");//商品规格
            textParams_user.put("PAK_UNIT", "箱,箱");//包装单位
            textParams_user.put("PAK_EXC", "1,4");//包装换算
            textParams_user.put("PAK_QTY", "10,30");//包装数量
            textParams_user.put("PAK_NW", "12,14");//包装净重
            textParams_user.put("PAK_GW", "13,15");//包装毛重
            textParams_user.put("PAK_MEAST", "60,80");//包装大小
            textParams_user.put("REMT", "测试明细,测试明细3");//摘要
            textParams_user.put("ISLP", "F,F");//是否是特殊政策
            Log.e("LiNing", "textParams_user-----------" + textParams_user);
        if (!file_sighn.equals("") && file_sighn != null
                && file_sighn.exists()) {

            fileparams.put("FILES", file_sighn);
        }
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
        Log.e("LiNing", "ddddddddddd" + fileparams.get("FILES"));
//        //图片上传判断（此处很重要）
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
        }catch (Exception e) {
            e.printStackTrace();
        }
                handler.sendEmptyMessage(0);// 执行耗时的方法之后发送消给handler
            }
        }).start();
    }
}
