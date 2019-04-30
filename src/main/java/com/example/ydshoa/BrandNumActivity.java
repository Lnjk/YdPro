package com.example.ydshoa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

//import com.umeng.message.PushAgent;

import java.util.ArrayList;
import java.util.List;

public class BrandNumActivity extends Activity {
    private Context context;
    private GridView gridView;
    private LayoutInflater inflater;
    private List<Picture> pictures;
    private TextView head;
    private String reportBus;
    // ID
    private String[] IDs = new String[] { "SATG", "SATGP", "SATGPC", "SATGPD",
            "SATGPS", "SATGPGC", "SATGC", "SATGCD", "SATGCS", "SATGCGC",
            "SATGD", "SATGDS", "SATGDGC", "SATGS", "SATGSGC", "SATGGC",
            "SATGPPN", "SATGPI", "SATGPIPN", "SATGPCPN", "SATGPCI",
            "SATGPCIPN", "SATGPDPN", "SATGPDI", "SATGPDIPN", "SATGPSPN",
            "SATGPSI", "SATGPSIPN", "SATGPGCPN", "SATGPGCI", "SATGPGCIPN" };
    // 文字标题
    private String[] titles = new String[] { "账套", "账套+品牌", "账套+品牌+渠道",
            "账套+品牌+部门", "账套+品牌+业务", "账套+品牌+终端网点", "账套+渠道",
            "账套+渠道+部门", "账套+渠道+业务", "账套+渠道+终端网点", "账套+部门",
            "账套+部门+业务", "账套+部门+终端网点", "账套+业务", "账套+业务+终端网点",
            "账套+终端网点", "账套+品牌+品号", "账套+品牌+货品中类", "账套+品牌+货品中类+品号",
            "账套+品牌+渠道+品号", "账套+品牌+渠道+货品中类", "账套+品牌+渠道+货品中类+品号",
            "账套+品牌+部门+品号", "账套+品牌+部门+货品中类", "账套+品牌+部门+货品中类+品号",
            "账套+品牌+业务+品号", "账套+品牌+业务+货品中类", "账套+品牌+业务+货品中类+品号",
            "账套+品牌+终端+品号", "账套+品牌+终端+货品中类", "账套+品牌+终端+货品中类+品号" };
    // 图片数组
    private int[] images = new int[] { R.drawable.aa, R.drawable.bb,
            R.drawable.cc, R.drawable.ooo, R.drawable.ee,
            R.drawable.ff, R.drawable.gg, R.drawable.jjj,
            R.drawable.ii, R.drawable.jj, R.drawable.kk,
            R.drawable.ll, R.drawable.mm, R.drawable.nn,
            R.drawable.oo, R.drawable.pp, R.drawable.qq,
            R.drawable.rr, R.drawable.ss, R.drawable.tt,
            R.drawable.uu, R.drawable.vv, R.drawable.ww,
            R.drawable.xx, R.drawable.yy, R.drawable.zz,
            R.drawable.aaa, R.drawable.bbb, R.drawable.ccc,
            R.drawable.ddd, R.drawable.eee };
    private PictureAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_brand_num);
        context = BrandNumActivity.this;
        //推送
//        PushAgent.getInstance(context).onAppStart();
        head = (TextView) findViewById(R.id.all_head);
        head.setText("报表种类");
        reportBus = getIntent().getStringExtra("reportB");// 获取业务类型
        gridView = (GridView) findViewById(R.id.gridview);
        adapter = new PictureAdapter(IDs, titles, images, this);
        gridView.setAdapter(adapter);

//        if(getIntent().getStringExtra("flag").equals("2")){
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    int mSelected = position;
                    adapter.setSelectItem(position);

                    String ID_zl = pictures.get(position).getId();
                    String name_zl = pictures.get(position).getTitle();
                    Intent localIntent = getIntent();
                    localIntent.putExtra("condition_db", ID_zl);
                    localIntent.putExtra("condition_name", name_zl);
                    setResult(1, localIntent);
                    finish();
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
