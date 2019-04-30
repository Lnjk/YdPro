package com.example.ydshoa;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.LeftOrRight.MyHScrollView;
import com.example.bean.DepInfo;
import com.example.bean.ReceiptSkdForm;
import com.example.bean.SkdYingsMx;
import com.example.bean.URLS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class YuShouActivity extends Activity {
    private Context context;
    private SharedPreferences sp;
    private String session, db_id, kh_id, s_zh, clientRows,name;
    String url_add_mx = URLS.skd_url_query;
    List<ReceiptSkdForm.Mf_monList> mf_monList;
    List<ReceiptSkdForm.Mf_monList> mf_monList_all = new ArrayList<ReceiptSkdForm.Mf_monList>();
    private SkdsQtyAdapter qtyAdapter;
    private View below;
    private ListView lv_query_skd;
    MyHScrollView headSrcrollView;
    RelativeLayout mHead_yis_item, mHead_yings;
    private String url_prdNo = URLS.prdNo_url;//通过id获取名称
    List<DepInfo.IdNameList> depInfo;
    private Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yu_shou);
        sp = getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        context = YuShouActivity.this;
        db_id = getIntent().getStringExtra("DB_ID");
        kh_id = getIntent().getStringExtra("ZD_KH");
        lv_query_skd = (ListView) findViewById(R.id.lv_skd_header_yus);
        below = getLayoutInflater().inflate(R.layout.bottom_jz, null);
        mHead_yings = (RelativeLayout) findViewById(R.id.skdmx_head_yus);
        mHead_yings.setFocusable(true);
        mHead_yings.setClickable(true);
        mHead_yings.setOnTouchListener(new YuShouActivity.ListViewAndHeadViewTouchLinstener_yings());
        get_yys_mx();
    }

    private void get_yys_mx() {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("db_Id", db_id)
                .add("cus_NO", "KH347")
                .add("showRow", "50")
                .add("clientRows", "0")
                .add("irp_Id", "T")
                .add("cls_Id", "F")
                .build();
        Log.e("LiNing", "提交数据" + db_id);
        client.newCall(new Request.Builder().addHeader("cookie", session).post(body)
                .url(url_add_mx).build()).enqueue(new Callback() {


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e("LiNing", "所有收款单===new" + str);
                if (str != null && !str.equals("") && !str.equals("null")) {

                    // 解析包含date的数据必须添加此代码(InputStream型)
                    Gson gson = new GsonBuilder().setDateFormat(
                            "yyyy-MM-dd HH:mm:ss").create();
                    final ReceiptSkdForm skd_all = gson.fromJson(str,
                            ReceiptSkdForm.class);
                    int sumShowRow = skd_all.getSumShowRow();
                    s_zh = String.valueOf(sumShowRow);
                    clientRows = s_zh;
                    if (skd_all != null) {
                        YuShouActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                mf_monList = skd_all.getMf_monList();

                                if (mf_monList != null && mf_monList.size() > 0) {

                                    mf_monList_all.addAll(mf_monList);
                                    qtyAdapter = new SkdsQtyAdapter(R.layout.skd_had, mf_monList_all, context);
                                    lv_query_skd.addFooterView(below);
                                    lv_query_skd.setAdapter(qtyAdapter);
                                    qtyAdapter.notifyDataSetChanged();

                                }
                            }
                        });
                    }

                }
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    public class SkdsQtyAdapter extends BaseAdapter {
        int id_row_layout;
        LayoutInflater mInflater;
        List<ReceiptSkdForm.Mf_monList> skdqty_infos;
        //item高亮显示
        private int selectItem = -1;
        double v_ysje,v_ycje;
        public void setSelectItem(int selectItem) {
            this.selectItem = selectItem;
        }

        public SkdsQtyAdapter(int skd_had, List<ReceiptSkdForm.Mf_monList> mf_monList, Context context) {
            this.id_row_layout = skd_had;
            this.skdqty_infos = mf_monList;
            this.mInflater = LayoutInflater.from(context);
        }

        public void bindData(List<ReceiptSkdForm.Mf_monList> mf_monList) {
            this.skdqty_infos = mf_monList;
        }

        @Override
        public int getCount() {
            return skdqty_infos.size();
        }

        @Override
        public Object getItem(int position) {
            return skdqty_infos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder_ysmx;
            if (convertView == null) {
                synchronized (YuShouActivity.this) {


                    convertView = mInflater.inflate(id_row_layout, null);
                    holder_ysmx = new ViewHolder();
                    MyHScrollView scrollView1 = (MyHScrollView) convertView
                            .findViewById(R.id.horizontalScrollView1);
                    holder_ysmx.scrollView = scrollView1;
                    holder_ysmx.yingsmx_xh = (TextView) convertView.findViewById(R.id.skdmx_yings_xh_item);
                    holder_ysmx.yingsmx_khbh = (TextView) convertView.findViewById(R.id.skdmx_yings_khbh_item);
                    holder_ysmx.yingsmx_khmc = (TextView) convertView.findViewById(R.id.skdmx_yings_khmc_item);
                    holder_ysmx.yingsmx_ysph = (TextView) convertView.findViewById(R.id.tv_skdmx_yingsph_item);
                    holder_ysmx.yingsmx_ysdh = (TextView) convertView.findViewById(R.id.tv_skdmx_yingsdh_item);
                    holder_ysmx.yingsmx_djrq = (TextView) convertView.findViewById(R.id.tv_skdmx_djrq_item);
                    holder_ysmx.yingsmx_ysje = (TextView) convertView.findViewById(R.id.tv_skdmx_yingsje_item);
                    holder_ysmx.yingsmx_ycje = (TextView) convertView.findViewById(R.id.tv_skdmx_yingsycje_item);
                    holder_ysmx.yingsmx_bccx = (EditText) convertView.findViewById(R.id.tv_skdmx_yingsbccx_item);
                    holder_ysmx.yingsmx_wcjy = (TextView) convertView.findViewById(R.id.tv_skdmx_yingswcjy_item);
                    holder_ysmx.yingsmx_bz = (TextView) convertView.findViewById(R.id.tv_skdmx_yingsbz_item);
                    headSrcrollView = (MyHScrollView) mHead_yings
                            .findViewById(R.id.horizontalScrollView1);
                    headSrcrollView
                            .AddOnScrollChangedListener(new OnScrollChangedListenerImp(
                                    scrollView1));
                    convertView.setTag(holder_ysmx);
                }
            } else {
                holder_ysmx = (ViewHolder) convertView.getTag();
            }
            ReceiptSkdForm.Mf_monList mfArpList = skdqty_infos.get(position);
            //编辑EditextSet(******放于此处避免listview数据错乱)
            EditextSet_add(holder_ysmx, mfArpList);
            int id_add = position + 1;
            holder_ysmx.yingsmx_xh.setText("" + id_add);
            if(mfArpList.getTf_MON_Z().getCus_NO_OS()!=null){
                holder_ysmx.yingsmx_khbh.setText(mfArpList.getTf_MON_Z().getCus_NO_OS());
            }else{
                holder_ysmx.yingsmx_khbh.setText("无");
            }

            holder_ysmx.yingsmx_bz.setText(mfArpList.getRem());
            //客户名称（id转化）
            //此处请求接口获取名称
            OkHttpClient client = new OkHttpClient();
            Log.e("LiNing", "-----" + mfArpList.getTf_MON_Z().getCus_NO_OS().toString());
            FormBody body = new FormBody.Builder().add("accountNo", "DB_BJ18")
                    .add("id",  mfArpList.getTf_MON_Z().getCus_NO_OS()).build();
            Request request = new Request.Builder()
                    .addHeader("cookie", session).url(url_prdNo).post(body)
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String str = response.body().string();
                    Log.e("LiNing", "查询数据===" + str);
                    final DepInfo dInfo = new Gson().fromJson(str,
                            DepInfo.class);
                    if (dInfo != null) {
                        YuShouActivity.this
                                .runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        depInfo = dInfo.getIdNameList();
                                        if (depInfo != null && depInfo.size() > 0) {
                                            for (int i = 0; i < depInfo.size(); i++) {
                                                name = depInfo.get(i).getName();
                                                holder_ysmx.yingsmx_khmc.setText(name);
                                                skdqty_infos.get(i).getTf_MON().setYis_khmc(name);
                                                Log.e("LiNing", "数据name" + skdqty_infos.get(position).getTf_MON().getYis_khmc());
                                            }
                                        }
                                        skdqty_infos.get(position).getTf_MON().setYis_khmc(name);
//                                        holder.price_name.setText(infos.get(position).getNAME_ZDY());
                                        Log.e("LiNing", "数据name---" + skdqty_infos.get(position).getTf_MON().getYis_khmc());
                                    }

                                });
                    }
                }

                @Override
                public void onFailure(Call call, IOException e) {

                }


            });
            holder_ysmx.yingsmx_ysph.setText("应收票号");
            holder_ysmx.yingsmx_ysdh.setText(mfArpList.getRp_NO());
            SimpleDateFormat sf1 = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
            try {
                date = sf1.parse(mfArpList.getRp_DD().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
            String format_data = sf2.format(date);
            holder_ysmx.yingsmx_djrq.setText(format_data);
            if (mfArpList.getAmtn().toString() != null) {

                v_ysje = new BigDecimal(mfArpList.getAmtn()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                holder_ysmx.yingsmx_ysje.setText("" + v_ysje);
            }
            if (mfArpList.getAmtn_ARP().toString() != null) {

                v_ycje = new BigDecimal(mfArpList.getAmtn_ARP()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                holder_ysmx.yingsmx_ycje.setText("" + v_ycje);
            }
            double v_wcjy = v_ysje - v_ysje;
            skdqty_infos.get(position).getTf_MON().setYis_wcjy("" + v_wcjy);
//            holder_ysmx.yingsmx_wcjy.setText("" +v_wcjy );
            holder_ysmx.yingsmx_wcjy.setText(skdqty_infos.get(position).getTf_MON().getYis_wcjy());
            skdqty_infos.get(position).getTf_MON().setYis_ysph("应收票号");
            holder_ysmx.yingsmx_ysph.setText(skdqty_infos.get(position).getTf_MON().getYis_ysph());
            return convertView;
        }

        private void EditextSet_add(ViewHolder holder_ysmx, final ReceiptSkdForm.Mf_monList mfArpList) {
            if (holder_ysmx.yingsmx_bccx.getTag() instanceof TextWatcher) {
                holder_ysmx.yingsmx_bccx.removeTextChangedListener((TextWatcher) holder_ysmx.yingsmx_bccx.getTag());
            }


            TextWatcher watcher_bccx = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    //editext改变，更新textview的数据
                    if (TextUtils.isEmpty(s)) {
                        mfArpList.getTf_MON().setYis_bccx("");
                    } else {
                        mfArpList.getTf_MON().setYis_bccx(s.toString());
                    }
                }
            };
            holder_ysmx.yingsmx_bccx.addTextChangedListener(watcher_bccx);
            holder_ysmx.yingsmx_bccx.setTag(watcher_bccx);
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
            class ViewHolder {

                HorizontalScrollView scrollView;
                public TextView yingsmx_xh;
                public TextView yingsmx_khbh;
                public TextView yingsmx_khmc;
                public TextView yingsmx_ysph;
                public TextView yingsmx_ysdh;
                public TextView yingsmx_djrq;
                public TextView yingsmx_ysje;
                public TextView yingsmx_ycje;
                public EditText yingsmx_bccx;
                public TextView yingsmx_wcjy;
                public TextView yingsmx_bz;
            }
        }
    class ListViewAndHeadViewTouchLinstener_yings implements View.OnTouchListener {
        ListViewAndHeadViewTouchLinstener_yings() {
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ((HorizontalScrollView) YuShouActivity.this.mHead_yings
                    .findViewById(R.id.horizontalScrollView1))
                    .onTouchEvent(event);
            return false;
        }

    }

}