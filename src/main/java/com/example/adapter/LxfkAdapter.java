package com.example.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bean.LxInfos;
import com.example.ydshoa.R;

import java.util.List;

/**
 * Created by Administrator on 2019/3/26 0026.
 */

public class LxfkAdapter extends BaseAdapter {
    int id_row_layout;
    LayoutInflater mInflater;
    List<LxInfos.IdNameList> lxfk_infos;
    //item高亮显示
    private int selectItem = -1;

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }
    public LxfkAdapter(int skd_had, List<LxInfos.IdNameList> mf_monList, Context context) {
        this.id_row_layout = skd_had;
        this.lxfk_infos = mf_monList;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lxfk_infos.size();
    }

    @Override
    public Object getItem(int position) {
        return lxfk_infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView=mInflater.inflate(id_row_layout,null);
            holder=new ViewHolder();
            holder.fklx_id= (TextView) convertView.findViewById(R.id.fk_id);
            holder.fklx_name= (TextView) convertView.findViewById(R.id.fk_name);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        LxInfos.IdNameList mf_monList_all = lxfk_infos.get(position);
        holder.fklx_id.setText(mf_monList_all.getId());
        holder.fklx_name.setText(mf_monList_all.getName());

        //操作修改，删除等
        if (position == selectItem) {
            convertView.setBackgroundColor(Color.RED);
//                Log.e("LiNing", "id_type结果====" + id_itm);
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }
        return convertView;
    }
    class ViewHolder {

        public TextView fklx_id;
        public TextView fklx_name;
    }
}
