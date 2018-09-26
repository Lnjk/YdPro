package com.example.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.bean.ViewHolder;
import com.example.ydshoa.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/9/26 0026.
 */

public class LocalAdapter extends BaseAdapter {
    public static HashMap<Integer, Boolean> isSelected;
    private Context context = null;
    private LayoutInflater inflater = null;
    private String keyString[] = null;
    private String itemString = null; // 记录每个item中textview的值
    private int idValue[] = null;// id值
    ArrayList<HashMap<String, Object>> list;

    public LocalAdapter(Context context, ArrayList<HashMap<String, Object>> depInfo, int info_item_four, String[] strings, int[] ints) {
        this.context = context;
        this.list = depInfo;
        this.keyString = new String[strings.length];
        this.idValue = new int[ints.length];
        System.arraycopy(strings, 0, this.keyString, 0,
                strings.length);
        System.arraycopy(ints, 0, this.idValue, 0,
                ints.length);
        inflater = LayoutInflater.from(context);
        init();
    }
    private void init() {
        isSelected = new HashMap<Integer, Boolean>();
        for (int i = 0; i < list.size(); i++) {
            isSelected.put(i, false);
        }
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        ViewHolder localViewHolder = new ViewHolder();
        if (paramView == null) {
            paramView = inflater.inflate(R.layout.info_item_four, null);
            localViewHolder.id = (TextView) paramView.findViewById(R.id.author_id);
            localViewHolder.tv = (TextView) paramView.findViewById(R.id.author);
            localViewHolder.cb = (CheckBox) paramView.findViewById(R.id.radio);
            paramView.setTag(localViewHolder);
        }else{
            localViewHolder=(ViewHolder) paramView.getTag();
        }
//        DepInfo.IdNameList localHashMap = list.get(paramInt);
        HashMap<String, Object> localHashMap = list.get(paramInt);
        Log.e("LiNing", "222222222222222222" + localHashMap);

        if (localHashMap != null) {
            String id = (String) localHashMap.get("序号").toString();
            String name = (String) localHashMap.get("名称");
            Log.e("LiNing", "1111111" + name+id);
            localViewHolder.id.setText(id);
            localViewHolder.tv.setText(name);
        }
        localViewHolder.cb.setChecked(isSelected.get(paramInt));
        return paramView;
    }
}
