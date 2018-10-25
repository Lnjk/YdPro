package com.example.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bean.GetPriceInfo;
import com.example.ydshoa.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ${宁.金珂} on 2018/7/9.
 */

public class PriceGetAdapter extends BaseAdapter {
    ViewHolder holder = null;
    private Context context;
    int id_row_layout;
    LayoutInflater mInflater;
    private List<GetPriceInfo.Prices> infos;
    //item高亮显示
    private int selectItem = -1;
    private Date date;
    private String llr;

    public PriceGetAdapter(int quick_head_main_item, List<GetPriceInfo.Prices> prices_main, Context context, String lrr_mr) {
        this.id_row_layout=quick_head_main_item;
        this.infos=prices_main;
        this.context=context;
        mInflater=LayoutInflater.from(context);
        this.llr=lrr_mr;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }


    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public Object getItem(int position) {
        return infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            holder=new ViewHolder();
            convertView=LayoutInflater.from(context).inflate(this.id_row_layout, null);
            holder.priceNo= (TextView) convertView.findViewById(R.id.main_dh);
            holder.price_name= (TextView) convertView.findViewById(R.id.main_name);
            holder.price_data= (TextView) convertView.findViewById(R.id.main_time);
//            holder.user_login= (TextView) convertView.findViewById(R.id.main_lrr);
            holder.user_comp= (TextView) convertView.findViewById(R.id.main_shr);
            holder.price_zt= (TextView) convertView.findViewById(R.id.main_zt);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        GetPriceInfo.Prices prices_info = infos.get(position);
        String priceId = prices_info.getPriceId();
        String pricename = prices_info.getPriceName();
        String price_dd = prices_info.getPrice_DD().toString();
        SimpleDateFormat sf1 = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
        try {
            date = sf1.parse(price_dd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
        String format_data = sf2.format(date);
        String compDepName = prices_info.getBiln_user();
        String zt = prices_info.getAccepted();
        if(zt.equals("Y")){
            holder.price_zt.setText("已执行");
        }else{
            holder.price_zt.setText("未执行");
        }
        holder.priceNo.setText(priceId);
        holder.price_name.setText(pricename);
        holder.price_data.setText(format_data);
//        holder.user_login.setText(llr);
        holder.user_comp.setText(compDepName);

        if (position == selectItem) {
            convertView.setBackgroundColor(Color.RED);
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }
        return convertView;
    }
    public class ViewHolder {
        public TextView priceNo;
        public TextView price_name;
        public TextView price_data;
        public TextView user_login;
        public TextView user_comp;
        public TextView price_zt;

    }
}
