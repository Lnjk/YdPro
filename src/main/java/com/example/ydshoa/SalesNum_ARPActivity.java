package com.example.ydshoa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.URLS;
import com.example.bean.UserInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SalesNum_ARPActivity extends Activity {
    private Context context;
    private GridView gridView;
    private LayoutInflater inflater;
    private List<Picture> pictures;
    private TextView head;
    private String reportBus;
    // ID
    private String[] IDs = new String[]{"ArpTG", "ArpTGGC", "ArpTGP", "ArpTGC",
            "ArpTGS", "ArpTGD", "ArpTGPC", "ArpTGPD", "ArpTGPS", "ArpTGPGC",
            "ArpTGPCGC", "ArpTGPDGC", "ArpTGPSGC"};
    // 文字标题
    private String[] titles = new String[]{"账套",
            "账套+终端网点",
            "账套+核算单位",
            "账套+渠道",
            "账套+业务",
            "账套+部门",
            "账套+核算单位+渠道",
            "账套+核算单位+部门",
            "账套+核算单位+业务",
            "账套+核算单位+终端网点",
            "账套+核算单位+渠道+终端网点",
            "账套+核算单位+部门+终端网点",
            "账套+核算单位+业务+终端网点"};

    // 图片数组
    private int[] images = new int[]{R.drawable.aa, R.drawable.bb,
            R.drawable.cc, R.drawable.ooo, R.drawable.ee,
            R.drawable.ff, R.drawable.gg, R.drawable.jjj,
            R.drawable.ii, R.drawable.jj, R.drawable.kk,
            R.drawable.ll, R.drawable.mm};
    private PictureAdapter adapter;
    private int flag;
    String url = URLS.userInfo_url;
    private String session;
    private SharedPreferences sp;
    ArrayList<String> modIds_get = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sales_num__arp);
        context = SalesNum_ARPActivity.this;
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
//		PushAgent.getInstance(context).onAppStart();
        head = (TextView) findViewById(R.id.all_head);
        head.setText("报表种类");
        reportBus = getIntent().getStringExtra("reportB");// 获取业务类型
        gridView = (GridView) findViewById(R.id.gridview_arp);
        getRoot();
        adapter = new PictureAdapter(IDs, titles, images, this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                int mSelected = position;
                adapter.setSelectItem(position);

                String ID_zl = pictures.get(position).getId();
                String name_zl = pictures.get(position).getTitle();
                // 传递的id值
                Log.e("LiNing", "---------" + ID_zl);
                if(modIds_get.contains(ID_zl)){

                    Intent intent = new Intent(context, SalesQueryArpActivity.class);
                    intent.putExtra("reportB", reportBus);
                    intent.putExtra("reportNo", ID_zl);
                    intent.putExtra("reportName", name_zl);
                    startActivity(intent);
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(context, "请等待...", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void getRoot() {
        OkHttpClient client = new OkHttpClient();
        Request localRequest = new Request.Builder()
                .addHeader("cookie", session).url(url).build();
        client.newCall(localRequest).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                String string = response.body().string();
                Log.e("LiNing", "------" + response.code() + "------" + string);
                Gson gson = new GsonBuilder().setDateFormat(
                        "yyyy-MM-dd HH:mm:ss").create();
                UserInfo info = gson.fromJson(string, UserInfo.class);
                if (info.getUser_Id().equalsIgnoreCase("admin")
                        && info.getUser_Pwd().equalsIgnoreCase("admin")) {
                    flag = 1;
                } else {
                    // }
                    List<com.example.bean.UserInfo.User_Mod> user_Mod = info
                            .getUser_Mod();
                    if (user_Mod.size() > 0 && user_Mod != null) {
                        for (int i = 0; i < user_Mod.size(); i++) {

                            String mod_ID = user_Mod.get(i).getMod_ID();
                            modIds_get.add(mod_ID);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call arg0, IOException arg1) {

            }
        });
    }
    class PictureAdapter extends BaseAdapter {
        private int selectItem = -1;

        public void setSelectItem(int selectItem) {
            this.selectItem = selectItem;
        }

        public PictureAdapter(String[] ids, String[] titles, int[] images,
                              Context context) {
            super();
            pictures = new ArrayList<Picture>();
            inflater = LayoutInflater.from(context);
            for (int i = 0; i < images.length; i++) {
                Picture picture = new Picture(ids[i], titles[i], images[i]);
                pictures.add(picture);
            }
        }

        @Override
        public int getCount() {
            if (null != pictures) {
                return pictures.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return pictures.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.picture_item, null);
                viewHolder = new ViewHolder();
                viewHolder.title = (TextView) convertView
                        .findViewById(R.id.title);
                viewHolder.image = (ImageView) convertView
                        .findViewById(R.id.image);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.title.setText(pictures.get(position).getTitle());
            viewHolder.image.setImageResource(pictures.get(position)
                    .getImageId());
            if (selectItem == position) {
                convertView.setBackgroundColor(Color.parseColor("#ff0000"));
            } else {
                convertView.setBackgroundColor(Color.parseColor("#F2F2F2"));
            }
            return convertView;
        }
    }

    class ViewHolder {
        public TextView title;
        public ImageView image;
    }

    class Picture {
        private String id;
        private String title;
        private int imageId;

        public Picture() {
            super();
        }

        public Picture(String id, String title, int imageId) {
            super();
            this.id = id;
            this.title = title;
            this.imageId = imageId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getImageId() {
            return imageId;
        }

        public void setImageId(int imageId) {
            this.imageId = imageId;
        }
    }

    public void allback(View v) {
        finish();
    }
}
