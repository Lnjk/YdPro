package com.example.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.LeftOrRight.MyHScrollView;
import com.example.bean.FollInfos;
import com.example.bean.SysApplication;
import com.example.bean.URLS;
import com.example.ydshoa.CustersAllActivity;
import com.example.ydshoa.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/11/12 0009.
 */

public class GzFragment extends Fragment {
//    fragment实现不同页面切换
    private Context context;
    private View view;
    private SharedPreferences sp;
    private String session,qty_cust_zt,qty_cust_dabh;
    //回调信息
    String vipid_hd, psxxid_hd, xsgw_z, idtoname, idtoname_gw;
    //跟踪信息表格
    private TextView tv_bt_xxg;
    private Button bg_gzxx_set, bg_gzxx_del;
    private int gz_do;
    private ListView lv_xxgz;
    private RelativeLayout head_xxgz;
    String xxgz_query = URLS.cust_gzxx_query;
    String xxgz_del = URLS.cust_gzxx_del;
    FollAdapter follAdapter;
    List<FollInfos.FollList> follList_All;
    private List<Integer> checkedIndexList;
    private List<CheckBox> checkBoxList;
    private List<String> idList;
    String format,foll_no_arr, sub_follNo;
    String extra_foll_time, extra_foll_way, extra_foll_them, extra_foll_case, extra_foll_jdlb, extra_foll_per, extra_foll_userno;
    @Override
    public View onCreateView(LayoutInflater paramLayoutInflater,
                             ViewGroup paramViewGroup, Bundle savedInstanceState) {
        this.view = paramLayoutInflater.inflate(R.layout.gz_fragment,
                paramViewGroup, false);
        this.context = getActivity();
        sp = getActivity().getSharedPreferences("ydbg", 0);
        session = sp.getString("SESSION", "");
        qty_cust_zt = sp.getString("FRG_ZT", "");
        qty_cust_dabh = sp.getString("FRG_NO", "");
        SysApplication.getInstance().addActivity(getActivity());

        initData();// 初始化
        return this.view;
    }

