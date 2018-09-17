package com.example.ydshoa;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.MediaColumns;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.bean.BackApplication;
import com.example.bean.NewPeople;
import com.example.bean.SysApplication;
import com.example.zxing.EncodingHandler;
import com.google.gson.Gson;
//import com.umeng.message.PushAgent;

public class NewAccountActivity extends Activity implements OnClickListener {
	private Spinner account_cust, account_zt, account_user, account_dep;
	// 第二版本
	private EditText default_account, default_dep, default_cust, default_user;
	private ImageButton ib_account, ib_dep, ib_cust, ib_user;
	private EditText account_id, sighn_pwd, account_name, account_pwd,
			phoneNum;
	private ImageView cbPwd;
	private Context context;
	private Editor editor;
	private TextView head;
	private boolean mbDisplayFlg = false;
	private ImageButton up, out, next, er, signatureAdd;
	private ImageView qrcodeImageView, signatureImg;
	private SharedPreferences sp;
	private boolean isHideFirst = true;// 输入框密码是否是隐藏的，默认为true
	private static final int PHOTO_REQUEST_GALLERY = 8;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 9;// 结果
	private Bitmap bitmap_xc;
	private String pathSignature;
	private String best_db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.default_new);
		BackApplication.getInstance().addActivity(NewAccountActivity.this);
		sp = getSharedPreferences("ydbg", 0);