    private void initData() {
        tv_bt_xxg = (TextView) view.findViewById(R.id.tv_id_btnName);
        bg_gzxx_set = (Button) view.findViewById(R.id.btn_gzxx_xg);
        bg_gzxx_del = (Button) view.findViewById(R.id.btn_gzxx_sc);

        lv_xxgz = (ListView) view.findViewById(R.id.lv_xxobj_header);
        head_xxgz = (RelativeLayout) view.findViewById(R.id.xxObj_head);
        head_xxgz.setFocusable(true);
        head_xxgz.setClickable(true);
        head_xxgz.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        lv_xxgz.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        getFool_xx();
    }
    private void getFool_xx() {
//        if (!qty_cust_zt.equals("") && !qty_cust_dabh.equals("")) {
        if (!context.equals("") && !context.equals("")) {
            OkHttpClient client = new OkHttpClient();
            FormBody localFormBody = new FormBody.Builder()
                    .add("Cust_Acc", qty_cust_zt)
                    .add("Cust_No", qty_cust_dabh)
                    .build();
            Request localRequest = new Request.Builder()
                    .addHeader("cookie", session).url(xxgz_query)
                    .post(localFormBody)
                    .build();
            client.newCall(localRequest).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String str = response.body().string();
                    Log.e("LiNing", "信息数据====" + str);
                    // 解析包含date的数据必须添加此代码(InputStream型)
                    Gson gson = new GsonBuilder().setDateFormat(
                            "yyyy-MM-dd HH:mm:ss").create();
                    final FollInfos cInfoDB = gson.fromJson(str,
                            FollInfos.class);
                    if (cInfoDB != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                follList_All = cInfoDB.getFollList();
                                follAdapter = new FollAdapter(R.layout.xxobj_head, follList_All, context);
                                lv_xxgz.setAdapter(follAdapter);
                                follAdapter.notifyDataSetChanged();
                                checkedIndexList.clear();
                                idList.clear();
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call call, IOException e) {

                }
            });
        }
    }
    public class FollAdapter extends BaseAdapter {
        //跟踪信息适配器
        int id_row;
        List<FollInfos.FollList> foll_infos;
        LayoutInflater mInflater;
        private int selectedPosition = -1;// 选中的位置

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public FollAdapter(int xxobj_head, List<FollInfos.FollList> follList_All, Context context) {
            this.id_row = xxobj_head;
            this.foll_infos = follList_All;
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return foll_infos.size();
        }

        @Override
        public Object getItem(int position) {
            return foll_infos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                synchronized (getActivity()) {
                    convertView = mInflater.inflate(id_row, null);
                    holder = new ViewHolder();
                    MyHScrollView scrollView1 = (MyHScrollView) convertView
                            .findViewById(R.id.horizontalScrollView1);
                    holder.scrollView = scrollView1;
                    holder.checkbox = (CheckBox) convertView.findViewById(R.id.xxobj_listDeleteCheckBox);
                    holder.checkbox.setVisibility(View.VISIBLE);
                    holder.layouts = (RelativeLayout) convertView
                            .findViewById(R.id.rl_item);
                    holder.foll_dabh = (TextView) convertView.findViewById(R.id.textView1_id_dabh);
                    holder.foll_gzbh = (TextView) convertView.findViewById(R.id.textView2_gzbh);
                    holder.foll_gjrq = (TextView) convertView.findViewById(R.id.textView3_gjrq);
                    holder.foll_gjfs = (TextView) convertView.findViewById(R.id.textView4_gjfs);
                    holder.foll_gjzt = (TextView) convertView.findViewById(R.id.textView5_gjzt);
                    holder.foll_gjqk = (TextView) convertView.findViewById(R.id.textView6_gjqk);
                    holder.foll_jdlb = (TextView) convertView.findViewById(R.id.textView7_jdlb);
                    holder.foll_gjry = (TextView) convertView.findViewById(R.id.textView8_gjry);
                    MyHScrollView headSrcrollView = (MyHScrollView) head_xxgz
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
            FollInfos.FollList follList = foll_infos.get(position);
            holder.foll_dabh.setText(follList.getCust_No().toString());
            holder.foll_gzbh.setText(follList.getFoll_No().toString());
            //            holder.foll_gjrq.setText(follList.getFoll_DD().toString());
//            SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sf1 = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
            try {
                Date parse = sf1.parse(follList.getFoll_DD().toString());
                format = new SimpleDateFormat("yyyy-MM-dd").format(parse);
                Log.e("LiNing", "时间====xin=====" + format);
                holder.foll_gjrq.setText(format);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            holder.foll_gjfs.setText(follList.getFoll_Way().toString());
            holder.foll_gjzt.setText(follList.getFoll_Them().toString());
            holder.foll_gjqk.setText(follList.getFoll_Case().toString());
            holder.foll_jdlb.setText(follList.getStag_Class().toString());
            holder.foll_gjry.setText(follList.getFoll_Per().toString());
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


        class ViewHolder {
            CheckBox checkbox;
            public RelativeLayout layouts;
            HorizontalScrollView scrollView;
            public TextView foll_dabh;
            public TextView foll_gzbh;
            public TextView foll_gjrq;
            public TextView foll_gjfs;
            public TextView foll_gjzt;
            public TextView foll_gjqk;
            public TextView foll_jdlb;
            public TextView foll_gjry;


        }
    }
    public class CheckBoxListener implements CompoundButton.OnCheckedChangeListener {
        int positions;
        private String userIds;

        public CheckBoxListener(int position) {
            this.positions = position;

        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                checkedIndexList.add(positions);
                foll_no_arr = follList_All.get(positions).getFoll_No();
                idList.add(foll_no_arr);
                String zts_str = "";
                for (String querys_db : idList) {
                    zts_str += querys_db + ",";
                }
                sub_follNo = zts_str.substring(0, zts_str.length() - 1);

                extra_foll_time = follList_All.get(positions).getFoll_DD().toString();
                extra_foll_way = follList_All.get(positions).getFoll_Way().toString();
                extra_foll_them = follList_All.get(positions).getFoll_Them().toString();
                extra_foll_case = follList_All.get(positions).getFoll_Case().toString();
                extra_foll_jdlb = follList_All.get(positions).getStag_Class().toString();
                extra_foll_per = follList_All.get(positions).getFoll_Per().toString();
                extra_foll_userno = follList_All.get(positions).getUser_No().toString();
            } else {
                checkedIndexList.remove((Integer) positions);
                Log.e("LiNing", "--------删除集合" + checkedIndexList);
                idList.remove(foll_no_arr);
                if (idList != null && idList.size() > 0) {
//                isnull = 1;
                    String zts_str = "";
                    for (String querys_db : idList) {
                        zts_str += querys_db + ",";
                    }
                    sub_follNo = zts_str.substring(0, zts_str.length() - 1);
                    Log.e("LiNing", "--------sub_id" + sub_follNo);
                } else {
                    Toast.makeText(getActivity(), "未选中数据", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {
        ListViewAndHeadViewTouchLinstener() {
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ((HorizontalScrollView) head_xxgz
                    .findViewById(R.id.horizontalScrollView1))
                    .onTouchEvent(event);
            return false;
        }

    }

}