//		PushAgent.getInstance(NewAccountActivity.this).onAppStart();
		inView();
	}

	private void inView() {

		this.head = ((TextView) findViewById(R.id.all_head));
		this.head.setText("新增用户");
		String andID = sp.getString("ANDROIDID", "");
		this.phoneNum = ((EditText) findViewById(R.id.tv_phoneNumber));
		// this.phoneNum.setText(andID);
		this.account_id = ((EditText) findViewById(R.id.et_account_id));
		this.sighn_pwd = ((EditText) findViewById(R.id.et_sighn_pwd));
		this.account_name = ((EditText) findViewById(R.id.et_account_name));
		this.account_pwd = ((EditText) findViewById(R.id.et_account_pwd));
		// this.account_zt = ((Spinner) findViewById(R.id.et_account_zt));
		// this.account_dep = ((Spinner) findViewById(R.id.et_account_dep));
		// this.account_cust = ((Spinner) findViewById(R.id.et_account_dep));
		// this.account_user = ((Spinner) findViewById(R.id.et_account_user));
		// --------------
		default_account = (EditText) findViewById(R.id.et_default_account);
		default_dep = (EditText) findViewById(R.id.et_default_dep);
		default_cust = (EditText) findViewById(R.id.et_default_cust);
		default_user = (EditText) findViewById(R.id.et_default_user);
		// ----------------
		this.next = ((ImageButton) findViewById(R.id.btn_account_next));
		this.out = ((ImageButton) findViewById(R.id.btn_account_exit));
		this.up = ((ImageButton) findViewById(R.id.btn_account_last));
		this.cbPwd = ((ImageView) findViewById(R.id.cb_pwd));
		// 个人签章
		signatureImg = ((ImageView) findViewById(R.id.iv_signature));

		cbPwd.setImageResource(R.drawable.yanjingguan);
		cbPwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isHideFirst == true) {
					cbPwd.setImageResource(R.drawable.yanjingkai);
					account_pwd
							.setTransformationMethod(HideReturnsTransformationMethod
									.getInstance());
					isHideFirst = false;
				} else {
					cbPwd.setImageResource(R.drawable.yanjingguan);
					account_pwd
							.setTransformationMethod(PasswordTransformationMethod
									.getInstance());
					isHideFirst = true;
				}
				// 光标的位置
				int index = account_pwd.getText().toString().length();
				account_pwd.setSelection(index);
			}
		});

		this.qrcodeImageView = ((ImageView) findViewById(R.id.iv_ewmcx));
		this.er = ((ImageButton) findViewById(R.id.ib_ewmcx));
		signatureAdd = ((ImageButton) findViewById(R.id.ib_signatureadd));
		// --------
		ib_account = (ImageButton) findViewById(R.id.default_account);
		ib_dep = (ImageButton) findViewById(R.id.default_dep);
		ib_cust = (ImageButton) findViewById(R.id.default_cust);
		ib_user = (ImageButton) findViewById(R.id.default_user);
		ib_account.setOnClickListener(this);
		ib_dep.setOnClickListener(this);
		ib_cust.setOnClickListener(this);
		ib_user.setOnClickListener(this);
		// ----------
		this.next.setOnClickListener(this);
		this.out.setOnClickListener(this);
		this.up.setOnClickListener(this);
		// this.er.setOnClickListener(this);
		signatureAdd.setOnClickListener(this);
		// 从SharedPreferences获取图片
		// getBitmapFromSharedPreferences();
	}

	private String picName = ".jpg";

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ib_ewmcx:
				String str = this.account_name.getText().toString();
				if ((str == null) || ("".equals(str))) {
					Toast.makeText(this, "请输入要写入二维码的内容...", Toast.LENGTH_LONG).show();
				}
				try {
					Bitmap localBitmap = EncodingHandler.createQRCode(str, 200);
					saveBitmap(localBitmap, str);

					File f = new File("/sdcard/namecard/", picName);
					if (f.exists()) {
						f.delete();
					}
					// 二维码图片
					qrcodeImageView.setImageBitmap(localBitmap);

					saveBitmap(localBitmap);
					File tempFile = new File(
							Environment.getExternalStorageDirectory(),
							getPhotoFileName());
					String path_test_er = Uri.fromFile(tempFile).getPath();
					sp.edit().putString("TEST_er", path_test_er).commit();

				} catch (Exception localException) {
					localException.printStackTrace();
				}
				break;
			// 相册获取签章图片
			case R.id.ib_signatureadd:
				// 激活系统图库，选择一张图片
				Intent intentPhoto = new Intent(Intent.ACTION_PICK);
				intentPhoto.setType("image/*");
				// 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
				startActivityForResult(intentPhoto, PHOTO_REQUEST_GALLERY);
				break;
			case R.id.btn_account_last:
				finish();
				break;
			case R.id.btn_account_next:
				if (!this.account_id.getText().toString().equals("")
						&& !this.account_name.getText().toString().equals("")
						&& !this.account_pwd.getText().toString().equals("")
						&& !this.default_account.getText().toString().equals("")) {
					// 保存信息到sp
					Editor editAll = sp.edit();
					editAll.putString("USERZTID", account_id.getText().toString());
					Log.e("LiNing", "数据是===" + account_id.getText().toString());
					editAll.putString("USERNAME", account_name.getText().toString());
					editAll.putString("DEVICEID_et", phoneNum.getText().toString());
					editAll.putString("SIGHN_pwd", sighn_pwd.getText().toString());
					editAll.putString("USERPWD", account_pwd.getText().toString());
					editAll.putString("USERACCOUNT", default_account.getText()
							.toString());
					editAll.putString("USERDEP", default_dep.getText().toString());
					editAll.putString("USERCUST", default_cust.getText().toString());
					editAll.putString("USERERPUSER", default_user.getText()
							.toString());
					editAll.commit();
					startActivity(new Intent(NewAccountActivity.this,
							PostInfoActivity.class));
				} else {
					Toast.makeText(NewAccountActivity.this, "内容不能为空", Toast.LENGTH_LONG).show();
				}

				break;
			case R.id.btn_account_exit:
				startActivity(new Intent(NewAccountActivity.this, SysActivity.class));
				finish();
				break;

			// 默认账套选取(单选)
			case R.id.default_account:
				Intent intent = new Intent(NewAccountActivity.this,
						GetInfoActivity.class);
				intent.putExtra("flag", "1");
				startActivityForResult(intent, 1);
				break;
			// 默认部门选取(单选)
			case R.id.default_dep:
				if (!default_account.getText().toString().equals("")) {

					Intent intent1 = new Intent(NewAccountActivity.this,
							ErpDepInfoActivity.class);
					intent1.putExtra("flag", "2");
					intent1.putExtra("DB_ID", default_account.getText().toString());
					startActivityForResult(intent1, 2);
				} else {
					Toast.makeText(NewAccountActivity.this, "请先选择默认账套",
							Toast.LENGTH_SHORT).show();
				}
				break;
			// 默认客户选取(单选)
			case R.id.default_cust:
				if (!default_account.getText().toString().equals("")) {

					Intent intent2 = new Intent(NewAccountActivity.this,
							ErpDepInfoActivity.class);
					intent2.putExtra("flag", "3");
					intent2.putExtra("DB_ID", default_account.getText().toString());
					startActivityForResult(intent2, 3);
				} else {
					Toast.makeText(NewAccountActivity.this, "请先选择默认账套",
							Toast.LENGTH_SHORT).show();
				}
				break;
			// 默认用户选取(单选)
			case R.id.default_user:
				if (!default_account.getText().toString().equals("")) {

					Intent intent4 = new Intent(NewAccountActivity.this,
							ErpDepInfoActivity.class);
					intent4.putExtra("flag", "4");
					intent4.putExtra("DB_ID", default_account.getText().toString());
					startActivityForResult(intent4, 4);
				} else {
					Toast.makeText(NewAccountActivity.this, "请先选择默认账套",
							Toast.LENGTH_SHORT).show();
				}
				break;
			default:
				break;
		}
	}

	private void saveBitmap(Bitmap localBitmap, String str) {
		File file = new File("/sdcard/DCIM/Camera/" + str);
		if (file.exists()) {
			file.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (localBitmap.compress(Bitmap.CompressFormat.PNG, 40, out)) {
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sp.edit().putString("er_file", "" + file).commit();
	}

	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	private void saveBitmap(Bitmap localBitmap) {
		// 第一步:将Bitmap压缩至字节数组输出流ByteArrayOutputStream
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		localBitmap.compress(Bitmap.CompressFormat.PNG, 80,
				byteArrayOutputStream);
		// 第二步:利用Base64将字节数组输出流中的数据转换成字符串String
		byte[] byteArray = byteArrayOutputStream.toByteArray();
		String imageER = new String(Base64.encodeToString(byteArray,
				Base64.DEFAULT));
		InputStream ERIMG = new ByteArrayInputStream(imageER.getBytes());
		// 第三步:将String保持至SharedPreferences
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("STR_erImg", imageER);
		editor.putString("imageER", "" + ERIMG);
		editor.commit();
		if (hasSdcard()) {
			File fileEr = new File(Environment.getExternalStorageDirectory(),
					imageER);
			String pathEr = Uri.fromFile(tempFile).getPath();
			sp.edit().putString("ERFILE", pathEr).commit();

			File file_path = new File(pathEr);
			BufferedOutputStream bos;
			try {
				bos = new BufferedOutputStream(new FileOutputStream(file_path));
				localBitmap.compress(Bitmap.CompressFormat.JPEG, 30, bos);
				bos.flush();
				bos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			sp.edit().putString("ERFILE", "" + file_path).commit();
		}
	}

	private boolean hasSdcard() {
		// 判断ＳＤ卡手否是安装好的
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case 1:
				if (resultCode == 1) {
					best_db = data.getStringExtra("data_return");
					default_account.setText(best_db);
					sp.edit().putString("ACCOUNTID", best_db).commit();
					Log.e("LiNing", "提交的id====" + best_db);
				}
				break;
			case 2:
				if (resultCode == 1) {
					String str1 = data.getStringExtra("data_dep");
					String IDdep = data.getStringExtra("data_dep_id");
					sp.edit().putString("DEPID", IDdep).commit();
					Log.e("LiNing", "提交的id====" + IDdep);
					default_dep.setText(str1);
				}
				break;
			case 3:
				if (resultCode == 1) {
					String str1 = data.getStringExtra("data_dep");
					String IDcust = data.getStringExtra("data_dep_id");
					sp.edit().putString("CUSTID", IDcust).commit();
					Log.e("LiNing", "提交的id====" + IDcust);
					default_cust.setText(str1);
				}
				break;
			case 4:
				if (resultCode == 1) {
					String str1 = data.getStringExtra("data_dep");
					String IDuser = data.getStringExtra("data_dep_id");
					sp.edit().putString("USERID", IDuser).commit();
					Log.e("LiNing", "提交的id====" + IDuser);
					default_user.setText(str1);
				}
				break;
			case PHOTO_REQUEST_GALLERY:
				// 从相册返回的数据
				if (data != null) {
					// 得到图片的全路径
					Uri uri = data.getData();
					Log.e("LiNing", "-------" + uri);
					crop(uri);
					String[] pojo = { MediaColumns.DATA };
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

	private void getBitmapFromSharedPreferences() {
		// 第一步:取出字符串形式的Bitmap
		String imageString = sp.getString("image", "");
		if (imageString != null) {
			// 第二步:利用Base64将字符串转换为ByteArrayInputStream
			byte[] byteArray = Base64.decode(imageString, Base64.DEFAULT);
			if (byteArray.length == 0) {
				signatureImg.setImageResource(R.drawable.ic_launcher);
			} else {
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
						byteArray);

				// 第三步:利用ByteArrayInputStream生成Bitmap
				Bitmap bitmap = BitmapFactory
						.decodeStream(byteArrayInputStream);
				signatureImg.setImageBitmap(bitmap);
			}
		} else {
			signatureImg.setImageBitmap(null);
		}

	}

	private File tempFile;

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
